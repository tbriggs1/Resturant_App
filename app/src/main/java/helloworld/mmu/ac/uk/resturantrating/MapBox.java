package helloworld.mmu.ac.uk.resturantrating;

import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.maps.MapView;

public class MapBox extends AppCompatActivity {

    private MapView mapview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, getString(R.string.access_token));
        setContentView(R.layout.activity_map_box);
        mapview = (MapView) findViewById(R.id.mapView);
        mapview.onCreate(savedInstanceState);
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

}
