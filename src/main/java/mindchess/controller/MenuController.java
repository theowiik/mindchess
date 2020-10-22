package mindchess.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import mindchess.model.ChessFacade;

import java.net.URL;
import java.util.*;

/**
 * MenuController handles the menu
 */
public class MenuController implements Initializable {
    private final String media_URL_1 = "/backgroundVideos/background_video_1.mp4";
    private final String media_URL_2 = "/backgroundVideos/background_video_2.mp4";
    private final String media_URL_3 = "/backgroundVideos/background_video_3.mp4";
    private final String media_URL_4 = "/backgroundVideos/background_video_4.mp4";
    private final String audio_URL_1 = "/backgroundMusic/C418_Sweden.mp3";
    private final String audio_URL_2 = "/backgroundMusic/C418_SubwooferLullaby.mp3";
    private final HashMap<String, Integer> timerMap = new LinkedHashMap<>();
    List<String> media_list = Arrays.asList(media_URL_1, media_URL_2, media_URL_3, media_URL_4);
    //private final String audio_URL_3 = "/backgroundMusic/CaptainSparklez_Revenge.mp3";
    List<String> audio_list = Arrays.asList(audio_URL_1, audio_URL_2);
    private ChessFacade model;
    private Parent chessParent;
    private Scene scene;
    private MediaPlayer mediaPlayer;
    private MediaPlayer audioPlayer;
    private ChessController chessController;
    @FXML
    private MediaView media;
    @FXML
    private AnchorPane rootAnchor;
    @FXML
    private AnchorPane gameListAnchorPane;
    @FXML
    private ImageView btnStart;
    @FXML
    private ImageView btnExit;
    @FXML
    private TextField player1NameField;
    @FXML
    private TextField player2NameField;
    @FXML
    private Label timeLabel;
    @FXML
    private ComboBox btnTimerDrop;
    @FXML
    private FlowPane gameListFlowPane;
    @FXML
    private Button btnBackGameList;

    /**
     * Gets the inputs from the start page and switches to the board scene, and brings the inputs with it
     * <p>
     * Happens when you click the start button
     *
     * @param event Clicked the button
     */
    @FXML
    void goToBoardNewGame(ActionEvent event) {
        model.createNewGame();

        if (!player1NameField.getText().equals("")) model.setCurrentPlayerWhiteName(player1NameField.getText());
        if (!player2NameField.getText().equals("")) model.setCurrentPlayerBlackName(player2NameField.getText());
        model.setCurrentWhitePlayerTimerTime(timerMap.get(btnTimerDrop.getValue()));
        model.setCurrentBlackPlayerTimerTime(timerMap.get(btnTimerDrop.getValue()));

        goToBoard(event.getSource());
    }

    void goToBoard(Object source) {
        Stage window = (Stage) ((Node) source).getScene().getWindow();
        window.setScene(scene);
        window.show();

        chessController.setMediaPlayer(mediaPlayer);
        chessController.setAudioPlayer(audioPlayer);
        chessController.init();
        chessController.drawPieces();
    }

    //-------------------------------------------------------------------------------------
    //Start
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeBackgroundMusic();
        initializeBackgroundVideo();
        initTimer();
    }

    /**
     * Fetches a random video from /background_videos/ and sets it as the background in our root
     */
    private void initializeBackgroundVideo() {
        Random ran = new Random();
        int videoIndex = ran.nextInt(4);
        mediaPlayer = new MediaPlayer(new Media(getClass().getResource(media_list.get(videoIndex)).toExternalForm()));
        //mediaPlayer.setAutoPlay(true);
        mediaPlayer.setCycleCount(1000);
        media.setMediaPlayer(mediaPlayer);
    }

    /**
     * Fetches a random music from /background_music/ and sets it as background music
     */
    private void initializeBackgroundMusic() {
        Random ran = new Random();
        int audioIndex = ran.nextInt(2);
        audioPlayer = new MediaPlayer(new Media(getClass().getResource(audio_list.get(audioIndex)).toExternalForm()));
        audioPlayer.setAutoPlay(true);
        audioPlayer.setVolume(0.5);
        audioPlayer.setOnEndOfMedia(() -> {
            int audioIndex2 = ran.nextInt(2);
            audioPlayer = new MediaPlayer(new Media(getClass().getResource(audio_list.get(audioIndex2)).toExternalForm()));
        });
    }

    /**
     * creates the timermap and gives the dropdown menu its options
     * <p>
     * Where the possible times is decided
     */
    private void initTimer() {
        timerMap.put("1 min", 60);
        timerMap.put("3 min", 180);
        timerMap.put("5 min", 300);
        timerMap.put("10 min", 600);
        timerMap.put("30 min", 1800);
        timerMap.put("60 min", 3600);

        timerMap.forEach((key, value) -> btnTimerDrop.getItems().add(key));

        btnTimerDrop.getSelectionModel().select(3);
    }

    public void createChessScene(Parent chessParent) {
        this.chessParent = chessParent;
        this.scene = new Scene(chessParent);
    }
  
    @FXML
    private void populateGameList() {
        gameListFlowPane.getChildren().clear();
        int i = 0;
        gameListAnchorPane.toFront();
        //Adds the plyControllers to the flowpane and fills the board with respective pieces
        for (String[] s : model.getPlayersAndStatusInGameList()) {
            GameListController gameListController = new GameListController(s[0],s[1],s[2]);
            gameListFlowPane.getChildren().add(gameListController);
            int finalI = i;
            System.out.println(i);
            gameListController.setOnMouseClicked(event -> {
                model.setIndexAsCurrentGame(finalI);
                gameListAnchorPane.toBack();
                goToBoard(event.getSource());
            });
            i++;
        }
    }

    @FXML
    private void closeGameList(){
        gameListAnchorPane.toBack();
    }

    //-------------------------------------------------------------------------------------
    //End

    /**
     * Exits the application when called
     *
     * @param event Pressed the button
     */
    @FXML
    void Exit(ActionEvent event) {
        System.exit(0);
    }

    //-------------------------------------------------------------------------------------
    //Setters
    public void setChessController(ChessController chessController) {
        this.chessController = chessController;
    }

    public void setModel(ChessFacade model) {
        this.model = model;
    }
      
}