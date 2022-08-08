package tw.hardy.tdxsample.data

import okhttp3.OkHttpClient
import okhttp3.Protocol
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.concurrent.TimeUnit

interface ApiService {

    /**
     * 取得TDX Token
     * @param grantType 固定使用"client_credentials"
     * @param clientId  your client id
     * @param clientSecret your client secret
     */
    @FormUrlEncoded
    @Headers("Content-type: application/x-www-form-urlencoded")
    @POST("auth/realms/TDXConnect/protocol/openid-connect/token")
    suspend fun getTDXToken(
        @Field("grant_type") grantType: String,
        @Field("client_id") clientId: String,
        @Field("client_secret") clientSecret: String
    ): GetTokenResponse

    /**
     * 取得指定縣市的市區公車營運業者資料 此處查詢臺中
     * @param headers 帶入token format
     */
    @GET("api/basic/v2/Bus/Operator/City/Taichung?%24format=JSON")
    suspend fun getBusOperator(
        @HeaderMap headers: Map<String, String>
    ): List<GetBusOperatorResponse>

    companion object {
        // TDX URL
        private const val TDX_URL = "https://tdx.transportdata.tw/"

        private val retrofitClient: Retrofit.Builder by lazy {

            val okhttpClient = OkHttpClient().newBuilder()
                .protocols(mutableListOf(Protocol.HTTP_1_1))
                .connectTimeout(90, TimeUnit.SECONDS)
                .readTimeout(90, TimeUnit.SECONDS)
                .build()

            Retrofit.Builder()
                .baseUrl(TDX_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okhttpClient)
        }

        val apiInterface: ApiService by lazy {
            retrofitClient.build().create(ApiService::class.java)
        }
    }
}