package dev.chewey.shooter.entities.creatures;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import dev.chewey.shooter.Handler;
import dev.chewey.shooter.gfx.Animation;
import dev.chewey.shooter.gfx.Assets;
import dev.chewey.shooter.tiles.Tile;

public class Zombie extends Creature {

	private Animation animUp, animDown, animLeft, animRight;
	private Random rand;

	private long lastMovementTimer, movementCooldown = 500, movementTimer = movementCooldown;

	public Zombie(Handler handler, float x, float y) {
		super(handler, x, y, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT);

		rand = new Random();

		bounds.x = 22;
		bounds.y = 44;
		bounds.width = 19;
		bounds.height = 19;
		
		health = 2;

		// Animations
		animUp = new Animation(500, Assets.zombie_up);
		animDown = new Animation(500, Assets.zombie_down);
		animLeft = new Animation(500, Assets.zombie_left);
		animRight = new Animation(500, Assets.zombie_right);
	}

	@Override
	public void tick() {
		// Animations
		animUp.tick();
		animDown.tick();
		animLeft.tick();
		animRight.tick();

		randomMove();
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(getCurrentAnimationFrame(), (int) (x - handler.getGameCamera().getxOffset()),
				(int) (y - handler.getGameCamera().getyOffset()), width, height, null);
	}

	public void randomMove() {
		movementTimer += System.currentTimeMillis() - lastMovementTimer;
		lastMovementTimer = System.currentTimeMillis();
		if (movementTimer < movementCooldown)
			return;

		if (Math.abs(this.x - handler.getWorld().getEntityManager().getPlayer().getX()) < 4 * Tile.TILEWIDTH
				&& Math.abs(this.y - handler.getWorld().getEntityManager().getPlayer().getY()) < 4 * Tile.TILEWIDTH) {
			moveTowardsPlayer();
		} else {
			xMove = (rand.nextInt(3) - 1);
			yMove = (rand.nextInt(3) - 1);

			if (xMove > 0)
				xMove += speed * 2;
			if (xMove < 0)
				xMove -= speed * 2;
			if (yMove > 0)
				yMove += speed * 2;
			if (yMove < 0)
				yMove -= speed * 2;

		}
		movementTimer = 0;
		move();
	}

	private void moveTowardsPlayer() {
		float playerX = handler.getWorld().getEntityManager().getPlayer().getX();
		float playerY = handler.getWorld().getEntityManager().getPlayer().getY();

		if (playerY > this.y && Math.abs((this.y - playerY)) > 5) {
			xMove = 0;
			yMove = 1 + speed * 2;
		} else if (playerY < this.y && Math.abs((this.y - playerY)) > 5) {
			xMove = 0;
			yMove = -1 - (speed * 2);
		} else if (playerX > this.x) {
			yMove = 0;
			xMove = 1 + speed * 2;
		} else if (playerX < this.x) {
			yMove = 0;
			xMove = -1 - (speed * 2);
		}
	}

	@Override
	public void die() {

	}

	private BufferedImage getCurrentAnimationFrame() {
		if (xMove < 0) {
			return animLeft.getCurrentFrame();
		} else if (xMove > 0) {
			return animRight.getCurrentFrame();
		} else if (yMove < 0) {
			return animUp.getCurrentFrame();
		} else {
			return animDown.getCurrentFrame();
		}
	}

}
