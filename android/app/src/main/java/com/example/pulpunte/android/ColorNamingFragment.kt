package com.example.pulpunte.android


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation

import com.example.pulpunte.android.R
import kotlinx.android.synthetic.main.fragment_color_naming.*

/**
 * A simple [Fragment] subclass.
 *
 */
class ColorNamingFragment : Fragment() {
    val hintCharList: List<String> = listOf("本", "文", "入", "本", "文", "入", "本", "文", "入")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_color_naming, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        hintCharList.forEach { word ->
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
            Navigation.findNavController(it).navigate(R.id.action_colorNamingFragment_to_colorPickFragment)
        }
    }

}
