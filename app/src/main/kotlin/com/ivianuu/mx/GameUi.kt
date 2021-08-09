/*
 * Copyright 2021 Manuel Wrage
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ivianuu.mx

import android.os.*
import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.res.*
import androidx.compose.ui.unit.*
import com.ivianuu.essentials.optics.*
import com.ivianuu.essentials.store.*
import com.ivianuu.essentials.ui.animation.*
import com.ivianuu.essentials.ui.animation.transition.*
import com.ivianuu.essentials.ui.material.Scaffold
import com.ivianuu.essentials.ui.material.TopAppBar
import com.ivianuu.essentials.ui.navigation.*
import com.ivianuu.injekt.*
import com.ivianuu.injekt.android.*
import com.ivianuu.injekt.coroutines.*
import com.ivianuu.injekt.scope.*
import kotlinx.coroutines.flow.*
import kotlin.time.*

@Provide class GameKey : RootKey

@OptIn(ExperimentalAnimationApi::class)
@Provide val gameUi: ModelKeyUi<GameKey, GameModel> = {
  Scaffold(
    topBar = { TopAppBar(title = { Text(stringResource(R.string.app_name)) }) }
  ) {
    Column(
      modifier = Modifier.fillMaxSize(),
      verticalArrangement = Arrangement.Center,
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Dice(dice = model.dice1, model.dicesVisible)

        Spacer(Modifier.width(8.dp))

        Dice(dice = model.dice2, model.dicesVisible)
      }

      Spacer(Modifier.height(8.dp))

      Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Button(
          modifier = Modifier.height(72.dp),
          onClick = model.rollDices,
          elevation = null
        ) {
          Text(stringResource(R.string.roll_dices))
        }

        Spacer(Modifier.width(8.dp))

        AnimatedVisibility(model.dice1 != null) {
          OutlinedButton(
            onClick = model.toggleDicesVisibility,
            modifier = Modifier.height(72.dp),
          ) {
            AnimatedContent(model.dicesVisible) { currentDicesVisible ->
              Text(
                text = stringResource(
                  if (currentDicesVisible) R.string.hide_dices else R.string.show_dices
                )
              )
            }
          }
        }
      }
    }
  }
}

@Composable private fun Dice(dice: Int?, visible: Boolean) {
  AnimatedBox(
    current = dice to visible,
    transition = CrossFadeStackTransition(defaultAnimationSpec(120.milliseconds))
  ) { (currentDice, currentVisible) ->
    Box(
      modifier = Modifier
        .size(128.dp)
        .border(
          width = 1.dp,
          color = Color.Black,
          shape = RoundedCornerShape(8.dp)
        )
        .clickable {},
      contentAlignment = Alignment.Center
    ) {
      Text(if (currentVisible) currentDice?.toString() ?: "?" else "?")
    }
  }
}

@Optics data class GameModel(
  val dice1: Int? = null,
  val dice2: Int? = null,
  val dicesVisible: Boolean = false,
  val rollDices: () -> Unit = {},
  val toggleDicesVisibility: () -> Unit = {}
)

@Provide fun gameModel(
  scope: InjektCoroutineScope<KeyUiScope>,
  vibrator: @SystemService Vibrator
): @Scoped<KeyUiScope> StateFlow<GameModel> = scope.state(GameModel()) {
  action(GameModel.rollDices()) {
    val diceResult1 = (Math.random() * 6 + 1).toInt()
    val diceResult2 = (Math.random() * 6 + 1).toInt()

    val dice1 = if (diceResult2 > diceResult1) diceResult2 else diceResult1
    val dice2 = if (diceResult2 > diceResult1) diceResult1 else diceResult2

    vibrator.vibrate(VibrationEffect.createOneShot(100, 10))

    update {
      copy(dice1 = dice1, dice2 = dice2, dicesVisible = true)
    }
  }

  action(GameModel.toggleDicesVisibility()) {
    update { copy(dicesVisible = !dicesVisible) }
  }
}
