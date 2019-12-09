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

class RegisterActivity : AppCompatActivity(), View.OnClickListener {

    private val activity = this@RegisterActivity

    private lateinit var nestedScrollView: NestedScrollView

    private lateinit var textInputLayoutName: TextInputLayout
    private lateinit var textInputLayoutEmail: TextInputLayout
    private lateinit var textInputLayoutPassword: TextInputLayout
    private lateinit var textInputLayoutConfirmPassword: TextInputLayout

    private lateinit var textInputEditTextName: TextInputEditText
    private lateinit var textInputEditTextEmail: TextInputEditText
    private lateinit var textInputEditTextPassword: TextInputEditText
    private lateinit var textInputEditTextConfirmPassword: TextInputEditText

    private lateinit var appCompatButtonRegister: Button
    private lateinit var appCompatTextViewLoginLink: TextView

    private lateinit var inputValidation: InputValidation

    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_register)

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

    private fun performRegister() {
        val nama = textInputEditTextName.text.toString()
        val email = textInputEditTextEmail.text.toString()
        val password = textInputEditTextPassword.text.toString()
        val confirmPassword = textInputEditTextConfirmPassword.text.toString()

        if (!inputValidation!!.isInputEditTextFilled(
                textInputEditTextName,
                textInputLayoutName,
                "Masukkan Nama"
            )
        ) {
            return
        }
        if (!inputValidation!!.isInputEditTextFilled(
                textInputEditTextEmail,
                textInputLayoutEmail,
                "Masukkan Email"
            )
        ) {
            return
        }
        if (!inputValidation!!.isInputEditTextEmail(
                textInputEditTextEmail,
                textInputLayoutEmail,
                "Masukkan Email yang Valid"
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
        if (!inputValidation!!.isInputEditTextMatches(
                textInputEditTextPassword, textInputEditTextConfirmPassword, textInputLayoutPassword
                , "Password Tidak Sama"
            )
        ) {
            return
        }

        if (nama.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty()) {
            if (password == confirmPassword) {
                firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener {
                        if (!it.isSuccessful) {
                            return@addOnCompleteListener
                        } else {
                            Snackbar.make(
                                nestedScrollView!!,
                                "Registrasi Berhasil, Mohon Tunggu Sebentar",
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
                            "Registrasi Gagal: ${it.message}",
                            Snackbar.LENGTH_LONG
                        ).show()
                    }
            }
        }
    }

    /**
     * This method is to initialize views
     */
    private fun initViews() {
        nestedScrollView = findViewById<View>(R.id.containerRegister) as NestedScrollView

        textInputLayoutName = findViewById<View>(R.id.textInputLayoutName) as TextInputLayout
        textInputLayoutEmail = findViewById<View>(R.id.textInputLayoutEmail) as TextInputLayout
        textInputLayoutPassword =
            findViewById<View>(R.id.textInputLayoutPassword) as TextInputLayout
        textInputLayoutConfirmPassword =
            findViewById<View>(R.id.textInputLayoutConfirmPassword) as TextInputLayout

        textInputEditTextName = findViewById<View>(R.id.textInputEditTextName) as TextInputEditText
        textInputEditTextEmail =
            findViewById<View>(R.id.textInputEditTextEmail) as TextInputEditText
        textInputEditTextPassword =
            findViewById<View>(R.id.textInputEditTextPassword) as TextInputEditText
        textInputEditTextConfirmPassword =
            findViewById<View>(R.id.textInputEditTextConfirmPassword) as TextInputEditText

        appCompatButtonRegister = findViewById<View>(R.id.appCompatButtonRegister) as Button

        appCompatTextViewLoginLink = findViewById<View>(R.id.appCompatTextViewLoginLink) as TextView

    }

    /**
     * This method is to initialize listeners
     */
    private fun initListeners() {
        appCompatButtonRegister!!.setOnClickListener(this)
        appCompatTextViewLoginLink!!.setOnClickListener(this)

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
     * @param v
     */
    override fun onClick(v: View) {
        when (v.id) {

            R.id.appCompatButtonRegister -> performRegister()
            R.id.appCompatTextViewLoginLink -> finish()
        }
    }

    /**
     * This method is to empty all input edit text
     */
    private fun emptyInputEditText() {
        textInputEditTextName!!.text = null
        textInputEditTextEmail!!.text = null
        textInputEditTextPassword!!.text = null
        textInputEditTextConfirmPassword!!.text = null
    }
}