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
            result.setText(response);
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