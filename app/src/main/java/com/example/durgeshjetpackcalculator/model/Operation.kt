package com.example.durgeshjetpackcalculator.model

enum class Operation(
    val symbol: String
) {
    NONE(""),
    AC("C"),
    PERCENTAGE1("+/-"),
    COMMA("."),
    PERCENTAGE("%"),
    DIVISION( "/"),
    MULTIPLICATION( "x"),
    SUBTRACTION("-"),
    ADDITION("+"),
    EQUALS("=")
}