package DAL.cours;

import BL.cours.Cours;
import DAL.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CoursDAOPrepared implements ICoursDAO {
    private Connection connexion;
    private PreparedStatement insertCours, updateCours, deleteCours, getIDCours, getCours;

    public CoursDAOPrepared() {
        try {
            this.connexion = ConnectionFactory.getConnection();

            this.insertCours = this.connexion.prepareStatement("INSERT INTO Cours (id_section, nom) VALUES (?, ?)");
            this.updateCours = this.connexion.prepareStatement("UPDATE Cours SET id_section=?, nom=? WHERE id=?");
            this.deleteCours = this.connexion.prepareStatement("DELETE FROM Cours WHERE id=?");
            this.getIDCours = this.connexion.prepareStatement("SELECT id FROM Cours WHERE nom=?");
            this.getCours = this.connexion.prepareStatement("SELECT id, id_section, nom FROM Cours");
        } catch (SQLException e) {
            System.out.println("Erreur d'initialisation DAO Cours : " + e.getMessage());
        }
    }

    @Override
    public ArrayList<Cours> getCours() {
        ArrayList<Cours> liste = new ArrayList<>();
        try {
            ResultSet set = this.getCours.executeQuery();
            while (set.next()) {
                // récup de id, id_section, nom
                liste.add(new Cours(set.getInt(1), set.getInt(2), set.getString(3)));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return liste;
    }

    @Override
    public int getIDCours(String nom) {
        int id = -1;
        try {
            this.getIDCours.setString(1, nom);
            ResultSet set = this.getIDCours.executeQuery();
            if (set.next()) {
                id = set.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return id;
    }

    @Override
    public boolean updateCours(int id, int id_section, String nom) {
        try {
            this.updateCours.setInt(1, id_section);
            this.updateCours.setString(2, nom);
            this.updateCours.setInt(3, id);
            this.updateCours.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean deleteCours(int id) {
        try {
            this.deleteCours.setInt(1, id);
            this.deleteCours.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean addCours(int id_section, String nom) {
        try {
            this.insertCours.setInt(1, id_section);
            this.insertCours.setString(2, nom);
            this.insertCours.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public void close() {
        try {
            if (this.insertCours != null) this.insertCours.close();
            if (this.updateCours != null) this.updateCours.close();
            if (this.deleteCours != null) this.deleteCours.close();
            if (this.getIDCours != null) this.getIDCours.close();
            if (this.getCours != null) this.getCours.close();
            if (this.connexion != null) this.connexion.close();
        } catch (SQLException e) {
            System.out.println("Erreur fermeture CoursDAO : " + e.getMessage());
        }
    }
}
