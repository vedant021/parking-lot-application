package org.interview.service;

import org.interview.entity.User;
import org.interview.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User findOrCreateUser(String email, String name, String role) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if(userOpt.isPresent()) {
            return userOpt.get();
        }
        User user = new User();
        user.setEmail(email);
        user.setName(name);
        user.setRole(role);
        return userRepository.save(user);
    }

    public User getByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }
}