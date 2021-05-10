/**
import java.io.*;
import java.text.*;
import java.util.*;


public class chairManagement
{
    private ArrayList<Conference> conferenceList;
    private ArrayList<Paper> paperList;
    private ArrayList<Reviewer> reviewerList;



    public chairManagement()
    {
        conferenceList = new ArrayList<>();
        paperList = new ArrayList<>();
        reviewerList = new ArrayList<>();

    }
    public void addConference() throws ParseException {
        Date date = new Date();
        //String conferenceName,String conferenceTitle,String conferenceTopic,String conSubTime, String conRevTime
        System.out.print("Please input the conference name:");
        Scanner sc = new Scanner(System.in);
        String name = sc.nextLine();
        System.out.print("Please input the conference title:");
        String title = sc.nextLine();
        System.out.print("Please input the conference topic:");
        String topic = sc.nextLine();
        System.out.println("Please set the submission deadline for this conference, the format is (yyyy-MM-dd HH:mm:ss)");
        String subDate = sc.nextLine();
        System.out.println("Please set the submission deadline for this conference, the format is (yyyy-MM-dd HH:mm:ss)");
        String revDate = sc.nextLine();
        SimpleDateFormat submission = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat review = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (submission.parse(subDate).after(review.parse(revDate)))
        {
            System.out.println("Submission time should before the review time");
            return;
        }
        Conference newConference = new Conference(name,title,topic,subDate,revDate);
        conferenceList.add(newConference);
        // add the new conference to database

    }


    public void modifyConference()
    {
        //read from database
        try
        {
            FileReader inputFile = new FileReader("src/conference.txt");
            try
            {
                Scanner parser = new Scanner(inputFile);
                while (parser.hasNextLine())
                {
                    String line = parser.nextLine();
                    String[] values = line.split(",");
                    String name = values[0];
                    String title = values[1];
                    String topic = values[2];
                    String submission = values[3];
                    String review = values[4];
                    Conference c = new Conference(name, title, topic,submission,review);
                    conferenceList.add(c);
                }
            }
            finally
            {
                inputFile.close();
            }
        }
        catch(FileNotFoundException exception)
        {
            System.out.println("file not found");
        }
        catch(IOException exception)
        {
            System.out.println("Unexpected I/O exception occurs");
        }


        // change part
        Scanner sc = new Scanner(System.in);
        for(Conference one : conferenceList)
            System.out.println(conferenceList.indexOf(one)+"."+ one.toString());
        System.out.println("Please choose one conference to modify");
        String option = sc.nextLine();
        if(Integer.parseInt(option) > conferenceList.size())
            System.out.println("Can not find the conference!");
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
                switch (number) {
                    case "1":
                        System.out.print("please input the new name:");
                        String newName = sc.nextLine();
                        one.setConName(newName);
                        break;
                    case "2":
                        System.out.print("please input the new title:");
                        String newTitle = sc.nextLine();
                        one.setConTitle(newTitle);
                        break;
                    case "3":
                        System.out.print("please input the new topic:");
                        String newTopic = sc.nextLine();
                        one.setConTitle(newTopic);
                        break;
                    case "4":
                        System.out.print("please input the new submission deadline:");
                        String newSubDeadline = sc.nextLine();
                        one.setSubDate(newSubDeadline);
                        break;
                    case "5":
                        System.out.print("please input the new review deadline:");
                        String newRevDeadline = sc.nextLine();
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
       // rewrite to database
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

       // show the new conference list
        System.out.println("this is new conference list");
        for(Conference one :conferenceList)
        {
            System.out.println(conferenceList.indexOf(one)+"."+ one.toString());
        }

    }

    public boolean assignReviewer() {
        //read the reviewer list from database
        try {
            FileReader inputFile = new FileReader("src/user.txt");
            try {
                Scanner parser = new Scanner(inputFile);
                while (parser.hasNextLine()) {
                    String line = parser.nextLine();
                    String[] values = line.split(",");
                    if (values[3].equals("Reviewer")) {
                        String userId = values[0];
                        String userName = values[1];
                        String psw = values[2];
                        String userType = values[3];
                        String keyword = values[4];
                        String task = values[5];
                        Reviewer newReviewer = new Reviewer(Integer.parseInt(userId), userName, psw, userType, keyword,task);
                        reviewerList.add(newReviewer);
                    }
                }
            } finally {
                inputFile.close();
            }
        } catch (FileNotFoundException exception) {
            System.out.println("file not found");
        } catch (IOException exception) {
            System.out.println("Unexpected I/O exception occurs");
        }
        //read the paper list from database
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
                    {
                        for (Reviewer one:reviewerList)
                        {
                            if(one.getName().equals(values[8]))
                            {
                                p.getAssignedReviewerList().add(one);
                                one.setHasTask("YES");

                            }
                        }
                    }
                    if(values.length == 10)
                    {
                        for (Reviewer one:reviewerList)
                        {
                            if(one.getName().equals(values[8]) || one.getName().equals(values[9]))
                            {
                                p.getAssignedReviewerList().add(one);
                                one.setHasTask("YES");
                            }

                        }
                    }
                    if(values.length == 11)
                    {
                        for (Reviewer one:reviewerList)
                        {
                            if(one.getName().equals(values[8]) || one.getName().equals(values[9]) ||one.getName().equals(values[10]))
                            {
                                p.getAssignedReviewerList().add(one);
                                one.setHasTask("YES");
                            }
                        }
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

        //show the paper list and find the unreviewed paper
        System.out.println("Please choose one paper to assign reviewers");
        for (int i = 0; i < paperList.size(); i++) {
            if (paperList.get(i).getStatus().equals("NO")) {
                System.out.println(i + "." + paperList.get(i).toString());
            }

        }
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        if (Integer.parseInt(input) > paperList.size())
            System.out.println("Can not find this paper!");
        Paper modifyObject = paperList.get(Integer.parseInt(input));
        for (Reviewer one : reviewerList)
            System.out.println(reviewerList.indexOf(one) +"."+ one.toString());
        boolean loop = true;
        while (loop)
        {
            if (modifyObject.getAssignedReviewerList().size() >= 3)
            {
                System.out.println("you have assign enough reviewers!");
                return false;
            }
            System.out.println("Please choose one reviewer for this paper");
            String choose = sc.nextLine();
            if (Integer.parseInt(choose) > reviewerList.size())
            {
                System.out.println("Cannot find this reviewer!");
                return false;
            }
            Reviewer newOne = reviewerList.get(Integer.parseInt(choose));
            if(!newOne.getKeyword().equals(modifyObject.getKeyword()))
            {
                System.out.println("this reviewer's key topic does not match the paper's topic!");
                return false;
            }
            for (Reviewer i : modifyObject.getAssignedReviewerList())
            {
                if (newOne.getName().equals(i.getName())) {
                    System.out.println("you assign the same reviewer!");
                    return false;
                }
            }

            modifyObject.getAssignedReviewerList().add(newOne);
            System.out.println(modifyObject.toString());
            // rewrite to paper database
            try
            {
                PrintWriter outputFile = new PrintWriter("src/paper.txt");
                for (Paper one : paperList)
                {
                    if(!one.getReviewer().equals(""))
                        one.setStatus("YES");
                    outputFile.println(one.toStringDatabase());
                }
                outputFile.close();
            }
            catch(IOException e)
            {
                System.out.println("Unexpected I/O exception occurs");
            }
           // rewrite to the user database
            try
            {
                PrintWriter outputFile = new PrintWriter("src/user.txt");
                for (Reviewer one : reviewerList)
                {
                    outputFile.println(one.toStringDatabase());
                }
                outputFile.close();
            }
            catch(IOException e)
            {
                System.out.println("Unexpected I/O exception occurs");
            }



        }

        return true;


    }

    public void sendNotification()
    {
        System.out.println("please choose the user type to send notification!");
        System.out.println("(1)Send notification to reviewers!");
        System.out.println("(2)Send notification to authors!");
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        switch (input)
        {
            case"1":
                //read reviewer from reviewerList
                for(Reviewer one: reviewerList)
                {
                        System.out.println(one.toString());
                }
                System.out.println("Choose one reviewer to send notification!");
                System.out.println("Please input the notification:");
                String reviewMessage = sc.nextLine();

                break;
            case"2":
                break;
        }

    }











    public static void main(String[] args) throws ParseException {
        chairManagement c = new chairManagement();
        c.assignReviewer();

    }
}
*/
