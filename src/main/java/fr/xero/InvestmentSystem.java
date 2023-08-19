package fr.xero;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class InvestmentSystem {

    public String onCommand(CommandSender sender, Command cmd, String label, String[] args) {


    if (cmd.getName().equalsIgnoreCase("invest")) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (args.length >= 2 && args[0].equalsIgnoreCase("start")) {
                String investName = args[1];
                investmentSystem.startInvestment(player, investName);
                return true;
            }

    private final Plugin plugin;

    public InvestmentSystem(Plugin plugin) {
        this.plugin = plugin;
    }

    public void startInvestment(Player player, String investName) {
        // Get the investment duration and reward from the configuration
        int requiredTime = plugin.getConfig().getInt("investments." + investName + ".duration");
        int reward = plugin.getConfig().getInt("investments." + investName + ".reward");

        Investment investment = new Investment(player, investName, requiredTime, reward);
        investment.start();
    }

    private class Investment {
        private final Player player;
        private final String investName;
        private final int requiredTime;
        private final int reward;
        private int progress;

        public Investment(Player player, String investName, int requiredTime, int reward) {
            this.player = player;
            this.investName = investName;
            this.requiredTime = requiredTime;
            this.reward = reward;
            this.progress = 0;
        }

        public void start() {
            // Inside the Investment class's start() method
            Bukkit.getScheduler().runTaskTimer(plugin, () -> {
                if (progress < requiredTime) {
                    // Increase progress
                    progress++;

                    // Update investment data
                    plugin.getPlayerData().set(player.getUniqueId() + "." + investName + ".progress", progress);
                    plugin.saveConfig();

                    // Calculate percentage
                    double percentage = (double) progress / requiredTime * 100;

                    // Update player's title or bar progress
                    updatePlayerUI(player, percentage);

                } else {
                    // Investment completed
                    player.sendMessage("Investment " + investName + " completed!");

                    // Give the reward using Vault or other methods
                    // Implement your own reward system here

                    // Remove investment data
                    plugin.getPlayerData().set(player.getUniqueId() + "." + investName, null);
                    plugin.saveConfig();

                    // Cancel the task
                    Bukkit.getScheduler().cancelTask(this.getTaskId());
                }
            }, 0L, 20L); // Run every second
        }
    }

    }
        }
        private void updatePlayerUI(Player Player player;
        player, double percentage) {
            String progressBar = generateProgressBar(percentage);

            String title = "Investment Progress";
            String subTitle = " [ " + progressBar + " ] " + String.format("%.1f", percentage) + "%";

            // Send the title and subtitle to the player
            player.sendTitle(title, subTitle, 0, 20, 0); // Title stays for 1 second, subtitle stays for 2 seconds
        }

        private String generateProgressBar(double percentage) {
            int totalBars = 10;
            int filledBars = (int) (totalBars * (percentage / 100));

            StringBuilder progressBar = new StringBuilder();
            for (int i = 0; i < totalBars; i++) {
                if (i < filledBars) {
                    progressBar.append("|");
                } else {
                    progressBar.append(" ");
                }
            }

            return progressBar.toString();
    }
        return false;
    }

