package edu.quinnipiac.ser210.ls06_network_nav

import android.content.Context

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
var heroList : ArrayList<Hero> = ArrayList()

class RecyclerAdapter(val context: Context,  var navController: NavController) : RecyclerView.Adapter<RecyclerAdapter.MyViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item,parent,false)
        return MyViewHolder(view, context)
    }

    override fun getItemCount(): Int {
        return heroList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {


            holder.bind(position)
    }

    fun setHerosListItems(heroListparam: ArrayList<Hero>){
        heroList = heroListparam;
        notifyDataSetChanged()
    }

    inner class MyViewHolder(itemView: View, private val context: Context) : RecyclerView.ViewHolder(itemView) {

        private val title: TextView = itemView!!.findViewById(R.id.item_title)
        private val image: ImageView = itemView!!.findViewById(R.id.item_image)
        private var pos:Int = 0

        init {
            itemView.setOnClickListener {
                val action = MainFragmentDirections.actionMainFragment2ToDetailFragment(pos)
                navController.navigate(action)

            }
        }
        fun bind(position:Int){
            pos = position
            val currHero = heroList.get(position)
            title.text = currHero.name
            Glide.with(context).load(currHero.imageurl)
                .apply(RequestOptions().centerCrop())
                .into(image)
        }

    }
}