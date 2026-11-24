package psk.pd.lab2;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

  // Hardcoded credentials
  @Value("${DB_USERNAME}")
  public String DB_USERNAME;

  @Value("${DB_PASSWORD}")
  public String DB_PASSWORD;

  @Value("${DB_URL}")
  public String DB_URL;

  @Value("${JWT_SECRET}")
  public String JWT_SECRET;

  @Value("${API_KEY}")
  public String API_KEY;

  @Value("${AWS_ACCESS_KEY}")
  public String AWS_ACCESS_KEY;

  @Value("${AWS_SECRET_KEY}")
  public String AWS_SECRET_KEY;

  public static void main(String[] args) {
    SpringApplication.run(DemoApplication.class, args);
  }

}
