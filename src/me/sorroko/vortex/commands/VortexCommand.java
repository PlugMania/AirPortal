package me.sorroko.vortex.commands;


import me.sorroko.vortex.AirPortal;
import me.sorroko.vortex.Util;
import me.sorroko.vortex.Vortex;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Map.Entry;


public class VortexCommand implements CommandExecutor {

	AirPortal plugin;
	public VortexCommand(AirPortal instance) {
		plugin = instance;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
		if(command.getName().equalsIgnoreCase("airportal")){
			if(!(sender instanceof Player)){
				Util.sendMessageNotPlayer(sender);
				return true;
			}
			
			Player player = (Player) sender;
			if(!plugin.hasPermission(player, "admin")){
				Util.sendMessageNoPerms(sender);
				return true;
			}

			if(args.length == 0){
				player.sendMessage(Util.formatMessage(ChatColor.GOLD + "=-=-=-=-=-=-=-=-=-=-=-=" + ChatColor.DARK_AQUA + " AirPortal Commands " + ChatColor.GOLD + "=-=-=-=-=-=-=-=-=-=-=-="));
				player.sendMessage(Util.formatMessage(ChatColor.GRAY + "/airportal create [name] - Creates portal [name]"));
				player.sendMessage(Util.formatMessage(ChatColor.GRAY + "/airportal remove [name] - Removes portal [name]"));
				player.sendMessage(Util.formatMessage(ChatColor.GRAY + "/airportal link [name] [target_name] - Creates teleport from [name] to [target_name]"));
				player.sendMessage(Util.formatMessage(ChatColor.GRAY + "/airportal height [name] [value] - Sets thew height of [name], values: low, normal, space"));
				return true;
			}
			
			if(args.length > 0){
				
				if(args[0].equalsIgnoreCase("create") && args.length >= 2){
					plugin.VortexManager.setVortex(args[1], new Vortex(((Player)sender).getLocation(),"normal","",2));
					player.sendMessage(ChatColor.DARK_GRAY + "AirPortal created");
					
					return true;
				} else if(args[0].equalsIgnoreCase("remove") && args.length >= 2){
					
					plugin.VortexManager.removeVortex(args[1]);
					sender.sendMessage("Removing portal '" + args[1] + "'.");
						return true;
					
				} else if(args[0].equalsIgnoreCase("link") && args.length >= 3){
				plugin.VortexManager.getVortex(args[1]).destination=args[2];
				sender.sendMessage("Linking portal '" + args[1] + "' to portal '" + args[2] + "'.");
						return true;
					
				} else if(args[0].equalsIgnoreCase("height") && args.length >= 3){
					plugin.VortexManager.getVortex(args[1]).height=args[2];
					sender.sendMessage("set " + args[0] + " to '" + args[2] + "' on portal '" + args[1] + "'.");
					return true;
					
				} else if(args[0].equalsIgnoreCase("radius") && args.length >= 3){
					plugin.VortexManager.getVortex(args[1]).radius=Integer.parseInt(args[2]);
					sender.sendMessage("set " + args[0] + " to '" + args[2] + "' on portal '" + args[1] + "'.");
					return true;
					
				} else {}
			}
				if(args[0].equalsIgnoreCase("list")) {
				for(Entry<String, me.sorroko.vortex.Vortex> entry: plugin.VortexManager.getVortexEntries()){
					player.sendMessage(entry.getKey() + "->" + entry.getValue().destination + " [" + entry.getValue().height + "]");
				}
				player.sendMessage(ChatColor.DARK_GRAY+ "If you can't see all the portal scroll the chat!");
				return true;
			
			}
	}
		return false;

}

}