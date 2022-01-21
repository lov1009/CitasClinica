package org.iesalandalus.programacion.citasclinica;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.citasclinica.modelo.Cita;
import org.iesalandalus.programacion.citasclinica.modelo.Citas;
import org.iesalandalus.programacion.citasclinica.vista.Consola;
import org.iesalandalus.programacion.citasclinica.vista.Opciones;

public class MainApp {

	private static final int NUM_MAX_CITAS = 5;
	private static Citas citasClinica;

	// metodo paar insertar citas, si algún dato no es correcto al leer los datos,
	// vuelve a pedirtelos.
	private static void insertarCita() {
		boolean insertarCitaCorrecta = false;
		do {
			try {
				Cita cita = Consola.leerCita();
				citasClinica.insertar(cita);
				insertarCitaCorrecta = true;
			} catch (OperationNotSupportedException e) {
				System.out.println(e.getMessage());
				System.out.println("");

			} catch (IllegalArgumentException e) {
				System.out.println(e.getMessage());
				System.out.println("");

			} catch (NullPointerException e) {
				System.out.println(e.getMessage());
				System.out.println("");
			}
		} while (insertarCitaCorrecta == false);

	}

	//metodo que lee una fecha hora y la comprueba en citasclinica, si la encuentra la imprime
	private static void buscarCita() {
		LocalDateTime fechaHora = Consola.leerFechaHora();
		for (int i = 0; i < citasClinica.getTamano(); i++) {
			if (citasClinica.getCitas()[i].getFechaHora().equals(fechaHora)) {
				System.out.println(citasClinica.getCitas()[i]);
				return;
			}
		}
		System.out.println("La cita no existe.");
	}

	//metodo que lee una fecha hora y la comprueba en citasclinica, si la encuentra la borra
	private static void borrarCita() {
		LocalDateTime fechaHora = Consola.leerFechaHora();
		for (int i = 0; i < citasClinica.getTamano(); i++) {
			if (citasClinica.getCitas()[i].getFechaHora().equals(fechaHora)) {
				try {
					citasClinica.borrar(citasClinica.getCitas()[i]);
				} catch (OperationNotSupportedException e) {
					System.out.println(e.getMessage());

				}
				return;
			}
		}
		System.out.println("No se puede borrar una cita que no existe.");

	}

	// metodo que muestra las citas, excepto cuando no hay ninguna, que avisa de que
	// no hay. Cuando son nulas tampoco las muestra.
	private static void mostrarCitas() {
		if (citasClinica.getTamano() == 0) {
			System.out.println("No hay citas.");
			return;
		}
		for (Cita citaActual : citasClinica.getCitas()) {
			if (citaActual != null) {
				System.out.println(citaActual);
			}
		}
	}

	// metodo que lee una fecha y muestra las citas para esa fecha, excepto cuando no hay o son nulas.
	private static void mostrarCitasDia() {
		LocalDate fecha = Consola.leerFecha();

		Cita[] citasFecha = citasClinica.getCitas(fecha);
		if (citasFecha[0] == null) {
			System.out.println("No hay citas para esa fecha.");
			return;
		}

		for (Cita citaActual : citasFecha) {
			if (citaActual != null) {
				System.out.println(citaActual);
			}
		}
	}

	// metodo con las opciones, llama a cada metodo para llevarlas a cabo
	private static void ejecutarOpcion(Opciones opcion) {
		switch (opcion) {
		case INSERTAR_CITA:

			insertarCita();
			break;
		case BUSCAR_CITA:

			buscarCita();
			break;
		case BORRAR_CITA:
			borrarCita();
			break;
		case MOSTRAR_CITAS:
			mostrarCitas();
			break;
		case MOSTRAR_CITAS_DIA:
			mostrarCitasDia();
			break;

		default:
			break;
		}

	}

	// método que muestra el menu con las opciones mientras no se elija la opcion
	// salir
	public static void main(String[] args) {
		citasClinica = new Citas(NUM_MAX_CITAS);
		System.out.println("Programa para gestionar las citas de la Clínica.");
		Opciones opcion;
		do {
			Consola.mostrarMenu();
			opcion = Consola.elegirOpcion();
			ejecutarOpcion(opcion);
		} while (opcion != Opciones.SALIR);

	}

}
