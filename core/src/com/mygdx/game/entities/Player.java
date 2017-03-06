package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.mygdx.game.main.Game;

/**
 * Created by SatXa on 5/3/17.
 */

public class Player extends B2DSprite {

    private int numPoints;
    private int totalPoints;

    public Player(Body body) {
        super(body);

        Texture tex = Game.res.getTexture("neku");
        TextureRegion[] sprites = TextureRegion.split(tex, 64, 64)[0];
        setAnimation(sprites, 1 / 12f);
    }

    public void addPoint() {
        numPoints++;
    }

    public int getNumPoints() {
        return numPoints;
    }

    public int getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(int totalPoints) {
        this.totalPoints = totalPoints;
    }
}
