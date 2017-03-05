package com.mygdx.game.states;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.mygdx.game.handlers.GameStateManager;

/**
 * Created by SatXa on 5/3/17.
 */

public class Play extends GameState {

    private BitmapFont font = new BitmapFont();

    public Play(GameStateManager gsm) {
        super(gsm);
    }

    @Override
    public void dispose() {

    }

    @Override
    public void handleInput() {

    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void render() {
//        System.out.println(sb + "SB");
//        System.out.println(cam.combined + "CAM");
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        font.draw(sb, "PLAYING", 100, 100);
        sb.end();
    }
}
