package com.example.documentbank.DocumentBank.data.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class ApiResp<T>(
    @SerializedName("data")
    val data: T? = null,
    @SerializedName("response")
    var response: String = "",
    @SerializedName("responseCode")
    var responseCode: Int? = null,
    @SerializedName("options")
    val options: Options? = null,
    @SerializedName("paginate")
    val paginate: Paginate? = null,
    @SerializedName("lead_code")
    var leadCode: String? = "",
    @SerializedName("action_name")
    var actionName: String? = ""
)

@Keep
data class Options(
    @SerializedName("validation")
    val validation: List<String>? = null,
    @SerializedName("original_location_name_before_change")
    val originalLocationNameBeforeChange: String? = null
)

@Keep
data class Paginate(
    @SerializedName("per_page")
    val perPage: String = "",
    @SerializedName("first_page_url")
    val firstPageUrl: String = "",
    @SerializedName("total")
    val total: String = "",
    @SerializedName("last_page")
    val lastPage: String = "",
    @SerializedName("last_page_url")
    val lastPageUrl: String = "",
    @SerializedName("next_page_url")
    val nextPageUrl: String = "",
    @SerializedName("prev_page_url")
    val prevPageUrl: String = "",
    @SerializedName("current_page")
    val currentPage: String = ""
)

