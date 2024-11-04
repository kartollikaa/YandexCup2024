package ru.kartollika.yandexcup.canvas.compose.frames

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.kartollika.yandexcup.R
import ru.kartollika.yandexcup.canvas.compose.DrawingCanvas
import ru.kartollika.yandexcup.canvas.mvi.CanvasState
import ru.kartollika.yandexcup.canvas.mvi.EditorConfiguration
import ru.kartollika.yandexcup.canvas.mvi.RealFrame
import ru.kartollika.yandexcup.canvas.rememberCanvasDrawState
import ru.kartollika.yandexcup.canvas.vm.FramesViewModel
import ru.kartollika.yandexcup.ui.theme.YandexCup2024Theme

@Composable
fun FramesScreen(
  canvasState: CanvasState,
  modifier: Modifier = Modifier,
  viewModel: FramesViewModel = hiltViewModel(),
  selectFrame: (Int) -> Unit = {},
  deleteFrame: (Int) -> Unit = {},
  deleteAllFrames: () -> Unit = {},
  generateDummyFrames: () -> Unit = {},
) {
  Surface(
    modifier = modifier
  ) {
    Column {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
      ) {
        TextButton(
          modifier = Modifier
            .padding(end = 16.dp),
          onClick = {
            generateDummyFrames()
          }
        ) {
          Text(
            text = "Создать dummy кадры",
            color = MaterialTheme.colorScheme.primary
          )
        }

        TextButton(
          modifier = Modifier,
          onClick = {
            deleteAllFrames()
          }
        ) {
          Text(
            text = "Удалить кадры",
            color = Color.Red
          )
        }
      }
      LazyColumn(
        modifier = Modifier.fillMaxSize()
      ) {
        items(canvasState.maxFramesCount) { index ->
          val frame = viewModel.getFrameAt(index)?.materialize() as? RealFrame

          FrameItem(
            modifier = Modifier
              .fillMaxWidth()
              .clickable {
                selectFrame(index)
              }
              .padding(16.dp),
            frame = frame,
            index = index,
            deleteFrame = {
              deleteFrame(index)
            },
            editorConfiguration = canvasState.editorConfiguration,
          )
        }
      }
    }
  }
}

@Composable
fun FrameItem(
  modifier: Modifier = Modifier,
  frame: RealFrame?,
  index: Int,
  editorConfiguration: EditorConfiguration,
  deleteFrame: () -> Unit = {},
) {
  Row(
    modifier = modifier,
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    val density = LocalDensity.current
    DrawingCanvas(
      paths = {
        frame?.paths
      },
      previousPaths = {
        null
      },
      modifier = Modifier
        .height(100.dp)
        .width(60.dp)
        .background(Color.White),
      scale = with(density) { 100.dp.toPx() } / editorConfiguration.canvasSize.height,
      canvasDrawUiState = rememberCanvasDrawState()
    )

    Text(
      text = "Кадр $index",
      modifier = Modifier.weight(1f),
    )

    IconButton(
      onClick = {
        deleteFrame()
      }
    ) {
      Icon(
        modifier = Modifier,
        painter = painterResource(R.drawable.bin),
        contentDescription = null
      )
    }
  }
}

@Preview
@Composable
private fun FramesScreenPreview() {
  YandexCup2024Theme {
    FramesScreen(
      modifier = Modifier.fillMaxSize(),
      canvasState = CanvasState(currentFrame = RealFrame())
//      frames = persistentListOf(Frame())
    )
  }
}