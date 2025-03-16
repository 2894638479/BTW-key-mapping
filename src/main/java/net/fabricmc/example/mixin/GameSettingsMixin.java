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
        keyBindings = Arrays.copyOf(keyBindings, keyBindings.length + KeyMapping.allKeys.length);
        for (int i = 0;i < KeyMapping.allKeys.length;i++){
            keyBindings[l+i] = KeyMapping.allKeys[i];
        }
    }
    @Inject(method = "<init>()V", at = @At(value = "TAIL"))
    private void KeyMapping$initTail(CallbackInfo ci) {
        KeyMappings$addKeyBinds();
    }
    @Inject(method = "<init>(Lnet/minecraft/src/Minecraft;Ljava/io/File;)V", at = @At(value = "TAIL"))
    private void KeyMapping$initTailLoadOpts(Minecraft par1Minecraft, File par2File, CallbackInfo ci) {
        KeyMappings$addKeyBinds();
        loadOptions();
    }


    @Inject(method = "getKeyBinding", at = @At("HEAD"))
    public void KeyMapping$getKeyBinding(EnumOptions par1EnumOptions, CallbackInfoReturnable<String> cir) {
    }
}

