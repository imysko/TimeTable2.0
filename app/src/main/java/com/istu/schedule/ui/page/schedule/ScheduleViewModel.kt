package com.istu.schedule.ui.page.schedule

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.istu.schedule.data.enums.UserStatus
import com.istu.schedule.data.model.User
import com.istu.schedule.domain.model.schedule.StudyDay
import com.istu.schedule.domain.usecase.schedule.GetGroupScheduleOnDayUseCase
import com.istu.schedule.domain.usecase.schedule.GetTeacherScheduleOnDayUseCase
import com.istu.schedule.ui.components.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class ScheduleViewModel @Inject constructor(
    private val _useCaseGroupScheduleOnDay: GetGroupScheduleOnDayUseCase,
    private val _useCaseTeacherScheduleOnDay: GetTeacherScheduleOnDayUseCase,
    private val _user: User
) : BaseViewModel() {

    private val _scheduleUiState = MutableStateFlow(ScheduleUiState()).apply {
        update {
            it.copy(
                userDescription = _user.userDescription
            )
        }
    }
    val scheduleUiState: StateFlow<ScheduleUiState> = _scheduleUiState.asStateFlow()

    private val _scheduleList = MutableLiveData<List<StudyDay>>()
    val scheduleList: LiveData<List<StudyDay>> = _scheduleList

    private val _selectedDate = MutableLiveData<LocalDate>()
    val selectedDate: LiveData<LocalDate> = _selectedDate

    init {
        getSchedule()
    }

    private fun getSchedule() {
        val date = LocalDate.of(2023, 3, 31)

        when (_user.userType) {
            UserStatus.STUDENT -> {
                call({
                    _useCaseGroupScheduleOnDay.getGroupScheduleOnDay(
                        groupId = _user.userId!!,
                        dateString = date.toString())
                }, onSuccess = {
                    _scheduleList.postValue(it)
                })
            }
            UserStatus.TEACHER -> {
                call({
                    _useCaseTeacherScheduleOnDay.getTeacherScheduleOnDay(
                        teacherId = _user.userId!!,
                        dateString = date.toString())
                }, onSuccess = {
                    _scheduleList.postValue(it)
                })
            }
            else -> { }
        }
    }
}

data class ScheduleUiState(
    val userDescription: String? = null
)