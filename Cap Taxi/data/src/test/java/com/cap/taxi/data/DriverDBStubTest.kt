package com.cap.taxi.data

import com.cap.taxi.data.db.DriverDBStub

import androidx.test.core.app.ApplicationProvider
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class DriverDBStubTest {

    private lateinit var db: DriverDBStub

    @Before fun setUp() {
        db = DriverDBStub(ApplicationProvider.getApplicationContext())
    }

    @Test fun `getDrivers returns 35 drivers and Daniel has null photo`() = runTest {
        val drivers = db.getDrivers()

        assertEquals(35, drivers.size)

        // data class safe-check via componentN (constructor has 11 params in stub)
        val daniel = drivers.first { it.component1() == 1013 }
        assertNull(daniel.component11())

        val first = drivers.first()
        assertEquals(1001, first.component1())
        assertEquals("James", first.component2())
        val photo = first.component11()
        assertNotNull(photo)
        assertTrue(photo!!.isNotEmpty())
    }
}
