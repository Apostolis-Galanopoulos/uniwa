package android.school_work.com.work.Helper;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;


public class WebServices {

    public static void callJsonResponse(final String fullUrl, final JsonResponse callbackSuccess, final ErrorResponse callbackError) {

        JsonArrayRequest strReq = new JsonArrayRequest(
                Request.Method.GET,
                fullUrl,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        if (response.length() > 0) {
                            callbackSuccess.onSuccess(response);
                        } else {
                            callbackError.onError(false);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callbackError.onError(false);
            }
        });
//        Log.i("strReq ", String.valueOf(strReq));
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq);
    }

    public interface JsonResponse {
        void onSuccess(JSONArray data);
    }

    public interface ErrorResponse {
        void onError(boolean state);
    }


}
