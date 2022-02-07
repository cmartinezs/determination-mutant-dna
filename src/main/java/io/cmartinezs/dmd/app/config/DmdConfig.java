package io.cmartinezs.dmd.app.config;

import io.cmartinezs.dmd.domain.port.persistence.DnaSequencePersistencePort;
import io.cmartinezs.dmd.domain.port.service.MutantServicePort;
import io.cmartinezs.dmd.domain.port.service.StatServicePort;
import io.cmartinezs.dmd.domain.service.MutantServiceImpl;
import io.cmartinezs.dmd.domain.service.StatServiceImpl;
import io.cmartinezs.dmd.infra.persistence.adapter.DnaSequenceJpaAdapter;
import io.cmartinezs.dmd.infra.persistence.repository.DnaSequenceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Carlos
 * @version 1.0
 */
@Configuration
@RequiredArgsConstructor
public class DmdConfig {
    private final DnaSequenceRepository dnaSequenceRepository;

    @Bean
    public DnaSequencePersistencePort dnaSequencePersistencePort() {
        return new DnaSequenceJpaAdapter(dnaSequenceRepository);
    }

    @Bean
    public MutantServicePort mutantServicePort() {
        return new MutantServiceImpl(dnaSequencePersistencePort());
    }

    @Bean
    public StatServicePort statServicePort() {
        return new StatServiceImpl(dnaSequencePersistencePort());
    }
}
