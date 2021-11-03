package com.example.machinori.ui.dashboard

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
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
import com.example.machinori.databinding.FragmentDashboardBinding



class DashboardFragment : Fragment(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().setTitle(com.example.machinori.R.string.title_dashboard)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root
//        val mapFragment = childFragmentManager.findFragmentById(R.id.text_dashboard) as SupportMapFragment
//        mapFragment.getMapAsync(this)
        return root
    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        val mapFragment = childFragmentManager.findFragmentById(R.id.text_dashboard) as? SupportMapFragment
//        mapFragment?.getMapAsync(this)
//    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        try {
            val data = parseJson("Machinori.json")
            val jsonObj = data.getJSONArray("Machinori")
            var cnt = 0
            for (i in 0 until jsonObj.length()) {
                val central = jsonObj.getJSONObject(i)
                val port = Port(central)
                googleMap.addMarker(
                    MarkerOptions()
                        .title(port.location)
                        .zIndex(10f)
                        .position(LatLng(port.lat, port.lng))
                        .icon(BitmapDescriptorFactory.fromResource(com.example.machinori.R.drawable.baseline_directions_bike_black_18))
                )
                cnt++
                Log.d("debug", cnt.toString())
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
        val inputStream= assetManager.open("Machinori.json")
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}