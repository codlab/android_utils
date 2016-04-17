package eu.codlab.utils.webservice;

import android.util.Log;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by kevinleperf on 16/03/16.
 */
public class DateSerializer implements JsonSerializer<Date> {
    private final static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");

    @Override
    public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
        Log.d("DateSerializer", "formatting date " + src + " to " + DATE_FORMAT.format(src));

        return new JsonPrimitive(DATE_FORMAT.format(src));
    }
}
