package ni.gob.minsa.sivin.forms;

import ni.gob.minsa.sivin.R;
import ni.gob.minsa.sivin.SivinApplication;
import android.content.res.Resources;

/**
 * 
 */
public class EncuestaIdentificacionFormLabels {
	
	protected String introMessage;
	protected String introMessageHint;
	protected String fechaEntrevista;
	protected String fechaEntrevistaHint;
	protected String encNum;
	protected String encuestador;
	protected String encuestadorHint;
	protected String supervisor;
	protected String supervisorHint;
	protected String jefeFamilia;
	protected String jefeFamiliaHint;
	protected String sexJefeFamilia;
	protected String sexJefeFamiliaHint;
	protected String numPersonas;
	protected String numPersonasHint;
	
	public EncuestaIdentificacionFormLabels(){
		Resources res = SivinApplication.getContext().getResources();
		introMessage = res.getString(R.string.intro);
		introMessageHint = res.getString(R.string.intro_message);
		encNum = res.getString(R.string.encNum);
		fechaEntrevista = res.getString(R.string.fechaEntrevista);
		fechaEntrevistaHint = res.getString(R.string.fechaEntrevistaHint);
		encuestador = res.getString(R.string.encuestador);
		encuestadorHint = res.getString(R.string.encuestadorHint);
		supervisor = res.getString(R.string.supervisor);
		supervisorHint = res.getString(R.string.supervisorHint);
		jefeFamilia = res.getString(R.string.jefeFamilia);
		jefeFamiliaHint = res.getString(R.string.jefeFamiliaHint);
		sexJefeFamilia = res.getString(R.string.sexJefeFamilia);
		sexJefeFamiliaHint = res.getString(R.string.sexJefeFamiliaHint);
		numPersonas = res.getString(R.string.numPersonas);
		numPersonasHint = res.getString(R.string.numPersonasHint);
	}
	
	
	public String getIntroMessage() {
		return introMessage;
	}

	public void setIntroMessage(String introMessage) {
		this.introMessage = introMessage;
	}
	

	public String getIntroMessageHint() {
		return introMessageHint;
	}
	
	public String getEncNum() {
		return encNum;
	}

	public void setEncNum(String encNum) {
		this.encNum = encNum;
	}

	public String getFechaEntrevista() {
		return fechaEntrevista;
	}

	public void setFechaEntrevista(String fechaEntrevista) {
		this.fechaEntrevista = fechaEntrevista;
	}

	public String getFechaEntrevistaHint() {
		return fechaEntrevistaHint;
	}

	public void setFechaEntrevistaHint(String fechaEntrevistaHint) {
		this.fechaEntrevistaHint = fechaEntrevistaHint;
	}

	public String getEncuestador() {
		return encuestador;
	}

	public void setEncuestador(String encuestador) {
		this.encuestador = encuestador;
	}

	public String getEncuestadorHint() {
		return encuestadorHint;
	}

	public void setEncuestadorHint(String encuestadorHint) {
		this.encuestadorHint = encuestadorHint;
	}

	public String getSupervisor() {
		return supervisor;
	}

	public void setSupervisor(String supervisor) {
		this.supervisor = supervisor;
	}

	public String getSupervisorHint() {
		return supervisorHint;
	}

	public void setSupervisorHint(String supervisorHint) {
		this.supervisorHint = supervisorHint;
	}

	public void setIntroMessageHint(String introMessageHint) {
		this.introMessageHint = introMessageHint;
	}

	public String getJefeFamilia() {
		return jefeFamilia;
	}

	public void setJefeFamilia(String jefeFamilia) {
		this.jefeFamilia = jefeFamilia;
	}

	public String getJefeFamiliaHint() {
		return jefeFamiliaHint;
	}

	public void setJefeFamiliaHint(String jefeFamiliaHint) {
		this.jefeFamiliaHint = jefeFamiliaHint;
	}

	public String getSexJefeFamilia() {
		return sexJefeFamilia;
	}

	public void setSexJefeFamilia(String sexJefeFamilia) {
		this.sexJefeFamilia = sexJefeFamilia;
	}

	public String getSexJefeFamiliaHint() {
		return sexJefeFamiliaHint;
	}

	public void setSexJefeFamiliaHint(String sexJefeFamiliaHint) {
		this.sexJefeFamiliaHint = sexJefeFamiliaHint;
	}

	public String getNumPersonas() {
		return numPersonas;
	}

	public void setNumPersonas(String numPersonas) {
		this.numPersonas = numPersonas;
	}

	public String getNumPersonasHint() {
		return numPersonasHint;
	}

	public void setNumPersonasHint(String numPersonasHint) {
		this.numPersonasHint = numPersonasHint;
	}
	
	
}
