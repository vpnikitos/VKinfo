package com.example.vkinfo.utills;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {
    private static final String VK_API_BASE_URL = "https://api.vk.com";
    private static final String VK_USERS_GET = "/method/users.get";
    private static final String PARAM_USERS_ID = "user_ids";
    private static final String PARAM_VERSION ="v";


    public static URL generateURL(String userId) {
        Uri builtUri = Uri.parse(VK_API_BASE_URL +VK_USERS_GET)
                .buildUpon()
                .appendQueryParameter(PARAM_USERS_ID,userId)
                .appendQueryParameter(PARAM_VERSION, "5.8")
                .build();

        URL url = null;
        try {
            url= new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            throw new RuntimeException();
        }

        return url;
    }
    public static String getResponseFromURL(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();

            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }

        } finally {
            urlConnection.disconnect();
        }
    }
}
