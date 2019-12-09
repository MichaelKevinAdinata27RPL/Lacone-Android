package com.kevin.lacone

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.kevin.lacone.helpers.InputValidation


class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private val activity = this@LoginActivity

    private lateinit var nestedScrollView: NestedScrollView

    private lateinit var textInputLayoutEmail: TextInputLayout
    private lateinit var textInputLayoutPassword: TextInputLayout

    private lateinit var textInputEditTextEmail: TextInputEditText
    private lateinit var textInputEditTextPassword: TextInputEditText

    private lateinit var appCompatButtonLogin: Button

    private lateinit var textViewLinkRegister: TextView

    private lateinit var inputValidation: InputValidation

    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)

        firebaseAuth = FirebaseAuth.getInstance()

        // hiding the action bar
        supportActionBar!!.hide()

        // initializing the views
        initViews()

        // initializing the listeners
        initListeners()

        // initializing the objects
        initObjects()
    }

    private fun performLogin() {
        val email = textInputEditTextEmail.getText().toString()
        val password = textInputEditTextPassword.getText().toString()

        if (!inputValidation!!.isInputEditTextFilled(
                textInputEditTextEmail,
                textInputLayoutEmail,
                "Masukkan Email"
            )
        ) {
            return
        }
        if (!inputValidation!!.isInputEditTextFilled(
                textInputEditTextPassword,
                textInputLayoutPassword,
                "Masukkan Password"
            )
        ) {
            return
        }

        if (email.isNotEmpty() && password.isNotEmpty()) {
            firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if (!it.isSuccessful) {
                        return@addOnCompleteListener
                    } else {
                        Snackbar.make(
                            nestedScrollView!!,
                            "Login Berhasil, Mohon Tunggu Sebentar",
                            Snackbar.LENGTH_LONG
                        ).show()
                        emptyInputEditText()
                        Handler().postDelayed({
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        }, 1000)
                    }
                }
                .addOnFailureListener {
                    Snackbar.make(
                        nestedScrollView!!,
                        "Login Gagal: ${it.message}",
                        Snackbar.LENGTH_LONG
                    ).show()
                }
        }
    }

    /**
     * This method is to initialize views
     */
    private fun initViews() {

        nestedScrollView = findViewById<View>(R.id.containerLogin) as NestedScrollView

        textInputLayoutEmail = findViewById<View>(R.id.textInputLayoutEmail) as TextInputLayout
        textInputLayoutPassword =
            findViewById<View>(R.id.textInputLayoutPassword) as TextInputLayout

        textInputEditTextEmail =
            findViewById<View>(R.id.textInputEditTextEmail) as TextInputEditText
        textInputEditTextPassword =
            findViewById<View>(R.id.textInputEditTextPassword) as TextInputEditText

        appCompatButtonLogin = findViewById<View>(R.id.appCompatButtonLogin) as Button

        textViewLinkRegister = findViewById<View>(R.id.textViewLinkRegister) as TextView

    }

    /**
     * This method is to initialize listeners
     */
    private fun initListeners() {

        appCompatButtonLogin!!.setOnClickListener(this)
        textViewLinkRegister!!.setOnClickListener(this)
    }

    /**
     * This method is to initialize objects to be used
     */
    private fun initObjects() {

        inputValidation = InputValidation(activity)

    }

    /**
     * This implemented method is to listen the click on view
     *
     * @param
     */
    override fun onClick(v: View) {
        when (v.id) {
            R.id.appCompatButtonLogin -> performLogin()
            R.id.textViewLinkRegister -> {
                // Navigate to RegisterActivity
                val intentRegister = Intent(applicationContext, RegisterActivity::class.java)
                startActivity(intentRegister)
            }
        }
    }

    /**
     * This method is to empty all input edit text
     */
    private fun emptyInputEditText() {
        textInputEditTextEmail!!.text = null
        textInputEditTextPassword!!.text = null
    }
}
