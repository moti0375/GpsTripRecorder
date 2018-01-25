package com.bartovapps.gpstriprec.db;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;

import com.bartovapps.gpstriprec.maphelper.ImageMarker;
import com.bartovapps.gpstriprec.trip.Trip;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class TripsDataSource {
    public static final String LOG_TAG = TripsDataSource.class.getSimpleName();

    SQLiteOpenHelper dbhelper;
    SQLiteDatabase database;

    private static final String[] allColumns = {
            TripsDBOpenHelper.COLUMN_ID,
            TripsDBOpenHelper.COLUMN_DATE,
            TripsDBOpenHelper.COLUMN_DURATION,
            TripsDBOpenHelper.COLUMN_DIST,
            TripsDBOpenHelper.COLUMN_SPEED,
            TripsDBOpenHelper.COLUMN_FROM,
            TripsDBOpenHelper.COLUMN_TO,
            TripsDBOpenHelper.COLUMN_MAP,
            TripsDBOpenHelper.COLUMN_MAX_SPEED,
            TripsDBOpenHelper.COLUMN_MAX_ALT,
            TripsDBOpenHelper.COLUMN_NAME,
            TripsDBOpenHelper.COLUMN_MAP_IMAGE,
            TripsDBOpenHelper.COLUMN_MOVE_SPEED,
            TripsDBOpenHelper.COLUMN_MOVE_TIME,
            TripsDBOpenHelper.COLUMN_STOP_TIME };


    private static final String[] markersColumns ={
            TripsDBOpenHelper.COLUMN_MARKER_ID,
            TripsDBOpenHelper.COLUMN_MARKER_TRIP_ID,
            TripsDBOpenHelper.COLUMN_MARKER_LATITUDE,
            TripsDBOpenHelper.COLUMN_MARKER_LONGITUDE,
            TripsDBOpenHelper.COLUMN_MARKER_URI,
            TripsDBOpenHelper.COLUMN_MARKER_SNIPPET
    };


    public TripsDataSource(Activity activity) {
        dbhelper = new TripsDBOpenHelper(activity.getApplicationContext());
    }

    public void open() {
//		Log.i(LOG_TAG, "Database opened");
        database = dbhelper.getWritableDatabase();
    }

    public void close() {
//		Log.i(LOG_TAG, "Database closed");		
        dbhelper.close();
    }

    public Trip create(Trip trip) {
        ContentValues values = new ContentValues();
        values.put(TripsDBOpenHelper.COLUMN_DATE, trip.getDate());
        values.put(TripsDBOpenHelper.COLUMN_DURATION, trip.getDuration());
        values.put(TripsDBOpenHelper.COLUMN_DIST, trip.getDistance());
        values.put(TripsDBOpenHelper.COLUMN_SPEED, trip.getSpeed());
        values.put(TripsDBOpenHelper.COLUMN_FROM, trip.getStartAddress());
        values.put(TripsDBOpenHelper.COLUMN_TO, trip.getStopAddress());
        values.put(TripsDBOpenHelper.COLUMN_MAP, trip.getKml());
        values.put(TripsDBOpenHelper.COLUMN_MAX_SPEED, trip.getMaxSpeed());
        values.put(TripsDBOpenHelper.COLUMN_MAX_ALT, trip.getMaxAlt());
        values.put(TripsDBOpenHelper.COLUMN_NAME, trip.getTripName());
        values.put(TripsDBOpenHelper.COLUMN_MAP_IMAGE, trip.getImageFileName());
        values.put(TripsDBOpenHelper.COLUMN_MOVE_SPEED, trip.getMove_average_speed());
        values.put(TripsDBOpenHelper.COLUMN_MOVE_TIME, trip.getMoveTime());
        values.put(TripsDBOpenHelper.COLUMN_STOP_TIME, trip.getStopTime());
        long insertId = database.insert(TripsDBOpenHelper.TABLE_TRIPS, null, values);
        trip.setId(insertId);
//		Log.i(LOG_TAG, "Trip created, map: " + trip.getKml());

        return trip;
    }

    public ArrayList<Trip> findAll() {
        ArrayList<Trip> trips = new ArrayList<Trip>();
        Cursor cursor = null;
        try {
            cursor = database.query(TripsDBOpenHelper.TABLE_TRIPS, allColumns,
                    null, null, null, null, TripsDBOpenHelper.COLUMN_ID + " DESC");
        } catch (SQLiteException e) {
            e.printStackTrace();
//            Log.i(LOG_TAG, "There was an SQLite exception: " + e.getMessage());

            if (e.getMessage().contains("no such column")) {
                // recoverDb();
            }
            return trips;
        }

//        Log.i(LOG_TAG, "Returned " + cursor.getCount() + " rows");
//        Log.i(LOG_TAG, "Trip MOVE column " + cursor.getColumnIndex(TripsDBOpenHelper.COLUMN_MOVE_SPEED));
//        Log.i(LOG_TAG, "Trip ID column " + cursor.getColumnIndex(TripsDBOpenHelper.COLUMN_ID));
//        Log.i(LOG_TAG, "Trip DATE column " + cursor.getColumnIndex(TripsDBOpenHelper.COLUMN_DATE));
//        Log.i(LOG_TAG, "Trip MOVE_TIME column " + cursor.getColumnIndex(TripsDBOpenHelper.COLUMN_MOVE_TIME));
//        Log.i(LOG_TAG, "Trip STOP_TIME column " + cursor.getColumnIndex(TripsDBOpenHelper.COLUMN_STOP_TIME));
        if (cursor.getCount() > 0) {

            while (cursor.moveToNext()) {
                Trip trip = new Trip();
                trip.setId(cursor.getLong(cursor.getColumnIndex(TripsDBOpenHelper.COLUMN_ID)));
                trip.setDate(cursor.getString(cursor.getColumnIndex(TripsDBOpenHelper.COLUMN_DATE)));
                trip.setDuration(cursor.getLong(cursor.getColumnIndex(TripsDBOpenHelper.COLUMN_DURATION)));
                trip.setDistance(cursor.getFloat(cursor.getColumnIndex(TripsDBOpenHelper.COLUMN_DIST)));
                trip.setSpeed(cursor.getDouble(cursor.getColumnIndex(TripsDBOpenHelper.COLUMN_SPEED)));
                trip.setStartAddress(cursor.getString(cursor.getColumnIndex(TripsDBOpenHelper.COLUMN_FROM)));
                trip.setStopAddress(cursor.getString(cursor.getColumnIndex(TripsDBOpenHelper.COLUMN_TO)));
                trip.setKml(cursor.getString(cursor.getColumnIndex(TripsDBOpenHelper.COLUMN_MAP)));
                trip.setMaxSpeed(cursor.getDouble(cursor.getColumnIndex(TripsDBOpenHelper.COLUMN_MAX_SPEED)));
                trip.setMaxAlt(cursor.getDouble(cursor.getColumnIndex(TripsDBOpenHelper.COLUMN_MAX_ALT)));
                trip.setTripName(cursor.getString(cursor.getColumnIndex(TripsDBOpenHelper.COLUMN_NAME)));
                trip.setImageFileName(cursor.getString(cursor.getColumnIndex(TripsDBOpenHelper.COLUMN_MAP_IMAGE)));
                trip.setMove_average_speed(cursor.getDouble(cursor.getColumnIndex(TripsDBOpenHelper.COLUMN_MOVE_SPEED)));
                trip.setMoveTime(cursor.getLong(cursor.getColumnIndex(TripsDBOpenHelper.COLUMN_MOVE_TIME)));
                trip.setStopTime(cursor.getLong(cursor.getColumnIndex(TripsDBOpenHelper.COLUMN_STOP_TIME)));
                trips.add(trip);

//				Log.i(LOG_TAG, "Trip add: " + trip.toString());
            }
        }
        return trips;
    }

    public boolean removeSavedTrip(Trip trip) {
        String where = TripsDBOpenHelper.COLUMN_ID + "=" + trip.getId();
        int result = database.delete(TripsDBOpenHelper.TABLE_TRIPS, where, null);

        if (trip.getKml() != null) {
            File mapFile = new File(trip.getKml());
            if (mapFile.exists()) {
                mapFile.delete();
            }
        }


//		Log.i(LOG_TAG, "Trip removed, map file deleted..");

        return (result == 1);
    }

    public boolean updateTripTitle(Trip trip, String title) {
//        Log.i(LOG_TAG, "About to update trip " + trip.getId() + " title with " + title);
        ContentValues args = new ContentValues();
        String where = TripsDBOpenHelper.COLUMN_ID + "=" + trip.getId();
        args.put(TripsDBOpenHelper.COLUMN_NAME, title);
        return database.update(TripsDBOpenHelper.TABLE_TRIPS, args, where, null) > 0;
    }

    public boolean updateTripData(long tripId, String column, String data) {
//        Log.i(LOG_TAG, "About to update trip " + tripId + " " + column + " with " + data);
        ContentValues args = new ContentValues();
        String where = TripsDBOpenHelper.COLUMN_ID + "=" + tripId;
        args.put(column, data);
        return database.update(TripsDBOpenHelper.TABLE_TRIPS, args, where, null) > 0;
    }

    private void recoverDb() {
        dbhelper.onCreate(database);
    }

    public List<ImageMarker> insertImageMarkers(List<ImageMarker> markers, double tripId){
        ContentValues values = new ContentValues();

        for(ImageMarker marker : markers){
            values.put(TripsDBOpenHelper.COLUMN_MARKER_TRIP_ID, tripId);
            values.put(TripsDBOpenHelper.COLUMN_MARKER_LATITUDE, marker.getLatitude());
            values.put(TripsDBOpenHelper.COLUMN_MARKER_LONGITUDE, marker.getLongitude());
            values.put(TripsDBOpenHelper.COLUMN_MARKER_URI, marker.getImageUri().toString());
            long insertId = database.insert(TripsDBOpenHelper.TABLE_MARKERS, null, values);
            values.clear();
        }
        Log.i(LOG_TAG, markers.size() + " marker was inserted to database");

        return  markers;
    }


    public ArrayList<ImageMarker> findAllMarkersForTrip(long tripId){
        ArrayList<ImageMarker> markers = new ArrayList<>();

        Cursor cursor = null;
        try {
            cursor = database.query(TripsDBOpenHelper.TABLE_MARKERS, markersColumns,
                    TripsDBOpenHelper.COLUMN_MARKER_TRIP_ID + " = ?", new String[] {"" + tripId}, null, null, TripsDBOpenHelper.COLUMN_MARKER_ID );
        } catch (SQLiteException e) {
            e.printStackTrace();
     //       Log.i(LOG_TAG, "There was an SQLite exception: " + e.getMessage());

            if (e.getMessage().contains("no such column")) {
                // recoverDb();
            }
            return markers;
        }

        if (cursor.getCount() > 0) {

            while (cursor.moveToNext()) {
                ImageMarker marker = new ImageMarker();
                marker.setLatitude(cursor.getDouble(cursor.getColumnIndex(TripsDBOpenHelper.COLUMN_MARKER_LATITUDE)));
                marker.setLongitude(cursor.getDouble(cursor.getColumnIndex(TripsDBOpenHelper.COLUMN_MARKER_LONGITUDE)));
                marker.setImageUri(Uri.parse(cursor.getString(cursor.getColumnIndex(TripsDBOpenHelper.COLUMN_MARKER_URI))));
                markers.add(marker);

//				Log.i(LOG_TAG, "Trip add: " + trip.toString());
            }
        }
        return markers;
    }

    public ArrayList<Uri> findAllMarkersUrisForTrip(long tripId){
        ArrayList<Uri> uris = new ArrayList<>();

        Cursor cursor = null;
        try {
            cursor = database.query(TripsDBOpenHelper.TABLE_MARKERS, markersColumns,
                    TripsDBOpenHelper.COLUMN_MARKER_TRIP_ID + " = ?", new String[] {"" + tripId}, null, null, TripsDBOpenHelper.COLUMN_MARKER_ID);
        } catch (SQLiteException e) {
            e.printStackTrace();
//            Log.i(LOG_TAG, "There was an SQLite exception: " + e.getMessage());

            if (e.getMessage().contains("no such column")) {
                // recoverDb();
            }
            return uris;
        }

        if (cursor.getCount() > 0) {

            while (cursor.moveToNext()) {
                Uri uri = Uri.parse(cursor.getString(cursor.getColumnIndex(TripsDBOpenHelper.COLUMN_MARKER_URI)));
                uris.add(uri);

//				Log.i(LOG_TAG, "Trip add: " + trip.toString());
            }
        }
        return uris;
    }

    public int deleteMarkersForTrip(double tripId){
        String where = TripsDBOpenHelper.COLUMN_MARKER_TRIP_ID + "=" + tripId;
        int result = database.delete(TripsDBOpenHelper.TABLE_MARKERS, where, null);
//        Log.i(LOG_TAG, result + " markers were removed from database");

        return result;
    }
}
