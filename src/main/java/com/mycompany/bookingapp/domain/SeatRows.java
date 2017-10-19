package com.mycompany.bookingapp.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A SeatRows.
 */
@Entity
@Table(name = "seat_rows")
public class SeatRows implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "row_code", nullable = false)
    private String rowCode;

    @NotNull
    @Column(name = "row_name", nullable = false)
    private String rowName;

    @ManyToOne(optional = false)
    @NotNull
    private Screening screening;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRowCode() {
        return rowCode;
    }

    public SeatRows rowCode(String rowCode) {
        this.rowCode = rowCode;
        return this;
    }

    public void setRowCode(String rowCode) {
        this.rowCode = rowCode;
    }

    public String getRowName() {
        return rowName;
    }

    public SeatRows rowName(String rowName) {
        this.rowName = rowName;
        return this;
    }

    public void setRowName(String rowName) {
        this.rowName = rowName;
    }

    public Screening getScreening() {
        return screening;
    }

    public SeatRows screening(Screening screening) {
        this.screening = screening;
        return this;
    }

    public void setScreening(Screening screening) {
        this.screening = screening;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SeatRows seatRows = (SeatRows) o;
        if (seatRows.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), seatRows.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SeatRows{" +
            "id=" + getId() +
            ", rowCode='" + getRowCode() + "'" +
            ", rowName='" + getRowName() + "'" +
            "}";
    }
}
