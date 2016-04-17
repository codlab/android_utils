package eu.codlab.utils.location;

import android.location.Location;

/**
 * Created by kevinleperf on 15/04/16.
 */
public interface ILocationListener {
    void onLocation(Location location);

    void onNoLocation();
}
