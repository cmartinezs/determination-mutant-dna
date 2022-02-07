package io.cmartinezs.dmd.domain.service;

import io.cmartinezs.dmd.domain.bussines.Stats;
import io.cmartinezs.dmd.domain.dto.StatsDTO;
import io.cmartinezs.dmd.domain.port.persistence.DnaSequencePersistencePort;
import io.cmartinezs.dmd.domain.port.service.StatServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;

/**
 * @author Carlos
 * @version 1.0
 */
@RequiredArgsConstructor
public class StatServiceImpl implements StatServicePort {
    private final DnaSequencePersistencePort dnaSequencePersistencePort;

    @Override
    @Cacheable("getStats")
    public StatsDTO getStats() {
        return Stats.of(dnaSequencePersistencePort.getAll()).toStatsDTO();
    }
}
