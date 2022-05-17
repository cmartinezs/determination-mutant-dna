package io.cmartinezs.dmd.common.util;

import lombok.experimental.UtilityClass;

import javax.xml.bind.DatatypeConverter;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author Carlos
 * @version 1.0
 */
@UtilityClass
public class HashUtils {
    public String hex(String toHash, String algorithm, Charset charset) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance(algorithm);
        md.update(toHash.getBytes(charset));
        return DatatypeConverter.printHexBinary(md.digest());
    }
}
