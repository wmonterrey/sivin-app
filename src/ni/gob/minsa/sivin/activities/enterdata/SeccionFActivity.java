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
import ni.gob.minsa.sivin.forms.SeccionFForm;
import ni.gob.minsa.sivin.forms.SeccionFFormLabels;
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;


public class SeccionFActivity extends FragmentActivity implements
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
	private SeccionFFormLabels labels = new SeccionFFormLabels();
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
		infoMovil = new DeviceInfo(SeccionFActivity.this);
        segmento = (Segmento) getIntent().getExtras().getSerializable(Constants.SEGMENTO);
        encuesta = (Encuesta) getIntent().getExtras().getSerializable(Constants.ENCUESTA);
        
        String mPass = ((SivinApplication) this.getApplication()).getPassApp();
        mWizardModel = new SeccionFForm(this,mPass);
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
        	datoCatalogo = sivinAdapter.getMessageResource(MainDBConstants.catKey + "='" + encuesta.getSexselec() + "' and " + MainDBConstants.catRoot + "='CAT_SEXO'", null);
        	modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getPesoTallaNin());
        	if(encuesta.getNselec()!=null && encuesta.getNomselec()!=null && encuesta.getFnacselec()!=null && encuesta.getEselect()!=null) {
        	modifPage.setHint(modifPage.getHint() + encuesta.getNselec() + "-" + encuesta.getNomselec() 
        										+"\n"+ this.getString(R.string.fnacselec).substring(4) + ": " + mDateFormat.format(encuesta.getFnacselec())
        										+"\n"+ this.getString(R.string.eselect).substring(4) + ": " + encuesta.getEselect()
        										+"\n"+ this.getString(R.string.sexselec).substring(4, 8) + ":" + datoCatalogo.getSpanish());
        	}
        	if(tieneValor(encuesta.getPesoTallaEnt())){
                modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getPesoTallaEnt());
                datoCatalogo = sivinAdapter.getMessageResource(MainDBConstants.catKey + "='" + encuesta.getPesoTallaEnt() + "' and " + MainDBConstants.catRoot + "='CAT_SINONA'", null);
                dato = new Bundle(); if(datoCatalogo!=null) dato.putString(SIMPLE_DATA_KEY, datoCatalogo.getSpanish()); modifPage.resetData(dato); modifPage.setmVisible(true);
                if(datoCatalogo.getCatKey().equals(Constants.SI)) {
                	modifPage = (NumberPage) mWizardModel.findByKey(labels.getPesoEnt1());
                	dato = new Bundle();dato.putString(SIMPLE_DATA_KEY, encuesta.getPesoEnt1().toString());modifPage.resetData(dato);modifPage.setmVisible(true);
                	modifPage = (NumberPage) mWizardModel.findByKey(labels.getPesoEnt2());
                	dato = new Bundle();dato.putString(SIMPLE_DATA_KEY, encuesta.getPesoEnt2().toString());modifPage.resetData(dato);modifPage.setmVisible(true);
                	modifPage = (NumberPage) mWizardModel.findByKey(labels.getTallaEnt1());
                	dato = new Bundle();dato.putString(SIMPLE_DATA_KEY, encuesta.getTallaEnt1().toString());modifPage.resetData(dato);modifPage.setmVisible(true);
                	modifPage = (NumberPage) mWizardModel.findByKey(labels.getTallaEnt2());
                	dato = new Bundle();dato.putString(SIMPLE_DATA_KEY, encuesta.getTallaEnt2().toString());modifPage.resetData(dato);modifPage.setmVisible(true);
                }
            }
        	if(tieneValor(encuesta.getPesoTallaNin())){
                modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getPesoTallaNin());
                datoCatalogo = sivinAdapter.getMessageResource(MainDBConstants.catKey + "='" + encuesta.getPesoTallaNin() + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null);
                dato = new Bundle(); if(datoCatalogo!=null) dato.putString(SIMPLE_DATA_KEY, datoCatalogo.getSpanish()); modifPage.resetData(dato); modifPage.setmVisible(true);
                if(datoCatalogo.getCatKey().equals(Constants.SI)) {
                	modifPage = (NumberPage) mWizardModel.findByKey(labels.getPesoNin1());
                	dato = new Bundle();dato.putString(SIMPLE_DATA_KEY, encuesta.getPesoNin1().toString());modifPage.resetData(dato);modifPage.setmVisible(true);
                	modifPage = (NumberPage) mWizardModel.findByKey(labels.getPesoNin2());
                	dato = new Bundle();dato.putString(SIMPLE_DATA_KEY, encuesta.getPesoNin2().toString());modifPage.resetData(dato);modifPage.setmVisible(true);
                	if(Integer.valueOf(encuesta.getEselect())<24) {
                    	modifPage = (NumberPage) mWizardModel.findByKey(labels.getLongNin1());
                    	dato = new Bundle();dato.putString(SIMPLE_DATA_KEY, encuesta.getLongNin1().toString());modifPage.resetData(dato);modifPage.setmVisible(true);
                    	modifPage = (NumberPage) mWizardModel.findByKey(labels.getLongNin2());
                    	dato = new Bundle();dato.putString(SIMPLE_DATA_KEY, encuesta.getLongNin2().toString());modifPage.resetData(dato);modifPage.setmVisible(true);
                	}
                	else {
                		modifPage = (NumberPage) mWizardModel.findByKey(labels.getTallaNin1());
                    	dato = new Bundle();dato.putString(SIMPLE_DATA_KEY, encuesta.getTallaNin1().toString());modifPage.resetData(dato);modifPage.setmVisible(true);
                    	modifPage = (NumberPage) mWizardModel.findByKey(labels.getTallaNin2());
                    	dato = new Bundle();dato.putString(SIMPLE_DATA_KEY, encuesta.getTallaNin2().toString());modifPage.resetData(dato);modifPage.setmVisible(true);
                	}
                }
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
        	if (page.getTitle().equals(labels.getPesoTallaEnt())) {
        		catalogo = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + page.getData().getString(TextPage.SIMPLE_DATA_KEY) + "' and " + MainDBConstants.catRoot + "='CAT_SINONA'", null);
        		visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && catalogo.getCatKey().matches(Constants.SI);
        		changeStatus(mWizardModel.findByKey(labels.getPesoEnt1()),visible);
        		changeStatus(mWizardModel.findByKey(labels.getPesoEnt2()),visible);
        		changeStatus(mWizardModel.findByKey(labels.getTallaEnt1()),visible);
        		changeStatus(mWizardModel.findByKey(labels.getTallaEnt2()),visible);
            }
        	if (page.getTitle().equals(labels.getPesoTallaNin())) {
        		catalogo = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + page.getData().getString(TextPage.SIMPLE_DATA_KEY) + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null);
        		visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && catalogo.getCatKey().matches(Constants.SI);
        		changeStatus(mWizardModel.findByKey(labels.getPesoNin1()),visible);
        		changeStatus(mWizardModel.findByKey(labels.getPesoNin2()),visible);
        		changeStatus(mWizardModel.findByKey(labels.getLongNin1()),visible);
        		changeStatus(mWizardModel.findByKey(labels.getLongNin2()),visible);
        		changeStatus(mWizardModel.findByKey(labels.getTallaNin1()),visible);
        		changeStatus(mWizardModel.findByKey(labels.getTallaNin2()),visible);
        		if (visible) {
        			changeStatus(mWizardModel.findByKey(labels.getLongNin1()),Integer.valueOf(encuesta.getEselect())<24);
        			changeStatus(mWizardModel.findByKey(labels.getLongNin2()),Integer.valueOf(encuesta.getEselect())<24);
        			changeStatus(mWizardModel.findByKey(labels.getTallaNin1()),!(Integer.valueOf(encuesta.getEselect())<24));
        			changeStatus(mWizardModel.findByKey(labels.getTallaNin2()),!(Integer.valueOf(encuesta.getEselect())<24));
        		}
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
            String pesoTallaEnt = datos.getString(this.getString(R.string.pesoTallaEnt));
            String pesoEnt1 = datos.getString(this.getString(R.string.pesoEnt1));
            String pesoEnt2 = datos.getString(this.getString(R.string.pesoEnt2));
            String tallaEnt1 = datos.getString(this.getString(R.string.tallaEnt1));
            String tallaEnt2 = datos.getString(this.getString(R.string.tallaEnt2));
            String pesoTallaNin = datos.getString(this.getString(R.string.pesoTallaNin));
            String pesoNin1 = datos.getString(this.getString(R.string.pesoNin1));
            String pesoNin2 = datos.getString(this.getString(R.string.pesoNin2));
            String longNin1 = datos.getString(this.getString(R.string.longNin1));
            String longNin2 = datos.getString(this.getString(R.string.longNin2));
            String tallaNin1 = datos.getString(this.getString(R.string.tallaNin1));
            String tallaNin2 = datos.getString(this.getString(R.string.tallaNin2));

    		
    		//Para recuperar catalogos
    		MessageResource catRespuesta;
            
            if (tieneValor(pesoTallaEnt)) {
    			catRespuesta = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + pesoTallaEnt + "' and " + MainDBConstants.catRoot + "='CAT_SINONA'", null);
    			if (catRespuesta!=null) encuesta.setPesoTallaEnt(catRespuesta.getCatKey());
    		}
            else {
            	encuesta.setPesoTallaEnt(null);
            }
            if (tieneValor(pesoEnt1)) {
            	Float pesoEnt1Value = Float.parseFloat(pesoEnt1);
            	if(pesoEnt1Value>0) encuesta.setPesoEnt1(pesoEnt1Value);
            } else {
            	encuesta.setPesoEnt1(null);
            }
            if (tieneValor(pesoEnt2)) {
            	Float pesoEnt2Value = Float.parseFloat(pesoEnt2);
            	if(pesoEnt2Value>0) encuesta.setPesoEnt2(pesoEnt2Value);
            } else {
            	encuesta.setPesoEnt2(null);
            }
            if (tieneValor(tallaEnt1)) {
            	Float tallaEnt1Value = Float.parseFloat(tallaEnt1);
            	if(tallaEnt1Value>0) encuesta.setTallaEnt1(tallaEnt1Value);
            } else {
            	encuesta.setTallaEnt1(null);
            }
            if (tieneValor(tallaEnt2)) {
            	Float tallaEnt2Value = Float.parseFloat(tallaEnt2);
            	if(tallaEnt2Value>0) encuesta.setTallaEnt2(tallaEnt2Value);
            } else {
            	encuesta.setTallaEnt2(null);
            }
            
            if (tieneValor(pesoTallaNin)) {
    			catRespuesta = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + pesoTallaNin + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null);
    			if (catRespuesta!=null) encuesta.setPesoTallaNin(catRespuesta.getCatKey());
    		}
            else {
            	encuesta.setPesoTallaNin(null);
            }
            if (tieneValor(pesoNin1)) {
            	Float pesoNin1Value = Float.parseFloat(pesoNin1);
            	if(pesoNin1Value>0) encuesta.setPesoNin1(pesoNin1Value);
            } else {
            	encuesta.setPesoNin1(null);
            }
            if (tieneValor(pesoNin2)) {
            	Float pesoNin2Value = Float.parseFloat(pesoNin2);
            	if(pesoNin2Value>0) encuesta.setPesoNin2(pesoNin2Value);
            } else {
            	encuesta.setPesoNin2(null);
            }
            if (tieneValor(tallaNin1)) {
            	Float tallaNin1Value = Float.parseFloat(tallaNin1);
            	if(tallaNin1Value>0) encuesta.setTallaNin1(tallaNin1Value);
            } else {
            	encuesta.setTallaNin1(null);
            }
            if (tieneValor(tallaNin2)) {
            	Float tallaNin2Value = Float.parseFloat(tallaNin2);
            	if(tallaNin2Value>0) encuesta.setTallaNin2(tallaNin2Value);
            } else {
            	encuesta.setTallaNin2(null);
            }
            if (tieneValor(longNin1)) {
            	Float longNin1Value = Float.parseFloat(longNin1);
            	if(longNin1Value>0) encuesta.setLongNin1(longNin1Value);
            } else {
            	encuesta.setLongNin1(null);
            }
            if (tieneValor(longNin2)) {
            	Float longNin2Value = Float.parseFloat(longNin2);
            	if(longNin2Value>0) encuesta.setLongNin2(longNin2Value);
            } else {
            	encuesta.setLongNin2(null);
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
