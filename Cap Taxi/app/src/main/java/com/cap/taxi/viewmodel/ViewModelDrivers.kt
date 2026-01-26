package com.cap.taxi.viewmodel

import android.content.Context
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cap.taxi.R
import com.cap.taxi.domain.model.common.DriverCarType
import com.cap.taxi.domain.model.common.ErrCodes
import com.cap.taxi.domain.model.common.SearchDriverType
import com.cap.taxi.facade.DriversModel
import com.cap.taxi.facade.OverlayController
import com.cap.taxi.facade.states.DriverInfoUi
import com.cap.taxi.facade.states.toUi
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModelDrivers @Inject constructor(
    @param:ApplicationContext private val context: Context,
    private val model: DriversModel,
    private val overlay: OverlayController
) : ViewModel() {

    private val _isExplanationOfConstructionVisible = MutableStateFlow(false)

    private val _searchDriverType = MutableStateFlow(SearchDriverType.NAME)
    private val _searchDriverCarType = MutableStateFlow(DriverCarType.NONE)
    private val _textOfSearchDriver = MutableStateFlow("")

    //////////////
    //////////////

    val isExplanationOfConstructionVisible = _isExplanationOfConstructionVisible.asStateFlow()

    val listOfDrivers = mutableStateListOf<DriverInfoUi>()

    val searchDriverType = _searchDriverType.asStateFlow()
    val searchDriverCarType = _searchDriverCarType.asStateFlow()
    val textOfSearchDriver = _textOfSearchDriver.asStateFlow()

    val currentDriver = model.currentDriver
        .map { it?.toUi() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    fun realize() {
        loadDrivers()
    }

    fun start() {

    }

    fun selectDriver(driverState: DriverInfoUi) {
        model.selectDriver(driverState)
    }

    fun clickDriverCard(index: Int, driver: DriverInfoUi) {
        val extended = !driver.isExpended
        val updatedState = driver.copy(isExpended = extended)

        listOfDrivers[index] = updatedState
    }

    fun selectSearchType(type: SearchDriverType) {
        _searchDriverType.value = type

        setTextSearchDriver("")
    }

    fun selectSearchCarType(type: DriverCarType) {
        _searchDriverCarType.value = type

        setTextSearchDriver("")
    }

    fun setTextSearchDriver(text: String) {
        _textOfSearchDriver.value = text

        viewModelScope.launch {
            val driversFound = model.searchForDrivers(
                text,
                searchDriverType.value,
                searchDriverCarType.value,
                onFail = { err ->
                    // Notify user
                    if (err == ErrCodes.INVALID_INPUT_FORMAT) {
                        overlay.message(
                            shortDescription = context.getString(R.string.error_occurred),
                            description = context.getString(R.string.error_text_only_numbers),
                            buttonText = context.getString(R.string.ok),
                            onButtonClick = { hide ->
                                _textOfSearchDriver.value = ""

                                hide()
                            }
                        )
                    } else {
                        overlay.message(
                            shortDescription = context.getString(R.string.problem),
                            description = context.getString(R.string.error_occurred),
                            buttonText = context.getString(R.string.ok),
                            onButtonClick = { hide ->
                                _textOfSearchDriver.value = ""

                                hide()
                            }
                        )
                    }
                }
            )

            updateDrivers(driversFound)
        }
    }

    fun visibleExplanationOfDriverCard(visible: Boolean) {
        _isExplanationOfConstructionVisible.value = visible
    }

    private fun loadDrivers() {
        if (listOfDrivers.isEmpty()) {
            viewModelScope.launch(Dispatchers.IO) {
                val loadedDrivers = model.loadDrivers()

                updateDrivers(loadedDrivers)
            }
        }
    }

    private fun updateDrivers(drivers: List<DriverInfoUi>){
        listOfDrivers.clear()
        listOfDrivers.addAll(drivers)
    }
}