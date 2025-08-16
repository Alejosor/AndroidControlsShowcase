package com.example.androidcontrols;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        EditText txtUsuario = findViewById(R.id.TxtUsuario);
        EditText txtPassword = findViewById(R.id.txtPassword);
        Button buttLogin = findViewById(R.id.buttLogin);

        buttLogin.setOnClickListener(v -> {
            String usuario = txtUsuario.getText().toString();
            String password = txtPassword.getText().toString();

            // Credenciales sencillas
            if (usuario.equals("admin") && password.equals("1234")) {
                onLoginSuccess();
            } else {
                Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Método que se llama cuando el usuario inicia sesión correctamente
    private void onLoginSuccess() {
        SharedPreferences prefs = getSharedPreferences("user_session", MODE_PRIVATE);
        prefs.edit().putBoolean("is_logged_in", true).apply();

        // Redirigir al MainActivity
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
