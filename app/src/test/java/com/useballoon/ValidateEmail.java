package com.useballoon;

import static junit.framework.TestCase.assertTrue;

import org.junit.Test;

import java.util.regex.Pattern;

public class ValidateEmail {


    @Test
    public void isValidMail() {
       String emailAddress = "username@domain.com";
       String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        assertTrue(ValidateEmail.patternMatches(emailAddress, regexPattern));
    }

    public static boolean patternMatches(String emailAddress, String regexPattern) {
        return Pattern.compile(regexPattern)
                .matcher(emailAddress)
                .matches();
    }
}
