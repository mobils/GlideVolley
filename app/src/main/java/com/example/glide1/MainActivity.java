package com.example.glide1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        /***** GLIDE: Descarrega la imatge de la URL i la posa a la IMAGEVIEW ****/
        ImageView image1 = findViewById(R.id.image1);

        String url0 = "https://img2.freepng.es/20180202/apq/kisspng-android-operating-system-application-software-android-5a7451873b4b23.7869329515175724872429.jpg";

        Glide
                .with(this)
                .load(url0)
                .fitCenter()
                .into(image1);


        /***** VOLLEY: Descarregar un json i escriure un text del json per pantalla en el TextView */

       TextView textView= findViewById(R.id.text1);
       TextView textViewNom= findViewById(R.id.textNom);

        String url2 = "https://run.mocky.io/v3/74f70c0c-f1dc-4424-8c86-d4879396d79d";
        // Instanciem la cua de peticions, RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);

        /* El jsonObjectRequest té com a paràmetres 2 listener, un que escolta la resposta y que pot
        retornar correcte llavors executa onResponse o un altre listener per si retorna error  i
        s'executa onErrorResponse
        */

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url2, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                textView.setText("Response JSON sencer: " + response.toString());

                //Segons l'estructura del Json el llegirem d'una forma o altra. Recomano debugar.
                JSONArray restaurantes = null;
                try {
                    restaurantes = response.getJSONArray("restaurantes");

                    for (int i=0;i<restaurantes.length();i++){  //Concateno els noms de tots els restaurants
                        textViewNom.setText( textViewNom.getText() + ", " +
                                restaurantes.getJSONObject(i).getString("nombre"));

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                textView.setText("Error descarregant dades!");
            }
        });

        queue.add(jsonObjectRequest);  //Finalment la petició (JsonObjectRequest)  l'afegim a la cua





    }
}