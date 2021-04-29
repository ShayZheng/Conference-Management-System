import java.util.*;

public class Conference
{
    private String conName;
    private String conTitle;
    private String conTopic;
    private String subDate;
    private String revDate;
    public Conference(String name, String title, String topic,String submissionDate,String reviewDate )
    {
        conName = name;
        conTitle = title;
        conTopic = topic;
        subDate = submissionDate;
        revDate = reviewDate;

    }

    public String getConName()
    {
        return conName;
    }
    public String getConTitle()
    {
        return conTitle;
    }
    public String getConTopic()
    {
        return conTopic;
    }
    public  String getSubDate()
    {
        return subDate;
    }
    public String gatRevDate()
    {
        return revDate;
    }
    public void setConName(String newName)
    {
        conName = newName;
    }
    public void setConTitle(String newTitle)
    {
        conTitle = newTitle;
    }
    public void setConTopic(String newTopic)
    {
        conTopic = newTopic;
    }
    public void setSubDate(String newDate)
    {
        subDate  = newDate;
    }
    public void setRevDate(String newDate)
    {
       revDate  = newDate;
    }

    public String toString()
    {
        return "Conference Name:" + " " + conName + "," + "Conference Title:" + " " + conTitle + "," + "Conference Topic:" + " "
                + conTopic + "，" + "Submission time:" + " " + subDate + "," + "Review time:" + " " + revDate;
    }
    public String toStringToDatabase()
    {
        return conName + ","+ conTitle +","+ conTopic +","+ subDate + "," + revDate;

    }
}
