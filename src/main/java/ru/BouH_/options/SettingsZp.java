package ru.BouH_.options;

import cpw.mods.fml.common.FMLLog;
import net.minecraft.client.Minecraft;
import ru.BouH_.network.NetworkHandler;
import ru.BouH_.network.packets.data.PacketClientSettings;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

public class SettingsZp {
    public File optionsFile;
    public SettingTrueFalse fancyGrass;
    public SettingTrueFalse fancyLeaf;
    public SettingTrueFalse pickUp_F;
    public SettingTrueFalse showPing;
    public SettingTrueFalse fastFly;
    public SettingTrueFalse fancyDrop;
    public SettingTrueFalse autoReload;
    public SettingTrueFalse scaleCross;
    public SettingTrueFalse strafes;
    public SettingTrueFalse redScreen;
    public SettingTrueFalse progressBar;
    public SettingTrueFalse dot;
    public SettingTrueFalse crossEffect;
    public SettingFloatBar length;
    public SettingFloatBar distance;
    public SettingFloatBar dynamicStr;

    public SettingFloatBar crossRed;
    public SettingFloatBar crossGreen;
    public SettingFloatBar crossBlue;
    public SettingTrueFalse selector;

    public SettingTrueFalse lowParticles;
    public SettingFloatBar entDistance;
    public SettingFloatBar itemDistance;
    public SettingFloatBar chestDistance;
    public SettingTrueFalse notificationHud;
    public SettingTrueFalse notificationChat;
    public Set<SettingObject> settingObjectSet;
    public SettingTrueFalse musicVolume;
    public SettingTrueFalse ambientVolume;

    public SettingsZp(File p_i1016_2_) {
        this.fancyGrass = new SettingTrueFalse("fancyGrass", true);
        this.fancyLeaf = new SettingTrueFalse("fancyLeaf", true);
        this.pickUp_F = new SettingTrueFalse("pickUp_F", true);
        this.showPing = new SettingTrueFalse("showPing", true);
        this.fastFly = new SettingTrueFalse("fastFly", true);
        this.fancyDrop = new SettingTrueFalse("fancyDrop", true);
        this.autoReload = new SettingTrueFalse("autoReload", true);
        this.scaleCross = new SettingTrueFalse("scaleCross", false);
        this.strafes = new SettingTrueFalse("strafes", true);
        this.redScreen = new SettingTrueFalse("redScreen", true);
        this.progressBar = new SettingTrueFalse("progressBar", true);
        this.dot = new SettingTrueFalse("dot", false);
        this.crossEffect = new SettingTrueFalse("crossEffect", true);
        this.selector = new SettingTrueFalse("selector", true);

        this.length = new SettingFloatBar("length", 3.0d, 0.0d, 16.0d, 1.0d);
        this.distance = new SettingFloatBar("distance", 0.0d, 0.0d, 20.0d, 1.0d);
        this.dynamicStr = new SettingFloatBar("dynamicStr", 1.0d, 0.0d, 3.0d, 0.1d);
        this.crossRed = new SettingFloatBar("crossRed", 1.0d, 0.0d, 1.0d, 0.01d);
        this.crossGreen = new SettingFloatBar("crossGreen", 1.0d, 0.0d, 1.0d, 0.01d);
        this.crossBlue = new SettingFloatBar("crossBlue", 1.0d, 0.0d, 1.0d, 0.01d);

        this.musicVolume = new SettingTrueFalse("musicVolume", true);
        this.ambientVolume = new SettingTrueFalse("ambientVolume", true);

        this.lowParticles = new SettingTrueFalse("lowParticles", false);
        this.entDistance = new SettingFloatBar("entDistance", 256.0d, 0.0d, 256.0d, 1.0d);
        this.itemDistance = new SettingFloatBar("itemDistance", 256.0d, 0.0d, 256.0d, 1.0d);
        this.chestDistance = new SettingFloatBar("chestDistance", 256.0d, 0.0d, 256.0d, 1.0d);

        this.notificationChat = new SettingTrueFalse("notificationChat", true);
        this.notificationHud = new SettingTrueFalse("notificationHud", true);

        this.settingObjectSet = new HashSet<>();
        this.settingObjectSet.add(this.fancyGrass);
        this.settingObjectSet.add(this.fancyLeaf);
        this.settingObjectSet.add(this.pickUp_F);
        this.settingObjectSet.add(this.showPing);
        this.settingObjectSet.add(this.fastFly);
        this.settingObjectSet.add(this.fancyDrop);
        this.settingObjectSet.add(this.autoReload);
        this.settingObjectSet.add(this.scaleCross);
        this.settingObjectSet.add(this.strafes);
        this.settingObjectSet.add(this.redScreen);
        this.settingObjectSet.add(this.progressBar);
        this.settingObjectSet.add(this.dot);
        this.settingObjectSet.add(this.crossEffect);
        this.settingObjectSet.add(this.length);
        this.settingObjectSet.add(this.distance);
        this.settingObjectSet.add(this.dynamicStr);
        this.settingObjectSet.add(this.crossRed);
        this.settingObjectSet.add(this.crossGreen);
        this.settingObjectSet.add(this.crossBlue);
        this.settingObjectSet.add(this.redScreen);
        this.settingObjectSet.add(this.progressBar);
        this.settingObjectSet.add(this.selector);
        this.settingObjectSet.add(this.notificationChat);
        this.settingObjectSet.add(this.notificationHud);

        this.settingObjectSet.add(this.lowParticles);
        this.settingObjectSet.add(this.entDistance);
        this.settingObjectSet.add(this.itemDistance);
        this.settingObjectSet.add(this.chestDistance);

        this.optionsFile = new File(p_i1016_2_, "optionsZpm.txt");
        this.loadOptions();
    }

    public void saveOptions() {
        try {
            PrintWriter printwriter = new PrintWriter(new FileWriter(this.optionsFile));
            for (SettingObject settingObject : this.settingObjectSet) {
                if (settingObject instanceof SettingFloatBar) {
                    SettingFloatBar settingFloatBar = (SettingFloatBar) settingObject;
                    printwriter.println(settingObject.getName() + ":" + settingFloatBar.getValue());
                } else if (settingObject instanceof SettingTrueFalse) {
                    SettingTrueFalse settingTrueFalse = (SettingTrueFalse) settingObject;
                    printwriter.println(settingObject.getName() + ":" + settingTrueFalse.isFlag());
                }
            }
            printwriter.close();
        } catch (Exception exception) {
            FMLLog.info("Failed to save options", exception);
        }
        if (Minecraft.getMinecraft().thePlayer != null) {
            this.sendSettingsToServer();
        }
    }

    public void loadOptions() {
        try {
            if (!this.optionsFile.exists()) {
                this.optionsFile.createNewFile();
                this.saveOptions();
                return;
            }
            BufferedReader bufferedreader = new BufferedReader(new FileReader(this.optionsFile));
            String s;
            while ((s = bufferedreader.readLine()) != null) {
                String[] string = s.split(":");
                String txt = string[0];
                String value = string[1];
                for (SettingObject settingObject : this.settingObjectSet) {
                    if (settingObject.getName().equals(txt)) {
                        if (settingObject instanceof SettingFloatBar) {
                            SettingFloatBar settingFloatBar = (SettingFloatBar) settingObject;
                            settingFloatBar.setValue(Float.parseFloat(value));
                        } else if (settingObject instanceof SettingTrueFalse) {
                            SettingTrueFalse settingTrueFalse = (SettingTrueFalse) settingObject;
                            settingTrueFalse.setFlag(value.equals("true"));
                        }
                        break;
                    }
                }
            }
            bufferedreader.close();
        } catch (Exception exception1) {
            FMLLog.info("Failed to load options", exception1);
        }
    }

    public void sendSettingsToServer() {
        NetworkHandler.NETWORK.sendToServer(new PacketClientSettings(this.pickUp_F.isFlag()));
    }
}
