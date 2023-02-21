package com.example.spaceus.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.spaceus.R
import com.example.spaceus.databinding.FragmentHomeBinding
import com.example.spaceus.models.Adapter
import com.example.spaceus.models.Locations

class HomeFragment : Fragment() {

    lateinit var recycle1:RecyclerView
    private val list = ArrayList<Locations>()
    private val adapter:Adapter = Adapter(list)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var v =inflater.inflate(R.layout.fragment_home, container, false)

        recycle1 = v.findViewById(R.id.rv_item)
        recycle1.layoutManager = LinearLayoutManager(activity)

        list.clear()
        testData()

        val adapterr = Adapter(list)
        recycle1.adapter = adapterr
        adapter.notifyDataSetChanged()
        recycle1.setHasFixedSize(true)

        return v
    }

    private fun testData(){
        list.add(Locations(R.drawable.book_cafe,"The Book Cafe","20 Martin Rd","Cafe"))
        list.add(Locations(R.drawable.citysprouts,"City Sprouts","102 Henderson Road","Cafe"))
        list.add(Locations(R.drawable.esplanade,"Library@esplande","8 Raffles Ave","Library"))
        list.add(Locations(R.drawable.hf,"Library@Harbourfront","1 HarbourFront Walk","Library"))
        list.add(Locations(R.drawable.mangawork,"MangaWork","291 Serangoon Rd","Cafe"))
        list.add(Locations(R.drawable.orchard,"Library@orchard","277 Orchard Road","Library"))
        list.add(Locations(R.drawable.rabbitandfox,"Rabbit&Fox","160 Orchard Rd","Cafe"))
        list.add(Locations(R.drawable.sixlettercoffee,"T6 Letter Coffee","259 Tanjong Katong Rd","Cafe"))

    }

}

