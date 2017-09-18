package com.luxter.interprice.instruments.FX;
import com.luxter.interprice.instruments.InstrumentController;
import com.luxter.interprice.instruments.Instrument;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.time.LocalDate;

import static com.luxter.interprice.Utils.getConverter;

public class SwapController extends FXController implements InstrumentController {
    public DatePicker initialSettlementDate;
    public DatePicker finalSettlementDate;
    public TextField baseNotional;
    public TextField termNotional;
    public TextField spread;

    @FXML
    private void initialize() {
        this.initialSettlementDate.setValue( LocalDate.now() );
        this.initialSettlementDate.setConverter(getConverter());
        this.finalSettlementDate.setValue( LocalDate.now() );
        this.finalSettlementDate.setConverter(getConverter());
    }

    @Override
    public Instrument getInstrument() {
        return Swap.builder()
                .baseNotional(Double.parseDouble(baseNotional.getText()))
                .termNotional(Double.parseDouble(termNotional.getText()))
                .initialSettlementDate(initialSettlementDate.getValue())
                .finalSettlementDate(finalSettlementDate.getValue())
                .underlying(underlying.getSelectionModel().getSelectedItem().toString())
                .spread(Double.parseDouble(spread.getText()))
                .build();
    }
}
