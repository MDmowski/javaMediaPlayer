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

## Manual (<a href="https://gitlab-stud.elka.pw.edu.pl/mdmowski/javamediaplayer/-/blob/master/doc/manual.md">Manual only</a>)


### Open a file you want to play

Use this button to choose a file you want to play

![Open file img](doc/openFile.png)

**Instructional video**

![Open file gif](doc/openFile_small.gif)

### Play and pause

Use this button to play and pause playback. You can press K to play and pause too.

![Play and pause img](doc/playPause.png)

**Instructional video**

![Play and pause gif](doc/playPause_small.gif)

### Stop playback

Use this button to stop playback. You can start from the beginning afterwards by clicking the play button. 
![Stop playback img](doc/stop.png)

**Instructional video**

![Stop playback gif](doc/stop_small.gif)

### Change volume and mute

Use this slider to change volume. Press M to mute and press again to unmute.

![Change volume and mute img](doc/changeVolume.png)

**Instructional video**

![Change volume and mute gif](doc/volumeChange_small.gif)

### Scrub through the file

Use this slider to scrub through the file

![Scrubbing through file gif](doc/scrubbing.png)

**Instructional video**

![Scrubbing through file gif](doc/scrubbing_small.gif)

### Jump forward and backward

Press L to jump 10 seconds forward.
Press J to jump 10 seconds forward.
Press K to pause and play.

**Instructional video**

![Jumping forward and backward gif](doc/jumping_small.gif)

### Change playback speed

Use this buttons to change playback speed.

![Changing playback speed gif](doc/changeSpeed.png)

**Instructional video**

![Changing playback speed gif](doc/changeSpeed_small.gif)

### Change color of the audio spectrum

If you have chosen mp3 file, audio spectrum will be visualised on the screen. You can change it's color by pressing keys:
R - red, G - green, B - blue.

**Instructional video**

![Changing spectrum color gif](doc/colorChange_small.gif)

### Open fullscreen

Press F or double-click on media to switch to full-screen mode. Press ESC to exit full-screen mode.

![Fullscreen mode](doc/fullscreenMode.png)

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

