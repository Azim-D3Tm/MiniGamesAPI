package ru.stormcraft.mgapi.utils;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import ru.stormcraft.mgapi.Main;

import com.sk89q.worldedit.CuboidClipboard;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.EmptyClipboardException;
import com.sk89q.worldedit.LocalPlayer;
import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitUtil;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.data.DataException;
import com.sk89q.worldedit.schematic.SchematicFormat;
import com.sk89q.worldedit.session.ClipboardHolder;

@SuppressWarnings("deprecation")
public class Schematic {
	
	public static Location save(Player player, String schematicName) {
        Location loc;
		try {
            File schematic = new File(Main.PLUGIN.getDataFolder(), "/schematics/" + schematicName);
            if(schematic.exists()){
            	schematic.delete();
            }
            File dir = new File(Main.PLUGIN.getDataFolder(), "/schematics/");
            if (!dir.exists())
                dir.mkdirs();

            //WorldEditPlugin wep = (WorldEditPlugin) Bukkit.getServer().getPluginManager().getPlugin("WorldEdit");
            WorldEdit we = Main.worldEdit.getWorldEdit();

            LocalPlayer localPlayer = Main.worldEdit.wrapPlayer(player);
            
            
            LocalSession localSession = we.getSession(localPlayer);
            
            ClipboardHolder selection = localSession.getClipboard();
            EditSession editSession = localSession.createEditSession(localPlayer);
            
            
            Vector min = selection.getClipboard().getMinimumPoint();
            Vector max = selection.getClipboard().getMaximumPoint();

            editSession.enableQueue();
            CuboidClipboard clipboard = new CuboidClipboard(max.subtract(min).add(new Vector(1, 1, 1)), min);
            clipboard.copy(editSession);
            SchematicFormat.MCEDIT.save(clipboard, schematic);
            editSession.flushQueue();

            //player.sendMessage("Saved schematic!");
            loc = new Location(player.getWorld(), min.getX(), min.getY(), min.getZ());
            Bukkit.getLogger().info("success!");
            return loc;
        } catch (IOException | DataException  ex) {
        	Bukkit.getLogger().info("nope!");
            ex.printStackTrace();
            return null;
        } catch (EmptyClipboardException e) {
			player.sendMessage("You must copy the area first!");
			e.printStackTrace();
			return null;
		}
    }


	public static boolean paste(String schematicName, Location pasteLoc) {
        try {
            File dir = new File(Main.PLUGIN.getDataFolder(), "/schematics/" + schematicName);

            EditSession editSession = new EditSession(new BukkitWorld(pasteLoc.getWorld()), 999999999);
            editSession.enableQueue();

            SchematicFormat schematic = SchematicFormat.getFormat(dir);
            CuboidClipboard clipboard = schematic.load(dir);

            clipboard.paste(editSession, BukkitUtil.toVector(pasteLoc), false);
            editSession.flushQueue();
            Bukkit.getLogger().info("success!");
            return true;
        } catch (DataException | IOException | MaxChangedBlocksException ex) {
        	Bukkit.getLogger().info("nope!");
            ex.printStackTrace();
            return false;
        }
    }

}
