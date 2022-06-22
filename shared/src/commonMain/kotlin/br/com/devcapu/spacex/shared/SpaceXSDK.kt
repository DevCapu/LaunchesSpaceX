package br.com.devcapu.spacex.shared

import br.com.devcapu.spacex.entity.RocketLaunch
import br.com.devcapu.spacex.shared.cache.Database
import br.com.devcapu.spacex.shared.cache.DatabaseDriverFactory
import br.com.devcapu.spacex.shared.network.SpaceXApi

class SpaceXSDK(databaseDriverFactory: DatabaseDriverFactory) {
    private val database = Database(databaseDriverFactory)
    private val api = SpaceXApi()

    @Throws(Exception::class)
    suspend fun getLaunches(forceReload: Boolean): List<RocketLaunch> {
        val cachedLaunches = database.getAllLaunches()
        return if (cachedLaunches.isNotEmpty() && !forceReload) {
            cachedLaunches
        } else {
            api.getAllLaunches().also {
                database.clearDatabase()
                database.createLaunches(it)
            }
        }
    }
}