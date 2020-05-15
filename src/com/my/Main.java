package com.my;

import static com.my.MyUtil.decryptDES;
import static com.my.MyUtil.encryptDES;

public class Main {

    public static void main(String[] args) {
        String plaintext1 = "aaaaaaaa";
        System.out.println("待加密字符串: " + plaintext1);
        String ciphertext = encryptDES(plaintext1);
        System.out.println("加密后：" + ciphertext);
        String plaintext2 = decryptDES(ciphertext);
        System.out.println("解密后：" + plaintext2);
    }

}
