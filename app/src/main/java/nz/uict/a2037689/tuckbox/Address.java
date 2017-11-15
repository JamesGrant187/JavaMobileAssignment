package nz.uict.a2037689.tuckbox;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.Arrays;
import java.util.List;

import static nz.uict.a2037689.tuckbox.R.id.place_autocomplete_fragment;



public class Address extends AppCompatActivity {

    public String TAG = "AddressAutoWidget";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);


        //Create the Fragment and link it to the xml entry.
        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(place_autocomplete_fragment);
        //Set filters to NZ/Regions only
        AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
                .setCountry("NZ")
                .setTypeFilter(AutocompleteFilter.TYPE_FILTER_NONE)
                .build();
        autocompleteFragment.setFilter(typeFilter);


        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {


                List<String> regions = Arrays.asList(getResources().getStringArray(R.array.locations));
                int regionIndex = -1;
                for (String region : regions)
                    if (place.getAddress().toString().toLowerCase().contains(region.toLowerCase()))
                        regionIndex = regions.indexOf(region);
                if (regionIndex != -1)

                Log.i(TAG, "Place: " + place.getName());

                UserManager.user.setOrderLocationStreet(place.getAddress().toString());


                Toast.makeText(Address.this, UserManager.user.getOrderLocationStreet(),
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Status status) {
                Log.i(TAG, "An error occurred: " + status);
            }
        });
    }

    public void continueClick(View view) {


        for (String loc : getResources().getStringArray(R.array.locations)) {
            if (UserManager.user.getOrderLocationStreet().contains(loc)) {
                Intent intent = new Intent(this, OrderList.class);
                startActivity(intent);
                return;
            }

        }
        Toast.makeText(Address.this, "Sorry, we do not deliver to that area!",
                Toast.LENGTH_SHORT).show();
    }
}
