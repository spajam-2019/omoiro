package com.example.pulpunte.android

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.squareup.moshi.Moshi
import kotlinx.android.synthetic.main.fragment_one.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import ru.gildor.coroutines.okhttp.await
import java.time.LocalDate


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [OneFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [OneFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class OneFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        GlobalScope.launch {
            with(Dispatchers.IO) {
                val response = OkHttpClient().newCall(Request.Builder().url("https://79fecbbd.ngrok.io/json").build()).await()
                val testModel = response.get<TestModel>()
                Log.d("testModel", testModel.toString())
            }
        }


        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_one, container, false)
        view.button.setOnClickListener {
            val action = OneFragmentDirections.actionOneFragmentToSecondFragment(LocalDate.now())
            findNavController().navigate(action)
        }
        return view
    }
}
