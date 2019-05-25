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
import android.media.Image
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.esafirm.imagepicker.features.ImagePicker
import android.graphics.BitmapFactory


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


        datePicker.setOnFocusChangeListener { _, focus ->
            if(focus.not()) return@setOnFocusChangeListener

            DatePickerDialog(activity, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                date = LocalDate.of(year, month, dayOfMonth)
                DateTimeFormatter.ISO_DATE
                datePicker.setText(date.format(DateTimeFormatter.ofPattern("yyyy年MM月dd日")), TextView.BufferType.NORMAL)

                datePicker.clearFocus()
            }, date.year, date.monthValue, date.dayOfMonth).show()
        }

        addImage.setOnClickListener {
            ImagePicker.create(this).start()
        }

        next.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_memoryFragment_to_colorNamingFragment)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {
            val images = ImagePicker.getImages(data)
        }
    }

    fun Image.toBitMap(): Bitmap {
        val buffer = planes[0].buffer
        val bytes = ByteArray(buffer.capacity())
        buffer.get(bytes)
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.size, null)
    }
}
