package com.example.documentbank.Firebase.firebaseRealtimeDb.repository

import com.example.documentbank.Firebase.firebaseRealtimeDb.RealtimeModelResponse
import com.example.documentbank.utils.Resource
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class RealtimeDbRepositoryImpl @Inject constructor(
    private val db: DatabaseReference
) : RealtimeRepository {

    override fun insert(item: RealtimeModelResponse.RealtimeItems): Flow<Resource<String>> =
        callbackFlow {
            trySend(Resource.Loading())
            db.push().setValue(
                item
            ).addOnCompleteListener {
                if (it.isSuccessful)
                    trySend(Resource.Success("Data Inserted Successfully.."))

            }.addOnFailureListener {
                trySend(Resource.DataError(it.toString()))

            }
            awaitClose {
                close()
            }
        }

    override fun getItems(): Flow<Resource<List<RealtimeModelResponse>>> = callbackFlow {
        trySend(Resource.Loading())

        val valueEvent = object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                val items = snapshot.children.map {
                    RealtimeModelResponse(
                        items = it.getValue(RealtimeModelResponse.RealtimeItems::class.java),
                        key = it.key
                    )
                }
                trySend(Resource.Success(items))
            }

            override fun onCancelled(error: DatabaseError) {
                trySend(Resource.DataError(error.toString()))
            }
        }
        db.addValueEventListener(valueEvent)
        awaitClose {
            db.removeEventListener(valueEvent)
            close()
        }
    }

    override fun delete(key: String): Flow<Resource<String>> = callbackFlow {
        trySend(Resource.Loading())
        db.child(key).removeValue()
            .addOnCompleteListener {
                trySend(Resource.Success("Data Deleted Successfully.."))

            }.addOnFailureListener {
                trySend(Resource.DataError(it.toString()))
            }
        awaitClose {
            close()
        }
    }

    override fun update(res: RealtimeModelResponse): Flow<Resource<String>> = callbackFlow {
        trySend(Resource.Loading())
        val map = HashMap<String, Any>()
        map["title"] = res.items?.title!!
        map["description"] = res.items.description!!
        db.child(res.key!!).updateChildren(map)
            .addOnCompleteListener {
                trySend(Resource.Success("Data Updated Successfully.."))
            }.addOnFailureListener {
                trySend(Resource.DataError(it.toString()))

            }
        awaitClose {
            close()
        }


    }
}