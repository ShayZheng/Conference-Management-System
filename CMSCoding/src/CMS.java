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
        while (email.trim().equals("") || CM.findAccount(email) != -1) // check whether the name is allowed.
        {
            System.out.println("This email cannot be used.");
            System.out.print("Enter the user's name: ");
            email = scan.nextLine();
        }


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

        CM.getUserList().add(new User(ID, name, psw2, 0, email, occupation, mobileNumber, highQualification, employerDetail, interestArea));
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

    public void specifyExpertise(String email)
    {
        User userReviewer = CM.searchUserByEmail(email);
        ArrayList<String> rvKeywordList = userReviewer.getKeywords();
        System.out.println("Please input your keywords (Split with comma,at least three keywords)");
        Scanner sc = new Scanner(System.in);
        String[] inputKeywords = sc.nextLine().split(",");
        for (int i = 0;i<inputKeywords.length;i++)
        {
            userReviewer.getKeywords().add(inputKeywords[i]);
        }

        System.out.println(userReviewer.getKeywords().size());
        CM.writeUserToUserFile();


    }

    public void writeEvaluation(String email) throws ParseException {
        User userReviewer = CM.searchUserByEmail(email);
        ArrayList<Paper> assignPaper = userReviewer.getAssignedPaper();
        ArrayList<String> assignMesage = userReviewer.getMessageBox();
        System.out.println(assignMesage.size());
        System.out.println(assignPaper.size());
        if (assignMesage.size() >= 1) {
            for (String message : assignMesage)
            {
                System.out.println("This is your message: " + message);
            }
        }
        else
        {
            System.out.println("You do not have message");
            return;
        }


        Scanner sc = new Scanner(System.in);
        int paperIndex = 0;
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String evPaper = "";
        System.out.println("Current Time: " + sdf.format(d));
        if (assignPaper.size() >= 1)
        {
            for (int i = 0; i < assignPaper.size(); i++) {
                Paper one = assignPaper.get(i);
                {
                    if (!sdf.parse(one.getRmDeadline()).before(d)){
                        System.out.println("This is your assign paper :" + (i + 1) + "." + "Paper name: " + one.getName() + "," + "Conference name: " + one.getConName() + "," +
                                "Review deadline" + "," + one.getRmDeadline());}
                    else
                        System.out.println("The review time of the paper :  " + one.getName() + "is expired, review deadline: " + one.getRmDeadline());
                }

            }
            System.out.println("Please select the paper to evaluate");
            evPaper = sc.nextLine();
            if (!evPaper.isEmpty() && !isStringAlphabetic(evPaper))
            {
                Paper rvPaper = assignPaper.get(Integer.parseInt(evPaper));
                System.out.println("Please enter an evaluation");
                String Evaulation = sc.nextLine();
                if (!Evaulation.isEmpty())
                {
                    rvPaper.getEvaluation().add(Evaulation);
                    CM.writePaperToFile();
                    System.out.println("Add Evaluation for paper "+ "'" + rvPaper.getName() + "'" + ": " + rvPaper.getEvaluation().get(0));

                }

                else
                    System.out.println("Evaluation should not be empty");

            }
            if (isStringAlphabetic(evPaper))
                System.out.println("you should enter numbers");
        }
        else
            System.out.println("You do not have assign paper");
    }


    public static void main(String[] args) throws Exception{
        new CMS();


    }
}
