package com.github.cawboyroy.expertcoursestudy.stats.data

interface StatsRepository {

    fun stats(): Triple<Int, Int, Int>

    fun clear()

    class Base(
        private val statsCache: StatsCache.Stats
    ) : StatsRepository {

        override fun stats(): Triple<Int, Int, Int> {
            return statsCache.data()
        }

        override fun clear() {
            statsCache.clear()
        }
    }
}
