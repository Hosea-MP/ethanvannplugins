package com.example.autointeract;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("autointeract")
public interface AutoInteractConfig extends Config
{
    @ConfigItem(
            keyName = "targetType",
            name = "Target Type",
            description = "NPC or Object"
    )
    default TargetType targetType()
    {
        return TargetType.NPC;
    }

    @ConfigItem(
            keyName = "targetName",
            name = "Target Name",
            description = "Name of NPC or object"
    )
    default String targetName()
    {
        return "";
    }

    @ConfigItem(
            keyName = "interaction",
            name = "Interaction",
            description = "Menu action to perform"
    )
    default String interaction()
    {
        return "";
    }

    @ConfigItem(
            keyName = "maxDistance",
            name = "Max Distance",
            description = "Max distance from starting position"
    )
    default int maxDistance()
    {
        return 0;
    }
}
