package tw.hardy.tdxsample.data

import com.google.gson.annotations.SerializedName

/**
 * tdx Token response
 */
data class GetTokenResponse(
    val access_token: String?, // token
    val expires_in: Int?, // token 有效期限 單位為秒數  基本為86400(一天)
    @SerializedName("not-before-policy")
    val not_before_policy: Int?,
    val refresh_expires_in: Int?,
    val scope: String?,
    val token_type: String? // token類型，基本上固定為"Bearer"
)

/**
 * 市區公車營運業者資料 response
 */
data class GetBusOperatorResponse(
    val AuthorityCode: String?,
    val OperatorCode: String?,
    val OperatorEmail: String?,
    val OperatorID: String?,
    val OperatorName: OperatorName?,
    val OperatorNo: String?,
    val OperatorPhone: String?,
    val OperatorUrl: String?,
    val ProviderID: String?,
    val ReservationPhone: String?,
    val ReservationUrl: String?,
    val UpdateTime: String?
)

data class OperatorName(
    val En: String?,
    val Zh_tw: String?
)