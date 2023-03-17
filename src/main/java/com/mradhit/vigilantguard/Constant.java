package com.mradhit.vigilantguard;

import net.md_5.bungee.api.chat.TextComponent;

import java.util.concurrent.TimeUnit;

public class Constant {
    public static final String PLUGIN_NAME = "VigilantGuard";

    public static final int PLAYER_MAX_THROTTLE = 3;
    public static final Util.Time PLAYER_THROTTLE_TIME = new Util.Time(10, TimeUnit.SECONDS);

    public static final Util.Time PLAYER_COUNTER_DECREMENT = new Util.Time(1, TimeUnit.SECONDS);

    public static final Util.Time LOCKDOWN_TIME = new Util.Time(10, TimeUnit.MINUTES);
    public static final Util.Time THROTTLE_TIME = new Util.Time(1, TimeUnit.MINUTES);

    public static final String[] IP_PREFIXES = {"Axlend", "Bravio", "Crixus", "Drogan", "Erendir", "Fyodor", "Grimald", "Haldor", "Ignius", "Jarnulf", "Kethren", "Lysande", "Maldrek", "Nironos", "Othello", "Paxton", "Quillon", "Ragnarr", "Selvius", "Tandros", "Uldrich", "Valtair", "Wynton", "Xandros", "Yldaris", "Zarekai", "Arctos", "Balinor", "Crommell", "Drakken", "Elsinor", "Falkner", "Gilden", "Heliose", "Isadora", "Jaxxon", "Kalidor", "Larethar", "Mystras", "Naxos", "Orinthe", "Pyrrhus", "Quirion", "Rivenne", "Sarekka", "Tarquin", "Ulfgar", "Vespera", "Wrynnin", "Valor"};
    public static final String IP_FORMAT = "{f}.lockdown.lightnetworkmc.xyz";

    public static final Util.Time IP_ROTATE_TIME = new Util.Time(10, TimeUnit.SECONDS);

    public static final String[] PRIVATE_IP = {"192", "172", "10"};

    public static final String LOCKDOWN_MESSAGE = "&c&lServer Lockdown&r &8| &fMasuk Dengan IP Dibawah§r";
    public static final TextComponent THROTTLING_MESSAGE = Util.ColoredText("&f&lTolong masuk kembali setelah beberapa detik");
}
