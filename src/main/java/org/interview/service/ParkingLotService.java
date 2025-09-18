package org.interview.service;

import org.interview.entity.ParkingLot;
import org.interview.repository.ParkingLotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParkingLotService {
    @Autowired
    private ParkingLotRepository repo;

    public List<ParkingLot> getAll() {
        return repo.findAll();
    }

    public ParkingLot get(Long id) {
        return repo.findById(id).orElse(null);
    }
}