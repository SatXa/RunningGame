package com.mygdx.game.handlers;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.main.Game;

/**
 * Created by SatXa on 6/3/17.
 */

public class Background {

    private TextureRegion image;
    private OrthographicCamera cam;
    private float scale;

    private float x;
    private float y;
    private int numDrawY;
    private int numDrawX;

    private float dx;
    private float dy;

    public Background(TextureRegion image, OrthographicCamera cam, float scale) {
        this.image = image;
        this.cam = cam;
        this.scale = scale;

        numDrawX = Game.WIDTH / image.getRegionWidth() + 1;
        numDrawY = Game.HEIGHT / image.getRegionHeight() + 1;
    }

    public void setVector(float dx, float dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public void update(float dt) {
        x += (dx * scale) * dt;
        y += (dy * scale) * dt;
    }

    public void render(SpriteBatch sb) {

        float x = ((this.x + cam.viewportWidth / 2 - cam.position.x) * scale) % image.getRegionWidth();
        float y = ((this.y + cam.viewportHeight / 2 - cam.position.y) * scale) % image.getRegionHeight();

        sb.begin();

        int colOffset = x > 0 ? -1 : 0;
        int rowOffset = y > 0 ? -1 : 0;
        for(int row = 0; row < numDrawY; row++) {
            for(int col = 0; col < numDrawX; col++) {
                sb.draw(image, x + (col + colOffset) * image.getRegionWidth(), y + (rowOffset + row) * image.getRegionHeight());
            }
        }

        sb.end();

    }
}
