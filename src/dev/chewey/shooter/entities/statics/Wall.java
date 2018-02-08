package dev.chewey.shooter.entities.statics;

import java.awt.Graphics;

import dev.chewey.shooter.Handler;
import dev.chewey.shooter.gfx.Assets;
import dev.chewey.shooter.tiles.Tile;

public class Wall extends StaticEntity {

	public Wall(Handler handler, float x, float y, int width, int height) {
		super(handler, x, y, width, height);
		
		bounds.x = 0;
		bounds.y = 0;
		bounds.width = Tile.TILEWIDTH;
		bounds.height = Tile.TILEHEIGHT;
	}

	@Override
	public void tick() {
		
	}
	
	@Override
	public void die(){
		
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(Assets.rock, (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);

	}

}
