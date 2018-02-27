package ni.gob.minsa.sivin.domain;



/**
 * Encuestador es la clase que representa la persona que realiza una encuesta.
 * 
 * Encuestador incluye información básica como:
 * 
 * <ul>
 * <li>Codigo 
 * <li>Nombre
 * </ul>
 * 
 *  
 * @author      William Avilés
 * @version     1.0
 * @since       1.0
 */
public class Encuestador extends BaseMetaData{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String ident;
	private String codigo;
	private String nombre;
	
	
	public Encuestador() {
		super();
	}


	public Encuestador(String ident, String codigo, String nombre) {
		super();
		this.ident = ident;
		this.codigo = codigo;
		this.nombre = nombre;
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

	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public String toString(){
		return this.getNombre();
	}
	
	@Override
	public boolean equals(Object other) {
		
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof Encuestador))
			return false;
		
		Encuestador castOther = (Encuestador) other;

		return (this.getIdent().equals(castOther.getIdent()));
	}
	

}
