package com.icabanas.parkinggaragechallenge

import com.icabanas.parkinggaragechallenge.repository.ParkingRepository
import com.icabanas.parkinggaragechallenge.vo.Level
import com.icabanas.parkinggaragechallenge.vo.Parking
import com.icabanas.parkinggaragechallenge.vo.Spot
import com.icabanas.parkinggaragechallenge.vo.Vehicle
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.util.Date

@RunWith(JUnit4::class)
class ParkingRepositoryTest {

    private lateinit var parkingRepository: ParkingRepository

    @Before
    fun setup() {
        parkingRepository = ParkingRepository()
    }

    @Test
    fun testNoEmptySpots() {
        val size = 15
        val spot = parkingRepository.findFreeParkingSpot(size, parkingMock)
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
        var parking = parkingMock.copy(levels = levels)

        //Test spot is null when there are empty spots
        //but their size is not enough to fit the vehicle
        val spot = parkingRepository.findFreeParkingSpot(size, parking)
        assertNull(spot)
    }

    @Test
    fun testEmptySpots_whenMultipleAvailableFits() {
        var size = 4
        var spots:MutableList<Spot> = mutableListOf()
            for (j in 1..10) {
                spots.add(spotMock.copy(size = j, vehicle = null))
            }
        var parking = parkingMock.copy(levels = listOf(levelMock.copy(spots = spots)))

        //Test multiple sizes of vehicles combined with
        //multiple available spots with different sizes
        var spot = parkingRepository.findFreeParkingSpot(size, parking)
        assertNotNull(spot)
        assert(spot?.size == size)

        size = 6
        spot = parkingRepository.findFreeParkingSpot(size, parking)
        assertNotNull(spot)
        assert(spot?.size == size)

        size = 30
        spot = parkingRepository.findFreeParkingSpot(size, parking)
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
        var parking = parkingMock.copy(levels = levels)
        val spot = parkingRepository.findFreeParkingSpot(size, parking)
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

