package hu.naturlecso.pdpd.features.startup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import hu.naturlecso.pdpd.R
import hu.naturlecso.pdpd.databinding.FragmentSplashBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashFragment: Fragment() {
    private val viewModel: SplashViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: FragmentSplashBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_splash, container, false)

        binding.lifecycleOwner = this
        binding.vm = viewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.refreshPersonsAndNavigateCommand.execute()
    }

    override fun onStart() {
        super.onStart()
        (activity as AppCompatActivity).supportActionBar?.hide()
    }

    override fun onStop() {
        (activity as AppCompatActivity).supportActionBar?.show()
        super.onStop()
    }
}
