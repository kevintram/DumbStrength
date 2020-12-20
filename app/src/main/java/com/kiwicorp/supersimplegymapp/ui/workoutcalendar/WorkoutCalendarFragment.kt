package com.kiwicorp.supersimplegymapp.ui.workoutcalendar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.kiwicorp.supersimplegymapp.EventObserver
import com.kiwicorp.supersimplegymapp.R
import com.kiwicorp.supersimplegymapp.data.WorkoutWithEntries
import com.kiwicorp.supersimplegymapp.databinding.CalendarDayBinding
import com.kiwicorp.supersimplegymapp.databinding.CalendarHeaderBinding
import com.kiwicorp.supersimplegymapp.databinding.FragmentWorkoutCalendarBinding
import com.kiwicorp.supersimplegymapp.ui.workouts.WorkoutsViewModel
import com.kiwicorp.supersimplegymapp.ui.workoutcalendar.WorkoutCalendarFragmentDirections.Companion.toWorkoutsFragment
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.model.CalendarMonth
import com.kizitonwose.calendarview.model.DayOwner
import com.kizitonwose.calendarview.ui.DayBinder
import com.kizitonwose.calendarview.ui.MonthHeaderFooterBinder
import com.kizitonwose.calendarview.ui.ViewContainer
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate
import java.time.YearMonth
import java.time.temporal.WeekFields
import java.util.*

@AndroidEntryPoint
class WorkoutCalendarFragment : Fragment() {

    private lateinit var binding: FragmentWorkoutCalendarBinding

    private val viewModel: WorkoutsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWorkoutCalendarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupCalendar()
        viewModel.workoutsGroupedByDate.observe(viewLifecycleOwner, Observer {
            binding.calendarView.notifyCalendarChanged()
        })
        binding.toolbar.setNavigationOnClickListener {
            viewModel.navigateToWorkoutsFragment()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.navigateToWorkoutsFragment.observe(viewLifecycleOwner, EventObserver {
            findNavController().navigateUp()
        })
    }

    private fun setupCalendar() {
        val currentMonth = YearMonth.now()
        val firstMonth = currentMonth.minusMonths(10)
        val firstDayOfWeek = WeekFields.of(Locale.getDefault()).firstDayOfWeek
        binding.calendarView.setup(firstMonth, currentMonth, firstDayOfWeek)
        binding.calendarView.scrollToMonth(currentMonth)

        class DayViewContainer(view: View): ViewContainer(view) {
            lateinit var day: CalendarDay
            val binding = CalendarDayBinding.bind(view)
            val textView = binding.calendarDayText
            val bg = binding.calendarDayBg

            init {
                view.setOnClickListener {
                    viewModel.navigateToWorkoutsFragmentAndScrollToDate(day.date)
                }
            }
        }

        binding.calendarView.dayBinder = object : DayBinder<DayViewContainer> {
            override fun create(view: View): DayViewContainer = DayViewContainer(view)

            override fun bind(container: DayViewContainer, day: CalendarDay) {
                container.day = day
                val textView = container.textView
                val bg = container.bg

                textView.text = day.date.dayOfMonth.toString()

                val workouts = viewModel.workoutsGroupedByDate.value?.get(day.date)

                if (day.owner == DayOwner.THIS_MONTH) {
                    textView.visibility = View.VISIBLE

                    if (workouts != null) {
                        bg.setBackgroundResource(R.drawable.calendar_selected)
                        container.view.isClickable = true
                    } else {
                        bg.background = null
                        container.view.isClickable = false
                    }
                } else {
                    textView.visibility = View.INVISIBLE
                    bg.background = null
                }
            }
        }

        class MonthViewContainer(view: View): ViewContainer(view) {
            val binding = CalendarHeaderBinding.bind(view)

            val monthText = binding.monthText
            val yearText = binding.yearText
        }

        binding.calendarView.monthHeaderBinder = object :
            MonthHeaderFooterBinder<MonthViewContainer> {
            override fun create(view: View) = MonthViewContainer(view)
            override fun bind(container: MonthViewContainer, month: CalendarMonth) {
                container.monthText.text = "${month.yearMonth.month.name.toLowerCase().capitalize()}"
                container.yearText.text = month.year.toString()
            }
        }
    }

}