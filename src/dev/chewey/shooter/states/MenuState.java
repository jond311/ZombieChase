package dev.chewey.shooter.states;

import java.awt.Graphics;

import dev.chewey.shooter.Handler;
import dev.chewey.shooter.gfx.Assets;
import dev.chewey.shooter.ui.ClickListener;
import dev.chewey.shooter.ui.UIImageButton;
import dev.chewey.shooter.ui.UIManager;

public class MenuState extends State {

	private UIManager uiManager;
	private int buttonWidth = 128, buttonHeight = 64;

	public MenuState(Handler handler) {
		super(handler);
		uiManager = new UIManager(handler);
		handler.getMouseManager().setUIManager(uiManager);

		uiManager.addObject(new UIImageButton((handler.getWidth() / 2) - buttonWidth / 2,
				(int) (handler.getHeight() / 1.5), 128, 64, Assets.btn_start, new ClickListener() {

					@Override
					public void onClick() {
						handler.getMouseManager().setUIManager(null);
						State.setState(handler.getGame().gameState);
					}
				}));
	}

	@Override
	public void tick() {
		uiManager.tick();
		
		//temp dev code to get to game state right away
		//handler.getMouseManager().setUIManager(null);
		//State.setState(handler.getGame().gameState);
	}

	@Override
	public void render(Graphics g) {
		uiManager.render(g);
	}

}