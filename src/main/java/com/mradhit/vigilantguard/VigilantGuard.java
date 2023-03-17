package com.mradhit.vigilantguard;

import com.mradhit.vigilantguard.Command.Lockdown;
import com.mradhit.vigilantguard.Command.PPS;
import net.md_5.bungee.api.plugin.Plugin;

import java.util.concurrent.TimeUnit;

public final class VigilantGuard extends Plugin {
    public static VigilantGuard plugin;
    public static final String version = "dmVyc2lvbmVuYw==";

    @Override
    public void onEnable() {
        plugin = this;

        this.getProxy().getPluginManager().registerListener(this, new Listener());

        this.getProxy().getPluginManager().registerCommand(this, new Lockdown());
        this.getProxy().getPluginManager().registerCommand(this, new PPS());

        startRepeatingScheduler(API.PPS.Graph::run, new Util.Time(500, TimeUnit.MILLISECONDS));

        Util.Log("Enabling §c" + Constant.PLUGIN_NAME + "§r build (" + version + ")");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static void startScheduler(Runnable task, Util.Time time) {
        plugin.getProxy().getScheduler().schedule(plugin, task, time.getTime(), time.getUnit());
    }

    public static int startRepeatingScheduler(Runnable task, Util.Time time) {
        return plugin.getProxy().getScheduler().schedule(plugin, task, 0, time.getTime(), time.getUnit()).getId();
    }

    public static void cancelRepeatingScheduler(int id) {
        plugin.getProxy().getScheduler().cancel(id);
    }
}
