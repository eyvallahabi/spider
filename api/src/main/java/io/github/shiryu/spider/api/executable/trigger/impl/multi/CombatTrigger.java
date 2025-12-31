package io.github.shiryu.spider.api.executable.trigger.impl.multi;

import io.github.shiryu.spider.api.executable.trigger.TriggerInfo;
import io.github.shiryu.spider.api.executable.trigger.impl.TriggerPack;
import io.github.shiryu.spider.api.executable.trigger.impl.event.AttackTrigger;
import io.github.shiryu.spider.api.executable.trigger.impl.event.DamagedTrigger;
import io.github.shiryu.spider.api.executable.trigger.impl.event.DeathTrigger;
import io.github.shiryu.spider.api.executable.trigger.impl.event.SpawnTrigger;

@TriggerInfo(name = "@combat", description = "Triggers when in combat.")
public class CombatTrigger extends TriggerPack {

    public CombatTrigger(){
        super(
                new AttackTrigger(),
                new DamagedTrigger(),
                new SpawnTrigger(),
                new DeathTrigger()
        );
    }

}
