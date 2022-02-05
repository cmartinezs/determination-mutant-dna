package io.cmartinezs.dmd.domain.bussines;

import io.cmartinezs.dmd.domain.exception.DnaMatrixException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Carlos
 * @version 1.0
 */
@Slf4j
@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class DnaMatrix {

    private static final int MIN_LENGTH_SEQUENCE = 4;
    private static final int MIN_SEQUENCES = 2;
    private static final Pattern ADN_NITROGENIC_BASES = Pattern.compile("^[ACGT]*$");
    private static final Pattern ADN_MUTANT_SEQUENCE = Pattern.compile("^(.*)(A{4}|C{4}|G{4}|T{4})(.*)$");

    @NonNull
    private final char[][] matrix;
    private long verticalCount;
    private long horizontalCount;
    private long diagonalCount;
    private boolean analyzed;

    public static DnaMatrix of(@NonNull String[] dna) {
        validateDnaMatrix(dna);
        return new DnaMatrix(
                Arrays.stream(dna)
                        .map(String::toCharArray)
                        .collect(Collectors.toList())
                        .toArray(new char[][]{})
        );
    }

    private static void validateDnaMatrix(@NonNull String[] dna) {
        int size = dna.length;

        if(size < MIN_LENGTH_SEQUENCE) {
            throw new DnaMatrixException("The DNA sequence is not present or does not have the minimum length necessary to evaluate");
        }

        for (String s : dna) {
            if (s.length() != size) {
                throw new DnaMatrixException("It is not an NxN matrix");
            }

            if(!ADN_NITROGENIC_BASES.matcher(s).matches()){
                throw new DnaMatrixException("Not a valid DNA sequence: " + s);
            }
        }
    }

    public boolean isMutant() {
        if(!this.isAnalyzed()) {
            this.analyze();
        }
        return this.verticalCount + this.horizontalCount + this.diagonalCount > 0;
    }

    private void analyze() {
        this.horizontalCount = countHorizontalSequence();
        this.verticalCount = countVerticalSequence();
        this.diagonalCount = countDiagonalSequence();
        this.analyzed = true;
    }

    private long countHorizontalSequence() {
        return countSequence(this.matrix);
    }

    private long countVerticalSequence() {
        return countSequence(leftRotateMatrix(this.matrix));
    }

    private long countSequence(@NonNull char[][] matrix) {
        return Arrays.stream(matrix)
                .map(String::copyValueOf)
                .filter(this.getMatchesMutantPredicate())
                .count();
    }

    private long countDiagonalSequence() {
        return Stream.of(this.getDiagonal(this.matrix), this.getDiagonal(this.leftRotateMatrix(this.matrix)))
                .filter(this.getMatchesMutantPredicate())
                .count();
    }

    private String getDiagonal(@NonNull char[][] matrix) {
        int length = matrix.length;
        StringBuilder sb = new StringBuilder();
        for(int row = 0; row < length; row++) {
            sb.append(matrix[row][row]);
        }
        return sb.toString();
    }

    private char[][] leftRotateMatrix(@NonNull char[][] matrix) {
        int length = matrix.length;
        char[][] auxMatrix = new char[length][length];
        for(int row = 0; row < length; row++) {
            for(int col = 0; col < length; col++) {
                auxMatrix[length-col-1][row] = matrix[row][col];
            }
        }
        return auxMatrix;
    }

    private Predicate<String> getMatchesMutantPredicate() {
        return s -> {
            boolean matches = ADN_MUTANT_SEQUENCE.matcher(s).matches();
            log.info("secuence: {} - matches: {}", s, matches);
            return matches;
        };
    }
}
