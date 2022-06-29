package com.example.timetable.ui.exams

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.timetable.R
import com.example.timetable.databinding.FragmentExamsBinding
import com.example.timetable.ui.factory.ExamsViewModelFactory
import com.example.timetable.repositories.ExamFirebaseRepository
import com.example.timetable.ui.adapters.ExamListAdapter
import kotlinx.android.synthetic.main.fragment_exams.*

class ExamsFragment : Fragment() {

    private val _repository = ExamFirebaseRepository()
    private val _examsViewModel: ExamsViewModel by viewModels {ExamsViewModelFactory(_repository)}
    private var _binding: FragmentExamsBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentExamsBinding.inflate(inflater, container, false)
            .apply { viewmodel = _examsViewModel }
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListAdapter()

        _examsViewModel.updateGroup("ИСТб-20-2", "Институт информационных технологий и анализа данных")
        //_examsViewModel.updateGroup("ЭПЭБ-20-1", "Институт экономики, управления и права")
    }

    private fun setupListAdapter() {
        _examsViewModel.examList.observe(viewLifecycleOwner, Observer { exams ->
            exams_recycle_view.also {
                it.layoutManager = LinearLayoutManager(requireContext())
                it.setHasFixedSize(false)
                it.adapter = ExamListAdapter(exams)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

@BindingAdapter("bind_exams_header")
fun TextView.bindExamHeader(companion: String) {
    this.text = resources.getString(R.string.exams_header, companion)
}