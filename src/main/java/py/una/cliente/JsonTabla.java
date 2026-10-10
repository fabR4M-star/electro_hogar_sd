package py.una.cliente;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public final class JsonTabla {

	private JsonTabla() {
	}

	public static String convertir(String json) {
		Object resultado;
		try {
			resultado = new JSONParser().parse(json);
		} catch (Exception ex) {
			return json;
		}

		if (resultado instanceof JSONArray) {
			return tablaArreglo((JSONArray) resultado);
		}
		if (!(resultado instanceof JSONObject)) {
			return json;
		}

		JSONObject objeto = (JSONObject) resultado;
		List<List<String>> filas = new ArrayList<List<String>>();
		List<String> columnas = new ArrayList<String>();
		columnas.add("Campo");
		columnas.add("Valor");
		StringBuilder salida = new StringBuilder();
		List<String> seccionesArreglos = new ArrayList<String>();

		for (Object clave : objeto.keySet()) {
			String campo = limpiar(String.valueOf(clave));
			Object valor = objeto.get(clave);
			if (valor instanceof JSONArray) {
				seccionesArreglos.add(campo + System.lineSeparator()
						+ tablaArreglo((JSONArray) valor));
			} else {
				List<String> fila = new ArrayList<String>();
				fila.add(campo);
				fila.add(formatearValor(valor));
				filas.add(fila);
			}
		}

		if (!filas.isEmpty()) {
			salida.append(tabla(columnas, filas));
		}
		for (String seccion : seccionesArreglos) {
			if (salida.length() > 0) {
				salida.append(System.lineSeparator()).append(System.lineSeparator());
			}
			salida.append(seccion);
		}
		return salida.toString();
	}

	private static String tablaArreglo(JSONArray arreglo) {
		if (arreglo.isEmpty()) {
			return "(sin elementos)";
		}

		boolean objetos = true;
		Set<String> conjuntoColumnas = new LinkedHashSet<String>();
		for (Object elemento : arreglo) {
			if (!(elemento instanceof JSONObject)) {
				objetos = false;
				break;
			}
			for (Object clave : ((JSONObject) elemento).keySet()) {
				conjuntoColumnas.add(String.valueOf(clave));
			}
		}

		List<String> columnas = new ArrayList<String>();
		List<List<String>> filas = new ArrayList<List<String>>();
		if (objetos && !conjuntoColumnas.isEmpty()) {
			columnas.addAll(conjuntoColumnas);
			for (Object elemento : arreglo) {
				JSONObject objeto = (JSONObject) elemento;
				List<String> fila = new ArrayList<String>();
				for (String columna : columnas) {
					fila.add(formatearValor(objeto.get(columna)));
				}
				filas.add(fila);
			}
		} else if (objetos) {
			return "(" + arreglo.size() + " elementos sin campos)";
		} else {
			columnas.add("Índice");
			columnas.add("Valor");
			for (int indice = 0; indice < arreglo.size(); indice++) {
				List<String> fila = new ArrayList<String>();
				fila.add(String.valueOf(indice + 1));
				fila.add(formatearValor(arreglo.get(indice)));
				filas.add(fila);
			}
		}
		return tabla(columnas, filas);
	}

	private static String tabla(List<String> columnas, List<List<String>> filas) {
		int[] anchos = new int[columnas.size()];
		for (int indice = 0; indice < columnas.size(); indice++) {
			anchos[indice] = limpiar(columnas.get(indice)).length();
		}
		for (List<String> fila : filas) {
			for (int indice = 0; indice < anchos.length; indice++) {
				anchos[indice] = Math.max(anchos[indice], limpiar(fila.get(indice)).length());
			}
		}

		StringBuilder borde = new StringBuilder("+");
		for (int ancho : anchos) {
			borde.append(repetir('-', ancho + 2)).append('+');
		}
		StringBuilder resultado = new StringBuilder();
		resultado.append(borde).append(System.lineSeparator());
		agregarFila(resultado, columnas, anchos);
		resultado.append(borde).append(System.lineSeparator());
		for (List<String> fila : filas) {
			agregarFila(resultado, fila, anchos);
		}
		resultado.append(borde);
		return resultado.toString();
	}

	private static void agregarFila(StringBuilder tabla, List<String> valores, int[] anchos) {
		tabla.append('|');
		for (int indice = 0; indice < anchos.length; indice++) {
			tabla.append(' ').append(String.format("%-" + anchos[indice] + "s",
					limpiar(valores.get(indice)))).append(" |");
		}
		tabla.append(System.lineSeparator());
	}

	private static String formatearValor(Object valor) {
		if (valor instanceof JSONObject || valor instanceof JSONArray) {
			return limpiar(valor.toString());
		}
		return limpiar(String.valueOf(valor));
	}

	private static String limpiar(String texto) {
		return texto.replace("\n", " ").replace("\r", " ");
	}

	private static String repetir(char caracter, int veces) {
		StringBuilder texto = new StringBuilder(veces);
		for (int indice = 0; indice < veces; indice++) {
			texto.append(caracter);
		}
		return texto.toString();
	}
}