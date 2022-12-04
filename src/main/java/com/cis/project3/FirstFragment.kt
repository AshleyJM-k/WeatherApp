package com.cis.project3

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.cis.project3.databinding.FragmentFirstBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
    setHasOptionsMenu(true)
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.current.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment, bundleOf("CITY" to binding.editCity.text.toString()))
        }

        binding.list.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_ThirdFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
       inflater.inflate(R.menu.first_menu, menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            R.id.action_current -> {
                findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment, bundleOf("CITY" to binding.editCity.text.toString()))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}