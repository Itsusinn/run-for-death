package org.meowcat.rfd.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * 当一个玩家杀死狂热者时触发
 */
public class KillCrazeEvent extends Event {
	private static final HandlerList handlers = new HandlerList();

	public final Player player;

	public KillCrazeEvent(@NotNull Player player) {
		this.player = player;
	}

	@NotNull
	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	@NotNull
	public static HandlerList getHandlerList() {
		return handlers;
	}
}
