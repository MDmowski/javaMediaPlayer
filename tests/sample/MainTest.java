package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.junit.*;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;

import java.io.IOException;

import static org.junit.Assert.*;

public class MainTest extends ApplicationTest {

    FXMLLoader loader;

    Controller testController;

    Stage testStage;

    public MainTest() throws IOException {
    }

    @Override
    public void start (Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
        Parent mainNode = loader.load();
        testController = loader.getController();
        testStage = stage;
        stage.setScene(new Scene(mainNode));
        stage.show();
        stage.toFront();
    }

    @Before
    public void setUp (){
    }

    @After
    public void tearDown () throws Exception {
        FxToolkit.hideStage();
        release(new KeyCode[]{});
        release(new MouseButton[]{});
    }

    @Test
    public void playPause () {
        clickOn("#testButton");
        clickOn("#playButton");
        Assert.assertEquals(MediaPlayer.Status.PAUSED, testController.getCurrentStatus());
        clickOn("#playButton");
        Assert.assertEquals(MediaPlayer.Status.PLAYING, testController.getCurrentStatus());
        press(KeyCode.K);
        Assert.assertEquals(MediaPlayer.Status.PAUSED, testController.getCurrentStatus());
    }
    @Test
    public void jumpForwardAndBackward () throws InterruptedException {
        clickOn("#testButton");
        Thread.sleep(100);
        Duration current = testController.getCurrentTime();
        press(KeyCode.L).release(KeyCode.L);
        Assert.assertTrue("New current time should be greater then old one", current.toSeconds() < testController.getCurrentTime().toSeconds());
        current = testController.getCurrentTime();
        press(KeyCode.J).release(KeyCode.J);
        Assert.assertTrue("New current time should be lesser then old one", current.toSeconds() > testController.getCurrentTime().toSeconds());
    }
    @Test
    public void audioSpectrumColors () {
        clickOn("#testButton");
        testController.audioSpectrumEnable();
        assertNotEquals(Color.TRANSPARENT, testController.getSpectrumFill());
        press(KeyCode.R).release(KeyCode.R);
        assertEquals(Color.RED, testController.getSpectrumFill());
        press(KeyCode.G).release(KeyCode.G);
        assertEquals(Color.GREEN, testController.getSpectrumFill());
        press(KeyCode.B).release(KeyCode.B);
        assertEquals(Color.BLUE, testController.getSpectrumFill());
        testController.audioSpectrumDisable();
        assertEquals(Color.TRANSPARENT, testController.getSpectrumFill());
    }
    @Test
    public void muting () {
        clickOn("#testButton");
        double volume  = testController.getVolume();
        press(KeyCode.M).release(KeyCode.M);
        assertEquals("Volume after muting should be set to 0", 0, testController.getVolume(), 0.0);
        press(KeyCode.M).release(KeyCode.M);
        assertEquals("Volume after unmuting should be set to previous value", testController.getVolume(), volume, 0.0);
    }
    @Test
    public void changeSpeed () {
        clickOn("#testButton");
        double rate = testController.getCurrentRate();
        clickOn("#fasterButton");
        assertEquals("After clicking faster button, rate should be increased by 25%", testController.getCurrentRate(), rate + 0.25, 0.0);
        clickOn("#normalSpeedButton");
        assertEquals("After clicking normal speed button, rate should be set on 100%", testController.getCurrentRate(), 1, 0.0);
        rate = testController.getCurrentRate();
        clickOn("#slowerButton");
        assertTrue("After clicking slower button, rate should be decreased by 25%", testController.getCurrentRate() == rate-0.25);
    }
    @Test
    public void changeVolume(){
        clickOn("#testButton");
        clickOn("#volumeSlider");
        assertEquals("Volume after clicking on the center should be set to 50%", 0.5, testController.getVolume(), 0.05);
    }
    @Test
    public void scrollMedia() throws InterruptedException {
        clickOn("#testButton");
        Thread.sleep(200);
        Duration time = testController.getCurrentTime();
        clickOn("#timeSlider");
        assertNotEquals(time, testController.getCurrentTime());
    }
    @Test
    public void stopButton () throws InterruptedException {
        clickOn("#testButton");
        clickOn("#stopButton");
        Thread.sleep(100);
        Assert.assertEquals(MediaPlayer.Status.STOPPED, testController.getCurrentStatus());
        clickOn("#playButton");
        Assert.assertEquals(MediaPlayer.Status.PLAYING, testController.getCurrentStatus());
        press(KeyCode.K);
        Assert.assertEquals(MediaPlayer.Status.PAUSED, testController.getCurrentStatus());
    }

}