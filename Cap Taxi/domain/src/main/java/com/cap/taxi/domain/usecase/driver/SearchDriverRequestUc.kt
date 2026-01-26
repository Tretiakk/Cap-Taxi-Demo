package com.cap.taxi.domain.usecase.driver

import com.cap.taxi.domain.model.common.DriverCarType
import com.cap.taxi.domain.model.common.ErrCodes
import com.cap.taxi.domain.model.driver.DriverInfo
import com.cap.taxi.domain.model.common.SearchDriverType
import com.cap.taxi.domain.core.TaxiCore

class SearchDriverRequestUc(
    private val state: TaxiCore
) {

    fun execute(
        request: String,
        typeOfSearch: SearchDriverType,
        typeOfCarBody: DriverCarType,
        onFail : (err: Int) -> Unit
    ): List<DriverInfo> {
        val drivers = state.allDrivers
        val searchForBodiesList = ArrayList<DriverInfo>()
        var finalList = ArrayList<DriverInfo>()

        // remove not compatible elements
        if (typeOfCarBody != DriverCarType.NONE) {
            drivers.forEach { driverInfo ->
                if (driverInfo.typeOfCar == typeOfCarBody) {
                    searchForBodiesList.add(driverInfo)
                }
            }
        } else {
            searchForBodiesList.addAll(drivers)
        }


        when (typeOfSearch) {
            SearchDriverType.NAME -> {
                searchForBodiesList.forEach { driver ->
                    if (driver.name.lowercase().contains(request.lowercase())) {
                        finalList.add(driver)
                    }
                }
            }

            SearchDriverType.CAR -> {
                searchForBodiesList.forEach { driver ->
                    if (driver.car.lowercase().contains(request.lowercase())) {
                        finalList.add(driver)
                    }
                }
            }

            SearchDriverType.PRICE -> {
                if (request.all { it.isDigit() }) {
                    finalList = searchForBodiesList

                    if (request.isNotEmpty()) {
                        finalList.sortBy { driver ->
                            Math.abs(request.toInt() - driver.tariffM)
                        }
                    }
                } else {

                    onFail(ErrCodes.INVALID_INPUT_FORMAT)
                }
            }
        }

        return finalList
    }
}