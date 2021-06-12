package org.meowcat.rfd

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import org.meowcat.rfd.data.Record
import org.meowcat.rfd.events.CrazeEvent
import org.meowcat.rfd.events.KillCrazeEvent
import kotlin.coroutines.CoroutineContext

object RfdListener : Listener, CoroutineScope {
   override val coroutineContext: CoroutineContext
      get() = Dispatchers.Default

   @EventHandler
   suspend fun onPlayerKilledEvent(event: PlayerDeathEvent) {
      event.deathMessage = null
      val victim = event.entity
      val killer = victim.killer ?: run {
         broadcast("§6[赴死] §3${victim.name}擅自死了！")
         return
      }
      // 清零victim的记录
      records.getOrPut(victim) { Record(victim) }.clear()
      broadcast("§6[赴死] §3${killer.name}斩杀了${victim.name}!")
      // 增添killer的记录
      records.getOrPut(killer) { Record(killer) }.update()
      // 杀死狂热者的奖励
      val diff = now() - crazeTimes.getOrDefault(victim, -1 * KillTimeSill)
      if (diff < CrazeDuration) {
         KillCrazeEvent(killer).publish()
      }
   }
   @EventHandler
   suspend fun onCrazeEvent(event: CrazeEvent) {
      val killer = event.player
      val location = killer.location
      broadcast(
         """§6[赴死] §c${event.player.name}陷入了疯狂！
         |>>>> 他在x${location.x.toInt()};y${location.y.toInt()};z${location.z.toInt()}""".trimMargin()
      )
      // 20 ticks/s
      val effect = PotionEffect(PotionEffectType.GLOWING, EffectDuration * 20, 1)
      killer.addPotionEffect(effect)
      Vault.addMoney(killer, CrazeReward)
   }
   @EventHandler
   suspend fun onKillCrazeEvent(event: KillCrazeEvent) {
      val killer = event.player
      Vault.addMoney(killer, KillCrazeReward)
   }
}
