package DAL.status;

import BL.status.Status;
import DAL.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class StatusDAOPrepared implements IStatusDAO {
    private Connection connexion;
    private PreparedStatement insertStatus, updateStatus, deleteStatus, getIDStatus, getStatuses;

    public StatusDAOPrepared() {
        try {
            this.connexion = ConnectionFactory.getConnection();

            this.insertStatus = this.connexion.prepareStatement("INSERT INTO Status (nom) VALUES (?)");
            this.updateStatus = this.connexion.prepareStatement("UPDATE Status SET nom=? WHERE id=?");
            this.deleteStatus = this.connexion.prepareStatement("DELETE FROM Status WHERE id=?");
            this.getIDStatus = this.connexion.prepareStatement("SELECT id FROM Status WHERE nom=?");
            this.getStatuses = this.connexion.prepareStatement("SELECT id, nom FROM Status");
        } catch (SQLException e) {
            System.out.println("Erreur d'initialisation DAO Status : " + e.getMessage());
        }
    }

    @Override
    public ArrayList<Status> getStatuses() {
        ArrayList<Status> liste = new ArrayList<>();
        try {
            ResultSet set = this.getStatuses.executeQuery();
            while (set.next()) {
                liste.add(new Status(set.getInt(1), set.getString(2)));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return liste;
    }

    @Override
    public int getIDStatus(String nom) {
        int id = -1;
        try {
            this.getIDStatus.setString(1, nom);
            ResultSet set = this.getIDStatus.executeQuery();
            if (set.next()) {
                id = set.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return id;
    }

    @Override
    public boolean updateStatus(int id, String nom) {
        try {
            this.updateStatus.setString(1, nom);
            this.updateStatus.setInt(2, id);
            this.updateStatus.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean deleteStatus(int id) {
        try {
            this.deleteStatus.setInt(1, id);
            this.deleteStatus.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean addStatus(String nom) {
        try {
            this.insertStatus.setString(1, nom);
            this.insertStatus.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public void close() {
        try {
            if (this.insertStatus != null) this.insertStatus.close();
            if (this.updateStatus != null) this.updateStatus.close();
            if (this.deleteStatus != null) this.deleteStatus.close();
            if (this.getIDStatus != null) this.getIDStatus.close();
            if (this.getStatuses != null) this.getStatuses.close();
            if (this.connexion != null) this.connexion.close();
        } catch (SQLException e) {
            System.out.println("Erreur fermeture StatusDAO : " + e.getMessage());
        }
    }
}
