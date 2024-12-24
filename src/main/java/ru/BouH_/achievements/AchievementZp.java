package ru.BouH_.achievements;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;

public class AchievementZp {
    public static int globalId;
    private final int id;
    private final TierEnum tierEnum;
    private final String unlocalizedName;
    private final String unlocalizedDescription;
    @SideOnly(Side.CLIENT)
    private Item logo;

    public AchievementZp(TierEnum tierEnum, String unlocalizedName, String unlocalizedDescription) {
        this.id = AchievementZp.globalId++;
        this.tierEnum = tierEnum;
        this.unlocalizedName = unlocalizedName;
        this.unlocalizedDescription = unlocalizedDescription;
    }

    public String getNBT() {
        return "ach_zp_" + this.getId();
    }

    @SideOnly(Side.CLIENT)
    public Item getLogo() {
        return this.logo;
    }

    @SideOnly(Side.CLIENT)
    public void setLogo(Item logo) {
        this.logo = logo;
    }

    public void trigger(EntityPlayer entityPlayer) {
        entityPlayer.getEntityData().setByte(this.getNBT(), (byte) 1);
    }

    public TierEnum getLvlEnum() {
        return this.tierEnum;
    }

    public String getUnlocalizedName() {
        return this.unlocalizedName;
    }

    public String getUnlocalizedDescription() {
        return this.unlocalizedDescription;
    }

    @SideOnly(Side.CLIENT)
    public String getName() {
        return I18n.format(this.unlocalizedName);
    }

    @SideOnly(Side.CLIENT)
    public String getDescription() {
        return I18n.format(this.unlocalizedDescription);
    }

    public int getId() {
        return this.id;
    }
}
