package io.cmartinezs.dmd.domain.port.persistence;

import io.cmartinezs.dmd.domain.dto.DnaSequenceDTO;

import java.util.List;
import java.util.Optional;

/**
 * @author Carlos
 * @version 1.0
 */
public interface DnaSequencePersistencePort {
    Optional<DnaSequenceDTO> getBySequence(String sequence);
    void save(DnaSequenceDTO dnaSequence);
    List<DnaSequenceDTO> getAll();
}
