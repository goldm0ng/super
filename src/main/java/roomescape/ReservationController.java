package roomescape;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.exception.MissingReservationDataException;
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
    @ResponseBody
    public ResponseEntity<List<Reservation>> readReservation(){
        return ResponseEntity.ok(reservations);
    }

    @PostMapping("/reservations")
    @ResponseBody
    public ResponseEntity<Reservation> createReservation(@RequestBody Reservation reservation){
        if (reservation.getName().isEmpty() || reservation.getDate().isEmpty() ||reservation.getTime().isEmpty()){
            throw new MissingReservationDataException("예약 필수 정보가 입력되지 않았습니다.");
        }

        reservation.setId(index.getAndIncrement());
        reservations.add(reservation);

        return ResponseEntity.created(URI.create("/reservations/"+reservation.getId())).body(reservation);
    }

    @DeleteMapping("/reservations/{id}")
    @ResponseBody
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id){
        Reservation reservation = reservations.stream()
                .filter(r -> Objects.equals(r.getId(), id))
                .findFirst()
                .orElseThrow(()->new NotFoundReservationException("예악을 찾을 수 없습니다."));

        reservations.remove(reservation);
        return ResponseEntity.noContent().build();
    }
}
