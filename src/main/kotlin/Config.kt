package org.meowcat.rfd

private val config = Plugin.CONFIG

// 杀敌数阈值
val KillCountSill by lazy {
   config.getInt("sill.kill-count-sill", 3)
}
// 杀敌时间阈值
val KillTimeSill by lazy {
   config.getInt("sill.kill-time-sill", 5 * 60)
}
// 狂热奖励金钱数
val CrazeReward by lazy {
   config.getDouble("reward.craze-reward", 200.0)
}
// 狂热弑杀者奖励金钱数
val KillCrazeReward by lazy {
   config.getDouble("reward.kill-craze-reward", 150.0)
}
// 狂热状态持续时间
val CrazeDuration by lazy {
   config.getInt("duration.craze-duration", 5 * 60)
}
// 标亮BUFF持续时间
val EffectDuration by lazy {
   config.getInt("duration.effect-duration", 60)
}
