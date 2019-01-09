package com.jalinfotec.kankoannai

import android.content.Context
import android.support.v4.content.AsyncTaskLoader
import android.util.Log
import com.github.kittinunf.fuel.*
import com.github.kittinunf.fuel.core.Request
import com.github.kittinunf.fuel.core.Response
import com.github.kittinunf.result.Result

class FuelTaskLoader(context: Context,
                     private val method: FuelManager.Method,
                     private val endpoint: String,
                     private val param: List<Pair<String, Any>>?,
                     private val header: Map<String, Any>?,
                     private val body: String?
): AsyncTaskLoader<String>(context) {

    private var mResult: String? = null // 処理結果
    private var mIsStarted = false // 非同期処理開始フラグ

    // 関連するフラグメント/アクティビティが開始されるときまでに自動的に呼び出される
    override fun onStartLoading() {
        if (mResult != null) deliverResult(mResult) // 結果取得済みの場合は処理結果を返す
        else if (!mIsStarted || takeContentChanged()) forceLoad() // 処理未開始または結果取得に失敗した場合は非同期処理を開始する
    }

    // 以前にロードされたデータセットを無視し、新しいデータセットをロード
    override fun onForceLoad() {
        super.onForceLoad()
        mIsStarted = true
    }

    // 非同期処理の開始
    override fun loadInBackground(): String? = FuelManager().syncRequest(method, endpoint, param, header, body)

    // ロードの結果を登録済みリスナーに送信
    override fun deliverResult(data: String?) {
        mResult = data // 処理結果を格納
        super.deliverResult(data)
    }
    class FuelManager {

        enum class Method { GET, POST, PUT, PATCH, DELETE }
        private val to = 3000 // タイムアウト
        private val logTag = "fuel"

        // 同期通信
        fun syncRequest(method: Method,
                        endpoint: String,
                        param: List<Pair<String, Any>>?,
                        header: Map<String, Any>?,
                        body: String?
        ): String? {
            //　リクエスト作成
            val request = createRequest(method, endpoint, param, header, body)
            // リクエスト送信
            val tri = request.timeout(to).timeoutRead(to).responseString()
            return when (tri.third) {
                is Result.Success -> {
                    // デバッグビルドの場合はログ出力
                    if (BuildConfig.DEBUG) writeLog(tri.first, tri.second)
                    tri.third.get()
                }
                is Result.Failure -> {
                    // デバッグビルドの場合はエラーログ出力
                    if (BuildConfig.DEBUG) writeErrorLog(tri.first, tri.second)
                    // エラーの場合はnullを返す
                    null
                }
            }
        }
        /*
        // 非同期通信
        fun asyncRequest(method: Method,
                         endpoint: String,
                         param: List<Pair<String, Any>>?,
                         header: Map<String, Any>?,
                         body: String?
        ) {
            //　リクエスト作成
            val request = createRequest(method, endpoint, param, header, body)
            // リクエスト送信
            request.timeout(to).timeoutRead(to).response { req, resp, result ->
                when (result) {
                    is Result.Success -> if (BuildConfig.DEBUG) writeLog(req, resp) // デバッグビルドの場合はログ出力
                    is Result.Failure -> if (BuildConfig.DEBUG) writeErrorLog(req, resp) // デバッグビルドの場合はエラーログ出力
                }
            }
        }
        */
        // リクエスト作成
        private fun createRequest(method: Method,
                                  endpoint: String,
                                  param: List<Pair<String, Any>>?,
                                  header: Map<String, Any>?,
                                  body: String?
        ): Request {
            // パラメータを設定
            var url = endpoint
            if (param != null) {
                var isInit = true
                param.forEach { p ->
                    if (isInit) {
                        url += "?${p.first}=${p.second}"
                        isInit = false
                    } else url += "&${p.first}=${p.second}"
                }
            }
            // リクエスト作成
            val request : Request = when (method) {
                Method.GET -> endpoint.httpGet()
                Method.POST -> endpoint.httpPost()
                Method.PUT -> endpoint.httpPut()
                Method.PATCH -> endpoint.httpPatch()    // patchは未実装 http post x-http-method-overrideでpatchが設定される
                Method.DELETE -> endpoint.httpDelete()
            }
            // ヘッダーを設定
            request.header(header)
            // ボディーを設定
            if (body != null) request.body(body)
            return request
        }

        /**
         * ログ書き込み
         */
        private fun writeLog(req: Request, resp: Response) {
            Log.i(logTag, "エンドポイント:${req.url}")
            Log.i(logTag, "リクエストヘッダ:${req.headers}")
            Log.i(logTag, "レスポンスコード:${resp.statusCode}")
            Log.i(logTag, "レスポンスメッセージ:${resp.responseMessage}")
            Log.i(logTag, "レスポンスデータ:${resp.data}")
        }
        private fun writeErrorLog(req: Request, resp: Response) {
            Log.e(logTag, "エンドポイント:${req.url}")
            Log.e(logTag, "リクエストヘッダ:${req.headers}")
            Log.e(logTag, "レスポンスコード:${resp.statusCode}")
            Log.e(logTag, "レスポンスメッセージ:${resp.responseMessage}")
            Log.e(logTag, "レスポンスデータ:${resp.data}")
        }
    }
}