package org.meowcat.rfd

import com.github.shynixn.mccoroutine.registerSuspendingEvents
import io.izzel.taboolib.loader.Plugin
import io.izzel.taboolib.module.config.TConfig
import io.izzel.taboolib.module.inject.TInject
import io.izzel.taboolib.module.locale.TLocale
import org.bukkit.Bukkit

object Plugin : Plugin() {
   @TInject("config.yml", locale = "language")
   lateinit var CONFIG: TConfig
      private set

   override fun onLoad() {
      TLocale.sendToConsole("plugin.loading", Bukkit.getBukkitVersion())
   }

   override fun onEnable() {
      TLocale.sendToConsole("plugin.enable", plugin.description.version)
      plugin.server.pluginManager.registerSuspendingEvents(Listener, plugin)
   }
}
