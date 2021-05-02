// @ author:Ying Zheng
// @ date: 25/04/2021
//

import java.util.ArrayList;

public class Reviewer extends User{


    //field
    private String Keyword;
    private ArrayList<Paper> papers;
    private ArrayList<String> reviewerMassage;//change part




    public ArrayList<Paper> getAssignedPaper()//change part
    {
        return papers;
    }
    public ArrayList<String> getReviewerMassage()//change part
    {
        return reviewerMassage;
    }
    public String getPaperNames()
    {
        String paperNames="";
        for(Paper one: papers)
             paperNames += ","+ one.getName();
        return paperNames;
    }


    public String getKeyword() {
        return Keyword;
    }

    public void setKeyword(String keyword) {
        Keyword = keyword;
    }

    //change part
    public Reviewer(int newID, String newName, String newPsw, String newType, String newKeyword) {
        super(newID, newName, newPsw, newType); // inherit from User class
        Keyword = newKeyword;
        papers = new ArrayList<>();
        reviewerMassage = new ArrayList<>();

    }

    @Override
    public String toString() {
        return "Reviewer{" + super.toString() +
                "Keyword='" + Keyword + '\'' +
                "} " ;
    }
    public String toStringDatabase(){
        return super.toStringData() + "," + getKeyword()  + getPaperNames();
    }
    //
//        public String toString() {
//        return "ID: " + newID + "Name: " + newName + "Type: " + newType + "Keyword: " + newKeyword;
//    }
}
