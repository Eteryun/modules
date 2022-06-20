package com.eteryun.scale.commands;

import com.eteryun.scale.extensions.IPlayer;
import com.google.common.collect.ImmutableList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.craftbukkit.v1_18_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PlayerScaleCommand extends BukkitCommand {

    @SuppressWarnings("serial")
    private static final ArrayList<String> sizes = new ArrayList<String>() {
        {
            add("0.25");
            add("0.5");
            add("1");
            add("1.25");
            add("1.5");
            add("2");
        }
    };

    public PlayerScaleCommand() {
        super("setscale");
        this.setPermission("eteryun.scale");
    }

    @Override
    public boolean execute(@NotNull CommandSender commandSender, @NotNull String alias, @NotNull String[] arguments) {
        if (!testPermission(commandSender) || !(commandSender instanceof Player)) {
            return true;
        } else {
            if (arguments.length >= 1 && arguments.length<=2){
                Player player = (Player) commandSender;
                float scale = 1F;
                if (arguments.length == 2) {
                    player = Bukkit.getPlayer(arguments[0]);
                    if (player == null)
                        commandSender.sendMessage(ChatColor.RED + "Não foi possível localizar este player");

                    scale = Float.parseFloat(arguments[1]);
                } else {
                    try{
                        scale = Float.parseFloat(arguments[0]);
                    } catch (Exception e){
                        commandSender.sendMessage(ChatColor.RED + "Informe um número valido.");
                        return false;
                    }
                }

                ((IPlayer)((CraftPlayer) player).getHandle()).setScaleRender(scale);
                commandSender.sendMessage("Escala do player setada para " + ChatColor.YELLOW + String.valueOf(scale) + "F");
            }else {
                commandSender.sendMessage("Utilize: " + ChatColor.RED + "/setscale <player> <scale>");
            }
        }
        return false;
    }

    @NotNull
    public List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) {
        if (args.length == 1) {
            List<String> completions = new ArrayList<>();
            String toComplete = args[0].toLowerCase(Locale.ENGLISH);
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (StringUtil.startsWithIgnoreCase(player.getName(), toComplete))
                    completions.add(player.getName());
            }
            return completions;
        } else if (args.length == 2) {
            return sizes;
        }
        return ImmutableList.of();
    }
}
