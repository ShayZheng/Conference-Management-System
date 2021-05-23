/**
 * @author Ying Zheng
 * @version 20 May 2021
 */

import java.util.ArrayList;
import java.util.Iterator;

public class Paper {

    private String Name;
    private String smDeadline;
    private String rmDeadline;
    private String Status;
    private String Author;
    private String Decision;
    private String conName;
    private String filePath;
    private ArrayList<User> assignedReviewers;
    private ArrayList<String> Evaluation;
    private ArrayList<String> Keywords;

    public Paper() {
    }

    public Paper(String name, String smDeadline, String rmDeadline, String status, String author, String decision, String conName,String file) {
        this.Name = name;
        this.smDeadline = smDeadline;
        this.rmDeadline = rmDeadline;
        this.Status = status;
        this.Author = author;
        this.Decision = decision;
        this.conName = conName;
        this.filePath = file;
        this.assignedReviewers = new ArrayList();
        this.Keywords = new ArrayList();
        this.Evaluation = new ArrayList();
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFilePath() {
        return filePath;
    }

    public String getName() {
        return this.Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public String getSmDeadline() {
        return this.smDeadline;
    }

    public void setSmDeadline(String smDeadline) {
        this.smDeadline = smDeadline;
    }

    public String getRmDeadline() {
        return this.rmDeadline;
    }

    public void setRmDeadline(String rmDeadline) {
        this.rmDeadline = rmDeadline;
    }

    public String getStatus() {
        return this.Status;
    }

    public void setStatus(String status) {
        this.Status = status;
    }

    public String getAuthor() {
        return this.Author;
    }

    public void setAuthor(String author) {
        this.Author = author;
    }

    public ArrayList<String> getEvaluation() {
        return this.Evaluation;
    }

    public void setEvaluation(ArrayList<String> evaluation) {
        this.Evaluation = evaluation;
    }

    public ArrayList<String> getKeywords() {
        return this.Keywords;
    }

    public void setKeywords(ArrayList<String> keywords) {
        this.Keywords = keywords;
    }

    public String getEachKeyword() {
        String eachKeyword = "null";
        Iterator var2 = this.getKeywords().iterator();
        if (var2.hasNext()) {
            String Keyword = (String) var2.next();
            return "," + Keyword;
        } else {
            return eachKeyword;
        }
    }

    public String getDecision() {
        return this.Decision;
    }

    public void setDecision(String decision) {
        this.Decision = decision;
    }

    public String getConName() {
        return this.conName;
    }

    public void setConName(String conName) {
        this.conName = conName;
    }

    public ArrayList<User> getAssignedReviewerList() {
        return this.assignedReviewers;
    }

    public String getReviewerNames(ArrayList<User> u) {
        String names = "";
        if(u.size() > 0)
        {
            for(int i = 1 ; i < u.size();i++)
            {
                names += u.get(i).getName()+",";
            }

        }



        return names;
    }

    public String getStringListNames(ArrayList<String> as)
    {
        String names = "";
        if(as.size() > 0 )
        {
            for(int i = 1 ; i < as.size();i++)
            {
                if(as.get(i) != null)
                    names += as.get(i)+",";
            }

        }

        return names;
    }


}
