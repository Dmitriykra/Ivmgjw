package cu.ivmgjw.duwvscmwp.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import cu.ivmgjw.duwvscmwp.adapter.MyAdapter
import cu.ivmgjw.duwvscmwp.api.ApiInterface
import cu.ivmgjw.duwvscmwp.api.ExchangeInterface
import cu.ivmgjw.duwvscmwp.api.ExchangeUtils
import cu.ivmgjw.duwvscmwp.api.ServiceUtils
import cu.ivmgjw.duwvscmwp.databinding.FragmentHomeBinding
import cu.ivmgjw.duwvscmwp.modelExchange.Rates
import cu.ivmgjw.duwvscmwp.price_dialog.CustomFragmentDialog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
       binding = FragmentHomeBinding.inflate(layoutInflater)
       getExchangeList()
       return binding.root
    }

    private fun getExchangeList() {
        lifecycleScope.launch(Dispatchers.IO){
            val exchange = ExchangeUtils.getExchange().create(ExchangeInterface::class.java).exchangeData()
            withContext(Dispatchers.Main){
                Log.d("TAG", "getExchangeList: binding.uahCb.isChecked")
                val listExchange = exchange.body()!!.rates

                checkBoxClicked(listExchange)
            }
        }
    }

    private fun getCurrencyList(listExchange: Double) {
       lifecycleScope.launch(Dispatchers.IO){
           val res = ServiceUtils.getInstance().create(ApiInterface::class.java).currenciesData()
           withContext(Dispatchers.Main){
               val adapter = MyAdapter(requireContext(), res.body()!!.data.cryptoCurrencyList, listExchange)
               binding.recyclerView.adapter = adapter
               adapter.setOnItemClickListener(object: MyAdapter.onItemClickListener{
                   override fun onItemClick(position: Int) {

                       val dialogFragment = CustomFragmentDialog()
                       val currency = adapter.list[position].name
                       val week = String.format("%.02f",adapter.list[position].quotes[0].percentChange7d)
                       val month = String.format("%.02f",adapter.list[position].quotes[0].percentChange30d)

                       val args = Bundle()
                       args.putString("currency", currency)
                       args.putString("week", week)
                       args.putString("month", month)
                       dialogFragment.arguments = args

                       dialogFragment.show(requireActivity().supportFragmentManager, "currency")
                   }
               })
           }
       }
    }

    fun checkBoxClicked(listExchange: Rates) {
        if(!binding.uahCb.isChecked){
            binding.uahCb.setOnClickListener {
                binding.euCb.isChecked = false
                binding.usCb.isChecked = false
                binding.pzlCb.isChecked = false
                binding.uahCb.isChecked = true
                getCurrencyList(listExchange.UAH)
            }
        }
        if (!binding.euCb.isChecked) {
            binding.euCb.setOnClickListener {
                binding.uahCb.isChecked = false
                binding.usCb.isChecked = false
                binding.pzlCb.isChecked = false
                binding.euCb.isChecked = true
                getCurrencyList(listExchange.EUR-(listExchange.USD-listExchange.EUR))
            }
        }
        if (binding.usCb.isChecked) {
            getCurrencyList(1.0)
            binding.usCb.setOnClickListener {
                binding.uahCb.isChecked = false
                binding.euCb.isChecked = false
                binding.pzlCb.isChecked = false
                binding.usCb.isChecked = true
                getCurrencyList(1.0)
            }
        }
        if (!binding.pzlCb.isChecked) {
            binding.pzlCb.setOnClickListener {
                binding.uahCb.isChecked = false
                binding.euCb.isChecked = false
                binding.usCb.isChecked = false
                binding.pzlCb.isChecked = true
                getCurrencyList(listExchange.PLN)
            }
        }

    }
}