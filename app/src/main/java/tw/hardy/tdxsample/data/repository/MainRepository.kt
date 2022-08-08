package tw.hardy.tdxsample.data.repository

import tw.hardy.tdxsample.data.ApiCall
import tw.hardy.tdxsample.data.ApiService

class MainRepository : ApiCall {

    companion object {
        const val CLIENT_CREDENTIALS = "client_credentials" //grant_type 固定使用"client_credentials"
        const val CLIENT_ID = "FFFFFFFF-FFFFFFFF-FFFF-FFFF" // your client id
        const val CLIENT_SECRET = "FFFFFFFF-FFFF-FFFF-FFFF-FFFFFFFFFFFF" // your client secret
    }

    /**
     * TDX API Header
     * token format:token_type + 一隔空格 + access_token
     * token 基本上為 Bearer
     */
    private fun getAuthorizationHeader(token: String): Map<String, String> =
        mapOf("Authorization" to token)


    /**
     * 取得TDX Token
     */
    suspend fun getToken() = safeApiCall {
        ApiService.apiInterface.getTDXToken(CLIENT_CREDENTIALS, CLIENT_ID, CLIENT_SECRET)
    }

    /**
     * 取得指定縣市的市區公車營運業者資料
     */
    suspend fun getBusOperator(token: String) = safeApiCall {
        ApiService.apiInterface.getBusOperator(getAuthorizationHeader(token))
    }

}