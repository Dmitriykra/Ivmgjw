package cu.ivmgjw.duwvscmwp.ui.home

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
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
import cu.ivmgjw.duwvscmwp.webview.NetworkChecker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class HomeFragment : Fragment() {

    //private val binding: FragmentHomeBinding
    private lateinit var ctx: Context
    override fun onAttach(context: Context) {
        super.onAttach(context)
        ctx = context
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentHomeBinding.inflate(layoutInflater)
        binding.progressBar.isVisible = true


        if(NetworkChecker.isNetworkAvailable(ctx)) {
            getExchangeList(binding)
        } else {
            Toast.makeText(
                ctx,
                "Check your internet connection, please",
                Toast.LENGTH_SHORT
            ).show()
        }
       return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    private fun getExchangeList(binding: FragmentHomeBinding) {
        lifecycleScope.launch(Dispatchers.IO){
            val exchange = ExchangeUtils.getExchange().create(ExchangeInterface::class.java).exchangeData()
            withContext(Dispatchers.Main){
                val listExchange = exchange.body()!!.rates
                checkBoxClicked(listExchange, binding)
            }
        }
    }

    @SuppressLint("SuspiciousIndentation")
    private fun getCurrencyList(listExchange: Double, binding: FragmentHomeBinding) {
       lifecycleScope.launch(Dispatchers.IO){
           val res = ServiceUtils.getInstance().create(ApiInterface::class.java).currenciesData()
               withContext(Dispatchers.Main) {
                   if(res.isSuccessful) {
                       binding.progressBar.isVisible = false
                   val adapter = MyAdapter(
                       requireContext(),
                       res.body()!!.data.cryptoCurrencyList,
                       listExchange
                   )
                   binding.recyclerView.adapter = adapter
                   adapter.setOnItemClickListener(object : MyAdapter.onItemClickListener {
                       override fun onItemClick(position: Int) {

                           val dialogFragment = CustomFragmentDialog()
                           val currency = adapter.list[position].name
                           val week = String.format(
                               "%.02f",
                               adapter.list[position].quotes[0].percentChange7d
                           )
                           val month = String.format(
                               "%.02f",
                               adapter.list[position].quotes[0].percentChange30d
                           )

                           val args = Bundle()
                           args.putString("currency", currency)
                           args.putString("week", week)
                           args.putString("month", month)
                           dialogFragment.arguments = args

                           dialogFragment.show(requireActivity().supportFragmentManager, "currency")
                       }
                   })
               } else {
                       Handler(Looper.getMainLooper()).postDelayed({
                           Toast.makeText(
                               context,
                               "Check your internet connection, please",
                               Toast.LENGTH_SHORT
                           ).show()
                       }, 1000)
                   }
           }
       }
    }

    fun checkBoxClicked(listExchange: Rates, binding: FragmentHomeBinding) {
        if(!binding.uahCb.isChecked){
            binding.uahCb.setOnClickListener {
                binding.euCb.isChecked = false
                binding.usCb.isChecked = false
                binding.pzlCb.isChecked = false
                binding.uahCb.isChecked = true
                getCurrencyList(listExchange.UAH, binding)
            }
        }
        if (!binding.euCb.isChecked) {
            binding.euCb.setOnClickListener {
                binding.uahCb.isChecked = false
                binding.usCb.isChecked = false
                binding.pzlCb.isChecked = false
                binding.euCb.isChecked = true
                getCurrencyList(listExchange.EUR-(listExchange.USD-listExchange.EUR), binding)
            }
        }
        if (binding.usCb.isChecked) {
            getCurrencyList(1.0, binding)
            binding.usCb.setOnClickListener {
                binding.uahCb.isChecked = false
                binding.euCb.isChecked = false
                binding.pzlCb.isChecked = false
                binding.usCb.isChecked = true
                getCurrencyList(1.0, binding)
            }
        }
        if (!binding.pzlCb.isChecked) {
            binding.pzlCb.setOnClickListener {
                binding.uahCb.isChecked = false
                binding.euCb.isChecked = false
                binding.usCb.isChecked = false
                binding.pzlCb.isChecked = true
                getCurrencyList(listExchange.PLN, binding)
            }
        }

    }
}