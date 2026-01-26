package com.cap.taxi.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.Dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.cap.taxi.R

class TaxiStyle {

    @Composable
    fun DecorationBackground(
        modifier: Modifier = Modifier,
        color: Color,
        content: @Composable BoxScope.() -> Unit
    ) {
        ConstraintLayout  (
            modifier = modifier
                .height(IntrinsicSize.Min)
        ){
            val (icon1,content,icon2) = createRefs()
            Image(
                modifier = Modifier
                    .aspectRatio(18f / 56f)
                    .constrainAs(icon1){
                        end.linkTo(content.start)
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                        bottom.linkTo(content.bottom)

                        height = Dimension.fillToConstraints
                    },
                painter = painterResource(R.drawable.box_left_corner),
                colorFilter = ColorFilter.tint(color),
                contentDescription = null,
                contentScale = ContentScale.FillBounds
            )

            Image(
                modifier = Modifier
                    .aspectRatio(18f / 55f)
                    .constrainAs(icon2){
                        end.linkTo(parent.end)
                        start.linkTo(content.end)
                        top.linkTo(parent.top)
                        bottom.linkTo(content.bottom)

                        height = Dimension.fillToConstraints
                    },
                painter = painterResource(R.drawable.box_right_corner),
                colorFilter = ColorFilter.tint(color),
                contentDescription = null,
                contentScale = ContentScale.FillBounds
            )

            Box(
                modifier = Modifier
                    .constrainAs(content){
                        start.linkTo(icon1.end)
                        end.linkTo(icon2.start)
                        top.linkTo(parent.top)
                    }
                    .background(color),
            ){
                content()
            }
        }
    }

    @Composable
    fun DecorationBackgroundWithFixedCorners(
        modifier: Modifier = Modifier,
        color: Color,
        widthOfCorners: Dp,
        content: @Composable BoxScope.() -> Unit
    ) {
        ConstraintLayout  (
            modifier = modifier
                .height(IntrinsicSize.Min)
        ){
            val (icon1,content,icon2) = createRefs()
            Image(
                modifier = Modifier
                    .width(widthOfCorners)
                    .constrainAs(icon1){
                        end.linkTo(content.start)
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                        bottom.linkTo(content.bottom)

                        height = Dimension.fillToConstraints
                    },
                painter = painterResource(R.drawable.box_left_corner),
                colorFilter = ColorFilter.tint(color),
                contentDescription = null,
                contentScale = ContentScale.FillBounds
            )

            Image(
                modifier = Modifier
                    .width(widthOfCorners)
                    .constrainAs(icon2){
                        end.linkTo(parent.end)
                        start.linkTo(content.end)
                        top.linkTo(parent.top)
                        bottom.linkTo(content.bottom)

                        height = Dimension.fillToConstraints
                    },
                painter = painterResource(R.drawable.box_right_corner),
                colorFilter = ColorFilter.tint(color),
                contentDescription = null,
                contentScale = ContentScale.FillBounds
            )

            Box(
                modifier = Modifier
                    .constrainAs(content){
                        start.linkTo(icon1.end)
                        end.linkTo(icon2.start)
                        top.linkTo(parent.top)
                    }
                    .background(color),
            ){
                content()
            }

        }
    }
}

val unbounded_regular = FontFamily(
    Font(R.font.unbounded_regular)
)

val unbounded_medium = FontFamily(
    Font(R.font.unbounded_medium)
)

val unbounded_bold = FontFamily(
    Font(R.font.unbounded_bold)
)

val unbounded_black = FontFamily(
    Font(R.font.unbounded_black)
)
