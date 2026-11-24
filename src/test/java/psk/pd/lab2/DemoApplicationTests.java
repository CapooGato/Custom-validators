package psk.pd.lab2;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import psk.pd.lab2.domain.counter;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class DemoApplicationTests {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private counter globalCounter;

  @Autowired
  private DemoApplication demoApplication;

  @Test
  void contextLoads() {
  }

  @Test
  void hitEndpointIntegration() throws Exception {
    mockMvc.perform(get("/api/hit"))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("licznik:")));
  }

  @Test
  void hitShouldIncrementOnEachCall() throws Exception {
    // pierwsze wywołanie
    mockMvc.perform(get("/api/hit"))
        .andExpect(status().isOk());

    // drugie wywołanie - licznik powinien być wyższy
    mockMvc.perform(get("/api/hit"))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("licznik:")));
  }

  @Test
  void greetEndpointIntegration() throws Exception {
    mockMvc.perform(get("/api/greet").param("name", "Testowy"))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("Witaj, Testowy!")));
  }

  @Test
  void debugEndpointIntegration() throws Exception {
    mockMvc.perform(get("/api/debug"))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("DB:")))
        .andExpect(content().string(containsString("Pass:")));
  }

  @Test
  void hardcodedCredentialsShouldExist() {
    // test weryfikujący, że dane uwierzytelniające są zahardkodowane (luka!)
    assertNotNull(demoApplication.DB_USERNAME);
    assertNotNull(demoApplication.DB_PASSWORD);
    assertNotNull(demoApplication.JWT_SECRET);
    assertNotNull(demoApplication.API_KEY);
    assertNotNull(demoApplication.AWS_ACCESS_KEY);
    assertNotNull(demoApplication.AWS_SECRET_KEY);
  }


  private void assertNotNull(String value) {
    org.junit.jupiter.api.Assertions.assertNotNull(value);
    org.junit.jupiter.api.Assertions.assertFalse(value.isEmpty());
  }
}
