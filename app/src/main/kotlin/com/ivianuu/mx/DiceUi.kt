package com.ivianuu.mx

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.geometry.*
import androidx.compose.ui.unit.*
import com.ivianuu.essentials.ui.animation.*
import com.ivianuu.essentials.ui.animation.transition.*
import kotlin.time.*

@Composable fun Dice(dice: Int?, visible: Boolean) {
  AnimatedBox(
    current = dice to visible,
    transition = CrossFadeStackTransition(defaultAnimationSpec(120.milliseconds))
  ) { (currentDice, currentVisible) ->
    Box(
      modifier = Modifier
        .size(128.dp)
        .border(
          width = 1.dp,
          color = LocalContentColor.current,
          shape = RoundedCornerShape(8.dp)
        )
        .clickable {},
      contentAlignment = Alignment.Center
    ) {
      if (currentDice == null || !currentVisible) {
        Text(
          text = "?",
          fontSize = 36.sp
        )
      } else {
        DiceContent(currentDice)
      }
    }
  }
}

@Composable private fun DiceContent(dice: Int) {
  val dotColor = LocalContentColor.current
  Canvas(Modifier.fillMaxSize()) {
    val dotRadius = 8.dp.toPx()

    when (dice) {
      1 -> {
        drawCircle(color = dotColor, radius = dotRadius)
      }
      2 -> {
        drawCircle(
          color = dotColor,
          radius = dotRadius,
          center = Offset(
            x = size.width * 0.25f,
            y = size.height * 0.75f
          )
        )

        drawCircle(
          color = dotColor,
          radius = dotRadius,
          center = Offset(
            x = size.width * 0.75f,
            y = size.height * 0.25f
          )
        )
      }
      3 -> {
        drawCircle(
          color = dotColor,
          radius = dotRadius,
          center = Offset(
            x = size.width * 0.25f,
            y = size.height * 0.75f
          )
        )

        drawCircle(color = dotColor, radius = dotRadius)

        drawCircle(
          color = dotColor,
          radius = dotRadius,
          center = Offset(
            x = size.width * 0.75f,
            y = size.height * 0.25f
          )
        )
      }
      4 -> {
        drawCircle(
          color = dotColor,
          radius = dotRadius,
          center = Offset(
            x = size.width * 0.25f,
            y = size.height * 0.25f
          )
        )

        drawCircle(
          color = dotColor,
          radius = dotRadius,
          center = Offset(
            x = size.width * 0.75f,
            y = size.height * 0.25f
          )
        )

        drawCircle(
          color = dotColor,
          radius = dotRadius,
          center = Offset(
            x = size.width * 0.25f,
            y = size.height * 0.75f
          )
        )

        drawCircle(
          color = dotColor,
          radius = dotRadius,
          center = Offset(
            x = size.width * 0.75f,
            y = size.height * 0.75f
          )
        )
      }
      5 -> {
        drawCircle(
          color = dotColor,
          radius = dotRadius,
          center = Offset(
            x = size.width * 0.25f,
            y = size.height * 0.25f
          )
        )

        drawCircle(
          color = dotColor,
          radius = dotRadius,
          center = Offset(
            x = size.width * 0.75f,
            y = size.height * 0.25f
          )
        )

        drawCircle(
          color = dotColor,
          radius = dotRadius,
          center = Offset(
            x = size.width * 0.25f,
            y = size.height * 0.75f
          )
        )

        drawCircle(
          color = dotColor,
          radius = dotRadius,
          center = Offset(
            x = size.width * 0.75f,
            y = size.height * 0.75f
          )
        )

        drawCircle(color = dotColor, radius = dotRadius)
      }
      6 -> {
        drawCircle(
          color = dotColor,
          radius = dotRadius,
          center = Offset(
            x = size.width * 0.25f,
            y = size.height * 0.2f
          )
        )

        drawCircle(
          color = dotColor,
          radius = dotRadius,
          center = Offset(
            x = size.width * 0.75f,
            y = size.height * 0.2f
          )
        )

        drawCircle(
          color = dotColor,
          radius = dotRadius,
          center = Offset(
            x = size.width * 0.25f,
            y = size.height * 0.5f
          )
        )

        drawCircle(
          color = dotColor,
          radius = dotRadius,
          center = Offset(
            x = size.width * 0.75f,
            y = size.height * 0.5f
          )
        )

        drawCircle(
          color = dotColor,
          radius = dotRadius,
          center = Offset(
            x = size.width * 0.25f,
            y = size.height * 0.8f
          )
        )

        drawCircle(
          color = dotColor,
          radius = dotRadius,
          center = Offset(
            x = size.width * 0.75f,
            y = size.height * 0.8f
          )
        )
      }
      else -> throw AssertionError("Unexpected dice $dice")
    }
  }
}
