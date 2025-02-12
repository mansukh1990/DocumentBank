package com.example.documentbank.common

object ApiParams {
    const val AUTHORIZATION = "Authorization"
    const val LEAD_CODE = "lead_code"
    const val FILTER_STATUS = "filter_status"
    const val FROM_DATE = "from_date"
    const val TO_DATE = "to_date"
    const val EXPENSE_TYPE = "expense_type"

    const val TYPE = "type"
    const val REQUEST_AMOUNT = "request_amount"
    const val BALANCE_AMOUNT = "balance_amount"
    const val TRIP_TYPE = "trip_type"
    const val LOAD_ID = "load_id"
    const val OPERATION_REMARK = "operation_remark"
    const val OPERATION_PROOF = "operation_proof"

    const val ID = "id"
    const val IMAGE_NAME = "image_name"
    const val REMARKS = "remark"
    const val LEAD_TYPE = "lead_type"
    const val FUEL_ID = "fuel_id"
    const val DRIVER_TRANSPORTER_ID = "driver_transporter_id"

    // Assign Vehicle And Driver
    const val VEHICLE_ID = "vehicle_id"
    const val DRIVER_NAME = "driver_name"
    const val DRIVER_OTP = "driver_otp"
    const val IS_VEHICLE_PER_KM_PRICE_UPDATE = "is_vehicle_per_km_price_update"
    const val PER_KM_RUNNING_COST = "per_km_running_cost"
    const val PER_KM_EMPTY_TRIP_COST = "per_km_empty_trip_cost"
    const val PER_KM_AMBIENT_COST = "per_km_ambient_cost"
    const val IS_CHANGE_DRIVER_MOBILE = "is_change_driver_mobile"
    const val DRIVER_MOBILE_NUMBER = "driver_mobile_number"

    // Load Filters
    const val SEARCH = "search"
    const val SEARCH_FILTER_LEAD_CODE = "search_filter[lead_code]"
    const val SEARCH_FILTER_USER_ID = "search_filter[user_id]"
    const val SEARCH_FILTER_TRANSPORTER_ID = "search_filter[transporter_id]"
    const val SEARCH_FILTER_FROM_LOC = "search_filter[from_loc]"
    const val SEARCH_FILTER_TO_LOC = "search_filter[to_loc]"
    const val SEARCH_FILTER_STATUS = "search_filter[status]"
    const val SEARCH_FILTER_OPERATION = "search_filter[operation]"
    const val SEARCH_FILTER_CONTAINER_SIZE_L = "search_filter[container_size_l]"
    const val SEARCH_FILTER_DRIVER_ID = "search_filter[driver_id]"
    const val SEARCH_FILTER_TRANSPORTER_VEHICLE_ID = "search_filter[transporter_vehicle_id]"
    const val SEARCH_FILTER_LOAD_START_DATE = "search_filter[load_start_date]"
    const val SEARCH_FILTER_LOAD_END_DATE = "search_filter[load_end_date]"
    const val SEARCH_FILTER_FROM_DATE = "search_filter[from_date]"
    const val SEARCH_FILTER_TO_DATE = "search_filter[to_date]"
    const val SEARCH_FILTER_LOAD_END_DATE_FROM = "search_filter[load_end_date_from]"
    const val SEARCH_FILTER_LOAD_END_DATE_TO = "search_filter[load_end_date_to]"

    const val SEARCH_FILTER_GOODS_TYPE = "search_filter[goods_type]"
    const val SEARCH_FILTER_TRIP_LEAD_CODE = "search_filter[trip_lead_code]"

    const val IS_CONFIRM_ADDRESS_START_TRIP = "is_confirm_address_start_trip"
    const val IS_CONF_ADDR = "is_conf_addr"
    const val TRANSPORTER_ID = "transporter_id"

    const val IS_PICK_UP = "is_pickup"
    const val IS_MAIN_LOCATION = "is_main_location"
    const val LEAD_PICK_UP_DROP_ID = "lead_pickup_drop_id"
    const val LEAD_REACHED_DROP_PICKUP_ID = "load_reached_drop_pickup_id"
    const val TEMPERATURE_READING = "temperature_reading"
    const val TEMPERATURE_IMAGE = "temperature_image"

    // Empty Trip
    const val EMPTY_LEAD_ID = "empty_lead_id"
    const val START_KM_IMAGE = "start_kilometer_image"
    const val START_KM_READING = "start_kilometer_reading"
    const val END_KM_IMAGE = "end_kilometer_image"
    const val END_KM_READING = "end_kilometer_reading"
    const val ASSIGN_DRIVER_EMPTY_LEAD = "assign_driver_empty_lead"
    const val STATUS = "status"

    //

    const val ADD_UPDATE_DELETE_LOC_TRANSACTION_FLAG = "transaction_flag"
    const val LOCATION_NAME = "location_name"

    // Vehicle Edit
    const val RC_BOOK_FRONT_FILE = "rc_book_front"
    const val RC_BOOK_BACK_FILE = "rc_book_back"
    const val INSURANCE_FILE = "insurance"
    const val NATIONAL_PERMIT_FILE = "national_permit"
    const val FITNESS_CERTIFICATE_FILE = "fitness_certificate"
    const val ROAD_TAX_FILE = "road_tax_file"

    const val ROAD_TAX_EXPIRE_DATE = "road_tax_expire_date"
    const val FITNESS_CERTIFICATE_EXPIRE_DATE: String = "fitness_certificate_expire_date"
    const val RC_EXPIRY_DATE: String = "rc_expire_date"
    const val INSURANCE_EXP_DATE: String = "insurance_expire_date"
    const val INSURANCE_NUMBER: String = "insurance_no"
    const val PERMIT_EXP_DATE: String = "national_permit_expire_date"
    const val PERMIT_NUMBER: String = "national_permit_no"

    const val SCREEN_TYPE: String = "screen_type"
    const val REGISTRATION_NUM: String = "registration_num"
    const val VEHICLE_MODEL: String = "vehicle_model"
    const val LOADING_CAPECITY: String = "loading_capecity"
    const val REFER_UNIT: String = "refer_unit"
    const val CONTAINER_SIZE_L: String = "container_size_l"
    const val CONTAINER_SIZE_W: String = "container_size_b"
    const val CONTAINER_SIZE_H: String = "container_size_h"
    const val TEMP_MIN: String = "temp_min"
    const val TEMP_MAX: String = "temp_max"
    const val TRACKING_DEVICE_COMPANY: String = "tracking_device_company"
    const val TRACKING_DEVICE_COMPANY_OTHER: String = "tracking_device_company_other"
    const val IS_GEO_TRACK_DEVICE: String = "checkbox_geo"
    const val GEO_TRACK_VEHICLE_NO: String = "geo_track_vehicle_no"
    const val GEO_TRACK_USER_KEY: String = "geo_track_user_key"

    // Document Bank
    const val MODEL: String = "model"
    const val MODEL_ID: String = "model_id"
    const val LINKED_INPUT_TYPE_ID: String = "linked_input_type_id"
    const val DOCUMENT_BANK_ID: String = "document_bank_id"
    const val COLLECTION: String = "collection"
    const val FILE_TYPE:String ="file_type"
    const val MEDIA_FILE = "media_file"
    const val LINKED_INPUT_TYPE: String = "linked_input_type"
    const val LINKED_INPUT_VALUE: String = "linked_input_value"
    const val LINKED_EXTRA_VALUE: String = "linked_extra_value"

}