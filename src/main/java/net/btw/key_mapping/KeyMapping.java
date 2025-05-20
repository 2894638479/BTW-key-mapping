package net.btw.key_mapping;

import net.minecraft.src.KeyBinding;
import org.lwjgl.input.Keyboard;

public class KeyMapping {
    public static KeyBinding NUM1 = new KeyBinding("1",Keyboard.KEY_1);
    public static KeyBinding NUM2 = new KeyBinding("2",Keyboard.KEY_2);
    public static KeyBinding NUM3 = new KeyBinding("3",Keyboard.KEY_3);
    public static KeyBinding NUM4 = new KeyBinding("4",Keyboard.KEY_4);
    public static KeyBinding NUM5 = new KeyBinding("5",Keyboard.KEY_5);
    public static KeyBinding NUM6 = new KeyBinding("6",Keyboard.KEY_6);
    public static KeyBinding NUM7 = new KeyBinding("7",Keyboard.KEY_7);
    public static KeyBinding NUM8 = new KeyBinding("8",Keyboard.KEY_8);
    public static KeyBinding NUM9 = new KeyBinding("9",Keyboard.KEY_9);
    public static KeyBinding F5 = new KeyBinding("F5", Keyboard.KEY_F5);
    public static KeyBinding getNum(int i){
        return switch (i){
            case 1 -> NUM1;
            case 2 -> NUM2;
            case 3 -> NUM3;
            case 4 -> NUM4;
            case 5 -> NUM5;
            case 6 -> NUM6;
            case 7 -> NUM7;
            case 8 -> NUM8;
            case 9 -> NUM9;
            default -> throw new IllegalStateException("Unexpected value: " + i);
        };
    }
    public static KeyBinding[] allKeys = {NUM1,NUM2,NUM3,NUM4,NUM5,NUM6,NUM7,NUM8,NUM9,F5};
}
