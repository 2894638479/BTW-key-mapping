package net.fabricmc.example.mixin;

import net.fabricmc.example.KeyMapping;
import net.minecraft.src.*;
import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(Minecraft.class)
public class MinecraftMixin {
    @Shadow public EntityClientPlayerMP thePlayer;
    @Shadow public GuiScreen currentScreen;
    @Shadow public GameSettings gameSettings;
    @Unique int itemIndex;
    @Unique int thirdPersonView;
    @Inject(at = @At("HEAD"),method = "runTick")
    void KeyMapping$tickHead(CallbackInfo ci){
        if(thePlayer != null) itemIndex = thePlayer.inventory.currentItem;
        if(gameSettings != null) thirdPersonView = gameSettings.thirdPersonView;
    }
    @Inject(at = @At("RETURN"),method = "runTick")
    void KeyMapping$tickEnd(CallbackInfo ci){
        if(thePlayer != null) thePlayer.inventory.currentItem = itemIndex;
        if(gameSettings != null) gameSettings.thirdPersonView = thirdPersonView;
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
        if (currentScreen == null || this.currentScreen.allowUserInput) {
            if (result && Keyboard.getEventKeyState()) {
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
        return result;
    }
}
