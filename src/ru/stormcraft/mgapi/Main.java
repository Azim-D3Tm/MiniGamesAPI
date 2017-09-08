package ru.stormcraft.mgapi;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;


public class Main extends JavaPlugin{
	static boolean debug;
	public static JavaPlugin PLUGIN;
	public ArrayList<Arena> Arenas;
	public static WorldEditPlugin worldEdit;
	public static FileConfiguration config;
	
	@Override
	public void onEnable() {
		worldEdit = (WorldEditPlugin) Bukkit.getServer().getPluginManager().getPlugin("WorldEdit");
		getLogger().info("Hooked onto WE!");
		PLUGIN = this;
		config = getConfig();
	}
	
	@Override
	public void onDisable() {
		
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player){
			Player player = (Player) sender;
			if(args.length>0){
				if(label.equals("new")){
					Arena ar = new Arena(args[0],player.getLocation() );
					Arenas.add(ar);
					getLogger().info("success!");
					return true;
				}
				if(label.equals("save")){
					Arena ar = getArena(Arenas,args[0]);
					if(ar!= null){
						return ar.save(player);
					}
					return false;
				}
				if(label.equals("reset")){
					Arena ar = getArena(Arenas,args[0]);
					if(ar!= null){
						return ar.reset();
					}
					return false;
				}
			}
		}
		return false;
	}
	
	public static Arena getArena(ArrayList<Arena> Arenas, String name){
		for(Arena ar:Arenas){
			if(ar.name.equals(name)){
				return ar;
			}
		}
		return null;
	}
	
	public static void debug(String msng){
  		if (debug) {
  			Bukkit.getLogger().info(msng);
  		}
  	}
}
