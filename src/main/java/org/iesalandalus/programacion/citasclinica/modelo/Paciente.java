package org.iesalandalus.programacion.citasclinica.modelo;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Paciente {
	// crear atributos
	private static final String ER_DNI = "^([0-9]{8})([A-Za-z])$";
	private static final String ER_TELEFONO = "^[69][0-9]{8}$";
	private String nombre, dni, telefono;

	// metodo que separa por los espacios y se queda con lo otro como distintos
	// string del array.
	private String formateaNombre(String nombre) {
		String[] nombreDividido = nombre.split(" ");

		StringBuilder nombreFormateado = new StringBuilder();
		// recorre el string y lo que encuentra vacío, lo omite y continua
		for (int i = 0; i < nombreDividido.length; i++) {
			if (nombreDividido[i].isEmpty()) {
				continue;
			}

			// la "letra" en posicion 0 de cada string del array se pone en mayuscula y las
			// demás en minuscula.
			// se unen, y se le añade un espacio detrás de cada string del array, excepto
			// tras el ultimo. Se guardan en un string que es nombreFormateado.
			nombreFormateado.append(nombreDividido[i].substring(0, 1).toUpperCase());
			nombreFormateado.append(nombreDividido[i].substring(1, nombreDividido[i].length()).toLowerCase());
			if (i + 1 < nombreDividido.length) {
				nombreFormateado.append(" ");
			}
		}
		return nombreFormateado.toString();
	}

	private boolean comprobarLetraDNI(String dni) {
		Pattern p = Pattern.compile(ER_DNI);
		Matcher m = p.matcher(dni);

		// compruebo que se cumple el patrón y luego separo por grupos.
		if (m.find()) {
			int numero = Integer.parseInt(m.group(1));// como quiero obtener un int, hago el cambio del string.
			String letra = m.group(2).toUpperCase();

			// Array de char, cada valor de letra (ej: T), ocupa la posición o
			// índice del número que hay en la tabla de validaciones de letras
			// (https://calculadorasonline.com/calcular-la-letra-del-dni-validar-un-dni/).
			// Ej: para la T sería el 0, para la P el 8, etc
			char[] letrasValidasDni = { 'T', 'R', 'W', 'A', 'G', 'M', 'Y', 'F', 'P', 'D', 'X', 'B', 'N', 'J', 'Z', 'S',
					'Q', 'V', 'H', 'L', 'C', 'K', 'E' };

			// Hago el modulo de 23 al numero del DNI para obtener el resto de esa division
			// y comparar la letra con el array anterior
			int numLetra = numero % 23;

			// comparo la letra del patrón (la convierto a char porque es un string) con la
			// posicion que me ha dado numLetra para letrasValidasDni
			if (letra.charAt(0) == letrasValidasDni[numLetra]) {
				return true;
			}
			return false; // Si la letra está bien, devuelve true, si no, false.

		} // si el patrón está bien, devuelve true, si no la excepcion de abajo.

		throw new IllegalArgumentException("ERROR: El DNI no tiene un formato válido.");
	}

	// getters
	public String getNombre() {
		return nombre;
	}

	public String getDni() {
		return dni;
	}

	public String getTelefono() {
		return telefono;
	}

	// setters: comprueban que nombre, dni y telefono no sean nulos, vacios ni estén
	// en blanco. Y lanza excepciones si es necesario.
	public void setNombre(String nombre) {
		if (nombre == null || nombre.isEmpty() || nombre.isBlank()) {
			throw new NullPointerException("ERROR: El nombre de un paciente no puede ser nulo o vacío.");
		}

		// toCharArray convierte un String en array d caracteres.
		// en el for each recorre array y comprueba que cada caracter no
		// sea un letra o un espacio y si se cumple, lanza excepcion.
		for (char caracter : nombre.toCharArray()) {
			if (!Character.isAlphabetic(caracter) && caracter != ' ') {
				throw new IllegalArgumentException("ERROR: El nombre solo puede contener letras.");
			}
		}

		this.nombre = formateaNombre(nombre); // llama a ese metodo para asignar el nombre correcto (formateado).
	}

	private void setDni(String dni) {
		if (dni == null || dni.isEmpty() || dni.isBlank()) {
			throw new NullPointerException("ERROR: El DNI de un paciente no puede ser nulo o vacío.");
		}
		// comprueba que la letra del dni sea correcta, o lanza excepción. Llama al
		// metodo comprobarLetraDni para ello.
		if (comprobarLetraDNI(dni) == false) {
			throw new IllegalArgumentException("ERROR: La letra del DNI no es correcta.");
		}
		this.dni = dni;
	}

	public void setTelefono(String telefono) throws IllegalArgumentException {
		if (telefono == null || telefono.isEmpty() || telefono.isBlank()) {
			throw new NullPointerException("ERROR: El teléfono de un paciente no puede ser nulo o vacío.");
		}
		// Compruebo el patron del telefono y si está bien lo asigno, si no, lanzo
		// excepcion.
		Pattern p = Pattern.compile(ER_TELEFONO);
		Matcher m = p.matcher(telefono);

		if (m.find()) {
			this.telefono = telefono;

		} else {
			throw new IllegalArgumentException("ERROR: El teléfono no tiene un formato válido.");
		}

	}

	// Constructor con parámetros
	public Paciente(String nombre, String dni, String telefono) {
		setNombre(nombre);
		setDni(dni);
		setTelefono(telefono);
	}

	// Constructor copia. Compruebo q no sea nulo (o lanzo excepcion), se crea un
	// paciente con los datos.
	public Paciente(Paciente paciente) {
		if (paciente == null) {
			throw new NullPointerException("ERROR: No es posible copiar un paciente nulo.");
		}
		setNombre(paciente.nombre);
		setDni(paciente.dni);
		setTelefono(paciente.telefono);
	}

	@Override
	public int hashCode() {
		return Objects.hash(dni, nombre, telefono);
	}

	// equals que comprueba si dos pacientes son el mismo, comparando sus dni.
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Paciente other = (Paciente) obj;
		return Objects.equals(dni, other.dni);
	}

	// metodo que divide el array por los espacios y se queda con el 1º valor de
	// cada string (lo paso a char y me quedo con el valor 0), los uno y revuelvo
	// como string en iniciales
	private String getIniciales() {
		String[] nombreDividido = nombre.split(" ");

		StringBuilder iniciales = new StringBuilder();

		for (int i = 0; i < nombreDividido.length; i++) {
			iniciales.append(nombreDividido[i].charAt(0));

		}
		return iniciales.toString();
	}

	// metodo toString que devuelve la inforamcion como se pide en los test.
	@Override
	public String toString() {

		return "nombre=" + nombre + " (" + getIniciales() + "), DNI=" + dni + ", teléfono=" + telefono;
	}

}
