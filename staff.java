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

    final static String food_order_file_name = "order_food.json";
    final static String menu_file_name = "menu.json";
    final static String receipt_file_name = "receipt.json";

    public staff() {

    }

    //addMenu method into menu.json file
    public void addMenu() {
        // Read existing menu items from menu.json file
        JSONParser parser = new JSONParser();
        try {
            JSONArray jsonArray = (JSONArray) parser.parse(new FileReader("menu.json"));

            // Generate new MenuID based on size of existing menu items
            int newMenuId = jsonArray.size() + 1;

            // Read user input for MenuName and MenuPrice
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter MenuName: ");
            String menuName = scanner.nextLine();
            System.out.print("Enter MenuPrice: ");
            long menuPrice = scanner.nextLong();
            scanner.nextLine(); // Consume newline character

            // Create new menu item as JSONObject
            JSONObject newMenu = new JSONObject();
            newMenu.put("MenuID", String.valueOf(newMenuId));
            newMenu.put("MenuName", menuName);
            newMenu.put("MenuPrice", menuPrice);

            // Add new menu item to the JSONArray
            jsonArray.add(newMenu);

            // Write the updated JSONArray back to menu.json file
            FileWriter fileWriter = new FileWriter("menu.json");
            fileWriter.write(jsonArray.toJSONString());
            fileWriter.flush();
            fileWriter.close();

            System.out.println("New menu item added successfully!");

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    //deleteMenu
    public void deleteMenu() {
        try {
            // Load menu.json file into JSONArray
            JSONParser parser = new JSONParser();
            JSONArray jsonArray = (JSONArray) parser.parse(new FileReader("menu.json"));
    
            // Prompt user for MenuID to delete

            //show menu list form menu.json file
            System.out.println("Menu List");
            System.out.println("MenuID\tMenuName\tMenuPrice");
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject menu = (JSONObject) jsonArray.get(i);
                String menuId = (String) menu.get("MenuID");
                String menuName = (String) menu.get("MenuName");
                long menuPrice = (long) menu.get("MenuPrice");
                System.out.println(menuId + "\t" + menuName + "\t" + menuPrice);
            }
            System.out.println();

            System.out.print("Enter MenuID to delete: ");
            Scanner scanner = new Scanner(System.in);
            String menuIdToDelete = scanner.nextLine();
    
            // Loop through the JSONArray to find the menu item with matching MenuID
            boolean found = false;
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject menu = (JSONObject) jsonArray.get(i);
                String menuId = (String) menu.get("MenuID");
                if (menuId.equals(menuIdToDelete)) {
                    // Remove the menu item from the JSONArray
                    jsonArray.remove(i);
                    found = true;
                    break;
                }
            }
    
            if (found) {
                // Write the updated JSONArray back to menu.json file
                FileWriter fileWriter = new FileWriter("menu.json");
                fileWriter.write(jsonArray.toJSONString());
                fileWriter.flush();
                fileWriter.close();
    
                System.out.println("Menu item with MenuID " + menuIdToDelete + " deleted successfully!");
            } else {
                System.out.println("Menu item with MenuID " + menuIdToDelete + " not found.");
            }
    
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
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

    // orderMenu new
    public void order() {
        ArrayList<String> Food_Menu = new ArrayList<>();
        ArrayList<String> Food_Name = new ArrayList<>();
        ArrayList<Integer> Prices = new ArrayList<>();
        ArrayList<Integer> Qty = new ArrayList<>();
        int total_price = 0;
        float change = 0;
        

        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String orderDate = formatter.format(date);

        Scanner food_input = new Scanner(System.in);

        JSONParser parser = new JSONParser();

        try {

            FileReader menu_reader = new FileReader(menu_file_name);
            JSONArray menu_Load = (JSONArray) parser.parse(menu_reader);

            FileReader order_reader = new FileReader(food_order_file_name);
            JSONArray order_Load = (JSONArray) parser.parse(order_reader);

            FileReader receipt_reader = new FileReader(receipt_file_name);
            JSONArray receipt_Load = (JSONArray) parser.parse(receipt_reader);

            // add new order in this object to get value

            JSONObject new_Order = new JSONObject();

            // generate order id and add it to object in line 32
            int order_id = order_Load.size() + 1;
            new_Order.put("OrderID", order_id);

            // System.out.print("Enter order number: ");
            // int order_id = food_input.nextInt(); food_input.nextLine();
            // new_Order.put("OrderID", order_id);

            System.out.println(staffName);
            System.out.println(staffUsername);
            System.out.println("============================================");
            System.out.println("\tMenuID\tMenuName\tMenuPrice");
            System.out.println("============================================");
            for (Object menu : menu_Load) {
                JSONObject menulist = (JSONObject) menu;
                System.out.print("\t" + menulist.get("MenuID") + "\t");
                System.out.print(menulist.get("MenuName") + "\t");
                System.out.print(menulist.get("MenuPrice") + "\n");
            }

            int sum = 0;

            while (true) {

                try {

                    // System.out.println("\tMenuID\tMenuName\tMenuPrice");

                    // for(Object menu : menu_Load) {
                    // JSONObject menulist = (JSONObject) menu;
                    // System.out.print("\t" + menulist.get("MenuID") + "\t");
                    // System.out.print(menulist.get("MenuName") + "\t");
                    // System.out.print(menulist.get("MenuPrice") + "\n");
                    // }

                    System.out.println();

                    if (Food_Menu.size() == 0) {

                    } else {
                        System.out.println("\tMenuID\t  MenuName\tMenuPrice\tQty\tTotal");
                    }

                    for (int i = 0; i < Food_Menu.size(); i++) {
                        System.out.print("\t " +
                                Food_Menu.get(i).toString() + "\t " +
                                Food_Name.get(i).toString() + "\t " +
                                Prices.get(i).toString() + "\t " + " \t" +
                                Qty.get(i).toString() + "\t ");
                        sum = Prices.get(i) * Qty.get(i);
                        System.out.println(sum);
                    }
                    System.out.println();

                    System.out.print("Enter MenuID (Type 'clear' to clear orders ,'done' to finish or 'none' to cancel):");
                    String food_Item = food_input.nextLine();
                    if (food_Item.equals("done")) {

                        // clear screen
                        System.out.print("\033[H\033[2J");
                        System.out.flush();

                        break;

                    } else if (food_Item.equals("clear")) {
                        Food_Menu.clear();
                        Food_Name.clear();
                        Prices.clear();
                        Qty.clear();
                        System.out.print("Order clear successfully. \n");
                    } else if(food_Item.equals("n")){
                        System.out.print("Cancel order. \n");
                    } 

                    for (Object menuID : menu_Load) {
                        JSONObject menu_list = (JSONObject) menuID;

                        if (food_Item.equals(menu_list.get("MenuID").toString())) {
                            Food_Menu.add(food_Item);
                            Food_Name.add(menu_list.get("MenuName").toString());
                            Integer price = Integer.parseInt(menu_list.get("MenuPrice").toString());
                            // System.out.print(price);
                            Prices.add(price);

                            //enter quantity for menu name 
                            System.out.print("Enter quantity  for " + menu_list.get("MenuName").toString() + ": ");
                            // System.out.print("Enter quantity  for " + food_Item + ": ");
                            int quantity = food_input.nextInt();
                            food_input.nextLine();
                            Qty.add(quantity);

                        }

                    }
                    // Food_Menu.add(food_Item);

                    // new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            System.out.println("--------------------------------------------------------");
            System.out.println("\t\t\tOrder detail\n");
            System.out.println("--------------------------------------------------------");
            System.out.println("\tMenuID\t  MenuName\tMenuPrice\tQty\tTotal");
            for (int i = 0; i < Food_Menu.size(); i++) {
                System.out.print("\t " +
                        Food_Menu.get(i).toString() + "\t " +
                        Food_Name.get(i).toString() + "\t " +
                        Prices.get(i).toString() + "\t " + " \t" +
                        Qty.get(i).toString() + "\t ");
                sum = Prices.get(i) * Qty.get(i);
                total_price += sum;
                System.out.println(sum);
            }
            System.out.println("----------------------------------------");
            System.out.println("Total price: " + total_price);
            System.out.println("----------------------------------------");

            // confirm order by fill Y or N
            System.out.print("Confirm order? (Y/N): ");
            String confirm = food_input.nextLine();
            if (confirm.equals("Y")) {

                // payment
                int cash = 0;
                boolean isCash = false;

                int tries = 0;

                while (tries != 3) {
                    System.out.print("Enter cash: ");
                    cash = food_input.nextInt();
                    food_input.nextLine();
                    if (cash < total_price) {
                        System.out.println("Cash is not enough. Please try again.");
                        tries++;
                    } else {
                        isCash = true;
                        break;
                    }
                }

                if (tries == 3) {
                    System.out.println("You have entered wrong cash 3 times. Please try again later.");
                }

                if (cash >= total_price) {
                    System.out.println("Change: " + (cash - total_price));
                }

                JSONObject new_receipt = new JSONObject();
                // generate receipt id and date time for order receipt
                int receipt_id = receipt_Load.size() + 1;
                new_receipt.put("ReceiptID", receipt_id);
                new_receipt.put("OrderID", order_id);

                if (isCash) {

                    // clear screen
                    System.out.print("\033[H\033[2J");
                    System.out.flush();



                    System.out.println(staffId);

                    System.out.println("============================================================");
                    System.out.println("\t\t\tReceipt");
                    System.out.println("============================================================");
                    System.out.println("Receipt ID: " + receipt_id);
                    System.out.println("Order ID: " + order_id);
                    System.out.println("Order Date: " + orderDate);
                    System.out.println("Order Taker:" + staffName);
                    

                    // print order detail
                    System.out.println("------------------------------------------------------------");
                    System.out.println("\t\t\tOrder detail");
                    System.out.println("------------------------------------------------------------");
                    System.out.println("\tMenuID\t  MenuName\tMenuPrice\tQty\tTotal");
                    for (int i = 0; i < Food_Menu.size(); i++) {
                        System.out.print("\t " +
                                Food_Menu.get(i).toString() + "\t " +
                                Food_Name.get(i).toString() + "\t " +
                                Prices.get(i).toString() + "\t " + " \t" +
                                Qty.get(i).toString() + "\t ");
                        sum = Prices.get(i) * Qty.get(i);
                        System.out.println(sum);
                    }

                    // print total price
                    System.out.println("------------------------------------------------------------");
                    System.out.println("Total price: " + total_price);
                    System.out.println("------------------------------------------------------------");

                    // print cash and change

                    change = cash - total_price;

                    System.out.println("Cash: " + cash);
                    System.out.println("Change: " + change);
                    System.out.println("============================================================");

                    // add receipt to receipt json array

                    System.out.println("Payment successful.");
                }

                // when done add food menu and price, add them to object
                new_Order.put("FoodMenu", Food_Menu);
                new_Order.put("FoodName", Food_Name);
                new_Order.put("Prices", Prices);
                new_Order.put("Quantity", Qty);

                // put to receipt json array
                new_receipt.put("FoodMenu", Food_Menu);
                new_receipt.put("FoodName", Food_Name);
                new_receipt.put("Prices", Prices);
                new_receipt.put("Quantity", Qty);
                new_receipt.put("Change", change);
                new_receipt.put("Total Price", total_price);
                new_receipt.put("Cash", cash);
                new_receipt.put("Order Date", orderDate);
                new_receipt.put("Order Taker", staffName);

                //clear staffName 
                //staffName = "";

                // calculate price into total price
                // for (int i = 0; i < Prices.size(); i++) {
                // total_price += Prices.get(i) * Qty.get(i);
                // // System.out.println("Summary for index " + i + ": " + total_price);
                // }

                // for(int price : Prices) { // take 'price' in array 'Prices' to calculate and
                // get value in 'total_price'
                // total_price += price;
                // }

                // add value of total_price in object
                new_Order.put("Total Price", total_price);

                // add object into order json array in line 22
                order_Load.add(new_Order);

                // add object into receipt json array in line 23
                receipt_Load.add(new_receipt);

                // write a file
                FileWriter file = new FileWriter(food_order_file_name);
                file.write(order_Load.toJSONString());
                file.flush();
                file.close();

                // write a file
                FileWriter file2 = new FileWriter(receipt_file_name);
                file2.write(receipt_Load.toJSONString());
                file2.flush();
                file2.close();

                System.out.println();
                System.out.println("\t\t**Thank you for your order**");

                Food_Menu.clear();
                Food_Name.clear();
                Prices.clear();
                Qty.clear();

                System.out.println();
                System.out.println("Press enter to continue...");
                try {
                    System.in.read();
                } catch (IOException e) {
                    e.printStackTrace();
                }


            } else if (confirm.equals("N")) {
                System.out.println("Order canceled");

                //delay 2 seconds
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //clear
                System.out.print("\033[H\033[2J");
                System.out.flush();


            } else {
                System.out.println("Invalid input");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

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

                    // readStaff getStaffName = new readStaff(username, password);
                    // display the bill
                    System.out.println("====================================");
                    System.out.println("\t       Receipt");
                    System.out.println("====================================");
                    System.out.println("Order Date: " + orderDate);
                    // System.out.println("Order Taker:" + getStaffName.getName());
                    System.out.println("Menu ID: " + menuId);
                    System.out.println("Menu Name: " + menu.get("MenuName"));
                    System.out.println("Quantity: " + qty);
                    System.out.println("------------------------------------");
                    System.out.println("\tTotal Price: " + total);
                    System.out.println("\tChange:  " + (cash - total));
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

    //orderHistory method to 

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
            Object obj = parser.parse(new FileReader("order_food.json"));
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
