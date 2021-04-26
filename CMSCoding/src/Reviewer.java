// @ author:Ying Zheng
// @ date: 25/04/2021
//

import java.util.ArrayList;

public class Reviewer extends User{


    //field
    private String Keyword;
    private ArrayList<Paper> papers;


    public String getKeyword() {
        return Keyword;
    }

    public void setKeyword(String keyword) {
        Keyword = keyword;
    }

    public Reviewer(int newID, String newName, String newPsw, String newType, String newKeyword) {
        super(newID, newName, newPsw, newType); // inherit from User class
        Keyword = newKeyword;
        papers = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Reviewer{" + super.toString() +
                "Keyword='" + Keyword + '\'' +
                "} " ;
    }
    //
//        public String toString() {
//        return "ID: " + newID + "Name: " + newName + "Type: " + newType + "Keyword: " + newKeyword;
//    }
}
