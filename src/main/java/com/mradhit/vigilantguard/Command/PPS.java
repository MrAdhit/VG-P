package com.mradhit.vigilantguard.Command;

import com.mradhit.vigilantguard.API;
import com.mradhit.vigilantguard.Permission;
import com.mradhit.vigilantguard.Util;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class PPS extends Command {
    public PPS() {
        super("pps");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!sender.hasPermission(Permission.PPS)) return;

        sender.sendMessage(Util.ColoredText("&6PPS Graph: &a" + API.PPS.Graph.get().toString()));
    }
}
