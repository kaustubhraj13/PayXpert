////package util;
////
////import java.io.FileInputStream;
////import java.io.IOException;
////import java.util.Properties;
////
////public class DBPropertyUtil {
////    public static String getConnectionString(String propertyFileName) {
////        Properties properties = new Properties();
////        try (FileInputStream fis = new FileInputStream(propertyFileName)) {
////            properties.load(fis);
////            return properties.getProperty("db.connection.string");
////        } catch (IOException e) {
////            e.printStackTrace();
////            return null; // Handle the exception appropriately based on your needs
////        }
////    }
////}
//package util;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.Properties;
//
//public class DBPropertyUtil {
//
//    public static String getConnectionString(String fileName) {
//        Properties properties = new Properties();
//
//        // Use try-with-resources to automatically close the input stream
//        try (InputStream input = DBPropertyUtil.class.getClassLoader().getResourceAsStream(fileName)) {
//            // Check if the resource is found
//            if (input != null) {
//                // Load properties from input stream
//                properties.load(input);
//            } else {
//                // Handle the case when the resource is not found
//                System.err.println("Error: Resource not found - " + fileName);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        // Retrieve the value for the "connectionString" key
//        return properties.getProperty("connectionString");
//    }

//}
//
package util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DBPropertyUtil {
    public static String getConnectionString(String string) {
        Properties properties = new Properties();
        try (InputStream input = DBPropertyUtil.class.getClassLoader().getResourceAsStream("database.properties")) {
            if (input == null) {
                System.out.println("Sorry, unable to find database.properties");
                return null; // You may handle this case differently based on your application requirements
            }

            properties.load(input);

            String url = properties.getProperty("db.url");
            String user = properties.getProperty("db.user");
            String password = properties.getProperty("db.password");

            return "jdbc:mysql://" + url + "?user=" + user + "&password=" + password;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
