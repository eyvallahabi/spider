package io.github.shiryu.spider.util.version;

import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

public enum ServerVersion {

    UNKNOWN, V1_7, V1_8, V1_9, V1_10, V1_11, V1_12, V1_13, V1_14, V1_15, V1_16, V1_17, V1_18, V1_19, V1_20;

    private final static String serverPackagePath = Bukkit.getServer().getClass().getPackage().getName();
    private final static String serverPackageVersion = serverPackagePath.substring(serverPackagePath.lastIndexOf('.') + 1);
    private final static String serverReleaseVersion = serverPackageVersion.indexOf('R') != -1 ? serverPackageVersion.substring(serverPackageVersion.indexOf('R') + 1) : "";
    private final static ServerVersion serverVersion = getVersion();

    private static ServerVersion getVersion() {
        for (ServerVersion version : values()) {
            if (serverPackageVersion.toUpperCase().startsWith(version.name())) {
                return version;
            }
        }
        return UNKNOWN;
    }

    public boolean isLessThan(@NotNull final ServerVersion other) {
        return this.ordinal() < other.ordinal();
    }

    public boolean isAtOrBelow(@NotNull final ServerVersion other) {
        return this.ordinal() <= other.ordinal();
    }

    public boolean isGreaterThan(@NotNull final ServerVersion other) {
        return this.ordinal() > other.ordinal();
    }

    public boolean isAtLeast(@NotNull final ServerVersion other) {
        return this.ordinal() >= other.ordinal();
    }

    public static boolean isServerVersion(@NotNull final ServerVersion version) {
        return serverVersion == version;
    }

    public static boolean isServerVersionAtOrAbove(@NotNull final ServerVersion version){
        return version.ordinal() >= serverVersion.ordinal();
    }

    public static boolean isServerVersionAbove(@NotNull final ServerVersion version) {
        return serverVersion.ordinal() > version.ordinal();
    }

    public static boolean isServerVersionAtLeast(@NotNull final ServerVersion version) {
        return serverVersion.ordinal() >= version.ordinal();
    }

    public static boolean isServerVersionAtOrBelow(@NotNull final ServerVersion version) {
        return serverVersion.ordinal() <= version.ordinal();
    }

    public static boolean isServerVersionBelow(@NotNull final ServerVersion version) {
        return serverVersion.ordinal() < version.ordinal();
    }

    public static boolean isLatestVersion() {
        try {
            Class.forName("net.minecraft.network.syncher.DataWatcher$b");

            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    public static String getServerVersionString() {
        return serverPackageVersion;
    }

    public static String getVersionReleaseNumber() {
        return serverReleaseVersion;
    }

    public static ServerVersion getServerVersion() {
        return serverVersion;
    }
}
