package game.main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import game.item.SeedType;

public class KeyHandler implements KeyListener{
	
	public SeedType selectedSeed = SeedType.CARROT;
	public boolean upPressed, downPressed, leftPressed, rightPressed, spacePressed,waterPressed,harvestPressed,escPressed;
	
	@Override
	public void keyTyped(KeyEvent e) {
				
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
		
		if(code == KeyEvent.VK_1) {
			selectedSeed = SeedType.CARROT;
		}
		if(code == KeyEvent.VK_2) {
			selectedSeed = SeedType.RICE;
		}
		if(code == KeyEvent.VK_3) {
			selectedSeed = SeedType.SUNFLOWER;
		}
		if(code == KeyEvent.VK_4) {
			selectedSeed = SeedType.TOMATO;
		}
		
		if(code == KeyEvent.VK_W) {
			upPressed = true;
		}
		if(code == KeyEvent.VK_S) {
			downPressed = true;
		}
		if(code == KeyEvent.VK_A) {
			leftPressed = true;
		}
		if(code == KeyEvent.VK_D) {
			rightPressed = true;
		}
		if(code == KeyEvent.VK_SPACE) {
			spacePressed = true;
		}
        if (code == KeyEvent.VK_F) {
            waterPressed = true;
        }
        if (code == KeyEvent.VK_H){
            harvestPressed = true;
        }
        if (code == KeyEvent.VK_ESCAPE) {
            escPressed = true;
        }

    }

	@Override
	public void keyReleased(KeyEvent e) {
		int code = e.getKeyCode();
		
		if(code == KeyEvent.VK_W) {
			upPressed = false;
		}
		if(code == KeyEvent.VK_S) {
			downPressed = false;
		}
		if(code == KeyEvent.VK_A) {
			leftPressed = false;
		}
		if(code == KeyEvent.VK_D) {
			rightPressed = false;
		}
		if(code == KeyEvent.VK_SPACE) {
			spacePressed = false;
		}
        if (code == KeyEvent.VK_F){
            waterPressed = false;
        }
        if (code == KeyEvent.VK_H){
            harvestPressed = false;
        }
        if (code == KeyEvent.VK_ESCAPE) {
            escPressed = false;
        }

    }
}