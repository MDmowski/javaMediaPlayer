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
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import javafx.event.ActionEvent;
import javafx.util.Duration;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    private MediaPlayer mediaPlayer;

    @FXML
    private MediaView mediaView;

    private String filePath;
    private double oldVolume = 0;
    boolean isFileLoaded = false;

    @FXML
    private Slider volumeSlider;
    @FXML
    private Slider timeSlider;


    @FXML
    private void selectFileButtonAction(ActionEvent event){
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Select a File (*.mp4, *.mp3)", "*.mp4", "*.mp3");
        fileChooser.getExtensionFilters().add(filter);
        File file = fileChooser.showOpenDialog(null);
        filePath = file.toURI().toString();
        if(filePath != null) {
            Media media = null;
            try{
                media = new Media(filePath);
            }catch (Exception e){
                System.out.print(e);
            }
            mediaPlayer = new MediaPlayer(media);
            mediaView.setMediaPlayer(mediaPlayer);
            mediaPlayer.setOnReady(new Runnable() {
                @Override
                public void run() {
                    isFileLoaded = true;
                    DoubleProperty width = mediaView.fitWidthProperty();
                    DoubleProperty height = mediaView.fitHeightProperty();

                    width.bind(Bindings.selectDouble(mediaView.sceneProperty(), "width"));
                    height.bind(Bindings.selectDouble(mediaView.sceneProperty(), "height"));

                    volumeSlider.setValue(mediaPlayer.getVolume() * 100);
                    volumeSlider.valueProperty().addListener(new InvalidationListener() {
                        @Override
                        public void invalidated(Observable observable) {
                            mediaPlayer.setVolume(volumeSlider.getValue() / 100);
                        }
                    });

                    timeSlider.setMin(0);
                    timeSlider.setMax(mediaPlayer.getTotalDuration().toSeconds());
                    timeSlider.setValue(0);

                    mediaPlayer.currentTimeProperty().addListener(new ChangeListener<Duration>() {
                        @Override
                        public void changed(ObservableValue<? extends Duration> observableValue, Duration duration, Duration current) {

                            timeSlider.setValue(current.toSeconds());
                        }
                    });

                    timeSlider.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent mouseEvent) {
                            mediaPlayer.seek(Duration.seconds(timeSlider.getValue()));
                        }
                    });

                    mediaPlayer.play();
                }

            });
        }

    }
    @FXML
    private void playButtonAction(ActionEvent event){
        playPause();
    }
    @FXML
    private void stopButtonAction(ActionEvent event){
        mediaPlayer.stop();
    }
    @FXML
    private void fasterButtonAction(ActionEvent event){
        mediaPlayer.setRate(mediaPlayer.getRate() + 0.25);
    }
    @FXML
    private void slowerButtonAction(ActionEvent event){
        if(mediaPlayer.getRate() > 0.25)
            mediaPlayer.setRate(mediaPlayer.getRate() - 0.25);
    }
    @FXML
    private void normalSpeedButtonAction(ActionEvent event){
        mediaPlayer.setRate(1);
    }
    @FXML
    private void exitButtonAction(ActionEvent event){
        System.exit(0);
    }
    @FXML
    private void playPause(){
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
    private void keyPressed(KeyEvent key){
        switch (key.getCode()){
            case L:
                mediaPlayer.seek(Duration.seconds(mediaPlayer.getCurrentTime().toSeconds()+10));
                break;
            case J:
                mediaPlayer.seek(Duration.seconds(mediaPlayer.getCurrentTime().toSeconds()-10));
                break;
            case K:
                playPause();
                break;
            case M:
                if(mediaPlayer.getVolume() != 0) {
                    oldVolume = mediaPlayer.getVolume();
                    mediaPlayer.setVolume(0);
                    volumeSlider.setValue(0);
                }else {
                    mediaPlayer.setVolume(oldVolume);
                    volumeSlider.setValue(oldVolume*100);
                }
                break;
        }
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
