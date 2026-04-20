package com.cg.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cg.entity.RouteSchedule;

@Repository
public interface RouteScheduleRepository extends JpaRepository<RouteSchedule, Long>
{
	@Query("select p.seatNo from Passenger p join p.booking b join b.schedule s where s.id = ?1")
	public List<Integer> getBookedSeats(Long scheduleId);
}
