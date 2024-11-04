package ru.kartollika.yandexcup.canvas.vm

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.kartollika.yandexcup.canvas.sources.FramesRepository
import javax.inject.Inject

@HiltViewModel
class FramesViewModel @Inject constructor(
  private val framesRepository: FramesRepository
) : ViewModel() {

  fun getFrameAt(index: Int) = framesRepository.frames.getOrNull(index)
}