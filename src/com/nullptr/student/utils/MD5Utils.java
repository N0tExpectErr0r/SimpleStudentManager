package com.nullptr.student.utils;

import com.nullptr.student.service.StudentServiceImpl;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 静态方法的MD5工具类
 * created by 梁文俊
 * date:2018-4-16
 */
public class MD5Utils {
    /**
     * Md5加盐加密(本管理软件以用户名作为盐值。因为用户名不可改)
     * @param password:用于加密的妈咪
     * @param salt:盐值
     * @return 加密后的字符串
     */
    public static String generate(String password, String salt) {
        if (password == null || password.length() == 0) {
            throw new IllegalArgumentException("Password to encript cannot be null or zero length");
        }
        if (salt == null || salt.length() == 0) {
            throw new IllegalArgumentException("salt to encript cannot be null or zero length");
        }
        password += salt;
        StringBuffer hexString = new StringBuffer();
        try {
            //用md5来加密字符串
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
            byte[] hash = md.digest();
            //填充字符串(不足的地方补0,凑够位数)
            for (int i = 0; i < hash.length; i++) {
                if ((0xff & hash[i]) < 0x10) {
                    hexString.append("0" + Integer.toHexString((0xFF & hash[i])));
                } else {
                    hexString.append(Integer.toHexString(0xFF & hash[i]));
                }
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return hexString.toString();
    }
}
