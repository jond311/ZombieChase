package dev.chewey.shooter.entities.statics;

import dev.chewey.shooter.Handler;
import dev.chewey.shooter.entities.Entity;

public abstract class StaticEntity extends Entity {

	public StaticEntity(Handler handler, float x, float y, int width, int height) {
		super(handler, x, y, width, height);
	}
}
