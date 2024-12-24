package ru.BouH_.skills;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.Item;

public class SkillZp {
    public static int globalId;
    private final int id;
    private final String unlocalizedName;
    @SideOnly(Side.CLIENT)
    private Item logo;

    public SkillZp(String unlocalizedName) {
        this.id = SkillZp.globalId++;
        this.unlocalizedName = unlocalizedName;
    }

    public String getNBT() {
        return "skill_zp_" + this.getId();
    }

    @SideOnly(Side.CLIENT)
    public Item getLogo() {
        return this.logo;
    }

    @SideOnly(Side.CLIENT)
    public void setLogo(Item logo) {
        this.logo = logo;
    }

    public String getUnlocalizedName() {
        return this.unlocalizedName;
    }

    public String getDescription() {
        return I18n.format(this.getUnlocalizedName() + ".desc");
    }

    @SideOnly(Side.CLIENT)
    public String getName() {
        return I18n.format(this.unlocalizedName);
    }

    public int getId() {
        return this.id;
    }
}
