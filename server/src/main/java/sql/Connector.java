package sql;

import readers.PropertiesReader;
import routes.Coordinates;
import routes.Location;
import routes.Route;
import users.User;
import users.UsersCollection;
import utility.ServerMain;

import java.sql.*;
import java.time.format.DateTimeFormatter;
import java.util.Collections;

public class Connector {

    public synchronized static void loading(){

        try {
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
        }

        try(
                Connection connection1 = DriverManager.getConnection(ServerMain.URL, PropertiesReader.readResource("config.properties")); //подключаемся к бд
                Statement statement = connection1.createStatement(); //штука для взаимодействия с бд, создание запроса1
                Statement statement2 = connection1.createStatement(); //ещё одна штука для взаимодействия с бд, создание запроса2
        ){
            ResultSet resRoutes = statement.executeQuery("SELECT * from routes;"); //заполненние запроса. возвращает результат. представляет из себя таблицу

            while (resRoutes.next()){ //перебор строк результата

                Route nextRoute = new Route ();
                nextRoute.setId(resRoutes.getLong("id_seq"));
                nextRoute.setName(resRoutes.getString("route_name"));
                nextRoute.setCreatorLogin(resRoutes.getString("creator_name"));
                long idCoord = resRoutes.getLong("coordinates_id");
                {
                    ResultSet coord = statement2.executeQuery("SELECT * from coordinates where id_seq=" + idCoord + ";");
                    coord.next();
                    Coordinates coordinates = new Coordinates(
                            coord.getInt("x"),
                            coord.getFloat("y")
                    );
                    coord.close();
                    nextRoute.setCoordinates(coordinates);
                }
                nextRoute.setCreationDate(resRoutes.getDate("creation_date").toLocalDate());
                long idFrom = resRoutes.getLong("location_from_id");
                {
                    if (idFrom!=0) {
                    ResultSet resFrom = statement2.executeQuery("SELECT * from location_from where id_seq=" + idFrom + ";");
                    resFrom.next();
                    Location from = new Location(
                            resFrom.getLong("x"),
                            resFrom.getDouble("y"),
                            resFrom.getString("location_from_name")
                    );
                    resFrom.close();
                    nextRoute.setFrom(from);

                    }
                }
                long idTo = resRoutes.getLong("location_to_id");
                {
                    ResultSet resTo = statement2.executeQuery("SELECT * from location_to where id_seq=" +idTo+";");
                    resTo.next();
                    Location to = new Location(resTo.getLong("x"),
                            resTo.getDouble("y"),
                            resTo.getString("location_to_name"));
                    resTo.close();
                    nextRoute.setTo(to);
                }
                if(resRoutes.getFloat("distance")!= 0){
                    nextRoute.setDistance(resRoutes.getFloat("distance"));
                }

                ServerMain.c.Routes.add(nextRoute);
            }
            resRoutes.close();

            ResultSet resultUsers = statement.executeQuery("SELECT * from users;");
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

    public synchronized static void saving(){

        Collections.sort(ServerMain.c.Routes);

        try{
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
        }

        try(
            Connection connection1 = DriverManager.getConnection(ServerMain.URL, PropertiesReader.readResource("config.properties"));
            Statement savingStatement = connection1.createStatement();
            Statement checkingStatement = connection1.createStatement();
        ){
            savingStatement.execute("delete from routes;"); //очищение таблицы с коллекцией рутов и рестарт отсчета айди
            savingStatement.execute("SELECT SETVAL('routes_id_seq_seq', 1, false);");

            savingStatement.execute("delete from coordinates;"); //очищение таблицы с коллекцией координат и рестарт отсчета айди
            savingStatement.execute("SELECT SETVAL('coordinates_id_seq_seq', 1, false);");

            savingStatement.execute("delete from location_from;");//очищение таблицы с коллекцией локейшенов ту и рестарт отсчета айди
            savingStatement.execute("SELECT SETVAL('location_from_id_seq_seq', 1, false);");

            savingStatement.execute("delete from location_to;");//очищение таблицы с коллекцией локейшенов фром и рестарт отсчета айди
            savingStatement.execute("SELECT SETVAL('location_to_id_seq_seq', 1, false);");

            savingStatement.execute("delete from users;"); //очищение таблицы с коллекцией юзеров

            UsersCollection.users.stream()
                    .filter(x -> !x.getPassword().equals(" "))
                    .forEach(x ->{
                        try {
                            savingStatement.execute("insert into users values('"
                                    + x.getLogin() + "', '"
                                    + x.getPassword() + "', '"
                                    + x.getTotemAnimal() + "');");
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    });

            ServerMain.c.Routes.stream()
                    .forEach(x -> {
                        try {
                            ResultSet coordinatesRS = checkingStatement.executeQuery("select count(*) as row_count from coordinates where x = " + x.getCoordinates().getX() + " and y = " + x.getCoordinates().getY() + ";");
                            coordinatesRS.next();
                            int coordinatesNum = coordinatesRS.getInt("row_count");
                            coordinatesRS.close();

                            int locationFromNum;
                            if (x.getFrom() != null) {
                                ResultSet locationFromRS = checkingStatement.executeQuery("select count(*) as row_count from location_from where location_from_name = '" + x.getFrom().getName() + "' and x =" + x.getFrom().getX() + " and y =" + x.getFrom().getY() + ";");
                                locationFromRS.next();
                                locationFromNum = locationFromRS.getInt("row_count");
                                locationFromRS.close();
                            } else {
                                locationFromNum = 1;
                            }

                            ResultSet locationToRS = checkingStatement.executeQuery("select count(*) as row_count from location_to where location_to_name = '" + x.getTo().getName() + "' and x =" + x.getTo().getX() +" and y ="+ x.getTo().getY() + ";");
                            locationToRS.next();
                            int locationToNum = locationToRS.getInt("row_count");
                            locationToRS.close();

                            if(coordinatesNum == 0){
                                savingStatement.execute("insert into coordinates values ("
                                        + x.getCoordinates().getX() + ", "
                                        + x.getCoordinates().getY() + ", "
                                        + "(select nextval('coordinates_id_seq_seq')));"); //заполнение новой строки таблицы координат
                            }

                            if(locationFromNum == 0){
                                savingStatement.execute("insert into location_from values ("
                                        + x.getFrom().getX() + ", "
                                        + x.getFrom().getY() + ", '"
                                        + x.getFrom().getName() + "', "
                                        + "(select nextval('location_from_id_seq_seq')));");
                            }

                            if(locationToNum == 0){
                                savingStatement.execute("insert into location_to values ("
                                        + x.getTo().getX() + ", "
                                        + x.getTo().getY() + ", '"
                                        + x.getTo().getName() + "', "
                                        + "(select nextval('location_to_id_seq_seq')));");
                            }

                            if(x.getDistance() != null && x.getFrom()!=null) {
                                savingStatement.execute("insert into routes values ("
                                        + "(select nextval('routes_id_seq_seq')), '"
                                        + x.getName() + "', '"
                                        + x.getCreatorLogin() + "', "
                                        + "(select id_seq from coordinates where x=" + x.getCoordinates().getX() + " and y=" + x.getCoordinates().getY() + "), "
                                        + "date('" + x.getCreationDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + "'), "
                                        + "(select id_seq from location_from where x=" + x.getFrom().getX() + " and y=" + x.getFrom().getY()
                                        + " and location_from_name='" + x.getFrom().getName() + "'), "
                                        + "(select id_seq from location_to where x=" + x.getTo().getX() + " and y=" + x.getTo().getY()
                                        + " and location_to_name='" + x.getTo().getName() + "'), "
                                        + x.getDistance() + ");");
                            }else if (x.getDistance() == null && x.getFrom() != null){
                                savingStatement.execute("insert into routes values ("
                                        + "(select nextval('routes_id_seq_seq')), '"
                                        + x.getName() + "', '"
                                        + x.getCreatorLogin() + "', "
                                        + "(select id_seq from coordinates where x=" + x.getCoordinates().getX() + " and y=" + x.getCoordinates().getY() + "), "
                                        + "date('" + x.getCreationDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + "'), "
                                        + "(select id_seq from location_from where x=" + x.getFrom().getX() + " and y=" + x.getFrom().getY()
                                        + " and location_from_name='" + x.getFrom().getName() + "'), "
                                        + "(select id_seq from location_to where x=" + x.getTo().getX() + " and y=" + x.getTo().getY()
                                        + " and location_to_name='" + x.getTo().getName() + "'), null);");
                            }else if (x.getDistance() != null && x.getFrom() == null){
                                savingStatement.execute("insert into routes values ("
                                        + "(select nextval('routes_id_seq_seq')), '"
                                        + x.getName() + "', '"
                                        + x.getCreatorLogin() + "', "
                                        + "(select id_seq from coordinates where x=" + x.getCoordinates().getX() + " and y=" + x.getCoordinates().getY() + "), "
                                        + "date('" + x.getCreationDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + "'), "
                                        + "null, "
                                        + "(select id_seq from location_to where x=" + x.getTo().getX() + " and y=" + x.getTo().getY()
                                        + " and location_to_name='" + x.getTo().getName() + "'), "+ x.getDistance()+");");
                            }else {
                                savingStatement.execute("insert into routes values ("
                                        + "(select nextval('routes_id_seq_seq')), '"
                                        + x.getName() + "', '"
                                        + x.getCreatorLogin() + "', "
                                        + "(select id_seq from coordinates where x=" + x.getCoordinates().getX() + " and y=" + x.getCoordinates().getY() + "), "
                                        + "date('" + x.getCreationDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + "'), "
                                        + "null, "
                                        + "(select id_seq from location_to where x=" + x.getTo().getX() + " and y=" + x.getTo().getY()
                                        + " and location_to_name='" + x.getTo().getName() + "'), null);");
                            }
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
