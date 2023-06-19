package com.example.housing_and_communal_services.screens.authorization.registration

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.housing_and_communal_services.R
import com.example.housing_and_communal_services.showBars
import com.example.housing_and_communal_services.view_models.LoginViewModel


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AccountNumberEntryPage(
    loginViewModel: LoginViewModel? = null,
    onNextPage: () -> Unit
) {
    showBars(flag = true)
    val loginUiState = loginViewModel?.loginUiState
    val showDialog = remember { mutableStateOf(false) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp),
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 32.dp)
    ) {
        Text(
            "Введите номер личного счета",
            modifier = Modifier.padding(top = 24.dp),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground
        )


        OutlinedTextField(
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            value = loginUiState?.accountNumber ?: "",
            onValueChange = {
                loginViewModel?.onAccountNumberChange(it)
            },
            leadingIcon = {
                IconButton(
                    onClick = {
                        showDialog.value = true
                    },
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_info_24),
                        contentDescription = "Информация",
                    )
                }
            },
            label = { Text(text = "Номер ЛС") },
            placeholder = {
                Text(
                    text = "Введите номер ЛС",
                    maxLines = 1
                )
            },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
        )

        if(loginUiState?.accountNumber?.length == 8){
            loginViewModel.getAddressByAccountNumber()
            if (!loginUiState.addressError) {
                Text(
                    "По данному лицевому счету был найден следующий адрес:",
                    modifier = Modifier.padding(top = 24.dp),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Card(
                    border = ButtonDefaults.outlinedButtonBorder,
                    colors = CardDefaults.outlinedCardColors(),
                    shape = RoundedCornerShape(8.dp),
                ) {
                    Column(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Row(
                            Modifier
                                .padding(
                                    vertical = 16.dp
                                )
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .background(MaterialTheme.colorScheme.primary, CircleShape)
                                    .padding(8.dp)

                            ) {
                                Icon(
                                    modifier = Modifier.size(24.dp),
                                    tint = MaterialTheme.colorScheme.onPrimary,
                                    painter = painterResource(id = R.drawable.apartment_48px),
                                    contentDescription = "Icon"
                                )

                            }
                            Column(
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    loginUiState.address,
                                    style = MaterialTheme.typography.titleMedium,
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                            }
                        }
                    }
                }

                Button(
                    onClick = {
                        onNextPage.invoke()
                    },
                    modifier = Modifier
                        .wrapContentHeight()
                        .padding(horizontal = 24.dp, vertical = 24.dp),
                    shape = RoundedCornerShape(4.dp),
                ) {
                    Text(
                        text = "Подтвердить адрес",
                        color = MaterialTheme.colorScheme.onPrimary,
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(vertical = 4.dp, horizontal = 12.dp)
                    )
                }
            } else {
                Text(
                    "Адрес не найден",
                    modifier = Modifier.padding(top = 24.dp),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }


        if (showDialog.value) {
            AlertDialog(
                onDismissRequest = { showDialog.value = false },
                title = { Text(text = "Информация") },
                text = { Text(text = "Номер личного счета написан бумажном экземпляре квитанции") },
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
