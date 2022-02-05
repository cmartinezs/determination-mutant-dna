package io.cmartinezs.dmd.config;

import io.cmartinezs.dmd.domain.port.service.MutantServicePort;
import io.cmartinezs.dmd.domain.service.MutantServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Carlos
 * @version 1.0
 */
@Configuration
@RequiredArgsConstructor
public class MutantConfig {
    @Bean
    public MutantServicePort mutantServicePort() {
        return new MutantServiceImpl();
    }
}
