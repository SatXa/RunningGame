package com.mygdx.game.states;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.handlers.GameStateManager;
import com.mygdx.game.main.Game;

/**
 * Created by SatXa on 6/3/17.
 */

public class LevelMenu extends GameState {

    private TextureRegion reg;
    private GameButton[][] buttons;

    public LevelMenu(GameStateManager gsm) {
        super(gsm);

        reg = new TextureRegion(Game.res.getTexture("bgs"), 0, 0, 320, 240);

        TextureRegion buttonReg = new TextureRegion(Game.res.getTexture("hud"), 0, 0, 32, 32);
        buttons = new GameButton[5][5];

        for (int row = 0; row < buttons.length; row++) {
            for int col = 0; col < buttons[0].length; col++) {
                buttones[row][col] = new GameButton(buttonReg, 80 + col * 40, 200 - row * 40, cam)
            }
        }
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

    }
}
