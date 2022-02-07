package io.cmartinezs.dmd.common.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.xml.bind.DatatypeConverter;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author Carlos
 * @version 1.0
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MD5Utils {
    public static String md5Hex(String string) {
        String md5Hex;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(string.getBytes(StandardCharsets.UTF_8));
            md5Hex = DatatypeConverter.printHexBinary(md.digest()).toUpperCase();
        } catch (NoSuchAlgorithmException e) {
            log.error("MD5 algorithm not found, use hashCode for {}", string);
            md5Hex = String.valueOf(string.hashCode());
        }
        return md5Hex;
    }
}
