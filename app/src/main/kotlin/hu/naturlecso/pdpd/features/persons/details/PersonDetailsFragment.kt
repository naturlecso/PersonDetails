package hu.naturlecso.pdpd.features.persons.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import hu.naturlecso.pdpd.R
import hu.naturlecso.pdpd.databinding.FragmentPersonDetailsBinding
import kotlinx.android.synthetic.main.fragment_persons.*
import kotlinx.android.synthetic.main.partial_person_details_contacts.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class PersonDetailsFragment : Fragment() {
    private val viewModel: PersonDetailsViewModel by viewModel()
    private val args: PersonDetailsFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: FragmentPersonDetailsBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_person_details, container, false)

        binding.lifecycleOwner = this
        binding.vm = viewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.setPersonId(args.personId)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        contactList.apply {
            adapter = ContactAdapter()
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity)
            isNestedScrollingEnabled = false
        }
    }
}
