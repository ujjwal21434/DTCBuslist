import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

/**
 * LocationActivity is responsible for requesting location permissions, initializing location
 * components, and updating the user interface with the user's current coordinates.
 */
class LocationActivity : AppCompatActivity(), SensorEventListener, LocationListener {

    // Region: Properties

    /**
     * The FusedLocationProviderClient is used to get the user's last known location and request
     * location updates.
     */
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    /**
     * The current location of the user, updated through location updates.
     */
    private var currentLocation: Location? = null

    /**
     * The request code used when requesting location permissions.
     */
    private val requestcode = 1000

    /**
     * The TextView used to display the user's current coordinates and bus details.
     */
    private lateinit var locationTextView: TextView

    /**
     * The SensorManager is used to interact with the device's sensors.
     */
    private lateinit var sensorManager: SensorManager

    /**
     * The mySensor is used to get the device's orientation.
     */
    private var mySensor: Sensor? = null

    /**
     * The LocationManager is used to request location updates from the device.
     */
    private lateinit var locationManager: LocationManager? = null

    // EndRegion: Properties

    // Region: Lifecycle Methods

    /**
     * onCreate is called when the activity is first created. It initializes the user interface,
     * gets the device's sensors, and checks for location permissions.
     *
     * @param savedInstanceState The saved instance state, if available.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third)

        locationTextView = findViewById(R.id.locationTextView)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        mySensor = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION)

        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        if (hasLocationPermission()) {
            initializeLocationComponents()
        }
    }

    // EndRegion: Lifecycle Methods

    // Region: Permission Methods

    /**
     * hasLocationPermission checks if the app has the necessary location permissions. If not, it
     * requests the permissions and returns false.
     *
     * @return true if the app has location permissions, false otherwise.
     */
    private fun hasLocationPermission(): Boolean {
        return if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                requestcode
            )
            false
        } else {
            true
        }
    }

    // EndRegion: Permission Methods

    // Region: Location Methods

    /**
     * initializeLocationComponents initializes the FusedLocationProviderClient and requests the
     * user's last known location.
     */
    private fun initializeLocationComponents() {
        fusedLocationProviderClient =
