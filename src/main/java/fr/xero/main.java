package fr.xero;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class main extends JavaPlugin {

    private FileConfiguration investConfig;
    private FileConfiguration config;
    private FileConfiguration playerData;
    private InvestmentSystem investmentSystem;

    @Override
    public void onEnable() {
        PluginManager pluginManager = Bukkit.getPluginManager();
        investmentSystem = new InvestmentSystem(this);
        pluginManager.registerEvents(new InvestmentListener(this), this);

        // Load configuration files
        investConfig = getConfig("invest.yml");
        config = getConfig();
        playerData = getConfig("data.yml");

        getLogger().info("InvestmentPlugin has been enabled.");
    }

    @Override
    public void onDisable() {
        getLogger().info("InvestmentPlugin has been disabled.");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("invest")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;

                if (args.length >= 1) {
                    String subCommand = args[0].toLowerCase();

                    switch (subCommand) {
                        case "setspawn":
                            if (args.length >= 2) {
                                String investName = args[1];
                                // Handle /invest setspawn sub-command
                                investConfig.set("investments." + investName + ".spawn", player.getLocation());
                                saveConfig(investConfig, "invest.yml");
                                player.sendMessage("Investment spawn point set for " + investName);
                            } else {
                                player.sendMessage("Usage: /invest setspawn <investment_name>");
                            }
                            break;

                        // Handle other sub-commands

                        default:
                            player.sendMessage("Unknown sub-command. Use /invest help for help.");
                            break;
                    }
                } else {
                    // Handle /invest without sub-commands
                    player.sendMessage("Usage: /invest <sub_command>");
                }

            } else {
                sender.sendMessage("Only players can use this command.");
            }
            return true;
        }
        return false;
    }

    // Load or create a configuration file
    private FileConfiguration getConfig(String fileName) {
        File file = new File(getDataFolder(), fileName);
        if (!file.exists()) {
            saveResource(fileName, false);
        }
        return YamlConfiguration.loadConfiguration(file);
    }

    // Save a configuration file
    private void saveConfig(FileConfiguration config, String fileName) {
        try {
            config.save(new File(getDataFolder(), fileName));
        } catch (IOException e) {
            getLogger().severe("Could not save " + fileName + ": " + e.getMessage());
        }
    }

    public FileConfiguration getInvestConfig() {
        return investConfig;
    }

    public FileConfiguration getPlayerData() {
        return playerData;
    }

    // Add methods to handle other parts of the plugin (e.g., handling investments)
}
