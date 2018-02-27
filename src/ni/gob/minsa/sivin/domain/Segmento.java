package ni.gob.minsa.sivin.domain;

/**
 * Segmento es la clase que representa el segmento censal donde se realiza una encuesta.
 * 
 * Segmento incluye información básica como:
 * 
 * <ul>
 * <li>Departamento
 * <li>Municipio
 * <li>Barrio/Comunidad
 * <li>Comunidad SIS
 * </ul>
 * <p>
 * Comunidad SIS es el enlace al esquema General dentro del SIS MINSA.
 * 
 *  
 * @author      William Avilés
 * @version     1.0
 * @since       1.0
 */
public class Segmento extends BaseMetaData{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String ident;
	private String codigo;
	private String departamento;
	private String codMun;
	private String municipio;
	private String comunidad;
	private String region;
	private String procedencia;
	private String estrato;
	private Integer vivParticulares;
	private Integer vivInicial;
	private String codigoSis;
	private String grupo;
	
	
	public Segmento() {
		super();
	}


	public Segmento(String ident, String codigo, String departamento, String municipio, String comunidad, String region,
			String tipo, String codigoSis) {
		super();
		this.ident = ident;
		this.codigo = codigo;
		this.departamento = departamento;
		this.municipio = municipio;
		this.comunidad = comunidad;
		this.region = region;
		this.procedencia = tipo;
		this.codigoSis = codigoSis;
	}

	public String getIdent() {
		return ident;
	}


	public void setIdent(String ident) {
		this.ident = ident;
	}

	public String getCodigo() {
		return codigo;
	}


	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDepartamento() {
		return departamento;
	}


	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}

	public String getMunicipio() {
		return municipio;
	}


	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}

	public String getComunidad() {
		return comunidad;
	}

	public void setComunidad(String comunidad) {
		this.comunidad = comunidad;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getProcedencia() {
		return procedencia;
	}

	public void setProcedencia(String procedencia) {
		this.procedencia = procedencia;
	}

	public String getCodigoSis() {
		return codigoSis;
	}


	public void setCodigoSis(String codigoSis) {
		this.codigoSis = codigoSis;
	}

	

	public String getCodMun() {
		return codMun;
	}


	public void setCodMun(String codMun) {
		this.codMun = codMun;
	}


	public String getEstrato() {
		return estrato;
	}


	public void setEstrato(String estrato) {
		this.estrato = estrato;
	}


	public Integer getVivParticulares() {
		return vivParticulares;
	}


	public void setVivParticulares(Integer vivParticulares) {
		this.vivParticulares = vivParticulares;
	}


	public Integer getVivInicial() {
		return vivInicial;
	}


	public void setVivInicial(Integer vivInicial) {
		this.vivInicial = vivInicial;
	}


	public String getGrupo() {
		return grupo;
	}


	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}


	@Override
	public String toString(){
		return this.getComunidad()+"-"+this.getCodigo();
	}
	
	@Override
	public boolean equals(Object other) {
		
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof Segmento))
			return false;
		
		Segmento castOther = (Segmento) other;

		return (this.getIdent().equals(castOther.getIdent()));
	}
	
}
