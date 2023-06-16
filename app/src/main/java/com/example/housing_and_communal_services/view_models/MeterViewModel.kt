package com.example.housing_and_communal_services.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.housing_and_communal_services.data.models.MeterReading
import com.example.housing_and_communal_services.data.models.MeterStatus
import com.example.housing_and_communal_services.data.models.MeteringDevice
import com.example.housing_and_communal_services.data.repositories.MeteringDeviceRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Calendar

class MeterViewModel(private val repository: MeteringDeviceRepository) : ViewModel() {
    private val _meteringDevices = MutableStateFlow<List<MeteringDevice>>(emptyList())
    val meteringDevices: StateFlow<List<MeteringDevice>> = _meteringDevices

    fun fetchMeteringDevices(address: String) {
        viewModelScope.launch {
            val devices = repository.fetchMeteringDevices(address)
            _meteringDevices.value = devices
        }
    }

   private val _lastMeterReadingsMap = MutableLiveData<Map<String, MeterReading?>>()
    val lastMeterReadingsMap: LiveData<Map<String, MeterReading?>> = _lastMeterReadingsMap

    //Функция которая получает последние показания по счетчикам
    fun updateLastMeterReadings(serialNumbers: List<String>) {
        viewModelScope.launch {
            val lastMeterReadings = mutableMapOf<String, MeterReading?>()
            for (serialNumber in serialNumbers) {
                val lastMeterReading = repository.getLastMeterReading(serialNumber)
                lastMeterReadings[serialNumber] = lastMeterReading
            }
            _lastMeterReadingsMap.value = lastMeterReadings
        }
    }
    //Функция для обновления состояния прошлого показания
    fun updateLastMeterReadingValue(serialNumber: String) {
        viewModelScope.launch {
            val lastMeterReading = repository.getLastMeterReading(serialNumber)
            _lastMeterReadingsMap.value = _lastMeterReadingsMap.value?.toMutableMap()?.apply {
                put(serialNumber, lastMeterReading)
            }
        }
    }



    fun addMeterReading(meterReading: MeterReading) {
        viewModelScope.launch {
            repository.addMeterReading(meterReading)
        }
    }

    private val _meterStatusMap = MutableLiveData<Map<String, MeterStatus>>()
    val meterStatusMap: LiveData<Map<String, MeterStatus>> = _meterStatusMap

    fun checkIfLastReadingIsCurrentMonthAndYear(serialNumbers: List<String>) {
        viewModelScope.launch {
            val meterStatuses = mutableMapOf<String, MeterStatus>()
            for (serialNumber in serialNumbers) {
                val lastMeterReading = repository.getLastDateMeterReadingBySerialNumber(serialNumber)
                val isCurrentMonthAndYear = lastMeterReading?.let {
                    val lastReadingDate = it.date
                    val currentMonthAndYear = Calendar.getInstance().apply {
                        set(Calendar.DAY_OF_MONTH, 1)
                        set(Calendar.HOUR_OF_DAY, 0)
                        set(Calendar.MINUTE, 0)
                        set(Calendar.SECOND, 0)
                        set(Calendar.MILLISECOND, 0)
                    }.time

                    lastReadingDate == currentMonthAndYear && lastReadingDate == currentMonthAndYear
                } ?: false

                meterStatuses[serialNumber] = MeterStatus(isCurrentMonthAndYear, serialNumber)
            }
            _meterStatusMap.value = meterStatuses
        }
    }

}

class MeterViewModelFactory(private val repository: MeteringDeviceRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MeterViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MeterViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}