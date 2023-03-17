package com.mradhit.vigilantguard;

import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.event.PreLoginEvent;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.event.EventHandler;

import java.util.Arrays;
import java.util.Objects;

public class Listener implements net.md_5.bungee.api.plugin.Listener {
    @EventHandler
    public void onProxyPing(ProxyPingEvent event) {
        int playerProtocol = event.getResponse().getVersion().getProtocol();
        int protocol = Math.min(Math.max(playerProtocol, 340), 761);

        event.getResponse().setVersion(new ServerPing.Protocol("1.12.x - 1.19.x (VigilantGuard)", protocol));

        if (!API.Lockdown.get()) return;
        if (API.TemporaryIP.isMatch(event.getConnection().getVirtualHost().getHostName())) return;

        String ip = " ".repeat(API.TemporaryIP.get().length() / 4) + API.TemporaryIP.get();

        event.getResponse().setDescriptionComponent(Util.ColoredText("   " + Constant.LOCKDOWN_MESSAGE + "\n&b" + ip));
    }

    @EventHandler
    public void onPlayerPreLogin(PreLoginEvent event) {
        API.PPS.increase();

        if (API.Lockdown.get()) {
            String joinedIP = event.getConnection().getVirtualHost().getHostName();

            if (Arrays.stream(Constant.PRIVATE_IP).anyMatch(joinedIP::startsWith)) return;

            if (Objects.equals(joinedIP, API.TemporaryIP.get())) {
                if (!API.TemporaryIP.canRotate()) return;

                if (API.Lockdown.isAttacked()) {
                    API.TemporaryIP.tryRotate();
                }

                return;
            }

            event.setCancelled(true);
            event.setCancelReason(Util.ColoredText(Constant.LOCKDOWN_MESSAGE + "\n&b" + API.TemporaryIP.get()));
        }

        if (API.Throttle.get()) {
            if (API.Throttle.isAllowed()) return;

            event.setCancelled(true);
            event.setCancelReason(Constant.THROTTLING_MESSAGE);
        }

        new Thread(API.PPS.Graph::run).start();
    }
}
