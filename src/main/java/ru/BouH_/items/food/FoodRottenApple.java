package ru.BouH_.items.food;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import ru.BouH_.Main;
import ru.BouH_.entity.ieep.Hunger;

public class FoodRottenApple extends FoodZp {

    public FoodRottenApple(String unlocalizedName, int amount, float saturation, boolean isWolfFood, boolean createPoisonRecipe) {
        super(amount, saturation, isWolfFood, FoodType.FOOD, createPoisonRecipe);
        this.setUnlocalizedName(unlocalizedName);

    }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World world, EntityPlayer player) {
        --stack.stackSize;
        world.playSoundAtEntity(player, "random.burp", 0.5F, Main.rand.nextFloat() * 0.1F + 0.9F);
        if (!world.isRemote) {
            Hunger.getHunger(player).addHunger(this.getHealAmount(stack), this.getSaturationModifier(stack));
            player.getEntityData().setInteger("nausea", player.getEntityData().getInteger("nausea") + 15);
            switch (Main.rand.nextInt(5)) {
                case 0: {
                    player.addPotionEffect(new PotionEffect(19, 200));
                    break;
                }
                case 1: {
                    player.addPotionEffect(new PotionEffect(17, 300));
                    break;
                }
                case 2: {
                    player.addPotionEffect(new PotionEffect(9, 300));
                    break;
                }
                case 3: {
                    player.addPotionEffect(new PotionEffect(31, 300));
                    break;
                }
            }
        }
        return stack;
    }
}

