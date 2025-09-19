package Repositories;
import Module.User;

import java.util.List;
import java.util.UUID;

public interface UserRepository {
    User save(String fullName, String email, String address,String password);
    User findById(UUID id);
    User findByEmail(String email);
    List<User> findAll();
    User update(User user);
}
