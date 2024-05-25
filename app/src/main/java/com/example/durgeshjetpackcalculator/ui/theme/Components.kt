package com.example.durgeshjetpackcalculator.ui.theme

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.draw.clip
import com.example.durgeshjetpackcalculator.model.Operation



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputUIComponent(mutableValueState: State<String>, fontSize: TextUnit) {

    TextField(
        value = mutableValueState.value,
        onValueChange = { },
        textStyle = MaterialTheme.typography.headlineLarge.copy(
            textAlign = TextAlign.End,
            fontSize = fontSize
        ),
        readOnly = true,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 180.dp, end = 1.dp)
            .height(IntrinsicSize.Min)
            .animateContentSize(),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = WhiteColor,
            unfocusedContainerColor = WhiteColor,
            disabledContainerColor = WhiteColor,
            focusedIndicatorColor = Color.Transparent,    // Hide the bottom line when focused
            unfocusedIndicatorColor = Color.Transparent, // Hide the bottom line when unfocused
        ),

    )
}

@Composable
fun calculateFontSize(text: String): TextUnit {
    val baseFontSize = 94.sp
    val maxDigitsBeforeScaling = 6

    val scaleFactor = when {
        text.length <= maxDigitsBeforeScaling -> 1f
        else -> (maxDigitsBeforeScaling.toFloat() / text.length)
    }

    return baseFontSize * scaleFactor
}

@Composable
fun KeyboardUIComponent(
    modifier: Modifier = Modifier,
    buttonState: Operation,
    onNumberChange: (Int) -> Unit,
    onOperatorClick: (Operation) -> Unit
) {
    Row(modifier = modifier, horizontalArrangement = Arrangement.Absolute.SpaceAround) {
        SpecialOperatorRoundedButton(
            button = Operation.AC
        ) {
            onOperatorClick(Operation.AC)
        }
        SpecialOperatorRoundedButton(
            button = Operation.PERCENTAGE1,
            onClick = onOperatorClick

        )
        SpecialOperatorRoundedButton(
            button = Operation.PERCENTAGE,
            onClick = onOperatorClick
        )
        OperatorRoundedButton(
            operation = Operation.DIVISION,
            currentOperation = buttonState,
            onClick =  onOperatorClick
        )
    }
    Row(modifier = modifier, horizontalArrangement = Arrangement.Absolute.SpaceAround) {
        NumberRoundedButton(
            text = "7",
            onClick = onNumberChange
        )
        NumberRoundedButton(
            text = "8",
            onClick = onNumberChange
        )
        NumberRoundedButton(
            text = "9",
            onClick = onNumberChange
        )
        OperatorRoundedButton(
            operation = Operation.MULTIPLICATION,
            currentOperation = buttonState,
            onClick =  onOperatorClick
        )
    }
    Row(modifier = modifier, horizontalArrangement = Arrangement.Absolute.SpaceAround) {
        NumberRoundedButton(
            text = "4",
            onClick = onNumberChange
        )
        NumberRoundedButton(
            text = "5",
            onClick = onNumberChange
        )
        NumberRoundedButton(
            text = "6",
            onClick = onNumberChange
        )
        OperatorRoundedButton(
            operation = Operation.SUBTRACTION,
            currentOperation = buttonState,
            onClick =  onOperatorClick
        )
    }
    Row(modifier = modifier, horizontalArrangement = Arrangement.Absolute.SpaceAround) {
        NumberRoundedButton(
            text = "1",
            onClick = onNumberChange
        )
        NumberRoundedButton(
            text = "2",
            onClick = onNumberChange
        )
        NumberRoundedButton(
            text = "3",
            onClick = onNumberChange
        )
        OperatorRoundedButton(
            operation = Operation.ADDITION,
            currentOperation = buttonState,
            onClick =  onOperatorClick
        )
    }
    Row(modifier = modifier, horizontalArrangement = Arrangement.Absolute.SpaceAround) {
        NumberRoundedButtonZero(
            text = "0",
            onClick = onNumberChange
        )
        SpecialOperatorRoundedButton(
            button = Operation.COMMA,
            onClick = onOperatorClick
        )
        OperatorRoundedButton(operation = Operation.EQUALS, currentOperation = buttonState, onClick = onOperatorClick)
    }
}

@Composable
fun SpecialOperatorRoundedButton(
    button: Operation,
    onClick: (Operation) -> Unit = { }
) {
    CustomAnimatedButton(
        text = button.symbol,
        textColor = WhiteColor,
        backgroundColor = LightGray,
        onClick = {
            onClick.invoke(button)
        }
    )
}
@Composable
fun OperatorRoundedButton(
    operation: Operation,
    currentOperation: Operation,
    onClick: (Operation) -> Unit = { }
) {
    val backgroundColor = if (currentOperation == operation) LightGray else LightGray
    CustomAnimatedButton(
        text = operation.symbol,
        textColor = WhiteColor,
        backgroundColor = backgroundColor,
        onClick = {
            onClick.invoke(operation)
        }
    )
}

@Composable
fun NumberRoundedButton(
    text: String,
    onClick: (Int) -> Unit = { }
) {
    CustomAnimatedButton(
        text = text,
        textColor = White,
        backgroundColor = DarkGray,
        onClick = {
            onClick(text.toInt())
        }
    )
}


@Composable
fun NumberRoundedButtonZero(
    text: String,
    onClick: (Int) -> Unit = { }
) {
    CustomAnimatedButtonZero(
        text = text,
        textColor = White,
        backgroundColor = DarkGray,
        onClick = {
            onClick(text.toInt())
        }
    )
}

@Composable
fun CustomAnimatedButton(
    text: String,
    textColor: Color,
    backgroundColor: Color,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed = interactionSource.collectIsPressedAsState()
    val cornerRadius by animateDpAsState(targetValue = if (isPressed.value) 10.dp else 50.dp,
        label = ""
    )

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(color = Color(0xFF6200EE)) // Apply background color
            .size(96.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple()
            ) {
                onClick()
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = textColor
        )
    }
}


@Composable
fun CustomAnimatedButtonZero(
    text: String,
    textColor: Color,
    backgroundColor: Color,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed = interactionSource.collectIsPressedAsState()
    val cornerRadius by animateDpAsState(targetValue = if (isPressed.value) 10.dp else 50.dp,
        label = ""
    )

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(color = Color(0xFF6200EE)) // Apply background color
            .size(width = 192.dp, height = 96.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple()
            ) {
                onClick()
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = textColor
        )
    }
}