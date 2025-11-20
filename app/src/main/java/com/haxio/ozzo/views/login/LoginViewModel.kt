package com.haxio.ozzo.views.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.haxio.ozzo.core.DataState
import com.haxio.ozzo.data.models.UserLogin
import com.haxio.ozzo.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _loginResponse = MutableLiveData<DataState<String>>()
    val loginResponse: LiveData<DataState<String>> = _loginResponse

    fun userLogin(email: String, password: String) {
        _loginResponse.postValue(DataState.Loading())

        val userLogin = UserLogin(email, password)

        authRepository.userLogin(userLogin)
            .addOnSuccessListener { authResult ->
                val userId = authResult.user?.uid ?: ""
                _loginResponse.postValue(DataState.Success(userId))
                Log.d("LoginViewModel", "Login Success - UserID: $userId")
            }
            .addOnFailureListener { error ->
                _loginResponse.postValue(DataState.Error("${error.message}"))
                Log.d("LoginViewModel", "Login error: ${error.message}")
            }
    }
}