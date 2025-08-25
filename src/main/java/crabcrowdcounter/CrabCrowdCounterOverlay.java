package net.runelite.client.plugins.crabcrowdcounter;

import net.runelite.api.Client;
import net.runelite.api.NPC;
import net.runelite.api.Point;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayUtil;
import javax.inject.Inject;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.util.Map;

public class CrabCrowdCounterOverlay extends Overlay
{
    private final Client client;
    private final CrabCrowdCounterPlugin plugin;

    @Inject
    private CrabCrowdCounterOverlay(Client client, CrabCrowdCounterPlugin plugin)
    {
        // Set the overlay's position and layer
        setPosition(OverlayPosition.DYNAMIC);
        setLayer(OverlayLayer.ABOVE_SCENE);
        this.client = client;
        this.plugin = plugin;
    }

    @Override
    public Dimension render(Graphics2D graphics)
    {
        // Get the map of crab counts from our main plugin class
        Map<NPC, Integer> crabCounts = plugin.getCrabPlayerCounts();

        // Loop through each crab that has players attacking it
        for (Map.Entry<NPC, Integer> entry : crabCounts.entrySet())
        {
            NPC npc = entry.getKey();
            Integer count = entry.getValue();
            String text = String.valueOf(count); // The text to draw is the count

            // Get the position on the screen to draw the text
            Point textLocation = npc.getCanvasTextLocation(graphics, text, npc.getLogicalHeight() + 40);

            if (textLocation != null)
            {
                // Draw the text on the screen
                OverlayUtil.renderTextLocation(graphics, textLocation, text, Color.WHITE);
            }
        }

        return null;
    }
}