package travels;

import java.time.DateTimeException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    //storing user details(firstName,lastName,mobileNumber...) in an arraylist
    static ArrayList<UserDetails> users=new ArrayList<>();

    //available seats
    static int availableSeats=50;


    //storing usersJourneyDetails(source,destination....of each user) in an arrayList
    static  ArrayList<UserJourneyDetails> usersJourneyDetails=new ArrayList<>();

    //source to destination places and their prices
     static HashMap<String,Integer> hashMap=new HashMap<>();
    static {
        hashMap.put("visakhapatnam-vijayawada",500);
        hashMap.put("visakhapatnam-guntur",300);
        hashMap.put("visakhapatnam-nellore",400);
        hashMap.put("visakhapatnam-kurnool",350);
        hashMap.put("vijayawada-visakhapatnam",500);
        hashMap.put("vijayawada-guntur",300);
        hashMap.put("vijayawada-nellore",400);
        hashMap.put("vijayawada-kurnool",350);
        hashMap.put("guntur-vijayawada",500);
        hashMap.put("guntur-visakhapatnam",300);
        hashMap.put("guntur-nellore",400);
        hashMap.put("guntur-kurnool",350);
        hashMap.put("nellore-vijayawada",500);
        hashMap.put("nellore-visakhapatnam",300);
        hashMap.put("nellore-guntur",400);
        hashMap.put("nellore-kurnool",350);
        hashMap.put("nellore-kakinada",250);
        hashMap.put("kakinada-vijayawada",500);
        hashMap.put("kakinada-visakhapatnam",300);
        hashMap.put("kakinada-guntur",400);
        hashMap.put("kakinada-kurnool",350);
        hashMap.put("kakinada-kakinada",250);
    }


    public static void main(String[] args) {
        Scanner scanner=new Scanner(System.in);
        boolean decider=true;//for loop control
        while (decider)
        {
            System.out.println("***** welcome to abc travels *****");
            System.out.println("1.sign up");
            System.out.println("2.login");
            System.out.println("3.plan journey");
            System.out.println("4.edit travelDate");
            System.out.println("5.exit");
            System.out.println("enter your choice(1,2,3,4,5)");
            try {
                int option=scanner.nextInt();//for selecting the given options
                switch (option)
                {
                    //getting details from user like firstName....
                    case 1:
                        System.out.println("enter your first name");
                        String firstName=scanner.next();
                        System.out.println("enter your last name");
                        String lastName=scanner.next();
                        System.out.println("enter your mobile number");
                        String mobileNumber=isValidMobileNumber(scanner.next());
                        System.out.println("enter your gender");
                        String gender=isValidGender(scanner.next());
                        System.out.println("enter your email id");
                        String emailID=isValidEmailId(scanner.next());
                        System.out.println("enter your password");
                        String password=scanner.next();
                        users.add(new UserDetails(firstName,lastName,mobileNumber,emailID,gender,password));
                        break;
                    case 2:
                        //login
                        System.out.println("enter your email");
                        String emailOrNot=scanner.next();
                        System.out.println("enter password");
                        String passwordOrNot=scanner.next();
                        verifyLoginDetails(emailOrNot,passwordOrNot);
                        break;
                    case 3:
                        //plan journey
                        System.out.println("enter the emailId");
                        String emailIDCheck=isValidEmailId(scanner.next());//email validation
                        if(users.isEmpty())  //is there any users in the application
                        {
                            throw new NullPointerException("you want to sign up first");
                        }
                        for(UserDetails u:users)//is a user of the application
                        {
                            if(!u.email.equals(emailIDCheck))
                            {
                                throw new NoServiceAvailable("not a user");
                            }
                        }
                        //store source and destination places in hashset
                        HashSet<String> ABCSourcePlaces = new HashSet<>(Arrays.asList("visakhapatnam","vijayawada","guntur","nellore","kurnool"));
                        HashSet<String> ABCDestinationPlaces = new HashSet<>(Arrays.asList("visakhapatnam","vijayawada","guntur","nellore","kurnool"));
                        System.out.println("enter the source place(city) of journey ");
                        System.out.println(ABCSourcePlaces);
                        String userSource=isServiceAvailable(scanner.next(),ABCSourcePlaces);
                        System.out.println("enter the destination place(city) of journey");
                        System.out.println(ABCDestinationPlaces);
                        String userDestination=isServiceAvailable(ABCDestinationPlaces,scanner.next());
                        System.out.println("enter the journey date (yyyy-mm-dd)");
                        LocalDate date=isDate(scanner.next());
                        System.out.println("available seats are " + availableSeats);
                        System.out.println("enter no of seats you want");
                        int userSeats=scanner.nextInt();
                        if(availableSeats>userSeats)
                        {
                            availableSeats=availableSeats-userSeats;
                        }
                        else
                        {
                            System.out.println("you enter more seats than the available seats");
                            break;
                        }
                        int billAmount=generateBill(userSource,userDestination,userSeats,date);
                        System.out.println("total price = " +billAmount);
                        usersJourneyDetails.add(new UserJourneyDetails(emailIDCheck,userSource,userDestination,userSeats, date.toString(),billAmount));
                        break;
                    case 4:
                        System.out.println("enter the emailID");
                        String email=isValidEmailId(scanner.next());
                        int fact=0;
                        for(UserJourneyDetails u:usersJourneyDetails)
                        {
                            if(u.emailID.equals(email))
                            {
                                System.out.println("the journey from " + u.source + " to " + u.destination + " and the travel date is " + u.travelDate);
                                System.out.println("enter the new travel date (yyyy-mm-dd)");
                                LocalDate newDate;
                                newDate = isDate(scanner.next());
                                u.travelDate=newDate.toString();
                                System.out.println("your travel date is updated");
                                fact=1;
                                break;
                            }
                        }
                        if(fact==0)
                        {
                            System.out.println("no travel tickets are booked from your email");
                        }

                        break;
                    case 5:
                        System.out.println("ok you are exiting");
                        decider=false;
                        break;
                    default:
                        System.out.println("you need to enter the given options only");
                }
            }
            catch (InputMismatchException e)
            {
                System.out.println("please enter the given options(1,2,3,4,5) only");
                scanner.nextLine();
            }
            catch (InvalidMobileNumber | InValidGender | InvalidEmail | NoServiceAvailable | NullPointerException e )
            {
                System.out.println(e.getMessage());
            }
            catch (DateTimeException e)
            {
                System.out.println("not a valid date");
            }
        }
    }
    //checking the user entered a valid mobile number
    public static String isValidMobileNumber(String mobileNumber)
    {
        Pattern pattern=Pattern.compile("[789]\\d{9}");
        Matcher matcher=pattern.matcher(mobileNumber);
        if(!matcher.matches())
        {
            throw new InvalidMobileNumber("not a mobile number");
        }
        return mobileNumber;
    }
    public static String isValidGender(String gender)
    {
        Pattern pattern=Pattern.compile("(male|female|m|f)");
        Matcher matcher=pattern.matcher(gender.toLowerCase());
        if(!matcher.matches())
        {
            throw new InValidGender("not a valid gender");
        }
        return gender;
    }

    public static String isValidEmailId(String emailId)
    {
        Pattern pattern=Pattern.compile("[a-zA-Z][a-zA-Z0-9]+@[a-zA-Z0-9]+\\.com");
        Matcher matcher=pattern.matcher(emailId);
        if(!matcher.matches())
        {
            throw new InvalidEmail("not a emailID");
        }
        return emailId;
    }
    public static void verifyLoginDetails(String email,String password)
    {
        for(UserDetails user:users)
        {
            if(!(user.email.equals(email) && user.password.equals(password)))
            {
                System.out.println("invalid login details");
                return;
            }
            System.out.println("you are successfully logged in");
            return;
        }
        System.out.println("invalid login details");
    }
    public static String isServiceAvailable(String source,HashSet<String> obj)
    {
        if(!obj.contains(source))
        {
            throw new NoServiceAvailable("no services available at the place");
        }
        return source;
    }
    public static String isServiceAvailable(HashSet<String> obj,String destination)
    {
        if(!obj.contains(destination))
        {
            throw new NoServiceAvailable("no services available at the place");
        }
        return destination;
    }
    public static LocalDate isDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(date, formatter);
        if (localDate.isBefore(LocalDate.now())) {
            throw new DateTimeException("it should be a future date");
        }
        return localDate;
    }
    public static int generateBill(String source,String destination,int passangers,LocalDate date)
    {
        DayOfWeek dayOfWeek=date.getDayOfWeek();
        String day=dayOfWeek.toString();
        int amount=hashMap.get(source + "-" + destination);
        if(day.equals("sunday") || day.equals("saturday"))
        {
            return (amount*passangers)+200;
        }
        return (amount*passangers);
    }
}