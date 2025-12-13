package io.github.shiryu.spider.api.registry;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Registries {

    public final IntegrationRegistry INTEGRATION = new IntegrationRegistry();
    public final ActionRegistry ACTION = new ActionRegistry();
    public final RequirementRegistry REQUIREMENT = new RequirementRegistry();

}
