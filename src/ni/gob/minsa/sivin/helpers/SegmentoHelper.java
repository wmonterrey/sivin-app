package ni.gob.minsa.sivin.helpers;

import java.util.Date;

import android.content.ContentValues;
import android.database.Cursor;
import ni.gob.minsa.sivin.domain.Segmento;
import ni.gob.minsa.sivin.utils.MainDBConstants;

public class SegmentoHelper {
	
	public static ContentValues crearSegmentoContentValues(Segmento segmento){
		ContentValues cv = new ContentValues();
		cv.put(MainDBConstants.segmento, segmento.getIdent());
		cv.put(MainDBConstants.codigo, segmento.getCodigo());
		cv.put(MainDBConstants.departamento, segmento.getDepartamento());
		cv.put(MainDBConstants.municipio, segmento.getMunicipio());
		cv.put(MainDBConstants.comunidad, segmento.getComunidad());
		cv.put(MainDBConstants.region, segmento.getRegion());
		cv.put(MainDBConstants.procedencia, segmento.getProcedencia());
		cv.put(MainDBConstants.codigosis, segmento.getCodigoSis());
		cv.put(MainDBConstants.codMun, segmento.getCodMun());
		cv.put(MainDBConstants.estrato, segmento.getEstrato());
		cv.put(MainDBConstants.grupo, segmento.getGrupo());
		cv.put(MainDBConstants.vivParticulares, segmento.getVivParticulares());
		cv.put(MainDBConstants.vivInicial, segmento.getVivInicial());
		if (segmento.getRecordDate() != null) cv.put(MainDBConstants.recordDate, segmento.getRecordDate().getTime());
		cv.put(MainDBConstants.recordUser, segmento.getRecordUser());
		cv.put(MainDBConstants.pasive, String.valueOf(segmento.getPasive()));
		cv.put(MainDBConstants.estado, String.valueOf(segmento.getEstado()));
		cv.put(MainDBConstants.deviceId, segmento.getDeviceid());
		return cv; 
	}	
	
	public static Segmento crearSegmento(Cursor cursorSegmento){
		
		Segmento mSegmento = new Segmento();
		mSegmento.setIdent(cursorSegmento.getString(cursorSegmento.getColumnIndex(MainDBConstants.segmento)));
		mSegmento.setCodigo(cursorSegmento.getString(cursorSegmento.getColumnIndex(MainDBConstants.codigo)));
		mSegmento.setDepartamento(cursorSegmento.getString(cursorSegmento.getColumnIndex(MainDBConstants.departamento)));
		mSegmento.setMunicipio(cursorSegmento.getString(cursorSegmento.getColumnIndex(MainDBConstants.municipio)));
		mSegmento.setComunidad(cursorSegmento.getString(cursorSegmento.getColumnIndex(MainDBConstants.comunidad)));
		mSegmento.setRegion(cursorSegmento.getString(cursorSegmento.getColumnIndex(MainDBConstants.region)));
		mSegmento.setProcedencia(cursorSegmento.getString(cursorSegmento.getColumnIndex(MainDBConstants.procedencia)));
		mSegmento.setCodigoSis(cursorSegmento.getString(cursorSegmento.getColumnIndex(MainDBConstants.codigosis)));
		mSegmento.setCodMun(cursorSegmento.getString(cursorSegmento.getColumnIndex(MainDBConstants.codMun)));
		mSegmento.setEstrato(cursorSegmento.getString(cursorSegmento.getColumnIndex(MainDBConstants.estrato)));
		mSegmento.setGrupo(cursorSegmento.getString(cursorSegmento.getColumnIndex(MainDBConstants.grupo)));
		mSegmento.setVivParticulares(cursorSegmento.getInt(cursorSegmento.getColumnIndex(MainDBConstants.vivParticulares)));
		mSegmento.setVivInicial(cursorSegmento.getInt(cursorSegmento.getColumnIndex(MainDBConstants.vivInicial)));
		if(cursorSegmento.getLong(cursorSegmento.getColumnIndex(MainDBConstants.recordDate))>0) mSegmento.setRecordDate(new Date(cursorSegmento.getLong(cursorSegmento.getColumnIndex(MainDBConstants.recordDate))));
		mSegmento.setRecordUser(cursorSegmento.getString(cursorSegmento.getColumnIndex(MainDBConstants.recordUser)));
		mSegmento.setPasive(cursorSegmento.getString(cursorSegmento.getColumnIndex(MainDBConstants.pasive)).charAt(0));
		mSegmento.setEstado(cursorSegmento.getString(cursorSegmento.getColumnIndex(MainDBConstants.estado)).charAt(0));
		mSegmento.setDeviceid(cursorSegmento.getString(cursorSegmento.getColumnIndex(MainDBConstants.deviceId)));
		return mSegmento;
	}
	
}
