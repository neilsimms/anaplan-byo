package com.anaplan.byo.dashboard;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController()
@RequestMapping("/dashboards")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Dashboard>> list() {
        log.info("list");

        return new ResponseEntity<>(dashboardService.list(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Dashboard> get(@PathVariable(name = "id") Integer id) {
        log.info("get {}", id);

        Dashboard dashboard = dashboardService.get(id);
        if (dashboard == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(dashboard, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Dashboard> post(@RequestBody Dashboard dashboard) {
        log.info("post {}", dashboard);

        return new ResponseEntity<>(dashboardService.create(dashboard), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Dashboard> put(@PathVariable(name = "id") Integer id, @RequestBody Dashboard dashboard) {
        log.info("put {} {}", id, dashboard);

        return new ResponseEntity<>(dashboardService.update(id, dashboard.getTitle()), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Integer> delete(@PathVariable(name = "id") Integer id) {
        log.info("delete {}", id);

        Dashboard dashboard = dashboardService.get(id);
        dashboardService.delete(dashboard);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

}
