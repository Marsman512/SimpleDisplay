package me.marsman512.simpleDisplay.input;

public class ModBits {
	/** If this bit is set one or more Shift keys were held down. */
    public static final int MOD_SHIFT = 0x1;

    /** If this bit is set one or more Control keys were held down. */
    public static final int MOD_CONTROL = 0x2;

    /** If this bit is set one or more Alt keys were held down. */
    public static final int MOD_ALT = 0x4;

    /** If this bit is set one or more Super keys were held down. */
    public static final int MOD_SUPER = 0x8;

    /** If this bit is set the Caps Lock key is enabled and the {@link #GLFW_LOCK_KEY_MODS LOCK_KEY_MODS} input mode is set. */
    public static final int MOD_CAPS_LOCK = 0x10;

    /** If this bit is set the Num Lock key is enabled and the {@link #GLFW_LOCK_KEY_MODS LOCK_KEY_MODS} input mode is set. */
    public static final int MOD_NUM_LOCK = 0x20;
}
