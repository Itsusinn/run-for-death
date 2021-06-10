package org.meowcat.rfd

import org.bukkit.Bukkit
import org.bukkit.event.Event

val logger by lazy { Bukkit.getLogger() }

inline fun <reified T> T.publish()where T : Event =
   Bukkit.getServer().getPluginManager().callEvent(this)

fun broadcast(message: String) = Bukkit.broadcastMessage(message)

private val start = (System.currentTimeMillis() / 1000).toInt()

// unit: second
fun now(): Int = (System.currentTimeMillis() / 1000).toInt() - start
