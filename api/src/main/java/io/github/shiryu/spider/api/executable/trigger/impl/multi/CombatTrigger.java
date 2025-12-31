package io.github.shiryu.spider.api.executable.trigger.impl.multi;

import io.github.shiryu.spider.api.executable.parseable.Parse;
import io.github.shiryu.spider.api.executable.trigger.impl.TriggerPack;
import io.github.shiryu.spider.api.executable.trigger.impl.event.AttackTrigger;
import io.github.shiryu.spider.api.executable.trigger.impl.event.DamagedTrigger;
import io.github.shiryu.spider.api.executable.trigger.impl.event.DeathTrigger;
import io.github.shiryu.spider.api.executable.trigger.impl.event.SpawnTrigger;

@Parse(name = "@combat", description = "Triggers related to combat events")
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
