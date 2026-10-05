package DAL.cours;

import BL.cours.Cours;

import java.util.ArrayList;

public interface ICoursDAO {
    ArrayList<Cours> getCours();
    int getIDCours(String nom);
    // id_section pour l'update et le add
    boolean updateCours(int id, int id_section, String nom);
    boolean deleteCours(int id);
    boolean addCours(int id_section, String nom);
    void close();
}