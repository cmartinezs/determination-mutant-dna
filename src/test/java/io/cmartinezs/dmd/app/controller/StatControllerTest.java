package io.cmartinezs.dmd.app.controller;

import io.cmartinezs.dmd.app.response.StatGet;
import io.cmartinezs.dmd.domain.dto.StatsDTO;
import io.cmartinezs.dmd.domain.port.service.StatServicePort;
import lombok.Builder;
import lombok.ToString;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class StatControllerTest {

    StatServicePort statServicePort;
    StatController statController;

    @BeforeEach
    void setUp() {
        statServicePort = mock(StatServicePort.class);
        statController = new StatController(statServicePort);
    }

    @ParameterizedTest
    @MethodSource("getParameters")
    void get(GetParameter getParameter) {
        int countHumanDna = getParameter.countHumanDna;
        int countMutantDna = getParameter.countMutantDna;
        double ratio = getParameter.ratio;
        when(statServicePort.getStats())
                .thenReturn(StatsDTO.builder().countHumanDna(countHumanDna).countMutantDna(countMutantDna).ratio(ratio).build());
        ResponseEntity<StatGet> statGetResponseEntity = statController.get();
        assertEquals(HttpStatus.OK, statGetResponseEntity.getStatusCode());
        StatGet body = statGetResponseEntity.getBody();
        assertNotNull(body);
        assertEquals(countHumanDna, body.getCountHumanDna());
        assertEquals(countMutantDna, body.getCountMutantDna());
        assertEquals(ratio, body.getRatio());
    }

    public static Stream<Arguments> getParameters() {
        return Stream.of(
                Arguments.of(GetParameter.builder().countHumanDna(2).countMutantDna(3).ratio(2.0/3.0).build()),
                Arguments.of(GetParameter.builder().countHumanDna(0).countMutantDna(0).ratio(0).build()),
                Arguments.of(GetParameter.builder().countHumanDna(2).countMutantDna(0).ratio(0).build())
        );
    }

    @Builder
    @ToString
    static class GetParameter {
        private int countHumanDna;
        private int countMutantDna;
        private double ratio;
    }
}