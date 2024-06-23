package com.example.ozhinshe.sign

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.ozhinshe.R
import com.example.ozhinshe.data.MainApi
import com.example.ozhinshe.databinding.FragmentRegistrationBinding
import com.example.ozhinshe.modiedata.RegistrationRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RegistrationFragment : Fragment() {
    private lateinit var binding: FragmentRegistrationBinding
    private lateinit var mainApi: MainApi
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegistrationBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)

        val sharedPreferences = requireContext().getSharedPreferences("Profile", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        binding.imageButton.setOnClickListener {
            findNavController().navigate(R.id.action_registrationFragment_to_onBoardFragment)
        }
        binding.toLogin.setOnClickListener {
            findNavController().navigate(R.id.action_registrationFragment_to_authorizationFragment)
        }

        initRetrofit()

        val inputLayoutEmail = binding.textInputLayout
        val inputLayoutPassword = binding.textInputLayout2
        val inputLayoutPassword2 = binding.textInputLayout3
        binding.btnRegistration.setOnClickListener {
            hideKeyboard(it)
            val email = inputLayoutEmail.editText?.text.toString().trim()
            val password = inputLayoutPassword.editText?.text.toString().trim()
            val password2 = inputLayoutPassword2.editText?.text.toString().trim()
            var access = false
            var access1 = false
            var access2 = false
            if(isValidEmail(email)){
                inputLayoutEmail.error = null
                access = true
            }
            else{
                inputLayoutEmail.error ="Қате формат"
                var access = false
            }
            if(password2 == password){
                inputLayoutPassword2.error = null
                access1 = true
            }
            else{
                inputLayoutPassword2.error = "Құпия сөздер тең емес"
                var access1 = false
            }
            if(password.length >= 6){
                inputLayoutPassword.error = null
                access2 = true
            }
            else{
                inputLayoutPassword.error ="Қате формат"
                var access2 = false
            }
            if (access && access1 && access2) {
                regirst(RegistrationRequest(email, password)) { success ->
                    if (success) {
                        editor.putString("email", email)
                        editor.putString("password", password)
                        editor.apply()
                        findNavController().navigate(R.id.action_authorizationFragment_to_homeActivity)
                        requireActivity().finish()
                    }
                }
            }
        }
    }
    private fun isValidEmail(email: String): Boolean {
        val emailRegex = "^[a-zA-Z][a-zA-Z0-9._-]*@[a-z]+\\.[a-z]+\$"
        return email.matches(emailRegex.toRegex())
    }
    private fun initRetrofit(){
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
        val BASE_URL = "http://api.ozinshe.com"
        val retrofit: Retrofit = Retrofit.Builder().baseUrl(BASE_URL).
        client(client).addConverterFactory(GsonConverterFactory.create()).build()
        mainApi = retrofit.create(MainApi::class.java)
    }
    private fun regirst(registrationRequest: RegistrationRequest, onComplete: (Boolean) -> Unit){
        lifecycleScope.launch(Dispatchers.IO){
            val responce = mainApi.regr(registrationRequest)
            lifecycleScope.launch(Dispatchers.Main) {
                if(responce.errorBody() != null){
                    binding.errorMesg.visibility = View.VISIBLE
                    onComplete(false)
                }
                else{
                    binding.errorMesg.visibility = View.GONE
                    onComplete(true)
                }
                val user = responce.body()
                binding.tvUnderSalemText.text = user?.email
            }
        }
    }
    fun hideKeyboard(view: View) {
        val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}
