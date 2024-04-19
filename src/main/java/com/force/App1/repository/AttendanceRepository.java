package com.force.App1.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.force.App1.model.Attendance;
import com.force.App1.model.User;

public interface AttendanceRepository extends JpaRepository<Attendance, Integer> {

	Attendance save(Attendance attendance);

	List<Attendance> findByUserId(int userId);
	
	  @Query("SELECT a FROM Attendance a WHERE a.user.id = :userId AND DATE(a.signIn) = :currentDate AND a.signOut IS NULL")
	    Optional<Attendance> findLatestByUserIdAndCurrentDate(@Param("userId") int userId, @Param("currentDate") LocalDate currentDate);
	  
	  @Query("SELECT a FROM Attendance a WHERE a.user.id = :userId AND a.date = :date ORDER BY a.signIn ASC")
	    List<Attendance> findByUserIdAndDate(@Param("userId") int userId, @Param("date") LocalDate date);
	  
	  @Query("SELECT a FROM Attendance a WHERE a.user.id = :userId AND a.date = :date AND a.signOut IS NULL")
	    List<Attendance> findByUserIdAndDateWithNoSignOut(@Param("userId") int userId, @Param("date") LocalDate date);

	    @Query("SELECT u FROM User u WHERE NOT EXISTS (SELECT a FROM Attendance a WHERE a.user = u AND a.date = :date)")
	    List<User> findUsersWithNoAttendanceOnDate(@Param("date") LocalDate date);
}