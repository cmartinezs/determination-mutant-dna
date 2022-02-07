package io.cmartinezs.dmd.domain.port.service;

import io.cmartinezs.dmd.domain.dto.DnaSequenceDTO;

import java.util.Optional;

/**
 * @author Carlos
 * @version 1.0
 */
public interface MutantServicePort {
    Optional<DnaSequenceDTO> getBySequence(String[] dna);
    boolean isMutant(String[] dna);
    void save(DnaSequenceDTO dnaSequenceDTO);
}
