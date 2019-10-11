package webersoftwaresolutions.contacts_nate_weber;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;

public class ContactDetailsActivity extends AppCompatActivity{

    TextView name, street, city, state, zip, email, phone;
    DatabaseHelper mDatabaseHelper;
    MapFragment mMapFragment;
    LatLng location;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_details);

        name = (TextView)findViewById(R.id.contact_name);
        street = (TextView)findViewById(R.id.contact_street);
        city = (TextView)findViewById(R.id.contact_city);
        state = (TextView)findViewById(R.id.contact_state);
        zip = (TextView)findViewById(R.id.contact_zip);
        email = (TextView)findViewById(R.id.contact_email);
        phone = (TextView)findViewById(R.id.contact_phone);

        id = getIntent().getExtras().getInt("id", 0);
        mDatabaseHelper = new DatabaseHelper(this);
        Cursor data = mDatabaseHelper.getData();
        if(data.moveToPosition(id)){
            name.setText(data.getString(0));
            street.setText(data.getString(1));
            city.setText(data.getString(2));
            state.setText(data.getString(3));
            zip.setText(data.getString(4));
            email.setText(data.getString(5));
            phone.setText(data.getString(6));
        }


        String address = street.getText().toString() +", "
                + city.getText().toString() + ", "
                + state.getText().toString() + ", "
                + zip.getText().toString();

        location = getLatLngFromAddress(address);
        GoogleMapOptions options = new GoogleMapOptions().camera(CameraPosition.fromLatLngZoom(new LatLng(location.latitude, location.longitude), 16))
                .compassEnabled(false).mapType(GoogleMap.MAP_TYPE_NORMAL).rotateGesturesEnabled(false).scrollGesturesEnabled(false).tiltGesturesEnabled(false)
                .zoomControlsEnabled(false).zoomGesturesEnabled(false);

        mMapFragment = MapFragment.newInstance(options);
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.fragment, mMapFragment);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        //Handle item selection
        GoogleMapOptions options;
        FragmentTransaction fragmentTransaction;
        if(item.getItemId() == R.id.normal){
            options = new GoogleMapOptions().camera(CameraPosition.fromLatLngZoom(new LatLng(location.latitude, location.longitude), 16))
                    .compassEnabled(false).mapType(GoogleMap.MAP_TYPE_NORMAL).rotateGesturesEnabled(false).scrollGesturesEnabled(false).tiltGesturesEnabled(false)
                    .zoomControlsEnabled(false).zoomGesturesEnabled(false);
            mMapFragment = MapFragment.newInstance(options);
            fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.fragment, mMapFragment);
            fragmentTransaction.commit();
            return true;
        } else if(item.getItemId() == R.id.hybrid) {
            options = new GoogleMapOptions().camera(CameraPosition.fromLatLngZoom(new LatLng(location.latitude, location.longitude), 16))
                    .compassEnabled(false).mapType(GoogleMap.MAP_TYPE_HYBRID).rotateGesturesEnabled(false).scrollGesturesEnabled(false).tiltGesturesEnabled(false)
                    .zoomControlsEnabled(false).zoomGesturesEnabled(false);
            mMapFragment = MapFragment.newInstance(options);
            fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.fragment, mMapFragment);
            fragmentTransaction.commit();
            return true;
        } else if(item.getItemId() == R.id.satellite) {
            options = new GoogleMapOptions().camera(CameraPosition.fromLatLngZoom(new LatLng(location.latitude, location.longitude), 16))
                    .compassEnabled(false).mapType(GoogleMap.MAP_TYPE_SATELLITE).rotateGesturesEnabled(false).scrollGesturesEnabled(false).tiltGesturesEnabled(false)
                    .zoomControlsEnabled(false).zoomGesturesEnabled(false);
            mMapFragment = MapFragment.newInstance(options);
            fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.fragment, mMapFragment);
            fragmentTransaction.commit();
            return true;
        } else if(item.getItemId() == R.id.terrain) {
            options = new GoogleMapOptions().camera(CameraPosition.fromLatLngZoom(new LatLng(location.latitude, location.longitude), 16))
                    .compassEnabled(false).mapType(GoogleMap.MAP_TYPE_TERRAIN).rotateGesturesEnabled(false).scrollGesturesEnabled(false).tiltGesturesEnabled(false)
                    .zoomControlsEnabled(false).zoomGesturesEnabled(false);
            mMapFragment = MapFragment.newInstance(options);
            fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.fragment, mMapFragment);
            fragmentTransaction.commit();
            return true;
        } else if(item.getItemId() == R.id.delete){
            //DELETE
            Intent i = new Intent(this, MainActivity.class);
            i.putExtra("delete_id", id);
            startActivity(i);

        }

        return true;
    }


    public LatLng getLatLngFromAddress(String strAddress) {
        Geocoder coder = new Geocoder(this);
        List<Address> address;

        try {
            address = coder.getFromLocationName(strAddress,5);
            if (address == null) {
                return null;
            }
            Address location=address.get(0);
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
            return latLng;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
