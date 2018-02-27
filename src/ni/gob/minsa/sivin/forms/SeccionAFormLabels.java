package ni.gob.minsa.sivin.forms;

import ni.gob.minsa.sivin.R;
import ni.gob.minsa.sivin.SivinApplication;
import android.content.res.Resources;

/**
 * 
 */
public class SeccionAFormLabels {
	
	protected String agua;
	protected String aguaHint;
	protected String oagua;
	protected String oaguaHint;
	protected String cuartos;
	protected String cuartosHint;
	protected String lugNecesidades;
	protected String lugNecesidadesHint;
	protected String olugNecesidades;
	protected String olugNecesidadesHint;
	protected String usaCocinar;
	protected String usaCocinarHint;
	protected String ousaCocinar;
	protected String ousaCocinarHint;
	protected String articulos;
	protected String articulosHint;
	protected String oarticulos;
	protected String oarticulosHint;

	
	public SeccionAFormLabels(){
		Resources res = SivinApplication.getContext().getResources();
		agua = res.getString(R.string.agua);
		aguaHint = res.getString(R.string.aguaHint);
		oagua = res.getString(R.string.oagua);
		oaguaHint = res.getString(R.string.oaguaHint);
		cuartos = res.getString(R.string.cuartos);
		cuartosHint = res.getString(R.string.cuartosHint);
		lugNecesidades = res.getString(R.string.lugNecesidades);
		lugNecesidadesHint = res.getString(R.string.lugNecesidadesHint);
		olugNecesidades = res.getString(R.string.olugNecesidades);
		olugNecesidadesHint = res.getString(R.string.olugNecesidadesHint);
		usaCocinar = res.getString(R.string.usaCocinar);
		usaCocinarHint = res.getString(R.string.usaCocinarHint);
		ousaCocinar = res.getString(R.string.ousaCocinar);
		ousaCocinarHint = res.getString(R.string.ousaCocinarHint);
		articulos = res.getString(R.string.articulos);
		articulosHint = res.getString(R.string.articulosHint);		 
		oarticulos = res.getString(R.string.oarticulos);
		oarticulosHint = res.getString(R.string.oarticulosHint);	 
	}


	public String getAgua() {
		return agua;
	}


	public void setAgua(String agua) {
		this.agua = agua;
	}


	public String getAguaHint() {
		return aguaHint;
	}


	public void setAguaHint(String aguaHint) {
		this.aguaHint = aguaHint;
	}


	public String getOagua() {
		return oagua;
	}


	public void setOagua(String oagua) {
		this.oagua = oagua;
	}


	public String getOaguaHint() {
		return oaguaHint;
	}


	public void setOaguaHint(String oaguaHint) {
		this.oaguaHint = oaguaHint;
	}


	public String getCuartos() {
		return cuartos;
	}


	public void setCuartos(String cuartos) {
		this.cuartos = cuartos;
	}


	public String getCuartosHint() {
		return cuartosHint;
	}


	public void setCuartosHint(String cuartosHint) {
		this.cuartosHint = cuartosHint;
	}


	public String getLugNecesidades() {
		return lugNecesidades;
	}


	public void setLugNecesidades(String lugNecesidades) {
		this.lugNecesidades = lugNecesidades;
	}


	public String getLugNecesidadesHint() {
		return lugNecesidadesHint;
	}


	public void setLugNecesidadesHint(String lugNecesidadesHint) {
		this.lugNecesidadesHint = lugNecesidadesHint;
	}


	public String getOlugNecesidades() {
		return olugNecesidades;
	}


	public void setOlugNecesidades(String olugNecesidades) {
		this.olugNecesidades = olugNecesidades;
	}


	public String getOlugNecesidadesHint() {
		return olugNecesidadesHint;
	}


	public void setOlugNecesidadesHint(String olugNecesidadesHint) {
		this.olugNecesidadesHint = olugNecesidadesHint;
	}


	public String getUsaCocinar() {
		return usaCocinar;
	}


	public void setUsaCocinar(String usaCocinar) {
		this.usaCocinar = usaCocinar;
	}


	public String getUsaCocinarHint() {
		return usaCocinarHint;
	}


	public void setUsaCocinarHint(String usaCocinarHint) {
		this.usaCocinarHint = usaCocinarHint;
	}


	public String getOusaCocinar() {
		return ousaCocinar;
	}


	public void setOusaCocinar(String ousaCocinar) {
		this.ousaCocinar = ousaCocinar;
	}


	public String getOusaCocinarHint() {
		return ousaCocinarHint;
	}


	public void setOusaCocinarHint(String ousaCocinarHint) {
		this.ousaCocinarHint = ousaCocinarHint;
	}


	public String getArticulos() {
		return articulos;
	}


	public void setArticulos(String articulos) {
		this.articulos = articulos;
	}


	public String getArticulosHint() {
		return articulosHint;
	}


	public void setArticulosHint(String articulosHint) {
		this.articulosHint = articulosHint;
	}


	public String getOarticulos() {
		return oarticulos;
	}


	public void setOarticulos(String oarticulos) {
		this.oarticulos = oarticulos;
	}


	public String getOarticulosHint() {
		return oarticulosHint;
	}


	public void setOarticulosHint(String oarticulosHint) {
		this.oarticulosHint = oarticulosHint;
	}
	
	
	
}
