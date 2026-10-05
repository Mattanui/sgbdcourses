import DAL.ConnectionFactory;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class SetupDB {

    public static void main(String[] args) {
        try (Connection conn = ConnectionFactory.getConnection();
             Statement stmt = conn.createStatement()) {

            if (conn != null) {
                System.out.println("Connexion Factory OK. Création des tables");

                // tables indép. en premier
                stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Section (id SERIAL PRIMARY KEY, nom VARCHAR(20))");
                stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Status (id SERIAL PRIMARY KEY, nom VARCHAR(20))");
                stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Local (id SERIAL PRIMARY KEY, numero VARCHAR(5), lieu VARCHAR(10), type VARCHAR(3))");

                // tables avec 1FK
                stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Cours (id SERIAL PRIMARY KEY, id_section INT REFERENCES Section(id), nom VARCHAR(30))");
                stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Personne (id SERIAL PRIMARY KEY, id_status INT REFERENCES Status(id), nom VARCHAR(15), prenom VARCHAR(15))");

                // tables avec plusieurs liaisons
                stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Cours_Personne (" +
                        "id_personne INT REFERENCES Personne(id), " +
                        "id_cours INT REFERENCES Cours(id), " +
                        "annee_academique DATE, " +
                        "PRIMARY KEY (id_personne, id_cours))");

                stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Seance (" +
                        "id SERIAL PRIMARY KEY, " +
                        "id_cours INT REFERENCES Cours(id), " +
                        "id_local INT REFERENCES Local(id), " +
                        "date TIMESTAMP)");

                stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Seance_Personne (" +
                        "id_personne INT REFERENCES Personne(id), " +
                        "id_seance INT REFERENCES Seance(id), " +
                        "PRIMARY KEY (id_personne, id_seance))");

                System.out.println("MPD complet créé avec succès !");
            }
        } catch (SQLException e) {
            System.out.println("Erreur SQL lors du Setup : " + e.getMessage());
        }
    }
}