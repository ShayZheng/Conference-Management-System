import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Scanner;
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
        try
        {
            FileReader inputFile = new FileReader("src/paper test.txt");
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
                    //Add paper information in paper list
                    String[] paperInfo = cleaM.get(0).split(",");
                    paperList.add(new Paper(paperInfo[0],paperInfo[1], paperInfo[2], paperInfo[3], paperInfo[4],paperInfo[5],paperInfo[6],paperInfo[7]));

                    //reviewer list for paper
                    String[] reviewer = cleaM.get(1).split(",");
                    for(int j = 0; j < reviewer.length;j++)
                    {
                        if(!reviewer[j].equals(null))
                            paperList.get(i).getAssignedReviewerList().add(searchUser(reviewer[j]));

                    }
                    //keyword list for this paper
                    String[] paperKeyWord = cleaM.get(2).split(",");
                    for(int j = 0; j < paperKeyWord.length;j++)
                    {
                        if(!paperKeyWord[j].equals(null))
                            paperList.get(i).getKeywords().add(paperKeyWord[j]);
                    }
                    //Evaluation for this paper
                    String[] evaluations = cleaM.get(3).split(",");
                    for(int j = 0; j < evaluations.length;j++)
                    {
                        if(!evaluations[j].equals(null))
                            paperList.get(i).getEvaluation().add(evaluations[j]);
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

        for(User u:userList)
        {
            if(u.getMessageBox().size()==1)
            {
                if(u.getMessageBox().get(0).equals(""))
                    u.getMessageBox().clear();
            }
            if(u.getKeywords().size() == 1)
            {
                if(u.getKeywords().get(0).equals(""))
                    u.getKeywords().clear();
            }
            if(u.getConferenceListForChair().size()==1)
            {
                if(u.getConferenceListForChair().get(0) == null)
                    u.getConferenceListForChair().clear();
            }
            if(u.getConferenceListForAuthor().size()==1)
            {
                if(u.getConferenceListForAuthor().get(0) == null)
                    u.getConferenceListForAuthor().clear();
            }
            if(u.getConferenceListForReviewer().size()==1)
            {
                if(u.getConferenceListForReviewer().get(0) == null)
                    u.getConferenceListForReviewer().clear();
            }
            if(u.getAssignedPaper().size()==1)
            {
                if(u.getAssignedPaper().get(0) == null)
                    u.getAssignedPaper().clear();
            }
            if(u.getSubmittedPaper().size()==1)
            {
                if(u.getSubmittedPaper().get(0) == null)
                    u.getSubmittedPaper().clear();
            }



        }//clear some variables equals ""
        for(Paper p:paperList)
        {
            if(p.getEvaluation().size() == 1)
            {
                if(p.getEvaluation().get(0).equals(""))
                     p.getEvaluation().clear();
            }
            if(p.getKeywords().size() == 1)
            {
                if(p.getKeywords().get(0).equals(""))
                     p.getKeywords().clear();
            }
            if(p.getAssignedReviewerList().size() == 1)
            {
                if(p.getAssignedReviewerList().get(0) == null);
                    p.getAssignedReviewerList().clear();
            }

        }//clear some variables equals ""



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


    public User searchUserByEmail(String s)
    {
        for(User u:userList)
            if(u.getEmail().equals(s))
                return u;

        return null;
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


    public boolean checkTwoArrayListHaveSameVariable(ArrayList<String> one, ArrayList<String> two)
    {
        for(String s: one)
        {
            if(two.contains(s))
                return true;
        }
        return false;
    }

    public int findAccount(String email)
    {
        int index = -1;
        for (User user : userList)
        {
            if (user.getEmail().equals(email))
                index = userList.indexOf(user);
        }
        return index;
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
            PrintWriter outputFile = new PrintWriter("src/paper test.txt");
            String str = "";
            for (Paper one : paperList)
            {
                String reviewerString = "" ;
                String keywordString = "";
                String evaluationString = "";

                if(one.getAssignedReviewerList().size() ==0)
                    reviewerString = "";
                if(one.getAssignedReviewerList().size() > 0)
                    reviewerString = one.getReviewerNames(one.getAssignedReviewerList());

                if(one.getKeywords().size() ==0)
                    keywordString = "";
                if(one.getKeywords().size() > 0)
                    keywordString = one.getStringListNames(one.getKeywords());

                if(one.getEvaluation().size() == 0)
                    evaluationString ="";
                if(one.getEvaluation().size() > 0)
                    evaluationString = one.getStringListNames(one.getEvaluation());



                str = "paper{"+one.getName()+","+one.getSmDeadline()+","+one.getRmDeadline()+","+one.getStatus()+","+one.getAuthor()+","+one.getDecision()+","+one.getConName()+","+one.getFilePath()+"}\n"
                        +"reviewer for this paper{"+reviewerString+"}\n"
                        +"keywords{"+keywordString+"}\n"
                        +"Evaluation{"+evaluationString+"}\n";
                outputFile.println(str);
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
        if(!s.matches("^((([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29))\\s+([0-1]?[0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])$"))
        {
            System.out.println("You time format is not correct");
            return false;
        }

        Date now = new Date();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date limitation = dateFormat.parse("2025-12-31 23:55:55");
        if(dateFormat.parse(s).before(now))
        {
            System.out.println("The input date could not before the current time!");
            return false;
        }
        if(dateFormat.parse(s).after(limitation))
        {
            System.out.println("You time format could not be after 2025-12-31 23:55:55");
            return false;
        }

    /*String[]dateBlocks = s.split(" ");
    String[]dateLeftPart = dateBlocks[0].split("-");
    String[]dateRightPart = dateBlocks[1].split(":");
    if(Integer.parseInt(dateLeftPart[0]) > 2025 || Integer.parseInt(dateLeftPart[1]) < 0|| Integer.parseInt(dateLeftPart[1]) > 12
            || Integer.parseInt(dateLeftPart[2]) < 0|| Integer.parseInt(dateLeftPart[2]) > 31 || Integer.parseInt(dateRightPart[0]) < 0
            ||Integer.parseInt(dateRightPart[0]) > 24||Integer.parseInt(dateRightPart[1]) < 0 || Integer.parseInt(dateRightPart[1]) > 60
            ||Integer.parseInt(dateRightPart[2]) < 0 || Integer.parseInt(dateRightPart[2]) > 60)*/
        return true;
    }


    public boolean checkConferenceOverlaps(ArrayList<Conference> one,Conference two)
    {
        if(one.contains(two))
            return true;

        return false;
    }//this method is used to check if the same user login with different identity, whether his conferences for different identities overlap


    public boolean validFile(String fileName)
    {
        if (fileName.endsWith(".pdf") || fileName.endsWith(".docx"))
            return true;
        return false;
    }



}