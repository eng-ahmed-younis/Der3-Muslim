package com.der3.shared.data.mappers

import com.der3.shared.data.dto.MasbahaAzkarDto
import com.der3.shared.data.source.local.entity.MasbahaAzkarEntity
import com.der3.shared.domain.model.MasbahaAzkar
import org.junit.Assert.assertEquals
import org.junit.Test

class MasbahaAzkarMapperTest {

    @Test
    fun `toMasbahaAzkarDomainModel converts DTO to Domain model correctly`() {
        val dto = MasbahaAzkarDto(id = 1, text = "سبحان الله", count = 33)
        val domain = dto.toMasbahaAzkarDomainModel()

        assertEquals(1, domain.id)
        assertEquals("سبحان الله", domain.text)
        assertEquals(33, domain.count)
    }

    @Test
    fun `toEntity converts DTO to Entity correctly`() {
        val dto = MasbahaAzkarDto(id = 2, text = "الحمد لله", count = 33)
        val entity = dto.toEntity()

        assertEquals(2, entity.id)
        assertEquals("الحمد لله", entity.text)
        assertEquals(33, entity.count)
    }

    @Test
    fun `toDomain converts Entity to Domain model correctly`() {
        val entity = MasbahaAzkarEntity(id = 3, text = "الله أكبر", count = 34)
        val domain = entity.toDomain()

        assertEquals(3, domain.id)
        assertEquals("الله أكبر", domain.text)
        assertEquals(34, domain.count)
    }

    @Test
    fun `toEntity converts Domain model to Entity correctly`() {
        val domain = MasbahaAzkar(id = 4, text = "لا إله إلا الله", count = 100)
        val entity = domain.toEntity()

        assertEquals(4, entity.id)
        assertEquals("لا إله إلا الله", entity.text)
        assertEquals(100, entity.count)
    }
}
