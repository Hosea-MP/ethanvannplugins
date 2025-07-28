package com.example.autointeract;

import com.example.EthanApiPlugin.Collections.NPCs;
import com.example.EthanApiPlugin.Collections.TileObjects;
import com.example.InteractionApi.NPCInteraction;
import com.example.InteractionApi.TileObjectInteraction;
import com.google.inject.Inject;
import com.google.inject.Provides;
import net.runelite.api.Client;
import net.runelite.api.NPC;
import net.runelite.api.TileObject;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.events.GameTick;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

import java.util.Optional;

@PluginDescriptor(name = "Auto Interact")
public class AutoInteractPlugin extends Plugin
{
    @Inject
    private Client client;

    @Inject
    private AutoInteractConfig config;

    private WorldPoint startPoint;

    @Provides
    public AutoInteractConfig getConfig(ConfigManager configManager)
    {
        return configManager.getConfig(AutoInteractConfig.class);
    }

    @Override
    protected void startUp()
    {
        if (client.getLocalPlayer() != null)
        {
            startPoint = client.getLocalPlayer().getWorldLocation();
        }
    }

    @Override
    protected void shutDown()
    {
        startPoint = null;
    }

    @Subscribe
    private void onGameTick(GameTick tick)
    {
        if (startPoint == null)
        {
            return;
        }

        String name = config.targetName();
        String action = config.interaction();
        int maxDist = config.maxDistance();

        if (name.isEmpty() || action.isEmpty())
        {
            return;
        }

        if (config.targetType() == TargetType.NPC)
        {
            Optional<NPC> npcOpt = NPCs.search()
                    .withName(name)
                    .nearestToPoint(startPoint);
            if (npcOpt.isPresent())
            {
                NPC npc = npcOpt.get();
                if (npc.getWorldLocation().distanceTo(startPoint) <= maxDist)
                {
                    NPCInteraction.interact(npc, action);
                }
            }
        }
        else
        {
            Optional<TileObject> objOpt = TileObjects.search()
                    .withName(name)
                    .nearestToPoint(startPoint);
            if (objOpt.isPresent())
            {
                TileObject obj = objOpt.get();
                if (obj.getWorldLocation().distanceTo(startPoint) <= maxDist)
                {
                    TileObjectInteraction.interact(obj, action);
                }
            }
        }
    }
}
