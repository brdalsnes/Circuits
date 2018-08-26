package sample;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class Controller {

    private List<Component> activeComponents = new ArrayList<>();
    private List<StackPane> paneList = new ArrayList<>();
    private Component selectedComponent;

    @FXML
    private BorderPane borderPane;
    @FXML
    private ImageView componentImage;
    @FXML
    private StackPane pane;
    @FXML
    private StackPane centerPane;
    @FXML
    private ListView<Component> componentList;

    @FXML
    public void initialize() throws FileNotFoundException {

        centerPane.setStyle("-fx-border-color: black");
        selectedComponent = null;

        //Adds all available components in listview
        Component volt = new VoltageSource();
        Component current = new CurrentSource();
        Component resistor = new Resistor();
        componentList.getItems().addAll(volt,current,resistor);

        componentList.setCellFactory(new Callback<ListView<Component>, ListCell<Component>>() {
            @Override
            public ListCell<Component> call(ListView<Component> param) {
                ListCell<Component> cell = new ListCell<Component>(){
                    @Override
                    protected void updateItem(Component item, boolean empty) {
                        super.updateItem(item, empty);
                        if(empty){
                            setText(null);
                        }else{
                            setText(item.getName());
                        }
                    }
                };
                return cell;
            }
        });
    }

    @FXML
    public void handleListClick(MouseEvent event){
        //Adds new component to group of components in center
        Component component = new Component(componentList.getSelectionModel().getSelectedItem());
        activeComponents.add(component);

        //Creates imageview and pane for new component
        ImageView componentImage = new ImageView(component.getImage());
        //Pane is added for easier drag and drop
        StackPane pane = new StackPane();
        pane.getChildren().add(componentImage);
        pane.setMaxWidth(componentImage.getFitWidth());
        pane.setMaxHeight(componentImage.getFitHeight());
        paneList.add(pane);
        centerPane.getChildren().add(pane);

        //Handle pane drag
        pane.setOnDragDetected(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent paneEvent) {
                Dragboard db = pane.startDragAndDrop(TransferMode.ANY);
                ClipboardContent content = new ClipboardContent();
                content.putImage(componentImage.getImage());
                db.setContent(content);
                paneEvent.consume();
            }
        });

        //Handle click
        pane.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(event.getButton().equals(MouseButton.PRIMARY)){
                    if(event.getClickCount() == 2){
                        System.out.println("Double click");
                        pane.setRotate(pane.getRotate() + 90);
                    }
                    else if(event.getClickCount() == 1){
                        System.out.println("Single click");
                        if(selectedComponent == null){
                            selectedComponent = component;
                            System.out.println(selectedComponent.toString());
                        }else{
                            selectedComponent.addConnection(component);
                            selectedComponent = null;
                        }
                    }
                }
                else{
                    System.out.println("Right click");
                }
            }
        });
        event.consume();
    }

    @FXML
    public void handleDragOver(DragEvent event){
        event.acceptTransferModes(TransferMode.ANY);
        event.consume();
    }

    @FXML
    public void handleDragDropped(DragEvent event){
        StackPane currentPane = (StackPane) event.getGestureSource();
        currentPane.setTranslateX(event.getX() - currentPane.getLayoutX() - (currentPane.getWidth()/2));
        currentPane.setTranslateY(event.getY() - currentPane.getLayoutY() - (currentPane.getHeight()/2));
    }

    @FXML
    public void handleDragDroppedList(DragEvent event){
        StackPane currentPane = (StackPane) event.getGestureSource();
        int index = paneList.indexOf(currentPane);
        paneList.remove(currentPane);
        centerPane.getChildren().remove(currentPane);
        activeComponents.remove(index);
        System.out.println(activeComponents.size());
    }


}
