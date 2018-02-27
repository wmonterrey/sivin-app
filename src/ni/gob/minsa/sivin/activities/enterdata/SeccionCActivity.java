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
import ni.gob.minsa.sivin.forms.SeccionCForm;
import ni.gob.minsa.sivin.forms.SeccionCFormLabels;
import ni.gob.minsa.sivin.preferences.PreferencesActivity;
import ni.gob.minsa.sivin.utils.CalcularEdad;
import ni.gob.minsa.sivin.utils.Constants;
import ni.gob.minsa.sivin.utils.DeviceInfo;
import ni.gob.minsa.sivin.utils.FileUtils;
import ni.gob.minsa.sivin.utils.MainDBConstants;
import ni.gob.minsa.sivin.utils.SeleccionarNino;
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
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.joda.time.Period;


public class SeccionCActivity extends FragmentActivity implements
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
    private static Integer nextViv = 0;
	private String username;
	private SharedPreferences settings;
	private static final int EXIT = 1;
	private AlertDialog alertDialog;
	private SeccionCFormLabels labels = new SeccionCFormLabels();
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
		infoMovil = new DeviceInfo(SeccionCActivity.this);
        segmento = (Segmento) getIntent().getExtras().getSerializable(Constants.SEGMENTO);
        encuesta = (Encuesta) getIntent().getExtras().getSerializable(Constants.ENCUESTA);
        nextViv = (Integer) getIntent().getExtras().getInt(Constants.VIVIENDA);
        
        String mPass = ((SivinApplication) this.getApplication()).getPassApp();
        mWizardModel = new SeccionCForm(this,mPass);
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
        	if(tieneValor(encuesta.getNumNinos())){
        		modifPage = (NumberPage) mWizardModel.findByKey(labels.getNumNinos());
	        	dato = new Bundle();dato.putString(SIMPLE_DATA_KEY, encuesta.getNumNinos());modifPage.resetData(dato);modifPage.setmVisible(true);
            }
        	if(tieneValor(encuesta.getN1())){
        		modifPage = (TextPage) mWizardModel.findByKey(labels.getN1());
	        	dato = new Bundle();dato.putString(SIMPLE_DATA_KEY, encuesta.getN1());modifPage.resetData(dato);modifPage.setmVisible(true);
            }
        	if(tieneValor(encuesta.getE1())){
        		modifPage = (NumberPage) mWizardModel.findByKey(labels.getE1());
	        	dato = new Bundle();dato.putString(SIMPLE_DATA_KEY, encuesta.getE1());modifPage.resetData(dato);modifPage.setmVisible(true);
            }
        	if(tieneValor(encuesta.getN2())){
        		modifPage = (TextPage) mWizardModel.findByKey(labels.getN2());
	        	dato = new Bundle();dato.putString(SIMPLE_DATA_KEY, encuesta.getN2());modifPage.resetData(dato);modifPage.setmVisible(true);
            }
        	if(tieneValor(encuesta.getE2())){
        		modifPage = (NumberPage) mWizardModel.findByKey(labels.getE2());
	        	dato = new Bundle();dato.putString(SIMPLE_DATA_KEY, encuesta.getE2());modifPage.resetData(dato);modifPage.setmVisible(true);
            }
        	if(tieneValor(encuesta.getN3())){
        		modifPage = (TextPage) mWizardModel.findByKey(labels.getN3());
	        	dato = new Bundle();dato.putString(SIMPLE_DATA_KEY, encuesta.getN3());modifPage.resetData(dato);modifPage.setmVisible(true);
            }
        	if(tieneValor(encuesta.getE3())){
        		modifPage = (NumberPage) mWizardModel.findByKey(labels.getE3());
	        	dato = new Bundle();dato.putString(SIMPLE_DATA_KEY, encuesta.getE3());modifPage.resetData(dato);modifPage.setmVisible(true);
            }
        	if(tieneValor(encuesta.getN4())){
        		modifPage = (TextPage) mWizardModel.findByKey(labels.getN4());
	        	dato = new Bundle();dato.putString(SIMPLE_DATA_KEY, encuesta.getN4());modifPage.resetData(dato);modifPage.setmVisible(true);
            }
        	if(tieneValor(encuesta.getE4())){
        		modifPage = (NumberPage) mWizardModel.findByKey(labels.getE4());
	        	dato = new Bundle();dato.putString(SIMPLE_DATA_KEY, encuesta.getE4());modifPage.resetData(dato);modifPage.setmVisible(true);
            }
        	if(tieneValor(encuesta.getN5())){
        		modifPage = (TextPage) mWizardModel.findByKey(labels.getN5());
	        	dato = new Bundle();dato.putString(SIMPLE_DATA_KEY, encuesta.getN5());modifPage.resetData(dato);modifPage.setmVisible(true);
            }
        	if(tieneValor(encuesta.getE5())){
        		modifPage = (NumberPage) mWizardModel.findByKey(labels.getE5());
	        	dato = new Bundle();dato.putString(SIMPLE_DATA_KEY, encuesta.getE5());modifPage.resetData(dato);modifPage.setmVisible(true);
            }
        	if(tieneValor(encuesta.getN6())){
        		modifPage = (TextPage) mWizardModel.findByKey(labels.getN6());
	        	dato = new Bundle();dato.putString(SIMPLE_DATA_KEY, encuesta.getN6());modifPage.resetData(dato);modifPage.setmVisible(true);
            }
        	if(tieneValor(encuesta.getE6())){
        		modifPage = (NumberPage) mWizardModel.findByKey(labels.getE6());
	        	dato = new Bundle();dato.putString(SIMPLE_DATA_KEY, encuesta.getE6());modifPage.resetData(dato);modifPage.setmVisible(true);
            }
        	if(tieneValor(encuesta.getNselec())){
        		modifPage = (TextPage) mWizardModel.findByKey(labels.getNselec());
	        	dato = new Bundle();dato.putString(SIMPLE_DATA_KEY, encuesta.getNselec());modifPage.resetData(dato);modifPage.setmVisible(true);modifPage.setmEnabled(false);
            }
        	if(tieneValor(encuesta.getNomselec())){
        		modifPage = (TextPage) mWizardModel.findByKey(labels.getNomselec());
	        	dato = new Bundle();dato.putString(SIMPLE_DATA_KEY, encuesta.getNomselec());modifPage.resetData(dato);modifPage.setmVisible(true);modifPage.setmEnabled(false);
            }
        	if(encuesta.getFnacselec()!=null){
        		modifPage = (NewDatePage) mWizardModel.findByKey(labels.getFnacselec());
	        	dato = new Bundle();dato.putString(SIMPLE_DATA_KEY, mDateFormat.format(encuesta.getFnacselec()));modifPage.resetData(dato);modifPage.setmVisible(true);
            }
        	if(tieneValor(encuesta.getEselect())){
        		modifPage = (NumberPage) mWizardModel.findByKey(labels.getEselect());
	        	dato = new Bundle();dato.putString(SIMPLE_DATA_KEY, encuesta.getEselect());modifPage.resetData(dato);modifPage.setmVisible(true);
	        	modifPage.setmEnabled(false);
            }
        	if(tieneValor(encuesta.getSexselec())){
                modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getSexselec());
                datoCatalogo = sivinAdapter.getMessageResource(MainDBConstants.catKey + "='" + encuesta.getSexselec() + "' and " + MainDBConstants.catRoot + "='CAT_SEXO'", null);
                dato = new Bundle(); if(datoCatalogo!=null) dato.putString(SIMPLE_DATA_KEY, datoCatalogo.getSpanish()); modifPage.resetData(dato); modifPage.setmVisible(true);
            }
        	if(tieneValor(encuesta.getCalim())){
                modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getCalim());
                datoCatalogo = sivinAdapter.getMessageResource(MainDBConstants.catKey + "='" + encuesta.getCalim() + "' and " + MainDBConstants.catRoot + "='CAT_CALIM'", null);
                dato = new Bundle(); if(datoCatalogo!=null) dato.putString(SIMPLE_DATA_KEY, datoCatalogo.getSpanish()); modifPage.resetData(dato); modifPage.setmVisible(true);
            }
        	if(tieneValor(encuesta.getVcome())){
        		modifPage = (NumberPage) mWizardModel.findByKey(labels.getVcome());
	        	dato = new Bundle();dato.putString(SIMPLE_DATA_KEY, encuesta.getVcome());modifPage.resetData(dato);modifPage.setmVisible(true);
            }
        	if(tieneValor(encuesta.getConsalim())){
                modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getConsalim());
                datoCatalogo = sivinAdapter.getMessageResource(MainDBConstants.catKey + "='" + encuesta.getConsalim() + "' and " + MainDBConstants.catRoot + "='CAT_CONSALIM'", null);
                dato = new Bundle(); if(datoCatalogo!=null) dato.putString(SIMPLE_DATA_KEY, datoCatalogo.getSpanish()); modifPage.resetData(dato); modifPage.setmVisible(true);
            }
        	if(tieneValor(encuesta.getCalimenf())){
                modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getCalimenf());
                datoCatalogo = sivinAdapter.getMessageResource(MainDBConstants.catKey + "='" + encuesta.getCalimenf() + "' and " + MainDBConstants.catRoot + "='CAT_AENF'", null);
                dato = new Bundle(); if(datoCatalogo!=null) dato.putString(SIMPLE_DATA_KEY, datoCatalogo.getSpanish()); modifPage.resetData(dato); modifPage.setmVisible(true);
            }
        	if(tieneValor(encuesta.getClecheenf())){
                modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getClecheenf());
                datoCatalogo = sivinAdapter.getMessageResource(MainDBConstants.catKey + "='" + encuesta.getClecheenf() + "' and " + MainDBConstants.catRoot + "='CAT_LENF'", null);
                dato = new Bundle(); if(datoCatalogo!=null) dato.putString(SIMPLE_DATA_KEY, datoCatalogo.getSpanish()); modifPage.resetData(dato); modifPage.setmVisible(true);
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
                        if (nextViv!=null) arguments.putSerializable(Constants.VIVIENDA , nextViv);
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
        	if (page.getTitle().equals(labels.getNumNinos())) {
        		limpiarNumEdades(false);
        		visible = true;
        		Integer numNinosDig = Integer.parseInt(page.getData().getString(TextPage.SIMPLE_DATA_KEY));
        		if(numNinosDig>1) changeStatus(mWizardModel.findByKey(labels.getN2()),visible);
        		if(numNinosDig>1) changeStatus(mWizardModel.findByKey(labels.getE2()),visible);
        		if(numNinosDig>2) changeStatus(mWizardModel.findByKey(labels.getN3()),visible);
        		if(numNinosDig>2) changeStatus(mWizardModel.findByKey(labels.getE3()),visible);
        		if(numNinosDig>3) changeStatus(mWizardModel.findByKey(labels.getN4()),visible);
        		if(numNinosDig>3) changeStatus(mWizardModel.findByKey(labels.getE4()),visible);
        		if(numNinosDig>4) changeStatus(mWizardModel.findByKey(labels.getN5()),visible);
        		if(numNinosDig>4) changeStatus(mWizardModel.findByKey(labels.getE5()),visible);
        		if(numNinosDig>5) changeStatus(mWizardModel.findByKey(labels.getN6()),visible);
        		if(numNinosDig>5) changeStatus(mWizardModel.findByKey(labels.getE6()),visible);
        	}
        	if (page.getTitle().equals(labels.getN1())) {
        		Integer numNinosDig = Integer.parseInt(mWizardModel.findByKey(labels.getNumNinos()).getData().getString(TextPage.SIMPLE_DATA_KEY));
        		seleccionarNino(nextViv, numNinosDig);
        	}
        	if (page.getTitle().equals(labels.getN2())) {
        		Integer numNinosDig = Integer.parseInt(mWizardModel.findByKey(labels.getNumNinos()).getData().getString(TextPage.SIMPLE_DATA_KEY));
        		seleccionarNino(nextViv, numNinosDig);
        	}
        	if (page.getTitle().equals(labels.getN3())) {
        		Integer numNinosDig = Integer.parseInt(mWizardModel.findByKey(labels.getNumNinos()).getData().getString(TextPage.SIMPLE_DATA_KEY));
        		seleccionarNino(nextViv, numNinosDig);
        	}
        	if (page.getTitle().equals(labels.getN4())) {
        		Integer numNinosDig = Integer.parseInt(mWizardModel.findByKey(labels.getNumNinos()).getData().getString(TextPage.SIMPLE_DATA_KEY));
        		seleccionarNino(nextViv, numNinosDig);
        	}
        	if (page.getTitle().equals(labels.getN5())) {
        		Integer numNinosDig = Integer.parseInt(mWizardModel.findByKey(labels.getNumNinos()).getData().getString(TextPage.SIMPLE_DATA_KEY));
        		seleccionarNino(nextViv, numNinosDig);
        	}
        	if (page.getTitle().equals(labels.getN6())) {
        		Integer numNinosDig = Integer.parseInt(mWizardModel.findByKey(labels.getNumNinos()).getData().getString(TextPage.SIMPLE_DATA_KEY));
        		seleccionarNino(nextViv, numNinosDig);
        	}
        	if (page.getTitle().equals(labels.getFnacselec())) {
        		Date fnacEntParsed = mDateFormat.parse(page.getData().getString(TextPage.SIMPLE_DATA_KEY));
               	Date fecEntrevista = encuesta.getFechaEntrevista();
               	CalcularEdad ce = new CalcularEdad();
               	Period fiff = ce.calcDiff(fnacEntParsed, fecEntrevista);
               	NumberPage edadValue = (NumberPage) mWizardModel.findByKey(labels.getEselect());
               	edadValue.setValue(String.valueOf(fiff.getYears()*12 + fiff.getMonths()));
               	edadValue.setmEnabled(false);
        	}
        	
        	if (page.getTitle().equals(labels.getCalim())) {
        		catalogo = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + page.getData().getString(TextPage.SIMPLE_DATA_KEY) + "' and " + MainDBConstants.catRoot + "='CAT_CALIM'", null);
        		visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && catalogo.getCatKey().matches("6");
        		changeStatus(mWizardModel.findByKey(labels.getVcome()),!visible);
        		changeStatus(mWizardModel.findByKey(labels.getConsalim()),!visible);
        		changeStatus(mWizardModel.findByKey(labels.getCalimenf()),!visible);
            }
        	onPageTreeChanged();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
    
    public void limpiarNumEdades(boolean valor) {
    	changeStatus(mWizardModel.findByKey(labels.getN2()),valor);
		changeStatus(mWizardModel.findByKey(labels.getE2()),valor);
		changeStatus(mWizardModel.findByKey(labels.getN3()),valor);
		changeStatus(mWizardModel.findByKey(labels.getE3()),valor);
		changeStatus(mWizardModel.findByKey(labels.getN4()),valor);
		changeStatus(mWizardModel.findByKey(labels.getE4()),valor);
		changeStatus(mWizardModel.findByKey(labels.getN5()),valor);
		changeStatus(mWizardModel.findByKey(labels.getE5()),valor);
		changeStatus(mWizardModel.findByKey(labels.getN6()),valor);
		changeStatus(mWizardModel.findByKey(labels.getE6()),valor);
    }
    
    public void seleccionarNino(Integer numEncuesta, Integer numNinos) {
    	SeleccionarNino seleccionarNino = new SeleccionarNino();
		Integer ninoSeleccionado = seleccionarNino.numSeleccionado(numEncuesta, numNinos);
		TextPage nSelecPage = (TextPage) mWizardModel.findByKey(labels.getNselec());
		TextPage nomSelecPage = (TextPage) mWizardModel.findByKey(labels.getNomselec());
		nSelecPage.setmEnabled(false);
		nomSelecPage.setmEnabled(false);
		if(ninoSeleccionado==1) {
			nSelecPage.setValue(ninoSeleccionado.toString());
			nomSelecPage.setValue(mWizardModel.findByKey(labels.getN1()).getData().getString(TextPage.SIMPLE_DATA_KEY));
		}
		else if(ninoSeleccionado==2) {
			nSelecPage.setValue(ninoSeleccionado.toString());
			nomSelecPage.setValue(mWizardModel.findByKey(labels.getN2()).getData().getString(TextPage.SIMPLE_DATA_KEY));
		}
		else if(ninoSeleccionado==3) {
			nSelecPage.setValue(ninoSeleccionado.toString());
			nomSelecPage.setValue(mWizardModel.findByKey(labels.getN3()).getData().getString(TextPage.SIMPLE_DATA_KEY));
		}
		else if(ninoSeleccionado==4) {
			nSelecPage.setValue(ninoSeleccionado.toString());
			nomSelecPage.setValue(mWizardModel.findByKey(labels.getN4()).getData().getString(TextPage.SIMPLE_DATA_KEY));
		}
		else if(ninoSeleccionado==5) {
			nSelecPage.setValue(ninoSeleccionado.toString());
			nomSelecPage.setValue(mWizardModel.findByKey(labels.getN5()).getData().getString(TextPage.SIMPLE_DATA_KEY));
		}
		else if(ninoSeleccionado==6) {
			nSelecPage.setValue(ninoSeleccionado.toString());
			nomSelecPage.setValue(mWizardModel.findByKey(labels.getN6()).getData().getString(TextPage.SIMPLE_DATA_KEY));
		}
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
            String numNinos = datos.getString(this.getString(R.string.numNinos));
            String n1 = datos.getString(this.getString(R.string.n1));
            String e1 = datos.getString(this.getString(R.string.e1));
            String n2 = datos.getString(this.getString(R.string.n2));
            String e2 = datos.getString(this.getString(R.string.e2));
            String n3 = datos.getString(this.getString(R.string.n3));
            String e3 = datos.getString(this.getString(R.string.e3));
            String n4 = datos.getString(this.getString(R.string.n4));
            String e4 = datos.getString(this.getString(R.string.e4));
            String n5 = datos.getString(this.getString(R.string.n5));
            String e5 = datos.getString(this.getString(R.string.e5));
            String n6 = datos.getString(this.getString(R.string.n6));
            String e6 = datos.getString(this.getString(R.string.e6));
            String nselec = datos.getString(this.getString(R.string.nselec));
            String nomselec = datos.getString(this.getString(R.string.nomselec));
            String fnacselec = datos.getString(this.getString(R.string.fnacselec));
            String eselect = datos.getString(this.getString(R.string.eselect));
            String sexselec = datos.getString(this.getString(R.string.sexselec));
            String calim = datos.getString(this.getString(R.string.calim));
            String vcome = datos.getString(this.getString(R.string.vcome));
            String consalim = datos.getString(this.getString(R.string.consalim));
            String calimenf = datos.getString(this.getString(R.string.calimenf));
            String clecheenf = datos.getString(this.getString(R.string.clecheenf));

        	
    		//Para recuperar catalogos
    		MessageResource catRespuesta;
    		if (tieneValor(numNinos)) {encuesta.setNumNinos(numNinos);} else {encuesta.setNumNinos(null);}
    		if (tieneValor(n1)) {encuesta.setN1(n1);} else {encuesta.setN1(null);}
    		if (tieneValor(e1)) {encuesta.setE1(e1);} else {encuesta.setE1(null);}
    		if (tieneValor(n2)) {encuesta.setN2(n2);} else {encuesta.setN2(null);}
    		if (tieneValor(e2)) {encuesta.setE2(e2);} else {encuesta.setE2(null);}
    		if (tieneValor(n3)) {encuesta.setN3(n3);} else {encuesta.setN3(null);}
    		if (tieneValor(e4)) {encuesta.setE3(e3);} else {encuesta.setE3(null);}
    		if (tieneValor(n4)) {encuesta.setN4(n4);} else {encuesta.setN4(null);}
    		if (tieneValor(e4)) {encuesta.setE4(e4);} else {encuesta.setE4(null);}
    		if (tieneValor(n5)) {encuesta.setN5(n5);} else {encuesta.setN5(null);}
    		if (tieneValor(e5)) {encuesta.setE5(e5);} else {encuesta.setE5(null);}
    		if (tieneValor(n6)) {encuesta.setN6(n6);} else {encuesta.setN6(null);}
    		if (tieneValor(e6)) {encuesta.setE6(e6);} else {encuesta.setE6(null);}
    		if (tieneValor(nselec)) {encuesta.setNselec(nselec);} else {encuesta.setNselec(null);}
    		if (tieneValor(nomselec)) {encuesta.setNomselec(nomselec);} else {encuesta.setNomselec(null);}
    		Date fnacSelecParsed = null;
            if (tieneValor(fnacselec)) {
	            try {
	            	fnacSelecParsed = mDateFormat.parse(fnacselec);
	    		} catch (ParseException e) {
	    			// TODO Auto-generated catch block
	    			e.printStackTrace();
	    			Toast toast = Toast.makeText(getApplicationContext(),e.getLocalizedMessage(),Toast.LENGTH_LONG);
	    			toast.show();
	    			finish();
	    		}
            }
            encuesta.setFnacselec(fnacSelecParsed);
            if (tieneValor(eselect)) {encuesta.setEselect(eselect);} else {encuesta.setEselect(null);}
            if (tieneValor(sexselec)) {
    			catRespuesta = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + sexselec + "' and " + MainDBConstants.catRoot + "='CAT_SEXO'", null);
    			if (catRespuesta!=null) encuesta.setSexselec(catRespuesta.getCatKey());
    		}
            else {
            	encuesta.setSexselec(null);
            }
            if (tieneValor(calim)) {
    			catRespuesta = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + calim + "' and " + MainDBConstants.catRoot + "='CAT_CALIM'", null);
    			if (catRespuesta!=null) encuesta.setCalim(catRespuesta.getCatKey());
    		}
            else {
            	encuesta.setCalim(null);
            }
            if (tieneValor(vcome)) {encuesta.setVcome(vcome);} else {encuesta.setVcome(null);}
            if (tieneValor(consalim)) {
    			catRespuesta = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + consalim + "' and " + MainDBConstants.catRoot + "='CAT_CONSALIM'", null);
    			if (catRespuesta!=null) encuesta.setConsalim(catRespuesta.getCatKey());
    		}
            else {
            	encuesta.setConsalim(null);
            }
            if (tieneValor(calimenf)) {
    			catRespuesta = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + calimenf + "' and " + MainDBConstants.catRoot + "='CAT_AENF'", null);
    			if (catRespuesta!=null) encuesta.setCalimenf(catRespuesta.getCatKey());
    		}
            else {
            	encuesta.setCalimenf(null);
            }
            if (tieneValor(clecheenf)) {
    			catRespuesta = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + clecheenf + "' and " + MainDBConstants.catRoot + "='CAT_LENF'", null);
    			if (catRespuesta!=null) encuesta.setClecheenf(catRespuesta.getCatKey());
    		}
            else {
            	encuesta.setClecheenf(null);
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
            if (nextViv!=null) arguments.putSerializable(Constants.VIVIENDA , nextViv);
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
