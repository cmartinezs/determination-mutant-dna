package io.cmartinezs.dmd.domain.service;

import io.cmartinezs.dmd.domain.dto.DnaSequenceDTO;
import io.cmartinezs.dmd.domain.dto.StatsDTO;
import io.cmartinezs.dmd.domain.port.persistence.DnaSequencePersistencePort;
import io.cmartinezs.dmd.domain.port.service.StatServicePort;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class StatServiceImplTest {

    DnaSequencePersistencePort dnaSequencePersistencePort;
    StatServicePort statServicePort;

    @BeforeEach
    void setUp(){
        dnaSequencePersistencePort = mock(DnaSequencePersistencePort.class);
        statServicePort = new StatServiceImpl(dnaSequencePersistencePort);
    }

    @ParameterizedTest
    @MethodSource("getStatsParameters")
    void getStats(GetStatsParameter parameter) {
        when(dnaSequencePersistencePort.getAll()).thenReturn(parameter.getDnaSequences());
        StatsDTO stats = statServicePort.getStats();
        assertEquals(parameter.countHumanDna, stats.getCountHumanDna());
        assertEquals(parameter.countMutantDna, stats.getCountMutantDna());
        assertEquals(parameter.ratio, stats.getRatio());
    }

    public static Stream<Arguments> getStatsParameters() {
        return Stream.of(
                Arguments.of(GetStatsParameter.builder()
                        .dnaSequences(List.of(
                                DnaSequenceDTO.builder().mutant(true).build(),
                                DnaSequenceDTO.builder().mutant(true).build(),
                                DnaSequenceDTO.builder().build(),
                                DnaSequenceDTO.builder().build(),
                                DnaSequenceDTO.builder().build()
                        ))
                        .countMutantDna(2).countHumanDna(3).ratio(2.0/3.0)
                        .build()
                ),
                Arguments.of(GetStatsParameter.builder()
                        .dnaSequences(Collections.emptyList())
                        .countMutantDna(0).countHumanDna(0).ratio(0)
                        .build()
                ),
                Arguments.of(GetStatsParameter.builder()
                        .dnaSequences(List.of(
                                DnaSequenceDTO.builder().mutant(true).build(),
                                DnaSequenceDTO.builder().mutant(true).build()
                        ))
                        .countMutantDna(2).countHumanDna(0).ratio(0)
                        .build()
                )
        );
    }

    @Getter
    @Builder
    @ToString
    static class GetStatsParameter {
        private List<DnaSequenceDTO> dnaSequences;
        private int countHumanDna;
        private int countMutantDna;
        private double ratio;
    }
}