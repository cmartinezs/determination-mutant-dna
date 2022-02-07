package io.cmartinezs.dmd.app.controller;

import io.cmartinezs.dmd.app.response.StatGet;
import io.cmartinezs.dmd.domain.dto.StatsDTO;
import io.cmartinezs.dmd.domain.port.service.StatServicePort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Carlos
 * @version 1.0
 */

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/stats")
public class StatController {

    private final StatServicePort statServicePort;

    @GetMapping
    public ResponseEntity<StatGet> get() {
        return ResponseEntity.ok(toStatGet(statServicePort.getStats()));
    }

    private StatGet toStatGet(StatsDTO stats) {
        return StatGet.builder()
                .countHumanDna(stats.getCountHumanDna())
                .countMutantDna(stats.getCountMutantDna())
                .ratio(stats.getRatio())
                .build();
    }
}
