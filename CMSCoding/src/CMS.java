/**
 * @author Yuzhe Wang
 * @version 30 Apr 2021
 */
import java.io.*;
import java.util.*;

public class CMS {
    private ConferenceManagement CM;

    public CMS(){
        CM = new ConferenceManagement();
    }

    public void register()
    {
        String name;
        String psw1;
        String psw2;
        int ID;
        int chooseType = 0;
        String email;
        String occupation;
        String mobileNumber;
        String highQualification;
        String employerDetail;
        String interestArea;

        ID = CM.getUserList().size() + 1;
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter the user's name: ");
        name = scan.nextLine();
        while (name.trim().equals("") || !isStringAlphabetic(name)) // check whether the name is allowed.
        {
            System.out.println("The user's name cannot be null and should only be alphabetic.");
            System.out.print("Enter the user's name: ");
            name = scan.nextLine();
        }

        email = "email";//validations
        occupation ="occupation";
        psw1 = "password";

        System.out.print("Enter the password again: ");
        psw2 = scan.nextLine();
        while (!psw2.equals(psw1)) {
            System.out.println("The password should be the same with the first time.");
            System.out.print("Enter the password again: ");
            psw2 = scan.nextLine();
        }

        mobileNumber = "mobileNumber";
        highQualification = "high qualification";
        employerDetail = "employer detail";
        interestArea = "interesting area";
        User newUser = new User(ID,name,psw2,chooseType,email,occupation,mobileNumber,highQualification,employerDetail,interestArea);
        CM.getUserList().add(newUser);
        System.out.println("Please choose one keyword from the list or add your new keyword(if you want to add more keywords, please use comma to split it.)");
        System.out.println("0.Add keywords from list");
        System.out.println("1.Add new keywords");
        String option = scan.nextLine();
        switch (option){
            case"0":
                System.out.println("Here is the keywords list");
                for(String k:CM.getKeywordList())
                {
                    if(!k.equals(""))
                         System.out.println(CM.getKeywordList().indexOf(k)+"."+ k);
                }
                String choose = scan.nextLine();//validations
                newUser.getKeywords().add(CM.getKeywordList().get(Integer.parseInt(choose)));
                System.out.println("Add successfully");
                break;
            case"1":
                System.out.println("Please input the keywords, if you want to put several keywords, please use comma to split");
                String[] newKeys = scan.nextLine().split(",");
                for(int k = 0;k <newKeys.length;k++)
                {
                    CM.getKeywordList().add(newKeys[k]);
                    newUser.getKeywords().add(newKeys[k]);
                }
                if(newKeys.length <= 1)
                    System.out.println("You have not add anything!");
                if(newKeys.length > 1)
                    System.out.println("Add successfully");
                break;
            default:
                System.out.println("Please input the correct number");
                break;

        }





    }

    /**
     * To check whether the borrower's name is alphabetic.
     */
    public boolean isStringAlphabetic(String checkedString)
    {
        int i;
        for (i = 0; i < checkedString.length(); i ++)
        {
            char character = checkedString.charAt(i);
            // To restrict the string can only contain alpha and space. Avoid special symbol like , + =.
            if ((character >= 'a' && character <= 'z') || (character >= 'A' && character <= 'Z') || character == ' '){}
            else
                return false;
        }
        return true;
    }

    public String setUserInfo(String info){
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter the " + info + " : ");
        String inFo = scan.nextLine();
        while (inFo.trim().equals("")) // check whether the name is allowed.
        {
            System.out.println("The " + info + " cannot be null.");
            System.out.print("Enter the " + info + ": ");
            inFo = scan.nextLine();
        }
        return inFo;
    }


    public static void main(String[] args) throws Exception
    {
        CMS cms = new CMS();
        cms.CM.readFromFile();
        cms.register();
        for(User u: cms.CM.getUserList())
        {
            System.out.println(u.toString());
        }
        for(String s: cms.CM.getKeywordList())
        {
            if(!s.equals(""))
                System.out.println(s);
        }

    }
}
