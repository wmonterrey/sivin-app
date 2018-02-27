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
import ni.gob.minsa.sivin.forms.SeccionAForm;
import ni.gob.minsa.sivin.forms.SeccionAFormLabels;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class SeccionAActivity extends FragmentActivity implements
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
	private SeccionAFormLabels labels = new SeccionAFormLabels();
	public static final String SIMPLE_DATA_KEY = "_";

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
		infoMovil = new DeviceInfo(SeccionAActivity.this);
        segmento = (Segmento) getIntent().getExtras().getSerializable(Constants.SEGMENTO);
        encuesta = (Encuesta) getIntent().getExtras().getSerializable(Constants.ENCUESTA);
        nextViv = (Integer) getIntent().getExtras().getInt(Constants.VIVIENDA);
        
        String mPass = ((SivinApplication) this.getApplication()).getPassApp();
        mWizardModel = new SeccionAForm(this,mPass);
        if (savedInstanceState != null) {
            mWizardModel.load(savedInstanceState.getBundle("model"));
        }
        try {
        //Abre la base de datos
		sivinAdapter = new SivinAdapter(this.getApplicationContext(),mPass,false,false);
		sivinAdapter.open();

        if (encuesta != null) {
        	Bundle dato = null;
        	Page modifPage;
	        if(tieneValor(encuesta.getAgua())){
                modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getAgua());
                MessageResource catAgua = sivinAdapter.getMessageResource(MainDBConstants.catKey + "='" + encuesta.getAgua() + "' and " + MainDBConstants.catRoot + "='CAT_AGUA'", null);
                dato = new Bundle();
                if(catAgua!=null) dato.putString(SIMPLE_DATA_KEY, catAgua.getSpanish());
                modifPage.resetData(dato);
                modifPage.setmVisible(true);
            }
	        if(tieneValor(encuesta.getOagua())){
	        	modifPage = (TextPage) mWizardModel.findByKey(labels.getOagua());
	        	dato = new Bundle();
	        	dato.putString(SIMPLE_DATA_KEY, encuesta.getOagua());
	        	modifPage.resetData(dato);
	        	modifPage.setmVisible(true);
	        }
	        if(encuesta.getCuartos()!=null && encuesta.getCuartos()>0){
	        	modifPage = (NumberPage) mWizardModel.findByKey(labels.getCuartos());
	            dato = new Bundle();
	            dato.putString(SIMPLE_DATA_KEY, String.valueOf(encuesta.getCuartos()));
	            modifPage.resetData(dato);
	            modifPage.setmVisible(true);
	        }
	        if(tieneValor(encuesta.getLugNecesidades())){
                modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getLugNecesidades());
                MessageResource catNecesidades = sivinAdapter.getMessageResource(MainDBConstants.catKey + "='" + encuesta.getLugNecesidades() + "' and " + MainDBConstants.catRoot + "='CAT_NECESIDADES'", null);
                dato = new Bundle();
                if(catNecesidades!=null) dato.putString(SIMPLE_DATA_KEY, catNecesidades.getSpanish());
                modifPage.resetData(dato);
                modifPage.setmVisible(true);
            }
	        if(tieneValor(encuesta.getOlugNecesidades())){
	        	modifPage = (TextPage) mWizardModel.findByKey(labels.getOlugNecesidades());
	        	dato = new Bundle();
	        	dato.putString(SIMPLE_DATA_KEY, encuesta.getOlugNecesidades());
	        	modifPage.resetData(dato);
	        	modifPage.setmVisible(true);
	        }
	        if(tieneValor(encuesta.getUsaCocinar())){
                modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getUsaCocinar());
                MessageResource catCocina = sivinAdapter.getMessageResource(MainDBConstants.catKey + "='" + encuesta.getUsaCocinar() + "' and " + MainDBConstants.catRoot + "='CAT_COCINA'", null);
                dato = new Bundle();
                if(catCocina!=null) dato.putString(SIMPLE_DATA_KEY, catCocina.getSpanish());
                modifPage.resetData(dato);
                modifPage.setmVisible(true);
            }
	        if(tieneValor(encuesta.getOusaCocinar())){
	        	modifPage = (TextPage) mWizardModel.findByKey(labels.getOusaCocinar());
	        	dato = new Bundle();
	        	dato.putString(SIMPLE_DATA_KEY, encuesta.getOusaCocinar());
	        	modifPage.resetData(dato);
	        	modifPage.setmVisible(true);
	        }
	        if(tieneValor(encuesta.getArticulos())){
                modifPage = (MultipleFixedChoicePage) mWizardModel.findByKey(labels.getArticulos());
                String codArticulos = encuesta.getArticulos().replaceAll("," , "','");
                List<String> descArticulos = new ArrayList<String>();
                List<MessageResource> msArticulos = sivinAdapter.getMessageResources(MainDBConstants.catKey + " in ('" + codArticulos + "') and " + MainDBConstants.catRoot + "='CAT_ARTICULOS'", null);
                for(MessageResource ms : msArticulos){
                    descArticulos.add(ms.getSpanish());
                }
                dato = new Bundle();
                dato.putStringArrayList(SIMPLE_DATA_KEY, (ArrayList<String>) descArticulos);
                modifPage.resetData(dato);
                modifPage.setmVisible(true);
            }
	        if(tieneValor(encuesta.getOarticulos())){
	        	modifPage = (TextPage) mWizardModel.findByKey(labels.getOarticulos());
	        	dato = new Bundle();
	        	dato.putString(SIMPLE_DATA_KEY, encuesta.getOarticulos());
	        	modifPage.resetData(dato);
	        	modifPage.setmVisible(true);
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
            if (page.getTitle().equals(labels.getAgua())) {
            	catalogo = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + page.getData().getString(TextPage.SIMPLE_DATA_KEY) + "' and " + MainDBConstants.catRoot + "='CAT_AGUA'", null);
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && catalogo.getCatKey().matches(Constants.OTRO);
                changeStatus(mWizardModel.findByKey(labels.getOagua()),visible);
            }
            if (page.getTitle().equals(labels.getLugNecesidades())) {
            	catalogo = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + page.getData().getString(TextPage.SIMPLE_DATA_KEY) + "' and " + MainDBConstants.catRoot + "='CAT_NECESIDADES'", null);
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && catalogo.getCatKey().matches(Constants.OTRO);
                changeStatus(mWizardModel.findByKey(labels.getOlugNecesidades()),visible);
            }
            if (page.getTitle().equals(labels.getUsaCocinar())) {
            	catalogo = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + page.getData().getString(TextPage.SIMPLE_DATA_KEY) + "' and " + MainDBConstants.catRoot + "='CAT_COCINA'", null);
                visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && catalogo.getCatKey().matches(Constants.OTRO);
                changeStatus(mWizardModel.findByKey(labels.getOusaCocinar()),visible);
            }
            if (page.getTitle().equals(labels.getArticulos())) {
            	ArrayList<String> respuestas = page.getData().getStringArrayList(TextPage.SIMPLE_DATA_KEY);
            	for(String respuesta:respuestas) {
            		catalogo = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + respuesta + "' and " + MainDBConstants.catRoot + "='CAT_ARTICULOS'", null);
            		if(catalogo.getCatKey().matches(Constants.OTRO)) {
            			visible = true;
            			break;
            		}
            	}
            	changeStatus(mWizardModel.findByKey(labels.getOarticulos()),visible);
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
            
            String agua = datos.getString(this.getString(R.string.agua));
            String oagua = datos.getString(this.getString(R.string.oagua));
            String cuartos = datos.getString(this.getString(R.string.cuartos));
            String lugNecesidades = datos.getString(this.getString(R.string.lugNecesidades));
            String olugNecesidades = datos.getString(this.getString(R.string.olugNecesidades));
            String usaCocinar = datos.getString(this.getString(R.string.usaCocinar));
            String ousaCocinar = datos.getString(this.getString(R.string.ousaCocinar));
            String articulos = datos.getString(this.getString(R.string.articulos));
            String oarticulos = datos.getString(this.getString(R.string.oarticulos));
            
            
            if (tieneValor(agua)) {
    			MessageResource catAgua = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + agua + "' and " + MainDBConstants.catRoot + "='CAT_AGUA'", null);
    			if (catAgua!=null) encuesta.setAgua(catAgua.getCatKey());
    		}
            if (tieneValor(oagua)) {
                encuesta.setOagua(oagua);
            } else {
                encuesta.setOagua(null);
            }
            if (tieneValor(cuartos)) encuesta.setCuartos(Integer.valueOf(cuartos));
            if (tieneValor(lugNecesidades)) {
    			MessageResource catLugNecesidades = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + lugNecesidades + "' and " + MainDBConstants.catRoot + "='CAT_NECESIDADES'", null);
    			if (catLugNecesidades!=null) encuesta.setLugNecesidades(catLugNecesidades.getCatKey());
    		}
            if (tieneValor(olugNecesidades)) {
                encuesta.setOlugNecesidades(olugNecesidades);
            } else {
                encuesta.setOlugNecesidades(null);
            }
            if (tieneValor(usaCocinar)) {
    			MessageResource catUsaCocinar = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + usaCocinar + "' and " + MainDBConstants.catRoot + "='CAT_COCINA'", null);
    			if (catUsaCocinar!=null) encuesta.setUsaCocinar(catUsaCocinar.getCatKey());
    		}
            if (tieneValor(ousaCocinar)) {
                encuesta.setOusaCocinar(ousaCocinar);
            } else {
                encuesta.setOusaCocinar(null);
            }
            
            if (tieneValor(articulos)) {
                String keysArticulos = "";
                articulos = articulos.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll(", " , "','");
                List<MessageResource> msArticulos = sivinAdapter.getMessageResources(MainDBConstants.spanish + " in ('" + articulos + "') and "
                        + MainDBConstants.catRoot + "='CAT_ARTICULOS'", null);
                for(MessageResource ms : msArticulos) {
                    keysArticulos += ms.getCatKey() + ",";
                }
                if (!keysArticulos.isEmpty())
                    keysArticulos = keysArticulos.substring(0, keysArticulos.length() - 1);
                encuesta.setArticulos(keysArticulos);
            } else {
            	encuesta.setArticulos(null);
            }
            
            if (tieneValor(oarticulos)) {
                encuesta.setOarticulos(oarticulos);
            } else {
                encuesta.setOarticulos(null);
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
