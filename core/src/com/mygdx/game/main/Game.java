package com.mygdx.game.main;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.handlers.Content;
import com.mygdx.game.handlers.GameStateManager;
import com.mygdx.game.handlers.MyInput;
import com.mygdx.game.handlers.MyInputProcessor;

public class Game extends ApplicationAdapter {

    public static final String TITLE = "Running Players";
    public static final int WIDTH = 320;
    public static final int HEIGHT = 240;
    public static final int SCALE = 2;

    private static int level = 1;

    public static int getLevel() {
        return level;
    }

    public static void setLevel(int level) {
        Game.level = level;
    }

    public static final float STEP = 1 / 60f;
    public static Content res;
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
        Gdx.input.setInputProcessor(new MyInputProcessor());

        res = new Content();
        res.loadTexture("neku.png", "neku");
        res.loadTexture("pins.png", "pin");
        res.loadTexture("hud.png", "hud");

        res.loadSound("jump.wav", "jump");
        res.loadSound("pin_get.wav", "getPin");
        res.loadSound("dead.wav", "dead");

        res.loadMusic("3min.mp3", "song");

        Music music = Game.res.getMusic("song");
        music.setLooping(true);
        music.play();

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

            MyInput.update();
        }

//        sb.setProjectionMatrix(hudCam.combined);
//        sb.begin();
//        sb.draw(res.getTexture("neku"), 0, 0);
//        sb.end();
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
