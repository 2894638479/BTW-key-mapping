package net.btw.key_mapping.mixin;

import net.btw.key_mapping.KeyMapping;
import net.minecraft.src.*;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.minecraft.src.Minecraft.getSystemTime;


@Mixin(Minecraft.class)
public class MinecraftMixin {
    @Shadow public EntityClientPlayerMP thePlayer;
    @Shadow public GuiScreen currentScreen;
    @Shadow public GameSettings gameSettings;
    @Shadow long systemTime;
    @Unique int itemIndex;
    @Unique int thirdPersonView;

    @Inject(at = @At("HEAD"),method = "runTick")
    void KeyMapping$tickHead(CallbackInfo ci){
        if(thePlayer != null) itemIndex = thePlayer.inventory.currentItem;
        if(gameSettings != null) thirdPersonView = gameSettings.thirdPersonView;
    }
    @Redirect(
            method = "runTick",
            at = @At(
                    value = "INVOKE",
                    target = "Lorg/lwjgl/input/Keyboard;next()Z"
            )
    )
    private boolean KeyMapping$replaceKeyboardNext() {
        boolean result = Keyboard.next();
        if(!result){
            if(thePlayer != null) thePlayer.inventory.currentItem = itemIndex;
            if(gameSettings != null) gameSettings.thirdPersonView = thirdPersonView;
            return false;
        }
        if (currentScreen == null || currentScreen.allowUserInput) {
            if (Keyboard.getEventKeyState()) {
                for (int i = 1; i <= 9; i++) {
                    if (Keyboard.getEventKey() == KeyMapping.getNum(i).keyCode) {
                        itemIndex = i - 1;
                    }
                }
                if(Keyboard.getEventKey() == KeyMapping.F5.keyCode){
                    thirdPersonView++;
                    if(thirdPersonView > 2) thirdPersonView = 0;
                }
            }
        }
        return true;
    }

    @Redirect(
            method = "runTick",
            at = @At(
                    value = "INVOKE",
                    target = "Lorg/lwjgl/input/Mouse;next()Z"
            )
    )
    private boolean KeyMapping$replaceMouseNext() {
        boolean result = Mouse.next();
        if (currentScreen == null || currentScreen.allowUserInput) {
            if(result){
                if (getSystemTime() - systemTime <= 200L) {
                    int scroll = Mouse.getEventDWheel();
                    if (scroll > 0) {
                        itemIndex--;
                        if(itemIndex < 0) itemIndex += 9;
                    }
                    if(scroll < 0) {
                        itemIndex++;
                        if(itemIndex >= 9) itemIndex -= 9;
                    }
                }
            }
        }
        return result;
    }
}
