package ni.gob.minsa.sivin.database;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import net.sqlcipher.Cursor;
import net.sqlcipher.SQLException;
import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteQueryBuilder;
import ni.gob.minsa.sivin.domain.Encuesta;
import ni.gob.minsa.sivin.domain.Encuestador;
import ni.gob.minsa.sivin.domain.MessageResource;
import ni.gob.minsa.sivin.domain.Segmento;
import ni.gob.minsa.sivin.domain.Supervisor;
import ni.gob.minsa.sivin.domain.users.Authority;
import ni.gob.minsa.sivin.domain.users.UserSistema;
import ni.gob.minsa.sivin.helpers.EncuestaHelper;
import ni.gob.minsa.sivin.helpers.EncuestadorHelper;
import ni.gob.minsa.sivin.helpers.MessageResourceHelper;
import ni.gob.minsa.sivin.helpers.SegmentoHelper;
import ni.gob.minsa.sivin.helpers.SupervisorHelper;
import ni.gob.minsa.sivin.helpers.UserSistemaHelper;
import ni.gob.minsa.sivin.utils.Constants;
import ni.gob.minsa.sivin.utils.FileUtils;
import ni.gob.minsa.sivin.utils.MainDBConstants;

/**
 * Adaptador de la base de datos Sivin
 * 
 * @author William Aviles
 */

public class SivinAdapter {
	
	private DatabaseHelper mDbHelper;
	private SQLiteDatabase mDb;
	private final Context mContext;
	private final String mPassword;
	private final boolean mFromServer;
	private final boolean mCleanDb;
	

	public SivinAdapter(Context context, String password, boolean fromServer, boolean cleanDb) {
		mContext = context;
		mPassword = password;
		mFromServer = fromServer;
		mCleanDb = cleanDb;
	}
	
	private static class DatabaseHelper extends SivinSQLiteOpenHelper {
		DatabaseHelper(Context context, String password, boolean fromServer, boolean cleanDb) {
			super(FileUtils.DATABASE_PATH, MainDBConstants.DATABASE_NAME, MainDBConstants.DATABASE_VERSION, context,
					password, fromServer, cleanDb);
			createStorage();
		}
		
		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(MainDBConstants.CREATE_USER_TABLE);
			db.execSQL(MainDBConstants.CREATE_ROLE_TABLE);
			db.execSQL(MainDBConstants.CREATE_SEGMENTO_TABLE);
			db.execSQL(MainDBConstants.CREATE_ENCUESTADOR_TABLE);
			db.execSQL(MainDBConstants.CREATE_SUPERVISOR_TABLE);
			db.execSQL(MainDBConstants.CREATE_ENCUESTA_TABLE);
			db.execSQL(MainDBConstants.CREATE_MESSAGES_TABLE);
		}
		
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			onCreate(db);
			if(oldVersion==0) return;
		}
	}
	
	public SivinAdapter open() throws SQLException {
		mDbHelper = new DatabaseHelper(mContext,mPassword,mFromServer,mCleanDb);
		mDb = mDbHelper.getWritableDatabase();
		return this;
	}

	public void close() {
		mDbHelper.close();
	}
	
	/**
	 * Crea un cursor desde la base de datos
	 * 
	 * @return cursor
	 */
	public Cursor crearCursor(String tabla, String whereString, String projection[], String ordenString) throws SQLException {
		Cursor c = null;
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		qb.setTables(tabla);
		c = qb.query(mDb,projection,whereString,null,null,null,ordenString);
		return c;
	}

	public static boolean createStorage() {
		return FileUtils.createFolder(FileUtils.DATABASE_PATH);
	}
	
	/**
	 * Metodos para usuarios en la base de datos
	 * 
	 * @param user
	 *            Objeto Usuario que contiene la informacion
	 *
	 */
	//Crear nuevo usuario en la base de datos
	public void crearUsuario(UserSistema user) {
		ContentValues cv = UserSistemaHelper.crearUserSistemaContentValues(user);
		mDb.insert(MainDBConstants.USER_TABLE, null, cv);
	}
	//Editar usuario existente en la base de datos
	public boolean editarUsuario(UserSistema user) {
		ContentValues cv = UserSistemaHelper.crearUserSistemaContentValues(user);
		return mDb.update(MainDBConstants.USER_TABLE, cv, MainDBConstants.username + "='" 
				+ user.getUsername()+"'", null) > 0;
	}
	//Limpiar la tabla de usuarios de la base de datos
	public boolean borrarUsuarios() {
		return mDb.delete(MainDBConstants.USER_TABLE, null, null) > 0;
	}
	//Obtener un usuario de la base de datos
	public UserSistema getUsuario(String filtro, String orden) throws SQLException {
		UserSistema mUser = null;
		Cursor cursorUser = crearCursor(MainDBConstants.USER_TABLE, filtro, null, orden);
		if (cursorUser != null && cursorUser.getCount() > 0) {
			cursorUser.moveToFirst();
			mUser=UserSistemaHelper.crearUserSistema(cursorUser);
		}
		if (!cursorUser.isClosed()) cursorUser.close();
		return mUser;
	}
	//Obtener una lista de usuarios de la base de datos
	public List<UserSistema> getUsuarios(String filtro, String orden) throws SQLException {
		List<UserSistema> mUsuarios = new ArrayList<UserSistema>();
		Cursor cursorUsuarios = crearCursor(MainDBConstants.USER_TABLE, filtro, null, orden);
		if (cursorUsuarios != null && cursorUsuarios.getCount() > 0) {
			cursorUsuarios.moveToFirst();
			mUsuarios.clear();
			do{
				UserSistema mUser = null;
				mUser = UserSistemaHelper.crearUserSistema(cursorUsuarios);
				mUsuarios.add(mUser);
			} while (cursorUsuarios.moveToNext());
		}
		if (!cursorUsuarios.isClosed()) cursorUsuarios.close();
		return mUsuarios;
	}
	
	/**
	 * Metodos para roles en la base de datos
	 * 
	 * @param rol
	 *            Objeto Authority que contiene la informacion
	 *
	 */
	//Crear nuevo rol en la base de datos
	public void crearRol(Authority rol) {
		ContentValues cv = UserSistemaHelper.crearRolValues(rol);
		mDb.insert(MainDBConstants.ROLE_TABLE, null, cv);
	}
	//Limpiar la tabla de roles de la base de datos
	public boolean borrarRoles() {
		return mDb.delete(MainDBConstants.ROLE_TABLE, null, null) > 0;
	}
	//Verificar un rol de usuario
	public Boolean buscarRol(String username, String Rol) throws SQLException {
		Cursor c = mDb.query(true, MainDBConstants.ROLE_TABLE, null,
				MainDBConstants.username + "='" + username + "' and " + MainDBConstants.role + "='" + Rol + "'" , null, null, null, null, null);
		boolean result = c != null && c.getCount()>0; 
		c.close();
		return result;
	}
	
	/**
	 * Metodos para segmentos en la base de datos
	 * 
	 * @param segmento
	 *            Objeto Segmento que contiene la informacion
	 *
	 */
	//Crear nuevo segmento en la base de datos
	public void crearSegmento(Segmento segmento) {
		ContentValues cv = SegmentoHelper.crearSegmentoContentValues(segmento);
		mDb.insert(MainDBConstants.SEGMENTO_TABLE, null, cv);
	}
	//Limpiar la tabla de Segmento de la base de datos
	public boolean borrarSegmentos() {
		return mDb.delete(MainDBConstants.SEGMENTO_TABLE, null, null) > 0;
	}
	//Obtener un segmento de la base de datos
	public Segmento getSegmento(String filtro, String orden) throws SQLException {
		Segmento mSegmento = null;
		Cursor cursorSegmento = crearCursor(MainDBConstants.SEGMENTO_TABLE, filtro, null, orden);
		if (cursorSegmento != null && cursorSegmento.getCount() > 0) {
			cursorSegmento.moveToFirst();
			mSegmento=SegmentoHelper.crearSegmento(cursorSegmento);
		}
		if (!cursorSegmento.isClosed()) cursorSegmento.close();
		return mSegmento;
	}
	//Obtener una lista de segmentos de la base de datos
	public List<Segmento> getSegmentos(String filtro, String orden) throws SQLException {
		List<Segmento> mSegmentos = new ArrayList<Segmento>();
		Cursor cursorSegmentos = crearCursor(MainDBConstants.SEGMENTO_TABLE, filtro, null, orden);
		if (cursorSegmentos != null && cursorSegmentos.getCount() > 0) {
			cursorSegmentos.moveToFirst();
			mSegmentos.clear();
			do{
				Segmento mSegmento = null;
				mSegmento = SegmentoHelper.crearSegmento(cursorSegmentos);
				mSegmentos.add(mSegmento);
			} while (cursorSegmentos.moveToNext());
		}
		if (!cursorSegmentos.isClosed()) cursorSegmentos.close();
		return mSegmentos;
	}
	
	/**
	 * Metodos para encuestadores en la base de datos
	 * 
	 * @param encuestador
	 *            Objeto Encuestador que contiene la informacion
	 *
	 */
	//Crear nuevo Encuestador en la base de datos
	public void crearEncuestador(Encuestador encuestador) {
		ContentValues cv = EncuestadorHelper.crearEncuestadorContentValues(encuestador);
		mDb.insert(MainDBConstants.ENCUESTADOR_TABLE, null, cv);
	}
	//Limpiar la tabla de Encuestador de la base de datos
	public boolean borrarEncuestadores() {
		return mDb.delete(MainDBConstants.ENCUESTADOR_TABLE, null, null) > 0;
	}
	//Obtener un Encuestador de la base de datos
	public Encuestador getEncuestador(String filtro, String orden) throws SQLException {
		Encuestador mEncuestador = null;
		Cursor cursorEncuestador = crearCursor(MainDBConstants.ENCUESTADOR_TABLE, filtro, null, orden);
		if (cursorEncuestador != null && cursorEncuestador.getCount() > 0) {
			cursorEncuestador.moveToFirst();
			mEncuestador=EncuestadorHelper.crearEncuestador(cursorEncuestador);
		}
		if (!cursorEncuestador.isClosed()) cursorEncuestador.close();
		return mEncuestador;
	}
	//Obtener una lista de encuestadores de la base de datos
	public List<Encuestador> getEncuestadores(String filtro, String orden) throws SQLException {
		List<Encuestador> mEncuestadores = new ArrayList<Encuestador>();
		Cursor cursorEncuestadores = crearCursor(MainDBConstants.ENCUESTADOR_TABLE, filtro, null, orden);
		if (cursorEncuestadores != null && cursorEncuestadores.getCount() > 0) {
			cursorEncuestadores.moveToFirst();
			mEncuestadores.clear();
			do{
				Encuestador mEncuestador = null;
				mEncuestador = EncuestadorHelper.crearEncuestador(cursorEncuestadores);
				mEncuestadores.add(mEncuestador);
			} while (cursorEncuestadores.moveToNext());
		}
		if (!cursorEncuestadores.isClosed()) cursorEncuestadores.close();
		return mEncuestadores;
	}
	
	/**
	 * Metodos para supervisores en la base de datos
	 * 
	 * @param encuestador
	 *            Objeto Supervisor que contiene la informacion
	 *
	 */
	//Crear nuevo Supervisor en la base de datos
	public void crearSupervisor(Supervisor encuestador) {
		ContentValues cv = SupervisorHelper.crearSupervisorContentValues(encuestador);
		mDb.insert(MainDBConstants.SUPERVISOR_TABLE, null, cv);
	}
	//Limpiar la tabla de Supervisor de la base de datos
	public boolean borrarSupervisores() {
		return mDb.delete(MainDBConstants.SUPERVISOR_TABLE, null, null) > 0;
	}
	//Obtener un Supervisor de la base de datos
	public Supervisor getSupervisor(String filtro, String orden) throws SQLException {
		Supervisor mSupervisor = null;
		Cursor cursorSupervisor = crearCursor(MainDBConstants.SUPERVISOR_TABLE, filtro, null, orden);
		if (cursorSupervisor != null && cursorSupervisor.getCount() > 0) {
			cursorSupervisor.moveToFirst();
			mSupervisor=SupervisorHelper.crearSupervisor(cursorSupervisor);
		}
		if (!cursorSupervisor.isClosed()) cursorSupervisor.close();
		return mSupervisor;
	}
	//Obtener una lista de supervisores de la base de datos
	public List<Supervisor> getSupervisores(String filtro, String orden) throws SQLException {
		List<Supervisor> mSupervisores = new ArrayList<Supervisor>();
		Cursor cursorSupervisores = crearCursor(MainDBConstants.SUPERVISOR_TABLE, filtro, null, orden);
		if (cursorSupervisores != null && cursorSupervisores.getCount() > 0) {
			cursorSupervisores.moveToFirst();
			mSupervisores.clear();
			do{
				Supervisor mSupervisor = null;
				mSupervisor = SupervisorHelper.crearSupervisor(cursorSupervisores);
				mSupervisores.add(mSupervisor);
			} while (cursorSupervisores.moveToNext());
		}
		if (!cursorSupervisores.isClosed()) cursorSupervisores.close();
		return mSupervisores;
	}
	
	
	/**
	 * Metodos para encuestas en la base de datos
	 * 
	 * @param encuesta
	 *            Objeto Encuesta que contiene la informacion
	 *
	 */
	//Crear nuevo encuesta en la base de datos
	public void crearEncuesta(Encuesta encuesta) {
		ContentValues cv = EncuestaHelper.crearEncuestaContentValues(encuesta);
		mDb.insert(MainDBConstants.ENCUESTA_TABLE, null, cv);
	}
	//Editar encuesta existente en la base de datos
	public boolean editarEncuesta(Encuesta encuesta) {
		ContentValues cv = EncuestaHelper.crearEncuestaContentValues(encuesta);
		return mDb.update(MainDBConstants.ENCUESTA_TABLE, cv, MainDBConstants.ident + "='" 
				+ encuesta.getIdent()+"'", null) > 0;
	}
	//Limpiar la tabla de Encuesta de la base de datos
	public boolean borrarEncuestas() {
		return mDb.delete(MainDBConstants.ENCUESTA_TABLE, null, null) > 0;
	}
	//Obtener una Encuesta de la base de datos
	public Encuesta getEncuesta(String filtro, String orden) throws SQLException {
		Encuesta mEncuesta = null;
		Cursor cursorEncuesta = crearCursor(MainDBConstants.ENCUESTA_TABLE, filtro, null, orden);
		if (cursorEncuesta != null && cursorEncuesta.getCount() > 0) {
			cursorEncuesta.moveToFirst();
			mEncuesta=EncuestaHelper.crearEncuesta(cursorEncuesta);
			Segmento segmento = this.getSegmento(MainDBConstants.segmento + "='" +cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.segmento))+"'", null);
			mEncuesta.setSegmento(segmento);
			Encuestador encuestador = this.getEncuestador(MainDBConstants.identificador + " = '" +cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.encuestador+"'")), null);
			mEncuesta.setEncuestador(encuestador);
			Supervisor supervisor = this.getSupervisor(MainDBConstants.identificador + " = '" +cursorEncuesta.getString(cursorEncuesta.getColumnIndex(MainDBConstants.supervisor+"'")), null);
			mEncuesta.setSupervisor(supervisor);
		}
		if (!cursorEncuesta.isClosed()) cursorEncuesta.close();
		return mEncuesta;
	}
	//Obtener una lista de encuestas de la base de datos
	public List<Encuesta> getEncuestas(String filtro, String orden) throws SQLException {
		List<Encuesta> mEncuestas = new ArrayList<Encuesta>();
		Cursor cursorEncuestas = crearCursor(MainDBConstants.ENCUESTA_TABLE, filtro, null, orden);
		if (cursorEncuestas != null && cursorEncuestas.getCount() > 0) {
			cursorEncuestas.moveToFirst();
			mEncuestas.clear();
			do{
				Encuesta mEncuesta = null;
				mEncuesta = EncuestaHelper.crearEncuesta(cursorEncuestas);
				Segmento segmento = this.getSegmento(MainDBConstants.segmento + "='" +cursorEncuestas.getString(cursorEncuestas.getColumnIndex(MainDBConstants.segmento))+"'", null);
				mEncuesta.setSegmento(segmento);
				if (cursorEncuestas.getString(cursorEncuestas.getColumnIndex(MainDBConstants.encuestador))!=null) {
					Encuestador encuestador = this.getEncuestador(MainDBConstants.identificador + " = '" +cursorEncuestas.getString(cursorEncuestas.getColumnIndex(MainDBConstants.encuestador))+"'", null);
					mEncuesta.setEncuestador(encuestador);
				}
				Supervisor supervisor = this.getSupervisor(MainDBConstants.identificador + " = '" +cursorEncuestas.getString(cursorEncuestas.getColumnIndex(MainDBConstants.supervisor))+"'", null);
				mEncuesta.setSupervisor(supervisor);
				mEncuestas.add(mEncuesta);
			} while (cursorEncuestas.moveToNext());
		}
		if (!cursorEncuestas.isClosed()) cursorEncuestas.close();
		return mEncuestas;
	}
	
    //Seleccionar ultima encuesta
    public Integer selectUltimaEncuestaSegmento(String segmento) {
    	Integer vis = 0;
    	Cursor cursor = mDb.rawQuery("select max(numEncuesta) from encuestas where segmento = '"+segmento+"'", null);
    	if (cursor != null && cursor.getCount() > 0) {
    		cursor.moveToFirst();
    		vis = cursor.getInt(0);
    	}
    	if (!cursor.isClosed()) cursor.close();
        return vis;
    }
	
	/**
	 * Metodos para mensajes en la base de datos
	 * 
	 * @param mensaje
	 *            Objeto MessageResource que contiene la informacion
	 *
	 */
	//Crear nuevo MessageResource en la base de datos
	public void crearMessageResource(MessageResource mensaje) {
		ContentValues cv = MessageResourceHelper.crearMessageResourceValues(mensaje);
		mDb.insert(MainDBConstants.MESSAGES_TABLE, null, cv);
	}
	//Editar MessageResource existente en la base de datos
	public boolean editarMessageResource(MessageResource mensaje) {
		ContentValues cv = MessageResourceHelper.crearMessageResourceValues(mensaje);
		return mDb.update(MainDBConstants.MESSAGES_TABLE , cv, MainDBConstants.messageKey + "='" 
				+ mensaje.getMessageKey() + "'", null) > 0;
	}
	//Limpiar la tabla de MessageResource de la base de datos
	public boolean borrarMessageResource() {
		return mDb.delete(MainDBConstants.MESSAGES_TABLE, null, null) > 0;
	}
	//Obtener un MessageResource de la base de datos
	public MessageResource getMessageResource(String filtro, String orden) throws SQLException {
		MessageResource mMessageResource = null;
		Cursor cursorMessageResource = crearCursor(MainDBConstants.MESSAGES_TABLE , filtro, null, orden);
		if (cursorMessageResource != null && cursorMessageResource.getCount() > 0) {
			cursorMessageResource.moveToFirst();
			mMessageResource=MessageResourceHelper.crearMessageResource(cursorMessageResource);
		}
		if (!cursorMessageResource.isClosed()) cursorMessageResource.close();
		return mMessageResource;
	}
	//Obtener una lista de MessageResource de la base de datos
	public List<MessageResource> getMessageResources(String filtro, String orden) throws SQLException {
		List<MessageResource> mMessageResources = new ArrayList<MessageResource>();
		Cursor cursorMessageResources = crearCursor(MainDBConstants.MESSAGES_TABLE, filtro, null, orden);
		if (cursorMessageResources != null && cursorMessageResources.getCount() > 0) {
			cursorMessageResources.moveToFirst();
			mMessageResources.clear();
			do{
				MessageResource mMessageResource = null;
				mMessageResource = MessageResourceHelper.crearMessageResource(cursorMessageResources);
				mMessageResources.add(mMessageResource);
			} while (cursorMessageResources.moveToNext());
		}
		if (!cursorMessageResources.isClosed()) cursorMessageResources.close();
		return mMessageResources;
	}
	
	//Obtener el numero de registros de una tabla
    public Integer getNumeroRegistros(String tabla) {
    	Integer total = 0;
    	Cursor cursor = mDb.rawQuery("select * from " + tabla, null);
    	total = cursor.getCount();
    	if (!cursor.isClosed()) cursor.close();
        return total;
    }
	
	public Boolean verificarData() throws SQLException{
		Cursor c = null;
		c = crearCursor(MainDBConstants.ENCUESTA_TABLE, MainDBConstants.estado + "='"  + Constants.STATUS_NOT_SUBMITTED+ "' or " + MainDBConstants.estado + "='"  + Constants.STATUS_NOT_FINALIZED+ "'" , null, null);
		if (c != null && c.getCount()>0) {c.close();return true;}
		c.close();
		return false;
	}
}