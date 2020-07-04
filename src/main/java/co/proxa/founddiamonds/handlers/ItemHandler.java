package co.proxa.founddiamonds.handlers;

import java.util.List;
import java.util.Random;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import co.proxa.founddiamonds.FoundDiamonds;
import co.proxa.founddiamonds.file.Config;
import co.proxa.founddiamonds.util.Format;

public class ItemHandler {
	public static int randInt(int min, int max) {
		return new Random().nextInt(max - min + 1) + min;
	}
    private FoundDiamonds fd;

    public ItemHandler(FoundDiamonds fd) {
        this.fd = fd;
    }

    public static List<String> randomItems() {
    	return (List<String>) FoundDiamonds.fd.getConfig().getList(Config.randomItems); 
    }
    public void handleRandomItems(final Player player) {
        int randomInt = (int) (Math.random()*100);
        if (randomInt <= fd.getConfig().getInt(Config.chanceToGetItem)) {
        	selectRandomItem(player);
        }
    }

    private void selectRandomItem(Player player) {
        List<String> items = randomItems();
        giveItems(player, Material.matchMaterial(items.get(randInt(1, items.size() - 1))), getRandomItemAmount());
    }

    //@SuppressWarnings("deprecation")
    private void giveItems(Player player, Material item, int amount) {
        if (fd.getConfig().getBoolean(Config.awardAllItems)) {
            for(Player p: fd.getServer().getOnlinePlayers()) {
                if (fd.getWorldHandler().isEnabledWorld(p)) {
                    p.sendMessage(ChatColor.GRAY + "Everyone else got " + amount +
                            " " + Format.getFormattedName(item, amount));
                    if (p != player) {
                    	p.getWorld().dropItem(player.getLocation(), new ItemStack(item, amount));
                        
                    }
                }
            }
        } else {
            player.sendMessage(ChatColor.GRAY + "You got " + amount +
                    " " + Format.getFormattedName(item, amount));
            player.getWorld().dropItem(player.getLocation(), new ItemStack(item, amount));
        }
    }

    private int getRandomItemAmount(){
        return randInt(1, fd.getConfig().getInt(Config.maxItems));
    }
}
