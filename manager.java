import java.util.*;
import java.util.Scanner;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.Iterator;
import java.io.FileWriter;

public class manager extends staff {
    
    private double totalSales;

    public manager() {

    }

    public void addStaff() {
        Scanner kb = new Scanner(System.in);
        // clear screen
        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.println("============");
        System.out.println("Add staff");
        System.out.println("============");
        System.out.println();

        // Load existing staff data from file
        JSONObject staffData = new JSONObject();
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader("./staff.json"));
            staffData = (JSONObject) obj;
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        // Prompt the user to enter the staff details
        // Staff ID
        staff newStaff = new staff();
        String new_staffId;
        int try_count = 0;
        do {
            System.out.print("Enter New Staff ID (format : s0x): ");
            new_staffId = kb.nextLine();
            try_count++;
        } while (!new_staffId.matches("s\\d\\d") && try_count < 3);

        if (!new_staffId.matches("s\\d\\d")) {

            //clear screen
            System.out.print("\033[H\033[2J");
            System.out.flush();

            System.out.println("Invalid Staff ID format. Please try again.");

            //cooldown
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //clear screen
            System.out.print("\033[H\033[2J");
            System.out.flush();
            

            return;
        }
        newStaff.setStaffId(new_staffId);

        JSONObject newStaffJson = new JSONObject();
        newStaffJson.put("StaffId", newStaff.getStaffId());

        // Staff Name
        String new_staffName;
        try_count = 0;
        do {
            System.out.print("Enter New Staff Name: ");
            new_staffName = kb.nextLine();
            try_count++;
        } while (!new_staffName.matches("[a-zA-Z ]+") && try_count < 3);

        if (!new_staffName.matches("[a-zA-Z ]+")) {

            //clear screen
            System.out.print("\033[H\033[2J");
            System.out.flush();

            System.out.println("Invalid Staff Name format. Please try again.");

            //cooldown
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //clear screen
            System.out.print("\033[H\033[2J");
            System.out.flush();
            return;
        }

        newStaff.setStaffName(new_staffName);
        newStaffJson.put("name", newStaff.getStaffName());

        // Staff username have bug****
        String new_staffUsername;

        int try_count2 = 0;
        do{
            System.out.print("Enter New Staff Username: ");
            new_staffUsername = kb.nextLine();
            try_count2++;
        }while(staffData.containsKey(newStaff.getStaffUsername()) && try_count2 < 3);

        newStaff.setStaffUsername(new_staffUsername);
        newStaffJson.put("username", newStaff.getStaffUsername());

        // Staff password
        String new_staffPassword;

        System.out.print("Enter New Staff Password: ");
        new_staffPassword = kb.nextLine();

        newStaff.setStaffPassword(new_staffPassword);
        newStaffJson.put("password", newStaff.getStaffPassword());

        // Staff role
        String new_staffRole;
        try_count = 0;
        do{
            System.out.print("Enter New Staff Role (Manager or Employee): ");
            new_staffRole = kb.nextLine();
        } while (!new_staffRole.matches("Manager|Employee"));


        if (new_staffRole.matches("Manager|Employee")) {
            newStaff.setStaffRole(new_staffRole);
            newStaffJson.put("role", newStaff.getStaffRole());
        }

        // Staff salary
        int new_staffSalary;
        try_count = 0;
        do {
            System.out.print("Enter New Staff Salary (positive integer): ");
            try {
                new_staffSalary = Integer.parseInt(kb.nextLine());
            } catch (NumberFormatException e) {
                new_staffSalary = 0;
            }
            try_count++;
        } while (new_staffSalary <= 0 && try_count < 3);

        if (new_staffSalary <= 0) {

            //clear screen
            System.out.print("\033[H\033[2J");
            System.out.flush();

            System.out.println("Invalid Staff Salary. Please try again.");

            //cooldown
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //clear screen

            return;
        }

        newStaff.setStaffSalary(new_staffSalary);
        newStaffJson.put("salary", newStaff.getStaffSalary());


        if(staffData.containsKey(newStaff.getStaffId())){

            //clear screen
            System.out.print("\033[H\033[2J");
            System.out.flush();
            System.out.println("Staff ID already exists. Please try again.");

            //cooldown
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //clear screen
            System.out.print("\033[H\033[2J");
            System.out.flush();

            return;
        }

        staffData.put(newStaff.getStaffId(), newStaffJson);

        // Write updated staff data back to file
        try (FileWriter file = new FileWriter("./staff.json")) {
            file.write(staffData.toJSONString());

            //clear screen
            System.out.print("\033[H\033[2J");
            System.out.flush();

            System.out.println("");
            System.out.println("--------------------------------");
            System.out.println("   Staff added successfully.");
            System.out.println("--------------------------------");


            //cooldown
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //clear screen
            System.out.print("\033[H\033[2J");
            System.out.flush();

            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("");

        

    }

    public void deleteStaff(){
        //delete staff from staff.json file by staff id
        Scanner kb = new Scanner(System.in);
        //clear screen
        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.println("=================");
        System.out.println("Delete staff");
        System.out.println("=================");
        System.out.println();

        // Load existing staff data from file
        JSONObject staffData = new JSONObject();
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader("./staff.json"));
            staffData = (JSONObject) obj;
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        // Prompt the user to enter the staff details
        // Staff ID
        String staffId = "";
        int tries = 0;
        while(tries < 3){
            System.out.print("Enter the staff ID to delete: ");
            staffId = kb.nextLine();
            if(staffData.containsKey(staffId)){
                break;
            }
            else{
                System.out.println("Staff ID does not exist. Please try again.");
                tries++;
            }

            if(tries == 3){
                //clear screen
                System.out.print("\033[H\033[2J");
                System.out.flush();
                System.out.println("Invalid Staff ID. Please try again.");

                //cooldown
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //clear screen
                System.out.print("\033[H\033[2J");
                System.out.flush();
                return;
            }
            
        }

        if(staffData.containsKey(staffId)){
            staffData.remove(staffId);
            // Write updated staff data back to file
            try (FileWriter file = new FileWriter("./staff.json")) {
                file.write(staffData.toJSONString());

                //clear screen
                System.out.print("\033[H\033[2J");
                System.out.flush();

                System.out.println("");
                System.out.println("--------------------------------");
                System.out.println("Staff deleted successfully.");
                System.out.println("--------------------------------");

                //cooldown
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //clear screen
                System.out.print("\033[H\033[2J");
                System.out.flush();

                file.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else{
            System.out.println("Staff ID does not exist. Please try again.");
            return;
        }
}
    
    public void report() {
        try {
            // Parse the order.json file
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(new FileReader("order.json"));
            JSONObject jsonObject = (JSONObject) obj;

            // Iterate over the orders and calculate the total sales
            double total = 0;
            Iterator<?> iterator = jsonObject.keySet().iterator();
            while (iterator.hasNext()) {
                String orderId = (String) iterator.next();
                JSONObject order = (JSONObject) jsonObject.get(orderId);
                double orderTotal = (double) order.get("Total");
                total += orderTotal;
            }

            // Set the total sales and display the summary
            
            setTotalSales(total);
            //clear screen
            System.out.print("\033[H\033[2J");
            System.out.flush();
            System.out.println("================================");
            System.out.println("Sales Report");
            System.out.println("================================");
            System.out.println("Total sales: " + totalSales);
            System.out.println("--------------------------------");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setTotalSales(double totalSale) {
        this.totalSales = totalSale;
    }

    public double getTotalSale() {
        return totalSales;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffSalary(int staffSalary) {
        this.staffSalary = staffSalary;
    }

    public int getStaffSalary() {
        return staffSalary;
    }

    public void setStaffUsername(String staffUsername) {
        this.staffUsername = staffUsername;
    }

    public String getStaffUsername() {
        return staffUsername;
    }

    public void setStaffPassword(String staffPassword) {
        this.staffPassword = staffPassword;
    }

    public String getStaffPassword() {
        return staffPassword;
    }

    public void setStaffRole(String staffRole) {
        this.staffRole = staffRole;
    }

    public String getStaffRole() {
        return staffRole;
    }



}
