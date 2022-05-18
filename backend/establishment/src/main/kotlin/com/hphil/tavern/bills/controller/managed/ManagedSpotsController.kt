package com.hphil.tavern.bills.controller.managed

import com.hphil.tavern.bills.domain.Spot
import com.hphil.tavern.bills.domain.SpotGroup
import com.hphil.tavern.bills.repository.EstablishmentRepository
import com.hphil.tavern.bills.repository.SpotGroupRepository
import com.hphil.tavern.bills.repository.SpotRepository
import com.hphil.tavern.bills.services.FileStorage
import com.hphil.tavern.bills.services.security.UserInfo
import org.hashids.Hashids
import org.springframework.beans.factory.annotation.Value
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/managed/spots")
class ManagedSpotsController(
    val establishmentRepository: EstablishmentRepository,
    val spotRepository: SpotRepository,
    val spotGroupRepository: SpotGroupRepository,
    val hashIds: Hashids,
    val fileStorage: FileStorage
) : ManagedEstablishmentTrait {
    @Value("\${app.backofficeUrl}")
    private val backofficeUrl = "http://localhost:3000"

    @PostMapping
    @Transactional
    fun addSpot(@RequestBody groupRequest: AddSpotGroupRequest, userInfo: UserInfo) {
        val group = spotGroupRepository.save(
            SpotGroup(
                getEstablishment(establishmentRepository, userInfo),
                groupRequest.name
            )
        )
        (1..groupRequest.amount).forEach {
            saveSpotQrCode(group, it)
        }
    }

    @GetMapping
    fun findAll(userInfo: UserInfo): AllSpotsResponse {
        return AllSpotsResponse(
            spotGroupRepository
                .findAllByEstablishment(getEstablishment(establishmentRepository, userInfo))
                .map { SpotGroupDto(it, spotRepository.countByGroup(it)) }
        )
    }

    @PutMapping("/{groupId}")
    @Transactional
    fun update(
        @RequestBody groupRequest: UpdateSpotGroupRequest,
        @PathVariable groupId: Long,
        userInfo: UserInfo
    ) {
        val group = spotGroupRepository.findById(groupId).orElseThrow()
        validateManager(group.establishment, userInfo)
        group.name = groupRequest.name
        group.active = groupRequest.active

        val countSpots = spotRepository.countByGroup(group)
        if (groupRequest.amount > countSpots) {
            ((countSpots + 1)..groupRequest.amount).forEach {
                saveSpotQrCode(group, it)
            }
        } else if (groupRequest.amount < countSpots) {
            ((groupRequest.amount + 1)..countSpots).forEach {
                spotRepository.deleteByGroupAndNumber(group, it)
            }
        }
    }

    private fun saveSpotQrCode(group: SpotGroup, sequence: Int) {
        val spot = spotRepository.saveAndFlush(
            Spot(group, sequence)
        )
        val establishmentHash = hashIds.encode(spot.group.establishment.id!!)
        val spotHash = hashIds.encode(spot.id!!)
        spot.qrCode = "$establishmentHash.$spotHash"
//        fileStorage.generateAndStoreQrCodeImage("$backofficeUrl/e=$establishmentHash&s=$spotHash", spot.qrCode!!)
    }
}

class AddSpotGroupRequest(val name: String, val amount: Int)

class AllSpotsResponse(val groups: List<SpotGroupDto>)
class SpotGroupDto(val id: Long, val name: String, val active: Boolean, val amount: Int) {
    constructor(group: SpotGroup, amount: Int) : this(group.id!!, group.name, group.active, amount)
}

class UpdateSpotGroupRequest(val name: String, val amount: Int, val active: Boolean)
