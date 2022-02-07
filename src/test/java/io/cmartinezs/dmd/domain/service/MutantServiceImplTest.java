package io.cmartinezs.dmd.domain.service;

import io.cmartinezs.dmd.domain.dto.DnaSequenceDTO;
import io.cmartinezs.dmd.domain.exception.DnaMatrixException;
import io.cmartinezs.dmd.domain.port.service.MutantServicePort;
import io.cmartinezs.dmd.infra.persistence.adapter.DnaSequenceJpaAdapter;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.dao.DataIntegrityViolationException;

import static org.mockito.Mockito.*;

import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class MutantServiceImplTest {
    DnaSequenceJpaAdapter dnaSequenceJpaAdapter;
    MutantServicePort mutantServicePort;

    @BeforeEach
    void setUp(){
        dnaSequenceJpaAdapter = mock(DnaSequenceJpaAdapter.class);
        mutantServicePort = new MutantServiceImpl(dnaSequenceJpaAdapter);
    }

    @ParameterizedTest
    @MethodSource("isMutantParameters")
    void isMutant(IsMutantParameters parameter) {
        if(parameter.isException()) {
            Executable executable = () -> mutantServicePort.isMutant(parameter.getDna());
            assertThrows(DnaMatrixException.class, executable);
        } else {
            assertEquals(parameter.isMutant(), mutantServicePort.isMutant(parameter.getDna()));
        }
    }

    public static Stream<Arguments> isMutantParameters() {
        return Stream.of(
                Arguments.of(IsMutantParameters.builder()
                        .dna(new String[]{ "ACGTACC", "CGTACAA", "GTCCAAA", "CCAAAAC", "GTACAAA", "GTCCGAA", "GTCCGAA" })
                        .mutant(true)
                        .build()),
                Arguments.of(IsMutantParameters.builder()
                        .dna(new String[]{ "ACGTACC", "CGTACAA", "GTCCACT", "CCATAAC", "GTACACT", "GTCCGAT", "GTCCGAG" })
                        .build()),
                Arguments.of(IsMutantParameters.builder()
                        .dna(new String[]{ "ACGTACC", "CGTACAA", "GTCCAAA", "CCAAAAC", "GTACAAA", "GTCCGAA" })
                        .exception(true)
                        .build())
        );
    }

    @ParameterizedTest
    @MethodSource("getBySequenceParameters")
    void getBySequence(GetBySequenceParameters parameters) {
        when(dnaSequenceJpaAdapter.getByMd5(anyString())).thenReturn(Optional.ofNullable(parameters.dto));
        Optional<DnaSequenceDTO> bySequence = mutantServicePort.getByMd5(parameters.dna);
        assertEquals(parameters.empty, bySequence.isEmpty());
    }

    public static Stream<Arguments> getBySequenceParameters() {
        return Stream.of(
                Arguments.of(GetBySequenceParameters.builder()
                        .dna(new String[]{ "ACGTACC", "CGTACAA", "GTCCAAA", "CCAAAAC", "GTACAAA", "GTCCGAA", "GTCCGAA" })
                        .empty(true)
                        .build()),
                Arguments.of(GetBySequenceParameters.builder()
                        .dna(new String[]{ "ACGTACC", "CGTACAA", "GTCCACT", "CCATAAC", "GTACACT", "GTCCGAT", "GTCCGAG" })
                        .dto(DnaSequenceDTO.builder().build())
                        .build())
        );
    }

    @ParameterizedTest
    @MethodSource("saveParameters")
    void save(SaveParameters parameters) {
        if(parameters.isException()) {
            doThrow(DataIntegrityViolationException.class).when(dnaSequenceJpaAdapter).save(any(DnaSequenceDTO.class));
        }
        assertDoesNotThrow(() -> mutantServicePort.save(parameters.dto));
    }

    public static Stream<Arguments> saveParameters() {
        return Stream.of(
                Arguments.of(SaveParameters.builder().dto(DnaSequenceDTO.builder().build()).exception(true).build()),
                Arguments.of(SaveParameters.builder().dto(DnaSequenceDTO.builder().build()).build())
        );
    }

    @Getter
    @Builder
    @ToString
    static class IsMutantParameters {
        private String[] dna;
        private boolean mutant;
        private boolean exception;
    }

    @Getter
    @Builder
    @ToString
    static class GetBySequenceParameters {
        private String[] dna;
        private DnaSequenceDTO dto;
        private boolean empty;
    }

    @Getter
    @Builder
    @ToString
    static class SaveParameters {
        private DnaSequenceDTO dto;
        private boolean exception;
    }
}