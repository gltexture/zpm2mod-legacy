package ru.BouH_.init;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.FMLLaunchHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.util.EnumHelper;
import ru.BouH_.Main;
import ru.BouH_.entity.projectile.*;
import ru.BouH_.items.armor.CamoType;
import ru.BouH_.items.armor.ItemArmor;
import ru.BouH_.items.armor.ItemArmorCamo;
import ru.BouH_.items.cases.*;
import ru.BouH_.items.food.*;
import ru.BouH_.items.gun.*;
import ru.BouH_.items.gun.ammo.ACommonAmmo;
import ru.BouH_.items.gun.ammo.ACustomAmmo;
import ru.BouH_.items.gun.ammo.AGaussAmmo;
import ru.BouH_.items.gun.ammo.fun.ASodaAmmo;
import ru.BouH_.items.gun.base.AGunBase;
import ru.BouH_.items.gun.base.fun.ALauncherBase;
import ru.BouH_.items.gun.base.fun.ItemIgla;
import ru.BouH_.items.gun.base.fun.ItemJavelin;
import ru.BouH_.items.gun.base.fun.ItemSodaGun;
import ru.BouH_.items.gun.crossbow.ItemCrossbow;
import ru.BouH_.items.gun.modules.*;
import ru.BouH_.items.gun.modules.base.ModuleInfo;
import ru.BouH_.items.gun.render.Shell;
import ru.BouH_.items.medicine.*;
import ru.BouH_.items.melee.*;
import ru.BouH_.items.misc.*;
import ru.BouH_.items.progs.ItemAntiGarbage;
import ru.BouH_.items.progs.ItemChance;
import ru.BouH_.items.progs.ItemFreezer;
import ru.BouH_.items.tab.CustomTab;
import ru.BouH_.items.tools.*;
import ru.BouH_.skills.SkillManager;

import java.util.List;

public class ItemsZp {
    public static AGunBase deagle;
    public static AGunBase deagle_gold;
    public static AGunBase pm;
    public static AGunBase tt;
    public static AGunBase glock18;
    public static AGunBase python;
    public static AGunBase fiveseven;
    public static AGunBase akm;
    public static AGunBase rpk;
    public static AGunBase ak12;
    public static AGunBase aksu;
    public static AGunBase bizon;
    public static AGunBase m1895;
    public static AGunBase m1894;
    public static AGunBase ar15;
    public static AGunBase casull290;
    public static AGunBase fal;
    public static AGunBase mini14;
    public static AGunBase m16a1;
    public static AGunBase m24;
    public static AGunBase scar;
    public static AGunBase mp5;
    public static AGunBase walther;
    public static AGunBase sporter;
    public static AGunBase g36k;
    public static AGunBase pkm;
    public static AGunBase m249;
    public static AGunBase mp40;
    public static AGunBase ppsh;
    public static AGunBase mini_uzi;
    public static AGunBase scout;
    public static AGunBase custom_pistol;
    public static AGunBase custom_revolver;
    public static AGunBase custom_sniper;
    public static AGunBase custom_shotgun;
    public static AGunBase custom_rifle;
    public static AGunBase rpg28;
    public static ALauncherBase javelin;
    public static ALauncherBase igla;
    public static AGunBase m79;
    public static AGunBase sks;
    public static AGunBase spas12;
    public static AGunBase ump45;
    public static AGunBase vss;
    public static AGunBase toz66;
    public static AGunBase toz66_short;
    public static AGunBase rem870;
    public static AGunBase mosin;
    public static Item book_fisher;
    public static Item book_gunsmith;
    public static Item book_farmer;
    public static Item book_hunter;
    public static Item book_survivor;
    public static Item book_miner;
    public static Item _50bmg;
    public static Item _custom;
    public static Item _custom2;
    public static AGunBase sg550;
    public static AGunBase barrett_m82a1;
    public static AGunBase m4a1;
    public static AGunBase sodagun;
    public static AGunBase saiga12;
    public static AGunBase blaser93;
    public static AGunBase aug;
    public static AGunBase svd;
    public static AGunBase p90;
    public static AGunBase m1911;
    public static AGunBase flare;
    public static AGunBase gauss;
    public static AGunBase crossbow;
    public static Item lockpick;
    public static Item freezer;
    public static Item antigarbage;
    public static Item chance;
    public static Item boiled_egg;
    public static Item bellows;
    public static Item hotdog;
    public static ItemScope hunting_scope;
    public static ItemScope scope_eotech;
    public static ItemScope scope_kobra;
    public static ItemScope pistol_scope;
    public static ItemFlashSuppressor flashSuppressor_pistol;
    public static ItemFlashSuppressor flashSuppressor_rifle;
    public static ItemLaser laser;
    public static AGunBase mac10;
    public static Item mem_elixir;
    public static Item boiled_water;
    public static Item cactus_water;
    public static Item cactus_food;
    public static ItemScope acog2x;
    public static ItemScope scope_kashtan;
    public static ItemScope scope4x;
    public static ItemScope scope_pu;
    public static ItemScope pso4x;
    public static ItemScope scope6x;
    public static ItemScope pso6x;
    public static ItemNVScope anPvs4;
    public static ItemNVScope nspu;
    public static ItemForegrip foregrip;
    public static ItemForegrip angle_foregrip;
    public static ItemM203 m203;
    public static ItemGP25 gp25;
    public static ItemBipod bipod;
    public static ItemSilencer silencer_rifle;
    public static ItemSilencer silencer_pistol;
    public static ItemMuzzleBrake muzzlebrake_rifle;
    public static ItemMuzzleBrake muzzlebrake_pistol;
    public static Item tnt;
    public static Item _gauss;
    public static Item _22lr;
    public static Item _9mm;
    public static Item _5_45x39;
    public static Item _5_56x45;
    public static Item _7_62x25;
    public static Item _7_62x39;
    public static Item _7_62x54R;
    public static Item _9x39;
    public static Item _12;
    public static Item _45acp;
    public static Item _5_7x28;
    public static Item _308win;
    public static Item _357m;
    public static Item _rocket;
    public static Item _javelin;
    public static Item electrician_kit;
    public static Item _igla;
    public static Item _grenade40mm;
    public static Item _wog25;
    public static Item _flare;
    public static Item _scare;
    public static Item _caramel;
    public static AGunBase caramel_gun;
    public static AGunBase scare_gun;
    public static AGunBase oc14;
    public static Item wrench;
    public static Item gift;
    public static Item bag;
    public static Item acid;
    public static Item plate;
    public static Item plate_meat;
    public static Item rock;
    public static Item holywater;
    public static Item trap_grenade;
    public static Item rot_mass;
    public static Item chisel;
    public static Item shelves;
    public static Item table;
    public static Item scrap_material;
    public static Item manyscrap;
    public static Item uran_material;
    public static Item armor_material;
    public static Item rad_helmet;
    public static Item rad_chestplate;
    public static Item rad_leggings;
    public static Item rad_boots;
    public static Item aqualung_helmet;
    public static Item aqualung_chestplate;
    public static Item aqualung_leggings;
    public static Item aqualung_boots;
    public static Item indcostume_helmet;
    public static Item indcostume_chestplate;
    public static Item indcostume_leggings;
    public static Item indcostume_boots;
    public static Item steel_helmet;
    public static Item steel_chestplate;
    public static Item steel_leggings;
    public static Item dynamike;
    public static Item steel_boots;
    public static Item forest_helmet;
    public static Item forest_chestplate;
    public static Item forest_leggings;
    public static Item forest_boots;
    public static Item rotten_chestplate;
    public static Item rotten_leggings;
    public static Item rotten_boots;
    public static Item sand_helmet;
    public static Item sand_chestplate;
    public static Item sand_leggings;
    public static Item sand_boots;
    public static Item winter_helmet;
    public static Item winter_chestplate;
    public static Item winter_leggings;
    public static Item winter_boots;
    public static ItemSword oxygen;
    public static Item gasmask;
    public static Item dosimeter;
    public static Item battery;
    public static Item coils;
    public static Item CBS;
    public static Item acid_bucket;
    public static Item cement;
    public static Item lubricant;
    public static Item matches;
    public static Item blood_material;
    public static Item binoculars;
    public static Item chemicals2;
    public static Item cash_coin;
    public static Item chemicals1;
    public static Item chemicals1_a;
    public static Item chemicals1_b;
    public static Item solid_fuel;
    public static Item chemicals1_c;
    public static Item repair;
    public static Item steel_material;
    public static Item brass_material;
    public static Item titan_ingot;
    public static Item copper_ingot;
    public static Item ammo_press;
    public static Item fish_bones;
    public static Item gps;
    public static Item stimulator;
    public static Item brass_nugget;
    public static Item juggernaut_helmet;
    public static Item juggernaut_chestplate;
    public static Item juggernaut_leggings;
    public static Item juggernaut_boots;
    public static Item kevlar;
    public static Item frame_helmet;
    public static Item frame_chestplate;
    public static Item frame_leggings;
    public static Item frame_boots;
    public static Item balaclava;
    public static Item kevlar_vest;
    public static Item kevlar_helmet;
    public static Item pnv;
    public static Item adrenaline;
    public static Item ai2_kit;
    public static Item aid_kit;
    public static Item ananas;
    public static Item banana;
    public static Item antidote_syringe;
    public static Item antihunger;
    public static Item antipoison;
    public static Item poison;
    public static Item antiradiation;
    public static Item death_syringe;
    public static Item antitoxin;
    public static Item antivirus_syringe;
    public static Item toxicwater_bucket;
    public static Item tire;
    public static ItemSword steel_sword;
    public static ItemSword scare_sword;
    public static ItemSword caramel_sword;
    public static ItemSword lucille;
    public static ItemAxe steel_axe;
    public static ItemPickaxe steel_pickaxe;
    public static ItemSpade steel_shovel;
    public static ItemHoe steel_hoe;
    public static ItemSword titan_sword;
    public static ItemSword copper_spear;
    public static ItemSword steel_spear;
    public static ItemAxe titan_axe;
    public static ItemPickaxe titan_pickaxe;
    public static ItemSpade titan_shovel;
    public static ItemSword copper_sword;
    public static ItemAxe copper_axe;
    public static ItemPickaxe copper_pickaxe;
    public static ItemSpade copper_shovel;
    public static ItemHoe copper_hoe;
    public static ItemHoe titan_hoe;
    public static Item bandage;
    public static Item military_bandage;
    public static Item m_scissors;
    public static Item beer;
    public static Item blind_syringe;
    public static Item blood_bag;
    public static Item burn;
    public static Item jam;
    public static Item donut;
    public static Item burger;
    public static Item cola;
    public static Item water;
    public static Item fish_canned;
    public static Item heroin;
    public static Item good_vision;
    public static Item heal;
    public static Item meth;
    public static Item morphine;
    public static Item night_vision;
    public static Item orange;
    public static Item pea;
    public static Item pepsi;
    public static Item rotten_apple;
    public static Item soup;
    public static Item steroid;
    public static Item stewed_meat;
    public static Item virus_syringe;
    public static Item vodka;
    public static Item fish_zp;
    public static Item fish_zp_cooked;
    public static Item antiheadache;
    public static Item steel_ingot;
    public static Item frag_grenade;
    public static Item gas_grenade;
    public static Item smoke_grenade;
    public static Item bag_random;
    public static Item coke;
    public static Item nuka_cola;
    public static Item scare_pumpkin;
    public static Item caramel;
    public static Item chemres;
    public static Item box_paper;
    public static Item tanning;
    public static Item upd_leather;
    public static Item transmitter;
    public static Item transmitter_tactic;
    public static Item old_backpack;
    public static Item old_backpack2;
    public static Item fish_box;
    public static Item fish_crate;
    public static Item fish_iron_crate;
    public static Item raw_iron;
    public static Item raw_copper;
    public static Item raw_gold;
    public static Item raw_titan;
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

    //FUN//
    public static CustomTab fun;
    public static CustomTab books;
    public static Item _customRocket;
    public static Item _katyushaRocket;
    public static Item _gradRocket;
    public static Item _c300Rocket;
    public static Item _r27Rocket;
    public static Item _solncepekRocket;
    public static Item _kalibrRocket;
    public static Item _iskanderRocket;
    public static Item _ovodRocket;
    public static Item _geran2;
    public static Item _x101Rocket;
    public static Item _pancirRocket;
    public static Item _uraganRocket;
    public static Item _bastionRocket;
    public static Item _kinzhalRocket;

    //FUN//

    public static ArmorMaterial rotten_mat = EnumHelper.addArmorMaterial("rotten_mat", 1, new int[]{1, 1, 1, 1}, 1);
    public static ArmorMaterial special_costume_mat = EnumHelper.addArmorMaterial("special_costume", 14, new int[]{2, 3, 3, 2}, 12);
    public static ArmorMaterial special_costume2_mat = EnumHelper.addArmorMaterial("special_costume2", 16, new int[]{2, 3, 3, 2}, 12);
    public static ArmorMaterial special_costume3_mat = EnumHelper.addArmorMaterial("special_costume3", 12, new int[]{2, 3, 3, 2}, 12);
    public static ArmorMaterial steel_mat = EnumHelper.addArmorMaterial("steel", 22, new int[]{2, 6, 5, 2}, 6);
    public static ArmorMaterial titan_mat = EnumHelper.addArmorMaterial("titan", 38, new int[]{3, 8, 6, 3}, 9);
    public static ArmorMaterial cloth_mat = EnumHelper.addArmorMaterial("cloth_forest_mat", 8, new int[]{1, 3, 2, 1}, 15);
    public static ArmorMaterial kevlar_mat = EnumHelper.addArmorMaterial("kevlar_mat", 10, new int[]{3, 4, 2, 1}, 12);
    public static Item.ToolMaterial oxygen_mat = EnumHelper.addToolMaterial("oxygen", 0, 250, 0, -1, 5);

    public static ItemSword armature;
    public static ItemSword bat;
    public static ItemSword ripper;
    public static ItemSword inferno;
    public static ItemSword bone_knife;
    public static ItemPickaxe mjolnir;
    public static Item electronic;
    public static Item custom_gunpowder;
    public static Item custom_repair;
    public static ItemSword cleaver;
    public static ItemSword crowbar;
    public static ItemSword golf_club;
    public static ItemSword hammer;
    public static ItemAxe hatchet;
    public static ItemSword iron_club;
    public static ItemSword katana;
    public static ItemSword machete;
    public static ItemSword pipe;
    public static ItemSword police_club;
    public static ItemSword screwdriver;
    public static ItemPickaxe sledgehammer;
    public static Item.ToolMaterial ripper_mat = EnumHelper.addToolMaterial("ripper", 0, 250, 0, 4.5f, 14);
    public static Item.ToolMaterial inferno_mat = EnumHelper.addToolMaterial("inferno_mat", 0, 182, 0, 2.0f, 14);
    public static Item.ToolMaterial mjolnir_mat = EnumHelper.addToolMaterial("mjolnir", 2, 226, 2, 5.0f, 14);
    public static Item.ToolMaterial armature_mat = EnumHelper.addToolMaterial("armature", 0, 32, 0, 0.0f, 5);
    public static Item.ToolMaterial bat_mat = EnumHelper.addToolMaterial("bat", 0, 131, 0, 0.5f, 5);
    public static Item.ToolMaterial lucille_mat = EnumHelper.addToolMaterial("lucille", 0, 154, 0, 1.5f, 14);
    public static Item.ToolMaterial cleaver_mat = EnumHelper.addToolMaterial("cleaver", 0, 120, 0, 2.0f, 14);
    public static Item.ToolMaterial crowbar_mat = EnumHelper.addToolMaterial("crowbar", 0, 162, 0, 1.0f, 14);
    public static Item.ToolMaterial golf_club_mat = EnumHelper.addToolMaterial("golf_club", 0, 124, 0, 0.5f, 5);
    public static Item.ToolMaterial hammer_mat = EnumHelper.addToolMaterial("hammer", 0, 81, 0, 0.5f, 5);
    public static Item.ToolMaterial sledgehammer_mat = EnumHelper.addToolMaterial("sledgehammer", 2, 200, 3, 4.0f, 14);
    public static Item.ToolMaterial hatchet_mat = EnumHelper.addToolMaterial("hatchet", 2, 200, 3, 3.0f, 14);
    public static Item.ToolMaterial iron_club_mat = EnumHelper.addToolMaterial("iron_club", 0, 186, 0, 1.5f, 14);
    public static Item.ToolMaterial katana_mat = EnumHelper.addToolMaterial("katana", 0, 300, 0, 3.0f, 22);
    public static Item.ToolMaterial machete_mat = EnumHelper.addToolMaterial("machete", 0, 350, 0, 2.5f, 22);
    public static Item.ToolMaterial pipe_mat = EnumHelper.addToolMaterial("pipe", 0, 96, 0, 0.0f, 5);
    public static Item.ToolMaterial police_club_mat = EnumHelper.addToolMaterial("police_club", 0, 144, 0, 1.0f, 14);
    public static Item.ToolMaterial screwdriver_mat = EnumHelper.addToolMaterial("screwdriver", 0, 61, 0, 1.0f, 5);
    public static Item.ToolMaterial steelItem_mat = EnumHelper.addToolMaterial("steel", 2, 450, 6.0F, 2.0F, 14);
    public static Item.ToolMaterial titanItem_mat = EnumHelper.addToolMaterial("titan", 3, 1125, 8.0F, 3.0F, 10);
    public static Item.ToolMaterial copperItem_mat = EnumHelper.addToolMaterial("copper", 1, 82, 5.0F, 1.5F, 15);
    public static Item.ToolMaterial scare_mat = EnumHelper.addToolMaterial("scare_sword", 3, 625, 8.0F, 3.0F, 10);
    public static Item.ToolMaterial caramel_mat = EnumHelper.addToolMaterial("caramel_sword", 3, 625, 8.0F, 3.0F, 10);
    public static Item.ToolMaterial bone_mat = EnumHelper.addToolMaterial("bone", 0, 10, 0.0F, 0.0F, 14);

    public static void init() {
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

        cash_coin = new ItemCoin();
        cash_coin.setCreativeTab(items);
        cash_coin.setMaxStackSize(10);

        adrenaline = new MedicineSyringe("adrenaline", new PotionEffect[]{new PotionEffect(29, 1200), new PotionEffect(3, 1200)}, false, true);
        adrenaline.setMaxStackSize(4);
        adrenaline.setCreativeTab(medicine);

        coke = new MedicineCoke("coke", false, true);
        coke.setMaxStackSize(4);
        coke.setCreativeTab(medicine);

        nuka_cola = new FoodNuka("nuka_cola", 12, 1, false, true);
        nuka_cola.setMaxStackSize(16);
        nuka_cola.setCreativeTab(food);

        antiheadache = new MedicinePill("antiheadache", new PotionEffect[]{new PotionEffect(9, -1)}, false, true);
        antiheadache.setMaxStackSize(4);
        antiheadache.setCreativeTab(medicine);

        antihunger = new MedicinePill("antihunger", new PotionEffect[]{new PotionEffect(17, -1), new PotionEffect(31, -1)}, false, true);
        antihunger.setMaxStackSize(4);
        antihunger.setCreativeTab(medicine);

        antipoison = new MedicinePill("antipoison", new PotionEffect[]{new PotionEffect(19, -1)}, false, true);
        antipoison.setMaxStackSize(4);
        antipoison.setCreativeTab(medicine);

        poison = new MedicinePoisonousPill("poison", false, false);
        poison.setMaxStackSize(1);
        poison.setMaxDurability(4);
        poison.setCreativeTab(medicine);

        antitoxin = new MedicinePill("antitoxin", new PotionEffect[]{new PotionEffect(9, -1), new PotionEffect(17, -1), new PotionEffect(19, -1), new PotionEffect(31, -1)}, false, true);
        antitoxin.setMaxStackSize(4);
        antitoxin.setCreativeTab(medicine);

        good_vision = new MedicinePill("good_vision", new PotionEffect[]{new PotionEffect(25, 12000)}, false, true);
        good_vision.setMaxStackSize(4);
        good_vision.setCreativeTab(medicine);

        heal = new MedicinePill("heal", new PotionEffect[]{new PotionEffect(10, 300)}, false, true);
        heal.setMaxStackSize(4);
        heal.setCreativeTab(medicine);

        antiradiation = new MedicinePill("antiradiation", new PotionEffect[]{new PotionEffect(33, 1800)}, false, true);
        antiradiation.setMaxStackSize(4);
        antiradiation.setCreativeTab(medicine);

        meth = new MedicinePill("meth", new PotionEffect[]{new PotionEffect(29, 8400), new PotionEffect(9, 12000), new PotionEffect(31, 1800)}, false, true);
        meth.setMaxStackSize(4);
        meth.setCreativeTab(medicine);

        night_vision = new MedicinePill("night_vision", new PotionEffect[]{new PotionEffect(16, 6000)}, false, true);
        night_vision.setMaxStackSize(4);
        night_vision.setCreativeTab(medicine);

        steroid = new MedicinePill("steroid", new PotionEffect[]{new PotionEffect(30, 1200), new PotionEffect(1, 6000), new PotionEffect(5, 6000, 1), new PotionEffect(11, 6000, 1)}, false, true);
        steroid.setMaxStackSize(4);
        steroid.setCreativeTab(medicine);

        antivirus_syringe = new MedicineAntivirusSyringe("antivirus_syringe", false, true);
        antivirus_syringe.setMaxStackSize(4);
        antivirus_syringe.setCreativeTab(medicine);

        bandage = new MedicineBandage("bandage", false, true);
        bandage.setMaxStackSize(4);
        bandage.setCreativeTab(medicine);

        tire = new MedicineTire("tire", false, true);
        tire.setMaxStackSize(4);
        tire.setCreativeTab(medicine);

        military_bandage = new MedicineBandage("military_bandage", false, true);
        military_bandage.setMaxStackSize(1);
        military_bandage.setMaxDurability(5);
        military_bandage.setCreativeTab(medicine);

        blind_syringe = new MedicineSyringe("blind_syringe", new PotionEffect[]{new PotionEffect(15, 200), new PotionEffect(9, 600)}, false, true);
        blind_syringe.setMaxStackSize(4);
        blind_syringe.setCreativeTab(medicine);

        blood_bag = new MedicineBloodBag("blood_bag", false, true);
        blood_bag.setMaxStackSize(8);
        blood_bag.setCreativeTab(medicine);

        heroin = new MedicineSyringe("heroin", new PotionEffect[]{new PotionEffect(29, 6000), new PotionEffect(11, 3600, 1), new PotionEffect(9, 18000), new PotionEffect(31, 1800)}, false, true);
        heroin.setMaxStackSize(4);
        heroin.setCreativeTab(medicine);

        morphine = new MedicineSyringe("morphine", new PotionEffect[]{new PotionEffect(27, -1)}, false, true);
        morphine.setUnlocalizedName("morphine");
        morphine.setMaxStackSize(4);
        morphine.setCreativeTab(medicine);

        death_syringe = new MedicineDeathSyringe("death_syringe", false, true);
        death_syringe.setMaxStackSize(4);
        death_syringe.setCreativeTab(medicine);

        virus_syringe = new MedicineSyringe("virus_syringe", new PotionEffect[]{new PotionEffect(26, 21600)}, false, true);
        virus_syringe.setMaxStackSize(4);
        virus_syringe.setCreativeTab(medicine);

        ai2_kit = new MedicineAi2("ai2_kit", false, true);
        ai2_kit.setMaxStackSize(4);
        ai2_kit.setCreativeTab(medicine);

        aid_kit = new MedicineAid("aid_kit", false, true);
        aid_kit.setMaxStackSize(4);
        aid_kit.setCreativeTab(medicine);

        binoculars = new ItemBinoculars("binoculars");
        binoculars.setMaxStackSize(1);
        binoculars.setCreativeTab(tools);

        mem_elixir = new FoodMemElixir("mem_elixir", 10, 0.5f, false, true);
        mem_elixir.setMaxStackSize(1);
        mem_elixir.setCreativeTab(medicine);

        ananas = new FoodCan("ananas", 18, 1.5f, false, true);
        ananas.setMaxStackSize(16);
        ananas.setCreativeTab(food);

        banana = new FoodCommon("banana", 14, 1.0f, false, true);
        banana.setMaxStackSize(16);
        banana.setCreativeTab(food);

        jam = new FoodCommon("jam", 15, 0.5f, false, true);
        jam.setMaxStackSize(16);
        jam.setCreativeTab(food);

        burger = new FoodCommon("burger", 20, 1.0f, false, true);
        burger.setMaxStackSize(16);
        burger.setCreativeTab(food);

        donut = new FoodCommon("donut", 12, 0.5f, false, true);
        donut.setMaxStackSize(16);
        donut.setCreativeTab(food);

        antidote_syringe = new MedicineAntidoteSyringe("antidote_syringe", false, true);
        antidote_syringe.setMaxStackSize(4);
        antidote_syringe.setCreativeTab(medicine);

        beer = new FoodBeer("beer", 18, 1.0f, false, true);
        beer.setMaxStackSize(16);
        beer.setCreativeTab(food);

        burn = new FoodSoda("burn", 15, 0.5f, false, true);
        burn.setMaxStackSize(16);
        burn.setCreativeTab(food);

        cola = new FoodSoda("cola", 15, 0.5f, false, true);
        cola.setMaxStackSize(16);
        cola.setCreativeTab(food);

        water = new FoodDrink("water", 24, 1.5f, false, true);
        water.setMaxStackSize(16);
        water.setCreativeTab(food);

        boiled_water = new FoodDrinkGlass("boiled_water", 38, 2.0f, false, true);
        boiled_water.setMaxStackSize(1);
        boiled_water.setCreativeTab(food);

        cactus_water = new FoodDrinkCactus("cactus_water", 12, 1.0f, false, true);
        cactus_water.setMaxStackSize(1);
        cactus_water.setCreativeTab(food);

        cactus_food = new FoodCactusBowl("cactus_food", 12, 1.0f, false, true);
        cactus_food.setMaxStackSize(1);
        cactus_food.setCreativeTab(food);

        pepsi = new FoodSoda("pepsi", 15, 0.5f, false, true);
        pepsi.setMaxStackSize(16);
        pepsi.setCreativeTab(food);

        stimulator = new FoodDrinkStimulator("stimulator", 15, 0.5f, false, true);
        stimulator.setMaxStackSize(1);
        stimulator.setCreativeTab(medicine);

        fish_canned = new FoodFish("fish", 18, 1.5f, true, true);
        fish_canned.setMaxStackSize(16);
        fish_canned.setCreativeTab(food);

        orange = new FoodCommon("orange", 14, 1.0f, true, true);
        orange.setMaxStackSize(16);
        orange.setCreativeTab(food);

        pea = new FoodCan("pea", 20, 1.5f, false, true);
        pea.setMaxStackSize(16);
        pea.setCreativeTab(food);

        rotten_apple = new FoodRottenApple("rotten_apple", 8, 1.0f, false, true);
        rotten_apple.setMaxStackSize(16);
        rotten_apple.setCreativeTab(food);

        boiled_egg = new FoodEgg("boiled_egg", 6, 0.05f, false, true);
        boiled_egg.setMaxStackSize(16);
        boiled_egg.setCreativeTab(food);

        hotdog = new FoodCommon("hotdog", 20, 1.5f, false, true);
        hotdog.setMaxStackSize(16);
        hotdog.setCreativeTab(food);

        rot_mass = new FoodRotMass("rot_mass", 1, 0.0f, false, false);
        rot_mass.setMaxStackSize(16);
        rot_mass.setCreativeTab(items);

        soup = new FoodCan("soup", 24, 3.0f, true, true);
        soup.setCreativeTab(food);
        soup.setMaxStackSize(16);
        soup.setCreativeTab(food);

        stewed_meat = new FoodCan("stewed_meat", 30, 1.5f, true, true);
        stewed_meat.setMaxStackSize(16);
        stewed_meat.setCreativeTab(food);

        vodka = new FoodVodka("vodka", 15, 1.5f, false, true);
        vodka.setMaxStackSize(16);
        vodka.setCreativeTab(food);

        fish_zp = new FoodWaterFish(false);
        fish_zp.setMaxStackSize(16);
        fish_zp.setCreativeTab(food);

        fish_zp_cooked = new FoodWaterFish(true);
        fish_zp_cooked.setMaxStackSize(16);
        fish_zp_cooked.setCreativeTab(food);

        gift = new ItemGift("gift");
        gift.setMaxStackSize(1);
        gift.setCreativeTab(special);

        bag = new ItemHalloweenBag("bag");
        bag.setMaxStackSize(1);
        bag.setCreativeTab(special);

        old_backpack = new ItemOldBackpack("old_backpack");
        old_backpack.setMaxStackSize(1);
        old_backpack.setCreativeTab(items);

        old_backpack2 = new ItemOldBackpack2("old_backpack2");
        old_backpack2.setMaxStackSize(1);
        old_backpack2.setCreativeTab(items);

        fish_box = new ItemFishBox("fish_box");
        fish_box.setMaxStackSize(1);
        fish_box.setCreativeTab(items);

        fish_crate = new ItemFishCrate("fish_crate");
        fish_crate.setMaxStackSize(1);
        fish_crate.setCreativeTab(items);

        fish_iron_crate = new ItemFishIronCrate("fish_iron_crate");
        fish_iron_crate.setMaxStackSize(1);
        fish_iron_crate.setCreativeTab(items);

        book_hunter = new ItemBookSkill("book_hunter", SkillManager.instance.hunter);
        book_hunter.setMaxStackSize(1);
        book_hunter.setCreativeTab(books);

        book_fisher = new ItemBookSkill("book_fisher", SkillManager.instance.fisher);
        book_fisher.setMaxStackSize(1);
        book_fisher.setCreativeTab(books);

        book_miner = new ItemBookSkill("book_miner", SkillManager.instance.miner);
        book_miner.setMaxStackSize(1);
        book_miner.setCreativeTab(books);

        book_survivor = new ItemBookSkill("book_survivor", SkillManager.instance.survivor);
        book_survivor.setMaxStackSize(1);
        book_survivor.setCreativeTab(books);

        book_farmer = new ItemBookSkill("book_farmer", SkillManager.instance.farmer);
        book_farmer.setMaxStackSize(1);
        book_farmer.setCreativeTab(books);

        book_gunsmith = new ItemBookSkill("book_gunsmith", SkillManager.instance.gunSmith);
        book_gunsmith.setMaxStackSize(1);
        book_gunsmith.setCreativeTab(books);

        bag_random = new ItemBagRandom("bag_random");
        bag_random.setMaxStackSize(1);
        bag_random.setCreativeTab(fun);

        transmitter = new ItemTransmitter("transmitter");
        transmitter.setMaxStackSize(1);
        transmitter.setMaxDurability(8);
        transmitter.setCreativeTab(tools);

        transmitter_tactic = new ItemTransmitterTactic("transmitter_tactic");
        transmitter_tactic.setMaxStackSize(1);
        transmitter_tactic.setCreativeTab(fun);

        freezer = new ItemFreezer("freezer");
        freezer.setMaxStackSize(1);
        freezer.setCreativeTab(progs);

        chance = new ItemChance("chance");
        chance.setMaxStackSize(1);
        chance.setCreativeTab(progs);

        antigarbage = new ItemAntiGarbage("antigarbage");
        antigarbage.setMaxStackSize(1);
        antigarbage.setCreativeTab(progs);

        scare_sword = new ItemSword(scare_mat) {
            @SideOnly(Side.CLIENT)
            @SuppressWarnings("unchecked")
            public void addInformation(ItemStack stack, EntityPlayer playerIn, List tooltip, boolean advanced) {
                tooltip.add(EnumChatFormatting.RED + I18n.format("misc.scare"));
            }
        };
        scare_sword.setUnlocalizedName("scare_sword");
        scare_sword.setCreativeTab(special);

        caramel_sword = new ItemSword(caramel_mat) {
            @SideOnly(Side.CLIENT)
            @SuppressWarnings("unchecked")
            public void addInformation(ItemStack stack, EntityPlayer playerIn, List tooltip, boolean advanced) {
                tooltip.add(EnumChatFormatting.AQUA + I18n.format("misc.newyear"));
            }
        };
        caramel_sword.setUnlocalizedName("caramel_sword");
        caramel_sword.setCreativeTab(special);

        armature = new ItemSword(armature_mat);
        armature.setUnlocalizedName("armature");
        armature.setCreativeTab(melee);

        bone_knife = new ItemSword(bone_mat);
        bone_knife.setUnlocalizedName("bone_knife");
        bone_knife.setCreativeTab(melee);

        bat = new ItemSword(bat_mat);
        bat.setUnlocalizedName("bat");
        bat.setCreativeTab(melee);

        lucille = new ItemSword(lucille_mat);
        lucille.setUnlocalizedName("lucille");
        lucille.setCreativeTab(melee);

        ripper = new ItemBloodsucker(ripper_mat);
        ripper.setUnlocalizedName("ripper");
        ripper.setCreativeTab(melee);

        inferno = new ItemTorch(inferno_mat);
        inferno.setUnlocalizedName("inferno");
        inferno.setCreativeTab(melee);

        mjolnir = new ItemMjolnir(mjolnir_mat);
        mjolnir.setUnlocalizedName("mjolnir");
        mjolnir.setCreativeTab(melee);

        cleaver = new ItemSword(cleaver_mat);
        cleaver.setUnlocalizedName("cleaver");
        cleaver.setCreativeTab(melee);

        crowbar = new ItemCrowbar(crowbar_mat);
        crowbar.setUnlocalizedName("crowbar");
        crowbar.setCreativeTab(melee);

        golf_club = new ItemSword(golf_club_mat);
        golf_club.setUnlocalizedName("golf_club");
        golf_club.setCreativeTab(melee);

        hammer = new ItemSword(hammer_mat);
        hammer.setUnlocalizedName("hammer");
        hammer.setCreativeTab(melee);

        hatchet = new ItemHatchet(hatchet_mat);
        hatchet.setUnlocalizedName("hatchet");
        hatchet.setCreativeTab(melee);

        iron_club = new ItemSword(iron_club_mat);
        iron_club.setUnlocalizedName("iron_club");
        iron_club.setCreativeTab(melee);

        katana = new ItemSword(katana_mat);
        katana.setUnlocalizedName("katana");
        katana.setCreativeTab(melee);

        steel_sword = new ItemSword(steelItem_mat);
        steel_sword.setUnlocalizedName("steel_sword");
        steel_sword.setCreativeTab(melee);

        steel_hoe = new ItemHoe(steelItem_mat);
        steel_hoe.setUnlocalizedName("steel_hoe");
        steel_hoe.setCreativeTab(tools);

        steel_axe = new ItemHatchet(steelItem_mat);
        steel_axe.setUnlocalizedName("steel_axe");
        steel_axe.setCreativeTab(tools);

        steel_pickaxe = new ItemHammer(steelItem_mat);
        steel_pickaxe.setUnlocalizedName("steel_pickaxe");
        steel_pickaxe.setCreativeTab(tools);

        steel_shovel = new ItemSpade(steelItem_mat);
        steel_shovel.setUnlocalizedName("steel_shovel");
        steel_shovel.setCreativeTab(tools);

        titan_sword = new ItemSword(titanItem_mat);
        titan_sword.setUnlocalizedName("titan_sword");
        titan_sword.setCreativeTab(melee);

        titan_hoe = new ItemHoe(titanItem_mat);
        titan_hoe.setUnlocalizedName("titan_hoe");
        titan_hoe.setCreativeTab(tools);

        titan_axe = new ItemHatchet(titanItem_mat);
        titan_axe.setUnlocalizedName("titan_axe");
        titan_axe.setCreativeTab(tools);

        titan_pickaxe = new ItemHammer(titanItem_mat);
        titan_pickaxe.setUnlocalizedName("titan_pickaxe");
        titan_pickaxe.setCreativeTab(tools);

        titan_shovel = new ItemSpade(titanItem_mat);
        titan_shovel.setUnlocalizedName("titan_shovel");
        titan_shovel.setCreativeTab(tools);

        steel_spear = new ItemSpear(steelItem_mat);
        steel_spear.setUnlocalizedName("steel_spear");
        steel_spear.setMaxDurability((int) (steel_spear.getMaxDurability() / 2.0f));
        steel_spear.setCreativeTab(melee);

        copper_spear = new ItemSpear(copperItem_mat);
        copper_spear.setUnlocalizedName("copper_spear");
        copper_spear.setMaxDurability((int) (copper_spear.getMaxDurability() / 2.0f));
        copper_spear.setCreativeTab(melee);

        copper_sword = new ItemSword(copperItem_mat);
        copper_sword.setUnlocalizedName("copper_sword");
        copper_sword.setCreativeTab(melee);

        copper_hoe = new ItemHoe(copperItem_mat);
        copper_hoe.setUnlocalizedName("copper_hoe");
        copper_hoe.setCreativeTab(tools);

        copper_axe = new ItemHatchet(copperItem_mat);
        copper_axe.setUnlocalizedName("copper_axe");
        copper_axe.setCreativeTab(tools);

        copper_pickaxe = new ItemHammer(copperItem_mat);
        copper_pickaxe.setUnlocalizedName("copper_pickaxe");
        copper_pickaxe.setCreativeTab(tools);

        copper_shovel = new ItemSpade(copperItem_mat);
        copper_shovel.setUnlocalizedName("copper_shovel");
        copper_shovel.setCreativeTab(tools);

        machete = new ItemSword(machete_mat);
        machete.setUnlocalizedName("machete");
        machete.setCreativeTab(melee);

        pipe = new ItemSword(pipe_mat);
        pipe.setUnlocalizedName("pipe");
        pipe.setCreativeTab(melee);

        police_club = new ItemSword(police_club_mat);
        police_club.setUnlocalizedName("police_club");
        police_club.setCreativeTab(melee);

        screwdriver = new ItemSword(screwdriver_mat);
        screwdriver.setUnlocalizedName("screwdriver");
        screwdriver.setCreativeTab(melee);

        sledgehammer = new ItemHammer(sledgehammer_mat);
        sledgehammer.setUnlocalizedName("sledgehammer");
        sledgehammer.setCreativeTab(melee);

        acid_bucket = new ItemFluidBucket(FluidsZp.acidblock, "acid_bucket");
        acid_bucket.setCreativeTab(items);
        acid_bucket.setMaxStackSize(1);

        toxicwater_bucket = new ItemFluidBucket(FluidsZp.toxicwater_block, "toxicwater_bucket");
        toxicwater_bucket.setCreativeTab(items);
        toxicwater_bucket.setMaxStackSize(1);

        oxygen = new ItemOxygen("oxygen", oxygen_mat);
        oxygen.setCreativeTab(items);
        oxygen.setMaxStackSize(1);

        custom_gunpowder = new Item().setUnlocalizedName("custom_gunpowder");
        custom_gunpowder.setCreativeTab(items);
        custom_gunpowder.setMaxStackSize(16);

        raw_iron = new Item().setUnlocalizedName("raw_iron");
        raw_iron.setCreativeTab(items);
        raw_iron.setMaxStackSize(16);

        raw_copper = new Item().setUnlocalizedName("raw_copper");
        raw_copper.setCreativeTab(items);
        raw_copper.setMaxStackSize(16);

        raw_gold = new Item().setUnlocalizedName("raw_gold");
        raw_gold.setCreativeTab(items);
        raw_gold.setMaxStackSize(16);

        raw_titan = new Item().setUnlocalizedName("raw_titan");
        raw_titan.setCreativeTab(items);
        raw_titan.setMaxStackSize(16);

        lubricant = new Item().setUnlocalizedName("lubricant");
        lubricant.setCreativeTab(items);
        lubricant.setMaxStackSize(1);

        electronic = new Item().setUnlocalizedName("electronic");
        electronic.setCreativeTab(items);
        electronic.setMaxStackSize(16);

        solid_fuel = new Item().setUnlocalizedName("solid_fuel");
        solid_fuel.setCreativeTab(items);
        solid_fuel.setMaxStackSize(4);

        battery = new Item().setUnlocalizedName("battery");
        battery.setCreativeTab(items);
        battery.setMaxStackSize(16);

        copper_ingot = new Item().setUnlocalizedName("copper_ingot");
        copper_ingot.setCreativeTab(items);
        copper_ingot.setMaxStackSize(16);

        brass_nugget = new Item().setUnlocalizedName("brass_nugget");
        brass_nugget.setCreativeTab(items);

        brass_material = new ItemBrassMaterial("brass_material");
        brass_material.setCreativeTab(items);
        brass_material.setMaxStackSize(16);

        fish_bones = new Item().setUnlocalizedName("fish_bones");
        fish_bones.setCreativeTab(items);

        ammo_press = new ItemAmmoPress("ammo_press");
        ammo_press.setMaxStackSize(1);
        ammo_press.setMaxDurability(16);
        ammo_press.setCreativeTab(items);
        ammo_press.setNoRepair();

        coils = new Item().setUnlocalizedName("coils");
        coils.setCreativeTab(items);
        coils.setMaxStackSize(16);

        bellows = new Item().setUnlocalizedName("bellows");
        bellows.setCreativeTab(items);
        bellows.setMaxStackSize(1);

        lockpick = new Item().setUnlocalizedName("lockpick");
        lockpick.setCreativeTab(tools);
        lockpick.setMaxStackSize(1);

        kevlar = new Item().setUnlocalizedName("kevlar");
        kevlar.setCreativeTab(items);
        kevlar.setMaxStackSize(16);

        frame_helmet = new Item().setUnlocalizedName("frame_helmet");
        frame_helmet.setCreativeTab(items);
        frame_helmet.setMaxStackSize(1);

        frame_chestplate = new Item().setUnlocalizedName("frame_chestplate");
        frame_chestplate.setCreativeTab(items);
        frame_chestplate.setMaxStackSize(1);

        frame_leggings = new Item().setUnlocalizedName("frame_leggings");
        frame_leggings.setCreativeTab(items);
        frame_leggings.setMaxStackSize(1);

        frame_boots = new Item().setUnlocalizedName("frame_boots");
        frame_boots.setCreativeTab(items);
        frame_boots.setMaxStackSize(1);

        scare_pumpkin = new Item().setUnlocalizedName("scare_pumpkin");
        scare_pumpkin.setCreativeTab(special);
        scare_pumpkin.setMaxStackSize(1);

        caramel = new Item().setUnlocalizedName("caramel");
        caramel.setCreativeTab(special);
        caramel.setMaxStackSize(1);

        gps = new Item().setUnlocalizedName("gps");
        gps.setCreativeTab(tools);
        gps.setMaxDurability(120);
        gps.setMaxStackSize(1);

        upd_leather = new Item().setUnlocalizedName("upd_leather");
        upd_leather.setCreativeTab(items);
        upd_leather.setMaxStackSize(16);

        tanning = new Item().setUnlocalizedName("tanning");
        tanning.setCreativeTab(items);
        tanning.setMaxStackSize(3);

        chisel = new Item().setUnlocalizedName("chisel");
        chisel.setCreativeTab(items);
        chisel.setMaxStackSize(6);

        table = new Item().setUnlocalizedName("table");
        table.setCreativeTab(items);
        table.setMaxStackSize(6);

        shelves = new Item().setUnlocalizedName("shelves");
        shelves.setCreativeTab(items);
        shelves.setMaxStackSize(6);

        box_paper = new Item().setUnlocalizedName("box_paper");
        box_paper.setCreativeTab(items);
        box_paper.setMaxStackSize(64);

        dosimeter = new Item().setUnlocalizedName("dosimeter");
        dosimeter.setCreativeTab(tools);
        dosimeter.setMaxDurability(180);
        dosimeter.setMaxStackSize(1);

        CBS = new Item().setUnlocalizedName("CBS");
        CBS.setCreativeTab(items);
        CBS.setMaxDurability(180);
        CBS.setMaxStackSize(1);

        electrician_kit = new ItemLampTool("electrician_kit");
        electrician_kit.setCreativeTab(items);
        electrician_kit.setMaxStackSize(1);
        electrician_kit.setMaxDurability(5);
        electrician_kit.setNoRepair();

        cement = new Item().setUnlocalizedName("cement").setContainerItem(Items.bucket);
        cement.setCreativeTab(items);
        cement.setMaxStackSize(1);

        chemres = new Item().setUnlocalizedName("chemres");
        chemres.setCreativeTab(items);
        chemres.setMaxStackSize(6);

        matches = new ItemMatches("matches");
        matches.setCreativeTab(items);
        matches.setMaxDurability(24);
        matches.setMaxStackSize(1);

        wrench = new ItemWrench("wrench");
        wrench.setCreativeTab(tools);
        wrench.setMaxDurability(48);
        wrench.setMaxStackSize(1);

        blood_material = new Item().setUnlocalizedName("blood_material");
        blood_material.setCreativeTab(items);
        blood_material.setMaxStackSize(1);

        steel_ingot = new Item().setUnlocalizedName("steel_ingot");
        steel_ingot.setCreativeTab(items);
        steel_ingot.setMaxStackSize(16);

        steel_material = new ItemSteelMaterial("steel_material");
        steel_material.setCreativeTab(items);
        steel_material.setMaxStackSize(16);

        chemicals2 = new Item().setUnlocalizedName("chemicals2");
        chemicals2.setCreativeTab(items);
        chemicals2.setMaxDurability(24);
        chemicals2.setMaxStackSize(1);

        gasmask = new ItemArmor("gasmask", special_costume_mat, 1, 0);
        gasmask.setCreativeTab(armor);

        pnv = new ItemArmor("pnv", steel_mat, 1, 0);
        pnv.setCreativeTab(armor);

        balaclava = new ItemArmorCamo("balaclava", cloth_mat, 1, CamoType.UNIVERSAL, 0);
        balaclava.setCreativeTab(camo);

        dynamike = new ItemArmor("dynamike", cloth_mat, 1, 1);
        dynamike.setCreativeTab(armor);

        kevlar_vest = new ItemArmor("kevlar_vest", kevlar_mat, 1, 1);
        kevlar_vest.setCreativeTab(armor);

        kevlar_helmet = new ItemArmor("kevlar_helmet", kevlar_mat, 1, 0);
        kevlar_helmet.setCreativeTab(armor);

        forest_helmet = new ItemArmorCamo("forest_helmet", cloth_mat, 1, CamoType.FOREST, 0);
        forest_helmet.setCreativeTab(camo);

        forest_chestplate = new ItemArmorCamo("forest_chestplate", cloth_mat, 1, CamoType.FOREST, 1);
        forest_chestplate.setCreativeTab(camo);

        forest_leggings = new ItemArmorCamo("forest_leggings", cloth_mat, 2, CamoType.FOREST, 2);
        forest_leggings.setCreativeTab(camo);

        forest_boots = new ItemArmorCamo("forest_boots", cloth_mat, 1, CamoType.FOREST, 3);
        forest_boots.setCreativeTab(camo);

        rotten_chestplate = new ItemArmor("rotten_chestplate", rotten_mat, 1, 1);
        rotten_chestplate.setCreativeTab(camo);

        rotten_leggings = new ItemArmor("rotten_leggings", rotten_mat, 2, 2);
        rotten_leggings.setCreativeTab(camo);

        rotten_boots = new ItemArmor("rotten_boots", rotten_mat, 1, 3);
        rotten_boots.setCreativeTab(camo);

        winter_helmet = new ItemArmorCamo("winter_helmet", cloth_mat, 1, CamoType.WINTER, 0);
        winter_helmet.setCreativeTab(camo);

        winter_chestplate = new ItemArmorCamo("winter_chestplate", cloth_mat, 1, CamoType.WINTER, 1);
        winter_chestplate.setCreativeTab(camo);

        winter_leggings = new ItemArmorCamo("winter_leggings", cloth_mat, 2, CamoType.WINTER, 2);
        winter_leggings.setCreativeTab(camo);

        winter_boots = new ItemArmorCamo("winter_boots", cloth_mat, 1, CamoType.WINTER, 3);
        winter_boots.setCreativeTab(camo);

        sand_helmet = new ItemArmorCamo("sand_helmet", cloth_mat, 1, CamoType.SAND, 0);
        sand_helmet.setCreativeTab(camo);

        sand_chestplate = new ItemArmorCamo("sand_chestplate", cloth_mat, 1, CamoType.SAND, 1);
        sand_chestplate.setCreativeTab(camo);

        sand_leggings = new ItemArmorCamo("sand_leggings", cloth_mat, 2, CamoType.SAND, 2);
        sand_leggings.setCreativeTab(camo);

        sand_boots = new ItemArmorCamo("sand_boots", cloth_mat, 1, CamoType.SAND, 3);
        sand_boots.setCreativeTab(camo);

        armor_material = new Item().setUnlocalizedName("armor_material");
        armor_material.setCreativeTab(items);
        armor_material.setMaxStackSize(8);

        steel_helmet = new ItemArmor("steel_helmet", steel_mat, 1, 0);
        steel_helmet.setCreativeTab(armor);

        steel_chestplate = new ItemArmor("steel_chestplate", steel_mat, 1, 1);
        steel_chestplate.setCreativeTab(armor);

        steel_leggings = new ItemArmor("steel_leggings", steel_mat, 2, 2);
        steel_leggings.setCreativeTab(armor);

        steel_boots = new ItemArmor("steel_boots", steel_mat, 1, 3);
        steel_boots.setCreativeTab(armor);

        titan_ingot = new Item().setUnlocalizedName("titan_ingot");
        titan_ingot.setCreativeTab(items);
        titan_ingot.setMaxStackSize(16);

        juggernaut_helmet = new ItemArmor("juggernaut_helmet", titan_mat, 1, 0);
        juggernaut_helmet.setCreativeTab(armor);

        juggernaut_chestplate = new ItemArmor("juggernaut_chestplate", titan_mat, 1, 1);
        juggernaut_chestplate.setCreativeTab(armor);

        juggernaut_leggings = new ItemArmor("juggernaut_leggings", titan_mat, 2, 2);
        juggernaut_leggings.setCreativeTab(armor);

        juggernaut_boots = new ItemArmor("juggernaut_boots", titan_mat, 1, 3);
        juggernaut_boots.setCreativeTab(armor);

        rad_helmet = new ItemArmor("rad_helmet", special_costume_mat, 1, 0);
        rad_helmet.setCreativeTab(armor);

        rad_chestplate = new ItemArmor("rad_chestplate", special_costume_mat, 1, 1);
        rad_chestplate.setCreativeTab(armor);

        rad_leggings = new ItemArmor("rad_leggings", special_costume_mat, 2, 2);
        rad_leggings.setCreativeTab(armor);

        rad_boots = new ItemArmor("rad_boots", special_costume_mat, 1, 3);
        rad_boots.setCreativeTab(armor);

        indcostume_helmet = new ItemArmor("indcostume_helmet", special_costume2_mat, 1, 0);
        indcostume_helmet.setCreativeTab(armor);

        indcostume_chestplate = new ItemArmor("indcostume_chestplate", special_costume2_mat, 1, 1);
        indcostume_chestplate.setCreativeTab(armor);

        indcostume_leggings = new ItemArmor("indcostume_leggings", special_costume2_mat, 2, 2);
        indcostume_leggings.setCreativeTab(armor);

        indcostume_boots = new ItemArmor("indcostume_boots", special_costume2_mat, 1, 3);
        indcostume_boots.setCreativeTab(armor);

        aqualung_helmet = new ItemArmor("aqualung_helmet", special_costume3_mat, 1, 0);
        aqualung_helmet.setCreativeTab(armor);

        aqualung_chestplate = new ItemArmor("aqualung_chestplate", special_costume3_mat, 1, 1);
        aqualung_chestplate.setCreativeTab(armor);

        aqualung_leggings = new ItemArmor("aqualung_leggings", special_costume3_mat, 2, 2);
        aqualung_leggings.setCreativeTab(armor);

        aqualung_boots = new ItemArmor("aqualung_boots", special_costume3_mat, 1, 3);
        aqualung_boots.setCreativeTab(armor);

        chemicals1 = new Item().setUnlocalizedName("chemicals1");
        chemicals1.setCreativeTab(items);
        chemicals1.setMaxDurability(9);
        chemicals1.setMaxStackSize(1);

        chemicals1_a = new Item().setUnlocalizedName("chemicals1_a");
        chemicals1_a.setCreativeTab(items);
        chemicals1_a.setMaxStackSize(16);

        chemicals1_b = new Item().setUnlocalizedName("chemicals1_b");
        chemicals1_b.setCreativeTab(items);
        chemicals1_b.setMaxStackSize(16);

        chemicals1_c = new Item().setUnlocalizedName("chemicals1_c");
        chemicals1_c.setCreativeTab(items);
        chemicals1_c.setMaxDurability(9);
        chemicals1_c.setMaxStackSize(1);

        custom_repair = new Item().setUnlocalizedName("custom_repair");
        custom_repair.setCreativeTab(items);
        custom_repair.setMaxDurability(100);
        custom_repair.setMaxStackSize(1);

        repair = new Item().setUnlocalizedName("repair");
        repair.setCreativeTab(items);
        repair.setMaxDurability(100);
        repair.setMaxStackSize(1);

        scrap_material = new Item().setUnlocalizedName("scrap_material");
        scrap_material.setCreativeTab(items);
        scrap_material.setMaxStackSize(64);

        uran_material = new Item().setUnlocalizedName("uran_material");
        uran_material.setCreativeTab(items);
        uran_material.setMaxStackSize(1);

        m_scissors = new ItemMetalScissors().setUnlocalizedName("m_scissors");
        m_scissors.setCreativeTab(tools);
        m_scissors.setMaxDurability(16);
        m_scissors.setMaxStackSize(1);

        manyscrap = new Item().setUnlocalizedName("manyscrap");
        manyscrap.setCreativeTab(items);
        manyscrap.setMaxStackSize(16);

        acid = new ItemThrowable("acid", EntityAcid.class, 1.6f, 1.0f);
        acid.setCreativeTab(items);
        acid.setMaxStackSize(1);

        trap_grenade = new ItemThrowable("trap_grenade", EntityTrapGrenade.class, 1.6f, 1.0f);
        trap_grenade.setCreativeTab(items);
        trap_grenade.setMaxStackSize(1);

        frag_grenade = new ItemGrenade("frag_grenade", EntityGrenade.class, 1.6f, 1.0f);
        frag_grenade.setCreativeTab(items);
        frag_grenade.setMaxStackSize(1);

        smoke_grenade = new ItemGrenade("smoke_grenade", EntityGrenadeSmoke.class, 1.6f, 1.0f);
        smoke_grenade.setCreativeTab(items);
        smoke_grenade.setMaxStackSize(1);

        gas_grenade = new ItemGrenade("gas_grenade", EntityGrenadeGas.class, 1.6f, 1.0f);
        gas_grenade.setCreativeTab(items);
        gas_grenade.setMaxStackSize(1);

        holywater = new ItemThrowable("holywater", EntityHolywater.class, 1.6f, 1.0f);
        holywater.setCreativeTab(items);
        holywater.setMaxStackSize(1);

        tnt = new ItemGrenade("tnt", EntityTnt.class, 1.6f, 3.0f);
        tnt.setCreativeTab(items);
        tnt.setMaxStackSize(4);

        plate = new ItemThrowable("plate", EntityPlate.class, 1.6f, 1.0f);
        plate.setCreativeTab(items);
        plate.setMaxStackSize(16);

        plate_meat = new ItemThrowable("plate_meat", EntityPlateBait.class, 1.6f, 1.0f);
        plate_meat.setCreativeTab(items);
        plate_meat.setMaxStackSize(16);

        rock = new ItemThrowable("rock", EntityRock.class, 1.6f, 1.0f);
        rock.setCreativeTab(items);
        rock.setMaxStackSize(1);

        _scare = new Item().setUnlocalizedName("_scare");
        _scare.setCreativeTab(special);
        _scare.setMaxStackSize(32);

        _caramel = new Item().setUnlocalizedName("_caramel");
        _caramel.setCreativeTab(special);
        _caramel.setMaxStackSize(32);

        _9mm = new Item().setUnlocalizedName("_9mm");
        _9mm.setCreativeTab(ammo);
        _9mm.setMaxStackSize(32);

        _custom = new Item().setUnlocalizedName("_custom");
        _custom.setCreativeTab(ammo);
        _custom.setMaxStackSize(32);

        _custom2 = new Item().setUnlocalizedName("_custom2");
        _custom2.setCreativeTab(ammo);
        _custom2.setMaxStackSize(32);

        _22lr = new Item().setUnlocalizedName("_22lr");
        _22lr.setCreativeTab(ammo);
        _22lr.setMaxStackSize(32);

        _5_45x39 = new Item().setUnlocalizedName("_5_45x39");
        _5_45x39.setCreativeTab(ammo);
        _5_45x39.setMaxStackSize(32);

        _5_56x45 = new Item().setUnlocalizedName("_5_56x45");
        _5_56x45.setCreativeTab(ammo);
        _5_56x45.setMaxStackSize(32);

        _12 = new Item().setUnlocalizedName("_12");
        _12.setCreativeTab(ammo);
        _12.setMaxStackSize(32);

        _rocket = new Item().setUnlocalizedName("_rocket");
        _rocket.setCreativeTab(ammo);
        _rocket.setMaxStackSize(1);

        _javelin = new Item().setUnlocalizedName("_javelin");
        _javelin.setCreativeTab(fun);
        _javelin.setMaxStackSize(1);

        _igla = new Item().setUnlocalizedName("_igla");
        _igla.setCreativeTab(fun);
        _igla.setMaxStackSize(4);

        _flare = new Item().setUnlocalizedName("_flare");
        _flare.setCreativeTab(ammo);
        _flare.setMaxStackSize(1);

        _gauss = new Item().setUnlocalizedName("_gauss");
        _gauss.setCreativeTab(ammo);
        _gauss.setMaxStackSize(32);

        _grenade40mm = new Item().setUnlocalizedName("_grenade40mm");
        _grenade40mm.setCreativeTab(ammo);
        _grenade40mm.setMaxStackSize(1);

        _wog25 = new Item().setUnlocalizedName("_wog25");
        _wog25.setCreativeTab(ammo);
        _wog25.setMaxStackSize(1);

        _7_62x25 = new Item().setUnlocalizedName("_7_62x25");
        _7_62x25.setCreativeTab(ammo);
        _7_62x25.setMaxStackSize(32);

        _7_62x39 = new Item().setUnlocalizedName("_7_62x39");
        _7_62x39.setCreativeTab(ammo);
        _7_62x39.setMaxStackSize(32);

        _7_62x54R = new Item().setUnlocalizedName("_7_62x54R");
        _7_62x54R.setCreativeTab(ammo);
        _7_62x54R.setMaxStackSize(32);

        _9x39 = new Item().setUnlocalizedName("_9x39");
        _9x39.setCreativeTab(ammo);
        _9x39.setMaxStackSize(32);

        _45acp = new Item().setUnlocalizedName("_45acp");
        _45acp.setCreativeTab(ammo);
        _45acp.setMaxStackSize(32);

        _5_7x28 = new Item().setUnlocalizedName("_5_7x28");
        _5_7x28.setCreativeTab(ammo);
        _5_7x28.setMaxStackSize(32);

        _308win = new Item().setUnlocalizedName("_308win");
        _308win.setCreativeTab(ammo);
        _308win.setMaxStackSize(32);

        _357m = new Item().setUnlocalizedName("_357m");
        _357m.setCreativeTab(ammo);
        _357m.setMaxStackSize(32);

        _50bmg = new Item().setUnlocalizedName("_50bmg");
        _50bmg.setCreativeTab(ammo);
        _50bmg.setMaxStackSize(32);

        ACommonAmmo _scareAmmo = new ACommonAmmo(_scare, new Shell(1.0f, 0.8f, 0.4f, 0.26f), 8.0f);

        ACommonAmmo _caramelAmmo = new ACommonAmmo(_caramel, new Shell(1.0f, 1.0f, 1.0f, 0.26f), 8.0f);
        ACommonAmmo _9mmAmmo = new ACommonAmmo(_9mm, new Shell(1.0f, 0.9f, 0.5f, 0.26f), 4.0f);

        ACustomAmmo _customAmmo = new ACustomAmmo(_custom, new Shell(0.8f, 0.8f, 0.8f, 0.25f), 4.0f);

        ACustomAmmo _custom2Ammo = new ACustomAmmo(_custom2, new Shell(0.8f, 0.8f, 0.8f, 0.28f), 8.0f);

        ACommonAmmo _22lrAmmo = new ACommonAmmo(_22lr, new Shell(1.0f, 0.9f, 0.5f, 0.235f), 3.0f);

        ACommonAmmo _5_45x39Ammo = new ACommonAmmo(_5_45x39, new Shell(0.7f, 0.7f, 0.2f, 0.3f), 8.0f);

        ACommonAmmo _5_56x45Ammo = new ACommonAmmo(_5_56x45, new Shell(1.0f, 0.9f, 0.5f, 0.3f), 8.0f);

        ACommonAmmo _12Ammo = new ACommonAmmo(_12, new Shell(0.8f, 0.2f, 0.2f, 0.35f), 16.0f);

        ACommonAmmo _rocketAmmo = new ACommonAmmo(_rocket, null, 12.0f);

        ACommonAmmo _javelinAmmo = new ACommonAmmo(_javelin, null, 12.0f);

        ACommonAmmo _iglaAmmo = new ACommonAmmo(_igla, null, 12.0f);

        ACommonAmmo _flareAmmo = new ACommonAmmo(_flare, null, 2.0f);

        AGaussAmmo _gaussAmmo = new AGaussAmmo(_gauss, null, 3.0f);

        ACommonAmmo _grenade40mmAmmo = new ACommonAmmo(_grenade40mm, null, 12.0f);

        ACommonAmmo _arrow = new ACommonAmmo(Items.arrow, null, 1.5f);

        ACommonAmmo _wog25Ammo = new ACommonAmmo(_wog25, null, 12.0f);

        ACommonAmmo _7_62x25Ammo = new ACommonAmmo(_7_62x25, new Shell(0.7f, 0.7f, 0.2f, 0.26f), 8.0f);

        ACommonAmmo _7_62x39Ammo = new ACommonAmmo(_7_62x39, new Shell(0.8f, 0.8f, 0.8f, 0.3f), 8.0f);

        ACommonAmmo _7_62x54RAmmo = new ACommonAmmo(_7_62x54R, new Shell(0.6f, 0.4f, 0.2f, 0.3f), 12.0f);

        ACommonAmmo _9x39Ammo = new ACommonAmmo(_9x39, new Shell(0.8f, 0.8f, 0.8f, 0.3f), 12.0f);

        ACommonAmmo _45acpAmmo = new ACommonAmmo(_45acp, new Shell(1.0f, 0.9f, 0.5f, 0.28f), 5.0f);

        ACommonAmmo _5_7x28Ammo = new ACommonAmmo(_5_7x28, new Shell(0.8f, 0.8f, 0.8f, 0.25f), 6.0f);

        ACommonAmmo _308winAmmo = new ACommonAmmo(_308win, new Shell(1.0f, 0.9f, 0.5f, 0.35f), 16.0f);

        ACommonAmmo _357mAmmo = new ACommonAmmo(_357m, new Shell(1.0f, 0.9f, 0.5f, 0.3f), 12.0f);

        ACommonAmmo _50bmgAmmo = new ACommonAmmo(_50bmg, new Shell(1.0f, 0.9f, 0.5f, 0.4f), 32.0f);

        ASodaAmmo _burnAmmo = new ASodaAmmo(burn, null, 32.0f);

        laser = new ItemLaser("laser");
        laser.setInaccuracyModifier(-0.05f);
        laser.setCreativeTab(mods);

        angle_foregrip = new ItemForegrip("angle_foregrip");
        angle_foregrip.setRecoilHorizontalModifier(-0.1f);
        angle_foregrip.setRecoilVerticalModifier(-0.05f);
        angle_foregrip.setStabilityModifier(0.3f);
        angle_foregrip.setCreativeTab(mods);

        foregrip = new ItemForegrip("foregrip");
        foregrip.setRecoilHorizontalModifier(-0.05f);
        foregrip.setRecoilVerticalModifier(-0.1f);
        foregrip.setStabilityModifier(0.3f);
        foregrip.setCreativeTab(mods);

        m203 = new ItemM203("m203", "m79_s", "m79_r", 20, 50, _grenade40mmAmmo, 1);
        m203.setCreativeTab(mods);

        gp25 = new ItemGP25("gp25", "m79_s", "m79_r", 20, 50, _wog25Ammo, 1);
        gp25.setCreativeTab(mods);

        bipod = new ItemBipod("bipod");
        bipod.setRecoilHorizontalModifier(-0.2f);
        bipod.setRecoilVerticalModifier(-0.2f);
        bipod.setStabilityModifier(0.8f);
        bipod.setCreativeTab(mods);

        acog2x = new ItemScope("acog2x", "acogCross", 0.5f);
        acog2x.setInaccuracyModifier(-0.05f);
        acog2x.setCreativeTab(mods);

        scope_eotech = new ItemScope("scope_eotech", "null", -1);
        scope_eotech.setInaccuracyModifier(-0.12f);
        scope_eotech.setCreativeTab(mods);

        scope_kobra = new ItemScope("scope_kobra", "null", -1);
        scope_kobra.setInaccuracyModifier(-0.12f);
        scope_kobra.setCreativeTab(mods);

        scope_kashtan = new ItemScope("scope_kashtan", "psoCross", 0.5f);
        scope_kashtan.setInaccuracyModifier(-0.1f);
        scope_kashtan.setCreativeTab(mods);

        pistol_scope = new ItemScope("pistol_scope", "huntingCross", 0.55f);
        pistol_scope.setInaccuracyModifier(-0.05f);
        pistol_scope.setCreativeTab(mods);

        scope_pu = new ItemScope("scope_pu", "puCross", 0.6f);
        scope_pu.setInaccuracyModifier(-0.05f);
        scope_pu.setCreativeTab(mods);

        hunting_scope = new ItemScope("hunting_scope", "huntingCross", 0.7f);
        hunting_scope.setInaccuracyModifier(-0.08f);
        hunting_scope.setCreativeTab(mods);

        scope4x = new ItemScope("scope4x", "poCross", 0.65f);
        scope4x.setInaccuracyModifier(-0.08f);
        scope4x.setCreativeTab(mods);

        pso4x = new ItemScope("pso4x", "psoCross", 0.65f);
        pso4x.setInaccuracyModifier(-0.08f);
        pso4x.setCreativeTab(mods);

        scope6x = new ItemScope("scope6x", "poCross", 0.8f);
        scope6x.setInaccuracyModifier(-0.1f);
        scope6x.setCreativeTab(mods);

        pso6x = new ItemScope("pso6x", "psoCross", 0.8f);
        pso6x.setInaccuracyModifier(-0.1f);
        pso6x.setCreativeTab(mods);

        anPvs4 = new ItemNVScope("anPvs4", "anPvsCross", 0.6f);
        anPvs4.setInaccuracyModifier(-0.08f);
        anPvs4.setCreativeTab(mods);

        nspu = new ItemNVScope("nspu", "psoCross", 0.6f);
        nspu.setInaccuracyModifier(-0.08f);
        nspu.setCreativeTab(mods);

        flashSuppressor_pistol = new ItemFlashSuppressor("flashSuppressor_pistol");
        flashSuppressor_pistol.setRecoilHorizontalModifier(-0.06f);
        flashSuppressor_pistol.setCreativeTab(mods);

        flashSuppressor_rifle = new ItemFlashSuppressor("flashSuppressor_rifle");
        flashSuppressor_rifle.setRecoilHorizontalModifier(-0.06f);
        flashSuppressor_rifle.setCreativeTab(mods);

        muzzlebrake_rifle = new ItemMuzzleBrake("muzzlebrake_rifle");
        muzzlebrake_rifle.setRecoilVerticalModifier(-0.1f);
        muzzlebrake_rifle.setRecoilHorizontalModifier(-0.1f);
        muzzlebrake_rifle.setDistanceModifier(-0.1f);
        muzzlebrake_rifle.setCreativeTab(mods);

        muzzlebrake_pistol = new ItemMuzzleBrake("muzzlebrake_pistol");
        muzzlebrake_pistol.setRecoilVerticalModifier(-0.1f);
        muzzlebrake_pistol.setRecoilHorizontalModifier(-0.1f);
        muzzlebrake_pistol.setDistanceModifier(-0.1f);
        muzzlebrake_pistol.setCreativeTab(mods);

        silencer_rifle = new ItemSilencer("silencer_rifle");
        silencer_rifle.setRecoilVerticalModifier(-0.05f);
        silencer_rifle.setRecoilHorizontalModifier(0.05f);
        silencer_rifle.setInaccuracyModifier(-0.05f);
        silencer_rifle.setCreativeTab(mods);

        silencer_pistol = new ItemSilencer("silencer_pistol");
        silencer_pistol.setRecoilVerticalModifier(-0.05f);
        silencer_pistol.setRecoilHorizontalModifier(0.05f);
        silencer_pistol.setInaccuracyModifier(-0.05f);
        silencer_pistol.setCreativeTab(mods);

        _customRocket = new Item() {
            @SideOnly(Side.CLIENT)
            @SuppressWarnings("unchecked")
            public void addInformation(ItemStack stack, EntityPlayer playerIn, List tooltip, boolean advanced) {
                tooltip.add(EnumChatFormatting.GRAY + I18n.format("misc.dispenser"));
            }
        };
        _customRocket.setUnlocalizedName("_customRocket");
        _customRocket.setCreativeTab(items);
        _customRocket.setMaxStackSize(4);


        rpg28 = new ItemRpg("rpg28", "rpg_s", "rpg_r", 1, 100, 20, 6.0f, 6.0f, 1.5f, 0, 0.0f, false, false, _rocketAmmo);
        rpg28.setMaxDurability(15);

        javelin = new ItemJavelin("javelin", "rpg_s", "rpg_r", 1, 100, 20, 6.0f, 6.0f, 1.5f, 0, 0.0f, false, false, _javelinAmmo);
        javelin.setCreativeTab(fun);
        javelin.setMaxDurability(15);

        igla = new ItemIgla("igla", "rpg_s", "rpg_r", 1, 100, 20, 6.0f, 6.0f, 1.5f, 0, 0.0f, false, false, _iglaAmmo);
        igla.setCreativeTab(fun);
        igla.setMaxDurability(15);

        m79 = new ItemM79("m79", "m79_s", "m79_r", 1, 100, 20, 6.0f, 6.0f, 2.5f, 0.5f, 0.0f, false, false, _grenade40mmAmmo);
        m79.setMaxDurability(15);

        deagle = new ItemPistol("deagle", "deagle_s", "deagle_r", 9, 90, 48, 6, 202, 142, 5.0f, 2.5f, 3.0f, 1.0f, 0.25f, 2.0f, 0.35f, true, false, _357mAmmo);
        deagle.addModule(new ModuleInfo(ItemsZp.laser, 3, 5));
        deagle.addModule(new ModuleInfo(ItemsZp.muzzlebrake_pistol, 7, 11));
        deagle.addModule(new ModuleInfo(ItemsZp.flashSuppressor_pistol, 7, 11));
        deagle.setMaxDurability(145);

        deagle_gold = new ItemPistol("deagle_gold", "deagle_s", "deagle_r", 9, 90, 48, 6, 202, 142, 5.0f, 2.5f, 3.0f, 1.0f, 0.25f, 2.0f, 0.35f, true, false, _357mAmmo);
        deagle_gold.addModule(new ModuleInfo(ItemsZp.laser, 3, 5));
        deagle_gold.addModule(new ModuleInfo(ItemsZp.muzzlebrake_pistol, 7, 11));
        deagle_gold.addModule(new ModuleInfo(ItemsZp.flashSuppressor_pistol, 7, 11));
        deagle_gold.setMaxDurability(290);

        flare = new ItemFlare("flare", "flare_s", "flare_r", 1, 50, 20, 3.0f, 3.0f, 3.0f, 1.0f, 0.0f, false, false, _flareAmmo);
        flare.setMaxDurability(20);

        tt = new ItemPistol("tt", "tt_s", "tt_r", 8, 70, 28, 5, 132, 86, 3.2f, 1.0f, 4.0f, 2.0f, 0.3f, 2.0f, 0.4f, true, false, _7_62x25Ammo);
        tt.addModule(new ModuleInfo(ItemsZp.flashSuppressor_pistol, 7, 11));
        tt.addModule(new ModuleInfo(ItemsZp.muzzlebrake_pistol, 7, 11));
        tt.setMaxDurability(170);

        m1911 = new ItemPistol("m1911", "m1911_s", "m1911_r", 7, 80, 32, 5, 124, 64, 3.0f, 1.0f, 3.5f, 1.5f, 0.2f, 1.8f, 0.4f, true, false, _45acpAmmo);
        m1911.addModule(new ModuleInfo(ItemsZp.flashSuppressor_pistol, 7, 11));
        m1911.addModule(new ModuleInfo(ItemsZp.muzzlebrake_pistol, 7, 11));
        m1911.setMaxDurability(160);

        glock18 = new ItemPistol("glock18", "glock_s", "glock_r", 17, 60, 26, 3, 136, 96, 2.5f, 0.75f, 3.5f, 1.5f, 0.15f, 1.5f, 0.25f, true, true, _9mmAmmo);
        glock18.addModule(new ModuleInfo(ItemsZp.laser, 3, 5));
        glock18.addModule(new ModuleInfo(ItemsZp.flashSuppressor_pistol, 7, 11));
        glock18.addModule(new ModuleInfo(ItemsZp.muzzlebrake_pistol, 7, 11));
        glock18.addModule(new ModuleInfo(ItemsZp.silencer_pistol, 7, 11));
        glock18.setMaxDurability(180);

        python = new ItemRevolver("python", "revolver_s", "reload_r", 6, 15, 45, 5, 224, 164, 4.0f, 1.0f, 2.5f, 0.5f, 0.25f, 2.0f, 0.0f, false, false, _357mAmmo);
        python.addModule(new ModuleInfo(ItemsZp.pistol_scope, -3, 6));
        python.setMaxDurability(160);

        m1894 = new ItemSniperRifleNomag("m1894", "m1894_s", "sniper_r", 9, 18, 50, 20, 256, 224, 5.5f, 1.2f, 3.5f, 0.75f, 0.75f, 3.0f, 0.4f, true, false, _357mAmmo);
        m1894.enableShutterAnim("sniper_r");
        m1894.setMaxDurability(160);

        custom_pistol = new ItemRevolver("custom_pistol", "custom_pistol_s", "reload_r", 1, 18, 40, 5, 92, 42, 4.0f, 4.0f, 3.0f, 2.0f, 1.0f, 1.0f, 1.0f, false, false, _customAmmo) {
            @Override
            public Item repairItem() {
                return ItemsZp.custom_repair;
            }
        };
        custom_pistol.setMaxDurability(60);

        custom_revolver = new ItemRevolver("custom_revolver", "custom_pistol_s", "reload_r", 4, 22, 28, 5, 86, 32, 3.5f, 3.5f, 3.25f, 2.25f, 1.5f, 0.5f, 1.0f, false, false, _customAmmo) {
            @Override
            public Item repairItem() {
                return ItemsZp.custom_repair;
            }
        };
        custom_revolver.setMaxDurability(100);

        custom_sniper = new ItemSniperRifleNomag("custom_sniper", "custom_sniper_s", "reload_r", 1, 30, 50, 2, 182, 148, 5.0f, 5.0f, 3.5f, 2.0f, 2.0f, 2.0f, 1.0f, false, false, _custom2Ammo) {
            @Override
            public Item repairItem() {
                return ItemsZp.custom_repair;
            }
        };
        custom_sniper.addModule(new ModuleInfo(ItemsZp.scope_pu, 0, 3));
        custom_sniper.setMaxDurability(72);

        custom_rifle = new ItemRifle("custom_rifle", "custom_rifle_s", "custom_rifle_r", 20, 60, 10, 2, 112, 68, 2.0f, 1.0f, 4.0f, 2.5f, 0.15f, 2.0f, 0.4f, true, true, _customAmmo) {
            @Override
            public Item repairItem() {
                return ItemsZp.custom_repair;
            }
        };
        custom_rifle.setMaxDurability(148);

        fiveseven = new ItemPistol("fiveseven", "fiveseven_s", "fiveseven_r", 10, 50, 30, 3, 144, 112, 2.5f, 0.5f, 3.5f, 1.5f, 0.1f, 1.0f, 0.3f, true, false, _5_7x28Ammo);
        fiveseven.addModule(new ModuleInfo(ItemsZp.laser, 3, 5));
        fiveseven.addModule(new ModuleInfo(ItemsZp.flashSuppressor_pistol, 7, 11));
        fiveseven.addModule(new ModuleInfo(ItemsZp.muzzlebrake_pistol, 7, 11));
        fiveseven.addModule(new ModuleInfo(ItemsZp.silencer_pistol, 7, 11));
        fiveseven.setMaxDurability(130);

        pm = new ItemPistol("pm", "pm_s", "pm_r", 8, 60, 20, 4, 122, 92, 3.0f, 0.6f, 4.0f, 2.0f, 0.2f, 1.6f, 0.35f, true, false, _9mmAmmo);
        pm.addModule(new ModuleInfo(ItemsZp.flashSuppressor_pistol, 7, 11));
        pm.addModule(new ModuleInfo(ItemsZp.silencer_pistol, 7, 11));
        pm.setMaxDurability(150);

        walther = new ItemPistol("walther", "walther_s", "walther_r", 10, 60, 10, 3, 64, 32, 1.5f, 0.1f, 5.0f, 3.0f, 0.05f, 0.5f, 0.3f, true, false, _22lrAmmo);
        walther.addModule(new ModuleInfo(ItemsZp.flashSuppressor_pistol, 7, 11));
        walther.setMaxDurability(120);

        mac10 = new ItemPistol("mac10", "mac10_s", "mac10_r", 20, 50, 16, 2, 112, 88, 2.5f, 0.5f, 4.5f, 2.5f, 0.15f, 3.0f, 0.3f, true, true, _45acpAmmo);
        mac10.addModule(new ModuleInfo(ItemsZp.flashSuppressor_pistol, 7, 11));
        mac10.addModule(new ModuleInfo(ItemsZp.muzzlebrake_pistol, 7, 11));
        mac10.addModule(new ModuleInfo(ItemsZp.silencer_pistol, 7, 11));
        mac10.setMaxDurability(180);

        mini_uzi = new ItemPistol("mini_uzi", "mini_uzi_s", "mini_uzi_r", 30, 60, 18, 2, 102, 76, 2.0f, 0.8f, 5.0f, 3.0f, 0.2f, 2.5f, 0.3f, true, true, _9mmAmmo);
        mini_uzi.addModule(new ModuleInfo(ItemsZp.laser, 2, 6));
        mini_uzi.addModule(new ModuleInfo(ItemsZp.silencer_pistol, 7, 11));
        mini_uzi.setMaxDurability(200);

        casull290 = new ItemRifle("casull290", "casull290_s", "casull290_r", 290, 120, 2, 1, 64, 24, 1.6f, 0.4f, 10.0f, 6.0f, 0.1f, 12.0f, 0.15f, true, true, _22lrAmmo);
        casull290.setMaxDurability(290);

        scare_gun = new ItemRifleHoliday("scare_gun", "scare_gun_s", "scare_gun_r", 30, 60, 20, 2, 240, 186, 1.6f, 0.2f, 2.5f, 0.5f, 0.07f, 1.5f, 0.15f, true, true, _scareAmmo) {
            @SideOnly(Side.CLIENT)
            @SuppressWarnings("unchecked")
            public void addInformation(ItemStack stack, EntityPlayer playerIn, List tooltip, boolean advanced) {
                tooltip.add(EnumChatFormatting.RED + I18n.format("misc.scare"));
            }
        };
        scare_gun.addModule(new ModuleInfo(ItemsZp.laser, 6, 6));
        scare_gun.addModule(new ModuleInfo(ItemsZp.flashSuppressor_rifle, 11, 11));
        scare_gun.addModule(new ModuleInfo(ItemsZp.muzzlebrake_rifle, 11, 11));
        scare_gun.addModule(new ModuleInfo(ItemsZp.silencer_rifle, 11, 11));
        scare_gun.setCreativeTab(special);
        scare_gun.setMaxDurability(390);

        caramel_gun = new ItemRifleHoliday("caramel_gun", "caramel_gun_s", "caramel_gun_r", 30, 60, 20, 2, 240, 186, 1.6f, 0.2f, 2.5f, 0.5f, 0.07f, 1.5f, 0.15f, true, true, _caramelAmmo) {
            @SideOnly(Side.CLIENT)
            @SuppressWarnings("unchecked")
            public void addInformation(ItemStack stack, EntityPlayer playerIn, List tooltip, boolean advanced) {
                tooltip.add(EnumChatFormatting.AQUA + I18n.format("misc.newyear"));
            }
        };
        caramel_gun.addModule(new ModuleInfo(ItemsZp.laser, 6, 6));
        caramel_gun.addModule(new ModuleInfo(ItemsZp.flashSuppressor_rifle, 11, 11));
        caramel_gun.addModule(new ModuleInfo(ItemsZp.muzzlebrake_rifle, 11, 11));
        caramel_gun.addModule(new ModuleInfo(ItemsZp.silencer_rifle, 11, 11));
        caramel_gun.setCreativeTab(special);
        caramel_gun.setMaxDurability(390);

        akm = new ItemRifle("akm", "akm_s", "akm_r", 30, 90, 22, 2, 238, 186, 1.85f, 0.3f, 3.5f, 1.5f, 0.075f, 2.0f, 0.25f, true, true, _7_62x39Ammo);
        akm.addModule(new ModuleInfo(ItemsZp.scope_kobra, -2, 2));
        akm.addModule(new ModuleInfo(ItemsZp.scope_kashtan, -2, 2));
        akm.addModule(new ModuleInfo(ItemsZp.pso4x, -2, 2));
        akm.addModule(new ModuleInfo(ItemsZp.pso6x, -2, 2));
        akm.addModule(new ModuleInfo(ItemsZp.nspu, -2, 2));
        akm.addModule(new ModuleInfo(ItemsZp.flashSuppressor_rifle, 11, 11));
        akm.addModule(new ModuleInfo(ItemsZp.muzzlebrake_rifle, 11, 11));
        akm.addModule(new ModuleInfo(ItemsZp.gp25, 5, 1));
        akm.setMaxDurability(230);

        ak12 = new ItemRifle("ak12", "ak12_s", "ak12_r", 30, 70, 20, 2, 256, 200, 1.7f, 0.25f, 3.0f, 1.0f, 0.06f, 1.5f, 0.2f, true, true, _5_45x39Ammo);
        ak12.addModule(new ModuleInfo(ItemsZp.laser, 6, 6));
        ak12.addModule(new ModuleInfo(ItemsZp.foregrip, 5, 1));
        ak12.addModule(new ModuleInfo(ItemsZp.angle_foregrip, 5, 1));
        ak12.addModule(new ModuleInfo(ItemsZp.scope_eotech, -2, 1));
        ak12.addModule(new ModuleInfo(ItemsZp.scope_kobra, -2, 2));
        ak12.addModule(new ModuleInfo(ItemsZp.acog2x, -2, 1));
        ak12.addModule(new ModuleInfo(ItemsZp.scope_kashtan, -2, 2));
        ak12.addModule(new ModuleInfo(ItemsZp.scope4x, -2, 1));
        ak12.addModule(new ModuleInfo(ItemsZp.scope6x, -2, 1));
        ak12.addModule(new ModuleInfo(ItemsZp.anPvs4, -2, 1));
        ak12.addModule(new ModuleInfo(ItemsZp.pso4x, -2, 2));
        ak12.addModule(new ModuleInfo(ItemsZp.pso6x, -2, 2));
        ak12.addModule(new ModuleInfo(ItemsZp.nspu, -2, 2));
        ak12.addModule(new ModuleInfo(ItemsZp.flashSuppressor_rifle, 11, 11));
        ak12.addModule(new ModuleInfo(ItemsZp.muzzlebrake_rifle, 11, 11));
        ak12.addModule(new ModuleInfo(ItemsZp.silencer_rifle, 11, 11));
        ak12.addModule(new ModuleInfo(ItemsZp.gp25, 5, 1));
        ak12.setMaxDurability(275);

        m16a1 = new ItemRifle("m16a1", "m16_s", "m16_r", 20, 90, 22, 2, 256, 176, 1.6f, 0.3f, 2.75f, 0.75f, 0.08f, 3.0f, 0.25f, true, true, _5_56x45Ammo);
        m16a1.addModule(new ModuleInfo(ItemsZp.scope_eotech, -3, 0));
        m16a1.addModule(new ModuleInfo(ItemsZp.acog2x, -3, 0));
        m16a1.addModule(new ModuleInfo(ItemsZp.scope4x, -3, 0));
        m16a1.addModule(new ModuleInfo(ItemsZp.scope6x, -3, 0));
        m16a1.addModule(new ModuleInfo(ItemsZp.anPvs4, -3, 0));
        m16a1.addModule(new ModuleInfo(ItemsZp.flashSuppressor_rifle, 11, 11));
        m16a1.addModule(new ModuleInfo(ItemsZp.muzzlebrake_rifle, 11, 11));
        m16a1.addModule(new ModuleInfo(ItemsZp.m203, 5, 1));
        m16a1.setMaxDurability(230);

        m4a1 = new ItemRifle("m4a1", "m4_s", "m4_r", 30, 80, 20, 2, 236, 200, 1.75f, 0.2f, 3.0f, 1.0f, 0.06f, 1.75f, 0.2f, true, true, _5_56x45Ammo);
        m4a1.addModule(new ModuleInfo(ItemsZp.laser, 6, 6));
        m4a1.addModule(new ModuleInfo(ItemsZp.foregrip, 5, 1));
        m4a1.addModule(new ModuleInfo(ItemsZp.angle_foregrip, 5, 1));
        m4a1.addModule(new ModuleInfo(ItemsZp.scope_eotech, -2, 1));
        m4a1.addModule(new ModuleInfo(ItemsZp.acog2x, -2, 1));
        m4a1.addModule(new ModuleInfo(ItemsZp.scope4x, -2, 1));
        m4a1.addModule(new ModuleInfo(ItemsZp.scope6x, -2, 1));
        m4a1.addModule(new ModuleInfo(ItemsZp.anPvs4, -2, 1));
        m4a1.addModule(new ModuleInfo(ItemsZp.flashSuppressor_rifle, 11, 11));
        m4a1.addModule(new ModuleInfo(ItemsZp.muzzlebrake_rifle, 11, 11));
        m4a1.addModule(new ModuleInfo(ItemsZp.silencer_rifle, 11, 11));
        m4a1.addModule(new ModuleInfo(ItemsZp.m203, 5, 1));
        m4a1.setMaxDurability(275);

        aksu = new ItemRifle("aksu", "aksu_s", "aksu_r", 30, 60, 18, 2, 212, 152, 1.9f, 0.4f, 4.0f, 2.0f, 0.1f, 2.0f, 0.25f, true, true, _5_45x39Ammo);
        aksu.addModule(new ModuleInfo(ItemsZp.flashSuppressor_rifle, 11, 11));
        aksu.addModule(new ModuleInfo(ItemsZp.muzzlebrake_rifle, 11, 11));
        aksu.addModule(new ModuleInfo(ItemsZp.silencer_rifle, 11, 11));
        aksu.setMaxDurability(250);

        vss = new ItemRifleSilenced("vss", "silenced_s", "vss_r", 20, 90, 24, 3, 256, 222, 1.7f, 0.3f, 2.5f, 0.5f, 0.1f, 1.0f, 0.2f, true, true, _9x39Ammo);
        vss.addModule(new ModuleInfo(ItemsZp.laser, 6, 6));
        vss.addModule(new ModuleInfo(ItemsZp.foregrip, 5, 1));
        vss.addModule(new ModuleInfo(ItemsZp.angle_foregrip, 5, 1));
        vss.addModule(new ModuleInfo(ItemsZp.bipod, 5, 1));
        vss.addModule(new ModuleInfo(ItemsZp.scope_kobra, -2, 2));
        vss.addModule(new ModuleInfo(ItemsZp.scope_kashtan, -2, 2));
        vss.addModule(new ModuleInfo(ItemsZp.pso4x, -2, 1));
        vss.addModule(new ModuleInfo(ItemsZp.pso6x, -2, 1));
        vss.addModule(new ModuleInfo(ItemsZp.nspu, -2, 1));
        vss.setMaxDurability(210);

        oc14 = new ItemRifle("oc14", "oc14_s", "oc14_r", 20, 100, 26, 2, 226, 186, 1.7f, 0.3f, 3.0f, 1.0f, 0.1f, 2.0f, 0.2f, true, true, _9x39Ammo);
        oc14.setRenderType(AGunBase.GunType.RIFLE2);
        oc14.addModule(new ModuleInfo(ItemsZp.foregrip, 7, 2));
        oc14.addModule(new ModuleInfo(ItemsZp.angle_foregrip, 7, 3));
        oc14.addModule(new ModuleInfo(ItemsZp.gp25, 6, 2));
        oc14.addModule(new ModuleInfo(ItemsZp.flashSuppressor_rifle, 11, 11));
        oc14.addModule(new ModuleInfo(ItemsZp.muzzlebrake_rifle, 11, 11));
        oc14.addModule(new ModuleInfo(ItemsZp.silencer_rifle, 11, 11));
        oc14.setMaxDurability(200);

        g36k = new ItemRifle("g36k", "g36k_s", "g36k_r", 30, 60, 20, 2, 220, 164, 1.7f, 0.2f, 3.25f, 1.25f, 0.06f, 1.3f, 0.2f, true, true, _5_56x45Ammo);
        g36k.addModule(new ModuleInfo(ItemsZp.laser, 6, 6));
        g36k.addModule(new ModuleInfo(ItemsZp.foregrip, 5, 1));
        g36k.addModule(new ModuleInfo(ItemsZp.angle_foregrip, 5, 1));
        g36k.addModule(new ModuleInfo(ItemsZp.scope_eotech, -3, 2));
        g36k.addModule(new ModuleInfo(ItemsZp.acog2x, -3, 2));
        g36k.addModule(new ModuleInfo(ItemsZp.scope4x, -3, 2));
        g36k.addModule(new ModuleInfo(ItemsZp.scope6x, -3, 2));
        g36k.addModule(new ModuleInfo(ItemsZp.anPvs4, -3, 2));
        g36k.addModule(new ModuleInfo(ItemsZp.flashSuppressor_rifle, 11, 11));
        g36k.addModule(new ModuleInfo(ItemsZp.muzzlebrake_rifle, 11, 11));
        g36k.addModule(new ModuleInfo(ItemsZp.silencer_rifle, 11, 11));
        g36k.setMaxDurability(260);

        aug = new ItemRifle("aug", "aug_s", "aug_r", 30, 80, 20, 2, 236, 186, 1.9f, 0.3f, 2.8f, 0.8f, 0.1f, 2.5f, 0.2f, true, true, _5_56x45Ammo);
        aug.setRenderType(AGunBase.GunType.RIFLE2);
        aug.addModule(new ModuleInfo(ItemsZp.laser, 6, 6));
        aug.addModule(new ModuleInfo(ItemsZp.foregrip, 5, 0));
        aug.addModule(new ModuleInfo(ItemsZp.angle_foregrip, 5, 0));
        aug.addModule(new ModuleInfo(ItemsZp.acog2x, -3, 1));
        aug.addModule(new ModuleInfo(ItemsZp.scope4x, -3, 1));
        aug.addModule(new ModuleInfo(ItemsZp.scope6x, -3, 1));
        aug.addModule(new ModuleInfo(ItemsZp.anPvs4, -3, 1));
        aug.addModule(new ModuleInfo(ItemsZp.flashSuppressor_rifle, 11, 11));
        aug.addModule(new ModuleInfo(ItemsZp.muzzlebrake_rifle, 11, 11));
        aug.addModule(new ModuleInfo(ItemsZp.silencer_rifle, 11, 11));
        aug.setMaxDurability(280);

        bizon = new ItemRifle("bizon", "bizon_s", "bizon_r", 64, 80, 16, 3, 192, 136, 1.8f, 0.35f, 3.75f, 1.75f, 0.08f, 1.5f, 0.3f, true, true, _7_62x25Ammo);
        bizon.addModule(new ModuleInfo(ItemsZp.laser, 6, 6));
        bizon.addModule(new ModuleInfo(ItemsZp.scope_kobra, -2, 2));
        bizon.addModule(new ModuleInfo(ItemsZp.scope_kashtan, -2, 2));
        bizon.addModule(new ModuleInfo(ItemsZp.flashSuppressor_pistol, 11, 11));
        bizon.addModule(new ModuleInfo(ItemsZp.muzzlebrake_pistol, 11, 11));
        bizon.addModule(new ModuleInfo(ItemsZp.silencer_pistol, 11, 11));
        bizon.setMaxDurability(250);

        ump45 = new ItemRifle("ump45", "ump_s", "ump_r", 25, 60, 18, 3, 124, 96, 1.7f, 0.3f, 3.5f, 1.5f, 0.08f, 1.0f, 0.35f, true, true, _45acpAmmo);
        ump45.addModule(new ModuleInfo(ItemsZp.laser, 6, 6));
        ump45.addModule(new ModuleInfo(ItemsZp.foregrip, 5, 1));
        ump45.addModule(new ModuleInfo(ItemsZp.angle_foregrip, 5, 1));
        ump45.addModule(new ModuleInfo(ItemsZp.scope_eotech, -1, 2));
        ump45.addModule(new ModuleInfo(ItemsZp.acog2x, -1, 2));
        ump45.addModule(new ModuleInfo(ItemsZp.flashSuppressor_pistol, 11, 11));
        ump45.addModule(new ModuleInfo(ItemsZp.muzzlebrake_pistol, 11, 11));
        ump45.addModule(new ModuleInfo(ItemsZp.silencer_pistol, 11, 11));
        ump45.setMaxDurability(210);

        mp5 = new ItemRifle("mp5", "mp5_s", "mp5_r", 30, 70, 15, 2, 142, 112, 1.8f, 0.25f, 4.0f, 2.0f, 0.07f, 1.0f, 0.35f, true, true, _9mmAmmo);
        mp5.addModule(new ModuleInfo(ItemsZp.laser, 6, 6));
        mp5.addModule(new ModuleInfo(ItemsZp.angle_foregrip, 6, 1));
        mp5.addModule(new ModuleInfo(ItemsZp.scope_eotech, -1, 1));
        mp5.addModule(new ModuleInfo(ItemsZp.acog2x, -1, 1));
        mp5.addModule(new ModuleInfo(ItemsZp.flashSuppressor_pistol, 11, 11));
        mp5.addModule(new ModuleInfo(ItemsZp.muzzlebrake_pistol, 11, 11));
        mp5.addModule(new ModuleInfo(ItemsZp.silencer_pistol, 11, 11));
        mp5.addModule(new ModuleInfo(ItemsZp.m203, 6, 1));
        mp5.setMaxDurability(230);

        p90 = new ItemRifle("p90", "p90_s", "p90_r", 50, 90, 14, 2, 182, 124, 1.6f, 0.5f, 3.75f, 1.75f, 0.05f, 2.0f, 0.3f, true, true, _5_7x28Ammo);
        p90.setRenderType(AGunBase.GunType.RIFLE2);
        p90.addModule(new ModuleInfo(ItemsZp.laser, 6, 6));
        p90.addModule(new ModuleInfo(ItemsZp.scope_eotech, -1, 3));
        p90.addModule(new ModuleInfo(ItemsZp.acog2x, 0, 4));
        p90.addModule(new ModuleInfo(ItemsZp.flashSuppressor_pistol, 11, 11));
        p90.addModule(new ModuleInfo(ItemsZp.muzzlebrake_pistol, 11, 11));
        p90.addModule(new ModuleInfo(ItemsZp.silencer_pistol, 11, 11));
        p90.setMaxDurability(260);

        sporter = new ItemRifle("sporter", "sporter_s", "sporter_r", 20, 80, 12, 3, 156, 64, 2.0f, 0.2f, 4.0f, 2.0f, 0.2f, 4.0f, 0.3f, true, false, _22lrAmmo);
        sporter.addModule(new ModuleInfo(ItemsZp.flashSuppressor_rifle, 11, 11));
        sporter.addModule(new ModuleInfo(ItemsZp.muzzlebrake_rifle, 11, 11));
        sporter.addModule(new ModuleInfo(ItemsZp.hunting_scope, -2, 0));
        sporter.setMaxDurability(140);

        mini14 = new ItemRifle("mini14", "mini14_s", "mini14_r", 15, 70, 32, 6, 182, 134, 2.8f, 0.6f, 3.5f, 1.5f, 0.1f, 1.2f, 0.3f, true, false, _5_56x45Ammo);
        mini14.addModule(new ModuleInfo(ItemsZp.laser, 6, 6));
        mini14.addModule(new ModuleInfo(ItemsZp.scope_eotech, -2, 0));
        mini14.addModule(new ModuleInfo(ItemsZp.scope_pu, -2, 1));
        mini14.addModule(new ModuleInfo(ItemsZp.hunting_scope, -1, 1));
        mini14.addModule(new ModuleInfo(ItemsZp.flashSuppressor_rifle, 11, 11));
        mini14.addModule(new ModuleInfo(ItemsZp.muzzlebrake_rifle, 11, 11));
        mini14.setMaxDurability(200);

        scout = new ItemRifle("scout", "scout_s", "scout_r", 10, 60, 45, 14, 224, 152, 3.0f, 0.5f, 3.5f, 1.5f, 0.2f, 1.0f, 0.3f, true, false, _5_56x45Ammo);
        scout.enableShutterAnim("sniper_r");
        scout.addModule(new ModuleInfo(ItemsZp.laser, 6, 6));
        scout.addModule(new ModuleInfo(ItemsZp.bipod, 5, 0));
        scout.addModule(new ModuleInfo(ItemsZp.scope_eotech, -2, 0));
        scout.addModule(new ModuleInfo(ItemsZp.acog2x, -2, 0));
        scout.addModule(new ModuleInfo(ItemsZp.scope4x, -2, 0));
        scout.addModule(new ModuleInfo(ItemsZp.scope6x, -2, 0));
        scout.addModule(new ModuleInfo(ItemsZp.anPvs4, -2, 0));
        scout.addModule(new ModuleInfo(ItemsZp.flashSuppressor_rifle, 11, 11));
        scout.addModule(new ModuleInfo(ItemsZp.muzzlebrake_rifle, 11, 11));
        scout.addModule(new ModuleInfo(ItemsZp.silencer_rifle, 11, 11));
        scout.setMaxDurability(180);

        crossbow = new ItemCrossbow("crossbow", "crossbow_s", "crossbow_r", 40, 10, 1.0f, 1.0f, _arrow) {
            @Override
            public Item repairItem() {
                return ItemsZp.custom_repair;
            }
        };
        crossbow.setMaxDurability(164);

        gauss = new ItemGauss("gauss", "gauss_s", "gauss_r", 5, 100, 60, 20, 128, 64, 1.0f, 0.0f, 1.75f, 0.75f, 0.2f, 1.0f, 0.0f, false, false, _gaussAmmo) {
            @Override
            public Item repairItem() {
                return ItemsZp.battery;
            }
        };
        gauss.addModule(new ModuleInfo(ItemsZp.laser, 6, 6));
        gauss.setMaxDurability(50);

        rpk = new ItemRifle("rpk", "rpk_s", "rpk_r", 60, 130, 18, 2, 232, 202, 2.0f, 0.6f, 3.0f, 1.5f, 0.03f, 6.0f, 0.2f, true, true, _5_45x39Ammo);
        rpk.addModule(new ModuleInfo(ItemsZp.muzzlebrake_rifle, 11, 11));
        rpk.addModule(new ModuleInfo(ItemsZp.flashSuppressor_rifle, 11, 11));
        rpk.addModule(new ModuleInfo(ItemsZp.scope_kobra, -2, 2));
        rpk.addModule(new ModuleInfo(ItemsZp.scope_kashtan, -2, 2));
        rpk.addModule(new ModuleInfo(ItemsZp.pso4x, -2, 2));
        rpk.addModule(new ModuleInfo(ItemsZp.pso6x, -2, 2));
        rpk.addModule(new ModuleInfo(ItemsZp.nspu, -2, 2));
        rpk.addModule(new ModuleInfo(ItemsZp.bipod, 7, 3));
        rpk.setMaxDurability(240);

        pkm = new ItemRifle("pkm", "pkm_s", "pkm_r", 80, 130, 20, 3, 220, 180, 2.2f, 0.5f, 3.75f, 1.75f, 0.05f, 6.0f, 0.2f, true, true, _7_62x39Ammo);
        pkm.addModule(new ModuleInfo(ItemsZp.scope_kobra, -2, 2));
        pkm.addModule(new ModuleInfo(ItemsZp.scope_kashtan, -3, 2));
        pkm.addModule(new ModuleInfo(ItemsZp.pso4x, -3, 2));
        pkm.addModule(new ModuleInfo(ItemsZp.pso6x, -3, 2));
        pkm.addModule(new ModuleInfo(ItemsZp.nspu, -2, 2));
        pkm.addModule(new ModuleInfo(ItemsZp.bipod, 7, 3));
        pkm.setMaxDurability(250);

        m249 = new ItemRifle("m249", "m249_s", "m249_r", 100, 100, 16, 2, 220, 156, 2.0f, 0.6f, 4.5f, 2.5f, 0.025f, 6.0f, 0.2f, true, true, _5_56x45Ammo);
        m249.addModule(new ModuleInfo(ItemsZp.acog2x, -2, 1));
        m249.addModule(new ModuleInfo(ItemsZp.scope4x, -2, 1));
        m249.addModule(new ModuleInfo(ItemsZp.scope6x, -2, 1));
        m249.addModule(new ModuleInfo(ItemsZp.anPvs4, -2, 1));
        m249.addModule(new ModuleInfo(ItemsZp.muzzlebrake_rifle, 11, 11));
        m249.addModule(new ModuleInfo(ItemsZp.flashSuppressor_rifle, 11, 11));
        m249.addModule(new ModuleInfo(ItemsZp.bipod, 7, 3));
        m249.setMaxDurability(280);

        mp40 = new ItemRifle("mp40", "mp40_s", "mp40_r", 32, 50, 16, 2, 112, 92, 2.2f, 0.45f, 4.5f, 2.5f, 0.06f, 1.5f, 0.4f, true, true, _9mmAmmo);
        mp40.setMaxDurability(210);

        ppsh = new ItemRifle("ppsh", "ppsh_s", "ppsh_r", 71, 90, 16, 2, 110, 84, 2.2f, 0.45f, 6.0f, 4.0f, 0.03f, 2.0f, 0.4f, true, true, _7_62x25Ammo);
        ppsh.setMaxDurability(210);

        ar15 = new ItemRifle("ar15", "ar15_s", "ar15_r", 25, 60, 28, 3, 246, 192, 2.2f, 0.5f, 3.0f, 1.0f, 0.2f, 2.25f, 0.4f, true, false, _5_56x45Ammo);
        ar15.addModule(new ModuleInfo(ItemsZp.laser, 6, 6));
        ar15.addModule(new ModuleInfo(ItemsZp.bipod, 5, 1));
        ar15.addModule(new ModuleInfo(ItemsZp.foregrip, 5, 1));
        ar15.addModule(new ModuleInfo(ItemsZp.angle_foregrip, 5, 1));
        ar15.addModule(new ModuleInfo(ItemsZp.scope_eotech, -2, 1));
        ar15.addModule(new ModuleInfo(ItemsZp.acog2x, -2, 1));
        ar15.addModule(new ModuleInfo(ItemsZp.scope4x, -2, 1));
        ar15.addModule(new ModuleInfo(ItemsZp.scope6x, -2, 1));
        ar15.addModule(new ModuleInfo(ItemsZp.anPvs4, -2, 1));
        ar15.addModule(new ModuleInfo(ItemsZp.flashSuppressor_rifle, 11, 11));
        ar15.addModule(new ModuleInfo(ItemsZp.muzzlebrake_rifle, 11, 11));
        ar15.addModule(new ModuleInfo(ItemsZp.silencer_rifle, 11, 11));
        ar15.setMaxDurability(200);

        sg550 = new ItemRifle("sg550", "sg550_s", "sg550_r", 20, 80, 32, 6, 256, 186, 2.2f, 0.5f, 2.75f, 0.75f, 0.25f, 2.0f, 0.35f, true, true, _5_56x45Ammo);
        sg550.addModule(new ModuleInfo(ItemsZp.laser, 6, 6));
        sg550.addModule(new ModuleInfo(ItemsZp.bipod, 5, 1));
        sg550.addModule(new ModuleInfo(ItemsZp.foregrip, 5, 1));
        sg550.addModule(new ModuleInfo(ItemsZp.angle_foregrip, 5, 1));
        sg550.addModule(new ModuleInfo(ItemsZp.scope_eotech, -2, 1));
        sg550.addModule(new ModuleInfo(ItemsZp.acog2x, -1, 2));
        sg550.addModule(new ModuleInfo(ItemsZp.scope4x, -1, 2));
        sg550.addModule(new ModuleInfo(ItemsZp.scope6x, -1, 2));
        sg550.addModule(new ModuleInfo(ItemsZp.anPvs4, -1, 2));
        sg550.addModule(new ModuleInfo(ItemsZp.flashSuppressor_rifle, 11, 11));
        sg550.addModule(new ModuleInfo(ItemsZp.muzzlebrake_rifle, 11, 11));
        sg550.addModule(new ModuleInfo(ItemsZp.silencer_rifle, 11, 11));
        sg550.setMaxDurability(180);

        fal = new ItemRifle("fal", "fal_s", "fal_r", 20, 90, 36, 4, 256, 176, 3.2f, 0.6f, 3.5f, 1.5f, 0.35f, 2.0f, 0.35f, true, true, _308winAmmo);
        fal.addModule(new ModuleInfo(ItemsZp.laser, 6, 6));
        fal.addModule(new ModuleInfo(ItemsZp.bipod, 5, 1));
        fal.addModule(new ModuleInfo(ItemsZp.scope_eotech, -2, 1));
        fal.addModule(new ModuleInfo(ItemsZp.acog2x, -2, 1));
        fal.addModule(new ModuleInfo(ItemsZp.scope4x, -2, 1));
        fal.addModule(new ModuleInfo(ItemsZp.scope6x, -2, 1));
        fal.addModule(new ModuleInfo(ItemsZp.anPvs4, -2, 1));
        fal.addModule(new ModuleInfo(ItemsZp.flashSuppressor_rifle, 11, 11));
        fal.addModule(new ModuleInfo(ItemsZp.muzzlebrake_rifle, 11, 11));
        fal.addModule(new ModuleInfo(ItemsZp.silencer_rifle, 11, 11));
        fal.setMaxDurability(200);

        sks = new ItemRifle("sks", "sks_s", "sks_r", 10, 70, 55, 5, 256, 192, 3.8f, 1.0f, 3.0f, 1.0f, 0.25f, 2.5f, 0.35f, true, false, _7_62x39Ammo);
        sks.addModule(new ModuleInfo(ItemsZp.bipod, 5, 1));
        sks.addModule(new ModuleInfo(ItemsZp.scope_kobra, -3, 1));
        sks.addModule(new ModuleInfo(ItemsZp.scope_kashtan, -3, 1));
        sks.addModule(new ModuleInfo(ItemsZp.pso4x, -3, 1));
        sks.addModule(new ModuleInfo(ItemsZp.pso6x, -3, 1));
        sks.addModule(new ModuleInfo(ItemsZp.nspu, -3, 1));
        sks.addModule(new ModuleInfo(ItemsZp.flashSuppressor_rifle, 11, 11));
        sks.addModule(new ModuleInfo(ItemsZp.muzzlebrake_rifle, 11, 11));
        sks.setMaxDurability(200);

        svd = new ItemRifle("svd", "svd_s", "svd_r", 10, 80, 66, 10, 256, 216, 5.0f, 1.5f, 4.0f, 0.5f, 0.4f, 2.5f, 0.3f, true, false, _7_62x54RAmmo);
        svd.addModule(new ModuleInfo(ItemsZp.laser, 6, 6));
        svd.addModule(new ModuleInfo(ItemsZp.bipod, 5, 1));
        svd.addModule(new ModuleInfo(ItemsZp.scope_kashtan, -3, 1));
        svd.addModule(new ModuleInfo(ItemsZp.pso4x, -3, 1));
        svd.addModule(new ModuleInfo(ItemsZp.pso6x, -3, 1));
        svd.addModule(new ModuleInfo(ItemsZp.nspu, -3, 1));
        svd.addModule(new ModuleInfo(ItemsZp.flashSuppressor_rifle, 11, 11));
        svd.addModule(new ModuleInfo(ItemsZp.muzzlebrake_rifle, 11, 11));
        svd.addModule(new ModuleInfo(ItemsZp.silencer_rifle, 11, 11));
        svd.setMaxDurability(180);

        scar = new ItemSniperRifle("scar", "scar_s", "scar_r", 10, 80, 60, 8, 256, 206, 4.0f, 2.0f, 4.5f, 1.0f, 0.5f, 3.0f, 0.3f, true, false, _308winAmmo);
        scar.addModule(new ModuleInfo(ItemsZp.laser, 6, 6));
        scar.addModule(new ModuleInfo(ItemsZp.bipod, 5, 0));
        scar.addModule(new ModuleInfo(ItemsZp.foregrip, 5, 0));
        scar.addModule(new ModuleInfo(ItemsZp.angle_foregrip, 5, 0));
        scar.addModule(new ModuleInfo(ItemsZp.scope_eotech, -3, 0));
        scar.addModule(new ModuleInfo(ItemsZp.scope4x, -3, 0));
        scar.addModule(new ModuleInfo(ItemsZp.acog2x, -2, 1));
        scar.addModule(new ModuleInfo(ItemsZp.scope6x, -3, 0));
        scar.addModule(new ModuleInfo(ItemsZp.anPvs4, -3, 0));
        scar.addModule(new ModuleInfo(ItemsZp.flashSuppressor_rifle, 11, 11));
        scar.addModule(new ModuleInfo(ItemsZp.muzzlebrake_rifle, 11, 11));
        scar.addModule(new ModuleInfo(ItemsZp.silencer_rifle, 11, 11));
        scar.setMaxDurability(180);

        blaser93 = new ItemSniperRifle("blaser93", "blaser93_s", "blaser93_r", 5, 80, 80, 16, 256, 212, 4.0f, 4.0f, 4.0f, 0.5f, 0.5f, 2.0f, 0.3f, true, false, _308winAmmo);
        blaser93.enableShutterAnim("sniper_r");
        blaser93.addModule(new ModuleInfo(ItemsZp.scope_pu, -1, 2));
        blaser93.addModule(new ModuleInfo(ItemsZp.hunting_scope, 0, 2));
        blaser93.addModule(new ModuleInfo(ItemsZp.flashSuppressor_rifle, 11, 11));
        blaser93.addModule(new ModuleInfo(ItemsZp.muzzlebrake_rifle, 11, 11));
        blaser93.addModule(new ModuleInfo(ItemsZp.silencer_rifle, 11, 11));
        blaser93.setMaxDurability(140);

        mosin = new ItemSniperRifleNomag("mosin", "mosin_s", "sniper_r", 6, 20, 60, 18, 256, 212, 5.0f, 2.0f, 4.0f, 0.5f, 0.5f, 3.0f, 0.5f, true, false, _7_62x54RAmmo);
        mosin.enableShutterAnim("sniper_r");
        mosin.addModule(new ModuleInfo(ItemsZp.bipod, 5, 0));
        mosin.addModule(new ModuleInfo(ItemsZp.scope_pu, -2, 1));
        mosin.addModule(new ModuleInfo(ItemsZp.hunting_scope, -1, 1));
        mosin.addModule(new ModuleInfo(ItemsZp.flashSuppressor_rifle, 11, 11));
        mosin.addModule(new ModuleInfo(ItemsZp.muzzlebrake_rifle, 11, 11));
        mosin.setMaxDurability(160);

        barrett_m82a1 = new ItemSniperRifle("barrett_m82a1", "barrett_m82a1_s", "barrett_m82a1_r", 5, 140, 100, 20, 256, 182, 12.0f, 6.0f, 3.75f, 0.25f, 1.0f, 5.0f, 0.25f, true, false, _50bmgAmmo);
        barrett_m82a1.addModule(new ModuleInfo(ItemsZp.laser, 6, 6));
        barrett_m82a1.addModule(new ModuleInfo(ItemsZp.bipod, 7, 3));
        barrett_m82a1.addModule(new ModuleInfo(ItemsZp.acog2x, -2, 1));
        barrett_m82a1.addModule(new ModuleInfo(ItemsZp.scope4x, -2, 1));
        barrett_m82a1.addModule(new ModuleInfo(ItemsZp.scope6x, -2, 1));
        barrett_m82a1.addModule(new ModuleInfo(ItemsZp.anPvs4, -2, 1));
        barrett_m82a1.setMaxDurability(140);

        m24 = new ItemSniperRifleNomag("m24", "m24_s", "sniper_r", 5, 20, 85, 20, 256, 204, 6.0f, 2.5f, 3.75f, 0.25f, 0.6f, 3.0f, 0.35f, true, false, _308winAmmo);
        m24.enableShutterAnim("sniper_r");
        m24.addModule(new ModuleInfo(ItemsZp.laser, 6, 6));
        m24.addModule(new ModuleInfo(ItemsZp.bipod, 7, 3));
        m24.addModule(new ModuleInfo(ItemsZp.acog2x, -2, 0));
        m24.addModule(new ModuleInfo(ItemsZp.scope4x, -2, 0));
        m24.addModule(new ModuleInfo(ItemsZp.scope6x, -2, 0));
        m24.addModule(new ModuleInfo(ItemsZp.anPvs4, -2, 0));
        m24.addModule(new ModuleInfo(ItemsZp.flashSuppressor_rifle, 11, 11));
        m24.addModule(new ModuleInfo(ItemsZp.muzzlebrake_rifle, 11, 11));
        m24.addModule(new ModuleInfo(ItemsZp.silencer_rifle, 11, 11));
        m24.setMaxDurability(140);

        m1895 = new ItemSniperRifleNomag("m1895", "m1895_s", "sniper_r", 5, 25, 48, 20, 246, 186, 4.5f, 0.9f, 5.0f, 1.5f, 0.5f, 2.0f, 0.5f, true, false, _7_62x54RAmmo);
        m1895.enableShutterAnim("sniper_r");
        m1895.addModule(new ModuleInfo(ItemsZp.scope_pu, -2, 1));
        m1895.addModule(new ModuleInfo(ItemsZp.hunting_scope, -1, 1));
        m1895.setMaxDurability(140);

        toz66 = new ItemShotgunNomag("toz66", "shotgun_s", "shotgun_r2", 2, 20, 9, 18, 42, 36, 8.0f, 4.0f, 20.0f, 18.0f, 0.5f, 1.0f, 0.0f, false, false, _12Ammo);
        toz66.setMaxDurability(100);

        custom_shotgun = new ItemShotgunNomag("custom_shotgun", "shotgun_s", "shotgun_r2", 1, 30, 9, 18, 42, 34, 12.0f, 12.0f, 20.0f, 18.0f, 0.15f, 2.0f, 1.0f, false, false, _12Ammo) {
            @Override
            public Item repairItem() {
                return ItemsZp.custom_repair;
            }
        };
        custom_shotgun.setMaxDurability(55);

        toz66_short = new ItemShotgunNomag("toz66_short", "shotgun_s", "shotgun_r2", 2, 14, 9, 8, 34, 26, 10.0f, 5.0f, 22.0f, 22.0f, 0.5f, 1.0f, 0.0f, false, false, _12Ammo);
        toz66_short.setRenderType(AGunBase.GunType.PISTOL2);
        toz66_short.setMaxDurability(100);

        rem870 = new ItemShotgunNomag("rem870", "shotgun_s", "shotgun_r2", 4, 15, 9, 12, 42, 36, 7.0f, 3.5f, 18.0f, 16.0f, 0.25f, 1.0f, 0.4f, true, false, _12Ammo);
        rem870.enableShutterAnim("shotgun_r3");
        rem870.addModule(new ModuleInfo(ItemsZp.laser, 6, 6));
        rem870.addModule(new ModuleInfo(ItemsZp.flashSuppressor_rifle, 11, 11));
        rem870.addModule(new ModuleInfo(ItemsZp.muzzlebrake_rifle, 11, 11));
        rem870.setMaxDurability(120);

        saiga12 = new ItemShotgun("saiga12", "shotgun_s", "saiga_r", 5, 80, 9, 10, 56, 42, 5.0f, 5.0f, 14.0f, 12.0f, 0.5f, 2.0f, 0.6f, true, false, _12Ammo);
        saiga12.addModule(new ModuleInfo(ItemsZp.laser, 6, 6));
        saiga12.addModule(new ModuleInfo(ItemsZp.flashSuppressor_rifle, 11, 11));
        saiga12.addModule(new ModuleInfo(ItemsZp.muzzlebrake_rifle, 11, 11));
        saiga12.addModule(new ModuleInfo(ItemsZp.foregrip, 5, 0));
        saiga12.addModule(new ModuleInfo(ItemsZp.angle_foregrip, 5, 0));
        saiga12.setMaxDurability(130);

        spas12 = new ItemShotgunNomag("spas12", "shotgun_s", "shotgun_r2", 8, 16, 9, 10, 42, 36, 7.0f, 3.5f, 22.0f, 20.0f, 0.5f, 3.0f, 0.35f, true, true, _12Ammo);
        spas12.addModule(new ModuleInfo(ItemsZp.laser, 6, 6));
        spas12.addModule(new ModuleInfo(ItemsZp.flashSuppressor_rifle, 11, 11));
        spas12.addModule(new ModuleInfo(ItemsZp.muzzlebrake_rifle, 11, 11));
        spas12.addModule(new ModuleInfo(ItemsZp.silencer_rifle, 11, 11));
        spas12.setMaxDurability(140);

        sodagun = new ItemSodaGun("sodagun", "soda_s", "shotgun_r2", 4, 16, 12, 12, 52, 32, 7.0f, 3.5f, 32.0f, 30.0f, 0.5f, 3.0f, 0.0f, false, true, _burnAmmo);
        sodagun.setCreativeTab(fun);
        sodagun.setMaxDurability(140);

        steel_mat.customCraftingMaterial = steel_ingot;
        steelItem_mat.customCraftingMaterial = steel_ingot;
        titan_mat.customCraftingMaterial = titan_ingot;
        titanItem_mat.customCraftingMaterial = titan_ingot;
        copperItem_mat.customCraftingMaterial = copper_ingot;
        iron_club_mat.customCraftingMaterial = Items.iron_ingot;
        crowbar_mat.customCraftingMaterial = Items.iron_ingot;
        hatchet_mat.customCraftingMaterial = Items.iron_ingot;
        sledgehammer_mat.customCraftingMaterial = Items.iron_ingot;
        katana_mat.customCraftingMaterial = ItemsZp.steel_ingot;
        machete_mat.customCraftingMaterial = ItemsZp.steel_ingot;

        //FUN//

        _solncepekRocket = new ItemRocket().setUnlocalizedName("_solncepekRocket");
        _solncepekRocket.setCreativeTab(fun);
        _solncepekRocket.setMaxStackSize(10);

        _katyushaRocket = new ItemRocket().setUnlocalizedName("_katyushaRocket");
        _katyushaRocket.setCreativeTab(fun);
        _katyushaRocket.setMaxStackSize(64);

        _gradRocket = new ItemRocket().setUnlocalizedName("_gradRocket");
        _gradRocket.setCreativeTab(fun);
        _gradRocket.setMaxStackSize(48);

        _geran2 = new ItemRocket().setUnlocalizedName("_geran2");
        _geran2.setCreativeTab(fun);
        _geran2.setMaxStackSize(4);

        _kalibrRocket = new ItemRocket().setUnlocalizedName("_kalibrRocket");
        _kalibrRocket.setCreativeTab(fun);
        _kalibrRocket.setMaxStackSize(4);

        _iskanderRocket = new ItemRocket().setUnlocalizedName("_iskanderRocket");
        _iskanderRocket.setCreativeTab(fun);
        _iskanderRocket.setMaxStackSize(1);

        _c300Rocket = new ItemRocket().setUnlocalizedName("_c300Rocket");
        _c300Rocket.setCreativeTab(fun);
        _c300Rocket.setMaxStackSize(4);

        _r27Rocket = new ItemRocket().setUnlocalizedName("_r27Rocket");
        _r27Rocket.setCreativeTab(fun);
        _r27Rocket.setMaxStackSize(8);

        _ovodRocket = new ItemRocket().setUnlocalizedName("_ovodRocket");
        _ovodRocket.setCreativeTab(fun);
        _ovodRocket.setMaxStackSize(4);

        _x101Rocket = new ItemRocket().setUnlocalizedName("_x101Rocket");
        _x101Rocket.setCreativeTab(fun);
        _x101Rocket.setMaxStackSize(2);

        _pancirRocket = new ItemRocket().setUnlocalizedName("_pancirRocket");
        _pancirRocket.setCreativeTab(fun);
        _pancirRocket.setMaxStackSize(14);

        _uraganRocket = new ItemRocket().setUnlocalizedName("_uraganRocket");
        _uraganRocket.setCreativeTab(fun);
        _uraganRocket.setMaxStackSize(16);

        _bastionRocket = new ItemRocket().setUnlocalizedName("_bastionRocket");
        _bastionRocket.setCreativeTab(fun);
        _bastionRocket.setMaxStackSize(1);

        _kinzhalRocket = new ItemRocket().setUnlocalizedName("_kinzhalRocket");
        _kinzhalRocket.setCreativeTab(fun);
        _kinzhalRocket.setMaxStackSize(1);
    }

    public static void register() {
        registerItem(bag_random);

        registerItem(gift);
        registerItem(bag);
        registerItem(rotten_apple);
        registerItem(boiled_egg);
        registerItem(cola);
        registerItem(pepsi);
        registerItem(water);
        registerItem(boiled_water);
        registerItem(cactus_water);
        registerItem(cactus_food);
        registerItem(donut);
        registerItem(orange);
        registerItem(banana);
        registerItem(fish_zp);
        registerItem(fish_zp_cooked);
        registerItem(jam);
        registerItem(pea);
        registerItem(ananas);
        registerItem(stewed_meat);
        registerItem(fish_canned);
        registerItem(soup);
        registerItem(hotdog);
        registerItem(burger);
        registerItem(burn);
        registerItem(beer);
        registerItem(vodka);
        registerItem(nuka_cola);
        registerItem(ai2_kit);
        registerItem(aid_kit);
        registerItem(blood_bag);
        registerItem(mem_elixir);
        registerItem(morphine);
        registerItem(adrenaline);
        registerItem(bandage);
        registerItem(military_bandage);
        registerItem(tire);
        registerItem(good_vision);
        registerItem(night_vision);
        registerItem(antihunger);
        registerItem(antiheadache);
        registerItem(antipoison);
        registerItem(antitoxin);
        registerItem(heal);
        registerItem(steroid);
        registerItem(stimulator);
        registerItem(meth);
        registerItem(antiradiation);
        registerItem(coke);
        registerItem(poison);
        registerItem(antidote_syringe);
        registerItem(blind_syringe);
        registerItem(heroin);
        registerItem(antivirus_syringe);
        registerItem(virus_syringe);
        registerItem(death_syringe);

        registerItem(bone_knife);
        registerItem(armature);
        registerItem(copper_sword);
        registerItem(screwdriver);
        registerItem(pipe);
        registerItem(hammer);
        registerItem(bat);
        registerItem(cleaver);
        registerItem(golf_club);
        registerItem(police_club);
        registerItem(crowbar);
        registerItem(iron_club);
        registerItem(steel_sword);
        registerItem(hatchet);
        registerItem(sledgehammer);
        registerItem(machete);
        registerItem(katana);
        registerItem(titan_sword);
        registerItem(lucille);
        registerItem(inferno);
        registerItem(mjolnir);
        registerItem(ripper);
        registerItem(scare_sword);
        registerItem(caramel_sword);

        registerItem(copper_spear);
        registerItem(steel_spear);

        registerItem(m_scissors);
        registerItem(wrench);
        registerItem(lockpick);
        registerItem(copper_axe);
        registerItem(copper_hoe);
        registerItem(copper_shovel);
        registerItem(copper_pickaxe);

        registerItem(steel_axe);
        registerItem(steel_hoe);
        registerItem(steel_shovel);
        registerItem(steel_pickaxe);

        registerItem(titan_axe);
        registerItem(titan_hoe);
        registerItem(titan_shovel);
        registerItem(titan_pickaxe);

        registerItem(transmitter);
        registerItem(transmitter_tactic);
        registerItem(freezer);
        registerItem(antigarbage);
        registerItem(chance);

        registerItem(ammo_press);
        registerItem(oxygen);
        registerItem(dosimeter);
        registerItem(gps);
        registerItem(CBS);

        registerItem(fish_bones);
        registerItem(rot_mass);
        registerItem(custom_gunpowder);

        registerItem(cement);

        registerItem(electrician_kit);
        registerItem(table);
        registerItem(shelves);
        registerItem(chisel);

        registerItem(copper_ingot);
        registerItem(brass_material);
        registerItem(brass_nugget);
        registerItem(steel_ingot);
        registerItem(steel_material);
        registerItem(titan_ingot);
        registerItem(matches);
        registerItem(solid_fuel);
        registerItem(blood_material);
        registerItem(chemres);
        registerItem(chemicals1_a);
        registerItem(chemicals1_b);
        registerItem(chemicals1_c);
        registerItem(chemicals1);
        registerItem(chemicals2);
        registerItem(scare_pumpkin);
        registerItem(caramel);

        registerItem(tanning);
        registerItem(upd_leather);
        registerItem(box_paper);
        registerItem(bellows);

        registerItem(frame_helmet);
        registerItem(frame_chestplate);
        registerItem(frame_leggings);
        registerItem(frame_boots);

        registerItem(raw_copper);
        registerItem(raw_iron);
        registerItem(raw_gold);
        registerItem(raw_titan);

        registerItem(kevlar);
        registerItem(lubricant);
        registerItem(electronic);
        registerItem(battery);
        registerItem(coils);
        registerItem(repair);
        registerItem(custom_repair);

        registerItem(acid_bucket);
        registerItem(toxicwater_bucket);

        registerItem(acid);
        registerItem(holywater);
        registerItem(tnt);
        registerItem(plate);
        registerItem(plate_meat);
        registerItem(trap_grenade);
        registerItem(rock);
        registerItem(frag_grenade);
        registerItem(gas_grenade);
        registerItem(smoke_grenade);

        registerItem(scrap_material);
        registerItem(manyscrap);
        registerItem(uran_material);
        registerItem(armor_material);

        registerItem(gasmask);
        registerItem(pnv);
        registerItem(dynamike);
        registerItem(kevlar_vest);
        registerItem(kevlar_helmet);

        registerItem(rad_helmet);
        registerItem(rad_chestplate);
        registerItem(rad_leggings);
        registerItem(rad_boots);

        registerItem(aqualung_helmet);
        registerItem(aqualung_chestplate);
        registerItem(aqualung_leggings);
        registerItem(aqualung_boots);

        registerItem(indcostume_helmet);
        registerItem(indcostume_chestplate);
        registerItem(indcostume_leggings);
        registerItem(indcostume_boots);

        registerItem(rotten_chestplate);
        registerItem(rotten_leggings);
        registerItem(rotten_boots);
        registerItem(balaclava);
        registerItem(forest_helmet);
        registerItem(forest_chestplate);
        registerItem(forest_leggings);
        registerItem(forest_boots);

        registerItem(winter_helmet);
        registerItem(winter_chestplate);
        registerItem(winter_leggings);
        registerItem(winter_boots);

        registerItem(sand_helmet);
        registerItem(sand_chestplate);
        registerItem(sand_leggings);
        registerItem(sand_boots);

        registerItem(steel_helmet);
        registerItem(steel_chestplate);
        registerItem(steel_leggings);
        registerItem(steel_boots);

        registerItem(juggernaut_helmet);
        registerItem(juggernaut_chestplate);
        registerItem(juggernaut_leggings);
        registerItem(juggernaut_boots);

        registerItem(_scare);
        registerItem(_caramel);

        registerItem(_custom);
        registerItem(_custom2);
        registerItem(_gauss);
        registerItem(_22lr);
        registerItem(_9mm);
        registerItem(_45acp);
        registerItem(_7_62x25);
        registerItem(_357m);
        registerItem(_9x39);
        registerItem(_5_7x28);
        registerItem(_12);
        registerItem(_5_45x39);
        registerItem(_5_56x45);
        registerItem(_7_62x39);
        registerItem(_7_62x54R);
        registerItem(_308win);
        registerItem(_50bmg);
        registerItem(_flare);
        registerItem(_grenade40mm);
        registerItem(_wog25);
        registerItem(_rocket);

        registerItem(book_fisher);
        registerItem(book_hunter);
        registerItem(book_gunsmith);
        registerItem(book_survivor);
        registerItem(book_farmer);
        registerItem(book_miner);

        registerItem(angle_foregrip);
        registerItem(foregrip);
        registerItem(m203);
        registerItem(gp25);
        registerItem(laser);
        registerItem(bipod);
        registerItem(scope_eotech);
        registerItem(scope_kobra);
        registerItem(acog2x);
        registerItem(scope_kashtan);
        registerItem(scope4x);
        registerItem(pso4x);
        registerItem(scope6x);
        registerItem(pso6x);
        registerItem(nspu);
        registerItem(anPvs4);
        registerItem(scope_pu);
        registerItem(hunting_scope);
        registerItem(pistol_scope);
        registerItem(flashSuppressor_pistol);
        registerItem(flashSuppressor_rifle);
        registerItem(muzzlebrake_pistol);
        registerItem(muzzlebrake_rifle);
        registerItem(silencer_pistol);
        registerItem(silencer_rifle);

        registerItem(scare_gun);
        registerItem(caramel_gun);

        registerItem(crossbow);
        registerItem(custom_pistol);
        registerItem(custom_revolver);
        registerItem(custom_rifle);
        registerItem(custom_sniper);
        registerItem(custom_shotgun);
        registerItem(flare);
        registerItem(walther);
        registerItem(pm);
        registerItem(tt);
        registerItem(m1911);
        registerItem(glock18);
        registerItem(fiveseven);
        registerItem(python);
        registerItem(deagle);
        registerItem(deagle_gold);
        registerItem(mac10);
        registerItem(mini_uzi);

        registerItem(sporter);
        registerItem(casull290);
        registerItem(akm);
        registerItem(ak12);
        registerItem(aksu);
        registerItem(g36k);
        registerItem(m16a1);
        registerItem(m4a1);
        registerItem(aug);
        registerItem(vss);
        registerItem(oc14);
        registerItem(mp40);
        registerItem(ppsh);
        registerItem(mini14);
        registerItem(scout);
        registerItem(mp5);
        registerItem(ump45);
        registerItem(bizon);
        registerItem(p90);
        registerItem(rpk);
        registerItem(pkm);
        registerItem(m249);
        registerItem(ar15);
        registerItem(sg550);
        registerItem(fal);
        registerItem(sks);
        registerItem(scar);
        registerItem(svd);
        registerItem(m1894);
        registerItem(m1895);
        registerItem(mosin);
        registerItem(blaser93);
        registerItem(m24);
        registerItem(barrett_m82a1);

        registerItem(toz66);
        registerItem(toz66_short);
        registerItem(rem870);
        registerItem(saiga12);
        registerItem(spas12);

        registerItem(m79);
        registerItem(rpg28);

        registerItem(sodagun);
        registerItem(javelin);
        registerItem(_javelin);

        registerItem(igla);
        registerItem(_igla);

        registerItem(gauss);

        registerItem(old_backpack);
        registerItem(old_backpack2);
        registerItem(fish_box);
        registerItem(fish_crate);
        registerItem(fish_iron_crate);
        registerItem(cash_coin);

        registerItem(binoculars);

        //FUN//
        registerItem(_katyushaRocket);
        registerItem(_gradRocket);
        registerItem(_solncepekRocket);
        registerItem(_pancirRocket);
        registerItem(_c300Rocket);
        registerItem(_r27Rocket);
        registerItem(_geran2);
        registerItem(_ovodRocket);
        registerItem(_kalibrRocket);
        registerItem(_x101Rocket);
        registerItem(_iskanderRocket);
        registerItem(_uraganRocket);
        registerItem(_bastionRocket);
        registerItem(_kinzhalRocket);

        registerItem(_customRocket);
        if (FMLLaunchHandler.side().isClient()) {
            weap.loadTable();
            ammo.loadTable();
            items.loadTable();
            armor.loadTable();
            camo.loadTable();
            melee.loadTable();
            tools.loadTable();
            blocks.loadTable();
            decorations.loadTable();
            admin_blocks.loadTable();
            food.loadTable();
            medicine.loadTable();
            fun.loadTable();
            special.loadTable();
            mods.loadTable();
            progs.loadTable();
            books.loadTable();
        }

        Item.getItemFromBlock(BlocksZp.copper_ore).setMaxStackSize(16);
        Item.getItemFromBlock(BlocksZp.titan_ore).setMaxStackSize(16);
        Item.getItemFromBlock(BlocksZp.uranium).setMaxStackSize(16);
        Item.getItemFromBlock(BlocksZp.mine).setMaxStackSize(16);
    }

    public static void registerItem(Item item) {
        GameRegistry.registerItem(item, item.getUnlocalizedName().substring(5));
        item.setTextureName(Main.MODID + ":" + item.getUnlocalizedName().substring(5));
    }

    @SuppressWarnings("unused")
    public static void registerItem(Item item, String textureName) {
        GameRegistry.registerItem(item, item.getUnlocalizedName().substring(5));
        item.setTextureName(Main.MODID + ":" + textureName);
    }
}
