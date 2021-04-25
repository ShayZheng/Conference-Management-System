public class Adminstrator {
    private String Type;
    private String Psw;

    public Adminstrator(String newType, String newPsw){
        Type = newType;
        Psw = newPsw;
    }

    public String getType(){
        return Type;
    }

    public String getPsw(){
        return Psw;
    }

    public void setType(String newType){
        Type = newType;
    }

    public void setPsw(String newPsw){
        Psw = newPsw;
    }
}
