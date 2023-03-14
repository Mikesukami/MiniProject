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
import java.text.SimpleDateFormat;
import java.util.Comparator;

public class staff extends person {
    protected int staffSalary;
    protected String staffId;
    protected String staffName;
    protected String staffRole;
    protected String staffUsername;
    protected String staffPassword;

    public staff() {

    }

    // addProduct
    public void addProduct() {
        Scanner kb = new Scanner(System.in);
        System.out.println("Create menu");
        System.out.println();

        // Load existing menu data from file
        JSONObject menuData = new JSONObject();
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader("./menu.json"));
            menuData = (JSONObject) obj;
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        // Create new menu object and add to menu data object
        menu newMenu = new menu();

        // Input validation for new menu ID
        String new_menuId;
        int try_count = 0;
        do {
            System.out.print("Enter New menu ID (format: m0x): ");
            new_menuId = kb.nextLine();
            try_count++;
        } while (!new_menuId.matches("m\\d\\d") && try_count < 3);

        if (!new_menuId.matches("m\\d\\d")) {

            // clear screen
            System.out.print("\033[H\033[2J");
            System.out.flush();

            System.out.println("Invalid menu ID format. Please try again later.");

            // cool down
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // clear screen
            System.out.print("\033[H\033[2J");
            System.out.flush();

            return;
        }
        newMenu.setMenuId(new_menuId);

        JSONObject newMenuJson = new JSONObject();
        newMenuJson.put("MenuID", newMenu.getMenuId());

        // Input validation for new menu name
        String new_menuName;
        try_count = 0;
        do {
            System.out.print("Enter New menu name (letters only): ");
            new_menuName = kb.nextLine();
            try_count++;
        } while (!new_menuName.matches("[a-zA-Z]+") && try_count < 3);

        if (!new_menuName.matches("[a-zA-Z]+")) {

            // clear screen
            System.out.print("\033[H\033[2J");
            System.out.flush();

            System.out.println("Invalid menu name format. Please try again later.");

            // cool down
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // clear screen
            System.out.print("\033[H\033[2J");
            System.out.flush();

            return;
        }
        newMenuJson.put("MenuName", new_menuName);

        // Input validation for new menu price
        int new_menuPrice = 0;
        try_count = 0;
        do {
            System.out.print("Enter New menu price (positive integer): ");
            try {
                new_menuPrice = Integer.parseInt(kb.nextLine());
            } catch (NumberFormatException e) {
                new_menuPrice = 0;
            }
            try_count++;
        } while (new_menuPrice <= 0 && try_count < 3);

        if (new_menuPrice <= 0) {

            // clear screen
            System.out.print("\033[H\033[2J");
            System.out.flush();

            System.out.println("Invalid menu price format. Please try again later.");

            // cool down
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // clear screen
            System.out.print("\033[H\033[2J");
            System.out.flush();

            return;
        }
        newMenuJson.put("MenuPrice", new_menuPrice);

        if (menuData.containsKey(newMenu.getMenuId())) {

            // clear screen
            System.out.print("\033[H\033[2J");
            System.out.flush();

            System.out.println("Menu ID already exists. Please try again later.");

            // cool down
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // clear screen
            System.out.print("\033[H\033[2J");
            System.out.flush();

            return;
        }

        menuData.put(newMenu.getMenuId(), newMenuJson);

        // Write updated menu data back to file
        try (FileWriter file = new FileWriter("./menu.json", false)) {
            file.write(menuData.toJSONString());

            // clear screen
            System.out.print("\033[H\033[2J");
            System.out.flush();

            System.out.println("==========================");
            System.out.println("Add Menu successfully!");
            System.out.println("==========================");

            // cool down
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // clear screen
            System.out.print("\033[H\033[2J");
            System.out.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println();
    }

    // deleteProduct
    public void deleteProduct() {
        Scanner kb = new Scanner(System.in);

        // clear screen
        System.out.print("\033[H\033[2J");
        System.out.flush();

        System.out.println("================");
        System.out.println("Delete menu");
        System.out.println("================");
        System.out.println();

        // Load existing menu data from file
        JSONObject menuData = new JSONObject();
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader("./menu.json"));
            menuData = (JSONObject) obj;
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        // Prompt the user to enter the menu ID to delete
        String menuId = "";
        int tries = 0;
        while (tries < 3) {
            System.out.print("Enter the menu ID to delete: ");
            menuId = kb.nextLine();
            if (menuData.containsKey(menuId)) {
                break;
            }
            tries++;
            System.out.println("Menu ID not found. Please try again.");
        }

        // Delete the menu item from the menu data object and write it back to file
        if (menuData.containsKey(menuId)) {
            menuData.remove(menuId);
            try (FileWriter file = new FileWriter("./menu.json", false)) {
                file.write(menuData.toJSONString());
                System.out.println("Delete Menu successfully.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {

            // clear screen
            System.out.print("\033[H\033[2J");
            System.out.flush();

            System.out.println("Menu ID not found after 3 attempts. Returning to main menu.");

            // cool down
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // clear screen
            System.out.print("\033[H\033[2J");
        }
        System.out.println();
    }

    // order menu
    public void orderMenu() {
        Scanner kb = new Scanner(System.in);
        System.out.println("Order menu");
        System.out.println();

        productView();

        // Load existing menu data from file
        JSONObject menuData = new JSONObject();
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader("./menu.json"));
            menuData = (JSONObject) obj;
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        // Prompt the user to enter the menu ID to order
        String menuId = "";
        int tries = 0;
        while (tries < 3) {
            System.out.print("Enter the menu ID to order: ");
            menuId = kb.nextLine();
            if (menuData.containsKey(menuId)) {
                break;
            }
            tries++;
            // if tries = 3, back to the main menu
            if (tries == 3) {

                // clear the screen
                System.out.print("\033[H\033[2J");
                System.out.flush();

                System.out.println("Menu ID not found after 3 attempts. Returning to main menu.");

                // cooldown
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // clear the screen
                System.out.print("\033[H\033[2J");
                System.out.flush();
                return;

            } else if (tries < 3) {
                System.out.println("Menu ID not found. Please try again.");
            }

        }

        // enter the qty
        int qty = 0;
        tries = 0;
        while (tries < 3) {
            System.out.print("Enter the quantity: ");
            try {
                qty = Integer.parseInt(kb.nextLine());
            } catch (NumberFormatException e) {
                qty = 0;
            }
            if (qty > 0) {
                break;
            }
            tries++;

            // back to the main menu
            if (tries == 3) {
                // clear the screen
                System.out.print("\033[H\033[2J");
                System.out.flush();

                System.out.println("Invalid quantity after 3 attempts. Returning to main menu.");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // clear the screen
                System.out.print("\033[H\033[2J");
                System.out.flush();
                return;
            } else if (tries < 3) {

                System.out.println("Invalid quantity. Please try again.");
            }
        }

        // sum the total price
        JSONObject menu = (JSONObject) menuData.get(menuId);
        int price = Integer.parseInt(menu.get("MenuPrice").toString());
        int total = qty * price;

        // show orderDate and orderTime
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String orderDate = formatter.format(date);

        // Display the order details
        // clear the screen
        System.out.print("\033[H\033[2J");
        System.out.flush();

        // display the order details
        System.out.println("====================================");
        System.out.println("\tOrder details");
        System.out.println("====================================");
        System.out.println("Menu ID: " + menuId);
        System.out.println("Menu Name: " + menu.get("MenuName"));
        System.out.println("Quantity: " + qty);
        System.out.println("------------------------------------");
        System.out.println("\tTotal Price: " + total);
        System.out.println("====================================");
        System.out.println();

        // Prompt the user to confirm the order
        System.out.print("Confirm order? (Y/N): ");
        String confirm = kb.nextLine();
        if (confirm.equalsIgnoreCase("Y")) {
            // Load existing order data from file
            int cash = 0;
            Boolean orderPrint = false;
            // check payment
            tries = 0;
            while (tries != 3) {
                System.out.println("Enter money to pay : ");
                cash = kb.nextInt();

                if (cash < total) {
                    System.out.println("Cash not enough!");
                } else {
                    orderPrint = true;
                    break;
                }

                tries++;
            }

            if (orderPrint == true) {
                JSONObject orderData = new JSONObject();
                try {
                    Object obj = parser.parse(new FileReader("./order.json"));
                    orderData = (JSONObject) obj;
                } catch (IOException | ParseException e) {
                    e.printStackTrace();
                }

                // Generate a new order ID
                String new_orderId = "Order" + (orderData.size() + 1);

                // Create a new order object
                order newOrder = new order();
                newOrder.setOrderId(new_orderId);
                newOrder.setMenuName(menu.get("MenuName").toString());
                newOrder.setOrderDate(orderDate);
                newOrder.setMenuId(menuId);
                newOrder.setQty(qty);
                newOrder.setTotal(total);

                // Add the new order to the order data object and write it back to file
                JSONObject newOrderJson = new JSONObject();
                newOrderJson.put("OrderID", newOrder.getOrderId());
                newOrderJson.put("OrderDate", newOrder.getOrderDate());
                newOrderJson.put("MenuID", newOrder.getMenuId());
                newOrderJson.put("MenuName", newOrder.getMenuName());
                newOrderJson.put("Qty", newOrder.getQty());
                newOrderJson.put("Total", newOrder.getTotal());
                orderData.put(newOrder.getOrderId(), newOrderJson);
                try (FileWriter file = new FileWriter("./order.json", false)) {
                    file.write(orderData.toJSONString());

                    // clear screen
                    System.out.print("\033[H\033[2J");
                    System.out.flush();

                    System.out.println("===================");
                    System.out.println("Order successfully.");
                    System.out.println("===================");

                    // cooldown
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    // clear screen
                    System.out.print("\033[H\033[2J");
                    System.out.flush();

                    readStaff getStaffName = new readStaff(username, password);
                    // display the bill
                    System.out.println("====================================");
                    System.out.println("\t       Receipt");
                    System.out.println("====================================");
                    System.out.println("Order Date: " + orderDate);
                    System.out.println("Order Taker:" + getStaffName.getName());
                    System.out.println("Menu ID: " + menuId);
                    System.out.println("Menu Name: " + menu.get("MenuName"));
                    System.out.println("Quantity: " + qty);
                    System.out.println("------------------------------------");
                    System.out.println("\tTotal Price: " + total);
                    System.out.println("\tChange:  "  + (cash - total));
                    System.out.println("------------------------------------");
                    System.out.println("           THANK YOU !             ");
                    System.out.println("====================================");

                    // press any key to continue
                    System.out.println("Press enter to continue.");
                    try {
                        System.in.read();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    // clear screen
                    System.out.print("\033[H\033[2J");
                    System.out.flush();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {

            // clear screen
            System.out.print("\033[H\033[2J");
            System.out.flush();

            System.out.println("===================");
            System.out.println("Order cancelled.");
            System.out.println("===================");

            // cool down
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // clear screen
            System.out.print("\033[H\033[2J");
            System.out.flush();
        }

    }

    // order history
    public void orderHistory() {

        // clear screen
        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.println("Order history");
        System.out.println();

        // Load existing order data from file
        List<JSONObject> orderList = new ArrayList<>();
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader("order.json"));
            JSONObject jsonObject = (JSONObject) obj;
            for (Object key : jsonObject.keySet()) {
                String orderId = (String) key;
                JSONObject order = (JSONObject) jsonObject.get(orderId);
                order.put("OrderId", orderId);
                orderList.add(order);
                // orderList.add((JSONObject) orderData.get(key));

            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        // Sort the order list by order ID
        orderList.sort(new Comparator<JSONObject>() {
            @Override
            public int compare(JSONObject o1, JSONObject o2) {
                String orderID1 = (String) o1.get("OrderId");
                String orderID2 = (String) o2.get("OrderId");
                return orderID1.compareTo(orderID2);
            }
        });

        // Display the order history
        System.out.println(
                "============================================================================================================");
        System.out.println("\t\t\t\t\tOrder history");
        System.out.println(
                "============================================================================================================");
        System.out.println("Order ID\tOrder Date\t\tMenu ID\t\tMenu Name\t\tQty\t\tTotal");
        System.out.println(
                "------------------------------------------------------------------------------------------------------------");
        for (JSONObject order : orderList) {
            System.out.println(order.get("OrderID") + "\t\t" + order.get("OrderDate") + "\t" + order.get("MenuID")
                    + "\t\t" + order.get("MenuName") + "\t\t" + order.get("Qty") + "\t\t" + order.get("Total"));
        }
        System.out.println(
                "============================================================================================================");
        System.out.println();

        // press any key to continue
        System.out.println("Press enter to continue.");
        try {
            System.in.read();
        } catch (Exception e) {
        }

        // clear screen
        System.out.print("\033[H\033[2J");
        System.out.flush();

    }

    public void displayStaff() {
        // clear screen
        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.println();

        // Load existing staff data from file
        List<JSONObject> staffList = new ArrayList<>();
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader("staff.json"));
            JSONObject jsonObject = (JSONObject) obj;
            for (Object key : jsonObject.keySet()) {
                String staffId = (String) key;
                JSONObject staff = (JSONObject) jsonObject.get(staffId);
                staff.put("StaffId", staffId);
                staffList.add(staff);
                // orderList.add((JSONObject) orderData.get(key));

            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        // Sort the staff list by staff ID
        staffList.sort(new Comparator<JSONObject>() {
            @Override
            public int compare(JSONObject o1, JSONObject o2) {
                String staffID1 = (String) o1.get("StaffId");
                String staffID2 = (String) o2.get("StaffId");
                return staffID1.compareTo(staffID2);
            }
        });

        // Display the staff list
        System.out.println(
                "============================================================================================================");
        System.out.println("\t\t\t\t\tStaff List");
        System.out.println(
                "============================================================================================================");
        System.out.println("Staff ID\tStaff Name\t\t\tStaff Role");
        System.out.println(
                "------------------------------------------------------------------------------------------------------------");
        for (JSONObject staff : staffList) {
            System.out.println(staff.get("StaffId") + "\t\t" + staff.get("name") + "\t\t\t" + staff.get("role"));
        }
        System.out.println(
                "============================================================================================================");
        System.out.println();

        // press any key to continue
        System.out.println("Press enter to continue.");
        try {
            System.in.read();
        } catch (Exception e) {
        }

        // clear screen
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    // setter getter method for staff
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
