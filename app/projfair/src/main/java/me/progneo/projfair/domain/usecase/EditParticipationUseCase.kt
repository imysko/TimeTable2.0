package me.progneo.projfair.domain.usecase

import javax.inject.Inject
import me.progneo.projfair.domain.model.PriorityRequest
import me.progneo.projfair.domain.repository.ParticipationRepository

class EditParticipationUseCase @Inject constructor(
    private val participationRepository: ParticipationRepository
) {

    suspend operator fun invoke(
        token: String,
        participationId: Int,
        priority: PriorityRequest
    ): Result<Unit> {
        return participationRepository.editParticipation(
            token = token,
            participationId = participationId,
            priority = priority
        )
    }
}
