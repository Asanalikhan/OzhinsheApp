package com.example.ozhinshe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.ozhinshe.data.AuthRequest
import com.example.ozhinshe.data.MainApi
import com.example.ozhinshe.databinding.FragmentAuthorizationBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AuthorizationFragment : Fragment() {
    private lateinit var binding: FragmentAuthorizationBinding
    private val viewModel: AuthViewModel by activityViewModels()
    private lateinit var mainApi: MainApi
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAuthorizationBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toRegistration.setOnClickListener {
            findNavController().navigate(R.id.action_authorizationFragment_to_registrationFragment)
        }
        binding.imageButton.setOnClickListener {
            findNavController().navigate(R.id.action_authorizationFragment_to_onBoardFragment)
        }

        initRetrofit()

        val inputEmail = binding.textInputLayout
        val inputPassworld = binding.textInputLayout2

        binding.btnAuthorization.setOnClickListener {
            val email = inputEmail.editText?.text.toString().trim()
            val password = inputPassworld.editText?.text.toString().trim()
            if(isValidEmail(email)){
                inputEmail.error = null
            }
            else{
                inputEmail.error = "Қате формат"
            }
            if(password.length >= 6){
                inputPassworld.error = null
            }
            else{
                inputPassworld.error = "Қате формат"
            }

            auth(AuthRequest(email, password))

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
    private fun auth(authRequest: AuthRequest){
        lifecycleScope.launch(Dispatchers.IO){
            val responce = mainApi.auth(authRequest)
            lifecycleScope.launch(Dispatchers.Main) {
                if(responce.errorBody() != null){
                    binding.errorMesg.visibility = View.VISIBLE
                }
                else{
                    binding.errorMesg.visibility = View.GONE
                }
                binding.tvUnderSalemText.text = responce.body()?.email ?: ""
                val user = responce.body()
                viewModel.token.value = user?.accessToken
            }
        }
    }
}