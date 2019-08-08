/*
 * Decompiled with CFR 0_118.
 * 
 * Could not load the following classes:
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandExecutor
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.PluginDescriptionFile
 */
package me.netindev.command;

import me.netindev.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;

public class Command implements CommandExecutor {
public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            if (args.length == 0 || args.length > 1) {
                sender.sendMessage("[BungeeCompass] / Use: /bungeecompass reload.");
            }
            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("reload")) {
                    Main.plugin.reloadConfig();
                    sender.sendMessage("[BungeeCompass] / Config reloaded.");
                } else {
                    sender.sendMessage("[BungeeCompass] / Use: /bungeecompass reload.");
                }
            }
        } else {
            Player player = (Player) sender;
            if (jogador.hasPermission("bungeecompass.command")) {
                if (args.length == 0 || args.length > 1) {
                    player.sendMessage("\u00a7f[\u00a7aBungeeCompass\u00a7f] \u00a7aV: \u00a7f" + Main.plugin.getDescription().getVersion() + "\u00a7a.");
                    player.sendMessage("\u00a7aPlugin created by: \u00a7fnetindev\u00a7a.");
                    player.sendMessage("\u00a7aCommands:");
                    player.sendMessage("\u00a7f/bungeecompass reload / \u00a7aReload this plugin\u00a7f.");
                } else if (args.length == 1) {
                    if (args[0].equalsIgnoreCase("reload")) {
                        Main.plugin.reloadConfig();
                        sender.sendMessage("\u00a7f[\u00a7aBungeeCompass\u00a7f] \u00a7aConfig reloaded.");
                    } else {
                        sender.sendMessage("\u00a7f[\u00a7aBungeeCompass\u00a7f] \u00a7aUse: /bungeecompass reload.");
                    }
                }
            } else {
                player.sendMessage("\u00a7cNo permission.");
            }
        }
        return false;
    }
}

