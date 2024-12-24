package ru.BouH_.utils;

import cpw.mods.fml.relauncher.FMLLaunchHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import java.lang.reflect.InvocationTargetException;

public class PluginUtils {
    public static boolean canDamage(Entity ent, Entity ent2) {
        if (FMLLaunchHandler.side().isServer()) {
            try {
                return !((boolean) Class.forName("com.gamerforea.eventhelper.util.EventUtils").getMethod("cantDamage", Entity.class, Entity.class).invoke(Class.forName("com.gamerforea.eventhelper.util.EventUtils").newInstance(), ent, ent2));
            } catch (NoSuchMethodException | ClassNotFoundException | InstantiationException | IllegalAccessException |
                     InvocationTargetException ignored) {
            }
        }
        return true;
    }

    public static boolean canBreak(EntityPlayer player, int x, int y, int z) {
        if (FMLLaunchHandler.side().isServer()) {
            try {
                return !((boolean) Class.forName("com.gamerforea.eventhelper.util.EventUtils").getMethod("cantBreak", EntityPlayer.class, int.class, int.class, int.class).invoke(Class.forName("com.gamerforea.eventhelper.util.EventUtils").newInstance(), player, x, y, z));
            } catch (NoSuchMethodException | ClassNotFoundException | InstantiationException | IllegalAccessException |
                     InvocationTargetException ignored) {
            }
        }
        return true;
    }

    public static boolean isInPrivate(Entity ent) {
        if (FMLLaunchHandler.side().isServer()) {
            try {
                return (boolean) Class.forName("com.gamerforea.eventhelper.util.EventUtils").getMethod("isInPrivate", Entity.class).invoke(Class.forName("com.gamerforea.eventhelper.util.EventUtils").newInstance(), ent);
            } catch (NoSuchMethodException | ClassNotFoundException | InstantiationException | IllegalAccessException |
                     InvocationTargetException ignored) {
            }
        }
        return false;
    }

    public static boolean isInPrivate2(World world, int x, int y, int z) {
        if (FMLLaunchHandler.side().isServer()) {
            try {
                return (boolean) Class.forName("com.gamerforea.eventhelper.util.EventUtils").getMethod("isInPrivate", World.class, int.class, int.class, int.class).invoke(Class.forName("com.gamerforea.eventhelper.util.EventUtils").newInstance(), world, x, y, z);
            } catch (NoSuchMethodException | ClassNotFoundException | InstantiationException | IllegalAccessException |
                     InvocationTargetException ignored) {
            }
        }
        return false;
    }
}
