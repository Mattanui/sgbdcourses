import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class ExerciceEcole {
    static void main() throws Exception {
        Properties props = new Properties();

        // chargement du db.properties
        try (InputStream input = ExerciceEcole.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (input == null) {
                System.out.println("Désolé, impossible de trouver le fichier db.properties");
                return;
            }
            props.load(input);
        } catch (Exception e) {
            System.out.println("Erreur lors de la lecture du fichier db.properties");
            return;
        }

        final String url = props.getProperty("db.url");
        final String user = props.getProperty("db.user");
        final String password = props.getProperty("db.password");

        Connection connexion = null;
        Statement statement = null;

        try {
            // ==== connexion intiale pour créer la DB 'ecole'
            connexion = DriverManager.getConnection(url, user, password);
            statement = connexion.createStatement();

            try {
                statement.executeUpdate("CREATE DATABASE ecole");
                System.out.println("Base de données 'ecole' créée avec succès !");
            } catch (SQLException e) {
                System.out.println("La base de données existe déjà.");
            }

            // fermeture de la connexion actuelle
            statement.close();
            connexion.close();

            // === reconnexion sur la nouvelle base 'ecole'
            String urlEcole = url.replace("/postgres", "/ecole");
            connexion = DriverManager.getConnection(urlEcole, user, password);
            statement = connexion.createStatement();
            System.out.println("Connecté à la base de données : ecole");

            // ==== création des tables
            // Table Section (créée en premier car cours dépend d'elle)
            String createSection = "CREATE TABLE IF NOT EXISTS Section (" +
                    "id SERIAL PRIMARY KEY, " +
                    "nom VARCHAR(30))";
            statement.executeUpdate(createSection);

            // Table Cours
            String createCours = "CREATE TABLE IF NOT EXISTS Cours (" +
                    "id SERIAL PRIMARY KEY, " +
                    "nom VARCHAR(30), " +
                    "id_section INT, " +
                    "FOREIGN KEY (id_section) REFERENCES Section(id))";
            statement.executeUpdate(createCours);
            System.out.println("Table 'Section' et 'Cours' créées.");

            // === Insertion des données
            // flush avant de réupload des données pour pas insérer les mêmes données au lancement du programme
            statement.executeUpdate("TRUNCATE TABLE Cours, Section RESTART IDENTITY");

            // insertion dans Section
            String insertSection = "INSERT INTO Section (nom) VALUES " +
                    "('Informatique de gestion'), " +
                    "('Droit')";
            statement.executeUpdate(insertSection);

            //insertion dans Cours
            String insertCours = "INSERT INTO Cours (nom, id_section) VALUES " +
                    "('Base des réseaux', 1), " +
                    "('Systèmes d''exploitation', 1), " +
                    "('Programmation orienté objet', 1), " +
                    "('Droit civil', 2), " +
                    "('Droit commercial', 2)";
            statement.executeUpdate(insertCours);

            System.out.println("Données insérées avec succès !");
        } catch (Exception e) {
            System.out.println("Erreur SQL : " + e.getMessage());
        } finally {
            if (statement != null) statement.close();
            if (connexion != null) connexion.close();

        }
    }
}
