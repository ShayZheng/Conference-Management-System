public class Adminstrator {
    private String adminType;
    private String adminPsw;

    public Adminstrator(String newType, String newPsw){
        adminPsw = newPsw;
        adminType = newType;
    }

    public String getAdminType(){
        return adminType;
    }

    public  String getAdminPsw(){

        return adminPsw;
    }

    public void setAdminType(String newType){

        adminType = newType;
    }

    public void setAdminPsw(String newPsw){

        adminPsw = newPsw;
    }
}
