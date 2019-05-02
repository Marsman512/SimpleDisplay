package me.marsman512.simpleDisplay.input.gamepad;

import static org.lwjgl.glfw.GLFW.*;
import org.lwjgl.glfw.GLFWJoystickCallbackI;
import org.lwjgl.glfw.GLFWGamepadState;

public class Gamepad {
	
	// Handler stuff
	public static final GamepadEventHandler defEventHandler = new GamepadEventHandler() {

		@Override
		public void buttonPressed(int player, int button) {
			
		}

		@Override
		public void buttonReleased(int player, int button) {
			
		}
		
		@Override
		public void axisMoved(int player, int axis, float currentPos) {
			
		}

		@Override
		public void gamepadConnected(int player) {
			
		}

		@Override
		public void gamepadDisconnected(int player) {
			
		}
	};
	
	private static GamepadEventHandler currentHandler = defEventHandler;
	
	public static void setCurrentHandler(GamepadEventHandler value) {
		if(value != null)
			currentHandler = value;
		else
			throw new NullPointerException("You can't have a null GamepadEventHandler!");
	}
	
	public static GamepadEventHandler getCurrentHandler() {
		return currentHandler;
	}
	
	public static boolean isGamepadConnected(int id) {
		return connected[id];
	}
	
	// Internal stuff
	private static final boolean[] connected = new boolean[GLFW_JOYSTICK_LAST + 1];
	private static final GamepadState[] lastStates = new GamepadState[GLFW_JOYSTICK_LAST + 1];
	private static final GamepadState currentGPS = new GamepadState();
	private static final GLFWGamepadState glfwGPS = GLFWGamepadState.create();
	
	private static final GLFWJoystickCallbackI joyCB = (int jid, int event) -> {
		if(event == GLFW_CONNECTED && glfwJoystickIsGamepad(jid)) {
			connected[jid] = true;
			currentHandler.gamepadConnected(jid);
		} else if(event == GLFW_DISCONNECTED && connected[jid]) {
			connected[jid] = false;
			currentHandler.gamepadDisconnected(jid);
		}
	};
	
	/**
	 * Internal function. DO NOT USE!!!
	 */
	public static void start() {
		glfwSetJoystickCallback(joyCB);
		
		for(int i = GLFW_JOYSTICK_1; i <= GLFW_JOYSTICK_LAST; i++) {
			if(glfwJoystickIsGamepad(i))
				connected[i] = true;
			else
				connected[i] = false;
		}
	}
	/**
	 * Internal function. DO NOT USE!!!
	 */
	public static void update() {
		for(int jid = GLFW_JOYSTICK_1; jid <= GLFW_JOYSTICK_LAST; jid++) {
			if(!connected[jid])
				continue;
			
			glfwGetGamepadState(jid, glfwGPS);
			currentGPS.set(glfwGPS);
			
			if(lastStates[jid] == null) {
				lastStates[jid] = new GamepadState();
				lastStates[jid].set(currentGPS);
			}
			GamepadState lastGPS = lastStates[jid];
			
			// Check buttons
			for(int button = 0; button < GLFW_GAMEPAD_BUTTON_LAST + 1; button++) {
				if(currentGPS.getButton(button) && !lastGPS.getButton(button))
					currentHandler.buttonPressed(jid, button);
				else if(!currentGPS.getButton(button) && lastGPS.getButton(button)) {
					currentHandler.buttonReleased(jid, button);
				}
			}
			
			// Check axes
			for(int axis = 0; axis < GLFW_GAMEPAD_AXIS_LAST + 1; axis++) {
				if(currentGPS.getAxis(axis) != lastGPS.getAxis(axis))
					currentHandler.axisMoved(jid, axis, currentGPS.getAxis(axis));
			}
			
			lastStates[jid].set(currentGPS);
		}
	}
}
