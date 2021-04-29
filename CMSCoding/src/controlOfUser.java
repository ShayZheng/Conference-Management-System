import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;


public class controlOfUser {
    private User user;
    //    private Reviewer reviewer;
    private ArrayList<User> files;

    public controlOfUser() {
        files = new ArrayList<>();
    }

    public void addUser() {

//        default:
//        ID = 56;
//        Type = "Reviewer";
//        Name = "Nono";
//        Psw = "123456";

        user = new User();


        System.out.println("Enter User's ID: ");
        Scanner scanner = new Scanner(System.in);
        String strID = scanner.nextLine().trim();
        //should have validation here

        int intID = Integer.parseInt(strID);
        user.setID(intID);
        intID = user.getID();


        String Type = "";
        System.out.println("Enter User's Type: ");
        Scanner console = new Scanner(System.in);
        Type = console.nextLine().trim();
        //should have validation here

        user.setType(Type);
        Type = user.getType();


        String Name = "";
        System.out.println("Enter User's Name: ");
        Scanner sc = new Scanner(System.in);
        Name = sc.nextLine().trim();
        //should have validation here

        user.setName(Name);
        Name = user.getName();

        String Psw = "";
        System.out.println("Enter User's Passward: ");
        Scanner cs = new Scanner(System.in);
        Psw = cs.nextLine().trim();
        //should have validation here

        user.setPsw(Psw);
        Psw = user.getPsw();

        files.add(new User(intID, Name, Psw, Type));
    }

    public void displayUsers()
    {
        for (User user : files)
        {
            System.out.println((files.indexOf(user) + 1) + "." + "     " + user.getID() + "," + user.getName()
                    + "," + user.getType());
        }
    }

    public void readFile()
    {
        Scanner scan = null;
        try
        {
            FileReader reader = new FileReader("user.txt");
            scan = new Scanner(reader);
            while (scan.hasNextLine())
            {
                // get the name,id and age of the borrowers of each line in the txt file
                String line = scan.nextLine();
                String[] values = line.split(",");
                int id = Integer.parseInt(values[0].trim());
                String name = values[1].trim();
                String psw = values[2].trim();
                String type = values[3].trim();

                user = new User(id, name, psw, type);
                files.add(user);

//                    if (values.length == 6)
//                    {
//                        String newTitle = values[3].trim();
//                        Book book = bookDatabase.getBookUsingTitle(newTitle);
//                        borrower.borrowBook(book);
//                    }
//                    else if (values.length == 9 )
//                    {
//                        String newTitleOne = values[3].trim();
//                        Book book = bookDatabase.getBookUsingTitle(newTitleOne);
//                        borrower.borrowBook(book);
//                        String newTitleTwo = values[6].trim();
//                        Book bookTwo = bookDatabase.getBookUsingTitle(newTitleTwo);
//                        borrower.borrowBook(bookTwo);
//                    }
            }
        }
        catch (FileNotFoundException e)
        {
            System.out.println("Error: User.txt not found");
        }
        catch (IOException e)
        {
            System.out.println("I/O Exception occurs");
        }
        finally
        {
            scan.close();
        }
    }

    public void writeFile()
    {
        PrintWriter writer = null;
        try
        {
            writer = new PrintWriter("user.txt");
            // get every details of borrowers of the borrower database
            for (User user : files)
            {
                int id = user.getID();
                String name = user.getName().trim();
                String type = user.getType().trim();
                String psw = user.getPsw().trim();
                String line = id + "," + name + "," + psw + "," + type;

                writer.println(line);
            }


        }
        catch (IOException e)
        {
            System.out.println("I/O Exception occurs");
        }
        finally
        {
            writer.close();
        }
    }


}

