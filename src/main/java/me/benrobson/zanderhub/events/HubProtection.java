package me.benrobson.zanderhub.events;

import me.benrobson.zanderhub.ZanderHubMain;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.player.*;
import org.bukkit.event.weather.WeatherChangeEvent;

public class HubProtection implements Listener {
    ZanderHubMain plugin;
    public HubProtection(ZanderHubMain plugin) {
        this.plugin = plugin;
    }

    // Stop Users from changing/modifying Armour Stands
    @EventHandler
    public void pasme(PlayerArmorStandManipulateEvent pasme) {
        pasme.setCancelled(true);
    }

    // Stop Users from dropping items.
    @EventHandler
    public void pcwe(PlayerDropItemEvent pdie) {
        pdie.setCancelled(true);
    }

    // Stop Users from modifying their inventories
    @EventHandler
    public void iie(InventoryInteractEvent iie) {
        iie.setCancelled(true);
    }

    // When a user joins, sets their Gamemode to Adventure
    @EventHandler(priority = EventPriority.HIGH)
    public void GamemodeAd(PlayerJoinEvent event) {
        event.getPlayer().setGameMode(GameMode.ADVENTURE);
    }

    // Cancel Block gravity
    @EventHandler(priority = EventPriority.HIGH)
    public void physics(final BlockPhysicsEvent event) {
        event.setCancelled(false);
    }

    // Block the weather from changing
    @EventHandler
    public void noWeather(final WeatherChangeEvent event) {
        event.setCancelled(true);
    }

    // Cancel out users from taking damage
    @EventHandler(priority = EventPriority.HIGH)
    public void noDamage(final EntityDamageEvent event) {
        event.setCancelled(true);
    }

    // Cancel out users taking damage from others
    @EventHandler(priority = EventPriority.HIGH)
    public void noDamage(final EntityDamageByEntityEvent event) {
        event.setCancelled(true);
    }

    // Block entity spawning and the use of spawn eggs
    @EventHandler
    public void onMobSpawn(CreatureSpawnEvent event) {
        CreatureSpawnEvent.SpawnReason reason = event.getSpawnReason();

        if (reason == CreatureSpawnEvent.SpawnReason.CHUNK_GEN || reason == CreatureSpawnEvent.SpawnReason.DEFAULT || reason == CreatureSpawnEvent.SpawnReason.NATURAL || reason == CreatureSpawnEvent.SpawnReason.NETHER_PORTAL || reason == CreatureSpawnEvent.SpawnReason.DISPENSE_EGG || reason == CreatureSpawnEvent.SpawnReason.REINFORCEMENTS) {
            if (event.getEntity().getType().equals(EntityType.VILLAGER) || event.getEntity().getType().equals(EntityType.PILLAGER)) {
                event.setCancelled(false);
            } else {
                event.setCancelled(true);
            }
        }
    }

    // Stop Chickens from laying eggs
    @EventHandler(priority = EventPriority.HIGH)
    public void noMobDrop(final EntityDropItemEvent event) {
        event.setCancelled(true);
    }

    // Cancel blocks from burning
    @EventHandler(priority = EventPriority.HIGH)
    public void noFire(final BlockBurnEvent event) {
        event.setCancelled(true);
    }

    // Cancel blocks from spreading
    @EventHandler(priority = EventPriority.HIGH)
    public void noSpread(final BlockSpreadEvent event) {
        event.setCancelled(true);
    }

    // Block liquid from flowing
    @EventHandler(priority = EventPriority.HIGH)
    public void noMelt(final BlockFromToEvent event) {
        event.setCancelled(true);
    }

    // Stop blocks from melting and disappearing
    @EventHandler(priority = EventPriority.HIGH)
    public void noFade(final BlockFadeEvent event) {
        event.setCancelled(true);
    }

    // Stops blocks forming or spreading
    @EventHandler(priority = EventPriority.HIGH)
    public void noForm(final BlockFormEvent event) {
        event.setCancelled(true);
    }

    // Blocks leaves from decaying
    @EventHandler(priority = EventPriority.HIGH)
    public void noBucket(final LeavesDecayEvent event) {
        event.setCancelled(true);
    }

    // Blocks buckets from being used
    @EventHandler(priority = EventPriority.HIGH)
    public void noBucket(final PlayerBucketEmptyEvent event) {
        event.setCancelled(true);
    }

    // Blocks buckets from being filled
    @EventHandler(priority = EventPriority.HIGH)
    public void noBucket(final PlayerBucketFillEvent event) {
        event.setCancelled(true);
    }

    // Block user from interacting or using any items
    @EventHandler(priority = EventPriority.HIGH)
    public void interactEntity(PlayerInteractEvent event) {
        Block clicked = event.getClickedBlock();

        if (event.getPlayer().hasPermission("zanderhub.build")) {
            event.setCancelled(false);
            return;
        }

        if (!event.hasItem()) {
            event.setCancelled(true);
        } else {
            // Allows access for Doors and Buttons to be used.
            if (clicked.getType() == Material.DARK_OAK_DOOR || clicked.getType() == Material.DARK_OAK_BUTTON || clicked.getType() == Material.STONE_BUTTON) {
                event.setCancelled(false);
                return;
            } else {
                event.setCancelled(true);
                event.setUseInteractedBlock(Event.Result.DENY);
                event.setUseItemInHand(Event.Result.DENY);
            }

            // Allows access for Pressure Plates to be used.
            if (event.getAction().equals(Action.PHYSICAL)) {
                if (event.getClickedBlock().getType() == Material.HEAVY_WEIGHTED_PRESSURE_PLATE || event.getClickedBlock().getType() == Material.LIGHT_WEIGHTED_PRESSURE_PLATE || event.getClickedBlock().getType() == Material.STONE_PRESSURE_PLATE || event.getClickedBlock().getType() == Material.BIRCH_PRESSURE_PLATE || event.getClickedBlock().getType() == Material.SPRUCE_PRESSURE_PLATE) {
                    event.setCancelled(false);
                    return;
                }
            }
        }
    }

    // Block user from interacting with entitys
    @EventHandler(priority = EventPriority.HIGH)
    public void interactEntity(PlayerInteractEntityEvent event) {
        event.setCancelled(true);
    }

    // Block users from picking up items
    @EventHandler(priority = EventPriority.HIGH)
    public void pickupItem(PlayerPickupItemEvent event) {
        event.setCancelled(true);
    }

    // Block users from dropping items
    @EventHandler(priority = EventPriority.HIGH)
    public void drop(final PlayerDropItemEvent event) {
        event.setCancelled(true);
    }

    // On players death, clear all items
    @EventHandler(priority = EventPriority.HIGH)
    public void death(final PlayerDeathEvent event) {
        event.getDrops().clear();
    }

    // Hide the normal join message
    @EventHandler(priority = EventPriority.HIGH)
    public void HideJoinMessage(PlayerJoinEvent event) {
        event.setJoinMessage(null);
    }

    // Hide the normal leave message
    @EventHandler(priority = EventPriority.HIGH)
    public void HideLeaveMessage(PlayerQuitEvent event) {
        event.setQuitMessage(null);
    }

    // Hide all death messages
    @EventHandler(priority = EventPriority.HIGH)
    public void HideDeathMessage(PlayerDeathEvent event) {
        event.setDeathMessage(null);
    }

    // If user looses hunger, instantly reset hunger
    @EventHandler(priority = EventPriority.HIGH)
    public void losinghunger(FoodLevelChangeEvent event) {
        event.setCancelled(true);
    }
}
