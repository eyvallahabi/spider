package io.github.shiryu.spider.api.executable.parseable;

import org.jetbrains.annotations.NotNull;

public record ParsedHolder(
        ParseableType type,
        String name,
        String description,
        String[] aliases,
        Class<?> clazz
){

    public boolean matches(@NotNull final String name){
        if (this.name.equalsIgnoreCase(name))
            return true;

        for (final String alias : this.aliases) {
            if (alias.equalsIgnoreCase(name))
                return true;
        }

        return false;
    }

    @NotNull
    public <T> T create(){
        try {
            return (T) clazz.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new IllegalStateException("Could not create instance of class: " + clazz.getName(), e);
        }
    }
}
