import java.util.*;
import java.io.*;
import java.util.Date;
import java.text.SimpleDateFormat;
public class author_demo {
    private ArrayList<Conference> conferenceList;

    public author_demo()
    {
        conferenceList = new ArrayList<>();
    }

    public static void readConferTxt(){
        displayAuthorConferenceMenu();
    }

    public static void displayAuthorConferenceMenu(){
        System.out.println("**************************************");
        System.out.println("          Author Management           ");
        System.out.println("**************************************");
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
        System.out.println("Current Time: " + sdf.format(d));
        System.out.println("Please Choose a Conference: ");
        //System.out.println("Conference List: " + readFromFile());

    }

    public void validConName(){
        Scanner sc = new Scanner(System.in);
        String st = sc.nextLine();
        String a = "Cyber Security";
        String b = "Info Technology";
        String c = "Information Technology";
        String d = "Data Science";
        Date da = new Date();
        if (st.equals(a)){
            System.out.println("**************************************");
            System.out.println("          Author Management           ");
            System.out.println("**************************************");

            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
            System.out.println("Conference Name: Cyber Security");
            System.out.println("Current Time: " + sdf.format(da));
            System.out.println("Please enter the option:(1-3)");
            System.out.println("1: Please upload paper");
            System.out.println("2: Please provide keyword");
            System.out.println("3: Back home screen");
            System.out.println("Please choose a option: ");
        }
        else if(st.equals(b)){
            System.out.println("**************************************");
            System.out.println("          Author Management           ");
            System.out.println("**************************************");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
            System.out.println("Conference Name: Info Technology");
            System.out.println("Current Time: " + sdf.format(da));
            System.out.println("Please enter the option:(1-3)");
            System.out.println("1: Please upload paper");
            System.out.println("2: Please provide keyword");
            System.out.println("3: Back home screen");
            System.out.println("Please choose a option: ");
        }
        else if (st.equals(c)){
            System.out.println("**************************************");
            System.out.println("          Author Management           ");
            System.out.println("**************************************");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
            System.out.println("Conference Name: Information Technology");
            System.out.println("Current Time: " + sdf.format(da));
            System.out.println("Please enter the option:(1-3)");
            System.out.println("1: Please upload paper");
            System.out.println("2: Please provide keyword");
            System.out.println("3: Back home screen");
            System.out.println("Please choose a option: ");
        }
        else if (st.equals(d)){
            System.out.println("**************************************");
            System.out.println("          Author Management           ");
            System.out.println("**************************************");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
            System.out.println("Conference Name: Data Science");
            System.out.println("Current Time: " + sdf.format(da));
            System.out.println("Please enter the option:(1-3)");
            System.out.println("1: Please upload paper");
            System.out.println("2: Please provide keyword");
            System.out.println("3: Back home screen");
            System.out.println("Please choose a option: ");
        }
        else {
            System.out.println("fasfafa");
        }
        try{
            PrintWriter outputFile = new PrintWriter("author_demo.txt");
            outputFile.println(st + "\r\n");
            outputFile.close();
        }
        catch (IOException e){
            System.out.println("Unexpected I/O exception occurs");
        }


    }

    public void readFromConFile(){
        try
        {
            FileReader inputFile = new FileReader("conference.txt");
            try
            {
                Scanner sc = new Scanner(inputFile);
                while (sc.hasNextLine())
                {
                    String line = sc.nextLine();
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

    public static int validNum(int minimum, int maximum)
    {
        // valid num should be in a range
        Scanner sc = new Scanner(System.in);
        int value = 0;
        boolean flag = false;
        while (!flag)
        {
            try
            {
                String tempValue = sc.nextLine();
                value = Integer.parseInt(tempValue);
                checkValueRange(value, minimum, maximum);
                flag = true;
            }
            catch(NumberFormatException n)
            {
                System.out.println("please input int");
            }
            catch(IllegalArgumentException i)
            {
                System.out.println("Valid int should be between " + minimum + " and " + maximum);
            }
            catch (Exception e)
            {
                System.out.println("Error when input int ");
                return 1;
            }
        }
        return value;

    }

    public static void checkValueRange(int a, int minimum, int maximum)
    {
        if (a < minimum || a > maximum)
        {
            //check a range
            System.out.println("Please enter valid number between " + minimum + " and " + maximum);
            throw new IllegalArgumentException();
        }
    }
    public static String uploadPaper(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Add your paper：");
        String fileName = sc.nextLine();
        try{
            PrintWriter outputFile = new PrintWriter("author_demo.txt");
            outputFile.println(fileName + "\r\n");
            outputFile.close();
        }
        catch (IOException e){
            System.out.println("Unexpected I/O exception occurs");
        }
        if (validFile(fileName) == false){
            System.out.println("Please upload specific paper format(pdf): ");
        }
        return fileName;
    }

    public static boolean validFile(String fileName){
        if(fileName.endsWith(".pdf")){
            return true;
        }
        return false;
    }

    public static String keyWords(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Provide your paper's keywords:");
        String keyWords = sc.nextLine();
        try{
            PrintWriter outputFile = new PrintWriter("author_demo.txt");
            outputFile.println(keyWords);
            outputFile.close();
        }
        catch (IOException e){
            System.out.println("Unexpected I/O exception occurs");
        }
        return keyWords;
    }
  /*  public void writeToFile()
    {
        try
        {
            PrintWriter outputFile = new PrintWriter("Author.txt");
            for (Conference one : conferenceList)
                outputFile.println(one.toString());
            outputFile.close();
        }
        catch(IOException e)
        {
            System.out.println("Unexpected I/O exception occurs");
        }
    }*/

    public static void main(String[] args) {
        author_demo c = new author_demo();
        c.readFromConFile();
        System.out.println("**************************************");
        System.out.println("          Author Management           ");
        System.out.println("**************************************");
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
        System.out.println("Current Time: " + sdf.format(d));
        System.out.println("Please Choose a Conference: "+ "\r\n"+ c.conferenceList.get(0).getConName()+  "\r\n"+
                c.conferenceList.get(1).getConName() + "\r\n"+ c.conferenceList.get(2).getConName() + "\r\n"+
                c.conferenceList.get(3).getConName());
        c.validConName();

        int choice = 0;
        while(choice != 3) {
            choice = validNum(1,3);
            switch (choice){
                case 1: //upload paper
                    uploadPaper();

                case 2: //provide key words
                    keyWords();
                case 3: //back home screen
                    System.out.println("home Screen");
            }
        }
    }
}
