package ru.BouH_.misc;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;

public class DamageSourceZp extends DamageSource {
    public static DamageSource boil = (new DamageSourceZp("boil"));
    public static DamageSource wire = (new DamageSourceZp("wire"));
    public static DamageSource stakes = (new DamageSourceZp("stakes"));
    public static DamageSource acid = (new DamageSourceZp("acid")).setDamageBypassesArmor();
    public static DamageSource virus = (new DamageSourceZp("virus")).setDamageIsAbsolute().setDamageBypassesArmor();
    public static DamageSource blood = (new DamageSourceZp("blood")).setDamageIsAbsolute().setDamageBypassesArmor();
    public static DamageSource explosionNew = (new DamageSourceZp("explosionNew")).setDamageBypassesArmor();

    public DamageSourceZp(String p_i1566_1_) {
        super(p_i1566_1_);
    }

    public static DamageSource causePlayerGlassBottleDamage(EntityPlayer p_76365_0_) {
        return new EntityDamageSource("glass_bottle", p_76365_0_);
    }

    public static DamageSource causePlayerBulletDamage(EntityPlayer p_76365_0_) {
        return new EntityDamageSource("bullet", p_76365_0_).setDamageBypassesArmor();
    }

    public static DamageSource causePlayerBulletHeadShotDamage(EntityPlayer p_76365_0_) {
        return new EntityDamageSource("bullet_headshot", p_76365_0_).setDamageBypassesArmor();
    }

    public static DamageSource causePlayerExplosionDamage(EntityPlayer p_76365_0_) {
        return new EntityDamageSource("explosionPlayer", p_76365_0_).setDamageBypassesArmor();
    }

    public static boolean isGlassBottleDamage(DamageSource damageSource) {
        return damageSource.getDamageType().equals("glass_bottle");
    }

    public static boolean isBulletDamage(DamageSource damageSource) {
        return damageSource.getDamageType().equals("bullet");
    }

    public static boolean isBulletHeadShotDamage(DamageSource damageSource) {
        return damageSource.getDamageType().equals("bullet_headshot");
    }

    public static boolean isExplosionDamage(DamageSource damageSource) {
        return damageSource.getDamageType().equals("explosionPlayer") || damageSource.getDamageType().equals("explosionNew") || damageSource.isExplosion();
    }
}
