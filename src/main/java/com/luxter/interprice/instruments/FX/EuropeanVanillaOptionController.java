package com.luxter.interprice.instruments.FX;

import com.luxter.interprice.instruments.InstrumentController;
import com.luxter.interprice.instruments.Instrument;
import com.luxter.interprice.instruments.PostInitable;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.time.LocalDate;

import static com.luxter.interprice.Utils.getConverter;
import static com.luxter.interprice.Utils.getCurrenciesFromUnderlying;

public class EuropeanVanillaOptionController extends FXController implements InstrumentController, PostInitable {
    public DatePicker settlementDate;
    public DatePicker expiryDate;
    public ComboBox settlementType;
    public TextField notional;
    public ComboBox notionalCurrency;
    public ComboBox optionType;
    public TextField strike;

    @FXML
    private void initialize() {
        settlementDate.setValue( LocalDate.of(2016, 06, 27) );
        settlementDate.setConverter(getConverter());
        expiryDate.setValue( LocalDate.of(2017, 06, 27) );
        expiryDate.setConverter(getConverter());
        settlementType.getSelectionModel().selectFirst();
        optionType.getSelectionModel().selectFirst();
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
        return EuropeanVanillaOption.builder()
                .underlying(underlying.getSelectionModel().getSelectedItem().toString())
                .settlementDate(settlementDate.getValue())
                .expiryDate(expiryDate.getValue())
                .settlementType(settlementType.getValue().toString().toLowerCase())
                .notional(Double.parseDouble(notional.getText()))
                .notionalCurrency(notionalCurrency.getValue().toString())
                .optionType(optionType.getValue().toString().toLowerCase())
                .strike(Double.parseDouble(strike.getText()))
                .build();
    }
}
