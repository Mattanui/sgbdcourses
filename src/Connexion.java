import java.sql.*;
import java.io.InputStream;
import java.util.Properties;

public class Connexion {

    public static void main(String[] args) throws Exception {

        Properties props = new Properties();

        // Chargement du fichier db.properties
        try (InputStream input = Connexion.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (input == null) {
                System.out.println("Désolé, impossible de trouver le fichier db.properties");
                return; // On arrête l'exécution si le fichier n'est pas trouvé
            }
            props.load(input);
        } catch (Exception e) {
            System.out.println("Erreur lors de la lecture du fichier properties: " + e.getMessage());
            return;
        }

        // Récupération des informations depuis le fichier
        final String url = props.getProperty("db.url");
        final String user = props.getProperty("db.user");
        final String password = props.getProperty("db.password");

        // Connexion à la base de données.
        Connection connexion = null;
        Statement statement = null;
        ResultSet resultSet =null;
        try {
            connexion = DriverManager.getConnection(url, user, password);
            System.out.println("Connecté à la base de donnée: " + url);
            statement = connexion.createStatement();
            try {
                String requeteSQL = "CREATE DATABASE test1";
                statement.executeUpdate(requeteSQL);
            } catch (SQLException e) {
                System.out.println("Base de données test1 déjà existante.\n");
            }

            //ferme la connexion existante pour se connecter sur la DB test1.
            statement.close();
            connexion.close();
            connexion = DriverManager.getConnection(url.replace("/postgres", "/test1"), user, password);
            statement = connexion.createStatement();
            String requeteSQLTable = "CREATE TABLE IF NOT EXISTS status (id SERIAL PRIMARY KEY, status VARCHAR(20))";
            statement.executeUpdate(requeteSQLTable);
            String requeteSQLValeur = "INSERT INTO status (status) VALUES ('Etudiant'),('Chargé de cours'),('Administratif')";
            statement.executeUpdate(requeteSQLValeur);
            String requeteSQLValeurDansTableTest1 = "SELECT id, status FROM status";
            resultSet = statement.executeQuery(requeteSQLValeurDansTableTest1);
            while (resultSet.next()){
                // attention, les indices commencent à 1 et non 0.
                System.out.println(resultSet.getInt(1) + " " + resultSet.getString(2));
            }

        } catch (SQLException e) {
            System.out.println("Erreur de connexion: " + e.getMessage());
        } finally {
            // Toujours fermer les requetes
            if (resultSet != null){
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            // Toujours fermer la connexion à la DB
            if (connexion != null) {
                connexion.close();
            }
        }
    }
}
