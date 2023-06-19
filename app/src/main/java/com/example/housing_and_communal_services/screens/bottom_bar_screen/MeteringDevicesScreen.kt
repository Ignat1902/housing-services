package com.example.housing_and_communal_services.screens.bottom_bar_screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.housing_and_communal_services.R
import com.example.housing_and_communal_services.data.models.MeterReading
import com.example.housing_and_communal_services.data.models.MeterStatus
import com.example.housing_and_communal_services.data.models.MeteringDevice
import com.example.housing_and_communal_services.data.models.User
import com.example.housing_and_communal_services.data.repositories.MeteringDeviceRepository
import com.example.housing_and_communal_services.view_models.MeterViewModel
import com.example.housing_and_communal_services.view_models.MeterViewModelFactory
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@Composable
fun MeteringDevicesScreen(navController: NavController) {

    var user by remember { mutableStateOf<User?>(null) }
    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(Unit) {
        coroutineScope.launch {
            val meteringDeviceRepository = MeteringDeviceRepository()
            user = meteringDeviceRepository.fetchUserById()
        }
    }

    val viewModel: MeterViewModel = viewModel(
        factory = MeterViewModelFactory(
            MeteringDeviceRepository()
        )
    )

    val meteringDevices by viewModel.meteringDevices.collectAsState()
    val lastMeterReadingsMap by viewModel.lastMeterReadingsMap.observeAsState(emptyMap())
    val serialNumbers = meteringDevices.map { it.serial_number }

    val meterStatusMap by viewModel.meterStatusMap.observeAsState(emptyMap())
    // Вызовите функцию checkIfLastReadingIsCurrentMonthAndYear при первом отображении Composable
    LaunchedEffect(serialNumbers) {
        meteringDevices.map { it.serial_number }
            .let { viewModel.checkIfLastReadingIsCurrentMonthAndYear(it as List<String>) }
    }

    user?.address?.let { viewModel.fetchMeteringDevices(it) }
    viewModel.updateLastMeterReadings(meteringDevices.map { it.serial_number!! })

    if (meteringDevices.isEmpty()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                "По вашему адресу не найдено счетчиков, необходимо сделать поверку",
                modifier = Modifier.padding(top = 24.dp),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleMedium,
            )
        }
    } else {
        MeterList(meteringDevices, lastMeterReadingsMap, meterStatusMap, viewModel, navController)
    }
}


@Composable
fun MeterList(
    meteringDevices: List<MeteringDevice>,
    lastMeterReadingsMap: Map<String, MeterReading?>,
    meterStatusMap: Map<String, MeterStatus>,
    viewModel: MeterViewModel,
    navController: NavController
) {
    LazyColumn {
        items(meteringDevices) { meteringDevice ->
            Column(modifier = Modifier.padding(16.dp)) {
                CounterCard(
                    meteringDevice = meteringDevice,
                    lastMeterReadingsMap = lastMeterReadingsMap,
                    viewModel = viewModel,
                    isCurrentMonthAndYear = meterStatusMap,
                    navController = navController
                )
            }
        }
    }
}

@Composable
fun CounterCard(
    meteringDevice: MeteringDevice,
    lastMeterReadingsMap: Map<String, MeterReading?>,
    viewModel: MeterViewModel,
    isCurrentMonthAndYear: Map<String, MeterStatus>,
    navController: NavController
) {
    val context = LocalContext.current
    var inputValue by remember { mutableStateOf("") }
    val currentDate = remember {
        getCurrentDate()
    }
    // Переменная для отображения диалогового окна, если пользователь ввел неправильные данные
    val showDialog = remember { mutableStateOf(false) }
    // Состояние для текстового поля
    val isError = remember { mutableStateOf(false) }


    Card(
        /*border = ButtonDefaults.outlinedButtonBorder,
        colors = CardDefaults.outlinedCardColors(),*/
        shape = RoundedCornerShape(8.dp),
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                Modifier
                    .padding(
                        vertical = 16.dp
                    )
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                Icon(
                    modifier = Modifier.size(48.dp),
                    tint =
                    when (meteringDevice.name) {
                        "Холодная вода" -> MaterialTheme.colorScheme.onSurface
                        "Горячая вода" -> MaterialTheme.colorScheme.error
                        "Газ" -> MaterialTheme.colorScheme.onSurface
                        else -> Color.Yellow
                    },
                    painter = painterResource(
                        id = if (meteringDevice.name == "Холодная вода" || meteringDevice.name == "Горячая вода") {
                            R.drawable.baseline_water_drop_24
                        } else if (meteringDevice.name == "Электричество день" || meteringDevice.name == "Электричество ночь") {
                            R.drawable.baseline_bolt_24
                        } else R.drawable.baseline_gas_meter_24
                    ),
                    contentDescription = "Icon"
                )


                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = "${meteringDevice.name}",
                        style = MaterialTheme.typography.titleMedium,
                        textAlign = TextAlign.Center
                    )
                    Row(
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Номер ИПУ: ",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.outline,
                        )
                        Text(
                            text = "${meteringDevice.serial_number}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.tertiary,
                        )
                    }
                }
            }

            if (isCurrentMonthAndYear[meteringDevice.serial_number]?.isCurrentMonthAndYear == false) {
                OutlinedTextField(
                    value = inputValue,
                    onValueChange = {
                        inputValue = it
                    },
                    label = { Text(text = "Показания") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    placeholder = {
                        lastMeterReadingsMap[meteringDevice.serial_number]?.value?.toString()
                            ?.let { Text(text = it) }
                    },
                    singleLine = true,
                    isError = isError.value,
                    modifier = Modifier
                        .fillMaxWidth(),
                )
            }


            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Прошлые показания: ",
                    color = MaterialTheme.colorScheme.outline,
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "${lastMeterReadingsMap[meteringDevice.serial_number]?.value ?: "Нет данных"}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.tertiary,
                )
            }

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Дата поверки: ",
                    color = MaterialTheme.colorScheme.outline,
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "${meteringDevice.verification_date}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.tertiary,
                )
            }

            // Уведомление
            if (isCurrentMonthAndYear[meteringDevice.serial_number]?.isCurrentMonthAndYear == true) {
                Notify(
                    icon = R.drawable.baseline_security_update_good_24,
                    text = "Показания успешно переданы",
                    boolean = false
                )
            } else {
                Notify(
                    icon = R.drawable.baseline_assignment_late_24,
                    text = "Передайте показания до 26 числа текущего месяца",
                    boolean = true
                )
            }




            Row(
                Modifier
                    .padding(vertical = 16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                OutlinedButton(
                    colors = ButtonDefaults.filledTonalButtonColors(
                        contentColor = MaterialTheme.colorScheme.onSurface,
                        containerColor = MaterialTheme.colorScheme.inverseOnSurface
                    ),
                    border = null,
                    onClick = {
                        //navController.navigate("detailScreen/${meteringDevice.serial_number}")
                        val serialNumber = meteringDevice.serial_number
                        val name = meteringDevice.name
                        navController.navigate("detailScreen/$serialNumber/$name"){
                            launchSingleTop = true
                        }
                    }
                ) {
                    Text(text = "История показаний")
                }

                if (isCurrentMonthAndYear[meteringDevice.serial_number]?.isCurrentMonthAndYear == false) {
                    OutlinedButton(
                        colors = ButtonDefaults.filledTonalButtonColors(
                            contentColor = MaterialTheme.colorScheme.onPrimary,
                            containerColor = MaterialTheme.colorScheme.primary
                        ),
                        border = null,
                        onClick = {
                            if (inputValue.toDoubleOrNull() != null) {
                                if (inputValue.toDouble() != (lastMeterReadingsMap[meteringDevice.serial_number]?.value
                                        ?: Double.NaN)
                                ) {
                                    viewModel.addMeterReading(
                                        MeterReading(
                                            serial_number = meteringDevice.serial_number!!,
                                            date = currentDate,
                                            value = inputValue.toDoubleOrNull() ?: 0.0
                                        )
                                    )

                                    viewModel.checkIfLastReadingIsCurrentMonthAndYear(
                                        lastMeterReadingsMap.keys.toList()
                                    )
                                    viewModel.updateLastMeterReadingValue(meteringDevice.serial_number)
                                    Toast.makeText(
                                        context,
                                        "Показания успешно переданы",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    isError.value = false
                                    inputValue = ""
                                } else {
                                    showDialog.value = true
                                }
                            } else {
                                isError.value = true
                                Toast.makeText(
                                    context,
                                    "Проверьте коректность данных и внесите показания",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                        }
                    ) {
                        Text(text = "Внести")
                    }
                }

            }

            if (showDialog.value) {
                AlertDialog(
                    onDismissRequest = { showDialog.value = false },
                    title = { Text(text = "Внимание") },
                    text = { Text(text = "Текущие показания не могут быть меньше предыдущих") },
                    confirmButton = {
                        OutlinedButton(
                            onClick = { showDialog.value = false }
                        ) {
                            Text(text = "ОК")
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun Notify(icon: Int, text: String, boolean: Boolean) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .background(
                if (boolean) {
                    MaterialTheme.colorScheme.error
                } else {
                    MaterialTheme.colorScheme.secondary
                }

            )
            .fillMaxWidth()
            .padding(vertical = 10.dp, horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.size(24.dp),
            tint = if (boolean) {
                MaterialTheme.colorScheme.onError
            } else {
                MaterialTheme.colorScheme.onSecondary
            },
            painter = painterResource(id = icon),
            contentDescription = "Icon"
        )

        Text(
            text = text,
            style = MaterialTheme.typography.labelMedium,
            color = if (boolean) {
                MaterialTheme.colorScheme.onError
            } else {
                MaterialTheme.colorScheme.onSecondary
            }
        )
    }
}

fun getCurrentDate(): Date {
    val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
    val currentDate = Date()
    val formattedDate = dateFormat.format(currentDate)
    return dateFormat.parse(formattedDate) ?: currentDate
}