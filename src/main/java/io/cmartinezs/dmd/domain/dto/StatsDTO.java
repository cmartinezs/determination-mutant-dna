package io.cmartinezs.dmd.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

/**
 * @author Carlos
 * @version 1.0
 */
@Getter
@Builder
@ToString
public class StatsDTO {
    private long countMutantDna;
    private long countHumanDna;
    private double ratio;
}
