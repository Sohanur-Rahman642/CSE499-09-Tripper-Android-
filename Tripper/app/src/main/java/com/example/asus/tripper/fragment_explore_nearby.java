package com.example.asus.tripper;





import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.IntentSender;

import android.location.Location;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;


import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;

import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.AutocompletePrediction;
import com.google.android.libraries.places.api.model.AutocompleteSessionToken;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.FetchPlaceResponse;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsResponse;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.mancj.materialsearchbar.adapter.SuggestionsAdapter;
import com.skyfishjy.library.RippleBackground;





import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//Bottom Sheet imports

import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.INPUT_METHOD_SERVICE;


public class fragment_explore_nearby extends Fragment implements OnMapReadyCallback {


    private SupportMapFragment mapFragment;

    View rootView;


    private GoogleMap mGoogleMap;


    private FusedLocationProviderClient mFusedLocationProviderClient;
    private PlacesClient placesClient;

    //AutoCompletePrediction instance
    private List<AutocompletePrediction> predictionList;
    private Location mLastKnownLocation;

    private LocationCallback locationCallback;

    private MaterialSearchBar materialSearchBar;
    private View mapView;
    private Button btnFind;

    private final float DEFAULT_ZOOM = 20;

    private RippleBackground rippleBackground;


    //Bottom Sheet instances
    @BindView(R.id.textview_explore)
    TextView textView_explorer;

    @SuppressLint("ResourceType")
    @BindView(R.id.layout_explore_nearby)
    ConstraintLayout layout_explore_nearby;

    BottomSheetBehavior bottomSheetBehavior;










/*

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);


    }
*/


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        getActivity().getWindow().setFlags(WindowManager.LayoutParams
                .FLAG_FULLSCREEN, WindowManager.LayoutParams
                .FLAG_FULLSCREEN);


        rootView = inflater.inflate(R.layout.fragment_explore_nearby, container, false);

        materialSearchBar = rootView.findViewById(R.id.searchBar);

        rippleBackground = rootView.findViewById(R.id.ripple_bg);


        //Bottom Sheet

        ButterKnife.bind(getActivity());

        bottomSheetBehavior = BottomSheetBehavior.from(rootView.findViewById(R.id.layout_explore_nearby));


        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_HIDDEN:
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED:
                        break;
                    case BottomSheetBehavior.STATE_COLLAPSED:
                        break;
                    case BottomSheetBehavior.STATE_DRAGGING:
                        break;
                    case BottomSheetBehavior.STATE_SETTLING:
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });


        textView_explorer = (TextView) rootView.findViewById(R.id.textview_explore);
        textView_explorer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bottomSheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

                } else {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

                }
            }
        });


        final SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mapView = mapFragment.getView();


        mFusedLocationProviderClient = LocationServices
                .getFusedLocationProviderClient(getContext());

        Places.initialize(getContext(),
                "AIzaSyDqBxICSh107RYJe6xLH_6qux4eGngE0as");
        final PlacesClient placesClient = Places.createClient(getContext());

        final AutocompleteSessionToken token = AutocompleteSessionToken.newInstance();


        //material searchbar methods

        materialSearchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {

            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                getActivity().startSearch(text.toString(), true, null, true);
            }

            @Override
            public void onButtonClicked(int buttonCode) {

                if (buttonCode == MaterialSearchBar.BUTTON_NAVIGATION) {

                    //opening or closing drawer layout

                } else if (buttonCode == MaterialSearchBar.BUTTON_BACK) {
                    materialSearchBar.disableSearch();
                }


            }
        });


        //PlacesClient code


        materialSearchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                final FindAutocompletePredictionsRequest predictionsRequest =
                        FindAutocompletePredictionsRequest.builder()
                                .setCountry("BD")
                                .setTypeFilter(TypeFilter.CITIES)
                                .setSessionToken(token)
                                .setQuery(s.toString())
                                .build();

                placesClient.findAutocompletePredictions(predictionsRequest)
                        .addOnCompleteListener(new OnCompleteListener<FindAutocompletePredictionsResponse>() {
                            @Override
                            public void onComplete(@NonNull Task<FindAutocompletePredictionsResponse> task) {

                                if (task.isSuccessful()) {
                                    FindAutocompletePredictionsResponse predictionsResponse = task.getResult();
                                    if (predictionsResponse != null) {
                                        predictionList = predictionsResponse.getAutocompletePredictions();
                                        List<String> suggestionsList = new ArrayList<>();
                                        for (int i = 0; i < predictionList.size(); i++) {
                                            AutocompletePrediction prediction = predictionList.get(i);
                                            suggestionsList.add(prediction.getFullText(null).toString());
                                        }
                                        materialSearchBar.updateLastSuggestions(suggestionsList);
                                        if (!materialSearchBar.isSuggestionsVisible()) {
                                            materialSearchBar.showSuggestionsList();
                                        }
                                    }
                                } else {
                                    Log.i("mytag", "prediction fetching task unsuccessful");
                                }

                            }
                        });

            }

            @Override
            public void afterTextChanged(Editable s) {

            }

        });


        // Set Suggestions code
        materialSearchBar.setSuggestionsClickListener(new SuggestionsAdapter.OnItemViewClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
            @Override
            public void OnItemClickListener(int position, View v) {
                if (position >= predictionList.size()) {
                    return;
                }

                AutocompletePrediction selectedPrediction = predictionList.get(position);
                String suggestion = materialSearchBar
                        .getLastSuggestions().get(position).toString();
                materialSearchBar.setText(suggestion);

                //Clear suggestions after clicking
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        materialSearchBar.clearSuggestions();
                    }
                }, 1000);


                InputMethodManager inputMethodManager =
                        (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);

                if (inputMethodManager != null) {
                    inputMethodManager.hideSoftInputFromWindow(materialSearchBar.getWindowToken()
                            , InputMethodManager.HIDE_IMPLICIT_ONLY);
                    final String placeId = selectedPrediction.getPlaceId();
                    List<Place.Field> placeFields = Arrays.asList(Place.Field.LAT_LNG);

                    FetchPlaceRequest fetchPlaceRequest = FetchPlaceRequest.builder(placeId, placeFields).build();
                    placesClient.fetchPlace(fetchPlaceRequest).addOnSuccessListener(new OnSuccessListener<FetchPlaceResponse>() {
                        @Override
                        public void onSuccess(FetchPlaceResponse fetchPlaceResponse) {
                            Place place = fetchPlaceResponse.getPlace();
                            Log.i("mytag", "Place found: " + place.getName());
                            LatLng latLngOfPlace = place.getLatLng();
                            if (latLngOfPlace != null) {
                                mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLngOfPlace, DEFAULT_ZOOM));
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            if (e instanceof ApiException) {
                                ApiException apiException = (ApiException) e;
                                apiException.printStackTrace();
                                int statusCode = apiException.getStatusCode();
                                Log.i("mytag", "place not found: " + e.getMessage());
                                Log.i("mytag", "status code: " + statusCode);
                            }
                        }
                    });
                }
            }

            @Override
            public void OnItemDeleteListener(int position, View v) {

            }
        });


        return rootView;
    }


    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {


        MapsInitializer.initialize(getContext());
        mGoogleMap = googleMap;

        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        /*mGoogleMap.getUiSettings().setZoomControlsEnabled(true);*/
        mGoogleMap.getUiSettings().setZoomGesturesEnabled(true);
        mGoogleMap.getUiSettings().setCompassEnabled(true);

        mGoogleMap.getUiSettings().setMyLocationButtonEnabled(true);


        mGoogleMap.setMyLocationEnabled(true);
        mGoogleMap.getUiSettings().setMyLocationButtonEnabled(true);


        if (mapView != null &&
                mapView.findViewById(Integer.parseInt("1")) != null) {
            // Get the button view
            View locationButton = ((View) mapView.findViewById(Integer.parseInt("1"))
                    .getParent()).findViewById(Integer.parseInt("2"));
            // and next place it, on bottom right (as Google Maps app)
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)
                    locationButton.getLayoutParams();
            // position on right bottom
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
            layoutParams.setMargins(0, 0, 40, 280);

        }

        //check if gps is enabled or not and then request user to enable it
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);


        LocationSettingsRequest.Builder builder = new LocationSettingsRequest
                .Builder().addLocationRequest(locationRequest);

        SettingsClient settingsClient = LocationServices.getSettingsClient(getContext());
        Task<LocationSettingsResponse> task = settingsClient.checkLocationSettings(builder.build());

        //two task function, first 1 will run when gps is enabled & second 1 will run when isn't enabled
        task.addOnSuccessListener(getActivity(), new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {

                getDeviceLocation();

            }
        });

        task.addOnFailureListener(getActivity(), new OnFailureListener() {
            @RequiresApi(api = Build.VERSION_CODES.DONUT)
            @Override
            public void onFailure(@NonNull Exception e) {

                if (e instanceof ResolvableApiException) {
                    ResolvableApiException resolvableApiException = (ResolvableApiException) e;
                    try {
                        resolvableApiException.startResolutionForResult(getActivity(), 51);
                    } catch (IntentSender.SendIntentException e1) {
                        e1.printStackTrace();
                    }
                }

            }
        });

        mGoogleMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {
                if (materialSearchBar.isSuggestionsVisible()) {
                    materialSearchBar.clearSuggestions();
                }
                if (materialSearchBar.isSearchEnabled()) {
                    materialSearchBar.disableSearch();
                }
                return false;
            }
        });


    }

    // onActivityResult method
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 51) {
            //checking gps enabled or not
            if (resultCode == RESULT_OK) {

                getDeviceLocation();

            }
        }
    }


    //getDeviceLocation() method

    @SuppressLint("MissingPermission")
    private void getDeviceLocation() {

        mFusedLocationProviderClient.getLastLocation()
                .addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {

                        if (task.isSuccessful()) {
                            mLastKnownLocation = task.getResult();
                            if (mLastKnownLocation != null) {
                                mGoogleMap.moveCamera(CameraUpdateFactory
                                        .newLatLngZoom(new LatLng(mLastKnownLocation.getLatitude()
                                                , mLastKnownLocation.getLongitude()), DEFAULT_ZOOM));
                            } else {
                                final LocationRequest locationRequest = LocationRequest.create();
                                locationRequest.setInterval(10000);
                                locationRequest.setFastestInterval(5000);
                                locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                                locationCallback = new LocationCallback() {

                                    @Override
                                    public void onLocationResult(LocationResult locationResult) {
                                        super.onLocationResult(locationResult);

                                        if (locationResult == null) {
                                            return;
                                        }
                                        mLastKnownLocation = locationResult.getLastLocation();
                                        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                                new LatLng(mLastKnownLocation.getLatitude()
                                                        , mLastKnownLocation.getLongitude()), DEFAULT_ZOOM));
                                        mFusedLocationProviderClient.removeLocationUpdates(locationCallback);

                                    }
                                };

                                mFusedLocationProviderClient
                                        .requestLocationUpdates(locationRequest, locationCallback, null);

                            }
                        } else {
                            Toast.makeText(getContext(),
                                    "unable to get last location", Toast.LENGTH_LONG).show();
                        }

                    }
                });

    }

}

