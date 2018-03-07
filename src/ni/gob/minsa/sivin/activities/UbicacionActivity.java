package ni.gob.minsa.sivin.activities;

import java.io.File;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import ni.gob.minsa.sivin.AbstractAsyncActivity;
import ni.gob.minsa.sivin.R;
import ni.gob.minsa.sivin.database.SivinAdapter;
import ni.gob.minsa.sivin.domain.Encuesta;
import ni.gob.minsa.sivin.domain.Segmento;
import ni.gob.minsa.sivin.utils.Constants;
import ni.gob.minsa.sivin.utils.DeviceInfo;
import ni.gob.minsa.sivin.utils.FileUtils;
import ni.gob.minsa.sivin.utils.GPSTracker;
import ni.gob.minsa.sivin.utils.MainDBConstants;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.content.Intent;
import ni.gob.minsa.sivin.SivinApplication;

public class UbicacionActivity extends AbstractAsyncActivity {
	

	private SivinAdapter sivinAdapter;
	private static Encuesta mEncuesta = new Encuesta();
	private static Segmento mSegmento = new Segmento();
	private TextView textViewEncuesta;
	private TextView textViewLatitud;
	private TextView textViewLongitud;
	private Button mSaveButton;
	private DeviceInfo infoMovil;
    private GPSTracker gps;
	

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ubicacion);

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			ActionBar actionBar = getActionBar();
			actionBar.setDisplayHomeAsUpEnabled(true);
		}
		String mPass = ((SivinApplication) this.getApplication()).getPassApp();
		sivinAdapter = new SivinAdapter(this.getApplicationContext(),mPass,false,false);
		infoMovil = new DeviceInfo(UbicacionActivity.this);
		gps = new GPSTracker(UbicacionActivity.this);
		//Aca se recupera los datos de la encuesta y el segmento
		mSegmento = (Segmento) getIntent().getExtras().getSerializable(Constants.SEGMENTO);
		mEncuesta = (Encuesta) getIntent().getExtras().getSerializable(Constants.ENCUESTA);
		
		textViewEncuesta = (TextView) findViewById(R.id.label_encuesta);
		textViewLatitud = (TextView) findViewById(R.id.label_latitud);
		textViewLongitud = (TextView) findViewById(R.id.label_longitud);
		textViewEncuesta.setText(textViewEncuesta.getText()+"\n"+mEncuesta.getJefeFamilia()+"\n"+getString(R.string.enc_num)+": "+mEncuesta.getNumEncuesta()+"\n\n");
		
		textViewLatitud.setText(textViewLatitud.getText()+"\n"+mEncuesta.getLatitud()+"\n\n");
		textViewLongitud.setText(textViewLongitud.getText()+"\n"+mEncuesta.getLongitud()+"\n\n");
		
		mSaveButton = (Button) findViewById(R.id.save_button);
		mSaveButton.setOnClickListener(new View.OnClickListener()  {
			@Override
			public void onClick(View v) {
				new SaveDataTask().execute();
			}
		});
		mSaveButton.setEnabled(false);
		
		if(gps.canGetLocation()){
			textViewLatitud.setText(getString(R.string.latitud)+"\n"+String.valueOf(gps.getLatitude()));
			textViewLongitud.setText(getString(R.string.longitud)+"\n"+String.valueOf(gps.getLongitude()));
			mSaveButton.setEnabled(true);
    	}

	}

	
	
	// ***************************************
	 	// Private classes
	 	// ************************************
	 	private class SaveDataTask extends AsyncTask<String, Void, String> {
	 		@Override
	 		protected void onPreExecute() {
	 			// before the request begins, show a progress indicator
	 			showLoadingProgressDialog();
	 		}

	 		@Override
	 		protected String doInBackground(String... values) {
	 			try {
	 				sivinAdapter.open();
	 				//mEncuesta.setEstado(Constants.STATUS_NOT_SUBMITTED);
	 				mEncuesta.setDeviceid(infoMovil.getDeviceId());
	 				mEncuesta.setLatitud(String.valueOf(gps.getLatitude()));
	 				mEncuesta.setLongitud(String.valueOf(gps.getLongitude()));
	 				if (mEncuesta.getEstado()==Constants.STATUS_SUBMITTED) {
	 					mEncuesta.setEstado(Constants.STATUS_NOT_SUBMITTED);
	 	            }
	 	            else if (mEncuesta.getEstado()==Constants.STATUS_NOT_SUBMITTED) {
	 	            	mEncuesta.setEstado(Constants.STATUS_NOT_SUBMITTED);
	 	            }
	 	            else {
	 	            	mEncuesta.setEstado(Constants.STATUS_NOT_FINALIZED);
	 	            }
	 				sivinAdapter.editarEncuesta(mEncuesta);
	 				sivinAdapter.close();
	 	            FileUtils.createFolder(FileUtils.BACKUP_PATH);
	 	            File databaseFile = new File(FileUtils.DATABASE_PATH + "/" +MainDBConstants.DATABASE_NAME);
	 	            File databaseFileBackup = new File(FileUtils.BACKUP_FILE);
	 	            
	 	            FileUtils.copy(databaseFile, databaseFileBackup);
	 			} catch (Exception e) {
	 				Log.e(TAG, e.getLocalizedMessage(), e);
	 				return "error";
	 			}
	 			return "exito";
	 		}

	 		protected void onPostExecute(String resultado) {
	 			// after the network request completes, hide the progress indicator
	 			dismissProgressDialog();
	 			showResult(resultado);
	 		}

	 	}

	 	// ***************************************
	 	// Private methods
	 	// ***************************************
	 	private void showResult(String resultado) {
	 		if(resultado.equals("error")) {
				Toast.makeText(getApplicationContext(), resultado,Toast.LENGTH_LONG).show();
				Toast.makeText(getApplicationContext(), getString(R.string.bd_error),Toast.LENGTH_LONG).show();
				if (sivinAdapter != null)
	                sivinAdapter.close();
				finish();
			}
	 		Bundle arguments = new Bundle();
	 		Intent i;
            if (mSegmento!=null) arguments.putSerializable(Constants.SEGMENTO , mSegmento);
            if (mEncuesta!=null) arguments.putSerializable(Constants.ENCUESTA , mEncuesta);
            i = new Intent(getApplicationContext(),
                    MenuEncuestaActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.putExtras(arguments);
            startActivity(i);
            Toast toast = Toast.makeText(getApplicationContext(),getString(R.string.success),Toast.LENGTH_LONG);
            toast.show();
            finish();
	 	}	
}
