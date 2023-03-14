package com.istu.schedule.ui.page.projfair.list

import androidx.compose.foundation.lazy.LazyListState
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.istu.schedule.domain.model.Project
import com.istu.schedule.domain.usecase.GetProjectsListUseCase
import com.istu.schedule.ui.components.base.BaseViewModel
import com.istu.schedule.util.addNewItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val useCase: GetProjectsListUseCase
) : BaseViewModel() {

    private val _projectsListUiState = MutableStateFlow(ProjectsListUiState())
    val projectsListUiState: StateFlow<ProjectsListUiState> = _projectsListUiState.asStateFlow()

    private val _projectsList = MutableLiveData<MutableList<Project>>()
    val projectsList: LiveData<MutableList<Project>> = _projectsList

    private var _currentPage = 0

    fun getProjectsList() {
        call({
            useCase.getProjectsList(_currentPage)
        }, onSuccess = {
            for (item in it) {
                _projectsList.addNewItem(item)
            }
            _currentPage += 1
        })
    }
}

data class ProjectsListUiState(
    val listState: LazyListState = LazyListState()
)