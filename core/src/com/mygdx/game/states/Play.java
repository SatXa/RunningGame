package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.handlers.B2DVars;
import com.mygdx.game.handlers.GameStateManager;
import com.mygdx.game.handlers.MyContactListener;
import com.mygdx.game.handlers.MyInput;
import com.mygdx.game.main.Game;

import static com.mygdx.game.handlers.B2DVars.PPM;

/**
 * Created by SatXa on 5/3/17.
 */

public class Play extends GameState {

    private World world;
    private Box2DDebugRenderer b2dr;

    private OrthographicCamera b2dCam;

    private Body playerBody;
    private MyContactListener cl;

    private TiledMap tileMap;
    private float tileSize;
    private OrthogonalTiledMapRenderer tmr;

    public Play(GameStateManager gsm) {
        super(gsm);

        world = new World(new Vector2(0, -9.81f), true);
        cl = new MyContactListener();
        world.setContactListener(cl);
        b2dr = new Box2DDebugRenderer();

        // Platform

        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();

        // Player
        bdef.position.set(160 / PPM, 200 / PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        playerBody = world.createBody(bdef);

        shape.setAsBox(5 / PPM, 5 / PPM);
        fdef.filter.categoryBits = B2DVars.BIT_PLAYER;
        fdef.filter.maskBits = B2DVars.BIT_RED | B2DVars.BIT_BLUE | B2DVars.BIT_GREEN;
        fdef.shape = shape;
        fdef.restitution = 0.5f;
        playerBody.createFixture(fdef).setUserData(" Player ");

        // Foot sensor (?)
        shape.setAsBox(2 / PPM, 2 / PPM, new Vector2(0, -5 / PPM), 0);
        fdef.shape = shape;
        fdef.filter.categoryBits = B2DVars.BIT_PLAYER;
        fdef.filter.maskBits = B2DVars.BIT_RED | B2DVars.BIT_BLUE | B2DVars.BIT_GREEN;
        fdef.isSensor = true;
        playerBody.createFixture(fdef).setUserData(" foot ");

        b2dCam = new OrthographicCamera();
        b2dCam.setToOrtho(false, Game.WIDTH / PPM, Game.HEIGHT / PPM);

        // MAP
//        tileMap = new TmxMapLoader().load("maps/test_map.tmx");
        tileMap = new TmxMapLoader().load("maps/level1.tmx");
        tmr = new OrthogonalTiledMapRenderer(tileMap);

        TiledMapTileLayer layerRed = (TiledMapTileLayer) tileMap.getLayers().get("red");
//        TiledMapTileLayer layerGreen = (TiledMapTileLayer) tileMap.getLayers().get("Green");
//        TiledMapTileLayer layerBlue = (TiledMapTileLayer) tileMap.getLayers().get("Blue");

        tileSize = layerRed.getTileWidth();

        for (int row = 0; row < layerRed.getHeight(); row++) {
            for (int col = 0; col < layerRed.getWidth(); col++) {
                TiledMapTileLayer.Cell cell = layerRed.getCell(col, row);

                if (cell == null) {
                    continue;
                }
                if (cell.getTile() == null) {
                    continue;
                }

                bdef.type = BodyDef.BodyType.StaticBody;
                bdef.position.set((col + 0.5f) * tileSize / PPM, (row + 0.5f) * tileSize / PPM);

                ChainShape cs = new ChainShape();
                Vector2[] v = new Vector2[3];
                v[0] = new Vector2(-tileSize / 2 / PPM, -tileSize / 2 / PPM);
                v[1] = new Vector2(-tileSize / 2 / PPM, tileSize / 2 / PPM);
                v[2] = new Vector2(tileSize / 2 / PPM, tileSize / 2 / PPM);

                cs.createChain(v);
                fdef.friction = 0;
                fdef.shape = cs;
                fdef.filter.categoryBits = B2DVars.BIT_RED;
                fdef.filter.maskBits = -1;
                fdef.isSensor = false;

                world.createBody(bdef).createFixture(fdef);
            }
        }
    }

    @Override
    public void dispose() {

    }

    @Override
    public void handleInput() {
        // Jump
        if (MyInput.isPressed(MyInput.BUTTON1)) {
            if (cl.isPlayerOnGround()) {
                playerBody.applyForceToCenter(0, 100, true);
            }
        }

        if (MyInput.isPressed(MyInput.BUTTON2)) {

        }

        if (MyInput.isDown(MyInput.BUTTON1)) {
            if (cl.isPlayerOnGround()) {
                playerBody.applyForceToCenter(0, 100, true);
            }
        }

        if (MyInput.isDown(MyInput.BUTTON2)) {

        }
    }

    @Override
    public void update(float delta) {
        handleInput();

        world.step(delta, 6, 2);
    }

    @Override
    public void render() {

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        tmr.setView(cam);
        tmr.render();

        b2dr.render(world, b2dCam.combined);
    }
}
