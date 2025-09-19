package Services;
import Repositories.UserRepository;
import Module.User;
public class AuthService {

    private final UserRepository userRepository;

    public AuthService (UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String register(String fullName, String email, String address, String password) {
        if(userRepository.findByEmail(email)!=null){
            return "Email already exists";
        }
        userRepository.save(fullName,email,address,password);
        return "You have successfully registered";
    }

    public User login(String email, String password) {
        User user = userRepository.findByEmail(email);
        if(user != null && user.getPassword().equals(password)){
            return user ;
        }

        return  null;

    }

    public User updateProfile(User user, String newFullName, String newEmail, String newAddress) {
        User existing = userRepository.findByEmail(newEmail);
        if (existing != null && !existing.getId().equals(user.getId())) {
            System.out.println(" Email already in use.");
            return null;
        }

        user.setFullName(newFullName);
        user.setEmail(newEmail);
        user.setAddress(newAddress);

        return userRepository.update(user);
    }




}
