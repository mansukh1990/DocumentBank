package com.example.documentbank.ThreadYoutube.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.documentbank.ThreadYoutube.model.ThreadModel
import com.example.documentbank.ThreadYoutube.model.UserModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class UserViewModel : ViewModel() {

    private val db = FirebaseDatabase.getInstance()
    val threadRef = db.getReference("threads")
    val userRef = db.getReference("users")

    private val _threads = MutableLiveData(listOf<ThreadModel>())
    val threads: LiveData<List<ThreadModel>> get() = _threads

    private val _users = MutableLiveData(UserModel())
    val users: LiveData<UserModel> get() = _users

    fun fetchUser(uid: String) {
        userRef.child(uid).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.getValue(UserModel::class.java)
                _users.postValue(user)

            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    fun fetchThreads(uid: String) {
        threadRef.orderByChild("userId").equalTo(uid)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val threadList = snapshot.children.mapNotNull {
                        it.getValue(ThreadModel::class.java)
                    }
                    _threads.postValue(threadList)

                }

                override fun onCancelled(error: DatabaseError) {

                }

            })
    }

    val firestoreDb = Firebase.firestore

    private val _followerList = MutableLiveData(listOf<String>())
    val followerList: LiveData<List<String>> get() = _followerList

    private val _followingList = MutableLiveData(listOf<String>())
    val followingList: LiveData<List<String>> get() = _followingList

    fun followUsers(userId: String, currentUserId: String) {

        if (currentUserId.isNotEmpty() && userId.isNotEmpty()) {

            val ref = firestoreDb.collection("following").document(currentUserId)
            val followerRef = firestoreDb.collection("followers").document(userId)

            ref.update("followerIds", FieldValue.arrayUnion(userId))
            followerRef.update("followingIds", FieldValue.arrayUnion(currentUserId))
        } else {
            Log.e("FirestoreError", "User IDs cannot be null or empty")
        }
    }

    fun getFollowers(userId: String) {

        if (userId.isEmpty()) {
            Log.e("FirestoreError", "User ID is null or empty")
            return
        }

        firestoreDb.collection("followers").document(userId)
            .addSnapshotListener { value, error ->

                val followerIds = value?.get("followerIds") as? List<String> ?: listOf()
                _followerList.value = followerIds
            }
    }

    fun getFollowing(userId: String) {

        firestoreDb.collection("following").document(userId)
            .addSnapshotListener { value, error ->

                val followingIds = value?.get("followingIds") as? List<String> ?: listOf()
                _followingList.value = followingIds
            }
    }


}