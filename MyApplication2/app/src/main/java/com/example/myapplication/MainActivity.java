package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.io.*;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private EditText txtCliente;
    private ListView txtListado;
    private ArrayList<String> clientList;
    private ArrayAdapter<String> adapter;
    private static final String FILE_NAME = "texto.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtCliente = findViewById(R.id.txtCliente);
        txtListado = findViewById(R.id.txtListado);
        Button btnGuardar = findViewById(R.id.btnGuardar);
        Button btnLeerClientes = findViewById(R.id.btnLeerClientes);

        clientList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, clientList);
        txtListado.setAdapter(adapter);

        btnGuardar.setOnClickListener(this::guardarCliente);
        btnLeerClientes.setOnClickListener(this::leerClientes);
    }

    private void guardarCliente(View vista) {
        String clientName = txtCliente.getText().toString();
        if (!clientName.isEmpty()) {
            try (FileOutputStream fos = openFileOutput(FILE_NAME, MODE_APPEND)) {
                fos.write((clientName + "\n").getBytes());
                txtCliente.setText("");
                Log.d("MainActivity", "Cliente guardado: " + clientName);
            } catch (IOException e) {
                Log.e("MainActivity", "Error al guardar el cliente", e);
            }
        }
    }

    private void leerClientes(View vista) {
        clientList.clear();  // Limpiar la lista antes de leer del archivo
        try (FileInputStream fis = openFileInput(FILE_NAME);
             InputStreamReader isr = new InputStreamReader(fis);
             BufferedReader br = new BufferedReader(isr)) {

            String line;
            while ((line = br.readLine()) != null) {
                clientList.add(line);
            }
            adapter.notifyDataSetChanged();
            Log.d("MainActivity", "Clientes leídos del archivo.");
        } catch (IOException e) {
            Log.e("MainActivity", "Error al leer el archivo de clientes", e);
        }
    }

}