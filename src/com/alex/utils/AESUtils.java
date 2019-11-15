package com.alex.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * 工具类
 */
public class AESUtils {
    private static final String KEY_ALGORITHM = "AES";

    public static byte[] crypt(String algorithm, int crypt, byte[] content, byte[] password, byte[] iv) {
        try {
            //创建密码器
            Cipher cipher = Cipher.getInstance(algorithm);

            //密码key(超过16字节即128bit的key，需要替换jre中的local_policy.jar和US_export_policy.jar，否则报错：Illegal key size)
            SecretKeySpec keySpec = new SecretKeySpec(password, KEY_ALGORITHM);

            //向量iv
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);

            //初始化为加密模式的密码器
            cipher.init(crypt, keySpec, ivParameterSpec);

            //加密
            byte[] result = cipher.doFinal(content);

            return result;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return null;
    }

}
