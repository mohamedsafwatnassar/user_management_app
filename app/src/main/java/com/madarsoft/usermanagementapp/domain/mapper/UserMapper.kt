package com.madarsoft.usermanagementapp.domain.mapper

import com.madarsoft.usermanagementapp.data.entity.UserEntity
import com.madarsoft.usermanagementapp.domain.model.UserModel
import javax.inject.Inject
import javax.inject.Singleton

fun UserEntity.toModel(): UserModel {
    return UserModel(
        id = id,
        name = name,
        age = age,
        jobTitle = jobTitle,
        gender = gender
    )
}

fun UserModel.toEntity(): UserEntity {
    return UserEntity(
        id = id,
        name = name,
        age = age,
        jobTitle = jobTitle,
        gender = gender
    )
}
