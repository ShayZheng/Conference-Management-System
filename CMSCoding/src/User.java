// @ author:Yuzhe Wang
// @ date: 25/04/2021
//

import java.util.ArrayList;

public class User {
    private int ID;
    private String Type;
    private String Name;
    private String Psw;
    private String Email;
    private String Occupation;
    private String mobileNumber;
    private String highQualification;
    private String employerDetail;
    private String interestArea;


    public User(int newID, String newName, String newPsw, String newEmail, String newOccupation, String newMN, String newHQ, String newED, String newIA){
        ID = newID;
        Name = newName;
        Psw = newPsw;
        Email = newEmail;
        Occupation = newOccupation;
        mobileNumber = newMN;
        highQualification = newHQ;
        employerDetail = newED;
        interestArea = newIA;
    }

    public int getID(){
        return ID;
    }

    public String getType(){
        return Type;
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

    public void setEmail(String newEmail){ Email = newEmail; }

    public void setOccupation(String newOccupation){ Occupation = newOccupation; }

    public void setMobileNumber(String newNuber){ mobileNumber = newNuber; }

    public void setHighQualification(String newHighQualification){ highQualification = newHighQualification; }

    public void setEmployerDetail(String newEmployerDetail){ employerDetail = newEmployerDetail; }

    public void setInterestArea(String newInterestArea){ interestArea = newInterestArea; }

    public void setID(int newID){
        ID = newID;
    }

    public void setType(String newType){
        Type = newType;
    }

    public void setName(String newName){
        Name = newName;
    }

    public void setPsw(String newPsw){
        Psw = newPsw;
    }



    public void printUserInfo(){
        System.out.println(ID);
        System.out.println(Name);
        System.out.println(Type);
    }

    @Override
    public String toString() {
        return "ID=" + ID + ", Type='" + Type + '\'' + ", Name='" + Name + '\'' + ", Psw='" + Psw + '\'';
    }

    public String toStringData()
    {
        return ID + "," + Name + "," + Type +","+ Psw;
    }

}
