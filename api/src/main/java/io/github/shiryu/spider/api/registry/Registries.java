package io.github.shiryu.spider.api.registry;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Registries {

    public final ExecutableRegistry EXECUTABLE = new ExecutableRegistry();
    public final IntegrationRegistry INTEGRATION = new IntegrationRegistry();

}
