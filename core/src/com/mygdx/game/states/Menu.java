package com.mygdx.game.states;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.entities.B2DSprite;
import com.mygdx.game.handlers.Animation;
import com.mygdx.game.handlers.GameStateManager;
import com.mygdx.game.main.Game;

/**
 * Created by SatXa on 6/3/17.
 */

public class Menu extends GameState {

//    private Background bg;
    private Animation animation;
//    private GameButton playButton;

    private World world;
    private Box2DDebugRenderer b2dRenderer;

    private Array<B2DSprite> blocks;

    public Menu(GameStateManager gsm) {
        super(gsm);

        Texture tex = Game.res.getTexture("menu");
//        bg = new Background(new TextureRegion(tex), cam, 1f);
//        bg.setVector(-20, 0);

//        tex = Game.res.getTexture("standing_neku");
        tex = Game.res.getTexture("neku");
        TextureRegion[] reg = new TextureRegion[4];

        for(int i = 0; i < reg.length; i++) {
            reg[i] = new TextureRegion(tex, i * 32, 0, 32, 32);
        }
        animation = new Animation(reg, 1 / 12f);
    }

    @Override
    public void handleInput() {
//        if (playButton.isClicked()) {
//            Game.res.getSound("getPin");
//            gsm.setState(GameStateManager.LEVEL_MENU);
//        }
    }

    @Override
    public void update(float delta) {
        handleInput();

        world.step(delta / 5, 8, 3);

//        bg.update(delta);
        animation.update(delta);
//        playButton.update(delta);
    }

    @Override
    public void render() {
        sb. setProjectionMatrix(cam.combined);

//        bg.render(sb);
//        playButton.render(sb);

        sb.begin();
        sb.draw(animation.getFrame(), 64, 64);
//        sb.draw(new BitmapFont()); // TITLE?
        sb.end();
    }

    @Override
    public void dispose() {

    }
}
