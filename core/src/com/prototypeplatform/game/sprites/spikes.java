package com.prototypeplatform.game.sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;

// This class is to represent the spike sprites in the game. if the player comes in contact with a spike he dies.

public class spikes extends interactiveObjects {


    public spikes(World world, TiledMap map, Rectangle bounds) {
        super(world, map, bounds);
    }
}
