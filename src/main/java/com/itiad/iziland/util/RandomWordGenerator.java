package com.itiad.iziland.util;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class RandomWordGenerator {

    public String generateRandomWord() {
        int len = 15;
        String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijk"
                +"lmnopqrstuvwxyz!@#$%&";
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++)
            sb.append(chars.charAt(rnd.nextInt(chars.length())));
        return sb.toString();
    }
}
