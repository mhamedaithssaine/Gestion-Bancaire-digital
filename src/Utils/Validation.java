package Utils;

public class Validation {



    public boolean RegexPassword(String Password){
        String regex = "^(?=.*[a-zA-Z])(?=.*\\d).{6,}$";
        if(Password == null || Password.isEmpty()){
            System.out.println("Your password is Empty ! ");
            return false;
        }
        if(Password.matches(regex)) {
            return true;
        } else {
            System.out.println("password should not contain any number or special characters, max or 6");
        }
            return false;

    }

    public  boolean RegexEmail (String Email){
        String regex = "^[a-zA-Z0-9]+@[a-zA-Z0-9]+\\.[a-zA-Z]{2,6}$";
        if (Email.isEmpty()){
            System.out.println("Your email is vide !");
            return false;
        }
        if(Email.matches(regex)){
            return true ;
        }else{
            System.out.println("email should not contain any space or special characters(excep @ and .)");
            return false;
        }

    }

}
