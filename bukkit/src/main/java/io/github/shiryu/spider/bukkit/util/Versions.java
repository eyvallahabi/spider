package io.github.shiryu.spider.bukkit.util;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public enum Versions {

    v1_8(0, "v1_8_R1", 8, 0),
    v1_8_3(1, "v1_8_R2", 8, 3),
    v1_8_8(2, "v1_8_R3", 8, 8),
    v1_9_2(3, "v1_9_R1", 9, 2),
    v1_9_4(4, "v1_9_R2", 9, 4),
    v1_10_2(5, "v1_10_R1", 10, 2),
    v1_11_2(6, "v1_11_R1", 11, 2),
    v1_12_2(7, "v1_12_R1", 12, 2),
    v1_13(8, "v1_13_R1", 13, 0),
    v1_13_2(9, "v1_13_R2", 13, 2),
    v1_14(10, "v1_14_R1", 14, 0, "30f0a3bd4ceb5c03fe41ac0cfea4ffe3"),
    v1_14_1(11, "v1_14_R1", 14, 1, "48be70f51ffe914d865f175ed3bf992d"),
    v1_14_2(12, "v1_14_R1", 14, 2, "c31c513e1fa6657aacbd4facd394c5b0"),
    v1_14_3(13, "v1_14_R1", 14, 3, "a69acbca3007d2ae1b4b69881f0ab9ad"),
    v1_14_4(14, "v1_14_R1", 14, 4, "11ae498d9cf909730659b6357e7c2afa"),
    v1_15(15, "v1_15_R1", 15, 0, "e50e3dd1d07234cc9c09cb516a951227"),
    v1_15_1(16, "v1_15_R1", 15, 1, "d2fa25e37d6e69667dc7f4a33d7644e1"),
    v1_15_2(17, "v1_15_R1", 15, 2, "5684afcc1835d966e1b6eb0ed3f72edb"),
    v1_16_1(18, "v1_16_R1", 16, 1, "25afc67716a170ea965092c1067ff439"),
    v1_16_2(19, "v1_16_R2", 16, 2, "c2d5d7871edcc4fb0f81d18959c647af"),
    v1_16_3(20, "v1_16_R2", 16, 3, "09f04031f41cb54f1077c6ac348cc220"),
    v1_16_4(21, "v1_16_R3", 16, 4, "da85101b34b252659e3ddf10c0c57cc9"),
    v1_16_5(22, "v1_16_R3", 16, 5, "d4b392244df170796f8779ef0fc1f2e9"),
    v1_17(23, "v1_17_R1", 17, 0, "acd6e6c27e5a0a9440afba70a96c27c9"),
    v1_17_1(24, "v1_17_R1", 17, 1, "f0e3dfc7390de285a4693518dd5bd126"),
    v1_18(25, "v1_18_R1", 18, 0, "9e9fe6961a80f3e586c25601590b51ec"),
    v1_18_1(26, "v1_18_R1", 18, 1, "20b026e774dbf715e40a0b2afe114792"),
    v1_18_2(27, "v1_18_R2", 18, 2, "eaeedbff51b16ead3170906872fda334"),
    v1_19(28, "v1_19_R1", 19, 0, "7b9de0da1357e5b251eddde9aa762916"),
    v1_19_1(29, "v1_19_R1", 19, 1, "4cc0cc97cac491651bff3af8b124a214"),
    v1_19_2(30, "v1_19_R1", 19, 2, "69c84c88aeb92ce9fa9525438b93f4fe"),
    v1_19_3(31, "v1_19_R2", 19, 3, "1afe2ffe8a9d7fc510442a168b3d4338"),
    v1_19_4(32, "v1_19_R3", 19, 4, "3009edc0fff87fa34680686663bd59df"),
    v1_20(33, "v1_20_R1", 20, 0, "34f399b4f2033891290b7f0700e9e47b"),
    v1_20_1(34, "v1_20_R1", 20, 1, "bcf3dcb22ad42792794079f9443df2c0"),
    v1_20_2(35, "v1_20_R2", 20, 2, "3478a65bfd04b15b431fe107b3617dfc");

    @NotNull
    public static String getNMSVersion() {
        final String version = Bukkit.getServer().getClass().getPackage().getName();

        return version.substring(version.lastIndexOf('.') + 1);
    }

    @Nullable
    public static String calculateMappingsHash(){
        try{
            final String nms = getNMSVersion();

            final int major = Integer.parseInt(nms.substring(3, nms.length() - 3));

            if (major <= 13)
                return null;

            Class<?> res;

            try{
                res = Class.forName("org.bukkit.craftbukkit." + nms + ".util.CraftMagicNumbers");
            }catch (final ClassNotFoundException exception){
                return null;
            }

            final Method method = res.getMethod("getMappingsVersion");

            return method.invoke(res.getField("INSTANCE").get(null)).toString();
        }catch (final Throwable throwable){
            throw new RuntimeException(throwable);
        }
    }

    private static final Versions versions = matchVersion(getNMSVersion(), calculateMappingsHash());

    private int number;
    private String version;
    private int major;
    private int minor;
    private String hash;

    Versions(final int number, final String version, final int major, final int minor) {
        this(number, version, major, minor, "");
    }

    Versions(final int number, final String version, final int major, final int minor, final String hash) {
        this.number = number;
        this.version = version;
        this.major = major;
        this.minor = minor;
        this.hash = hash;
    }

    @NotNull
    public static Versions matchVersion(final String version, final String hash) {
        final List<Versions> versions = Arrays.stream(values())
                .filter(x -> x.getVersion().equals(version))
                .collect(Collectors.toList());

        if (versions.size() == 1)
            return versions.get(0);

        return versions
                .stream()
                .filter(x -> x.getHash().equals(hash))
                .findFirst()
                .orElse(Versions.v1_12_2);
    }

    public static boolean lowerThanOr(Versions version) {
        return (version.getNumber() <= version.getNumber());
    }

    public static boolean higherThanOr(Versions version) {
        return (version.getNumber() >= versions.getNumber());
    }

}
