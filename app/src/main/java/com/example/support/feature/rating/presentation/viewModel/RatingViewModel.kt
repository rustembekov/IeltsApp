package com.example.support.feature.rating.presentation.viewModel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.support.core.BaseViewModel
import com.example.support.feature.rating.presentation.repository.RatingRepository
import com.example.support.core.util.ResultCore
import com.example.support.feature.rating.model.RatingResult
import com.example.support.feature.rating.model.RatingState
import com.example.support.feature.rating.presentation.usecase.FetchAvatarsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RatingViewModel @Inject constructor(
    private val ratingRepository: RatingRepository,
    private val fetchAvatarsUseCase: FetchAvatarsUseCase
) : BaseViewModel<RatingState, RatingResult>(RatingState()), RatingController {

    init {
        observeUsers()
    }

    private fun observeUsers() {
        viewModelScope.launch {
            ratingRepository.updateUserRanks()
            delay(500)
            loadUsers()
        }
    }


    override fun loadUsers() {
        updateState(RatingState(result = RatingResult.Loading))

        viewModelScope.launch {
            when (val result = ratingRepository.getUsersByRating()) {
                is ResultCore.Success -> {
                    val users = result.data
                    val avatarMap = fetchAvatarsUseCase.execute(users.take(10)) // lazy load more later

                    updateState(
                        RatingState(
                            players = users,
                            avatars = avatarMap,
                            result = RatingResult.Success
                        )
                    )

                    launch {
                        fetchAvatarsUseCase.execute(users.drop(10))
                    }

                }

                is ResultCore.Failure -> {
                    updateState(RatingState(result = RatingResult.Error(result.message)))
                }
            }
        }
    }

    override fun onEvent(event: RatingResult) {
        TODO("Not yet implemented")
    }

}
