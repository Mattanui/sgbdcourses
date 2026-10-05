import BL.cours.Cours;
import BL.section.Section;
import BL.status.Status;
import DAL.cours.CoursDAOPrepared;
import DAL.section.SectionDAOPrepared;
import DAL.status.StatusDAOPrepared;

public class MainApp {

    public static void main(String[] args) {

        System.out.println("=== INITIALISATION DES DAO ===");
        StatusDAOPrepared statusDAO = new StatusDAOPrepared();
        SectionDAOPrepared sectionDAO = new SectionDAOPrepared();
        CoursDAOPrepared coursDAO = new CoursDAOPrepared();

        System.out.println("=== INSERTION DES DONNÉES ===");

        statusDAO.addStatus("Etudiant");
        statusDAO.addStatus("Charge de cours");
        statusDAO.addStatus("Employe administratif");

        sectionDAO.addSection("Informatique");
        int idSectionInfo = sectionDAO.getIdSection("Informatique");

        coursDAO.addCours(idSectionInfo, "Systemes d'exploitation");
        coursDAO.addCours(idSectionInfo, "Base des reseaux");

        System.out.println("\n=== AFFICHAGE DES TABLES (Point 2 de l'exercice) ===");

        System.out.println("\n--- TABLE STATUS ---");
        for (Status s : statusDAO.getStatuses()) {
            System.out.println("ID: " + s.getId() + " | Nom: " + s.getNom());
        }

        System.out.println("\n--- TABLE SECTION ---");
        for (Section s : sectionDAO.getSections()) {
            System.out.println("ID: " + s.getId() + " | Nom: " + s.getNom());
        }

        System.out.println("\n--- TABLE COURS ---");
        for (Cours c : coursDAO.getCours()) {
            System.out.println("ID: " + c.getId() + " | ID_Section: " + c.getIdSection() + " | Nom: " + c.getNom());
        }

        statusDAO.close();
        sectionDAO.close();
        coursDAO.close();

        System.out.println("\nProgramme terminé et connexions fermées avec succès !");
    }
}