package com.herprogramacion.guadehotelesenroma;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Actividad que muestra el parsing XML en una lista
 */
public class ActividadPrincipal extends AppCompatActivity
        implements AdaptadorDeHoteles.OnItemClickListener {
    private RecyclerView reciclador;
    private LinearLayoutManager linearManager;
    private AdaptadorDeHoteles adaptador;

    private final static String URL =
            "http://ejemplos.hermosaprogramacion.com/hoteles-roma/hoteles.xml";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_principal);

        usarToolbar();

        reciclador = (RecyclerView) findViewById(R.id.reciclador);
        reciclador.setHasFixedSize(true);
        linearManager = new LinearLayoutManager(this);
        reciclador.setLayoutManager(linearManager);

        adaptador = new AdaptadorDeHoteles();
        adaptador.setHasStableIds(true);
        adaptador.setOnItemClickListener(this);

        reciclador.setAdapter(adaptador);

        new TareaDescargaXml().execute(URL);

    }

    private void usarToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal, menu);
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

    @Override
    public void onItemClick(AdaptadorDeHoteles.ViewHolder item, int position) {
        Intent intent = new Intent(this, ActividadDetalle.class);
        int itemId = (int) item.getItemId();
        intent.putExtra(ActividadDetalle.EXTRA_ID, itemId);
        startActivity(intent);
    }

    private class TareaDescargaXml extends AsyncTask<String, Void, List<Hotel>> {

        @Override
        protected List<Hotel> doInBackground(String... urls) {
            try {
                return parsearXmlDeUrl(urls[0]);
            } catch (IOException e) {
                return null; // null si hay error de red
            } catch (XmlPullParserException e) {
                return null; // null si hay error de parsing XML
            }
        }

        @Override
        protected void onPostExecute(List<Hotel> result) {
            // Actualizar contenido del proveedor de datos
            Hotel.HOTELES = result;
            // Actualizar la vista del adaptador
            adaptador.notifyDataSetChanged();
        }
    }

    private List<Hotel> parsearXmlDeUrl(String urlString)
            throws XmlPullParserException, IOException {
        InputStream stream = null;
        ParserXml parserXml = new ParserXml();
        List<Hotel> entries = null;

        try {
            stream = descargarContenido(urlString);
            entries = parserXml.parsear(stream);

        } finally {
            if (stream != null) {
                stream.close();
            }
        }

        return entries;
    }

    private InputStream descargarContenido(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(10000);
        conn.setConnectTimeout(15000);
        conn.setRequestMethod("GET");
        conn.setDoInput(true);
        // Iniciar la petición
        conn.connect();
        return conn.getInputStream();
    }
}
