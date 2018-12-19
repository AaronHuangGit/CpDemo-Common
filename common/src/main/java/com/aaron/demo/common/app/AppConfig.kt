package com.aaron.demo.common.app

/**
 * Created on 2018/4/3.
 * @author HuangRan
 * @version
 */
object AppConfig {
    var appFlag: String? = null
    var appVersionCode: Int? = null
    var appVersionName: String? = null
    var isTestMode: Boolean? = false
    var hostUrl: String? = null

    fun init(appConfigData: AppConfigData) {
        appFlag = appConfigData.mAppFlag
        appVersionCode = appConfigData.mAppVersionCode
        appVersionName = appConfigData.mAppVersionName
        isTestMode = appConfigData.mIsTestMode
        hostUrl = appConfigData.mHostUrl
    }

    data class AppConfigData(var mAppFlag: String?, var mAppVersionCode: Int?,
                             var mAppVersionName: String?, var mIsTestMode: Boolean?, var mHostUrl :String?)

}