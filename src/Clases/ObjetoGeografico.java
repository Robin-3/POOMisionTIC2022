package Clases;

public class ObjetoGeografico {
    
    private int numeroDeIdentificacion;
    private String nombre/*DelCuerpoDeAgua*/, municipioAlQuePertenece;

    public ObjetoGeografico(int _numeroDeIdentificacion, String _nombre, String _municipioAlQuePertenece) {
        this.numeroDeIdentificacion = _numeroDeIdentificacion;
        this.nombre = _nombre;
        this.municipioAlQuePertenece = _municipioAlQuePertenece;
    }

    public int getNumeroDeIdentificacion() {
        return this.numeroDeIdentificacion;
    }

    public String getNombre() {
        return this.nombre;
    }

    public String getMunicipioAlQuePertenece() {
        return this.municipioAlQuePertenece;
    }

    public void setNumeroDeIdentificacion(int _numeroDeIdentificacion) {
        this.numeroDeIdentificacion = _numeroDeIdentificacion;
    }

    public void setNombre(String _nombre) {
        this.nombre = _nombre;
    }

    public void setMunicipioAlQuePertenece(String _municipioAlQuePertenece) {
        this.municipioAlQuePertenece = _municipioAlQuePertenece;
    }
}