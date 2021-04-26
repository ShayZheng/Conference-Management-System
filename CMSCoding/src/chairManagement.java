import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.*;
import java.util.*;


public class chairManagement
{
    private ArrayList<Conference> conferenceList;
    public chairManagement()
    {
        conferenceList = new ArrayList<>();
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
    }

    public void readFromFile()
    {
        try
        {
            FileReader inputFile = new FileReader("src/test.txt");
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
    }

    public void modifyConference()
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please choose one conference to modify");

        for(Conference one : conferenceList)
        {
           /*if(one.getConName().equals(name))
            {
                System.out.println("please choose which part to modify:");
                System.out.println("(1) conference name");
                System.out.println("(1) conference title");
                System.out.println("(1) conference topic");
                System.out.println("(1) conference submission deadline");
                System.out.println("(1) conference review deadline");
                String option = sc.nextLine();
                switch (option)
                {
                    case"1":
                        System.out.print("please input the new name:");
                        String newName = sc.nextLine();
                        one.setConName(newName);
                        break;
                    case"2":
                        System.out.print("please input the new title:");
                        String newTitle = sc.nextLine();
                        one.setConName(newTitle);
                        break;
                    case"3":
                        System.out.print("please input the new topic:");
                        String newTopic = sc.nextLine();
                        one.setConName(newTopic);
                        break;
                    case"4":
                        System.out.print("please input the new submission deadline:");
                        String newSubDeadline = sc.nextLine();
                        one.setConName(newSubDeadline);
                        break;
                    case"5":
                        System.out.print("please input the new review deadline:");
                        String newRevDeadline = sc.nextLine();
                        one.setConName(newRevDeadline);
                        break;
                    default:
                        System.out.println("please input the correct number!");
                        break;

                }
            }
            else
                return;*/
            System.out.println(conferenceList.indexOf(one)+"."+ one.toString());

        }
        int option = sc.nextInt();
        Conference modifyObject = conferenceList.get(option);
        System.out.println("please choose which part to modify:");
        System.out.println("(1) conference name");
        System.out.println("(2) conference title");
        System.out.println("(3) conference topic");
        System.out.println("(4) conference submission deadline");
        System.out.println("(5) conference review deadline");
        String number = sc.nextLine();
        switch (number)
        {
            case"1":
                System.out.print("please input the new name:");
                String newName = sc.nextLine();
                modifyObject.setConName(newName);
                break;
            case"2":
                System.out.print("please input the new title:");
                String newTitle = sc.nextLine();
                modifyObject.setConName(newTitle);
                break;
            case"3":
                System.out.print("please input the new topic:");
                String newTopic = sc.nextLine();
                modifyObject.setConName(newTopic);
                break;
            case"4":
                System.out.print("please input the new submission deadline:");
                String newSubDeadline = sc.nextLine();
                modifyObject.setConName(newSubDeadline);
                break;
            case"5":
                System.out.print("please input the new review deadline:");
                String newRevDeadline = sc.nextLine();
                modifyObject.setConName(newRevDeadline);
                break;
            default:
                System.out.println("please input the correct number!");
                break;

        }
        System.out.println("this is new conference list");
        for(Conference one :conferenceList)
        {
            System.out.println(conferenceList.indexOf(one)+"."+ one.toString());
        }

    }
    public static void main(String[] args) throws ParseException {
        chairManagement c = new chairManagement();
        c.readFromFile();
        c.modifyConference();

    }

}
