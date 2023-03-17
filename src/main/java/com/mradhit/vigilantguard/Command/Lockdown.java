package com.mradhit.vigilantguard.Command;

import com.mradhit.vigilantguard.API;
import com.mradhit.vigilantguard.Permission;
import com.mradhit.vigilantguard.Util;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

import java.util.Arrays;

public class Lockdown extends Command {
    public Lockdown() {
        super("lockdown");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!sender.hasPermission(Permission.LOCKDOWN)) return;

        String arguments = Arrays.toString(Util.getEnumNames(Arguments.class)).toLowerCase();
        if (args.length < 1) { sender.sendMessage(Util.ColoredText("/lockdown " + arguments)); return; }

        String arg = args[0];

        try {
            switch (Arguments.valueOf(arg.toUpperCase())) {
                case STATUS:
                    sender.sendMessage(Util.ColoredText("Mode lockdown " + (API.Lockdown.get() ? "&chidup" : "&amati")));
                    break;
                case ON:
                    sender.sendMessage(Util.ColoredText("&cMenghidupkan mode lockdown!"));
                    API.Lockdown.set(true);
                    break;
                case OFF:
                    sender.sendMessage(Util.ColoredText("&aMematikan mode lockdown!"));
                    API.Lockdown.set(false);
                    break;
                case IP:
                    if (!API.Lockdown.get()) {
                        sender.sendMessage(Util.ColoredText("Mode lockdown mati"));
                        break;
                    }
                    sender.sendMessage(Util.ColoredText("&b" + API.TemporaryIP.get()));
                    break;
                case ROTATE:
                    if (!API.TemporaryIP.canRotate()) {
                        sender.sendMessage(Util.ColoredText("Rotate IP masih cooldown, coba lagi nanti"));
                        break;
                    }
                    API.TemporaryIP.rotate();
                    sender.sendMessage(Util.ColoredText("Sukses merotate IP"));
                    break;
            }
        } catch (IllegalArgumentException e) {
            sender.sendMessage(Util.ColoredText("/lockdown " + arguments));
        }
    }

    private enum Arguments {
        STATUS,
        ON,
        OFF,
        IP,
        ROTATE
    }
}
