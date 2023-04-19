import java.util.Scanner;
import java.util.InputMismatchException;
import java.io.Console;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import netscape.javascript.JSObject;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Time;
import java.util.Iterator;

public class callingP3A1 {
    public static void main(String[] args) {
        Scanner kb = new Scanner(System.in);
        int ans1 = 0;
        String username;
        String password;
        int i = 0;

        // Start
        while (true) {

            // clear screen
            System.out.print("\033[H\033[2J");
            System.out.flush();

            System.out.println("");
            System.out.println("===================================");
            System.out.println("     Welcome to P3A1 Restaurant     ");
            System.out.println("===================================");
            System.out.println("");
            System.out.println("=== Select ===");
            System.out.println("1 Login as staff");
            System.out.println("2 close shop");
            System.out.println("");
            System.out.println("-----------------------------------");

            while (true) {
                try {
                    System.out.print("Input your Choice : ");
                    ans1 = kb.nextInt();

                    if (ans1 == 1 || ans1 == 2) {
                        break;
                    } else {
                        System.out.println("Invalid input. Please enter either 1 or 2.");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter a valid integer.");
                    kb.next(); // consume the invalid input to prevent an infinite loop
                }
            }

            try {

                for (;;) {
                    // If select Login as staff
                    if (ans1 == 1) {
                        // clear screen
                        System.out.print("\033[H\033[2J");
                        System.out.flush();

                        System.out.println("");
                        System.out.println("===================================");
                        System.out.println("\tStaff Login");
                        System.out.println("===================================");
                        System.out.println("");
                        System.out.println("== Login ==");
                        System.out.println("");
                        System.out.println("-----------------------------------");

                        //
                        for (i = 0; i < 3; i++) {
                            System.out.print("Enter you username [" + (i + 1) + "] : ");
                            username = kb.next();

                            Console console = System.console();
                            if (console == null) {
                                System.out.println("No console: not in interactive mode!");
                                System.exit(0);
                            }
                            char[] passwordArray = console.readPassword("Enter your password: ");
                            password = new String(passwordArray);

                            readStaff staffCheck = new readStaff(username, password);

                            if (staffCheck.isLogin() == true) {
                                String role = staffCheck.getRole();
                                String name = staffCheck.getName();

                                LogInOut_File log = new LogInOut_File(name, role);

                                if (role.equals("Manager")) {

                                    for (;;) {

                                        System.out.println("");
                                        System.out.println("===================================");
                                        System.out.println("Welcome " + name + "!");
                                        System.out.println("Role : " + role);
                                        System.out.println("===================================");

                                        System.out.println("");
                                        System.out.println("=== Manager Menu ===");
                                        System.out.println("1. View Product");
                                        System.out.println("2. Order");
                                        System.out.println("3. Report");
                                        System.out.println("4. Add Menu");
                                        System.out.println("5. Delete Menu");
                                        System.out.println("6. Add Staff");
                                        System.out.println("7. Delete Staff");
                                        System.out.println("8. Order History");
                                        System.out.println("9. View Staff");
                                        System.out.println("10. History Staff Login");
                                        System.out.println("11. Logout");
                                        System.out.println("---------------------");
                                        System.out.print("Input : ");
                                        int ans2 = kb.nextInt();

                                        if (ans2 == 1) {

                                            System.out.println("View Product");
                                            person view = new person();
                                            view.viewMenu();

                                            System.out.println("Press enter to continue...");
                                            try {
                                                System.in.read();
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }

                                            // clear screen
                                            System.out.print("\033[H\033[2J");
                                            System.out.flush();


                                        } else if (ans2 == 2) {
                                            System.out.println("Order");
                                            staff order = new staff();
                                            order.setStaffName(name);
                                            order.order();

                                        } else if (ans2 == 3) {
                                            System.out.println("Report");
                                            manager MgReport = new manager();
                                            MgReport.report();
                                        } else if (ans2 == 4) {
                                            System.out.println("Add Menu");
                                            staff add = new staff();
                                            add.addMenu();
                                        } else if (ans2 == 5) {
                                            staff delete = new staff();
                                            delete.deleteMenu();
                                        } else if (ans2 == 6) {
                                            System.out.println("Add Staff");
                                            manager addStaff = new manager();
                                            addStaff.addStaff();
                                        } else if (ans2 == 7) {
                                            System.out.println("Delete Staff");
                                            manager delete = new manager();
                                            delete.deleteStaff();
                                        } else if (ans2 == 8) {
                                            staff orderHistory = new staff();
                                            orderHistory.orderHistory();
                                        } else if (ans2 == 9) {
                                            System.out.println("View Staff");
                                            manager viewStaff = new manager();
                                            viewStaff.displayStaff();
                                        } else if (ans2 == 10) {
                                            log.printLoginHistory();
                                        } else if (ans2 == 11) {

                                            System.out.println("");
                                            System.out.println("----------------");
                                            System.out.println("Logged out.");
                                            System.out.println("----------------");
                                            System.out.println("");

                                            log.logout_history();

                                            // cooldown
                                            try {
                                                Thread.sleep(1000);
                                            } catch (InterruptedException e) {
                                                e.printStackTrace();
                                            }
                                            break;
                                        } else {
                                            System.out.println("Please Try Again!");
                                        }
                                    }
                                } else {
                                    for (;;) {
                                        System.out.println("");
                                        System.out.println("===================================");
                                        System.out.println("Welcome " + name + "!");
                                        System.out.println("Role : " + role);
                                        System.out.println("===================================");

                                        System.out.println("");
                                        System.out.println("=== Employee Menu ===");
                                        System.out.println("1. View Product");
                                        System.out.println("2. Order");
                                        System.out.println("3. Add Menu");
                                        System.out.println("4. Delete Menu");
                                        System.out.println("5. Order History");
                                        System.out.println("6. View Staff");
                                        System.out.println("7. Logout");
                                        System.out.println("---------------------");
                                        System.out.print("Input : ");
                                        int ans2 = kb.nextInt();

                                        if (ans2 == 1) {
                                            System.out.println("View Product");
                                            person view = new person();
                                            view.viewMenu();

                                            System.out.println("Press enter to continue...");
                                            try {
                                                System.in.read();
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }

                                            // clear screen
                                            System.out.print("\033[H\033[2J");
                                            System.out.flush();

                                        } else if (ans2 == 2) {
                                            System.out.println("Order");
                                            staff order = new staff();
                                            order.setStaffName(name);
                                            order.order();
                                        } else if (ans2 == 3) {
                                            System.out.println("Add Menu");
                                            staff add = new staff();
                                            add.addMenu();
                                        } else if (ans2 == 4) {
                                            staff delete = new staff();
                                            delete.deleteMenu();
                                        } else if (ans2 == 5) {
                                            staff orderHistory = new staff();
                                            orderHistory.orderHistory();
                                        } else if (ans2 == 6) {
                                            System.out.println("View Staff");
                                            employee viewStaff = new employee();
                                            viewStaff.displayStaff();
                                        } else if (ans2 == 7) {
                                            System.out.println("");
                                            System.out.println("----------------");
                                            System.out.println("Logged out.");
                                            System.out.println("----------------");
                                            System.out.println("");

                                            log.logout_history();

                                            // cooldown
                                            try {
                                                Thread.sleep(1000);
                                            } catch (InterruptedException e) {
                                                e.printStackTrace();
                                            }
                                            break;
                                        } else {
                                            System.out.println("Please Try Again!");
                                        }
                                    }

                                }

                                break;
                            } else if (i < 2) {
                                System.out.println("\n**Username or Password is incorrect**");
                                
                            } else {
                                for(int j = 5; j > 0; j--){
                                    // clear screen
                                    System.out.print("\033[H\033[2J");
                                    System.out.flush();
                                    try {
                                        System.out.println(
                                                "\nYou have entered the wrong username or password 3 times. Cooldown " + j + " seconds Please try again later."
                                                );
                                        // cooldown
                                        
                                            Thread.sleep(1000);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                    
                                        

                                }
                                
                            }
                        }

                        break;
                    }

                    //this  

                    // If select close shop
                    else if (ans1 == 2) {
                        // clear screen
                        System.out.print("\033[H\033[2J");
                        System.out.flush();

                        System.out.println("The shop is closing.");

                        // cooldown
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        // clear screen
                        System.out.print("\033[H\033[2J");
                        System.out.flush();

                        System.out.println("The shop is closing. .");

                        // cooldown
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        // clear screen
                        System.out.print("\033[H\033[2J");
                        System.out.flush();

                        System.out.println("The shop is closing. . . ");

                        // cooldown
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        // clear screen
                        System.out.print("\033[H\033[2J");
                        System.out.flush();

                        System.out.println("===================================");
                        System.out.println("\tThe shop is closed. ");
                        System.out.println("===================================");
                        System.out.println("Thank you for using our system!");
                        System.out.println("Have a nice day! ^ ^");
                        System.out.println("See you next time!");
                        System.out.println("===================================");

                        System.exit(0);
                        return;
                    } else {
                        System.out.println("Please Try Again!");
                    }
                }
            } catch (Exception e) {
                // TODO: handle exception
                System.out.println(e);

            }
        }
    }
}