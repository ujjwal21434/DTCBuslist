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

class LocationActivity : AppCompatActivity(), SensorEventListener, LocationListener {

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private var currentLocation: Location? = null
    private val requestcode = 1000
    private lateinit var locationTextView: TextView
    private lateinit var sensorManager: SensorManager
    private var mySensor: Sensor? = null
    private var locationManager: LocationManager? = null

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

    private fun initializeLocationComponents() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        if (hasLocationPermission()) {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
            fusedLocationProviderClient.lastLocation.addOnSuccessListener { location: Location? ->
                currentLocation = location
                updateUI()
                requestLocationUpdates()
            }
        }
    }

    private fun requestLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        locationManager?.requestLocationUpdates(
            LocationManager.GPS_PROVIDER,
            1000,
            0f,
            this
        )
    }

    override fun onSensorChanged(event: SensorEvent?) {
        // Handle sensor events here
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // Handle accuracy changes here
    }

    override fun onLocationChanged(location: Location?) {
        currentLocation = location
        updateUI()
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
        // Handle provider status changes here
    }

    override fun onProviderEnabled(provider: String?) {
        // Handle provider enabled events here
    }

    override fun onProviderDisabled(provider: String?) {
        // Handle provider disabled events here
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            requestcode -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    initializeLocationComponents()
                } else {
                    Log.d("MainActivity", "Location permission was not granted.")
                }
                return
            }
            else -> {
                //Ignoring all other requests.
            }
        }
    }

    private fun updateUI() {
        val latitude = currentLocation?.latitude
        val longitude = currentLocation?.longitude
        Log.d("MainActivity", "Latitude: $latitude, Longitude: $longitude")
        locationTextView.text =
            "My Current Coordinates\nLatitude: $latitude\nLongitude: $longitude\n\nBus Details\nRoute Number: \nVehicle Number:"
    }

    override fun onDestroy() {
        super.onDestroy()
        sensorManager.unregisterListener(this)
        locationManager?.removeUpdates(this)
    }
}
