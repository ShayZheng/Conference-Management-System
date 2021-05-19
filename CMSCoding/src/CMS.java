/**
 * @author Yuzhe Wang
 * @version 30 Apr 2021
 */
import java.io.*;
import java.util.*;

public class CMS {
    private ConferenceManagement CM;
    private Menu menu;

    public CMS() throws Exception{
        CM = new ConferenceManagement();
        menu = new Menu();
        CM.readFromFile();
        openSystem();
    }

    public void openSystem(){
        String option;
        option = "";
        while (!option.equals("4"))
        {
            menu.displayMainMenu();

            Scanner scan = new Scanner(System.in);
            option = scan.nextLine();
            System.out.print("\n");
            // Check whether the input option is valid.
            if (!isStringNumeric(option))
                System.out.println("You need to choose a number between 1 to 4.");
            else
            {
                int checkOption = Integer.parseInt(option); // convert the String into an integer. I got this method from https://blog.csdn.net/a772304419/article/details/79723249.
                if (checkOption < 1 || checkOption > 4)
                    System.out.println("You need to choose a number between 1 to 4.");
            }

            switch (option)
            {
                case "1": administratorLogin(); break;
                case "2": register(); break;
                case "3": login(); break;
            }
        }

    }

    public void administratorLogin(){
        String adminUsername;
        String adminPsw;
        String userName;
        String psw;

        adminUsername = "admin";
        adminPsw = "admin";

        Scanner scan = new Scanner(System.in);
        System.out.print("Please enter the administrator's username: ");
        userName = scan.nextLine();
        System.out.print("\n");
        while(!userName.equals(adminUsername)){
            System.out.print("Your administrator name is incorrect, please enter again: ");
            userName = scan.nextLine();
        }

        System.out.print("Please enter the administrator's pasword: ");
        psw = scan.nextLine();
        System.out.print("\n");
        while(!psw.equals(adminPsw)){
            System.out.print("Your administrator password is incorrect, please enter again: ");
            psw = scan.nextLine();
        }

        String option;
        option = "";
        while (!option.equals("4"))
        {
            menu.displayMainMenu();

            option = scan.nextLine();
            System.out.print("\n");
            // Check whether the input option is valid.
            if (!isStringNumeric(option))
                System.out.println("You need to choose a number between 1 to 4.");
            else
            {
                int checkOption = Integer.parseInt(option); // convert the String into an integer. I got this method from https://blog.csdn.net/a772304419/article/details/79723249.
                if (checkOption < 1 || checkOption > 3)
                    System.out.println("You need to choose a number between 1 to 4.");
            }

            switch (option)
            {
                case "1": break;
                case "2": break;
                case "3": break;
            }
        }



    }

    public void login() {
        String name;
        String psw;

        Scanner scan = new Scanner(System.in);
        System.out.println("\n Welcome to the Conference Management System");
        System.out.println("======================================");
        System.out.print("Enter the username: ");
        name = scan.nextLine();
        for (User thisUser : CM.getUserList() ) {
            if (thisUser.getName().equals(name)) {
                System.out.print("Enter password: ");
                psw = scan.nextLine();
                int count = 0;
                while(count < 3 && !psw.equals(thisUser.getPsw())) {
                    System.out.print("Your password is incorrect, please enter again: ");
                    psw = scan.nextLine();
                    count += 1;
                }
                break;
            }
            else {
                System.out.println("This user is not exist, please register first.");
                register();
            }
        }
        String option;
        option = "";
        while (!option.equals("4"))
        {
            menu.displayTypeMenu();

            option = scan.nextLine();
            System.out.print("\n");
            // Check whether the input option is valid.
            if (!isStringNumeric(option))
                System.out.println("You need to choose a number between 1 to 4.");
            else
            {
                int checkOption = Integer.parseInt(option); // convert the String into an integer. I got this method from https://blog.csdn.net/a772304419/article/details/79723249.
                if (checkOption < 1 || checkOption > 3)
                    System.out.println("You need to choose a number between 1 to 4.");
            }

            switch (option)
            {
                case "1": break;
                case "2": break;
                case "3": break;
            }
        }




    }
    public void register() {
        String name;
        String psw1;
        String psw2;
        int ID;
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
        while (name.trim().equals("") || !isStringAlphabetic(name) || CM.findUser(name) != -1) // check whether the name is allowed.
        {
            System.out.println("The user's neme cannot be null and should only be alphabetic.");
            System.out.print("Enter the user's name: ");
            name = scan.nextLine();
        }

        email = setUserInfo("email");
        occupation = setUserInfo("occupation");
        psw1 = setUserInfo("password");

        System.out.print("Enter the password again: ");
        psw2 = scan.nextLine();
        while (!psw2.equals(psw1)) {
            System.out.println("The password should be the same with the first time.");
            System.out.print("Enter the password again: ");
            psw2 = scan.nextLine();
        }

        mobileNumber = setUserInfo("mobileNumber");
        highQualification = setUserInfo("high qualification");
        employerDetail = setUserInfo("employer detail");
        interestArea = setUserInfo("interesting area");

        CM.addUser(new User(ID, name, psw2, 0, email, occupation, mobileNumber, highQualification, employerDetail, interestArea));
        login();
    }


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

    public boolean isStringNumeric(String checkedString)
    {
        int i;
        for (i = 0; i < checkedString.length(); i ++)
        {
            if (!Character.isDigit(checkedString.charAt(i)))
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


        public static void main(String[] args) throws Exception{
            new CMS();
        }
    }
