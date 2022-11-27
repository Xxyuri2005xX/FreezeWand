package it.xxyuri2005xx.freezewand.Listeners;

import it.xxyuri2005xx.freezewand.Main;
import it.xxyuri2005xx.freezewand.Util.ServerUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;

import java.util.List;

public class FreezeListener implements Listener {

    public List<Player> frozen;

    @EventHandler
    public void onInteract(PlayerInteractAtEntityEvent event) {
        Player player = event.getPlayer();
        Player target = (Player) event.getRightClicked();
        if (player.hasPermission("freeze.freeze") && player.getItemInHand().getType() == Material.BLAZE_ROD) {
            player.sendMessage(ServerUtil.color("&aPlayer {target} have benn frozen").replace("{target}", target.getName()));
            frozen.add(target);
            Bukkit.getScheduler().runTaskLaterAsynchronously(Main.getInstance(), () -> {
                frozen.remove(target);
                target.sendMessage(ServerUtil.color("&cYou got frozen!"));
            }, 10 * 20L);
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Location from = event.getFrom();
        Location to = event.getTo();
        if (frozen.contains(player) &&
                from != to) {
            player.teleport(event.getFrom(), PlayerTeleportEvent.TeleportCause.PLUGIN);
            player.sendMessage(ServerUtil.color("&cYou are frozen!"));
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if(frozen.contains(player)) {
            event.setCancelled(true);
            player.sendMessage(ServerUtil.color("&cYou are frozen!"));
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if(frozen.contains(player)) {
            event.setCancelled(true);
            player.sendMessage(ServerUtil.color("&cYou are frozen!"));
        }
    }

    @EventHandler
    public void onTeleport(PlayerTeleportEvent event) {
        Player player = event.getPlayer();
        if(frozen.contains(player)) {
            if(event.getCause().equals(PlayerTeleportEvent.TeleportCause.UNKNOWN)) return;
            event.setCancelled(true);
            player.sendMessage(ServerUtil.color("&cYou are frozen!"));
        }
    }

    @EventHandler
    public void onFoodChange(FoodLevelChangeEvent event) {
        Player player = (Player)event.getEntity();
        if (frozen.contains(player))
            event.setCancelled(true);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if(frozen.contains(player)) {
            event.setCancelled(true);
            player.sendMessage(ServerUtil.color("&cYou are frozen!"));
        }
    }

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        if(frozen.contains(player)) {
            event.setCancelled(true);
            player.sendMessage(ServerUtil.color("&cYou are frozen!"));
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        if(frozen.contains(player)) {
            event.setCancelled(true);
            player.sendMessage(ServerUtil.color("&cYou are frozen!"));
        }
    }

    @EventHandler
    public void onAsyncPlayerChat(AsyncPlayerChatEvent event){
        Player player = event.getPlayer();
        if(frozen.contains(player)){
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event){
        if(event.getEntity() instanceof Player){
            Player player = (Player) event.getEntity();
            if(frozen.contains(player)){
                event.setCancelled(true);
                if(event.getDamager() instanceof Player){
                    event.getDamager().sendMessage(ServerUtil.color("&cTarget player is frozen"));
                }
            }
        }
        if(event.getDamager() instanceof Player){
            Player player = (Player) event.getDamager();
            if(frozen.contains(player)){
                event.setCancelled(true);
                player.sendMessage(ServerUtil.color("&cYou are frozen!"));
            }
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if(event.getEntityType().equals(EntityType.PLAYER)){
            Player player = (Player) event.getEntity();
            if(frozen.contains(player)){
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (frozen.contains(player)) {
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (p.hasPermission("freeze.freeze"))
                    p.sendMessage(ServerUtil.color("&cPlayer {target} left the server!").replace("{target}", player.getName()));
            }
        }
    }
}
