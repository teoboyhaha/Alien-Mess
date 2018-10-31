package Input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener {

	private boolean[] keys = new boolean[256];
	public boolean up, down, left, right, 
		FIRE, JUMP, RELOAD, AMMO, HEALTH, REPAIR;

	public void update() {
		
		down = keys[KeyEvent.VK_DOWN];
		left = keys[KeyEvent.VK_LEFT];
		right = keys[KeyEvent.VK_RIGHT];
		
		FIRE = keys[KeyEvent.VK_X];
		JUMP = keys[KeyEvent.VK_Z];
		RELOAD = keys[KeyEvent.VK_C];
		
		AMMO = keys[KeyEvent.VK_A];
		HEALTH = keys[KeyEvent.VK_S];
		REPAIR = keys[KeyEvent.VK_D];
		
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		keys[e.getKeyCode()] = true;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}
}
