package org.interview.service;

import org.interview.entity.PricingRule;
import org.interview.enums.VehicleType;
import org.interview.repository.PricingRuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PricingRuleService {
    @Autowired
    private PricingRuleRepository repo;

    public List<PricingRule> getAll() {
        return repo.findAll();
    }

    public PricingRule addOrUpdate(PricingRule rule) {
        return repo.save(rule);
    }

    public void deleteRule(Long id) {
        repo.deleteById(id);
    }

    public PricingRule getForType(VehicleType type) {
        return repo.findByVehicleType(type).orElse(null);
    }
}