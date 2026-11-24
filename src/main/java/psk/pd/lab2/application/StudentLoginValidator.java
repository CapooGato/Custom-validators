package psk.pd.lab2.application;

import org.springframework.stereotype.Service;
import psk.pd.lab2.domain.StudentLoginRequest;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentLoginValidator {

    private static final String REQUIRED_DOMAIN = "student.tu.kielce.pl";
    private static final int MIN_PASSWORD_LENGTH = 8;

    public List<String> validate(StudentLoginRequest request) {
        List<String> errors = new ArrayList<>();

        validateEmail(request.getEmail(), errors);
        validatePassword(request.getPassword(), errors);
        validateStudentId(request.getStudentId(), errors);

        return errors;
    }

    private void validateEmail(String email, List<String> errors) {
        if (email == null || email.isBlank()) {
            errors.add("Email jest wymagany");
            return;
        }
        if (!email.contains("@")) {
            errors.add("Email musi zawierać znak @");
            return;
        }
        String domain = email.substring(email.indexOf("@") + 1);
        if (!domain.equals(REQUIRED_DOMAIN)) {
            errors.add("Email musi być w domenie " + REQUIRED_DOMAIN);
        }
    }

    private void validatePassword(String password, List<String> errors) {
        if (password == null || password.isBlank()) {
            errors.add("Hasło jest wymagane");
            return;
        }
        if (password.length() < MIN_PASSWORD_LENGTH) {
            errors.add("Hasło musi mieć co najmniej " + MIN_PASSWORD_LENGTH + " znaków");
        }
        if (password.chars().noneMatch(Character::isUpperCase)) {
            errors.add("Hasło musi zawierać co najmniej jedną wielką literę");
        }
        if (password.chars().noneMatch(Character::isDigit)) {
            errors.add("Hasło musi zawierać co najmniej jedną cyfrę");
        }
    }

    private void validateStudentId(String studentId, List<String> errors) {
        if (studentId == null || studentId.isBlank()) {
            errors.add("Numer albumu jest wymagany");
            return;
        }
        if (!studentId.matches("^\\d{6}$")) {
            errors.add("Numer albumu musi składać się z dokładnie 6 cyfr");
        }
    }
}
