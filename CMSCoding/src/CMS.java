/**
 * @author Yuzhe Wang
 * @version 30 Apr 2021
 */
import java.io.*;
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
    public void chairFunction(String email)throws Exception
    {
        menu.displayChairMenu();
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter your option: ");
        String option = scan.nextLine();
        switch (option)
        {
            case "1":
                addConference(email);
                //add a conference
                break;
            case "2":
                modifyConference(email);
                //modify a conference
                break;
            case "3":
                assignReviewer(email);
                //assign reviewer papers
                break;
            case"4":
                makeDecision(email);
            case"5":break;
            default:
                System.out.println("Please choose a correct number");
                break;
        }

    }



    public void addConference(String email) throws ParseException
    {

        User chair = CM.searchUserByEmail(email);
        System.out.println("Hi"+" "+chair.getName()+","+"you are now logging in as a chair!");
        System.out.println("**************************************");
        System.out.println("          Chair Management           ");
        System.out.println("**************************************");
        System.out.print("Please input the conference name:");
        Scanner sc = new Scanner(System.in);
        String name = sc.nextLine();
        while (!CM.isInputUpToFormat(name)) // check whether the name is allowed.
        {
            System.out.println("The name cannot be null and should only be alphabetic.");
            System.out.print("Enter the conference name: ");
            name = sc.nextLine();
        }
        System.out.print("Please input the conference title:");
        String title = sc.nextLine();
        while (!CM.isInputUpToFormat(title)) // check whether the name is allowed.
        {
            System.out.println("The title cannot be null and should only be alphabetic.");
            System.out.print("Enter the conference title: ");
            title = sc.nextLine();
        }
        System.out.println("Please choose the conference topic from the keywordList(Here is the keyword list):");
        for(int i = 0; i < CM.getKeywordList().size();i++)//choose one keyword as a this conference topic
        {
            if (CM.getKeywordList().get(i).equals(""))
            {
                CM.getKeywordList().remove(CM.getKeywordList().get(i));
            }
            else
                System.out.println(i+"."+CM.getKeywordList().get(i));
        }
        String option = sc.nextLine();//add validations
        while(isStringAlphabetic(option) || Integer.parseInt(option) >= CM.getKeywordList().size() || Integer.parseInt(option) < 0)
        {
            System.out.println("Please input the correct number");
            option = sc.nextLine();
        }
        String topic = CM.getKeywordList().get(Integer.parseInt(option));

        System.out.println("Please set the submission deadline for this conference, the format is (yyyy-MM-dd HH:mm:ss)");
        String subDate = sc.nextLine();//set submission deadline for this conference
        while(!CM.isTimeUpToStandard(subDate))
        {
            subDate = sc.nextLine();
        }

        System.out.println("Please set the review deadline for this conference, the format is (yyyy-MM-dd HH:mm:ss)");
        String revDate = sc.nextLine();//set the review deadline for this conference
        while(!CM.isTimeUpToStandard(revDate))
        {
            revDate = sc.nextLine();
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        while (dateFormat.parse(subDate).after(dateFormat.parse(revDate)))//submission deadline should not after the review deadline
        {
            System.out.println("Submission time should before the review time");
            revDate = sc.nextLine();
        }
        Conference newConference = new Conference(name,title,topic,subDate,revDate);//create the conference object
        CM.getConferenceList().add(newConference);
        CM.writeConferenceToFile();//reload the database

    }

    public void modifyConference(String email) throws ParseException
    {

        User chair = CM.searchUserByEmail(email);
        System.out.println("Hi"+" "+chair.getName()+","+"you are now logging in as a chair!");
        Scanner sc = new Scanner(System.in);
        for(Conference one : CM.getConferenceList())
            System.out.println( CM.getConferenceList().indexOf(one)+"."+ one.toString());
        System.out.println("Please choose one conference to modify");
        String option = sc.nextLine();
        while(CM.isInputUpToFormat(option) || Integer.parseInt(option) >= CM.getConferenceList().size() || Integer.parseInt(option) < 0)
        {
            System.out.println("Can not find the conference!");
            System.out.println("Please enter the number again: ");
            option = sc.nextLine();

        }//let chair to choose one conference to modify
        for(Conference one:  CM.getConferenceList())
        {
            if (Integer.parseInt(option) ==  CM.getConferenceList().indexOf(one))
            {
                System.out.println("please choose which part to modify:");
                System.out.println("(1) conference name");
                System.out.println("(2) conference title");
                System.out.println("(3) conference topic");
                System.out.println("(4) conference submission deadline");
                System.out.println("(5) conference review deadline");
                System.out.println("(6) delete the conference");
                String number = sc.nextLine();
                while(isStringAlphabetic(number)||Integer.parseInt(number) < 0||Integer.parseInt(number) >6)
                {
                    System.out.println("Please input the correct number");
                    number = sc.nextLine();
                }
                switch (number) {
                    case "1":
                        System.out.print("please input the new name:");
                        String newName = sc.nextLine();
                        while(!CM.isInputUpToFormat(newName))
                        {
                            System.out.println("Please input the correct name format:");
                            sc.nextLine();
                        }
                        one.setConName(newName);//new name, title and topic should not be null
                        chair.getConferenceListForChair().add(one);
                        //add this conference into chair's chair conference list
                        break;
                    case "2":
                        System.out.print("please input the new title:");
                        String newTitle = sc.nextLine();
                        while(!CM.isInputUpToFormat(newTitle))
                        {
                            System.out.println("Please input the correct title format:");
                            sc.nextLine();
                        }
                        one.setConTitle(newTitle);
                        chair.getConferenceListForChair().add(one);
                        //add this conference into chair's chair conference list
                        break;
                    case "3":
                        System.out.println("Please choose a new topic for this conference");
                        for(int i = 0; i < CM.getKeywordList().size();i++)
                        {
                            if (CM.getKeywordList().get(i).equals(""))
                            {
                                CM.getKeywordList().remove(CM.getKeywordList().get(i));
                            }
                            else
                                System.out.println(i+"."+CM.getKeywordList().get(i));
                        }
                        String newTopic = sc.nextLine();//from the existing keyword list to choose one keyword for this conference
                        while(isStringAlphabetic(newTopic) || Integer.parseInt(newTopic) >= CM.getKeywordList().size() || Integer.parseInt(newTopic) < 0)
                        {
                            System.out.println("Please input the correct number");
                            newTopic = sc.nextLine();
                        }

                        one.setConTitle(CM.getKeywordList().get(Integer.parseInt(newTopic)));
                        chair.getConferenceListForChair().add(one);
                        //add this conference into chair's chair conference list
                        break;
                    case "4":
                        System.out.print("please input the new submission deadline,the format is (yyyy-MM-dd HH:mm:ss)");
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String newSubDeadline = sc.nextLine();
                        while(!CM.isTimeUpToStandard(newSubDeadline)|| dateFormat.parse(newSubDeadline).after(dateFormat.parse(one.gatRevDate())))
                        {
                            System.out.println("Please input the correct submission deadline:");
                            newSubDeadline = sc.nextLine();
                        }
                        one.setSubDate(newSubDeadline);
                        //some validation on deadline, its new submission time should not after the existing review deadline
                        chair.getConferenceListForChair().add(one);
                        //add this conference into chair's chair conference list
                        break;
                    case "5":
                        System.out.print("please input the new review deadline,the format is (yyyy-MM-dd HH:mm:ss)");
                        String newRevDeadline = sc.nextLine();
                        SimpleDateFormat dF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        while(!CM.isTimeUpToStandard(newRevDeadline) || dF.parse(one.getSubDate()).after(dF.parse(newRevDeadline)))
                        {
                            System.out.print("please input the correct review deadline:");
                            newRevDeadline = sc.nextLine();
                        }
                        //some validation on deadline, its existing  submission time should not after the new review deadline
                        one.setRevDate(newRevDeadline);
                        chair.getConferenceListForChair().add(one);
                        //add this conference into chair's chair conference list
                        break;
                    case "6":
                        CM.getConferenceList().remove(one);
                        System.out.println("Successfully delete!");
                        break;
                    default:
                        System.out.println("please input the correct number!");
                        break;

                }
                CM.writeUserToUserFile();
                CM.writeConferenceToFile();
                //reload the database
            }

        }

    }


    public void assignReviewer(String email) throws Exception
    {
        User chair = CM.searchUserByEmail(email);
        System.out.println("Hi"+" "+chair.getName()+","+"you are now logging in as a chair!");
        System.out.println("**************************************");
        System.out.println("          Chair Management           ");
        System.out.println("**************************************");
        System.out.println("0.Assign reviewer by system");
        System.out.println("1.Assign reviewer manually");
        Scanner sc = new Scanner(System.in);
        String option  = sc.nextLine();
        while(isStringAlphabetic(option)||(Integer.parseInt(option)!= 1 && Integer.parseInt(option)!= 0))
        {
            System.out.println("Please input the correct number");
            option = sc.nextLine();
        }//create a chair object and let he/she choose the function
        switch (option)
        {
            case"0"://this choice is assign reviewers automatically
                ArrayList<User> reviewerListAuto = new ArrayList<>();//find the reviewer list int the whole user list
                System.out.println("Please choose one paper to assign reviewer:");//find the non reviewed papers
                for(Paper p:CM.getPaperList())
                {
                    if(p.getStatus().equals("NO"))
                        System.out.println(CM.getPaperList().indexOf(p)+"."+p.getName()+" "+"[Key word:]"+p.getStringListNames(p.getKeywords()));
                }

                String paperNumAuto = sc.nextLine();
                while(isStringAlphabetic(paperNumAuto)||Integer.parseInt(paperNumAuto) < 0)
                {
                    System.out.println("Please input the correct number");
                    paperNumAuto = sc.nextLine();
                }
                Paper paperAuto = CM.getPaperList().get(Integer.parseInt(paperNumAuto));//after choose the paper , create a paper object
                for(User u:CM.getUserList())
                {
                    if(u.getChooseType() == 2&& u!=null )
                        reviewerListAuto.add(u);
                }

                for(Paper p: CM.getPaperList())
                {
                    p.getAssignedReviewerList().clear();
                }//clear the list to avoid the list does not contain null elements

                int i = 0;
                while(paperAuto.getAssignedReviewerList().size() < 3 && i < reviewerListAuto.size())
                //choose 3 reviewers for this paper and ensure the choice in the reviewer list boundary
                {
                    User userAutoOne = reviewerListAuto.get(i);//find the specific reviewer

                    User userAuto = CM.searchUser(userAutoOne.getName());
                    //find the specific reviewer and find the location in the user list
                    if(!userAuto.getName().equals(paperAuto.getAuthor())
                            &&CM.checkTwoArrayListHaveSameVariable(userAutoOne.getKeywords(),paperAuto.getKeywords())
                            &&userAuto!=null
                            &&(CM.checkConferenceOverlaps(userAuto.getConferenceListForChair(),CM.searchConference(paperAuto.getConName())))==false)
                    //the reviewer could not be the author of this paper and the reviewer keywords should match the paper's keywords and ensure this conference not in this reviewer's chair list and author list
                    {
                        if(userAuto != null)
                            paperAuto.getAssignedReviewerList().add(userAuto);
                        //paper add this reviewer into its assigned reviewer list
                    }

                    i++;
                }
                for(User u:paperAuto.getAssignedReviewerList())
                {
                    if(u!=null)
                    {
                        sendMessage(u,chair);//send every reviewer a message to prompt him/her they have a paper to review
                        u.getAssignedPaper().add(paperAuto);
                        //reviewer add this paper into his/her assigned paper list
                        u.getConferenceListForReviewer().add(CM.searchConference(paperAuto.getConName()));
                    }
                    //reviewer add this paper's conference into his/her reviewer conference list
                }
                if(CM.checkConferenceOverlaps(chair.getConferenceListForAuthor(),CM.searchConference(paperAuto.getConName())) ==false
                        &&CM.checkConferenceOverlaps(chair.getConferenceListForReviewer(),CM.searchConference(paperAuto.getConName()))== false)
                    chair.getConferenceListForChair().add(CM.searchConference(paperAuto.getConName()));
                //this chair add this paper's conference into his chair conference list and avoid his other lists contain this conference
                if(paperAuto.getAssignedReviewerList().size() == 3)
                    paperAuto.setStatus("YES");
                //set the paper status to "yes",so it can not be choose to review next time
                System.out.println("Add Successfully!");
                CM.writeUserToUserFile();
                CM.writePaperToFile();
                //reload into database
                break;
            case"1":
                System.out.println("please choose one paper to assign");
                for(Paper p: CM.getPaperList())
                {
                    p.getAssignedReviewerList().clear();
                }//clear the list to avoid the list does not contain null elements
                for(Paper p:CM.getPaperList())
                {
                    if(p.getStatus().equals("NO"))
                        System.out.println(CM.getPaperList().indexOf(p)+"."+p.getName()+" "+"[Key word:]"+p.getStringListNames(p.getKeywords()));
                }

                String paperNum = sc.nextLine();
                while(isStringAlphabetic(paperNum)|| Integer.parseInt(paperNum) < 0)
                {
                    System.out.println("Please input the correct number");
                    paperNum = sc.nextLine();
                }
                Paper paperObject = CM.getPaperList().get(Integer.parseInt(paperNum));
                //show the paper which could be assigned reviewer and after choosing create a paper object
                System.out.println("This is the reviewer list, please choose one to assign this paper");

                for(User u:CM.getUserList())
                {
                    if(u.getChooseType() == 2
                            && !u.getName().equals(paperObject.getAuthor())
                            && CM.checkConferenceOverlaps(u.getConferenceListForChair(),CM.searchConference(paperObject.getConName()))==false
                    )//reviewer could not be the author of this paper
                        System.out.println(CM.getUserList().indexOf(u)+"."+u.getName()+" "+"[Keywords:]"+u.getStringListNames(u.getKeywords()));
                }
                //show who can review paper and show their keywords
                while(paperObject.getAssignedReviewerList().size() < 3)
                //assign 3 reviewers a time for a paper
                {
                    System.out.println("Please choose one reviewer:");
                    String number = sc.nextLine();
                    while(isStringAlphabetic(number)|| Integer.parseInt(number) < 0)
                    {
                        System.out.println("Please input the correct number");
                        number = sc.nextLine();
                    }
                    for(User u: paperObject.getAssignedReviewerList())
                    {
                        while(u.getName().equals(CM.getUserList().get(Integer.parseInt(number)).getName()))
                        {
                            System.out.println("You have already assign this reviewer,please choose again");
                            number = sc.nextLine();
                        }
                    }//could not assign the same reviewer
                    while(!CM.checkTwoArrayListHaveSameVariable(CM.getUserList().get(Integer.parseInt(number)).getKeywords(),paperObject.getKeywords()))
                    {
                        System.out.println("You choose the reviewer's keywords do not match the paper's key word");
                        number = sc.nextLine();
                    }//reviewers' key words should match the paper's key words

                    User reviewerObject =CM.getUserList().get(Integer.parseInt(number));
                    //create this reviewer object
                    paperObject.getAssignedReviewerList().add(reviewerObject);
                    //paper's reviewer list add this reviewer
                }
                for(User u:paperObject.getAssignedReviewerList())
                {
                    u.getAssignedPaper().add(paperObject);
                    //reviewer add this paper into their assigned paper list
                    u.getConferenceListForReviewer().add(CM.searchConference(paperObject.getConName()));
                    //reviewer add this paper's conference into their reviewer conference list
                    sendMessage(u,chair);
                    //send every reviewer a message
                }
                if(CM.checkConferenceOverlaps(chair.getConferenceListForAuthor(),CM.searchConference(paperObject.getConName())) ==false
                        &&CM.checkConferenceOverlaps(chair.getConferenceListForReviewer(),CM.searchConference(paperObject.getConName()))==false)
                    chair.getConferenceListForChair().add(CM.searchConference(paperObject.getConName()));
                //ensure the chair conference list for chair is not overlap his other identities conference lists
                paperObject.setStatus("YES");
                //set the paper status to yes
                System.out.println("This paper have enough reviewers!");
                CM.writeUserToUserFile();
                CM.writePaperToFile();
                //reload into database
                break;
            default:
                System.out.println("Please input the correct number");
                break;

        }
    }

    public void sendMessage(User u,User one)
    {
        System.out.println("You are sending message to"+" "+u.getName()+"(Please end with the sender name)");
        Scanner sc = new Scanner(System.in);
        String content = sc.nextLine()+"["+"From: "+one.getName()+","+"To:"+u.getName()+"]";
        u.getMessageBox().add(content);

    }

    public void makeDecision(String email)
    {
        User chair = CM.searchUserByEmail(email);// create the user object
        System.out.println("Hi"+" "+chair.getName()+","+"you are now logging in as an author!");
        System.out.println("**************************************");
        System.out.println("           Chair Management           ");
        System.out.println("**************************************");
        ArrayList<Paper> couldMakeDecision = new ArrayList<>();
        for(Paper p: CM.getPaperList())
        {
            if(p.getEvaluation().size() > 0)
                couldMakeDecision.add(p);
        }
        System.out.println("This is the paper which get the evaluation and you can make decision for it");
        for(Paper P:couldMakeDecision)
            System.out.println(couldMakeDecision.indexOf(P)+"."+P.getName());
        Scanner sc =new Scanner(System.in);
        String option = sc.nextLine();
        while(isStringAlphabetic(option)||Integer.parseInt(option)>couldMakeDecision.size()||Integer.parseInt(option)<0)
        {
            System.out.println("Please input the correct number");
            option = sc.nextLine();
        }
        Paper paperObject = couldMakeDecision.get(Integer.parseInt(option));
        System.out.println("These are the evaluations");
        for(String s: paperObject.getEvaluation())
            System.out.println(paperObject.getEvaluation().indexOf(s)+"."+s);
        System.out.println("Please choose your decision");
        System.out.println("1.Accept");
        System.out.println("2.Reject");
        String decision = sc.nextLine();
        while(isStringAlphabetic(decision)||(Integer.parseInt(decision)!=1 && Integer.parseInt(decision)!=2))
        {
            System.out.println("Please input the correct number");
            decision = sc.nextLine();
        }
        switch (decision) {
            case "1":
                paperObject.setDecision("Accept");
                chair.getConferenceListForChair().add(CM.searchConference(paperObject.getConName()));
                break;
            case "2":
                paperObject.setDecision("Reject");
                chair.getConferenceListForChair().add(CM.searchConference(paperObject.getConName()));
                break;
        }
        CM.writePaperToFile();

    }

    public void authorFunction(String email) throws Exception
    {

        menu.displayAuthorMenu();
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter your option: ");
        String option = scan.nextLine();
        switch (option)
        {
            case "1":
                specifyExpertise(email);
                //provide key words for paper
                break;
            case "2":
                submitPaper(email);
                //submit paper
                break;
            case "3": break;
        }


    }

    public void submitPaper(String email) throws Exception//this function is for chair to submit the paper
    {
        User author =CM.searchUserByEmail(email);// create the user object
        System.out.println("Hi"+" "+author.getName()+","+"you are now logging in as an author!");
        System.out.println("**************************************");
        System.out.println("          Author Management           ");
        System.out.println("**************************************");
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("Current Time: " + sdf.format(d));
        for(Conference c: CM.getConferenceList())
            System.out.println(CM.getConferenceList().indexOf(c)+"."+c.getConName());
        Scanner sc = new Scanner(System.in);
        System.out.println("Please Choose a Conference: ");
        String option  = sc.nextLine();
        while(isStringAlphabetic(option)|| Integer.parseInt(option) < 0)
        {
            System.out.println("Please input the correct number:");
            option =sc.nextLine();
        }
        while(CM.checkConferenceOverlaps(author.getConferenceListForChair(),CM.getConferenceList().get(Integer.parseInt(option)))==true
                ||CM.checkConferenceOverlaps(author.getConferenceListForReviewer(),CM.getConferenceList().get(Integer.parseInt(option)))==true)
        {
            System.out.println("You can not submit paper in this conference, because you have another identity in this conference");
            option =sc.nextLine();
        }
        Conference conferenceObject = CM.getConferenceList().get(Integer.parseInt(option));
        //show the conference list and let the author to choose one conference to submit paper.
        System.out.println("The submission deadline for conference: " + conferenceObject.getSubDate());
        if(d.after(sdf.parse(conferenceObject.getSubDate())))
        {
            System.out.println("you can not submit the paper in this current time");
            //show the submission deadline for this conference, if the submission time is after this deadline, this paper will be rejected.
            return;
        }
        System.out.println("Please enter the paper name (end with [.pdf] ot [.docx])");
        //in put the specific paper format
        String paperName = sc.nextLine();
        while(!CM.validFile(paperName))
        {
            System.out.println("The file format is invalid");
            paperName = sc.nextLine();//some validation for this paper format
        }
        //choose 3 keywords for this author key word list
        ArrayList<String> submittingPaperKeywords = new ArrayList<>();
        int i = 0;
        while(i < 3)
        {
            for(String s:author.getKeywords())
                System.out.println(author.getKeywords().indexOf(s)+"."+s);
            System.out.println("Please choose the key word for this paper");
            String number = sc.nextLine();//add validations
            while(isStringAlphabetic(number) || Integer.parseInt(number) >= author.getKeywords().size() || Integer.parseInt(number) < 0)
            //validation in input
            {
                System.out.println("Please input the correct number");
                number = sc.nextLine();
            }
            submittingPaperKeywords.add(author.getKeywords().get(Integer.parseInt(number)));
            i++;
        }

        Paper newPaper = new Paper(paperName,conferenceObject.getSubDate(),conferenceObject.gatRevDate(),"NO",author.getName(),"null",conferenceObject.getConName());
        //create the paper object
        newPaper.getKeywords().addAll(submittingPaperKeywords);
        CM.getPaperList().add(newPaper);
        //add the paper to paper list

        author.getConferenceListForAuthor().add(conferenceObject);
        //add the relevant conference to this author's chair conference list
        author.getSubmittedPaper().add(newPaper);
        //add this paper to this author's submitted paper list

        CM.writePaperToFile();
        CM.writeUserToUserFile();
        //reload this new adding info into user and paper database
        System.out.println("Submit the paper successfully");
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
                    System.out.println("Add Evaluation successfully: " + rvPaper.getEvaluation().get(0));

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
