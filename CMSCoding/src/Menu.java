/**
 * @author Yuzhe Wang
 * @version 29 Oct 2020
 */
public class Menu {
    public Menu(){}

    public void displayMainMenu(){
        System.out.println("\n");
        System.out.println("**************************************");
        System.out.println("     Conference Management System     ");
        System.out.println("**************************************");
        System.out.println("(1) Register");
        System.out.println("(2) Login");
        System.out.println("(3) Exit System");
        System.out.print("Choose an option: ");
    }

    public void displayAdministratorMenu(){
        System.out.println("\n");
        System.out.println("**************************************");
        System.out.println("        Administrator Management      ");
        System.out.println("**************************************");
        System.out.println("(1) Retrieve user information");
        System.out.println("(2) Retrieve conference information");
        System.out.println("(3) Retrieve papers information");
        System.out.println("(4) Exit System");
        System.out.print("Choose an option: ");
    }

    public void displayChairMenu(){
        System.out.println("\n");
        System.out.println("**************************************");
        System.out.println("           Chair Management           ");
        System.out.println("**************************************");
        System.out.println("(1) Add a new conference");
        System.out.println("(2) Modify a conference");
        System.out.println("(3) Assign reviewers papers and send their notification");
        System.out.println("(4) Make decision for paper");
        System.out.println("(5) Return to Main Menu");
        System.out.print("Choose an option: ");
    }

    public void displayReviewerMenu(){
        System.out.println("\n");
        System.out.println("**************************************");
        System.out.println("          Reviewer Management         ");
        System.out.println("**************************************");
        System.out.println("(1) Specify your expertise keywords");
        System.out.println("(2) Submit evaluation for paper");
        System.out.println("(3) Return to Main Menu");
        System.out.print("Choose an option: ");
    }

    public void displayAuthorMenu(){
        System.out.println("\n");
        System.out.println("**************************************");
        System.out.println("          Author Management           ");
        System.out.println("**************************************");
        System.out.println("(1) Submit paper");
        System.out.println("(2) Return to Main Menu");
        System.out.print("Choose an option: ");
    }

    public void displayTypeMenu() {
        System.out.println("\n");
        System.out.println("**************************************");
        System.out.println("     Conference Management System     ");
        System.out.println("**************************************");
        System.out.println("Please Choose a user type: ");
        System.out.println("(1) Chair");
        System.out.println("(2) Reviewer");
        System.out.println("(3) Author");
        System.out.println("(4) Return to main menu.");
        System.out.print("Choose an option: ");
    }

    //Provides keywords menu for user to select, if the menu do not contains reviewers' keyword, he/she can choose manually input keywords.
    public void displayKeywordsMenu()
    {
        System.out.println("\n" + "Please select your keywords(first one should be your strong expertise)" + "\n");
        System.out.println("1.Information Technology");
        System.out.println("2.Cyber Security");
        System.out.println("3.Cloud Computing");
        System.out.println("4.Network Develop");
        System.out.println("5.Software Engineering");
        System.out.println("6.Distributed Mobile Develop");
        System.out.println("7.Database");
        System.out.println("8.Big Data");
        System.out.println("9.User Interface Design");
        System.out.println("10.Manually Add New Keywords ");
    }

     public void displayAuthorKeywordsMenu(){
        System.out.println("\n" + "Please select three keywords" + "\n");
        System.out.println("1.Information Technology");
        System.out.println("2.Cyber Security");
        System.out.println("3.Cloud Computing");
        System.out.println("4.Network Develop");
        System.out.println("5.Software Engineering");
        System.out.println("6.Distributed Mobile Develop");
        System.out.println("7.Database");
        System.out.println("8.Big Data");
        System.out.println("9.User Interface Design");
        System.out.println("10.Manually Add New Keywords ");
    }
}
