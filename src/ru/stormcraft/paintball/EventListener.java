package ru.stormcraft.paintball;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import ru.stormcraft.mgapi.utils.NBTEditor;

public class EventListener implements Listener {
	
	@EventHandler
    public void onInteract(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if(!(e.getAction() == Action.RIGHT_CLICK_AIR)){
			return;
		}
		
		if(NBTEditor.getItemTag(e.getPlayer().getItemInHand(),"PBgun")==null){
			return;
		}
		System.out.println("test");
		Snowball snowball = p.getWorld().spawn(p.getEyeLocation(), Snowball.class);
		snowball.setShooter(p);
		snowball.setVelocity(p.getLocation().getDirection().multiply(1.5));   
		snowball.setBounce(true);
		
	}
	
	
	@EventHandler
	public void hitb(ProjectileHitEvent e){
		if(!(e.getEntity() instanceof Snowball)){
			return;
		}
		System.out.println(e.getEntity().getLocation().getBlock().getTypeId()+" typeID");
		e.getEntity().getLocation().getBlock().setType(Material.WOOL);
	}
	
	@EventHandler
	public void hitp(EntityDamageByEntityEvent e){
		if(!((e.getDamager() instanceof Snowball)&&(e.getEntity() instanceof Player))){
			return;
		}
		
	}
}
