package DAL.section;

import BL.section.Section;
import DAL.ConnectionFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class SectionDAOPrepared implements ISectionDAO {

    Connection connexion;

    PreparedStatement insertSection;
    PreparedStatement updateSection;
    PreparedStatement deleteSection;
    PreparedStatement getIDSection;
    PreparedStatement getSections;

    public SectionDAOPrepared() {
        try {
            this.connexion = ConnectionFactory.getConnection();

            try (Statement createStmt = this.connexion.createStatement()) {
                createStmt.executeUpdate("CREATE TABLE IF NOT EXISTS Section (id SERIAL PRIMARY KEY, nom VARCHAR(30))");
            } catch (SQLException e) {
                System.out.println("Info: " + e.getMessage());
            }

            this.insertSection = this.connexion.prepareStatement("INSERT into Section (nom) VALUES (?)");
            this.updateSection = this.connexion.prepareStatement("UPDATE Section SET nom=? WHERE id=?");
            this.deleteSection = this.connexion.prepareStatement("DELETE FROM Section WHERE id=?");
            this.getIDSection = this.connexion.prepareStatement("SELECT id FROM Section WHERE nom=?");
            this.getSections = this.connexion.prepareStatement("SELECT id,nom FROM Section");

        } catch (SQLException e) {
            System.out.println("Erreur d'initialisation : " + e.getMessage());
        }
    }

    @Override
    public ArrayList<Section> getSections() {
        ArrayList<Section> listeSection = new ArrayList<>();
        try {
            ResultSet set = this.getSections.executeQuery();
            while (set.next()) {
                listeSection.add(new Section(set.getInt(1), set.getString(2)));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return listeSection;
    }

    @Override
    public int getIdSection(String nom) {
        int id = -1;
        try {
            this.getIDSection.setString(1, nom);
            ResultSet set = this.getIDSection.executeQuery();
            if (set.next()) {
                id = set.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return id;
    }

    @Override
    public boolean updateSection(int id, String nom) {
        try {
            this.updateSection.setString(1, nom);
            this.updateSection.setInt(2, id);
            this.updateSection.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean deleteSection(int id) {
        try {
            this.deleteSection.setInt(1, id);
            this.deleteSection.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean addSection(String nom) {
        try {
            this.insertSection.setString(1, nom);
            this.insertSection.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public void close() {
        try {
            if (this.insertSection != null) this.insertSection.close();
            if (this.updateSection != null) this.updateSection.close();
            if (this.deleteSection != null) this.deleteSection.close();
            if (this.getIDSection != null) this.getIDSection.close();
            if (this.getSections != null) this.getSections.close();
            if (this.connexion != null) this.connexion.close();
        } catch (SQLException e) {
            System.out.println("Erreur lors de la fermeture : " + e.getMessage());
        }
    }
}