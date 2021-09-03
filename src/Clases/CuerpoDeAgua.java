package Clases;

import java.util.Locale;

public class CuerpoDeAgua extends ObjetoGeografico {

    private String tipoCuerpoDeAgua, tipoAgua;
    private float clasificacionIRCA;

    public CuerpoDeAgua(int _numeroDeIdentificacion, String _nombre, String _municipioAlQuePertenece, String _tipoCuerpoDeAgua, String _tipoAgua, float _clasificacionIRCA) {
        super(_numeroDeIdentificacion, _nombre, _municipioAlQuePertenece);
        this.tipoCuerpoDeAgua = _tipoCuerpoDeAgua;
        this.tipoAgua = _tipoAgua;
        this.clasificacionIRCA = _clasificacionIRCA;
    }

    public String nivel() {
        if (this.clasificacionIRCA > 80) {
            return "INVIABLE SANITARIAMENTE";
        }
        if (this.clasificacionIRCA > 35) {
            return "ALTO";
        }
        if (this.clasificacionIRCA > 14) {
            return "MEDIO";
        }
        if (this.clasificacionIRCA > 5) {
            return "BAJO";
        }
        return "SIN RIESGO";
    }

    public String getTipoCuerpoDeAgua() {
        return this.tipoCuerpoDeAgua;
    }

    public String getTipoAgua() {
        return this.tipoAgua;
    }

    public float GetClasificacionIRCA() {
        return this.clasificacionIRCA;
    }
    
    public String getClasificacionIRCA() {
        String riesgo = nivel();
        riesgo = riesgo.substring(0,1).toUpperCase() + riesgo.substring(1).toLowerCase();
        return String.format(Locale.US, "%.2f", this.clasificacionIRCA) + "%\nRiesgo: "+riesgo;
    }

    public void setTipoCuerpoDeAgua(String _tipoCuerpoDeAgua) {
        this.tipoCuerpoDeAgua = _tipoCuerpoDeAgua;
    }

    public void setTipoAgua(String _tipoAgua) {
        this.tipoAgua = _tipoAgua;
    }

    public void setClasificacionIRCA(float _clasificacionIRCA) {
        this.clasificacionIRCA = _clasificacionIRCA;
    }
}
