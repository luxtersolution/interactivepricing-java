package com.luxter.interprice.instruments.FX;

import com.luxter.interprice.instruments.InstrumentController;
import com.luxter.interprice.instruments.Instrument;
import com.luxter.interprice.instruments.PostInitable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.time.LocalDate;

import static com.luxter.interprice.Utils.getConverter;
import static com.luxter.interprice.Utils.getCurrenciesFromUnderlying;

public class DoubleNoTouchController extends FXController implements InstrumentController, PostInitable {
    public DatePicker settlementDate;
    public DatePicker expiryDate;
    public TextField notional;
    public ComboBox notionalCurrency;
    public TextField barrierLow;
    public TextField barrierHigh;
    public ComboBox settlementType;

    @FXML
    private void initialize() {
        settlementDate.setValue( LocalDate.of(2016, 06, 27) );
        settlementDate.setConverter(getConverter());
        expiryDate.setValue( LocalDate.of(2017, 06, 27) );
        expiryDate.setConverter(getConverter());
    }

    @Override
    public void postinit() {
        notionalCurrency.setItems(getCurrenciesFromUnderlying(underlying.getValue().toString()));
        notionalCurrency.getSelectionModel().selectFirst();
        underlying.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            notionalCurrency.setItems(getCurrenciesFromUnderlying(newValue.toString()));
            notionalCurrency.getSelectionModel().selectFirst();
        });
    }

    @Override
    public Instrument getInstrument() {
        return DoubleNoTouch.builder()
                .underlying(underlying.getSelectionModel().getSelectedItem().toString())
                .settlementDate(settlementDate.getValue())
                .expiryDate(expiryDate.getValue())
                .notional(Double.parseDouble(notional.getText()))
                .notionalCurrency(notionalCurrency.getValue().toString())
                .barrierLow(Double.parseDouble(barrierLow.getText()))
                .barrierHigh(Double.parseDouble(barrierHigh.getText()))
                .build();
    }
}
