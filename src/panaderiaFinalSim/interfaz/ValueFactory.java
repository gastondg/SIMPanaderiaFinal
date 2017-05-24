package panaderiaFinalSim.interfaz;

import javafx.beans.value.ObservableValue;
import javafx.beans.value.ObservableValueBase;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

import java.util.function.Function;

/**
 * Created by gaston on 24/05/17.
 */
public class ValueFactory<C, T> implements Callback<TableColumn.CellDataFeatures<C, T>, ObservableValue<T>> {
    private Function<C, T> consumer;

    public ValueFactory(Function<C, T> consumer) {
        this.consumer = consumer;
    }

    @Override
    public ObservableValue<T> call(TableColumn.CellDataFeatures<C, T> param) {
        T value = consumer.apply(param.getValue());
        return new ObservableValueBase<T>() {
            @Override
            public T getValue() {
                return value;
            }
        };
    }
}


