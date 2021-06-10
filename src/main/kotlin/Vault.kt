package org.meowcat.rfd

import net.milkbowl.vault.economy.Economy
import org.bukkit.OfflinePlayer

object Vault {
   private val api by lazy {
      val server = RfdPlugin.plugin.server
      if (server.pluginManager.getPlugin("Vault") == null) {
         logger.warning("缺失经济插件Vault")
         return@lazy null
      }
      val rsp =
         server.servicesManager.getRegistration(Economy::class.java)
      rsp?.provider
   }
   fun takeMoney(player: OfflinePlayer, money: Double) =
      api?.withdrawPlayer(player, money)

   fun addMoney(player: OfflinePlayer, money: Double) =
      api?.depositPlayer(player, money)

   fun hasMoney(player: OfflinePlayer, money: Double): Boolean =
      api?.has(player, money) ?: false

   fun getMoney(player: OfflinePlayer): Double =
      api?.getBalance(player) ?: 0.0

   fun setMoney(player: OfflinePlayer, money: Double) =
      addMoney(player, money - getMoney(player))
}
