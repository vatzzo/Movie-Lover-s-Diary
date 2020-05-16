import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.w3c.dom.ls.LSOutput;

import java.sql.SQLOutput;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;


public class ZTI_App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // Controls
            //Nav Buttons
        Button home = new Button();
        home.getStyleClass().add("nav-buttons");
        home.setText("NEW RECORD"); // Add CSS Class Name
        Button registry = new Button();
        registry.getStyleClass().add("nav-buttons"); // Add CSS Class Name
        registry.setText("REGISTRY");

            //Content Text Fields, Data Picker, Choice Box And Their Labels for vBoxSection
        Label titleLabel = new Label("Title");
        titleLabel.getStyleClass().add("labels");
        TextField title = new TextField();
        title.setPromptText("Write down the movie title you have seen lately ...");
        title.setPrefColumnCount(20); // Maximum Number Of Characters
        title.getStyleClass().add("textfields");
        Label dateLabel = new Label("Date");
        dateLabel.getStyleClass().add("labels");
        DatePicker date = new DatePicker();
        date.setPromptText("Type in the date when you finished watching ... ");
        date.getStyleClass().add("date");
        Label descriptionLabel = new Label("Description");
        descriptionLabel.getStyleClass().add("labels");
        TextField description = new TextField();
        description.setPrefColumnCount(100); // Maximum Number Of Characters
        description.setPromptText("Share with your feelings about it ...");
        description.getStyleClass().add("textfields");
        Label gradeLabel = new Label("Grade");
        gradeLabel.getStyleClass().add("labels");
        ChoiceBox grade = new ChoiceBox();
        grade.getStyleClass().add("textfields");
        grade.setValue("5 - Average");

            // Setting Choice Box Options
        grade.setItems(FXCollections.observableArrayList(
                "1 - Poor", "2 - Very Weak", "3 - Weak", "4 - Below Average", "5 - Average", "6 - Above Average", "7 - Good", "8 - Very Good", "9 - Excellent", "10 - Outstanding"
        ));

        Button submit = new Button();
        submit.getStyleClass().add("submit");
        submit.setText("SUBMIT");

        // Controlls for vBoxregsitry
        // List Of Movies
        ListView<String> movieListV = new ListView<String>(); // Create List Of Films
        movieListV.getStyleClass().add("movie-list"); // Add Some Styles

        // Remove Button
        Button remove = new Button("REMOVE");
        remove.getStyleClass().add("submit");

        // Groups / Layout Elements --> HOME
            // Root Element
        BorderPane bBox = new BorderPane(); // New Border Pane
            // Children - HBox
        HBox hBoxTop = new HBox(60); // Create Horizontal Box
        hBoxTop.setAlignment(Pos.CENTER); // Center Children Nodes
        hBoxTop.getStyleClass().add("nav-bar"); // Add CSS Class Name
            // Appending Children - HBox
        hBoxTop.getChildren().add(home);
        hBoxTop.getChildren().add(registry);
            // Children - VBox
        VBox vBoxSection = new VBox(90); // Create Vertical Box
        vBoxSection.getStyleClass().add("section-container");// Add CSS Class Name
        vBoxSection.setAlignment(Pos.CENTER);
            // Children - VBox - vBoxSection
        VBox vBoxTitle = new VBox();
        vBoxTitle.setAlignment(Pos.CENTER);
        VBox vBoxDate = new VBox();
        vBoxDate.setAlignment(Pos.CENTER);
        VBox vBoxDescription = new VBox();
        vBoxDescription.setAlignment(Pos.CENTER);
        VBox vBoxGrade = new VBox();
        vBoxGrade.setAlignment(Pos.CENTER);

            // Appending Children - VBox - vBoxSection
        vBoxSection.getChildren().add(vBoxTitle);
        vBoxSection.getChildren().add(vBoxDate);
        vBoxSection.getChildren().add(vBoxDescription);
        vBoxSection.getChildren().add(vBoxGrade);
        vBoxSection.getChildren().add(submit);

            // Appending Children - VBox - vBoxTitle
        vBoxTitle.getChildren().add(titleLabel);
        vBoxTitle.getChildren().add(title);
            // Appending Children - VBox - vBoxDate
        vBoxDate.getChildren().add(dateLabel);
        vBoxDate.getChildren().add(date);
            // Appending Children - VBox - vBoxDescription
        vBoxDescription.getChildren().add(descriptionLabel);
        vBoxDescription.getChildren().add(description);
            // Appending Children - VBox - vBoxGrade
        vBoxGrade.getChildren().add(gradeLabel);
        vBoxGrade.getChildren().add(grade);

        // VBox --> vBoxRegistry
        VBox vBoxRegistry = new VBox(50);
        vBoxRegistry.getStyleClass().add("section-container");
        vBoxRegistry.setAlignment(Pos.CENTER);

        // List Of Elements For vBoxRegistry
        vBoxRegistry.getChildren().add(movieListV);
        vBoxRegistry.getChildren().add(remove);

            // Appending Boxes to Border Pane
        bBox.setTop(hBoxTop); // Set horizontal box as a navigation bar
        bBox.setCenter(vBoxSection); // Set a vertical box a main section

        // Groups / Layout Elements --> 2ND PAGE

        // Create List Items
        ObservableList<String> items = FXCollections.observableArrayList();

        // Upload MovieListS
        String[][] movieListS = FileHandler.readFromFile();
        String[] listItems = new String[movieListS.length];
        for(int i=0; i < movieListS.length; i++) {
            listItems[i] = "title: " + movieListS[i][0] + ", date: " + movieListS[i][1] + ", description: " + movieListS[i][2] + ", grade: " + movieListS[i][3];
            items.add(listItems[i]);
        }
        // Set List To ListView Controll
        movieListV.setItems(items);

        // Scene
        Scene newRecord = new Scene(bBox); // Create New Scene, Add bBox as a root element
            // Add Stylesheet To Main Page
        newRecord.getStylesheets().add(getClass().getResource("styles_home.css").toExternalForm());
        // Add Stylesheet To Main Page

        // Buttons Events
        home.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                bBox.setCenter(vBoxSection); // Switch Center To vBoxSection
            }
        });

        registry.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                bBox.setCenter(vBoxRegistry); //Switch Center To vVoxRegistry
            }
        });

        remove.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // Remove Selected Item
                movieListV.getItems().remove(movieListV.getSelectionModel().getSelectedItem());
            }
        });

        String[] outputs = new String[4];

        submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // Simple Validation
                if (title.getText().isEmpty()) {
                    title.setPromptText("The title field cannot be empty ... ");
                    title.getStyleClass().add("errors");
                }else {
                    title.setPromptText("Write down the movie title you have seen lately ...");
                    title.getStyleClass().remove("errors");
                }

                if(description.getText().isEmpty()){
                    description.setPromptText("The description field cannot be empty ...");
                    description.getStyleClass().add("errors");
                }else {
                    description.setPromptText("Share with your feelings about it ...");
                    description.getStyleClass().remove("errors");
                }

                if(date.getValue() == null ) {
                    date.setPromptText("Choose date properly ... ");
                    date.getStyleClass().add("errors");
                }else {
                    date.setPromptText("Type in the date when you finished watching ... ");
                    date.getStyleClass().remove("errors");
                }

                // If Everything Is All Right --> Get Information And Clear Fields
                if(title.getText().isEmpty() == false && description.getText().isEmpty() == false && date.getValue() != null) {
                    String titleValue = title.getText();
                    String descriptionValue = description.getText();
                    LocalDate dateValueDF = date.getValue();
                    String dateValueS = dateValueDF.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT));
                    String gradeValueS = grade.getValue().toString();
                    // Outputs
                    outputs[0] = titleValue;
                    outputs[1] = descriptionValue;
                    outputs[2] = dateValueS;
                    outputs[3] = gradeValueS;
                    String addItem = "title: " + titleValue + ", date: " + dateValueS + ", description: " + descriptionValue + ", grade: " + gradeValueS;
                    items.add(addItem);
                    FileHandler.saveToFile(outputs);
                    title.setText("");
                    description.setText("");
                    date.setValue(null);
                    grade.setValue("5 - Average");
                }
            }
        });

        // Stage
        primaryStage.setScene(newRecord); // Add Scene To Stage
        primaryStage.setTitle("Movie Lover's Diary"); // Title Of The Stage
        primaryStage.setWidth(1080); // Dimensions - Width
        primaryStage.setHeight(960); // Dimensions - Height
        primaryStage.show(); // Display The Scene

    }
}
