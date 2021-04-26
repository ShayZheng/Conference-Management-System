public class CMS {


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
