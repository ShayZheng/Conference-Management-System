// @ date: 25/04/2021
//

import java.util.*;

public class User {
    private int ID;
    private String Name;
    private String Psw;
    private int chooseType;
    private String Email;
    private String Occupation;
    private String mobileNumber;
    private String highQualification;
    private String employerDetail;
    private String interestArea;
    private ArrayList<String> keywords;
    private ArrayList<Conference> conferenceListForChair;
    private ArrayList<Conference> conferenceListForAuthor;
    private ArrayList<Conference> conferenceListForReviewer;
    private ArrayList<Paper> assignedPaper;
    private ArrayList<Paper> submittedPaper;
    private ArrayList<String> messageBox;



    public User(int newID, String newName, String newPsw,int newType, String newEmail, String newOccupation, String newMN, String newHQ, String newED, String newIA)
    {
        ID = newID;
        Name = newName;
        Psw = newPsw;
        chooseType = newType;//user can choose different types
        Email = newEmail;
        Occupation = newOccupation;
        mobileNumber = newMN;
        highQualification = newHQ;
        employerDetail = newED;
        interestArea = newIA;//some basic features for user
        keywords = new ArrayList<>();
        conferenceListForChair = new ArrayList<>();
        conferenceListForAuthor= new ArrayList<>();
        conferenceListForReviewer = new ArrayList<>();
        assignedPaper = new ArrayList<>();
        submittedPaper = new ArrayList<>();
        messageBox = new ArrayList<>();
    }


    public int getID(){
        return ID;
    }

    public String getName(){
        return Name;
    }

    public String getPsw(){
        return Psw;
    }

    public String getEmail(){ return Email; }

    public String getOccupation(){ return Occupation; }

    public String getMobileNumber(){ return mobileNumber; }

    public String getHighQualification(){ return highQualification; }

    public String getEmployerDetail(){ return employerDetail; }

    public String getInterestArea(){ return interestArea; }


    public int getChooseType() { return chooseType; }

    public ArrayList<String> getKeywords() { return keywords; }

    public ArrayList<Conference> getConferenceListForAuthor() { return conferenceListForAuthor; }

    public ArrayList<Conference> getConferenceListForChair() { return conferenceListForChair; }

    public ArrayList<Conference> getConferenceListForReviewer() { return conferenceListForReviewer; }

    public void setEmail(String newEmail){ Email = newEmail; }

    public void setOccupation(String newOccupation){ Occupation = newOccupation; }

    public void setMobileNumber(String newNuber){ mobileNumber = newNuber; }

    public void setHighQualification(String newHighQualification){ highQualification = newHighQualification; }

    public void setEmployerDetail(String newEmployerDetail){ employerDetail = newEmployerDetail; }

    public void setInterestArea(String newInterestArea){ interestArea = newInterestArea; }

    public void setID(int newID){ ID = newID; }

    public void setName(String newName){ Name = newName; }

    public void setPsw(String newPsw){ Psw = newPsw; }

    public void setChooseType(int chooseType) { this.chooseType = chooseType; }

    public void setConferenceListForChair(ArrayList<Conference> conferenceListForChair) {
        this.conferenceListForChair = conferenceListForChair;
    }

    public void setConferenceListForReviewer(ArrayList<Conference> conferenceListForReviewer) {
        this.conferenceListForReviewer = conferenceListForReviewer;
    }

    public void setKeywords(ArrayList<String> keywords) {
        this.keywords = keywords;
    }


    public void setConferenceListForAuthor(ArrayList<Conference> conferenceListForAuthor) {
        this.conferenceListForAuthor = conferenceListForAuthor;
    }

    public void setAssignedPaper(ArrayList<Paper> assignedPaper) {
        this.assignedPaper = assignedPaper;
    }
    public ArrayList<Paper> getSubmittedPaper(){return submittedPaper;}

    public ArrayList<Paper> getAssignedPaper() { return assignedPaper;}

    public String getStringListNames(ArrayList<String> as)
    {
        String names = "";
        if(as.size() == 0)
            names="";
        if(as.size() == 1)
            names=as.get(0);
        if(as.size() > 1 )
        {
            if(as.get(0)==null)
                names ="";
            if(as.get(0)!=null)
                names+=as.get(0)+",";
            for(int i = 1 ; i < as.size() -1 ;i++)
            {
                if(as.get(i) != null)
                    names += as.get(i)+",";
            }
            names += as.get(as.size()-1);
        }

        return names;
    }

    public String getConferenceNames(ArrayList<Conference> con)
    {
        String names = "";
        if(con.size() == 0 )
            names="";
        if(con.size() == 1)
        {
            if(con.get(0) != null)
                names = con.get(0).getConName();
            if(con.get(0) == null)
                names = "";
        }
        if(con.size() > 1)
        {
            for(int i =0 ; i < con.size() -1 ;i++)
            {
                if(con.get(i) != null)
                    names += con.get(i).getConName()+",";
            }
            names += con.get(con.size()-1).getConName();
        }

        return names;
    }



    public String getPaperNames(ArrayList<Paper> papers)
    {
        String names = "";
        if(papers.size() == 0 )
            names="";
        if(papers.size() == 1)
        {
            if(papers.get(0) != null)
                names = papers.get(0).getName();
            if(papers.get(0) == null)
                names = "";
        }
        if(papers.size() > 1)
        {
            for(int i =0 ; i < papers.size() -1 ;i++)
            {
                if(papers.get(i) != null)
                    names += papers.get(i).getName()+",";
            }
            names += papers.get(papers.size()-1).getName();
        }

        return names;
    }


    public ArrayList<String> getMessageBox() {
        return messageBox;
    }

    public void setMessageBox(ArrayList<String> messageBox) {
        this.messageBox = messageBox;
    }

}
