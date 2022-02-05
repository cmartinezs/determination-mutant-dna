package io.cmartinezs.dmd.domain.service;

import io.cmartinezs.dmd.domain.bussines.DnaMatrix;
import io.cmartinezs.dmd.domain.port.service.MutantServicePort;
import lombok.RequiredArgsConstructor;

/**
 * @author Carlos
 * @version 1.0
 */
@RequiredArgsConstructor
public class MutantServiceImpl implements MutantServicePort {
    @Override
    public boolean isMutant(String[] dna) {
        DnaMatrix dnaMatrix = DnaMatrix.of(dna);
        return dnaMatrix.isMutant();
    }
}
