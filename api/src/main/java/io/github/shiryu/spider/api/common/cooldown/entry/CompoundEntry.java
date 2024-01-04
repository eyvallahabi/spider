package io.github.shiryu.spider.api.common.cooldown.entry;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class CompoundEntry implements CooldownEntry {

    private final List<SingularEntry> entries = new ArrayList<>();

    public boolean contains(@NotNull final String id, @NotNull final UUID uuid){
        return this.entries.stream()
                .filter(entry -> entry.getId().equals(id))
                .filter(entry -> entry.getUuid().equals(uuid))
                .anyMatch(entry -> entry.active());
    }

    @NotNull
    public CompoundEntry add( @NotNull final String id, @NotNull final UUID uuid, final long amount){
        this.entries.add(
                new SingularEntry(
                        id,
                        uuid,
                        System.currentTimeMillis() + amount
                )
        );

        return this;
    }

    @Nullable
    public SingularEntry getEntry(@NotNull final UUID uuid, @NotNull final String id){
        return this.entries.stream()
                .filter(entry -> entry.getId().equals(id))
                .filter(entry -> entry.getUuid().equals(uuid))
                .findAny()
                .orElse(null);
    }

    public boolean active(@NotNull final UUID uuid, @NotNull final String id){
        if (this.entries.size() == 0)
            return false;

        final List<SingularEntry> singulars = this.entries
                .stream()
                .filter(entry -> entry != null)
                .filter(entry -> entry.getId().equalsIgnoreCase(id))
                .filter(entry -> entry.getUuid().equals(uuid))
                .collect(Collectors.toList());

        if (singulars.isEmpty())
            return false;

        return singulars.stream()
                .allMatch(CooldownEntry::active);
    }

    public boolean active(@NotNull final String id){
        return this.entries.stream()
                .filter(entry -> entry.getId().equals(id))
                .allMatch(CooldownEntry::active);
    }

    @Override
    public boolean active() {
        return this.entries.stream()
                .allMatch(CooldownEntry::active);
    }
}
