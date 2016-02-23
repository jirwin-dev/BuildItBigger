package com.joshirwin.comedian;

import java.util.Random;

public class Comedian {
    public String tellJoke() {
        boolean readOk = true;
        String joke = "";
        Random rand = new Random();
        if (readOk)
            joke = rand.nextBoolean() ? "This a really funny joke" : "THIS JOKE IS FUNNIER BECAUSE ITS IN CAPS";
        return joke;
    }
}
