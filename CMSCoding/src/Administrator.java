package src;

/**
 * @author Yuzhe Wang
 * @version 26 Oct 2020
 */
public class Administrator {
    private String adminType;
    private String adminPsw;

    public Administrator(String newType, String newPsw){
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
