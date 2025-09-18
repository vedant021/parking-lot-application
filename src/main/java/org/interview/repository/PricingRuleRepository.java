package org.interview.repository;

import org.interview.entity.PricingRule;
import org.interview.enums.VehicleType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PricingRuleRepository extends JpaRepository<PricingRule, Long> {
    Optional<PricingRule> findByVehicleType(VehicleType type);
}
