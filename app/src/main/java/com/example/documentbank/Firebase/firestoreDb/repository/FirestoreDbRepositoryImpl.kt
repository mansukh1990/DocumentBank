package com.example.documentbank.Firebase.firestoreDb.repository

import com.example.documentbank.Firebase.firestoreDb.FirestoreModelResponse
import com.example.documentbank.utils.Resource
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class FirestoreDbRepositoryImpl @Inject constructor(
    private val db: FirebaseFirestore
) : FirestoreRepository {

    override fun insert(item: FirestoreModelResponse.FirestoreItem): Flow<Resource<String>> =
        callbackFlow {
            trySend(Resource.Loading())
            db.collection("user")
                .add(item)
                .addOnSuccessListener {
                    trySend(Resource.Success(data = "Data Inserted Successfully..${it.id}"))

                }.addOnFailureListener {
                    trySend(
                        Resource.DataError(
                            message = it.toString(),
                            data = "Data is not Inserting"
                        )
                    )
                }
            awaitClose {
                close()
            }

        }

    override fun getItems(): Flow<Resource<List<FirestoreModelResponse>>> = callbackFlow {
        trySend(Resource.Loading())
        db.collection("user")
            .get()
            .addOnSuccessListener {
                val items = it.map { data ->
                    FirestoreModelResponse(
                        item = FirestoreModelResponse.FirestoreItem(
                            title = data["title"] as String?,
                            description = data["description"] as String?
                        ),
                        key = data.id
                    )
                }
                trySend(Resource.Success(items))
            }.addOnFailureListener {
                trySend(Resource.DataError(message = it.toString()))
            }
        awaitClose {
            close()
        }

    }


    override fun delete(key: String): Flow<Resource<String>> = callbackFlow {
        trySend(Resource.Loading())
        db.collection("user")
            .document(key)
            .delete()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    trySend(Resource.Success("Data Deleted Successfully.."))
                }
            }.addOnFailureListener {
                trySend(Resource.DataError(message = it.toString()))
            }
        awaitClose {
            close()
        }

    }

    override fun update(item: FirestoreModelResponse): Flow<Resource<String>> = callbackFlow {
        trySend(Resource.Loading())
        val map = HashMap<String, Any>()
        map["title"] = item.item?.title!!
        map["description"] = item.item.description!!

        db.collection("user")
            .document(item.key!!)
            .update(map)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    trySend(Resource.Success("Data Updated Successfully.."))
                }
            }.addOnFailureListener {
                trySend(Resource.DataError(message = it.toString()))
            }
        awaitClose {
            close()
        }

    }
}