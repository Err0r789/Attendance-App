package com.force.App1.model;

import java.time.LocalDate;

import java.time.LocalTime;

import jakarta.persistence.*;


@Entity
public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int attendanceId;

    private LocalTime signIn;
    
    private LocalTime signOut;
    
    private String status;
    
     private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    // Constructors
    public Attendance() {}

    public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public Attendance(LocalTime signIn, User user) {
        this.signIn = signIn;
        this.user = user;
        this.status = "Present"; 
    }

    public int getAttendanceId() {
        return attendanceId;
    }

    public void setAttendanceId(int attendanceId) {
        this.attendanceId = attendanceId;
    }

    public LocalTime getSignIn() {
        return signIn;
    }

    public void setSignIn(LocalTime signIn) {
        this.signIn = signIn;
    }

    public LocalTime getSignOut() {
        return signOut;
    }

    public void setSignOut(LocalTime signOut) {
        this.signOut = signOut;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }    
}
