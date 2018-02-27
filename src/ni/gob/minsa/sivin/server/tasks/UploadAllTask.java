package ni.gob.minsa.sivin.server.tasks;

import java.util.ArrayList;
import java.util.List;


import org.springframework.http.HttpAuthentication;
import org.springframework.http.HttpBasicAuthentication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import android.content.Context;
import android.util.Log;
import ni.gob.minsa.sivin.database.SivinAdapter;
import ni.gob.minsa.sivin.domain.Encuesta;
import ni.gob.minsa.sivin.listeners.UploadListener;
import ni.gob.minsa.sivin.utils.Constants;
import ni.gob.minsa.sivin.utils.MainDBConstants;

public class UploadAllTask extends UploadTask {
	
	private final Context mContext;

	public UploadAllTask(Context context) {
		mContext = context;
	}

	protected static final String TAG = UploadAllTask.class.getSimpleName();
    private static final String TOTAL_TASK = "1";
	private SivinAdapter sivinAdapter = null;
	private List<Encuesta> mEncuestas = new ArrayList<Encuesta>();
	


	private String url = null;
	private String username = null;
	private String password = null;
	private String error = null;
	protected UploadListener mStateListener;
	public static final int ENCUESTA = 0;
	
	

	@Override
	protected String doInBackground(String... values) {
		url = values[0];
		username = values[1];
		password = values[2];

		try {
			publishProgress("Obteniendo registros de la base de datos", "1", "2");
			sivinAdapter = new SivinAdapter(mContext, password, false,false);
			sivinAdapter.open();
			String filtro = MainDBConstants.estado + "='" + Constants.STATUS_NOT_SUBMITTED + "'";
			mEncuestas = sivinAdapter.getEncuestas(filtro, null);
			publishProgress("Datos completos!", "2", "2");
			if(mEncuestas.size()>0) {
				actualizarBaseDatos(Constants.STATUS_SUBMITTED, ENCUESTA);
				error = cargarEncuestas(url, username, password);
				if (!error.matches("Datos recibidos!")){
					actualizarBaseDatos(Constants.STATUS_NOT_SUBMITTED, ENCUESTA);
					return error;
				}
			}
			else {
				return "no hay datos";
			}
            sivinAdapter.close();
		} catch (Exception e1) {
			sivinAdapter.close();
			e1.printStackTrace();
			return e1.getLocalizedMessage();
		}
		return error;
	}
	
	private void actualizarBaseDatos(char estado, int opcion) {
		int c;
		if(opcion==ENCUESTA){
			c = mEncuestas.size();
			if(c>0){
				for (Encuesta encuesta : mEncuestas) {
					encuesta.setEstado(estado);
					sivinAdapter.editarEncuesta(encuesta);
					publishProgress("Actualizando encuestas base de datos local", Integer.valueOf(mEncuestas.indexOf(encuesta)).toString(), Integer
							.valueOf(c).toString());
				}
			}
		}
	}

	
	/***************************************************/
	/********************* Encuesta ********************/
	/***************************************************/
    // url, username, password
    protected String cargarEncuestas(String url, String username, 
    		String password) throws Exception {
    	try {
    		if(mEncuestas.size()>0){
    			// La URL de la solicitud POST
    			publishProgress("Enviando encuestas!", "1", TOTAL_TASK);
    			final String urlRequest = url + "/movil/encuestas";
    			Encuesta[] envio = mEncuestas.toArray(new Encuesta[mEncuestas.size()]);
    			HttpHeaders requestHeaders = new HttpHeaders();
    			HttpAuthentication authHeader = new HttpBasicAuthentication(username, password);
    			requestHeaders.setContentType(MediaType.APPLICATION_JSON);
    			requestHeaders.setAuthorization(authHeader);
    			HttpEntity<Encuesta[]> requestEntity = 
    					new HttpEntity<Encuesta[]>(envio, requestHeaders);
    					RestTemplate restTemplate = new RestTemplate();
    					restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
    					restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
    					// Hace la solicitud a la red, pone la vivienda y espera un mensaje de respuesta del servidor
    					ResponseEntity<String> response = restTemplate.exchange(urlRequest, HttpMethod.POST, requestEntity,
    							String.class);
    					return response.getBody();
    		}
    		else{
    			return "Datos recibidos!";
    		}
    	} catch (Exception e) {
    		Log.e(TAG, e.getMessage(), e);
    		return e.getMessage();
    	}
    }
}