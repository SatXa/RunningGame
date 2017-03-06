package com.mygdx.game.handlers;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;

/**
 * Created by SatXa on 5/3/17.
 */

public class MyInputProcessor extends InputAdapter {

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.W) {
            MyInput.setKey(MyInput.UP, true);
        }
        if (keycode == Input.Keys.A) {
            MyInput.setKey(MyInput.LEFT, true);
        }
        if (keycode == Input.Keys.D) {
            MyInput.setKey(MyInput.RIGHT, true);
        }
        if (keycode == Input.Keys.SPACE) {
            MyInput.setKey(MyInput.SWITCH, true);
        }
        if (keycode == Input.Keys.R) {
            MyInput.setKey(MyInput.RESPAWN, true);
        }


        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.W) {
            MyInput.setKey(MyInput.UP, false);
        }
        if (keycode == Input.Keys.A) {
            MyInput.setKey(MyInput.LEFT, false);
        }
        if (keycode == Input.Keys.D) {
            MyInput.setKey(MyInput.RIGHT, false);
        }
        if (keycode == Input.Keys.SPACE) {
            MyInput.setKey(MyInput.SWITCH, false);
        }
        if (keycode == Input.Keys.R) {
            MyInput.setKey(MyInput.RESPAWN, false);
        }

        return true;
    }
}
