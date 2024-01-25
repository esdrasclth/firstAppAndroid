package com.example.firstappandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class ActivityWelcome extends AppCompatActivity {

    TextView nombre, apellido, telefono;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        nombre = findViewById(R.id.lblNombre);
        apellido = findViewById(R.id.lblApellido);
        telefono = findViewById(R.id.lblTelefono);

        Bundle recibirDatos = getIntent().getExtras();

        if (recibirDatos != null) {
            String nombreInfo = recibirDatos.getString("keyNombre");
            String apellidoInfo = recibirDatos.getString("keyApellido");
            String telefonoInfo = recibirDatos.getString("keyTelefono");

            nombre.setText(nombreInfo);
            apellido.setText(apellidoInfo);
            telefono.setText(telefonoInfo);
        }
    }
}