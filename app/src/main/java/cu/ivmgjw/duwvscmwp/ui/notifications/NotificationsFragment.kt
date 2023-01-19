package cu.ivmgjw.duwvscmwp.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.app.ActivityCompat.finishAffinity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import cu.ivmgjw.duwvscmwp.databinding.FragmentNotificationsBinding
import kotlin.system.exitProcess

class NotificationsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
            //exitProcess(-1)
            activity?.finish()
        return null
    }
}