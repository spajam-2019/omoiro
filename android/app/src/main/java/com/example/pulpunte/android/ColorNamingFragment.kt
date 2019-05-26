package com.example.pulpunte.android


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs

import kotlinx.android.synthetic.main.fragment_color_naming.*

/**
 * A simple [Fragment] subclass.
 *
 */
class ColorNamingFragment : Fragment() {
    val params: ColorNamingFragmentArgs by navArgs()
//    val hintWardList: List<String> = listOf()//params.params.hintWardList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_color_naming, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        params.params.hintWardList().forEach { word ->
            hintWords.addView(Button(context).apply {
                text = word
                setOnClickListener {
                    inputName.text = inputName.text.append(word)
                }
            })
        }

        nameReset.setOnClickListener {
            inputName.text.clear()
        }

        next.setOnClickListener {
            val action = ColorNamingFragmentDirections.actionColorNamingFragmentToColorPickFragment(params.params.copy(
                kana = inputKana.editableText.toString(),
                name = inputName.editableText.toString()
            ))
            findNavController().navigate(action)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = ColorNamingFragment()
    }

}
