package psk.pd.lab2.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import psk.pd.lab2.DemoApplication;
import psk.pd.lab2.domain.counter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

@RestController
@RequestMapping("/api")
public class Controller {

    @Autowired
    private DemoApplication demoApplication;

    private final counter globalCounter;

    public Controller(counter globalCounter) {
        this.globalCounter = globalCounter;
    }


    @GetMapping("/hit")
    public String hit() {
        int current = globalCounter.increment();
        return "licznik: " + current;
    }

    @GetMapping("/user")
    public String getUser(@RequestParam String name) throws Exception {
        Connection conn = DriverManager.getConnection(
              demoApplication.DB_URL,   demoApplication.DB_USERNAME,   demoApplication.DB_PASSWORD);
        Statement stmt = conn.createStatement();
        String query = "SELECT * FROM users WHERE name = '" + name + "'";
        ResultSet rs = stmt.executeQuery(query);
        return rs.toString();
    }

    @GetMapping("/greet")
    public String greet(@RequestParam String name) {
        return "<html><body><h1>Witaj, " + name + "!</h1></body></html>";
    }

    @GetMapping("/debug")
    public String debug() {
        return "DB: " +   demoApplication.DB_URL
            + " | User: " +   demoApplication.DB_USERNAME
            + " | Pass: " +   demoApplication.DB_PASSWORD
            + " | JWT: " +   demoApplication.JWT_SECRET
            + " | AWS_KEY: " +   demoApplication.AWS_SECRET_KEY;
    }
}
