package helloworld.mmu.ac.uk.resturantrating;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.mapbox.android.core.location.LocationEngine;
import com.mapbox.android.core.location.LocationEngineListener;
import com.mapbox.android.core.location.LocationEnginePriority;
import com.mapbox.android.core.location.LocationEngineProvider;
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdate;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
import com.mapbox.mapboxsdk.location.modes.RenderMode;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.plugins.locationlayer.LocationLayerPlugin;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Activity2 extends AppCompatActivity {

    double lng;
    double lat;
    int position1;
    private String entry = "";
    private String url = "";
    private MapView mapview;
    private ArrayList<String> listItems = new ArrayList<>();
    private ArrayList<String> data = new ArrayList<>();
    private ArrayList<String> names = new ArrayList<>();
    private ArrayList<Integer> FHRS1 = new ArrayList<>();
    private ArrayList<Integer> pos = new ArrayList<>();
    private ArrayList<Double> lng1 = new ArrayList<>();
    private ArrayList<Double> lat1 = new ArrayList<>();
    ResturantData resturants = new ResturantData("BusinessName", "AddressLine1", "AddressLine2", "AddressLine3", "PostCode", "RatingValue", "DistanceKM", "Location", "Latitude", "Longitude");
    private int[] FHRS = {R.drawable.rating0, R.drawable.rating1, R.drawable.rating2, R.drawable.rating3, R.drawable.rating4, R.drawable.rating5, R.drawable.ratingexempt};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, getString(R.string.access_token));
        setContentView(R.layout.activity_2);
        mapview = (MapView) findViewById(R.id.mapView);
        mapview.onCreate(savedInstanceState);

        //Creates drop down menu
        final Spinner mySpinner = (Spinner) findViewById(R.id.spinner1);
        Button buttonHome = (Button) findViewById(R.id.home);
        //Set on click for the drop down menu
        buttonHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnHome();
            }
        });

        Button b2 = (Button) findViewById(R.id.Search);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newsearch();
            }
        });

        Button clear = (Button) findViewById(R.id.clear);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearData();
            }
        });

        Button showMap = (Button) findViewById(R.id.mapB);
        showMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mapboxAdd();
            }
        });
        //Creates adapter to get list data from strings
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(Activity2.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.options));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(myAdapter);
        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                position = mySpinner.getSelectedItemPosition();
                position1 = position;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //Checks connection
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            // fetch data
        } else {
            //display error
        }
        //Checks permissions and gets location services
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{
                    "android.permission.ACCESS_FINE_LOCATION", "android.permission.ACCESS_COARSE_LOCATION"
            }, 1);
        } else {
            LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    lat = location.getLatitude();
                    lng = location.getLongitude();
//                    ((TextView) findViewById(R.id.latDisplay)).setText("" + lat);
//                    ((TextView) findViewById(R.id.lngDisplay)).setText("" + lng);
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
            });
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        mapview.onStart();
    }
    @Override
    protected void onResume() {
        super.onResume();
        mapview.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        mapview.onPause();
    }
    @Override
    protected void onStop() {
        super.onStop();
        mapview.onStop();
    }
    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        mapview.onSaveInstanceState(outState);
    }
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapview.onLowMemory();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapview.onDestroy();
    }

    //Sets recycleView up with recycle class.
    private void initRecyclerview() {
        RecyclerView recyclerView = findViewById(R.id.recyclerv_view);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(data, pos, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
    //Return home fucntion
    private void returnHome() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    //Clears all data in views
    private void clearData(){
        pos.clear();
        data.clear();
        lat1.clear();
        lng1.clear();
        initRecyclerview();
        mapboxRemove();
    }

    //Mapbox function
    private void mapboxAdd(){
        mapview.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(MapboxMap mapboxMap) {
                mapboxMap.setCameraPosition(
                        new CameraPosition.Builder().target(new LatLng(lat, lng)).build()
                );
                for(int i = 0; i < data.size(); i++) {
                    double latitude = lat1.get(i);
                    double longitude = lng1.get(i);
                    String name = names.get(i);
                    String data1 = data.get(i);
                    mapboxMap.addMarker(new MarkerOptions()
                            .position(new LatLng(latitude, longitude))
                            .title(name)
                            .snippet(data1));
                }
            }
        });

    }
    //Removes mapbox data
    private void mapboxRemove(){
        mapview.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(MapboxMap mapboxMap) {
                mapboxMap.setCameraPosition(new CameraPosition.Builder().target(new LatLng(lat, lng)).build());
                mapboxMap.clear();
            }

        });

    }

    //AsyncTask to perform search in the background
    public class RetrieveData extends AsyncTask<String, Void, String> {
        private static final String TAG = "RetrieveData";

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            initRecyclerview();
            Log.d(TAG, "onPostExecute: parameter is " + s);
        }

        @Override
        protected String doInBackground(String... strings) {
            getJson(url + entry);
            Log.d(TAG, "doInBackground: starts with " + strings[0]);
            return "doInBackground completed.";
        }

        //Performs server search and parses data.
        private String getJson(String urlPath) {
            try {
                URL url = new URL(urlPath);
                URLConnection connection = url.openConnection();
                InputStreamReader ins = new InputStreamReader(connection.getInputStream());
                BufferedReader in = new BufferedReader(ins);
                //now read the input stream, as normal I/O
                String line = "";
                while ((line = in.readLine()) != null) {
                    JSONArray ja = new JSONArray(line);
                    for (int i = 0; i < ja.length(); i++) {
                        JSONObject jo = (JSONObject) ja.get(i);
                        names.add(jo.getString(resturants.getBusinessName()));
                        listItems.add(jo.getString(resturants.getBusinessName()));
                        listItems.add(jo.getString(resturants.getAddressLine1()));
                        listItems.add(jo.getString(resturants.getAddressLine2()));
                        listItems.add(jo.getString(resturants.getAddressLine3()));
                        listItems.add(jo.getString(resturants.getPostcode()));
                        listItems.add(jo.getString(resturants.getRatingValue()));

                        JSONObject jo1 = (JSONObject) jo.get("Location");
                        lat1.add(jo1.getDouble(resturants.getLat()));
                        lng1.add(jo1.getDouble(resturants.getLng()));

                        //Adds KM to the search based on location
                        if (position1 == 1) {
                            double km = jo.getDouble(resturants.getDistanceKm());
                            km = Math.round(km * 1000.0)/1000.0;
                            listItems.add(("" + km));
                            checkRating();
                            data.add(listItems.get(0) + "\n" + listItems.get(1) + "," + listItems.get(2) + "," + listItems.get(3) + "," + listItems.get(4) + "\n" + "Distance to establishment: " + listItems.get(5) + "km");
                        } else {
                            checkRating();
                            data.add(listItems.get(0) + "\n" + listItems.get(1) + "," + listItems.get(2) + "," + listItems.get(3) + "," + listItems.get(4));
                        }
                        listItems.clear();
                    }
                }
                in.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return urlPath;
        }
    }

    //Gives user options to select which search to perform.
    public void getData() {
        if(position1 == 1){
            url = "http://sandbox.kriswelsh.com/hygieneapi/hygiene.php?op=search_location&lat=" + lat + "&long=" + lng;
            Activity2.RetrieveData retrieveData = new Activity2.RetrieveData();
            retrieveData.execute(url);
        }
        else if (2 == position1) {
            url = "http://sandbox.kriswelsh.com/hygieneapi/hygiene.php?op=search_name&name=";
            Activity2.RetrieveData retrieveData = new Activity2.RetrieveData();
            retrieveData.execute(url + entry);
        }
        else if (position1 == 3) {
            url = "http://sandbox.kriswelsh.com/hygieneapi/hygiene.php?op=search_postcode&postcode=";
            Activity2.RetrieveData retrieveData = new Activity2.RetrieveData();
            retrieveData.execute(url + entry);
        } else if (position1 == 4) {
            url = "http://sandbox.kriswelsh.com/hygieneapi/hygiene.php?op=show_recent";
            Activity2.RetrieveData retrieveData = new Activity2.RetrieveData();
            retrieveData.execute(url);
        } else {

        }
    }

    //Adds images instead of rating
    public void checkRating()
    {
        String exempt = listItems.get(5);
        int parse = Integer.parseInt(exempt);

        if(parse < 0 ){
            listItems.remove(5);
            listItems.add("Exempt");
            pos.add(FHRS[6]);
        }
        else if(parse == 0){
            listItems.remove(5);
            pos.add(FHRS[0]);
        }  else if(parse == 1){
            listItems.remove(5);
            pos.add(FHRS[1]);
        }  else if(parse == 2){
            listItems.remove(5);
            pos.add(FHRS[2]);
        }  else if(parse == 3){
            listItems.remove(5);
            pos.add(FHRS[3]);
        }  else if(parse == 4){
            listItems.remove(5);
            pos.add(FHRS[4]);
        }  else if(parse == 5){
            listItems.remove(5);
            pos.add(FHRS[5]);
        }
    }
    //Takes editText data and performs searches based on text.
    public void newsearch(){
        EditText text1 = (EditText) findViewById(R.id.editText);
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);

        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
        getData();
        entry = text1.getText().toString();
        text1.setText("");
        mapboxAdd();
    }
}
