package src;

/**
 * @author Yuzhe Wang
 * @version 26 Oct 2020
 */
public class Administrator {
    private String adminUsername;
    private String adminPsw;

    public Administrator(String newUsername, String newPsw){
        adminPsw = newPsw;
        adminUsername = newUsername;
    }

    public String getAdminUsername(){
        return adminUsername;
    }

    public  String getAdminPsw(){

        return adminPsw;
    }

    public void setAdminUsername(String newUsername){

        adminUsername = newUsername;
    }

    public void setAdminPsw(String newPsw){

        adminPsw = newPsw;
    }
}
