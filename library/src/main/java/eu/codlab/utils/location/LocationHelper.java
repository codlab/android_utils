package eu.codlab.utils.location;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;

import java.util.Locale;

import eu.codlab.library.BuildConfig;

/**
 * Created by kevinleperf on 16/06/14.
 */
public class LocationHelper implements LocationListener {

    private Context _context;
    private ILocationListener _listener;

    public LocationHelper(Context context, ILocationListener listener) {
        _listener = listener;
        _context = context;
    }

    public boolean hasPermission() {
        return hasPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                && hasPermission(Manifest.permission.ACCESS_COARSE_LOCATION);
    }

    public void startMaps(Location location) {
        if (_context != null &&
                location != null) {
            double longitude = location.getLongitude();
            double latitude = location.getLatitude();

            String uri = String.format(Locale.ENGLISH, "geo:%f,%f", latitude, longitude);
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
            _context.startActivity(intent);
        }
    }

    public void startLocationActivityIfNeeded() {
        if (_context != null) {
            LocationManager service = getLocationManager();
            boolean enabled = service.isProviderEnabled(LocationManager.GPS_PROVIDER);

            if (!enabled) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                _context.startActivity(intent);
            }
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location != null)
            on_location(location);
        else
            on_no_location();
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    private void on_location(Location location) {
        if (_listener != null)
            _listener.onLocation(location);
        LocationManager locationManager = (LocationManager) _context
                .getSystemService(Context.LOCATION_SERVICE);

        try {
            locationManager.removeUpdates(this);
        } catch (SecurityException e) {
            if (BuildConfig.DEBUG)
                e.printStackTrace();
        }
    }

    private void on_no_location() {
        if (_listener != null) _listener.onNoLocation();
        LocationManager locationManager = getLocationManager();
        try {
            locationManager.removeUpdates(this);
        } catch (SecurityException e) {
            if (BuildConfig.DEBUG)
                e.printStackTrace();
        }
    }

    public void retrieveLocationFromAsync() {
        try {
            retrieveGPS();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void retrieveGPS() {
        LocationManager locationManager = getLocationManager();

        boolean isGPSEnabled = locationManager
                .isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean isNetworkEnabled = locationManager
                .isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        Location location = null;

        if (!isGPSEnabled && !isNetworkEnabled) {
            // no network provider is enabled
        } else {
            if (isNetworkEnabled) {
                locationManager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, LocationHelper.this, null);
                location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            }
            // if GPS Enabled get lat/long using GPS Services
            if (isGPSEnabled) {
                locationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, LocationHelper.this, null);
                location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            }
        }

        //return location != null ? location.getLongitude()+","+location.getLatitude():"0,0";
        if (location != null)
            on_location(location);
    }

    private LocationManager getLocationManager() {
        return (LocationManager) _context
                .getSystemService(Context.LOCATION_SERVICE);
    }

    private boolean hasPermission(String permission) {
        return ActivityCompat.checkSelfPermission(_context, permission) != PackageManager.PERMISSION_GRANTED;
    }
}
