package com.example.housing_and_communal_services.screens.authorization

import android.app.Activity
import android.text.TextUtils
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.housing_and_communal_services.R
import com.example.housing_and_communal_services.repository.AuthRepository
import com.example.housing_and_communal_services.repository.FirebaseRequest
import com.example.housing_and_communal_services.showBars
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider

@Composable
fun RegistrationTelephonePage(
    loginViewModel: LoginViewModel? = null,
    onHomePage: () -> Unit,
    onRegistrationPage: () -> Unit
) {

    val loginUiState = loginViewModel?.loginUiState
    val request = FirebaseRequest()
    val authRepository = AuthRepository()
    val isErrorTel = remember { mutableStateOf(false) }
    val isError = remember { mutableStateOf(false) }
    val context = LocalContext.current
    showBars(flag = true)

    // on below line creating variable for course name,
    // course duration and course description.

    val otp = remember {
        mutableStateOf("")
    }

    val verificationID = remember {
        mutableStateOf("")
    }

    // on below line creating variable
    // for firebase auth and callback
    val mAuth: FirebaseAuth = FirebaseAuth.getInstance();
    lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp),
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 32.dp)
    ) {
        Text(
            stringResource(R.string.registration_name),
            modifier = Modifier.padding(top = 24.dp),
            style = MaterialTheme.typography.displayMedium,
            color = MaterialTheme.colorScheme.onBackground
        )

        Text(
            "Введите номер телефона, на который будет отправлен код подтверждения",
            modifier = Modifier.padding(top = 24.dp),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground
        )


        OutlinedTextField(
            value = loginUiState?.phoneNumber ?: "",
            onValueChange = { newValue ->
                // Очищаем значение от символов, которые нельзя вводить
                val filteredText = newValue.filter { it.isDigit() }
                if (filteredText.length <= 10) {
                    // Если символов не больше 10, то обновляем поле ввода
                    loginViewModel?.onPhoneNumberChange(filteredText)
                }
            },
            isError = isErrorTel.value,
            label = { Text(text = "Номер телефона") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            leadingIcon = { Text(text = "+7") },
            // Определяем класс визуального форматирования
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
        )

        Button(
            onClick = {
                if (TextUtils.isEmpty(loginUiState?.phoneNumber) || (loginUiState?.phoneNumber?.length != 10)) {
                    Toast.makeText(context, "Введите номер телефона", Toast.LENGTH_SHORT).show()
                    isErrorTel.value = true
                } else {
                    isErrorTel.value = false
                    // on below line calling method to generate verification code.
                    loginViewModel.sendVerificationCode(mAuth, context as Activity, callbacks)
                }

            },
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(top = 24.dp),
            shape = RoundedCornerShape(4.dp),

            ) {
            Text(
                text = "Отправить код",
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding()
            )
        }


        TextField(
            value = otp.value,
            isError = isError.value,
            onValueChange = {
                otp.value = it
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
        )


        Button(
            onClick = {
                if (TextUtils.isEmpty(otp.value)) {
                    // displaying toast message on below line.
                    Toast.makeText(context, "Введите код подтверждения", Toast.LENGTH_SHORT).show()
                    isError.value = true
                } else {
                    isError.value = false
                    // on below line generating phone credentials.
                    val credential: PhoneAuthCredential =
                        PhoneAuthProvider.getCredential(verificationID.value, otp.value)
                    // on below line signing within credentials.
                    loginViewModel?.signInWithPhoneAuthCredential(
                        credential,
                        mAuth,
                        context as Activity,
                        context
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(top = 24.dp),
            shape = RoundedCornerShape(4.dp),

            ) {
            Text(
                text = "Подтвердить",
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding()
            )
        }

        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(p0: PhoneAuthCredential) {
                // on below line updating message
                // and displaying toast message
                Toast.makeText(context, "Верификация прошла успешно", Toast.LENGTH_SHORT).show()

            }

            override fun onVerificationFailed(p0: FirebaseException) {
                // on below line displaying error as toast message.
                Toast.makeText(
                    context,
                    "Скорее всего некорректно введен номер телефона",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onCodeSent(
                verificationId: String,
                p1: PhoneAuthProvider.ForceResendingToken
            ) {
                // this method is called when code is send
                super.onCodeSent(verificationId, p1)
                verificationID.value = verificationId
            }
        }
        if (loginUiState?.isLoading == true) {
            CircularProgressIndicator()
        }

        LaunchedEffect(key1 = loginViewModel?.hasUser) {
            if (loginViewModel?.hasUser == true) {
                request.isDocumentExists("User", authRepository.getUserId()) { exists ->
                    if (exists) {
                        onHomePage.invoke()
                    } else {
                        onRegistrationPage.invoke()
                    }
                }
            }
        }
    }
}
