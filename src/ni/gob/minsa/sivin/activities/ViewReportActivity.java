package ni.gob.minsa.sivin.activities;


import java.util.ArrayList;
import java.util.List;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import ni.gob.minsa.sivin.AbstractAsyncListActivity;
import ni.gob.minsa.sivin.R;
import ni.gob.minsa.sivin.SivinApplication;
import ni.gob.minsa.sivin.adapters.DatoAdapter;
import ni.gob.minsa.sivin.database.SivinAdapter;
import ni.gob.minsa.sivin.utils.Constants;
import ni.gob.minsa.sivin.utils.MainDBConstants;

public class ViewReportActivity extends AbstractAsyncListActivity {

    private String reportName;
    private SivinAdapter sivinAdapter;
    private TextView mLabelTitle;
    private List<Object[]> mResultados = new ArrayList<Object[]>();
    private Integer numEncuestas;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.reporte_datos);
		mLabelTitle = (TextView) findViewById(R.id.label_header);
		reportName = getIntent().getStringExtra(Constants.REPORTE_NAME);
		mLabelTitle.setText(reportName);
		String mPass = ((SivinApplication) this.getApplication()).getPassApp();
		sivinAdapter = new SivinAdapter(this.getApplicationContext(),mPass,false,false);
		new FetchDataTask().execute(reportName);
	}
	
	
	// ***************************************
	// Private classes
	// ***************************************
	private class FetchDataTask extends AsyncTask<String, Void, String> {
		@Override
		protected void onPreExecute() {
			// before the request begins, show a progress indicator
			showLoadingProgressDialog();
		}

		@Override
		protected String doInBackground(String... values) {
			String strReporte = values[0];
			try {
				sivinAdapter.open();
				numEncuestas = sivinAdapter.getNumeroRegistros(MainDBConstants.ENCUESTA_TABLE);
				if(strReporte.equals(Constants.REPORTE_1)) {
					mResultados = sivinAdapter.getNumeroRegistrosFecha();
				}
				else if(strReporte.equals(Constants.REPORTE_2)) {
					mResultados = sivinAdapter.getNumeroRegistrosSegmento();
				}
				else if(strReporte.equals(Constants.REPORTE_3)) {
					mResultados = sivinAdapter.getNumeroRegistrosEstado();
				}
				sivinAdapter.close();
			} catch (Exception e) {
				Log.e(TAG, e.getLocalizedMessage(), e);
				return "error";
			}
			return "exito";
		}

		protected void onPostExecute(String resultado) {
			dismissProgressDialog();
			showResult(resultado);
		}

	}

	// ***************************************
	// Private methods
	// ***************************************
	private void showResult(String resultado) {
		if(resultado.equals("exito")) {
			DatoAdapter adapter = new DatoAdapter(this, R.layout.dato_list_item, mResultados);
			setListAdapter(adapter);
			mLabelTitle.setText(reportName+"\n"+getString(R.string.total)+": "+numEncuestas);
			if (mResultados.isEmpty()) Toast.makeText(getApplicationContext(), getString(R.string.no_items),Toast.LENGTH_LONG).show();
		}
		else {
			Toast.makeText(getApplicationContext(), resultado,Toast.LENGTH_LONG).show();
			Toast.makeText(getApplicationContext(), getString(R.string.bd_error),Toast.LENGTH_LONG).show();
			if (sivinAdapter != null)
                sivinAdapter.close(); 
			finish();
		}
	}		

}
