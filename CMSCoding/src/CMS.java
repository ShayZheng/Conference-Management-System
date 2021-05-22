
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class CMS
{
    private ConferenceManagement CM;
    private Menu menu;
    private Administrator admin;

    public CMS() throws Exception{
        CM = new ConferenceManagement();
        menu = new Menu();
        CM.readFromFile();
        admin = new Administrator("admin@monash.edu", "Administrator");
        // openSystem();
    }



    public void openSystem() throws Exception {
        String option;
        option = "";
        while (!option.equals("4"))
        {
            menu.displayMainMenu();

            Scanner scan = new Scanner(System.in);
            option = scan.nextLine().trim();
            System.out.print("\n");
            // Check whether the input option is valid.
            if (!isStringNumeric(option))
                System.out.println("You need to choose a number between 1 to 3.");
            else
            {
                int checkOption = Integer.parseInt(option); // convert the String into an integer. I got this method from https://blog.csdn.net/a772304419/article/details/79723249.
                if (checkOption < 1 || checkOption > 4)
                    System.out.println("You need to choose a number between 1 to 3.");
            }

            switch (option)
            {
                case "1": register(); break;
                case "2": login(); break;
                case "3": System.exit(0);  //exit system
            }
        }

    }


    public void login() throws Exception {
        String email;
        String psw;
        int label = 0;
        String option;
        option = "";

        Scanner scan = new Scanner(System.in);
        System.out.println("**************************************");
        System.out.println("     Conference Management System     ");
        System.out.println("**************************************");
        System.out.print("Enter the email: ");
        email = scan.nextLine().trim();
        if(email.equals(admin.getAdminUsername())){
            System.out.print("Please enter the administrator's password: ");
            psw = scan.nextLine().trim();
            System.out.print("\n");
            while(!psw.equals(admin.getAdminPsw())){
                System.out.print("Your administrator password is incorrect, please enter again: ");
                psw = scan.nextLine().trim();
            }
            while (!option.equals("4"))
            {
                menu.displayAdministratorMenu();

                option = scan.nextLine().trim();
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
                    case "1": retrieveUser(); break;
                    case "2": retrieveConference(); break;
                    case "3": retrievePaper(); break;
                }
            }

        } else{
            for (User thisUser : CM.getUserList() ) {
                if (thisUser.getEmail().equals(email)) {
                    label = 1;
                    System.out.print("Enter password: ");
                    psw = scan.nextLine().trim();
                    int count = 1;
                    while(count < 3 && !psw.equals(thisUser.getPsw())) {
                        System.out.print("Your password is incorrect, please enter again: ");
                        psw = scan.nextLine().trim();
                        count += 1;
                    }
                    if (psw.equals(thisUser.getPsw())){
                        while (!option.equals("4"))
                        {
                            menu.displayTypeMenu();

                            option = scan.nextLine().trim();
                            System.out.print("\n");
                            // Check whether the input option is valid.
                            if (!isStringNumeric(option))
                                System.out.println("You need to choose a number between 1 to 4. The option should not be empty.");
                            else
                            {
                                int checkOption = Integer.parseInt(option); // convert the String into an integer. I got this method from https://blog.csdn.net/a772304419/article/details/79723249.
                                if (checkOption < 1 || checkOption > 3)
                                    System.out.println("You need to choose a number between 1 to 4.");
                            }

                            switch (option)
                            {
                                case "1":
                                    chairFunction(email);//0519 chair functions
                                    break;
                                case "2":
                                    //0519 changing part
                                    reviewerFunctions(email);
                                    break;
                                case "3":
                                    authorFunction(email);//0519 author functions
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
        name = scan.nextLine().trim();
        while (name.trim().equals("") || !isStringAlphabetic(name)) // check whether the name is allowed.
        {
            System.out.println("The user's neme cannot be empty and should only be alphabetic.");
            System.out.print("Enter the user's name again: ");
            name = scan.nextLine().trim();
        }

        System.out.print("Enter the user's email: ");
        email = scan.nextLine().trim();
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
            System.out.println("The email format is not correct, the email should be in a format like xxx@xxx.xx.");
            System.out.print("Enter the user's email again: ");
            email = scan.nextLine().trim();
        }

        while (CM.findAccount(email) != -1)
        {
            System.out.println("This email exist already.");
            System.out.print("Enter the user's email again: ");
            email = scan.nextLine().trim();
        }



        System.out.print("Enter the user's password: ");
        psw1 = scan.nextLine().trim();


        //validation for password format
        //reference from here:  https://segmentfault.com/a/1190000038356577
        while (!psw1.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$"))
        {
            System.out.println("The password should be at least 8 characters long, must include 1 upper case, 1 lower case and 1 number.");
            System.out.print("Enter the user's password again: ");
            psw1 = scan.nextLine().trim();
        }

        System.out.print("Enter the password again: ");
        psw2 = scan.nextLine().trim();
        while (!psw2.equals(psw1)) {
            System.out.println("The password should be the same with the first time.");
            System.out.print("Enter the password again: ");
            psw2 = scan.nextLine().trim();
        }

        System.out.print("Enter the Mobile Number: ");
        mobileNumber = scan.nextLine().trim();
        while(mobileNumber.trim().equals("") || isStringNumeric(mobileNumber) == false || mobileNumber.length() != 10){
            System.out.println("The mobile number cannot be empty, it should be numeric and must be 10 digits long.");
            System.out.print("Enter the mobile number again: ");
            mobileNumber = scan.nextLine().trim();
        }

        occupation = setUserInfo("occupation");
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
        if (checkedString.trim().equals("")){
            return false;
        }
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
        if (checkedString.trim().equals("")){
            return false;
        }
        for (i = 0; i < checkedString.length(); i ++)
        {
            if (!Character.isDigit(checkedString.charAt(i)))
                return false;
        }
        return true;
    }


    public String setUserInfo(String info){
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter the " + info + ": ");
        String inFo = scan.nextLine().trim();
        while (inFo.trim().equals("") || isStringAlphabetic(inFo) == false) // check whether the name is allowed.
        {
            System.out.println("The " + info + " cannot be empty and should be alphabetic.");
            System.out.print("Enter the " + info + " again: ");
            inFo = scan.nextLine().trim();
        }
        return inFo;
    }
    public void chairFunction(String email)throws Exception
    {
        menu.displayChairMenu();
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter your option: ");
        String option = scan.nextLine().trim();
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
        String name = sc.nextLine().trim();
        while (!isStringAlphabetic(name)) // check whether the name is allowed.
        {
            System.out.println("The name cannot be empty and should only be alphabetic.");
            System.out.print("Enter the conference name: ");
            name = sc.nextLine().trim();
        }
        System.out.print("Please input the conference title:");
        String title = sc.nextLine().trim();
        while (!isStringAlphabetic(title)) // check whether the name is allowed.
        {
            System.out.println("The title cannot be empty and should only be alphabetic.");
            System.out.print("Enter the conference title: ");
            title = sc.nextLine().trim();
        }
        System.out.println("Please choose the conference topic from the keywordList(Here is the keyword list):");
        for(int i = 0; i < CM.getKeywordList().size();i++)//choose one keyword as a this conference topic
        {
            if (CM.getKeywordList().get(i).equals(""))
            {
                CM.getKeywordList().remove(CM.getKeywordList().get(i));
            }
            else
                System.out.println(i+1 +"."+CM.getKeywordList().get(i));
        }
        String option = sc.nextLine().trim();//add validations
        while(!isStringNumeric(option) || Integer.parseInt(option) >= CM.getKeywordList().size() || Integer.parseInt(option) < 0)
        {
            System.out.println("Please input the correct number");
            option = sc.nextLine().trim();
        }
        String topic = CM.getKeywordList().get(Integer.parseInt(option));

        System.out.println("Please set the submission deadline for this conference, the format is (yyyy-MM-dd HH:mm:ss)");
        String subDate = sc.nextLine().trim();//set submission deadline for this conference
        while(!CM.isTimeUpToStandard(subDate))
        {
            System.out.println("Your time format is not correct, please enter again: ");
            subDate = sc.nextLine().trim();
        }

        System.out.println("Please set the review deadline for this conference, the format is (yyyy-MM-dd HH:mm:ss)");
        String revDate = sc.nextLine().trim();//set the review deadline for this conference
        while(!CM.isTimeUpToStandard(revDate))
        {
            System.out.println("Your time format is not correct, please enter again: ");
            revDate = sc.nextLine().trim();
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        while (dateFormat.parse(subDate).after(dateFormat.parse(revDate)))//submission deadline should not after the review deadline
        {
            System.out.println("Submission time should before the review time");
            revDate = sc.nextLine().trim();
        }
        Conference newConference = new Conference(name,title,topic,subDate,revDate);//create the conference object
        CM.getConferenceList().add(newConference);
        if(confirmChanges() == true)
            CM.writeConferenceToFile();//reload the database
        else
            return;

    }

    public void modifyConference(String email) throws ParseException
    {

        User chair = CM.searchUserByEmail(email);
        System.out.println("Hi"+" "+chair.getName()+","+"you are now logging in as a chair!");
        Scanner sc = new Scanner(System.in);
        for(Conference one : CM.getConferenceList())
            System.out.println( CM.getConferenceList().indexOf(one)+1 +"."+ one.toString());
        System.out.println("Please choose one conference to modify");
        String option = sc.nextLine().trim();
        while(!isStringNumeric(option) || Integer.parseInt(option) >= CM.getConferenceList().size() || Integer.parseInt(option) < 0)
        {
            System.out.println("Can not find the conference!");
            System.out.println("Please enter the number again: ");
            option = sc.nextLine().trim();

        }//let chair to choose one conference to modify
        for(Conference one:  CM.getConferenceList())
        {
            if (Integer.parseInt(option)-1 ==  CM.getConferenceList().indexOf(one))
            {
                System.out.println("Please choose which part to modify:");
                System.out.println("(1) conference name");
                System.out.println("(2) conference title");
                System.out.println("(3) conference topic");
                System.out.println("(4) conference submission deadline");
                System.out.println("(5) conference review deadline");
                String number = sc.nextLine().trim();
                while(!isStringNumeric(number)||Integer.parseInt(number) < 0||Integer.parseInt(number) >5)
                {
                    System.out.println("Please input the correct number");
                    number = sc.nextLine().trim();
                }
                switch (number) {
                    case "1":
                        System.out.print("Please input the new name:");
                        String newName = sc.nextLine().trim();
                        while(!isStringAlphabetic(newName))
                        {
                            System.out.println("Please input the correct name format:");
                            newName = sc.nextLine().trim();
                        }
                        one.setConName(newName);//new name, title and topic should not be null
                        chair.getConferenceListForChair().add(one);
                        //add this conference into chair's chair conference list
                        break;
                    case "2":
                        System.out.print("Please input the new title:");
                        String newTitle = sc.nextLine().trim();
                        while(isStringAlphabetic(newTitle)==false)
                        {
                            System.out.println("Please input the correct title format:");
                            newTitle = sc.nextLine().trim();
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
                                System.out.println(i+1 +"."+CM.getKeywordList().get(i));
                        }
                        String newTopic = sc.nextLine().trim();//from the existing keyword list to choose one keyword for this conference
                        while(!isStringNumeric(newTopic) || Integer.parseInt(newTopic) >= CM.getKeywordList().size() || Integer.parseInt(newTopic) < 0)
                        {
                            System.out.println("Please input the correct number");
                            newTopic = sc.nextLine().trim();
                        }

                        one.setConTitle(CM.getKeywordList().get(Integer.parseInt(newTopic)-1));
                        chair.getConferenceListForChair().add(one);
                        //add this conference into chair's chair conference list
                        break;
                    case "4":
                        System.out.print("Please input the new submission deadline,the format is (yyyy-MM-dd HH:mm:ss)");
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String newSubDeadline = sc.nextLine().trim();
                        while(!CM.isTimeUpToStandard(newSubDeadline)|| dateFormat.parse(newSubDeadline).after(dateFormat.parse(one.gatRevDate())))
                        {
                            System.out.println("Please input the correct submission deadline:");
                            newSubDeadline = sc.nextLine().trim();
                        }
                        one.setSubDate(newSubDeadline);
                        //some validation on deadline, its new submission time should not after the existing review deadline
                        chair.getConferenceListForChair().add(one);
                        //add this conference into chair's chair conference list
                        break;
                    case "5":
                        System.out.print("Please input the new review deadline,the format is (yyyy-MM-dd HH:mm:ss)");
                        String newRevDeadline = sc.nextLine().trim();
                        SimpleDateFormat dF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        while(!CM.isTimeUpToStandard(newRevDeadline) || dF.parse(one.getSubDate()).after(dF.parse(newRevDeadline)))
                        {
                            System.out.print("Please input the correct review deadline:");
                            newRevDeadline = sc.nextLine().trim();
                        }
                        //some validation on deadline, its existing  submission time should not after the new review deadline
                        one.setRevDate(newRevDeadline);
                        chair.getConferenceListForChair().add(one);
                        //add this conference into chair's chair conference list
                        break;
                    default:
                        System.out.println("Please input the correct number!");
                        break;

                }
                if(confirmChanges() == true)
                {
                    CM.writeUserToUserFile();
                    CM.writeConferenceToFile();
                }
                else
                    return;
                //reload the database
            }

        }

    }


    public void assignReviewer(String email) throws Exception
    {
        User chair = CM.searchUserByEmail(email);//create a chair object and let he/she choose the function
        System.out.println("Hi"+" "+chair.getName()+","+"you are now logging in as a chair!");
        System.out.println("**************************************");
        System.out.println("          Chair Management           ");
        System.out.println("**************************************");
        System.out.println("1.Assign reviewer by system");
        System.out.println("2.Assign reviewer manually");
        System.out.println("Please choose one function");
        Scanner sc = new Scanner(System.in);
        String option  = sc.nextLine().trim();
        while(!isStringNumeric(option)||(Integer.parseInt(option)!= 1 && Integer.parseInt(option)!= 2)||option.trim().equals(""))
        {
            System.out.println("You input an invalid number, please input again");
            option = sc.nextLine().trim();
        }
        switch (option)
        {
            case"1"://this choice is assign reviewers automatically
                ArrayList<Paper> couldAssignedPapers= new ArrayList<>();
                ArrayList<User> reviewerListAuto = new ArrayList<>();//find the reviewer list int the whole user list
                System.out.println("Please choose one paper to assign reviewer:");//find the non reviewed papers
                for(Paper p:CM.getPaperList())
                {
                    if(p.getStatus().equals("NO"))
                        couldAssignedPapers.add(p);
                }

                for(Paper nonAssignedPaper:couldAssignedPapers)
                {
                    System.out.println(couldAssignedPapers.indexOf(nonAssignedPaper)+1+"."+nonAssignedPaper.getName()+" "+"[Key word:]"+nonAssignedPaper.getStringListNames(nonAssignedPaper.getKeywords()));
                }

                String paperNumAuto = sc.nextLine().trim();
                while(!isStringNumeric(paperNumAuto)||Integer.parseInt(paperNumAuto) < 0||Integer.parseInt(paperNumAuto) > couldAssignedPapers.size()||paperNumAuto.trim().equals(""))
                {
                    System.out.println("Please input the correct number");
                    paperNumAuto = sc.nextLine().trim();
                }

                Paper paperAuto = couldAssignedPapers.get(Integer.parseInt(paperNumAuto)-1);//after choose the paper , create a paper object
                for(User u:CM.getUserList())
                {
                    if(u.getChooseType() == 2 && u!=null )
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
                    User userAuto = reviewerListAuto.get(i);//find the specific reviewer
                    if(paperAuto.getKeywords().contains(userAuto.getKeywords().get(0))
                            &&userAuto !=null &&!userAuto.getName().equals(paperAuto.getAuthor()))
                    //the reviewer could not be the author of this paper and the paper keywords will match the reviewer's strong expertises
                    {
                        if(userAuto != null)
                            paperAuto.getAssignedReviewerList().add(userAuto);
                        //paper add this reviewer into its assigned reviewer list
                    }
                    //find the specific reviewer and find the location in the user list
                    if(!userAuto.getName().equals(paperAuto.getAuthor())
                            &&CM.checkTwoArrayListHaveSameVariable(userAuto.getKeywords(),paperAuto.getKeywords())
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
                if(confirmChanges()==true)
                {
                    CM.writeUserToUserFile();
                    CM.writePaperToFile();
                    System.out.println("Add Successfully!");
                }
                //reload into database
                else
                    return;
                break;
            case"2":
                ArrayList<Paper> reviewPapers = new ArrayList<>();
                ArrayList<User>  reviewers = new ArrayList<>();
                System.out.println("Please choose one paper to assign");
                //clear the list to avoid the list does not contain empty elements
                for(Paper p:CM.getPaperList())
                {
                    if(p.getStatus().equals("NO"))
                        reviewPapers.add(p);

                }
                for(Paper revP:reviewPapers)
                {
                    System.out.println(reviewPapers.indexOf(revP)+1+"."+revP.getName()+" "+"[Key word:]"+revP.getStringListNames(revP.getKeywords()));
                }

                String paperNum = sc.nextLine().trim();
                while(!isStringNumeric(paperNum) || Integer.parseInt(paperNum) < 0 || Integer.parseInt(paperNum) > reviewPapers.size()||paperNum.trim().equals(""))
                {
                    System.out.println("You input an invalid number, please input again");
                    paperNum = sc.nextLine().trim();
                }
                Paper paperObject = reviewPapers.get(Integer.parseInt(paperNum) -1);
                //show the paper which could be assigned reviewer and after choosing create a paper object
                System.out.println("This is the reviewer list, please choose one to assign this paper");
                for(User u:CM.getUserList())
                {
                    if(u.getChooseType() == 2
                            && !u.getName().equals(paperObject.getAuthor())
                            && !CM.checkConferenceOverlaps(u.getConferenceListForChair(),CM.searchConference(paperObject.getConName()))
                    )//reviewer could not be the author of this paper
                    {
                        System.out.println(u.getName());
                        reviewers.add(u);
                    }
                }
                for(User reU:reviewers)
                {
                    System.out.println(reviewers.indexOf(reU)+1+"."+reU.getName()+" "+"[Keywords:]"+reU.getStringListNames(reU.getKeywords()));
                }
                //show who can review paper and show their keywords
                while(paperObject.getAssignedReviewerList().size() < 3)
                //assign 3 reviewers a time for a paper
                {
                    System.out.println("Please choose one reviewer:");
                    String number = sc.nextLine().trim();
                    while(!isStringNumeric(number)|| Integer.parseInt(number) < 0||Integer.parseInt(number)>reviewers.size()||number.trim().equals(""))
                    {
                        System.out.println("You input an invalid number, please input again");
                        number = sc.nextLine().trim();
                    }

                    for(User u: paperObject.getAssignedReviewerList())
                    {
                        if(paperObject.getAssignedReviewerList().size() > 0 && u != null)
                        {
                            while(u.getName().equals(reviewers.get(Integer.parseInt(number)-1).getName()))
                            {
                                System.out.println("You have already assign this reviewer,please choose again");
                                number = sc.nextLine().trim();
                            }
                        }

                    }//could not assign the same reviewer
                    while(!CM.checkTwoArrayListHaveSameVariable(reviewers.get(Integer.parseInt(number)-1).getKeywords(),paperObject.getKeywords()))
                    {
                        System.out.println("You choose the reviewer's keywords do not match the paper's key word, please choose again");
                        number = sc.nextLine().trim();
                        while(!isStringNumeric(number))
                        {
                            System.out.println("You input the invalid number, please choose again");
                            number = sc.nextLine().trim();

                        }
                    }//reviewers' key words should match the paper's key words

                    User reviewerObject =reviewers.get(Integer.parseInt(number)-1);
                    //create this reviewer object
                    paperObject.getAssignedReviewerList().add(reviewerObject);
                    //paper's reviewer list add this reviewer
                }
                for(User u:paperObject.getAssignedReviewerList())
                {
                    if(u != null){
                        u.getAssignedPaper().add(paperObject);
                        //reviewer add this paper into their assigned paper list
                        u.getConferenceListForReviewer().add(CM.searchConference(paperObject.getConName()));
                        //reviewer add this paper's conference into their reviewer conference list
                        sendMessage(u,chair);
                        //send every reviewer a message
                    }

                }
                if(CM.checkConferenceOverlaps(chair.getConferenceListForAuthor(),CM.searchConference(paperObject.getConName())) ==false
                        &&CM.checkConferenceOverlaps(chair.getConferenceListForReviewer(),CM.searchConference(paperObject.getConName()))==false)
                    chair.getConferenceListForChair().add(CM.searchConference(paperObject.getConName()));
                //ensure the chair conference list for chair is not overlap his other identities conference lists
                paperObject.setStatus("YES");
                //set the paper status to yes
                System.out.println("This paper have enough reviewers!");
                if(confirmChanges() == true)
                {
                    CM.writeUserToUserFile();
                    CM.writePaperToFile();
                }
                else
                    return;
                //reload into database
                break;
            default:
                System.out.println("Please input the correct number");
                break;

        }
    }

    public void sendMessage(User destination,User sources)
    {
        System.out.println("You are sending message to"+" "+destination.getName());
        Scanner sc = new Scanner(System.in);
        String content = sc.nextLine().trim() +"["+"From: "+sources.getName()+" "+"To:"+destination.getName()+"]";
        destination.getMessageBox().add(content);

    }

    public void makeDecision(String email)
    {
        User chair = CM.searchUserByEmail(email);// create the user object
        System.out.println("Hi"+" "+chair.getName()+","+"you are now logging in as an author!");
        System.out.println("**************************************");
        System.out.println("           Chair Management           ");
        System.out.println("**************************************");
        if(chair.getMessageBox().size()==0)
            System.out.println("You have no message");
        System.out.println("==================<Message Box>=====================");
        if(chair.getMessageBox().size()>0)
        {

            for(String s: chair.getMessageBox())
                System.out.println(chair.getMessageBox().indexOf(s)+1+"."+s);

        }
        System.out.println("==================<Message Box>=====================");

        ArrayList<Paper> couldMakeDecision = new ArrayList<>();
        for(Paper p: CM.getPaperList())
        {
            if(p.getEvaluation().size() > 0)
                couldMakeDecision.add(p);
        }
        System.out.println("This is the evaluation for this paper and you can make decision for it");
        System.out.println("==================<Paper List>=====================");
        for(Paper P:couldMakeDecision)
            System.out.println(couldMakeDecision.indexOf(P)+1+"."+P.getName());
        System.out.println("==================<Paper List>=====================");
        System.out.println("Please input your choice");
        Scanner sc =new Scanner(System.in);
        String option = sc.nextLine().trim();
        while(!isStringNumeric(option)||Integer.parseInt(option)>couldMakeDecision.size()||Integer.parseInt(option)<0||option.trim().equals(""))
        {
            System.out.println("You input the invalid number, please input again");
            option = sc.nextLine().trim();
        }
        Paper paperObject = couldMakeDecision.get(Integer.parseInt(option)-1);
        System.out.println("These are the evaluations");
        System.out.println("==================<Evaluation>=====================");
        for(String s: paperObject.getEvaluation())
            System.out.println(paperObject.getEvaluation().indexOf(s)+1+"."+s);
        System.out.println("==================<Evaluation>=====================");
        System.out.println("Please choose your decision for this paper");
        System.out.println("1.Accept");
        System.out.println("2.Reject");
        String decision = sc.nextLine().trim();
        while(!isStringNumeric(decision)||(Integer.parseInt(decision)!=1 && Integer.parseInt(decision)!=2)||decision.trim().equals(""))
        {
            System.out.println("You input an invalid number, please input again");
            decision = sc.nextLine().trim();
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
        if(confirmChanges() ==true)
            CM.writePaperToFile();
        else
            return;

    }

    public void authorFunction(String email) throws Exception
    {

        menu.displayAuthorMenu();
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter your option: ");
        String option = scan.nextLine().trim();
        switch (option)
        {
            case "1":
                submitPaper(email);
                //provide key words for paper
                break;
            default:
                System.out.println("Please input the correct number!");
                break;
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
        System.out.println("Please choose a conference to submit paper:");
        for(Conference c: CM.getConferenceList())
            System.out.println(CM.getConferenceList().indexOf(c)+"."+c.getConName());
        Scanner sc = new Scanner(System.in);
        System.out.println("Please Choose a Conference: ");
        String option  = sc.nextLine().trim();
        while(isStringAlphabetic(option)|| Integer.parseInt(option) < 0)
        {
            System.out.println("Please input the correct number:");
            option =sc.nextLine().trim();
        }
        while(CM.checkConferenceOverlaps(author.getConferenceListForChair(),CM.getConferenceList().get(Integer.parseInt(option)))==true
                ||CM.checkConferenceOverlaps(author.getConferenceListForReviewer(),CM.getConferenceList().get(Integer.parseInt(option)))==true)
        {
            System.out.println("You can not submit paper in this conference, because you have another identity in this conference");
            option =sc.nextLine().trim();
        }
        Conference conferenceObject = CM.getConferenceList().get(Integer.parseInt(option));
        //show the conference list and let the author to choose one conference to submit paper.
        System.out.println("The submission deadline for conference: " + conferenceObject.getSubDate());
        if(d.after(sdf.parse(conferenceObject.getSubDate())))
        {
            System.out.println("You can not submit the paper in this current time");
            //show the submission deadline for this conference, if the submission time is after this deadline, this paper will be rejected.
            return;
        }
        System.out.println("Please enter the paper name");
        //in put the specific paper format
        String paperName = sc.nextLine().trim();
        while(!isStringAlphabetic(paperName)&&!paperName.trim().equals(""))
        {
            System.out.println("You input an invalid name,please input again");
            paperName = sc.nextLine().trim();
        }
        Paper paperObject = createAPaper(paperName,conferenceObject,author);
        System.out.println("This file's path is: <"+ paperObject.getFilePath() + "> the system will use it to check the file format!");
        if(!CM.validFile( paperObject.getFilePath()))
        {
            System.out.println("The file should only be .pdf or .docx, it could not be submitted!");
            return;//some validation for this paper format
        }

        //choose 3 keywords for this author key word list
        System.out.println("Please input three keywords");
        String selectKeywords = "";
        while (paperObject.getKeywords().size() < 3)
        {
            menu.displayKeywordsMenu();
            selectKeywords = sc.nextLine().trim();
            keywordSelectionForPaper(paperObject, selectKeywords, sc);
        }





        CM.getPaperList().add(paperObject);
        //add the paper to paper list

        author.getConferenceListForAuthor().add(conferenceObject);
        //add the relevant conference to this author's chair conference list
        author.getSubmittedPaper().add(paperObject);
        //add this paper to this author's submitted paper list
        if(confirmChanges()==true){
            CM.writePaperToFile();
            CM.writeUserToUserFile();
            //reload this new adding info into user and paper database
            System.out.println("Submit the paper successfully");
        }
        else
            return;
    }


    public Paper createAPaper(String name,Conference conference,User user) throws IOException
    //create a paper for checking the format
    {
        Scanner sc= new Scanner(System.in);
        System.out.println("Please offer the file path");
        String filePath = sc.nextLine().trim();
        Paper newPaper = new Paper(name,conference.getSubDate(),conference.gatRevDate(),"NO",user.getName(),"null",conference.getConName(),filePath);
        return newPaper;

    }

    //0519 changing part
    public void reviewerFunctions(String email) throws Exception
    {
        menu.displayReviewerMenu();
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter your option: ");
        String option = scan.nextLine().trim();
        switch (option)
        {
            case "1":
                specifyKeywords(email);
                //specify expertise keywords
                break;
            case "2":
                writeEvaluation(email); //get the paper
                //write evaluation
                break;
            case "3":
                break;
            default:
                System.out.println("You should choose 1 - 3");
        }


    }


    public void keywordSelection(User user, String selection, Scanner sc)
    {

        switch (selection)
        {
            case "1":
                if (confirmChanges())
                {
                    user.getKeywords().add("Information Technology");
                    System.out.println("Information Technology have added to your keywords");

                } else
                    System.out.println("Cancel the change");
                break;
            case "2":
                if (confirmChanges()) {
                    user.getKeywords().add("Cyber Security");
                    System.out.println("Cyber Security have added");
                } else
                    System.out.println("Cancel the change");
                break;
            case "3":
                if (confirmChanges()) {
                    user.getKeywords().add("Cloud Computing");
                    System.out.println("Cloud Computing have added");
                } else
                    System.out.println("Cancel the change");
                break;
            case "4":
                if (confirmChanges()) {
                    user.getKeywords().add("Network Develop");
                    System.out.println("Network Develop have added");
                } else
                    System.out.println("Cancel the change");
                break;
            case "5":
                if (confirmChanges()) {
                    user.getKeywords().add("Software Engineering");
                    System.out.println("Software Engineering have added");
                } else
                    System.out.println("Cancel the change");
                break;
            case "6":
                if (confirmChanges()) {
                    user.getKeywords().add("Distributed Mobile Develop");
                    System.out.println("Distributed Mobile Develop have added");
                } else
                    System.out.println("Cancel the change");
                break;
            case "7":
                if (confirmChanges()) {
                    user.getKeywords().add("Database");
                    System.out.println("Database have added");
                } else
                    System.out.println("Cancel the change");
                break;
            case "8":
                if (confirmChanges()) {
                    user.getKeywords().add("Big Data");
                    System.out.println("Big Data have added");
                } else
                    System.out.println("Cancel the change");
                break;
            case "9":
                if (confirmChanges()) {
                    user.getKeywords().add("User Interface Design");
                    System.out.println("User Interface Design have added");
                } else
                    System.out.println("Cancel the change");
                break;
            case "10":
                System.out.println("Please input your keywords (separate with comma,format: keyword,keyword,keyword,... )");
                String[] arrayKeywords = sc.nextLine().trim().split(",");
                String inputKeywords = Arrays.toString(arrayKeywords);
                String keywords = inputKeywords.replaceAll("^.*\\[", "").replaceAll("].*", "");
                keywords = keywords.replace(" ", "");
                System.out.println("You have enter the keywords: " + keywords);
                if (keywords.matches("^([a-zA-Z]{1,40},)*[a-zA-Z]{1,40}$") && arrayKeywords.length >= 3)
                {
                    //keyword should not repeat
                    if (confirmChanges())
                    {
                        for (int i = 0; i < arrayKeywords.length; i++)
                        {

                            user.getKeywords().add(arrayKeywords[i]);
                            System.out.println("Keywords: " + arrayKeywords[i] + " added successfully");

                        }
                    }
                    else
                        System.out.println("Cancel the change");
                }
                else
                    System.out.println("Input format should be: keyword,keyword,keyword,... ");
                break;
            default:
                System.out.println("Please choose 1 - 10");
                break;

        }
    }

    //specify the keywords for reviewer
    public void specifyKeywords(String email)
    {
        User currentUser = CM.searchUserByEmail(email);

        System.out.println("Before specify: You have " + currentUser.getKeywords().size() + " Keywords" + "\n" +
                "You need to have at least three keywords");


        Scanner sc = new Scanner(System.in);

        String selectKeywords = "";

        if (currentUser.getKeywords().size() < 3) {
            //select keywords for 3 times
            while (currentUser.getKeywords().size() < 3)
            {
                menu.displayKeywordsMenu();
                selectKeywords = sc.nextLine().trim();
                keywordSelection(currentUser, selectKeywords, sc);

            }
        }
        // if user already have three keywords then select keyword for one time
        else
        {
            menu.displayKeywordsMenu();
            selectKeywords = sc.nextLine();
            keywordSelection(currentUser, selectKeywords, sc);
        }
        HashSet<String> noneRepeatKeyworList = new HashSet<String>();
        noneRepeatKeyworList.addAll(currentUser.getKeywords());
        currentUser.getKeywords().clear();
        currentUser.getKeywords().addAll(noneRepeatKeyworList);

        System.out.println("Your current keywords: ");
        for (int i = 0; i < currentUser.getKeywords().size(); i++)
        {
            System.out.println("\n" + currentUser.getKeywords().get(i));
        }

        System.out.println("\n" + "Please select your strong expertise keyword ");
        String strongExpertise = "";
        strongExpertise = sc.nextLine().trim();

        while (!isStringAlphabetic(strongExpertise) || strongExpertise.trim().isEmpty())
        {
            System.out.println("Invalid inputs, pleasae try again");
            strongExpertise = sc.nextLine().trim();
        }
        if (currentUser.getKeywords().contains(strongExpertise) && isStringAlphabetic(strongExpertise))
        {
            int indexOfStrongKeyword = returnIndex(currentUser.getKeywords(), strongExpertise);
            System.out.println(indexOfStrongKeyword);
            String topKeyword = currentUser.getKeywords().get(0);
            currentUser.getKeywords().set(indexOfStrongKeyword, topKeyword);
            currentUser.getKeywords().set(0, strongExpertise);

        }
        if (!currentUser.getKeywords().contains(strongExpertise))
            System.out.println("Keyword not exist in your list");


        System.out.println("After specify: You have " + currentUser.getKeywords().size() + " Keywords");
        if (confirmChanges() == true)
        {
            CM.writeUserToUserFile();
        }
    }


    public void writeEvaluation(String email) throws ParseException
    {
        User userReviewer = CM.searchUserByEmail(email);
        System.out.println("Hi"+" "+userReviewer.getName()+","+"you are now logging in as a reviewer!");
        System.out.println("**************************************");
        System.out.println("          Reviewer Management         ");
        System.out.println("**************************************");
        Date currentTime = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ArrayList<Paper> assignedPaper = userReviewer.getAssignedPaper();
        ArrayList<String> messages = userReviewer.getMessageBox();

        System.out.println("Hello," +userReviewer.getName()+" this is your massages");
        if(messages.size() == 0)
        {
            System.out.println("You do not have any message!");
            return;
        }
        System.out.println("==================<Message Box>=====================");
        if(messages.size()>0)
        {
            for(String s: messages)
            {
                ;
                System.out.println(messages.indexOf(s)+1+"."+s);

            }
        }
        System.out.println("==================<Message Box>=====================");

        if(assignedPaper.size()==0||assignedPaper.get(0)==null)
        {
            System.out.println("You have no assigned paper now");
            return;
        }
        if(assignedPaper.size()>0)
        {
            System.out.println("This is your assigned paper, please choose one to review");
            for(Paper p:assignedPaper)
                if(!sdf.parse(p.getRmDeadline()).before(currentTime))
                    System.out.println(assignedPaper.indexOf(p)+1+"."+p.getName());

        }
        Scanner sc = new Scanner(System.in);
        String choose = sc.nextLine().trim();
        while(isStringAlphabetic(choose)||Integer.parseInt(choose)<0||Integer.parseInt(choose)>assignedPaper.size())
        {
            System.out.println("You input the invalid number,please input again");
            choose = sc.nextLine().trim();
        }
        Paper paperObject = assignedPaper.get(Integer.parseInt(choose)-1);
        System.out.println("Please enter your evaluation for this paper");
        String content = sc.nextLine().trim();
        while(content.trim().equals(""))
        {
            System.out.println("You input nothing, please input again");
            content= sc.nextLine().trim();
        }
        paperObject.getEvaluation().add(content);
        userReviewer.getAssignedPaper().remove(paperObject);
        System.out.println("Set the evaluation for this paper successfully, and you will send message to chair");
        System.out.println("Here is your message box again, please choose one to send message");
        for(String s: messages)
        {
            System.out.println(messages.indexOf(s)+1+"."+s);

        }
        System.out.println("Enter one sender name:");
        String chairName = sc.nextLine().trim();
        while(CM.searchUser(chairName) == null)
        {
            System.out.println(chairName+" does not exist!");
            chairName =sc.nextLine().trim();
        }
        sendMessage(CM.searchUser(chairName),userReviewer);
        for(int i = 0;i<messages.size();i++)
        {
            if(messages.get(i).contains(chairName))
                userReviewer.getMessageBox().remove(messages.get(i));
        }
        System.out.println("Send successfully");
        if(confirmChanges()==true)
        {
            CM.writePaperToFile();
            CM.writeUserToUserFile();
        }
        else
            return;



    }

    public void retrieveUser(){
        String option;
        int numOption;
        Scanner scan = new Scanner(System.in);
        for (User user : CM.getUserList()){
            System.out.println("(" + user.getID() + ") " + user.getName());
        }
        System.out.print("Please choose a user: ");
        option = scan.nextLine().trim();

        while(isStringAlphabetic(option) || Integer.parseInt(option) > CM.getUserList().size() || Integer.parseInt(option) < 1){
            System.out.print("You should input a number and choose a user from the list. Please enter again: ");
            option = scan.nextLine().trim();
        }

        numOption = Integer.parseInt(option);
        System.out.println("\nEmail: " + CM.getUserList().get(numOption - 1).getEmail());
        System.out.println("Password: " + CM.getUserList().get(numOption - 1).getPsw());
        System.out.println("Mobile Number: " + CM.getUserList().get(numOption - 1).getMobileNumber());
        System.out.println("Employer Detail: " + CM.getUserList().get(numOption - 1).getEmployerDetail());
        System.out.println("High Qualification: " + CM.getUserList().get(numOption - 1).getHighQualification());
        System.out.println("Occupation: " + CM.getUserList().get(numOption - 1).getOccupation());
        System.out.println("Interest Area: " + CM.getUserList().get(numOption - 1).getInterestArea());
        System.out.println("Keywords: " + CM.getUserList().get(numOption - 1).getKeywords());
    }

    public void retrieveConference(){
        String option;
        int numOption;
        int i = 1;
        Scanner scan = new Scanner(System.in);
        for (Conference con : CM.getConferenceList()){
            System.out.println("(" + i + ") " + con.getConName());
            i ++;
        }
        System.out.print("Please choose a conference: ");
        option = scan.nextLine().trim();
        while(isStringAlphabetic(option) || Integer.parseInt(option) > CM.getConferenceList().size() || Integer.parseInt(option) < 1){
            System.out.print("You should input a number and choose a conference from the list. Please enter again: ");
            option = scan.nextLine().trim();
        }

        numOption = Integer.parseInt(option);
        System.out.println("\nTitle: " + CM.getConferenceList().get(numOption - 1).getConTitle());
        System.out.println("Topic: " + CM.getConferenceList().get(numOption - 1).getConTopic());
        System.out.println("Submission Deadline: " + CM.getConferenceList().get(numOption - 1).getSubDate());
        System.out.println("Review deadline: " + CM.getConferenceList().get(numOption - 1).gatRevDate());

    }

    public void retrievePaper(){
        String option;
        int numOption;
        int i = 1;
        Scanner scan = new Scanner(System.in);
        for (Paper pap : CM.getPaperList()){
            System.out.println("(" + i + ") " + pap.getName());
            i ++;
        }
        System.out.print("Please choose a paper: ");
        option = scan.nextLine().trim();
        while(isStringAlphabetic(option) || Integer.parseInt(option) > CM.getPaperList().size() || Integer.parseInt(option) < 1){
            System.out.print("You should input a number and choose a paper from the list. Please enter again: ");
            option = scan.nextLine().trim();
        }

        numOption = Integer.parseInt(option);
        System.out.println("\nAuthor: " + CM.getPaperList().get(numOption - 1).getAuthor());
        System.out.println("Conference: " + CM.getPaperList().get(numOption - 1).getConName());
        System.out.println("Keywords: " + CM.getPaperList().get(numOption - 1).getKeywords());
        System.out.println("Submission Deadline: " + CM.getPaperList().get(numOption - 1).getSmDeadline());
        System.out.println("Review Deadline: " + CM.getPaperList().get(numOption - 1).getRmDeadline());
        System.out.println("Status: " + CM.getPaperList().get(numOption - 1).getStatus());
        System.out.println("Decision: " + CM.getPaperList().get(numOption - 1).getDecision());
        System.out.println("Reviewer: " + CM.getPaperList().get(numOption - 1).getAssignedReviewerList());
        System.out.println("Evaluation: " + CM.getPaperList().get(numOption - 1).getEvaluation());
    }

    public boolean confirmChanges()
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("Do you want to save your changes?");
        System.out.println("Enter yes or no");
        String change = sc.nextLine().trim().toLowerCase();
        while (!change.equals("yes") && !change.equals("no"))
        {
            System.out.println("Please enter yes or no");
            change = sc.nextLine().trim().toLowerCase();
        }
        if (change.equals("yes"))
            return true;
        else
            return false;

    }

    //return the index of the input of Arraylist
    public int returnIndex(ArrayList<String> kList, String input)
    {
        int i = -1;

        for (String sIndex: kList)
        {
            if (input.equals(sIndex))
            {
                i = kList.indexOf(sIndex);
                return i;
            }
        }
        return i;
    }

    public void keywordSelectionForPaper(Paper paper, String selection, Scanner sc)
    {

        switch (selection)
        {
            case "1":
                if (confirmChanges())
                {
                    paper.getKeywords().add("Information Technology");
                    System.out.println("Information Technology have added to your keywords");

                } else
                    System.out.println("Cancel the change");
                break;
            case "2":
                if (confirmChanges()) {
                    paper.getKeywords().add("Cyber Security");
                    System.out.println("Cyber Security have added");
                } else
                    System.out.println("Cancel the change");
                break;
            case "3":
                if (confirmChanges()) {
                    paper.getKeywords().add("Cloud Computing");
                    System.out.println("Cloud Computing have added");
                } else
                    System.out.println("Cancel the change");
                break;
            case "4":
                if (confirmChanges()) {
                    paper.getKeywords().add("Network Develop");
                    System.out.println("Network Develop have added");
                } else
                    System.out.println("Cancel the change");
                break;
            case "5":
                if (confirmChanges()) {
                    paper.getKeywords().add("Software Engineering");
                    System.out.println("Software Engineering have added");
                } else
                    System.out.println("Cancel the change");
                break;
            case "6":
                if (confirmChanges()) {
                    paper.getKeywords().add("Distributed Mobile Develop");
                    System.out.println("Distributed Mobile Develop have added");
                } else
                    System.out.println("Cancel the change");
                break;
            case "7":
                if (confirmChanges()) {
                    paper.getKeywords().add("Database");
                    System.out.println("Database have added");
                } else
                    System.out.println("Cancel the change");
                break;
            case "8":
                if (confirmChanges()) {
                    paper.getKeywords().add("Big Data");
                    System.out.println("Big Data have added");
                } else
                    System.out.println("Cancel the change");
                break;
            case "9":
                if (confirmChanges()) {
                    paper.getKeywords().add("User Interface Design");
                    System.out.println("User Interface Design have added");
                } else
                    System.out.println("Cancel the change");
                break;
            case "10":
                System.out.println("Please input your keywords (separate with comma,format: keyword,keyword,keyword,... )");
                String[] arrayKeywords = sc.nextLine().trim().split(",");
                String inputKeywords = Arrays.toString(arrayKeywords);
                String keywords = inputKeywords.replaceAll("^.*\\[", "").replaceAll("].*", "");
                keywords = keywords.replace(" ", "");
                System.out.println("You have enter the keywords: " + keywords);
                if (keywords.matches("^([a-zA-Z]{1,40},)*[a-zA-Z]{1,40}$") && arrayKeywords.length >= 3)
                {
                    //keyword should not repeat
                    if (confirmChanges())
                    {
                        for (int i = 0; i < arrayKeywords.length; i++)
                        {

                            paper.getKeywords().add(arrayKeywords[i]);
                            System.out.println("Keywords: " + arrayKeywords[i] + " added successfully");

                        }
                    }
                    else
                        System.out.println("Cancel the change");
                }
                else
                    System.out.println("Input format should be: keyword,keyword,keyword,... ");
                break;
            default:
                System.out.println("Please choose 1 - 10");
                break;

        }
    }



    public static void main(String[] args) throws Exception
    {
        CMS cms = new CMS();
        cms.makeDecision("Joyce@monash.edu");



    }
}
