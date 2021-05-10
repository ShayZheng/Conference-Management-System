
public class Chair extends User
{
   private boolean reviewStatus;
   public Chair (int newID, String newName, String newPsw, String newEmail, String newOccupation, String newMN, String newHQ, String newED, String newIA, boolean Status)
   {
       super(newID, newName, newPsw, newEmail, newOccupation, newMN, newHQ, newED, newIA);
       reviewStatus = Status;
   }
    public boolean getStatus()
    {
        return reviewStatus;
    }
    public void setStatus(boolean newStatus)
    {
        reviewStatus = newStatus;
    }

}
