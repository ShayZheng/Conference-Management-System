/**
 * @author Yuzhe Wang
 * @version 30 Apr 2021
 */
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class CMS
{
    private ConferenceManagement CM;
    private Menu menu;

    public CMS() throws Exception{
        CM = new ConferenceManagement();
        menu = new Menu();
        CM.readFromFile();
        openSystem();
    }



    public void openSystem() throws Exception {
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
                case "4": System.exit(0);  //exit system
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

    public void login() throws Exception {
        String email;
        String psw;
        int label = 0;

        Scanner scan = new Scanner(System.in);
        System.out.println("\n Welcome to the Conference Management System");
        System.out.println("======================================");
        System.out.print("Enter the email: ");
        email = scan.nextLine();
        for (User thisUser : CM.getUserList() ) {
            if (thisUser.getEmail().equals(email)) {
                label = 1;
                System.out.print("Enter password: ");
                psw = scan.nextLine();
                int count = 1;
                while(count < 3 && !psw.equals(thisUser.getPsw())) {
                    System.out.print("Your password is incorrect, please enter again: ");
                    psw = scan.nextLine();
                    count += 1;
                }
                if (psw.equals(thisUser.getPsw())){
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
                            case "1":
                                //0519 chair functions
                                break;
                            case "2":
                                //0519 changing part
                                reviewerFunctions(email);
                                break;
                            case "3":
                                //0519 author functions
                                break;
                        }
                    }
                    break;
                }
                else{
                    System.out.println("Login failed!");
                    System.out.println("System will return to main menu.");
                    break;
                }
            }
        }

        if (label == 0)
            System.out.println("This user is not exist, please register first.");




    }
    public void register() throws Exception {
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
        while (name.trim().equals("") || !isStringAlphabetic(name)) // check whether the name is allowed.
        {
            System.out.println("The user's neme cannot be null and should only be alphabetic.");
            System.out.print("Enter the user's name: ");
            name = scan.nextLine();
        }

        System.out.print("Enter the user's email: ");
        email = scan.nextLine();
        // regex syntax
        // " \w"：equals to '[A-Za-z0-9_]',could be alphabets,numbers and _
        // "|"  : means "or"
        // "*" : 0 or multiple times
        // "+" : 1 or multiple times
        // "{n,m}" : the length should be n to m(n and m is numbers)
        // "$" : end by the previous string

        //validation for email format
        // check whether the email is allowed.
        while (!email.matches("^\\w+((-\\w+)|(\\.\\w+))*@\\w+(\\.\\w{2,3}){1,3}$"))
        {
            System.out.println("The email format is not correct.");
            System.out.print("Enter the user's email: ");
            email = scan.nextLine();
        }

        while (CM.findAccount(email) != -1)
        {
            System.out.println("This email exist already.");
            System.out.print("Enter the user's email: ");
            email = scan.nextLine();
        }


        System.out.print("Enter the user's password: ");
        psw1 = scan.nextLine();


        //validation for password format
        //reference from here:  https://segmentfault.com/a/1190000038356577
        while (!psw1.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$"))
        {
            System.out.println("The password should be at least 8 characters long, must include 1 upper case, 1 lower case and 1 number.");
            System.out.print("Enter the user's password: ");
            psw1 = scan.nextLine();
        }

        System.out.print("Enter the password again: ");
        psw2 = scan.nextLine();
        while (!psw2.equals(psw1)) {
            System.out.println("The password should be the same with the first time.");
            System.out.print("Enter the password again: ");
            psw2 = scan.nextLine();
        }

        occupation = setUserInfo("occupation");
        mobileNumber = setUserInfo("mobileNumber");
        highQualification = setUserInfo("high qualification");
        employerDetail = setUserInfo("employer detail");
        interestArea = setUserInfo("interesting area");

        CM.getUserList().add(new User(ID, name, psw2, 0, email, occupation, mobileNumber, highQualification, employerDetail, interestArea));
        CM.writeUserToUserFile();
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

    //0519 changing part
    public void reviewerFunctions(String email) throws Exception
    {



        menu.displayReviewerMenu();

        Scanner scan = new Scanner(System.in);
        System.out.print("Enter your option: ");
        String option = scan.nextLine();
        switch (option)
        {
            case "1":
                specifyExpertise(email);
                //specify expertise keywords
                break;
            case "2":
                writeEvaluation(email); //get the paper
                //write evaluation
                break;
            case "3": break;
        }


    }

    //specify the keywords for reviewer
    public void specifyExpertise(String email)
    {
        User userReviewer = CM.searchUserByEmail(email);

        System.out.println("\n" + "Please select three keywords(first one should be your strong expertise)" + "\n");
        System.out.println("1.Information Technology");
        System.out.println("2.Cyber Security");
        System.out.println("3.Cloud Computing");
        System.out.println("4.Network Develop");
        System.out.println("5.Software Engineering");
        System.out.println("6.Distributed Mobile Develop");
        System.out.println("7.Database");
        System.out.println("8.Big Data");
        System.out.println("9.User Interface Design");
        System.out.println("10.Manually Add New Keywords ");
        Scanner sc = new Scanner(System.in);

        String selectKeywords = sc.nextLine();

        //need validation of at least three options
        switch(selectKeywords)
        {
            case "1":
                userReviewer.getKeywords().add("Information Technology");
                System.out.println("Information Technology have added");
                break;
            case "2":
                userReviewer.getKeywords().add("Cyber Security");
                System.out.println("Cyber Security have added");
                break;
            case "3":
                userReviewer.getKeywords().add("Cloud Computing");
                System.out.println("Cloud Computing have added");

                break;
            case "4":
                userReviewer.getKeywords().add("Network Develop");
                System.out.println("Network Develop have added");
                break;
            case "5":
                userReviewer.getKeywords().add("Software Engineering");
                System.out.println("Software Engineering have added");
                break;
            case "6":
                userReviewer.getKeywords().add("Distributed Mobile Develop");
                System.out.println("Distributed Mobile Develop have added");
                break;
            case "7":
                userReviewer.getKeywords().add("Database");
                System.out.println("Database have added");
                break;
            case "8":
                userReviewer.getKeywords().add("Big Data");
                System.out.println("Big Data have added");
                break;
            case "9":
                userReviewer.getKeywords().add("User Interface Design");
                System.out.println("User Interface Design have added");
                break;
            case "10":
                System.out.println("Please input your keywords (Split with comma,at least three keywords)");
                String[] inputKeywords = sc.nextLine().split(",");

                for (int i = 0; i<inputKeywords.length; i++)
                {
//                    if (isStringAlphabetic(inputKeywords[i]))
                    userReviewer.getKeywords().add(inputKeywords[i]);
                    System.out.println("Keywords: " + inputKeywords[i]  + " added successfully");
                }
                break;
            default:
                System.out.println("Please choose 1 - 10");
                break;



        }



        System.out.println("You have " + userReviewer.getKeywords().size() + " Keywords");
        CM.writeUserToUserFile();


    }

    public void selectKeywords(String selection)
    {

    }

    //reviewer write evaluation for assign paper
    public void writeEvaluation(String email) throws ParseException {
        User userReviewer = CM.searchUserByEmail(email);
        ArrayList<Paper> assignPaper = userReviewer.getAssignedPaper();
        ArrayList<String> assignMesage = userReviewer.getMessageBox();
        System.out.println("Message numbers: " + assignMesage.size());
        System.out.println("Assign paper numbers: " +assignPaper.size() + "\n");
        if (assignMesage.size() >= 1) {
            for (String message : assignMesage)
            {
                System.out.println("This is your message: " + message + "\n");
            }
        }
        else
        {
            System.out.println("You do not have message and assign paper"  + "\n");
            return;
        }


        Scanner sc = new Scanner(System.in);
        String Evaulation = "";
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String evPaper = "";
        System.out.println("Current Time: " + sdf.format(d));

        while (assignPaper.size() >= 1)
        {
            for (int i = 0; i < assignPaper.size(); i++)
            {
                Paper one = assignPaper.get(i);
                {
                    if (!sdf.parse(one.getRmDeadline()).before(d)){
                        System.out.println("This is your assign paper :" + (i + 1) + "." + "Paper name: " + one.getName() + "," + "Conference name: " + one.getConName() + "," +
                                "Review deadline" + "," + one.getRmDeadline());}
                    else
                        System.out.println("The review time of the paper :  " + one.getName() + "is expired, review deadline: " + one.getRmDeadline());
                }

            }
            System.out.println("\n" + "Please select the paper to evaluate");
            evPaper = sc.nextLine();
            if (!evPaper.trim().isEmpty() && isStringNumeric(evPaper))
            {
                int option = Integer.parseInt(evPaper);
                if (option <= assignPaper.size())
                {
                    Paper rvPaper = assignPaper.get(Integer.parseInt(evPaper) - 1); //index should minus 1 of the list size

                    System.out.println("Please enter an evaluation");
                    Evaulation = sc.nextLine();
                    if (!Evaulation.trim().isEmpty() && isStringAlphabetic(Evaulation))
                    {
                        rvPaper.getEvaluation().add(Evaulation);

                        CM.writePaperToFile();
                        System.out.println("Add Evaluation for paper "+ "'" + rvPaper.getName() + "'" + ": " + rvPaper.getEvaluation().get(0));
                        return;

                    }

                    else
                    {
                    System.out.println("Evaluation should not be empty");
//                    Evaulation = sc.nextLine();
                    }
                }

            }
            else if (Integer.parseInt(evPaper) > assignPaper.size())
            {
                System.out.println("Please choose from 1 to " + assignPaper.size() + "\n");
//                evPaper = sc.nextLine();
            }
            else if (!isStringNumeric(evPaper))
            {
                System.out.println("you should enter numbers");
//                evPaper = sc.nextLine();
            }
        }
        if (assignPaper.size() == 0)
            System.out.println("You do not have assign paper");
    }


    public static void main(String[] args) throws Exception{
        new CMS();


    }
}
