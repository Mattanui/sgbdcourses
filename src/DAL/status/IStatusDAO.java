package DAL.status;

import BL.status.Status;

import java.util.ArrayList;

public interface IStatusDAO {
    ArrayList<Status> getStatuses();
    int getIDStatus(String nom);
    boolean updateStatus(int id, String nom);
    boolean deleteStatus(int id);
    boolean addStatus(String nom);
    void close();
}