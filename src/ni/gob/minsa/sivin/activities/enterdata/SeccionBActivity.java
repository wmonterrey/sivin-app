package ni.gob.minsa.sivin.activities.enterdata;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.*;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import ni.gob.minsa.sivin.R;
import ni.gob.minsa.sivin.SivinApplication;
import ni.gob.minsa.sivin.activities.MenuEncuestaActivity;
import ni.gob.minsa.sivin.database.SivinAdapter;
import ni.gob.minsa.sivin.domain.Encuesta;
import ni.gob.minsa.sivin.domain.MessageResource;
import ni.gob.minsa.sivin.domain.Segmento;
import ni.gob.minsa.sivin.forms.SeccionBForm;
import ni.gob.minsa.sivin.forms.SeccionBFormLabels;
import ni.gob.minsa.sivin.preferences.PreferencesActivity;
import ni.gob.minsa.sivin.utils.Constants;
import ni.gob.minsa.sivin.utils.CalcularEdad;
import ni.gob.minsa.sivin.utils.DeviceInfo;
import ni.gob.minsa.sivin.utils.FileUtils;
import ni.gob.minsa.sivin.utils.MainDBConstants;
import ni.gob.minsa.sivin.wizard.model.AbstractWizardModel;
import ni.gob.minsa.sivin.wizard.model.BarcodePage;
import ni.gob.minsa.sivin.wizard.model.DatePage;
import ni.gob.minsa.sivin.wizard.model.LabelPage;
import ni.gob.minsa.sivin.wizard.model.ModelCallbacks;
import ni.gob.minsa.sivin.wizard.model.MultipleFixedChoicePage;
import ni.gob.minsa.sivin.wizard.model.NewDatePage;
import ni.gob.minsa.sivin.wizard.model.NumberPage;
import ni.gob.minsa.sivin.wizard.model.Page;
import ni.gob.minsa.sivin.wizard.model.SingleFixedChoicePage;
import ni.gob.minsa.sivin.wizard.model.TextPage;
import ni.gob.minsa.sivin.wizard.ui.PageFragmentCallbacks;
import ni.gob.minsa.sivin.wizard.ui.ReviewFragment;
import ni.gob.minsa.sivin.wizard.ui.StepPagerStrip;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.joda.time.Period;


public class SeccionBActivity extends FragmentActivity implements
        PageFragmentCallbacks,
        ReviewFragment.Callbacks,
        ModelCallbacks {
	private ViewPager mPager;
    private MyPagerAdapter mPagerAdapter;
    private boolean mEditingAfterReview;
    private AbstractWizardModel mWizardModel;
    private boolean mConsumePageSelectedEvent;
    private Button mNextButton;
    private Button mPrevButton;
    private List<Page> mCurrentPageSequence;
    private StepPagerStrip mStepPagerStrip;
    private SivinAdapter sivinAdapter;
    private DeviceInfo infoMovil;
    private static Segmento segmento = new Segmento();
    private static Encuesta encuesta = new Encuesta();
	private String username;
	private SharedPreferences settings;
	private static final int EXIT = 1;
	private AlertDialog alertDialog;
	private SeccionBFormLabels labels = new SeccionBFormLabels();
	public static final String SIMPLE_DATA_KEY = "_";
	DateFormat mDateFormat = new SimpleDateFormat("dd/MM/yyyy");

    @Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!FileUtils.storageReady()) {
			Toast toast = Toast.makeText(getApplicationContext(),getString(R.string.error, R.string.storage_error),Toast.LENGTH_LONG);
			toast.show();
			finish();
		}
        setContentView(R.layout.activity_data_enter);
        settings =
				PreferenceManager.getDefaultSharedPreferences(this);
		username =
				settings.getString(PreferencesActivity.KEY_USERNAME,
						null);
		infoMovil = new DeviceInfo(SeccionBActivity.this);
        segmento = (Segmento) getIntent().getExtras().getSerializable(Constants.SEGMENTO);
        encuesta = (Encuesta) getIntent().getExtras().getSerializable(Constants.ENCUESTA);
        
        String mPass = ((SivinApplication) this.getApplication()).getPassApp();
        mWizardModel = new SeccionBForm(this,mPass);
        if (savedInstanceState != null) {
            mWizardModel.load(savedInstanceState.getBundle("model"));
        }
        try {
        //Abre la base de datos
		sivinAdapter = new SivinAdapter(this.getApplicationContext(),mPass,false,false);
		sivinAdapter.open();

        if (encuesta != null) {
        	Bundle dato = null;
        	MessageResource datoCatalogo;
        	Page modifPage;
        	if(tieneValor(encuesta.getConoceFNac())){
                modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getConoceFNac());
                datoCatalogo = sivinAdapter.getMessageResource(MainDBConstants.catKey + "='" + encuesta.getConoceFNac() + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null);
                dato = new Bundle(); if(datoCatalogo!=null) dato.putString(SIMPLE_DATA_KEY, datoCatalogo.getSpanish()); modifPage.resetData(dato); modifPage.setmVisible(true);
            }
        	if(encuesta.getFnacEnt()!=null){
        		modifPage = (NewDatePage) mWizardModel.findByKey(labels.getFnacEnt());
	        	dato = new Bundle();dato.putString(SIMPLE_DATA_KEY, mDateFormat.format(encuesta.getFnacEnt()));modifPage.resetData(dato);modifPage.setmVisible(true);
            }
        	if(tieneValor(encuesta.getEdadEnt())){
        		modifPage = (NumberPage) mWizardModel.findByKey(labels.getEdadEnt());
	        	dato = new Bundle();dato.putString(SIMPLE_DATA_KEY, encuesta.getEdadEnt());modifPage.resetData(dato);modifPage.setmVisible(true);
	        	if(encuesta.getConoceFNac().equals(Constants.SI)) modifPage.setmEnabled(false);
            }
        	if(tieneValor(encuesta.getLeerEnt())){
                modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getLeerEnt());
                datoCatalogo = sivinAdapter.getMessageResource(MainDBConstants.catKey + "='" + encuesta.getLeerEnt() + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null);
                dato = new Bundle();if(datoCatalogo!=null) dato.putString(SIMPLE_DATA_KEY, datoCatalogo.getSpanish());modifPage.resetData(dato);modifPage.setmVisible(true);
            }
        	if(tieneValor(encuesta.getEscribirEnt())){
                modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getEscribirEnt());
                datoCatalogo = sivinAdapter.getMessageResource(MainDBConstants.catKey + "='" + encuesta.getEscribirEnt() + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null);
                dato = new Bundle();if(datoCatalogo!=null) dato.putString(SIMPLE_DATA_KEY, datoCatalogo.getSpanish());modifPage.resetData(dato);modifPage.setmVisible(true);
            }
        	if(tieneValor(encuesta.getLeeescEnt())){
                modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getLeeescEnt());
                datoCatalogo = sivinAdapter.getMessageResource(MainDBConstants.catKey + "='" + encuesta.getLeeescEnt() + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null);
                dato = new Bundle();if(datoCatalogo!=null) dato.putString(SIMPLE_DATA_KEY, datoCatalogo.getSpanish());modifPage.resetData(dato);modifPage.setmVisible(true);
            }
        	if(tieneValor(encuesta.getNivelEnt())){
                modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getNivelEnt());
                datoCatalogo = sivinAdapter.getMessageResource(MainDBConstants.catKey + "='" + encuesta.getNivelEnt() + "' and " + MainDBConstants.catRoot + "='CAT_NIVEL'", null);
                dato = new Bundle();if(datoCatalogo!=null) dato.putString(SIMPLE_DATA_KEY, datoCatalogo.getSpanish());modifPage.resetData(dato);modifPage.setmVisible(true);
            }
        	if(tieneValor(encuesta.getGradoEnt())){
                modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getGradoEnt());
                datoCatalogo = sivinAdapter.getMessageResource(MainDBConstants.catKey + "='" + encuesta.getGradoEnt() + "' and " + MainDBConstants.catRoot + "='CAT_GRADO'", null);
                dato = new Bundle();if(datoCatalogo!=null) dato.putString(SIMPLE_DATA_KEY, datoCatalogo.getSpanish());modifPage.resetData(dato);modifPage.setmVisible(true);
            }
        	if(tieneValor(encuesta.getEntRealizada())){
                modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getEntRealizada());
                datoCatalogo = sivinAdapter.getMessageResource(MainDBConstants.catKey + "='" + encuesta.getEntRealizada() + "' and " + MainDBConstants.catRoot + "='CAT_ENTREVISTADO'", null);
                dato = new Bundle();if(datoCatalogo!=null) dato.putString(SIMPLE_DATA_KEY, datoCatalogo.getSpanish());modifPage.resetData(dato);modifPage.setmVisible(true);
            }
        	if(tieneValor(encuesta.getEntEmb())){
                modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getEntEmb());
                datoCatalogo = sivinAdapter.getMessageResource(MainDBConstants.catKey + "='" + encuesta.getEntEmb() + "' and " + MainDBConstants.catRoot + "='CAT_SINONR'", null);
                dato = new Bundle();if(datoCatalogo!=null) dato.putString(SIMPLE_DATA_KEY, datoCatalogo.getSpanish());modifPage.resetData(dato);modifPage.setmVisible(true);
            }
        	if(tieneValor(encuesta.getEntEmbUnico())){
                modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getEntEmbUnico());
                datoCatalogo = sivinAdapter.getMessageResource(MainDBConstants.catKey + "='" + encuesta.getEntEmbUnico() + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null);
                dato = new Bundle();if(datoCatalogo!=null) dato.putString(SIMPLE_DATA_KEY, datoCatalogo.getSpanish());modifPage.resetData(dato);modifPage.setmVisible(true);
            }
        	if(tieneValor(encuesta.getEntDioluz())){
                modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getEntDioluz());
                datoCatalogo = sivinAdapter.getMessageResource(MainDBConstants.catKey + "='" + encuesta.getEntDioluz() + "' and " + MainDBConstants.catRoot + "='CAT_SINONR'", null);
                dato = new Bundle();if(datoCatalogo!=null) dato.putString(SIMPLE_DATA_KEY, datoCatalogo.getSpanish());modifPage.resetData(dato);modifPage.setmVisible(true);
            }
        	if(tieneValor(encuesta.getEntHieacfol())){
                modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getEntHieacfol());
                datoCatalogo = sivinAdapter.getMessageResource(MainDBConstants.catKey + "='" + encuesta.getEntHieacfol() + "' and " + MainDBConstants.catRoot + "='CAT_SINONR'", null);
                dato = new Bundle();if(datoCatalogo!=null) dato.putString(SIMPLE_DATA_KEY, datoCatalogo.getSpanish());modifPage.resetData(dato);modifPage.setmVisible(true);
            }
        	if(tieneValor(encuesta.getEntMeseshierro())){
        		modifPage = (NumberPage) mWizardModel.findByKey(labels.getEntMeseshierro());
	        	dato = new Bundle();dato.putString(SIMPLE_DATA_KEY, encuesta.getEntMeseshierro());modifPage.resetData(dato);modifPage.setmVisible(true);
            }
        	if(tieneValor(encuesta.getEntRecHierro())){
                modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getEntRecHierro());
                datoCatalogo = sivinAdapter.getMessageResource(MainDBConstants.catKey + "='" + encuesta.getEntRecHierro() + "' and " + MainDBConstants.catRoot + "='CAT_HIERROFREC'", null);
                dato = new Bundle();if(datoCatalogo!=null) dato.putString(SIMPLE_DATA_KEY, datoCatalogo.getSpanish());modifPage.resetData(dato);modifPage.setmVisible(true);
            }
        	if(tieneValor(encuesta.getEntORecHierro())){
	        	modifPage = (TextPage) mWizardModel.findByKey(labels.getEntORecHierro());
	        	dato = new Bundle();dato.putString(SIMPLE_DATA_KEY, encuesta.getEntORecHierro());modifPage.resetData(dato);modifPage.setmVisible(true);
	        }
        	if(tieneValor(encuesta.getTiemHierroUnd())){
                modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getTiemHierroUnd());
                datoCatalogo = sivinAdapter.getMessageResource(MainDBConstants.catKey + "='" + encuesta.getTiemHierroUnd() + "' and " + MainDBConstants.catRoot + "='CAT_HIERROTIEMP'", null);
                dato = new Bundle();if(datoCatalogo!=null) dato.putString(SIMPLE_DATA_KEY, datoCatalogo.getSpanish());modifPage.resetData(dato);modifPage.setmVisible(true);
            }
        	if(tieneValor(encuesta.getTiemHierro())){
	        	modifPage = (TextPage) mWizardModel.findByKey(labels.getTiemHierro());
	        	dato = new Bundle();dato.putString(SIMPLE_DATA_KEY, encuesta.getTiemHierro());modifPage.resetData(dato);modifPage.setmVisible(true);
	        	datoCatalogo = sivinAdapter.getMessageResource(MainDBConstants.catKey + "='" + encuesta.getThierround() + "' and " + MainDBConstants.catRoot + "='CAT_HIERROTIEMP'", null);
                NumberPage hierroCant = (NumberPage) mWizardModel.findByKey(labels.getTiemHierro());
	        	if(datoCatalogo.getCatKey().matches("d")){
	        		hierroCant.setHint("En días desde 1 hasta 270");
	        		hierroCant.setmGreaterOrEqualsThan(1);
	        		hierroCant.setmLowerOrEqualsThan(270);
        		}else if(datoCatalogo.getCatKey().matches("s")){
        			hierroCant.setHint("En semanas desde 1 hasta 36");
        			hierroCant.setmGreaterOrEqualsThan(1);
        			hierroCant.setmLowerOrEqualsThan(36);
        		}
        		else if(datoCatalogo.getCatKey().matches("m")){
        			hierroCant.setHint("En meses desde 1 hasta 9");
        			hierroCant.setmGreaterOrEqualsThan(1);
        			hierroCant.setmLowerOrEqualsThan(9);
        		}
	        }
        	if(tieneValor(encuesta.getDondeHierro())){
                modifPage = (MultipleFixedChoicePage) mWizardModel.findByKey(labels.getDondeHierro());
                String codDondeHierro = encuesta.getDondeHierro().replaceAll("," , "','");
                List<String> descDondeHierro = new ArrayList<String>();
                List<MessageResource> msDondeHierro = sivinAdapter.getMessageResources(MainDBConstants.catKey + " in ('" + codDondeHierro + "') and " + MainDBConstants.catRoot + "='CAT_HIERROOBT'", null);
                for(MessageResource ms : msDondeHierro){
                    descDondeHierro.add(ms.getSpanish());
                }
                dato = new Bundle();
                dato.putStringArrayList(SIMPLE_DATA_KEY, (ArrayList<String>) descDondeHierro);
                modifPage.resetData(dato);
                modifPage.setmVisible(true);
            }
        	if(tieneValor(encuesta.getDondeHierroBesp())){
	        	modifPage = (TextPage) mWizardModel.findByKey(labels.getDondeHierroBesp());
	        	dato = new Bundle();dato.putString(SIMPLE_DATA_KEY, encuesta.getDondeHierroBesp());modifPage.resetData(dato);modifPage.setmVisible(true);
	        }
        	if(tieneValor(encuesta.getDondeHierroFesp())){
	        	modifPage = (TextPage) mWizardModel.findByKey(labels.getDondeHierroFesp());
	        	dato = new Bundle();dato.putString(SIMPLE_DATA_KEY, encuesta.getDondeHierroFesp());modifPage.resetData(dato);modifPage.setmVisible(true);
	        }
        	if(tieneValor(encuesta.getTomadoHierro())){
                modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getTomadoHierro());
                datoCatalogo = sivinAdapter.getMessageResource(MainDBConstants.catKey + "='" + encuesta.getTomadoHierro() + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null);
                dato = new Bundle();if(datoCatalogo!=null) dato.putString(SIMPLE_DATA_KEY, datoCatalogo.getSpanish());modifPage.resetData(dato);modifPage.setmVisible(true);
            }
        	if(tieneValor(encuesta.getVita())){
                modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getVita());
                datoCatalogo = sivinAdapter.getMessageResource(MainDBConstants.catKey + "='" + encuesta.getVita() + "' and " + MainDBConstants.catRoot + "='CAT_SINONR'", null);
                dato = new Bundle();if(datoCatalogo!=null) dato.putString(SIMPLE_DATA_KEY, datoCatalogo.getSpanish());modifPage.resetData(dato);modifPage.setmVisible(true);
            }
        	if(tieneValor(encuesta.getTiempVita())){
                modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getTiempVita());
                datoCatalogo = sivinAdapter.getMessageResource(MainDBConstants.catKey + "='" + encuesta.getTiempVita() + "' and " + MainDBConstants.catRoot + "='CAT_VITA'", null);
                dato = new Bundle();if(datoCatalogo!=null) dato.putString(SIMPLE_DATA_KEY, datoCatalogo.getSpanish());modifPage.resetData(dato);modifPage.setmVisible(true);
            }
        	if(tieneValor(encuesta.getEvitaEmb())){
                modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getEvitaEmb());
                datoCatalogo = sivinAdapter.getMessageResource(MainDBConstants.catKey + "='" + encuesta.getEvitaEmb() + "' and " + MainDBConstants.catRoot + "='CAT_XEMB'", null);
                dato = new Bundle();if(datoCatalogo!=null) dato.putString(SIMPLE_DATA_KEY, datoCatalogo.getSpanish());modifPage.resetData(dato);modifPage.setmVisible(true);
            }
        	if(tieneValor(encuesta.getDondeAntic())){
                modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getDondeAntic());
                datoCatalogo = sivinAdapter.getMessageResource(MainDBConstants.catKey + "='" + encuesta.getDondeAntic() + "' and " + MainDBConstants.catRoot + "='CAT_DXEMB'", null);
                dato = new Bundle();if(datoCatalogo!=null) dato.putString(SIMPLE_DATA_KEY, datoCatalogo.getSpanish());modifPage.resetData(dato);modifPage.setmVisible(true);
            }
        	if(tieneValor(encuesta.getNuevoEmb())){
                modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getNuevoEmb());
                datoCatalogo = sivinAdapter.getMessageResource(MainDBConstants.catKey + "='" + encuesta.getNuevoEmb() + "' and " + MainDBConstants.catRoot + "='CAT_SINONR'", null);
                dato = new Bundle();if(datoCatalogo!=null) dato.putString(SIMPLE_DATA_KEY, datoCatalogo.getSpanish());modifPage.resetData(dato);modifPage.setmVisible(true);
            }
        	if(tieneValor(encuesta.getHierro())){
                modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getHierro());
                datoCatalogo = sivinAdapter.getMessageResource(MainDBConstants.catKey + "='" + encuesta.getHierro() + "' and " + MainDBConstants.catRoot + "='CAT_SINONR'", null);
                dato = new Bundle();if(datoCatalogo!=null) dato.putString(SIMPLE_DATA_KEY, datoCatalogo.getSpanish());modifPage.resetData(dato);modifPage.setmVisible(true);
            }
        	if(tieneValor(encuesta.getdHierro())){
                modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getdHierro());
                datoCatalogo = sivinAdapter.getMessageResource(MainDBConstants.catKey + "='" + encuesta.getdHierro() + "' and " + MainDBConstants.catRoot + "='CAT_DXEMB'", null);
                dato = new Bundle();if(datoCatalogo!=null) dato.putString(SIMPLE_DATA_KEY, datoCatalogo.getSpanish());modifPage.resetData(dato);modifPage.setmVisible(true);
            }
        }
        }
        catch(Exception e) {
        	Toast.makeText(getApplicationContext(), e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
        	if (sivinAdapter != null)
                sivinAdapter.close();
        	finish();
        }

        mWizardModel.registerListener(this);
        mPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(mPagerAdapter);
        mStepPagerStrip = (StepPagerStrip) findViewById(R.id.strip);
        mStepPagerStrip.setOnPageSelectedListener(new StepPagerStrip.OnPageSelectedListener() {
            @Override
            public void onPageStripSelected(int position) {
                position = Math.min(mPagerAdapter.getCount() - 1, position);
                if (mPager.getCurrentItem() != position) {
                    mPager.setCurrentItem(position);
                }
            }
        });

        mNextButton = (Button) findViewById(R.id.next_button);
        mPrevButton = (Button) findViewById(R.id.prev_button);

        mPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                mStepPagerStrip.setCurrentPage(position);

                if (mConsumePageSelectedEvent) {
                    mConsumePageSelectedEvent = false;
                    return; 
                }

                mEditingAfterReview = false;
                updateBottomBar();
            }
        });

        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mPager.getCurrentItem() == mCurrentPageSequence.size()) {
                    DialogFragment dg = new DialogFragment() {
                        @Override
                        public Dialog onCreateDialog(Bundle savedInstanceState) {
                            return new AlertDialog.Builder(getActivity())
                                    .setMessage(R.string.submit_confirm_message)
                                    .setPositiveButton(R.string.submit_confirm_button, new DialogInterface.OnClickListener() {
                                    	@Override
										public void onClick(DialogInterface arg0, int arg1) {
                                    		saveData();
										}
                                    })
                                    .setNegativeButton(R.string.cancel,new DialogInterface.OnClickListener() {
                                    	@Override
										public void onClick(DialogInterface arg0, int arg1) {
                                    		createDialog(EXIT);
										}
                                    })
                                    .create();
                        }
                    };
                    dg.show(getSupportFragmentManager(), "guardar_dialog");
                } else {
                    if (mEditingAfterReview) {
                        mPager.setCurrentItem(mPagerAdapter.getCount() - 1);
                    } else {
                        mPager.setCurrentItem(mPager.getCurrentItem() + 1);
                    }
                }
            }
        });

        mPrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPager.setCurrentItem(mPager.getCurrentItem() - 1);
            }
        });
        
        onPageTreeChangedInitial(); 
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
                    	if (sivinAdapter != null)
                            sivinAdapter.close();
                        Bundle arguments = new Bundle();
                        Intent i;
                        if (segmento!=null) arguments.putSerializable(Constants.SEGMENTO , segmento);
                        if (encuesta!=null) arguments.putSerializable(Constants.ENCUESTA , encuesta);
                        i = new Intent(getApplicationContext(),
                                MenuEncuestaActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i.putExtras(arguments);
                        startActivity(i);
                        Toast toast = Toast.makeText(getApplicationContext(),getString(R.string.err_cancel),Toast.LENGTH_LONG);
                        toast.show();
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
            default:
                break;
        }
        alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public void onPageTreeChanged() {
        mCurrentPageSequence = mWizardModel.getCurrentPageSequence();
        mStepPagerStrip.setPageCount(mCurrentPageSequence.size() + 1); // + 1 = review step
        mPagerAdapter.notifyDataSetChanged();
        updateBottomBar();
    }
    
    public void onPageTreeChangedInitial() {
        mCurrentPageSequence = mWizardModel.getCurrentPageSequence();
        mStepPagerStrip.setPageCount(mCurrentPageSequence.size() + 1); // + 1 = review step
        mPagerAdapter.notifyDataSetChanged();
        if (recalculateCutOffPage()) {
            updateBottomBar();
        }
    }

    private void updateBottomBar() {
        int position = mPager.getCurrentItem();
        if (position == mCurrentPageSequence.size()) {
            mNextButton.setText(R.string.finish);
            mNextButton.setBackgroundResource(R.drawable.finish_background);
            mNextButton.setTextAppearance(this, R.style.TextAppearanceFinish);
        } else {
            mNextButton.setText(mEditingAfterReview
                    ? R.string.review
                    : R.string.next);
            mNextButton.setBackgroundResource(R.drawable.selectable_item_background);
            TypedValue v = new TypedValue();
            getTheme().resolveAttribute(android.R.attr.textAppearanceMedium, v, true);
            mNextButton.setTextAppearance(this, v.resourceId);
            mNextButton.setEnabled(position != mPagerAdapter.getCutOffPage());
        }
        mPrevButton.setVisibility(position <= 0 ? View.INVISIBLE : View.VISIBLE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mWizardModel.unregisterListener(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBundle("model", mWizardModel.save());
    }

    @Override
    public AbstractWizardModel onGetModel() {
        return mWizardModel;
    }

    @Override
    public void onEditScreenAfterReview(String key) {
        for (int i = mCurrentPageSequence.size() - 1; i >= 0; i--) {
            if (mCurrentPageSequence.get(i).getKey().equals(key)) {
                mConsumePageSelectedEvent = true;
                mEditingAfterReview = true;
                mPager.setCurrentItem(i);
                updateBottomBar();
                break;
            }
        }
    }

    @Override
    public void onPageDataChanged(Page page) {
    	try{
	    	updateModel(page);
	        if (recalculateCutOffPage()) {
	        	mPagerAdapter.notifyDataSetChanged();
	            updateBottomBar();
	        }
	    }catch (Exception ex){
	        ex.printStackTrace();
	    }
    }

    @Override
    public Page onGetPage(String key) {
        return mWizardModel.findByKey(key);
    }

    private boolean recalculateCutOffPage() {
        // Cut off the pager adapter at first required page that isn't completed
        int cutOffPage = mCurrentPageSequence.size() + 1;
        for (int i = 0; i < mCurrentPageSequence.size(); i++) {
            Page page = mCurrentPageSequence.get(i);
            String clase = page.getClass().toString();
            if (page.isRequired() && !page.isCompleted()) {
                cutOffPage = i;
                break;
            }     
            if (!page.getData().isEmpty() && clase.equals("class ni.gob.minsa.sivin.wizard.model.NumberPage")) {
            	NumberPage np = (NumberPage) page;
            	String valor = np.getData().getString(NumberPage.SIMPLE_DATA_KEY);
        		if((np.ismValRange() && (np.getmGreaterOrEqualsThan() > Double.valueOf(valor) || np.getmLowerOrEqualsThan() < Double.valueOf(valor)))
        				|| (np.ismValPattern() && !valor.matches(np.getmPattern()))){
        			cutOffPage = i;
        			break;
        		}
            }
            if (!page.getData().isEmpty() && clase.equals("class ni.gob.minsa.sivin.wizard.model.TextPage")) {
            	TextPage tp = (TextPage) page;
            	if (tp.ismValPattern()) {
            		String valor = tp.getData().getString(TextPage.SIMPLE_DATA_KEY);
            		if(!valor.matches(tp.getmPattern())){
            			cutOffPage = i;
            			break;
            		}
            	}
            }
        }

        if (mPagerAdapter.getCutOffPage() != cutOffPage) {
            mPagerAdapter.setCutOffPage(cutOffPage);
            return true;
        }

        return false;
    }
    

    
    public void updateModel(Page page){
        try{
        	MessageResource catalogo;
        	boolean visible = false;
        	if (page.getTitle().equals(labels.getConoceFNac())) {
        		catalogo = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + page.getData().getString(TextPage.SIMPLE_DATA_KEY) + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null);
        		visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && catalogo.getCatKey().matches(Constants.SI);
        		changeStatus(mWizardModel.findByKey(labels.getFnacEnt()),visible);
        		NumberPage edadPage = (NumberPage) mWizardModel.findByKey(labels.getEdadEnt());
                if (visible) {edadPage.setmEnabled(false);}else {edadPage.setmEnabled(true);edadPage.setValue("");}
            }
        	if (page.getTitle().equals(labels.getFnacEnt())) {
        		Date fnacEntParsed = mDateFormat.parse(page.getData().getString(TextPage.SIMPLE_DATA_KEY));
               	Date fecEntrevista = encuesta.getFechaEntrevista();
               	CalcularEdad ce = new CalcularEdad();
               	Period fiff = ce.calcDiff(fnacEntParsed, fecEntrevista);
               	NumberPage edadValue = (NumberPage) mWizardModel.findByKey(labels.getEdadEnt());
               	edadValue.setValue(String.valueOf(fiff.getYears()));
        	}
        	if (page.getTitle().equals(labels.getLeeescEnt())) {
        		catalogo = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + page.getData().getString(TextPage.SIMPLE_DATA_KEY) + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null);
        		visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && catalogo.getCatKey().matches(Constants.SI);
        		changeStatus(mWizardModel.findByKey(labels.getNivelEnt()),visible);
            }
        	if (page.getTitle().equals(labels.getNivelEnt())) {
        		catalogo = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + page.getData().getString(TextPage.SIMPLE_DATA_KEY) + "' and " + MainDBConstants.catRoot + "='CAT_NIVEL'", null);
        		visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && !catalogo.getCatKey().matches(Constants.NINGUNO);
        		changeStatus(mWizardModel.findByKey(labels.getGradoEnt()),visible);
            }
        	if (page.getTitle().equals(labels.getEntRealizada())) {
        		catalogo = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + page.getData().getString(TextPage.SIMPLE_DATA_KEY) + "' and " + MainDBConstants.catRoot + "='CAT_ENTREVISTADO'", null);
        		visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && (catalogo.getCatKey().matches("1")||catalogo.getCatKey().matches("2"));
        		cambiarFormParaEmbarazada(false);
        		cambiarFormSiEmbarazada(false);
        		cambiarFormParaHierro(false);
        		changeStatus(mWizardModel.findByKey(labels.getEntEmb()),visible);
            }
        	if (page.getTitle().equals(labels.getEntEmb())) {
        		catalogo = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + page.getData().getString(TextPage.SIMPLE_DATA_KEY) + "' and " + MainDBConstants.catRoot + "='CAT_SINONR'", null);
        		visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && catalogo.getCatKey().matches(Constants.SI);
        		cambiarFormSiEmbarazada(false);
        		cambiarFormParaHierro(false);
        		changeStatus(mWizardModel.findByKey(labels.getEntEmbUnico()),visible);
        		changeStatus(mWizardModel.findByKey(labels.getEntDioluz()),!visible);
        		changeStatus(mWizardModel.findByKey(labels.getEvitaEmb()),!visible);
        		changeStatus(mWizardModel.findByKey(labels.getEntHieacfol()),!visible);
        		changeStatus(mWizardModel.findByKey(labels.getNuevoEmb()),!visible);
            }
        	if (page.getTitle().equals(labels.getEntEmbUnico())) {
        		catalogo = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + page.getData().getString(TextPage.SIMPLE_DATA_KEY) + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null);
        		visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && catalogo.getCatKey().matches(Constants.SI);
        		cambiarFormParaHierro(false);
        		changeStatus(mWizardModel.findByKey(labels.getEntDioluz()),!visible);
            }
        	if (page.getTitle().equals(labels.getEntDioluz())) {
        		catalogo = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + page.getData().getString(TextPage.SIMPLE_DATA_KEY) + "' and " + MainDBConstants.catRoot + "='CAT_SINONR'", null);
        		visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && catalogo.getCatKey().matches(Constants.SI);
        		cambiarFormParaHierro(false);
        		changeStatus(mWizardModel.findByKey(labels.getEntHieacfol()),visible);
            }
        	if (page.getTitle().equals(labels.getEntHieacfol())) {
        		catalogo = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + page.getData().getString(TextPage.SIMPLE_DATA_KEY) + "' and " + MainDBConstants.catRoot + "='CAT_SINONR'", null);
        		visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && catalogo.getCatKey().matches(Constants.SI);
        		cambiarFormParaHierro(false);
        		changeStatus(mWizardModel.findByKey(labels.getEntMeseshierro()),visible);
        		changeStatus(mWizardModel.findByKey(labels.getEntRecHierro()),visible);
        		changeStatus(mWizardModel.findByKey(labels.getTiemHierroUnd()),visible);
        		changeStatus(mWizardModel.findByKey(labels.getDondeHierro()),visible);
        		changeStatus(mWizardModel.findByKey(labels.getTomadoHierro()),visible);
        		changeStatus(mWizardModel.findByKey(labels.getVita()),visible);
            }
        	if (page.getTitle().equals(labels.getEntRecHierro())) {
        		catalogo = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + page.getData().getString(TextPage.SIMPLE_DATA_KEY) + "' and " + MainDBConstants.catRoot + "='CAT_HIERROFREC'", null);
        		visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && catalogo.getCatKey().matches(Constants.OTRO);
        		changeStatus(mWizardModel.findByKey(labels.getEntORecHierro()),visible);
            }
        	if (page.getTitle().equals(labels.getTiemHierroUnd())) {
        		catalogo = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + page.getData().getString(TextPage.SIMPLE_DATA_KEY) + "' and " + MainDBConstants.catRoot + "='CAT_HIERROTIEMP'", null);
        		visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && !catalogo.getCatKey().matches(Constants.NOSABE);
        		NumberPage tiempoHierroPage = (NumberPage) mWizardModel.findByKey(labels.getTiemHierro());
        		tiempoHierroPage.setValue("");
        		if(catalogo.getCatKey().matches("d")){
        			tiempoHierroPage.setHint("En días desde 1 hasta 270");
        			tiempoHierroPage.setmGreaterOrEqualsThan(1);
        			tiempoHierroPage.setmLowerOrEqualsThan(270);
        		}else if(catalogo.getCatKey().matches("s")){
        			tiempoHierroPage.setHint("En semanas desde 1 hasta 36");
        			tiempoHierroPage.setmGreaterOrEqualsThan(1);
        			tiempoHierroPage.setmLowerOrEqualsThan(36);
        		}
        		else if(catalogo.getCatKey().matches("m")){
        			tiempoHierroPage.setHint("En meses desde 1 hasta 9");
        			tiempoHierroPage.setmGreaterOrEqualsThan(1);
        			tiempoHierroPage.setmLowerOrEqualsThan(9);
        		}
        		changeStatus(tiempoHierroPage,visible);
            }
        	if (page.getTitle().equals(labels.getDondeHierro())) {
            	ArrayList<String> respuestas = page.getData().getStringArrayList(TextPage.SIMPLE_DATA_KEY);
            	for(String respuesta:respuestas) {
            		catalogo = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + respuesta + "' and " + MainDBConstants.catRoot + "='CAT_HIERROOBT'", null);
            		if(catalogo.getCatKey().matches("b")) {
            			visible=true;
            			break;
            		}
            	}
            	changeStatus(mWizardModel.findByKey(labels.getDondeHierroBesp()),visible);
            	visible=false;
            	for(String respuesta:respuestas) {
            		catalogo = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + respuesta + "' and " + MainDBConstants.catRoot + "='CAT_HIERROOBT'", null);
            		if(catalogo.getCatKey().matches("f")) {
            			visible=true;
            			break;
            		}
            	}
            	changeStatus(mWizardModel.findByKey(labels.getDondeHierroFesp()),visible);
            }
        	if (page.getTitle().equals(labels.getVita())) {
        		catalogo = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + page.getData().getString(TextPage.SIMPLE_DATA_KEY) + "' and " + MainDBConstants.catRoot + "='CAT_SINONR'", null);
        		visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && catalogo.getCatKey().matches(Constants.SI);
        		changeStatus(mWizardModel.findByKey(labels.getTiempVita()),visible);
            }
        	if (page.getTitle().equals(labels.getEvitaEmb())) {
        		catalogo = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + page.getData().getString(TextPage.SIMPLE_DATA_KEY) + "' and " + MainDBConstants.catRoot + "='CAT_XEMB'", null);
        		visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && !(catalogo.getCatKey().matches("1")||catalogo.getCatKey().matches("9")||catalogo.getCatKey().matches("10"));
        		changeStatus(mWizardModel.findByKey(labels.getDondeAntic()),visible);
            }
        	if (page.getTitle().equals(labels.getNuevoEmb())) {
        		catalogo = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + page.getData().getString(TextPage.SIMPLE_DATA_KEY) + "' and " + MainDBConstants.catRoot + "='CAT_SINONR'", null);
        		visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && catalogo.getCatKey().matches(Constants.SI);
        		changeStatus(mWizardModel.findByKey(labels.getHierro()),visible);
            }
        	if (page.getTitle().equals(labels.getHierro())) {
        		catalogo = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + page.getData().getString(TextPage.SIMPLE_DATA_KEY) + "' and " + MainDBConstants.catRoot + "='CAT_SINONR'", null);
        		visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && catalogo.getCatKey().matches(Constants.SI);
        		changeStatus(mWizardModel.findByKey(labels.getdHierro()),visible);
            }
        	onPageTreeChanged();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
    
    public void cambiarFormParaEmbarazada(boolean valor) {
    	changeStatus(mWizardModel.findByKey(labels.getEntEmb()),valor);
		changeStatus(mWizardModel.findByKey(labels.getEntEmbUnico()),valor);
		changeStatus(mWizardModel.findByKey(labels.getEntDioluz()),valor);
		changeStatus(mWizardModel.findByKey(labels.getEntHieacfol()),valor);
    }
    
    public void cambiarFormSiEmbarazada(boolean valor) {
    	changeStatus(mWizardModel.findByKey(labels.getEvitaEmb()),valor);
		changeStatus(mWizardModel.findByKey(labels.getDondeAntic()),valor);
		changeStatus(mWizardModel.findByKey(labels.getNuevoEmb()),valor);
		changeStatus(mWizardModel.findByKey(labels.getHierro()),valor);
		changeStatus(mWizardModel.findByKey(labels.getdHierro()),valor);
    }
    
    public void cambiarFormParaHierro(boolean valor) {
    	changeStatus(mWizardModel.findByKey(labels.getEntMeseshierro()),valor);
		changeStatus(mWizardModel.findByKey(labels.getEntRecHierro()),valor);
		changeStatus(mWizardModel.findByKey(labels.getEntORecHierro()),valor);
		changeStatus(mWizardModel.findByKey(labels.getTiemHierroUnd()),valor);
		changeStatus(mWizardModel.findByKey(labels.getTiemHierro()),valor);
		changeStatus(mWizardModel.findByKey(labels.getDondeHierro()),valor);
		changeStatus(mWizardModel.findByKey(labels.getDondeHierroBesp()),valor);
		changeStatus(mWizardModel.findByKey(labels.getDondeHierroFesp()),valor);
		changeStatus(mWizardModel.findByKey(labels.getTomadoHierro()),valor);
		changeStatus(mWizardModel.findByKey(labels.getVita()),valor);
		changeStatus(mWizardModel.findByKey(labels.getTiempVita()),valor);
    }
    
    public void changeStatus(Page page, boolean visible){
        String clase = page.getClass().toString();
        if (clase.equals("class ni.gob.minsa.sivin.wizard.model.SingleFixedChoicePage")){
            SingleFixedChoicePage modifPage = (SingleFixedChoicePage) page; modifPage.setValue("").setmVisible(visible);
        }
        else if (clase.equals("class ni.gob.minsa.sivin.wizard.model.BarcodePage")){
            BarcodePage modifPage = (BarcodePage) page; modifPage.setValue("").setmVisible(visible);
        }
        else if (clase.equals("class ni.gob.minsa.sivin.wizard.model.LabelPage")){
            LabelPage modifPage = (LabelPage) page; modifPage.setmVisible(visible);
        }
        else if (clase.equals("class ni.gob.minsa.sivin.wizard.model.TextPage")){
            TextPage modifPage = (TextPage) page; modifPage.setValue("").setmVisible(visible);
        }
        else if (clase.equals("class ni.gob.minsa.sivin.wizard.model.NumberPage")){
            NumberPage modifPage = (NumberPage) page; modifPage.setValue("").setmVisible(visible);
        }
        else if (clase.equals("class ni.gob.minsa.sivin.wizard.model.MultipleFixedChoicePage")){
            MultipleFixedChoicePage modifPage = (MultipleFixedChoicePage) page; modifPage.setValue("").setmVisible(visible);
        }
        else if (clase.equals("class ni.gob.minsa.sivin.wizard.model.DatePage")){
            DatePage modifPage = (DatePage) page; modifPage.setValue("").setmVisible(visible);
        }
        else if (clase.equals("class ni.gob.minsa.sivin.wizard.model.NewDatePage")){
            NewDatePage modifPage = (NewDatePage) page; modifPage.setValue("").setmVisible(visible);
        }
    }
    
    private boolean tieneValor(String entrada){
        return (entrada != null && !entrada.isEmpty());
    }
    
    
    public void saveData(){
        try {
            Map<String, String> mapa = mWizardModel.getAnswers();
            //Guarda las respuestas en un bundle
            Bundle datos = new Bundle();
            for (Map.Entry<String, String> entry : mapa.entrySet()) {
                datos.putString(entry.getKey(), entry.getValue());
            }

            //Abre la base de datos
            //String mPass = ((SivinApplication) this.getApplication()).getPassApp();
            //sivinAdapter = new SivinAdapter(this.getApplicationContext(), mPass, false, false);
            //sivinAdapter.open();
            
            //Obtener datos del bundle para la encuesta
            String conoceFNac = datos.getString(this.getString(R.string.conoceFNac));
            String fnacEnt = datos.getString(this.getString(R.string.fnacEnt));
            String edadEnt = datos.getString(this.getString(R.string.edadEnt));
    		String leerEnt = datos.getString(this.getString(R.string.leerEnt));
    		String escribirEnt = datos.getString(this.getString(R.string.escribirEnt));
    		String leeescEnt = datos.getString(this.getString(R.string.leeescEnt));
    		String nivelEnt = datos.getString(this.getString(R.string.nivelEnt));
    		String gradoEnt = datos.getString(this.getString(R.string.gradoEnt));
    		String entRealizada = datos.getString(this.getString(R.string.entRealizada));
    		String entEmb = datos.getString(this.getString(R.string.entEmb));
    		String entEmbUnico = datos.getString(this.getString(R.string.entEmbUnico));
    		String entDioluz = datos.getString(this.getString(R.string.entDioluz));
    		String entHieacfol = datos.getString(this.getString(R.string.entHieacfol));
    		String entMeseshierro = datos.getString(this.getString(R.string.entMeseshierro));
    		String entRecHierro = datos.getString(this.getString(R.string.entRecHierro));
    		String entORecHierro = datos.getString(this.getString(R.string.entORecHierro));
    		String tiemHierroUnd = datos.getString(this.getString(R.string.tiemHierroUnd));
    		String tiemHierro = datos.getString(this.getString(R.string.tiemHierro));
    		String dondeHierro = datos.getString(this.getString(R.string.dondeHierro));
    		String dondeHierroBesp = datos.getString(this.getString(R.string.dondeHierroBesp));
    		String dondeHierroFesp = datos.getString(this.getString(R.string.dondeHierroFesp));
    		String tomadoHierro = datos.getString(this.getString(R.string.tomadoHierro));
    		String vita = datos.getString(this.getString(R.string.vita));
    		String tiempVita = datos.getString(this.getString(R.string.tiempVita));
    		String evitaEmb = datos.getString(this.getString(R.string.evitaEmb));
    		String dondeAntic = datos.getString(this.getString(R.string.dondeAntic));
    		String nuevoEmb = datos.getString(this.getString(R.string.nuevoEmb));
    		String hierro = datos.getString(this.getString(R.string.hierro));
    		String dHierro = datos.getString(this.getString(R.string.dHierro));
    		
    		//Para recuperar catalogos
    		MessageResource catRespuesta;
            
            if (tieneValor(conoceFNac)) {
    			catRespuesta = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + conoceFNac + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null);
    			if (catRespuesta!=null) encuesta.setConoceFNac(catRespuesta.getCatKey());
    		}
            else {
            	encuesta.setConoceFNac(null);
            }
            
            Date fnacEntParsed = null;
            if (tieneValor(fnacEnt)) {
	            try {
	            	fnacEntParsed = mDateFormat.parse(fnacEnt);
	    		} catch (ParseException e) {
	    			// TODO Auto-generated catch block
	    			e.printStackTrace();
	    			Toast toast = Toast.makeText(getApplicationContext(),e.getLocalizedMessage(),Toast.LENGTH_LONG);
	    			toast.show();
	    			finish();
	    		}
            }
            encuesta.setFnacEnt(fnacEntParsed);
            if (tieneValor(edadEnt)) {encuesta.setEdadEnt(edadEnt);} else {encuesta.setEdadEnt(null);}
            if (tieneValor(leerEnt)) {
            	catRespuesta = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + leerEnt + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null);
    			if (catRespuesta!=null) encuesta.setLeerEnt(catRespuesta.getCatKey());
    		}
            else {
            	encuesta.setLeerEnt(null);
            }
            if (tieneValor(escribirEnt)) {
            	catRespuesta = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + escribirEnt + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null);
    			if (catRespuesta!=null) encuesta.setEscribirEnt(catRespuesta.getCatKey());
    		}
            else {
            	encuesta.setEscribirEnt(null);
            }
            if (tieneValor(leeescEnt)) {
            	catRespuesta = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + leeescEnt + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null);
    			if (catRespuesta!=null) encuesta.setLeeescEnt(catRespuesta.getCatKey());
    		}
            else {
            	encuesta.setLeeescEnt(null);
            }
            if (tieneValor(nivelEnt)) {
            	catRespuesta = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + nivelEnt + "' and " + MainDBConstants.catRoot + "='CAT_NIVEL'", null);
    			if (catRespuesta!=null) encuesta.setNivelEnt(catRespuesta.getCatKey());
    		}
            else {
            	encuesta.setNivelEnt(null);
            }
            if (tieneValor(gradoEnt)) {
            	catRespuesta = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + gradoEnt + "' and " + MainDBConstants.catRoot + "='CAT_GRADO'", null);
    			if (catRespuesta!=null) encuesta.setGradoEnt(catRespuesta.getCatKey());
    		}
            else {
            	encuesta.setGradoEnt(null);
            }
            if (tieneValor(entRealizada)) {
            	catRespuesta = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + entRealizada + "' and " + MainDBConstants.catRoot + "='CAT_ENTREVISTADO'", null);
    			if (catRespuesta!=null) encuesta.setEntRealizada(catRespuesta.getCatKey());
    		}
            else {
            	encuesta.setEntRealizada(null);
            }
            if (tieneValor(entEmb)) {
            	catRespuesta = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + entEmb + "' and " + MainDBConstants.catRoot + "='CAT_SINONR'", null);
    			if (catRespuesta!=null) encuesta.setEntEmb(catRespuesta.getCatKey());
    		}
            else {
            	encuesta.setEntEmb(null);
            }
            if (tieneValor(entEmbUnico)) {
            	catRespuesta = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + entEmbUnico + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null);
    			if (catRespuesta!=null) encuesta.setEntEmbUnico(catRespuesta.getCatKey());
    		}
            else {
            	encuesta.setEntEmbUnico(null);
            }
            if (tieneValor(entDioluz)) {
            	catRespuesta = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + entDioluz + "' and " + MainDBConstants.catRoot + "='CAT_SINONR'", null);
    			if (catRespuesta!=null) encuesta.setEntDioluz(catRespuesta.getCatKey());
    		}
            else {
            	encuesta.setEntDioluz(null);
            }
            if (tieneValor(entHieacfol)) {
            	catRespuesta = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + entHieacfol + "' and " + MainDBConstants.catRoot + "='CAT_SINONR'", null);
    			if (catRespuesta!=null) encuesta.setEntHieacfol(catRespuesta.getCatKey());
    		}
            else {
            	encuesta.setEntHieacfol(null);
            }
            if (tieneValor(entMeseshierro)) {encuesta.setEntMeseshierro(entMeseshierro);} else {encuesta.setEntMeseshierro(null);}
            if (tieneValor(entRecHierro)) {
            	catRespuesta = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + entRecHierro + "' and " + MainDBConstants.catRoot + "='CAT_HIERROFREC'", null);
    			if (catRespuesta!=null) encuesta.setEntRecHierro(catRespuesta.getCatKey());
    		}
            else {
            	encuesta.setEntRecHierro(null);
            }
            if (tieneValor(entORecHierro)) {encuesta.setEntORecHierro(entORecHierro);} else {encuesta.setEntORecHierro(null);}
            if (tieneValor(tiemHierroUnd)) {
            	catRespuesta = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + tiemHierroUnd + "' and " + MainDBConstants.catRoot + "='CAT_HIERROTIEMP'", null);
    			if (catRespuesta!=null) encuesta.setTiemHierroUnd(catRespuesta.getCatKey());
    		}
            else {
            	encuesta.setTiemHierroUnd(null);
            }
            if (tieneValor(tiemHierro)) {encuesta.setTiemHierro(tiemHierro);} else {encuesta.setTiemHierro(null);}
            
            if (tieneValor(dondeHierro)) {
                String keysDondeHierro = "";
                dondeHierro = dondeHierro.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll(", " , "','");
                List<MessageResource> msDondeHierro = sivinAdapter.getMessageResources(MainDBConstants.spanish + " in ('" + dondeHierro + "') and "
                        + MainDBConstants.catRoot + "='CAT_HIERROOBT'", null);
                for(MessageResource ms : msDondeHierro) {
                    keysDondeHierro += ms.getCatKey() + ",";
                }
                if (!keysDondeHierro.isEmpty())
                    keysDondeHierro = keysDondeHierro.substring(0, keysDondeHierro.length() - 1);
                encuesta.setDondeHierro(keysDondeHierro);
            } else {
            	encuesta.setDondeHierro(null);
            }
            if (tieneValor(dondeHierroBesp)) {encuesta.setDondeHierroBesp(dondeHierroBesp);} else {encuesta.setDondeHierroBesp(null);}
            if (tieneValor(dondeHierroFesp)) {encuesta.setDondeHierroFesp(dondeHierroFesp);} else {encuesta.setDondeHierroFesp(null);}
            if (tieneValor(tomadoHierro)) {
            	catRespuesta = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + tomadoHierro + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null);
    			if (catRespuesta!=null) encuesta.setTomadoHierro(catRespuesta.getCatKey());
    		}
            else {
            	encuesta.setTomadoHierro(null);
            }
            if (tieneValor(vita)) {
            	catRespuesta = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + vita + "' and " + MainDBConstants.catRoot + "='CAT_SINONR'", null);
    			if (catRespuesta!=null) encuesta.setVita(catRespuesta.getCatKey());
    		}
            else {
            	encuesta.setVita(null);
            }
            if (tieneValor(tiempVita)) {
            	catRespuesta = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + tiempVita + "' and " + MainDBConstants.catRoot + "='CAT_VITA'", null);
    			if (catRespuesta!=null) encuesta.setTiempVita(catRespuesta.getCatKey());
    		}
            else {
            	encuesta.setTiempVita(null);
            }
            if (tieneValor(evitaEmb)) {
            	catRespuesta = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + evitaEmb + "' and " + MainDBConstants.catRoot + "='CAT_XEMB'", null);
    			if (catRespuesta!=null) encuesta.setEvitaEmb(catRespuesta.getCatKey());
    		}
            else {
            	encuesta.setEvitaEmb(null);
            }
            if (tieneValor(dondeAntic)) {
            	catRespuesta = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + dondeAntic + "' and " + MainDBConstants.catRoot + "='CAT_DXEMB'", null);
    			if (catRespuesta!=null) encuesta.setDondeAntic(catRespuesta.getCatKey());
    		}
            else {
            	encuesta.setDondeAntic(null);
            }
            if (tieneValor(nuevoEmb)) {
            	catRespuesta = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + nuevoEmb + "' and " + MainDBConstants.catRoot + "='CAT_SINONR'", null);
    			if (catRespuesta!=null) encuesta.setNuevoEmb(catRespuesta.getCatKey());
    		}
            else {
            	encuesta.setNuevoEmb(null);
            }
            if (tieneValor(hierro)) {
            	catRespuesta = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + hierro + "' and " + MainDBConstants.catRoot + "='CAT_SINONR'", null);
    			if (catRespuesta!=null) encuesta.setHierro(catRespuesta.getCatKey());
    		}
            else {
            	encuesta.setHierro(null);
            }
            if (tieneValor(dHierro)) {
            	catRespuesta = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + dHierro + "' and " + MainDBConstants.catRoot + "='CAT_DXEMB'", null);
    			if (catRespuesta!=null) encuesta.setdHierro(catRespuesta.getCatKey());
    		}
            else {
            	encuesta.setdHierro(null);
            }
            
            encuesta.setRecordUser(username);
            encuesta.setDeviceid(infoMovil.getDeviceId());
            encuesta.setPasive('0');
            if (encuesta.getEstado()==Constants.STATUS_SUBMITTED) {
            	encuesta.setEstado(Constants.STATUS_NOT_SUBMITTED);
            }
            else if (encuesta.getEstado()==Constants.STATUS_NOT_SUBMITTED) {
            	encuesta.setEstado(Constants.STATUS_NOT_SUBMITTED);
            }
            else {
            	encuesta.setEstado(Constants.STATUS_NOT_FINALIZED);
            }
            sivinAdapter.editarEncuesta(encuesta);
            sivinAdapter.close();
            
            FileUtils.createFolder(FileUtils.BACKUP_PATH);
            File databaseFile = new File(FileUtils.DATABASE_PATH + "/" +MainDBConstants.DATABASE_NAME);
            File databaseFileBackup = new File(FileUtils.BACKUP_FILE);
            
            FileUtils.copy(databaseFile, databaseFileBackup);
            
            Bundle arguments = new Bundle();
            Intent i;
            if (segmento!=null) arguments.putSerializable(Constants.SEGMENTO , segmento);
            if (encuesta!=null) arguments.putSerializable(Constants.ENCUESTA , encuesta);
            i = new Intent(getApplicationContext(),
                    MenuEncuestaActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.putExtras(arguments);
            startActivity(i);
            Toast toast = Toast.makeText(getApplicationContext(),getString(R.string.success),Toast.LENGTH_LONG);
            toast.show();
            finish();
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            if (sivinAdapter != null)
                sivinAdapter.close();
        }
    }


    public class MyPagerAdapter extends FragmentStatePagerAdapter {
        private int mCutOffPage;
        private Fragment mPrimaryItem;

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            if (i >= mCurrentPageSequence.size()) {
                return new ReviewFragment();
            }

            return mCurrentPageSequence.get(i).createFragment();
        }

        @Override
        public int getItemPosition(Object object) {
            // TODO: be smarter about this
            if (object == mPrimaryItem) {
                // Re-use the current fragment (its position never changes)
                return POSITION_UNCHANGED;
            }

            return POSITION_NONE;
        }

        @Override
        public void setPrimaryItem(ViewGroup container, int position, Object object) {
            super.setPrimaryItem(container, position, object);
            mPrimaryItem = (Fragment) object;
        }

        @Override
        public int getCount() {
            return Math.min(mCutOffPage + 1, mCurrentPageSequence.size() + 1);
        }

        public void setCutOffPage(int cutOffPage) {
            if (cutOffPage < 0) {
                cutOffPage = Integer.MAX_VALUE;
            }
            mCutOffPage = cutOffPage;
        }

        public int getCutOffPage() {
            return mCutOffPage;
        }
    }
}
