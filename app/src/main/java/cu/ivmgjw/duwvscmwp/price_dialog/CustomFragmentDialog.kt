package cu.ivmgjw.duwvscmwp.price_dialog

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import cu.ivmgjw.duwvscmwp.databinding.FragmentCustomDialogBinding

class CustomFragmentDialog : DialogFragment(){

    // This property is only valid between onCreateDialog and
    // onDestroyView.
    private lateinit var binding: FragmentCustomDialogBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = FragmentCustomDialogBinding.inflate(LayoutInflater.from(context))
        return AlertDialog.Builder(requireActivity())
            .setView(binding.root)
            .create()
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val currency = arguments?.getString("currency")
        val week = arguments?.getString("week")
        val month = arguments?.getString("month")
        binding.currencyNameDialog.text = currency
        binding.dialogWeek.text = "Average grow for last week: $week%"
        binding.dialogMonth.text = "Average grow for last month: $month%"

        return binding.root
    }
}