package com.example.firstappandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import Configuracion.SQLiteConexion;
import Configuracion.Transacciones;

public class MainActivity extends AppCompatActivity {

    EditText nombres, apellido, edad, correo;
    Button enviar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nombres = findViewById(R.id.txtNombre);
        apellido = findViewById(R.id.txtApellido);
        edad = findViewById(R.id.txtEdad);
        correo = findViewById(R.id.txtEmail);
        enviar = findViewById(R.id.btnEnviar);

        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddPerson();
            }
        });
    }

    private void AddPerson() {
        SQLiteConexion conexion = new SQLiteConexion(this, Transacciones.DBName, null, 1);
        SQLiteDatabase db = conexion.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put(Transacciones.nombres, nombres.getText().toString());
        valores.put(Transacciones.apellidos, apellido.getText().toString());
        valores.put(Transacciones.edad, edad.getText().toString());
        valores.put(Transacciones.correo, correo.getText().toString());

        long resultado = db.insert(Transacciones.TablePersonas, Transacciones.id, valores);

        if (resultado > 0) {
            Toast.makeText(getApplicationContext(), "Registro insertado correctamente", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(), "Error al insertar el registro", Toast.LENGTH_LONG).show();
        }

        // Cierra la conexión a la base de datos
        db.close();

        // Limpia los campos después de la inserción
        nombres.setText("");
        apellido.setText("");
        edad.setText("");
        correo.setText("");
    }
}