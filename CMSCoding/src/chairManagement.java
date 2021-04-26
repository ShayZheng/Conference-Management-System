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
        if (submission.parse(subDate).before(review.parse(revDate)))
        {
            System.out.println("Submission time should after the review time");
            return;
        }
        Conference newConference = new Conference(name,title,topic,subDate,revDate);
        conferenceList.add(newConference);
    }


    public void modifyConference()
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please input the name of conference for modification:");
        String name = sc.nextLine();
        for(Conference one:conferenceList)
        {
            if(one.getConName().equals(name))
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
                return;
        }
    }


}
