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
import ni.gob.minsa.sivin.forms.SeccionGForm;
import ni.gob.minsa.sivin.forms.SeccionGFormLabels;
import ni.gob.minsa.sivin.preferences.PreferencesActivity;
import ni.gob.minsa.sivin.utils.Constants;
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
import java.util.List;
import java.util.Map;


public class SeccionGActivity extends FragmentActivity implements
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
	private SeccionGFormLabels labels = new SeccionGFormLabels();
	public static final String SIMPLE_DATA_KEY = "_";
	private boolean barcode;
	

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
		infoMovil = new DeviceInfo(SeccionGActivity.this);
        segmento = (Segmento) getIntent().getExtras().getSerializable(Constants.SEGMENTO);
        encuesta = (Encuesta) getIntent().getExtras().getSerializable(Constants.ENCUESTA);
        nextViv = (Integer) getIntent().getExtras().getInt(Constants.VIVIENDA);
        barcode = settings.getBoolean(PreferencesActivity.KEY_BARCODE, true);
        
        String mPass = ((SivinApplication) this.getApplication()).getPassApp();
        mWizardModel = new SeccionGForm(this,mPass);
        if (savedInstanceState != null) {
            mWizardModel.load(savedInstanceState.getBundle("model"));
        }
        //Abre la base de datos
        try {
		sivinAdapter = new SivinAdapter(this.getApplicationContext(),mPass,false,false);
		sivinAdapter.open();

        if (encuesta != null) {
        	Bundle dato = null;
        	MessageResource datoCatalogo;
        	Page modifPage;
        	if(tieneValor(encuesta.getMsEnt())){
                modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getMsEnt());
                datoCatalogo = sivinAdapter.getMessageResource(MainDBConstants.catKey + "='" + encuesta.getMsEnt() + "' and " + MainDBConstants.catRoot + "='CAT_SINONA'", null);
                dato = new Bundle(); if(datoCatalogo!=null) dato.putString(SIMPLE_DATA_KEY, datoCatalogo.getSpanish()); modifPage.resetData(dato); modifPage.setmVisible(true);
                if(datoCatalogo.getCatKey().equals(Constants.SI)) {
                	if (!barcode) modifPage = (TextPage) mWizardModel.findByKey(labels.getCodMsEnt());
                	if (barcode) modifPage = (BarcodePage) mWizardModel.findByKey(labels.getCodMsEntBc());
                	dato = new Bundle();dato.putString(SIMPLE_DATA_KEY, encuesta.getCodMsEnt());modifPage.resetData(dato);modifPage.setmVisible(true);
                	modifPage = (NumberPage) mWizardModel.findByKey(labels.getHbEnt());
                	dato = new Bundle();dato.putString(SIMPLE_DATA_KEY, encuesta.getHbEnt().toString());modifPage.resetData(dato);modifPage.setmVisible(true);
                }
            }
        	if(tieneValor(encuesta.getMsNin())){
	        	modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getMsNin());
	            datoCatalogo = sivinAdapter.getMessageResource(MainDBConstants.catKey + "='" + encuesta.getMsNin() + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null);
	            dato = new Bundle(); if(datoCatalogo!=null) dato.putString(SIMPLE_DATA_KEY, datoCatalogo.getSpanish()); modifPage.resetData(dato); modifPage.setmVisible(true);
	            if(datoCatalogo.getCatKey().equals(Constants.SI)) {
	            	if (!barcode) modifPage = (TextPage) mWizardModel.findByKey(labels.getCodMsNin());
	            	if (barcode) modifPage = (BarcodePage) mWizardModel.findByKey(labels.getCodMsNinBc());
	            	dato = new Bundle();dato.putString(SIMPLE_DATA_KEY, encuesta.getCodMsNin());modifPage.resetData(dato);modifPage.setmVisible(true);
	            	modifPage = (NumberPage) mWizardModel.findByKey(labels.getHbNin());
	            	dato = new Bundle();dato.putString(SIMPLE_DATA_KEY, encuesta.getHbNin().toString());modifPage.resetData(dato);modifPage.setmVisible(true);
	            }
        	}
        	if(tieneValor(encuesta.getMoEnt())){
	            modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getMoEnt());
	            datoCatalogo = sivinAdapter.getMessageResource(MainDBConstants.catKey + "='" + encuesta.getMoEnt() + "' and " + MainDBConstants.catRoot + "='CAT_SINONA'", null);
	            dato = new Bundle(); if(datoCatalogo!=null) dato.putString(SIMPLE_DATA_KEY, datoCatalogo.getSpanish()); modifPage.resetData(dato); modifPage.setmVisible(true);
	            if(datoCatalogo.getCatKey().equals(Constants.SI)) {
	            	if (!barcode) modifPage = (TextPage) mWizardModel.findByKey(labels.getCodMoEnt());
	            	if (barcode) modifPage = (BarcodePage) mWizardModel.findByKey(labels.getCodMoEntBc());
	            	dato = new Bundle();dato.putString(SIMPLE_DATA_KEY, encuesta.getCodMoEnt());modifPage.resetData(dato);modifPage.setmVisible(true);
	            }
        	}
            if(tieneValor(encuesta.getPan())){
        		modifPage = (TextPage) mWizardModel.findByKey(labels.getPan());
	        	dato = new Bundle();dato.putString(SIMPLE_DATA_KEY, encuesta.getPan());modifPage.resetData(dato);modifPage.setmVisible(true);
            }
            if(tieneValor(encuesta.getMarcaSal())){
        		modifPage = (TextPage) mWizardModel.findByKey(labels.getMarcaSal());
	        	dato = new Bundle();dato.putString(SIMPLE_DATA_KEY, encuesta.getMarcaSal());modifPage.resetData(dato);modifPage.setmVisible(true);
            }
            if(tieneValor(encuesta.getMarcaAzucar())){
        		modifPage = (TextPage) mWizardModel.findByKey(labels.getMarcaAzucar());
	        	dato = new Bundle();dato.putString(SIMPLE_DATA_KEY, encuesta.getMarcaAzucar());modifPage.resetData(dato);modifPage.setmVisible(true);
            }
            if(tieneValor(encuesta.getSal())){
                modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getSal());
                datoCatalogo = sivinAdapter.getMessageResource(MainDBConstants.catKey + "='" + encuesta.getSal() + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null);
                dato = new Bundle(); if(datoCatalogo!=null) dato.putString(SIMPLE_DATA_KEY, datoCatalogo.getSpanish()); modifPage.resetData(dato); modifPage.setmVisible(true);
            }
            if(tieneValor(encuesta.getAzucar())){
                modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getAzucar());
                datoCatalogo = sivinAdapter.getMessageResource(MainDBConstants.catKey + "='" + encuesta.getAzucar() + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null);
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
        	if (page.getTitle().equals(labels.getMsEnt())) {
        		catalogo = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + page.getData().getString(TextPage.SIMPLE_DATA_KEY) + "' and " + MainDBConstants.catRoot + "='CAT_SINONA'", null);
        		visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && catalogo.getCatKey().matches(Constants.SI);
        		changeStatus(mWizardModel.findByKey(labels.getCodMsEnt()),visible);
        		changeStatus(mWizardModel.findByKey(labels.getCodMsEntBc()),visible);
        		changeStatus(mWizardModel.findByKey(labels.getHbEnt()),visible);
        		if (visible) {
        			changeStatus(mWizardModel.findByKey(labels.getCodMsEnt()),!barcode);
        			changeStatus(mWizardModel.findByKey(labels.getCodMsEntBc()),barcode);
        		}
            }
        	if (page.getTitle().equals(labels.getCodMsEntBc())||page.getTitle().equals(labels.getCodMsEnt())) {
        		String codigoEncuesta = encuesta.getCodigo() + "SE";
        		String codigoMuestra = page.getData().getString(TextPage.SIMPLE_DATA_KEY);
        		if(!codigoMuestra.matches(codigoEncuesta)) {
        			page.resetData(new Bundle());
        			Toast.makeText(getApplicationContext(), getString(R.string.sample_code_error) + "\n" + codigoMuestra,Toast.LENGTH_LONG).show();
        		}
        	}
        	if (page.getTitle().equals(labels.getCodMsNinBc())||page.getTitle().equals(labels.getCodMsNin())) {
        		String codigoEncuesta = encuesta.getCodigo() + "SN";
        		String codigoMuestra = page.getData().getString(TextPage.SIMPLE_DATA_KEY);
        		if(!codigoMuestra.matches(codigoEncuesta)) {
        			page.resetData(new Bundle());
        			Toast.makeText(getApplicationContext(), getString(R.string.sample_code_error) + "\n" + codigoMuestra,Toast.LENGTH_LONG).show();
        		}
        	}
        	if (page.getTitle().equals(labels.getCodMoEntBc())||page.getTitle().equals(labels.getCodMoEnt())) {
        		String codigoEncuesta = encuesta.getCodigo() + "OE";
        		String codigoMuestra = page.getData().getString(TextPage.SIMPLE_DATA_KEY);
        		if(!codigoMuestra.matches(codigoEncuesta)) {
        			page.resetData(new Bundle());
        			Toast.makeText(getApplicationContext(), getString(R.string.sample_code_error) + "\n" + codigoMuestra,Toast.LENGTH_LONG).show();
        		}
        	}
        	if (page.getTitle().equals(labels.getMsNin())) {
        		catalogo = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + page.getData().getString(TextPage.SIMPLE_DATA_KEY) + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null);
        		visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && catalogo.getCatKey().matches(Constants.SI);
        		changeStatus(mWizardModel.findByKey(labels.getCodMsNin()),visible);
        		changeStatus(mWizardModel.findByKey(labels.getCodMsNinBc()),visible);
        		changeStatus(mWizardModel.findByKey(labels.getHbNin()),visible);
        		if (visible) {
        			changeStatus(mWizardModel.findByKey(labels.getCodMsNin()),!barcode);
        			changeStatus(mWizardModel.findByKey(labels.getCodMsNinBc()),barcode);
        		}
            }
        	if (page.getTitle().equals(labels.getMoEnt())) {
        		catalogo = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + page.getData().getString(TextPage.SIMPLE_DATA_KEY) + "' and " + MainDBConstants.catRoot + "='CAT_SINONA'", null);
        		visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && catalogo.getCatKey().matches(Constants.SI);
        		changeStatus(mWizardModel.findByKey(labels.getCodMoEnt()),visible);
        		changeStatus(mWizardModel.findByKey(labels.getCodMoEntBc()),visible);
        		if (visible) {
        			changeStatus(mWizardModel.findByKey(labels.getCodMoEnt()),!barcode);
        			changeStatus(mWizardModel.findByKey(labels.getCodMoEntBc()),barcode);
        		}
            }
        	if (page.getTitle().equals(labels.getSal())) {
        		catalogo = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + page.getData().getString(TextPage.SIMPLE_DATA_KEY) + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null);
        		visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && catalogo.getCatKey().matches(Constants.SI);
        		changeStatus(mWizardModel.findByKey(labels.getMarcaSal()),visible);
            }
        	if (page.getTitle().equals(labels.getAzucar())) {
        		catalogo = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + page.getData().getString(TextPage.SIMPLE_DATA_KEY) + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null);
        		visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && catalogo.getCatKey().matches(Constants.SI);
        		changeStatus(mWizardModel.findByKey(labels.getMarcaAzucar()),visible);
            }
        	
        	onPageTreeChanged();
        }catch (Exception ex){
            ex.printStackTrace();
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
            String msEnt = datos.getString(this.getString(R.string.msEnt));
            String codMsEnt = datos.getString(this.getString(R.string.codMsEnt));
            String codMsEntBc = datos.getString(this.getString(R.string.codMsEntBc));
            String hbEnt = datos.getString(this.getString(R.string.hbEnt));
            String msNin = datos.getString(this.getString(R.string.msNin));
            String codMsNin = datos.getString(this.getString(R.string.codMsNin));
            String codMsNinBc = datos.getString(this.getString(R.string.codMsNinBc));
            String hbNin = datos.getString(this.getString(R.string.hbNin));
            String moEnt = datos.getString(this.getString(R.string.moEnt));
            String codMoEnt = datos.getString(this.getString(R.string.codMoEnt));
            String codMoEntBc = datos.getString(this.getString(R.string.codMoEntBc));
            String pan = datos.getString(this.getString(R.string.pan));
            String sal = datos.getString(this.getString(R.string.sal));
            String marcaSal = datos.getString(this.getString(R.string.marcaSal));
            String azucar = datos.getString(this.getString(R.string.azucar));
            String marcaAzucar = datos.getString(this.getString(R.string.marcaAzucar));


    		
    		//Para recuperar catalogos
    		MessageResource catRespuesta;
    		if (tieneValor(msEnt)) {
    			catRespuesta = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + msEnt + "' and " + MainDBConstants.catRoot + "='CAT_SINONA'", null);
    			if (catRespuesta!=null) encuesta.setMsEnt(catRespuesta.getCatKey());
    		}
            else {
            	encuesta.setMsEnt(null);
            }
    		if (tieneValor(codMsEnt)) {
    			encuesta.setCodMsEnt(codMsEnt);
    		}
    		else if (tieneValor(codMsEntBc)) {
    			encuesta.setCodMsEnt(codMsEntBc);
    		}
    		else {
    			encuesta.setCodMsEnt(null);
    		}
    		if (tieneValor(hbEnt)) {
    			Float hbEntValue = Float.parseFloat(hbEnt);
            	if(hbEntValue>0) encuesta.setHbEnt(hbEntValue);
    		} else {
    			encuesta.setHbEnt(null);
    		}
    		
    		if (tieneValor(msNin)) {
    			catRespuesta = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + msNin + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null);
    			if (catRespuesta!=null) encuesta.setMsNin(catRespuesta.getCatKey());
    		}
            else {
            	encuesta.setMsNin(null);
            }
    		if (tieneValor(codMsNin)) {
    			encuesta.setCodMsNin(codMsNin);
    		}
    		else if (tieneValor(codMsNinBc)) {
    			encuesta.setCodMsNin(codMsNinBc);
    		}
    		else {
    			encuesta.setCodMsNin(null);
    		}
    		if (tieneValor(hbNin)) {
    			Float hbNinValue = Float.parseFloat(hbNin);
            	if(hbNinValue>0) encuesta.setHbNin(hbNinValue);
    		} else {
    			encuesta.setHbEnt(null);
    		}
    		
    		if (tieneValor(moEnt)) {
    			catRespuesta = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + moEnt + "' and " + MainDBConstants.catRoot + "='CAT_SINONA'", null);
    			if (catRespuesta!=null) encuesta.setMoEnt(catRespuesta.getCatKey());
    		}
            else {
            	encuesta.setMoEnt(null);
            }
    		if (tieneValor(codMoEnt)) {
    			encuesta.setCodMoEnt(codMoEnt);
    		}
    		else if (tieneValor(codMoEntBc)) {
    			encuesta.setCodMoEnt(codMoEntBc);
    		}
    		else {
    			encuesta.setCodMoEnt(null);
    		}
    		
    		if (tieneValor(pan)) {encuesta.setPan(pan);} else {encuesta.setPan(null);}
    		
    		if (tieneValor(sal)) {
    			catRespuesta = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + sal + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null);
    			if (catRespuesta!=null) encuesta.setSal(catRespuesta.getCatKey());
    		}
            else {
            	encuesta.setSal(null);
            }
    		
    		if (tieneValor(marcaSal)) {encuesta.setMarcaSal(marcaSal);} else {encuesta.setMarcaSal(null);}
    		
    		if (tieneValor(azucar)) {
    			catRespuesta = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + azucar + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null);
    			if (catRespuesta!=null) encuesta.setAzucar(catRespuesta.getCatKey());
    		}
            else {
            	encuesta.setAzucar(null);
            }
    		
    		if (tieneValor(marcaAzucar)) {encuesta.setMarcaAzucar(marcaAzucar);} else {encuesta.setMarcaAzucar(null);}
           
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
