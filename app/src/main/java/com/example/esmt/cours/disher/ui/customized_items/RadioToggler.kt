package com.example.esmt.cours.disher.ui.customized_items

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.esmt.cours.disher.ui.theme.MeltyGreen

//sealed class RadioTogglerItem(
//    itemName: String,
//    isSelected: Boolean
//)

@Composable
fun RadioToggler(
    item1: String,
    item2: String,
    trigger: Boolean,
    onClickItem1: () -> Unit,
    onClickItem2: () -> Unit
){

//    var isToggled by remember { mutableStateOf(true) }

    Surface(
        shape = RoundedCornerShape(36.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        color = Color.LightGray.copy(alpha=.2f)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.LightGray.copy(alpha = .4f))
            ,

        ){

            Button(
                modifier = Modifier
                    .fillMaxWidth(.5f)
                    .fillMaxHeight(1f)
                    .padding(start = 4.dp, top = 4.dp, bottom = 4.dp)
                    .align(Alignment.CenterStart)
                ,
                elevation = ButtonDefaults.elevation(0.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = if(trigger){Color.White}else{Color.Transparent}),
                shape = RoundedCornerShape(46.dp),
                onClick = {onClickItem1()}
            ){
                Text(
                    text = item1,
                    color = if(trigger){MeltyGreen}else{Color.DarkGray},
                    style = MaterialTheme.typography.h6,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Button(
                modifier = Modifier
                    .fillMaxWidth(.5f)
                    .fillMaxHeight(1f)
                    .padding(end = 4.dp, top = 4.dp, bottom = 4.dp)
                    .align(Alignment.CenterEnd)
                ,
                elevation = ButtonDefaults.elevation(0.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = if(!trigger){Color.White}else{Color.Transparent}),
                shape = RoundedCornerShape(46.dp),
                onClick = { onClickItem2() }
            ){
                Text(
                    text = item2,
                    color = if(!trigger){MeltyGreen}else{Color.DarkGray},
                    style = MaterialTheme.typography.h6,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RadioTogglerPreview(){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(20.dp),

    ) {
        RadioToggler("Ingredients","Ustensiles",true,{},{})
    }
}