package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.mygdx.game.main.Game;

/**
 * Created by SatXa on 5/3/17.
 */

public class Pin extends B2DSprite {

    public Pin(Body body) {
        super(body);

//        Texture tex = Game.res.getTexture("pin");
        Texture tex = Game.res.getTexture("pin");
        TextureRegion[] sprites = TextureRegion.split(tex, 16, 16)[0];

        setAnimation(sprites, 1 / 12f);
    }
}
