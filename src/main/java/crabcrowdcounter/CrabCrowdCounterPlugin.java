package crabcrowdcounter;

import com.google.inject.Provides;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.NPC;
import net.runelite.api.Player;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.events.GameTick;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Slf4j
@PluginDescriptor(
		name = "Crab Crowd Counter"
)
public class CrabCrowdCounterPlugin extends Plugin
{
	// The NPC ID for the Gemstone Crab
	private static final int GEMSTONE_CRAB_ID = 14779;

	@Inject
	private Client client;

	@Inject
	private OverlayManager overlayManager;

	@Inject
	private CrabCrowdCounterOverlay overlay;

	// This map will store the count of players for each Gemstone Crab
	// Key: The specific NPC instance
	// Value: The number of players attacking it
	private final Map<NPC, Integer> crabPlayerCounts = new HashMap<>();

	@Override
	protected void startUp() throws Exception
	{
		// This method is called when the plugin is enabled.
		// We add our overlay to the screen here.
		overlayManager.add(overlay);
	}

	@Override
	protected void shutDown() throws Exception
	{
		// This method is called when the plugin is disabled.
		// We remove our overlay and clear the map.
		overlayManager.remove(overlay);
		crabPlayerCounts.clear();
	}

	// This method runs on every "game tick" (every 600ms).
	@Subscribe
	public void onGameTick(GameTick gameTick)
	{
		// Clear the map at the start of each tick to get fresh data
		crabPlayerCounts.clear();

		// Get a list of all NPCs currently rendered on the screen
		for (NPC npc : client.getNpcs())
		{
			// Check if the NPC is a Gemstone Crab
			if (npc.getId() == GEMSTONE_CRAB_ID)
			{
				// If it's a crab, count how many players are attacking it
				countPlayersAttacking(npc);
			}
		}
	}

	private void countPlayersAttacking(NPC crab)
	{
		int count = 0;
		// A set to avoid counting the same player multiple times for one crab
		Set<Player> attackingPlayers = new HashSet<>();

		// Loop through all players on screen
		for (Player player : client.getPlayers())
		{
			// Check if the player is interacting with our crab
			if (player.getInteracting() == crab)
			{
				attackingPlayers.add(player);
			}
		}
		count = attackingPlayers.size();

		// If at least one player is attacking, store the count
		if (count > 0)
		{
			crabPlayerCounts.put(crab, count);
		}
	}

	// This makes our map of crab counts accessible to the overlay
	public Map<NPC, Integer> getCrabPlayerCounts()
	{
		return crabPlayerCounts;
	}

	@Provides
	CrabCrowdCounterConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(CrabCrowdCounterConfig.class);
	}
}