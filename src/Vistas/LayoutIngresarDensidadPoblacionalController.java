package Vistas;

import Clases.DensidadPoblacional;
import static java.lang.Integer.max;
import java.util.Arrays;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class LayoutIngresarDensidadPoblacionalController {

    //Recibo los cuerpos de agua que existan en la tabla de la ventana principal
    public static DensidadPoblacional[] cuerposDeAgua = new DensidadPoblacional[0];

    @FXML
    private ChoiceBox input_ids;

    @FXML
    private TextField input_n_habitantes;

    //Verifico que el texto de un campo de texto si pueda ser transforada a entero por Integer.parseInt()
    String ValidarEntero(String texto) {

        String entero = "";
        boolean signo = false;
        for (char c : texto.toCharArray()) {
            if (c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c == '8' || c == '9') {
                signo = true;
                entero += c;
            } else if (!signo && c == '-') {
                entero += c;
                signo = true;
            } else if (!signo && c == '+') {
                signo = true;
            } else {
                break;
            }
        }
        if (entero.equals("-") || entero.equals("") || entero.isEmpty()) {
            entero = "0";
        }
        return entero;
    }

    @FXML
    public void initialize() {

        //Al inicializarse verifico si existen datos en la tabla y de existir muestro el id y el nombre del cuerpo de agua
        this.input_ids.getItems().removeAll(this.input_ids.getItems());
        if (cuerposDeAgua.length > 0) {
            String[] ids = new String[cuerposDeAgua.length];
            for (int i = 0; i < ids.length; i++) {
                ids[i] = cuerposDeAgua[i].getNumeroDeIdentificacion() + " - " + cuerposDeAgua[i].getNombre();
            }
            this.input_ids.getItems().addAll(Arrays.asList(ids));
            this.input_ids.getSelectionModel().select(0);
        }
    }

    @FXML
    void ModificarDensidadPoblacional(MouseEvent event) {

        //Actualizo los datos si se llega a modificar la densidad poblacional
        if (cuerposDeAgua.length > 0) {
            int index = this.input_ids.getSelectionModel().getSelectedIndex();
            int nuevoNumeroDeHabitantes = max(Integer.parseInt(ValidarEntero(this.input_n_habitantes.getText())), 0);
            cuerposDeAgua[index].setCantidadDeHabitantesEnLaZona(nuevoNumeroDeHabitantes);
            LayoutReto5Controller.ActualizarDatos(cuerposDeAgua);
            this.input_n_habitantes.setText(nuevoNumeroDeHabitantes + "");
        }
    }
}
