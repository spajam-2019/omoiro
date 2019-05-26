package com.example.pulpunte.android

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.fragment_memory.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import android.content.Intent
import android.graphics.Bitmap
import androidx.fragment.app.Fragment
import com.esafirm.imagepicker.features.ImagePicker
import android.graphics.BitmapFactory
import android.widget.ImageView
import androidx.navigation.fragment.findNavController
import com.esafirm.imagepicker.model.Image


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [MemoryFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [MemoryFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class MemoryFragment : Fragment() {
    private val RESULT_PICK_FILENAME: Int = 1
    var date: LocalDate = LocalDate.now()
    var imageList: List<Bitmap> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_memory, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        datePicker.setText(date.format(DateTimeFormatter.ofPattern("yyyy年MM月dd日")), TextView.BufferType.NORMAL)

        datePicker.setOnFocusChangeListener { _, focus ->
            if(focus.not()) return@setOnFocusChangeListener

            DatePickerDialog(activity, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                date = LocalDate.of(year, month, dayOfMonth)
                DateTimeFormatter.ISO_DATE
                datePicker.setText(date.format(DateTimeFormatter.ofPattern("yyyy年MM月dd日")), TextView.BufferType.NORMAL)

                datePicker.clearFocus()
            }, date.year, date.monthValue + 1, date.dayOfMonth).show()
        }

        addImage.setOnClickListener {
            ImagePicker.create(this).start()
        }

        next.setOnClickListener {
            val action = MemoryFragmentDirections.actionMemoryFragmentToColorNamingFragment(
                OmoiroParams().apply {
                    date = date
                    text = editText.editableText.toString()
                    images = imageList
                }
            )
            this.findNavController().navigate(action)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {
            imageList = ImagePicker.getImages(data).map { it.toBitMap() }.toMutableList()
            imageList.forEach {
                imageViews.addView(ImageView(context).apply {
                    layoutParams = ViewGroup.LayoutParams(400, 400)
                    setImageBitmap(it)
                })
            }

        }
    }

    private fun Image.toBitMap(): Bitmap {
        val options = BitmapFactory.Options()
        options.inPreferredConfig = Bitmap.Config.ARGB_8888
        return BitmapFactory.decodeFile(path, options)
    }
}
