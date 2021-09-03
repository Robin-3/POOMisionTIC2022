package Clases;

public class DensidadPoblacional extends CuerpoDeAgua {
    
    private int cantidadDeHabitantesEnLaZona;

    public DensidadPoblacional(int _numeroDeIdentificacion, String _nombre, String _municipioAlQuePertenece, String _tipoCuerpoDeAgua, String _tipoAgua, float _clasificacionIRCA) {
        super(_numeroDeIdentificacion, _nombre, _municipioAlQuePertenece, _tipoCuerpoDeAgua, _tipoAgua, _clasificacionIRCA);
    }
    
    public int afeccion() {
        if(this.cantidadDeHabitantesEnLaZona < 10000)
            return 0;
        if(this.cantidadDeHabitantesEnLaZona < 50000)
            return 1;
        return 2;
    }

    public int GetCantidadDeHabitantesEnLaZona() {
        return this.cantidadDeHabitantesEnLaZona;
    }
    
    public String getCantidadDeHabitantesEnLaZona() {
        return "# "+this.cantidadDeHabitantesEnLaZona+"\nAfección: "+afeccion();
    }

    public void setCantidadDeHabitantesEnLaZona(int _cantidadDeHabitantesEnLaZona) {
        this.cantidadDeHabitantesEnLaZona = _cantidadDeHabitantesEnLaZona;
    }
}
