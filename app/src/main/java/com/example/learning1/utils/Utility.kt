package com.example.learning1.utils



class Utility {


companion object {
    private val EMAIL_REGEX_PRAGMATIC = Regex(
        pattern = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,63}$",
        options = setOf(RegexOption.IGNORE_CASE)
    )


    fun isValidEmail(value: String): Boolean {
        return EMAIL_REGEX_PRAGMATIC.matches(value)
    }

}

}