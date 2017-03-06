package com.mygdx.game.handlers;

import com.mygdx.game.main.Game;
import com.mygdx.game.states.GameState;
import com.mygdx.game.states.Play;

import java.util.Stack;

/**
 * Created by SatXa on 5/3/17.
 */

public class GameStateManager {

    public static final int PLAY = 001;
//    public static final int MENU = 002;
//    public static final int LEVEL_MENU = 003;
    private Game game;
    private Stack<GameState> gameStates;

    public GameStateManager(Game game) {
        this.game = game;
        this.gameStates = new Stack<GameState>();
        pushState(PLAY);
//        pushState(MENU);
    }

    public Game game() {
        return this.game;
    }

    public void update(float delta) {
        gameStates.peek().update(delta);
    }

    public void render() {
        gameStates.peek().render();
    }

    private GameState getState(int state) {
//        if (state == MENU) {
//            return new Menu(this);
//        }
        if (state == PLAY) {
            return new Play(this);
        }
        return null;
    }

    public void setState(int state) {
        popState();
        pushState(state);
    }

    public void pushState(int state) {
        gameStates.push(getState(state));
    }

    public void popState() {
        GameState g = gameStates.pop();
        g.dispose();
    }
}
