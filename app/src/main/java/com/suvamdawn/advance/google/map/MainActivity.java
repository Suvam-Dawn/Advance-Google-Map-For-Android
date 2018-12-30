package com.suvamdawn.advance.google.map;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import static android.support.constraint.Constraints.TAG;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback, LocationListener, GoogleMap.OnCameraMoveStartedListener {
    private String theme = "standard";
    SupportMapFragment mapView;
    GoogleMap mMap=null;
    private LatLng currentlocationlatlong;
    private int int_zoom_value = 14;
    public static Double cur_latitude = 0.0, cur_longitude = 0.0;
    LocationManager locationManager;
    boolean gps_enabled = false;
    boolean network_enabled = false;
    final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    boolean GpsStatus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        setTitle("Standard");
        /*Map View*/
        cur_latitude=12.9716;
        cur_longitude=77.5946;
        currentlocationlatlong = new LatLng(cur_latitude,cur_longitude );
        mapView = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapView);
        mapView.getMapAsync(this);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.zoomIn) {
            if (int_zoom_value <= 20) {
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentlocationlatlong, int_zoom_value));
                CameraUpdate zoom = CameraUpdateFactory.zoomTo(++int_zoom_value);
                mMap.animateCamera(zoom);
            }
            return true;
        } else if (id == R.id.zoomOut) {
            if (int_zoom_value >= 3) {
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentlocationlatlong, int_zoom_value));
                CameraUpdate zoom = CameraUpdateFactory.zoomTo(--int_zoom_value);
                mMap.animateCamera(zoom);
            }
            return true;
        } else if (id == R.id.currentLocation) {
            CheckLocationPermission();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.standard) {
            if (mMap != null) {
                mMap.clear();
                theme = "standard";
                setTitle("Standard");
                mapView.getMapAsync(this);
            }
        } else if (id == R.id.silver) {
            if (mMap != null) {
                mMap.clear();
                theme = "silver";
                setTitle("Silver");
                mapView.getMapAsync(this);
            }
        } else if (id == R.id.retro) {
            if (mMap != null) {
                mMap.clear();
                theme = "retro";
                setTitle("Retro");
                mapView.getMapAsync(this);
            }
        } else if (id == R.id.dark) {
            if (mMap != null) {
                mMap.clear();
                theme = "dark";
                setTitle("Dark");
                mapView.getMapAsync(this);
            }
        } else if (id == R.id.night) {
            if (mMap != null) {
                mMap.clear();
                theme = "night";
                setTitle("Night");
                mapView.getMapAsync(this);
            }
        } else if (id == R.id.aubergine) {
            if (mMap != null) {
                mMap.clear();
                theme = "aubergine";
                setTitle("Aubergine");
                mapView.getMapAsync(this);
            }
        } else if (id == R.id.about) {

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onCameraMoveStarted(int i) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        try {
            mMap = googleMap;
            boolean success = false;
            /*set the style for map*/
            if (theme == "standard") {
                success = mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(MainActivity.this, R.raw.style_json_standard));
            } else if (theme == "silver") {
                success = mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(MainActivity.this, R.raw.style_json_silver));
            } else if (theme == "retro") {
                success = mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(MainActivity.this, R.raw.style_json_retro));
            } else if (theme == "dark") {
                success = mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(MainActivity.this, R.raw.style_json_dark));
            } else if (theme == "night") {
                success = mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(MainActivity.this, R.raw.style_json_night));
            } else if (theme == "aubergine") {
                success = mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(MainActivity.this, R.raw.style_json_aubergine));
            }
            if (!success) {
                Log.e(TAG, "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e(TAG, "Can't find style. Error: ", e);
        }
        /*This is for marker info-window text and set Marker into map*/
        mMap.addMarker(new MarkerOptions().position(currentlocationlatlong)
                .title("Marker Location")
                .snippet("Lat : " + String.valueOf(cur_latitude) + "\nLng : " + String.valueOf(cur_longitude)));
        /*This is handle zoom and Camera view for google map*/
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentlocationlatlong, int_zoom_value));
        if(mMap!=null) {
            /* If you click on Marker this function will work*/
            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    Toast.makeText(MainActivity.this,
                            "-:Marker Click Listener:-\n\n"+
                            "\nPosition : " + String.valueOf(marker.getPosition()) +
                            "\nMarker Title : " + marker.getTitle() +
                            "\nMarker Snippet : " + marker.getSnippet(),
                            Toast.LENGTH_LONG).show();
                    return false;
                }
            });
            /* If you click on Marker top info-window this function will work*/
            mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                @Override
                public void onInfoWindowClick(Marker marker) {
                    Toast.makeText(MainActivity.this,
                            "-:InfoWindow Click Listener:-\n\n"+
                            "\nPosition : " + String.valueOf(marker.getPosition()) +
                            "\nMarker Title : " + marker.getTitle() +
                            "\nMarker Snippet : " + marker.getSnippet(),
                            Toast.LENGTH_LONG).show();
                }
            });
        }
    }
    /*Location Permission Check*/
    public void CheckLocationPermission() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        GpsStatus = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (GpsStatus == false) {
            /* If GPS is disable then open setting for GPS permission*/
            Toast.makeText(getApplicationContext(), "Location Services Is Disabled.Please turn on.", Toast.LENGTH_LONG).show();
            Intent locatioSource = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(locatioSource);
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.ACCESS_FINE_LOCATION)) {
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            MY_PERMISSIONS_REQUEST_LOCATION);
                } else {
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            MY_PERMISSIONS_REQUEST_LOCATION);
                }
            }
        } else {
            getCurrentLocation();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (ActivityCompat.checkSelfPermission(this, permissions[0]) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                /*If you are not enable permission then it will show setting page for enable permission*/
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent.setData(uri);
                startActivity(intent);
            }
        } else {
            getCurrentLocation();
        }
    }
    private void getCurrentLocation() {
        /* final location permission chacking*/
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            Uri uri = Uri.fromParts("package", getPackageName(), null);
            intent.setData(uri);
            startActivity(intent);
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 5, this);
        gps_enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        network_enabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        Location net_loc = null, gps_loc = null, finalLoc = null;
        if (gps_enabled)
            gps_loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (network_enabled)
            net_loc = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        if (gps_loc != null && net_loc != null) {
            //smaller the number more accurate result will
            if (gps_loc.getAccuracy() > net_loc.getAccuracy())
                finalLoc = net_loc;
            else
                finalLoc = gps_loc;
            // I used this just to get an idea (if both avail, its upto you which you want to take as I've taken location with more accuracy)
        } else {

            if (gps_loc != null) {
                finalLoc = gps_loc;
            } else if (net_loc != null) {
                finalLoc = net_loc;
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if(finalLoc!=null) {
                cur_latitude=finalLoc.getLatitude();
                cur_longitude=finalLoc.getLongitude();
            }else{
                cur_latitude=12.9716;
                cur_longitude=77.5946;
            }
        }else{
            if(finalLoc!=null) {
                cur_latitude=finalLoc.getLatitude();
                cur_longitude=finalLoc.getLongitude();
            }else{
                cur_latitude=12.9716;
                cur_longitude=77.5946;
            }
        }
        if(cur_latitude!=12.9716 && cur_longitude!=77.5946) {
            /* based on current location set marker into map and show info window*/
            currentlocationlatlong = new LatLng(cur_latitude, cur_longitude);
            if (mMap != null) {
                mMap.clear();
            }
            mMap.addMarker(new MarkerOptions().position(currentlocationlatlong)
                    .title("Marker Location")
                    .snippet("Lat : " + String.valueOf(cur_latitude) + "\nLng : " + String.valueOf(cur_longitude)));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentlocationlatlong, int_zoom_value));
            CameraUpdate zoom = CameraUpdateFactory.zoomTo(--int_zoom_value);
            mMap.animateCamera(zoom);
        }
    }
}
