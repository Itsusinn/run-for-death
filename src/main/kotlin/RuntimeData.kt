package org.meowcat.rfd

import org.bukkit.entity.Player
import org.meowcat.rfd.data.Record
import java.util.concurrent.ConcurrentHashMap

val records = ConcurrentHashMap<Player, Record>()

val crazeTimes = ConcurrentHashMap<Player, Int>()
