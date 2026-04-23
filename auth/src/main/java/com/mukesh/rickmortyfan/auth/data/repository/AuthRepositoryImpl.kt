package com.mukesh.rickmortyfan.auth.data.repository

import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.mukesh.common.Resource
import com.mukesh.rickmortyfan.auth.data.dto.toRickMortyUser
import com.mukesh.rickmortyfan.auth.domain.modal.RickMortyUser
import com.mukesh.rickmortyfan.auth.domain.repository.AuthRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class AuthRepositoryImpl
    @Inject
    constructor(
        private val firebaseAuth: FirebaseAuth,
    ) : AuthRepository {
        override fun login(
            email: String,
            password: String,
        ): Flow<Resource<RickMortyUser>> =
            callbackFlow {
                trySend(Resource.Loading())
                firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnSuccessListener {
                        it.user?.let { firebaseUser ->
                            trySend(Resource.Success(firebaseUser.toRickMortyUser()))
                        } ?: trySend(Resource.Error("User is null"))
                        close()
                    }
                    .addOnFailureListener {
                        trySend(Resource.Error(it.message ?: "An unknown error occurred"))
                        close()
                    }
                awaitClose()
            }

        override fun signUp(
            email: String,
            password: String,
        ): Flow<Resource<RickMortyUser>> =
            callbackFlow {
                trySend(Resource.Loading())
                firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnSuccessListener {
                        it.user?.let { firebaseUser ->
                            trySend(Resource.Success(firebaseUser.toRickMortyUser()))
                        } ?: trySend(Resource.Error("User is null"))
                        close()
                    }
                    .addOnFailureListener {
                        trySend(Resource.Error(it.message ?: "An unknown error occurred"))
                        close()
                    }
                awaitClose()
            }

        override suspend fun logout() {
            firebaseAuth.signOut()
        }

        override suspend fun getLoggedInUser(): Flow<RickMortyUser?> =
            callbackFlow {
                val authStateListener =
                    FirebaseAuth.AuthStateListener { auth ->
                        trySend(auth.currentUser?.toRickMortyUser())
                    }
                Firebase.auth.addAuthStateListener(authStateListener)
                awaitClose {
                    Firebase.auth.removeAuthStateListener(authStateListener)
                }
            }
    }
