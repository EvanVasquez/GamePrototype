package com.prototypeplatform.game.sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;

public class door extends interactiveObjects {

    public door(World world, TiledMap map, Rectangle bounds) {
        super(world, map, bounds);
    }
}
