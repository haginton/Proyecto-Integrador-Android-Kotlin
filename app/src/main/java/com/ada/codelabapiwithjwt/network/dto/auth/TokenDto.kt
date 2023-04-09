package com.ada.codelabapiwithjwt.network.dto.auth

import java.util.*

data class TokenDto(val token: String, val expirationDate: Date)
