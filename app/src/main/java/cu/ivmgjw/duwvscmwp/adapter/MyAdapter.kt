package cu.ivmgjw.duwvscmwp.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import cu.ivmgjw.duwvscmwp.Const
import cu.ivmgjw.duwvscmwp.R
import cu.ivmgjw.duwvscmwp.databinding.CurrencyLayoutBinding
import cu.ivmgjw.duwvscmwp.models.CryptoCurrency

class MyAdapter(
    var context: Context,
    val list: List<CryptoCurrency>,
    val listExchange: Double
):
    RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    private lateinit var mListener: onItemClickListener
    //interface for item click
    interface onItemClickListener {
        fun onItemClick(position: Int)
    }
    inner class MyViewHolder(view: View, listener: onItemClickListener): RecyclerView.ViewHolder(view){
        var binding = CurrencyLayoutBinding.bind(view)
        init {
            itemView.setOnClickListener{
                listener.onItemClick(adapterPosition)
            }
        }
    }

    fun setOnItemClickListener(listener: onItemClickListener){
        mListener = listener
        //go to myviewholder and init this
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.currency_layout, parent,false), mListener)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = list[position]
        for(i in item.quotes.indices) {
            holder.binding.currencyName.text = item.name
            holder.binding.price.text = String.format("%.02f", item.quotes[i].price * listExchange)
            holder.binding.percent.text = String.format("%.02f", item.quotes[i].percentChange24h)+"%"
            Glide.with(context)
                .load(Const.IMG + item.id + Const.PNG)
                .thumbnail(Glide.with(context).load(R.raw.loading))
                .into(
                    holder.binding.currencyLogo
                )
        }
    }
}
