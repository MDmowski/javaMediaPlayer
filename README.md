# Media Player based on JavaFX

## Features
- Support for mp3 and mp4
- Loading a file from a computer via default file manager
- Speeding up and down media
- Fullscreen mode - double-click and F key
- Scrolling through media
- Adjusting volume
- Jumping -/+ 10s - keys J/L
- Pausing video - key K
- Muting/unmuting - key M
- Spectrum visualizer when mp3 file loaded
- Changing spectrum color using keys - R - red, G - green, B - blue

## How to run project
In order to run the project you should add the following VM Options ( easy using IntelliJ , Eclipse or Netbeans ) :

```
--add-exports javafx.controls/com.sun.javafx.scene.control.behavior=com.jfoenix
--add-exports javafx.controls/com.sun.javafx.scene.control=com.jfoenix
--add-exports javafx.base/com.sun.javafx.binding=com.jfoenix
--add-exports javafx.graphics/com.sun.javafx.stage=com.jfoenix
--add-exports javafx.base/com.sun.javafx.event=com.jfoenix
--add-exports javafx.graphics/com.sun.javafx.scene=org.controlsfx.controls,
--add-exports javafx.graphics/com.sun.javafx.scene.traversal=org.controlsfx.controls
```