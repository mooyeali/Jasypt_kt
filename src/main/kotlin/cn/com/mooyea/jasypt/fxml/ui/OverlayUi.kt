package cn.com.mooyea.jasypt.fxml.ui

import javafx.scene.layout.Pane
import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle

class OverlayUi() : Pane() {
    companion object{
        private const val OPACITY = 0.5
    }

    init {
        val overlayRect =  Rectangle()
        overlayRect.fill = Color.BLACK.deriveColor(0.0, 1.0, 1.0, OPACITY)
        overlayRect.isManaged = false
        overlayRect.widthProperty().bind(widthProperty())
        overlayRect.heightProperty().bind(heightProperty())
        children.add(overlayRect)
        isVisible = false
    }
    fun show(){
        isVisible = true
    }
    fun hide(){
        isVisible = false
    }

}
