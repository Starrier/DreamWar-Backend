package org.starrier.dreamwar.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author Starrier
 * @date 2019/1/10.
 */
public class MD5Utils {


    /**
     * Encrypt the source String as a byte array via using MD5.
     *
     * @param source which is the target String.
     * @return return the {@code result} which has encrypted with MD5.
     */
    public static byte[] encode2bytes(String source) {
        byte[] result = null;

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.reset();
            md.update(source.getBytes("UTF-8"));
            result = md.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static String encode2hex(String source) {
        byte[] data = encode2bytes(source);
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < data.length; i++) {
            String hex = Integer.toHexString(0xff & data[i]);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    public static boolean validate(String validateString, String encryptHex) {
        return encryptHex.equals(encode2hex(validateString));
    }
}
