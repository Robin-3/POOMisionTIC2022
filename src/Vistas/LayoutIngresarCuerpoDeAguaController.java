package Vistas;

import Clases.DensidadPoblacional;
import static java.lang.Integer.compare;
import java.util.Arrays;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.MouseEvent;

public class LayoutIngresarCuerpoDeAguaController {

    //Llevar un conteo de los nuevos datos a ingresar
    private int cantidad = 0;

    //Recibo los cuerpos de agua que existan en la tabla de la ventana principal
    public static DensidadPoblacional[] cuerposDeAgua = new DensidadPoblacional[0];

    @FXML
    private TextField input_id_i;

    @FXML
    private ChoiceBox input_id_m;

    @FXML
    private TextField input_nombre_i;

    @FXML
    private TextField input_nombre_m;

    @FXML
    private TextField input_municipio_i;

    @FXML
    private TextField input_municipio_m;

    @FXML
    private ChoiceBox input_tipo_agua_i;

    @FXML
    private ChoiceBox input_tipo_agua_m;

    @FXML
    private TextField input_tipo_cuerpo_i;

    @FXML
    private TextField input_tipo_cuerpo_m;

    @FXML
    private TextField input_IRCA_i;

    @FXML
    private TextField input_IRCA_m;

    @FXML
    private Label label_n_datos;

    @FXML
    private Label label_mostrar_datos;

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

    //Verifico que el texto de un campo de texto si pueda ser transforada a flotante por float.parseFloat()
    String ValidarFlotante(String texto) {

        texto = texto.replace(",", ".");
        String flotante = "";
        boolean signo = false;
        boolean punto = false;
        for (char c : texto.toCharArray()) {
            if (c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c == '8' || c == '9') {
                signo = true;
                flotante += c;
            } else if (!signo && c == '-') {
                flotante += c;
                signo = true;
            } else if (!signo && c == '+') {
                signo = true;
            } else if (!punto && c == '.') {
                if (flotante.equals("") || flotante.isEmpty()) {
                    flotante += "0";
                }
                flotante += c;
                punto = true;
            } else {
                break;
            }
        }
        if (flotante.equals("-") || flotante.equals("") || flotante.isEmpty()) {
            flotante = "0";
        }
        return flotante;
    }

    //Vefifico si existen coincidencias en un texto
    boolean ValidarRegex(String texto, String regex) {
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(texto);
        return m.find();
    }

    //Tomo los datos que se han validado para sumarlos a la tabla de la ventana principal
    void IngresarNuevosDatos(String[] datos) {
        DensidadPoblacional[] nuevosCuerposDeAgua = new DensidadPoblacional[Integer.parseInt(datos[0])];
        for (int i = 0; i < nuevosCuerposDeAgua.length; i++) {
            String[] subdatos = datos[i + 1].split(" ");
            nuevosCuerposDeAgua[i] = new DensidadPoblacional(Integer.parseInt(subdatos[0]), subdatos[1], subdatos[2], subdatos[4], subdatos[3], Float.parseFloat(subdatos[5]));
        }
        DensidadPoblacional[] totalCuerposDeAgua = new DensidadPoblacional[cuerposDeAgua.length + nuevosCuerposDeAgua.length];
        System.arraycopy(cuerposDeAgua, 0, totalCuerposDeAgua, 0, cuerposDeAgua.length);
        System.arraycopy(nuevosCuerposDeAgua, 0, totalCuerposDeAgua, cuerposDeAgua.length, nuevosCuerposDeAgua.length);
        cuerposDeAgua = totalCuerposDeAgua;
        Arrays.sort(cuerposDeAgua, (a, b) -> compare(a.getNumeroDeIdentificacion(), b.getNumeroDeIdentificacion()));
        LayoutReto5Controller.ActualizarDatos(cuerposDeAgua);
    }

    //Muestra una alerta personalizada y devuelve si se acepto el mensaje
    boolean GenerarAlerta(String titulo, String mensaje, String mensaje_si, String mensaje_no) {
        ButtonType btn_si = new ButtonType(mensaje_si, ButtonBar.ButtonData.OK_DONE);
        ButtonType btn_no = new ButtonType(mensaje_no, ButtonBar.ButtonData.CANCEL_CLOSE);
        Alert alert = new Alert(AlertType.WARNING, mensaje, btn_si, btn_no);
        alert.setTitle(titulo);
        Optional<ButtonType> result = alert.showAndWait();
        return result.orElse(btn_no) == btn_si;
    }

    //Muestra un mensaje de error
    void GenerarError(String titulo, String mensaje) {
        Alert alert = new Alert(AlertType.ERROR, mensaje);
        alert.setTitle(titulo);
        alert.showAndWait();
    }

    //Vefifico que todos los nuevos datos respeten las validaciones de entrada para luego agregaros a la tabla
    void ValidarCuerpoDeAgua() {
        boolean todosLosCampos = true;
        //Campo id
        if (ValidarRegex(this.input_id_i.getText(), "\\D")) {
            String nuevoEntero = ValidarEntero(this.input_id_i.getText());
            if (!this.input_id_i.getText().equals(nuevoEntero)) {
                todosLosCampos = GenerarAlerta("Campo 1: \"ID\"", "El Id solo debe de tener números\n¿Desea cambiarlo por \"" + nuevoEntero + "\"?", "Sí, cambialo", "No, voy a modificarlo");
                if (todosLosCampos) {
                    this.input_id_i.setText(nuevoEntero);
                }
            } else {
                this.input_id_i.setText(nuevoEntero);
            }
        } else {
            this.input_id_i.setText(ValidarEntero(this.input_id_i.getText()));
        }
        for (int i = 0; i < cuerposDeAgua.length; i++) {
            if (cuerposDeAgua[i].getNumeroDeIdentificacion() == Integer.parseInt(this.input_id_i.getText())) {
                int nuevoId = cuerposDeAgua[cuerposDeAgua.length - 1].getNumeroDeIdentificacion() + 1;
                todosLosCampos = GenerarAlerta("Campo 1: \"ID\"", "El Id debe ser único\n¿Desea cambiarlo por \"" + nuevoId + "\"?", "Sí, cambialo", "No, voy a modificarlo");
                if (todosLosCampos) {
                    this.input_id_i.setText(nuevoId + "");
                }
                break;
            }
        }
        String[] datos = this.label_mostrar_datos.getText().split("\n");
        boolean existe = false;
        int maxId = -1;
        if (datos.length > 1) {
            maxId = Integer.parseInt(datos[1].split(" ")[0]);
            for (int i = 1; i < datos.length; i++) {
                int evaluarId = Integer.parseInt(datos[i].split(" ")[0]);
                if (evaluarId == Integer.parseInt(this.input_id_i.getText())) {
                    existe = true;
                }
                if (maxId < evaluarId) {
                    maxId = evaluarId;
                }
            }
        }
        if (existe) {
            int nuevoId = maxId + 1;
            if (cuerposDeAgua.length > 0) {
                nuevoId += cuerposDeAgua[cuerposDeAgua.length - 1].getNumeroDeIdentificacion() + 1;
            }
            todosLosCampos = GenerarAlerta("Campo 1: \"ID\"", "El Id debe ser único\n¿Desea cambiarlo por \"" + nuevoId + "\"?", "Sí, cambialo", "No, voy a modificarlo");
            if (todosLosCampos) {
                this.input_id_i.setText(nuevoId + "");
            }
        }
        if (this.input_id_i.getText().equals("") || this.input_id_i.getText().isEmpty()) {
            int nuevoId;
            if (cuerposDeAgua.length > 0) {
                nuevoId = cuerposDeAgua[cuerposDeAgua.length - 1].getNumeroDeIdentificacion() + 1;
            } else {
                nuevoId = 0;
            }
            todosLosCampos = GenerarAlerta("Campo 1: \"ID\"", "El Id no debe estar en blanco\n¿Desea cambiarlo por \"" + nuevoId + "\"?", "Sí, cambialo", "No, voy a modificarlo");
            if (todosLosCampos) {
                this.input_id_i.setText(nuevoId + "");
            }
        }
        //Campo nombre
        if (todosLosCampos) {
            if (ValidarRegex(this.input_nombre_i.getText(), "\\s")) {
                todosLosCampos = GenerarAlerta("Campo 2: \"Nombre\"", "El nombre tiene espacios en blanco\n¿Desea removerlos?", "Sí, quitalos", "No, voy a modificarlo");
                if (todosLosCampos) {
                    this.input_nombre_i.setText(this.input_nombre_i.getText().replace(" ", ""));
                }
            }
            if (this.input_nombre_i.getText().equals("") || this.input_nombre_i.getText().isEmpty()) {
                GenerarError("Campo 2: \"Nombre\"", "El nombre no debe estar en blanco");
                todosLosCampos = false;
            }
        }
        //Campo municipio
        if (todosLosCampos) {
            if (ValidarRegex(this.input_municipio_i.getText(), "\\s")) {
                todosLosCampos = GenerarAlerta("Campo 3: \"Municipio\"", "El municipio tiene espacios en blanco\n¿Desea removerlos?", "Sí, quitalos", "No, voy a modificarlo");
                if (todosLosCampos) {
                    this.input_municipio_i.setText(this.input_municipio_i.getText().replace(" ", ""));
                }
            }
            if (this.input_municipio_i.getText().equals("") || this.input_municipio_i.getText().isEmpty()) {
                GenerarError("Campo 3: \"Municipio\"", "El nombre no debe estar en blanco");
                todosLosCampos = false;
            }
        }
        //Campo tipo de cuerpo
        if (todosLosCampos) {
            if (ValidarRegex(this.input_tipo_cuerpo_i.getText(), "\\s")) {
                todosLosCampos = GenerarAlerta("Campo 5: \"Tipo de cuerpo\"", "El tipo de cuerpo tiene espacios en blanco\n¿Desea removerlos?", "Sí, quitalos", "No, voy a modificarlo");
                if (todosLosCampos) {
                    this.input_tipo_cuerpo_i.setText(this.input_tipo_cuerpo_i.getText().replace(" ", ""));
                }
            }
            if (this.input_tipo_cuerpo_i.getText().equals("") || this.input_tipo_cuerpo_i.getText().isEmpty()) {
                GenerarError("Campo 5: \"Tipo de cuerpo\"", "El tipo de cuerpo no debe estar en blanco");
                todosLosCampos = false;
            }
        }
        //Campo IRCA
        if (todosLosCampos) {
            if (ValidarRegex(this.input_IRCA_i.getText(), "\\D")) {
                String nuevoFlotante = ValidarFlotante(this.input_IRCA_i.getText());
                if (!this.input_IRCA_i.getText().equals(nuevoFlotante)) {
                    todosLosCampos = GenerarAlerta("Campo 6: \"IRCA\"", "El IRCA solo debe de tener números\n¿Desea cambiarlo por \"" + nuevoFlotante + "\"?", "Sí, cambialo", "No, voy a modificarlo");
                    if (todosLosCampos) {
                        this.input_IRCA_i.setText(nuevoFlotante);
                    }
                } else {
                    this.input_IRCA_i.setText(nuevoFlotante);
                }
            } else {
                this.input_IRCA_i.setText(ValidarFlotante(this.input_IRCA_i.getText()));
            }
            if (Float.parseFloat(this.input_IRCA_i.getText()) < 0 || Float.parseFloat(this.input_IRCA_i.getText()) > 100) {
                GenerarError("Campo 6: \"IRCA\"", "El IRCA debe estar entre 0% y 100% (Ver la tabla en la pestaña \"Nivel de riesgo\")");
                todosLosCampos = false;
            }
            if (this.input_IRCA_i.getText().equals("") || this.input_IRCA_i.getText().isEmpty()) {
                GenerarError("Campo 6: \"IRCA\"", "El IRCA no debe estar en blanco");
                todosLosCampos = false;
            }
        }
        //Añadir al label
        if (todosLosCampos) {
            this.cantidad++;
            this.label_n_datos.setText("Cantidad de cuerpos a analizar: " + this.cantidad);
            String nuevoCuerpo = this.label_mostrar_datos.getText() + "\n"
                    + this.input_id_i.getText() + " "
                    + this.input_nombre_i.getText() + " "
                    + this.input_municipio_i.getText() + " "
                    + this.input_tipo_agua_i.getSelectionModel().getSelectedItem() + " "
                    + this.input_tipo_cuerpo_i.getText() + " "
                    + this.input_IRCA_i.getText();
            this.label_mostrar_datos.setText(nuevoCuerpo);
            this.input_id_i.setText("");
            this.input_nombre_i.setText("");
            this.input_municipio_i.setText("");
            this.input_tipo_cuerpo_i.setText("");
            this.input_IRCA_i.setText("");
        }
    }

    //Limpia los datos existentes
    void BorrarTodo() {
        this.input_id_i.setText("");
        this.input_nombre_i.setText("");
        this.input_municipio_i.setText("");
        this.input_tipo_cuerpo_i.setText("");
        this.input_IRCA_i.setText("");
        this.label_n_datos.setText("Cantidad de cuerpos de agua a analizar: 0");
        this.label_mostrar_datos.setText("");
        this.cantidad = 0;
    }

    //En la pestala modificar/eliminar actualizar los Ids del choicebox
    void ActualizarIDS() {
        this.input_id_m.getItems().removeAll(this.input_id_m.getItems());
        String[] ids = new String[cuerposDeAgua.length];
        for (int i = 0; i < ids.length; i++) {
            ids[i] = cuerposDeAgua[i].getNumeroDeIdentificacion() + "";
        }
        this.input_id_m.getItems().addAll(Arrays.asList(ids));
        this.input_id_m.getSelectionModel().select(0);
    }

    //Obtiene los datos del cuerpo de agua según el choicebox de la pestaña modificar/eliminar selecionado
    void ObtenerCuerpoDeAgua(int index) {
        this.input_nombre_m.setText(cuerposDeAgua[index].getNombre());
        this.input_municipio_m.setText(cuerposDeAgua[index].getMunicipioAlQuePertenece());
        if (cuerposDeAgua[index].getTipoAgua().equals("Dulce")) {
            this.input_tipo_agua_m.getSelectionModel().select(0);
        } else {
            this.input_tipo_agua_m.getSelectionModel().select(1);
        }
        this.input_tipo_cuerpo_m.setText(cuerposDeAgua[index].getTipoCuerpoDeAgua());
        this.input_IRCA_m.setText(cuerposDeAgua[index].GetClasificacionIRCA() + "");
    }

    //Genero las obciones de los choicebox de la ventana
    @FXML
    public void initialize() {
        this.input_tipo_agua_i.getItems().removeAll(this.input_tipo_agua_i.getItems());
        this.input_tipo_agua_i.getItems().addAll("Dulce", "Salada");
        this.input_tipo_agua_i.getSelectionModel().select("Dulce");
        this.input_tipo_agua_m.getItems().removeAll(this.input_tipo_agua_m.getItems());
        this.input_tipo_agua_m.getItems().addAll("Dulce", "Salada");
        this.input_tipo_agua_m.getSelectionModel().select("Dulce");
        if (cuerposDeAgua.length > 0) {
            ActualizarIDS();
            ObtenerCuerpoDeAgua(0);
        }
    }

    //Tomo los datos de la tabla y los copia en el portapapeles
    @FXML
    void CopiarDatos(MouseEvent event) {
        String datos = cuerposDeAgua.length + "";
        for (DensidadPoblacional c : cuerposDeAgua) {
            datos += "\n"
                    + c.getNumeroDeIdentificacion() + " "
                    + c.getNombre() + " "
                    + c.getMunicipioAlQuePertenece() + " "
                    + c.getTipoAgua() + " "
                    + c.getTipoCuerpoDeAgua() + " "
                    + c.GetClasificacionIRCA();
        }
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent content = new ClipboardContent();
        content.putString(datos);
        clipboard.setContent(content);
    }

    //Toma los datos del portapapeles y los ubica en los inputs correspondientes para validarlos
    @FXML
    void PegarDatos(MouseEvent event) {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        String[] datos = clipboard.getString().trim().split("\n");
        int cantidadDeNuevosElementos = Integer.parseInt(ValidarEntero(datos[0]));
        boolean validarCantidadDeElementos = true;
        if (cantidadDeNuevosElementos + 1 != datos.length) {
            validarCantidadDeElementos = GenerarAlerta("Número de datos erróneo", "No se puede procesar. Se tiene como primer dato \"" + cantidadDeNuevosElementos + "\"", "Corrigelo (" + (datos.length - 1) + ")", "Dejalo (" + cantidadDeNuevosElementos + ")");
            if (validarCantidadDeElementos) {
                cantidadDeNuevosElementos = datos.length - 1;
            }
        }
        if (validarCantidadDeElementos) {
            for (int i = 1; i <= cantidadDeNuevosElementos; i++) {
                String[] subdatos = datos[i].trim().split(" ");
                if (subdatos.length != 6) {
                    GenerarError("Número de datos erróneo", "En la línea " + i + " Se han ingresado " + subdatos.length + " de 6 permitidos");
                } else {
                    this.input_id_i.setText(subdatos[0]);
                    this.input_nombre_i.setText(subdatos[1]);
                    this.input_municipio_i.setText(subdatos[2]);
                    switch (subdatos[3].toLowerCase()) {
                        case "dulce":
                            this.input_tipo_agua_i.getSelectionModel().select(0);
                            break;
                        case "salada":
                            this.input_tipo_agua_i.getSelectionModel().select(1);
                            break;
                        default:
                            boolean nuevoTipoAgua = GenerarAlerta("Tipo de agua", "El tipo de agua de la línea " + i + " es " + subdatos[3] + ". Solo es permitido \"Dulce\" o \"Salada\"", "Colocar \"Dulce\"", "Colocar \"Salada\"");
                            if (nuevoTipoAgua) {
                                this.input_tipo_agua_i.getSelectionModel().select(0);
                            } else {
                                this.input_tipo_agua_i.getSelectionModel().select(1);
                            }
                            break;
                    }
                    this.input_tipo_cuerpo_i.setText(subdatos[4]);
                    this.input_IRCA_i.setText(subdatos[5]);
                    ValidarCuerpoDeAgua();
                }
            }
        }
    }

    @FXML
    void ValidarDatos(MouseEvent event) {
        ValidarCuerpoDeAgua();
    }

    @FXML
    void BorrarTodo(MouseEvent event) {
        BorrarTodo();
    }

    //Verifico si existen datos en el label para incluirlos en la tabla de la ventana principal y actualizar el choicebox de la pestaña modificar/eliminar
    @FXML
    void IngresarDatos(MouseEvent event) {
        String[] datos = this.label_mostrar_datos.getText().split("\n");
        if (datos.length > 0) {
            datos[0] = this.cantidad + "";
            IngresarNuevosDatos(datos);
            BorrarTodo();
            ActualizarIDS();
            ObtenerCuerpoDeAgua(0);
        }
    }

    //Obtengo los datos de la pestaña modificar/eliminar para cambiarlo en la tabla según su respectivo id
    @FXML
    void ModificarDatos(MouseEvent event) {
        boolean todosLosCampos = cuerposDeAgua.length > 0;
        //Campo nombre
        if (todosLosCampos) {
            if (ValidarRegex(this.input_nombre_m.getText(), "\\s")) {
                todosLosCampos = GenerarAlerta("Campo 2: \"Nombre\"", "El nombre tiene espacios en blanco\n¿Desea removerlos?", "Sí, quitalos", "No, voy a modificarlo");
                if (todosLosCampos) {
                    this.input_nombre_m.setText(this.input_nombre_m.getText().replace(" ", ""));
                }
            }
            if (this.input_nombre_m.getText().equals("") || this.input_nombre_m.getText().isEmpty()) {
                GenerarError("Campo 2: \"Nombre\"", "El nombre no debe estar en blanco");
                todosLosCampos = false;
            }
        }
        //Campo municipio
        if (todosLosCampos) {
            if (ValidarRegex(this.input_municipio_m.getText(), "\\s")) {
                todosLosCampos = GenerarAlerta("Campo 3: \"Municipio\"", "El municipio tiene espacios en blanco\n¿Desea removerlos?", "Sí, quitalos", "No, voy a modificarlo");
                if (todosLosCampos) {
                    this.input_municipio_m.setText(this.input_municipio_m.getText().replace(" ", ""));
                }
            }
            if (this.input_municipio_m.getText().equals("") || this.input_municipio_m.getText().isEmpty()) {
                GenerarError("Campo 3: \"Municipio\"", "El nombre no debe estar en blanco");
                todosLosCampos = false;
            }
        }
        //Campo tipo de cuerpo
        if (todosLosCampos) {
            if (ValidarRegex(this.input_tipo_cuerpo_m.getText(), "\\s")) {
                todosLosCampos = GenerarAlerta("Campo 5: \"Tipo de cuerpo\"", "El tipo de cuerpo tiene espacios en blanco\n¿Desea removerlos?", "Sí, quitalos", "No, voy a modificarlo");
                if (todosLosCampos) {
                    this.input_tipo_cuerpo_m.setText(this.input_tipo_cuerpo_m.getText().replace(" ", ""));
                }
            }
            if (this.input_tipo_cuerpo_m.getText().equals("") || this.input_tipo_cuerpo_m.getText().isEmpty()) {
                GenerarError("Campo 5: \"Tipo de cuerpo\"", "El tipo de cuerpo no debe estar en blanco");
                todosLosCampos = false;
            }
        }
        //Campo IRCA
        if (todosLosCampos) {
            if (ValidarRegex(this.input_IRCA_m.getText(), "\\D")) {
                String nuevoFlotante = ValidarFlotante(this.input_IRCA_m.getText());
                if (!nuevoFlotante.equals(this.input_IRCA_m.getText())) {
                    if (!this.input_IRCA_i.getText().equals(nuevoFlotante)) {
                        todosLosCampos = GenerarAlerta("Campo 6: \"IRCA\"", "El IRCA solo debe de tener números\n¿Desea cambiarlo por \"" + nuevoFlotante + "\"?", "Sí, cambialo", "No, voy a modificarlo");
                        if (todosLosCampos) {
                            this.input_IRCA_m.setText(nuevoFlotante);
                        } else {
                            this.input_IRCA_m.setText(nuevoFlotante);
                        }
                    }
                }
            } else {
                this.input_IRCA_m.setText(ValidarFlotante(this.input_IRCA_m.getText()));
            }
            if (Float.parseFloat(this.input_IRCA_m.getText()) < 0 || Float.parseFloat(this.input_IRCA_m.getText()) > 100) {
                GenerarError("Campo 6: \"IRCA\"", "El IRCA debe estar entre 0% y 100% (Ver la tabla en la pestaña \"Nivel de riesgo\")");
                todosLosCampos = false;
            }
            if (this.input_IRCA_m.getText().equals("") || this.input_IRCA_m.getText().isEmpty()) {
                GenerarError("Campo 6: \"IRCA\"", "El IRCA no debe estar en blanco");
                todosLosCampos = false;
            }
        }
        //Actualizar
        if (todosLosCampos) {
            int index = this.input_id_m.getSelectionModel().getSelectedIndex();
            cuerposDeAgua[index].setNombre(this.input_nombre_m.getText());
            cuerposDeAgua[index].setMunicipioAlQuePertenece(this.input_municipio_m.getText());
            cuerposDeAgua[index].setTipoAgua(this.input_tipo_agua_m.getSelectionModel().getSelectedItem().toString());
            cuerposDeAgua[index].setTipoCuerpoDeAgua(this.input_tipo_cuerpo_m.getText());
            cuerposDeAgua[index].setClasificacionIRCA(Float.parseFloat(this.input_IRCA_m.getText()));
            LayoutReto5Controller.ActualizarDatos(cuerposDeAgua);
        }
    }

    //Según el id elimina el cuerpo de agua de la tabla de la ventana principal
    @FXML
    void EliminarDatos(MouseEvent event) {
        if (cuerposDeAgua.length > 0) {
            DensidadPoblacional ca = cuerposDeAgua[this.input_id_m.getSelectionModel().getSelectedIndex()];
            boolean eliminar = GenerarAlerta("Eliminar Id: " + ca.getNumeroDeIdentificacion(), "Seguro de eliminar el cuerpo de agua \"" + ca.getNombre() + "\"", "Si", "No");
            if (eliminar) {
                DensidadPoblacional[] cuerposCopia = new DensidadPoblacional[cuerposDeAgua.length - 1];
                int index = 0;
                for (DensidadPoblacional c : cuerposDeAgua) {
                    if (c.getNumeroDeIdentificacion() == ca.getNumeroDeIdentificacion()) {
                        continue;
                    }
                    cuerposCopia[index] = c;
                    index++;
                }
                cuerposDeAgua = cuerposCopia;
                LayoutReto5Controller.ActualizarDatos(cuerposDeAgua);
                if (cuerposDeAgua.length > 0) {
                    ActualizarIDS();
                    ObtenerCuerpoDeAgua(0);
                } else {
                    this.input_id_m.getItems().removeAll(this.input_id_m.getItems());
                }
            }
        }
    }

    //Según el id del choicebox de la pestaña modificar/eliminar actualiza los otros campos dentro de esa pestaña según lo guardado en la tabla de la ventana principal
    @FXML
    void ObtenerDatos(MouseEvent event) {
        if (cuerposDeAgua.length > 0) {
            ObtenerCuerpoDeAgua(this.input_id_m.getSelectionModel().getSelectedIndex());
        }
    }
}
