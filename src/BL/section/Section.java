package BL.section;

public class Section {
    private final int id;
    private String nom;


    public Section(int id, String nom) {
        this.id = id;
        this.nom = nom;
    }


    public int getId() {
        return this.id;
    }


    public String getNom() {
        return this.nom;
    }


    public void setNom(String nom) {
        this.nom = nom;
    }
}
