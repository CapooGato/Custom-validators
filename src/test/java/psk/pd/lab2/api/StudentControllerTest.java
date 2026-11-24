package psk.pd.lab2.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import psk.pd.lab2.application.StudentLoginValidator;
import psk.pd.lab2.domain.StudentLoginRequest;
import tools.jackson.databind.ObjectMapper;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StudentController.class)
class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private StudentLoginValidator validator;

    private final ObjectMapper objectMapper = new ObjectMapper();

    // ==================== TESTY ENDPOINTU POST /api/student/login ====================

    // TODO: test — wysłanie poprawnych danych powinno zwrócić status 200 i JSON z "status": "ok"
    //  1. Zamockuj validator.validate() żeby zwracał pustą listę: when(validator.validate(any())).thenReturn(Collections.emptyList())
    //  2. Utwórz obiekt StudentLoginRequest z poprawnymi danymi (import psk.pd.lab2.domain.StudentLoginRequest)
    //  3. Wyślij POST na "/api/student/login" z Content-Type application/json i body = objectMapper.writeValueAsString(request)
    //  4. Sprawdź: status().isOk(), jsonPath("$.status").value("ok"), jsonPath("$.student").value("123456")
    @Test
    void loginShouldAcceptValidStudentLoginRequest() throws Exception {
        org.mockito.Mockito.when(validator.validate(any())).thenReturn(Collections.emptyList());
        StudentLoginRequest request = new StudentLoginRequest(
                "jan.kowalski@student.tu.kielce.pl",
                "Haslo123",
                "123456");

        mockMvc.perform(post("/api/student/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("ok"))
                .andExpect(jsonPath("$.student").value("123456"));
    }

    // TODO: test — wysłanie niepoprawnych danych powinno zwrócić status 400 i listę błędów
    //  1. Zamockuj validator żeby zwracał listę błędów: when(validator.validate(any())).thenReturn(List.of("Email jest wymagany", "Hasło jest wymagane"))
    //  2. Wyślij POST z dowolnym body
    //  3. Sprawdź: status().isBadRequest(), jsonPath("$.status").value("error"), jsonPath("$.errors", hasSize(2))
    @Test
    void loginShouldReturnBadRequestUponDetectingErrorsInStudentLoginRequest() throws Exception{
        org.mockito.Mockito.when(validator.validate(any())).thenReturn(List.of("Email jest wymagany", "Hasło jest wymagane"));
        StudentLoginRequest request = new StudentLoginRequest();

        mockMvc.perform(post("/api/student/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value("error"))
                .andExpect(jsonPath("$.errors", hasSize(2)));
    }

    // TODO: test — wysłanie pustego body ({}) powinno nadal wywołać walidator i zwrócić błędy
    //  Zamockuj validator żeby zwracał błędy i sprawdź, że endpoint zwraca 400
    @Test
    void loginShouldDetectErrorsGivenErrorListAndEmptyBody() throws Exception{
        org.mockito.Mockito.when(validator.validate(any())).thenReturn(List.of("Email jest wymagany", "Hasło jest wymagane"));

        mockMvc.perform(post("/api/student/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest());
    }

    // TODO: test — sprawdź że endpoint odpowiada tylko na POST (nie na GET)
    //  Wyślij GET na "/api/student/login" i sprawdź, że zwraca status 405 (Method Not Allowed)
    //  Użyj: MockMvcRequestBuilders.get("/api/student/login")
    @Test
    void loginShouldNotRespondToHTTPGetRequest() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/api/student/login")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isMethodNotAllowed());
    }

    // TODO: test — wysłanie requestu bez Content-Type application/json powinno zwrócić 415 (Unsupported Media Type)
    //  Wyślij POST z contentType(MediaType.TEXT_PLAIN) i sprawdź status().isUnsupportedMediaType()
    @Test
    void loginShouldNotAcceptOtherMediaTypeThanJSON() throws Exception{
           mockMvc.perform(post("/api/student/login")
                        .contentType(MediaType.TEXT_PLAIN))
                .andExpect(status().isUnsupportedMediaType());
    }

    // TODO: test — sprawdź że w odpowiedzi sukcesu jest pole "message" z treścią "Dane logowania poprawne"
    //  Zamockuj validator na brak błędów i sprawdź jsonPath("$.message").value("Dane logowania poprawne")
    @Test
    void loginReturnMessageShouldContainMessageField() throws Exception{
        org.mockito.Mockito.when(validator.validate(any())).thenReturn(Collections.emptyList());
        StudentLoginRequest request = new StudentLoginRequest(
                "jan.kowalski@student.tu.kielce.pl",
                "Haslo123",
                "123456");

        mockMvc.perform(post("/api/student/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Dane logowania poprawne"));
    }
}
