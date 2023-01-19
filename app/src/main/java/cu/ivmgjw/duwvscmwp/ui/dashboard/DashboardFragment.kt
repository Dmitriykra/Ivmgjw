package cu.ivmgjw.duwvscmwp.ui.dashboard

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import cu.ivmgjw.duwvscmwp.Const
import cu.ivmgjw.duwvscmwp.databinding.FragmentDashboardBinding


class DashboardFragment : Fragment() {
    private lateinit var binding: FragmentDashboardBinding
    lateinit var ctx: Context
    override fun onAttach(context: Context) {
        super.onAttach(context)
        ctx = context
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDashboardBinding.inflate(layoutInflater)
        moveToWebView()
        return binding.root
    }

    private fun moveToWebView(){
        binding.webViewDash.settings.javaScriptEnabled
        binding.webViewDash.settings.javaScriptCanOpenWindowsAutomatically
        binding.webViewDash.settings.loadWithOverviewMode
        binding.webViewDash.webViewClient = WebViewClient()
        binding.webViewDash.loadUrl(Const.WEB_VIEW)
        binding.webViewDash.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, progress: Int) {
                Log.e("progress", "" + progress)
                if (progress == 100) {
                    binding.progressBar.isVisible = false
                }
            }
        }
    }
}