package sample;

import java.io.FileNotFoundException;

public class Resistor extends Component {

    private double resistance = 1;

    public Resistor() throws FileNotFoundException {
        super("Resistor", "res\\resistor.png");
    }

    public double getResistance() {
        return resistance;
    }

    public void setResistance(double resistance) {
        this.resistance = resistance;
    }
}
