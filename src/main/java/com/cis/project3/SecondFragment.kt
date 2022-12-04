package com.cis.project3

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.toolbox.Volley
import com.cis.project3.databinding.FragmentSecondBinding
import com.squareup.picasso.Picasso



class SecondFragment : Fragment() {

    companion object {
        fun newInstance() = SecondFragment()
    }
    private lateinit var viewModel: SecondViewModel
    private lateinit var binding:FragmentSecondBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) { //view is inside onViewCreated
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(SecondViewModel::class.java)


        var cityInfo = arguments?.getString("CITY").toString() //?can be nullable
        binding.city.text = cityInfo


        var queue = Volley.newRequestQueue(context)//request city info from viewModel
        viewModel.currentWeather(queue, cityInfo)

        //temp Observer
        val tempObserver = Observer<String> { temp -> binding.temp.text = temp }
        viewModel.getTemp().observe(viewLifecycleOwner, tempObserver)

        //date observer
        val dateObserver = Observer<String> { date -> binding.date.text = date }
        viewModel.getDate().observe(viewLifecycleOwner, dateObserver)

        //desc
        val descObserver = Observer<String> { desc -> binding.desc.text = desc }
        viewModel.getDesc().observe(viewLifecycleOwner, descObserver)


        //icon
        val iconObserver =
            Observer<String> { icon -> Picasso.with(context).load(icon).into(binding.icon) }
        viewModel.getIcon().observe(viewLifecycleOwner, iconObserver)






    }


}