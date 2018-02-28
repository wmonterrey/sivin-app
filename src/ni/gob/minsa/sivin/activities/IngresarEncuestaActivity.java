package ni.gob.minsa.sivin.activities;

import java.util.ArrayList;
import java.util.List;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import ni.gob.minsa.sivin.AbstractAsyncListActivity;
import ni.gob.minsa.sivin.MainActivity;
import ni.gob.minsa.sivin.R;
import ni.gob.minsa.sivin.database.SivinAdapter;
import ni.gob.minsa.sivin.domain.Encuesta;
import ni.gob.minsa.sivin.domain.Segmento;
import ni.gob.minsa.sivin.preferences.PreferencesActivity;
import ni.gob.minsa.sivin.utils.Constants;
import ni.gob.minsa.sivin.utils.MainDBConstants;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import ni.gob.minsa.sivin.SivinApplication;
import ni.gob.minsa.sivin.adapters.EncuestaAdapter;

public class IngresarEncuestaActivity extends AbstractAsyncListActivity {
	

	private SivinAdapter sivinAdapter;
	private List<Encuesta> mEncuestas = new ArrayList<Encuesta>();
	private SharedPreferences settings;
	private String mSegmento;
    private Segmento segmento = null;
    private Encuesta encuesta = null;
    private TextView mLabelTitle;
    private Button mAddButton;
    private AlertDialog alertDialog;
    private static final int ADD_ENC = 1;
    private static final int EDIT_ENC = 2;
	

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.selec_enc_list);

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			ActionBar actionBar = getActionBar();
			actionBar.setDisplayHomeAsUpEnabled(true);
		}
		String mPass = ((SivinApplication) this.getApplication()).getPassApp();
		sivinAdapter = new SivinAdapter(this.getApplicationContext(),mPass,false,false);
		settings =
				PreferenceManager.getDefaultSharedPreferences(this);
		mSegmento =
				settings.getString(PreferencesActivity.KEY_CODE_SEGMENTO,
						null);
		mLabelTitle = (TextView) findViewById(R.id.label_segmento);
		mAddButton = (Button) findViewById(R.id.add_button);
		mAddButton.setOnClickListener(new View.OnClickListener()  {
			@Override
			public void onClick(View v) {
				createDialog(ADD_ENC);
			}
		});
		new FetchEncuestasSegmentoTask().execute(mSegmento);
	}
	
	
	@Override
	protected void onListItemClick(ListView listView, View view, int position,
			long id) {
		encuesta = (Encuesta) getListAdapter().getItem(position);
		createDialog(EDIT_ENC);
	}
	
	private void createDialog(final int accion) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        switch(accion){
            case ADD_ENC:
                builder.setTitle(this.getString(R.string.confirm));
                builder.setMessage(getString(R.string.confirm_enc_add));
                builder.setPositiveButton(this.getString(R.string.yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Bundle arguments = new Bundle();
        		        if (segmento!=null) arguments.putSerializable(Constants.SEGMENTO , segmento);
        		        arguments.putSerializable(Constants.ENCUESTA , new Encuesta());
                        Intent i = new Intent(getApplicationContext(),
                        		MenuEncuestaActivity.class);
                        i.putExtras(arguments);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                    }
                });
                builder.setNegativeButton(this.getString(R.string.no), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing
                        dialog.dismiss();
                    }
                });
                break;
            case EDIT_ENC:
                builder.setTitle(this.getString(R.string.confirm));
                builder.setMessage(getString(R.string.confirm_enc_edit));
                builder.setPositiveButton(this.getString(R.string.yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Bundle arguments = new Bundle();
        		        if (segmento!=null) arguments.putSerializable(Constants.SEGMENTO , segmento);
        		        arguments.putSerializable(Constants.ENCUESTA , encuesta);
        		        arguments.putInt(Constants.VIVIENDA, encuesta.getNumEncuesta());
                        Intent i = new Intent(getApplicationContext(),
                        		MenuEncuestaActivity.class);
                        i.putExtras(arguments);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                    }
                });
                builder.setNegativeButton(this.getString(R.string.no), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing
                        dialog.dismiss();
                    }
                });
                break;  
            default:
                break;
        }
        alertDialog = builder.create();
        alertDialog.show();
    }
    
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.general, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getItemId()==android.R.id.home){
			Intent i = new Intent(getApplicationContext(),
					MainActivity.class);
			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(i);
			finish();
			return true;
		}
		else if(item.getItemId()==R.id.MENU_BACK){
			Intent i = new Intent(getApplicationContext(),
					MainActivity.class);
			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(i);
			finish();
			return true;
		}
		else if(item.getItemId()==R.id.MENU_HOME){
			Intent i = new Intent(getApplicationContext(),
					MainActivity.class);
			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(i);
			finish();
			return true;
		}
		else{
			return super.onOptionsItemSelected(item);
		}
	}
	
	
	// ***************************************
	// Private classes
	// ***************************************
	private class FetchEncuestasSegmentoTask extends AsyncTask<String, Void, String> {
		@Override
		protected void onPreExecute() {
			// before the request begins, show a progress indicator
			showLoadingProgressDialog();
		}

		@Override
		protected String doInBackground(String... values) {
			String strSegmento = values[0];
			try {
				sivinAdapter.open();
				segmento = sivinAdapter.getSegmento(MainDBConstants.segmento + " = '"+ strSegmento + "'", null);
				mEncuestas = sivinAdapter.getEncuestas(MainDBConstants.segmento + " = '"+ strSegmento + "'", MainDBConstants.numEncuesta);
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
			if(segmento==null) {
				Toast.makeText(getApplicationContext(), resultado,Toast.LENGTH_LONG).show();
			}else {
				mLabelTitle.setTextColor(Color.BLUE);
				mLabelTitle.setText(getString(R.string.enc_segmento)+":"+segmento.getCodigo()+"\n"+
										getString(R.string.enc_comunidad)+":"+segmento.getComunidad()+"\n"+
											getString(R.string.enc_municipio)+":"+segmento.getMunicipio()+"\n"+
													getString(R.string.enc_departamento)+":"+segmento.getDepartamento());
			}
			EncuestaAdapter adapter = new EncuestaAdapter(this, R.layout.encuesta_list_item, mEncuestas);
			setListAdapter(adapter);
			if (mEncuestas.isEmpty()) Toast.makeText(getApplicationContext(), getString(R.string.no_items),Toast.LENGTH_LONG).show();
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
