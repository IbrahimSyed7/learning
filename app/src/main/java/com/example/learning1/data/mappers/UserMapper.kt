package com.example.learning1.data.mappers

import com.example.learning1.data.dto.UserDTO
import com.example.learning1.data.dto.UserRequestDTO
import com.example.learning1.domain.entity.User

fun UserDTO.toUser() = User(
    id = id,
    username = username,
    email = email,
    firstName = firstName,
    lastName = lastName,
    image = image,
)