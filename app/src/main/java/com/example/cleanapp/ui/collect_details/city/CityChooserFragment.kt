package com.example.cleanapp.ui.collect_details.city

import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Build
import android.text.Editable
import android.text.TextWatcher
import android.util.Log.d
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cleanapp.R
import com.example.cleanapp.base.BaseFragment
import com.example.cleanapp.databinding.CityChooserFragmentBinding
import com.example.cleanapp.extensions.gone
import com.example.cleanapp.extensions.goneIf
import com.example.cleanapp.models.City
import com.example.cleanapp.models.Order
import com.example.cleanapp.models.ResultHandler
import com.example.cleanapp.ui.collect_details.ChooserViewModel
import com.example.cleanapp.utils.LocationSettings
import com.google.android.gms.location.*
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class CityChooserFragment :
    BaseFragment<CityChooserFragmentBinding>(CityChooserFragmentBinding::inflate) {

    private val viewModel: CityChooserViewModel by viewModels()
    private val sharedViewModel: ChooserViewModel by activityViewModels()

    private var cities = mutableListOf<City>()
    private lateinit var adapter: CitiesAdapter
    private lateinit var order: Order

    //remember if we are tracking location or not - shesatania settingebshi
    //TODO


    //Google API for location services
    private lateinit var locationProvider: FusedLocationProviderClient

    //It is a config file for all settings related to FusedLocationProviderClient
    private val locationRequest = LocationRequest.create()

    private lateinit var locationCallback: LocationCallback

    companion object {
        const val PERMISSION_FINE_LOCATION = 99
    }


    override fun start() {

        init()
        updateGPS()
        binding.btnBack.setOnClickListener {
            sharedViewModel.setFragmentTitle(getString(R.string.rooms_title))
            findNavController().navigateUp()
        }
    }


    private fun init() {
        order = arguments?.getParcelable("order") ?: Order()
        observes()
        initRecycler()
        binding.inputCity.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                filter(s.toString())
            }
        })
        getLocation()
    }

    private fun initRecycler() {
        adapter =
            CitiesAdapter().apply {
                clickListener = { city ->
                    sharedViewModel.setFragmentTitle(city.cityEn)
                    order.city = city

                    navigateTo(R.id.action_global_masterResultsFragment, order)
                }
            }
        binding.recyclerCities.layoutManager = LinearLayoutManager(requireActivity())
        binding.recyclerCities.adapter = adapter
        adapter.notifyDataSetChanged()
        viewModel.getCities()
    }

    private fun navigateTo(id: Int, order: Order) {
        binding.etFindCity.clearFocus()
        requireActivity().findNavController(R.id.nav_host_fragment).navigate(
            R.id.action_global_masterResultsFragment,
            bundleOf("order" to order)
        )
    }

    private fun filter(input: String) {
        d("FILTER ", "$input ${cities.size}")
        val filteredCities = mutableListOf<City>()
        for (city in cities) {
            if (city.cityEn.lowercase(Locale.getDefault())
                    .contains(input.lowercase(Locale.getDefault()))
            ) {
                filteredCities.add(city)
            }
        }
        adapter.filterCities(filteredCities)
    }

    private fun observes() {
        viewModel.citiesLiveData.observe(viewLifecycleOwner, {
            when (it) {
                is ResultHandler.Success -> {
                    cities = it.data!!.toMutableList()
                    adapter.setItem(it.data)
                    binding.progress.gone()
                }
                is ResultHandler.Error -> {
                    showErrorDialog(it.message)
                    binding.progress.gone()
                }

                is ResultHandler.Loading -> {
                    binding.progress.goneIf(!it.loading)
                }
            }
        })
    }


    private fun getLocation() {

        // how often does the default location check occur
        locationRequest.interval = LocationSettings.DEFAULT_UPDATE_INTERVAL

        // how often does the location check occur when set to the most frequent update
        locationRequest.fastestInterval = LocationSettings.FAST_UPDATE_INTERVAL

        locationRequest.priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY

        //Location Call Back - get the timestamp of the location update and display the latitude,
        // longitude and timestamp
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult ?: return
                for (location in locationResult.locations) {
                    updateUIValues(location)
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        stopLocationUpdates()
    }

    // set location accuracy (LocationRequest.INT)
    //TODO
    private fun accuracy() {
        //GPS sensor
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

        //Towers or WiFi
        locationRequest.priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY

    }

    private fun updateGPS() {
        //get permissions from the user to track GPS
        //get the current location from the fused client
        //update the UI

        locationProvider = LocationServices.getFusedLocationProviderClient(requireActivity())

        // check if user gave permission
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        //user gave permission
        ) {
            locationProvider.lastLocation
                .addOnSuccessListener {
                    if (it == null) {
                        d("IT", "NULL")
                    } else {
                        updateUIValues(it)
                    }
                }
        } else {
            // user didn't give permission
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(
                    arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                    PERMISSION_FINE_LOCATION
                )
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSION_FINE_LOCATION -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    updateGPS()
                } else {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.location_permission),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun updateUIValues(location: Location) {
        val currentLat = location.latitude.toString()
        val currentLon = location.longitude.toString()
        val accuracy = location.accuracy
        d("LOCATION ", "LONGITUDE - $currentLon, LATITUDE - $currentLat, ACCURACY - $accuracy")
        binding.inputCity.setText(getCityName(location))
    }

    //TODO
    private fun startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        locationProvider.requestLocationUpdates(locationRequest, locationCallback, null)
    }

    private fun stopLocationUpdates() {
        locationProvider.removeLocationUpdates(locationCallback)
    }

    private fun getCityName(location: Location): String {
        var city = ""
        val geocoder = Geocoder(requireContext())
        try {
            val geoCity = geocoder.getFromLocation(location.latitude, location.longitude, 1)
            city = geoCity[0].locality.toString().replace("'", "")

        } catch (e: Exception) {
            d("GEOCODER", "Couldn't get city name")
        }
        return city
    }
}