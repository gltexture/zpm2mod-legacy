package ru.BouH_.items.misc;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import ru.BouH_.Main;
import ru.BouH_.skills.SkillManager;
import ru.BouH_.skills.SkillZp;

public class ItemBookSkill extends Item {
    private final SkillZp skillZp;

    public ItemBookSkill(String unlocalizedName, SkillZp skillZp) {
        this.setUnlocalizedName(unlocalizedName);
        this.skillZp = skillZp;
    }

    public int getMaxItemUseDuration(ItemStack stack) {
        return 120;
    }

    public EnumAction getItemUseAction(ItemStack stack) {
        return EnumAction.block;
    }

    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        if (!player.capabilities.isCreativeMode) {
            player.setItemInUse(stack, this.getMaxItemUseDuration(stack));
        }
        return stack;
    }

    public ItemStack onItemUseFinish(ItemStack stack, World world, EntityPlayer player) {
        if (!world.isRemote) {
            world.playSoundAtEntity(player, Main.MODID + ":book", 1.0f, 1.0F);
            --stack.stackSize;
            if (!player.capabilities.isCreativeMode) {
                if (SkillManager.instance.isMaxSkill(this.skillZp, player)) {
                    player.addExperience(125);
                } else {
                    SkillManager.instance.addSkillPoints(this.skillZp, player, 1);
                }
            }
        }
        return stack;
    }
}
