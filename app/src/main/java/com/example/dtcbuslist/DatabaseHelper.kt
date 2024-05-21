import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

// DatabaseHelper class to manage database creation and upgrades
class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        // Database version and name
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "BusRoutes.db"

        // Table name and column names
        private const val TABLE_NAME = "bus_routes"
        private const val COLUMN_ROUTE = "route"
        private const val COLUMN_STOP_NAME = "stop_name"
        private const val COLUMN_LAT = "lat"
        private const val COLUMN_LONG = "long"
    }

    // Context variable to access application resources
    private val mContext: Context = context

    // onCreate method called when the database is created for the first time
    override fun onCreate(db: SQLiteDatabase) {
        // Creating the bus_routes table with specified columns
        val createTableQuery = ("CREATE TABLE " + TABLE_NAME + "("
                + COLUMN_ROUTE + " TEXT,"
                + COLUMN_STOP_NAME + " TEXT,"
                + COLUMN_LAT + " REAL,"
                + COLUMN_LONG + " REAL" + ")")

        db.execSQL(createTableQuery)

        // Inserting bus routes from CSV file into the table
        insertBusRoutesFromCsv(db)
    }

    // onUpgrade method called when the database needs to be upgraded
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Dropping the existing table if it exists
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        // Recreating the table after dropping
        onCreate(db)
    }

    // Method to insert bus routes from CSV file into the table
    private fun insertBusRoutesFromCsv(db: SQLiteDatabase) {
        try {
            // Opening the CSV file from assets folder
            val inputStream = mContext.assets.open("busroutes.csv")
            val bufferedReader = BufferedReader(InputStreamReader(inputStream))

            // Skipping the header line
            bufferedReader.readLine()

            var line: String? = bufferedReader.readLine()
            while (line != null) {
                // Splitting the line into columns
                val split = line.split(",")
                val route = split[0]
                val stopName = split[1]
                val lat = split[2].toDouble()
                val long = split[3].toDouble()

                // Creating an SQL query to insert the data
                val insertQuery = ("INSERT INTO $TABLE_NAME ($COLUMN_ROUTE, $COLUMN_STOP_NAME, $COLUMN_LAT, $COLUMN_LONG) " +
                                  "VALUES ('$route', '$stopName', $lat, $long)")

                // Executing the SQL query to insert the data
                db.execSQL(insertQuery)

                // Reading the next line
                line = bufferedReader.readLine()
            }

            // Closing the buffered reader
            bufferedReader.close()
        } catch (e: IOException) {
            // Logging the error while reading the CSV file
            Log.e("DatabaseHelper", "Error reading CSV file: ${e.message}")
        } catch (e: Exception) {
            // Logging the error while inserting data into the database
            Log.e("DatabaseHelper", "Error inserting data into database: ${e.message}")
        }
    }
}
