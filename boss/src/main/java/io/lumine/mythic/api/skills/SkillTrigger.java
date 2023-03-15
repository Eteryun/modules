package io.lumine.mythic.api.skills;

import org.apache.commons.lang.Validate;

import java.util.*;

public record SkillTrigger(String name, List<String> aliases) {
    private static final Map<String, SkillTrigger> TRIGGERS = new HashMap();

    public SkillTrigger(String name, List<String> aliases) {
        this.name = name.toUpperCase();
        this.aliases = aliases.stream().map(String::toUpperCase).toList();
    }

    /** @deprecated */
    @Deprecated
    public static SkillTrigger trigger(String name) {
        String thatName = name.toUpperCase();
        return TRIGGERS != null && TRIGGERS.containsKey(thatName) ? (SkillTrigger)TRIGGERS.get(thatName) : create(thatName);
    }

    public static SkillTrigger create(String name, String... aliases) {
        return new SkillTrigger(name, Arrays.asList(aliases));
    }

    public static SkillTrigger get(String name) {
        String thatName = name.toUpperCase();
        return TRIGGERS.containsKey(thatName) ? (SkillTrigger)TRIGGERS.get(thatName) : (SkillTrigger)TRIGGERS.get("DEFAULT");
    }

    public static void register(SkillTrigger trigger) {
        Validate.notNull(trigger, "trigger cannot be null");
        String name = trigger.name();
        List<String> aliases = trigger.aliases;
        if (!TRIGGERS.containsKey(name)) {
            TRIGGERS.put(name, trigger);
        }

        aliases.forEach((alias) -> {
            if (!TRIGGERS.containsKey(alias)) {
                TRIGGERS.put(alias, trigger);
            }

        });
    }

    public static Collection<SkillTrigger> values() {
        return TRIGGERS.values();
    }

    public void register() {
        register(this);
    }

    public String name() {
        return this.name;
    }

    public List<String> aliases() {
        return this.aliases;
    }
}
