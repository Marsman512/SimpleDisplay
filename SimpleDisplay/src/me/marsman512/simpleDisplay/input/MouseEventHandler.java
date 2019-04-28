package me.marsman512.simpleDisplay.input;

public interface MouseEventHandler {
	// Copied, modified, and pasted from LWJGL's GLFW.java
	int
    MOUSE_BUTTON_1      = 0,
    MOUSE_BUTTON_2      = 1,
    MOUSE_BUTTON_3      = 2,
    MOUSE_BUTTON_4      = 3,
    MOUSE_BUTTON_5      = 4,
    MOUSE_BUTTON_6      = 5,
    MOUSE_BUTTON_7      = 6,
    MOUSE_BUTTON_8      = 7,
    MOUSE_BUTTON_LAST   = MOUSE_BUTTON_8,
    MOUSE_BUTTON_LEFT   = MOUSE_BUTTON_1,
    MOUSE_BUTTON_RIGHT  = MOUSE_BUTTON_2,
    MOUSE_BUTTON_MIDDLE = MOUSE_BUTTON_3;
	
	void mousePressed(int button, int mods);
	void mouseReleased(int button);
	void mouseMoved(double x, double y);
	void mouseScrolled(double amt); // Only tracks Y-scroll because who on earth has a mouse that can scroll on X?
}
