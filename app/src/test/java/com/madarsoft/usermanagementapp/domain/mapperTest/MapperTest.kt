package com.madarsoft.usermanagementapp.domain.mapperTest

import com.madarsoft.usermanagementapp.data.entity.UserEntity
import com.madarsoft.usermanagementapp.domain.mapper.toEntity
import com.madarsoft.usermanagementapp.domain.mapper.toModel
import com.madarsoft.usermanagementapp.domain.model.UserModel
import org.junit.Test
import org.junit.Assert.assertEquals

class MapperTest {

    @Test
    fun `userEntity to userModel mapping should work correctly`() {
        // Given
        val userEntity = UserEntity(
            id = 1,
            name = "John Doe",
            age = 30,
            jobTitle = "Developer",
            gender = "Male"
        )

        // When
        val userModel = userEntity.toModel()

        // Then
        assertEquals(userEntity.id, userModel.id)
        assertEquals(userEntity.name, userModel.name)
        assertEquals(userEntity.age, userModel.age)
        assertEquals(userEntity.jobTitle, userModel.jobTitle)
        assertEquals(userEntity.gender, userModel.gender)
    }

    @Test
    fun `userModel to userEntity mapping should work correctly`() {
        // Given
        val userModel = UserModel(
            id = 2,
            name = "Jane Smith",
            age = 25,
            jobTitle = "Designer",
            gender = "Female"
        )

        // When
        val userEntity = userModel.toEntity()

        // Then
        assertEquals(userModel.id, userEntity.id)
        assertEquals(userModel.name, userEntity.name)
        assertEquals(userModel.age, userEntity.age)
        assertEquals(userModel.jobTitle, userEntity.jobTitle)
        assertEquals(userModel.gender, userEntity.gender)
    }
}