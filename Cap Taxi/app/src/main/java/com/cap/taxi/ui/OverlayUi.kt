package com.cap.taxi.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cap.taxi.R
import com.cap.taxi.facade.states.OverlayMessageInfoUi
import com.cap.taxi.ui.theme.TaxiStyle
import com.cap.taxi.ui.theme.unbounded_bold
import com.cap.taxi.ui.theme.unbounded_regular
import com.cap.taxi.facade.OverlayController


private val styleClass: TaxiStyle = TaxiStyle()

@Composable
fun InternetConnectionError(overlay: OverlayController) {

    val context = LocalContext.current

    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.whiteF2B20))
            .padding(top = 70.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = {}
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(Modifier.weight(1.5f))

        Image(
            modifier = Modifier.size(150.dp),
            painter = painterResource(R.drawable.no_internet_connection_error),
            contentDescription = null
        )

        Spacer(Modifier.height(40.dp))

        Text(
            text = stringResource(R.string.internet_error),
            fontFamily = unbounded_bold,
            fontSize = 16.sp,
            color = colorResource(R.color.black15WF2)
        )

        Spacer(Modifier.weight(1f))

        styleClass.DecorationBackground(
            modifier = Modifier
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = {
                        overlay.isNetworkConnected()
                    }
                ),
            color = colorResource(R.color.black20WF2)
        ) {
            Text(
                modifier = Modifier.padding(horizontal = 25.dp, vertical = 10.dp),
                text = stringResource(R.string.try_again),
                fontFamily = unbounded_bold,
                fontSize = 16.sp,
                color = colorResource(R.color.whiteF2B15)
            )
        }

        Spacer(Modifier.height(100.dp))
    }
}

@Composable
fun Message(modifier: Modifier = Modifier, messageState: OverlayMessageInfoUi) {
    val shortDescription = messageState.shortDescription
    val buttonDescription = messageState.buttonText
    val description = messageState.description
    val onOk = messageState.onButtonClick

    Box(
        modifier = modifier.widthIn(min = 220.dp, max = 350.dp)
            .padding(horizontal = 50.dp)
    ){
        Text(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(horizontal = 12.dp, vertical = 7.dp)
                .background(colorResource(R.color.black20WF2))
                .padding(horizontal = 25.dp, vertical = 35.dp),
            text = description,
            fontFamily = unbounded_regular,
            fontSize = 14.sp,
            color = colorResource(R.color.whiteF2B15)
        )


        styleClass.DecorationBackground(
            modifier = Modifier
                .widthIn(80.dp, 180.dp)
                .align(Alignment.TopStart),
            color = colorResource(R.color.whiteF2B20)
        ){
            Text(
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp),
                text = shortDescription,
                fontFamily = unbounded_regular,
                fontSize = 14.sp,
                color = colorResource(R.color.black15WF2)
            )
        }

        styleClass.DecorationBackground(
            modifier = Modifier
                .widthIn(80.dp, 180.dp)
                .align(Alignment.BottomEnd)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = {
                        onOk()
                    }
                ),
            color = colorResource(R.color.whiteF2B20)
        ){
            Text(
                modifier = Modifier
                    .padding(horizontal = 30.dp, vertical = 10.dp),
                text = buttonDescription,
                fontFamily = unbounded_regular,
                fontSize = 14.sp,
                color = colorResource(R.color.black15WF2)
            )
        }
    }
}


@Composable
fun Modifier.fadingEdgeVertical(topEdge: Dp, bottomEdge: Dp) = this
    .graphicsLayer(compositingStrategy = CompositingStrategy.Offscreen)
    .drawWithContent {
        drawContent()
        drawRect(
            brush = Brush.verticalGradient(
                colors = listOf(Color.Transparent, Color.Black),
                startY = 0f,
                endY = topEdge.toPx(),
            ),
            blendMode = BlendMode.DstIn,
        )

        drawRect(
            brush = Brush.verticalGradient(
                colors = listOf(Color.Black, Color.Transparent),
                startY = size.height - bottomEdge.toPx(),
                endY = size.height,
            ),
            blendMode = BlendMode.DstIn,
        )
    }

@Composable
fun Modifier.fadingEdgeHorizontal(startEdge: Dp, endEdge: Dp) = this
    .graphicsLayer(compositingStrategy = CompositingStrategy.Offscreen)
    .drawWithContent {
        drawContent()
        drawRect(
            brush = Brush.horizontalGradient(
                colors = listOf(Color.Transparent, Color.Black),
                startX = 0f,
                endX = startEdge.toPx(),
            ),
            blendMode = BlendMode.DstIn,
        )

        drawRect(
            brush = Brush.horizontalGradient(
                colors = listOf(Color.Black, Color.Transparent),
                startX = size.width - endEdge.toPx(),
                endX = size.width,
            ),
            blendMode = BlendMode.DstIn,
        )
    }