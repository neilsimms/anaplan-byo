package com.anaplan.byo.dashboard;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class DashboardServiceTest {
    @Mock
    private DashboardRepository dashboardRepository;
    @InjectMocks
    private DashboardService dashboardService;

    @Test
    public void createSavesTheDataToRepository() {
        Dashboard dashboard = new Dashboard();
        when(dashboardRepository.save(dashboard)).thenReturn(Dashboard.builder().id(5).build());

        Dashboard returned = dashboardService.create(dashboard);

        assertNotNull(returned);
        assertEquals(5, returned.getId().intValue());
    }

    @Test
    public void updateSavesTheDataToRepository() {
        Dashboard dashboard = Dashboard.builder().id(1).title("old title").build();
        when(dashboardRepository.findById(1)).thenReturn(Optional.of(dashboard));
        when(dashboardRepository.save(dashboard)).thenReturn(dashboard);

        Dashboard returned = dashboardService.update(1, "new title");

        assertNotNull(returned);
        assertEquals(1, returned.getId().intValue());
        assertEquals("new title", returned.getTitle());
    }

    @Test
    public void listCallsRepository() {
        Iterable<Dashboard> dashboards = Arrays.asList(new Dashboard());
        when(dashboardRepository.findAll()).thenReturn(dashboards);

        List<Dashboard> list = dashboardService.list();
        assertNotNull(list);
    }

    @Test
    public void getCallsRepository() {
        Dashboard dashboard = new Dashboard();
        Optional<Dashboard> optional = Optional.of(dashboard);
        when(dashboardRepository.findById(1)).thenReturn(optional);

        Dashboard returned = dashboardService.get(1);
        assertNotNull(returned);
        assertEquals(dashboard, returned);
    }

    @Test
    public void deleteCallsDelete() {
        Dashboard dashboard = new Dashboard();

        dashboardService.delete(dashboard);

        verify(dashboardRepository).delete(dashboard);
    }

}
