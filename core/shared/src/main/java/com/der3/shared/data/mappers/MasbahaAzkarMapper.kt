package com.der3.shared.data.mappers

import com.der3.shared.data.dto.MasbahaAzkarDto
import com.der3.shared.data.source.local.entity.MasbahaAzkarEntity
import com.der3.shared.domain.model.MasbahaAzkar

fun MasbahaAzkarDto.toMasbahaAzkarDomainModel(): MasbahaAzkar {
    return MasbahaAzkar(
        id = this.id,
        text = this.text,
        count = this.count
    )
}

fun MasbahaAzkarDto.toEntity(): MasbahaAzkarEntity {
    return MasbahaAzkarEntity(
        id = this.id,
        text = this.text,
        count = this.count
    )
}

fun MasbahaAzkarEntity.toDomain(): MasbahaAzkar {
    return MasbahaAzkar(
        id = this.id,
        text = this.text,
        count = this.count
    )
}

fun MasbahaAzkar.toEntity(): MasbahaAzkarEntity {
    return MasbahaAzkarEntity(
        id = this.id ?: -1,
        text = this.text ?: "",
        count = this.count ?: -1
    )
}