// @ date: 25/04/2021
//

import java.util.ArrayList;

public class Paper {

    private String Name;
    private String smDeadline;
    private String rmDeadline;
    private String Status;
    private String Author;
    private String Decision;
    private String conName;
    private ArrayList<User> assignedReviewers;
    private ArrayList<String> Evaluation;
    private ArrayList<String> Keywords;

    public Paper() {
    }

    public Paper(String name, String smDeadline, String rmDeadline, String status, String author, String decision, String conName) {
        Name = name;
        this.smDeadline = smDeadline;
        this.rmDeadline = rmDeadline;
        Status = status;
        Author = author;

        Decision = decision;
        this.conName = conName;
        assignedReviewers = new ArrayList<>();//change part
        Keywords = new ArrayList<>();//change part
        Evaluation = new ArrayList<>();//change part

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

    public ArrayList<String> getEvaluation() {
        return Evaluation;
    }

    public void setEvaluation(ArrayList<String> evaluation) {
        Evaluation = evaluation;
    }

    public ArrayList<String> getKeywords() {
        return Keywords;
    }

    public void setKeywords(ArrayList<String> keywords) {
        Keywords = keywords;
    }

    //change part get each keyword in Keyword List
    public String getEachKeyword()
    {
        String eachKeyword = "null";
        for (String Keyword:getKeywords()){
            eachKeyword = Keyword;
            return "," + eachKeyword;
        }
        return  eachKeyword;
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


    public ArrayList<User> getAssignedReviewerList()
    {
       return assignedReviewers;
    }


    public String getReviewerNames(ArrayList<User> u)
    {
        String names = "";
        if(u.size()==1)
            if(u.get(0)!=null)
                names = u.get(0).getName();
        if(u.size()>1)
        {
            for(int i = 0 ; i < u.size(); i++ )
            {
                if(u.get(i) != null)
                    names += u.get(i).getName()+",";
            }

        }


        return names;
    }

    public String getStringListNames(ArrayList<String> as)
    {
        String names = "";
        if(as.size()==1)
            if(as.get(0)!=null)
                names = as.get(0);
        if(as.size()>1)
        {
            for(int i = 0 ; i < as.size()-1; i++ )
            {
                if(as.get(i) != null)
                    names += as.get(i)+",";
            }
            names += as.get(as.size()-1);
        }


        return names;
    }




   /* public String toString() {
        return "Paper{" +
                "Name='" + Name + '\'' +
                ", smDeadline='" + smDeadline + '\'' +
                ", rmDeadline='" + rmDeadline + '\'' +
                ", Status='" + Status + '\'' +
                ", Author='" + Author + '\'' +
                ", Keyword='" + Keyword + '\'' +
                ", Decision='" + Decision + '\'' +
                ", conName='" + conName + '\'' +
                ", Assigned Reviewers='" + getReviewerNames(assignedReviewers) +'\''+
                '}';
    }*/

    /*public String toStringDatabase()//change part
    {
        String str = Name + "," +smDeadline +","+rmDeadline+","+Status+","+Author+","+Keyword+","+Decision+","+conName + getReviewerNames(assignedReviewers);
        return str;
    }*/


}
