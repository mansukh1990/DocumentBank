package com.example.documentbank.ThreadYoutube.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.documentbank.ThreadYoutube.model.ThreadModel
import com.example.documentbank.ThreadYoutube.model.UserModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class SearchViewModel : ViewModel() {

    private val db = FirebaseDatabase.getInstance()
    private val users = db.getReference("users")

    private var _users = MutableLiveData<List<UserModel>>()
    var userList: LiveData<List<UserModel>> = _users


    init {
        fetchUsers {
            _users.value = it
        }
    }

    private fun fetchUsers(
        onResult: (List<UserModel>) -> Unit
    ) {
        users.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                val result = mutableListOf<UserModel>()
                for (userSnapshot in snapshot.children) {
                    val user = userSnapshot.getValue(UserModel::class.java)
                    result.add(user!!)
                }
                onResult(result)
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })
    }

    fun fetchUserFromThread(
        thread: ThreadModel,
        onResult: (UserModel) -> Unit
    ) {
        db.getReference("users").child(thread.userId)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {

                    val user = snapshot.getValue(UserModel::class.java)
                    user?.let(onResult)
                }

                override fun onCancelled(error: DatabaseError) {
                }

            })
    }

}