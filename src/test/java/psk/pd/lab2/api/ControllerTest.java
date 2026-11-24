package psk.pd.lab2.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import psk.pd.lab2.DemoApplication;
import psk.pd.lab2.domain.counter;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(Controller.class)
class ControllerTest {

    @Autowired
    private DemoApplication demoApplication;

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private counter globalCounter;


    @Test
    void hitShouldReturnCounter() throws Exception {
        org.mockito.Mockito.when(globalCounter.increment()).thenReturn(5);

        mockMvc.perform(get("/api/hit"))
            .andExpect(status().isOk())
            .andExpect(content().string("licznik: 5"));
    }

    @Test
    void hitShouldReturnDifferentValues() throws Exception {
        org.mockito.Mockito.when(globalCounter.increment()).thenReturn(1, 2, 3);

        mockMvc.perform(get("/api/hit"))
            .andExpect(content().string("licznik: 1"));
        mockMvc.perform(get("/api/hit"))
            .andExpect(content().string("licznik: 2"));
        mockMvc.perform(get("/api/hit"))
            .andExpect(content().string("licznik: 3"));
    }


    @Test
    void greetShouldReturnHtmlWithName() throws Exception {
        mockMvc.perform(get("/api/greet").param("name", "Jan"))
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("Witaj, Jan!")));
    }

    @Test
    void greetShouldNotEscapeHtml() throws Exception {
        String xssPayload = "<script>alert('xss')</script>";
        mockMvc.perform(get("/api/greet").param("name", xssPayload))
            .andExpect(status().isOk())
            .andExpect(content().string(containsString(xssPayload)));
    }

    @Test
    void greetShouldRequireNameParam() throws Exception {
        mockMvc.perform(get("/api/greet"))
            .andExpect(status().isBadRequest());
    }


    @Test
    void debugShouldExposeCredentials() throws Exception {
        mockMvc.perform(get("/api/debug"))
            .andExpect(status().isOk())
            .andExpect(content().string(containsString(demoApplication.DB_PASSWORD)))
            .andExpect(content().string(containsString(demoApplication.JWT_SECRET)))
            .andExpect(content().string(containsString(demoApplication.AWS_SECRET_KEY)));
    }

    @Test
    void debugShouldExposeDbUrl() throws Exception {
        mockMvc.perform(get("/api/debug"))
            .andExpect(status().isOk())
            .andExpect(content().string(containsString(demoApplication.DB_URL)));
    }

}
