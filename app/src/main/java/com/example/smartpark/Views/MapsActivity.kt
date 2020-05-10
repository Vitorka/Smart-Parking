package com.example.smartpark.Views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.smartpark.Data.Institutes
import com.example.smartpark.Models.Institute
import com.example.smartpark.R

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.info_maps_dialog.view.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var listInstitutes: List<Institute>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        // Get the list of institutes
        listInstitutes = Institutes.getInstitutesList()

        // Show the back button in toolbar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker to the target institute
        val targetInstitute = listInstitutes.get(
            intent.getStringExtra("targetInstituteId").toInt())
        val targetPos = LatLng(targetInstitute.getLatitude(), targetInstitute.getLongitude())
        mMap.addMarker(MarkerOptions()
            .position(targetPos)
            .title(targetInstitute.getInstituteName())
            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)))

        // Add a marker to the near institute
        val nearInstitute = listInstitutes.get(
            intent.getStringExtra("nearInstituteId").toInt())
        val nearInstitutePos = LatLng(nearInstitute.getLatitude(), nearInstitute.getLongitude())
        mMap.addMarker(MarkerOptions()
            .position(nearInstitutePos)
            .title(nearInstitute.getInstituteName())
            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)))

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(targetPos, 15F))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.maps_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == R.id.info_maps) {
            // Inflate the dialog with the layout created for the dialog box
            val dialogView = LayoutInflater.from(this).inflate(R.layout.info_maps_dialog, null)

            // Put the name of institutes to show which on the marker represent
            val targetInstitute = listInstitutes.get(
                intent.getStringExtra("targetInstituteId").toInt())
            val nearInstitute = listInstitutes.get(
                intent.getStringExtra("nearInstituteId").toInt())

            dialogView.targetInstName.text = targetInstitute.getInstituteName()
            dialogView.nearInstName.text = nearInstitute.getInstituteName()

            // Build the alert dialog
            val alertDialogBuilder = AlertDialog.Builder(this)
                .setView(dialogView)

            //Show the dialog
            alertDialogBuilder.show()
        }
        return super.onOptionsItemSelected(item)
    }
}