package com.my;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.security.SecureRandom;

public class MyUtil {

    // DES加密密钥key
    private static String key = "vpRZ1kmU";
    // 偏移写死
    private static String iv = "EbpU4WtY";

    // DES加密明文plaintext
    public static String encryptDES(String plaintext) {
        try {
            // 首先，DES算法要求有一个可信任的随机数源，可以通过 SecureRandom类,内置两种随机数算法，NativePRNG和SHA1PRNG
//            SecureRandom random = new SecureRandom(); //可替代 偏移ivi
            // 创建一个DESKeySpec对象
            DESKeySpec desKey = new DESKeySpec(key.getBytes());
            // 创建一个密匙工厂
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            // 将DESKeySpec对象转换成SecretKey对象
            SecretKey securekey = keyFactory.generateSecret(desKey);
            // Cipher对象实际完成加密操作
            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            //初始化偏移
            IvParameterSpec ivi = new IvParameterSpec(iv.getBytes("UTF-8"));
            // 用密匙初始化Cipher对象
            cipher.init(cipher.ENCRYPT_MODE, securekey, ivi);
            // 加密生成密文byte数组
            byte[] cipherBytes = cipher.doFinal(plaintext.getBytes());
            // 将密文byte数组转化为16进制密文
            String ciphertext = byteToHex(cipherBytes);
            return ciphertext;
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }

    // DES解密
    public static String decryptDES(String ciphertext) {
        try {
            // DES算法要求有一个可信任的随机数源，SecureRandom内置两种随机数算法，NativePRNG和SHA1PRNG,
            // 通过new来初始化，默认来说会使用NativePRNG算法生成随机数，但是也可以配置java.security参数来修改调用的算法，
            // 如果是/dev/[u]random两者之一就是NativePRNG，否则就是SHA1PRNG。
//            SecureRandom random = new SecureRandom(); //可替代 偏移ivi
            // 创建一个DESKeySpec对象
            DESKeySpec desKey = new DESKeySpec(key.getBytes());
            // 创建一个密匙工厂
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            // 将DESKeySpec对象转换成SecretKey对象
            SecretKey securekey = keyFactory.generateSecret(desKey);
            // Cipher对象实际完成解密操作
            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            //初始化偏移
            IvParameterSpec ivi = new IvParameterSpec(iv.getBytes("UTF-8"));
            // 用密匙初始化Cipher对象
            cipher.init(cipher.DECRYPT_MODE, securekey, ivi);
            // 将16进制密文转化为密文byte数组
            byte[] cipherBytes = hexToByte(ciphertext);
            // 真正开始解密操作
            String plaintext = new String(cipher.doFinal(cipherBytes));
            return plaintext;
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }

    // 将byte转化为16进制
    public static String byteToHex(byte[] bs) {
        if (bs.length == 0) {
            return "";
        } else {
            StringBuffer stringBuffer = new StringBuffer();
            for (int i = 0; i < bs.length; i++) {
                String s = Integer.toHexString(bs[i] & 0xFF);
                if (1 == s.length()) {
                    stringBuffer.append("0");
                }
                stringBuffer = stringBuffer.append(s);
            }
            return stringBuffer.toString();
        }
    }

    // 将16进制转化为byte
    public static byte[] hexToByte(String ciphertext) {
        byte[] cipherBytes = ciphertext.getBytes();
        if ((cipherBytes.length % 2) != 0) {
            throw new IllegalArgumentException("长度不为偶数");
        } else {
            byte[] result = new byte[cipherBytes.length / 2];
            for (int i = 0; i < cipherBytes.length; i += 2) {
                String item = new String(cipherBytes, i, 2);
                result[i / 2] = (byte) Integer.parseInt(item, 16);
            }
            return result;
        }
    }

}
