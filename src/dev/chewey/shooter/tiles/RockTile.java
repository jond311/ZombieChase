package dev.chewey.shooter.tiles;

import dev.chewey.shooter.gfx.Assets;

public class RockTile extends Tile {

	public RockTile(int id) {
		super(Assets.stone, id);
	}

	@Override
	public boolean isSolid() {
		return true;
	}

}
