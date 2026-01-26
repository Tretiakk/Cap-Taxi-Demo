@file:OptIn(ExperimentalFoundationApi::class)

package com.cap.taxi.ui

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.core.Ease
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.ComposeMapColorScheme
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState
import com.cap.taxi.R
import com.cap.taxi.domain.model.taxi.MapPlaceInfo
import com.cap.taxi.ui.theme.TaxiStyle
import com.cap.taxi.ui.theme.unbounded_black
import com.cap.taxi.ui.theme.unbounded_regular
import com.cap.taxi.viewmodel.ViewModelDrivers
import com.cap.taxi.facade.OverlayController
import com.cap.taxi.viewmodel.ViewModelTaxi
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class Map : androidx.appcompat.app.AppCompatActivity() {

    private val viewModelTaxi: ViewModelTaxi by viewModels()
    private val viewModelDrivers: ViewModelDrivers by viewModels()

    @Inject
    lateinit var overlayController: OverlayController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_Taxist)

        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.auto(
                android.graphics.Color.TRANSPARENT,
                android.graphics.Color.TRANSPARENT
            ),
            navigationBarStyle = SystemBarStyle.auto(
                android.graphics.Color.TRANSPARENT,
                android.graphics.Color.TRANSPARENT
            )
        )

        realize()

        setContent {
            animationClass.Realize()

            MainUi(viewModelTaxi, viewModelDrivers, overlayController)
        }

        start()
    }

    private fun realize() {
        viewModelTaxi.realize(this)

        viewModelDrivers.realize()
    }

    private fun start() {
        viewModelTaxi.start()

        viewModelDrivers.start()
    }
}

private val animationClass = AnimationUi()
private val styleClass: TaxiStyle = TaxiStyle()

// TODO: Remake
@Composable
private fun MainUi(
    viewModelTaxi: ViewModelTaxi,
    viewModelDrivers: ViewModelDrivers,
    overlay: OverlayController
) {
    val isNetworkConnectedState by overlay.isNetworkConnected.collectAsState()
    val messageState by overlay.messageState.collectAsState()

    val isExplanationOfConstructionVisibleState by viewModelDrivers.isExplanationOfConstructionVisible.collectAsState()
    val blurAnimation by animateDpAsState(
        targetValue = if (isExplanationOfConstructionVisibleState) 7.dp else 0.dp,
        animationSpec = tween(
            durationMillis = 1000,
            easing = Ease
        )
    )

    GoogleMap(modifier = Modifier.fillMaxSize()) {

    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .blur(blurAnimation)
    ) {
        MakeDeliverRequestMenu(viewModelTaxi)

        var isVisibleChangeDriver by remember { mutableStateOf(false) }
        animationClass.AppearView(isVisibleChangeDriver) {
            DriversUi(viewModelDrivers)
        }

        styleClass.DecorationBackgroundWithFixedCorners(
            modifier = Modifier
                .padding(horizontal = 50.dp)
                .align(Alignment.TopCenter)
                .offset(y = 40.dp),
            color = colorResource(R.color.whiteF2B20),
            widthOfCorners = 20.dp
        ) {
            // taxi bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(65.dp)
                    .padding(horizontal = 10.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                val driverInfo by viewModelDrivers.currentDriver.collectAsState()
                if (driverInfo != null) {
                    Column {
                        Text(
                            modifier = Modifier
                                .widthIn(100.dp, 150.dp)
                                .fadingEdgeHorizontal(5.dp,5.dp)
                                .basicMarquee()
                                .padding(horizontal = 5.dp),
                            text = stringResource(R.string.driver) + " : " + driverInfo?.name, // driver
                            fontFamily = unbounded_regular,
                            fontSize = 14.sp,
                            color = colorResource(R.color.black15WF2),
                            maxLines = 1
                        )

                        Spacer(Modifier.height(3.dp))

                        Text(
                            text = stringResource(R.string.tariff) + " : " + driverInfo?.tariffM + "$", // tariff
                            fontFamily = unbounded_regular,
                            fontSize = 14.sp,
                            color = colorResource(R.color.black15WF2),
                            maxLines = 1
                        )
                    }
                }

                Spacer(Modifier.weight(1f))
                Spacer(Modifier.width(10.dp))

                Text(
                    modifier = Modifier.clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = {
                            isVisibleChangeDriver = !isVisibleChangeDriver
                        }
                    ),
                    text =
                        if (isVisibleChangeDriver)
                            stringResource(R.string.close)
                        else if (driverInfo != null)
                            stringResource(R.string.change)
                        else
                            stringResource(R.string.choose_a_driver),
                    fontFamily = unbounded_black,
                    fontSize = 14.sp,
                    color = colorResource(R.color.black15WF2),
                    textDecoration = TextDecoration.Underline,
                    maxLines = 1
                )
            }
        }
    }

    animationClass.AppearView(isExplanationOfConstructionVisibleState) {
        ExplanationOfConstruction(viewModelDrivers)
    }

    animationClass.AppearView(!isNetworkConnectedState) {
        InternetConnectionError(overlay)
    }

    animationClass.AppearView(
        isVisible = messageState.visible
    ) {
        Box(Modifier.fillMaxSize().background(colorResource(R.color.whiteF2B20).copy(alpha = 0.5f)))
    }
    animationClass.SelfFromRight(
        modifier = Modifier.fillMaxSize().clickable(enabled = false, onClick = {}),
        isVisible = messageState.visible
    ) {
        Message(Modifier.align(Alignment.Center), messageState)
    }
}

@Composable
private fun MakeDeliverRequestMenu(viewModel: ViewModelTaxi) {

    val coScope = rememberCoroutineScope()

    val context = LocalContext.current

    val userLocationState by viewModel.userLocation.collectAsState()
    val cameraPositionState = rememberCameraPositionState()

    val roadPath by viewModel.currentRoadPath.collectAsState()
    val departurePoint by viewModel.placeOfDeparture.collectAsState()
    val arrivalPoint by viewModel.placeOfArrival.collectAsState()


    Box(
        modifier = Modifier
            .background(colorResource(R.color.black15WF2))
            .fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)

            ) {

                LaunchedEffect(userLocationState) {
                    userLocationState?.let { location ->
                        cameraPositionState.animate(
                            CameraUpdateFactory.newLatLngZoom(
                                LatLng(
                                    location.latitude,
                                    location.longitude
                                ), 12f
                            )
                        )
                    }
                }

                GoogleMap(
                    modifier = Modifier.fillMaxSize(),
                    cameraPositionState = cameraPositionState,
                    uiSettings = MapUiSettings(
                        compassEnabled = false,
                        indoorLevelPickerEnabled = false,
                        mapToolbarEnabled = false,
                        zoomControlsEnabled = false,
                        myLocationButtonEnabled = false,
                    ),
                    mapColorScheme = ComposeMapColorScheme.FOLLOW_SYSTEM
                ) {

                    // if roadPath != null show the road
                    if (roadPath.isNotEmpty()) {
                        Polyline(
                            points = roadPath,
                            color = colorResource(R.color.taxi)
                        )
                    }

                    departurePoint?.let {
                        Marker(
                            state = MarkerState(LatLng(it.latitude, it.longitude)),
                            icon = bitmapDescriptorFromVector(
                                context,
                                R.drawable.map_marker_from
                            )
                        )
                    }

                    arrivalPoint?.let {
                        Marker(
                            state = MarkerState(LatLng(it.latitude, it.longitude)),
                            icon = bitmapDescriptorFromVector(
                                context,
                                R.drawable.map_marker_to
                            )
                        )
                    }
                }

                Row(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(start = 20.dp, end = 20.dp, bottom = 20.dp)
                ) {
                    val priceOfTrip by viewModel.priceOfTrip.collectAsState()
                    animationClass.SelfFromLeft(isVisible = priceOfTrip != null) {
                        styleClass.DecorationBackground(
                            color = colorResource(R.color.whiteF2B20)
                        )
                        {
                            Text(
                                modifier = Modifier
                                    .padding(horizontal = 12.dp, vertical = 12.dp),
                                text = "${stringResource(R.string.price)}: $priceOfTrip$",
                                fontFamily = unbounded_regular,
                                fontSize = 12.sp,
                                color = colorResource(R.color.black15WF2),
                            )
                        }

                    }

                    Spacer(Modifier.weight(1f))

                    styleClass.DecorationBackground(
                        modifier = Modifier.clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            onClick = {
                                // put the road
                                viewModel.putRoad()
                            }
                        ),
                        color = colorResource(R.color.whiteF2B20)
                    )
                    {
                        Text(
                            modifier = Modifier
                                .padding(horizontal = 12.dp, vertical = 12.dp),
                            text = stringResource(R.string.make_a_route),
                            fontFamily = unbounded_regular,
                            fontSize = 12.sp,
                            color = colorResource(R.color.black15WF2),
                        )
                    }
                }

                val focus = LocalFocusManager.current
                val foundPlacesState by viewModel.searchPlacesList.collectAsState()
                var listOfPlaces by remember { mutableStateOf<List<MapPlaceInfo>>(listOf()) }


                LaunchedEffect(foundPlacesState) {

                    // Update to last not empty state
                    // Needed for correct UI
                    if (foundPlacesState.isNotEmpty()) {
                        listOfPlaces = foundPlacesState
                    }
                }

                animationClass.SelfFromBottom(
                    modifier = Modifier.align(Alignment.BottomCenter),
                    duration = 800,
                    isVisible = foundPlacesState.isNotEmpty()
                ) {

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .align(Alignment.BottomCenter)
                            .padding(horizontal = 40.dp)
                            .background(colorResource(R.color.whiteDB30))
                            .padding(vertical = 15.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {

                        items(listOfPlaces) { place ->
                            Text(
                                modifier = Modifier
                                    .clickable {
                                        viewModel.selectPlace(
                                            place,
                                            onPlaceFound = { place ->
                                                focus.clearFocus()

                                                coScope.launch(Dispatchers.Main) {
                                                    cameraPositionState.animate(
                                                        CameraUpdateFactory.newLatLngZoom(
                                                            LatLng(
                                                                place.latitude,
                                                                place.longitude,
                                                            ),
                                                            12f
                                                        ),
                                                        1000
                                                    )
                                                }
                                            }
                                        )
                                    }
                                    .padding(horizontal = 20.dp),
                                text = place.description,
                                fontFamily = unbounded_regular,
                                fontSize = 14.sp,
                                color = colorResource(R.color.black15WF2)
                            )
                        }
                    }
                }
            }

            Column(
                modifier = Modifier
                    .height(IntrinsicSize.Min)
                    .background(colorResource(R.color.whiteF2B20))
                    .padding(horizontal = 40.dp, vertical = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                styleClass.DecorationBackground(
                    color = colorResource(R.color.black20WF2)
                ) {
                    val textOfField by viewModel.textOfDeparturePoint.collectAsState()

                    BasicTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.CenterStart)
                            .padding(horizontal = 20.dp, vertical = 15.dp)
                            .padding(end = 10.dp),
                        value = textOfField,
                        onValueChange = { text ->
                            viewModel.searchPlacesDeparture(text)
                        },
                        cursorBrush = SolidColor(colorResource(R.color.whiteF2B15)),
                        textStyle = TextStyle(
                            fontFamily = unbounded_regular,
                            fontSize = 14.sp,
                            color = colorResource(R.color.whiteF2B15),
                        ),
                        maxLines = 3,
                        decorationBox = { TextField ->
                            Box {
                                TextField()

                                if (textOfField.isEmpty()) {
                                    Text(
                                        text = stringResource(R.string.enter_departure_point),
                                        fontFamily = unbounded_regular,
                                        fontSize = 14.sp,
                                        color = colorResource(R.color.whiteF2B15)
                                    )
                                }
                            }
                        }
                    )

                    Image(
                        modifier = Modifier
                            .size(30.dp)
                            .align(Alignment.CenterEnd)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null,
                                onClick = {
                                    viewModel.selectUserLocationAsDeparture()
                                }
                            ),
                        painter = painterResource(R.drawable.your_location),
                        contentDescription = null
                    )
                }

                Spacer(Modifier.height(25.dp))

                styleClass.DecorationBackground(
                    color = colorResource(R.color.black20WF2)
                ) {
                    val textOfField by viewModel.textOfArrivalPoint.collectAsState()
                    BasicTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.CenterStart)
                            .padding(horizontal = 20.dp, vertical = 15.dp),
                        value = textOfField,
                        onValueChange = { text ->
                            viewModel.searchPlacesArrival(text)
                        },
                        cursorBrush = SolidColor(colorResource(R.color.whiteF2B15)),
                        textStyle = TextStyle(
                            fontFamily = unbounded_regular,
                            fontSize = 14.sp,
                            color = colorResource(R.color.whiteF2B15),
                        ),
                        maxLines = 3,
                        decorationBox = { textField ->
                            Box {
                                textField()

                                if (textOfField.isEmpty()) {
                                    Text(
                                        text = stringResource(R.string.enter_point_of_arrival),
                                        fontFamily = unbounded_regular,
                                        fontSize = 14.sp,
                                        color = colorResource(R.color.whiteF2B15)
                                    )
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}


// Function that converts the vector form to Bitmap form.
private fun bitmapDescriptorFromVector(context: Context, vectorResId: Int): BitmapDescriptor? {
    val vectorDrawable = ContextCompat.getDrawable(context, vectorResId)
    return vectorDrawable?.let {
        it.setBounds(0, 0, it.intrinsicWidth, it.intrinsicHeight)
        val bitmap = Bitmap.createBitmap(it.intrinsicWidth, it.intrinsicHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        it.draw(canvas)
        BitmapDescriptorFactory.fromBitmap(bitmap)
    }
}