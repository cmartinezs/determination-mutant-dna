package io.cmartinezs.dmd.app.controller;

import io.cmartinezs.dmd.app.request.MutantPost;
import io.cmartinezs.dmd.domain.dto.DnaSequenceDTO;
import io.cmartinezs.dmd.domain.port.service.MutantServicePort;
import lombok.Builder;
import lombok.ToString;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class MutantControllerTest {

    MutantController mutantController;
    MutantServicePort mutantServicePort;

    @BeforeEach
    void setUp() {
        mutantServicePort = mock(MutantServicePort.class);
        mutantController = new MutantController(mutantServicePort);
    }

    @ParameterizedTest
    @MethodSource("postOkParameters")
    void postOk(MutantTestPost mutantTestPost) {
        when(mutantServicePort.getBySequence(any())).thenReturn(Optional.ofNullable(mutantTestPost.dto));
        when(mutantServicePort.isMutant(any())).thenReturn(mutantTestPost.isMutant);
        ResponseEntity<Void> post = mutantController.post(mutantTestPost.request);
        assertEquals(mutantTestPost.status, post.getStatusCode());
    }

    public static Stream<Arguments> postOkParameters() {
        return Stream.of(
                Arguments.of(
                        MutantTestPost.builder()
                                .request(new MutantPost(new String[]{}))
                                .isMutant(true)
                                .status(HttpStatus.OK)
                                .build()
                ),
                Arguments.of(
                        MutantTestPost.builder()
                                .request(new MutantPost(new String[]{}))
                                .dto(DnaSequenceDTO.builder().mutant(true).build())
                                .status(HttpStatus.OK)
                                .build()
                ),
                Arguments.of(
                        MutantTestPost.builder()
                                .request(new MutantPost(new String[]{}))
                                .dto(DnaSequenceDTO.builder().build())
                                .status(HttpStatus.FORBIDDEN)
                                .build()
                ),
                Arguments.of(
                        MutantTestPost.builder()
                                .request(new MutantPost(new String[]{}))
                                .status(HttpStatus.FORBIDDEN)
                                .build()
                )
        );
    }

    @Builder
    @ToString
    static class MutantTestPost {
        private MutantPost request;
        private DnaSequenceDTO dto;
        private boolean isMutant;
        private HttpStatus status;
    }
}