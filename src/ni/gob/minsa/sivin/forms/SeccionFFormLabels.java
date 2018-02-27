package ni.gob.minsa.sivin.forms;

import ni.gob.minsa.sivin.R;
import ni.gob.minsa.sivin.SivinApplication;

import android.content.res.Resources;

/**
 * 
 */
public class SeccionFFormLabels {
	
	protected String labelF;
	protected String labelFHint;
	protected String pesoTallaEnt;
	protected String pesoEnt1;
	protected String pesoEnt2;
	protected String tallaEnt1;
	protected String tallaEnt2;
	protected String pesoTallaNin;
	protected String pesoTallaNinHint;
	protected String pesoNin1;
	protected String pesoNin2;
	protected String longNin1;
	protected String longNin2;
	protected String tallaNin1;
	protected String tallaNin2;
	
	

	
	public SeccionFFormLabels(){
		Resources res = SivinApplication.getContext().getResources();
		labelF = res.getString(R.string.labelF);
		labelFHint = res.getString(R.string.labelFHint);
		pesoTallaEnt = res.getString(R.string.pesoTallaEnt);
		pesoEnt1 = res.getString(R.string.pesoEnt1);
		pesoEnt2 = res.getString(R.string.pesoEnt2);
		tallaEnt1 = res.getString(R.string.tallaEnt1);
		tallaEnt2 = res.getString(R.string.tallaEnt2);
		pesoTallaNin = res.getString(R.string.pesoTallaNin);
		pesoTallaNinHint = res.getString(R.string.pesoTallaNinHint);
		pesoNin1 = res.getString(R.string.pesoNin1);
		pesoNin2 = res.getString(R.string.pesoNin2);
		longNin1 = res.getString(R.string.longNin1);
		longNin2 = res.getString(R.string.longNin2);
		tallaNin1 = res.getString(R.string.tallaNin1);
		tallaNin2 = res.getString(R.string.tallaNin2);
		
	}




	public String getLabelF() {
		return labelF;
	}




	public void setLabelF(String labelF) {
		this.labelF = labelF;
	}




	public String getLabelFHint() {
		return labelFHint;
	}




	public void setLabelFHint(String labelFHint) {
		this.labelFHint = labelFHint;
	}




	public String getPesoEnt1() {
		return pesoEnt1;
	}



	public void setPesoEnt1(String pesoEnt1) {
		this.pesoEnt1 = pesoEnt1;
	}



	public String getPesoEnt2() {
		return pesoEnt2;
	}



	public void setPesoEnt2(String pesoEnt2) {
		this.pesoEnt2 = pesoEnt2;
	}



	public String getTallaEnt1() {
		return tallaEnt1;
	}



	public void setTallaEnt1(String tallaEnt1) {
		this.tallaEnt1 = tallaEnt1;
	}



	public String getTallaEnt2() {
		return tallaEnt2;
	}



	public void setTallaEnt2(String tallaEnt2) {
		this.tallaEnt2 = tallaEnt2;
	}



	public String getPesoNin1() {
		return pesoNin1;
	}



	public void setPesoNin1(String pesoNin1) {
		this.pesoNin1 = pesoNin1;
	}



	public String getPesoNin2() {
		return pesoNin2;
	}



	public void setPesoNin2(String pesoNin2) {
		this.pesoNin2 = pesoNin2;
	}



	public String getLongNin1() {
		return longNin1;
	}



	public void setLongNin1(String longNin1) {
		this.longNin1 = longNin1;
	}



	public String getLongNin2() {
		return longNin2;
	}



	public void setLongNin2(String longNin2) {
		this.longNin2 = longNin2;
	}



	public String getTallaNin1() {
		return tallaNin1;
	}



	public void setTallaNin1(String tallaNin1) {
		this.tallaNin1 = tallaNin1;
	}



	public String getTallaNin2() {
		return tallaNin2;
	}



	public void setTallaNin2(String tallaNin2) {
		this.tallaNin2 = tallaNin2;
	}




	public String getPesoTallaEnt() {
		return pesoTallaEnt;
	}




	public void setPesoTallaEnt(String pesoTallaEnt) {
		this.pesoTallaEnt = pesoTallaEnt;
	}




	public String getPesoTallaNin() {
		return pesoTallaNin;
	}




	public void setPesoTallaNin(String pesoTallaNin) {
		this.pesoTallaNin = pesoTallaNin;
	}




	public String getPesoTallaNinHint() {
		return pesoTallaNinHint;
	}




	public void setPesoTallaNinHint(String pesoTallaNinHint) {
		this.pesoTallaNinHint = pesoTallaNinHint;
	}
	
	

}
