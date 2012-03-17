/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.itsatacoshop247.FoundDiamonds;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.potion.PotionEffectType;

/**
 *
 * @author seed419
 */
public class PlayerDamageListener implements Listener {


    private FoundDiamonds fd;


    public PlayerDamageListener(FoundDiamonds fd) {
        this.fd = fd;
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent event) {
        if ((event.getCause() == DamageCause.FALL) && (event.getEntity() instanceof Player)) {
            Player player = (Player) event.getEntity();
            if (fd.getJumpPotion().containsKey(player) && player.hasPotionEffect(PotionEffectType.JUMP)) {
                event.setCancelled(true);
            } else {
                fd.getJumpPotion().put(player, false);
            }
        }
    }
}