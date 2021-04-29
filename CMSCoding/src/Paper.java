// @ author:Ying Zheng
// @ date: 25/04/2021
//

import java.util.ArrayList;
import java.util.Iterator;

public class Paper {

    private String Name;
    private String smDeadline;
    private String rmDeadline;
    private String Status;
    private String Author;
    private String Keyword;
    private String Decision;
    private String conName;
    private ArrayList<Reviewer> assignedReviewers;


    public Paper(String name, String smDeadline, String rmDeadline, String status, String author, String keyword, String decision, String conName) {
        Name = name;
        this.smDeadline = smDeadline;
        this.rmDeadline = rmDeadline;
        Status = status;
        Author = author;
        Keyword = keyword;
        Decision = decision;
        this.conName = conName;
        assignedReviewers = new ArrayList<>();//change part
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getSmDeadline() {
        return smDeadline;
    }

    public void setSmDeadline(String smDeadline) {
        this.smDeadline = smDeadline;
    }

    public String getRmDeadline() {
        return rmDeadline;
    }

    public void setRmDeadline(String rmDeadline) {
        this.rmDeadline = rmDeadline;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public String getKeyword() {
        return Keyword;
    }

    public void setKeyword(String keyword) {
        Keyword = keyword;
    }

    public String getDecision() {
        return Decision;
    }

    public void setDecision(String decision) {
        Decision = decision;
    }

    public String getConName() {
        return conName;
    }

    public void setConName(String conName) {
        this.conName = conName;
    }


    public ArrayList<Reviewer> getAssignedReviewerList()
    {
       return assignedReviewers;
    }//change part

    public String getReviewer()//change part
    {
        ArrayList<String> reviewerName = new ArrayList<>();

        for(Reviewer one: assignedReviewers)
            reviewerName.add(one.getName());

        Iterator<String> reviewer = reviewerName.iterator();
        String reviewers = "";
        while(reviewer.hasNext())
        {
            reviewers +=","+ reviewer.next();
        }

        return reviewers;
    }




    @Override
    public String toString() {
        return "Paper{" +
                "Name='" + Name + '\'' +
                ", smDeadline='" + smDeadline + '\'' +
                ", rmDeadline='" + rmDeadline + '\'' +
                ", Status='" + Status + '\'' +
                ", Author='" + Author + '\'' +
                ", Keyword='" + Keyword + '\'' +
                ", Decision='" + Decision + '\'' +
                ", conName='" + conName + '\'' +
                ", Assigned Reviewers='" + getReviewer() +'\''+//change part
                '}';
    }

    public String toStringDatabase()//change part
    {
        return Name + "," +smDeadline +","+rmDeadline+","+Status+","+Author+","+Keyword+","+Decision+","+conName+""+getReviewer();
    }

}
