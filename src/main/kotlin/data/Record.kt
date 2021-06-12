package org.meowcat.rfd.data

import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import org.bukkit.entity.Player
import org.meowcat.rfd.* // ktlint-disable no-wildcard-imports
import org.meowcat.rfd.events.CrazeEvent
import org.meowcat.rfd.now
import org.meowcat.rfd.publish
import java.util.* // ktlint-disable no-wildcard-imports

class Record(
   private val player: Player
) {
   private val times = LimitedIntArray(KillCountSill)
   private val lock = Mutex()

   suspend fun update() {
      lock.withLock {
         times + now()
      }
      if (check()) {
         crazeTimes[player] = now()
         CrazeEvent(player).publish()
      }
   }
   suspend fun clear() {
      lock.withLock {
         times.clear()
      }
   }
   private suspend fun check(): Boolean {
      var counter = 0
      val now = now()
      for (time in times) {
         // FIXME
         println("time$time")
         if ((now - time) < KillTimeSill) {
            counter++
         }
      }
      return if (counter >= KillCountSill) {
         true
      } else false
   }
}

class LimitedIntArray(val size: Int) {
   private val inner = LinkedList<Int>()
   operator fun get(index: Int) = inner[index]
   operator fun iterator() = inner.iterator()
   operator fun set(index: Int, value: Int) { inner[index] = value }
   operator fun plus(value: Int) {
      if (inner.size == size) { inner.remove() }
      inner.add(value)
   }
   fun clear() = inner.clear()
}
