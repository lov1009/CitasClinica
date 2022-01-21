package org.iesalandalus.programacion.citasclinica.vista;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import org.iesalandalus.programacion.citasclinica.modelo.Cita;
import org.iesalandalus.programacion.citasclinica.modelo.Paciente;
import org.iesalandalus.programacion.utilidades.Entrada;

public class Consola {

	private Consola() {

	}

	public static void mostrarMenu() {
		System.out.println("");
		System.out.println("          MENÚ       ");
		System.out.println("------------------------------------");
		System.out.println("  1. INSERTAR");
		System.out.println("  2. BUSCAR");
		System.out.println("  3. BORRAR");
		System.out.println("  4. MOSTRAR TODAS LAS CITAS");
		System.out.println("  5. MOSTRAR LAS CITAS DE UNA FECHA");
		System.out.println("  6. SALIR");
		System.out.println("");
	}

	public static Opciones elegirOpcion() {
		System.out.println("Elige una opción del menú.");
		int opcionElegida = Entrada.entero();
		while (opcionElegida < 1 || opcionElegida > 6) {
			System.out.println("Esa no es una opción del menú. Vuelve a elegir una opción correcta.");
			opcionElegida = Entrada.entero();
		}

		switch (opcionElegida) {
		case 1:
			return Opciones.INSERTAR_CITA;

		case 2:
			return Opciones.BUSCAR_CITA;

		case 3:
			return Opciones.BORRAR_CITA;

		case 4:
			return Opciones.MOSTRAR_CITAS;

		case 5:
			return Opciones.MOSTRAR_CITAS_DIA;

		case 6:
			return Opciones.SALIR;

		default:
			return null;
		}

	}

	public static Paciente leerPaciente() {
		System.out.println("Introduzca el nombre:");
		String nombreIntroducido = Entrada.cadena();
		System.out.println("Introduzca el DNI:");
		String dniIntroducido = Entrada.cadena();
		System.out.println("Introduzca el teléfono:");
		String telefonoIntroducido = Entrada.cadena();

		return new Paciente(nombreIntroducido, dniIntroducido, telefonoIntroducido);
	}

	// metodo que mientras la fecha-hora no cumpla el patrón, la está pidiendo,
	// cuando es válida la devuelve
	public static LocalDateTime leerFechaHora() {
		LocalDateTime fechaHoraValida = null;
		while (fechaHoraValida == null) {
			
			System.out.println("Introduzca la fecha y hora, con el formato: dd/MM/yyyy HH:mm ");
			String fechaHoraIntroducida = Entrada.cadena();
			try {
				fechaHoraValida = LocalDateTime.parse(fechaHoraIntroducida,
						DateTimeFormatter.ofPattern(Cita.FORMATO_FECHA_HORA));
			} catch (DateTimeParseException e) {
				System.out.println("ERROR: El formato de la fecha no es correcto.");
			}
		}
		return fechaHoraValida;
	}

	// metodo que lee un paciente y una fecha-hora y con el constructor Cita crea un
	// nuevo objeto cita con los parametros que le pasamos
	public static Cita leerCita() {
		Paciente paciente = leerPaciente();
		LocalDateTime fechaHora = leerFechaHora();
		return new Cita(paciente, fechaHora);

	}

	/*
	 * metodo que lee una fecha mientras la fecha no cumpla el patrón, la está
	 * pidiendo, cuando es válida la devuelve. Pide solo la fecha y luego le añado
	 * al patron la hora 00:00 para que pueda cumplir el formato fecha hora.
	 */
	public static LocalDate leerFecha() {
		LocalDate fechaValida = null;
		while (fechaValida == null) {
			System.out.println("Introduzca la fecha, con el formato: dd/MM/yyyy");
			StringBuilder fechaIntroducida = new StringBuilder();
			fechaIntroducida.append(Entrada.cadena()).append(" 00:00");
			try {
				fechaValida = LocalDate.parse(fechaIntroducida, DateTimeFormatter.ofPattern(Cita.FORMATO_FECHA_HORA));
			} catch (DateTimeParseException e) {
				System.out.println("ERROR: El formato de la fecha no es correcto.");
			}
		}
		return fechaValida;
	}
}
