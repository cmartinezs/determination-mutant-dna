package io.cmartinezs.dmd.app.controller;

import io.cmartinezs.dmd.app.request.MutantPost;
import io.cmartinezs.dmd.domain.port.service.MutantServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author Carlos
 * @version 1.0
 */

@RequiredArgsConstructor
@RestController
@RequestMapping("/mutant")
public class MutantController {

    private final MutantServicePort mutantServicePort;

    @PostMapping
    public ResponseEntity<Void> post(@RequestBody @Valid MutantPost request) {
        boolean isMutant = mutantServicePort.isMutant(request.getDna());
        return isMutant ? ResponseEntity.ok().build() : ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
}
