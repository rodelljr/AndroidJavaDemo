package com.java.demo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.java.interfaces.GetDataListener;
import com.java.models.LocationModel;
import com.java.models.ResponseModel;
import com.java.network.StationApiCall;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements GetDataListener {

    private double mLat, mLon;
    private AppCompatSpinner mSpinner;
    private List<LocationModel> model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSpinner = findViewById(R.id.sp_drop);
        setupSpinner();
    }

    private void setupSpinner() {
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, myTestModel());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);
        setupSpinnerSelected();
    }

    private void setupSpinnerSelected() {
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mLat = model.get(i).getLat();
                mLon = model.get(i).getLon();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private List<LocationModel> myTestModel() {
        model = new ArrayList<>();
        model.add(new LocationModel("Arrowhead Stadium", 39.0527456, -94.490726));
        model.add(new LocationModel("Kauffman Stadium",39.0489432, -94.4861044));
        model.add(new LocationModel("Children\'s Mercy Park",39.121597, -94.8253654));
        model.add(new LocationModel("Sprint Center",39.0974708, -94.5823416));
        return model;
    }

    public void onLocateClick(View view) {
        makeOkHttpCall();
    }

    @Override
    public void onApiReturn(String result) {
        ArrayList<ResponseModel> model = getStationModel(result);
        callFragment(model);
    }

    private void callFragment(ArrayList<ResponseModel> models) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        MainFragment frag = MainFragment.newInstance(models);
        transaction.replace(R.id.fl_layout, frag).commit();
    }

    /**
     * Method for beginning API call by calling the Async Task that it is in
     * A test url is provided for use during mock up of Location services.
     * **/
    private void makeOkHttpCall() {
        String myUrl = getAPIUrl(Double.toString(mLat), Double.toString(mLon));
        StationApiCall client = new StationApiCall(this,this);
        client.execute(myUrl);
    }


    private String getAPIUrl(String lat, String lon) {
        StringBuilder builder = new StringBuilder();
        builder.append("http://api.open-notify.org/iss-pass.json?lat=")
                .append(lat).append("&lon=").append(lon);
        return builder.toString();
    }

    /**
     * Method for taking the API result and parsing it into a working model.
     * **/
    private ArrayList<ResponseModel> getStationModel(String model) {
        ArrayList<ResponseModel> myModelList = new ArrayList<>();
        Gson gson = new Gson();
        try {
            JSONObject object = new JSONObject(model);
            JSONArray response = object.getJSONArray("response");
            InputStream inputStream = new ByteArrayInputStream(response.toString().getBytes("UTF-8"));
            Reader reader = new InputStreamReader(inputStream);
            Type listType = new TypeToken<ArrayList<ResponseModel>>() {}.getType();
            myModelList = gson.fromJson(reader, listType);
            return myModelList;
        } catch (UnsupportedEncodingException ue) {
            Log.e("Gson Encode Ex",ue.getMessage());
            return myModelList;
        } catch (Exception ex) {
            Log.e("Gson Ex",ex.getMessage());
            return myModelList;
        }
    }

}
