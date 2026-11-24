package psk.pd.lab2.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import psk.pd.lab2.application.StudentLoginValidator;
import psk.pd.lab2.domain.StudentLoginRequest;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/student")
public class StudentController {

    private final StudentLoginValidator validator;

    public StudentController(StudentLoginValidator validator) {
        this.validator = validator;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody StudentLoginRequest request) {
        List<String> errors = validator.validate(request);

        if (!errors.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of(
                "status", "error",
                "errors", errors
            ));
        }

        return ResponseEntity.ok(Map.of(
            "status", "ok",
            "message", "Dane logowania poprawne",
            "student", request.getStudentId()
        ));
    }
}
