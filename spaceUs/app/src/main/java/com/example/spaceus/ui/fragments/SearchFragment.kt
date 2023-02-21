package com.example.spaceus.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.spaceus.R
import com.example.spaceus.databinding.FragmentSearchBinding
import com.example.spaceus.models.Adapter
import com.example.spaceus.models.Locations
import com.example.spaceus.ui.activities.FilterActivity
import com.example.spaceus.ui.activities.MainActivity
import kotlinx.android.synthetic.main.fragment_search.view.*
import kotlin.collections.ArrayList
class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    lateinit var recycle1: RecyclerView
    private val list = ArrayList<Locations>()
    private val adapter: Adapter = Adapter(list)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var v =inflater.inflate(R.layout.fragment_search, container, false)

        recycle1 = v.findViewById(R.id.rv_item)
        recycle1.layoutManager = LinearLayoutManager(activity)

        list.clear()
        testData()

        val adapterr = Adapter(list)
        recycle1.adapter = adapterr
        adapter.notifyDataSetChanged()
        recycle1.setHasFixedSize(true)


        v.btn_filter.setOnClickListener{
            val intent = Intent(requireContext(), FilterActivity::class.java)
            startActivity(intent)
        }

        return v
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
        val item = menu?.findItem(R.id.search)
        val searchView = item?.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {

                return false
            }
        })
        return super.onCreateOptionsMenu(menu,inflater)
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