package com.cap.taxi.data.db

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.cap.taxi.data.R
import com.cap.taxi.domain.data.IDriversDB
import com.cap.taxi.domain.model.common.DriverCarType
import com.cap.taxi.domain.model.driver.DriverInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream

class DriverDBStub(private val context: Context) : IDriversDB {
    private val testDriversDatabase = ArrayList<DriverInfo>()

    override suspend fun getDrivers(): ArrayList<DriverInfo> = withContext(Dispatchers.IO) {
        val sedan = DriverCarType.SEDAN
        val hatchback = DriverCarType.HATCHBACK
        val crossover = DriverCarType.CROSSOVER
        val coupe = DriverCarType.COUPE
        val station = DriverCarType.STATION_WAGON

        val men = getMenDrivers(context)
        val women = getWomenDrivers(context)

        testDriversDatabase.add(
            DriverInfo(
                1001,
                "James",
                39,
                25,
                15,
                4.4f,
                "Toyota Camry 2024",
                sedan,
                4,
                "+1-000-000-0000",
                bitmapToJpegBytes(men[0])
            )
        )
        testDriversDatabase.add(
            DriverInfo(
                1002,
                "John",
                39,
                24,
                14,
                4.8f,
                "Honda Accord 2023",
                sedan,
                7,
                "+1-000-000-0000",
                bitmapToJpegBytes(men[23])
            )
        )
        testDriversDatabase.add(
            DriverInfo(
                1003,
                "Robert",
                55,
                35,
                29,
                4.1f,
                "Ford Mustang 2022",
                coupe,
                3,
                "+1-000-000-0000",
                bitmapToJpegBytes(men[6])
            )
        )
        testDriversDatabase.add(
            DriverInfo(
                1004,
                "Michael",
                35,
                24,
                14,
                3.6f,
                "Chevrolet Malibu 2023",
                sedan,
                2,
                "+1-000-000-0000",
                bitmapToJpegBytes(men[20])
            )
        )
        testDriversDatabase.add(
            DriverInfo(
                1005,
                "William",
                39,
                29,
                19,
                4.2f,
                "Nissan Altima 2023",
                sedan,
                5,
                "+1-000-000-0000",
                bitmapToJpegBytes(men[5])

            )
        )
        testDriversDatabase.add(
            DriverInfo(
                1006,
                "Charlotte",
                39,
                29,
                19,
                4.9f,
                "Toyota Venza 2024",
                station,
                4,
                "+1-000-000-0000",
                bitmapToJpegBytes(women[0])
            )
        )
        testDriversDatabase.add(
            DriverInfo(
                1007,
                "Mary",
                35,
                24,
                15,
                4.2f,
                "Hyundai Sonata 2024",
                sedan,
                2,
                "+1-000-000-0000",
                bitmapToJpegBytes(women[4])
            )
        )
        testDriversDatabase.add(
            DriverInfo(
                1008,
                "David",
                34,
                24,
                15,
                3.7f,
                "Subaru Legacy 2023",
                sedan,
                1,
                "+1-000-000-0000",
                bitmapToJpegBytes(men[21])

            )
        )
        testDriversDatabase.add(
            DriverInfo(
                1009,
                "Alexander",
                35,
                29,
                19,
                4.8f,
                "Mercedes-Benz E-Class Wagon 2024",
                station,
                8,
                "+1-000-000-0000",
                bitmapToJpegBytes(men[17])

            )
        )
        testDriversDatabase.add(
            DriverInfo(
                1010,
                "Richard",
                49,
                34,
                24,
                4.7f,
                "Mazda 6 2023",
                sedan,
                6,
                "+1-000-000-0000",
                bitmapToJpegBytes(men[22])

            )
        )
        testDriversDatabase.add(
            DriverInfo(
                1011,
                "Charles",
                34,
                20,
                14,
                4.6f,
                "Kia K5 2024",
                sedan,
                4,
                "+1-000-000-0000",
                bitmapToJpegBytes(men[18])

            )
        )
        testDriversDatabase.add(
            DriverInfo(
                1012,
                "Thomas",
                44,
                29,
                19,
                5.0f,
                "Volkswagen Passat 2022",
                station,
                5,
                "+1-000-000-0000",
                bitmapToJpegBytes(men[19])

            )
        )
        testDriversDatabase.add(
            DriverInfo(
                1013,
                "Daniel",
                49,
                34,
                24,
                4.4f,
                "Jeep Cherokee 2023",
                crossover,
                4,
                "+1-000-000-0000",
                null
            )
        )
        testDriversDatabase.add(
            DriverInfo(
                1014,
                "Matthew",
                55,
                39,
                25,
                3.7f,
                "Toyota RAV4 2024",
                crossover,
                6,
                "+1-000-000-0000",
                bitmapToJpegBytes(men[12])

            )
        )
        testDriversDatabase.add(
            DriverInfo(
                1015,
                "Mason",
                55,
                39,
                25,
                4.6f,
                "Honda Civic Hatchback 2023",
                hatchback,
                9,
                "+1-000-000-0000",
                bitmapToJpegBytes(men[11])

            )
        )
        testDriversDatabase.add(
            DriverInfo(
                1016,
                "Anthony",
                49,
                34,
                24,
                4.9f,
                "Honda CR-V 2023",
                crossover,
                11,
                "+1-000-000-0000",
                bitmapToJpegBytes(men[9])

            )
        )
        testDriversDatabase.add(
            DriverInfo(
                1017,
                "Jennifer",
                35,
                20,
                15,
                4.2f,
                "Mini Cooper 2023",
                hatchback,
                1,
                "+1-000-000-0000",
                bitmapToJpegBytes(women[2])

            )
        )
        testDriversDatabase.add(
            DriverInfo(
                1018,
                "Patricia",
                29,
                19,
                14,
                4.0f,
                "Fiat 500 2024",
                hatchback,
                2,
                "+1-000-000-0000",
                bitmapToJpegBytes(women[1])
            )
        )
        testDriversDatabase.add(
            DriverInfo(
                1019,
                "Jackson",
                39,
                29,
                19,
                4.5f,
                "Volvo V60 2023",
                station,
                5,
                "+1-000-000-0000",
                bitmapToJpegBytes(men[7])

            )
        )
        testDriversDatabase.add(
            DriverInfo(
                1020,
                "Mark",
                50,
                29,
                19,
                4.1f,
                "Hyundai Tucson 2024",
                crossover,
                7,
                "+1-000-000-0000",
                bitmapToJpegBytes(men[5])
            )
        )
        testDriversDatabase.add(
            DriverInfo(
                1021,
                "Donald",
                55,
                35,
                24,
                3.9f,
                "Subaru Forester 2023",
                crossover,
                5,
                "+1-000-000-0000",
                bitmapToJpegBytes(men[6])
            )
        )
        testDriversDatabase.add(
            DriverInfo(
                1022,
                "Amelia",
                30,
                20,
                14,
                4.7f,
                "Nissan Versa Note 2023",
                hatchback,
                9,
                "+1-000-000-0000",
                bitmapToJpegBytes(women[3])
            )
        )
        testDriversDatabase.add(
            DriverInfo(
                1023,
                "Steven",
                50,
                34,
                24,
                5.0f,
                "Mazda CX-5 2024",
                crossover,
                14,
                "+1-000-000-0000",
                bitmapToJpegBytes(men[4])
            )
        )
        testDriversDatabase.add(
            DriverInfo(
                1024,
                "Paul",
                45,
                30,
                19,
                4.2f,
                "Kia Sportage 2023",
                crossover,
                3,
                "+1-000-000-0000",
                bitmapToJpegBytes(men[3])
            )
        )
        testDriversDatabase.add(
            DriverInfo(
                1025,
                "Aria",
                45,
                30,
                19,
                4.7f,
                "Hyundai Ioniq 5 Wagon 2024",
                station,
                2,
                "+1-000-000-0000",
                bitmapToJpegBytes(women[5])
            )
        )
        testDriversDatabase.add(
            DriverInfo(
                1026,
                "John",
                49,
                29,
                24,
                4.9f,
                "Chevrolet Camaro 2023",
                coupe,
                8,
                "+1-000-000-0000",
                bitmapToJpegBytes(men[1])
            )
        )
        testDriversDatabase.add(
            DriverInfo(
                1027,
                "Andrew",
                60,
                35,
                25,
                4.3f,
                "Volkswagen Tiguan 2023",
                crossover,
                7,
                "+1-000-000-0000",
                bitmapToJpegBytes(men[16])
            )
        )
        testDriversDatabase.add(
            DriverInfo(
                1028,
                "Robert",
                59,
                44,
                34,
                4.7f,
                "BMW M4 2023",
                coupe,
                11,
                "+1-000-000-0000",
                bitmapToJpegBytes(men[2])
            )
        )
        testDriversDatabase.add(
            DriverInfo(
                1029,
                "Linda",
                34,
                24,
                19,
                4.8f,
                "Mercedes-Benz A-Class 2024",
                sedan,
                5,
                "+1-000-000-0000",
                bitmapToJpegBytes(women[6])
            )
        )
        testDriversDatabase.add(
            DriverInfo(
                1030,
                "Joshua",
                39,
                29,
                19,
                4.0f,
                "Ford Escape 2023",
                crossover,
                3,
                "+1-000-000-0000",
                bitmapToJpegBytes(men[10])
            )
        )
        testDriversDatabase.add(
            DriverInfo(
                1031,
                "Brian",
                44,
                34,
                24,
                4.1f,
                "Chevrolet Equinox 2023",
                crossover,
                7,
                "+1-000-000-0000",
                bitmapToJpegBytes(men[15])
            )
        )
        testDriversDatabase.add(
            DriverInfo(
                1032,
                "Ethan",
                39,
                29,
                19,
                4.9f,
                "Audi A5 2023",
                coupe,
                8,
                "+1-000-000-0000",
                bitmapToJpegBytes(men[14])
            )
        )
        testDriversDatabase.add(
            DriverInfo(
                1033,
                "Liam",
                69,
                49,
                29,
                5.0f,
                "Mercedes-Benz C-Class Coupe 2024",
                coupe,
                15,
                "+1-000-000-0000",
                bitmapToJpegBytes(men[6])
            )
        )
        testDriversDatabase.add(
            DriverInfo(
                1034,
                "Lucas",
                39,
                24,
                14,
                4.3f,
                "Volkswagen Golf 2024",
                hatchback,
                1,
                "+1-000-000-0000",
                bitmapToJpegBytes(men[13])
            )
        )
        testDriversDatabase.add(
            DriverInfo(
                1035,
                "Levi",
                35,
                25,
                15,
                4.1f,
                "Toyota Corolla Hatchback 2024",
                hatchback,
                4,
                "+1-000-000-0000",
                bitmapToJpegBytes(men[8])
            )
        )

        return@withContext testDriversDatabase
    }

    private fun getMenDrivers(context: Context): ArrayList<Bitmap> {
        val listOfDrivers = ArrayList<Bitmap>()

        val resources = context.resources
        listOfDrivers.add(BitmapFactory.decodeResource(resources, R.drawable.men_1))
        listOfDrivers.add(BitmapFactory.decodeResource(resources, R.drawable.men_2))
        listOfDrivers.add(BitmapFactory.decodeResource(resources, R.drawable.men_3))
        listOfDrivers.add(BitmapFactory.decodeResource(resources, R.drawable.men_4))
        listOfDrivers.add(BitmapFactory.decodeResource(resources, R.drawable.men_5))
        listOfDrivers.add(BitmapFactory.decodeResource(resources, R.drawable.men_6))
        listOfDrivers.add(BitmapFactory.decodeResource(resources, R.drawable.men_7))
        listOfDrivers.add(BitmapFactory.decodeResource(resources, R.drawable.men_8))
        listOfDrivers.add(BitmapFactory.decodeResource(resources, R.drawable.men_9))
        listOfDrivers.add(BitmapFactory.decodeResource(resources, R.drawable.men_10))
        listOfDrivers.add(BitmapFactory.decodeResource(resources, R.drawable.men_11))
        listOfDrivers.add(BitmapFactory.decodeResource(resources, R.drawable.men_12))
        listOfDrivers.add(BitmapFactory.decodeResource(resources, R.drawable.men_13))
        listOfDrivers.add(BitmapFactory.decodeResource(resources, R.drawable.men_14))
        listOfDrivers.add(BitmapFactory.decodeResource(resources, R.drawable.men_16))
        listOfDrivers.add(BitmapFactory.decodeResource(resources, R.drawable.men_17))
        listOfDrivers.add(BitmapFactory.decodeResource(resources, R.drawable.men_19))
        listOfDrivers.add(BitmapFactory.decodeResource(resources, R.drawable.men_20))
        listOfDrivers.add(BitmapFactory.decodeResource(resources, R.drawable.men_21))
        listOfDrivers.add(BitmapFactory.decodeResource(resources, R.drawable.men_22))
        listOfDrivers.add(BitmapFactory.decodeResource(resources, R.drawable.men_23))
        listOfDrivers.add(BitmapFactory.decodeResource(resources, R.drawable.men_24))
        listOfDrivers.add(BitmapFactory.decodeResource(resources, R.drawable.men_25))
        listOfDrivers.add(BitmapFactory.decodeResource(resources, R.drawable.men_26))

        return listOfDrivers
    }

    private fun getWomenDrivers(context: Context): ArrayList<Bitmap> {
        val listOfDrivers = ArrayList<Bitmap>()

        val resources = context.resources
        listOfDrivers.add(BitmapFactory.decodeResource(resources, R.drawable.woman_1))
        listOfDrivers.add(BitmapFactory.decodeResource(resources, R.drawable.woman_2))
        listOfDrivers.add(BitmapFactory.decodeResource(resources, R.drawable.woman_3))
        listOfDrivers.add(BitmapFactory.decodeResource(resources, R.drawable.woman_4))
        listOfDrivers.add(BitmapFactory.decodeResource(resources, R.drawable.woman_5))
        listOfDrivers.add(BitmapFactory.decodeResource(resources, R.drawable.woman_6))
        listOfDrivers.add(BitmapFactory.decodeResource(resources, R.drawable.woman_7))

        return listOfDrivers
    }

    private fun bitmapToJpegBytes(bitmap: Bitmap, quality: Int = 90): ByteArray {
        val out = ByteArrayOutputStream()
        val ok = bitmap.compress(Bitmap.CompressFormat.JPEG, quality, out)
        check(ok) { "Bitmap.compress failed" }
        return out.toByteArray()
    }

    private fun getCoordinates() {
        val lanStatuaZL = 49.809763
        val lonStatuaZL = 24.897144

        val lanMoaHataZL = 49.810020
        val lonSMoaHataZL = 24.891731

        val lanCenterLviv = 49.844511
        val lonCenterLviv = 24.025904

        val lanHresnaLviv = 49.817250
        val lonHresnaLviv = 24.072008
    }
}