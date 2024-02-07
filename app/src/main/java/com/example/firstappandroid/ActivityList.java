package com.example.firstappandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;

import Configuracion.SQLiteConexion;
import Configuracion.Transacciones;
import Models.Personas;

public class ActivityList extends AppCompatActivity {

    SQLiteConexion conexion;

    ListView listPersona;

    ArrayList<Personas> lista;

    ArrayList<String> Arreglo;

    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        // Inicialización del SearchView
        searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Filtra los resultados según la consulta ingresada por el usuario
                ArrayList<String> resultados = new ArrayList<>();
                for (String item : Arreglo) {
                    if (item.toLowerCase().contains(query.toLowerCase())) {
                        resultados.add(item);
                    }
                }

                // Actualiza el adaptador del ListView con los resultados de la búsqueda
                ArrayAdapter<String> adp = new ArrayAdapter<>(ActivityList.this, android.R.layout.simple_list_item_activated_1, resultados);
                listPersona.setAdapter(adp);

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Filtra los resultados en tiempo real mientras el usuario escribe en el SearchView
                ArrayList<String> resultados = new ArrayList<>();
                for (String item : Arreglo) {
                    if (item.toLowerCase().contains(newText.toLowerCase())) {
                        resultados.add(item);
                    }
                }

                // Actualiza el adaptador del ListView con los resultados filtrados
                ArrayAdapter<String> adp = new ArrayAdapter<>(ActivityList.this, android.R.layout.simple_list_item_activated_1, resultados);
                listPersona.setAdapter(adp);

                return true;
            }
        });

        // Inicialización de la conexión SQLite
        conexion = new SQLiteConexion(this, Transacciones.DBName, null, 1);
        listPersona = (ListView) findViewById(R.id.idListPersona);

        ObtenerDatos();

        ArrayAdapter<String> adp = new ArrayAdapter<>(this, android.R.layout.simple_list_item_activated_1, Arreglo);
        listPersona.setAdapter(adp);

        // Configura el clic de los elementos en el ListView
        listPersona.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Obtiene la persona seleccionada
                Personas personaSeleccionada = lista.get(position);

                // Crea un Intent para abrir la actividad de edición
                Intent intent = new Intent(ActivityList.this, MainActivity.class);

                // Pasa la información de la persona seleccionada a la actividad de edición
                intent.putExtra("id", personaSeleccionada.getId());
                intent.putExtra("nombres", personaSeleccionada.getNombres());
                intent.putExtra("apellidos", personaSeleccionada.getApellidos());
                intent.putExtra("edad", personaSeleccionada.getEdad());
                intent.putExtra("correo", personaSeleccionada.getCorreo());
                intent.putExtra("direccion", personaSeleccionada.getDireccion());

                // Inicia la actividad de edición
                startActivity(intent);
            }
        });
    }


    private void ObtenerDatos() {
        SQLiteDatabase db = conexion.getReadableDatabase();
        Personas person = null;
        lista = new ArrayList<Personas>();

        //Creando cursor de base de datos para recorrer los datos.
        Cursor cursor = db.rawQuery(Transacciones.SelectAllPersonas, null);

        while (cursor.moveToNext()) {
            person = new Personas();
            person.setId(cursor.getInt(0));
            person.setNombres(cursor.getString(1));
            person.setApellidos(cursor.getString(2));
            person.setEdad(cursor.getInt(3));
            person.setCorreo(cursor.getString(4));
            person.setDireccion(cursor.getString(5));

            lista.add(person);
        }

        cursor.close();

        FillData();
    }
    private void FillData() {
        Arreglo = new ArrayList<String>();
        for (int i = 0; i < lista.size(); i++) {
            Personas persona = lista.get(i);
            String datosPersona = persona.getNombres() + " " + persona.getApellidos() + "\n"
                    + "Edad: " + persona.getEdad() + "\n"
                    + "Correo: " + persona.getCorreo() + "\n"
                    + "Dirección: " + persona.getDireccion();
            Arreglo.add(datosPersona);
        }
    }
}