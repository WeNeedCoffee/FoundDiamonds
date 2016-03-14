package co.proxa.founddiamonds.handlers;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import co.proxa.founddiamonds.FoundDiamonds;
import co.proxa.founddiamonds.util.Format;
import co.proxa.founddiamonds.util.Prefix;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;

public class AdminMessageHandler {

    private FoundDiamonds fd;
    private HashSet<String> receivedAdminMessage = new HashSet<String>();

    public AdminMessageHandler(FoundDiamonds fd) {
        this.fd = fd;
    }

    public void sendAdminMessage(final Material mat, final int blockTotal, final Player player) {
    	 System.out.println("called adminmsg");
        String adminMessage = Prefix.getAdminPrefix() + " " + ChatColor.YELLOW + player.getName() +ChatColor.DARK_RED + " just found " + fd.getMapHandler().getAdminMessageBlocks().get(mat) + (blockTotal == 500 ? "over 500 " : String.valueOf(blockTotal)) + " " + Format.getFormattedName(mat, blockTotal);
        fd.getServer().getConsoleSender().sendMessage(adminMessage);
       

        for (Player y : fd.getServer().getOnlinePlayers()) {
            if (fd.getPermissions().hasAdminMessagePerm(y) && y != player) {
                y.sendMessage(adminMessage);
                if (!receivedAdminMessage.contains(y.getName())) {
                    receivedAdminMessage.add(y.getName());
                }
            }
        }        
    }

    public void clearReceivedAdminMessage() {
        receivedAdminMessage.clear();
    }

    public boolean receivedAdminMessage(Player player) {
        return receivedAdminMessage.contains(player.getName());
    }
}