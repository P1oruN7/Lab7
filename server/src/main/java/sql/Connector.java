package sql;

import routes.Coordinates;
import routes.Location;
import routes.Route;
import utility.ServerMain;

import routes.*;
import java.sql.*;

import users.User;
import users.UsersCollection;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Connector {

    public static void loading(){
        Properties config = new Properties(); // файл с логином паролем для доступа к бд

        try {
            config.load(new FileInputStream("res/config.properties")); //загружаем етот файл
            Class.forName("org.postgresql.Driver"); //подключение драйвера
        } catch (ClassNotFoundException e){
            System.out.println("Необходимый для работы драйвер не был найден. \n\nЗавершение программы");
            System.out.println("|___|___|___|___|___|___|___|___|___|___|___|___|\n" +
                    "__|___|___|___|___|___|___|___|___|___|___|___|___\n" +
                    "|___|___|___|___|___|___|___|___|___|___|___|___|\n" +
                    "__|___|___|___|__бицца галавой сюда___|___|___|___\n" +
                    "|___|___|___|___|___|___|___|___|___|___|___|___|\n" +
                    "__|___|___|___|___|___|___|___|___|___|___|___|___\n" +
                    "|___|___|___|___|___|___|___|___|___|___|___|___|");
            System.exit(0);
        } catch (IOException e){
            System.out.println("Не найден файл, содержащий необходимые для поключения к базе данных логин и пароль. \n\nЗавершение программы");
            System.out.println("|___|___|___|___|___|___|___|___|___|___|___|___|\n" +
                    "__|___|___|___|___|___|___|___|___|___|___|___|___\n" +
                    "|___|___|___|___|___|___|___|___|___|___|___|___|\n" +
                    "__|___|___|___|__бицца галавой сюда___|___|___|___\n" +
                    "|___|___|___|___|___|___|___|___|___|___|___|___|\n" +
                    "__|___|___|___|___|___|___|___|___|___|___|___|___\n" +
                    "|___|___|___|___|___|___|___|___|___|___|___|___|");
            System.exit(0);
        }

        try(
                Connection connection1 = DriverManager.getConnection(ServerMain.URL, config); //подключаемся к бд
                Statement statement = connection1.createStatement(); //штука для взаимодействия с бд, создание запроса1
                Statement statement2 = connection1.createStatement(); //ещё одна штука для взаимодействия с бд, создание запроса2
        ){
            ResultSet resRoutes = statement.executeQuery("SELECT * from routes"); //заполненние запроса. возвращает результат. представляет из себя таблицу
            while (resRoutes.next()){ //перебор строк результата

                Long id = resRoutes.getLong("id_seq");
                String name = resRoutes.getString("route_name");
                String creatorLogin = resRoutes.getString("creator_name");
                Long idCoord = resRoutes.getLong("coordinates_id");
                java.time.LocalDate creationDate = resRoutes.getDate("creation_date").toLocalDate();
                Long idFrom = resRoutes.getLong("location_from_id");
                Long idTo = resRoutes.getLong("location_to_id");
                Float distance = resRoutes.getFloat("distance");

                ResultSet coord = statement2.executeQuery("SELECT * from coordinates where id_seq=" +idCoord);
                coord.next();
                Integer x = coord.getInt("x");
                Float y = coord.getFloat("y");
                Coordinates coordinates = new Coordinates(x,y);
                coord.close();

                ResultSet resFrom = statement2.executeQuery("SELECT * from location_from where id_seq=" +idFrom);
                resFrom.next();
                Location from = new Location(resRoutes.getLong("x"),
                        resRoutes.getDouble("y"),
                        resRoutes.getString("location_from_name"));
                resFrom.close();

                ResultSet resTo = statement2.executeQuery("SELECT * from location_to where id_seq=" +idTo);
                resTo.next();
                Location to = new Location(resRoutes.getLong("x"),
                        resRoutes.getDouble("y"),
                        resRoutes.getString("location_to_name"));
                resTo.close();

                Route nextRoute = new Route (id, name, coordinates, creationDate, from, to, distance, creatorLogin);
                ServerMain.c.Routes.add(nextRoute);
            }
            resRoutes.close();

            ResultSet resultUsers = statement.executeQuery("SELECT * from users");
            while(resultUsers.next()){
                String login = resultUsers.getString("login_id");
                String passwordForLog = resultUsers.getString("password");
                String totemAnimal =  resultUsers.getString ("totem_animal");
                User user = new User(login, passwordForLog, totemAnimal);
                UsersCollection.users.add(user);
            }

        }catch(SQLException e){
            System.out.println("Ошибка подключения к базе данных. \n\n Завершение программы");
            System.out.println("|___|___|___|___|___|___|___|___|___|___|___|___|\n" +
                    "__|___|___|___|___|___|___|___|___|___|___|___|___\n" +
                    "|___|___|___|___|___|___|___|___|___|___|___|___|\n" +
                    "__|___|___|___|__бицца галавой сюда___|___|___|___\n" +
                    "|___|___|___|___|___|___|___|___|___|___|___|___|\n" +
                    "__|___|___|___|___|___|___|___|___|___|___|___|___\n" +
                    "|___|___|___|___|___|___|___|___|___|___|___|___|");
            System.exit(0);
        }
    }

    public static void saving(){

        Properties config = new Properties();

        try{
            config.load(new FileInputStream("res/config.properties"));
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e){
            System.out.println("Необходимый для работы драйвер не был найден. \n\n Завершение программы");
            System.out.println("|___|___|___|___|___|___|___|___|___|___|___|___|\n" +
                    "__|___|___|___|___|___|___|___|___|___|___|___|___\n" +
                    "|___|___|___|___|___|___|___|___|___|___|___|___|\n" +
                    "__|___|___|___|__бицца галавой сюда___|___|___|___\n" +
                    "|___|___|___|___|___|___|___|___|___|___|___|___|\n" +
                    "__|___|___|___|___|___|___|___|___|___|___|___|___\n" +
                    "|___|___|___|___|___|___|___|___|___|___|___|___|");
            System.exit(0);
        }catch (IOException e){
            System.out.println("Не найден файл, содержащий необходимые для поключения к базе данных логин и пароль. \n\n Завершение программы");
            System.out.println("|___|___|___|___|___|___|___|___|___|___|___|___|\n" +
                    "__|___|___|___|___|___|___|___|___|___|___|___|___\n" +
                    "|___|___|___|___|___|___|___|___|___|___|___|___|\n" +
                    "__|___|___|___|__бицца галавой сюда___|___|___|___\n" +
                    "|___|___|___|___|___|___|___|___|___|___|___|___|\n" +
                    "__|___|___|___|___|___|___|___|___|___|___|___|___\n" +
                    "|___|___|___|___|___|___|___|___|___|___|___|___|");
            System.exit(0);
        }

        try(
                Connection connection1 = DriverManager.getConnection(ServerMain.URL, config);
                Statement statement = connection1.createStatement();
        ){
            statement.execute("delete from routes"); //очищение таблицы с коллекцией рутов и рестарт отсчета айди
            statement.execute("SELECT SETVAL((SELECT pg_get_serial_sequence('routes', 'id_seq')), 1, false)");

            statement.execute("delete from coordinates"); //очищение таблицы с коллекцией координат и рестарт отсчета айди
            statement.execute("SELECT SETVAL((SELECT pg_get_serial_sequence('coordinates', 'id_seq')), 1, false)");

            statement.execute("delete from location_to");//очищение таблицы с коллекцией локейшенов ту и рестарт отсчета айди
            statement.execute("SELECT SETVAL((SELECT pg_get_serial_sequence('location_to', 'id_seq')), 1, false)");

            statement.execute("delete from location_from");//очищение таблицы с коллекцией локейшенов фром и рестарт отсчета айди
            statement.execute("SELECT SETVAL((SELECT pg_get_serial_sequence('location_from', 'id_seq')), 1, false)");

            statement.execute("delete from users"); //очищение таблицы с коллекцией юзеров

            ServerMain.c.Routes.stream()
                    .forEach(x -> {
                        try {
                            statement.execute("insert into coordinates values ("
                                    + x.getCoordinates().getX() + ", "
                                    + x.getCoordinates().getY() + ", "
                                    + "(select nextval('coordinates_id_seq_seq')));"); //заполнение новой строки таблицы координат


                            statement.execute("insert into location_from values ("
                                    + x.getFrom().getX() + ", "
                                    + x.getFrom().getY() + ", '"
                                    + x.getFrom().getName() + "', "
                                    + "(select nextval('location_from_id_seq_seq')));");

                            statement.execute("insert into location_to values ("
                                    + x.getTo().getX() + ", "
                                    + x.getTo().getY() + ", '"
                                    + x.getTo().getName() + "', "
                                    + "(select nextval('location_to_id_seq_seq')));");

                            statement.execute("insert into routes values ("
                                    + "(select nextval('routes_id_seq_seq')), '"
                                    + x.getName() +"', '"
                                    + x.getCreatorLogin()+ "', "
                                    + "select id_seq from coordinates where x=" + x.getCoordinates().getX() + " and y=" + x.getCoordinates().getY() + "), "
                                    + "date(" + Date.valueOf(x.getCreationDate()) + "), "
                                    + "select id_seq from location_from where x=" + x.getFrom().getX() + " and y=" + x.getFrom().getY() + " and location_from_name='" + x.getFrom().getName() + "'), "
                                    + "select id_seq from location_to where x=" + x.getTo().getX() + " and y=" + x.getTo().getY() + " and location_to_name='" + x.getTo().getName() + "'), "
                                    + x.getDistance() + ");");

                        } catch (SQLException e) {
                            e.printStackTrace();
                        }

                    });

            UsersCollection.users.stream()
                    .filter(x -> !x.getPassword().equals(" "))
                    .forEach(x ->{
                        try {
                            statement.execute("insert into users values('"
                                    + x.getLogin() + "', '"
                                    + x.getPassword() + "', '"
                                    + x.getTotemAnimal() + "')");
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    });

        }catch(SQLException e){
            System.out.println("Ошибка подключения к базе данных. \n\n Завершение программы");
            System.out.println("|___|___|___|___|___|___|___|___|___|___|___|___|\n" +
                    "__|___|___|___|___|___|___|___|___|___|___|___|___\n" +
                    "|___|___|___|___|___|___|___|___|___|___|___|___|\n" +
                    "__|___|___|___|__бицца галавой сюда___|___|___|___\n" +
                    "|___|___|___|___|___|___|___|___|___|___|___|___|\n" +
                    "__|___|___|___|___|___|___|___|___|___|___|___|___\n" +
                    "|___|___|___|___|___|___|___|___|___|___|___|___|");
            System.exit(0);
        }
    }
}