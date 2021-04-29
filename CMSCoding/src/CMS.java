import java.io.*;
import java.util.*;

public class CMS {


    public void register(){
        String type;
        String name;
        String psw1;
        String psw2;
        int ID;

        // Use String to avoid invalid input.
        String anID;

        Scanner scan = new Scanner(System.in);

        System.out.print("Enter the user's Type: ");
        type = scan.nextLine();
        while (!type.equals("Chair") || !type.equals("Reviewer") || !type.equals("Author")) // check whether the name is allowed.
        {
            System.out.println("The user type could only be Chair, Reviewer or Author.");
            System.out.print("Enter the user's type: ");
            type = scan.nextLine();
        }

        System.out.print("Enter the user's ID: ");
        anID = scan.nextLine();
        while(anID.trim().equals("") || !isStringNumeric(anID) || Integer.parseInt(anID) < 1)
        {
            System.out.println("The user's ID should be an integer greater than 1. Also should be unique and not be null.");
            System.out.print("Enter the user's ID: ");
            anID = scan.nextLine();
        }
        ID = Integer.parseInt(anID);


        System.out.print("Enter the user's name: ");
        name = scan.nextLine();
        while (name.trim().equals("") || !isStringAlphabetic(name)) // check whether the name is allowed.
        {
            System.out.println("The user's neme cannot be null and should only be alphabetic.");
            System.out.print("Enter the user's name: ");
            name = scan.nextLine();
        }

        System.out.print("Enter the password: ");
        psw1 = scan.nextLine();
        while(psw1.trim().equals(""))
        {
            System.out.println("The password should not be null.");
            System.out.print("Enter the password: ");
            psw1 = scan.nextLine();
        }

        System.out.print("Enter the password again: ");
        psw2 = scan.nextLine();
        while(!psw2.equals(psw1))
        {
            System.out.println("The password should be the same with the first time.");
            System.out.print("Enter the password again: ");
            psw2 = scan.nextLine();
        }

        if(type.equals("Chair")){
            boolean status;
            status = false;
            new Chair(ID, name, psw2, type, status);
        }else if(type.equals("Reviewer")){
            String keyword;
            ArrayList<Paper> papers;
            System.out.print("Enter your keyword: ");
            keyword = scan.nextLine();
            while(keyword.trim().equals("") || !isStringAlphabetic(keyword)){
                System.out.println("The keyword should not be null and could only be alphabetic.");
                keyword = scan.nextLine();
            }
            new Reviewer(ID, name, psw2, type, keyword);
        }else if(type.equals("Author")){
            String email;
            ArrayList<Paper> personalPaper;

            email = scan.nextLine();
            new Author(ID, name, psw2, type, email);
        }
    }

    /**
     * To check whether the option is numeric.
     */
    public boolean isStringNumeric(String checkedString)
    {
        int i;
        for (i = 0; i < checkedString.length(); i ++)
        {
            if (!Character.isDigit(checkedString.charAt(i)))
                return false;
        }
        return true;
    }

    /**
     * To check whether the borrower's name is alphabetic.
     */
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

    public static void main(String[] args){
        System.out.println("Hello World!");

        Reviewer r = new Reviewer(1, "Shay", "666666", "Reviewer", "IT");
        System.out.println("-----------Reviewer message------------");
        System.out.println(r);

        Paper p = new Paper("Distributed application", "03/05/2021", "13/05/2021", "No",
                "Peter Wong", "IT", "Notknow", "Application");

        System.out.println("\n" + "-----------Paper message------------");
        System.out.println(p);
    }
}
