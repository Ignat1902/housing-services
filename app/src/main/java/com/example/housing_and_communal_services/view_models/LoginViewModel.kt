package com.example.housing_and_communal_services.view_models

import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.housing_and_communal_services.data.models.User
import com.example.housing_and_communal_services.data.repositories.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class LoginViewModel(
    private val repository: AuthRepository = AuthRepository()
) : ViewModel() {
    val hasUser: Boolean
        get() = repository.hasUser()

    var loginUiState by mutableStateOf(LoginUiState())
        private set

    //Установка значения логина для входа
    fun onLoginChange(login: String) {
        loginUiState = loginUiState.copy(login = login)
    }

    //Установка значения пароля для входа
    fun onPasswordNameChange(password: String) {
        loginUiState = loginUiState.copy(password = password)
    }

    //Установка значения логина для регистрации
    fun onLoginChangeSignUp(login: String) {
        loginUiState = loginUiState.copy(loginSignUp = login)
    }

    //Установка значения пароля для регистрации
    fun onPasswordChangeSignUp(password: String) {
        loginUiState = loginUiState.copy(passwordSignUp = password)
    }

    //Установка значения подтвердения пароля для регистрации
    fun onConfirmPasswordChangeSignUp(password: String) {
        loginUiState = loginUiState.copy(confirmPasswordSignUp = password)
    }

    //Установка значения номера телефона для регистрации
    fun onPhoneNumberChange(phoneNumber: String) {
        loginUiState = loginUiState.copy(phoneNumber = phoneNumber)
    }

    //Установка значения имени для регистрации
    fun onNameChange(name: String) {
        loginUiState = loginUiState.copy(name = name)
    }

    //Установка значения фамилии для регистрации
    fun onSurnameChange(surname: String) {
        loginUiState = loginUiState.copy(surname = surname)
    }

    //Установка значения фамилии для регистрации
    fun onLastNameChange(lastName: String) {
        loginUiState = loginUiState.copy(lastName = lastName)
    }

    //Установка значения для номера личного счета
    fun onAccountNumberChange(accountNumber: String) {
        loginUiState = loginUiState.copy(accountNumber = accountNumber)
    }

    //Установка значения адреса для регистрации
    private fun onAddressChange(address: String) {
        loginUiState = loginUiState.copy(address = address)
    }


    private fun validateLoginForm() =
        loginUiState.login.isNotBlank() &&
                loginUiState.password.isNotBlank()

    private fun validateSignUpForm() =
        loginUiState.loginSignUp.isNotBlank() &&
                loginUiState.passwordSignUp.isNotBlank() &&
                loginUiState.confirmPasswordSignUp.isNotBlank() &&
                loginUiState.name.isNotBlank() &&
                loginUiState.surname.isNotBlank() &&
                loginUiState.phoneNumber.isNotBlank()

    fun getAddressByAccountNumber() = viewModelScope.launch {
        loginUiState = loginUiState.copy(isLoading = true)
        val address = repository.getAddressByAccountNumber(loginUiState.accountNumber)
        if (address != null) {
            onAddressChange(address)
            loginUiState = loginUiState.copy(addressError = false)
            loginUiState = loginUiState.copy(isLoading = false)

        } else {
            loginUiState = loginUiState.copy(addressError = true)
            loginUiState = loginUiState.copy(isLoading = false)
        }
    }

    fun createUser(context: Context) = viewModelScope.launch {
        try {
            if (!validateSignUpForm()) {
                throw IllegalArgumentException("Проверьте поля ввода и корректность введеных данных")
            }
            loginUiState = loginUiState.copy(isLoading = true)
            if (loginUiState.passwordSignUp != loginUiState.confirmPasswordSignUp) {
                throw IllegalArgumentException(
                    "Пароли не совпадают"
                )
            }
            loginUiState = loginUiState.copy(signUpError = null)
            repository.createUser(
                loginUiState.loginSignUp,
                loginUiState.passwordSignUp,
                user = User(
                    name = loginUiState.name,
                    surname = loginUiState.surname,
                    lastName = loginUiState.lastName,
                    phoneNumber = "+7" + loginUiState.phoneNumber,
                    address = loginUiState.address,
                    email = loginUiState.loginSignUp
                )
            ) { isSuccessful ->
                loginUiState = if (isSuccessful) {
                    Toast.makeText(context, "Вы успешно зарегистрировались", Toast.LENGTH_LONG)
                        .show()
                    loginUiState.copy(isSuccessLogin = true)
                } else {
                    Toast.makeText(context, "Ошибка регистрации", Toast.LENGTH_LONG).show()
                    loginUiState.copy(isSuccessLogin = false)
                }

            }
        } catch (e: Exception) {
            loginUiState = loginUiState.copy(signUpError = e.localizedMessage)
            e.printStackTrace()
        } finally {
            loginUiState = loginUiState.copy(isLoading = false)
        }
    }

    fun loginUser(context: Context) = viewModelScope.launch {
        try {
            if (!validateLoginForm()) {
                throw IllegalArgumentException("Введите E-mail и пароль")
            }
            loginUiState = loginUiState.copy(isLoading = true)

            loginUiState = loginUiState.copy(loginError = null)
            repository.login(
                loginUiState.login,
                loginUiState.password
            ) { isSuccessful ->
                loginUiState = if (isSuccessful) {
                    Toast.makeText(context, "Вы успешно вошли", Toast.LENGTH_LONG).show()
                    loginUiState.copy(isSuccessLogin = true)
                } else {
                    Toast.makeText(context, "Ошибка входа", Toast.LENGTH_LONG).show()
                    loginUiState.copy(isSuccessLogin = false)
                }

            }
        } catch (e: Exception) {
            loginUiState = loginUiState.copy(loginError = e.localizedMessage)
            e.printStackTrace()
        } finally {
            loginUiState = loginUiState.copy(isLoading = false)
        }
    }

    fun sendVerificationCode(
        auth: FirebaseAuth,
        activity: Activity,
        callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    ) = viewModelScope.launch {
        // on below line generating options for verification code
        auth.setLanguageCode("ru")
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber("+7${loginUiState.phoneNumber}") // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(activity) // Activity (for callback binding)
            .setCallbacks(callbacks) // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    fun signInWithPhoneAuthCredential(
        credential: PhoneAuthCredential,
        auth: FirebaseAuth,
        activity: Activity,
        context: Context,
    ) = viewModelScope.launch {
        // on below line signing with credentials.
        auth.signInWithCredential(credential)
            .addOnCompleteListener(activity) { task ->
                // displaying toast message when
                // verification is successful
                if (task.isSuccessful) {
                    Toast.makeText(context, "Верификация прошла успешно..", Toast.LENGTH_SHORT)
                        .show()
                    loginUiState = loginUiState.copy(isSuccessLogin = true)

                } else {
                    // Sign in failed, display a message
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        // The verification code
                        // entered was invalid
                        Toast.makeText(
                            context,
                            "Ошибка верификации:" + (task.exception as FirebaseAuthInvalidCredentialsException).message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
    }

    //Функция сброса пароля
    private val _toastMessage = MutableLiveData<String>()
    val toastMessage: LiveData<String> get() = _toastMessage

    fun sendPasswordResetEmail(email: String) {
        viewModelScope.launch {
            val result = repository.sendPasswordResetEmail(email)
            if (result.isSuccess) {
                _toastMessage.value = "Отправили письмо на вашу почту"
            } else {
                _toastMessage.value = "$result"
            }
        }
    }
}

data class LoginUiState(
    val phoneNumber: String = "",
    val name: String = "",
    val password: String = "",
    val surname: String = "",
    val lastName: String = "",
    val address: String = "",
    val login: String = "",
    val loginSignUp: String = "",
    val passwordSignUp: String = "",
    val confirmPasswordSignUp: String = "",
    val accountNumber: String = "",
    val isLoading: Boolean = false,
    val isSuccessLogin: Boolean = false,
    val signUpError: String? = null,
    val loginError: String? = null,
    val addressError: Boolean = false
)