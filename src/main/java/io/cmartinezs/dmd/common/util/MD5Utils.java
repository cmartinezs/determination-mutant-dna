package io.cmartinezs.dmd.common.util;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;

/**
 * @author Carlos
 * @version 1.0
 */
@Slf4j
@UtilityClass
public class MD5Utils {

    private static final String HASH_ALGORITHM = "MD5";

    /**
     *
     * @param toHash
     * @return
     */
    public String md5Hex(String toHash) {
        String md5Hex;
        try {
            md5Hex = HashUtils.hex(toHash, HASH_ALGORITHM, StandardCharsets.UTF_8);
        } catch (NoSuchAlgorithmException e) {
            log.error("MD5 algorithm not found, use hashCode for {}", toHash);
            md5Hex = String.valueOf(toHash.hashCode());
        }
        return md5Hex;
    }
}
