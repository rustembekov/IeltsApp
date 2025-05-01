package com.example.support.core.data


import com.example.support.core.util.ResultCore
import com.example.support.core.domain.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val database: DatabaseReference
) {

    ///Firebase Auth
    fun register(
        email: String,
        password: String,
        username: String,
        callback: (Boolean, String) -> Unit
    ) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val userId = task.result?.user?.uid ?: return@addOnCompleteListener

                    val user = User(userId, email, username, password, 0)
                    database.child("users").child(userId).setValue(user)
                        .addOnSuccessListener {
                            callback(true, "Пользователь зарегистрирован")
                        }
                        .addOnFailureListener {
                            callback(false, "Ошибка сохранения: ${it.message}")
                        }
                } else {
                    callback(false, "Ошибка регистрации: ${task.exception?.message}")
                }
            }
    }

    fun login(email: String, password: String, callback: (Boolean, String) -> Unit) {
        firebaseAuth
            .signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val userId = firebaseAuth.currentUser?.uid ?: return@addOnCompleteListener

                    database.child("users").child(userId).get()
                        .addOnSuccessListener { snapshot ->

                            if (snapshot.exists() && snapshot.value != null) {
                                val storedPassword =
                                    snapshot.child("password").getValue(String::class.java)

                                if (storedPassword == password) {
                                    callback(true, "Вход успешен!")
                                } else {
                                    callback(false, "Неверный пароль!")
                                }
                            } else {
                                callback(false, "Пользователь не найден в базе данных!")
                            }
                        }
                        .addOnFailureListener {
                            callback(false, "Ошибка базы данных!")
                        }
                } else {
                    callback(false, "Ошибка входа!")
                }
            }
    }

    fun getCurrentUser(callback: (ResultCore<User>) -> Unit) {
        val userId = firebaseAuth.currentUser?.uid
        if (userId == null) {
            callback(ResultCore.Failure("User not authenticated"))
            return
        }

        database.child("users").child(userId).get()
            .addOnSuccessListener { snapshot ->
                val user = snapshot.getValue(User::class.java)
                if (user != null) {
                    callback(ResultCore.Success(user))
                } else {
                    callback(ResultCore.Failure("User not found"))
                }
            }
            .addOnFailureListener {
                callback(ResultCore.Failure(it.localizedMessage ?: "Unknown error"))
            }

    }

    fun getUserId(): String? {
        return firebaseAuth.currentUser?.uid
    }


    fun logout() {
        firebaseAuth.signOut()
    }

}
