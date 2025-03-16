package net.fabricmc.example.mixin;

import net.fabricmc.example.KeyMapping;
import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.io.File;
import java.util.Arrays;

@Mixin(GameSettings.class)
public abstract class GameSettingsMixin {

    @Shadow
    public KeyBinding[] keyBindings;

    @Shadow public abstract void loadOptions();

    @Unique
    private void KeyMappings$addKeyBinds(){
        int l = keyBindings.length;
        keyBindings = Arrays.copyOf(keyBindings, keyBindings.length + 9);
        for (int i = 1;i <= 9;i++){
            keyBindings[l+i-1] = KeyMapping.getNum(i);
        }
    }
    @Inject(method = "<init>()V", at = @At(value = "TAIL"))
    private void KeyMapping$initTail(CallbackInfo ci) {
        KeyMappings$addKeyBinds();
    }
    @Redirect(
        method = "<init>(Lnet/minecraft/src/Minecraft;Ljava/io/File;)V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/src/GameSettings;loadOptions()V"
        )
    )
    private void KeyMapping$initTailLoadOpts(GameSettings instance) {
        KeyMappings$addKeyBinds();
        loadOptions();
    }


    @Inject(method = "getKeyBinding", at = @At("HEAD"))
    public void KeyMapping$getKeyBinding(EnumOptions par1EnumOptions, CallbackInfoReturnable<String> cir) {
    }
}

