package net.fabricmc.example.mixin;

import net.fabricmc.example.KeyMapping;
import net.fabricmc.example.interfaces.McAccessor;
import net.minecraft.src.GuiContainer;
import net.minecraft.src.Minecraft;
import net.minecraft.src.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GuiContainer.class)
public abstract class GuiContainerMixin {
    @Shadow private Slot theSlot;
    @Shadow protected abstract void handleMouseClick(Slot par1Slot, int par2, int par3, int par4);
    @Inject(method = "checkHotbarKeys",at = @At("HEAD"),cancellable = true)
    void KeyMapping$checkNumberKey(int par1, CallbackInfoReturnable<Boolean> cir){
        Minecraft mc = ((McAccessor)this).KeyMapping$getMc();
        if (mc.thePlayer.inventory.getItemStack() == null && this.theSlot != null) {
            for(int var2 = 0; var2 < 9; ++var2) {
                if (par1 == KeyMapping.getNum(var2 + 1).keyCode) {
                    this.handleMouseClick(this.theSlot, this.theSlot.slotNumber, var2, 2);
                    cir.setReturnValue(true);
                }
            }
        }
        cir.setReturnValue(false);
    }
}
