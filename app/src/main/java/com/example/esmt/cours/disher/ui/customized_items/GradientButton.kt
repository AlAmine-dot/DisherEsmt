package com.example.esmt.cours.disher.ui.customized_items

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.esmt.cours.disher.ui.theme.MeltyGreen


@Composable
fun GradientButton(
    text: String,
    textColor: Color,
    gradient: Brush,
    width: Dp,
    height: Dp,
    fontSize: Int,
    onClick: () -> Unit,
    icon: ImageVector?
) {
    Button(
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.Transparent
        ),
        contentPadding = PaddingValues(),
        onClick = { onClick() },
        modifier = Modifier
            .width(width)
            .height(height)
    )
    {
        Row(
            modifier = Modifier

                .background(gradient)
                .width(width)
                .height(height)
            ,
//                .padding(horizontal = width, vertical = height),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (icon != null) {
                Icon(
                    imageVector = icon,
                    contentDescription = "Back",
                    tint = Color.White,
                    modifier = Modifier

                        .size(28.dp)
                )
            }
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = text,
                color = textColor,
                fontSize = fontSize.sp
                )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview(){
    GradientButton(
        text = "Add to favorites",
        textColor = Color.White,
        gradient = Brush.horizontalGradient(
            colors = listOf(
                MeltyGreen,
                MeltyGreen,
                Color(0xFF11998E),
            )
        ),
        335.dp,
        65.dp,
        20,
        {},
        Icons.Default.Favorite
    )
}
