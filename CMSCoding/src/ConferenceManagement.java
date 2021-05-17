/**
 * @author Yuzhe Wang
 * @version 30 Apr 2021
 */

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
                    if(values.length == 11)
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
            FileReader inputFile = new FileReader("src/user.txt");
            try {
                Scanner parser = new Scanner(inputFile);

                String allLines = "";
                while (parser.hasNextLine()) {
                    String line = parser.nextLine();
                    allLines += line + "\n";
                }
                String[] blocks = allLines.split("\n\n");
                Pattern p = Pattern.compile("\\{(.*?)}");
                for (int i = 0; i < blocks.length - 1; i++)
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
                        userList.get(i).getConferenceListForChair().add(searchConference(conChair[j]));
                    }
                    //conference list for author for specific user
                    String[] conAuthor = cleaM.get(2).split(",");
                    for(int j = 0; j < conAuthor.length;j++)
                    {
                        userList.get(i).getConferenceListForAuthor().add(searchConference(conAuthor[j]));
                    }
                    //conference list from reviewer for specific user
                    String[] conReviewer = cleaM.get(3).split(",");
                    for(int j = 0; j < conReviewer.length;j++)
                    {
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
                        userList.get(i).getAssignedPaper().add(searchPaper(assignedPapers[j]));
                    }
                    //add submitted paper for specific user
                    String[] submittedPapers = cleaM.get(6).split(",");
                    for(int j = 0; j < submittedPapers.length;j++)
                    {
                        userList.get(i).getSubmittedPaper().add(searchPaper(submittedPapers[j]));
                    }
                    //add message to their messageBox
                    String[] messages = cleaM.get(7).split(",");
                    for(int j = 0; j < messages.length;j++)
                    {
                        userList.get(i).getMessageBox().add(messages[j]);
                    }

                    cleaM.clear();
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
        for(User u: userList)
        {

            if(name.equals(u.getName()))
            {

                int i = userList.indexOf(u);
                return userList.get(i);

            }
            else if(!name.equals(u.getName())){
                return null;
            }
        }
        return null;
    }

    public Paper searchPaper(String name)
    {
        for(Paper p: paperList)
        {

            if(name.equals(p.getName()))
            {

                int i = paperList.indexOf(p);
                return paperList.get(i);

            }
            else if(!name.equals(p.getName())){
                return null;
            }
        }
        return null;

    }

    public Conference searchConference(String name)
    {

        for(Conference c : conferenceList)
        {

            if(name.equals(c.getConName()))
            {

                int i = conferenceList.indexOf(c);
                return conferenceList.get(i);

            }
            else if(!name.equals(c.getConName())){
                return null;
            }
        }

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


    public void addPaper(Paper newPaper){
        paperList.add(newPaper);
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
    public void assignReviewer()
    {
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
                break;
            case"1":
                System.out.println("please choose paper to assign");
                for(Paper p:paperList)
                {
                    if(p.getStatus().equals("NO"))
                        System.out.println(paperList.indexOf(p)+"."+p.toString());
                }
                String paperNum = sc.nextLine();
                while(isStringAlphabetic(paperNum)||(Integer.parseInt(paperNum) >= paperList.size()|| Integer.parseInt(paperNum) < 0))
                {
                    System.out.println("Please input the correct number");
                    paperNum = sc.nextLine();
                }
                Paper paperObject = paperList.get(Integer.parseInt(paperNum));

                System.out.println("This is the reviewer list, please choose one to assign this paper");

                for(User u:userList)
                {
                    if(u.getChooseType() == 2)
                        System.out.println(userList.indexOf(u)+"."+ u.toString());
                }
                String number = sc.nextLine();
                while(isStringAlphabetic(number)||(Integer.parseInt(number) >= userList.size()|| Integer.parseInt(number) < 0))
                {
                    System.out.println("Please input the correct number");
                    number = sc.nextLine();
                }
                User reviewerObject = userList.get(Integer.parseInt(number));

                for(User u: paperObject.getAssignedReviewerList())
                {
                    if(reviewerObject.getName().equals(u.getName()))
                    {
                        System.out.println("You have already assign this reviewer!");
                        return;
                    }
                }
                reviewerObject.getAssignedPaper().add(paperObject);
                paperObject.getAssignedReviewerList().add(reviewerObject);
                break;
            default:
                System.out.println("Please input the correct number");
                break;

        }


    }




    public void writeToFile()
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

        //rewrite the paper to paper file
        try
        {
            PrintWriter outputFile = new PrintWriter("src/paper.txt");
            for (Paper one : paperList)
                outputFile.println(one.toStringDatabase());
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
            if ((character >= 'a' && character <= 'z') || (character >= 'A' && character <= 'Z') || character == ' '){}
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


    public static void main(String[] args) throws Exception {
        ConferenceManagement cm = new ConferenceManagement();
        cm.readFromFile();



    }

}
