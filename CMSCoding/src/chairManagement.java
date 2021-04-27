import java.io.*;
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


    public void modifyConference()
    {
        //read from database
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
            PrintWriter outputFile = new PrintWriter("src/test.txt");
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




    public static void main(String[] args) throws ParseException {
        chairManagement c = new chairManagement();
        c.modifyConference();

    }

}
