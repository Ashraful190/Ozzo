package com.haxio.ozzo.views.register

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.haxio.ozzo.core.DataState
import com.haxio.ozzo.data.models.UserRegistration
import com.haxio.ozzo.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _registrationResponse = MutableLiveData<DataState<UserRegistration>>()
    val registrationResponse: LiveData<DataState<UserRegistration>> = _registrationResponse

    fun userRegistration(user: UserRegistration) {
        _registrationResponse.postValue(DataState.Loading())

        authRepository.userRegistration(user)
            .addOnSuccessListener { authResult ->
                val userId = authResult.user?.uid ?: ""
                val userWithId = user.copy(userId=userId)

                authRepository.createUser(userWithId)
                    .addOnSuccessListener {
                        _registrationResponse.postValue(DataState.Success(userWithId))
                        Log.d("RegistrationViewModel", "User created in Firestore")
                    }
                    .addOnFailureListener { error ->
                        _registrationResponse.postValue(DataState.Error("${error.message}"))
                        Log.d("RegistrationViewModel", "Firestore error: ${error.message}")
                    }
            }
            .addOnFailureListener { error ->
                _registrationResponse.postValue(DataState.Error("${error.message}"))
                Log.d("RegistrationViewModel", "Auth error: ${error.message}")
            }
    }
}