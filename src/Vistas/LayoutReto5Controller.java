package Vistas;

import Clases.DensidadPoblacional;
import java.io.IOException;
import java.util.Arrays;
import java.util.Locale;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class LayoutReto5Controller {

    Stage stage;

    @FXML
    private TableView<DensidadPoblacional> tbl_cuerpo_agua;
    private static final ObservableList<DensidadPoblacional> CUERPOS_AGUA = FXCollections.observableArrayList();

    @FXML
    private TextArea txt_procesar;

    public static void ActualizarDatos(DensidadPoblacional[] cuerposDeAgua) {

        //Actualizó los datos de la tabla
        CUERPOS_AGUA.clear();
        CUERPOS_AGUA.addAll(Arrays.asList(cuerposDeAgua));
    }

    @FXML
    public void initialize() {

        //Generar las columnas de la tabla con los atributos de la clase DensidadPoblacional
        TableColumn id_col = new TableColumn("Id");
        id_col.setCellValueFactory(new PropertyValueFactory<>("numeroDeIdentificacion"));
        TableColumn nombre_col = new TableColumn("Nombre");
        nombre_col.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        TableColumn municipio_col = new TableColumn("Municipio");
        municipio_col.setCellValueFactory(new PropertyValueFactory<>("municipioAlQuePertenece"));
        TableColumn tipo_cuerpo_col = new TableColumn("Cuerpo agua");
        tipo_cuerpo_col.setCellValueFactory(new PropertyValueFactory<>("tipoCuerpoDeAgua"));
        TableColumn tipo_agua_col = new TableColumn("Tipo agua");
        tipo_agua_col.setCellValueFactory(new PropertyValueFactory<>("tipoAgua"));
        TableColumn IRCA_col = new TableColumn("IRCA");
        IRCA_col.setCellValueFactory(new PropertyValueFactory<>("clasificacionIRCA"));
        TableColumn n_habitantes_col = new TableColumn("Habitantes");
        n_habitantes_col.setCellValueFactory(new PropertyValueFactory<>("cantidadDeHabitantesEnLaZona"));
        this.tbl_cuerpo_agua.setItems(CUERPOS_AGUA);
        this.tbl_cuerpo_agua.getColumns().addAll(id_col, nombre_col, municipio_col, tipo_agua_col, tipo_cuerpo_col, IRCA_col, n_habitantes_col);

        //Genero un Stage para asegurar de que no existan más de dos ventanas a la vez
        stage = new Stage();
    }

    @FXML
    void IngresarCuerpoDeAguaLayout(MouseEvent event) throws IOException {

        //Cierro la ventana si se estuviera mostrando
        stage.close();

        //Paso los cuerpos de agua que existan en la tabla a la ventana de ingresar nuevos cuerpos de agua
        DensidadPoblacional[] cuerposDeAgua = new DensidadPoblacional[CUERPOS_AGUA.size()];
        for (int i = 0; i < cuerposDeAgua.length; i++) {
            cuerposDeAgua[i] = CUERPOS_AGUA.get(i);
        }
        LayoutIngresarCuerpoDeAguaController.cuerposDeAgua = cuerposDeAgua;

        //Inició una nueva ventana
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("./layout_ingresar_CuerpoDeAgua.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        stage = new Stage();
        stage.setTitle("RojasRobinson-#50 Ingresar datos de CuerpoDeAgua");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void IngresarDensidadPoblacionalLayout(MouseEvent event) throws IOException {

        //Cierro la ventana si se estuviera mostrando
        stage.close();

        //Paso los cuerpos de agua que existan en la tabla a la ventana de modificar la densidad oblacional
        DensidadPoblacional[] cuerposDeAgua = new DensidadPoblacional[CUERPOS_AGUA.size()];
        for (int i = 0; i < cuerposDeAgua.length; i++) {
            cuerposDeAgua[i] = CUERPOS_AGUA.get(i);
        }
        LayoutIngresarDensidadPoblacionalController.cuerposDeAgua = cuerposDeAgua;

        //Inició una nueva ventana
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("./layout_ingresar_DensidadPoblacional.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        stage = new Stage();
        stage.setTitle("RojasRobinson-#50 Ingresar datos de DensidadPoblacional");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void ProcesarDatos(MouseEvent event) {

        //Genero la salida luego de procesar los datos de la tabla
        if (CUERPOS_AGUA.size() > 0) {
            String datos = "";
            int cantidadDeCuerposDeAguaConNivelDeRiesgoMedioOInferior = 0;
            String cuerposDeAguaConNivelDeRiesgoMedio = "";
            float clasificasionPromedioIRCADeTodosLosCuerpoDeAgua = 0f;
            for (DensidadPoblacional c : CUERPOS_AGUA) {
                datos += String.format(Locale.US, "%.2f%n", c.GetClasificacionIRCA());
                if (c.GetClasificacionIRCA() <= 35f) {
                    cantidadDeCuerposDeAguaConNivelDeRiesgoMedioOInferior++;
                }
                if (c.nivel().equals("MEDIO")) {
                    if (!cuerposDeAguaConNivelDeRiesgoMedio.equals("")) {
                        cuerposDeAguaConNivelDeRiesgoMedio += " ";
                    }
                    cuerposDeAguaConNivelDeRiesgoMedio += c.getNombre();
                }
                clasificasionPromedioIRCADeTodosLosCuerpoDeAgua += c.GetClasificacionIRCA();
            }
            datos += String.format(Locale.US, "%.2f%n", (float) cantidadDeCuerposDeAguaConNivelDeRiesgoMedioOInferior);
            if (cuerposDeAguaConNivelDeRiesgoMedio.equals("")) {
                datos += "NA\n";
            } else {
                datos += cuerposDeAguaConNivelDeRiesgoMedio + "\n";
            }
            clasificasionPromedioIRCADeTodosLosCuerpoDeAgua /= CUERPOS_AGUA.size();
            datos += String.format(Locale.US, "%.2f", clasificasionPromedioIRCADeTodosLosCuerpoDeAgua);
            this.txt_procesar.setText(datos);
        } else {
            //Si no hay datos en la tabla solo borra el texto
            this.txt_procesar.setText("");
        }
    }
}
