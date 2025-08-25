package crabcrowdcounter;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class CrabCrowdCounterPluginTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(CrabCrowdCounterPlugin.class);
		RuneLite.main(args);
	}
}