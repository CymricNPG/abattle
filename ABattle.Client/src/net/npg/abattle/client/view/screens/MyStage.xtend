package net.npg.abattle.client.view.screens

import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.Input

class MyStage extends Stage {
	new() {
		super()
	}

	override boolean keyDown(int keyCode) {
		if(isBackKey(keyCode)) {
			if(getHardKeyListener() != null) {
				getHardKeyListener().onHardKey(keyCode, 1);
			}
		}
		return super.keyDown(keyCode);
	}

	def isBackKey(int keyCode) {
		keyCode == Input.Keys.BACK || keyCode == Input.Keys.MENU || keyCode == Input.Keys.ESCAPE
	}

	override boolean keyUp(int keyCode) {
		if(isBackKey(keyCode)) {
			if(getHardKeyListener() != null) {
				getHardKeyListener().onHardKey(keyCode, 0);
			}
		}
		return super.keyUp(keyCode);
	}

	/*********************Hard key listener***********************/
	public interface OnHardKeyListener {

		/**
         * Happens when user press hard key
         * @param keyCode Back or Menu key (keyCode one of the constants in Input.Keys)
         * @param state 1 - key down, 0 - key up
         */
		def void onHardKey(int keyCode, int state)
	}

	private OnHardKeyListener _HardKeyListener = null;

	def void setHardKeyListener(OnHardKeyListener HardKeyListener) {
		_HardKeyListener = HardKeyListener;
	}

	def OnHardKeyListener getHardKeyListener() {
		return _HardKeyListener;
	}
}
