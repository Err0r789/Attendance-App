package com.force.App1.service;


import java.time.LocalDate;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.force.App1.model.User;
import com.force.App1.repository.UserRepository;

@Service
public class UserService {
	
    @Autowired
    private UserRepository userRepository;
    
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }
    public List<User> findUsersWithNoAttendanceToday(LocalDate date) {
        return userRepository.findUsersWithNoAttendanceOnDate(date);
    }

    public User validateUser(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;  
        }
        return null; 
    }
}