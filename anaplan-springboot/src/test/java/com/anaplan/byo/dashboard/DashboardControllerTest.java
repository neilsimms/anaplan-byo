package com.anaplan.byo.dashboard;

import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import static org.junit.Assert.assertEquals;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DashboardControllerTest {
    @Mock private DashboardService dashboardService;

    @InjectMocks private DashboardController dashboardController;

    @Test
    public void listCallsService() {
        List<Dashboard> dashboardList = Lists.emptyList();
        when(dashboardService.list()).thenReturn(dashboardList);

        assertEquals(dashboardController.list().getBody(), dashboardList);
    }

    @Test
    public void getReturnsDataFromService() {
        Dashboard dashboard = new Dashboard();
        when(dashboardService.get(1)).thenReturn(dashboard);

        assertEquals(dashboardController.get(1).getBody(), dashboard);
    }

    @Test
    public void getReturnsNotFound() {
        ResponseEntity<Dashboard> dashboardResponse = dashboardController.get(1);

        assertEquals(HttpStatus.NOT_FOUND, dashboardResponse.getStatusCode());
    }

    @Test
    public void postCreatesAnEntry() {
        Dashboard dashboard = new Dashboard();
        when(dashboardService.create(dashboard)).thenReturn(Dashboard.builder().id(1).build());

        ResponseEntity<Dashboard> dashboardResponse = dashboardController.post(dashboard);

        assertEquals(HttpStatus.OK, dashboardResponse.getStatusCode());
        assertEquals(1, dashboardResponse.getBody().getId().intValue());
    }

    @Test
    public void putUpdatesAnEntry() {
        Dashboard dashboard = Dashboard.builder().id(1).title("title").build();
        when(dashboardService.update(1, "new title")).thenReturn(Dashboard.builder().id(1).title("new title").build());

        ResponseEntity<Dashboard> dashboardResponse = dashboardController.put(1, Dashboard.builder().id(1).title("new title").build());

        assertEquals(HttpStatus.OK, dashboardResponse.getStatusCode());
        assertEquals(1, dashboardResponse.getBody().getId().intValue());
        assertEquals("new title", dashboardResponse.getBody().getTitle());
    }

    @Test
    public void deleteRemovesAnEntry() {
        Dashboard dashboard = Dashboard.builder().id(1).build();
        when(dashboardService.get(1)).thenReturn(dashboard);

        ResponseEntity<Integer> dashboardResponse = dashboardController.delete(1);

        assertEquals(HttpStatus.OK, dashboardResponse.getStatusCode());
        assertEquals(1, dashboardResponse.getBody().intValue());

        verify(dashboardService).delete(dashboard);
    }
}

