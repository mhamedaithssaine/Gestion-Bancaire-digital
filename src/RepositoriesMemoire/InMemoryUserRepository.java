package RepositoriesMemoire;
import Module.User;
import Repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class InMemoryUserRepository implements UserRepository {
    private List<User> users = new ArrayList<>();


    @Override
    public User save(String fullName, String email, String address, String password) {
        User user = new User(fullName, email, address, password);
        users.add(user);
        return user;
    }

    @Override
    public User findById(UUID id) {
        for (User user : users) {
            if (user.getId() == id) {
                return user;
            }
        }
        return null;
    }

    @Override
    public User findByEmail(String email){
        for(User user : users){
            if(user.getEmail().equalsIgnoreCase(email)){
                return user;
            }
        }
        return null;
    }

    @Override
    public List<User> findAll() {
        return users;
    }

    public User update(User user) {
        for(User user1 : users){
            if(user1.getId().equals(user.getId())){
                user1.setFullName(user.getFullName());
                user1.setEmail(user.getEmail());
                user1.setAddress(user.getAddress());
                return user1;
            }
        }
        return null;
    }

}
