import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
public class author_demo
{




        private ArrayList<Conference> conferenceList = new ArrayList();
        private ArrayList<Paper> paperList = new ArrayList();

        public author_demo() {
        }

        public static void readConferTxt() {
            displayAuthorConferenceMenu();
        }

        public static void displayAuthorConferenceMenu() {
            System.out.println("**************************************");
            System.out.println("          Author Management           ");
            System.out.println("**************************************");
            Date d = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
            System.out.println("Current Time: " + sdf.format(d));
            System.out.println("Please Choose a Conference: ");
        }

        public void validConName(Paper paper) {
            boolean flag = false;
            Scanner sc = new Scanner(System.in);
            String st = sc.nextLine();

            while(true) {
                flag = true;
                if (false) {
                    break;
                }

                new Date();
                if (!st.equals("Cyber Security") && !st.equals("CS")) {
                    if (!st.equals("Info Technology") && !st.equals("IT")) {
                        if (!st.equals("Information Technology") && !st.equals("IT")) {
                            if (!st.equals("Data Science") && !st.equals("DS")) {
                                System.out.println("Please choose a correct conference");
                                st = sc.nextLine();
                                flag = true;
                                continue;
                            }

                            this.displayAuthorMenu();
                            this.writeConName(st);
                            paper.setConName(st);
                            break;
                        }

                        this.displayAuthorMenu();
                        this.writeConName(st);
                        paper.setConName(st);
                        break;
                    }

                    this.displayAuthorMenu();
                    this.writeConName(st);
                    paper.setConName(st);
                    break;
                }

                this.displayAuthorMenu();
                this.writeConName(st);
                paper.setConName(st);
                break;
            }

        }

        public void writeConName(String st) {
            try {
                FileWriter fw = new FileWriter("author_demo.txt", true);
                fw.write(st + ",");
                fw.close();
            } catch (IOException var3) {
                System.out.println("Unexpected I/O exception occurs");
            }

        }

        public void displayAuthorMenu() {
            Date da = new Date();
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

        public void readFromConFile() {
            try {
                FileReader inputFile = new FileReader("conference.txt");

                try {
                    Scanner sc = new Scanner(inputFile);

                    while(sc.hasNextLine()) {
                        String line = sc.nextLine();
                        String[] values = line.split(",");
                        String name = values[0];
                        String title = values[1];
                        String topic = values[2];
                        String submission = values[3];
                        String review = values[4];
                        Conference c = new Conference(name, title, topic, submission, review);
                        this.conferenceList.add(c);
                    }
                } finally {
                    inputFile.close();
                }
            } catch (FileNotFoundException var16) {
                System.out.println("file not found");
            } catch (IOException var17) {
                System.out.println("Unexpected I/O exception occurs");
            }

        }

        public static int validNum(int minimum, int maximum) {
            Scanner sc = new Scanner(System.in);
            int value = 0;
            boolean flag = false;

            while(!flag) {
                try {
                    String tempValue = sc.nextLine();
                    value = Integer.parseInt(tempValue);
                    checkValueRange(value, minimum, maximum);
                    flag = true;
                } catch (NumberFormatException var6) {
                    System.out.println("please input int");
                } catch (IllegalArgumentException var7) {
                    System.out.println("Valid int should be between " + minimum + " and " + maximum);
                } catch (Exception var8) {
                    System.out.println("Error when input int ");
                    return 1;
                }
            }

            return value;
        }

        public static void checkValueRange(int a, int minimum, int maximum) {
            if (a < minimum || a > maximum) {
                System.out.println("Please enter valid number between " + minimum + " and " + maximum);
                throw new IllegalArgumentException();
            }
        }

        public void uploadPaper(Paper paper) {
            Scanner sc = new Scanner(System.in);
            System.out.println("Add your paper：");
            String fileName = sc.nextLine();
            FileWriter ff = null;

            try {
                File f = new File("author_demo.txt");
                ff = new FileWriter(f, true);
                PrintWriter p = new PrintWriter(ff);
                p.print(fileName + ",");
                p.flush();
                p.close();
                ff.close();
            } catch (IOException var7) {
                System.out.println("Unexpected I/O exception occurs");
            }

            paper.setName(fileName);
        }

        public boolean validFile(String fileName) {
            return fileName.endsWith(".pdf") || fileName.endsWith(".docx");
        }

        public void keyWords(Paper paper) {
            Scanner sc = new Scanner(System.in);
            System.out.println("Provide your paper's keywords:");
            String keyWords = sc.nextLine();
            FileWriter ff = null;

            try {
                File f = new File("author_demo.txt");
                ff = new FileWriter(f, true);
                PrintWriter p = new PrintWriter(ff);
                p.println(keyWords);
                p.flush();
                p.close();
                ff.close();
            } catch (IOException var7) {
                System.out.println("Unexpected I/O exception occurs");
            }

            paper.setKeyword(keyWords);
        }

        public static void firstMenu(author_demo c, Paper paper) {
            System.out.println("**************************************");
            System.out.println("          Author Management           ");
            System.out.println("**************************************");
            Date d = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
            System.out.println("Current Time: " + sdf.format(d));
            System.out.println("Please Choose a Conference: \r\n" + ((Conference)c.conferenceList.get(0)).getConName() + "\r\n" + ((Conference)c.conferenceList.get(1)).getConName() + "\r\n" + ((Conference)c.conferenceList.get(2)).getConName() + "\r\n" + ((Conference)c.conferenceList.get(3)).getConName());
            c.validConName(paper);
        }

        public void authorSecondMenu(Paper p, author_demo c) {
            int choice = 0;

            while(choice != 3) {
                choice = validNum(1, 3);
                switch(choice) {
                    case 1:
                        c.uploadPaper(p);
                    case 2:
                        c.keyWords(p);
                        System.out.println(c.paperList);
                    case 3:
                        System.out.println("home Screen");
                }
            }
        }
        public static void main(String[] args) {
            author_demo c = new author_demo();
            c.readFromConFile();
            Paper p = new Paper();
            firstMenu(c, p);
            c.authorSecondMenu(p, c);
        }
    }


