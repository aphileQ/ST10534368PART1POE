package com.mycompany.main;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the validation and login logic in Main.java.
 *
 * These tests target the static helper methods directly and do not
 * exercise the console I/O (registerUser / main), since those read
 * from System.in and are better covered by manual or integration testing.
 */
public class MainTest {

    @BeforeEach
    void resetRegisteredUser() {
        // Ensure a clean slate before each test that touches registered fields
        Main.registeredUsername = null;
        Main.registeredPassword = null;
        Main.registeredCellPhone = null;
    }

    // ---------- checkUserName ----------

    @Test
    void checkUserName_validUsernameWithUnderscore_returnsTrue() {
        // Exactly 5 characters, contains an underscore
        assertTrue(Main.checkUserName("ab_cd"));
    }

    @Test
    void checkUserName_tooShort_returnsFalse() {
        assertFalse(Main.checkUserName("ab_c"));
    }

    @Test
    void checkUserName_tooLong_returnsFalse() {
        assertFalse(Main.checkUserName("ab_cde"));
    }

    @Test
    void checkUserName_correctLengthNoUnderscore_returnsFalse() {
        assertFalse(Main.checkUserName("abcde"));
    }

    @Test
    void checkUserName_emptyString_returnsFalse() {
        assertFalse(Main.checkUserName(""));
    }

    // ---------- checkPasswordComplexity ----------

    @Test
    void checkPasswordComplexity_meetsAllRequirements_returnsTrue() {
        // Uppercase, lowercase, digit, special character, length >= 8
        assertTrue(Main.checkPasswordComplexity("Abcdef1!"));
    }

    @Test
    void checkPasswordComplexity_tooShort_returnsFalse() {
        assertFalse(Main.checkPasswordComplexity("Ab1!"));
    }

    @Test
    void checkPasswordComplexity_missingUppercase_returnsFalse() {
        assertFalse(Main.checkPasswordComplexity("abcdefg1!"));
    }

    @Test
    void checkPasswordComplexity_missingLowercase_returnsFalse() {
        assertFalse(Main.checkPasswordComplexity("ABCDEFG1!"));
    }

    @Test
    void checkPasswordComplexity_missingDigit_returnsFalse() {
        assertFalse(Main.checkPasswordComplexity("Abcdefgh!"));
    }

    @Test
    void checkPasswordComplexity_missingSpecialCharacter_returnsFalse() {
        assertFalse(Main.checkPasswordComplexity("Abcdefg1"));
    }

    // ---------- checkCellPhoneNumber ----------

    @Test
    void checkCellPhoneNumber_validSouthAfricanNumber_returnsTrue() {
        assertTrue(Main.checkCellPhoneNumber("+27821234567"));
    }

    @Test
    void checkCellPhoneNumber_missingCountryCode_returnsFalse() {
        assertFalse(Main.checkCellPhoneNumber("0821234567"));
    }

    @Test
    void checkCellPhoneNumber_tooFewDigits_returnsFalse() {
        assertFalse(Main.checkCellPhoneNumber("+2782123456"));
    }

    @Test
    void checkCellPhoneNumber_tooManyDigits_returnsFalse() {
        assertFalse(Main.checkCellPhoneNumber("+278212345678"));
    }

    @Test
    void checkCellPhoneNumber_containsLetters_returnsFalse() {
        assertFalse(Main.checkCellPhoneNumber("+2782abc4567"));
    }

    // ---------- loginUser ----------

    @Test
    void loginUser_correctCredentials_returnsTrue() {
        Main.registeredUsername = "ab_cd";
        Main.registeredPassword = "Abcdef1!";

        assertTrue(Main.loginUser("ab_cd", "Abcdef1!"));
    }

    @Test
    void loginUser_incorrectUsername_returnsFalse() {
        Main.registeredUsername = "ab_cd";
        Main.registeredPassword = "Abcdef1!";

        assertFalse(Main.loginUser("wr_ong", "Abcdef1!"));
    }

    @Test
    void loginUser_incorrectPassword_returnsFalse() {
        Main.registeredUsername = "ab_cd";
        Main.registeredPassword = "Abcdef1!";

        assertFalse(Main.loginUser("ab_cd", "WrongPass1!"));
    }

    @Test
    void loginUser_noUserRegisteredYet_returnsFalse() {
        // registeredUsername / registeredPassword are null after resetRegisteredUser()
        assertFalse(Main.loginUser("ab_cd", "Abcdef1!"));
    }

    // ---------- returnLoginStatus ----------

    @Test
    void returnLoginStatus_successfulLogin_returnsWelcomeMessage() {
        assertEquals(
                "Welcome, it is great to see you again.",
                Main.returnLoginStatus(true)
        );
    }

    @Test
    void returnLoginStatus_failedLogin_returnsErrorMessage() {
        assertEquals(
                "Username or password incorrect, please try again.",
                Main.returnLoginStatus(false)
        );
    }
}
