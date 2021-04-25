import java.io.*;
import java.util.*;

public class CMS {
    private USER user;
    public CMS()
    {
        registerNewUser();
    }
    public void registerNewUser()
    {
        String type;
        String name;
        String psw;
        int ID;

        // Use String to avoid invalid input.
        String anID;

        Scanner scan = new Scanner(System.in);

        System.out.print("Enter the User's type: ");
        type = scan.nextLine();

        System.out.print("Enter the User's name: ");
        name = scan.nextLine();
        while (name.trim().equals("") || !isStringAlphabetic(name)) // check whether the name is allowed.
        {
            System.out.println("The User's neme cannot be null and should only be alphabetic.");
            System.out.print("Enter the Userr's name: ");
            name = scan.nextLine();
        }

        System.out.print("Enter the User's ID: ");
        anID = scan.nextLine();
        while(anID.trim().equals("") || !isStringNumeric(anID) || Integer.parseInt(anID) < 1 || Integer.parseInt(anID) > 100)
        {
            System.out.println("The user's ID should be an integer between 1 to 100. Also should be unique and not be null.");
            System.out.print("Enter the User's ID: ");
            anID = scan.nextLine();
        }
        ID = Integer.parseInt(anID);

        System.out.print("Enter the User's password: ");
        psw = scan.nextLine();

        user = new USER(ID, type, name, psw);
        user.printUserInfo();
    }

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

    public static void main (String[] args)
    {
        new CMS();
    }
}

