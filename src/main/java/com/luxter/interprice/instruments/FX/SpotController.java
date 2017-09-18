package com.luxter.interprice.instruments.FX;

import com.luxter.interprice.instruments.InstrumentController;
import com.luxter.interprice.instruments.Instrument;
import interactivepricing.Service;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.luxter.interprice.Utils.getConverter;
import static com.luxter.interprice.Utils.makeDate;

public class SpotController extends FXController implements InstrumentController {
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
        return Spot.builder()
                .underlying(underlying.getSelectionModel().getSelectedItem().toString())
                .settlementDate(settlementDate.getValue())
                .baseNotional(Double.parseDouble(baseNotional.getText()))
                .termNotional(Double.parseDouble(termNotional.getText()))
                .build();
    }
}
