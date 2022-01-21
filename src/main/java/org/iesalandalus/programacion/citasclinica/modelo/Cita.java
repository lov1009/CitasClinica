package org.iesalandalus.programacion.citasclinica.modelo;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Cita {

	// crear atributos
	public static final String FORMATO_FECHA_HORA = "dd/MM/yyyy HH:mm";
	private LocalDateTime fechaHora;
	private Paciente paciente;

	// getters
	// con new llamando al constructor copia de paciente para que no devuelva la
	// referencia del paciente si no una copia del mismo
	public Paciente getPaciente() {
		return new Paciente(paciente);
	}

	public LocalDateTime getFechaHora() {
		return fechaHora;
	}

	// setters. compruebo que paciente y la fecha-hora no sean nulos o lanzo
	// excepción
	// con new llamando al constructor copia de paciente para que no se quede con la
	// referencia del paciente si no con una copia del mismo
	private void setPaciente(Paciente paciente) {
		if (paciente == null) {
			throw new NullPointerException("ERROR: El paciente de una cita no puede ser nulo.");
		}
		this.paciente = new Paciente(paciente);
	}


	//comprueba que la fechaHora no sea nula y que no sea anterior a la hora real actual.
	public void setFechaHora(LocalDateTime fechaHoraLocal) {
		if (fechaHoraLocal == null) {
			throw new NullPointerException("ERROR: La fecha y hora de una cita no puede ser nula.");
		}
		if(fechaHoraLocal.isBefore(LocalDateTime.now())) {
			throw new IllegalArgumentException("ERROR: La fecha no puede ser anterior al momento actual.");
		}
		fechaHora = fechaHoraLocal;

	}

	// constructor con parámetros
	public Cita(Paciente paciente, LocalDateTime FechaHoraLocal) {
		setPaciente(paciente);
		setFechaHora(FechaHoraLocal);
	}

	// constructor copia de cita. Compruebo q no sea nulo (o lanzo excepcion), se
	// crea una cita con los datos.
	public Cita(Cita cita) {
		if (cita == null) {
			throw new NullPointerException("ERROR: No se puede copiar una cita nula.");
		}
		setPaciente(cita.paciente);
		setFechaHora(cita.fechaHora);

	}

	@Override
	public int hashCode() {
		return Objects.hash(fechaHora, paciente);
	}

	// equals que comprueba si dos citas son la misma, comparando la fecha y hora.
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cita other = (Cita) obj;
		return Objects.equals(fechaHora, other.fechaHora);
	}

	// metodo toString que devuelve la informacion como se pide en los test.
	@Override
	public String toString() {
		return paciente + ", fechaHora=" + fechaHora.format(DateTimeFormatter.ofPattern(FORMATO_FECHA_HORA));
	}

}
