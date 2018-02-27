package ni.gob.minsa.sivin.helpers;

import android.content.ContentValues;
import android.database.Cursor;
import ni.gob.minsa.sivin.domain.MessageResource;
import ni.gob.minsa.sivin.utils.MainDBConstants;

public class MessageResourceHelper {
	
	public static ContentValues crearMessageResourceValues(MessageResource barrio){
		ContentValues cv = new ContentValues();
		cv.put(MainDBConstants.messageKey, barrio.getMessageKey());
		cv.put(MainDBConstants.catRoot, barrio.getCatRoot());
		cv.put(MainDBConstants.catKey, barrio.getCatKey());
		cv.put(MainDBConstants.pasive, String.valueOf(barrio.getPasive()));
		cv.put(MainDBConstants.isCat, String.valueOf(barrio.getIsCat()));
		cv.put(MainDBConstants.order, barrio.getOrder());
		cv.put(MainDBConstants.spanish, barrio.getSpanish());
		cv.put(MainDBConstants.english, barrio.getEnglish());
		return cv; 
	}	
	
	public static MessageResource crearMessageResource(Cursor cursorMessageResource){
		
		MessageResource mMessageResource = new MessageResource();
		mMessageResource.setMessageKey(cursorMessageResource.getString(cursorMessageResource.getColumnIndex(MainDBConstants.messageKey)));
		mMessageResource.setCatRoot(cursorMessageResource.getString(cursorMessageResource.getColumnIndex(MainDBConstants.catRoot)));
		mMessageResource.setCatKey(cursorMessageResource.getString(cursorMessageResource.getColumnIndex(MainDBConstants.catKey)));
		mMessageResource.setPasive(cursorMessageResource.getString(cursorMessageResource.getColumnIndex(MainDBConstants.pasive)).charAt(0));
		mMessageResource.setPasive(cursorMessageResource.getString(cursorMessageResource.getColumnIndex(MainDBConstants.pasive)).charAt(0));
		mMessageResource.setOrder(cursorMessageResource.getInt(cursorMessageResource.getColumnIndex(MainDBConstants.order)));
		mMessageResource.setSpanish(cursorMessageResource.getString(cursorMessageResource.getColumnIndex(MainDBConstants.spanish)));
		mMessageResource.setEnglish(cursorMessageResource.getString(cursorMessageResource.getColumnIndex(MainDBConstants.english)));
		return mMessageResource;
	}
	
}
