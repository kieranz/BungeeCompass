/*
 * Decompiled with CFR 0_118.
 * 
 * Could not load the following classes:
 *  org.bukkit.Material
 *  org.bukkit.configuration.file.FileConfiguration
 *  org.bukkit.entity.HumanEntity
 *  org.bukkit.entity.Item
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 *  org.bukkit.event.inventory.InventoryClickEvent
 *  org.bukkit.event.player.PlayerDropItemEvent
 *  org.bukkit.event.player.PlayerInteractEvent
 *  org.bukkit.event.player.PlayerJoinEvent
 *  org.bukkit.inventory.Inventory
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.PlayerInventory
 *  org.bukkit.inventory.meta.ItemMeta
 *  org.bukkit.plugin.Plugin
 */
package me.netindev.listener;

import me.netindev.Main;
import me.netindev.utils.Seletor;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

public class Events implements Listener {

	Main plugin;

	public Events(Main main) {
		plugin = main;
	}

	@EventHandler
	private void onClickInventory(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		if (event.getInventory().getName().equals(Main.plugin.getConfig().getString("inv-name").replace("&", "\u00a7"))
				&& event.getCurrentItem() != null && event.getCurrentItem().getType() != null) {
			event.setCancelled(true);
			int i = 1;
			do {
				if (event.getCurrentItem().getTypeId() != Main.plugin.getConfig().getInt("server." + i + ".itemid")
						|| !event.getCurrentItem().getItemMeta().getDisplayName().equals(
								Main.plugin.getConfig().getString("server." + i + ".display").replace("&", "\u00a7")))
					continue;
				Main.sendPlayer(event, Main.plugin.getConfig().getString("server." + i + ".bungee-connect"));
				player.closeInventory();
			} while (++i <= Main.plugin.getConfig().getInt("total-servers"));
		}
	}

	@EventHandler
	private void onJoin(PlayerJoinEvent event) {
		final Player player = event.getPlayer();
		this.plugin.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable() {

			@Override
			public void run() {
				if (player.getWorld().getName().equalsIgnoreCase(Main.plugin.getConfig().getString("compass-world"))) {
					if (Main.plugin.getConfig().getBoolean("join-compass")) {
						ItemStack stack = new ItemStack(Material.COMPASS);
						ItemMeta meta = stack.getItemMeta();
						meta.setDisplayName(Main.plugin.getConfig().getString("compass-name").replace("&", "\u00a7"));
						stack.setItemMeta(meta);
						player.getInventory().setItem(Main.plugin.getConfig().getInt("player-slot"), stack);
					}
				}

			}
		}, 10);

	}

	@EventHandler
	private void onSwitch(PlayerChangedWorldEvent event) {
		final Player player = event.getPlayer();
		this.plugin.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable() {
			@Override
			public void run() {
				if (player.getWorld().getName().equalsIgnoreCase(Main.plugin.getConfig().getString("compass-world"))) {
					if (Main.plugin.getConfig().getBoolean("join-compass")) {
						ItemStack stack = new ItemStack(Material.COMPASS);
						ItemMeta meta = stack.getItemMeta();
						meta.setDisplayName(Main.plugin.getConfig().getString("compass-name").replace("&", "\u00a7"));
						stack.setItemMeta(meta);
						player.getInventory().setItem(Main.plugin.getConfig().getInt("player-slot"), stack);
					}
				}
			}
		}, 10);
	}

	@EventHandler
	private void onClick(PlayerInteractEvent event) {
		if (event.getPlayer().getItemInHand().getType() == Material.COMPASS
				&& event.getPlayer().hasPermission("bungeecompass.use")
				&& event.getPlayer().getItemInHand().getItemMeta().getDisplayName()
						.equals(Main.plugin.getConfig().getString("compass-name").replace("&", "\u00a7"))) {
			event.setCancelled(true);
			new me.netindev.utils.Seletor(event.getPlayer());
		}
	}

	@EventHandler
	private void onDrop(PlayerDropItemEvent event) {
		if (event.getItemDrop().getItemStack().getType() == Material.COMPASS
				&& event.getItemDrop().getItemStack().getItemMeta().getDisplayName()
						.equals(Main.plugin.getConfig().getString("compass-name").replace("&", "\u00a7"))
				&& !Main.plugin.getConfig().getBoolean("compass-drop")) {
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	private void onMove(InventoryClickEvent event){
		ItemStack item = event.getCurrentItem();
		if (item != null){
			if (item.getType() == Material.COMPASS && item.getItemMeta().getDisplayName()
					.equals(Main.plugin.getConfig().getString("compass-name").replace("&", "\u00a7"))) {
				event.setCancelled(true);
			}
		}
		
	}
}
