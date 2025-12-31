package io.github.shiryu.spider.api.location;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.util.NumberConversions;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
@Setter
@AllArgsConstructor
public class SpiderLocation {

    private static final Pattern PATTERN = Pattern.compile("(?<world>[^/]+):(?<x>[\\-0-9.]+),(?<y>[\\-0-9.]+),(?<z>[\\-0-9.]+)(:(?<yaw>[\\-0-9.]+):(?<pitch>[\\-0-9.]+))?");

    private String world;

    private double x,y,z;
    private float yaw,pitch;

    public static SpiderLocation empty(){
        return new SpiderLocation("world", 0, 0, 0, 0.0f, 0.0f);
    }

    public static SpiderLocation from(@NotNull final Location location){
        return new SpiderLocation(location.getWorld().getName(),
                location.getX(), location.getY(), location.getZ(),
                location.getYaw(), location.getPitch());
    }

    public static SpiderLocation from(@NotNull final String string){
        final Matcher match = PATTERN.matcher(string
                .replace("_", ".")
                .replace("/", ":"));

        if (!match.matches())
            return SpiderLocation.empty();

        return new SpiderLocation(
                match.group("world"),
                NumberConversions.toDouble(match.group("x")),
                NumberConversions.toDouble(match.group("y")),
                NumberConversions.toDouble(match.group("z")),
                Optional.ofNullable(match.group("yaw"))
                        .map(NumberConversions::toFloat)
                        .orElse(0.0f),
                Optional.ofNullable(match.group("pitch"))
                        .map(NumberConversions::toFloat)
                        .orElse(0.0f)
        );
    }

    public SpiderLocation(final double x, final double y, final double z) {
        this(x, y, z, 0.0F, 0.0F);
    }

    public SpiderLocation(@NotNull final String world, final double x, final double y, final double z) {
        this(world, x, y, z, 0.0F, 0.0F);
    }

    public SpiderLocation(final double x, final double y, final double z, final float yaw, final float pitch) {
        this("world", x, y, z, yaw, pitch);
    }

    public int getBlockX(){
        return this.convert().getBlockX();
    }

    public int getBlockY(){
        return this.convert().getBlockY();
    }

    public int getBlockZ(){
        return this.convert().getBlockZ();
    }

    @NotNull
    public List<SpiderLocation> blocksInSphere(final double radius, final double radiusY, final double noise, final boolean noAir, final boolean onlyAir){
        final List<SpiderLocation> locations = new ArrayList<>();

        for (int x = (int) -radius; x <= radius; x++) {
            for (int y = (int) -radiusY; y <= radiusY; y++) {
                for (int z = (int) -radius; z <= radius; z++) {
                    double distance = Math.sqrt(x * x + y * y + z * z);
                    if (distance <= radius) {
                        if (noise > 0.0 && Math.random() < noise)
                            continue;

                        SpiderLocation location = new SpiderLocation(
                                this.getWorld(),
                                this.getX() + x,
                                this.getY() + y,
                                this.getZ() + z
                        );

                        if (noAir && location.convert().getBlock().isEmpty())
                            continue;

                        if (onlyAir && !location.convert().getBlock().isEmpty())
                            continue;

                        locations.add(location);
                    }
                }
            }
        }
        return locations;
    }

    @NotNull
    public List<SpiderLocation> blocksInCube(final double radius, final double radiusY, final double noise, final boolean noAir, final boolean onlyAir){
        final List<SpiderLocation> locations = new ArrayList<>();

        for (int x = (int) -radius; x <= radius; x++) {
            for (int y = (int) -radiusY; y <= radiusY; y++) {
                for (int z = (int) -radius; z <= radius; z++) {
                    if (noise > 0.0 && Math.random() < noise)
                        continue;

                    SpiderLocation location = new SpiderLocation(
                            this.getWorld(),
                            this.getX() + x,
                            this.getY() + y,
                            this.getZ() + z
                    );

                    if (noAir && location.convert().getBlock().isEmpty())
                        continue;

                    if (onlyAir && !location.convert().getBlock().isEmpty())
                        continue;

                    locations.add(location);
                }
            }
        }
        return locations;
    }

    @NotNull
    public List<SpiderLocation> sphere(final double radius, final double step){
        final List<SpiderLocation> locations = new ArrayList<>();

        for (double x = -radius; x <= radius; x += step) {
            for (double y = -radius; y <= radius; y += step) {
                for (double z = -radius; z <= radius; z += step) {
                    if (x * x + y * y + z * z <= radius * radius) {
                        locations.add(new SpiderLocation(
                                this.getWorld(),
                                this.getX() + x,
                                this.getY() + y,
                                this.getZ() + z
                        ));
                    }
                }
            }
        }
        return locations;
    }

    @NotNull
    public List<SpiderLocation> rectangle(final double length, final double width, final double step){
        final List<SpiderLocation> locations = new ArrayList<>();

        double halfLength = length / 2;
        double halfWidth = width / 2;

        for (double x = -halfLength; x <= halfLength; x += step) {
            for (double z = -halfWidth; z <= halfWidth; z += step) {
                locations.add(new SpiderLocation(
                        this.getWorld(),
                        this.getX() + x,
                        this.getY(),
                        this.getZ() + z
                ));
            }
        }
        return locations;
    }

    @NotNull
    public List<SpiderLocation> cone(final int maxDistance, final double angle, final double yOffset, final double step){
        final Vector forward = this.convert().getDirection().normalize();
        final Location start = this.convert().clone().add(0, yOffset, 0);

        final List<Location> results = Lists.newArrayList();

        for (double d = 0; d <= maxDistance; d += step) {

            Location center = start.clone().add(forward.clone().multiply(d));

            double radius = Math.tan(angle) * d;

            if (radius < 0.001) {
                results.add(center);
                continue;
            }

            int points = (int) Math.max(6, (radius * 8)); // yoğunluk
            double stepAngle = (Math.PI * 2) / points;

            for (int i = 0; i < points; i++) {
                double theta = i * stepAngle;

                double x = Math.cos(theta) * radius;
                double z = Math.sin(theta) * radius;

                Location point = center.clone().add(x, 0, z);

                Vector dirToPoint = point.clone().subtract(start).toVector().normalize();
                double dot = dirToPoint.dot(forward);

                if (dot >= Math.cos(angle))
                    results.add(point);
            }
        }

        return results.stream()
                .map(SpiderLocation::from)
                .toList();
    }

    @NotNull
    public List<SpiderLocation> randomLocationsNear(final double radius, final int amount){
        final List<SpiderLocation> locations = new ArrayList<>();

        for (int i = 0; i < amount; i++) {
            double angle = Math.random() * 2 * Math.PI;
            double distance = Math.random() * radius;
            double x = Math.cos(angle) * distance;
            double z = Math.sin(angle) * distance;
            locations.add(new SpiderLocation(
                    this.getWorld(),
                    this.getX() + x,
                    this.getY(),
                    this.getZ() + z
            ));
        }
        return locations;
    }

    public double getGroundDistanceTo(@NotNull final SpiderLocation location) {
        return Math.sqrt(
                Math.pow(this.x - location.x, 2) + Math.pow(this.z - location.z, 2)
        );
    }

    public double getDistanceTo(@NotNull final SpiderLocation location) {
        return Math.sqrt(
                Math.pow(this.x - location.x, 2) + Math.pow(this.y - location.y, 2) + Math.pow(this.z - location.z, 2)
        );
    }

    @NotNull
    public List<SpiderLocation> getCircle(double radius, int amount){
        final double increment = (2 * Math.PI) / amount;

        final List<SpiderLocation> locations = new ArrayList<>();

        for (int i = 0; i < amount; i++){
            double angle = i * increment;

            double x = this.x + (radius * Math.cos(angle));
            double z = this.z + (radius * Math.sin(angle));

            locations.add(
                    new SpiderLocation(
                            this.world,
                            x,
                            this.y,
                            z
                    )
            );
        }

        return locations;
    }

    @NotNull
    public String locationToString(){
        String s = getBukkitWorld().getName() + ':';

        s += String.format(
                Locale.ENGLISH,
                "%.2f,%.2f,%.2f",
                this.x, this.y, this.z);

        if (this.yaw != 0.0f || this.pitch != 0.0f){
            s += String.format(
                    Locale.ENGLISH,
                    ":%.2f:%.2f",
                    this.yaw, this.pitch);
        }

        return s.replace(":", "/")
                .replace(".", "_");
    }

    @NotNull
    public Location convert() {
        return new Location(
                this.getBukkitWorld(),
                this.x, this.y, this.z,
                this.yaw, this.pitch
        );
    }

    public World getBukkitWorld() {
        final World world = Bukkit.getWorld(this.world);

        if (world != null)
            return world;

        return WorldCreator.name(this.world)
                .createWorld();
    }


    @Override
    public boolean equals(@NotNull final Object object) {
        if (!(object instanceof SpiderLocation location))
            return false;

        return location.x == this.x && location.y == this.y && location.z == this.z
                && location.pitch == this.pitch && location.yaw == this.yaw;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("x", this.x)
                .append("y", this.y)
                .append("z", this.z)
                .append("yaw", this.yaw)
                .append("pitch", this.pitch)
                .append("world", this.world)
                .toString();
    }

}
