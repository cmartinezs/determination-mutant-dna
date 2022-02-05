package io.cmartinezs.dmd.domain.service;

import io.cmartinezs.dmd.domain.exception.DnaMatrixException;
import io.cmartinezs.dmd.domain.port.service.MutantServicePort;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class MutantServiceImplTest {
    MutantServicePort mutantServicePort;

    @BeforeEach
    void setUp(){
        mutantServicePort = new MutantServiceImpl();
    }

    @ParameterizedTest
    @MethodSource("isMutantParameters")
    void isMutant(MutantTestIsMutant parameter) {
        if(parameter.isException()) {
            Executable executable = () -> mutantServicePort.isMutant(parameter.getDna());
            assertThrows(DnaMatrixException.class, executable);
        } else {
            assertEquals(parameter.isMutant(), mutantServicePort.isMutant(parameter.getDna()));
        }
    }

    public static Stream<Arguments> isMutantParameters() {
        return Stream.of(
                Arguments.of(MutantTestIsMutant.builder()
                        .dna(new String[]{ "ACGTACC", "CGTACAA", "GTCCAAA", "CCAAAAC", "GTACAAA", "GTCCGAA", "GTCCGAA" })
                        .mutant(true)
                        .build()),
                Arguments.of(MutantTestIsMutant.builder()
                        .dna(new String[]{ "ACGTACC", "CGTACAA", "GTCCACT", "CCATAAC", "GTACACT", "GTCCGAT", "GTCCGAG" })
                        .build()),
                Arguments.of(MutantTestIsMutant.builder()
                        .dna(new String[]{ "ACGTACC", "CGTACAA", "GTCCAAA", "CCAAAAC", "GTACAAA", "GTCCGAA" })
                        .exception(true)
                        .build())
        );
    }

    @Getter
    @Builder
    @ToString
    static class MutantTestIsMutant {
        private String[] dna;
        private boolean mutant;
        private boolean exception;
    }
}