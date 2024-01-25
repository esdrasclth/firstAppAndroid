package com.example.firstappandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText nombre, apellido, telefono;
    Button enviar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nombre = findViewById(R.id.txtNombre);
        apellido = findViewById(R.id.txtApellido);
        telefono = findViewById(R.id.txtTelefono);
        enviar = findViewById(R.id.btnEnviar);

        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String nombreValor = nombre.getText().toString();
                String apellidoValor = apellido.getText().toString();
                String telefonoValor = telefono.getText().toString();

                if (nombreValor.isEmpty() || apellidoValor.isEmpty() || telefonoValor.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
                } else {
                    Bundle enviarDatos = new Bundle();
                    enviarDatos.putString("keyNombre", nombreValor);
                    enviarDatos.putString("keyApellido", apellidoValor);
                    enviarDatos.putString("keyTelefono", telefonoValor);

                    Intent intent = new Intent(MainActivity.this, ActivityWelcome.class);
                    intent.putExtras(enviarDatos);

                    startActivity(intent);
                }
            }
        });
    }
}