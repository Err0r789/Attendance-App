package com.force.App1.shedule;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.force.App1.model.Attendance;
import com.force.App1.model.User;
import com.force.App1.service.AttendanceService;
import com.force.App1.service.UserService;

@Component
public class Scheduler {

	@Autowired
	private UserService userService;

	@Autowired
	private AttendanceService attendanceService;
				//Updates every day at 8pm
	@Scheduled(cron = "0 0 20 * * ?")
	public void absentUser() {
		System.out.println("Started");
		LocalDate date = LocalDate.now();
		List<User> allUsers = userService.findAllUsers();

		for (User user : allUsers) {
			List<Attendance> attendances = attendanceService.findAttendancesByUserIdAndDate(user.getId(),date);
			if (attendances.isEmpty()) {
				attendanceService.defaultAttendance(user.getId(),date);
			} else {
				for (Attendance attendance : attendances) {
					if (attendance.getSignOut() == null) {
						attendanceService.updateSignOutTime(attendance, LocalTime.now());
					}
				}
			}
		}
		System.out.println("exited");
	}
}
