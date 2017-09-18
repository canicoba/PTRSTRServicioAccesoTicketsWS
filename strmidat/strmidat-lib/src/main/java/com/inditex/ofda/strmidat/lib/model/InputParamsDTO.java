/**
 * HDFSPersistor.java 29-feb-2016
 *
 * Copyright 2016 INDITEX.
 * Departamento de Sistemas
 */
package com.inditex.ofda.strmidat.lib.model;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * The Class InputParamsDTO.
 *
 * @author <a href="dennis.federico@oracle.com">Dennis Federico</a>
 */
public class InputParamsDTO {

    /**
     * The Enum TipoDocumento.
     *
     * @author <a href="dennis.federico@oracle.com">Dennis Federico</a>
     */
    public enum TipoDocumento {

        /** The venta stock. */
        VENTA_STOCK((short) 00),

        /** The cierres integridad. */
        CIERRES_INTEGRIDAD((short) 60),

        /** The otras operaciones. */
        OTRAS_OPERACIONES((short) 70),

        /** The otros ficheros. */
        OTROS_FICHEROS((short) 80),

        /** The no loc. */
        NO_LOC((short) 90),

        /** The desconocidos. */
        DESCONOCIDOS((short) 99);

        /** The code. */
        short code;

        /**
         * Instancia un nuevo tipo documento.
         *
         * @param code
         *            code
         */
        TipoDocumento(final short code) {
            this.code = code;
        }

        /**
         * From code.
         *
         * @param code
         *            code
         * @return the tipo documento
         */
        public static TipoDocumento fromCode(final short code) {
            for (final TipoDocumento td : TipoDocumento.values()) {
                if (td.code == code) {
                    return td;
                }
            }
            return DESCONOCIDOS;
        }

        /**
         * Obtiene code value.
         *
         * @return code value
         */
        public short getCodeValue() {
            return this.code;
        }

        /**
         * Obtiene no loc code.
         *
         * @return no loc code
         */
        public short getNoLocCode() {
            return (short) (this.code + ordinal());
        }
    }

    // private boolean isSpecific = false;
    private String tipoDocumento = TipoDocumento.VENTA_STOCK.name();
    private Set<Integer> codTiendas;
    private short caja;
    private long codOperacion;
    private Fecha fechaDesde;
    private Fecha fechaHasta;

    private InputParamsDTO() {
    }

    /**
     * Obtiene tipo documento.
     *
     * @return tipo documento
     */
    public String getTipoDocumento() {
        return this.tipoDocumento;
    }

    /**
     * Obtiene cod tiendas.
     *
     * @return cod tiendas
     */
    public Set<Integer> getCodTiendas() {
        return Collections.unmodifiableSet(this.codTiendas);
    }

    /**
     * Obtiene caja.
     *
     * @return caja
     */
    public short getCaja() {
        return this.caja;
    }

    /**
     * Obtiene cod operacion.
     *
     * @return cod operacion
     */
    public long getCodOperacion() {
        return this.codOperacion;
    }

    /**
     * Obtiene fecha desde.
     *
     * @return fecha desde
     */
    public Fecha getFechaDesde() {
        return this.fechaDesde;
    }

    /**
     * Obtiene fecha hasta.
     *
     * @return fecha hasta
     */
    public Fecha getFechaHasta() {
        return this.fechaHasta;
    }

    /**
     * Chequea si operation specific.
     *
     * @param params
     *            params
     * @return true, si operation specific
     */
    public static boolean isOperationSpecific(final InputParamsDTO params) {
        return params.codOperacion > 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "InputParamsDTO{ tipoDocumento=" + this.tipoDocumento + ", codTiendas=" + this.codTiendas + ", caja=" + this.caja + ", codOperacion="
                + this.codOperacion + ", fechaDesde=" + this.fechaDesde + ", fechaHasta=" + this.fechaHasta + '}';
    }

    /**
     * The Interface SpecificInputParamsDTOBuilder.
     *
     * @author <a href="dennis.federico@oracle.com">Dennis Federico</a>
     */
    public interface SpecificInputParamsDTOBuilder {

        /**
         * Construye el with params.
         *
         * @param codTienda
         *            cod tienda
         * @param caja
         *            caja
         * @param codOperacion
         *            cod operacion
         * @param fecha
         *            fecha
         * @return the input params dto
         */
        InputParamsDTO buildWithParams(Integer codTienda, Integer caja, Long codOperacion, Fecha fecha);
    }

    /**
     * The Interface RangeInputParamsDTOBuilder.
     *
     * @author <a href="dennis.federico@oracle.com">Dennis Federico</a>
     */
    public interface RangeInputParamsDTOBuilder {

        /**
         * With range.
         *
         * @param desde
         *            desde
         * @param hasta
         *            hasta
         * @return the optional input params dto builder
         */
        OptionalInputParamsDTOBuilder withRange(Fecha desde, Fecha hasta);
    }

    /**
     * The Interface OptionalInputParamsDTOBuilder.
     *
     * @author <a href="dennis.federico@oracle.com">Dennis Federico</a>
     */
    public interface OptionalInputParamsDTOBuilder {

        /**
         * With cod tienda.
         *
         * @param codTienda
         *            cod tienda
         * @return the optional input params dto builder
         */
        OptionalInputParamsDTOBuilder withCodTienda(Integer codTienda);

        /**
         * For tiendas.
         *
         * @param codTiendas
         *            cod tiendas
         * @return the optional input params dto builder
         */
        OptionalInputParamsDTOBuilder forTiendas(Integer[] codTiendas);

        /**
         * For caja.
         *
         * @param caja
         *            caja
         * @return the optional input params dto builder
         */
        OptionalInputParamsDTOBuilder forCaja(Integer caja);

        /**
         * For operacion.
         *
         * @param codOperacion
         *            cod operacion
         * @return the optional input params dto builder
         */
        OptionalInputParamsDTOBuilder forOperacion(Long codOperacion);

        /**
         * Construye el.
         *
         * @return the input params dto
         */
        InputParamsDTO build();
    }

    /**
     * The Class Builder.
     *
     * @author <a href="dennis.federico@oracle.com">Dennis Federico</a>
     */
    public static class Builder implements SpecificInputParamsDTOBuilder, RangeInputParamsDTOBuilder, OptionalInputParamsDTOBuilder {
        // TODO Validate Fecha arguments and throw exception if needed

        private final Set<Integer> tiendas = new HashSet<>();
        private final InputParamsDTO dto = new InputParamsDTO();

        private Builder() {

        }

        /**
         * New specific input params dto for.
         *
         * @param tipoDocumento
         *            tipo documento
         * @return the specific input params dto builder
         */
        public static SpecificInputParamsDTOBuilder newSpecificInputParamsDTOFor(final TipoDocumento tipoDocumento) {
            final Builder builder = new Builder();
            builder.dto.tipoDocumento = tipoDocumento.name();
            return builder;
        }

        /**
         * New range input params dto for.
         *
         * @param tipoDocumento
         *            tipo documento
         * @return the range input params dto builder
         */
        public static RangeInputParamsDTOBuilder newRangeInputParamsDTOFor(final TipoDocumento tipoDocumento) {
            final Builder builder = new Builder();
            builder.dto.tipoDocumento = tipoDocumento.name();
            return builder;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public InputParamsDTO buildWithParams(final Integer codTienda, final Integer caja, final Long codOperacion, final Fecha fecha) {
            this.tiendas.add(codTienda);
            this.dto.caja = caja.shortValue();
            this.dto.codOperacion = codOperacion;
            this.dto.fechaDesde = fecha;
            this.dto.fechaHasta = fecha;
            return build();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public OptionalInputParamsDTOBuilder withRange(final Fecha desde, final Fecha hasta) {
            this.dto.fechaDesde = desde;
            this.dto.fechaHasta = hasta;
            return this;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public OptionalInputParamsDTOBuilder withCodTienda(final Integer codTienda) {
            this.tiendas.add(codTienda);
            return this;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public OptionalInputParamsDTOBuilder forTiendas(final Integer[] codTiendas) {
            this.tiendas.addAll(Arrays.asList(codTiendas));
            return this;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public OptionalInputParamsDTOBuilder forCaja(final Integer caja) {
            this.dto.caja = caja.shortValue();
            return this;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public OptionalInputParamsDTOBuilder forOperacion(final Long codOperacion) {
            this.dto.codOperacion = codOperacion;
            return this;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public InputParamsDTO build() {
            this.dto.codTiendas = this.tiendas;
            return this.dto;
        }
    }

    /**
     * The Class Fecha.
     *
     * @author <a href="dennis.federico@oracle.com">Dennis Federico</a>
     */
    // TODO add isValid method to support Builder validation?
    public static class Fecha {

        private final String year;
        private final String month;
        private final String day;

        /**
         * Instancia un nuevo fecha.
         *
         * @param year
         *            year
         * @param month
         *            month
         * @param day
         *            day
         */
        public Fecha(final String year, final String month, final String day) {
            this.year = year;
            this.month = month;
            this.day = day;
        }

        /**
         * Obtiene year.
         *
         * @return year
         */
        public String getYear() {
            return this.year;
        }

        /**
         * Obtiene month.
         *
         * @return month
         */
        public String getMonth() {
            return this.month;
        }

        /**
         * Obtiene day.
         *
         * @return day
         */
        public String getDay() {
            return this.day;
        }

        /**
         * As local date.
         *
         * @return the date
         */
        public Date asLocalDate() {
            final Calendar calendar = Calendar.getInstance();
            calendar.set(Integer.parseInt(this.year), Integer.parseInt(this.month) - 1, Integer.parseInt(this.day), 0, 0, 0);
            return calendar.getTime();
        }

        /**
         * Formatted as.
         *
         * @param format
         *            format
         * @return the string
         */
        public String formattedAs(final String format) {
            final SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.format(asLocalDate());
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            return "Fecha{" + "year=" + this.year + ", month=" + this.month + ", day=" + this.day + '}';
        }
    }

}