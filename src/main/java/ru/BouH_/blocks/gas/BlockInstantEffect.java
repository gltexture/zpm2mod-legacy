package ru.BouH_.blocks.gas;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import ru.BouH_.init.ItemsZp;
import ru.BouH_.utils.EntityUtils;

public class BlockInstantEffect extends BlockGasBase {
    private final PotionEffect[] effect;
    private final boolean update;

    public BlockInstantEffect(boolean update, PotionEffect... effect) {
        super();
        this.update = update;
        this.effect = effect;
    }

    public void onEntityCollidedWithBlock(World worldIn, int p_149670_2_, int p_149670_3_, int p_149670_4_, Entity entityIn) {
        if (!worldIn.isRemote) {
            if (entityIn instanceof EntityLivingBase) {
                EntityLivingBase en = ((EntityLivingBase) entityIn);
                if (!(EntityUtils.isInArmor(en, ItemsZp.rad_helmet, ItemsZp.rad_chestplate, ItemsZp.rad_leggings, ItemsZp.rad_boots) || EntityUtils.isInArmor(en, ItemsZp.indcostume_helmet, ItemsZp.indcostume_chestplate, ItemsZp.indcostume_leggings, ItemsZp.indcostume_boots))) {
                    for (PotionEffect potionEffect : this.effect) {
                        if (this.update || !en.isPotionActive(potionEffect.getPotionID())) {
                            en.addPotionEffect(new PotionEffect(potionEffect));
                        }
                    }
                }
            }
        }
    }
}
