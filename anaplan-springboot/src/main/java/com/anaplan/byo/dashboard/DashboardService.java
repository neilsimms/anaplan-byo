package com.anaplan.byo.dashboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class DashboardService {
    @Autowired
    private DashboardRepository dashboardRepository;

    public Dashboard create(Dashboard dashboard) {
        return dashboardRepository.save(dashboard);
    }

    public Dashboard update(Integer id, String title) {
        Optional<Dashboard> existingRecord = dashboardRepository.findById(id);
        if (existingRecord.isPresent()) {
            Dashboard dashboard = existingRecord.get();
            dashboard.setTitle(title);
            dashboard.setUpdatedAt(LocalDateTime.now());

            return dashboardRepository.save(dashboard);
        } else {
            return dashboardRepository.save(Dashboard.builder().id(id).title(title).build());
        }
    }

    public List<Dashboard> list() {
        Iterable<Dashboard> all = dashboardRepository.findAll();

        return StreamSupport
                .stream(all.spliterator(), false)
                .collect(Collectors.toList());
    }

    public Dashboard get(Integer id) {
        Optional<Dashboard> dashboardOptional = dashboardRepository.findById(id);
        return dashboardOptional.orElse(null);
    }

    public void delete(Dashboard dashboard) {
        dashboardRepository.delete(dashboard);
    }
}
