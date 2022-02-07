package io.cmartinezs.dmd.domain.port.service;

import io.cmartinezs.dmd.domain.dto.StatsDTO;

/**
 * @author Carlos
 * @version 1.0
 */
public interface StatServicePort {
    StatsDTO getStats();
}
