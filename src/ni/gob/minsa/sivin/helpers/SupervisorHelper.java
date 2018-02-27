package ni.gob.minsa.sivin.helpers;

import java.util.Date;

import android.content.ContentValues;
import android.database.Cursor;
import ni.gob.minsa.sivin.domain.Supervisor;
import ni.gob.minsa.sivin.utils.MainDBConstants;

public class SupervisorHelper {
	
	public static ContentValues crearSupervisorContentValues(Supervisor encuestador){
		ContentValues cv = new ContentValues();
		cv.put(MainDBConstants.identificador, encuestador.getIdent());
		cv.put(MainDBConstants.codigo, encuestador.getCodigo());
		cv.put(MainDBConstants.nombre, encuestador.getNombre());
		if (encuestador.getRecordDate() != null) cv.put(MainDBConstants.recordDate, encuestador.getRecordDate().getTime());
		cv.put(MainDBConstants.recordUser, encuestador.getRecordUser());
		cv.put(MainDBConstants.pasive, String.valueOf(encuestador.getPasive()));
		cv.put(MainDBConstants.estado, String.valueOf(encuestador.getEstado()));
		cv.put(MainDBConstants.deviceId, encuestador.getDeviceid());
		return cv; 
	}	
	
	public static Supervisor crearSupervisor(Cursor cursorSupervisor){
		
		Supervisor mSupervisor = new Supervisor();
		mSupervisor.setIdent(cursorSupervisor.getString(cursorSupervisor.getColumnIndex(MainDBConstants.identificador)));
		mSupervisor.setCodigo(cursorSupervisor.getString(cursorSupervisor.getColumnIndex(MainDBConstants.codigo)));
		mSupervisor.setNombre(cursorSupervisor.getString(cursorSupervisor.getColumnIndex(MainDBConstants.nombre)));
		if(cursorSupervisor.getLong(cursorSupervisor.getColumnIndex(MainDBConstants.recordDate))>0) mSupervisor.setRecordDate(new Date(cursorSupervisor.getLong(cursorSupervisor.getColumnIndex(MainDBConstants.recordDate))));
		mSupervisor.setRecordUser(cursorSupervisor.getString(cursorSupervisor.getColumnIndex(MainDBConstants.recordUser)));
		mSupervisor.setPasive(cursorSupervisor.getString(cursorSupervisor.getColumnIndex(MainDBConstants.pasive)).charAt(0));
		mSupervisor.setEstado(cursorSupervisor.getString(cursorSupervisor.getColumnIndex(MainDBConstants.estado)).charAt(0));
		mSupervisor.setDeviceid(cursorSupervisor.getString(cursorSupervisor.getColumnIndex(MainDBConstants.deviceId)));
		return mSupervisor;
	}
	
}
