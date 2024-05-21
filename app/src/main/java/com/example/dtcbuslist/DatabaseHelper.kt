import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "BusRoutes.db"
        private const val TABLE_NAME = "bus_routes"
        private const val COLUMN_ROUTE = "route"
        private const val COLUMN_STOP_NAME = "stop_name"
        private const val COLUMN_LAT = "lat"
        private const val COLUMN_LONG = "long"
    }

    private val mContext: Context = context

    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery = ("CREATE TABLE " + TABLE_NAME + "("
                + COLUMN_ROUTE + " TEXT,"
                + COLUMN_STOP_NAME + " TEXT,"
                + COLUMN_LAT + " REAL,"
                + COLUMN_LONG + " REAL" + ")")

        db.execSQL(createTableQuery)

        insertBusRoutesFromCsv(db)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    private fun insertBusRoutesFromCsv(db: SQLiteDatabase) {
        try {
            val inputStream = mContext.assets.open("busroutes.csv")
            val bufferedReader = BufferedReader(InputStreamReader(inputStream))
            bufferedReader.readLine() // Skip header line

            var line: String? = bufferedReader.readLine()
            while (line != null) {
                val split = line.split(",")
                val route = split[0]
                val stopName = split[1]
                val lat = split[2].toDouble()
                val long = split[3].toDouble()

                val insertQuery = ("INSERT INTO $TABLE_NAME ($COLUMN_ROUTE, $COLUMN_STOP_NAME, $COLUMN_LAT, $COLUMN_LONG) " +
                                  "VALUES ('$route', '$stopName', $lat, $long)")

                db.execSQL(insertQuery)
                line = bufferedReader.readLine()
            }

            bufferedReader.close()
        } catch (e: IOException) {
            Log.e("DatabaseHelper", "Error reading CSV file: ${e.message}")
        } catch (e: Exception) {
            Log.e("DatabaseHelper", "Error inserting data into database: ${e.message}")
        }
    }
}
