package sample;

import javafx.animation.PauseTransition;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Binding;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.chart.BarChart;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.effect.BlendMode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.geometry.Insets;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.media.AudioSpectrumListener;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import javafx.event.ActionEvent;
import javafx.util.Duration;

import java.awt.*;
import java.io.File;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    private MediaPlayer mediaPlayer;

    private Media media = null;

    @FXML
    private MediaView mediaView;
    @FXML
    private HBox audioSpectrum = new HBox();
    @FXML
    private Rectangle box0 = new Rectangle();
    @FXML
    private Rectangle box1 = new Rectangle();
    @FXML
    private Rectangle box2 = new Rectangle();
    @FXML
    private Rectangle box3 = new Rectangle();
    @FXML
    private Rectangle box4 = new Rectangle();
    @FXML
    private Rectangle box5 = new Rectangle();
    @FXML
    private Rectangle box6 = new Rectangle();
    @FXML
    private Rectangle box7 = new Rectangle();
    @FXML
    private Rectangle box8 = new Rectangle();
    @FXML
    private Rectangle box9 = new Rectangle();
    @FXML
    private Rectangle box10 = new Rectangle();
    @FXML
    private Rectangle box11 = new Rectangle();
    @FXML
    private Rectangle box12 = new Rectangle();
    @FXML
    private Rectangle box13 = new Rectangle();
    @FXML
    private Rectangle box14 = new Rectangle();
    @FXML
    private Rectangle box15 = new Rectangle();

    private Color spectrumFill= Color.BLUE;


    private String filePath;
    private double oldVolume = 0;
    boolean isFileLoaded = false;

    @FXML
    private Slider volumeSlider;
    @FXML
    private Slider timeSlider;


    @FXML
    private void selectFileButtonAction(ActionEvent event){ // handling events related to the file selection button
        audioSpectrumDisable();
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Select a File (*.mp4, *.mp3)", "*.mp4", "*.mp3");
        fileChooser.getExtensionFilters().add(filter); // opening file chooser with filter - only mp3 and mp4 files
        File file = fileChooser.showOpenDialog(null);
        if(file != null) {  // if file opened correctly create new media
            filePath = file.toURI().toString();
            System.out.print(file.toURI().toString());
            media = null;
            try{
                media = new Media(filePath);
            }catch (Exception e){
                System.out.print(e);
            }
            mediaPlayer = new MediaPlayer(media); // load media to a media player
            mediaView.setMediaPlayer(mediaPlayer);
            mediaPlayer.setOnReady(new Runnable() {
                @Override
                public void run() {
                    isFileLoaded = true;
                    DoubleProperty width = mediaView.fitWidthProperty();
                    DoubleProperty height = mediaView.fitHeightProperty();

                    width.bind(Bindings.selectDouble(mediaView.sceneProperty(), "width"));
                    height.bind(Bindings.selectDouble(mediaView.sceneProperty(), "height"));

                    volumeSlider.setValue(mediaPlayer.getVolume() * 100); // set default volume to 100%
                    volumeSlider.valueProperty().addListener(new InvalidationListener() {
                        @Override
                        public void invalidated(Observable observable) {
                            mediaPlayer.setVolume(volumeSlider.getValue() / 100);
                        }
                    });


                    timeSlider.setCursor(Cursor.CLOSED_HAND);
                    timeSlider.setMin(0);
                    timeSlider.setMax(mediaPlayer.getTotalDuration().toSeconds()); // get max value of a slider based on the length of media
                    timeSlider.setValue(0);

                    mediaPlayer.currentTimeProperty().addListener(new ChangeListener<Duration>() { // update value of a slider based on the current time of media
                        @Override
                        public void changed(ObservableValue<? extends Duration> observableValue, Duration duration, Duration current) {

                            timeSlider.setValue(current.toSeconds());
                        }
                    });

                    timeSlider.setOnMouseClicked(new EventHandler<MouseEvent>() {  // jump to time based on the slider
                        @Override
                        public void handle(MouseEvent mouseEvent) {
                            mediaPlayer.seek(Duration.seconds(timeSlider.getValue()));
                        }
                    });

                    if(getExtensionFromFilePath(filePath).equals(Optional.of("mp3"))) // if mp3 loaded show audio spectrum
                        audioSpectrum();

                    mediaPlayer.play();
                }

            });
        }

    }
    @FXML
    private void playButtonAction(ActionEvent event){ // handling events related to the play button
        playPause();
    }
    @FXML
    private void stopButtonAction(ActionEvent event) { // handling events related to the stop button
        mediaPlayer.stop();
    }
    @FXML
    private void fasterButtonAction(ActionEvent event){ // handling events related to the faster button - add 25% speed
        mediaPlayer.setRate(mediaPlayer.getRate() + 0.25);
    }
    @FXML
    private void slowerButtonAction(ActionEvent event){ // handling events related to the slower button
        if(mediaPlayer.getRate() > 0.25)
            mediaPlayer.setRate(mediaPlayer.getRate() - 0.25); // decrease speed by 25% if speed wouldn't be 0
    }
    @FXML
    private void normalSpeedButtonAction(ActionEvent event){ // handling events related to the normal speed button - setting speed to 100%
        mediaPlayer.setRate(1);
    }
    @FXML
    private void exitButtonAction(ActionEvent event){ // handling events related to the exit button - exit program
        System.exit(0);
    }
    @FXML
    private void playPause(){ // if playing - pause, if paused - play
        if(isFileLoaded) {
            if (mediaPlayer.getStatus().equals(MediaPlayer.Status.PAUSED) || mediaPlayer.getStatus().equals(MediaPlayer.Status.STOPPED)) {
                mediaPlayer.play();
            }
            if (mediaPlayer.getStatus().equals(MediaPlayer.Status.PLAYING)) {
                mediaPlayer.pause();
            }
        }
    }
    @FXML
    private void keyPressed(KeyEvent key){ // handling events related to keys pressed
        switch (key.getCode()){
            case L: // jump 10 seconds forward
                mediaPlayer.seek(Duration.seconds(mediaPlayer.getCurrentTime().toSeconds()+10));
                break;
            case J: // jump 10 seconds back
                mediaPlayer.seek(Duration.seconds(mediaPlayer.getCurrentTime().toSeconds()-10));
                break;
            case K: // play or pause
                playPause();
                break;
            case M: // mute/unmute
                if(mediaPlayer.getVolume() != 0) {
                    oldVolume = mediaPlayer.getVolume();
                    mediaPlayer.setVolume(0);
                    volumeSlider.setValue(0);
                }else {
                    mediaPlayer.setVolume(oldVolume);
                    volumeSlider.setValue(oldVolume*100);
                }
                break;
            case R: // change spectrum color to red
                spectrumFill = Color.RED;
                break;
            case G: // change spectrum color to green
                spectrumFill = Color.GREEN;
                break;
            case B: // change spectrum color to blue
                spectrumFill = Color.BLUE;
                break;
        }
    }
    @FXML
    public void audioSpectrum(){ // calculating and displaying audio spectrum
        audioSpectrumEnable();
        int bands = mediaPlayer.getAudioSpectrumNumBands();
        mediaPlayer.setAudioSpectrumListener(new AudioSpectrumListener() {
            @Override
            public void spectrumDataUpdate(double v, double v1, float[] floats, float[] floats1) {
                box0.setFill(spectrumFill);
                box0.setHeight((avg(0,bands/16, floats)+60)*10);
                box1.setFill(spectrumFill);
                box1.setHeight((avg(bands/16,bands/16, floats)+60)*10);
                box2.setFill(spectrumFill);
                box2.setHeight((avg(2*bands/16,bands/16, floats)+60)*10);
                box3.setFill(spectrumFill);
                box3.setHeight((avg(3*bands/16,bands/16, floats)+60)*10);
                box4.setFill(spectrumFill);
                box4.setHeight((avg(4*bands/16,bands/16, floats)+60)*10);
                box5.setFill(spectrumFill);
                box5.setHeight((avg(5*bands/16,bands/16, floats)+60)*10);
                box6.setFill(spectrumFill);
                box6.setHeight((avg(6*bands/16,bands/16, floats)+60)*10);
                box7.setFill(spectrumFill);
                box7.setHeight((avg(7*bands/16,bands/16, floats)+60)*10);
                box8.setFill(spectrumFill);
                box8.setHeight((avg(8*bands/16,bands/16, floats)+60)*10);
                box9.setFill(spectrumFill);
                box9.setHeight((avg(9*bands/16,bands/16, floats)+60)*10);
                box10.setFill(spectrumFill);
                box10.setHeight((avg(10*bands/16,bands/16, floats)+60)*10);
                box11.setFill(spectrumFill);
                box11.setHeight((avg(11*bands/16,bands/16, floats)+60)*10);
                box12.setFill(spectrumFill);
                box12.setHeight((avg(12*bands/16,bands/16, floats)+60)*10);
                box13.setFill(spectrumFill);
                box13.setHeight((avg(13*bands/16,bands/16, floats)+60)*10);
                box14.setFill(spectrumFill);
                box14.setHeight((avg(14*bands/16,bands/16, floats)+60)*10);
                box15.setFill(spectrumFill);
                box15.setHeight((avg(15*bands/16,bands/16, floats)+60)*10);
            }
        });
    }

    @FXML
    public void audioSpectrumEnable(){ // enabling audio spectrum
        spectrumFill = Color.BLUE;
    }
    @FXML
    public void audioSpectrumDisable(){ // disabling audio spectrum
        spectrumFill = Color.TRANSPARENT;
    }

    @FXML
    private float avg(int start, int number, float[] floats){
        float sum = 0;
        for(int i = start; i<start+number; i++){
            sum += floats[i];
        }
        return sum/number;
    }

    private Optional<String> getExtensionFromFilePath(String filePath) {
        return Optional.ofNullable(filePath)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(filePath.lastIndexOf(".") + 1));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
