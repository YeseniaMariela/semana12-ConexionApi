package com.example.myapplication.Models;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.myapplication.ListProductActivity;
import com.example.myapplication.helpers.QueueUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Products {
    public String name;
    public String description;

    public Products (String _name, String _desc){
        this.name = _name;
        this.description = _desc;
    }

    public String toString () {
        return this.name;
    }

    public static ArrayList<Products>  getData () {
        ArrayList<Products> products = new ArrayList<>();
        products.add(new Products ("Galletitas",
        "Galleta deliciosas"));

        products.add(new Products ("chocolate",
                "Chocolate la iberica"));

        products.add(new Products ("paneton",
                "paneton con pasas"));

        products.add(new Products ("Galletas",
                "galletas de trigo"));

        return products;
    }

    public static void sendRequestPOST(QueueUtils.QueueObject o, final ListProductActivity _interface) {
        String url = "http://rrojasen.alwaysdata.net/purchaseorders.json";
        url = "http://fipo.equisd.com/api/users/new.json";
//        url = "http://192.168.58.3:8056/api/users/new.json";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //Do it with this it will work
                            JSONObject _response = new JSONObject(response);
                            if (_response.has("object")) {
                                JSONObject object_response = null;
                                try {
                                    object_response = _response.getJSONObject("object");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                if ( object_response != null ) {
                                    try {
                                        System.out.println(object_response.getInt("id"));
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("first_name","Yesenia Mariela");
                params.put("last_name","Quispe Aguilar");
                params.put("avatar","yes");

                return params;
            }
        };
        o.addToRequestQueue(stringRequest);
    }




    public static void injectContactsFromCloud(final QueueUtils.QueueObject o,
                                               final ArrayList<Products> Products,
                                               final ListProductActivity _interface) {
        String url = "http://fipo.equisd.com/api/users.json";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        if (response.has("objects")) {

                            try {
                                JSONArray list = response.getJSONArray("objects");
                                for (int i=0; i < list.length(); i++) {
                                    JSONObject o = list.getJSONObject(i);
                                    Products.add(new Products(o.getString("first_name"),
                                            o.getString("last_name")));
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            _interface.refreshList(); // Esta función debemos implementarla
                            // en nuestro activity
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        o.addToRequestQueue(jsonObjectRequest);
    }

}
