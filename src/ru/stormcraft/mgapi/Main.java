package ru.stormcraft.mgapi;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;

public class Main extends JavaPlugin{
	static boolean debug;
	public static JavaPlugin PLUGIN;
	public Arena testArena;
	public static WorldEditPlugin worldEdit;
	@Override
	public void onEnable() {
		worldEdit = (WorldEditPlugin) Bukkit.getServer().getPluginManager().getPlugin("WorldEdit");
		getLogger().info("Hooked onto WE!");
		PLUGIN = this;
	}
	
	@Override
	public void onDisable() {
		
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player){
			Player player = (Player) sender;
			if(label.equals("new")){
				testArena = new Arena(args[0],player.getLocation() );
				getLogger().info("success!");
				return true;
			}
			if(label.equals("save")){
				return testArena.save(player);
			}
			if(label.equals("reset")){
				return testArena.reset();
			}
		}
		return false;
	}
	
	public static void debug(String msng){
  		if (debug) {
  			Bukkit.getLogger().info(msng);
  		}
  	}
}
