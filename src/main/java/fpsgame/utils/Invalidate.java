package fpsgame.utils;

import java.util.regex.Pattern;

public class Invalidate {
    public  static   String validateLogin(String username, String password) {
        if(username == null || username.trim().isEmpty()) { return "Username cannot be empty"; }
        if(password == null || password.trim().isEmpty()) { return "Password cannot be empty"; }
        return null;
    }

    public  static  String validateRegister(String username, String password, String email,String confirmPassword) {
        if(username == null || username.trim().isEmpty()) { return "Username cannot be empty"; }
        if(password == null || password.trim().isEmpty()) { return "Password cannot be empty"; }
        if(email == null || email.trim().isEmpty()) { return "Email cannot be empty"; }
        if(confirmPassword == null || confirmPassword.trim().isEmpty()) { return "Confirm Password cannot be empty"; }
        if(!isValidEmail(email)) { return "Invalid Email"; }
        return null;
    }

    private static boolean isValidEmail(String email) {
        String regex = "^\\S+@\\S+\\.\\S+$";
        return Pattern.matches(regex, email);
    }
}
