package ni.gob.minsa.sivin.helpers;

import java.util.Date;

import android.content.ContentValues;
import android.database.Cursor;
import ni.gob.minsa.sivin.domain.Encuesta;
import ni.gob.minsa.sivin.utils.MainDBConstants;

public class EncuestaHelper {
	
	public static ContentValues crearEncuestaContentValues(Encuesta encuesta){
		ContentValues cv = new ContentValues();
		cv.put(MainDBConstants.ident, encuesta.getIdent());
		cv.put(MainDBConstants.codigo, encuesta.getCodigo());
		cv.put(MainDBConstants.segmento, encuesta.getSegmento().getIdent());
		cv.put(MainDBConstants.numEncuesta, encuesta.getNumEncuesta());
		if (encuesta.getFechaEntrevista() != null) cv.put(MainDBConstants.fechaEntrevista, encuesta.getFechaEntrevista().getTime());
		cv.put(MainDBConstants.latitud, encuesta.getLatitud());
		cv.put(MainDBConstants.longitud, encuesta.getLongitud());
		cv.put(MainDBConstants.jefeFamilia, encuesta.getJefeFamilia());
		cv.put(MainDBConstants.sexJefeFamilia, encuesta.getSexJefeFamilia());
		cv.put(MainDBConstants.numPersonas, encuesta.getNumPersonas());
		if(encuesta.getEncuestador()!=null) cv.put(MainDBConstants.encuestador, encuesta.getEncuestador().getIdent());
		if(encuesta.getSupervisor() !=null) cv.put(MainDBConstants.supervisor, encuesta.getSupervisor().getIdent());
		//SECCION A
		cv.put(MainDBConstants.agua, encuesta.getAgua());
		cv.put(MainDBConstants.oagua, encuesta.getOagua());
		cv.put(MainDBConstants.cuartos, encuesta.getCuartos());
		cv.put(MainDBConstants.lugNecesidades, encuesta.getLugNecesidades());
		cv.put(MainDBConstants.olugNecesidades, encuesta.getOlugNecesidades());
		cv.put(MainDBConstants.usaCocinar, encuesta.getUsaCocinar());
		cv.put(MainDBConstants.ousaCocinar, encuesta.getOusaCocinar());
		cv.put(MainDBConstants.articulos, encuesta.getArticulos());
		cv.put(MainDBConstants.oarticulos, encuesta.getOarticulos());
		//SECCION B
		cv.put(MainDBConstants.conoceFNac, encuesta.getConoceFNac());
		if (encuesta.getFnacEnt() != null) {
			cv.put(MainDBConstants.fnacEnt, encuesta.getFnacEnt().getTime());
		}
		else {
			cv.put(MainDBConstants.fnacEnt, 0);
		}
		cv.put(MainDBConstants.edadEnt, encuesta.getEdadEnt());
		cv.put(MainDBConstants.leerEnt, encuesta.getLeerEnt());
		cv.put(MainDBConstants.escribirEnt, encuesta.getEscribirEnt());
		cv.put(MainDBConstants.leeescEnt, encuesta.getLeeescEnt());
		cv.put(MainDBConstants.nivelEnt, encuesta.getNivelEnt());
		cv.put(MainDBConstants.gradoEnt, encuesta.getGradoEnt());
		cv.put(MainDBConstants.entRealizada, encuesta.getEntRealizada());
		cv.put(MainDBConstants.entEmb, encuesta.getEntEmb());
		cv.put(MainDBConstants.entEmbUnico, encuesta.getEntEmbUnico());
		cv.put(MainDBConstants.entDioluz, encuesta.getEntDioluz());
		cv.put(MainDBConstants.entHieacfol, encuesta.getEntHieacfol());
		cv.put(MainDBConstants.entMeseshierro, encuesta.getEntMeseshierro());
		cv.put(MainDBConstants.entRecHierro, encuesta.getEntRecHierro());
		cv.put(MainDBConstants.entORecHierro, encuesta.getEntORecHierro());
		cv.put(MainDBConstants.tiemHierroUnd, encuesta.getTiemHierroUnd());
		cv.put(MainDBConstants.tiemHierro, encuesta.getTiemHierro());
		cv.put(MainDBConstants.dondeHierro, encuesta.getDondeHierro());
		cv.put(MainDBConstants.dondeHierroBesp, encuesta.getDondeHierroBesp());
		cv.put(MainDBConstants.dondeHierroFesp, encuesta.getDondeHierroFesp());
		cv.put(MainDBConstants.tomadoHierro, encuesta.getTomadoHierro());
		cv.put(MainDBConstants.vita, encuesta.getVita());
		cv.put(MainDBConstants.tiempVita, encuesta.getTiempVita());
		cv.put(MainDBConstants.evitaEmb, encuesta.getEvitaEmb());
		cv.put(MainDBConstants.dondeAntic, encuesta.getDondeAntic());
		cv.put(MainDBConstants.nuevoEmb, encuesta.getNuevoEmb());
		cv.put(MainDBConstants.hierro, encuesta.getHierro());
		cv.put(MainDBConstants.dHierro, encuesta.getdHierro());
		//SECCION C
		cv.put(MainDBConstants.numNinos, encuesta.getNumNinos());
		cv.put(MainDBConstants.n1, encuesta.getN1());
		cv.put(MainDBConstants.n2, encuesta.getN2());
		cv.put(MainDBConstants.n3, encuesta.getN3());
		cv.put(MainDBConstants.n4, encuesta.getN4());
		cv.put(MainDBConstants.n5, encuesta.getN5());
		cv.put(MainDBConstants.n6, encuesta.getN6());
		cv.put(MainDBConstants.e1, encuesta.getE1());
		cv.put(MainDBConstants.e2, encuesta.getE2());
		cv.put(MainDBConstants.e3, encuesta.getE3());
		cv.put(MainDBConstants.e4, encuesta.getE4());
		cv.put(MainDBConstants.e5, encuesta.getE5());
		cv.put(MainDBConstants.e6, encuesta.getE6());
		cv.put(MainDBConstants.nselec, encuesta.getNselec());
		cv.put(MainDBConstants.nomselec, encuesta.getNomselec());
		if (encuesta.getFnacselec() != null) {
			cv.put(MainDBConstants.fnacselec, encuesta.getFnacselec().getTime());
		}
		else {
			cv.put(MainDBConstants.fnacselec, 0);
		}
		cv.put(MainDBConstants.eselect, encuesta.getEselect());
		cv.put(MainDBConstants.sexselec, encuesta.getSexselec());
		cv.put(MainDBConstants.calim, encuesta.getCalim());
		cv.put(MainDBConstants.vcome, encuesta.getVcome());
		cv.put(MainDBConstants.consalim, encuesta.getConsalim());
		cv.put(MainDBConstants.calimenf, encuesta.getCalimenf());
		cv.put(MainDBConstants.clecheenf, encuesta.getClecheenf());
		//SECCION D
		cv.put(MainDBConstants.hierron, encuesta.getHierron());
		cv.put(MainDBConstants.thierround, encuesta.getThierround());
		cv.put(MainDBConstants.thierrocant, encuesta.getThierrocant());
		cv.put(MainDBConstants.fhierro, encuesta.getFhierro());
		cv.put(MainDBConstants.ghierro, encuesta.getGhierro());
		cv.put(MainDBConstants.donhierro, encuesta.getDonhierro());
		cv.put(MainDBConstants.donhierrobesp, encuesta.getDonhierrobesp());
		cv.put(MainDBConstants.donhierrofesp, encuesta.getDonhierrofesp());
		cv.put(MainDBConstants.fuhierro, encuesta.getFuhierro());
		if (encuesta.getFuhierror() != null) {
			cv.put(MainDBConstants.fuhierror, encuesta.getFuhierror().getTime());
		}
		else {
			cv.put(MainDBConstants.fuhierror, 0);
		}
		if (encuesta.getFauhierror() != null) {
			cv.put(MainDBConstants.fauhierror, encuesta.getFauhierror().getTime());
		}
		else {
			cv.put(MainDBConstants.fauhierror, 0);
		}
		cv.put(MainDBConstants.nvita, encuesta.getNvita());
		cv.put(MainDBConstants.ncvita, encuesta.getNcvita());
		cv.put(MainDBConstants.tvitaund, encuesta.getTvitaund());
		cv.put(MainDBConstants.tvitacant, encuesta.getTvitacant());
		cv.put(MainDBConstants.ndvita, encuesta.getNdvita());
		cv.put(MainDBConstants.ndvitao, encuesta.getNdvitao());
		cv.put(MainDBConstants.tdvita, encuesta.getTdvita());
		if (encuesta.getFuvita() != null) {
			cv.put(MainDBConstants.fuvita, encuesta.getFuvita().getTime());
		}
		else {
			cv.put(MainDBConstants.fuvita, 0);
		}
		if (encuesta.getFauvita() != null) {
			cv.put(MainDBConstants.fauvita, encuesta.getFauvita().getTime());
		}
		else {
			cv.put(MainDBConstants.fauvita, 0);
		}
		cv.put(MainDBConstants.ncnut, encuesta.getNcnut());
		cv.put(MainDBConstants.ncnutund, encuesta.getNcnutund());
		cv.put(MainDBConstants.ncnutcant, encuesta.getNcnutcant());
		cv.put(MainDBConstants.doncnut, encuesta.getDoncnut());
		cv.put(MainDBConstants.doncnutfotro, encuesta.getDoncnutfotro());
		cv.put(MainDBConstants.parasit, encuesta.getParasit());
		cv.put(MainDBConstants.cvparas, encuesta.getCvparas());
		cv.put(MainDBConstants.mParasitario, encuesta.getmParasitario());
		cv.put(MainDBConstants.otroMpEsp, encuesta.getOtroMpEsp());
		cv.put(MainDBConstants.donMp, encuesta.getDonMp());
		cv.put(MainDBConstants.otroDonMp, encuesta.getOtroDonMp());
		cv.put(MainDBConstants.evitarParasito, encuesta.getEvitarParasito());
		cv.put(MainDBConstants.oEvitarParasito, encuesta.getoEvitarParasito());
		//SECCION E
		cv.put(MainDBConstants.nlactmat, encuesta.getNlactmat());
		cv.put(MainDBConstants.sexlmat, encuesta.getSexlmat());
		cv.put(MainDBConstants.emeseslmat, encuesta.getEmeseslmat());
		if (encuesta.getFnaclmat() != null) {
			cv.put(MainDBConstants.fnaclmat, encuesta.getFnaclmat().getTime());
		}
		else {
			cv.put(MainDBConstants.fnaclmat, 0);
		}
		cv.put(MainDBConstants.pecho, encuesta.getPecho());
		cv.put(MainDBConstants.motNopecho, encuesta.getMotNopecho());
		cv.put(MainDBConstants.motNopechoOtro, encuesta.getMotNopechoOtro());
		cv.put(MainDBConstants.dapecho, encuesta.getDapecho());
		cv.put(MainDBConstants.tiempecho, encuesta.getTiempecho());
		cv.put(MainDBConstants.tiemmama, encuesta.getTiemmama());
		cv.put(MainDBConstants.tiemmamaMins, encuesta.getTiemmamaMins());
		cv.put(MainDBConstants.dospechos, encuesta.getDospechos());
		cv.put(MainDBConstants.vecespechodia, encuesta.getVecespechodia());
		cv.put(MainDBConstants.vecespechonoche, encuesta.getVecespechonoche());
		cv.put(MainDBConstants.elmatexcund, encuesta.getElmatexcund());
		cv.put(MainDBConstants.elmatexccant, encuesta.getElmatexccant());
		cv.put(MainDBConstants.ediopechound, encuesta.getEdiopechound());
		cv.put(MainDBConstants.ediopechocant, encuesta.getEdiopechocant());
		cv.put(MainDBConstants.combeb, encuesta.getCombeb());
		cv.put(MainDBConstants.ealimund, encuesta.getEalimund());
		cv.put(MainDBConstants.ealimcant, encuesta.getEalimcant());
		cv.put(MainDBConstants.bebeLiq, encuesta.getBebeLiq());
		cv.put(MainDBConstants.reunionPeso, encuesta.getReunionPeso());
		cv.put(MainDBConstants.quienReunionPeso, encuesta.getQuienReunionPeso());
		cv.put(MainDBConstants.quienReunionPesoOtro, encuesta.getQuienReunionPesoOtro());
		cv.put(MainDBConstants.assitioReunionPeso, encuesta.getAssitioReunionPeso());
		//SECCION F
		cv.put(MainDBConstants.pesoTallaEnt, encuesta.getPesoTallaEnt());
		cv.put(MainDBConstants.pesoEnt1, encuesta.getPesoEnt1());
		cv.put(MainDBConstants.pesoEnt2, encuesta.getPesoEnt2());
		cv.put(MainDBConstants.tallaEnt1, encuesta.getTallaEnt1());
		cv.put(MainDBConstants.tallaEnt2, encuesta.getTallaEnt2());
		cv.put(MainDBConstants.pesoTallaNin, encuesta.getPesoTallaNin());
		cv.put(MainDBConstants.pesoNin1, encuesta.getPesoNin1());
		cv.put(MainDBConstants.pesoNin2, encuesta.getPesoNin2());
		cv.put(MainDBConstants.longNin1, encuesta.getLongNin1());
		cv.put(MainDBConstants.longNin2, encuesta.getLongNin2());
		cv.put(MainDBConstants.tallaNin1, encuesta.getTallaNin1());
		cv.put(MainDBConstants.tallaNin2, encuesta.getTallaNin2());
		//SECCION G
		cv.put(MainDBConstants.msEnt, encuesta.getMsEnt());
		cv.put(MainDBConstants.codMsEnt, encuesta.getCodMsEnt());
		cv.put(MainDBConstants.hbEnt, encuesta.getHbEnt());
		cv.put(MainDBConstants.msNin, encuesta.getMsNin());
		cv.put(MainDBConstants.codMsNin, encuesta.getCodMsNin());
		cv.put(MainDBConstants.hbNin, encuesta.getHbNin());
		cv.put(MainDBConstants.moEnt, encuesta.getMoEnt());
		cv.put(MainDBConstants.codMoEnt, encuesta.getCodMoEnt());
		cv.put(MainDBConstants.pan, encuesta.getPan());
		cv.put(MainDBConstants.sal, encuesta.getSal());
		cv.put(MainDBConstants.marcaSal, encuesta.getMarcaSal());
		cv.put(MainDBConstants.azucar, encuesta.getAzucar());
		cv.put(MainDBConstants.marcaAzucar, encuesta.getMarcaAzucar());
		//SECCION H
		cv.put(MainDBConstants.patConsAzuc, encuesta.getPatConsAzuc());
		cv.put(MainDBConstants.patConsAzucFrec, encuesta.getPatConsAzucFrec());
		cv.put(MainDBConstants.patConsSal, encuesta.getPatConsSal());
		cv.put(MainDBConstants.patConsSalFrec, encuesta.getPatConsSalFrec());
		cv.put(MainDBConstants.patConsArroz, encuesta.getPatConsArroz());
		cv.put(MainDBConstants.patConsArrozFrec, encuesta.getPatConsArrozFrec());
		cv.put(MainDBConstants.patConsAcei, encuesta.getPatConsAcei());
		cv.put(MainDBConstants.patConsAceiFrec, encuesta.getPatConsAceiFrec());
		cv.put(MainDBConstants.patConsFri, encuesta.getPatConsFri());
		cv.put(MainDBConstants.patConsFriFrec, encuesta.getPatConsFriFrec());
		cv.put(MainDBConstants.patConsCebo, encuesta.getPatConsCebo());
		cv.put(MainDBConstants.patConsCeboFrec, encuesta.getPatConsCeboFrec());
		cv.put(MainDBConstants.patConsChi, encuesta.getPatConsChi());
		cv.put(MainDBConstants.patConsChiFrec, encuesta.getPatConsChiFrec());
		cv.put(MainDBConstants.patConsQue, encuesta.getPatConsQue());
		cv.put(MainDBConstants.patConsQueFrec, encuesta.getPatConsQueFrec());
		cv.put(MainDBConstants.patConsCafe, encuesta.getPatConsCafe());
		cv.put(MainDBConstants.patConsCafeFrec, encuesta.getPatConsCafeFrec());
		cv.put(MainDBConstants.patConsTor, encuesta.getPatConsTor());
		cv.put(MainDBConstants.patConsTorFrec, encuesta.getPatConsTorFrec());
		cv.put(MainDBConstants.patConsCar, encuesta.getPatConsCar());
		cv.put(MainDBConstants.patConsCarFrec, encuesta.getPatConsCarFrec());
		cv.put(MainDBConstants.patConsHue, encuesta.getPatConsHue());
		cv.put(MainDBConstants.patConsHueFrec, encuesta.getPatConsHueFrec());
		cv.put(MainDBConstants.patConsPan, encuesta.getPatConsPan());
		cv.put(MainDBConstants.patConsPanFrec, encuesta.getPatConsPanFrec());
		cv.put(MainDBConstants.patConsBan, encuesta.getPatConsBan());
		cv.put(MainDBConstants.patConsBanFrec, encuesta.getPatConsBanFrec());
		cv.put(MainDBConstants.patConsPanDul, encuesta.getPatConsPanDul());
		cv.put(MainDBConstants.patConsPanDulFrec, encuesta.getPatConsPanDulFrec());
		cv.put(MainDBConstants.patConsPla, encuesta.getPatConsPla());
		cv.put(MainDBConstants.patConsPlaFrec, encuesta.getPatConsPlaFrec());
		cv.put(MainDBConstants.patConsPapa, encuesta.getPatConsPapa());
		cv.put(MainDBConstants.patConsPapaFrec, encuesta.getPatConsPapaFrec());
		cv.put(MainDBConstants.patConsLec, encuesta.getPatConsLec());
		cv.put(MainDBConstants.patConsLecFrec, encuesta.getPatConsLecFrec());
		cv.put(MainDBConstants.patConsSalTom, encuesta.getPatConsSalTom());
		cv.put(MainDBConstants.patConsSalTomFrec, encuesta.getPatConsSalTomFrec());
		cv.put(MainDBConstants.patConsGas, encuesta.getPatConsGas());
		cv.put(MainDBConstants.patConsGasFrec, encuesta.getPatConsGasFrec());
		cv.put(MainDBConstants.patConsCarRes, encuesta.getPatConsCarRes());
		cv.put(MainDBConstants.patConsCarResFrec, encuesta.getPatConsCarResFrec());
		cv.put(MainDBConstants.patConsAvena, encuesta.getPatConsAvena());
		cv.put(MainDBConstants.patConsAvenaFrec, encuesta.getPatConsAvenaFrec());
		cv.put(MainDBConstants.patConsNaran, encuesta.getPatConsNaran());
		cv.put(MainDBConstants.patConsNaranFrec, encuesta.getPatConsNaranFrec());
		cv.put(MainDBConstants.patConsPasta, encuesta.getPatConsPasta());
		cv.put(MainDBConstants.patConsPastaFrec, encuesta.getPatConsPastaFrec());
		cv.put(MainDBConstants.patConsAyote, encuesta.getPatConsAyote());
		cv.put(MainDBConstants.patConsAyoteFrec, encuesta.getPatConsAyoteFrec());
		cv.put(MainDBConstants.patConsMagg, encuesta.getPatConsMagg());
		cv.put(MainDBConstants.patConsMaggFrec, encuesta.getPatConsMaggFrec());
		cv.put(MainDBConstants.patConsFrut, encuesta.getPatConsFrut());
		cv.put(MainDBConstants.patConsFrutFrec, encuesta.getPatConsFrutFrec());
		cv.put(MainDBConstants.patConsRaic, encuesta.getPatConsRaic());
		cv.put(MainDBConstants.patConsRaicFrec, encuesta.getPatConsRaicFrec());
		cv.put(MainDBConstants.patConsMenei, encuesta.getPatConsMenei());
		cv.put(MainDBConstants.patConsMeneiFrec, encuesta.getPatConsMeneiFrec());
		cv.put(MainDBConstants.patConsRepollo, encuesta.getPatConsRepollo());
		cv.put(MainDBConstants.patConsRepolloFrec, encuesta.getPatConsRepolloFrec());
		cv.put(MainDBConstants.patConsZana, encuesta.getPatConsZana());
		cv.put(MainDBConstants.patConsZanaFrec, encuesta.getPatConsZanaFrec());
		cv.put(MainDBConstants.patConsPinolillo, encuesta.getPatConsPinolillo());
		cv.put(MainDBConstants.patConsPinolilloFrec, encuesta.getPatConsPinolilloFrec());
		cv.put(MainDBConstants.patConsOVerd, encuesta.getPatConsOVerd());
		cv.put(MainDBConstants.patConsOVerdFrec, encuesta.getPatConsOVerdFrec());
		cv.put(MainDBConstants.patConsPesc, encuesta.getPatConsPesc());
		cv.put(MainDBConstants.patConsPescFrec, encuesta.getPatConsPescFrec());
		cv.put(MainDBConstants.patConsAlimComp, encuesta.getPatConsAlimComp());
		cv.put(MainDBConstants.patConsAlimCompFrec, encuesta.getPatConsAlimCompFrec());
		cv.put(MainDBConstants.patConsLecPol, encuesta.getPatConsLecPol());
		cv.put(MainDBConstants.patConsLecPolFrec, encuesta.getPatConsLecPolFrec());
		cv.put(MainDBConstants.patConsCarCer, encuesta.getPatConsCarCer());
		cv.put(MainDBConstants.patConsCarCerFrec, encuesta.getPatConsCarCerFrec());
		cv.put(MainDBConstants.patConsEmb, encuesta.getPatConsEmb());
		cv.put(MainDBConstants.patConsEmbFrec, encuesta.getPatConsEmbFrec());
		cv.put(MainDBConstants.patConsMar, encuesta.getPatConsMar());
		cv.put(MainDBConstants.patConsMarFrec, encuesta.getPatConsMarFrec());
		cv.put(MainDBConstants.patConsCarCaza, encuesta.getPatConsCarCaza());
		cv.put(MainDBConstants.patConsCarCazaFrec, encuesta.getPatConsCarCazaFrec());
		//METADATA
		if (encuesta.getRecordDate() != null) cv.put(MainDBConstants.recordDate, encuesta.getRecordDate().getTime());
		cv.put(MainDBConstants.recordUser, encuesta.getRecordUser());
		cv.put(MainDBConstants.pasive, String.valueOf(encuesta.getPasive()));
		cv.put(MainDBConstants.estado, String.valueOf(encuesta.getEstado()));
		cv.put(MainDBConstants.deviceId, encuesta.getDeviceid());
		return cv; 
	}	
	
	public static Encuesta crearEncuesta(Cursor cursorEncuesta){
		
		Encuesta mEncuesta = new Encuesta();
		mEncuesta.setIdent(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.ident)));
		mEncuesta.setCodigo(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.codigo)));
		mEncuesta.setSegmento(null);
		mEncuesta.setNumEncuesta(cursorEncuesta.getInt(cursorEncuesta.getColumnIndex(MainDBConstants.numEncuesta)));
		if(cursorEncuesta.getLong(cursorEncuesta.getColumnIndex(MainDBConstants.fechaEntrevista))>0) mEncuesta.setFechaEntrevista(new Date(cursorEncuesta.getLong(cursorEncuesta.getColumnIndex(MainDBConstants.fechaEntrevista))));
		mEncuesta.setLatitud(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.latitud)));
		mEncuesta.setLongitud(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.longitud)));
		mEncuesta.setJefeFamilia(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.jefeFamilia)));
		mEncuesta.setSexJefeFamilia(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.sexJefeFamilia)));
		mEncuesta.setNumPersonas(cursorEncuesta.getInt(cursorEncuesta.getColumnIndex(MainDBConstants.numPersonas)));
		//SECCION A
		mEncuesta.setAgua(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.agua)));
		mEncuesta.setOagua(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.oagua)));
		mEncuesta.setCuartos(cursorEncuesta.getInt(cursorEncuesta.getColumnIndex(MainDBConstants.cuartos)));
		mEncuesta.setLugNecesidades(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.lugNecesidades)));
		mEncuesta.setOlugNecesidades(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.olugNecesidades)));
		mEncuesta.setUsaCocinar(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.usaCocinar)));
		mEncuesta.setOusaCocinar(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.ousaCocinar)));
		mEncuesta.setArticulos(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.articulos)));
		mEncuesta.setOarticulos(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.oarticulos)));
		//SECCION B
		mEncuesta.setConoceFNac(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.conoceFNac)));
		if(cursorEncuesta.getLong(cursorEncuesta.getColumnIndex(MainDBConstants.fnacEnt))>0) mEncuesta.setFnacEnt(new Date(cursorEncuesta.getLong(cursorEncuesta.getColumnIndex(MainDBConstants.fnacEnt))));
		mEncuesta.setEdadEnt(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.edadEnt)));
		mEncuesta.setLeerEnt(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.leerEnt)));
		mEncuesta.setEscribirEnt(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.escribirEnt)));
		mEncuesta.setLeeescEnt(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.leeescEnt)));
		mEncuesta.setNivelEnt(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.nivelEnt)));
		mEncuesta.setGradoEnt(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.gradoEnt)));
		mEncuesta.setEntRealizada(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.entRealizada)));
		mEncuesta.setEntEmb(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.entEmb)));
		mEncuesta.setEntEmbUnico(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.entEmbUnico)));
		mEncuesta.setEntDioluz(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.entDioluz)));
		mEncuesta.setEntHieacfol(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.entHieacfol)));
		mEncuesta.setEntMeseshierro(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.entMeseshierro)));
		mEncuesta.setEntRecHierro(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.entRecHierro)));
		mEncuesta.setEntORecHierro(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.entORecHierro)));
		mEncuesta.setTiemHierroUnd(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.tiemHierroUnd)));
		mEncuesta.setTiemHierro(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.tiemHierro)));
		mEncuesta.setDondeHierro(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.dondeHierro)));
		mEncuesta.setDondeHierroBesp(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.dondeHierroBesp)));
		mEncuesta.setDondeHierroFesp(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.dondeHierroFesp)));
		mEncuesta.setTomadoHierro(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.tomadoHierro)));
		mEncuesta.setVita(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.vita)));
		mEncuesta.setTiempVita(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.tiempVita)));
		mEncuesta.setEvitaEmb(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.evitaEmb)));
		mEncuesta.setDondeAntic(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.dondeAntic)));
		mEncuesta.setNuevoEmb(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.nuevoEmb)));
		mEncuesta.setHierro(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.hierro)));
		mEncuesta.setdHierro(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.dHierro)));
		//SECCION C
		mEncuesta.setNumNinos(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.numNinos)));
		mEncuesta.setN1(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.n1)));
		mEncuesta.setN2(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.n2)));
		mEncuesta.setN3(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.n3)));
		mEncuesta.setN4(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.n4)));
		mEncuesta.setN5(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.n5)));
		mEncuesta.setN6(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.n6)));
		mEncuesta.setE1(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.e1)));
		mEncuesta.setE2(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.e2)));
		mEncuesta.setE3(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.e3)));
		mEncuesta.setE4(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.e4)));
		mEncuesta.setE5(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.e5)));
		mEncuesta.setE6(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.e6)));
		mEncuesta.setNselec(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.nselec)));
		mEncuesta.setNomselec(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.nomselec)));
		if(cursorEncuesta.getLong(cursorEncuesta.getColumnIndex(MainDBConstants.fnacselec))>0) mEncuesta.setFnacselec(new Date(cursorEncuesta.getLong(cursorEncuesta.getColumnIndex(MainDBConstants.fnacselec))));
		mEncuesta.setEselect(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.eselect)));
		mEncuesta.setSexselec(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.sexselec)));
		mEncuesta.setCalim(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.calim)));
		mEncuesta.setVcome(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.vcome)));
		mEncuesta.setConsalim(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.consalim)));
		mEncuesta.setCalimenf(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.calimenf)));
		mEncuesta.setClecheenf(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.clecheenf)));
		//SECCION D
		mEncuesta.setHierron(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.hierron)));
		mEncuesta.setThierround(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.thierround)));
		mEncuesta.setThierrocant(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.thierrocant)));
		mEncuesta.setFhierro(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.fhierro)));
		mEncuesta.setGhierro(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.ghierro)));
		mEncuesta.setDonhierro(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.donhierro)));
		mEncuesta.setDonhierrobesp(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.donhierrobesp)));
		mEncuesta.setDonhierrofesp(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.donhierrofesp)));
		mEncuesta.setFuhierro(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.fuhierro)));
		if(cursorEncuesta.getLong(cursorEncuesta.getColumnIndex(MainDBConstants.fuhierror))>0) mEncuesta.setFuhierror(new Date(cursorEncuesta.getLong(cursorEncuesta.getColumnIndex(MainDBConstants.fuhierror))));
		if(cursorEncuesta.getLong(cursorEncuesta.getColumnIndex(MainDBConstants.fauhierror))>0) mEncuesta.setFauhierror(new Date(cursorEncuesta.getLong(cursorEncuesta.getColumnIndex(MainDBConstants.fauhierror))));
		mEncuesta.setNvita(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.nvita)));
		mEncuesta.setNcvita(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.ncvita)));
		mEncuesta.setTvitaund(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.tvitaund)));
		mEncuesta.setTvitacant(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.tvitacant)));
		mEncuesta.setNdvita(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.ndvita)));
		mEncuesta.setNdvitao(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.ndvitao)));
		mEncuesta.setTdvita(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.tdvita)));
		if(cursorEncuesta.getLong(cursorEncuesta.getColumnIndex(MainDBConstants.fuvita))>0) mEncuesta.setFuvita(new Date(cursorEncuesta.getLong(cursorEncuesta.getColumnIndex(MainDBConstants.fuvita))));
		if(cursorEncuesta.getLong(cursorEncuesta.getColumnIndex(MainDBConstants.fauvita))>0) mEncuesta.setFauvita(new Date(cursorEncuesta.getLong(cursorEncuesta.getColumnIndex(MainDBConstants.fauvita))));
		mEncuesta.setNcnut(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.ncnut)));
		mEncuesta.setNcnutund(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.ncnutund)));
		mEncuesta.setNcnutcant(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.ncnutcant)));
		mEncuesta.setDoncnut(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.doncnut)));
		mEncuesta.setDoncnutfotro(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.doncnutfotro)));
		mEncuesta.setParasit(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.parasit)));
		mEncuesta.setCvparas(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.cvparas)));
		mEncuesta.setmParasitario(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.mParasitario)));
		mEncuesta.setOtroMpEsp(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.otroMpEsp)));
		mEncuesta.setDonMp(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.donMp)));
		mEncuesta.setOtroDonMp(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.otroDonMp)));
		mEncuesta.setEvitarParasito(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.evitarParasito)));
		mEncuesta.setoEvitarParasito(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.oEvitarParasito)));
		//SECCION E
		mEncuesta.setNlactmat(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.nlactmat)));
		mEncuesta.setSexlmat(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.sexlmat)));
		mEncuesta.setEmeseslmat(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.emeseslmat)));
		if(cursorEncuesta.getLong(cursorEncuesta.getColumnIndex(MainDBConstants.fnaclmat))>0) mEncuesta.setFnaclmat(new Date(cursorEncuesta.getLong(cursorEncuesta.getColumnIndex(MainDBConstants.fnaclmat))));
		mEncuesta.setPecho(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.pecho)));
		mEncuesta.setMotNopecho(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.motNopecho)));
		mEncuesta.setMotNopechoOtro(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.motNopechoOtro)));
		mEncuesta.setDapecho(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.dapecho)));
		mEncuesta.setTiempecho(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.tiempecho)));
		mEncuesta.setTiemmama(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.tiemmama)));
		mEncuesta.setTiemmamaMins(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.tiemmamaMins)));
		mEncuesta.setDospechos(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.dospechos)));
		mEncuesta.setVecespechodia(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.vecespechodia)));
		mEncuesta.setVecespechonoche(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.vecespechonoche)));
		mEncuesta.setElmatexcund(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.elmatexcund)));
		mEncuesta.setElmatexccant(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.elmatexccant)));
		mEncuesta.setEdiopechound(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.ediopechound)));
		mEncuesta.setEdiopechocant(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.ediopechocant)));
		mEncuesta.setCombeb(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.combeb)));
		mEncuesta.setEalimund(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.ealimund)));
		mEncuesta.setEalimcant(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.ealimcant)));
		mEncuesta.setBebeLiq(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.bebeLiq)));
		mEncuesta.setReunionPeso(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.reunionPeso)));
		mEncuesta.setQuienReunionPeso(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.quienReunionPeso)));
		mEncuesta.setQuienReunionPesoOtro(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.quienReunionPesoOtro)));
		mEncuesta.setAssitioReunionPeso(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.assitioReunionPeso)));
		//SECCION F
		mEncuesta.setPesoTallaEnt(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.pesoTallaEnt)));
		mEncuesta.setPesoEnt1(cursorEncuesta.getFloat(cursorEncuesta.getColumnIndex(MainDBConstants.pesoEnt1)));
		mEncuesta.setPesoEnt2(cursorEncuesta.getFloat(cursorEncuesta.getColumnIndex(MainDBConstants.pesoEnt2)));
		mEncuesta.setTallaEnt1(cursorEncuesta.getFloat(cursorEncuesta.getColumnIndex(MainDBConstants.tallaEnt1)));
		mEncuesta.setTallaEnt2(cursorEncuesta.getFloat(cursorEncuesta.getColumnIndex(MainDBConstants.tallaEnt2)));
		mEncuesta.setPesoTallaNin(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.pesoTallaNin)));
		mEncuesta.setPesoNin1(cursorEncuesta.getFloat(cursorEncuesta.getColumnIndex(MainDBConstants.pesoNin1)));
		mEncuesta.setPesoNin2(cursorEncuesta.getFloat(cursorEncuesta.getColumnIndex(MainDBConstants.pesoNin2)));
		mEncuesta.setLongNin1(cursorEncuesta.getFloat(cursorEncuesta.getColumnIndex(MainDBConstants.longNin1)));
		mEncuesta.setLongNin2(cursorEncuesta.getFloat(cursorEncuesta.getColumnIndex(MainDBConstants.longNin2)));
		mEncuesta.setTallaNin1(cursorEncuesta.getFloat(cursorEncuesta.getColumnIndex(MainDBConstants.tallaNin1)));
		mEncuesta.setTallaNin2(cursorEncuesta.getFloat(cursorEncuesta.getColumnIndex(MainDBConstants.tallaNin2)));
		//SECCION G
		mEncuesta.setMsEnt(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.msEnt)));
		mEncuesta.setCodMsEnt(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.codMsEnt)));
		mEncuesta.setHbEnt(cursorEncuesta.getFloat(cursorEncuesta.getColumnIndex(MainDBConstants.hbEnt)));
		mEncuesta.setMsNin(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.msNin)));
		mEncuesta.setCodMsNin(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.codMsNin)));
		mEncuesta.setHbNin(cursorEncuesta.getFloat(cursorEncuesta.getColumnIndex(MainDBConstants.hbNin)));
		mEncuesta.setMoEnt(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.moEnt)));
		mEncuesta.setCodMoEnt(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.codMoEnt)));
		mEncuesta.setPan(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.pan)));
		mEncuesta.setSal(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.sal)));
		mEncuesta.setMarcaSal(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.marcaSal)));
		mEncuesta.setAzucar(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.azucar)));
		mEncuesta.setMarcaAzucar(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.marcaAzucar)));
		//SECCION H
		mEncuesta.setPatConsAzuc(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.patConsAzuc)));
		mEncuesta.setPatConsAzucFrec(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.patConsAzucFrec)));
		mEncuesta.setPatConsSal(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.patConsSal)));
		mEncuesta.setPatConsSalFrec(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.patConsSalFrec)));
		mEncuesta.setPatConsArroz(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.patConsArroz)));
		mEncuesta.setPatConsArrozFrec(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.patConsArrozFrec)));
		mEncuesta.setPatConsAcei(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.patConsAcei)));
		mEncuesta.setPatConsAceiFrec(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.patConsAceiFrec)));
		mEncuesta.setPatConsFri(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.patConsFri)));
		mEncuesta.setPatConsFriFrec(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.patConsFriFrec)));
		mEncuesta.setPatConsCebo(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.patConsCebo)));
		mEncuesta.setPatConsCeboFrec(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.patConsCeboFrec)));
		mEncuesta.setPatConsChi(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.patConsChi)));
		mEncuesta.setPatConsChiFrec(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.patConsChiFrec)));
		mEncuesta.setPatConsQue(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.patConsQue)));
		mEncuesta.setPatConsQueFrec(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.patConsQueFrec)));
		mEncuesta.setPatConsCafe(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.patConsCafe)));
		mEncuesta.setPatConsCafeFrec(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.patConsCafeFrec)));
		mEncuesta.setPatConsTor(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.patConsTor)));
		mEncuesta.setPatConsTorFrec(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.patConsTorFrec)));
		mEncuesta.setPatConsCar(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.patConsCar)));
		mEncuesta.setPatConsCarFrec(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.patConsCarFrec)));
		mEncuesta.setPatConsHue(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.patConsHue)));
		mEncuesta.setPatConsHueFrec(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.patConsHueFrec)));
		mEncuesta.setPatConsPan(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.patConsPan)));
		mEncuesta.setPatConsPanFrec(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.patConsPanFrec)));
		mEncuesta.setPatConsBan(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.patConsBan)));
		mEncuesta.setPatConsBanFrec(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.patConsBanFrec)));
		mEncuesta.setPatConsPanDul(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.patConsPanDul)));
		mEncuesta.setPatConsPanDulFrec(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.patConsPanDulFrec)));
		mEncuesta.setPatConsPla(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.patConsPla)));
		mEncuesta.setPatConsPlaFrec(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.patConsPlaFrec)));
		mEncuesta.setPatConsPapa(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.patConsPapa)));
		mEncuesta.setPatConsPapaFrec(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.patConsPapaFrec)));
		mEncuesta.setPatConsLec(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.patConsLec)));
		mEncuesta.setPatConsLecFrec(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.patConsLecFrec)));
		mEncuesta.setPatConsSalTom(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.patConsSalTom)));
		mEncuesta.setPatConsSalTomFrec(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.patConsSalTomFrec)));
		mEncuesta.setPatConsGas(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.patConsGas)));
		mEncuesta.setPatConsGasFrec(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.patConsGasFrec)));
		mEncuesta.setPatConsCarRes(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.patConsCarRes)));
		mEncuesta.setPatConsCarResFrec(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.patConsCarResFrec)));
		mEncuesta.setPatConsAvena(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.patConsAvena)));
		mEncuesta.setPatConsAvenaFrec(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.patConsAvenaFrec)));
		mEncuesta.setPatConsNaran(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.patConsNaran)));
		mEncuesta.setPatConsNaranFrec(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.patConsNaranFrec)));
		mEncuesta.setPatConsPasta(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.patConsPasta)));
		mEncuesta.setPatConsPastaFrec(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.patConsPastaFrec)));
		mEncuesta.setPatConsAyote(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.patConsAyote)));
		mEncuesta.setPatConsAyoteFrec(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.patConsAyoteFrec)));
		mEncuesta.setPatConsMagg(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.patConsMagg)));
		mEncuesta.setPatConsMaggFrec(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.patConsMaggFrec)));
		mEncuesta.setPatConsFrut(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.patConsFrut)));
		mEncuesta.setPatConsFrutFrec(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.patConsFrutFrec)));
		mEncuesta.setPatConsRaic(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.patConsRaic)));
		mEncuesta.setPatConsRaicFrec(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.patConsRaicFrec)));
		mEncuesta.setPatConsMenei(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.patConsMenei)));
		mEncuesta.setPatConsMeneiFrec(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.patConsMeneiFrec)));
		mEncuesta.setPatConsRepollo(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.patConsRepollo)));
		mEncuesta.setPatConsRepolloFrec(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.patConsRepolloFrec)));
		mEncuesta.setPatConsZana(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.patConsZana)));
		mEncuesta.setPatConsZanaFrec(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.patConsZanaFrec)));
		mEncuesta.setPatConsPinolillo(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.patConsPinolillo)));
		mEncuesta.setPatConsPinolilloFrec(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.patConsPinolilloFrec)));
		mEncuesta.setPatConsOVerd(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.patConsOVerd)));
		mEncuesta.setPatConsOVerdFrec(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.patConsOVerdFrec)));
		mEncuesta.setPatConsPesc(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.patConsPesc)));
		mEncuesta.setPatConsPescFrec(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.patConsPescFrec)));
		mEncuesta.setPatConsAlimComp(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.patConsAlimComp)));
		mEncuesta.setPatConsAlimCompFrec(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.patConsAlimCompFrec)));
		mEncuesta.setPatConsLecPol(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.patConsLecPol)));
		mEncuesta.setPatConsLecPolFrec(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.patConsLecPolFrec)));
		mEncuesta.setPatConsCarCer(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.patConsCarCer)));
		mEncuesta.setPatConsCarCerFrec(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.patConsCarCerFrec)));
		mEncuesta.setPatConsEmb(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.patConsEmb)));
		mEncuesta.setPatConsEmbFrec(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.patConsEmbFrec)));
		mEncuesta.setPatConsMar(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.patConsMar)));
		mEncuesta.setPatConsMarFrec(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.patConsMarFrec)));
		mEncuesta.setPatConsCarCaza(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.patConsCarCaza)));
		mEncuesta.setPatConsCarCazaFrec(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.patConsCarCazaFrec)));
		//METADATA
		if(cursorEncuesta.getLong(cursorEncuesta.getColumnIndex(MainDBConstants.recordDate))>0) mEncuesta.setRecordDate(new Date(cursorEncuesta.getLong(cursorEncuesta.getColumnIndex(MainDBConstants.recordDate))));
		mEncuesta.setRecordUser(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.recordUser)));
		mEncuesta.setPasive(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.pasive)).charAt(0));
		mEncuesta.setEstado(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.estado)).charAt(0));
		mEncuesta.setDeviceid(cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.deviceId)));
		return mEncuesta;
	}
	
}
