package com.example.pulpunte.android


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.fragment.navArgs

import com.example.pulpunte.android.R
import httpClient.Color
import httpClient.HttpClient
import httpClient.ReqOmoiro
import kotlinx.android.synthetic.main.fragment_confirmation.*
import java.time.format.DateTimeFormatter
import kotlinx.coroutines.*
import java.nio.ByteBuffer
import java.util.concurrent.CountDownLatch
import kotlin.collections.ArrayList


/**
 * A simple [Fragment] subclass.
 *
 */
class ConfirmationFragment : Fragment() {
    val params: ConfirmationFragmentArgs by navArgs()
    val job: Job = Job()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_confirmation, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        kana.text = params.params.kana
        name.text = params.params.name
        date.text = params.params.date.format(DateTimeFormatter.ofPattern("yyyy年MM月dd日"))
        text.text = params.params.text
        params.params.images.forEach {
            imageList.addView(ImageView(context).apply {
                layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                setImageBitmap(it)
            })
        }



        post.setOnClickListener {
            val latch = CountDownLatch(params.params.images.size)
            val urls = ArrayList<String>()
            val httpClient=HttpClient()

            params.params.images.forEach {
                val byteBuffer = ByteBuffer.allocate(it.byteCount)
                it.copyPixelsToBuffer(byteBuffer)
                val byteArray = byteBuffer.array()
                httpClient.UploadFile("bmp",byteArray,{
                        url->
                            urls.add("https://firebasestorage.googleapis.com/v0/b/omoiro.appspot.com/o/images%2F28004000763.jpg?alt=media&token=08c0b9f4-4b2b-4c3e-9070-17bee44ebcce")
                            latch.countDown()
                })

            }

            latch.await()

            val req = ReqOmoiro().apply{
                val array = arrayOfNulls<String>(urls.size)
                urls.toArray(array)
                image_urls = array
                color = Color().apply {
                    code = params.params.color.toString()
                    furigana = params.params.kana
                    name = params.params.name
                }
                text = params.params.text
                date = params.params.date.format(DateTimeFormatter.ofPattern("yyyy/MM/dd:hh:mm:ss"))
                user_id = 1
            }
        }
    }
}
