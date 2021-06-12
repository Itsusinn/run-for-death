package org.meowcat.rfd

import org.bukkit.Bukkit
import org.bukkit.event.Event

internal val logger by lazy { Bukkit.getLogger() }

internal val pluginManager = Bukkit.getServer().getPluginManager()

internal inline fun <reified T> T.publish()where T : Event =
   pluginManager.callEvent(this)

internal fun broadcast(message: String) = Bukkit.broadcastMessage(message)

internal val start = (System.currentTimeMillis() / 1000).toInt()

// unit: second
internal fun now(): Int = (System.currentTimeMillis() / 1000).toInt() - start
