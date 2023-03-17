package com.mradhit.vigilantguard;

import net.md_5.bungee.api.chat.TextComponent;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class Util {
    public static class Time {
        private final long time;
        private final TimeUnit unit;

        public Time(long time, TimeUnit unit) {
            this.time = time;
            this.unit = unit;
        }

        public long getTime() {
            return time;
        }

        public TimeUnit getUnit() {
            return unit;
        }
    }

    public static TextComponent ColoredText(String text) {
        return new TextComponent(text.replaceAll("&", "§"));
    }

    public static String[] getEnumNames(Class<? extends Enum<?>> e) {
        return Arrays.stream(e.getEnumConstants()).map(Enum::name).toArray(String[]::new);
    }

    public static <T> void Log(T message) {
        System.out.println("[§c" + Constant.PLUGIN_NAME + "§r] " + message);
    }
}
