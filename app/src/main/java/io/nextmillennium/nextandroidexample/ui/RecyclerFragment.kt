package io.nextmillennium.nextandroidexample.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import io.nextmillennium.nextandroidexample.R
import io.nextmillennium.nextandroidexample.data.PeopleAdapter
import io.nextmillennium.nextandroidexample.data.Person
import io.nextmillennium.nextandroidexample.databinding.FragmentRecyclerBinding
import java.util.*

private const val ARG_UNIT = "unit_id"

/**
 * A simple [Fragment] subclass.
 * Use the [RecyclerFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RecyclerFragment : Fragment() {
    private var unitId: String? = null
    private lateinit var binding: FragmentRecyclerBinding

    private val names = listOf(
        "Lucille A. Jackson",
        "Nathaniel M. Bishop",
        "Richard B. Weiss",
        "Bernice L. Miller",
        "Kevin M. Lucas",
        "Sandra M. Vargas",
        "Judith D. Hunt",
        "Floyd M. Sherman",
        "John B. Parker",
        "Beth E. Lord",
        "John S. Hardin",
        "James E. Bianco",
        "Rashad D. Santana",
        "Stanley M. Hernandez",
        "Minerva Y. Saephan",
        "Cecelia D. Gadd",
        "Jeff J. Merrill",
        "Alvin J. Ford",
        "John R. Green",
        "Margie A. Council",
        "Dale E. Fearon"
    )

    private val banners = mutableMapOf(
        "banner" to "",
        "banner_mrec" to "",
        "banner_anchored" to "",
    )

    private fun createPeopleList(): List<Person> {
        val people = mutableListOf<Person>()
        for (i in 0..600) {
            val person = Person(
                "https://placeimg.com/200/200/people",
                names[Random().nextInt(names.size)],
                "Some description for person ${i + 1}"
            )
            people.add(person)
        }
        return people
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { unitId = it.getString(ARG_UNIT) }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRecyclerBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val preferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
        for (pair in banners) {
            banners[pair.key] = preferences.getString(pair.key, "103") ?: "103"
        }
        binding.peopleList.adapter = PeopleAdapter(createPeopleList(), banners.values.toList())
    }

    companion object {
        /**
         * @param unitId Unit id in Next Millennium Ads SDK
         * @return A new instance of fragment RecyclerFragment.
         */
        @JvmStatic
        fun newInstance(unitId: String) =
            RecyclerFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_UNIT, unitId)
                }
            }
    }
}