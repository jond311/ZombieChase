package dev.chewey.shooter.worlds;

import java.awt.Graphics;
import java.util.Random;
import java.util.Timer;

import dev.chewey.shooter.Handler;
import dev.chewey.shooter.entities.EntityManager;
import dev.chewey.shooter.entities.creatures.Player;
import dev.chewey.shooter.entities.creatures.Zombie;
import dev.chewey.shooter.entities.statics.Rock;
import dev.chewey.shooter.entities.statics.Tree;
import dev.chewey.shooter.tiles.Tile;
import dev.chewey.shooter.utils.Utils;

public class World {

	private Handler handler;
	private int width, height;
	private int spawnX, spawnY;
	private int[][] tiles;

	private Random rand;
	private long lastSpawnTimer, spawnCooldown = 5000, spawnTimer = spawnCooldown;

	// Entities
	private EntityManager entityManager;

	public World(Handler handler, String path) {
		this.handler = handler;
		entityManager = new EntityManager(handler, new Player(handler, 100, 100));
		rand = new Random();

		loadWorld(path);

		entityManager.addEntity(new Tree(handler, 300, 310));
		entityManager.addEntity(new Rock(handler, 100, 450));

		entityManager.getPlayer().setX(spawnX);
		entityManager.getPlayer().setY(spawnY);

	}

	public void spawnZombies() {
		spawnTimer += System.currentTimeMillis() - lastSpawnTimer;
		lastSpawnTimer = System.currentTimeMillis();
		if (spawnTimer < spawnCooldown)
			return;

		int spawnX = rand.nextInt(19) + 1;
		int spawnY = rand.nextInt(19) + 1;
		entityManager.addEntity(new Zombie(handler, spawnX * Tile.TILEWIDTH, spawnY * Tile.TILEHEIGHT));

		spawnTimer = 0;
	}

	public void tick() {
		entityManager.tick();
		spawnZombies();
	}

	public void render(Graphics g) {

		int xStart = (int) Math.max(0, handler.getGameCamera().getxOffset() / Tile.TILEWIDTH);
		int xEnd = (int) Math.min(width,
				(handler.getGameCamera().getxOffset() + handler.getWidth()) / Tile.TILEWIDTH + 1);
		int yStart = (int) Math.max(0, handler.getGameCamera().getyOffset() / Tile.TILEHEIGHT);
		int yEnd = (int) Math.min(height,
				(handler.getGameCamera().getyOffset() + handler.getHeight()) / Tile.TILEHEIGHT + 1);

		for (int y = yStart; y < yEnd; y++) {
			for (int x = xStart; x < xEnd; x++) {
				getTile(x, y).render(g, (int) (x * Tile.TILEWIDTH - handler.getGameCamera().getxOffset()),
						(int) (y * Tile.TILEHEIGHT - handler.getGameCamera().getyOffset()));
			}
		}
		// Entities
		entityManager.render(g);
	}

	public Tile getTile(int x, int y) {
		if (x < 0 || y < 0 || x >= width || y >= height)
			return Tile.grassTile;

		Tile t = Tile.tiles[tiles[x][y]];
		if (t == null)
			return Tile.dirtTile;
		return t;
	}

	private void loadWorld(String path) {
		String file = Utils.loadFileAsString(path);
		String[] tokens = file.split("\\s+");
		width = Utils.parseInt(tokens[0]);
		height = Utils.parseInt(tokens[1]);
		spawnX = (Utils.parseInt(tokens[2])) * Tile.TILEWIDTH;
		spawnY = (Utils.parseInt(tokens[3])) * Tile.TILEHEIGHT;

		tiles = new int[width][height];
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				tiles[x][y] = Utils.parseInt(tokens[(x + y * width) + 4]);
			}
		}
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

}