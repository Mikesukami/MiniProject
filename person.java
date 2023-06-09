import java.io.FileReader;
import java.util.Comparator;
import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.IOException;

public class person {

    protected String name;


    //6510210547 20:13:51.641545
    protected String username;
    protected String password;
    protected String role;


    public person(){
        
    }

    public void viewMenu(){

        // Read menu items from menu.json file
        JSONParser parser = new JSONParser();
        try {
            // Parse the JSON file
            JSONArray jsonArray = (JSONArray) parser.parse(new FileReader("menu.json"));

            // Sort the menu items by "MenuID" as integers in ascending order
            jsonArray.sort(new Comparator<JSONObject>() {
                @Override
                public int compare(JSONObject o1, JSONObject o2) {
                    int menuId1 = Integer.parseInt((String) o1.get("MenuID"));
                    int menuId2 = Integer.parseInt((String) o2.get("MenuID"));
                    return Integer.compare(menuId1, menuId2);
                }
            });

            // Display the sorted menu items
            System.out.format("%-10s %-20s %-10s\n", "MenuID", "MenuName", "MenuPrice");
            for (Object obj : jsonArray) {
                JSONObject menu = (JSONObject) obj;
                String menuId = (String) menu.get("MenuID");
                String menuName = (String) menu.get("MenuName");
                long menuPrice = (long) menu.get("MenuPrice");
                System.out.format("%-10s %-20s %-10s\n", String.format("%02d", Integer.parseInt(menuId)), menuName, menuPrice);
            }

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    public void productView() {
        // Clear the console screen
        System.out.print("\033[H\033[2J");
        System.out.flush();

        // Load and parse the menu items from the JSON file
        List<JSONObject> menuItems = new ArrayList<>();
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader("menu.json"));
            JSONObject jsonObject = (JSONObject) obj;
            for (Object key : jsonObject.keySet()) {
                String menuId = (String) key;
                JSONObject menu = (JSONObject) jsonObject.get(menuId);
                menu.put("MenuID", menuId);
                menuItems.add(menu);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Sort the menu items by ID ascending
        menuItems.sort(new Comparator<JSONObject>() {
            @Override
            public int compare(JSONObject o1, JSONObject o2) {
                String id1 = (String) o1.get("MenuID");
                String id2 = (String) o2.get("MenuID");
                return id1.compareTo(id2);
            }
        });

        // Print out the menu items in the sorted order
        System.out.println("==========================================");
        System.out.println("ID\tName\t\tPrice");
        System.out.println("==========================================");
        for (JSONObject menu : menuItems) {
            String menuId = (String) menu.get("MenuID");
            String menuName = (String) menu.get("MenuName");
            long menuPrice = (Long) menu.get("MenuPrice");
            
            System.out.printf("%s\t%s \t %d Bath\n", menuId, menuName, menuPrice);
        }

        System.out.println("==========================================");


    }

    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return name;
    }
}
