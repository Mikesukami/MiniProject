import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import netscape.javascript.JSObject;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;
import java.text.SimpleDateFormat;
import java.io.FileWriter;
//

public class readStaff {

    public String username;
    public String password;
    public String name;
    public String role;
    public String loginDate;
    public String loginCount;

    public Boolean isin = false;

    public readStaff() {

    }

    // login
    public readStaff(String userkb, String passkb) {
        JSONParser parser = new JSONParser();
        try {
            Reader reader = new FileReader("staff.json");
            JSONObject jsonObject = (JSONObject) parser.parse(reader);
            JSONObject staff_user;

            for (int i = 1; i < 100; i++) {                
                staff_user = (JSONObject) jsonObject.get("s0" + i);
                this.username = (String) staff_user.get("username");
                this.password = (String) staff_user.get("password");
                this.name = (String) staff_user.get("name");
                this.role = (String) staff_user.get("role");
                String role = getRole();
                if (userkb.equals(this.username) && passkb.equals(this.password)) {

                    // clear screen
                    System.out.print("\033[H\033[2J");
                    System.out.flush();

                    System.out.println("");
                    System.out.println("----------------");
                    System.out.println("Login Complete!");
                    System.out.println("----------------");
                    System.out.println("");

                    LogInOut_File login = new LogInOut_File(this.name, this.role);
                    login.loginHistory();


                    // cooldown
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        // clear screen
                        System.out.print("\033[H\033[2J");
                        System.out.flush();

                    }

                    // clear screen
                    System.out.print("\033[H\033[2J");
                    System.out.flush();


                    this.isin = true;
                    break;
                }
            }

        } catch (Exception e) {
            // clear screen 
        }

    }

    public boolean isLogin() {
        return isin;
    }

    // getter
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

    public String getLoginCount() {
        return loginCount;
    }

    // setter
    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setLoginDate(String loginDate) {
        this.loginDate = loginDate;
    }

    public void setLoginId(String loginCount) {
        this.loginCount = loginCount;
    }

    // get login date
    public String getLoginDate() {
        return loginDate;
    }

}
