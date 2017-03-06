package com.mygdx.game.handlers;

/**
 * Created by SatXa on 5/3/17.
 */

public class MyInput {

    public static int x;
    public static int y;
    public static boolean down;
    public static boolean oldDown;

    public static final int NUM_KEYS = 5;
    public static final int UP = 0;
    public static final int LEFT = 1;
    public static final int RIGHT = 2;
    public static final int SWITCH = 3;
//    public static final int RESPAWN = 4;
    public static boolean[] keys;
    public static boolean[] oldKeys;

    static {
        keys = new boolean[NUM_KEYS];
        oldKeys = new boolean[NUM_KEYS];
    }

    public static void update() {
        for (int i = 0; i < NUM_KEYS; i++) {
            oldKeys[i] = keys[i];
            oldDown = down;
        }
    }

    // KEYBOARD

    public static void setKey(int i, boolean b) {
        keys[i] = b;
    }

    public static boolean isDown(int i) {
        return keys[i] && !oldKeys[i];
    }

    public static boolean isPressed(int i) {
        return keys[i] && !oldKeys[i];
    }

    // PHONE

    public static boolean isDown() {
        return down;
    }

    public static boolean isPressed() {
        return down && !oldDown;
    }

    public static boolean isReleased() {
        return !down && oldDown;
    }

}
