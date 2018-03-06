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
import ni.gob.minsa.sivin.activities.IngresarEncuestaActivity;
import ni.gob.minsa.sivin.activities.MenuEncuestaActivity;
import ni.gob.minsa.sivin.database.SivinAdapter;
import ni.gob.minsa.sivin.domain.Encuesta;
import ni.gob.minsa.sivin.domain.Encuestador;
import ni.gob.minsa.sivin.domain.MessageResource;
import ni.gob.minsa.sivin.domain.Segmento;
import ni.gob.minsa.sivin.forms.EncuestaIdentificacionForm;
import ni.gob.minsa.sivin.forms.EncuestaIdentificacionFormLabels;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


public class EncuestaIdentificacionActivity extends FragmentActivity implements
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
	private boolean notificarCambios = true;
	private EncuestaIdentificacionFormLabels labels = new EncuestaIdentificacionFormLabels();
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
		infoMovil = new DeviceInfo(EncuestaIdentificacionActivity.this);
        segmento = (Segmento) getIntent().getExtras().getSerializable(Constants.SEGMENTO);
        encuesta = (Encuesta) getIntent().getExtras().getSerializable(Constants.ENCUESTA);
		
        String mPass = ((SivinApplication) this.getApplication()).getPassApp();
        mWizardModel = new EncuestaIdentificacionForm(this,mPass);
        if (savedInstanceState != null) {
            mWizardModel.load(savedInstanceState.getBundle("model"));
        }
        try {
        //Abre la base de datos
		sivinAdapter = new SivinAdapter(this.getApplicationContext(),mPass,false,false);
		sivinAdapter.open();
		
		List<Encuesta> mEncuestas = sivinAdapter.getEncuestas(MainDBConstants.segmento + " = '"+ segmento.getIdent() + "'", MainDBConstants.numEncuesta);
		List<Integer> mNumeros = new ArrayList<Integer>();
		for (int i=1;i<11;i++) {
			mNumeros.add(i);
		}
		for(Encuesta enc:mEncuestas) {
			if(mNumeros.contains(enc.getNumEncuesta())) {
				mNumeros.remove(enc.getNumEncuesta());
			}
		}
		
		String[] numeros;
		numeros = new String[mNumeros.size()];
        int index = 0;
        for (Integer num: mNumeros){
        	numeros[index] = num.toString();
            index++;
        }
		
		
		SingleFixedChoicePage pageNumEncuesta = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getEncNum());
		pageNumEncuesta.setChoices(numeros);

        if (encuesta != null) {
        	Bundle dato = null;
        	Page modifPage;
	        if(tieneValor(encuesta.getJefeFamilia())){
	        	modifPage = (TextPage) mWizardModel.findByKey(labels.getJefeFamilia());
	        	dato = new Bundle();
	        	dato.putString(SIMPLE_DATA_KEY, encuesta.getJefeFamilia());
	        	modifPage.resetData(dato);
	        	modifPage.setmVisible(true);
	        }
	        if(encuesta.getNumEncuesta()!=null){
                modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getEncNum());
                dato = new Bundle();
                dato.putString(SIMPLE_DATA_KEY, encuesta.getNumEncuesta().toString());
                modifPage.resetData(dato);
                modifPage.setmVisible(true);
            }
	        if(tieneValor(encuesta.getSexJefeFamilia())){
                modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getSexJefeFamilia());
                MessageResource catSexo = sivinAdapter.getMessageResource(MainDBConstants.catKey + "='" + encuesta.getSexJefeFamilia() + "' and " + MainDBConstants.catRoot + "='CAT_SEXO'", null);
                dato = new Bundle();
                if(catSexo!=null) dato.putString(SIMPLE_DATA_KEY, catSexo.getSpanish());
                modifPage.resetData(dato);
                modifPage.setmVisible(true);
            }
	        if(encuesta.getFechaEntrevista()!=null){
		        modifPage = (NewDatePage) mWizardModel.findByKey(labels.getFechaEntrevista());
	        	dato = new Bundle();
	        	dato.putString(SIMPLE_DATA_KEY, mDateFormat.format(encuesta.getFechaEntrevista()));
	        	modifPage.resetData(dato);
	        	modifPage.setmVisible(true);
	        }
	        if(encuesta.getNumPersonas()!=null){
	        	modifPage = (NumberPage) mWizardModel.findByKey(labels.getNumPersonas());
	            dato = new Bundle();
	            dato.putString(SIMPLE_DATA_KEY, String.valueOf(encuesta.getNumPersonas()));
	            modifPage.resetData(dato);
	            modifPage.setmVisible(true);
	        }
	        if(encuesta.getEncuestador()!=null){
                modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getEncuestador());
                Encuestador encuestador = sivinAdapter.getEncuestador(MainDBConstants.codigo + "='"+ encuesta.getEncuestador().getCodigo() + "'", null);
                dato = new Bundle();
                if(encuestador!=null) dato.putString(SIMPLE_DATA_KEY, encuestador.getNombre());
                modifPage.resetData(dato);
                modifPage.setmVisible(true);
            }
	        /*if(encuesta.getSupervisor()!=null){
                modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getSupervisor());
                Supervisor supervisor = sivinAdapter.getSupervisor(MainDBConstants.codigo + "='"+ encuesta.getSupervisor().getCodigo() + "'", null);
                dato = new Bundle();
                if(supervisor!=null) dato.putString(SIMPLE_DATA_KEY, supervisor.getNombre());
                modifPage.resetData(dato);
                modifPage.setmVisible(true);
            }*/
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
                        if (encuesta.getIdent()!=null) {
                        	i = new Intent(getApplicationContext(),
                                MenuEncuestaActivity.class);
                        	i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            i.putExtras(arguments);
                            startActivity(i);
                        }else {
                        	i = new Intent(getApplicationContext(),
                					IngresarEncuestaActivity.class);
                			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                			startActivity(i);
                        }
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
	    	updateConstrains();
	        if (recalculateCutOffPage()) {
	        	if (notificarCambios) mPagerAdapter.notifyDataSetChanged();
	            updateBottomBar();
	        }
	        notificarCambios = true;
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
    
    
    public void updateConstrains(){
        
    }
    
    public void updateModel(Page page){
        try{
            //boolean visible = false;
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
    
    public void changeStatus(Page page, boolean visible, String hint){
    	String clase = page.getClass().toString();
    	if (clase.equals("class ni.gob.minsa.sivin.wizard.model.SingleFixedChoicePage")){
    		SingleFixedChoicePage modifPage = (SingleFixedChoicePage) page; modifPage.resetData(new Bundle()); modifPage.setmVisible(visible);
    	}
    	else if (clase.equals("class ni.gob.minsa.sivin.wizard.model.BarcodePage")){
    		BarcodePage modifPage = (BarcodePage) page; modifPage.setValue(""); modifPage.setmVisible(visible);
    	}
    	else if (clase.equals("class ni.gob.minsa.sivin.wizard.model.LabelPage")){
    		LabelPage modifPage = (LabelPage) page; modifPage.setmVisible(visible);
    	}
    	else if (clase.equals("class ni.gob.minsa.sivin.wizard.model.TextPage")){
    		TextPage modifPage = (TextPage) page; modifPage.setValue(""); modifPage.setmVisible(visible);
    	}
    	else if (clase.equals("class ni.gob.minsa.sivin.wizard.model.NumberPage")){
    		NumberPage modifPage = (NumberPage) page; modifPage.setValue(""); modifPage.setmVisible(visible);
    	}
    	else if (clase.equals("class ni.gob.minsa.sivin.wizard.model.MultipleFixedChoicePage")){
    		MultipleFixedChoicePage modifPage = (MultipleFixedChoicePage) page; modifPage.setValue(""); modifPage.setmVisible(visible);
    	}
    	else if (clase.equals("class ni.gob.minsa.sivin.wizard.model.DatePage")){
    		DatePage modifPage = (DatePage) page; modifPage.setValue(""); modifPage.setmVisible(visible);
    	}
    	else if (clase.equals("class ni.gob.minsa.sivin.wizard.model.NewDatePage")){
    		NewDatePage modifPage = (NewDatePage) page; modifPage.setValue(""); modifPage.setmVisible(visible);
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
            String ident = infoMovil.getId();
            Date fechaEntrevista = null;
    		try {
    			fechaEntrevista = mDateFormat.parse(datos.getString(this.getString(R.string.fechaEntrevista)));
    		} catch (ParseException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    			Toast toast = Toast.makeText(getApplicationContext(),e.getLocalizedMessage(),Toast.LENGTH_LONG);
    			toast.show();
    			finish();
    		}
            String jefeFamilia = datos.getString(this.getString(R.string.jefeFamilia));
            String sexJefeFamilia = datos.getString(this.getString(R.string.sexJefeFamilia));
            String numPersonas = datos.getString(this.getString(R.string.numPersonas));
            String encuestador = datos.getString(this.getString(R.string.encuestador));
            String encNum = datos.getString(this.getString(R.string.encNum));
            //String supervisor = datos.getString(this.getString(R.string.supervisor));
            
            if (tieneValor(jefeFamilia)) {
                encuesta.setJefeFamilia(jefeFamilia);
            } else {
                encuesta.setJefeFamilia(null);
            }
            if (tieneValor(sexJefeFamilia)) {
    			MessageResource catSexo = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + sexJefeFamilia + "' and " + MainDBConstants.catRoot + "='CAT_SEXO'", null);
    			if (catSexo!=null) encuesta.setSexJefeFamilia(catSexo.getCatKey());
    		}
            if (tieneValor(sexJefeFamilia)) {
    			MessageResource catSexo = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + sexJefeFamilia + "' and " + MainDBConstants.catRoot + "='CAT_SEXO'", null);
    			if (catSexo!=null) encuesta.setSexJefeFamilia(catSexo.getCatKey());
    		}
            if (tieneValor(encuestador)) {
            	Encuestador catEncuestador = sivinAdapter.getEncuestador(MainDBConstants.nombre + "='"+ encuestador + "'", null);
    			if (catEncuestador!=null) encuesta.setEncuestador(catEncuestador);
    		}
            /*if (tieneValor(supervisor)) {
            	Supervisor catSupervisor = sivinAdapter.getSupervisor(MainDBConstants.nombre + "='"+ supervisor + "'", null);
    			if (catSupervisor!=null) encuesta.setSupervisor(catSupervisor);
    		}*/
            if (tieneValor(numPersonas)) encuesta.setNumPersonas(Integer.valueOf(numPersonas));
            
            encuesta.setSegmento(segmento);
            encuesta.setNumEncuesta(Integer.valueOf(encNum));
            encuesta.setFechaEntrevista(fechaEntrevista);
            if (encuesta.getRecordDate()==null) encuesta.setRecordDate(new Date());
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
            encuesta.setCodigo(segmento.getCodigo()+"-"+encNum);
            
            if(encuesta.getIdent()==null) {
            	encuesta.setIdent(ident);
            	sivinAdapter.crearEncuesta(encuesta);
            }else {
            	sivinAdapter.editarEncuesta(encuesta);
            }

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
