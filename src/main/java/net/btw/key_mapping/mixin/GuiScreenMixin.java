package net.btw.key_mapping.mixin;

import net.btw.key_mapping.interfaces.McAccessor;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(GuiScreen.class)
public class GuiScreenMixin implements McAccessor {
    @Shadow protected Minecraft mc;
    public Minecraft KeyMapping$getMc(){return mc;};
}
