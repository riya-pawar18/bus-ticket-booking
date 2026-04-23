package com.cg.service;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.cg.dtos.RouteScheduleDto;
import com.cg.entity.BusRoute;
import com.cg.entity.RouteSchedule;
import com.cg.exceptions.BadRequestException;
import com.cg.exceptions.NotFoundException;
import com.cg.repo.BusRouteRepository;
import com.cg.repo.RouteScheduleRepository;
import com.cg.service.BTBService;

@SpringBootTest
public class BusBookingService {
	private Optional<BusRoute> optRoute, emptyRoute;

    @MockitoBean
    private BusRouteRepository brRepo;

    @MockitoBean
    private RouteScheduleRepository rsRepo;

    @Autowired
    private BTBService service;

    @BeforeEach
    public void beforeEach() {
        BusRoute route = new BusRoute();
        route.setId(1L);

        optRoute = Optional.of(route);
        emptyRoute = Optional.empty();
    }


    @Test
    public void testCreateScheduleSuccess() {

        RouteScheduleDto dto = new RouteScheduleDto();
        dto.setRouteId(1L);
        dto.setDepartureTime(LocalTime.of(10, 30));
        dto.setScheduleDt(LocalDate.now().plusDays(1));
        dto.setAvlSeats(40);
        dto.setTotSeats(40);
        dto.setBusFare(500.0);

        Mockito.when(brRepo.findById(1L)).thenReturn(optRoute);
        Mockito.when(rsRepo.save(Mockito.any(RouteSchedule.class)))
                .thenReturn(new RouteSchedule());

        RouteSchedule result = service.createSchedule(dto);

        assertNotNull(result);

        Mockito.verify(brRepo).findById(1L);
        Mockito.verify(rsRepo).save(Mockito.any(RouteSchedule.class));
    }

 
    @Test
    public void testCreateScheduleRouteNotFound() {

        RouteScheduleDto dto = new RouteScheduleDto();
        dto.setRouteId(2L);

        Mockito.when(brRepo.findById(2L)).thenReturn(emptyRoute);

        assertThrows(NotFoundException.class, () -> {
            service.createSchedule(dto);
        });

        Mockito.verify(brRepo).findById(2L);
    }

 
    @Test
    public void testCreateSchedulePastDate() {

        RouteScheduleDto dto = new RouteScheduleDto();
        dto.setRouteId(1L);
        dto.setDepartureTime(LocalTime.of(10, 30));
        dto.setScheduleDt(LocalDate.now().minusDays(1));

        Mockito.when(brRepo.findById(1L)).thenReturn(optRoute);

        assertThrows(BadRequestException.class, () -> {
            service.createSchedule(dto);
        });

        Mockito.verify(brRepo).findById(1L);
    }
}

