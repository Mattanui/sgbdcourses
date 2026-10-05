package BL.cours;

public class Cours {
    private final int id;
    private int id_section; // La fk vers Section
    private String nom;

    public Cours(int id, int id_section, String nom) {
        this.id = id;
        this.id_section = id_section;
        this.nom = nom;
    }

    public int getId() { return id; }
    public int getIdSection() { return id_section; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public void setIdSection(int id_section) { this.id_section = id_section; }
}