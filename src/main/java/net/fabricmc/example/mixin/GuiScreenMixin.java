package net.fabricmc.example.mixin;

import net.fabricmc.example.interfaces.McAccessor;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(GuiScreen.class)
public class GuiScreenMixin implements McAccessor {
    @Shadow protected Minecraft mc;
    public Minecraft KeyMapping$getMc(){return mc;};
}
