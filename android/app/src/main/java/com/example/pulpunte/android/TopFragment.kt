package com.example.pulpunte.android

import android.content.Context
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation

import com.example.pulpunte.android.R
import httpClient.HttpClient
import httpClient.Omoiro
import kotlinx.android.synthetic.main.fragment_top.*
import kotlinx.android.synthetic.main.top_item_layout.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
    var omoirosList = mutableListOf<Omoiro>()
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

        val adapter = MyAdapter(context!!, omoirosList)
        omoirolist.adapter = adapter

        GlobalScope.launch(Dispatchers.IO) {
            val omoiros = HttpClient().GetOmoiros().toList()
            withContext(Dispatchers.Main) {
                adapter.items = omoiros.toMutableList()
                adapter.notifyDataSetChanged()
            }
        }
    }

    class MyAdapter(val context: Context, var items: MutableList<Omoiro>): BaseAdapter() {
        var layoutInflater: LayoutInflater? = null

        init {
            layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val view = layoutInflater!!.inflate(R.layout.top_item_layout,parent,false)
            val omoiro = getItem(position)

            view.colorPanel.color = Color.parseColor(omoiro.color.code)
            view.colorPanel.borderColor = Color.parseColor(omoiro.color.code)
            view.name.text = omoiro.color.name
            view.kana.text = omoiro.color.furigana

            return view
        }

        override fun getItem(position: Int): Omoiro {
            return items[position]
        }

        override fun getItemId(position: Int): Long {
            return 0
        }

        override fun getCount(): Int {
            return items.size
        }

    }
}
