package com.zayiflik;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class ZayiflikGostergesi extends JavaPlugin {
    private final Set<UUID> aktifOyuncular = new HashSet<>();

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(new CanKontrolListener(), this);
        getLogger().info("ZayiflikGostergesi Plugin aktif! (1.16.5 - 1.21 uyumlu)");
    }

    @Override
    public void onDisable() {
        getLogger().info("ZayiflikGostergesi Plugin devre disi!");
        aktifOyuncular.clear();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Bu komut sadece oyuncular için!");
            return true;
        }
        Player player = (Player) sender;
        if (args.length == 0) {
            player.sendMessage(ChatColor.YELLOW + "/zayıflıkgostergesi aç" + ChatColor.GRAY + " veya " + ChatColor.YELLOW + "/zayıflıkgostergesi kapat" + ChatColor.GRAY + " yazabilirsin.");
            return true;
        }
        if (args[0].equalsIgnoreCase("aç")) {
            aktifOyuncular.add(player.getUniqueId());
            player.sendMessage(ChatColor.GREEN + "Zayıflık göstergesi açıldı!");
        } else if (args[0].equalsIgnoreCase("kapat")) {
            aktifOyuncular.remove(player.getUniqueId());
            player.sendMessage(ChatColor.RED + "Zayıflık göstergesi kapatıldı!");
        } else {
            player.sendMessage(ChatColor.YELLOW + "/zayıflıkgostergesi aç" + ChatColor.GRAY + " veya " + ChatColor.YELLOW + "/zayıflıkgostergesi kapat" + ChatColor.GRAY + " yazabilirsin.");
        }
        return true;
    }

    class CanKontrolListener implements Listener {
        @EventHandler
        public void onDamage(EntityDamageEvent event) {
            if (!(event.getEntity() instanceof Player)) return;
            Player player = (Player) event.getEntity();
            if (!aktifOyuncular.contains(player.getUniqueId())) return;
            double maxHealth = player.getMaxHealth();
            double newHealth = player.getHealth() - event.getFinalDamage();
            if (newHealth > 0 && newHealth <= maxHealth * 0.3) {
                // Mesaj türünü belirle
                String ozelMesaj = null;
                if (event.getCause() == EntityDamageEvent.DamageCause.FIRE || event.getCause() == EntityDamageEvent.DamageCause.FIRE_TICK || event.getCause() == EntityDamageEvent.DamageCause.LAVA) {
                    ozelMesaj = ChatColor.GOLD + "Ateşten yanıyorsun! Suya gir ya da kaç!";
                } else if (event.getCause() == EntityDamageEvent.DamageCause.FALL) {
                    ozelMesaj = ChatColor.AQUA + "Düşerek hasar aldın! Yüksekten atlama!";
                } else if (event instanceof EntityDamageByEntityEvent) {
                    EntityDamageByEntityEvent edee = (EntityDamageByEntityEvent) event;
                    if (edee.getDamager() instanceof LivingEntity) {
                        LivingEntity damager = (LivingEntity) edee.getDamager();
                        String mob = damager.getType().toString();
                        ozelMesaj = ChatColor.RED + mob + ChatColor.YELLOW + " tarafından saldırıya uğradın! Savunmaya geç!";
                    }
                }
                // Ekran uyarısı (Title) - 1.16.5+ uyumlu
                try {
                    player.sendTitle(ChatColor.RED + "Uyarı!", ChatColor.YELLOW + "Zayıflıyorsun, kendini koru", 10, 60, 10);
                } catch (Exception e) {
                    player.sendMessage(ChatColor.RED + "=== UYARI! ===");
                    player.sendMessage(ChatColor.YELLOW + "Zayıflıyorsun, kendini koru!");
                }
                if (ozelMesaj != null) {
                    player.sendMessage(ozelMesaj);
                } else {
                    player.sendMessage(ChatColor.GOLD + "PVP Taktik: Kaç, pozisyon al, blok kullan!");
                    player.sendMessage(ChatColor.GOLD + "PVP Taktik: Takım arkadaşlarından yardım iste!");
                }
                try {
                    PotionEffectType resistance = PotionEffectType.getByName("DAMAGE_RESISTANCE");
                    if (resistance != null) {
                        player.addPotionEffect(new PotionEffect(resistance, 20*10, 1));
                    }
                    PotionEffectType slowness = PotionEffectType.getByName("SLOW");
                    if (slowness != null) {
                        player.addPotionEffect(new PotionEffect(slowness, 20*10, 0));
                    }
                } catch (Exception e) {
                    try {
                        PotionEffectType resistance = PotionEffectType.getByName("DAMAGE_RESISTANCE");
                        PotionEffectType slowness = PotionEffectType.getByName("SLOW");
                        if (resistance != null) {
                            player.addPotionEffect(new PotionEffect(resistance, 200, 1));
                        }
                        if (slowness != null) {
                            player.addPotionEffect(new PotionEffect(slowness, 200, 0));
                        }
                    } catch (Exception ex) {
                        player.sendMessage(ChatColor.GREEN + "Koruma efektleri aktif!");
                    }
                }
            }
        }
    }
} 