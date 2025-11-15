package com.github.ringlocker.romannumerals.mixin.client;

import com.github.ringlocker.romannumerals.RomanNumeral;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.alchemy.PotionContents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Environment(EnvType.CLIENT)
@Mixin(PotionContents.class)
public class PotionContentsMixin {

    @Redirect(method = "getPotionDescription", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/chat/Component;translatable(Ljava/lang/String;)Lnet/minecraft/network/chat/MutableComponent;"))
    private static MutableComponent modifyComponent(String key) {
        if (key.startsWith("potion.potency.")) {
            int level = Integer.parseInt(key.substring("potion.potency.".length()));
            String roman = RomanNumeral.toRoman(level);
            return Component.literal(roman);
        }
        return Component.translatable(key);
    }

}
