package com.github.cawboyroy.expertcoursestudy.load.data

import com.github.cawboyroy.expertcoursestudy.core.IntCache

typealias Loaded = Boolean

interface LoadRepository {

    suspend fun tryLoad(): Loaded

    suspend fun loadInternal()

    class Base(
        private val foregroundWrapper: ForegroundWrapper,
        private val cloud: WordsCloudDataSource,
        private val cache: WordsCacheDataSource.Save,
        private val indexCache: IntCache,
        private val handleError: HandleError<Exception>
    ) : LoadRepository {

        override suspend fun tryLoad(): Loaded {
            val noCache = indexCache.read() < 0
            return if (noCache) {
                foregroundWrapper.start()
                false
            } else
                true
        }

        override suspend fun loadInternal() {
            try {
                val data = cloud.words()
                cache.save(data)
                indexCache.save(0)
            } catch (e: Exception) {
                throw handleError.handle(e)
            }
        }
    }

    class Fake(
        private val indexCache: IntCache,
    ) : LoadRepository {

        private var count = 0

        override suspend fun tryLoad(): Loaded {
            delay(3000)
            if (count == 0) {
                count++
                throw NoInternetConnectionException()
            } else {
                indexCache.save(0)
            }
            return true
        }

        override suspend fun loadInternal() = Unit
    }
}

class NoInternetConnectionException : Exception()

class ServiceUnavailable : Exception()