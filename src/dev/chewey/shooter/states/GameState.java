package dev.chewey.shooter.states;

import java.awt.Graphics;

import dev.chewey.shooter.Handler;
import dev.chewey.shooter.gfx.ImageLoader;
import dev.chewey.shooter.worlds.World;

public class GameState extends State {

	private World world;

	public GameState(Handler handler) {
		super(handler);
		world = new World(handler, "res/worlds/world1.txt");
		handler.setWorld(world);
	}

	@Override
	public void tick() {
		world.tick();
	}

	@Override
	public void render(Graphics g) {
		world.render(g);
	}

}