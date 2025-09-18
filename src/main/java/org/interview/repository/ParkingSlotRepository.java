package org.interview.repository;

import org.interview.entity.ParkingLot;
import org.interview.entity.ParkingSlot;
import org.interview.enums.SlotStatus;
import org.interview.enums.VehicleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.LockModeType;
import java.util.List;

public interface ParkingSlotRepository extends JpaRepository<ParkingSlot, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT s FROM ParkingSlot s WHERE s.type = :type AND s.status = :status")
    List<ParkingSlot> findAvailableSlotsForType(@Param("type") VehicleType type, @Param("status") SlotStatus status);

    List<ParkingSlot> findByLotAndFloor(ParkingLot lot, int floor);
}
