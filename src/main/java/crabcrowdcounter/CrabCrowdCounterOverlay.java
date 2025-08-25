package crabcrowdcounter;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.util.Map;
import javax.inject.Inject;
import net.runelite.api.Client;
import net.runelite.api.NPC;
import net.runelite.api.Point;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayUtil;

public class CrabCrowdCounterOverlay extends Overlay
{
    private final Client client;
    private final CrabCrowdCounterPlugin plugin;
    private final CrabCrowdCounterConfig config;

    @Inject
    private CrabCrowdCounterOverlay(Client client, CrabCrowdCounterPlugin plugin, CrabCrowdCounterConfig config)
    {
        setPosition(OverlayPosition.DYNAMIC);
        setLayer(OverlayLayer.ABOVE_SCENE);
        this.client = client;
        this.plugin = plugin;
        this.config = config;
    }

    @Override
    public Dimension render(Graphics2D graphics)
    {
        Map<NPC, Integer> crabCounts = plugin.getCrabPlayerCounts();

        for (Map.Entry<NPC, Integer> entry : crabCounts.entrySet())
        {
            NPC npc = entry.getKey();
            Integer count = entry.getValue();

            if (count < config.minimumCount())
            {
                continue; // Skip this crab if the count is too low
            }

            String text = String.valueOf(count);
            if (config.showPlayerText())
            {
                text += " players attacking";
            }

            Point textLocation;
            int zOffset;

            switch (config.displayMode())
            {
                case ABOVE_HP_BAR:
                    zOffset = npc.getLogicalHeight() + 50;
                    break;
                case CUSTOM_HEIGHT:
                    zOffset = config.customHeight();
                    break;
                case CENTER_OF_NPC:
                default:
                    zOffset = (npc.getLogicalHeight() / 2) - 120;
                    break;
            }
            textLocation = npc.getCanvasTextLocation(graphics, text, zOffset);


            if (textLocation != null)
            {
                OverlayUtil.renderTextLocation(graphics, textLocation, text, config.textColor());
            }
        }

        return null;
    }
}