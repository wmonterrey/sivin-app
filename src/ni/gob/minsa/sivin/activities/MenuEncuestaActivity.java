package ni.gob.minsa.sivin.activities;

import ni.gob.minsa.sivin.AbstractAsyncActivity;
import ni.gob.minsa.sivin.MainActivity;
import ni.gob.minsa.sivin.R;
import ni.gob.minsa.sivin.SivinApplication;
import ni.gob.minsa.sivin.activities.enterdata.EncuestaIdentificacionActivity;
import ni.gob.minsa.sivin.activities.enterdata.SeccionAActivity;
import ni.gob.minsa.sivin.activities.enterdata.SeccionBActivity;
import ni.gob.minsa.sivin.activities.enterdata.SeccionCActivity;
import ni.gob.minsa.sivin.activities.enterdata.SeccionDActivity;
import ni.gob.minsa.sivin.activities.enterdata.SeccionEActivity;
import ni.gob.minsa.sivin.activities.enterdata.SeccionFActivity;
import ni.gob.minsa.sivin.activities.enterdata.SeccionGActivity;
import ni.gob.minsa.sivin.activities.enterdata.SeccionHActivity;
import ni.gob.minsa.sivin.adapters.MenuEncuestaAdapter;
import ni.gob.minsa.sivin.database.SivinAdapter;
import ni.gob.minsa.sivin.domain.Encuesta;
import ni.gob.minsa.sivin.domain.Segmento;
import ni.gob.minsa.sivin.utils.Constants;
import ni.gob.minsa.sivin.utils.FileUtils;
import ni.gob.minsa.sivin.utils.MainDBConstants;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import java.io.File;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

public class MenuEncuestaActivity extends AbstractAsyncActivity {
	private static Encuesta mEncuesta = new Encuesta();
	private static Segmento mSegmento = new Segmento();
	
	private GridView gridView;
	private TextView textView;
	private AlertDialog alertDialog;
	private static final int HOME = 1;
	private static final int BACK = 2;
	private static final int IDENTIFICACION = 101;
	private static final int SECCIONA = 102;
	private static final int SECCIONB = 103;
	private static final int SECCIONC = 104;
	private static final int SECCIOND = 105;
	private static final int SECCIONE = 106;
	private static final int SECCIONF = 107;
	private static final int SECCIONG = 108;
	private static final int SECCIONH = 109;
	private static final int FINALIZAR = 110;
	private static final int UBICACION = 111;
	
	private boolean pendiente = true;
	String[] menuEncuesta;
	private SivinAdapter sivinAdapter;

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu_encuesta);

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			ActionBar actionBar = getActionBar();
			actionBar.setDisplayHomeAsUpEnabled(true);
		}
		if (savedInstanceState != null) {
			
		}
		String mPass = ((SivinApplication) this.getApplication()).getPassApp();
		sivinAdapter = new SivinAdapter(this.getApplicationContext(),mPass,false,false);
		//Aca se recupera los datos de la encuesta y el segmento
		mSegmento = (Segmento) getIntent().getExtras().getSerializable(Constants.SEGMENTO);
		mEncuesta = (Encuesta) getIntent().getExtras().getSerializable(Constants.ENCUESTA);
		
		pendiente = mEncuesta.getEstado()==Constants.STATUS_NOT_FINALIZED;
		
		
		textView = (TextView) findViewById(R.id.label);
		textView.setText(getString(R.string.enc_segmento)+":"+mSegmento.getCodigo()+"\n"+
									getString(R.string.enc_comunidad)+":"+mSegmento.getComunidad()+"\n"+
										getString(R.string.enc_municipio)+":"+mSegmento.getMunicipio()+"\n"+
												getString(R.string.enc_departamento)+":"+mSegmento.getDepartamento());
		
		if(mEncuesta.getIdent()==null) {
			textView.setText(textView.getText()+"\n"+getString(R.string.enc_new));
		}
		else {
			textView.setText(textView.getText()+"\n"+mEncuesta.getJefeFamilia()+"\n"+getString(R.string.enc_num)+": "+mEncuesta.getCodigo());
			
		}
		
		menuEncuesta = getResources().getStringArray(R.array.menu_encuesta);
		gridView = (GridView) findViewById(R.id.gridView1);
		gridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				switch(position){ 
				case 0: // IDENTIFICACION DE LA ENCUESTA
					createDialog(IDENTIFICACION);
					break;
				case 1: // SECCION A DE LA ENCUESTA
					createDialog(SECCIONA);
					break;	
				case 2: // SECCION B DE LA ENCUESTA
					createDialog(SECCIONB);
					break;	
				case 3: // SECCION C DE LA ENCUESTA
					createDialog(SECCIONC);
					break;	
				case 4: // SECCION D DE LA ENCUESTA
					createDialog(SECCIOND);
					break;	
				case 5: // SECCION E DE LA ENCUESTA
					createDialog(SECCIONE);
					break;					
				case 6: // SECCION F DE LA ENCUESTA
					createDialog(SECCIONF);
					break;	
				case 7: // SECCION G DE LA ENCUESTA
					createDialog(SECCIONG);
					break;				
				case 8: // SECCION H DE LA ENCUESTA
					createDialog(SECCIONH);
					break;					
				case 9: // FINALIZAR ENCUESTA
					createDialog(FINALIZAR);
					break;	
				case 10: // UBICACION DE LA CASA
					createDialog(UBICACION);
					break;						
				default:					
					break;
				}
			}
		});
		gridView.setAdapter(new MenuEncuestaAdapter(getApplicationContext(), R.layout.menu_item_2, menuEncuesta, mEncuesta));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.general, menu);
		return true;
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
		if (alertDialog != null && alertDialog.isShowing()) {
			alertDialog.dismiss();
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent i;
		switch (item.getItemId()) {
		case android.R.id.home:
			i = new Intent(getApplicationContext(),
					MainActivity.class);
			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(i);
			finish();
			return true;
		case R.id.MENU_BACK:
			if (pendiente){
				createDialog(BACK);
			}
			else{
				i = new Intent(getApplicationContext(),
						IngresarEncuestaActivity.class);
				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(i);
				finish();
			}
			return true;
		case R.id.MENU_HOME:
			if (pendiente){
				createDialog(HOME);
			}
			else{
				i = new Intent(getApplicationContext(),
						MainActivity.class);
				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(i);
				finish();
			}
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onBackPressed (){
		if (pendiente){
			createDialog(BACK);
		}
		else{
			Intent i = new Intent(getApplicationContext(),
					IngresarEncuestaActivity.class);
			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(i);
			finish();
		}
	}

	private void createDialog(int dialog) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		switch(dialog){
		case HOME:
			builder.setTitle(this.getString(R.string.confirm));
			builder.setMessage(this.getString(R.string.pending_data));
			builder.setPositiveButton(this.getString(R.string.yes), new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					// Finish app
					dialog.dismiss();
					Intent i = new Intent(getApplicationContext(),
							MainActivity.class);
					i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(i);
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
		case BACK:
			builder.setTitle(this.getString(R.string.confirm));
			builder.setMessage(this.getString(R.string.pending_data));
			builder.setPositiveButton(this.getString(R.string.yes), new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					// Finish app
					dialog.dismiss();
					Intent i = new Intent(getApplicationContext(),
							IngresarEncuestaActivity.class);
					i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(i);
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
		case IDENTIFICACION:
            builder.setTitle(this.getString(R.string.confirm));
            builder.setMessage(getString(R.string.enter) + "..." + getString(R.string.identificacion)+"?");
            builder.setPositiveButton(this.getString(R.string.yes), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    new OpenDataActivityTask().execute(String.valueOf(IDENTIFICACION));
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
		case SECCIONA:
            builder.setTitle(this.getString(R.string.confirm));
            builder.setMessage(getString(R.string.enter) + "..." + getString(R.string.secciona)+"?");
            builder.setPositiveButton(this.getString(R.string.yes), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    new OpenDataActivityTask().execute(String.valueOf(SECCIONA));
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
		case SECCIONB:
            builder.setTitle(this.getString(R.string.confirm));
            builder.setMessage(getString(R.string.enter) + "..." + getString(R.string.seccionb)+"?");
            builder.setPositiveButton(this.getString(R.string.yes), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    new OpenDataActivityTask().execute(String.valueOf(SECCIONB));
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
		case SECCIONC:
            builder.setTitle(this.getString(R.string.confirm));
            builder.setMessage(getString(R.string.enter) + "..." + getString(R.string.seccionc)+"?");
            builder.setPositiveButton(this.getString(R.string.yes), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    new OpenDataActivityTask().execute(String.valueOf(SECCIONC));
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
		case SECCIOND:
            builder.setTitle(this.getString(R.string.confirm));
            builder.setMessage(getString(R.string.enter) + "..." + getString(R.string.secciond)+"?");
            builder.setPositiveButton(this.getString(R.string.yes), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    new OpenDataActivityTask().execute(String.valueOf(SECCIOND));
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
		case SECCIONE:
            builder.setTitle(this.getString(R.string.confirm));
            builder.setMessage(getString(R.string.enter) + "..." + getString(R.string.seccione)+"?");
            builder.setPositiveButton(this.getString(R.string.yes), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    new OpenDataActivityTask().execute(String.valueOf(SECCIONE));
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
		case SECCIONF:
            builder.setTitle(this.getString(R.string.confirm));
            builder.setMessage(getString(R.string.enter) + "..." + getString(R.string.seccionf)+"?");
            builder.setPositiveButton(this.getString(R.string.yes), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    new OpenDataActivityTask().execute(String.valueOf(SECCIONF));
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
		case SECCIONG:
            builder.setTitle(this.getString(R.string.confirm));
            builder.setMessage(getString(R.string.enter) + "..." + getString(R.string.secciong)+"?");
            builder.setPositiveButton(this.getString(R.string.yes), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    new OpenDataActivityTask().execute(String.valueOf(SECCIONG));
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
		case SECCIONH:
            builder.setTitle(this.getString(R.string.confirm));
            builder.setMessage(getString(R.string.enter) + "..." + getString(R.string.seccionh)+"?");
            builder.setPositiveButton(this.getString(R.string.yes), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    new OpenDataActivityTask().execute(String.valueOf(SECCIONH));
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
		case FINALIZAR:
            builder.setTitle(this.getString(R.string.confirm));
            builder.setMessage(getString(R.string.finalizar)+"?");
            builder.setPositiveButton(this.getString(R.string.yes), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    new SaveDataTask().execute();
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
		case UBICACION:
            builder.setTitle(this.getString(R.string.confirm));
            builder.setMessage(getString(R.string.enter) + "..." + getString(R.string.ubicacion)+"?");
            builder.setPositiveButton(this.getString(R.string.yes), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    new OpenDataActivityTask().execute(String.valueOf(UBICACION));
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
	
    // ***************************************
    // Private classes
    // ***************************************
    private class OpenDataActivityTask extends AsyncTask<String, Void, String> {
        private int position = 0;
        @Override
        protected void onPreExecute() {
            // before the request begins, show a progress indicator
            showLoadingProgressDialog();
        }

        @Override
        protected String doInBackground(String... values) {
            position = Integer.valueOf(values[0]);
            Bundle arguments = new Bundle();
            Intent i;
            try {
            	if (mEncuesta != null) arguments.putSerializable(Constants.ENCUESTA, mEncuesta);
                if (mSegmento != null) arguments.putSerializable(Constants.SEGMENTO, mSegmento);
                switch (position) {
                    case IDENTIFICACION:
                        i = new Intent(getApplicationContext(),
                                EncuestaIdentificacionActivity.class);
                        break;
                    case SECCIONA:
                        i = new Intent(getApplicationContext(),
                        		SeccionAActivity.class);
                        break; 
                    case SECCIONB:
                        i = new Intent(getApplicationContext(),
                        		SeccionBActivity.class);
                        break;  
                    case SECCIONC:
                        i = new Intent(getApplicationContext(),
                        		SeccionCActivity.class);
                        break;  
                    case SECCIOND:
                        i = new Intent(getApplicationContext(),
                        		SeccionDActivity.class);
                        break;   
                    case SECCIONE:
                        i = new Intent(getApplicationContext(),
                        		SeccionEActivity.class);
                        break;                          
                    case SECCIONF:
                        i = new Intent(getApplicationContext(),
                        		SeccionFActivity.class);
                        break; 
                    case SECCIONG:
                        i = new Intent(getApplicationContext(),
                        		SeccionGActivity.class);
                        break;          
                    case SECCIONH:
                        i = new Intent(getApplicationContext(),
                        		SeccionHActivity.class);
                        break; 
                    case UBICACION:
                        i = new Intent(getApplicationContext(),
                        		UbicacionActivity.class);
                        break;                         
                    default:
                    	i = new Intent(getApplicationContext(),
                                MainActivity.class);
                    	break;
                }
                i.putExtras(arguments);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            } catch (Exception e) {
                Log.e(TAG, e.getLocalizedMessage(), e);
                return "error";
            }
            finally {
            }
            return "exito";
        }

        protected void onPostExecute(String resultado) {
            // after the request completes, hide the progress indicator
            dismissProgressDialog();
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
 				mEncuesta.setEstado(Constants.STATUS_NOT_SUBMITTED);
 				sivinAdapter.editarEncuesta(mEncuesta);
 				pendiente = false;
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
 		Intent i = new Intent(getApplicationContext(),
				IngresarEncuestaActivity.class);
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(i);
		finish();
 	}	

}
	
