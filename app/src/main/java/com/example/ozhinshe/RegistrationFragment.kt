package com.example.ozhinshe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.ozhinshe.data.MainApi
import com.example.ozhinshe.data.RegistrationRequest
import com.example.ozhinshe.databinding.FragmentRegistrationBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RegistrationFragment : Fragment() {
    private lateinit var binding: FragmentRegistrationBinding
    private val viewModel: AuthViewModel by activityViewModels()
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
            val email = inputLayoutEmail.editText?.text.toString().trim()
            val password = inputLayoutPassword.editText?.text.toString().trim()
            val password2 = inputLayoutPassword2.editText?.text.toString().trim()
            if(isValidEmail(email)){
                inputLayoutEmail.error = null
            }
            else{
                inputLayoutEmail.error ="Қате формат"
            }
            if(password.length >= 6){
                inputLayoutPassword.error = null
            }
            else{
                inputLayoutPassword.error ="Қате формат"
            }
            if(password2 == password){
                inputLayoutPassword2.error = null
            }
            else{
                inputLayoutPassword2.error = "Құпия сөздер тең емес"
            }

            regirst(RegistrationRequest(email, password))
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
    private fun regirst(registrationRequest: RegistrationRequest){
        lifecycleScope.launch(Dispatchers.IO){
            val responce = mainApi.regr(registrationRequest)
            lifecycleScope.launch(Dispatchers.Main) {
                if(responce.errorBody() != null){
                    binding.errorMesg.visibility = View.VISIBLE
                }
                else{
                    binding.errorMesg.visibility = View.GONE
                }

                val user = responce.body()
                binding.tvUnderSalemText.text = user?.email
                viewModel.token.value = user?.accessToken
            }
        }
    }
}
