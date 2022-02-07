package io.cmartinezs.dmd.infra.persistence.adapter;

import io.cmartinezs.dmd.domain.dto.DnaSequenceDTO;
import io.cmartinezs.dmd.domain.port.persistence.DnaSequencePersistencePort;
import io.cmartinezs.dmd.infra.persistence.entity.DnaSequence;
import io.cmartinezs.dmd.infra.persistence.repository.DnaSequenceRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Carlos
 * @version 1.0
 */
@RequiredArgsConstructor
public class DnaSequenceJpaAdapter implements DnaSequencePersistencePort {

    private final DnaSequenceRepository dnaSequenceRepository;

    @Override
    public Optional<DnaSequenceDTO> getBySequence(String sequence) {
        return dnaSequenceRepository.getBySequence(sequence).map(toDto());
    }

    @Override
    public void save(DnaSequenceDTO dnaSequence) {
        dnaSequenceRepository.save(toEntity(dnaSequence));
    }

    private DnaSequence toEntity(DnaSequenceDTO dnaSequence) {
        return DnaSequence.builder()
                .sequence(dnaSequence.getSequence())
                .mutant(dnaSequence.isMutant())
                .createdAt(dnaSequence.getCreatedAt())
                .build();
    }

    @Override
    public List<DnaSequenceDTO> getAll() {
        return dnaSequenceRepository.findAll()
                .stream()
                .map(toDto())
                .collect(Collectors.toList());
    }

    private Function<DnaSequence, DnaSequenceDTO> toDto() {
        return dnaSequence -> DnaSequenceDTO.builder()
                .mutant(dnaSequence.isMutant())
                .createdAt(dnaSequence.getCreatedAt())
                .sequence(dnaSequence.getSequence())
                .build();
    }
}
