package cu.ivmgjw.duwvscmwp.ui.start

import android.R
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomnavigation.BottomNavigationView
import cu.ivmgjw.duwvscmwp.Const
import cu.ivmgjw.duwvscmwp.api.ApiInterface
import cu.ivmgjw.duwvscmwp.api.ServiceUtils
import cu.ivmgjw.duwvscmwp.databinding.FragmentStartBinding
import cu.ivmgjw.duwvscmwp.gist_model.GistModel
import cu.ivmgjw.duwvscmwp.ui.home.HomeFragment
import cu.ivmgjw.duwvscmwp.webview.NetworkChecker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class StartFragment() : Fragment() {
    private lateinit var ctx: Context
    override fun onAttach(context: Context) {
        super.onAttach(context)
        ctx = context
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val binding = FragmentStartBinding.inflate(layoutInflater)
        gistChecker(binding)
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
        Window.FEATURE_NO_TITLE
        return binding.root
    }

    fun gistChecker(binding: FragmentStartBinding) {
        Log.d("TAG", "onCreateViewer: ${NetworkChecker.isNetworkAvailable(ctx)}")
        if(NetworkChecker.isNetworkAvailable(ctx)) {
            lifecycleScope.launch(Dispatchers.IO) {
                val gist = ServiceUtils.getGist().create(ApiInterface::class.java).currenciesGist()
                withContext(Dispatchers.Main) {
                    if (gist.isSuccessful) {
                        val gistResult: GistModel? = gist.body()
                        if (gistResult != null) {
                            binding.progressBar.isVisible = false
                            if (gistResult.enabled) {
                                openWebView(binding)
                            } else {
                                moveToHomeFragment(binding)

                            }
                        }
                    } else {
                        moveToHomeFragment(binding)

                    }
                }
            }
        } else {
            moveToHomeFragment(binding)
        }
    }

    fun openWebView(binding: FragmentStartBinding){
        binding.webView.settings.javaScriptEnabled
        binding.webView.settings.javaScriptCanOpenWindowsAutomatically
        binding.webView.settings.loadWithOverviewMode
        binding.webView.webViewClient = WebViewClient()
        binding.webView.loadUrl(Const.WEB_VIEW)
    }

    private fun moveToHomeFragment(binding: FragmentStartBinding) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(binding.startFr.id, HomeFragment())
            .disallowAddToBackStack()
            .commit()
    }

}

