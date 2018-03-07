package ni.gob.minsa.sivin;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import ni.gob.minsa.sivin.activities.IngresarEncuestaActivity;
import ni.gob.minsa.sivin.activities.ListaSegmentosActivity;
import ni.gob.minsa.sivin.activities.ViewDataActivity;
import ni.gob.minsa.sivin.adapters.MainActivityAdapter;
import ni.gob.minsa.sivin.database.SivinAdapter;
import ni.gob.minsa.sivin.domain.Segmento;
import ni.gob.minsa.sivin.preferences.PreferencesActivity;
import ni.gob.minsa.sivin.server.DownloadCatalogosActivity;
import ni.gob.minsa.sivin.server.DownloadEncuestasActivity;
import ni.gob.minsa.sivin.server.UploadEncuestasActivity;
import ni.gob.minsa.sivin.utils.Constants;
import ni.gob.minsa.sivin.utils.MainDBConstants;

public class MainActivity extends AbstractAsyncListActivity {
	
	private static final int UPDATE_EQUIPO = 11;
	private static final int UPDATE_SERVER = 12;
	private static final int UPDATE_CATALOG = 13;

	
	private static final int EXIT = 1;
	private static final int DOWNLOAD = 2;
	private static final int UPLOAD = 3;
	private static final int VERIFY = 4;
	private static final int CATALOG = 5;
	private static final int CONFIRM_PIN = 6;
	
	private AlertDialog alertDialog;
	private SivinAdapter sivinAdapter;
	private SharedPreferences settings;
	private String mSegmento;
    private Segmento segmento = null;
    private TextView mLabelFooter;
    private String[] menu_main;
    private Integer numCatalogos;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		menu_main = getResources().getStringArray(R.array.menu_main);
		
		String mPass = ((SivinApplication) this.getApplication()).getPassApp();
		sivinAdapter = new SivinAdapter(this.getApplicationContext(),mPass,false,false);
		settings =
				PreferenceManager.getDefaultSharedPreferences(this);
		mSegmento =
				settings.getString(PreferencesActivity.KEY_CODE_SEGMENTO,
						null);
		mLabelFooter = (TextView) findViewById(R.id.label_foot);
		if(mSegmento==null) {
			mLabelFooter.setText(getString(R.string.default_segmento));
			Intent i = new Intent(getApplicationContext(),
					ListaSegmentosActivity.class);
			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(i);
			finish();
		}
		else {
			new FetchDataSegmentoTask().execute(mSegmento);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.MENU_EXIT:
			createDialog(EXIT);
			return true;
		case R.id.MENU_PREFERENCES:
			Intent ig = new Intent(this, PreferencesActivity.class);
			startActivity(ig);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	@Override
	protected void onListItemClick(ListView listView, View view, int position,
			long id) {
		// Opcion de menu seleccionada
		Intent i;
		switch(position){
		case 0: 
			if(segmento==null) {
				Toast.makeText(getApplicationContext(), getString(R.string.default_segmento),Toast.LENGTH_LONG).show();
				break;
			}
			i = new Intent(getApplicationContext(),
					IngresarEncuestaActivity.class);
			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(i);
			break;
		case 1: 
			i = new Intent(getApplicationContext(),
					ListaSegmentosActivity.class);
			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(i);
			finish();
			break;
		case 2: 
			createDialog(DOWNLOAD);
			break;	
		case 3: 
			createDialog(UPLOAD);
			break;	
		case 4: 
			createDialog(CATALOG);
			break;		
		case 5: 
			i = new Intent(getApplicationContext(),
					ViewDataActivity.class);
			startActivity(i);
			break;			
		default: 
			String s = (String) getListAdapter().getItem(position);
			Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
		}
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}


	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	public void onBackPressed (){
		createDialog(EXIT);
	}

	private void createDialog(int dialog) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		switch(dialog){
		case EXIT:
			builder.setTitle(this.getString(R.string.confirm));
			builder.setMessage(this.getString(R.string.exiting));
			builder.setPositiveButton(this.getString(R.string.yes), new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					// Finish app
					dialog.dismiss();
					finish();
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
		case DOWNLOAD:
			builder.setTitle(this.getString(R.string.confirm));
			builder.setMessage(this.getString(R.string.downloading));
			builder.setIcon(android.R.drawable.ic_menu_help);
			builder.setPositiveButton(this.getString(R.string.yes), new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
					sivinAdapter.open();
					boolean hayDatos = sivinAdapter.verificarData();
					sivinAdapter.close();
					if(hayDatos){
						createDialog(VERIFY);
					}
					else{
						createDialog(CONFIRM_PIN);
					}
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
		case CONFIRM_PIN:
			builder.setTitle(this.getString(R.string.confirm));
			builder.setMessage(getString(R.string.enter)+ " " + getString(R.string.pin_download));
			builder.setIcon(android.R.drawable.ic_dialog_info);
			// Set an EditText view to get user input 
			final EditText input = new EditText(this);
			input.setInputType(InputType.TYPE_CLASS_NUMBER);
			builder.setView(input);
			builder.setPositiveButton(this.getString(R.string.ok), new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
					String pinEntered = input.getText().toString(); 
					if(pinEntered.equals(Constants.PIN_DOWNLOAD)){
						Intent ie = new Intent(getApplicationContext(), DownloadEncuestasActivity.class);
						startActivityForResult(ie, UPDATE_EQUIPO);
					}
					else{
						Toast.makeText(getApplicationContext(),	getString(R.string.pin_error), Toast.LENGTH_LONG).show();
					}
				}
			});
			break;			
		case UPLOAD:
			builder.setTitle(this.getString(R.string.confirm));
			builder.setMessage(this.getString(R.string.uploading));
			builder.setIcon(android.R.drawable.ic_menu_help);
			builder.setPositiveButton(this.getString(R.string.yes), new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
					Intent ie = new Intent(getApplicationContext(), UploadEncuestasActivity.class);
					startActivityForResult(ie, UPDATE_SERVER);
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
		case VERIFY:
			builder.setTitle(this.getString(R.string.confirm));
			builder.setMessage(this.getString(R.string.data_not_sent));
			builder.setIcon(android.R.drawable.ic_menu_help);
			builder.setPositiveButton(this.getString(R.string.yes), new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
					createDialog(CONFIRM_PIN);
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
		case CATALOG:
			builder.setTitle(this.getString(R.string.confirm));
			builder.setMessage(this.getString(R.string.downloadingcat));
			builder.setIcon(android.R.drawable.ic_menu_help);
			builder.setPositiveButton(this.getString(R.string.yes), new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
					Intent ie = new Intent(getApplicationContext(), DownloadCatalogosActivity.class);
					startActivityForResult(ie, UPDATE_CATALOG);
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
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {	
		super.onActivityResult(requestCode, resultCode, intent);
		if (resultCode == RESULT_CANCELED) {
			if (requestCode == UPDATE_EQUIPO||requestCode == UPDATE_SERVER||requestCode == UPDATE_CATALOG){
				String mensajeCancel = intent.getStringExtra("resultado");
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				if (mensajeCancel.equals("no hay datos")) {
					builder.setTitle(getApplicationContext().getString(R.string.process_canceled));
					builder.setMessage(getApplicationContext().getString(R.string.no_items_tosend));
					builder.setIcon(R.drawable.ic_menu_revert)
					.setCancelable(false)
					.setPositiveButton("OK", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							//do things
						}
					});
				}
				else {
					builder.setTitle(getApplicationContext().getString(R.string.error));
					builder.setMessage(intent.getStringExtra("resultado"));
					builder.setIcon(R.drawable.ic_menu_close_clear_cancel)
					.setCancelable(false)
					.setPositiveButton("OK", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							//do things
						}
					});
				}
				AlertDialog alert = builder.create();
				alert.show();
				return;
			}
		}
		else{
			if (requestCode == UPDATE_EQUIPO||requestCode == UPDATE_SERVER||requestCode == UPDATE_CATALOG){
				new FetchDataSegmentoTask().execute(mSegmento);
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setTitle(getApplicationContext().getString(R.string.confirm));
				builder.setIcon(R.drawable.ic_menu_mark);
				builder.setMessage(getApplicationContext().getString(R.string.success))
				.setCancelable(false)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						//do things
					}
				});
				AlertDialog alert = builder.create();
				alert.show();
			}
			return;
		}
	}

	private class FetchDataSegmentoTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            // before the request begins, show a progress indicator
            showLoadingProgressDialog();
        }

        @Override
        protected String doInBackground(String... values) {
        	String strSegmento = values[0];
            try {
            	segmento = null;
                sivinAdapter.open();
                segmento = sivinAdapter.getSegmento(MainDBConstants.segmento + " = '"+ strSegmento + "'", null);
                numCatalogos = sivinAdapter.getNumeroRegistros(MainDBConstants.MESSAGES_TABLE);

            } catch (Exception e) {
                Log.e(TAG, e.getLocalizedMessage(), e);
                return "error";
            }finally {
            	if (sivinAdapter != null)
            			sivinAdapter.close();
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
		if(resultado.equals("exito") && segmento==null) {
			mLabelFooter.setText(getString(R.string.default_segmento));
			Intent i = new Intent(getApplicationContext(),
					ListaSegmentosActivity.class);
			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(i);
			finish();
		}else if(resultado.equals("exito")){
			setListAdapter(new MainActivityAdapter(this, R.layout.menu_item, menu_main, numCatalogos));
			mLabelFooter.setTextColor(Color.BLUE);
			mLabelFooter.setText(getString(R.string.enc_segmento)+":"+segmento.getCodigo()+"\n"+
									getString(R.string.enc_comunidad)+":"+segmento.getComunidad()+"\n"+
										getString(R.string.enc_municipio)+":"+segmento.getMunicipio()+"\n"+
												getString(R.string.enc_departamento)+":"+segmento.getDepartamento());
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
