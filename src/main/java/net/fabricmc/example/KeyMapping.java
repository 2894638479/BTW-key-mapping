package net.fabricmc.example;

import net.minecraft.src.KeyBinding;

public class KeyMapping {
    public static KeyBinding NUM1 = new KeyBinding("1",2);
    public static KeyBinding NUM2 = new KeyBinding("2",3);
    public static KeyBinding NUM3 = new KeyBinding("3",4);
    public static KeyBinding NUM4 = new KeyBinding("4",5);
    public static KeyBinding NUM5 = new KeyBinding("5",6);
    public static KeyBinding NUM6 = new KeyBinding("6",7);
    public static KeyBinding NUM7 = new KeyBinding("7",8);
    public static KeyBinding NUM8 = new KeyBinding("8",9);
    public static KeyBinding NUM9 = new KeyBinding("9",10);
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
}
