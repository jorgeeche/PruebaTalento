package commons.android

import android.content.Context
import android.os.Build
import android.telephony.TelephonyManager

class SystemInformation(private val context: Context) {

    fun getCurrentVersion(): String = context.packageManager.getPackageInfo(context.packageName, 0).versionName

    fun getPhoneManufacter(): String = Build.MANUFACTURER

    fun getPhoneModel(): String = Build.MODEL

    fun getOperativeSystem(): String = Build.VERSION.RELEASE

    //TODO: Deprecado
    /*fun getConnectionType(): String = with(context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager) {
        val networkInfo: NetworkInfo? = this.activeNetworkInfo
        return when (networkInfo?.type) {
            ConnectivityManager.TYPE_WIFI -> "WIFI"
            ConnectivityManager.TYPE_MOBILE -> "3G"
            else -> "NOT_CONNECTED"
        }
    }*/

    fun getChannel(): String = "APP"

    fun getBrandDevice(): String = Build.BRAND

    fun getOperator(): String{
        val tManager: TelephonyManager =  context.getSystemService(TelephonyManager::class.java)
        return tManager.networkOperatorName
    }
}