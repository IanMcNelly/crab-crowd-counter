package crabcrowdcounter;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DisplayMode
{
    ABOVE_HP_BAR("Above HP Bar"),
    CENTER_OF_NPC("Center of NPC"),
    CUSTOM_HEIGHT("Custom Height");

    private final String name;

    @Override
    public String toString()
    {
        return name;
    }
}