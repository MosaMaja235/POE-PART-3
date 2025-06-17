
package javaapplication24;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Login {

    
    public boolean checkUsername(String username){
        if(username.contains("_") &&username.length()<=5){
            System.out.println("username is captured");
            return true;
        }
        else{
            System.out.println("please try again");
        }
        return false;
    }
     //Method to validate the password
    public boolean Checkpassword(String password){
        if (password.length()>=8 && password.matches(".*\\d.*") && password.matches(".*[A-Z].*") && password.matches(".*[^a-zA-Z0-9].*"))
        {System.out.println("password successfully captured.");
           
            return true;
        }
        else{
            System.out.println("please try again");
        }
        return false;
    }
    //Method to validate South African cell phone number
    public boolean checkcellnumber(String cellnumber){
      if (cellnumber.length()==9 && cellnumber.matches("^\\d{9}$")){
           System.out.println("Cell phone number successfully added.");
           return true;
        } 
        else {
            System.out.println("Cell phone number incorrectly formatted or does not contain international code.");    
        }    
        return false;
        }
    

 
              
    
}

    
    
    
    
    
  
    

