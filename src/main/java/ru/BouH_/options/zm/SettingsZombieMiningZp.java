package ru.BouH_.options.zm;

import cpw.mods.fml.common.FMLLog;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import ru.BouH_.network.NetworkHandler;
import ru.BouH_.network.packets.data.PacketClientSettings;
import ru.BouH_.options.SettingFloatBar;
import ru.BouH_.options.SettingObject;
import ru.BouH_.options.SettingTrueFalse;

import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class SettingsZombieMiningZp {
    public File optionsFile;
    public static final Map<String, Integer> minigMap = new HashMap<>();

    public SettingsZombieMiningZp(File p_i1016_2_) {
        this.optionsFile = new File(p_i1016_2_, "optionsZmMiningZpm.txt");
    }

    public void loadOptions() {
        try {
            if (!this.optionsFile.exists()) {
                this.optionsFile.createNewFile();
                try (PrintWriter printwriter = new PrintWriter(new FileWriter(this.optionsFile))) {
                    printwriter.println("#tile.blockLapis=10");
                }
                return;
            }
            BufferedReader bufferedreader = new BufferedReader(new FileReader(this.optionsFile));
            String s;
            while ((s = bufferedreader.readLine()) != null) {
                String[] string = s.split("=");
                String txt = string[0];
                String value = string[1];
                if (txt.charAt(0) == '#') {
                    continue;
                }
                try {
                    SettingsZombieMiningZp.minigMap.put(txt, Integer.parseInt(value));
                } catch (Exception e) {
                    FMLLog.warning(e.getMessage());
                }
            }
            bufferedreader.close();
        } catch (Exception exception1) {
            FMLLog.info("Failed to load options", exception1);
        }
    }
}
