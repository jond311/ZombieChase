package dev.chewey.shooter.entities.creatures;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import dev.chewey.shooter.Handler;
import dev.chewey.shooter.entities.Entity;
import dev.chewey.shooter.gfx.Animation;
import dev.chewey.shooter.gfx.Assets;
import dev.chewey.shooter.states.State;

public class Player extends Creature {

	private Animation animUp, animDown, animLeft, animRight;

	private long lastAttackTimer, attackCooldown = 200, attackTimer = attackCooldown;

	public Player(Handler handler, float x, float y) {
		super(handler, x, y, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT);

		bounds.x = 22;
		bounds.y = 44;
		bounds.width = 19;
		bounds.height = 19;

		// Animations
		animUp = new Animation(500, Assets.player_up);
		animDown = new Animation(500, Assets.player_down);
		animLeft = new Animation(500, Assets.player_left);
		animRight = new Animation(500, Assets.player_right);

	}

	@Override
	public void tick() {
		// Animations
		animUp.tick();
		animDown.tick();
		animLeft.tick();
		animRight.tick();

		if (this.inDirt((int) x, (int) y)) {
			speed = 1.0f;
		}

		// Movement
		getInput();
		move();
		handler.getGameCamera().centerOnEntity(this);

		// Attack
		checkAttacks();
	}

	private void checkAttacks() {
		attackTimer += System.currentTimeMillis() - lastAttackTimer;
		lastAttackTimer = System.currentTimeMillis();
		if (attackTimer < attackCooldown)
			return;

		Rectangle cb = getCollisionBounds(0, 0);
		Rectangle ar = new Rectangle();
		int arSize = 20;
		ar.width = arSize;
		ar.height = arSize;

		if (handler.getKeyManager().up && handler.getKeyManager().shoot) {
			ar.x = cb.x + cb.width / 2 - arSize / 2;
			ar.y = cb.y - arSize;
		} else if (handler.getKeyManager().down && handler.getKeyManager().shoot) {
			ar.x = cb.x + cb.width / 2 - arSize / 2;
			ar.y = cb.y + cb.height;
		} else if (handler.getKeyManager().left && handler.getKeyManager().shoot) {
			ar.x = cb.x - arSize;
			ar.y = cb.y + cb.height / 2 - arSize / 2;
		} else if (handler.getKeyManager().right && handler.getKeyManager().shoot) {
			ar.x = cb.x + cb.width;
			ar.y = cb.y + cb.height / 2 - arSize / 2;
		} else {
			return;
		}

		attackTimer = 0;

		for (Entity e : handler.getWorld().getEntityManager().getEntities()) {
			if (e.equals(this))
				continue;
			if (e.getCollisionBounds(0, 0).intersects(ar)) {
				e.hurt(1);
				return;
			}
		}

	}

	@Override
	public void die() {
		State.setState(handler.getGame().menuState);
	}

	private void getInput() {
		xMove = 0;
		yMove = 0;

		if (handler.getKeyManager().up)
			yMove = -speed;
		if (handler.getKeyManager().down)
			yMove = speed;
		if (handler.getKeyManager().left)
			xMove = -speed;
		if (handler.getKeyManager().right)
			xMove = speed;
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(getCurrentAnimationFrame(), (int) (x - handler.getGameCamera().getxOffset()),
				(int) (y - handler.getGameCamera().getyOffset()), width, height, null);
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