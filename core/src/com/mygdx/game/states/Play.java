package com.mygdx.game.states;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.handlers.GameStateManager;

/**
 * Created by SatXa on 5/3/17.
 */

public class Play extends GameState {

    private World world;
    private Box2DDebugRenderer b2dr;

    public Play(GameStateManager gsm) {
        super(gsm);

        world = new World(new Vector2(0, -9.81f), true);
        b2dr = new Box2DDebugRenderer();

        BodyDef bdef = new BodyDef();
        bdef.position.set(160, 120);
        bdef.type = BodyDef.BodyType.StaticBody;

        Body body = world.createBody(bdef);
    }

    @Override
    public void dispose() {

    }

    @Override
    public void handleInput() {

    }

    @Override
    public void update(float delta) {
        world.step(delta, 6, 2);
    }

    @Override
    public void render() {
        b2dr.render(world,cam.combined);
    }
}
