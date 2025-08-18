package com.example.androidcontrols;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
public class MainActivity extends AppCompatActivity {

    // Variables de instancia
    private SharedPreferences prefs;
    private Button btnLogout, btnMain;
    private ImageView imgPortada;
    private Switch switchTheme;

    /**
     * Método principal que se ejecuta al crear la actividad.
     * Inicializa la interfaz, valida la sesión y configura los controles.
     * @param savedInstanceState Estado guardado de la actividad
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Validación de sesión
        prefs = getSharedPreferences("user_session", MODE_PRIVATE);
        if (!prefs.getBoolean("is_logged_in", false)) {
            redirectToLogin();
            return;
        }

        // Configuración de la interfaz
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Inicialización de vistas
        btnLogout = findViewById(R.id.btnLogout);
        btnMain = findViewById(R.id.btnMain);
        imgPortada = findViewById(R.id.imgPortada);
        switchTheme = findViewById(R.id.modeSwitch);

        setupLogoutButton();
        setupMainButton();
        setupThemeSwitch();
    }

    /**
     * Redirige al usuario a la pantalla de inicio de sesión y finaliza la actividad actual.
     */
    private void redirectToLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * Configura el botón de cierre de sesión para eliminar la sesión y redirigir al login.
     */
    private void setupLogoutButton() {
        btnLogout.setOnClickListener(v -> {
            prefs.edit().remove("is_logged_in").apply();
            redirectToLogin();
        });
    }

    /**
     * Configura el botón principal para mostrar la imagen de portada.
     */
    private void setupMainButton() {
        btnMain.setOnClickListener(v -> imgPortada.setVisibility(ImageView.VISIBLE));
    }

    /**
     * Configura el interruptor de tema para alternar entre modo claro y oscuro.
     */
    private void setupThemeSwitch() {
        boolean darkMode = prefs.getBoolean("dark_mode", false);
        switchTheme.setChecked(darkMode);
        AppCompatDelegate.setDefaultNightMode(darkMode ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);
        switchTheme.setOnCheckedChangeListener((buttonView, isChecked) -> {
            prefs.edit().putBoolean("dark_mode", isChecked).apply();
            AppCompatDelegate.setDefaultNightMode(isChecked ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);
            Toast.makeText(this,
                    isChecked ? "Modo Oscuro Activado" : "Modo Oscuro Desactivado",
                    Toast.LENGTH_SHORT).show();
        });
    }
}