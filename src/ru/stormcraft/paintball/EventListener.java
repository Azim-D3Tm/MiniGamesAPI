package ru.stormcraft.paintball;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import ru.stormcraft.mgapi.utils.NBTEditor;

public class EventListener implements Listener {
	
	@EventHandler
    public void onInteract(PlayerInteractEvent e) {
		System.out.println("test");
		if(!(e.getAction() == Action.RIGHT_CLICK_AIR)){
			return;
		}
		ItemStack item = new ItemStack( Material.WOOD_HOE, 1 );
	    
		// What's this!?! UNBREAKABILITY?!?! GREAT!
		item = NBTEditor.setItemTag(item,"TestValue","TestKey");
		String val = NBTEditor.getItemTag(item, "TestKey").toString();
		e.getPlayer().sendMessage(" : "+val);
		System.out.println("test "+val);
	}
	
}
