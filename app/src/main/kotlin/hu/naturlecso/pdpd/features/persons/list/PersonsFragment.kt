package hu.naturlecso.pdpd.features.persons.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import hu.naturlecso.pdpd.R
import hu.naturlecso.pdpd.databinding.FragmentPersonsBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlinx.android.synthetic.main.fragment_persons.*

class PersonsFragment : Fragment() {
    private val viewModel: PersonsViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: FragmentPersonsBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_persons, container, false)

        binding.lifecycleOwner = this
        binding.vm = viewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).setSupportActionBar(toolbar)

        personList.apply {
            adapter = PersonAdapter()
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity)
            addItemDecoration(DividerItemDecoration(activity, LinearLayoutManager.VERTICAL).apply {
                setDrawable(requireNotNull(ContextCompat.getDrawable(context, R.drawable.divider_personlist)))
            })
        }
    }
}
