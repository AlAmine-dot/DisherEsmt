//package com.example.esmt.cours.disher.ui.customized_items
//
//import androidx.compose.foundation.BorderStroke
//import androidx.compose.foundation.background
//import androidx.compose.foundation.border
//import androidx.compose.foundation.layout.*
//import androidx.compose.material.Button
//import androidx.compose.material.ButtonDefaults
//import androidx.compose.material.MaterialTheme
//import androidx.compose.material.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.Dp
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import com.example.esmt.cours.disher.ui.theme.MeltyGreen
//
//@Composable
//fun OutlinedButton(
//    text: String,
//    textColor: Color,
//    borderColor: Color,
//    borderWidth: Dp,
//    width: Dp,
//    height: Dp,
//    fontSize: Int,
//    onClick: () -> Unit,
//) {
//    Button(
//        colors = ButtonDefaults.buttonColors(
//            backgroundColor = Color.Transparent,
//            contentColor = textColor
//        ),
//        contentPadding = PaddingValues(),
//        onClick = { onClick() },
//        modifier = Modifier
//            .width(width)
//            .height(height)
//            .border(
//                BorderStroke(width = borderWidth, color = borderColor),
//                shape = MaterialTheme.shapes.small
//            )
//    ) {
//        Text(
//            text = text,
//            color = textColor,
//            fontSize = fontSize.sp,
//            modifier = Modifier.padding(8.dp)
//        )
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//fun OutlinedButtonPreview() {
//    OutlinedButton(
//        text = "Click me!",
//        borderColor = MeltyGreen,
//        textColor = MeltyGreen,
//        onClick = { /* Handle button click */ },
//        borderWidth = 10.dp,
//        fontSize = 10,
//        height = 15.dp,
//        width = 90.dp
//    )
//}
