import java.io.*;
import java.util.*;
import java.lang.*;

public class conferenceReport
{
    private ArrayList<Conference> conferenceList;

    public conferenceReport()
    {
        conferenceList = new ArrayList<>();

    }
    public void addConference()
    {
        Conference con = new Conference("1","2","3","4","5");
        conferenceList.add(con);


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


    public void writeToFile()
    {
        try
        {
            PrintWriter outputFile = new PrintWriter("conferenceReport.txt");
            for (Conference one : conferenceList)
                outputFile.println(one.toString());
            outputFile.close();
        }
        catch(IOException e)
        {
            System.out.println("Unexpected I/O exception occurs");
        }
    }



    public static void main(String[] args)
    {
        conferenceReport c = new conferenceReport();
        c.addConference();
        c.readFromFile();
        c.writeToFile();
    }
}

