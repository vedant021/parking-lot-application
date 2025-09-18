package org.interview.controller;

import org.interview.dto.PricingRuleDTO;
import org.interview.entity.PricingRule;
import org.interview.service.PricingRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/pricing-rules")
public class PricingRuleController {

    @Autowired
    private PricingRuleService pricingRuleService;

    @GetMapping
    public List<PricingRuleDTO> getAll() {
        return pricingRuleService.getAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public PricingRuleDTO addRule(@RequestBody PricingRuleDTO ruleDto) {
        PricingRule rule = pricingRuleService.addOrUpdate(fromDTO(ruleDto));
        return toDTO(rule);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteRule(@PathVariable Long id) {
        pricingRuleService.deleteRule(id);
    }

    private PricingRuleDTO toDTO(PricingRule rule) {
        if (rule == null) return null;
        PricingRuleDTO dto = new PricingRuleDTO();
        dto.setId(rule.getId());
        dto.setVehicleType(rule.getVehicleType());
        dto.setInitialHours(rule.getInitialHours());
        dto.setInitialFee(rule.getInitialFee());
        dto.setHourlyFee(rule.getHourlyFee());
        return dto;
    }

    private PricingRule fromDTO(PricingRuleDTO dto) {
        PricingRule rule = new PricingRule();
        rule.setId(dto.getId());
        rule.setVehicleType(dto.getVehicleType());
        rule.setInitialHours(dto.getInitialHours());
        rule.setInitialFee(dto.getInitialFee());
        rule.setHourlyFee(dto.getHourlyFee());
        return rule;
    }
}