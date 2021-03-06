package com.example.machinori.ui.spot

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import androidx.fragment.app.Fragment
import com.example.machinori.R
import com.google.android.gms.maps.*

class SpotFragment : Fragment(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var mapView: MapView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root= inflater.inflate(R.layout.fragment_spot, container, false)
        mapView = root.findViewById(R.id.text_spot) as MapView
        mapView.onCreate(null)
        mapView.onResume()
        mapView.getMapAsync(this)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val mapFragment = childFragmentManager.findFragmentById(R.id.text_spot) as? SupportMapFragment
        mapFragment?.getMapAsync(this)
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }
    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }
    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }
    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }
    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }
    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        try {
            val data = parseJson("Spot.json")
            val jsonObj = data.getJSONArray("Spot")
            var cnt = 0
            for (i in 0 until jsonObj.length()) {
                val central = jsonObj.getJSONObject(i)
                val port = Port(central)
                googleMap.addMarker(
                    MarkerOptions()
                        .title(port.location)
                        .zIndex(10f)
                        .position(LatLng(port.lat, port.lng))
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                )
                cnt++
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        val kanazawa = LatLng(36.5757632, 136.6372995)
        mMap.moveCamera(CameraUpdateFactory.newLatLng(kanazawa))
    }

    class Port(json: JSONObject) {
        var id: Int
        var location: String
        var lat: Double
        var lng: Double
        init {
            id = json.getInt("id")
            location = json.getString("name")
            lat = json.getDouble("lat")
            lng = json.getDouble("lng")
        }
    }

    @Throws(JSONException::class, IOException::class)
    private fun parseJson(file: String): JSONObject {
        val assetManager = resources.assets
        val inputStream= assetManager.open("Spot.json")
        val bufferedReader = BufferedReader(InputStreamReader(inputStream))
        var json: JSONObject? = null
        var data: String? = ""
        var str = bufferedReader.readLine()
        while (str != null) {
            data += str
            str = bufferedReader.readLine()
        }
        json = JSONObject(data)
        inputStream.close()
        bufferedReader.close()
        return json
    }
}