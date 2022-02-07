package io.cmartinezs.dmd.domain.service;

import io.cmartinezs.dmd.domain.bussines.DnaMatrix;
import io.cmartinezs.dmd.domain.dto.DnaSequenceDTO;
import io.cmartinezs.dmd.domain.port.persistence.DnaSequencePersistencePort;
import io.cmartinezs.dmd.domain.port.service.MutantServicePort;
import io.cmartinezs.dmd.common.util.MD5Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

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
    @Cacheable("getByMd5")
    public Optional<DnaSequenceDTO> getByMd5(String[] dna) {
        return dnaSequencePersistencePort.getByMd5(MD5Utils.md5Hex(String.join("-", dna)));
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
