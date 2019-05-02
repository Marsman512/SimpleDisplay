package me.marsman512.simpleDisplay.input.gamepad;

public interface GamepadEventHandler {
	
	// Copied, modified, and pasted from LWJGL's GLFW.java
	int
    GAMEPAD_BUTTON_A            = 0,
    GAMEPAD_BUTTON_B            = 1,
    GAMEPAD_BUTTON_X            = 2,
    GAMEPAD_BUTTON_Y            = 3,
    GAMEPAD_BUTTON_LEFT_BUMPER  = 4,
    GAMEPAD_BUTTON_RIGHT_BUMPER = 5,
    GAMEPAD_BUTTON_BACK         = 6,
    GAMEPAD_BUTTON_START        = 7,
    GAMEPAD_BUTTON_GUIDE        = 8,
    GAMEPAD_BUTTON_LEFT_THUMB   = 9,
    GAMEPAD_BUTTON_RIGHT_THUMB  = 10,
    GAMEPAD_BUTTON_DPAD_UP      = 11,
    GAMEPAD_BUTTON_DPAD_RIGHT   = 12,
    GAMEPAD_BUTTON_DPAD_DOWN    = 13,
    GAMEPAD_BUTTON_DPAD_LEFT    = 14,
    GAMEPAD_BUTTON_LAST         = GAMEPAD_BUTTON_DPAD_LEFT,
    GAMEPAD_BUTTON_CROSS        = GAMEPAD_BUTTON_A,
    GAMEPAD_BUTTON_CIRCLE       = GAMEPAD_BUTTON_B,
    GAMEPAD_BUTTON_SQUARE       = GAMEPAD_BUTTON_X,
    GAMEPAD_BUTTON_TRIANGLE     = GAMEPAD_BUTTON_Y;
	
	void buttonPressed(int player, int button);
	void buttonReleased(int player, int button);
	void axisMoved(int player, int axis, float currentPos);
	
	void gamepadConnected(int player);
	void gamepadDisconnected(int player);
}
