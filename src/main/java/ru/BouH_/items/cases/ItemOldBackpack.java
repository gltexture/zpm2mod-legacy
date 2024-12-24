package ru.BouH_.items.cases;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import ru.BouH_.Main;
import ru.BouH_.blocks.lootCases.EnumLootGroups;
import ru.BouH_.tiles.loot_spawn.LootSpawnManager;

import java.util.ArrayList;
import java.util.List;

public class ItemOldBackpack extends Item {
    public ItemOldBackpack(String unlocalizedName) {
        this.setUnlocalizedName(unlocalizedName);

    }

    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        player.setItemInUse(stack, this.getMaxItemUseDuration(stack));
        return stack;
    }

    public int getMaxItemUseDuration(ItemStack stack) {
        return 10;
    }

    public EnumAction getItemUseAction(ItemStack stack) {
        return EnumAction.block;
    }

    public ItemStack onItemUseFinish(ItemStack stack, World world, EntityPlayer player) {
        if (!world.isRemote) {
            player.getEntityData().setInteger("cdThrow", 20);
            player.worldObj.playSoundAtEntity(player, Main.MODID + ":cloth", 1.0F, 0.9f + Main.rand.nextFloat() * 0.1f);
            for (int i = 0; i < Main.rand.nextInt(2) + 1; i++) {
                float f1 = Main.rand.nextFloat();
                List<LootSpawnManager> list = new ArrayList<>(f1 <= 0.05f ? EnumLootGroups.Tier1Police.getLSP() : f1 <= 0.4f ? EnumLootGroups.Tier2Village.getLSP() : f1 <= 0.85f ? EnumLootGroups.Tier1City.getLSP() : EnumLootGroups.Tier1MegaCity.getLSP());
                ItemStack itemStack = list.get(Main.rand.nextInt(list.size())).getRandomItemStack();
                if (!player.inventory.addItemStackToInventory(itemStack)) {
                    player.entityDropItem(itemStack, 1.0f);
                }
            }
        }
        return null;
    }
}