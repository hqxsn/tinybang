package com.tinybang.lb.chh;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by TinyBang.
 * User: andy.song
 * Date: Jul 23, 2010
 * Time: 1:06:19 PM
 * To change this template use File | Settings | File Templates.
 */
public class MD5HashFunction implements HashFunction {

    ThreadLocal<MessageDigest> md5 = new ThreadLocal<MessageDigest>() {

        @Override
        public MessageDigest get() {
            return super.get();
        }

        @Override
        protected MessageDigest initialValue() {
            try {
                return MessageDigest.getInstance("MD5");
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public void remove() {
            super.remove();
        }

        @Override
        public void set(MessageDigest value) {
            super.set(value);
        }

    };

    @Override
    public Long hash(String key) {
        MessageDigest md5Ins = md5.get();
        md5Ins.reset();
        md5Ins.update(key.getBytes());
        byte[] bKey = md5Ins.digest();
        return ((long) (bKey[3] & 0xFF) << 24) | ((long) (bKey[2] & 0xFF) << 16) | ((long) (bKey[1] & 0xFF) << 8) | (long) (bKey[0] & 0xFF);
    }

    @Override
    public Long hash(Object key) {
        return hash(key.toString());
    }
}
