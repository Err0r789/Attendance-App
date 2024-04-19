package com.force.App1.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.force.App1.model.Attendance;
import com.force.App1.model.User;
import com.force.App1.repository.AttendanceRepository;
import com.force.App1.repository.UserRepository;

@Service
public class AttendanceService {

	@Autowired
	private AttendanceRepository attendanceRepository;

	@Autowired
	private UserRepository userRepository;

	public void saveSignIn(int userId, LocalTime time, LocalDate date) {
		User user = userRepository.findById(userId).orElse(null);
		if (user != null) {
			Attendance newAttendance = new Attendance();
			newAttendance.setUser(user);
			newAttendance.setSignIn(time);
			newAttendance.setDate(date);
			newAttendance.setStatus("Present");
			attendanceRepository.save(newAttendance);
		}
	}

	public void saveSignOut(int userId, LocalTime time, LocalDate currentDate) {
		Optional<Attendance> attendanceOpt = attendanceRepository.findLatestByUserIdAndCurrentDate(userId, currentDate);
		if (attendanceOpt.isPresent()) {
			Attendance attendance = attendanceOpt.get();
			attendance.setSignOut(time);
			attendanceRepository.save(attendance);
		}
	}

	public List<Attendance> getAttendancesByUserId(int userId) {
		return attendanceRepository.findByUserId(userId);
	}

	public Optional<Attendance> findLatestByUserIdAndCurrentDate(int userId, LocalDate date) {
		return attendanceRepository.findLatestByUserIdAndCurrentDate(userId, date);
	}

	public void updateSignOut(Attendance attendance, LocalTime signOutTime) {
		attendance.setSignOut(signOutTime);
		attendanceRepository.save(attendance);
	}

	public List<Attendance> findAttendancesByUserIdAndDate(int userId, LocalDate date) {
		return attendanceRepository.findByUserIdAndDate(userId, date);
	}

	public void updateSignOutTime(Attendance attendance, LocalTime signOutTime) {
		attendance.setSignOut(signOutTime);
		attendanceRepository.save(attendance);
	}

	public void defaultAttendance(int userId, LocalDate date) {
		Attendance attendance = new Attendance();
		attendance.setUser(new User(userId));
		attendance.setDate(date);
		attendance.setSignIn(LocalTime.MIDNIGHT);
		attendance.setSignOut(LocalTime.MIDNIGHT);
		attendance.setStatus("Absent");
		attendanceRepository.save(attendance);
	}

}
