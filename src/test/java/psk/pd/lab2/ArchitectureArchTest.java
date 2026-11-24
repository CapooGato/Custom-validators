package psk.pd.lab2;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

class ArchitectureArchTest {

    private static JavaClasses importedClasses;

    @BeforeAll
    static void setup() {
        importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("psk.pd.lab2");
    }

    // ==================== DDD LAYER RULES ====================

    @Test
    void controllersShouldResideInApiPackage() {
        classes()
            .that().areAnnotatedWith(org.springframework.web.bind.annotation.RestController.class)
            .should().resideInAPackage("psk.pd.lab2.api")
            .check(importedClasses);
    }

    @Test
    void controllersShouldBeAnnotatedWithRestController() {
        classes()
            .that().haveSimpleNameEndingWith("Controller")
            .should().beAnnotatedWith(org.springframework.web.bind.annotation.RestController.class)
            .check(importedClasses);
    }

    @Test
    void servicesShouldResideInApplicationPackage() {
        classes()
            .that().areAnnotatedWith(org.springframework.stereotype.Service.class)
            .should().resideInAPackage("psk.pd.lab2.application")
            .check(importedClasses);
    }

    @Test
    void domainShouldNotDependOnApi() {
        noClasses()
            .that().resideInAPackage("psk.pd.lab2.domain")
            .should().dependOnClassesThat().resideInAPackage("psk.pd.lab2.api")
            .check(importedClasses);
    }

    @Test
    void domainShouldNotDependOnApplication() {
        noClasses()
            .that().resideInAPackage("psk.pd.lab2.domain")
            .should().dependOnClassesThat().resideInAPackage("psk.pd.lab2.application")
            .check(importedClasses);
    }

    @Test
    void applicationShouldNotDependOnApi() {
        noClasses()
            .that().resideInAPackage("psk.pd.lab2.application")
            .should().dependOnClassesThat().resideInAPackage("psk.pd.lab2.api")
            .check(importedClasses);
    }

    // ==================== GENERAL RULES ====================

    @Test
    void noClassesShouldUseJavaUtilLogging() {
        noClasses()
            .should().dependOnClassesThat().resideInAPackage("java.util.logging")
            .check(importedClasses);
    }

    @Test
    void componentsShouldBeAnnotatedWithComponent() {
        classes()
            .that().haveSimpleNameEndingWith("counter")
            .should().beAnnotatedWith(org.springframework.stereotype.Component.class)
            .check(importedClasses);
    }
}
