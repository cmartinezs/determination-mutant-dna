package io.cmartinezs.dmd.domain.bussines;

import io.cmartinezs.dmd.domain.dto.DnaSequenceDTO;
import io.cmartinezs.dmd.domain.dto.StatsDTO;
import lombok.Getter;

import java.util.List;

/**
 * @author Carlos
 * @version 1.0
 */
@Getter
public class Stats {
    private final long countMutants;
    private final long countHumans;

    private Stats(long countMutants, long countHumans) {
        this.countMutants = countMutants;
        this.countHumans = countHumans;
    }

    public static Stats of(List<DnaSequenceDTO> dnaSequences) {
        var countMutants = dnaSequences.stream().filter(DnaSequenceDTO::isMutant).count();
        var countHumans = dnaSequences.stream().filter(dnaSequence -> !dnaSequence.isMutant()).count();
        return new Stats(countMutants, countHumans);
    }

    public double getRatio() {
        return countHumans == 0 ? 0.0 : (double)countMutants / (double)countHumans;
    }

    public StatsDTO toStatsDTO() {
        return StatsDTO.builder()
                .ratio(this.getRatio())
                .countHumanDna(this.countHumans)
                .countMutantDna(this.countMutants)
                .build();
    }
}
