package com.ebkir.survivalutilities.models;

import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.jetbrains.annotations.NotNull;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@SerializableAs("Home")
public class Warp implements ConfigurationSerializable {

    private final String name;
    private final Location location;
    private final UUID ownerUUID;
    private final Long createdOn;

    public Warp(String name, Location location, UUID ownerUUID) {
        this.name = name;
        this.location = location;
        this.ownerUUID = ownerUUID;
        this.createdOn = new Date().getTime();
    }

    public Warp(String name, Location location, UUID ownerUUID, Long createdOn) {
        this.name = name;
        this.location = location;
        this.ownerUUID = ownerUUID;
        this.createdOn = createdOn;
    }

    @Override
    public @NotNull Map<String, Object> serialize() {
        Map<String, Object> data = new HashMap<String, Object>();

        data.put("name", this.name);
        data.put("location", this.location);
        data.put("owneruuid", this.ownerUUID.toString());
        data.put("createdon", this.createdOn);

        return data;
    }

    public static Warp deserialize(@NotNull Map<String, Object> data) {
        String name = (String) data.get("name");
        Location location = (Location) data.get("location");
        UUID ownerUUID = (UUID) data.get("owneruuid");
        Long createdOn = (Long) data.get("createdon");

        return new Warp(name, location, ownerUUID, createdOn);
    }

    public String getName() {
        return name;
    }

    public Location getLocation() {
        return location;
    }

    public UUID getOwnerUUID() {
        return ownerUUID;
    }

    public Long getCreatedOn() {
        return createdOn;
    }
}
