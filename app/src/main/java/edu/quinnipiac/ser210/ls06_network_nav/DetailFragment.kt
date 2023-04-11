package edu.quinnipiac.ser210.ls06_network_nav

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import edu.quinnipiac.ser210.ls06_network_nav.databinding.ActivityMainBinding
import edu.quinnipiac.ser210.ls06_network_nav.databinding.FragmentDetailBinding


/**
 * A simple [Fragment] subclass.
 * Use the [DetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DetailFragment : Fragment() {


     var hero_id: Int = 0

    private var _binding: FragmentDetailBinding? = null
    // This property is only valid between onCreateView and
// onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //   recipient = arguments!!.getString("recipient")
        val bundle = arguments
        if (bundle == null) {
            Log.e("DetailFragment", "DetailFragment did not receive hero id")

            return
        }
        hero_id = DetailFragmentArgs.fromBundle(bundle).heroId

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.name.text = heroList.get(hero_id).name
        binding.realName.text = heroList.get(hero_id).realname
        binding.bio.text = heroList.get(hero_id).bio
        binding.firstApperance.text = heroList.get(hero_id).firstappearance
        Glide.with(requireContext()).load(heroList.get(hero_id).imageurl)
            .apply(RequestOptions().centerCrop())
            .into(binding.itemImage)

    }
}