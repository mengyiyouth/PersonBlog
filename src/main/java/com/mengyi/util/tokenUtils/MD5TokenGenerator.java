package com.mengyi.util.tokenUtils;

import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

/**
 * @author mengyiyouth
 * @date 2020/7/18 11:44
 * 使用MD5算法根据入参即时间戳生成Token
 **/
@Component
public class MD5TokenGenerator implements TokenGenerator {
    /**
     * 生成Token
     * @param strings
     * @return
     */
    @Override
    public String generate(String... strings) {
        long timestamp = System.currentTimeMillis();
        String tokenMeta = "";
        for(String s: strings){
            tokenMeta += s;
        }
        tokenMeta += timestamp;
        String token = DigestUtils.md5DigestAsHex(tokenMeta.getBytes());
        return token;
    }

    public static void main(String[] args) {
        System.out.println(DigestUtils.md5DigestAsHex("123".getBytes()));
    }
}
