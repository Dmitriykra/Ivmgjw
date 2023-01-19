package cu.ivmgjw.duwvscmwp.ui.start

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import cu.ivmgjw.duwvscmwp.Const
import cu.ivmgjw.duwvscmwp.api.ApiInterface
import cu.ivmgjw.duwvscmwp.api.ServiceUtils
import cu.ivmgjw.duwvscmwp.databinding.FragmentStartBinding
import cu.ivmgjw.duwvscmwp.gist_model.GistModel
import cu.ivmgjw.duwvscmwp.ui.home.HomeFragment
import cu.ivmgjw.duwvscmwp.webview.NetworkChecker.isNetworkAvailable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class StartFragment() : Fragment() {
    private lateinit var binding: FragmentStartBinding
    private lateinit var ctx: Context
    var gistResultDefault: Boolean = false
    override fun onAttach(context: Context) {
        super.onAttach(context)
        ctx = context
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentStartBinding.inflate(layoutInflater)
        Window.FEATURE_NO_TITLE
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        gistChecker(gistResultDefault)
    }



    fun gistChecker(gistResultDefault: Boolean) {
            lifecycleScope.launch(Dispatchers.IO) {
                val gist = ServiceUtils.getGist().create(ApiInterface::class.java).currenciesGist()
                withContext(Dispatchers.Main) {
                    val gistResult: GistModel? = gist.body()
                    Log.d("TAG", "jioer: ${gistResult}")
                    if (gistResult != null) {
                        if (gistResult.enabled) {
                            openWebView()
                        }

                        if(gistResultDefault){
                            openWebView()
                        }

                        moveToHomeFragment()

                    } else {
                        Toast.makeText(
                            ctx,
                            "Check your internet connection, please",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
    }

    fun openWebView(){
        binding.webView.settings.javaScriptEnabled
        binding.webView.settings.javaScriptCanOpenWindowsAutomatically
        binding.webView.settings.loadWithOverviewMode
        binding.webView.webViewClient = WebViewClient()
        binding.webView.loadUrl(Const.WEB_VIEW)
    }

    private fun moveToHomeFragment() {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(binding.startFr.id, HomeFragment())
            .commit()

    }

}

