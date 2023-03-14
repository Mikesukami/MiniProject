import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.FileReader;

public class LogInOut_File {
    private String name;
    private String role;

    public LogInOut_File(String name, String role) {
        this.name = name;
        this.role = role;
    }

    public void loginHistory() {
        // create JSON array to store login history data
        JSONArray loginHistoryArray = new JSONArray();

        // get current date and time
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String formattedDateTime = now.format(formatter);

        // create JSON object for current login
        JSONObject currentLogin = new JSONObject();
        currentLogin.put("name", this.name);
        currentLogin.put("role", this.role);
        currentLogin.put("date", formattedDateTime.split(" ")[0]);
        currentLogin.put("time", formattedDateTime.split(" ")[1]);
        currentLogin.put("behavior", "login");
        // read existing login history data from file (if it exists)
        JSONArray existingLoginHistoryArray = new JSONArray();
        try {
            existingLoginHistoryArray = (JSONArray) new JSONParser().parse(new FileReader("login_history.json"));
        } catch (IOException | ParseException e) {
            // file not found or could not be parsed - ignore and create new file
            FileWriter file;
            try {
                file = new FileWriter("login_history.json");
                file.write(existingLoginHistoryArray.toJSONString());
                file.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }

        }

        // add current login to login history array
        loginHistoryArray.addAll(existingLoginHistoryArray);
        loginHistoryArray.add(currentLogin);

        // write updated login history data to file
        try (FileWriter file = new FileWriter("login_history.json")) {
            file.write(loginHistoryArray.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void logout_history() {
        // create JSON array to store login history data
        JSONArray loginHistoryArray = new JSONArray();

        // get current date and time
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String formattedDateTime = now.format(formatter);

        // create JSON object for current login
        JSONObject currentLogin = new JSONObject();
        currentLogin.put("name", this.name);
        currentLogin.put("role", this.role);
        currentLogin.put("date", formattedDateTime.split(" ")[0]);
        currentLogin.put("time", formattedDateTime.split(" ")[1]);
        currentLogin.put("behavior", "logout");
        // read existing login history data from file (if it exists)
        JSONArray existingLoginHistoryArray = new JSONArray();
        try {
            existingLoginHistoryArray = (JSONArray) new JSONParser().parse(new FileReader("login_history.json"));
        } catch (IOException | ParseException e) {
            // file not found or could not be parsed - ignore and create new file
            FileWriter file;
            try {
                file = new FileWriter("login_history.json");
                file.write(existingLoginHistoryArray.toJSONString());
                file.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }

        }

        // add current login to login history array
        loginHistoryArray.addAll(existingLoginHistoryArray);
        loginHistoryArray.add(currentLogin);

        // write updated login history data to file
        try (FileWriter file = new FileWriter("login_history.json")) {
            file.write(loginHistoryArray.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void printLoginHistory() {
        JSONParser parser = new JSONParser();

        try {
            // Read the JSON file into a JSONArray
            JSONArray loginHistoryArray = (JSONArray) parser.parse(new FileReader("login_history.json"));
            System.out.print("\033[H\033[2J");
            System.out.flush();

            System.out.println(
                    "===================================================================================================================================");
            System.out.println("\t\t\t\t\t\t Login History ");
            System.out.println(
                    "===================================================================================================================================");
            System.out.println("\tName\t\t\tRole\t\t\tDate\t\t\tTime\t\t\tBehavior");
            System.out.println(
                    "===================================================================================================================================");

            // Iterate over the JSONArray
            loginHistoryArray.forEach(login -> {
                JSONObject loginObject = (JSONObject) login;
                // clear screen

                System.out.println(
                        loginObject.get("name") + "\t\t " + loginObject.get("role") + "\t\t " + loginObject.get("date")
                                + "\t\t " + loginObject.get("time") + "\t\t " + loginObject.get("behavior"));
            });

            System.out.println(
                    "===================================================================================================================================");
            // press any key to continue
            System.out.println("Press enter to continue.");
            try {
                System.in.read();
            } catch (Exception e) {
            }

            // clear screen
            System.out.print("\033[H\033[2J");
            System.out.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
