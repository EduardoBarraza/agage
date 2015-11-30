package com.herprogramacion.guadehotelesenroma;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class ActividadDetalle extends AppCompatActivity {

    public static final String EXTRA_ID = "com.herprogramacion.guadehotelesenroma.extra.ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_detalle);

        usarToolbar();

        // Extraer ID
        Intent intent = getIntent();
        int idHotel = intent.getIntExtra(EXTRA_ID, 0);
        Hotel hotelActual = Hotel.getItem(idHotel);

        // Obtener instancias de cada view
        TextView nombre = (TextView) findViewById(R.id.nombreTextView);
        TextView precio = (TextView) findViewById(R.id.precioTextView);
        RatingBar calificacion = (RatingBar) findViewById(R.id.calificacion);
        TextView noOpiniones = (TextView) findViewById(R.id.noOpinionesTextView);
        TextView descripcion = (TextView) findViewById(R.id.descripcionTextView);
        ImageView imagen = (ImageView) findViewById(R.id.hotelImageView);

        // Añadir valores del hotel
        assert hotelActual != null;
        nombre.setText(hotelActual.getNombre());
        precio.setText("$" + hotelActual.getPrecio());
        calificacion.setRating(hotelActual.getCalificacion());
        noOpiniones.setText(hotelActual.getNoOpiniones() + " Opiniones");
        descripcion.setText(hotelActual.getDescripcion());
        Glide.with(this)
                .load(hotelActual.getUrlImagen())
                .into(imagen);

    }

    private void usarToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actividad_detalle, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
