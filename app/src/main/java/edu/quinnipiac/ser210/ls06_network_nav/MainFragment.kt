package edu.quinnipiac.ser210.ls06_network_nav

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.quinnipiac.ser210.ls06_network_nav.databinding.ActivityMainBinding
import edu.quinnipiac.ser210.ls06_network_nav.databinding.FragmentMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


/**
 * A simple [Fragment] subclass.
 * Use the [MainFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MainFragment : Fragment() {
    lateinit var recyclerView: RecyclerView
    lateinit var recyclerAdapter: RecyclerAdapter

    private var _binding: FragmentMainBinding? = null
    // This property is only valid between onCreateView and
// onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.recyclerview)
        recyclerAdapter = RecyclerAdapter(requireContext(), Navigation.findNavController(view))
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = recyclerAdapter


        val apiInterface = ApiInterface.create().getHeros()

        //apiInterface.enqueue( Callback<List<Movie>>())
        if (apiInterface != null) {
            apiInterface.enqueue( object : Callback<ArrayList<Hero?>?> {
                override fun onResponse(call: Call<ArrayList<Hero?>?>?, response: Response<ArrayList<Hero?>?>) {
                    if (response != null) {
                        Log.d("Main activity", response.body().toString())
                    }
                    if(response?.body() != null)
                        recyclerAdapter.setHerosListItems(response.body()!! as ArrayList<Hero>)
                }

                override fun onFailure(call: Call<ArrayList<Hero?>?>, t: Throwable) {
                    if (t != null) {
                        Toast.makeText(requireContext(), t.message,
                            Toast.LENGTH_SHORT).show()
                        t.message?.let { Log.d("onFailure", it) }
                    }
                }
            })
        }

    }
}