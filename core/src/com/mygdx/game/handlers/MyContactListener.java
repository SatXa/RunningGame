package com.mygdx.game.handlers;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.utils.Array;

/**
 * Created by SatXa on 5/3/17.
 */

public class MyContactListener implements ContactListener {

    private boolean playerOnGround;
    private Array<Body> trashBin;

    public MyContactListener() {
        super();
        trashBin = new Array<Body>();
    }

    // Called when 2 fixtures start collision
    @Override
    public void beginContact(Contact contact) {
        Fixture fa = contact.getFixtureA(); // GROUND | PLAYER
        Fixture fb = contact.getFixtureB(); //  FOOT  |  PIN

        if (fa == null || fb == null) {
            return;
        }

        if (fa.getUserData() != null && fa.getUserData().equals("foot")) {
//            playerOnGround = true;
        }

        if (fb.getUserData() != null && fb.getUserData().equals("foot")) {
            playerOnGround = true;
        }

        if (fb.getUserData() != null && fb.getUserData().equals("pin")) {
//            trashBin.add(fa.getBody());
        }

        if (fb.getUserData() != null && fb.getUserData().equals("pin")) {
            trashBin.add(fb.getBody());
        }
    }

    // Called when 2 fixtures no longer collide
    @Override
    public void endContact(Contact contact) {
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();

        if (fa.getUserData() != null && fa.getUserData().equals("foot")) {
//            playerOnGround = false;
        }

        if (fb.getUserData() != null && fb.getUserData().equals("foot")) {
            playerOnGround = false;
        }
    }

    public boolean isPlayerOnGround() {
        return playerOnGround;
    }

    // Collision detection - preSolve - Collision handling - postSolve
    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

    public Array<Body> getTrashBin() {
        return trashBin;
    }
}
