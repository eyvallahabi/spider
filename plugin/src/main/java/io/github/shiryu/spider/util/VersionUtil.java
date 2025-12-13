package io.github.shiryu.spider.util;

import io.github.shiryu.spider.SpiderPlugin;
import lombok.experimental.UtilityClass;

import java.util.Map;

@UtilityClass
public class VersionUtil {

    public int getMinorVersion(){
        final Map.Entry<Integer, Integer> version = getVersion();

        if (version.getValue() == -1)
            return -1;

        return version.getValue();
    }

    public int getMajorVersion(){
        final Map.Entry<Integer, Integer> version = getVersion();

        if (version.getKey() == -1)
            return -1;

        return version.getKey();
    }

    public Map.Entry<Integer, Integer> getVersion(){
        final String version = SpiderPlugin.getPlugin().getServer().getVersion();

        final String[] split = version.split("-");

        final String text = split[0];

        final String[] parts = text.split("\\.");

        if (parts.length < 2)
            return Map.entry(-1, -1);

        if (parts.length == 2){
            try{
                return Map.entry(
                        Integer.parseInt(parts[1]),
                        -1
                );
            }catch (final Exception exception){
                return Map.entry(-1, -1);
            }
        }

        try{
            return Map.entry(
                    Integer.parseInt(parts[1]),
                    Integer.parseInt(parts[2])
            );
        }catch (final Exception exception){
            return Map.entry(-1, -1);
        }
    }
}
