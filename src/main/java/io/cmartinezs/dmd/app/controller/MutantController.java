package io.cmartinezs.dmd.app.controller;

import io.cmartinezs.dmd.app.request.MutantPost;
import io.cmartinezs.dmd.domain.dto.DnaSequenceDTO;
import io.cmartinezs.dmd.domain.port.service.MutantServicePort;
import io.cmartinezs.dmd.common.util.MD5Utils;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author Carlos
 * @version 1.0
 */

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/mutant")
public class MutantController {

    private final MutantServicePort mutantServicePort;

    @PostMapping
    public ResponseEntity<Void> post(@RequestBody @Valid MutantPost request) {
        var requestDna = request.getDna();
        var bySequence = mutantServicePort.getByMd5(requestDna);
        var mutant = new AtomicBoolean();
        bySequence.ifPresentOrElse(
                dnaSequenceDTO -> {
                    mutant.set(dnaSequenceDTO.isMutant());
                    log.info("{} - DNA determination from persistence. Is mutant = {}", requestDna, mutant.get());
                },
                () -> {
                    mutant.set(mutantServicePort.isMutant(requestDna));
                    mutantServicePort.save(createDnaSequenceDto(requestDna, mutant.get()));
                    log.info("{} - Calculated DNA determination. Is mutant = {}", requestDna, mutant.get());
                }
        );
        return mutant.get() ? ResponseEntity.ok().build() : ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    private DnaSequenceDTO createDnaSequenceDto(@NonNull String[] requestDna, boolean mutant) {
        String sequence = String.join("-", requestDna);
        return DnaSequenceDTO.builder()
                .mutant(mutant)
                .sequence(sequence)
                .md5(MD5Utils.md5Hex(sequence))
                .build();
    }
}
