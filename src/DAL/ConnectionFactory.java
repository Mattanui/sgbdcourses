package DAL;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class ConnectionFactory {

    public static Connection getConnection() {
        try {
            Properties props = new Properties();
            try (InputStream input = ConnectionFactory.class.getClassLoader().getResourceAsStream("db.properties")) {
                if (input == null) {
                    System.out.println("Fichier db.properties introuvable.");
                    return null;
                }
                props.load(input);
            }

            String url = props.getProperty("db.url");
            String user = props.getProperty("db.user");
            String password = props.getProperty("db.password");

            return DriverManager.getConnection(url, user, password);

        } catch (Exception e) {
            System.out.println("Erreur dans la Factory : " + e.getMessage());
            return null;
        }
    }
}