package io.cmartinezs.dmd.domain.service;

import io.cmartinezs.dmd.domain.bussines.DnaMatrix;
import io.cmartinezs.dmd.domain.dto.DnaSequenceDTO;
import io.cmartinezs.dmd.domain.port.persistence.DnaSequencePersistencePort;
import io.cmartinezs.dmd.domain.port.service.MutantServicePort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import java.util.Arrays;
import java.util.Optional;

/**
 * @author Carlos
 * @version 1.0
 */
@Slf4j
@RequiredArgsConstructor
public class MutantServiceImpl implements MutantServicePort {

    private final DnaSequencePersistencePort dnaSequencePersistencePort;

    @Override
    @Cacheable("getBySequence")
    public Optional<DnaSequenceDTO> getBySequence(String[] dna) {
        return dnaSequencePersistencePort.getBySequence(String.join("-", dna));
    }

    @Override
    public boolean isMutant(String[] dna) {
        DnaMatrix dnaMatrix = DnaMatrix.of(dna);
        return dnaMatrix.isMutant();
    }

    @Override
    @CacheEvict("getStats")
    public void save(DnaSequenceDTO dnaSequenceDTO) {
        try {
            dnaSequencePersistencePort.save(dnaSequenceDTO);
        } catch (Exception e) {
            log.warn("{} - For this DNA sequence there is already a record. | {} | {}", dnaSequenceDTO.getSequence(), e.getClass().getSimpleName(), e.getMessage());
        }
    }
}
