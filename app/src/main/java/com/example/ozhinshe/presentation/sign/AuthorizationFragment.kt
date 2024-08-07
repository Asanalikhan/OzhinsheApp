package com.example.ozhinshe.presentation.sign

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
import com.example.ozhinshe.domain.data.MainApi
import com.example.ozhinshe.databinding.FragmentAuthorizationBinding
import com.example.ozhinshe.domain.data.ServiceBuilder
import com.example.ozhinshe.domain.models.AuthRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AuthorizationFragment: Fragment(){
    private lateinit var binding: FragmentAuthorizationBinding
    private lateinit var mainApi: MainApi
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
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
        val sharedPreferences = requireContext().getSharedPreferences("Profile", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        binding.btnAuthorization.setOnClickListener {
            hideKeyboard(it)
            val email = inputEmail.editText?.text.toString().trim()
            val password = inputPassworld.editText?.text.toString().trim()
            var access = false
            var access1 = false
            if(isValidEmail(email)){
                inputEmail.error = null
                access = true
            }
            else{
                inputEmail.error = "Қате формат"
                access = false
            }
            if(password.length >= 6){
                inputPassworld.error = null
                access1 = true
            }
            else{
                inputPassworld.error = "Қате формат"
                access1 = false
            }
            if (access && access1) {
                auth(AuthRequest(email, password)) { success ->
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
    private fun auth(authRequest: com.example.ozhinshe.domain.models.AuthRequest, onComplete: (Boolean) -> Unit){
        lifecycleScope.launch(Dispatchers.IO){
            val responce = mainApi.auth(authRequest)
            lifecycleScope.launch(Dispatchers.Main) {
                if(responce.errorBody() != null){
                    binding.errorMesg.visibility = View.VISIBLE
                    onComplete(false)
                }
                else{
                    binding.errorMesg.visibility = View.GONE
                    onComplete(true)
                }
                binding.tvUnderSalemText.text = responce.body()?.email ?: ""
                val user = responce.body()
                saveUserToken(user?.accessToken.toString())
            }
        }
    }

    fun saveUserToken(token: String) {
        ServiceBuilder.saveToken(token)
    }

    fun hideKeyboard(view: View) {
        val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}