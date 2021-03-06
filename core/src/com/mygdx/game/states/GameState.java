package com.mygdx.game.states;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.handlers.GameStateManager;
import com.mygdx.game.main.Game;

/**
 * Created by SatXa on 5/3/17.
 */

public abstract class GameState {

    protected GameStateManager gsm;
    protected Game game;

    protected SpriteBatch sb;
    protected OrthographicCamera cam;
    protected OrthographicCamera hudCam;

    protected GameState(GameStateManager gsm) {
        this.gsm = gsm;
        this.game = gsm.game();
        this.sb = game.getSpriteBatch();
        this.cam = game.getCamera();
        this.hudCam = game.getHUDCamera();
    }

    public abstract void handleInput();

    public abstract void update(float delta);

    public abstract void render();

    public abstract void dispose();

}
