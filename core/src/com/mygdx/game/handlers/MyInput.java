package com.mygdx.game.handlers;

/**
 * Created by SatXa on 5/3/17.
 */

public class MyInput {

    public static boolean[] keys;
    public static boolean[] oldKeys;

    public static final int NUM_KEYS = 2;
    public static final int BUTTON1 = 0;
    public static final int BUTTON2 = 1;

    static {
        keys = new boolean[NUM_KEYS];
        oldKeys = new boolean[NUM_KEYS];
    }

    public static void update() {
        for(int i = 0; i < NUM_KEYS; i++) {
            oldKeys[i] = keys[i];
        }
    }

    public static void setKey(int i, boolean b) {
        keys[i] = b;
    }

    public static boolean isDown(int i) {
        return keys[i];
    }

    public static boolean isPressed(int i) {
        return keys[i] && !oldKeys[i];
    }

}
