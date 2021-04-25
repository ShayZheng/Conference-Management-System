public class USER {
    private int ID;
    private String Type;
    private String Name;
    private String Psw;

    public USER(int newID, String newName, String newPsw, String newType){
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

    public static void main(String[] args) {
        USER a = new USER(12, "wyz", "666", "reviewer");
        a.printUserInfo();
    }
}
