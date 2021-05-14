/**
 * @author Yuzhe Wang
 * @version 29 Oct 2020
 */
public class Menu {
    public Menu(){}

    public void displayMainMenu(){
        System.out.println("\n Welcome to the Conference Management System");
        System.out.println("======================================");
        System.out.println("(1) Register New User");
        System.out.println("(2) Login");
        System.out.println("(3) Exit System");
        System.out.print("Choose an option: ");
    }

    public void displayAdministratorMenu(){
        System.out.println("\n Welcome to the Administrator home screen");
        System.out.println("======================================");
        System.out.println("(1) Retrieve user information");
        System.out.println("(2) Retrieve conference information");
        System.out.println("(3) Retrieve papers information");
        System.out.println("(4) Exit System");
        System.out.print("Choose an option: ");
    }

    public void displayChairMenu(){
        System.out.println("\n Welcome to the Chair home screen");
        System.out.println("======================================");
        System.out.println("(1) Submit a conference");
        System.out.println("(2) Modify paper deadline");
        System.out.println("(3) Send review notification");
        System.out.println("(4) Assign reviewers to papers");
        System.out.println("(5) Make decision for paper");
        System.out.println("(6) Return to Main Menu");
        System.out.print("Choose an option: ");
    }

    public void displayReviewerMenu(){
        System.out.println("\n Welcome to the Reviewer home screen");
        System.out.println("======================================");
        System.out.println("(1) Choose a topic to review");
        System.out.println("(2) Submit evaluation for paper");
        System.out.println("(3) Return to Main Menu");
        System.out.print("Choose an option: ");
    }

    public void displayAuthorMenu(){
        System.out.println("\n Welcome to the Author home screen");
        System.out.println("======================================");
        System.out.println("(1) Submit paper");
        System.out.println("(2) Return to Main Menu");
        System.out.print("Choose an option: ");
    }
}
