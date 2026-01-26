package com.cap.taxi.ui

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.EaseInOutCubic
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cap.taxi.R
import com.cap.taxi.domain.model.common.DriverCarType
import com.cap.taxi.domain.model.common.SearchDriverType
import com.cap.taxi.facade.states.DriverInfoUi
import com.cap.taxi.ui.theme.TaxiStyle
import com.cap.taxi.ui.theme.unbounded_bold
import com.cap.taxi.ui.theme.unbounded_medium
import com.cap.taxi.ui.theme.unbounded_regular
import com.cap.taxi.viewmodel.ViewModelDrivers


private val animationClass = AnimationUi()
private val styleClass: TaxiStyle = TaxiStyle()

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DriversUi(viewModel: ViewModelDrivers) {
    val listOfDrivers = viewModel.listOfDrivers
    val currentDriverState by viewModel.currentDriver.collectAsState()
    val searchSelectedCarType by viewModel.searchDriverCarType.collectAsState()
    val searchSelectedType by viewModel.searchDriverType.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .clickable(
                enabled = true,
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = {})
            .background(colorResource(R.color.whiteF2B20).copy(alpha = 0.5f))
            .padding(top = 40.dp)
    ) {


        Box(
            modifier = Modifier
                .weight(1f)
                .padding(bottom = 100.dp)
        ){
            Box(
                Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(start = 50.dp, end = 30.dp)
                    .background(colorResource(R.color.whiteDB15))
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(top = 55.dp, end = 40.dp)
                    .fadingEdgeVertical(20.dp, 30.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
            ) {

                if (listOfDrivers.isNotEmpty()) {
                    item {
                        Spacer(Modifier.height(10.dp))
                    }

                    itemsIndexed(listOfDrivers) { index, driver ->
                        DriverCard(
                            modifier = Modifier.combinedClickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null,
                                onClick = {
                                    viewModel.selectDriver(driver)
                                },
                                onLongClick = {
                                    viewModel.clickDriverCard(index, driver)
                                },
                                onDoubleClick = {
                                    viewModel.clickDriverCard(index, driver)
                                }
                            ),
                            driverState = driver,
                            isSelected = driver.id == currentDriverState?.id,
                            isExpended = driver.isExpended
                        )

                    }

                    item {
                        Spacer(Modifier.height(20.dp))
                    }
                }
            }

            if (listOfDrivers.isEmpty()){
                Text(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(horizontal = 50.dp),
                    text = stringResource(R.string.sorry_we_didnt_find_driver),
                    fontFamily = unbounded_bold,
                    fontSize = 16.sp,
                    color = colorResource(R.color.black15WF2),
                    textAlign = TextAlign.Center
                )
            }
        }


        Row(
            modifier = Modifier
                .height(IntrinsicSize.Min)
                .background(colorResource(R.color.whiteF2B20))
        ) {
            Column(
                modifier = Modifier
                    .wrapContentHeight()
                    .weight(1f)
            ) {
                Text(
                    modifier = Modifier.padding(horizontal = 30.dp, vertical = 10.dp),
                    text = "${stringResource(R.string.transport_type)} :",
                    fontFamily = unbounded_regular,
                    fontSize = 12.sp,
                    color = colorResource(R.color.black15WF2)
                )

                Row(
                    modifier = Modifier
                        .fadingEdgeHorizontal(10.dp, 10.dp)
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(7.dp),
                ) {
                    Spacer(Modifier.width(20.dp))

                    DriverCarType.entries.forEach { entity ->
                        val text = when(entity) {
                            DriverCarType.NONE -> stringResource(R.string.all_types)
                            DriverCarType.SEDAN -> stringResource(R.string.sedan)
                            DriverCarType.STATION_WAGON -> stringResource(R.string.station_wagon)
                            DriverCarType.HATCHBACK -> stringResource(R.string.hatchback)
                            DriverCarType.CROSSOVER -> stringResource(R.string.crossover)
                            DriverCarType.COUPE -> stringResource(R.string.coupe)
                        }

                        ChoosingItem(
                            text = text,
                            isActivated = entity == searchSelectedCarType,
                            onClick = {
                                viewModel.selectSearchCarType(entity)
                            }
                        )
                    }

                    Spacer(Modifier.width(20.dp))
                }
                Text(
                    modifier = Modifier.padding(horizontal = 30.dp, vertical = 10.dp),
                    text = "${stringResource(R.string.search_for)} :",
                    fontFamily = unbounded_regular,
                    fontSize = 12.sp,
                    color = colorResource(R.color.black15WF2)
                )

                Row(
                    modifier = Modifier
                        .fadingEdgeHorizontal(10.dp, 10.dp)
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(7.dp),
                ) {
                    Spacer(Modifier.width(20.dp))

                    SearchDriverType.entries.forEach { entity ->
                        val text = when(entity) {
                            SearchDriverType.NAME -> stringResource(R.string.name)
                            SearchDriverType.CAR -> stringResource(R.string.car)
                            SearchDriverType.PRICE -> stringResource(R.string.price)
                        }

                        ChoosingItem(
                            text = text,
                            isActivated = entity == searchSelectedType,
                            onClick = {
                                viewModel.selectSearchType(entity)
                            }
                        )
                    }

                    Spacer(Modifier.width(20.dp))
                }

                Spacer(Modifier.height(20.dp))

                val textOfSearchDriverState by viewModel.textOfSearchDriver.collectAsState()
                BasicTextField(
                    modifier = Modifier
                        .width(200.dp)
                        .padding(start = 40.dp, bottom = 20.dp,),
                    value = textOfSearchDriverState,
                    onValueChange = {
                        viewModel.setTextSearchDriver(it)
                    },
                    textStyle = TextStyle(
                        fontFamily = unbounded_medium,
                        fontSize = 14.sp,
                        color = colorResource(R.color.black15WF2)
                    ),
                    cursorBrush = SolidColor(colorResource(R.color.black15WF2)),
                    decorationBox = { TextField ->
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                        ) {
                            Box {
                                TextField()

                                if (textOfSearchDriverState.isEmpty()) {
                                    Text(
                                        text = stringResource(R.string.enter_text),
                                        fontFamily = unbounded_regular,
                                        fontSize = 14.sp,
                                        color = colorResource(R.color.grey76)
                                    )
                                }
                            }

                            Spacer(Modifier.height(2.dp))

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(2.dp)
                                    .background(
                                        colorResource(R.color.black15WF2),
                                        CircleShape
                                    )
                            )
                        }
                    }
                )
            }

            Box(
                modifier = Modifier
                    .width(60.dp)
                    .fillMaxHeight()
            ) {
                Image(
                    modifier = Modifier
                        .size(40.dp)
                        .align(Alignment.TopCenter)
                        .offset(y = 25.dp)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            onClick = {
                                viewModel.visibleExplanationOfDriverCard(true)
                            }
                        ),
                    painter = painterResource(R.drawable.attention_icon),
                    contentDescription = null,
                )
            }
        }
    }
}


@Composable
fun ExplanationOfConstruction(viewModel: ViewModelDrivers) {
    Box {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(R.color.whiteF2B15).copy(alpha = 0.5f))
                .clickable(
                    enabled = true,
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = {}
                )
                .blur(0.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Column(
                modifier = Modifier
                    .width(250.dp)
                    .wrapContentHeight()
                    .background(colorResource(R.color.whiteF2B20))
                    .padding(horizontal = 20.dp, vertical = 15.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Box {
                    Text(
                        modifier = Modifier.padding(end = 10.dp),
                        text = "· ${stringResource(R.string.fare_explanation)}",
                        fontFamily = unbounded_regular,
                        fontSize = 10.sp,
                        color = colorResource(R.color.black15WF2)
                    )

                    Image(
                        modifier = Modifier
                            .size(20.dp)
                            .align(Alignment.TopEnd)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null,
                                onClick = {
                                    viewModel.visibleExplanationOfDriverCard(false)
                                }
                            )
                            .offset(x = 15.dp, y = (-10).dp),
                        painter = painterResource(R.drawable.close_button),
                        contentDescription = null
                    )
                }

                Text(
                    text = "· ${stringResource(R.string.card_explanation)}",
                    fontFamily = unbounded_regular,
                    fontSize = 10.sp,
                    color = colorResource(R.color.black15WF2)
                )

                Text(
                    text = "· ${stringResource(R.string.price_explanation)}",
                    fontFamily = unbounded_regular,
                    fontSize = 10.sp,
                    color = colorResource(R.color.black15WF2)
                )
            }

            Spacer(Modifier.height(20.dp))

            var animationStep by remember { mutableIntStateOf(-1) }
            val alphaIcon by animateFloatAsState(
                targetValue = if (animationStep == 0) 0.3f else 1f,
                animationSpec = tween(
                    durationMillis = 1500,
                    easing = EaseInOut
                ), label = "",
                finishedListener = {
                    if (animationStep == 0) {
                        animationStep = 1
                    }
                }
            )

            val alphaName by animateFloatAsState(
                targetValue = if (animationStep == 1) 0.3f else 1f,
                animationSpec = tween(
                    durationMillis = 1500,
                    easing = EaseInOut
                ), label = "",
                finishedListener = {
                    if (animationStep == 1) {
                        animationStep = 2
                    }
                }
            )

            val alphaAuto by animateFloatAsState(
                targetValue = if (animationStep == 2) 0.3f else 1f,
                animationSpec = tween(
                    durationMillis = 1500,
                    easing = EaseInOut
                ), label = "",
                finishedListener = {
                    if (animationStep == 2) {
                        animationStep = 3
                    }
                }
            )

            val alphaPrice by animateFloatAsState(
                targetValue = if (animationStep == 3) 0.3f else 1f,
                animationSpec = tween(
                    durationMillis = 1500,
                    easing = EaseInOut
                ), label = "",
                finishedListener = {
                    if (animationStep == 3) {
                        animationStep = 0
                    }
                }
            )

            LaunchedEffect(Unit) {
                animationStep = 0
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min)
                    .padding(horizontal = 10.dp)
            ) {
                styleClass.DecorationBackgroundWithFixedCorners(
                    modifier = Modifier
                        .padding(horizontal = 30.dp),
                    color = colorResource(R.color.whiteF2B20),
                    widthOfCorners = 20.dp
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            modifier = Modifier
                                .fillMaxHeight()
                                .padding(vertical = 3.dp)
                                .aspectRatio(1f)
                                .clip(CircleShape)
                                .graphicsLayer(alpha = alphaIcon),
                            painter = painterResource(R.drawable.unknow_icon),
                            contentDescription = null,
                        )

                        Column(
                            modifier = Modifier
                                .fillMaxHeight()
                                .padding(vertical = 10.dp),
                            verticalArrangement = Arrangement.SpaceAround
                        ) {
                            Text(
                                modifier = Modifier
                                    .padding(start = 20.dp, bottom = 10.dp)
                                    .graphicsLayer(alpha = alphaName),
                                text = "Alex",
                                fontFamily = unbounded_medium,
                                fontSize = 12.sp,
                                color = colorResource(R.color.black15WF2)
                            )
                            Text(
                                modifier = Modifier
                                    .width(170.dp)
                                    .padding(start = 15.dp)
                                    .graphicsLayer(alpha = alphaAuto),
                                text = "Mercedes-Benz C300 2023",
                                fontFamily = unbounded_regular,
                                fontSize = 10.sp,
                                color = colorResource(R.color.black15WF2),
                                overflow = TextOverflow.Ellipsis,
                                maxLines = 1
                            )
                        }

                        Spacer(Modifier.weight(1f))

                        Text(
                            modifier = Modifier
                                .padding(end = 5.dp)
                                .graphicsLayer(alpha = alphaPrice),
                            text = "29$",
                            fontFamily = unbounded_regular,
                            fontSize = 12.sp,
                            color = colorResource(R.color.black15WF2)
                        )
                    }

                    Image(
                        modifier = Modifier
                            .fillMaxHeight()
                            .align(Alignment.CenterEnd)
                            .offset(x = 23.dp),
                        painter = painterResource(R.drawable.die_to_driver_card),
                        contentDescription = null,
                        contentScale = ContentScale.FillHeight

                    )
                }
            }

            Spacer(Modifier.height(20.dp))

            Column(
                modifier = Modifier
                    .width(250.dp)
                    .wrapContentHeight()
                    .background(colorResource(R.color.whiteF2B20))
                    .padding(horizontal = 20.dp, vertical = 15.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    modifier = Modifier.graphicsLayer(alpha = alphaIcon),
                    text = "· ${stringResource(R.string.image_of_driver)}",
                    fontFamily = unbounded_regular,
                    fontSize = 12.sp,
                    color = colorResource(R.color.black15WF2)
                )

                Text(
                    modifier = Modifier.graphicsLayer(alpha = alphaName),
                    text = "· ${stringResource(R.string.name_of_driver)}",
                    fontFamily = unbounded_regular,
                    fontSize = 12.sp,
                    color = colorResource(R.color.black15WF2)
                )

                Text(
                    modifier = Modifier.graphicsLayer(alpha = alphaAuto),
                    text = "· ${stringResource(R.string.drivers_car)}",
                    fontFamily = unbounded_regular,
                    fontSize = 12.sp,
                    color = colorResource(R.color.black15WF2)
                )

                Text(
                    modifier = Modifier.graphicsLayer(alpha = alphaPrice),
                    text = "· ${stringResource(R.string.driver_fare)}",
                    fontFamily = unbounded_regular,
                    fontSize = 12.sp,
                    color = colorResource(R.color.black15WF2)
                )
            }
        }


        // Created by Orest Tretiak
        // for portfolio 12.09.2024
        Text(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(15.dp),
            text = stringResource(R.string.created_by),
            fontFamily = unbounded_bold,
            fontSize = 12.sp,
            color = colorResource(R.color.black15WF2)
        )
    }
}

@Composable
private fun DriverCard(
    modifier: Modifier = Modifier,
    driverState: DriverInfoUi,
    isSelected: Boolean,
    isExpended: Boolean = false
) {

    Box (modifier = modifier) {
        animationClass.AnimateHeight(isExpended, 220.dp) {
            Column (
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 20.dp)
                    .background(colorResource(R.color.whiteE9B2C))
                    .padding(top = 70.dp)
                    .padding(10.dp),
                verticalArrangement = Arrangement.SpaceEvenly,
            ) {
                Row {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(5.dp)
                    ){
                        Text(
                            text = "0-5 ${stringResource(R.string.km)}: ${driverState.tariffS}$",
                            fontFamily = unbounded_medium,
                            fontSize = 12.sp,
                            color = colorResource(R.color.black15WF2)
                        )

                        Text(
                            text = "5-10 ${stringResource(R.string.km)}: ${driverState.tariffM}$",
                            fontFamily = unbounded_medium,
                            fontSize = 12.sp,
                            color = colorResource(R.color.black15WF2)
                        )

                        Text(
                            text = "10-15 ${stringResource(R.string.km)}: ${driverState.tariffH}$",
                            fontFamily = unbounded_medium,
                            fontSize = 12.sp,
                            color = colorResource(R.color.black15WF2)
                        )
                    }

                    Spacer(Modifier.weight(1f))

                    Column(
                        verticalArrangement = Arrangement.spacedBy(5.dp)
                    ){
                        Text(
                            text = "${stringResource(R.string.experience)}: ${driverState.experience} ${stringResource(R.string.years)}",
                            fontFamily = unbounded_medium,
                            fontSize = 12.sp,
                            color = colorResource(R.color.black15WF2)
                        )

                        Text(
                            text = "${stringResource(R.string.rating)}: ${driverState.rate}",
                            fontFamily = unbounded_medium,
                            fontSize = 12.sp,
                            color = colorResource(R.color.black15WF2)
                        )
                    }
                }

                Spacer(Modifier.height(20.dp))

                Column(
                    verticalArrangement = Arrangement.spacedBy(5.dp)
                ){
                    Text(
                        text = "${stringResource(R.string.type_of_body)}: ${driverState.typeOfCar}",
                        fontFamily = unbounded_medium,
                        fontSize = 12.sp,
                        color = colorResource(R.color.black15WF2)
                    )

                    Text(
                        text = "${stringResource(R.string.phone_number)}: ${driverState.phoneNumber}",
                        fontFamily = unbounded_medium,
                        fontSize = 12.sp,
                        color = colorResource(R.color.black15WF2)
                    )
                }
            }
        }

        Box(
            modifier = Modifier
                .height(IntrinsicSize.Min)
        ) {
            styleClass.DecorationBackgroundWithFixedCorners(
                modifier = Modifier
                    .padding(start = 20.dp, end = 23.dp),
                color = colorResource(R.color.whiteF2B20),
                widthOfCorners = 20.dp
            ){
                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (driverState.image != null) {
                        Image(
                            modifier = Modifier
                                .size(60.dp)
                                .padding(3.dp)
                                .clip(CircleShape),
                            bitmap = driverState.image,
                            contentDescription = null,
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Image(
                            modifier = Modifier
                                .aspectRatio(1f)
                                .fillMaxHeight()
                                .padding(vertical = 3.dp),
                            painter = painterResource (R.drawable.unknow_icon),
                            contentDescription = null
                        )
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(vertical = 10.dp),
                        verticalArrangement = Arrangement.SpaceAround
                    ) {
                        Text(
                            modifier = Modifier.padding(start = 20.dp, bottom = 10.dp),
                            text = driverState.name,
                            fontFamily = unbounded_medium,
                            fontSize = 12.sp,
                            color = colorResource(R.color.black15WF2)
                        )
                        Text(
                            modifier = Modifier
                                .width(150.dp)
                                .padding(start = 12.dp)
                                .fadingEdgeHorizontal(3.dp, 3.dp)
                                .basicMarquee()
                                .padding(start = 3.dp),
                            text = driverState.car,
                            fontFamily = unbounded_regular,
                            fontSize = 10.sp,
                            color = colorResource(R.color.black15WF2),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }

                    Spacer(Modifier.weight(1f))

                    Text(
                        modifier = Modifier.padding(end = 5.dp),
                        text = "${driverState.tariffM}$",
                        fontFamily = unbounded_regular,
                        fontSize = 12.sp,
                        color = colorResource(R.color.black15WF2)
                    )
                }
                val colorOfEndIcon by animateColorAsState(
                    targetValue = if (isSelected) colorResource(R.color.taxi) else colorResource(R.color.black20WF2),
                    animationSpec = tween(
                        durationMillis = 400,
                        easing = EaseInOutCubic
                    )
                )

                Image(
                    modifier = Modifier
                        .fillMaxHeight()
                        .align(Alignment.CenterEnd)
                        .offset(x = 23.dp),
                    painter = painterResource(R.drawable.die_to_driver_card),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(colorOfEndIcon),
                    contentScale = ContentScale.FillHeight

                )
            }
        }
    }
}

@Composable
private fun ChoosingItem(
    modifier: Modifier = Modifier,
    text: String,
    isActivated: Boolean,
    onClick: () -> Unit
) {
    val colorOfCell by animateColorAsState(
        targetValue = if (isActivated) colorResource(R.color.taxi) else colorResource(R.color.black20WF2),
        animationSpec = tween(
            durationMillis = 500,
            easing = EaseInOut
        )
    )

    styleClass.DecorationBackground(
        modifier = modifier.clickable(onClick = onClick),
        color = colorOfCell
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 15.dp, vertical = 5.dp),
            text = text,
            fontFamily = unbounded_medium,
            fontSize = 12.sp,
            color = colorResource(R.color.whiteF2B15)
        )
    }
}
