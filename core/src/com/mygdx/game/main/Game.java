package com.mygdx.game.main;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.handlers.GameStateManager;

public class Game extends ApplicationAdapter {

    public static final String TITLE = "Running Players";
    public static final int WIDTH = 320;
    public static final int HEIGHT = 240;
    public static final int SCALE = 2;

    public static final float STEP = 1 / 60f;
    private float accum;

    private SpriteBatch sb;
    private OrthographicCamera cam;
    private OrthographicCamera hudCam;

    private GameStateManager gsm;

    public SpriteBatch getSpriteBatch() {
        return this.sb;
    }

    public OrthographicCamera getCamera() {
        return this.cam;
    }

    public OrthographicCamera getHUDCamera() {
        return this.hudCam;
    }

    @Override
    public void create() {

        this.sb = new SpriteBatch();
        this.cam = new OrthographicCamera();
        this.hudCam = new OrthographicCamera();

        cam.setToOrtho(false, WIDTH, HEIGHT);
        hudCam.setToOrtho(false, WIDTH, HEIGHT);

        this.gsm = new GameStateManager(this);
    }

    @Override
    public void render() {
        accum += Gdx.graphics.getDeltaTime();

        while (accum >= STEP) {
            accum -= STEP;
            gsm.update(STEP);
            gsm.render();
        }
    }

    @Override
    public void dispose() {

    }

    @Override
    public void pause() {
        super.pause();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void resume() {
        super.resume();
    }
}
