package com.example.vkinfo;

import static com.example.vkinfo.utills.NetworkUtils.generateURL;
import static com.example.vkinfo.utills.NetworkUtils.getResponseFromURL;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private EditText searchField;
    private Button searchButton;
    private TextView result;

    class VKQueryTask extends AsyncTask<URL, Void, String> {
        @Override
        protected String doInBackground(URL... urls) {
            String response = null;

            try {
                response = getResponseFromURL(urls[0]);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return response;

        }

        @Override
        protected void onPostExecute(String response){
            String firstName = null;
            String lastName = null;
            try {
                JSONObject jsonResponse = new JSONObject(response);
                JSONArray jsonArray = jsonResponse.getJSONArray("response");
                JSONObject userInfo = jsonArray.getJSONObject(0);
                firstName = userInfo.getString("firstname");
                lastName = userInfo.getString("lastname");

            } catch (JSONException e) {
                e.printStackTrace();
            }
            String resultingString = "Имя: " + firstName + "\n" + "Фамилия: " + lastName;
            result.setText(resultingString);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchField = findViewById(R.id.et_search_field);
        searchButton = findViewById(R.id.b_search_vk);
        result = findViewById(R.id.tv_result);

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            URL generatedURL = generateURL(searchField.getText().toString());

                new VKQueryTask().execute(generatedURL);

            }
        };

        searchButton.setOnClickListener(onClickListener);
    }
}