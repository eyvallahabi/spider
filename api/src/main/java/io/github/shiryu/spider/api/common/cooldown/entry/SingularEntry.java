package io.github.shiryu.spider.api.common.cooldown.entry;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class SingularEntry implements CooldownEntry {

    private final String id;
    private final UUID uuid;
    private final long finish;

    @Override
    public boolean active() {
        return this.finish >= System.currentTimeMillis();
    }
}
