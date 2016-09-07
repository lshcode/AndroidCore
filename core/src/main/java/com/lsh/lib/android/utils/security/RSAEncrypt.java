package com.lsh.lib.android.utils.security;

import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

/**
 * RAS加密
 * author:liush
 * version: 2016/4/18  11:52
 */
public class RSAEncrypt {
    public static final String KEY_ALGORITHM = "RSA";
    public static final String SIGNATURE_ALGORITHM = "MD5withRSA";

    private static final String PUBLIC_KEY = "RSAPublicKey";
    private static final String PRIVATE_KEY = "RSAPrivateKey";

    public static byte[] decryptBASE64(String privateKey) {
        byte[] output = null;
        output = Base64Utils.decode(privateKey);
        return output;
    }

    public static String encryptBASE64(byte[] keyBytes) {
        String s = Base64Utils.encode(keyBytes);
        return s;
    }

    /**
     * 解密<br>
     * 用私钥解密
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPrivateKey(byte[] data, String key)
            throws Exception {
        // 对密钥解密
        byte[] keyBytes = decryptBASE64(key);

        // 取得私钥
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);

        // 对数据解密
        Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return cipher.doFinal(data);
    }

    /**
     * 加密<br>
     * 用公钥加密
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPublicKey(byte[] data, String key)
            throws Exception {
        // 对公钥解密
        byte[] keyBytes = decryptBASE64(key);

        // 取得公钥
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicKey = keyFactory.generatePublic(x509KeySpec);

        // 对数据加密
        Cipher cipher = Cipher.getInstance("RSA/None/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);

        return cipher.doFinal(data);
        /**
         * 关于javax.crypto.BadPaddingException: Blocktype异常的几种解决办法

         转载请注明出处

         1.异常描述：最近做项目为了增强数据传输的安全性用到了RSA加密。即android客户端将要传送的信息，用私钥通过RSA非对称加密算法加密后，传到服务器端(PC端)。服务器端用对应(密钥)的公钥来解密时解密失败，抛出“javax.crypto.BadPaddingException: Blocktype”异常。


         2.异常原因：Android系统使用的虚拟机（dalvik）跟SUN标准JDK是有所区别的，其中他们默认的RSA实现就不同。即Android端用Cipher.getInstance("RSA")方法进行加密时，使用的provider是Bouncycastle Security provider，Bouncycastle Security provider默认实现的是“RSA/None/NoPadding”算法，而服务器(PC)端用Cipher.getInstance("RSA")进行解密时，使用的是Sun的security provider，实现的是“RSA/None/PKCS1Padding”算法，所以，解密时会失败。


         3.解决办法：我这里提供三种解决办法：

         第一种：将服务器(pc)端的Cipher.getInstance("RSA")方法改为Cipher.getInstance("RSA/ECB/NoPadding")。但这种改法有一个缺点就是解密后的明文比加密之前多了很多空格。(空格的长度个数+原来的明文字符数=产生密钥时采用的bit数/8)


         第二种：将Android端的Cipher.getInstance("RSA")方法改为Cipher.getInstance("RSA/None/PKCS1Padding")。这种方法解密后的明文和加密前的明文是对应的，不会出现第一种方法中的现象，推荐这种方法。


         第三种：在服务器(pc)端的jdk中加入Bouncycastle Security provider，关于Bouncycastle JCE的安装配置及验证请参看 http://blog.csdn.net/caoshichao520326/article/details/8732670， 配置好Bouncycastle Security provider后，将服务器(pc)端的Cipher.getInstance("RSA")方法改为Cipher.getInstance("RSA"，"BC")。
         */
    }


    /**
     * 取得私钥
     *
     * @param keyMap
     * @return
     * @throws Exception
     */
    public static String getPrivateKey(Map keyMap) throws Exception {
        Key key = (Key) keyMap.get(PRIVATE_KEY);

        return encryptBASE64(key.getEncoded());
    }

    /**
     * 取得公钥
     *
     * @param keyMap
     * @return
     * @throws Exception
     */
    public static String getPublicKey(Map keyMap) throws Exception {
        Key key = (Key) keyMap.get(PUBLIC_KEY);

        return encryptBASE64(key.getEncoded());
    }

    /**
     * 初始化密钥
     *
     * @return
     * @throws Exception
     */
    public static Map initKey() throws Exception {
        KeyPairGenerator keyPairGen = KeyPairGenerator
                .getInstance(KEY_ALGORITHM);
        keyPairGen.initialize(1024);

        KeyPair keyPair = keyPairGen.generateKeyPair();

        // 公钥
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();

        // 私钥
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        Map keyMap = new HashMap();
        keyMap.put(PUBLIC_KEY, publicKey);
        keyMap.put(PRIVATE_KEY, privateKey);
        return keyMap;
    }

    /**
     * 将二进制转换成16进制
     *
     * @param buf
     * @return
     */
    public static String parseByte2HexStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * 将16进制转换为二进制
     *
     * @param hexStr
     * @return
     */
    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1)
            return null;
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2),
                    16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }


    /**
     * 加密
     *
     * @param value 要加密的数据
     * @param key   加密key
     * @return
     */
    public static String encrypt(String value, String key) {
        String encryptStr = "";
        try {
            byte[] encodedData = RSAEncrypt.encryptByPublicKey(value.getBytes(), key);
            encryptStr = RSAEncrypt.encryptBASE64(encodedData);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encryptStr;
    }

    /**
     * 测试生成密钥
     *
     * @param args
     */
    public static void main(String[] args) {
        Map keyMap;
        try {
            keyMap = initKey();
            String publicKey = getPublicKey(keyMap);
            System.out.println("publicKey:" + publicKey + "\n");
            String privateKey = getPrivateKey(keyMap);
            System.out.println("privateKey:" + privateKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
