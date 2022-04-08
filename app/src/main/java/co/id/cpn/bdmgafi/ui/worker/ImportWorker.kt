package co.id.cpn.bdmgafi.ui.worker

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Environment
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import co.id.cpn.domain.main.MainRepository
import co.id.cpn.entity.Asset
import co.id.cpn.entity.Customer
import co.id.cpn.entity.CustomerType
import co.id.cpn.entity.ProductOrder
import co.id.cpn.entity.util.Constants
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONArray
import org.json.JSONObject
import org.koin.java.KoinJavaComponent

class ImportWorker(appContext: Context, workerParams: WorkerParameters)
    : CoroutineWorker(appContext, workerParams) {

    val appRepo: MainRepository by KoinJavaComponent.inject(MainRepository::class.java)

    override suspend fun doWork(): Result {
        var regionSid: String? = null
        var distributionSid: String? = null
        try {
            Log.w("WORK", "Import Work")
            regionSid = inputData.getString(Constants.REGION)
            distributionSid = inputData.getString(Constants.DISTRIBUTION)

            Gson().fromJson<List<Asset>>(readData("asset", regionSid).toString(), object : TypeToken<List<Asset>>() {}.type)
                .forEach {
                    Log.w("asset", "$it")
                    appRepo.insertAsset(it)
                }

            Gson().fromJson<List<Customer>>(readData("customer", regionSid).toString(), object : TypeToken<List<Customer>>() {}.type)
                .forEach {
                    it._id = "${regionSid}|${it.customerSID}|${distributionSid}"
                    it.regionSID = "$regionSid"
                    it.distributionSID = "$distributionSid"
                    appRepo.insertCustomer(it)
                    Log.w("customer", "$it")
                }

            Gson().fromJson<List<CustomerType>>(readData("customer_type", regionSid).toString(), object : TypeToken<List<CustomerType>>() {}.type)
                .forEach {
                    Log.w("customer_type", "$it")
                    appRepo.insertCustomerType(it)
                }

            Gson().fromJson<List<ProductOrder>>(readData("product_order", regionSid).toString(), object : TypeToken<List<ProductOrder>>() {}.type)
                .forEach {
                    Log.w("product_order", "$it")
                    it._id = "${it.productSID}|${it.customerSID}"
                    appRepo.insertProductOrder(it)
                }

            return Result.success()
        } catch (exception: Exception) {
            Log.e("WORK", "Error Import Work", exception)
            return Result.failure()
        }
    }

    fun readData(tableName: String, region: String?): JSONArray {
        val stringBuilder = StringBuilder()
        stringBuilder.append(Environment.getExternalStorageDirectory().toString())
        stringBuilder.append("/SADIX-GAFI/.temp/")
        stringBuilder.append(region)
        stringBuilder.append(".db")
        val list = JSONArray()
        try {
            val sQLiteDatabase = SQLiteDatabase.openDatabase(stringBuilder.toString(), null,
                SQLiteDatabase.NO_LOCALIZED_COLLATORS or
                        SQLiteDatabase.OPEN_READWRITE
            )
            val cursor = sQLiteDatabase.rawQuery("SELECT * FROM $tableName", null)
            while (cursor.moveToNext()) {
                val json = JSONObject()
                for (i in 0 until cursor.columnCount) {
                    if (cursor.getType(i) == Cursor.FIELD_TYPE_NULL) {
                        json.put(cursor.getColumnName(i), "")
                    } else if (cursor.getType(i) == Cursor.FIELD_TYPE_STRING) {
                        json.put(cursor.getColumnName(i), cursor.getString(i))
                    } else if (cursor.getType(i) == Cursor.FIELD_TYPE_INTEGER) {
                        json.put(cursor.getColumnName(i), cursor.getInt(i))
                    } else if (cursor.getType(i) == Cursor.FIELD_TYPE_FLOAT) {
                        json.put(cursor.getColumnName(i), cursor.getFloat(i).toDouble())
                    }
                }
                Log.w("TAG", "readData: $json")
                list.put(json)
            }
            cursor.close()
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        return list
    }
}