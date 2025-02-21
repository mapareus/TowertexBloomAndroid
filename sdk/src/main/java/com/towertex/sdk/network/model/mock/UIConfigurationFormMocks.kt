package com.towertex.sdk.network.model.mock

import com.towertex.sdk.network.model.UIConfigurationForm
import com.towertex.sdk.network.model.UIConfigurationStyle
import com.towertex.sdk.network.model.UIConfigurationText
import kotlinx.serialization.json.Json

private val uiConfigurationStyleMock1 = UIConfigurationStyle(
    fontSizeSP = 16,
    fontColorHex = "#0000FF",
    backgroundColorHex = "#00FFFF",
    backgroundImageUrl = "https://content.sportslogos.net/logos/6/220/thumbs/22081902021.gif"
)

private val uiConfigurationStyleMock2 = UIConfigurationStyle(
    fontSizeSP = 14,
    fontColorHex = "#FF0000",
    backgroundColorHex = "#FFFF00",
    backgroundImageUrl = "https://content.sportslogos.net/logos/6/213/thumbs/slhg02hbef3j1ov4lsnwyol5o.gif"
)

private val uiConfigurationFormMock1 = UIConfigurationForm(
    textInputs = listOf(
        UIConfigurationText(
            title = "Username",
            placeholder = "Enter your username",
            style = uiConfigurationStyleMock1,
        ),
        UIConfigurationText(
            title = "Password",
            placeholder = "Enter your password",
        )
    ),
    submitButtonLabel = "Submit1",
    style = uiConfigurationStyleMock2
)

val uiConfigurationFormMock1Json = Json.encodeToString(uiConfigurationFormMock1)

val uiConfigurationFormMock2Json = """{
       "textInputs":[
          {
             "title":"Username",
             "placeholder":"Enter your username",
             "style":{
                "fontSizeSP":14,
                "fontColorHex":"#FFFFFF",
                "backgroundColorHex":"#000000",
                "backgroundImageUrl":"https://content.sportslogos.net/logos/6/5120/thumbs/512019262015.gif"
             }
          },
          {
             "title":"Password",
             "placeholder":"Enter your password"
          }
       ],
       "submitButtonLabel":"Submit2",
       "style":{
          "fontSizeSP":16,
          "fontColorHex":"#000000",
          "backgroundColorHex":"#FFFFFF",
          "backgroundImageUrl":"https://content.sportslogos.net/logos/6/221/thumbs/hj3gmh82w9hffmeh3fjm5h874.gif"
       }
    }
"""

fun main() {
    println(uiConfigurationFormMock1Json)
    println(uiConfigurationFormMock2Json)
}