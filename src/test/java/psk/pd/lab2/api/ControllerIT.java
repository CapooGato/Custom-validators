package psk.pd.lab2.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void hitShouldReturnOkAndIncrement() throws Exception {
        mockMvc.perform(get("/api/hit"))
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("licznik:")));

        mockMvc.perform(get("/api/hit"))
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("licznik:")));
    }

    @Test
    void greetShouldReturnHtmlWithName() throws Exception {
        mockMvc.perform(get("/api/greet").param("name", "IntegrationTest"))
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("Witaj, IntegrationTest!")));
    }

    @Test
    void greetWithoutNameShouldReturn400() throws Exception {
        mockMvc.perform(get("/api/greet"))
            .andExpect(status().isBadRequest());
    }

    @Test
    void debugShouldExposeSecrets() throws Exception {
        mockMvc.perform(get("/api/debug"))
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("Pass:")))
            .andExpect(content().string(containsString("JWT:")));
    }
}
