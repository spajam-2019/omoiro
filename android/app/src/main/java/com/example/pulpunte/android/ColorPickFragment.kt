package com.example.pulpunte.android


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs

import com.jaredrummler.android.colorpicker.ColorPanelView
import kotlinx.android.synthetic.main.fragment_color_pick.*


/**
 * A simple [Fragment] subclass.
 *
 */
class ColorPickFragment() : Fragment() {
    val params: ColorPickFragmentArgs by navArgs()

    var selectColor = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_color_pick, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        params.params.hintColor().shuffled().take(4).forEach { c ->
            hintColors.addView(ColorPanelView(context).apply {
                color = c
                layoutParams = ViewGroup.LayoutParams(400, 400)
                setOnClickListener {
                    colorPicker.color = c
                    selectColor = color
                }
            })
        }

        colorPicker.setOnColorChangedListener { color ->
            selectColor = color
            colorPanel.color = selectColor
        }

        next.setOnClickListener {
            val action = ColorPickFragmentDirections.actionColorPickFragmentToConfirmationFragment(params.params.copy(
                color = selectColor
            ))
            findNavController().navigate(action)
        }
    }
}
