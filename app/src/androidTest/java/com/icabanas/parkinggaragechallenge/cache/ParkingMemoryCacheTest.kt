package com.icabanas.parkinggaragechallenge.cache

import android.preference.PreferenceManager
import android.support.test.InstrumentationRegistry
import com.google.gson.GsonBuilder
import com.icabanas.parkinggaragechallenge.vo.Level
import com.icabanas.parkinggaragechallenge.vo.Parking
import com.icabanas.parkinggaragechallenge.vo.Spot
import com.icabanas.parkinggaragechallenge.vo.Vehicle
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.util.Date


@RunWith(JUnit4::class)
class ParkingMemoryCacheTest {

    private lateinit var parkingMemoryCache: ParkingMemoryCache

    @Before
    fun setup() {
        parkingMemoryCache = ParkingMemoryCache(
                PreferenceManager.getDefaultSharedPreferences(InstrumentationRegistry.getContext()),
                GsonBuilder()
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                    .setLenient()
                    .create())

        parkingMemoryCache.storedParking = parkingMock
    }

    @After
    fun clearData() {
        PreferenceManager.getDefaultSharedPreferences(InstrumentationRegistry.getContext()).edit().clear().commit()
    }

    @Test
    fun testBookReservedSpot() {
        assertFalse(parkingMemoryCache.bookSpot(1, 1, vehicleMock))
    }


    @Test
    fun testReleaseSpot() {
        assertTrue(parkingMemoryCache.releaseSpot(1, 1))
    }

    @Test
    fun testReleaseThenBookSpot() {
        //First release a booked Spot
        assertTrue(parkingMemoryCache.releaseSpot(1, 1))

        var filteredLevels = parkingMemoryCache.storedParking.levels.filter { it.id == 1 }
        assertTrue(filteredLevels.isNotEmpty())

        var filteredSpots = filteredLevels.first().spots.filter { it.id == 1 }
        assertTrue(filteredSpots.isNotEmpty())
        assertNull(filteredSpots.first().vehicle)

        //Succeed re-booking the recently released Spot
        assert(parkingMemoryCache.bookSpot(1, 1, vehicleMock))

        filteredLevels = parkingMemoryCache.storedParking.levels.filter { it.id == 1 }
        filteredSpots = filteredLevels.first().spots.filter { it.id == 1 }
        assertNotNull(filteredSpots.first().vehicle)
    }

    @Test
    fun testNoEmptySpots() {
        val size = 15
        val spot = parkingMemoryCache.findFreeParkingSpot(size)
        assertNull(spot)
    }

    @Test
    fun testEmptySpots_whenNoFittingSpot() {
        val size = 15
        var levels:MutableList<Level> = mutableListOf()
        for (i in 1..10) {
            var spots:MutableList<Spot> = mutableListOf()
            for (j in 1..10) {
                spots.add(spotMock.copy(size = j, vehicle = null))
            }
            levels.add(levelMock.copy(spots = spots))
        }
        parkingMemoryCache.storedParking = parkingMock.copy(levels = levels)

        //Test spot is null when there are empty spots
        //but their size is not enough to fit the vehicle
        val spot = parkingMemoryCache.findFreeParkingSpot(size)
        assertNull(spot)
    }

    @Test
    fun testEmptySpots_whenMultipleAvailableFits() {
        var size = 4
        var spots:MutableList<Spot> = mutableListOf()
        for (j in 1..10) {
            spots.add(spotMock.copy(size = j, vehicle = null))
        }
        parkingMemoryCache.storedParking = parkingMock.copy(levels = listOf(levelMock.copy(spots = spots)))

        //Test multiple sizes of vehicles combined with
        //multiple available spots with different sizes
        var spot = parkingMemoryCache.findFreeParkingSpot(size)
        assertNotNull(spot)
        assert(spot?.size == size)

        size = 6
        spot = parkingMemoryCache.findFreeParkingSpot(size)
        assertNotNull(spot)
        assert(spot?.size == size)

        size = 30
        spot = parkingMemoryCache.findFreeParkingSpot(size)
        assertNull(spot)
    }

    @Test
    fun testEmptySpots_whenMultipleLevels() {
        val size = 4
        var levels:MutableList<Level> = mutableListOf()
        for (i in 0..3) {
            var spots:MutableList<Spot> = mutableListOf()
            for (j in 0..9) {
                spots.add(spotMock.copy(id = i*10 + j, size = j, vehicle = null))
            }
            levels.add(levelMock.copy(spots = spots))
        }
        parkingMemoryCache.storedParking = parkingMock.copy(levels = levels)
        val spot = parkingMemoryCache.findFreeParkingSpot(size)
        assertNotNull(spot)
        assert(spot?.size == size)
        assert(spot!!.size < 10)
    }

    //MOCKS FOR REMOTE API RETURNED OBJECTS
    companion object {

        val vehicleMock = Vehicle("PLATE", "BRAND", "COLOR", 10, Date())

        val spotMock = Spot(1,10, vehicleMock)

        val levelMock = Level(1, "LEVEL", listOf(spotMock))

        val parkingMock = Parking(1, "TEST PARKING", listOf(levelMock))

    }

}