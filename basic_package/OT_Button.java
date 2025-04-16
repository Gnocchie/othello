package de.lmu.bio.ifi.basicpackage;

import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

public class OT_Button extends Button {
    public int value;
    public int x;
    public int y;

    public OT_Button(int value, int x, int y) {
        this.x = x;
        this.y = y;
        setValue(value);
    }

    public void setValue(int value) {
        this.value = value;
        if (value != 0) {
            this.paint();
        }
    }
    public void paint(){
        Shape s = new Circle(this.getWidth()/2,this.getHeight()/2,30);
        s.setStroke(Color.DARKGREY);
        s.setStrokeWidth(3);
        if (value == 1) {
            s.setFill(Color.BLACK);
        }
        else if (value == 2) {
            s.setFill(Color.BEIGE);
        }
        this.setGraphic(s);
    }
}
