package nichele.meusgastos;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
      GoogleApiClient.ConnectionCallbacks,
      GoogleApiClient.OnConnectionFailedListener,
      com.google.android.gms.location.LocationListener {

   private static final long UPDATE_INTERVAL = 1000;
   private static final long FASTEST_INTERVAL = 1000;
   private GoogleMap mMap;

   private GoogleApiClient mGoogleApiClient;
   private Location mLocation;
   private LocationManager locationManager;
   private LocationRequest mLocationRequest;

   private String TAG = "GPS";

   double latitude =0;
   double longitude =0;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.maps);
      // Obtain the SupportMapFragment and get notified when the map is ready to be used.
      SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
            .findFragmentById(R.id.map);
      mapFragment.getMapAsync(this);

      mGoogleApiClient = new GoogleApiClient.Builder(this)
            .addConnectionCallbacks(this)
            .addOnConnectionFailedListener(this)
            .addApi(LocationServices.API)
            .build();
      locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);



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
   @Override
   public void onMapReady(GoogleMap googleMap) {

      LocationManager locationManager = (LocationManager)
            getSystemService(Context.LOCATION_SERVICE);
//
//
      mMap = googleMap;
      // Add a marker in Sydney and move the camera
      LatLng sydney = new LatLng(latitude, longitude);
//
      mMap.addMarker(new MarkerOptions().position(sydney).title(""));
      mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
   }

   @Override
   public void onConnected(Bundle bundle) {
      if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
         // TODO: Consider calling
         //    ActivityCompat#requestPermissions
         // here to request the missing permissions, and then overriding
         //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
         //                                          int[] grantResults)
         // to handle the case where the user grants the permission. See the documentation
         // for ActivityCompat#requestPermissions for more details.
         return;
      }
      startLocationUpdates();
      mLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
      if (mLocation == null) {
         startLocationUpdates();
      }
      if (mLocation != null) {
         latitude = mLocation.getLatitude();
         longitude = mLocation.getLongitude();
         Toast.makeText(this, "Geo: " + String.valueOf(latitude) + " / " + String.valueOf(longitude) , Toast.LENGTH_LONG).show();
      } else {
      }
   }

   protected void startLocationUpdates() {
      // Create the location request
      mLocationRequest = LocationRequest.create()
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
            .setInterval(UPDATE_INTERVAL)
            .setFastestInterval(FASTEST_INTERVAL);
      // Request location updates
      if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
         // TODO: Consider calling
         //    ActivityCompat#requestPermissions
         // here to request the missing permissions, and then overriding
         //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
         //                                          int[] grantResults)
         // to handle the case where the user grants the permission. See the documentation
         // for ActivityCompat#requestPermissions for more details.
         return;
      }
      LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,
            mLocationRequest, this);
      Log.d("reque", "--->>>>");
   }

   @Override
   public void onConnectionSuspended(int i) {
      Log.i(TAG, "Connection Suspended");
      mGoogleApiClient.connect();
   }

   @Override
   public void onConnectionFailed(ConnectionResult connectionResult) {
      Log.i(TAG, "Connection failed. Error: " + connectionResult.getErrorCode());
   }

   @Override
   public void onStart() {
      super.onStart();
      mGoogleApiClient.connect();
   }

   @Override
   public void onStop() {
      super.onStop();
      if (mGoogleApiClient.isConnected()) {
         mGoogleApiClient.disconnect();
      }
   }

   @Override
   public void onLocationChanged(Location location) {

   }

   @Override
   public void finish() {
      super.finish();
      Intent devolve = new Intent();
      devolve.putExtra("latitude", latitude);
      devolve.putExtra("longitude", longitude);
      setResult(RESULT_OK, devolve);
   }
}

