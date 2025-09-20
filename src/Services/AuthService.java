package Services;
import Repositories.UserRepository;
import Module.User;
import Utils.Validation;

import java.util.UUID;

public class AuthService {

    private final UserRepository userRepository;

    public AuthService (UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    Validation validation = new Validation();

    public String register(String fullName, String email, String address, String password) {
        boolean validationEmail = validation.RegexEmail(email);
        boolean validationPassword = validation.RegexPassword(password);

        if (!validationEmail){
            return "email is invalid";
        }

        if(userRepository.findByEmail(email)!=null){
                return "Email already exists";

        }

        if (!validationPassword){
            return "password is invalid !";
        }
            userRepository.save(fullName,email,address,password);
            return "You have successfully registered";


    }

    public User login(String email, String password) {
        boolean validationEmail = validation.RegexEmail(email);
        boolean validationPassword = validation.RegexPassword(password);
        if(validationEmail || validationPassword){
            User user = userRepository.findByEmail(email);
            if(user != null && user.getPassword().equals(password)){
                return user ;
            }

        } else System.out.println("Password or email is invalid !");



        return  null;

    }

    public User updateProfile(User user, String newFullName, String newEmail, String newAddress) {
        boolean validationEmail = validation.RegexEmail(newEmail);
        if(validationEmail){
            User existing = userRepository.findByEmail(newEmail);
            if (existing != null && !existing.getId().equals(user.getId())) {
                System.out.println(" Email already in use.");
                return null;
            }
        }


        user.setFullName(newFullName);
        user.setEmail(newEmail);
        user.setAddress(newAddress);

        return userRepository.update(user);
    }

    public User updatePassword(User user, String newPassword){
        boolean validationPassword = validation.RegexPassword(newPassword);
        User existing = userRepository.findById(user.getId());
        if(!validationPassword && !existing.getId().equals(user.getId())){
            System.out.println("the user not have in or password incorrect ");
        }
        user.setPassword(newPassword);
        return userRepository.update(user);

    }



}
