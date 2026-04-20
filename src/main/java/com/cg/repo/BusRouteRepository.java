package com.cg.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cg.entity.BusRoute;
import com.cg.entity.RouteSchedule;

@Repository
public interface BusRouteRepository extends JpaRepository<BusRoute, Long>
{
	public Optional<BusRoute> findBySrcIgnoreCaseAndDestIgnoreCase(String src,String dest);
	
}
