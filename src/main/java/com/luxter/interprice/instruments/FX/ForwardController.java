package com.luxter.interprice.instruments.FX;

import com.luxter.interprice.PricerController;
import com.luxter.interprice.instruments.InstrumentController;
import com.luxter.interprice.instruments.Instrument;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.time.LocalDate;
import java.util.HashMap;

import static com.luxter.interprice.Utils.getConverter;

public class ForwardController extends FXController implements InstrumentController {
    public TextField termNotional;
    public DatePicker settlementDate;
    public TextField baseNotional;

    @FXML
    private void initialize() {
        this.settlementDate.setValue( LocalDate.now() );
        this.settlementDate.setConverter(getConverter());
    }

    @Override
    public Instrument getInstrument() {
        return Forward.builder()
                .baseNotional(Double.parseDouble(baseNotional.getText()))
                .termNotional(Double.parseDouble(termNotional.getText()))
                .settlementDate(settlementDate.getValue())
                .underlying(underlying.getSelectionModel().getSelectedItem().toString())
                .build();
    }
}
