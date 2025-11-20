package com.haxio.ozzo.data.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.haxio.ozzo.data.models.UserRegistration
import com.haxio.ozzo.data.models.UserLogin
import com.haxio.ozzo.data.services.AuthService
import com.haxio.ozzo.core.Nodes
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val jAuth: FirebaseAuth,
    private val db: FirebaseFirestore
) : AuthService {

    override fun userRegistration(user: UserRegistration): Task<AuthResult> {
        return jAuth.createUserWithEmailAndPassword(user.email, user.password)
    }

    override fun userLogin(user: UserLogin): Task<AuthResult> {
        return jAuth.signInWithEmailAndPassword(user.email, user.password)
    }

    override fun createUser(user: UserRegistration): Task<Void> {
        return db.collection(Nodes.USER).document(user.userId).set(user)
    }
}