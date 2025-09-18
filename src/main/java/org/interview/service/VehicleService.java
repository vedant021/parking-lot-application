package org.interview.service;

import org.interview.entity.User;
import org.interview.entity.Vehicle;
import org.interview.enums.VehicleType;
import org.interview.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VehicleService {
    @Autowired
    private VehicleRepository vehicleRepository;

    public Vehicle findOrCreateVehicle(String plateNo, VehicleType type, User owner) {
        Vehicle vehicle = vehicleRepository.findByPlateNo(plateNo).orElse(null);
        if (vehicle != null) {
            return vehicle;
        }
        Vehicle v = new Vehicle();
        v.setPlateNo(plateNo);
        v.setType(type);
        v.setOwner(owner);
        return vehicleRepository.save(v);
    }

    public Vehicle getByPlateNo(String plateNo) {
        return vehicleRepository.findByPlateNo(plateNo).orElse(null);
    }
}