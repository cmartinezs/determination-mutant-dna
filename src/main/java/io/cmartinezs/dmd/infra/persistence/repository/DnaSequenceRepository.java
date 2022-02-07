package io.cmartinezs.dmd.infra.persistence.repository;

import io.cmartinezs.dmd.infra.persistence.entity.DnaSequence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Carlos
 * @version 1.0
 */
@Repository
public interface DnaSequenceRepository extends JpaRepository<DnaSequence, Long> {
    Optional<DnaSequence> getByMd5(String md5);
}
