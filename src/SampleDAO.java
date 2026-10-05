import BL.section.Section;
import DAL.section.ISectionDAO;
import DAL.section.SectionDAO;
import DAL.section.SectionDAOPrepared;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;

public class SampleDAO {
    public static void main(String[] args) throws Exception {

        Properties props = new Properties();
        try (InputStream input = SampleDAO.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (input == null) {
                System.out.println("Fichier db.properties introuvable.");
                return;
            }
            props.load(input);
        }

        String url = props.getProperty("db.url");
        String user = props.getProperty("db.user");
        String password = props.getProperty("db.password");

        String urlCible = url.replace("/postgres", "/ecole");

        ISectionDAO sectionDAO = new SectionDAOPrepared();

        sectionDAO.addSection("Comptabilite");
        sectionDAO.addSection("Erreur");
        sectionDAO.addSection("A Changer");

        ArrayList<Section> sections = sectionDAO.getSections();
        for (Section section : sections) {
            if (section.getNom().equals("Erreur")) {
                sectionDAO.deleteSection(section.getId());
            } else if (section.getNom().equals("A Changer")) {
                sectionDAO.updateSection(section.getId(), "Changement");
            }
        }

        sectionDAO.close();
    }
}
