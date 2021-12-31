package com.oscarvj.pruebanach.ui.view.locations

import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.firestore.QuerySnapshot
import com.oscarvj.pruebanach.R
import com.oscarvj.pruebanach.constants.Constants
import com.oscarvj.pruebanach.data.firebase.db.FireBase
import com.oscarvj.pruebanach.databinding.FragmentMapsBinding
import com.oscarvj.pruebanach.listener.ReturnLocations
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MapsFragment : Fragment(), ReturnLocations {

    private lateinit var binding: FragmentMapsBinding

    @Inject
    lateinit var fireBase: FireBase

    lateinit var mapFragment : SupportMapFragment

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentMapsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        fireBase.getLocation(this)
    }

    override fun getListLocations(result: QuerySnapshot) {
        mapFragment.getMapAsync(){ googleMap ->
            for (document in result) {
                val loc = LatLng(
                    document.data[Constants.LATITUD].toString().toDouble(),
                    document.data[Constants.LONGITUD].toString().toDouble()
                )
                googleMap.addMarker(MarkerOptions().position(loc).title(document.getDate(Constants.DATE).toString()))
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(loc))
            }

        }
    }

}