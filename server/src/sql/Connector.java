package sql;

import routes.*;
import users.User;
import users.UsersCollection;
import utility.ServerMain;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class Connector {

        public static void loading(){
            Properties config = new Properties(); // файл с логином паролем для доступа к бд

            try {
                config.load(new FileInputStream("config.properties")); //загружаем етот файл
                Class.forName("org.postgresql.Driver"); //подключение драйвера
            } catch (ClassNotFoundException e){
                System.out.println("Необходимый для работы драйвер не был найден. \n\n Завершение программы");
                System.exit(0);
            } catch (IOException e){
                System.out.println("Не найден файл, содержащий необходимые для поключения к базе данных логин и пароль. \n\n Завершение программы");
                System.exit(0);
            }

            try(
                    Connection connection1 = DriverManager.getConnection(ServerMain.URL, config); //подключаемся к бд
                    Statement statement = connection1.createStatement(); //штука для взаимодействия с бд, создание запроса1
                    Statement statement2 = connection1.createStatement(); //ещё одна штука для взаимодействия с бд, создание запроса2
            ){
                ResultSet resRoutes = statement.executeQuery("SELECT * from routes"); //заполненние запроса. возвращает результат. представляет из себя таблицу
                while (resRoutes.next()){ //перебор строк результата

                    Long id = resRoutes.getLong("id");
                    String name = resRoutes.getString("name");
                    java.time.LocalDate creationDate = resRoutes.getDate("date").toLocalDate();
                    Float distance = resRoutes.getFloat("distance");
                    String creatorLogin = resRoutes.getString("login");

                    // понятия не имею что это такое ниже написано но оно написано
                    Long idCoord = resRoutes.getLong("coordinates");
                    Long idTo = resRoutes.getLong("locationTo");
                    Long idFrom = resRoutes.getLong("locationFrom");

                    ResultSet coord = statement2.executeQuery("SELECT * from coordinates where id="+idCoord);
                    coord.next();
                    Integer x = coord.getInt("x");
                    Float y = coord.getFloat("y");
                    Coordinates coordinates = new Coordinates(x,y);
                    coord.close();

                    ResultSet resTo = statement2.executeQuery("SELECT * from locationTo where id_to="+idTo);
                    resTo.next();
                    Location to = new Location(resRoutes.getLong("x"),
                            resRoutes.getDouble("y"),
                            resRoutes.getString("name"));
                    resTo.close();

                    ResultSet resFrom = statement2.executeQuery("SELECT * from locationFrom where id_from="+idFrom);
                    resFrom.next();
                    Location from = new Location(resRoutes.getLong("x"),
                            resRoutes.getDouble("y"),
                            resRoutes.getString("name"));
                    resFrom.close();

                    Route nextRoute = new Route (id, name, coordinates, creationDate, from, to, distance, creatorLogin);
                    utility.ServerMain.c.Routes.add(nextRoute);
                }
                resRoutes.close();

                ResultSet resultLogPass = statement.executeQuery("SELECT * from users");
                while(resultLogPass.next()){
                    String login = resultLogPass.getString("login");
                    String passwordForLog = resultLogPass.getString("password");
                    String totemAnimal =  resultLogPass.getString ("totemAnimal");
                    users.User user = new User(login,passwordForLog, totemAnimal);
                     UsersCollection.users.add(user);
                }

            }catch(SQLException e){
                System.out.println("Ошибка подключения к базе данных. \n\n Завершение программы");
                System.exit(0);
            }
        }

        public static void saving(){

            Properties config = new Properties();

            try{
                config.load(new FileInputStream("config.properties"));
                Class.forName("org.postgresql.Driver");
            } catch (ClassNotFoundException e){
                System.out.println("Необходимый для работы драйвер не был найден. \n\n Завершение программы");
                System.exit(0);
            }catch (IOException e){
                System.out.println("Не найден файл, содержащий необходимые для поключения к базе данных логин и пароль. \n\n Завершение программы");
                System.exit(0);
            }

            try(
                Connection connection1 = DriverManager.getConnection(ServerMain.URL, config);
                Statement statement = connection1.createStatement();
                Statement statement2 = connection1.createStatement()
            ){
                statement.execute("delete from routes"); //очищение таблицы с коллекцией рутов и рестарт отсчета айди
                statement.execute("SELECT SETVAL((SELECT pg_get_serial_sequence('routes', 'id')), 1, false)");

                statement.execute("delete from coordinates"); //очищение таблицы с коллекцией координат и рестарт отсчета айди
                statement.execute("SELECT SETVAL((SELECT pg_get_serial_sequence('coordinates', 'id')), 1, false)");

                statement.execute("delete from locationTo");//очищение таблицы с коллекцией локейшенов ту и рестарт отсчета айди
                statement.execute("SELECT SETVAL((SELECT pg_get_serial_sequence('locationTo', 'id')), 1, false)");

                statement.execute("delete from locationFrom");//очищение таблицы с коллекцией локейшенов фром и рестарт отсчета айди
                statement.execute("SELECT SETVAL((SELECT pg_get_serial_sequence('locationFrom', 'id')), 1, false)");

                statement.execute("delete from users"); //очищение таблицы с коллекцией юзеров

                ServerMain.c.Routes.stream()
                        .forEach(x -> {
                            try {
                                statement.execute("insert into coordinates values("
                                        + x.getCoordinates().getX() + ", "
                                        + x.getCoordinates().getY() + ", "
                                        + "(select nextval(SELECT pg_get_serial_sequence('coordinates', 'id'))));"); //заполнение новой строки таблицы координат


                                statement.execute("insert into locationFrom values("
                                        + x.getFrom().getX() + ", "
                                        + x.getFrom().getY() + ", '"
                                        + x.getFrom().getName() + "', "
                                        + "(select nextval(SELECT pg_get_serial_sequence('locationFrom', 'id'))));");

                                statement.execute("insert into locationTo values("
                                        + x.getTo().getX() + ", "
                                        + x.getTo().getY() + ", '"
                                        + x.getTo().getName() + "', "
                                        + "(select nextval(SELECT pg_get_serial_sequence('locationTo', 'id'))));");

                                statement.execute("insert into routes values("
                                        + "(select nextval(SELECT pg_get_serial_sequence('routes', 'id'))), '"
                                        + x.getName() +"', '"
                                        + x.getCreatorLogin()+ "', "
                                        + "(select nextval(SELECT pg_get_serial_sequence('routes', 'coordinates'))), "
                                        + x.getCreationDate() + ", "
                                        + "(select nextval(SELECT pg_get_serial_sequence('routes', 'locationFrom'))), "
                                        + "(select nextval(SELECT pg_get_serial_sequence('routes', 'locationTo'))), "
                                        + x.getDistance() + ", "
                                        +");");
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }

                        });

                UsersCollection.users.stream()
                        .filter(x -> !x.getPassword().equals(" "))
                        .forEach(x ->{
                            try {
                                statement.execute("insert into log_pas values('"
                                        + x.getLogin() + "', '"
                                        + x.getPassword() + "', '"
                                        + x.getTotemAnimal() + "')");
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        });

            }catch(SQLException e){
                System.out.println("Ошибка подключения к базе данных. \n\n Завершение программы");
                System.exit(0);
            }
        }
    }
