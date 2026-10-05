package DAL.section;

import BL.section.Section;

import java.util.ArrayList;

public interface ISectionDAO {
    public ArrayList<Section> getSections();
    public int getIdSection(String nom);
    public boolean updateSection(int id, String nom);
    public boolean deleteSection(int id);
    public boolean addSection(String nom);
    public void close();
}
