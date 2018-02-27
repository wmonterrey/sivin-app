package ni.gob.minsa.sivin.helpers;

import java.util.Date;

import android.content.ContentValues;
import android.database.Cursor;
import ni.gob.minsa.sivin.domain.Encuestador;
import ni.gob.minsa.sivin.utils.MainDBConstants;

public class EncuestadorHelper {
	
	public static ContentValues crearEncuestadorContentValues(Encuestador encuestador){
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
	
	public static Encuestador crearEncuestador(Cursor cursorEncuestador){
		
		Encuestador mEncuestador = new Encuestador();
		mEncuestador.setIdent(cursorEncuestador.getString(cursorEncuestador.getColumnIndex(MainDBConstants.identificador)));
		mEncuestador.setCodigo(cursorEncuestador.getString(cursorEncuestador.getColumnIndex(MainDBConstants.codigo)));
		mEncuestador.setNombre(cursorEncuestador.getString(cursorEncuestador.getColumnIndex(MainDBConstants.nombre)));
		if(cursorEncuestador.getLong(cursorEncuestador.getColumnIndex(MainDBConstants.recordDate))>0) mEncuestador.setRecordDate(new Date(cursorEncuestador.getLong(cursorEncuestador.getColumnIndex(MainDBConstants.recordDate))));
		mEncuestador.setRecordUser(cursorEncuestador.getString(cursorEncuestador.getColumnIndex(MainDBConstants.recordUser)));
		mEncuestador.setPasive(cursorEncuestador.getString(cursorEncuestador.getColumnIndex(MainDBConstants.pasive)).charAt(0));
		mEncuestador.setEstado(cursorEncuestador.getString(cursorEncuestador.getColumnIndex(MainDBConstants.estado)).charAt(0));
		mEncuestador.setDeviceid(cursorEncuestador.getString(cursorEncuestador.getColumnIndex(MainDBConstants.deviceId)));
		return mEncuestador;
	}
	
}
