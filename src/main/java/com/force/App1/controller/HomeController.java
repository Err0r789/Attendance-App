package com.force.App1.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;

import com.force.App1.model.Attendance;
import com.force.App1.model.User;
import com.force.App1.service.AttendanceService;
import com.force.App1.service.UserService;
import com.force.App1.shedule.Scheduler;

@Controller
public class HomeController {
    @Autowired
    private UserService userService;
    
    @Autowired
    private AttendanceService attendanceService;


    @GetMapping("/login")
    public String login() {
        return "login";  
    }

    @PostMapping("/login")
    public String login(@RequestParam("username") String username, @RequestParam("password") String password, Model model) {
        if ("admin".equals(username) && "admin".equals(password)) {
            return "redirect:/report"; 
        }
        User user = userService.validateUser(username, password);
        if (user != null) {
            return "redirect:/attendance?userId=" + user.getId();
        } else {
            model.addAttribute("error", "Invalid username or password.");
            return "login"; 
        }
    }
    @GetMapping("/attendance")
    public String showAttendance(@RequestParam("userId") int userId, Model model) {
        List<Attendance> attendances = attendanceService.getAttendancesByUserId(userId);
        model.addAttribute("attendances", attendances);
        model.addAttribute("userId", userId);  
        return "attendance"; 
    }
    @GetMapping("/attendancereport")
    public String viewAttendanceReport(@RequestParam("userId") int userId, Model model) {
        List<Attendance> attendances = attendanceService.getAttendancesByUserId(userId);
        model.addAttribute("userId", userId);
        model.addAttribute("attendances", attendances);
        return "attendancereport";
    }
    
    @PostMapping("/attendance/sign-in")
    public String signIn(@RequestParam("userId") int userId) {
        LocalTime timeNow = LocalTime.now(); 
        LocalDate dateNow = LocalDate.now(); 
        attendanceService.saveSignIn(userId, timeNow,dateNow);
        return "redirect:/attendance?userId=" + userId;
    }

    @PostMapping("/attendance/sign-out")
    public String signOut(@RequestParam("userId") int userId) {
    	LocalTime now = LocalTime.now();
    	LocalDate dateNow = LocalDate.now();
        attendanceService.saveSignOut(userId, now,dateNow);
        return "redirect:/attendance?userId=" + userId;
    }    
    @GetMapping("/register")
    public String showRegister(Model model) {
        model.addAttribute("user", new User());  
        return "register";  
    }

    @PostMapping("/register")
    public String registerUser(User user, Model model) {
        User saveUser = userService.saveUser(user);
        return "redirect:/"; 
    }

    @GetMapping("/report")
    public String report(Model model) {
        model.addAttribute("users", userService.findAllUsers());
        return "report";  
    }

    @GetMapping("/logout")
    public String logout(SessionStatus status) {
        status.setComplete(); 
        return "redirect:/";
    }
    @Autowired
    private Scheduler scheduler;
    
    @GetMapping("/Schedule")
    	public @ResponseBody String Schedule() {
    	scheduler.absentUser();
    	return "schedule";
    			
    }
}