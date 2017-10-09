package com.example.androidthings.myproject;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

/**
 * Created by bjoern on 10/9/17.
 * see https://developer.android.com/training/volley/simple.html
 */

public class HttpTestApp extends SimplePicoPro {
    @Override
    public void setup() {

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        String url = "http://bjo3rn.github.io/idd-examples/hello.txt";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        println("Response is: \n" + response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        println("That didn't work!");
                    }
                });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    @Override
    public void loop() {

    }
}
