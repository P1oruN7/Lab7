package sql;
import routes.*;
import utility.ServerMain;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

    public class Connector {

        public static void loading() throws ClassNotFoundException, SQLException {
            FileInputStream fis; //файл с настройками
            Properties property = new Properties(); // абстракция настроек
            String user = " ";
            String password = " ";
            String connection = " ";
            String driver = " ";

            try{
                fis = new FileInputStream("config.properties");
                property.load(fis); //загружаем в абстракцию настроек записи из файла
                user = property.getProperty("db.login"); // закрепляем логин в переменную
                password = property.getProperty("db.password"); //закрепляем пароль в переменную
                connection = property.getProperty("db.host"); //закрепляем хост в переменную
                driver = property.getProperty("db.driver"); //закрепляем драйвер в переменную
            } catch (IOException e){
                System.out.println("Файл не найден. Выход");
                System.exit(0);
            }
            Class.forName(driver);
            try(Connection connection1 = DriverManager.getConnection(connection,user,password); //подключаемся к бд
                Statement statement = connection1.createStatement(); //штука для взаимодействия с бд, создание запроса1
                Statement statement2 = connection1.createStatement(); //ещё одна штука для взаимодействия с бд, создание запроса2
            ) {
                ResultSet resRoutes = statement.executeQuery("SELECT * from routes"); //заполненние запроса. возвращает результат. представляет из себя таблицу
                while (resRoutes.next()){ //перебор строк результата
                    Integer key = resRoutes.getInt("key");

                    Long id = resRoutes.getLong("id");
                    String name = resRoutes.getString("name");
                    //ZonedDateTime creationDate = resRoutes.getDate("creation_date").toLocalDate().atStartOfDay(ZoneId.of("Europe/Moscow"));
                    java.time.LocalDate creationDate = resRoutes.getDate("creation_date").toLocalDate();
                    //Integer area = resRoutes.getInt("area");
                    //Long numberOfRooms = resRoutes.getLong("number_of_rooms");
                    //Boolean newOrNot = resRoutes.getBoolean("new");
                    Float distance = resRoutes.getFloat("distance");
                    Long creatorID = resRoutes.getLong("creator_ID");

                    // понятия не имею что это такое ниже написано но оно написано
                    Long idCoord = resRoutes.getLong("id_coordinates");
                    //Long id_furnish = resRoutes.getLong("id_furnish");
                    //Long id_transport = resRoutes.getLong("id_transport");
                    //Long id_house = resRoutes.getLong("id_house");
                    Long idTo = resRoutes.getLong("id_to");
                    Long idFrom = resRoutes.getLong("id_from");

                    ResultSet coord = statement2.executeQuery("SELECT * from coordinates where id_coordinates="+idCoord);
                    coord.next();
                    Integer x = coord.getInt("x");
                    Float y = coord.getFloat("y");
                    Coordinates coordinates = new Coordinates(x,y);
                    coord.close();

                    //ResultSet resFurnish = statement2.executeQuery("SELECT * from furnish where id_furnish="+id_furnish);
                    //resFurnish.next();
                    //Furnish furnish = Furnish.getByName(resFurnish.getString("furnish_value"));
                    //resFurnish.close();

                    //ResultSet resTransport = statement2.executeQuery("select * from transport where id_transport="+id_transport);
                    //resTransport.next();
                    //Transport transport = Transport.getByName(resTransport.getString("transport_value"));
                    //resTransport.close();

                    //ResultSet resHouse = statement2.executeQuery("select * from house where id_house="+id_house);
                    //resHouse.next();
                    //House house = new House(resHouse.getString("house_name"),
                           // resHouse.getLong("year"),
                           // resHouse.getLong("number_of_floors"));
                    //resHouse.close();

                    ResultSet resTo = statement2.executeQuery("SELECT * from to where id_to="+idTo);
                    resTo.next();
                    Location To = Location.getByName(resFurnish.getString("furnish_value"));
                    resTo.close();

                    Route nextRoute = new Route (id, name, coordinates, creationDate, from, to, distance, creatorID);
                    ServerMain.routes.put(key,nextRoute); //тут у к*****а какие-то мапы зачем они там боже если бы мы знали но мы не знаем
                }
                resRoutes.close();

                //че тут ниже два блока тоже не очень понятно но надо значит надо
                ResultSet resultLogPass = statement.executeQuery("SELECT * from log_pas");
                while(resultLogPass.next()){
                    String login = resultLogPass.getString("login");
                    String passwordForLog = resultLogPass.getString("password");
                    ServerMain.loginPass.put(login,passwordForLog); //и тут
                }
                ResultSet resultRealtion = statement.executeQuery("select * from relation");
                while (resultRealtion.next()){
                    String login = resultRealtion.getString("login");
                    Integer key = resultRealtion.getInt("key");
                    ServerMain.relation.put(key,login); //и тут
                }

            }
        }

        public static void saving() throws ClassNotFoundException, SQLException {
            FileInputStream fis;
            Properties property = new Properties();
            String user = " ";
            String password = " ";
            String connection = " ";
            String driver = " ";
            try{
                fis = new FileInputStream("config.properties");
                property.load(fis);
                user = property.getProperty("db.login");
                password = property.getProperty("db.password");
                connection = property.getProperty("db.host");
                driver = property.getProperty("db.driver");
            } catch (IOException e){
                System.out.println("Нет файла. Выход");
                System.exit(0);
            }
            Class.forName(driver);
            try(Connection connection1 = DriverManager.getConnection(connection,user,password);
                Statement statement = connection1.createStatement(); Statement statement2 = connection1.createStatement();){
                statement.execute("delete from relation");
                statement.execute("delete from log_pas");
                statement.execute("delete from flats");
                statement.execute("alter sequence flats_id_seq restart with 1");
                statement.execute("delete from coordinates");
                statement.execute("alter sequence coordinates_id_coordinates_seq restart with 1;");
                statement.execute("delete from house");
                statement.execute("alter sequence house_id_house_seq restart with 1;");



//                MainServer.flats.entrySet()
//                        .stream()
//                        .forEach(x -> {
//                            Integer key = x.getKey();
//                            Long id = x.getValue().getId();
//                            String name = x.getValue().getName();
//                            ZonedDateTime creationDate = x.getValue().getCreationDate();
//                            Integer area = x.getValue().getArea();
//                            Long numberOfRooms = x.getValue().getNumberOfRooms();
//                            Boolean newOrNot = x.getValue().getNewOrNot();
//                            Float x_coord = x.getValue().getCoordinates().getX();
//                            Float y_coord = x.getValue().getCoordinates().getY();
//                            String furnish = x.getValue().getFurnish().toString();
//                            String transport = x.getValue().getTransport().toString();
//                            String house_name = x.getValue().getHouse().getName();
//                            Long year = x.getValue().getHouse().getYear();
//                            Long number_of_floors = x.getValue().getHouse().getNumberOfFloors();
//                            try {
//                                ResultSet numH = statement2.executeQuery("select count(*) as row_count from house where house_name = '" + house_name + "' and house.year =" + year +" and number_of_floors ="+ number_of_floors);
//                                numH.next();
//                                Integer countH = numH.getInt("row_count");
//                                ResultSet numC = statement2.executeQuery("select count(*) as row_count from coordinates where x="+ x_coord +" and y="+y_coord);
//                                numH.close();
//                                numC.next();
//                                Integer countC = numC.getInt("row_count");
//                                numC.close();
//                                if(countC ==0){
//                                    statement.execute("insert into coordinates values ((select nextval('coordinates_id_coordinates_seq')), "
//                                            + x_coord + ", "
//                                            + y_coord + ");");
//                                }
//                                if(countH == 0){
//                                    statement.execute("insert into house values ((select nextval('house_id_house_seq')), '"
//                                            + house_name + "', "
//                                            + year + ", "
//                                            + number_of_floors +" );");
//                                }
//                                statement.execute("insert into flats values("
//                                        +key+", "
//                                        +"(select nextval('flats_id_seq')), '"
//                                        +name+"', "
//                                        + "(select id_coordinates from coordinates where x="+ x_coord +" and y="+y_coord+"),"
//                                        + "date('" + creationDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy - hh:mm")) + "'),"
//                                        + area + ", "
//                                        + numberOfRooms + ", "
//                                        + newOrNot + ", "
//                                        + "(select id_furnish from furnish where furnish_value='"+furnish+"'), "
//                                        + "(select id_transport from transport where transport_value='"+transport+"'), "
//                                        + "(select id_house from house where house_name = '" + house_name + "' and house.year =" + year +" and number_of_floors ="+ number_of_floors+")"
//                                        +");");
//                            } catch (SQLException e) {
//                                e.printStackTrace();
//                            }
//
//                        });
//                MainServer.loginPass
//                        .entrySet()
//                        .stream()
//                        .filter(x -> x.getValue()!=" ")
//                        .forEach(x ->{
//                            try {
//                                statement.execute("insert into log_pas values('"+ x.getKey()+"', '"+x.getValue()+"')");
//                            } catch (SQLException e) {
//                                e.printStackTrace();
//                            }
//                        });
//                MainServer.relation
//                        .entrySet()
//                        .stream()
//                        .forEach(x -> {
//                            try {
//                                statement.execute("insert into relation values('"+x.getValue()+"', "+x.getKey()+")");
//                            } catch (SQLException e) {
//                                e.printStackTrace();
//                            }
//                        });
            }
        }
    }
