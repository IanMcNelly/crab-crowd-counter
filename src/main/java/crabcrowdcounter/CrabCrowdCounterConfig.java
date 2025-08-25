package crabcrowdcounter;

import java.awt.Color;
import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.Range;

@ConfigGroup("crabcrowdcounter")
public interface CrabCrowdCounterConfig extends Config
{
    @ConfigItem(
            keyName = "showPlayerText",
            name = "Show 'players attacking'",
            description = "When enabled, shows the text 'players attacking' after the number.",
            position = 1
    )
    default boolean showPlayerText()
    {
        return true;
    }

    @ConfigItem(
            keyName = "minimumCount",
            name = "Minimum Player Count",
            description = "Only show the counter when the player count is at or above this number.",
            position = 2
    )
    @Range(min = 1)
    default int minimumCount()
    {
        return 1;
    }

    @ConfigItem(
            keyName = "textColor",
            name = "Text Color",
            description = "The color of the player count text.",
            position = 3
    )
    default Color textColor()
    {
        return Color.YELLOW;
    }

    @ConfigItem(
            keyName = "displayMode",
            name = "Text Position",
            description = "Where to display the text in relation to the crab.",
            position = 4
    )
    default DisplayMode displayMode()
    {
        return DisplayMode.CENTER_OF_NPC;
    }

    @ConfigItem(
            keyName = "customHeight",
            name = "Custom Text Height",
            description = "Only used when 'Text Position' is set to 'Custom'.",
            position = 5
    )
    default int customHeight()
    {
        return 60;
    }
}