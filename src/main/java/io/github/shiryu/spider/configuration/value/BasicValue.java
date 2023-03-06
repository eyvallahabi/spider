package io.github.shiryu.spider.configuration.value;

import io.github.shiryu.spider.configuration.Config;
import io.github.shiryu.spider.configuration.ConfigValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

@Getter
@Setter
@AllArgsConstructor
public class BasicValue<T> implements ConfigValue<T> {

    private final String path;

    private T value;

    public BasicValue(@NotNull final String path){
        this(path, null);
    }

    @Override
    public void load(@NotNull Config config) {
        this.value = config.getOrSet(this.path, this.value);
    }

    @Override
    public void save(@NotNull Config config) {
        config.set(this.path, this.value);
    }
}
