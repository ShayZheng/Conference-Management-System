// @ author:Yuzhe Wang
// @ date: 25/04/2021
//

import java.util.ArrayList;

public class User {
    private int ID;
    private String Type;
    private String Name;
    private String Psw;

    //changing part, default constructor
    public User(){
        ID = 56;
        Type = "Reviewer";
        Name = "Nono";
        Psw = "123456";
    }

    public User(int newID, String newName, String newPsw, String newType){
        ID = newID;
        Type = newType;
        Name = newName;
        Psw = newPsw;
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
        return ID + "," + Name + "," + Psw +","+ Type;
    }

}
