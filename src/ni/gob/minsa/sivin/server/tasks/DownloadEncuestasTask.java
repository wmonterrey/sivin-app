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
import ni.gob.minsa.sivin.domain.Encuesta;



public class DownloadEncuestasTask extends DownloadTask {
	
	private final Context mContext;
	
	public DownloadEncuestasTask(Context context) {
		mContext = context;
	}
	
	protected static final String TAG = DownloadEncuestasTask.class.getSimpleName();
	private SivinAdapter sivinAdapter = null;
	private List<Encuesta> mEncuestas = null;

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
			error = descargarDatosGenerales();
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
		sivinAdapter.borrarEncuestas();
		
		try {
			if (mEncuestas != null){
				v = mEncuestas.size();
				ListIterator<Encuesta> iter = mEncuestas.listIterator();
				while (iter.hasNext()){
					sivinAdapter.crearEncuesta(iter.next());
					publishProgress("Insertando encuestas en la base de datos...", Integer.valueOf(iter.nextIndex()).toString(), Integer
							.valueOf(v).toString());
				}
				mEncuestas = null;
			}
		} catch (Exception e) {
			// Regresa error al insertar
			e.printStackTrace();
			sivinAdapter.close();
			return e.getLocalizedMessage();
		}
		finally{
			sivinAdapter.close();
		}
		return error;
	}

    // url, username, password
    protected String descargarDatosGenerales() throws Exception {
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
            //Descargar estudios
            urlRequest = url + "/movil/encuestas/{username}";
            publishProgress("Solicitando encuestas","1","1");
            // Perform the HTTP GET request
            ResponseEntity<Encuesta[]> responseEntityEncuestas = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
            		Encuesta[].class,username);
            // convert the array to a list and return it
            mEncuestas = Arrays.asList(responseEntityEncuestas.getBody());
            responseEntityEncuestas = null;
            return null;
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
            return e.getLocalizedMessage();
        }
    }
    
}
