package org.interview.service;

import org.interview.entity.ParkingSlot;
import org.interview.enums.SlotStatus;
import org.interview.enums.VehicleType;
import org.interview.repository.ParkingLotRepository;
import org.interview.repository.ParkingSlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ParkingSlotService {
    @Autowired
    private ParkingSlotRepository slotRepository;
    @Autowired
    private ParkingLotRepository lotRepository;

    public List<ParkingSlot> getAvailableSlots(VehicleType type) {
        return slotRepository.findAvailableSlotsForType(type, SlotStatus.AVAILABLE);
    }

    @Transactional
    public ParkingSlot addSlot(ParkingSlot slot) {
        return slotRepository.save(slot);
    }

    @Transactional
    public void deleteSlot(Long id) {
        slotRepository.deleteById(id);
    }
}