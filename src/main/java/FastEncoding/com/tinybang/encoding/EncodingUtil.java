package com.tinybang.encoding;


import com.tinybang.encoding.encodor.Base64;

import java.io.File;
import java.io.FileInputStream;

/**
 * @author conan.cao
 */
public class EncodingUtil {

    public static String base64BytesToString(byte[] bytes) {
        return Base64.encodeToString(bytes, false);
    }

    public static byte[] base64StringToBytes(String str) {
        return Base64.decode(str);
    }

    public static byte[] decodeBytesToString(String str) throws Exception {
        return new sun.misc.BASE64Decoder().decodeBuffer(str);
    }

    public static String encodeStringToBytes(byte[] bytes) {
        return new sun.misc.BASE64Encoder().encode(bytes);
    }

    public static String bytesToHexString(byte[] bytes) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (bytes == null || bytes.length <= 0) {
            return null;
        }
        for (int i = 0; i < bytes.length; i++) {
            int v = bytes[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    /**
     * Convert hex string to byte[]
     *
     * @param hexString the hex string
     * @return byte[]
     */
    public static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }

    protected static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    public static byte[] stringToBytesUTFCustom(String str) {
        byte[] b = new byte[str.length() << 1 + 1];
        for (int i = 0; i < str.length(); i++) {
            char strChar = str.charAt(i);
            int bpos = i << 1;
            b[bpos] = (byte) ((strChar & 0xFF00) >> 8);
            b[bpos + 1] = (byte) (strChar & 0x00FF);
        }

        b[b.length - 1] = b[0];

        return b;
    }

    public static String bytesToStringUTFCustom(byte[] bytes) {
        char[] buffer = new char[bytes.length >> 1];
        for (int i = 0; i < buffer.length; i++) {
            int bpos = i << 1;
            char c = (char) (((bytes[bpos] & 0x00FF) << 8) + (bytes[bpos + 1] & 0x00FF));
            buffer[i] = c;
        }
        return new String(buffer);
    }

    public static void main(String[] args) throws Exception {
        String sourceText = "invitationToken=36E8DF0C-9D40-416D-8530-F4DEC0412B90&accountOID=36E8DF0C-9D40-416D-8530-F4DEC0412B90&contactOID=36E8DF0C-9D40-416D-8530-F4DEC0412B90&appName=StarCite_MarketView&targetURL=http://www.marketview.com/36E8DF0C-9D40-416D-8530-F4DEC0412B90";

        String encodedText = EncodingUtil.base64BytesToString(sourceText.getBytes());

        System.out.println(encodedText);
        System.out.println(encodedText.length());

        String decodedText = new String(EncodingUtil.base64StringToBytes(encodedText));
        System.out.println(decodedText);
    }
}

