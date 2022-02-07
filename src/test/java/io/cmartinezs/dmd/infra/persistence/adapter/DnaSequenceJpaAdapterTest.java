package io.cmartinezs.dmd.infra.persistence.adapter;

import io.cmartinezs.dmd.domain.dto.DnaSequenceDTO;
import io.cmartinezs.dmd.domain.port.persistence.DnaSequencePersistencePort;
import io.cmartinezs.dmd.domain.service.StatServiceImpl;
import io.cmartinezs.dmd.infra.persistence.entity.DnaSequence;
import io.cmartinezs.dmd.infra.persistence.repository.DnaSequenceRepository;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class DnaSequenceJpaAdapterTest {

    DnaSequenceRepository dnaSequenceRepository;
    DnaSequenceJpaAdapter dnaSequenceJpaAdapter;

    @BeforeEach
    void setUp(){
        dnaSequenceRepository = mock(DnaSequenceRepository.class);
        dnaSequenceJpaAdapter = new DnaSequenceJpaAdapter(dnaSequenceRepository);
    }

    @ParameterizedTest
    @MethodSource("getBySequenceParameters")
    void getBySequence(GetBySequenceParameter parameter) {
        when(dnaSequenceRepository.getBySequence(parameter.sequence)).thenReturn(Optional.ofNullable(parameter.entity));
        Optional<DnaSequenceDTO> bySequence = dnaSequenceJpaAdapter.getBySequence(parameter.sequence);
        assertEquals(parameter.empty, bySequence.isEmpty());
    }

    public static Stream<Arguments> getBySequenceParameters() {
        return Stream.of(
                Arguments.of(GetBySequenceParameter.builder().sequence("AAAAAAAAA").entity(DnaSequence.builder().build()).build()),
                Arguments.of(GetBySequenceParameter.builder().sequence("CCCCCCCCC").empty(true).build())
        );
    }

    @ParameterizedTest
    @MethodSource("saveParameters")
    void save(SaveParameter parameter) {
        when(dnaSequenceRepository.save(parameter.entity)).thenReturn(parameter.entity);
        assertDoesNotThrow(() -> dnaSequenceJpaAdapter.save(parameter.dto));
    }

    public static Stream<Arguments> saveParameters() {
        return Stream.of(
                Arguments.of(SaveParameter.builder()
                        .entity(DnaSequence.builder().build())
                        .dto(DnaSequenceDTO.builder().build())
                        .build())
        );
    }

    @ParameterizedTest
    @MethodSource("getAllParameters")
    void getAll(GetAllParameter parameter) {
        when(dnaSequenceRepository.findAll()).thenReturn(parameter.dnaSequences);
        List<DnaSequenceDTO> all = dnaSequenceJpaAdapter.getAll();
        assertEquals(parameter.dnaSequences.size(), all.size());
    }

    public static Stream<Arguments> getAllParameters() {
        return Stream.of(
                Arguments.of(GetAllParameter.builder()
                        .dnaSequences(List.of(
                                DnaSequence.builder().mutant(true).build(),
                                DnaSequence.builder().mutant(true).build(),
                                DnaSequence.builder().build(),
                                DnaSequence.builder().build(),
                                DnaSequence.builder().build()
                        ))
                        .build())
        );
    }

    @Getter
    @Builder
    @ToString
    static class GetBySequenceParameter {
        private String sequence;
        private DnaSequence entity;
        private boolean empty;
    }

    @Getter
    @Builder
    @ToString
    static class SaveParameter {
        public DnaSequenceDTO dto;
        public DnaSequence entity;
    }

    @Getter
    @Builder
    @ToString
    static class GetAllParameter {
        public List<DnaSequence> dnaSequences;
    }
}