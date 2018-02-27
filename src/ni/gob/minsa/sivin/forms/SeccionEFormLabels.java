package ni.gob.minsa.sivin.forms;

import ni.gob.minsa.sivin.R;
import ni.gob.minsa.sivin.SivinApplication;

import android.content.res.Resources;

/**
 * 
 */
public class SeccionEFormLabels {
	
	protected String labelE;
	protected String labelEHint;
	protected String nlactmatno;
	protected String nlactmatna;
	protected String nlactmat;
	protected String sexlmat;
	protected String emeseslmat;
	protected String fnaclmat;
	protected String pecho;
	protected String motNopecho;
	protected String motNopechoOtro;
	protected String dapecho;
	protected String tiempecho;
	protected String tiemmama;
	protected String tiemmamaMins;
	protected String dospechos;
	protected String vecespechodia;
	protected String vecespechonoche;
	protected String elmatexcund;
	protected String elmatexccant;
	protected String ediopechound;
	protected String ediopechocant;
	protected String combeb;
	protected String ealimund;
	protected String ealimcant;
	protected String bebeLiq;
	protected String reunionPeso;
	protected String quienReunionPeso;
	protected String quienReunionPesoOtro;
	protected String assitioReunionPeso;
	protected String nlactmatHint;
	protected String sexlmatHint;
	protected String emeseslmatHint;
	protected String fnaclmatHint;
	protected String pechoHint;
	protected String motNopechoHint;
	protected String motNopechoOtroHint;
	protected String dapechoHint;
	protected String tiempechoHint;
	protected String tiemmamaHint;
	protected String tiemmamaMinsHint;
	protected String dospechosHint;
	protected String vecespechodiaHint;
	protected String vecespechonocheHint;
	protected String elmatexcundHint;
	protected String elmatexccantHint;
	protected String ediopechoundHint;
	protected String ediopechocantHint;
	protected String combebHint;
	protected String ealimundHint;
	protected String ealimcantHint;
	protected String bebeLiqHint;
	protected String reunionPesoHint;
	protected String quienReunionPesoHint;
	protected String quienReunionPesoOtroHint;
	protected String assitioReunionPesoHint;


	
	public SeccionEFormLabels(){
		Resources res = SivinApplication.getContext().getResources();
		labelE = res.getString(R.string.labelE);
		labelEHint = res.getString(R.string.labelEHint);
		nlactmatna = res.getString(R.string.nlactmatna);
		nlactmatno = res.getString(R.string.nlactmatno);
		nlactmat = res.getString(R.string.nlactmat);
		sexlmat = res.getString(R.string.sexlmat);
		emeseslmat = res.getString(R.string.emeseslmat);
		fnaclmat = res.getString(R.string.fnaclmat);
		pecho = res.getString(R.string.pecho);
		motNopecho = res.getString(R.string.motNopecho);
		motNopechoOtro = res.getString(R.string.motNopechoOtro);
		dapecho = res.getString(R.string.dapecho);
		tiempecho = res.getString(R.string.tiempecho);
		tiemmama = res.getString(R.string.tiemmama);
		tiemmamaMins = res.getString(R.string.tiemmamaMins);
		dospechos = res.getString(R.string.dospechos);
		vecespechodia = res.getString(R.string.vecespechodia);
		vecespechonoche = res.getString(R.string.vecespechonoche);
		elmatexcund = res.getString(R.string.elmatexcund);
		elmatexccant = res.getString(R.string.elmatexccant);
		ediopechound = res.getString(R.string.ediopechound);
		ediopechocant = res.getString(R.string.ediopechocant);
		combeb = res.getString(R.string.combeb);
		ealimund = res.getString(R.string.ealimund);
		ealimcant = res.getString(R.string.ealimcant);
		bebeLiq = res.getString(R.string.bebeLiq);
		reunionPeso = res.getString(R.string.reunionPeso);
		quienReunionPeso = res.getString(R.string.quienReunionPeso);
		quienReunionPesoOtro = res.getString(R.string.quienReunionPesoOtro);
		assitioReunionPeso = res.getString(R.string.assitioReunionPeso);
		nlactmatHint = res.getString(R.string.nlactmatHint);
		sexlmatHint = res.getString(R.string.sexlmatHint);
		emeseslmatHint = res.getString(R.string.emeseslmatHint);
		fnaclmatHint = res.getString(R.string.fnaclmatHint);
		pechoHint = res.getString(R.string.pechoHint);
		motNopechoHint = res.getString(R.string.motNopechoHint);
		motNopechoOtroHint = res.getString(R.string.motNopechoOtroHint);
		dapechoHint = res.getString(R.string.dapechoHint);
		tiempechoHint = res.getString(R.string.tiempechoHint);
		tiemmamaHint = res.getString(R.string.tiemmamaHint);
		tiemmamaMinsHint = res.getString(R.string.tiemmamaMinsHint);
		dospechosHint = res.getString(R.string.dospechosHint);
		vecespechodiaHint = res.getString(R.string.vecespechodiaHint);
		vecespechonocheHint = res.getString(R.string.vecespechonocheHint);
		elmatexcundHint = res.getString(R.string.elmatexcundHint);
		elmatexccantHint = res.getString(R.string.elmatexccantHint);
		ediopechoundHint = res.getString(R.string.ediopechoundHint);
		ediopechocantHint = res.getString(R.string.ediopechocantHint);
		combebHint = res.getString(R.string.combebHint);
		ealimundHint = res.getString(R.string.ealimundHint);
		ealimcantHint = res.getString(R.string.ealimcantHint);
		bebeLiqHint = res.getString(R.string.bebeLiqHint);
		reunionPesoHint = res.getString(R.string.reunionPesoHint);
		quienReunionPesoHint = res.getString(R.string.quienReunionPesoHint);
		quienReunionPesoOtroHint = res.getString(R.string.quienReunionPesoOtroHint);
		assitioReunionPesoHint = res.getString(R.string.assitioReunionPesoHint);

	}



	public String getNlactmatna() {
		return nlactmatna;
	}



	public void setNlactmatna(String nlactmatna) {
		this.nlactmatna = nlactmatna;
	}



	public String getNlactmatno() {
		return nlactmatno;
	}



	public void setNlactmatno(String nlactmatno) {
		this.nlactmatno = nlactmatno;
	}



	public String getLabelE() {
		return labelE;
	}



	public void setLabelE(String labelE) {
		this.labelE = labelE;
	}



	public String getLabelEHint() {
		return labelEHint;
	}



	public void setLabelEHint(String labelEHint) {
		this.labelEHint = labelEHint;
	}



	public String getNlactmat() {
		return nlactmat;
	}



	public void setNlactmat(String nlactmat) {
		this.nlactmat = nlactmat;
	}



	public String getSexlmat() {
		return sexlmat;
	}



	public void setSexlmat(String sexlmat) {
		this.sexlmat = sexlmat;
	}



	public String getEmeseslmat() {
		return emeseslmat;
	}



	public void setEmeseslmat(String emeseslmat) {
		this.emeseslmat = emeseslmat;
	}



	public String getFnaclmat() {
		return fnaclmat;
	}



	public void setFnaclmat(String fnaclmat) {
		this.fnaclmat = fnaclmat;
	}



	public String getPecho() {
		return pecho;
	}



	public void setPecho(String pecho) {
		this.pecho = pecho;
	}



	public String getMotNopecho() {
		return motNopecho;
	}



	public void setMotNopecho(String motNopecho) {
		this.motNopecho = motNopecho;
	}



	public String getMotNopechoOtro() {
		return motNopechoOtro;
	}



	public void setMotNopechoOtro(String motNopechoOtro) {
		this.motNopechoOtro = motNopechoOtro;
	}



	public String getDapecho() {
		return dapecho;
	}



	public void setDapecho(String dapecho) {
		this.dapecho = dapecho;
	}



	public String getTiempecho() {
		return tiempecho;
	}



	public void setTiempecho(String tiempecho) {
		this.tiempecho = tiempecho;
	}



	public String getTiemmama() {
		return tiemmama;
	}



	public void setTiemmama(String tiemmama) {
		this.tiemmama = tiemmama;
	}



	public String getTiemmamaMins() {
		return tiemmamaMins;
	}



	public void setTiemmamaMins(String tiemmamaMins) {
		this.tiemmamaMins = tiemmamaMins;
	}



	public String getDospechos() {
		return dospechos;
	}



	public void setDospechos(String dospechos) {
		this.dospechos = dospechos;
	}



	public String getVecespechodia() {
		return vecespechodia;
	}



	public void setVecespechodia(String vecespechodia) {
		this.vecespechodia = vecespechodia;
	}



	public String getVecespechonoche() {
		return vecespechonoche;
	}



	public void setVecespechonoche(String vecespechonoche) {
		this.vecespechonoche = vecespechonoche;
	}



	public String getElmatexcund() {
		return elmatexcund;
	}



	public void setElmatexcund(String elmatexcund) {
		this.elmatexcund = elmatexcund;
	}



	public String getElmatexccant() {
		return elmatexccant;
	}



	public void setElmatexccant(String elmatexccant) {
		this.elmatexccant = elmatexccant;
	}



	public String getEdiopechound() {
		return ediopechound;
	}



	public void setEdiopechound(String ediopechound) {
		this.ediopechound = ediopechound;
	}



	public String getEdiopechocant() {
		return ediopechocant;
	}



	public void setEdiopechocant(String ediopechocant) {
		this.ediopechocant = ediopechocant;
	}



	public String getCombeb() {
		return combeb;
	}



	public void setCombeb(String combeb) {
		this.combeb = combeb;
	}



	public String getEalimund() {
		return ealimund;
	}



	public void setEalimund(String ealimund) {
		this.ealimund = ealimund;
	}



	public String getEalimcant() {
		return ealimcant;
	}



	public void setEalimcant(String ealimcant) {
		this.ealimcant = ealimcant;
	}



	public String getBebeLiq() {
		return bebeLiq;
	}



	public void setBebeLiq(String bebeLiq) {
		this.bebeLiq = bebeLiq;
	}



	public String getReunionPeso() {
		return reunionPeso;
	}



	public void setReunionPeso(String reunionPeso) {
		this.reunionPeso = reunionPeso;
	}



	public String getQuienReunionPeso() {
		return quienReunionPeso;
	}



	public void setQuienReunionPeso(String quienReunionPeso) {
		this.quienReunionPeso = quienReunionPeso;
	}



	public String getQuienReunionPesoOtro() {
		return quienReunionPesoOtro;
	}



	public void setQuienReunionPesoOtro(String quienReunionPesoOtro) {
		this.quienReunionPesoOtro = quienReunionPesoOtro;
	}



	public String getAssitioReunionPeso() {
		return assitioReunionPeso;
	}



	public void setAssitioReunionPeso(String assitioReunionPeso) {
		this.assitioReunionPeso = assitioReunionPeso;
	}



	public String getNlactmatHint() {
		return nlactmatHint;
	}



	public void setNlactmatHint(String nlactmatHint) {
		this.nlactmatHint = nlactmatHint;
	}



	public String getSexlmatHint() {
		return sexlmatHint;
	}



	public void setSexlmatHint(String sexlmatHint) {
		this.sexlmatHint = sexlmatHint;
	}



	public String getEmeseslmatHint() {
		return emeseslmatHint;
	}



	public void setEmeseslmatHint(String emeseslmatHint) {
		this.emeseslmatHint = emeseslmatHint;
	}



	public String getFnaclmatHint() {
		return fnaclmatHint;
	}



	public void setFnaclmatHint(String fnaclmatHint) {
		this.fnaclmatHint = fnaclmatHint;
	}



	public String getPechoHint() {
		return pechoHint;
	}



	public void setPechoHint(String pechoHint) {
		this.pechoHint = pechoHint;
	}



	public String getMotNopechoHint() {
		return motNopechoHint;
	}



	public void setMotNopechoHint(String motNopechoHint) {
		this.motNopechoHint = motNopechoHint;
	}



	public String getMotNopechoOtroHint() {
		return motNopechoOtroHint;
	}



	public void setMotNopechoOtroHint(String motNopechoOtroHint) {
		this.motNopechoOtroHint = motNopechoOtroHint;
	}



	public String getDapechoHint() {
		return dapechoHint;
	}



	public void setDapechoHint(String dapechoHint) {
		this.dapechoHint = dapechoHint;
	}



	public String getTiempechoHint() {
		return tiempechoHint;
	}



	public void setTiempechoHint(String tiempechoHint) {
		this.tiempechoHint = tiempechoHint;
	}



	public String getTiemmamaHint() {
		return tiemmamaHint;
	}



	public void setTiemmamaHint(String tiemmamaHint) {
		this.tiemmamaHint = tiemmamaHint;
	}



	public String getTiemmamaMinsHint() {
		return tiemmamaMinsHint;
	}



	public void setTiemmamaMinsHint(String tiemmamaMinsHint) {
		this.tiemmamaMinsHint = tiemmamaMinsHint;
	}



	public String getDospechosHint() {
		return dospechosHint;
	}



	public void setDospechosHint(String dospechosHint) {
		this.dospechosHint = dospechosHint;
	}



	public String getVecespechodiaHint() {
		return vecespechodiaHint;
	}



	public void setVecespechodiaHint(String vecespechodiaHint) {
		this.vecespechodiaHint = vecespechodiaHint;
	}



	public String getVecespechonocheHint() {
		return vecespechonocheHint;
	}



	public void setVecespechonocheHint(String vecespechonocheHint) {
		this.vecespechonocheHint = vecespechonocheHint;
	}



	public String getElmatexcundHint() {
		return elmatexcundHint;
	}



	public void setElmatexcundHint(String elmatexcundHint) {
		this.elmatexcundHint = elmatexcundHint;
	}



	public String getElmatexccantHint() {
		return elmatexccantHint;
	}



	public void setElmatexccantHint(String elmatexccantHint) {
		this.elmatexccantHint = elmatexccantHint;
	}



	public String getEdiopechoundHint() {
		return ediopechoundHint;
	}



	public void setEdiopechoundHint(String ediopechoundHint) {
		this.ediopechoundHint = ediopechoundHint;
	}



	public String getEdiopechocantHint() {
		return ediopechocantHint;
	}



	public void setEdiopechocantHint(String ediopechocantHint) {
		this.ediopechocantHint = ediopechocantHint;
	}



	public String getCombebHint() {
		return combebHint;
	}



	public void setCombebHint(String combebHint) {
		this.combebHint = combebHint;
	}



	public String getEalimundHint() {
		return ealimundHint;
	}



	public void setEalimundHint(String ealimundHint) {
		this.ealimundHint = ealimundHint;
	}



	public String getEalimcantHint() {
		return ealimcantHint;
	}



	public void setEalimcantHint(String ealimcantHint) {
		this.ealimcantHint = ealimcantHint;
	}



	public String getBebeLiqHint() {
		return bebeLiqHint;
	}



	public void setBebeLiqHint(String bebeLiqHint) {
		this.bebeLiqHint = bebeLiqHint;
	}



	public String getReunionPesoHint() {
		return reunionPesoHint;
	}



	public void setReunionPesoHint(String reunionPesoHint) {
		this.reunionPesoHint = reunionPesoHint;
	}



	public String getQuienReunionPesoHint() {
		return quienReunionPesoHint;
	}



	public void setQuienReunionPesoHint(String quienReunionPesoHint) {
		this.quienReunionPesoHint = quienReunionPesoHint;
	}



	public String getQuienReunionPesoOtroHint() {
		return quienReunionPesoOtroHint;
	}



	public void setQuienReunionPesoOtroHint(String quienReunionPesoOtroHint) {
		this.quienReunionPesoOtroHint = quienReunionPesoOtroHint;
	}



	public String getAssitioReunionPesoHint() {
		return assitioReunionPesoHint;
	}



	public void setAssitioReunionPesoHint(String assitioReunionPesoHint) {
		this.assitioReunionPesoHint = assitioReunionPesoHint;
	}

	

}
