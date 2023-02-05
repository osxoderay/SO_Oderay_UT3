package org.example.entity;

public class CsvEntity {

    private String articulo;

    private String tipo;

    private String fechaVenta;

    private String precio;

    private String costeDerivado;

    private String costeProduccion;

    private String impuestos;

    public CsvEntity() {
    }

    public CsvEntity(String articulo, String tipo, String fechaVenta, String precio, String costeDerivado, String costeProduccion, String impuestos) {
        this.articulo = articulo;
        this.tipo = tipo;
        this.fechaVenta = fechaVenta;
        this.precio = precio;
        this.costeDerivado = costeDerivado;
        this.costeProduccion = costeProduccion;
        this.impuestos = impuestos;
    }

    public String getArticulo() {
        return articulo;
    }

    public void setArticulo(String articulo) {
        this.articulo = articulo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(String fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getCosteDerivado() {
        return costeDerivado;
    }

    public void setCosteDerivado(String costeDerivado) {
        this.costeDerivado = costeDerivado;
    }

    public String getCosteProduccion() {
        return costeProduccion;
    }

    public void setCosteProduccion(String costeProduccion) {
        this.costeProduccion = costeProduccion;
    }

    public String getImpuestos() {
        return impuestos;
    }

    public void setImpuestos(String impuestos) {
        this.impuestos = impuestos;
    }
}
