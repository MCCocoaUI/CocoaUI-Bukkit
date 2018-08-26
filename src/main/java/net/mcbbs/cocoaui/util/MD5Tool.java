package net.mcbbs.cocoaui.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.xml.bind.annotation.adapters.HexBinaryAdapter;

public class MD5Tool {

    public static String md5(byte[] data) {
        try {
            MessageDigest messageDigest=MessageDigest.getInstance("MD5");
            byte[] result = messageDigest.digest(data);
            return new HexBinaryAdapter().marshal(result);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
