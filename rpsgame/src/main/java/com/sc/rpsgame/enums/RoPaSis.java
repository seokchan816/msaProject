package com.sc.rpsgame.enums;

import java.util.Random;

public enum RoPaSis {
    ROCK("바위"), PAPER("보"),SCISSORS("가위");

    private String commentary;

    private RoPaSis(String commentary) {
        this.commentary = commentary;
    }

    public String getCommentary() {
        return commentary;
    }

    private static final Random RAND = new Random();

    public static RoPaSis getRps(){
        RoPaSis[] rpsArray=values();
        return rpsArray[RAND.nextInt(rpsArray.length)];
    }
}
