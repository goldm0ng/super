package roomescape.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationDto;
import roomescape.exception.NotFoundReservationException;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

@Controller
public class ReservationController {

    private List<Reservation> reservations = new ArrayList<>();
    private AtomicLong index = new AtomicLong(1);

    @GetMapping("/reservation")
    public String showReservationPage(){
        return "reservation";
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> readReservation(){
        return ResponseEntity.ok(reservations);
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> createReservation(@Valid  @RequestBody ReservationDto reservationDto){
        Reservation reservation = new Reservation(
                index.getAndIncrement(),
                reservationDto.getName(),
                reservationDto.getDate(),
                reservationDto.getTime()
        );
        reservations.add(reservation);

        return ResponseEntity.created(URI.create("/reservations/"+reservation.getId())).body(reservation);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id){
        Reservation reservation = reservations.stream()
                .filter(r -> Objects.equals(r.getId(), id))
                .findFirst()
                .orElseThrow(()->new NotFoundReservationException());

        reservations.remove(reservation);
        return ResponseEntity.noContent().build();
    }
}
