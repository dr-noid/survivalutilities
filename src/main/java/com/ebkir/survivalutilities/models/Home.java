package com.ebkir.survivalutilities.models;

import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.DelegateDeserialization;
import org.bukkit.configuration.serialization.SerializableAs;
import org.jetbrains.annotations.NotNull;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@SerializableAs("Home")
public class Home implements ConfigurationSerializable {

    private final String name;
    private final Location location;
    private final UUID ownerUUID;
    private final Long createdOn;

    public Home(String name, Location location, UUID ownerUUID) {
        this.name = name;
        this.location = location;
        this.ownerUUID = ownerUUID;
        this.createdOn = new Date().getTime();
    }

    public Home(String name, Location location, UUID ownerUUID, Long createdOn) {
        this.name = name;
        this.location = location;
        this.ownerUUID = ownerUUID;
        this.createdOn = createdOn;
    }

    public Home(Map<String, Object> data) {
        this.name = (String) data.get("name");
        this.location =  (Location) data.get("location");
        this.ownerUUID = UUID.fromString((String) data.get("owneruuid"));
        this.createdOn = (Long) data.get("createdon");
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

    public static Home deserialize(@NotNull Map<String, Object> data) {
        String name = (String) data.get("name");
        Location loc = (Location) data.get("location");
        UUID ownerUUID = UUID.fromString((String) data.get("owneruuid"));
        Long createdOn = (Long) data.get("createdon");

        return new Home(name, loc, ownerUUID, createdOn);
    }

    @Override
    public String toString() {
        return "Home{" +
                "name='" + name + '\'' +
                ", location=" + location +
                ", ownerUUID=" + ownerUUID +
                '}';
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
