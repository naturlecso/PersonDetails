package hu.naturlecso.pdpd.features.persons.list

import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import hu.naturlecso.pdpd.R
import hu.naturlecso.pdpd.common.view.BaseFragment
import kotlinx.android.synthetic.main.fragment_persons.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class PersonsFragment : BaseFragment<PersonsViewModel>() {
    override val layoutRes: Int = R.layout.fragment_persons
    override val viewModel: PersonsViewModel by viewModel()

    override fun afterViews() {
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
