public class Chair extends User
{
   private boolean reviewStatus;
   public Chair (int newId, String newName, String newPsw, String newType, boolean Status)
   {
       super(newId, newName, newPsw, newType);
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
