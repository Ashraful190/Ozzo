package com.haxio.ozzo.data.services

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.haxio.ozzo.data.models.UserRegistration
import com.haxio.ozzo.data.models.UserLogin

interface AuthService {
    fun userRegistration(user: UserRegistration): Task<AuthResult>
    fun userLogin(user: UserLogin): Task<AuthResult>
    fun createUser(user: UserRegistration): Task<Void>
}