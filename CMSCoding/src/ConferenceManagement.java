import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConferenceManagement {

    private ArrayList<User> userList;
    private ArrayList<Conference> conferenceList;
    private ArrayList<Paper> paperList;
    private ArrayList<String> keywordList;
    public ConferenceManagement()
    {
        userList = new ArrayList<>();
        conferenceList = new ArrayList<>();
        paperList = new ArrayList<>();
        keywordList = new ArrayList<>();

    }

    public void readFromFile () throws Exception
    {
        try  //read conference from database
        {
            FileReader inputFile = new FileReader("src/conference.txt");
            try {
                Scanner parser = new Scanner(inputFile);
                while (parser.hasNextLine()) {
                    String line = parser.nextLine();
                    String[] values = line.split(",");
                    String name = values[0];
                    String title = values[1];
                    String topic = values[2];
                    String submission = values[3];
                    String review = values[4];
                    Conference c = new Conference(name, title, topic, submission, review);
                    conferenceList.add(c);
                }
            } finally {
                inputFile.close();
            }
        } catch (FileNotFoundException exception) {
            System.out.println("file not found");
        } catch (IOException exception) {
            System.out.println("Unexpected I/O exception occurs");
        }

        //read paper from paper file
        try {
            FileReader inputFile = new FileReader("src/paper.txt");
            try {
                Scanner parser = new Scanner(inputFile);
                while (parser.hasNextLine()) {
                    String line = parser.nextLine();
                    String[] values = line.split(",");
                    String Name = values[0];
                    String smDeadline = values[1];
                    String rmDeadline = values[2];
                    String Status = values[3];
                    String Author = values[4];
                    String Keyword = values[5];
                    String Decision = values[6];
                    String conName = values[7];
                    Paper p = new Paper(Name, smDeadline, rmDeadline, Status, Author, Keyword, Decision, conName);
                    if(values.length >8)
                        p.setStatus("YES");
                    if(values.length == 9)
                        p.getAssignedReviewerList().add(searchUser(values[8]));
                    if(values.length == 10)
                    {
                        p.getAssignedReviewerList().add(searchUser(values[8]));
                        p.getAssignedReviewerList().add(searchUser(values[9]));
                    }
                    if(values.length == 11)
                    {
                        p.getAssignedReviewerList().add(searchUser(values[8]));
                        p.getAssignedReviewerList().add(searchUser(values[9]));
                        p.getAssignedReviewerList().add(searchUser(values[10]));
                    }

                    paperList.add(p);
                }
            } finally {
                inputFile.close();
            }
        } catch (FileNotFoundException exception) {
            System.out.println("file not found");
        } catch (IOException exception) {
            System.out.println("Unexpected I/O exception occurs");
        }


        try//read user from user file and collect their relevant list
        {
            FileReader inputFile = new FileReader("src/userT.txt");
            try {
                Scanner parser = new Scanner(inputFile);

                String allLines = "";
                while (parser.hasNextLine()) {
                    String line = parser.nextLine();
                    allLines += line + "\n";
                }
                String[] blocks = allLines.split("\n\n");
                Pattern p = Pattern.compile("\\{(.*?)}");
                for (int i = 0; i < blocks.length; i++)
                {
                    Matcher m = p.matcher(blocks[i]);
                    ArrayList<String> cleaM = new ArrayList<>();
                    while (m.find())
                    {
                        cleaM.add(m.group().replace("{", "").replace("}", ""));
                    }
                    //Add user information in user list
                    String[] userInfo = cleaM.get(0).split(",");
                    userList.add(new User(Integer.parseInt(userInfo[0]), userInfo[1], userInfo[2], Integer.parseInt(userInfo[3]), userInfo[4],
                            userInfo[5], userInfo[6], userInfo[7], userInfo[8], userInfo[9]));

                    //conference list for chair for specific user
                    String[] conChair = cleaM.get(1).split(",");
                    for(int j = 0; j < conChair.length;j++)
                    {
                        if(!conChair[j].equals(null))
                            userList.get(i).getConferenceListForChair().add(searchConference(conChair[j]));

                    }
                    //conference list for author for specific user
                    String[] conAuthor = cleaM.get(2).split(",");
                    for(int j = 0; j < conAuthor.length;j++)
                    {
                        if(!conAuthor[j].equals(null))
                            userList.get(i).getConferenceListForAuthor().add(searchConference(conAuthor[j]));
                    }
                    //conference list from reviewer for specific user
                    String[] conReviewer = cleaM.get(3).split(",");
                    for(int j = 0; j < conReviewer.length;j++)
                    {
                        if(!conReviewer[j].equals(null))
                            userList.get(i).getConferenceListForReviewer().add(searchConference(conReviewer[j]));
                    }
                    //key word list for specific user
                    String[] keywords = cleaM.get(4).split(",");
                    for(int j = 0; j < keywords.length;j++)
                    {
                        userList.get(i).getKeywords().add(keywords[j]);
                        if(!isRepeat(keywords[j]))
                            keywordList.add(keywords[j]);

                    }
                    //add assigned paper for specific user
                    String[] assignedPapers = cleaM.get(5).split(",");
                    for(int j = 0; j < assignedPapers.length;j++)
                    {
                        if(!assignedPapers[j].equals(null))
                            userList.get(i).getAssignedPaper().add(searchPaper(assignedPapers[j]));
                    }

                    //add submitted paper for specific user
                    String[] submittedPapers = cleaM.get(6).split(",");
                    for(int j = 0; j < submittedPapers.length;j++)
                    {
                        if(!submittedPapers[j].equals(null))
                            userList.get(i).getSubmittedPaper().add(searchPaper(submittedPapers[j]));

                    }
                    //add message to their messageBox
                    String[] messages = cleaM.get(7).split(",");
                    for(int j = 0; j < messages.length;j++)
                    {
                        if(!messages[j].equals(null))
                            userList.get(i).getMessageBox().add(messages[j]);
                    }


                }

            } finally
            {
                inputFile.close();
            }
        } catch (FileNotFoundException exception)
        {
            System.out.println("file not found");
        } catch (IOException exception)
        {
            System.out.println("Unexpected I/O exception occurs");
        }
    }


    public boolean isRepeat(String s)
    {
        for(String str:keywordList)
        {
            if(s.equals(str))
                return true;
        }

        return false;
    }

    public User searchUser(String name)
    {
        for(User u:userList)
            if(name.equals(u.getName()))
                return u;

        return null;
    }

    public Paper searchPaper(String name)
    {
        for(Paper p: paperList)
            if(name.equals(p.getName()))
                return p;

        return null;
    }

    public Conference searchConference(String name)
    {
        for(Conference c:conferenceList)
            if(name.equals(c.getConName()))
                return c;

        return null;

    }


    public void clearList(){
        System.out.println("1. User list ");
        System.out.println("2. Conference list ");
        System.out.println("3. Paper list ");
        System.out.print("Choose a list you want to clear: ");
        Scanner scan = new Scanner(System.in);
        String option = scan.nextLine();
        switch (option)
        {
            case "1": userList.clear(); break;
            case "2": conferenceList.clear(); break;
            case "3": paperList.clear(); break;
        }

    }

    public void addUser(User newUser){
        userList.add(newUser);
    }

    public void addConference() throws ParseException
    {

        System.out.print("Please input the conference name:");
        Scanner sc = new Scanner(System.in);
        String name = sc.nextLine();
        while (!isInputUpToFormat(name)) // check whether the name is allowed.
        {
            System.out.println("The name cannot be null and should only be alphabetic.");
            System.out.print("Enter the conference name: ");
            name = sc.nextLine();
        }
        System.out.print("Please input the conference title:");
        String title = sc.nextLine();
        while (!isInputUpToFormat(title)) // check whether the name is allowed.
        {
            System.out.println("The title cannot be null and should only be alphabetic.");
            System.out.print("Enter the conference title: ");
            title = sc.nextLine();
        }
        System.out.println("Please choose the conference topic from the keywordList(Here is the keyword list):");
        for(int i = 0; i < keywordList.size();i++)
        {
            if (keywordList.get(i).equals(""))
            {
                keywordList.remove(keywordList.get(i));
            }
            else
                System.out.println(i+"."+keywordList.get(i));
        }
        String option = sc.nextLine();//add validations
        while(isStringAlphabetic(option) || Integer.parseInt(option) >= keywordList.size() || Integer.parseInt(option) < 0)
        {
            System.out.println("Please input the correct number");
            option = sc.nextLine();
        }
        String topic = keywordList.get(Integer.parseInt(option));

        System.out.println("Please set the submission deadline for this conference, the format is (yyyy-MM-dd HH:mm:ss)");
        String subDate = sc.nextLine();
        while(!isTimeUpToStandard(subDate))
        {
            subDate = sc.nextLine();
        }

        System.out.println("Please set the review deadline for this conference, the format is (yyyy-MM-dd HH:mm:ss)");
        String revDate = sc.nextLine();
        while(!isTimeUpToStandard(revDate))
        {
            revDate = sc.nextLine();
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        while (dateFormat.parse(subDate).after(dateFormat.parse(revDate)))
        {
            System.out.println("Submission time should before the review time");
            revDate = sc.nextLine();
        }
        Conference newConference = new Conference(name,title,topic,subDate,revDate);
        conferenceList.add(newConference);

    }

    public void sendMessage(User u)
    {
        System.out.println("You are sending message to"+" "+u.getName()+"(Please end with the sender name)");
        Scanner sc = new Scanner(System.in);
        String content = sc.nextLine();
        u.getMessageBox().add(content);

    }


    public ArrayList<User> getUserList(){
        return userList;
    }

    public ArrayList<Conference> getConferenceList(){
        return conferenceList;
    }

    public ArrayList<Paper> getPaperList(){
        return paperList;
    }

    public ArrayList<String> getKeywordList() {
        return keywordList;
    }

    public void modifyConference() throws ParseException {
        // change part
        Scanner sc = new Scanner(System.in);
        for(Conference one : conferenceList)
            System.out.println(conferenceList.indexOf(one)+"."+ one.toString());
        System.out.println("Please choose one conference to modify");
        String option = sc.nextLine();
        while(isInputUpToFormat(option) || Integer.parseInt(option) >= conferenceList.size() || Integer.parseInt(option) < 0)
        {
            System.out.println("Can not find the conference!");
            System.out.println("Please enter the number again: ");
            option = sc.nextLine();

        }
        for(Conference one: conferenceList)
        {
            if (Integer.parseInt(option) == conferenceList.indexOf(one))
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
                        while(!isInputUpToFormat(newName))
                        {
                            System.out.println("Please input the correct name format:");
                            sc.nextLine();
                        }
                        one.setConName(newName);
                        break;
                    case "2":
                        System.out.print("please input the new title:");
                        String newTitle = sc.nextLine();
                        while(!isInputUpToFormat(newTitle))
                        {
                            System.out.println("Please input the correct title format:");
                            sc.nextLine();
                        }
                        one.setConTitle(newTitle);
                        break;
                    case "3":
                        System.out.println("Please choose a new topic for this conference");
                        for(int i = 0; i < keywordList.size();i++)
                        {
                            if (keywordList.get(i).equals(""))
                            {
                                keywordList.remove(keywordList.get(i));
                            }
                            else
                                System.out.println(i+"."+keywordList.get(i));
                        }
                        String newTopic = sc.nextLine();
                        while(isStringAlphabetic(newTopic) || Integer.parseInt(newTopic) >= keywordList.size() || Integer.parseInt(newTopic) < 0)
                        {
                            System.out.println("Please input the correct number");
                            newTopic = sc.nextLine();
                        }

                        one.setConTitle(keywordList.get(Integer.parseInt(newTopic)));
                        break;
                    case "4":
                        System.out.print("please input the new submission deadline,the format is (yyyy-MM-dd HH:mm:ss)");
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String newSubDeadline = sc.nextLine();
                        while(!isTimeUpToStandard(newSubDeadline)|| dateFormat.parse(newSubDeadline).after(dateFormat.parse(one.gatRevDate())))
                        {
                            System.out.println("Please input the correct submission deadline:");
                            newSubDeadline = sc.nextLine();
                        }
                        one.setSubDate(newSubDeadline);
                        break;
                    case "5":
                        System.out.print("please input the new review deadline,the format is (yyyy-MM-dd HH:mm:ss)");
                        String newRevDeadline = sc.nextLine();
                        SimpleDateFormat dF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        while(!isTimeUpToStandard(newRevDeadline) || dF.parse(one.getSubDate()).after(dF.parse(newRevDeadline)))
                        {
                            System.out.print("please input the correct review deadline:");
                            newRevDeadline = sc.nextLine();
                        }
                        one.setRevDate(newRevDeadline);
                        break;
                    case "6":
                        conferenceList.remove(one);
                        System.out.println("Successfully delete!");
                        break;
                    default:
                        System.out.println("please input the correct number!");
                        break;

                }
            }

        }

    }


    public void assignReviewer(String name) throws Exception {
        readFromFile();
        User chair = searchUser(name);
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
        }
        switch (option)
        {
            case"0":
                ArrayList<User> reviewerListAuto = new ArrayList<>();
                System.out.println("Please choose one paper to assign reviewer:");
                for(Paper p:paperList)
                {
                    if(p.getStatus().equals("NO"))
                        System.out.println(paperList.indexOf(p)+"."+p.getName()+" "+"[Key word:]"+p.getKeyword());
                }

                String paperNumAuto = sc.nextLine();
                while(isStringAlphabetic(paperNumAuto)||Integer.parseInt(paperNumAuto) < 0)
                {
                    System.out.println("Please input the correct number");
                    paperNumAuto = sc.nextLine();
                }
                Paper paperAuto = paperList.get(Integer.parseInt(paperNumAuto));
                for(User u:userList)
                {
                    if(u.getChooseType() == 2)
                        reviewerListAuto.add(u);
                }
                int i = 0;
                while(paperAuto.getAssignedReviewerList().size() < 3 && i < reviewerListAuto.size())
                {
                    User userAutoOne = reviewerListAuto.get(i);
                    User userAuto = searchUser(userAutoOne.getName());
                    if(!userAuto.getName().equals(paperAuto.getAuthor()) &&userAuto.getKeywords().contains(paperAuto.getKeyword()))
                    {
                        userAuto.getConferenceListForReviewer().add(searchConference(paperAuto.getConName()));
                        paperAuto.getAssignedReviewerList().add(userAuto);
                        userAuto.getAssignedPaper().add(paperAuto);

                    }

                    i++;
                }
                for(User u:paperAuto.getAssignedReviewerList())
                {
                    sendMessage(u);
                }
                chair.getConferenceListForChair().add(searchConference(paperAuto.getConName()));
                if(paperAuto.getAssignedReviewerList().size() == 3)
                    paperAuto.setStatus("YES");
                System.out.println("Add Successfully!");
                writeUserToUserFile();
                writePaperToFile();
                break;
            case"1":
                System.out.println("please choose one paper to assign");

                for(Paper p:paperList)
                {
                    if(p.getStatus().equals("NO"))
                        System.out.println(paperList.indexOf(p)+"."+p.getName()+" "+"[Key word:]"+p.getKeyword());
                }

                String paperNum = sc.nextLine();
                while(isStringAlphabetic(paperNum)|| Integer.parseInt(paperNum) < 0)
                {
                    System.out.println("Please input the correct number");
                    paperNum = sc.nextLine();
                }
                Paper paperObject = paperList.get(Integer.parseInt(paperNum));
                System.out.println("This is the reviewer list, please choose one to assign this paper");

                for(User u:userList)
                {
                    if(u.getChooseType() == 2 && !u.getName().equals(paperObject.getAuthor()))
                        System.out.println(userList.indexOf(u)+"."+u.getName()+" "+"[Keywords:]"+u.getStringListNames(u.getKeywords()));
                }

                while(paperObject.getAssignedReviewerList().size() < 3)
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
                        while(u.getName().equals(userList.get(Integer.parseInt(number)).getName()))
                        {
                            System.out.println("You have already assign this reviewer,please choose again");
                            number = sc.nextLine();
                        }
                    }
                    while(!userList.get(Integer.parseInt(number)).getKeywords().contains(paperObject.getKeyword()))
                    {
                        System.out.println("You choose the reviewer's keywords do not match the paper's key word");
                        number = sc.nextLine();
                    }

                    User reviewerObject = userList.get(Integer.parseInt(number));
                    paperObject.getAssignedReviewerList().add(reviewerObject);
                }
                for(User u:paperObject.getAssignedReviewerList())
                {
                    u.getAssignedPaper().add(paperObject);
                    u.getConferenceListForReviewer().add(searchConference(paperObject.getConName()));
                    sendMessage(u);
                }

                paperObject.setStatus("YES");
                System.out.println("This paper have enough reviewers!");
                writeUserToUserFile();
                writePaperToFile();
                break;
            default:
                System.out.println("Please input the correct number");
                break;

        }


    }


    public void writeConferenceToFile()
    {
        // rewrite the new conference to database
        try
        {
            PrintWriter outputFile = new PrintWriter("src/conference.txt");
            for (Conference one : conferenceList)
                outputFile.println(one.toStringToDatabase());
            outputFile.close();
        }
        catch(IOException e)
        {
            System.out.println("Unexpected I/O exception occurs");
        }
    }

    public void writePaperToFile()
    {
        //rewrite the new conference to database
        try
        {
            PrintWriter outputFile = new PrintWriter("src/paper.txt");
            String str = "";
            for (Paper one : paperList)
            { if(one.getAssignedReviewerList().size()==0)
            {
                str = one.getName()+ "," +one.getSmDeadline()
                        +","+one.getRmDeadline()+","+one.getStatus()
                        +"," +one.getAuthor()+","+one.getKeyword()
                        +","+one.getDecision()+","+one.getConName();
                outputFile.println(str);
            }
                if(one.getAssignedReviewerList().size()>0)
                {
                    str = one.getName()+ "," +one.getSmDeadline()
                            +","+one.getRmDeadline()+","+one.getStatus()
                            +"," +one.getAuthor()+","+one.getKeyword()
                            +","+one.getDecision()+","+one.getConName()
                            + one.getReviewerNames(one.getAssignedReviewerList());
                    outputFile.println(str);
                }
            }
            outputFile.close();
        }
        catch(IOException e)
        {
            System.out.println("Unexpected I/O exception occurs");
        }

    }

    public void writeUserToUserFile()
    {
        //rewrite the user to use file
        try
        {
            PrintWriter outputFile = new PrintWriter("src/userT.txt");
            String str = "";
            for (User one : userList)
            {
                String chairString = "" ;
                String authorString = "";
                String reviewerString = "";
                String assignPaper = "";
                String submitPaper = "";
                String message = "";

                if(one.getConferenceListForChair().size() ==0)
                    chairString = "";
                if(one.getConferenceListForChair().size() > 0)
                    chairString = one.getConferenceNames(one.getConferenceListForChair());

                if(one.getConferenceListForAuthor().size() ==0)
                    authorString = "";
                if(one.getConferenceListForAuthor().size() > 0)
                    authorString = one.getConferenceNames(one.getConferenceListForAuthor());

                if(one.getConferenceListForReviewer().size() == 0)
                    reviewerString ="";
                if(one.getConferenceListForReviewer().size() > 0)
                    reviewerString = one.getConferenceNames(one.getConferenceListForReviewer());

                if(one.getAssignedPaper().size() == 0)
                    assignPaper ="";
                if(one.getAssignedPaper().size() > 0)
                    assignPaper = one.getPaperNames(one.getAssignedPaper());

                if(one.getSubmittedPaper().size() == 0)
                    submitPaper = "";
                if(one.getSubmittedPaper().size() > 0)
                    submitPaper = one.getPaperNames(one.getSubmittedPaper());

                if(one.getMessageBox().size() == 0)
                    message = "";
                if(one.getMessageBox().size() > 0)
                    message = one.getStringListNames(one.getMessageBox());


                str = "user{"+one.getID()+","+one.getName()+","+one.getPsw()+","+one.getChooseType()+","+one.getEmail()+","+one.getOccupation()+","+one.getMobileNumber()
                        +","+one.getHighQualification()+","+one.getEmployerDetail()+","+one.getInterestArea()+"}\n"
                        +"Conference for chair{"+chairString+"}\n"
                        +"Conference for Author{"+authorString+"}\n"
                        +"Conference for Reviewer{"+reviewerString+"}\n"
                        +"Keywords{"+one.getStringListNames(one.getKeywords())+"}\n"
                        +"Assigned Paper{"+assignPaper+"}\n"
                        +"Submitted Paper{"+submitPaper+"}\n"
                        +"Message{"+message+"}\n";
                outputFile.println(str);
            }

            outputFile.close();
        }
        catch(IOException e)
        {
            System.out.println("Unexpected I/O exception occurs");
        }


    }









    public boolean isInputUpToFormat(String str)
    {
        if(str.trim().equals("") || !isStringAlphabetic(str))
            return false;
        return true;
    }
    public boolean isStringAlphabetic(String checkedString)
    {
        int i;
        for (i = 0; i < checkedString.length(); i ++)
        {
            char character = checkedString.charAt(i);
            // To restrict the string can only contain alpha and space. Avoid special symbol like , + =.
            if ((character >= 'a' && character <= 'z') || (character >= 'A' && character <= 'Z') || character == ' ')
            {

            }
            else
                return false;
        }
        return true;
    }
    public boolean isTimeUpToStandard(String s) throws ParseException {
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if(dateFormat.parse(s).before(now))
        {
            System.out.println("The input date could not before the current time!");
            return false;
        }
        String[]dateBlocks = s.split(" ");
        String[]dateLeftPart = dateBlocks[0].split("-");
        String[]dateRightPart = dateBlocks[1].split(":");
        if(Integer.parseInt(dateLeftPart[0]) > 2025 || Integer.parseInt(dateLeftPart[1]) < 0|| Integer.parseInt(dateLeftPart[1]) > 12
                || Integer.parseInt(dateLeftPart[2]) < 0|| Integer.parseInt(dateLeftPart[2]) > 31 || Integer.parseInt(dateRightPart[0]) < 0
                ||Integer.parseInt(dateRightPart[0]) > 24||Integer.parseInt(dateRightPart[1]) < 0 || Integer.parseInt(dateRightPart[1]) > 60
                ||Integer.parseInt(dateRightPart[2]) < 0 || Integer.parseInt(dateRightPart[2]) > 60)
        {
            System.out.println("Input the correct time format");
            return false;
        }
        return true;

    }

    public void submitPaper(String name) throws Exception
    {
        readFromFile();
        User author =searchUser(name);
        System.out.println("Hi"+" "+author.getName()+","+"you are now logging in as an author!");
        System.out.println("**************************************");
        System.out.println("          Author Management           ");
        System.out.println("**************************************");
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("Current Time: " + sdf.format(d));
        for(Conference c: conferenceList)
            System.out.println(conferenceList.indexOf(c)+"."+c.getConName());
        Scanner sc = new Scanner(System.in);
        System.out.println("Please Choose a Conference: ");
        String option  = sc.nextLine();
        while(isStringAlphabetic(option)|| Integer.parseInt(option) < 0)
        {
            System.out.println("Please input the correct number:");
            option =sc.nextLine();
        }
        Conference conferenceObject = conferenceList.get(Integer.parseInt(option));
        System.out.println("The submission deadline for conference: " + conferenceObject.getSubDate());
        if(d.after(sdf.parse(conferenceObject.getSubDate())))
        {
            System.out.println("you can not submit the paper");
            return;
        }
        System.out.println("Please enter the paper name (end with [.pdf] ot [.docx])");
        String paperName = sc.nextLine();
        while(!validFile(paperName))
        {
            System.out.println("The file format is invalid");
            paperName = sc.nextLine();
        }
        System.out.println("Please choose the key word for this paper");
        for(int i = 0; i < keywordList.size();i++)
        {
            if (keywordList.get(i).equals(""))
            {
                keywordList.remove(keywordList.get(i));
            }
            else
                System.out.println(i+"."+keywordList.get(i));
        }
        String number = sc.nextLine();//add validations
        while(isStringAlphabetic(number) || Integer.parseInt(number) >= keywordList.size() || Integer.parseInt(number) < 0)
        {
            System.out.println("Please input the correct number");
            number = sc.nextLine();
        }
        String paperKeyWord = keywordList.get(Integer.parseInt(number));
        Paper newPaper = new Paper(paperName,conferenceObject.getSubDate(),conferenceObject.gatRevDate(),"NO",author.getName(),paperKeyWord,"null",conferenceObject.getConName());
        paperList.add(newPaper);


        author.getConferenceListForAuthor().add(conferenceObject);
        author.getSubmittedPaper().add(newPaper);

        writePaperToFile();
        writeUserToUserFile();
    }




    public boolean validFile(String fileName)
    {
        if (fileName.endsWith(".pdf") || fileName.endsWith(".docx"))
            return true;
        return false;
    }

    public static void main(String[] args) throws Exception
    {

        ConferenceManagement cm = new ConferenceManagement();
        cm.submitPaper("Carol" );





    }

}