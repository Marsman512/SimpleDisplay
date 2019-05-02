package me.marsman512.simpleDisplay;

public interface WindowEventHandler {
	default void windowResized(int width, int height) {}
	default void windowClosed() {}
	
	default void windowMinimized() {}
	default void windowRestored() {}
}
