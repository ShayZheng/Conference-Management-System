/**
 * @author Yuzhe Wang
 * @version 30 Apr 2021
 */

import java.util.ArrayList;
import java.util.*;

public class ConferenceManagement {
    private ArrayList<User> userList;
    private ArrayList<Conference> conferenceList;
    private ArrayList<Paper> paperList;

    public ConferenceManagement(){
        userList = new ArrayList<>();
        conferenceList = new ArrayList<>();
        paperList = new ArrayList<>();
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

    public void addConference(Conference newConference){
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

}
