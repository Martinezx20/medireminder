// src/main/java/com/example/medireminder/fragments/RegisterFragment.kt
package com.example.medireminder.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.medireminder.R
import com.example.medireminder.database.AppDatabase
import com.example.medireminder.database.Usuario
import com.example.medireminder.viewmodel.UsuarioViewModel
import kotlinx.coroutines.launch

class RegisterFragment : Fragment() { // Quita el constructor de layout aquí
    private lateinit var usuarioDao: AppDatabase // Cambiamos a AppDatabase para facilitar el acceso
    private lateinit var usuarioViewModel: UsuarioViewModel

    // Sobreescribe onCreateView para inflar el layout correcto basado en la navegación
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Determinamos qué layout inflar.
        // Si estamos en el fragmento de login (startDestination), inflamos fragment_login.xml
        // Si venimos del botón "Regístrate" en login, inflamos fragment_register.xml
        return if (findNavController().currentDestination?.id == R.id.loginFragment) {
            inflater.inflate(R.layout.fragment_login, container, false)
        } else {
            inflater.inflate(R.layout.fragment_register, container, false)
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        usuarioDao = AppDatabase.getDatabase(requireContext()) // Obtenemos la instancia de la base de datos
        usuarioViewModel = ViewModelProvider(this).get(UsuarioViewModel::class.java)

        // Lógica para el fragmento de LOGIN (cuando currentDestination es R.id.loginFragment)
        if (findNavController().currentDestination?.id == R.id.loginFragment) {
            val etEmailLogin = view.findViewById<EditText>(R.id.etEmail)
            val etPasswordLogin = view.findViewById<EditText>(R.id.etPassword)
            val btnLogin = view.findViewById<Button>(R.id.btnLogin)
            val btnGoRegister = view.findViewById<Button>(R.id.btn_go_register)

            btnLogin.setOnClickListener {
                val email = etEmailLogin.text.toString().trim()
                val password = etPasswordLogin.text.toString().trim()

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(context, "Por favor, ingresa tu email y contraseña", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                usuarioViewModel.login(email, password)
            }

            usuarioViewModel.usuario.observe(viewLifecycleOwner, Observer { usuario ->
                if (usuario != null) {
                    // Login exitoso
                    Toast.makeText(context, "¡Inicio de sesión exitoso!", Toast.LENGTH_SHORT).show()

                    // **** PASO CLAVE: GUARDAR EL ID DEL USUARIO EN SHARED PREFERENCES ****
                    val sharedPref = activity?.getSharedPreferences(
                        getString(R.string.preference_file_key), Context.MODE_PRIVATE) ?: return@Observer
                    with (sharedPref.edit()) {
                        putInt("logged_in_user_id", usuario.id_usuario) // Guarda el ID del usuario
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

        } else { // Lógica para el fragmento de REGISTRO (cuando currentDestination es R.id.registerFragment)
            val etNameRegister = view.findViewById<EditText>(R.id.etName)
            val etEmailRegister = view.findViewById<EditText>(R.id.etEmail)
            val etPasswordRegister = view.findViewById<EditText>(R.id.etPassword)
            val btnRegister = view.findViewById<Button>(R.id.btnRegister)

            btnRegister.setOnClickListener {
                val name = etNameRegister.text.toString().trim()
                val email = etEmailRegister.text.toString().trim()
                val password = etPasswordRegister.text.toString().trim()

                if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(context, "Por favor llena todos los campos", Toast.LENGTH_SHORT).show()
                } else {
                    lifecycleScope.launch {
                        val existingUser = usuarioDao.usuarioDao().getUserByEmail(email)
                        if (existingUser != null) {
                            Toast.makeText(context, "Este correo ya está registrado", Toast.LENGTH_SHORT).show()
                        } else {
                            val newUser = Usuario(0, name, email, password)
                            usuarioDao.usuarioDao().insert(newUser)
                            Toast.makeText(context, "¡Registro exitoso!", Toast.LENGTH_SHORT).show()
                            // Navegar al fragmento de login después del registro exitoso
                            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                        }
                    }
                }
            }
        }
    }
}