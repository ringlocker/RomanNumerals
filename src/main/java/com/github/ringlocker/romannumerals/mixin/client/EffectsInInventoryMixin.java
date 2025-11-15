package com.github.ringlocker.romannumerals.mixin.client;

import com.github.ringlocker.romannumerals.RomanNumeral;
import com.github.ringlocker.romannumerals.RomanNumerals;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screens.inventory.EffectsInInventory;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Redirect;

@Environment(EnvType.CLIENT)
@Mixin(EffectsInInventory.class)
public class EffectsInInventoryMixin {

    @ModifyConstant(method = "getEffectName", constant = @Constant(intValue = 9))
    private int modifyEffectAmplifierLimitForRomanNumerals(int original) {
        return 256;
    }

    @Redirect(method = "getEffectName", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/chat/Component;translatable(Ljava/lang/String;)Lnet/minecraft/network/chat/MutableComponent;"))
    private MutableComponent modifyComponent(String key) {
        if (key.startsWith("enchantment.level.")) {
            int level = Integer.parseInt(key.substring("enchantment.level.".length()));
            String roman = RomanNumeral.toRoman(level);
            return Component.literal(roman);
        }
        return Component.translatable(key);
    }

}
