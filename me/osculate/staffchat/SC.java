package me.osculate.staffchat;

import java.util.ArrayList;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import net.md_5.bungee.api.ChatColor;
 
public class SC extends JavaPlugin implements Listener {
   
     private String prefix = getConfig().getString("Prefix");
     private ArrayList<String> staff = new ArrayList<String>();
     
     @Override
     public void onEnable(){
         Bukkit.getServer().getPluginManager().registerEvents(this, this);
         PluginDescriptionFile pdfFile = getDescription();
 		 Logger logger = Logger.getLogger("Minecraft");
 		 logger.info("[" + pdfFile.getName() + "]" + " has been enabled (V." + pdfFile.getVersion() + ")");
         getConfig().options().copyDefaults(true);
         saveConfig();
     }
     
    @Override
 	public void onDisable() {
    	PluginDescriptionFile pdfFile = getDescription();
 		Logger logger = Logger.getLogger("Minecraft");
 		logger.info("[" + pdfFile.getName() + "]" + " has been disabled (V." + pdfFile.getVersion() + ")");
 		
 	}
     
        @Override
        public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
            String noPerm = ("I'm Sorry, but you do not have permission to perform this command. Please contact the server administrators if you believe that this is an error.");
           
            if(label.equalsIgnoreCase("sc")){
            if(!(sender.hasPermission("staffchat.sc"))){
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', noPerm));
                return true;
            }
            Player p = (Player) sender;
            if(!staff.contains(p.getName())){
                staff.add(p.getName());
                p.sendMessage(ChatColor.AQUA + "Staff-Chat Enabled.");
                return true;
            }else if (staff.contains(p.getName())){
                staff.remove(p.getName());
                p.sendMessage(ChatColor.AQUA + "Staff-Chat Disabled.");
                return true;
            }
            }
           
            return true;
        }
      
    @SuppressWarnings("deprecation")
	@EventHandler
     public void onChat(AsyncPlayerChatEvent e){
         Player p = e.getPlayer();
         if(staff.contains(p.getName())){
             e.setCancelled(true);
             for(Player pl : Bukkit.getOnlinePlayers()){
                 if(pl.hasPermission("staffchat.sc.re")){
                     String format = getConfig().getString("ChatFormat");
                     format = format.replaceAll("%player%", p.getName());
                     format = format.replaceAll("%displayname%", p.getDisplayName());
                     format = format.replaceAll("%prefix%", prefix);
                     format = format.replaceAll("%message%", e.getMessage());
                     pl.sendMessage(ChatColor.translateAlternateColorCodes('&', format));
                 }
             }
         }   
     }
         
     }