package sample;

import javafx.scene.image.Image;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class Component {
    private int x;
    private int y;
    private String name;
    private List<Component> connections = new ArrayList<>();
    private Image image;

    public Component(String name, String imagePath) throws FileNotFoundException {
        this.name = name;
        this.image = new Image(new FileInputStream(imagePath)); //Sets image from path to imageview
    }

    //Copy constructor
    public Component(Component c){
        this.name = c.name;
        this.image = c.image;
    }

    public void addConnection(Component component){
        connections.add(component);
    }

    public void clearConnections(){
        connections.clear();
    }

    public Image getImage() {
        return image;
    }

    public String getName() {
        return name;
    }
}
