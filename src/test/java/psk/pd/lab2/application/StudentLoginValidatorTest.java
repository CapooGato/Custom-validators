package psk.pd.lab2.application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import psk.pd.lab2.domain.StudentLoginRequest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StudentLoginValidatorTest {

    private StudentLoginValidator validator;

    @BeforeEach
    void setUp() {
        validator = new StudentLoginValidator();
    }

    // ==================== TESTY EMAIL ====================
    @Nested
    class StudentLoginEmailTest {
        // TODO: test — poprawny email w domenie student.tu.kielce.pl powinien przejść walidację bez błędów
        //  Użyj np. "jan.kowalski@student.tu.kielce.pl", hasło "Haslo123", numer "123456"
        //  Sprawdź, że lista błędów jest pusta (assertTrue(errors.isEmpty()))
        @Test
        void shouldAcceptValidEmail() {
            StudentLoginRequest request = new StudentLoginRequest(
                    "jan.kowalski@student.tu.kielce.pl",
                    "Haslo123",
                    "123456");
            List<String> errors = validator.validate(request);

            assertTrue(errors.isEmpty());
        }

        // TODO: test — email z innej domeny (np. "jan@gmail.com") powinien zwrócić błąd o nieprawidłowej domenie
        //  Sprawdź, że errors zawiera komunikat o domenie student.tu.kielce.pl
        @Test
        void shouldRejectEmailFromWrongDomain() {
            StudentLoginRequest request = new StudentLoginRequest(
                    "jan.kowalski@gmail.com",
                    "Haslo123",
                    "123456");
            List<String> errors = validator.validate(request);

            assertFalse(errors.isEmpty());
            assertTrue(errors.stream().anyMatch(e -> e.contains("Email musi być w domenie student.tu.kielce.pl")));
        }

        // TODO: test — pusty email (null) powinien zwrócić błąd "Email jest wymagany"
        //  Utwórz request z email=null i sprawdź, że errors zawiera odpowiedni komunikat
        @Test
        void shouldRejectWhenEmailIsNull() {
            StudentLoginRequest request = new StudentLoginRequest(
                    null,
                    "Haslo123",
                    "123456");
            List<String> errors = validator.validate(request);

            assertFalse(errors.isEmpty());
            assertTrue(errors.stream().anyMatch(e -> e.contains("Email jest wymagany")));
        }

        // TODO: test — email bez znaku @ (np. "jan.kowalski.student.tu.kielce.pl") powinien zwrócić błąd
        //  Sprawdź, że errors zawiera komunikat "Email musi zawierać znak @"
        @Test
        void shouldRejectWenEmailLacksAtSign() {
            StudentLoginRequest request = new StudentLoginRequest(
                    "jan.kowalski.student.tu.kielce.pl",
                    "Haslo123",
                    "123456");
            List<String> errors = validator.validate(request);

            assertFalse(errors.isEmpty());
            assertTrue(errors.stream().anyMatch(e -> e.contains("Email musi zawierać znak @")));
        }

        // TODO: test — pusty string jako email powinien zwrócić błąd "Email jest wymagany"
        //  Utwórz request z email="" i sprawdź czy zachowuje się tak samo jak null
        @Test
        void shouldRejectWhenEmailIsEmptyString() {
            StudentLoginRequest request = new StudentLoginRequest(
                    "",
                    "Haslo123",
                    "123456");
            List<String> errors = validator.validate(request);

            assertFalse(errors.isEmpty());
            assertTrue(errors.stream().anyMatch(e -> e.contains("Email jest wymagany")));
        }
    }

    // ==================== TESTY HASŁA ====================
    @Nested
    class StudentLoginPasswordTest {
        // TODO: test — hasło krótsze niż 8 znaków (np. "Ab1") powinno zwrócić błąd o minimalnej długości
        //  Sprawdź, że errors zawiera komunikat o co najmniej 8 znakach
        @Test
        void shouldRejectPasswordShorterThanEightChars() {
            StudentLoginRequest request = new StudentLoginRequest(
                    "jan.kowalski@student.tu.kielce.pl",
                    "Ab1",
                    "123456");
            List<String> errors = validator.validate(request);

            assertFalse(errors.isEmpty());
            assertTrue(errors.stream().anyMatch(e -> e.contains("Hasło musi mieć co najmniej " + 8 + " znaków")));
        }

        // TODO: test — hasło bez wielkiej litery (np. "haslo12345") powinno zwrócić błąd
        //  Sprawdź, że errors zawiera komunikat o wielkiej literze
        @Test
        void shouldRejectPasswordWithoutCapitalLetter() {
            StudentLoginRequest request = new StudentLoginRequest(
                    "jan.kowalski@student.tu.kielce.pl",
                    "haslo12345",
                    "123456");
            List<String> errors = validator.validate(request);

            assertFalse(errors.isEmpty());
            assertTrue(errors.stream().anyMatch(e -> e.contains("Hasło musi zawierać co najmniej jedną wielką literę")));
        }

        // TODO: test — hasło bez cyfry (np. "HasloBezCyfry") powinno zwrócić błąd
        //  Sprawdź, że errors zawiera komunikat o cyfrze
        @Test
        void shouldRejectPasswordWithoutDigit() {
            StudentLoginRequest request = new StudentLoginRequest(
                    "jan.kowalski@student.tu.kielce.pl",
                    "HasloBezCyfry",
                    "123456");
            List<String> errors = validator.validate(request);

            assertFalse(errors.isEmpty());
            assertTrue(errors.stream().anyMatch(e -> e.contains("Hasło musi zawierać co najmniej jedną cyfrę")));
        }

        // TODO: test — hasło null powinno zwrócić błąd "Hasło jest wymagane"
        @Test
        void shouldRejectWhenPasswordIsNull() {
            StudentLoginRequest request = new StudentLoginRequest(
                    "jan.kowalski@student.tu.kielce.pl",
                    null,
                    "123456");
            List<String> errors = validator.validate(request);

            assertFalse(errors.isEmpty());
            assertTrue(errors.stream().anyMatch(e -> e.contains("Hasło jest wymagane")));
        }

        // TODO: test — hasło spełniające wszystkie wymagania (np. "Silne1Haslo") nie powinno generować błędów hasła
        //  Utwórz request z poprawnym emailem, tym hasłem i poprawnym numerem, sprawdź że errors jest puste
        @Test
        void shouldAcceptValidPassword() {
            StudentLoginRequest request = new StudentLoginRequest(
                    "jan.kowalski@student.tu.kielce.pl",
                    "Silne1Haslo",
                    "123456");
            List<String> errors = validator.validate(request);

            assertTrue(errors.isEmpty());
        }
    }
    // ==================== TESTY NUMERU ALBUMU ====================

    @Nested
    class StudentLoginStudentIdTest {
        // TODO: test — poprawny 6-cyfrowy numer albumu (np. "123456") powinien przejść walidację
        //  Sprawdź, że nie ma błędów dotyczących numeru albumu
        @Test
        void shouldAcceptValidStudentId() {
            StudentLoginRequest request = new StudentLoginRequest(
                    "jan.kowalski@student.tu.kielce.pl",
                    "Silne1Haslo",
                    "123456");
            List<String> errors = validator.validate(request);

            assertFalse(errors.stream().anyMatch(e -> e.contains("Numer albumu")));
        }

        // TODO: test — numer albumu z literami (np. "12AB56") powinien zwrócić błąd
        //  Sprawdź, że errors zawiera komunikat o 6 cyfrach
        @Test
        void shouldRejectWhenStudentIdHasLetter() {
            StudentLoginRequest request = new StudentLoginRequest(
                    "jan.kowalski@student.tu.kielce.pl",
                    "Silne1Haslo",
                    "12AB56");
            List<String> errors = validator.validate(request);

            assertFalse(errors.isEmpty());
            assertTrue(errors.stream().anyMatch(e -> e.contains("Numer albumu musi składać się z dokładnie 6 cyfr")));
        }

        // TODO: test — numer albumu krótszy niż 6 cyfr (np. "12345") powinien zwrócić błąd
        @Test
        void shouldRejectWhenStudentIdIsShorterThanSixDigits() {
            StudentLoginRequest request = new StudentLoginRequest(
                    "jan.kowalski@student.tu.kielce.pl",
                    "Silne1Haslo",
                    "12345");
            List<String> errors = validator.validate(request);

            assertFalse(errors.isEmpty());
            assertTrue(errors.stream().anyMatch(e -> e.contains("Numer albumu musi składać się z dokładnie 6 cyfr")));
        }

        // TODO: test — numer albumu dłuższy niż 6 cyfr (np. "1234567") powinien zwrócić błąd
        @Test
        void shouldRejectWhenStudentIdIsLongerThanSixDigits() {
            StudentLoginRequest request = new StudentLoginRequest(
                    "jan.kowalski@student.tu.kielce.pl",
                    "Silne1Haslo",
                    "1234567");
            List<String> errors = validator.validate(request);

            assertFalse(errors.isEmpty());
            assertTrue(errors.stream().anyMatch(e -> e.contains("Numer albumu musi składać się z dokładnie 6 cyfr")));
        }

        // TODO: test — numer albumu null powinien zwrócić błąd "Numer albumu jest wymagany"
        @Test
        void shouldRejectWhenStudentIdIsNull() {
            StudentLoginRequest request = new StudentLoginRequest(
                    "jan.kowalski@student.tu.kielce.pl",
                    "Silne1Haslo",
                    null);
            List<String> errors = validator.validate(request);

            assertFalse(errors.isEmpty());
            assertTrue(errors.stream().anyMatch(e -> e.contains("Numer albumu jest wymagany")));
        }
        // ==================== TESTY KOMBINACJI ====================

        // TODO: test — request z wieloma błędami (zły email, krótkie hasło, zły numer)
        //  powinien zwrócić listę z wieloma komunikatami błędów
        //  Użyj np. email="zly", hasło="ab", numer="abc" i sprawdź, że errors.size() >= 3
        @Test
        void shouldDetectAllThreeErrorsInStudentLoginRequest() {
            StudentLoginRequest request = new StudentLoginRequest(
                    "zly",
                    "ab",
                    "abc");
            List<String> errors = validator.validate(request);

            assertFalse(errors.isEmpty());
            assertTrue(errors.size() >= 3);
        }

        // TODO: test — całkowicie pusty request (wszystkie pola null)
        //  powinien zwrócić 3 błędy (po jednym dla każdego pola)
        //  Sprawdź assertEquals(3, errors.size())
        @Test
        void shouldRejectWhenStudentLoginRequestIsEmpty() {
            StudentLoginRequest request = new StudentLoginRequest();
            List<String> errors = validator.validate(request);

            assertFalse(errors.isEmpty());
            assertEquals(3, errors.size());
        }
    }
}
