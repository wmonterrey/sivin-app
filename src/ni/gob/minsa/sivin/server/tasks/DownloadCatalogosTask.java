package ni.gob.minsa.sivin.server.tasks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

import org.springframework.http.HttpAuthentication;
import org.springframework.http.HttpBasicAuthentication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import android.content.Context;
import android.util.Log;
import ni.gob.minsa.sivin.database.SivinAdapter;
import ni.gob.minsa.sivin.domain.Encuestador;
import ni.gob.minsa.sivin.domain.MessageResource;
import ni.gob.minsa.sivin.domain.Supervisor;



public class DownloadCatalogosTask extends DownloadTask {
	
	private final Context mContext;
	
	public DownloadCatalogosTask(Context context) {
		mContext = context;
	}
	
	protected static final String TAG = DownloadCatalogosTask.class.getSimpleName();
	private SivinAdapter sivinAdapter = null;
	private List<MessageResource> mCatalogos = null;
	private List<Encuestador> mEncuestadores = null;
	private List<Supervisor> mSupervisores = null;
	
	public static final String CATALOGOS = "1";
	public static final String ENCUESTADORES = "2";
	public static final String SUPERVISORES = "3";
	private static final String TOTAL_TASK = "3";

	private String error = null;
	private String url = null;
	private String username = null;
	private String password = null;
	private int v =0;

	@Override
	protected String doInBackground(String... values) {
		url = values[0];
		username = values[1];
		password = values[2];
		
		try {
			error = descargarCatalogos();
			if (error!=null) return error;
		} catch (Exception e) {
			// Regresa error al descargar
			e.printStackTrace();
			return e.getLocalizedMessage();
		}
		publishProgress("Abriendo base de datos...","1","1");
		sivinAdapter = new SivinAdapter(mContext, password, false,false);
		sivinAdapter.open();
		//Borrar los datos de la base de datos
		sivinAdapter.borrarMessageResource();
        
		try {
			if (mCatalogos != null){
				v = mCatalogos.size();
				ListIterator<MessageResource> iter = mCatalogos.listIterator();
				while (iter.hasNext()){
					sivinAdapter.crearMessageResource(iter.next());
					publishProgress("Insertando catalogos en la base de datos...", Integer.valueOf(iter.nextIndex()).toString(), Integer
							.valueOf(v).toString());
				}
			}

		} catch (Exception e) {
			// Regresa error al insertar
			e.printStackTrace();
			sivinAdapter.close();
			return e.getLocalizedMessage();
		}
		
		try {
			error = descargarEncuestadores();
			if (error!=null) return error;
		} catch (Exception e) {
			// Regresa error al descargar
			e.printStackTrace();
			return e.getLocalizedMessage();
		}
		
		publishProgress("Abriendo base de datos...","2","2");
		//Borrar los datos de la base de datos de encuestadores
		sivinAdapter.borrarEncuestadores();
        
		try {
			if (mEncuestadores != null){
				v = mEncuestadores.size();
				ListIterator<Encuestador> iter = mEncuestadores.listIterator();
				while (iter.hasNext()){
					sivinAdapter.crearEncuestador(iter.next());
					publishProgress("Insertando encuestadores en la base de datos...", Integer.valueOf(iter.nextIndex()).toString(), Integer
							.valueOf(v).toString());
				}
			}

		} catch (Exception e) {
			// Regresa error al insertar
			e.printStackTrace();
			sivinAdapter.close();
			return e.getLocalizedMessage();
		}
		
		try {
			error = descargarSupervisores();
			if (error!=null) return error;
		} catch (Exception e) {
			// Regresa error al descargar
			e.printStackTrace();
			return e.getLocalizedMessage();
		}
		
		publishProgress("Abriendo base de datos...","3","3");
		//Borrar los datos de la base de datos de supervisores
		sivinAdapter.borrarSupervisores();
        
		try {
			if (mSupervisores != null){
				v = mSupervisores.size();
				ListIterator<Supervisor> iter = mSupervisores.listIterator();
				while (iter.hasNext()){
					sivinAdapter.crearSupervisor(iter.next());
					publishProgress("Insertando supervisores en la base de datos...", Integer.valueOf(iter.nextIndex()).toString(), Integer
							.valueOf(v).toString());
				}
			}

		} catch (Exception e) {
			// Regresa error al insertar
			e.printStackTrace();
			sivinAdapter.close();
			return e.getLocalizedMessage();
		}
		
		
		sivinAdapter.close();
		return error;
	}

    // url, username, password
    protected String descargarCatalogos() throws Exception {
        try {
            // The URL for making the GET request
            String urlRequest;
            // Set the Accept header for "application/json"
            HttpAuthentication authHeader = new HttpBasicAuthentication(username, password);
            HttpHeaders requestHeaders = new HttpHeaders();
            List<MediaType> acceptableMediaTypes = new ArrayList<MediaType>();
            acceptableMediaTypes.add(MediaType.APPLICATION_JSON);
            requestHeaders.setAccept(acceptableMediaTypes);
            requestHeaders.setAuthorization(authHeader);
            // Populate the headers in an HttpEntity object to use for the request
            HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);
            // Create a new RestTemplate instance
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
            //Descargar catalogos
            urlRequest = url + "/movil/catalogos";
            publishProgress("Solicitando catalogos",CATALOGOS,TOTAL_TASK);
            // Perform the HTTP GET request
            ResponseEntity<MessageResource[]> responseEntityMessageResource = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
            		MessageResource[].class);
            // convert the array to a list and return it
            mCatalogos = Arrays.asList(responseEntityMessageResource.getBody());
            responseEntityMessageResource = null;
            return null;
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
            return e.getLocalizedMessage();
        }
    }
    
    
    
    // url, username, password
    protected String descargarEncuestadores() throws Exception {
        try {
            // The URL for making the GET request
            String urlRequest;
            // Set the Accept header for "application/json"
            HttpAuthentication authHeader = new HttpBasicAuthentication(username, password);
            HttpHeaders requestHeaders = new HttpHeaders();
            List<MediaType> acceptableMediaTypes = new ArrayList<MediaType>();
            acceptableMediaTypes.add(MediaType.APPLICATION_JSON);
            requestHeaders.setAccept(acceptableMediaTypes);
            requestHeaders.setAuthorization(authHeader);
            // Populate the headers in an HttpEntity object to use for the request
            HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);
            // Create a new RestTemplate instance
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
            //Descargar encuestadores
            urlRequest = url + "/movil/encuestadores";
            publishProgress("Solicitando encuestadores",ENCUESTADORES,TOTAL_TASK);
            // Perform the HTTP GET request
            ResponseEntity<Encuestador[]> responseEntityMessageResource = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
            		Encuestador[].class);
            // convert the array to a list and return it
            mEncuestadores = Arrays.asList(responseEntityMessageResource.getBody());
            responseEntityMessageResource = null;
            return null;
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
            return e.getLocalizedMessage();
        }
    }
    
    
    // url, username, password
    protected String descargarSupervisores() throws Exception {
        try {
            // The URL for making the GET request
            String urlRequest;
            // Set the Accept header for "application/json"
            HttpAuthentication authHeader = new HttpBasicAuthentication(username, password);
            HttpHeaders requestHeaders = new HttpHeaders();
            List<MediaType> acceptableMediaTypes = new ArrayList<MediaType>();
            acceptableMediaTypes.add(MediaType.APPLICATION_JSON);
            requestHeaders.setAccept(acceptableMediaTypes);
            requestHeaders.setAuthorization(authHeader);
            // Populate the headers in an HttpEntity object to use for the request
            HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);
            // Create a new RestTemplate instance
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
            //Descargar encuestadores
            urlRequest = url + "/movil/supervisores";
            publishProgress("Solicitando supervisores",SUPERVISORES,TOTAL_TASK);
            // Perform the HTTP GET request
            ResponseEntity<Supervisor[]> responseEntityMessageResource = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
            		Supervisor[].class);
            // convert the array to a list and return it
            mSupervisores = Arrays.asList(responseEntityMessageResource.getBody());
            responseEntityMessageResource = null;
            return null;
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
            return e.getLocalizedMessage();
        }
    }
}
