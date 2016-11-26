/*
 * Decompiled with CFR 0_118.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.configuration.file.FileConfiguration
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.Inventory
 *  org.bukkit.inventory.InventoryHolder
 *  org.bukkit.inventory.InventoryView
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.meta.ItemMeta
 *  org.bukkit.plugin.Plugin
 */
package me.netindev.utils;

import java.util.List;
import me.netindev.Main;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

public class Seletor {
    public Seletor(Player jogador) {
        Inventory inv = Bukkit.createInventory((InventoryHolder)jogador, (int)Main.plugin.getConfig().getInt("inv-size"), (String)Main.plugin.getConfig().getString("inv-name").replace("&", "\u00a7"));
        int i = 1;
        do {
            ItemStack stack = new ItemStack(Main.plugin.getConfig().getInt("server." + i + ".itemid"));
            ItemMeta meta = stack.getItemMeta();
            meta.setDisplayName(Main.plugin.getConfig().getString("server." + i + ".display").replace("&", "\u00a7"));
            if (Main.plugin.getConfig().getBoolean("server." + i + ".activatelore")) {
                meta.setLore(Main.plugin.getConfig().getStringList("server." + i + ".lore"));
            }
            stack.setItemMeta(meta);
            stack.setAmount(Main.plugin.getConfig().getInt("server." + i + ".itemamount"));
            inv.setItem(Main.plugin.getConfig().getInt("server." + i + ".invslot"), stack);
        } while (++i <= Main.plugin.getConfig().getInt("total-servers"));
        jogador.openInventory(inv);
    }
}

