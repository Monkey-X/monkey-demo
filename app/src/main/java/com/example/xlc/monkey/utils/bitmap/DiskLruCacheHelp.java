package com.example.xlc.monkey.utils.bitmap;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author:xlc
 * @date:2019/4/10
 * @descirbe:
 */
public class DiskLruCacheHelp {


    //计算url的MD5的值作为key
    private String hashKeyForDisk(String url) {
        String cacheKey;

        try {
            MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(url.getBytes());
            cacheKey = bytesToHexString(mDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            cacheKey = String.valueOf(url.hashCode());
        }

        return cacheKey;
    }


    private String bytesToHexString(byte[] bytes) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }

        return sb.toString();
    }
}
