package com.der3.shared.data.mappers

import com.der3.shared.data.dto.MasbahaAzkarDto
import com.der3.shared.domain.model.MasbahaAzkar


fun MasbahaAzkarDto.toMasbahaAzkarDomainModel(): MasbahaAzkar{
    return MasbahaAzkar(
        id = this.id,
        text = this.text,
        count = this.count
    )
}