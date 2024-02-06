package com.example.firstappandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import Configuracion.SQLiteConexion;
import Configuracion.Transacciones;

public class MainActivity extends AppCompatActivity {

    EditText inputNombres, inputApellidos, inputEdad, inputCorreo, inputDireccion;
    Button enviar, btnActualizar, btnEliminar;

    // Obtiene la información de la persona seleccionada del Intent
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        int id = intent.getIntExtra("id", -1);

        inputNombres = findViewById(R.id.txtNombre);
        inputApellidos = findViewById(R.id.txtApellido);
        inputEdad = findViewById(R.id.txtEdad);
        inputCorreo = findViewById(R.id.txtEmail);
        inputDireccion = findViewById(R.id.txtDireccion);
        enviar = findViewById(R.id.btnEnviar);
        btnActualizar = findViewById(R.id.btnActualizar);
        btnEliminar = findViewById(R.id.btnEliminar);

        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddPerson();
            }
        });

        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdatePerson();
            }
        });

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeletePerson();
            }
        });

        if (id > 0) {
            // Mostrar botones de actualizar y eliminar
            enviar.setVisibility(View.GONE);
            btnActualizar.setVisibility(View.VISIBLE);
            btnEliminar.setVisibility(View.VISIBLE);

            // Llena los EditText con la información
            String nombresSeleccionados = intent.getStringExtra("nombres");
            String apellidosSeleccionados = intent.getStringExtra("apellidos");
            int edadSeleccionada = intent.getIntExtra("edad", -1);
            String correoSeleccionado = intent.getStringExtra("correo");
            String direccionSeleccionada = intent.getStringExtra("direccion");

            inputNombres.setText(nombresSeleccionados);
            inputApellidos.setText(apellidosSeleccionados);
            inputEdad.setText(String.valueOf(edadSeleccionada));
            inputCorreo.setText(correoSeleccionado);
            inputDireccion.setText(direccionSeleccionada);
        } else {
            // Mostrar botón de guardar
            enviar.setVisibility(View.VISIBLE);
            btnActualizar.setVisibility(View.GONE);
            btnEliminar.setVisibility(View.GONE);
        }
    }

    private void DeletePerson() {
        SQLiteConexion conexion = new SQLiteConexion(this, Transacciones.DBName, null, 1);
        SQLiteDatabase db = conexion.getWritableDatabase();

        // Obtiene el ID de la persona seleccionada del Intent
        Intent intent = getIntent();
        int id = intent.getIntExtra("id", -1);

        if (id > 0) {
            // Elimina la persona de la base de datos
            long resultado = db.delete(Transacciones.TablePersonas, Transacciones.id + "=?", new String[]{String.valueOf(id)});

            if (resultado > 0) {
                Toast.makeText(getApplicationContext(), "Registro eliminado correctamente", Toast.LENGTH_LONG).show();

                // Redirige a ActivityList y limpia las otras actividades en la pila
                Intent intentActivityList = new Intent(this, ActivityList.class);
                intentActivityList.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intentActivityList);
                finish();  // Cierra la actividad actual
            } else {
                Toast.makeText(getApplicationContext(), "Error al eliminar el registro", Toast.LENGTH_LONG).show();
            }
        }

        // Cierra la conexión a la base de datos
        db.close();
    }

    private void UpdatePerson() {
        SQLiteConexion conexion = new SQLiteConexion(this, Transacciones.DBName, null, 1);
        SQLiteDatabase db = conexion.getWritableDatabase();

        Intent intent = getIntent();
        int id = intent.getIntExtra("id", -1);

        ContentValues valores = new ContentValues();
        valores.put(Transacciones.nombres, inputNombres.getText().toString());
        valores.put(Transacciones.apellidos, inputApellidos.getText().toString());

        try {
            int edad = Integer.parseInt(inputEdad.getText().toString());
            valores.put(Transacciones.edad, edad);
        } catch (NumberFormatException e) {
            valores.put(Transacciones.edad, 0);
        }

        valores.put(Transacciones.correo, inputCorreo.getText().toString());
        valores.put(Transacciones.direccion, inputDireccion.getText().toString());

        // Actualiza los datos en la base de datos
        long resultado = db.update(Transacciones.TablePersonas, valores, Transacciones.id + "=?", new String[]{String.valueOf(id)});

        if (resultado > 0) {
            Toast.makeText(getApplicationContext(), "Registro actualizado correctamente", Toast.LENGTH_LONG).show();

            // Redirige a ActivityList y limpia las otras actividades en la pila
            Intent intentActivityList = new Intent(this, ActivityList.class);
            intentActivityList.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intentActivityList);
            finish();  // Cierra la actividad actual
        } else {
            Toast.makeText(getApplicationContext(), "Error al actualizar el registro", Toast.LENGTH_LONG).show();
        }

        // Cierra la conexión a la base de datos
        db.close();
    }

    private void AddPerson() {
        SQLiteConexion conexion = new SQLiteConexion(this, Transacciones.DBName, null, 1);
        SQLiteDatabase db = conexion.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put(Transacciones.nombres, inputNombres.getText().toString());
        valores.put(Transacciones.apellidos, inputApellidos.getText().toString());

        // Convierte la cadena de edad a un entero
        try {
            int edad = Integer.parseInt(inputEdad.getText().toString());
            valores.put(Transacciones.edad, edad);
        } catch (NumberFormatException e) {
            // Manejar la excepción si la cadena no es un número
            valores.put(Transacciones.edad, 0); // O un valor por defecto
        }

        valores.put(Transacciones.correo, inputCorreo.getText().toString());
        valores.put(Transacciones.direccion, inputDireccion.getText().toString());

        long resultado = db.insert(Transacciones.TablePersonas, Transacciones.id, valores);

        if (resultado > 0) {
            Toast.makeText(getApplicationContext(), "Registro insertado correctamente", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(), "Error al insertar el registro", Toast.LENGTH_LONG).show();
        }

        // Cierra la conexión a la base de datos
        db.close();

        // Limpia los campos después de la inserción
        inputNombres.setText("");
        inputApellidos.setText("");
        inputEdad.setText("");
        inputCorreo.setText("");
        inputDireccion.setText("");
    }
}