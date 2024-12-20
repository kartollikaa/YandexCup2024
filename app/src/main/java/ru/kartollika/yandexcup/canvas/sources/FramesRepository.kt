package ru.kartollika.yandexcup.canvas.sources

import ru.kartollika.yandexcup.canvas.mvi.Frame
import ru.kartollika.yandexcup.canvas.mvi.RealFrame
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FramesRepository @Inject constructor() {
  val frames: MutableList<Frame> = mutableListOf(RealFrame())
}