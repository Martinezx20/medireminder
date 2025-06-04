// Asumo que este es tu LoginFragment o el fragmento donde manejas el login
// Si tu nav_graph.xml apunta a RegisterFragment para el login,
// el código de login debería estar en RegisterFragment y usar fragment_login.xml
// (aunque el snippet que me diste de RegisterFragment solo muestra el registro).

package com.example.medireminder.fragments

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.medireminder.R
import com.example.medireminder.database.AppDatabase
import com.example.medireminder.viewmodel.UsuarioViewModel // Asegúrate de importar el ViewModel
import androidx.lifecycle.Observer // Asegúrate de importar Observer

// Este es el fragmento que maneja el login
class LoginFragment : Fragment(R.layout.fragment_login) { // Asumo fragment_login.xml para el layout
    private lateinit var viewModel: UsuarioViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = androidx.lifecycle.ViewModelProvider(this).get(UsuarioViewModel::class.java)

        val etEmail = view.findViewById<EditText>(R.id.etEmail)
        val etPassword = view.findViewById<EditText>(R.id.etPassword)
        val btnLogin = view.findViewById<Button>(R.id.btnLogin)
        val btnGoRegister = view.findViewById<Button>(R.id.btn_go_register)


        btnLogin.setOnClickListener {
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(context, "Por favor, ingresa tu email y contraseña", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Llama a la función de login del ViewModel
            viewModel.login(email, password)
        }

        // Observa el resultado del login desde el ViewModel
        viewModel.usuario.observe(viewLifecycleOwner, Observer { usuario ->
            if (usuario != null) {
                // Login exitoso
                Toast.makeText(context, "¡Inicio de sesión exitoso!", Toast.LENGTH_SHORT).show()

                // **** PASO CLAVE: GUARDAR EL ID DEL USUARIO EN SHARED PREFERENCES ****
                val sharedPref = activity?.getSharedPreferences(
                    getString(R.string.preference_file_key), Context.MODE_PRIVATE) ?: return@Observer
                with (sharedPref.edit()) {
                    putInt("logged_in_user_id", usuario.id_usuario) // Guarda el ID
                    apply() // Aplica los cambios
                }

                // Navegar al HomeFragment
                findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
            } else {
                // Login fallido
                Toast.makeText(context, "Credenciales incorrectas", Toast.LENGTH_SHORT).show()
            }
        })

        btnGoRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }
}