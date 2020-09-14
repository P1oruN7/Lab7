package readers;

import java.io.*;
import java.util.Properties;

/**
 * Класс для чтения из файла
 */

public class PropertiesReader extends Reader {

    public static Properties readResource(String name){

        Properties config = new Properties();
        InputStream is = PropertiesReader.class.getClassLoader().getResourceAsStream(name);
        try {
            config.load(is);
        } catch (IOException e) {
            System.out.println("Не найден файл с необходимыми для подключения к базе данных логином и паролем. \n\nЗавершение программы");
            System.out.println("|___|___|___|___|___|___|___|___|___|___|___|___|\n" +
                    "__|___|___|___|___|___|___|___|___|___|___|___|___\n" +
                    "|___|___|___|___|___|___|___|___|___|___|___|___|\n" +
                    "__|___|___|___|__бицца галавой сюда___|___|___|___\n" +
                    "|___|___|___|___|___|___|___|___|___|___|___|___|\n" +
                    "__|___|___|___|___|___|___|___|___|___|___|___|___\n" +
                    "|___|___|___|___|___|___|___|___|___|___|___|___|");
            System.exit(0);
        }
        return config;

    }

    @Override
    public String getLine() {
        try {
            String s = bufferedReader.readLine();
            if (s != null) {
                System.out.print(s + "\n");
            }
            return s;
        } catch (IOException e) {
            System.out.println("Ввод неожиданно прервался");
            return null;
        }
    }

}