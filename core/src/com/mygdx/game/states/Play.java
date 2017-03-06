package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.entities.HUD;
import com.mygdx.game.entities.Pin;
import com.mygdx.game.entities.Player;
import com.mygdx.game.handlers.B2DVars;
import com.mygdx.game.handlers.GameStateManager;
import com.mygdx.game.handlers.MyContactListener;
import com.mygdx.game.handlers.MyInput;
import com.mygdx.game.main.Game;

import static com.mygdx.game.handlers.B2DVars.BIT_BLUE;
import static com.mygdx.game.handlers.B2DVars.BIT_GREEN;
import static com.mygdx.game.handlers.B2DVars.BIT_PIN;
import static com.mygdx.game.handlers.B2DVars.BIT_PLAYER;
import static com.mygdx.game.handlers.B2DVars.BIT_RED;
import static com.mygdx.game.handlers.B2DVars.PPM;

/**
 * Created by SatXa on 5/3/17.
 */

public class Play extends GameState {

    // SET TO TRUE ONLY WHEN DEBUGGING TO SEE Box2D HITBOXES
    private boolean debug = false;

    private World world;
    private Box2DDebugRenderer b2dr;

    private OrthographicCamera b2dCam;
    private MyContactListener cl;

    private TiledMap tileMap;
    private float tileSize;
    private float tileMapWidth;
    private float tileMapHeight;
    private OrthogonalTiledMapRenderer tmr;

    private int level;

    private Player player;
    private Array<Pin> pins;

    private HUD hud;

    public Play(GameStateManager gsm) {
        super(gsm);

        level = Game.getLevel();

        // Box2D world creation
        world = new World(new Vector2(0, -9.81f), true);
        cl = new MyContactListener();
        world.setContactListener(cl);
        b2dr = new Box2DDebugRenderer();

        // Entities
        createPlayer();
        createTiles();
        createPins();

        // Box2D cam setting
        b2dCam = new OrthographicCamera();
        b2dCam.setToOrtho(false, Game.WIDTH / PPM, Game.HEIGHT / PPM);

        // HUD
        hud = new HUD(player);
    }

    private void createPins() {
        pins = new Array<Pin>();

        MapLayer layer = tileMap.getLayers().get("pins");

        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();

        for (MapObject mo : layer.getObjects()) {
            bdef.type = BodyDef.BodyType.StaticBody;

            float x = mo.getProperties().get("x", Float.class) / PPM;
            float y = mo.getProperties().get("y", Float.class) / PPM;

            bdef.position.set(x, y);

            CircleShape cshape = new CircleShape();
            cshape.setRadius(8 / PPM);

            fdef.shape = cshape;
            fdef.isSensor = true;
            fdef.filter.categoryBits = BIT_PIN;
            fdef.filter.maskBits = BIT_PLAYER;

            Body body = world.createBody(bdef);
            body.createFixture(fdef).setUserData("pin");

            Pin pin = new Pin(body);
            pins.add(pin);
            body.setUserData(pin);

            cshape.dispose();
        }
    }

    private void createTiles() {
        // MAP
        tileMap = new TmxMapLoader().load("maps/level1.tmx");

        /*switch(level) {
            case 1:
                tileMap = new TmxMapLoader().load("maps/level1.tmx");
                System.out.println("PASO 1");
                break;
            case 2:
                tileMap = new TmxMapLoader().load("maps/level2.tmx");
                System.out.println("PASO 2");
                break;
            case 3:
                tileMap = new TmxMapLoader().load("maps/level3.tmx");
                System.out.println("PASO 3");
                break;
            default:
                tileMap = new TmxMapLoader().load("maps/level3.tmx");
                System.out.println("PASO 4");
                break;
        }*/
        
        tmr = new OrthogonalTiledMapRenderer(tileMap);
        tileSize = (Integer) tileMap.getProperties().get("tilewidth");
        tileMapWidth = (Integer) tileMap.getProperties().get("width");
        tileMapHeight = (Integer) tileMap.getProperties().get("height");

        TiledMapTileLayer layer;

        layer = (TiledMapTileLayer) tileMap.getLayers().get("red");
        createLayer(layer, BIT_RED);
        layer = (TiledMapTileLayer) tileMap.getLayers().get("green");
        createLayer(layer, BIT_GREEN);
        layer = (TiledMapTileLayer) tileMap.getLayers().get("blue");
        createLayer(layer, BIT_BLUE);
    }

    public void createLayer(TiledMapTileLayer layer, short bits) {
        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();

        for (int row = 0; row < layer.getHeight(); row++) {
            for (int col = 0; col < layer.getWidth(); col++) {
                TiledMapTileLayer.Cell cell = layer.getCell(col, row);

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
//                fdef.friction = 0.01f; // Only for Free Movement
                fdef.friction = 0.0f; // 0 Friction is needed for Auto Movement
                fdef.shape = cs;
                fdef.filter.categoryBits = bits;
                fdef.filter.maskBits = BIT_PLAYER;
                fdef.isSensor = false;

                world.createBody(bdef).createFixture(fdef);

                cs.dispose();
            }
        }
    }

    private void createPlayer() {
        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        // Player Body
        bdef.position.set(100 / PPM, 200 / PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.linearVelocity.set(1f, 0); // AUTO MOVEMENT
        Body body = world.createBody(bdef);

        shape.setAsBox(5 / PPM, 5 / PPM);
        fdef.filter.categoryBits = B2DVars.BIT_PLAYER;
        fdef.filter.maskBits = B2DVars.BIT_RED/* | B2DVars.BIT_BLUE | B2DVars.BIT_GREEN*/ | BIT_PIN;
        fdef.shape = shape;
//        fdef.restitution = 0.1f; // Bounciness (even small values make the game harder)
        body.createFixture(fdef).setUserData("player");

        // Foot sensor (?)
        shape.setAsBox(2 / PPM, 2 / PPM, new Vector2(0, -5 / PPM), 0);
        fdef.shape = shape;
        fdef.filter.categoryBits = B2DVars.BIT_PLAYER;
        fdef.filter.maskBits = B2DVars.BIT_RED /*| B2DVars.BIT_BLUE | B2DVars.BIT_GREEN*/;
        fdef.isSensor = true;
        body.createFixture(fdef).setUserData("foot");

        // Player
        player = new Player(body);
        body.setUserData(player);

        shape.dispose();
    }

    @Override
    public void dispose() {

    }

    @Override
    public void handleInput() {
        // PHONE
        if(MyInput.isPressed()) {
            if(MyInput.x < Gdx.graphics.getWidth() / 2) {
                switchBlocks();
            }
            else {
                jump();
            }
        }

        // KEYBOARD
        // Jump
        if (MyInput.isPressed(MyInput.UP)) {
            jump();
        }

        // Block collision switch
        if (MyInput.isPressed(MyInput.SWITCH)) {
            switchBlocks();
        }

        // DOESN'T WORK
        // Respawn
//        if (MyInput.isPressed(MyInput.RESPAWN)) {
////            player.respawn();
//        }

        // SCRAPPED (inconsistent, difficult to control)
        // Another jump? Feels smoother...
//        if (MyInput.isDown(MyInput.UP)) {
//            if (cl.isPlayerOnGround()) {
//                player.getBody().applyForceToCenter(0, 100, true);
//            }
//        }

        // Left
        if (MyInput.isDown(MyInput.LEFT)) {
            if (player.getBody().getLinearVelocity().x > 0.1f) {
//                player.getBody().applyLinearImpulse(-1, 0, 0, 0, true);
                player.getBody().applyForceToCenter(-5, 0, true);
            }
        }

        //Right
        if (MyInput.isDown(MyInput.RIGHT)) {
//            player.getBody().applyLinearImpulse(50, 0, 0, 0, true);
            player.getBody().applyForceToCenter(5, 0, true);
        }
    }

    private void switchBlocks() {
        Filter filter = player.getBody().getFixtureList().first()
                .getFilterData();
        short bits = filter.maskBits;

        // Switch to next colour: R ==> G ==> B ==> R ...
        /*
        * NOTE: the tilde (~) operator "bitwise-ly" inverts all values
        * Here, it unsets the current bitMask before changing it to the next one
        * &= and |= do this in some way I didn't fully understand
        * Code from https://youtu.be/byMj7ziPfOQ?list=PL-2t7SM0vDfdYJ5Pq9vxeivblbZuFvGJK&t=747
        */

        if((bits & B2DVars.BIT_RED) != 0) {
            bits &= ~B2DVars.BIT_RED;
            bits |= B2DVars.BIT_GREEN;
//            System.out.println("GREEN!");
        }
        else if((bits & B2DVars.BIT_GREEN) != 0) {
            bits &= ~B2DVars.BIT_GREEN;
            bits |= B2DVars.BIT_BLUE;
//            System.out.println("BLUE!");
        }
        else if((bits & B2DVars.BIT_BLUE) != 0) {
            bits &= ~B2DVars.BIT_BLUE;
            bits |= B2DVars.BIT_RED;
//            System.out.println("RED!");
        }

        filter.maskBits = bits;
        player.getBody().getFixtureList().first()
                .setFilterData(filter);

        // Also for the "feet"
        filter = player.getBody().getFixtureList().get(1)
                .getFilterData();
        bits &= ~B2DVars.BIT_PIN;
        filter.maskBits = bits;
        player.getBody().getFixtureList().get(1).setFilterData(filter);
    }

    @Override
    public void update(float delta) {
        handleInput();

        world.step(delta, 6, 2);

        bodyRemoval();

        player.update(delta);

        // Win?
        if (player.getBody().getPosition().x * PPM > tileMapWidth * tileSize) {
            Game.res.getSound("getPin").play();
            gsm.setState(GameStateManager.PLAY);
            Game.setLevel(level + 1);
        }

        // Lose?
        // FALL
        if (player.getBody().getPosition().y < 0) {
            end();
        }

        // STOP
        if (player.getBody().getLinearVelocity().x < 0.001f) {
            end();
        }

        for (int i = 0; i < pins.size; i++) {
            pins.get(i).update(delta);
        }
    }

    private void end() {
        Game.res.getSound("dead").play();
        gsm.setState(GameStateManager.PLAY);
        Game.setLevel(1);
    }

    private void bodyRemoval() {
        // Trash removal
        Array<Body> bodies = cl.getTrashBin();
        for (int i = 0; i < bodies.size; i++) {
            Body b = bodies.get(i);
            pins.removeValue((Pin) b.getUserData(), true);
            world.destroyBody(b);
            player.addPoint();
            Game.res.getSound("getPin").play();
        }
        bodies.clear();
    }

    @Override
    public void render() {

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Stalker Camera incoming
        cam.position.set(player.getPosition().x * PPM + Game.WIDTH / 4,
                Game.HEIGHT / 2, 0);
        cam.update();

        // TileMap
        tmr.setView(cam);
        tmr.render();

        // Player
        sb.setProjectionMatrix(cam.combined);
        player.render(sb);

        // Pins
        for (int i = 0; i < pins.size; i++) {
            pins.get(i).render(sb);
        }

        // HUD
        sb.setProjectionMatrix(hudCam.combined);
        hud.render(sb);

        //Box2D (Player hitbox) & cam to follow it (DEBUG MODE) (CAMERA DOESN'T WORK (problems with measurements?))
        if (debug) {
            b2dr.render(world, b2dCam.combined);

//            b2dCam.position.set(player.getPosition().x * PPM + Game.WIDTH / 4,
//                    Game.HEIGHT / 2, 0);
//            b2dCam.update();
        }
    }

    public void jump() {
        if (cl.isPlayerOnGround()) {
            player.getBody().applyForceToCenter(0, 250, true);
            Game.res.getSound("jump").play();
        }
    }
}
