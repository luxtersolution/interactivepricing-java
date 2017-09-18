package com.luxter.interprice.instruments.FX;

import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

public class FXController {
    public ComboBox underlying;

    public void setUnderlyings(ObservableList<String> underlyings) {
        this.underlying.setItems(underlyings);
        this.underlying.getSelectionModel().selectFirst();
    }
}
