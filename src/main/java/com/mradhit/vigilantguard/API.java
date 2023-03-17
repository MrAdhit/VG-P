package com.mradhit.vigilantguard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class API {
    private static final AtomicInteger playerPerSecond = new AtomicInteger(0);
    private static final ArrayList<Integer> playerPerSecondGraph = new ArrayList<>(Collections.nCopies(25, 0));

    private static final AtomicInteger playerThrottleQuota = new AtomicInteger(0);

    private static boolean isLockdown = false;
    private static boolean isThrottled = false;

    private static boolean canRotateIP = true;
    private static String tempIP = "";

    public static class PPS {
        public static int get() {
            return playerPerSecond.get();
        }

        public static void increase() {
            new Thread(() -> {
                playerPerSecond.incrementAndGet();

                VigilantGuard.startScheduler(playerPerSecond::decrementAndGet, Constant.PLAYER_COUNTER_DECREMENT);
            }).start();
        }

        public static class Graph {
            private static int runTask = -1;

            public static ArrayList<Integer> get() {
                return playerPerSecondGraph;
            }

            public static void run() {
                playerPerSecondGraph.remove(0);
                playerPerSecondGraph.add(playerPerSecond.get());

                if (!Lockdown.get()) {
                    if (Lockdown.isAttacked()) {
                        if (!Lockdown.get()) {
                            runTask = VigilantGuard.startRepeatingScheduler(() -> {
                                if (Lockdown.isAttacked()) return;

                                Lockdown.set(false);
                                VigilantGuard.cancelRepeatingScheduler(runTask);
                            }, Constant.LOCKDOWN_TIME);
                        }

                        Lockdown.set(true);

                        TemporaryIP.tryRotate();
                    }
                }

                if (Throttle.isAttacked()) {
                    if (!Throttle.get()) {
                        VigilantGuard.startRepeatingScheduler(() -> {
                            Throttle.set(false);
                        }, Constant.THROTTLE_TIME);
                    }

                    Throttle.set(true);
                }
            }

            private static int calculateLoad(int count) {
                return playerPerSecondGraph.stream().reduce((acc, val) -> {
                    if (val >= count) return acc + 1;
                    return 0;
                }).orElse(0);
            }
        }
    }

    public static class TemporaryIP {
        public static String get() {
            return tempIP;
        }

        public static void rotate() {
            tempIP = Constant.IP_FORMAT.replace("{f}", randomPrefix());
            Util.Log(tempIP);
        }

        public static void tryRotate() {
            if (!canRotateIP) return;

            rotate();

            canRotateIP = false;
            VigilantGuard.startScheduler(() -> {
                canRotateIP = true;
            }, Constant.IP_ROTATE_TIME);
        }

        public static boolean canRotate() {
            return canRotateIP;
        }

        public static boolean isMatch(String hostname) {
            return Objects.equals(hostname, get());
        }

        private static String randomPrefix() {
            return Constant.IP_PREFIXES[(int) (Math.random() * (Constant.IP_PREFIXES.length - 1))].toLowerCase();
        }
    }

    public static class Lockdown {
        public static boolean get() {
            return isLockdown;
        }

        public static void set(boolean state) {
            if (state && !isLockdown) API.TemporaryIP.tryRotate();

            isLockdown = state;

            if (isLockdown) {
                Util.Log("§c§lMENGHIDUPKAN MODE LOCKDOWN!!!");
            }
        }

        public static void toggle() {
            isLockdown = !isLockdown;
        }

        public static boolean isAttacked() {
            return PPS.Graph.calculateLoad(4) > 4 || PPS.Graph.calculateLoad(5) > 3;
        }
    }

    public static class Throttle {
        public static boolean get() {
            return isThrottled;
        }

        public static void set(boolean state) {
            isThrottled = state;
        }

        public static void toggle() {
            isThrottled = !isThrottled;
        }

        public static boolean isAttacked() {
            return PPS.Graph.calculateLoad(2) > 5 || PPS.Graph.calculateLoad(1) > 8;
        }

        public static boolean isAllowed() {
            if (playerThrottleQuota.get() >= Constant.PLAYER_MAX_THROTTLE) return false;

            new Thread(() -> {
                playerThrottleQuota.incrementAndGet();

                VigilantGuard.startScheduler(playerThrottleQuota::decrementAndGet, Constant.PLAYER_THROTTLE_TIME);
            }).start();

            return true;
        }
    }
}
