package com.example.pulpunte.android

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation

import com.example.pulpunte.android.R
import kotlinx.android.synthetic.main.fragment_top.*

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [TopFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [TopFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class TopFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_top, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        to_memory.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_topFragment_to_memoryFragment)
        }
    }
}
