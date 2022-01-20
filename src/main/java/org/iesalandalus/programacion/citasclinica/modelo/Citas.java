package org.iesalandalus.programacion.citasclinica.modelo;

import java.time.LocalDate;

import javax.naming.OperationNotSupportedException;

public class Citas {

	// crear atributos
	private int capacidad, tamano;
	private Cita[] citas;

	// metodos getters
	public Cita[] getCitas() {
		return citas;
	}

	public int getTamano() {
		return tamano;
	}

	public int getCapacidad() {
		return capacidad;
	}

	// metodo tamanoSuperado
	private boolean tamanoSuperado(int tamano) {
		if (this.tamano < tamano) {
			return true;
		}
		return false;
	}
	
	// metodo capacidadSuperada (si la capacidad es menor que la capacidad que le
	// pasamos, se supera la capacidad, sería true.
	private boolean capacidadSuperada(int capacidad) {
		if (this.capacidad < capacidad) {
			return true;
		}
		return false;

	}

	/*
	 * se recorre el array citas y se compara cada cita con la cita que le pasamos,
	 * cuando es igual, devuelve i que es el valor del indice del array citas donde
	 * se encuentra a cita, si no, devuelve el tamaño + 1
	 */
	private int buscarIndice(Cita cita) {
		for (int i = 0; i < tamano; i++) {
			if (citas[i].equals(cita)) {
				return i;
			}
		}
		return tamano + 1;
	}

	/*
	 * comprueba que no sea nulo o que el tamaño no sea igual que la capacidad
	 * (porque si es igual ya no se pueden añadir mas citas). Luego, si el tamano es
	 * > 0 es que mínimo hay una cita, con lo cual con el metodo buscarIndice
	 * buscamos en que posicion está (lo guardo en citaBuscada), luego si el tamano
	 * es mayor que dicho indice, significa que esa cita ya existe para esa fecha y
	 * hora, si no, se guarda una copia de la cita que
	 * nos han pasado en el array citas y luego se incrementa el tamaño de citas
	 */
	public void insertar(Cita cita) throws OperationNotSupportedException {
		if (cita == null) {
			throw new NullPointerException("ERROR: No se puede insertar una cita nula.");
		}
		if (tamano == capacidad) {
			throw new OperationNotSupportedException("ERROR: No se aceptan más citas.");
		}
		if (tamano > 0) {
			int citaBuscada;
			citaBuscada = buscarIndice(cita);

			if (tamano > citaBuscada) {
				throw new OperationNotSupportedException("ERROR: Ya existe una cita para esa fecha y hora.");
			}
		}
		citas[tamano++] = new Cita(cita);

	}

	/*
	 * metodo que recorre el array citas y si encuentra una igual que la que le
	 * pasamos, devuelve una copia, si no, devuelve nulo
	 */
	public Cita buscar(Cita cita) {
		for (int i = 0; i < tamano; i++) {
			if (citas[i].equals(cita)) {
				return new Cita(cita);
			}
		}
		return null;

	}

	/*
	 * comprueba el indice que le pasamos en el metodo capacidad superada, para que
	 * no supere la capacidad. En if tamanoSuperado, si el indice es menor al
	 * tamano, se pone nula esa posicion en el array citas y luego con el for vamos
	 * desplazando el resto de citas a la izquierda, intercambiandolas para que
	 * quede a la derecha del todo el que se pone a nulo
	 */
	private void desplazarUnaPosicionHaciaIzquierda(int indice) {
		if (capacidadSuperada(indice)) {
			throw new IllegalArgumentException("ERROR: Índice fuera de rango");
		}
		if (tamanoSuperado(indice)) {
			throw new IllegalArgumentException("ERROR: Índice fuera de rango");

		}
		citas[indice] = null;

		for (int i = indice; i < capacidad - 1; i++) {
			Cita aux = citas[i];
			citas[i] = citas[i + 1];
			citas[i + 1] = aux;

		}
	}

	/*
	 * comprueba que la cita no sea nula. Guarda en resultado el indice de la cita
	 * que le pasamos y si es mayor que el tamano, quiere decir que no hay ninguna
	 * cita para esa fecha hora, con lo cual no puede borrarla. Si no, la borra
	 * usando el metodo desplazarUnaPosicionHaciaIzquierda y decrementa el tamaño.
	 */
	public void borrar(Cita cita) throws OperationNotSupportedException {
		if (cita == null) {
			throw new IllegalArgumentException("ERROR: No se puede borrar una cita nula.");
		}
		int resultado = buscarIndice(cita);
		if (resultado > tamano) {
			throw new OperationNotSupportedException("ERROR: No existe ninguna cita para esa fecha y hora.");
		}
		desplazarUnaPosicionHaciaIzquierda(resultado);
		tamano--;
	}

	//Constructor con parámetros
	public Citas(int capacidad) {
		if (capacidad <= 0) {
			throw new IllegalArgumentException("ERROR: La capacidad debe ser mayor que cero.");
		}
		this.capacidad = capacidad;
		tamano = 0;
		citas = new Cita[capacidad];
	}


	/*
	 * creo el objeto citasPorFecha con la misma capacidad del objeto Citas que ya
	 * tengo. Lo recorro y en cada posicion de citas compruebo la fecha, si es igual
	 * que la fecha que le paso, las va insertando en el nuevo objeto citasPorFecha.
	 * Al final devuelve el array del objeto citasPorFecha
	 */
	public Cita[] getCitas(LocalDate fecha) {
		Citas citasPorFecha = new Citas(capacidad);
		for (int i = 0; i < tamano; i++) {
			if (citas[i].getFechaHora().toLocalDate().equals(fecha)) {
				try {
					citasPorFecha.insertar(citas[i]);
				} catch (OperationNotSupportedException e) {
					System.out.println(e.getMessage());

				}
			}
		}
		return citasPorFecha.citas;
	}

}
