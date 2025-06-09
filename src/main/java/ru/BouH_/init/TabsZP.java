package ru.BouH_.init;

import ru.BouH_.items.tab.CustomTab;

public class TabsZP {
    public static CustomTab blocks;
    public static CustomTab decorations;
    public static CustomTab admin_blocks;
    public static CustomTab weap;
    public static CustomTab items;
    public static CustomTab armor;
    public static CustomTab melee;
    public static CustomTab food;
    public static CustomTab medicine;
    public static CustomTab tools;
    public static CustomTab ammo;
    public static CustomTab mods;
    public static CustomTab progs;
    public static CustomTab camo;
    public static CustomTab special;
    public static CustomTab fun;
    public static CustomTab books;

    static {
        weap = new CustomTab("item.category.weapons");
        ammo = new CustomTab("item.category.ammo");
        items = new CustomTab("item.category.items");
        armor = new CustomTab("item.category.armor");
        camo = new CustomTab("item.category.camo");
        melee = new CustomTab("item.category.melee");
        tools = new CustomTab("item.category.tools");
        books = new CustomTab("item.category.books");
        blocks = new CustomTab("item.category.blocks");
        decorations = new CustomTab("item.category.decorations");
        admin_blocks = new CustomTab("item.category.admin_blocks");
        food = new CustomTab("item.category.food");
        medicine = new CustomTab("item.category.medicine");
        fun = new CustomTab("item.category.fun");
        special = new CustomTab("item.category.special");
        mods = new CustomTab("item.category.mods");
        progs = new CustomTab("item.category.programmers");
    }
}
