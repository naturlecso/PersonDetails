package hu.naturlecso.pdpd.features.persons.details

import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import hu.naturlecso.pdpd.R
import hu.naturlecso.pdpd.common.view.BaseFragment
import kotlinx.android.synthetic.main.fragment_persons.*
import kotlinx.android.synthetic.main.partial_person_details_contacts.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class PersonDetailsFragment : BaseFragment<PersonDetailsViewModel>() {
    override val layoutRes: Int = R.layout.fragment_person_details
    override val viewModel: PersonDetailsViewModel by viewModel()
    private val args: PersonDetailsFragmentArgs by navArgs()

    override fun afterViews() {
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
