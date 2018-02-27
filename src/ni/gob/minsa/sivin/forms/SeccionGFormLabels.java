package ni.gob.minsa.sivin.forms;

import ni.gob.minsa.sivin.R;
import ni.gob.minsa.sivin.SivinApplication;

import android.content.res.Resources;

/**
 * 
 */
public class SeccionGFormLabels {
	
	protected String labelG;
	protected String labelGHint;
	
	protected String msEnt;
	protected String codMsEnt;
	protected String codMsEntBc;
	protected String hbEnt;
	protected String msNin;
	protected String codMsNin;
	protected String codMsNinBc;
	protected String hbNin;
	protected String moEnt;
	protected String codMoEnt;
	protected String codMoEntBc;
	protected String pan;
	protected String sal;
	protected String marcaSal;
	protected String azucar;
	protected String marcaAzucar;
	
	

	
	public SeccionGFormLabels(){
		Resources res = SivinApplication.getContext().getResources();
		labelG = res.getString(R.string.labelG);
		labelGHint = res.getString(R.string.labelGHint);
		
		msEnt = res.getString(R.string.msEnt);
		codMsEnt = res.getString(R.string.codMsEnt);
		codMsEntBc = res.getString(R.string.codMsEntBc);
		hbEnt = res.getString(R.string.hbEnt);
		msNin = res.getString(R.string.msNin);
		codMsNin = res.getString(R.string.codMsNin);
		codMsNinBc = res.getString(R.string.codMsNinBc);
		hbNin = res.getString(R.string.hbNin);
		moEnt = res.getString(R.string.moEnt);
		codMoEnt = res.getString(R.string.codMoEnt);
		codMoEntBc = res.getString(R.string.codMoEntBc);
		pan = res.getString(R.string.pan);
		sal = res.getString(R.string.sal);
		marcaSal = res.getString(R.string.marcaSal);
		azucar = res.getString(R.string.azucar);
		marcaAzucar = res.getString(R.string.marcaAzucar);
		
		
	}




	public String getCodMsEntBc() {
		return codMsEntBc;
	}




	public void setCodMsEntBc(String codMsEntBc) {
		this.codMsEntBc = codMsEntBc;
	}




	public String getCodMsNinBc() {
		return codMsNinBc;
	}




	public void setCodMsNinBc(String codMsNinBc) {
		this.codMsNinBc = codMsNinBc;
	}




	public String getCodMoEntBc() {
		return codMoEntBc;
	}




	public void setCodMoEntBc(String codMoEntBc) {
		this.codMoEntBc = codMoEntBc;
	}




	public String getLabelG() {
		return labelG;
	}




	public void setLabelG(String labelG) {
		this.labelG = labelG;
	}




	public String getLabelGHint() {
		return labelGHint;
	}




	public void setLabelGHint(String labelGHint) {
		this.labelGHint = labelGHint;
	}




	public String getMsEnt() {
		return msEnt;
	}




	public void setMsEnt(String msEnt) {
		this.msEnt = msEnt;
	}




	public String getCodMsEnt() {
		return codMsEnt;
	}




	public void setCodMsEnt(String codMsEnt) {
		this.codMsEnt = codMsEnt;
	}




	public String getHbEnt() {
		return hbEnt;
	}




	public void setHbEnt(String hbEnt) {
		this.hbEnt = hbEnt;
	}




	public String getMsNin() {
		return msNin;
	}




	public void setMsNin(String msNin) {
		this.msNin = msNin;
	}




	public String getCodMsNin() {
		return codMsNin;
	}




	public void setCodMsNin(String codMsNin) {
		this.codMsNin = codMsNin;
	}




	public String getHbNin() {
		return hbNin;
	}




	public void setHbNin(String hbNin) {
		this.hbNin = hbNin;
	}




	public String getMoEnt() {
		return moEnt;
	}




	public void setMoEnt(String moEnt) {
		this.moEnt = moEnt;
	}




	public String getCodMoEnt() {
		return codMoEnt;
	}




	public void setCodMoEnt(String codMoEnt) {
		this.codMoEnt = codMoEnt;
	}




	public String getPan() {
		return pan;
	}




	public void setPan(String pan) {
		this.pan = pan;
	}




	public String getSal() {
		return sal;
	}




	public void setSal(String sal) {
		this.sal = sal;
	}




	public String getMarcaSal() {
		return marcaSal;
	}




	public void setMarcaSal(String marcaSal) {
		this.marcaSal = marcaSal;
	}




	public String getAzucar() {
		return azucar;
	}




	public void setAzucar(String azucar) {
		this.azucar = azucar;
	}




	public String getMarcaAzucar() {
		return marcaAzucar;
	}




	public void setMarcaAzucar(String marcaAzucar) {
		this.marcaAzucar = marcaAzucar;
	}
	
	
	

}
