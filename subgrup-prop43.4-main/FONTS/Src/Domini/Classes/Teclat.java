package Domini.Classes;

public class Teclat {

	private Integer idTeclat;
	private String nomTeclat;
	private Integer idAlfabet;
	private Integer idEntrada;
	private Character[][] teclat;

	public Teclat(Integer idTeclat, String nomTeclat, Integer idAlfabet, Integer idEntrada, String teclat) {
		this.idTeclat = idTeclat;
		this.nomTeclat = nomTeclat;
		this.idAlfabet = idAlfabet;
		this.idEntrada = idEntrada;
		String[] filesTeclat = teclat.split(",");
		Character[][] matriuTeclat = new Character[filesTeclat.length][];
		for(int i = 0; i < filesTeclat.length; ++i) {
			matriuTeclat[i] = new Character[filesTeclat[i].length()];
			for (int j = 0; j < filesTeclat[i].length(); ++j)
				matriuTeclat[i][j] = filesTeclat[i].charAt(j);
		}
		this.teclat = matriuTeclat;

	}

	public Character[][] consultarTeclat() {
		return teclat;
	}

	public String consultarNomTeclat() {
		return nomTeclat;
	}

	public Integer consultarIdEntrada() {
		return idEntrada;
	}

	public Integer consultarIdAlfabet() {
		return idAlfabet;
	}

	public void reassignarEntrada(String teclat, Integer idEntrada) {
		this.idEntrada = idEntrada;
		String[] filesTeclat = teclat.split(",");
		Character[][] matriuTeclat = new Character[filesTeclat.length][];
		for(int i = 0; i < filesTeclat.length; ++i) {
			matriuTeclat[i] = new Character[filesTeclat[i].length()];
			for (int j = 0; j < filesTeclat[i].length(); ++j)
				matriuTeclat[i][j] = filesTeclat[i].charAt(j);
		}
		this.teclat = matriuTeclat;
	}

	public void reassignarAlfabet(String teclat, Integer idAlfabet) {
		this.idAlfabet = idAlfabet;
		String[] filesTeclat = teclat.split(",");
		Character[][] matriuTeclat = new Character[filesTeclat.length][];
		for(int i = 0; i < filesTeclat.length; ++i) {
			matriuTeclat[i] = new Character[filesTeclat[i].length()];
			for (int j = 0; j < filesTeclat[i].length(); ++j)
				matriuTeclat[i][j] = filesTeclat[i].charAt(j);
		}
		this.teclat = matriuTeclat;
	}
}