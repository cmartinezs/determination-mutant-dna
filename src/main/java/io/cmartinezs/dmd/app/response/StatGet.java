package io.cmartinezs.dmd.app.response;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class StatGet {
    @JsonProperty("count_mutant_dna")
    private long countMutantDna;
    @JsonProperty("count_human_dna")
    private long countHumanDna;
    private double ratio;
}
