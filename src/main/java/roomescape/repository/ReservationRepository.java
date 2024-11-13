package roomescape.repository;

import roomescape.domain.Reservation;
import roomescape.dto.ReservationDto;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository {

    Reservation save(ReservationDto reservationDto);

    Optional<Reservation> findById(Long reservationId);

    List<Reservation> findAll();

    public Optional<Reservation> delete(Long reservationId);

}
