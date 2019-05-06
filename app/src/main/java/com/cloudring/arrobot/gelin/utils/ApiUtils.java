package com.cloudring.arrobot.gelin.utils;

import org.apache.commons.codec.digest.DigestUtils;

import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;

public class ApiUtils{

    public static final String geling_ak="vcat8yoczbgjkiofrnp5yt4xpx2ojcv0b1xtpkc3znkkm4ctgelej8p5mo5oifsy";
    public static final String geling_sk="k49UIqY1GHGaKv1s5WMpkhHPCeynlYJv1czyqcfNGCD1c3xgxfyP2lROEHiuKxXc";
    public static final String geling_url="http://test-rscloud.getlearn.cn/api/index/index";

    /**
     * 生成签名
     *
     * @param parameters 参数（名称与值）
     * @param secretKey  密钥
     * @return 签名字符串
     */
    public static String generateSignature(SortedMap<String, Object> parameters, String secretKey) {
        StringBuilder sb = new StringBuilder("");

        //改造为普通map遍历的方式
        SortedMap<String, Object> map = new TreeMap<String, Object>();
        map = parameters;
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }

        // 拼接 SK（SecretKey）
        sb.deleteCharAt(sb.length()-1);
        sb.append(secretKey);
        System.out.println(sb.toString());

        // MD5 Hash
        String signature = DigestUtils.md5Hex(sb.toString());

        return signature;
    }

    /**
     * 生成时间戳
     *
     * @return 时间戳
     */
    public static long generateTimestamp() {
        return System.currentTimeMillis() / 1000;
    }

    /**
     * 解密数据
     *
     * @param encryptedData 经过加密的数据
     * @param secretKey     密钥
     * @return 解密后数据
     */
    public static String decryptData(String encryptedData, String secretKey) {
        SymmetricCrypto aes = new SymmetricCrypto(SymmetricAlgorithm.AES, secretKey.substring(0, 16)
                .getBytes());
        return aes.decryptStr(encryptedData, CharsetUtil.CHARSET_UTF_8);
    }

    /**
     * 创建API请求并返回结果
     *
 //    * @param apiUrl     API地址
 //    * @param parameters 参数
 //    * @param secretKey  SecretKey
     * @return 请求响应结果
     */
    /**
     * 创建API请求并返回结果
     *
   //  * @param apiUrl     API地址
   //  * @param parameters 参数
   //  * @param secretKey  SecretKey
     * @return 请求响应结果
     */
    public static HttpResponse create(String apiUrl, SortedMap<String, Object> parameters, String secretKey) {
        return HttpRequest.post(apiUrl)
                .form(parameters)
                .form("sign", generateSignature(parameters, secretKey))
                .execute();
    }

    public static String decodeUnicode(String unicodeStr) {
        if (unicodeStr == null) {
            return null;
        }
        StringBuffer retBuf = new StringBuffer();
        int maxLoop = unicodeStr.length();
        for (int i = 0; i < maxLoop; i++) {
            if (unicodeStr.charAt(i) == '\\') {
                if ((i < maxLoop - 5)
                        && ((unicodeStr.charAt(i + 1) == 'u') || (unicodeStr
                        .charAt(i + 1) == 'U')))
                    try {
                        retBuf.append((char) Integer.parseInt(
                                unicodeStr.substring(i + 2, i + 6), 16));
                        i += 5;
                    } catch (NumberFormatException localNumberFormatException) {
                        retBuf.append(unicodeStr.charAt(i));
                    }
                else
                    retBuf.append(unicodeStr.charAt(i));
            } else {
                retBuf.append(unicodeStr.charAt(i));
            }
        }
        return retBuf.toString();
    }
}
