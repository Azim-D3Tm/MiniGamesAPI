package ru.stormcraft.mgapi;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import ru.stormcraft.mgapi.utils.Schematic;

public class Arena {
	public String name;
	public Location loc;
	public enum States { RUNNING , WAITING, STOPPED };
	@SuppressWarnings("unused")
	private States state;
	
	public HashMap<Player,List<UUID>> playershots;
	public HashMap<String,Player> players;
	
	public boolean save(Player player){
		if(player!= null){
			Location temploc;
			temploc = Schematic.save(player, this.name);
			if(temploc!=null){
				this.loc = temploc;
				Main.PLUGIN.getConfig().set("arenas."+name+".location.x", loc.getX());
				Main.PLUGIN.getConfig().set("arenas."+name+".location.y", loc.getY());
				Main.PLUGIN.getConfig().set("arenas."+name+".location.z", loc.getZ());
				Main.PLUGIN.getConfig().set("arenas."+name+".location.world", loc.getWorld().getName());
				Main.PLUGIN.saveConfig();
				return true;
			}
		}
		return false;
	}
	
	public boolean reset(){
		if(this.loc!= null){
			return Schematic.paste(this.name, this.loc);
		}
		return false;
	}
	
	public void stop(){
		//TODO generate stop method
		this.state = States.STOPPED;
	}
	
	public void run(){
		//TODO generate run method
		this.state = States.RUNNING;
	}

	public Arena(String name, Location loc) {
		super();
		this.name = name;
		this.loc = loc;
	}
	
	public Arena(String name, World world, double x, double y, double z) {
		super();
		this.name = name;
		this.loc = new Location(world,x,y,z);
	}
	
	public Arena(String name, String worldName, double x, double y, double z) {
		super();
		this.name = name;
		this.loc = new Location(Bukkit.getWorld(worldName),x,y,z);
	}
	
	
	
}
