package io.github.shiryu.spider.api.factory.impl;

import io.github.shiryu.spider.api.factory.converter.LocationConverter;
import org.bukkit.Location;

public class LocationFactory extends AbstractFactory {

    @Override
    public void register() {
        this.register(Location.class, new LocationConverter());
    }



}
