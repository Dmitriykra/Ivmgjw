package cu.ivmgjw.duwvscmwp.ui.start

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowInsets
import android.view.WindowManager
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import cu.ivmgjw.duwvscmwp.Const
import cu.ivmgjw.duwvscmwp.MainActivity
import cu.ivmgjw.duwvscmwp.api.ApiInterface
import cu.ivmgjw.duwvscmwp.api.ServiceUtils
import cu.ivmgjw.duwvscmwp.databinding.ActivityStartAppBinding
import cu.ivmgjw.duwvscmwp.gist_model.GistModel
import cu.ivmgjw.duwvscmwp.ui.home.HomeFragment
import cu.ivmgjw.duwvscmwp.webview.NetworkChecker
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.ConnectException

class StartAppActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityStartAppBinding = ActivityStartAppBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //gistChecker(binding)
        //hideTitleBar()
        //moveToHomeFragment()

    }


}