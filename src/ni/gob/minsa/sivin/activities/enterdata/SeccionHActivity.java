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
import ni.gob.minsa.sivin.forms.SeccionHForm;
import ni.gob.minsa.sivin.forms.SeccionHFormLabels;
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


public class SeccionHActivity extends FragmentActivity implements
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
	private SeccionHFormLabels labels = new SeccionHFormLabels();
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
		infoMovil = new DeviceInfo(SeccionHActivity.this);
        segmento = (Segmento) getIntent().getExtras().getSerializable(Constants.SEGMENTO);
        encuesta = (Encuesta) getIntent().getExtras().getSerializable(Constants.ENCUESTA);
        nextViv = (Integer) getIntent().getExtras().getInt(Constants.VIVIENDA);
        
        String mPass = ((SivinApplication) this.getApplication()).getPassApp();
        mWizardModel = new SeccionHForm(this,mPass);
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
        	if(tieneValor(encuesta.getPatConsAzuc())){  modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getPatConsAzuc());datoCatalogo = sivinAdapter.getMessageResource(MainDBConstants.catKey + "='" + encuesta.getPatConsAzuc() + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null);  dato = new Bundle(); if(datoCatalogo!=null) dato.putString(SIMPLE_DATA_KEY, datoCatalogo.getSpanish()); modifPage.resetData(dato); modifPage.setmVisible(true);}
        	if(tieneValor(encuesta.getPatConsAzucFrec())){  
        		modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getPatConsAzucFrec());
        		datoCatalogo = sivinAdapter.getMessageResource(MainDBConstants.catKey + "='" + encuesta.getPatConsAzucFrec() + "' and " + MainDBConstants.catRoot + "='CAT_FRECCON'", null);  
        		dato = new Bundle(); 
        		if(datoCatalogo!=null) dato.putString(SIMPLE_DATA_KEY, datoCatalogo.getSpanish()); 
        		modifPage.resetData(dato); 
        		modifPage.setmVisible(true);
        	}
        	if(tieneValor(encuesta.getPatConsSal())){  modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getPatConsSal());datoCatalogo = sivinAdapter.getMessageResource(MainDBConstants.catKey + "='" + encuesta.getPatConsSal() + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null);  dato = new Bundle(); if(datoCatalogo!=null) dato.putString(SIMPLE_DATA_KEY, datoCatalogo.getSpanish()); modifPage.resetData(dato); modifPage.setmVisible(true);}
        	if(tieneValor(encuesta.getPatConsSalFrec())){  
        		modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getPatConsSalFrec());
        		datoCatalogo = sivinAdapter.getMessageResource(MainDBConstants.catKey + "='" + encuesta.getPatConsSalFrec() + "' and " + MainDBConstants.catRoot + "='CAT_FRECCON'", null);  
        		dato = new Bundle(); 
        		if(datoCatalogo!=null) dato.putString(SIMPLE_DATA_KEY, datoCatalogo.getSpanish()); 
        		modifPage.resetData(dato); 
        		modifPage.setmVisible(true);
        	}
        	if(tieneValor(encuesta.getPatConsArroz())){  modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getPatConsArroz());datoCatalogo = sivinAdapter.getMessageResource(MainDBConstants.catKey + "='" + encuesta.getPatConsArroz() + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null);  dato = new Bundle(); if(datoCatalogo!=null) dato.putString(SIMPLE_DATA_KEY, datoCatalogo.getSpanish()); modifPage.resetData(dato); modifPage.setmVisible(true);}
        	if(tieneValor(encuesta.getPatConsArrozFrec())){  modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getPatConsArrozFrec());datoCatalogo = sivinAdapter.getMessageResource(MainDBConstants.catKey + "='" + encuesta.getPatConsArrozFrec() + "' and " + MainDBConstants.catRoot + "='CAT_FRECCON'", null);  dato = new Bundle(); if(datoCatalogo!=null) dato.putString(SIMPLE_DATA_KEY, datoCatalogo.getSpanish()); modifPage.resetData(dato); modifPage.setmVisible(true);}
        	if(tieneValor(encuesta.getPatConsAcei())){  modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getPatConsAcei());datoCatalogo = sivinAdapter.getMessageResource(MainDBConstants.catKey + "='" + encuesta.getPatConsAcei() + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null);  dato = new Bundle(); if(datoCatalogo!=null) dato.putString(SIMPLE_DATA_KEY, datoCatalogo.getSpanish()); modifPage.resetData(dato); modifPage.setmVisible(true);}
        	if(tieneValor(encuesta.getPatConsAceiFrec())){  modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getPatConsAceiFrec());datoCatalogo = sivinAdapter.getMessageResource(MainDBConstants.catKey + "='" + encuesta.getPatConsAceiFrec() + "' and " + MainDBConstants.catRoot + "='CAT_FRECCON'", null);  dato = new Bundle(); if(datoCatalogo!=null) dato.putString(SIMPLE_DATA_KEY, datoCatalogo.getSpanish()); modifPage.resetData(dato); modifPage.setmVisible(true);}
        	if(tieneValor(encuesta.getPatConsFri())){  modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getPatConsFri());datoCatalogo = sivinAdapter.getMessageResource(MainDBConstants.catKey + "='" + encuesta.getPatConsFri() + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null);  dato = new Bundle(); if(datoCatalogo!=null) dato.putString(SIMPLE_DATA_KEY, datoCatalogo.getSpanish()); modifPage.resetData(dato); modifPage.setmVisible(true);}
        	if(tieneValor(encuesta.getPatConsFriFrec())){  modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getPatConsFriFrec());datoCatalogo = sivinAdapter.getMessageResource(MainDBConstants.catKey + "='" + encuesta.getPatConsFriFrec() + "' and " + MainDBConstants.catRoot + "='CAT_FRECCON'", null);  dato = new Bundle(); if(datoCatalogo!=null) dato.putString(SIMPLE_DATA_KEY, datoCatalogo.getSpanish()); modifPage.resetData(dato); modifPage.setmVisible(true);}
        	if(tieneValor(encuesta.getPatConsCebo())){  modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getPatConsCebo());datoCatalogo = sivinAdapter.getMessageResource(MainDBConstants.catKey + "='" + encuesta.getPatConsCebo() + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null);  dato = new Bundle(); if(datoCatalogo!=null) dato.putString(SIMPLE_DATA_KEY, datoCatalogo.getSpanish()); modifPage.resetData(dato); modifPage.setmVisible(true);}
        	if(tieneValor(encuesta.getPatConsCeboFrec())){  modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getPatConsCeboFrec());datoCatalogo = sivinAdapter.getMessageResource(MainDBConstants.catKey + "='" + encuesta.getPatConsCeboFrec() + "' and " + MainDBConstants.catRoot + "='CAT_FRECCON'", null);  dato = new Bundle(); if(datoCatalogo!=null) dato.putString(SIMPLE_DATA_KEY, datoCatalogo.getSpanish()); modifPage.resetData(dato); modifPage.setmVisible(true);}
        	if(tieneValor(encuesta.getPatConsChi())){  modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getPatConsChi());datoCatalogo = sivinAdapter.getMessageResource(MainDBConstants.catKey + "='" + encuesta.getPatConsChi() + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null);  dato = new Bundle(); if(datoCatalogo!=null) dato.putString(SIMPLE_DATA_KEY, datoCatalogo.getSpanish()); modifPage.resetData(dato); modifPage.setmVisible(true);}
        	if(tieneValor(encuesta.getPatConsChiFrec())){  modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getPatConsChiFrec());datoCatalogo = sivinAdapter.getMessageResource(MainDBConstants.catKey + "='" + encuesta.getPatConsChiFrec() + "' and " + MainDBConstants.catRoot + "='CAT_FRECCON'", null);  dato = new Bundle(); if(datoCatalogo!=null) dato.putString(SIMPLE_DATA_KEY, datoCatalogo.getSpanish()); modifPage.resetData(dato); modifPage.setmVisible(true);}
        	if(tieneValor(encuesta.getPatConsQue())){  modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getPatConsQue());datoCatalogo = sivinAdapter.getMessageResource(MainDBConstants.catKey + "='" + encuesta.getPatConsQue() + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null);  dato = new Bundle(); if(datoCatalogo!=null) dato.putString(SIMPLE_DATA_KEY, datoCatalogo.getSpanish()); modifPage.resetData(dato); modifPage.setmVisible(true);}
        	if(tieneValor(encuesta.getPatConsQueFrec())){  modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getPatConsQueFrec());datoCatalogo = sivinAdapter.getMessageResource(MainDBConstants.catKey + "='" + encuesta.getPatConsQueFrec() + "' and " + MainDBConstants.catRoot + "='CAT_FRECCON'", null);  dato = new Bundle(); if(datoCatalogo!=null) dato.putString(SIMPLE_DATA_KEY, datoCatalogo.getSpanish()); modifPage.resetData(dato); modifPage.setmVisible(true);}
        	if(tieneValor(encuesta.getPatConsCafe())){  modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getPatConsCafe());datoCatalogo = sivinAdapter.getMessageResource(MainDBConstants.catKey + "='" + encuesta.getPatConsCafe() + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null);  dato = new Bundle(); if(datoCatalogo!=null) dato.putString(SIMPLE_DATA_KEY, datoCatalogo.getSpanish()); modifPage.resetData(dato); modifPage.setmVisible(true);}
        	if(tieneValor(encuesta.getPatConsCafeFrec())){  modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getPatConsCafeFrec());datoCatalogo = sivinAdapter.getMessageResource(MainDBConstants.catKey + "='" + encuesta.getPatConsCafeFrec() + "' and " + MainDBConstants.catRoot + "='CAT_FRECCON'", null);  dato = new Bundle(); if(datoCatalogo!=null) dato.putString(SIMPLE_DATA_KEY, datoCatalogo.getSpanish()); modifPage.resetData(dato); modifPage.setmVisible(true);}
        	if(tieneValor(encuesta.getPatConsTor())){  modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getPatConsTor());datoCatalogo = sivinAdapter.getMessageResource(MainDBConstants.catKey + "='" + encuesta.getPatConsTor() + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null);  dato = new Bundle(); if(datoCatalogo!=null) dato.putString(SIMPLE_DATA_KEY, datoCatalogo.getSpanish()); modifPage.resetData(dato); modifPage.setmVisible(true);}
        	if(tieneValor(encuesta.getPatConsTorFrec())){  modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getPatConsTorFrec());datoCatalogo = sivinAdapter.getMessageResource(MainDBConstants.catKey + "='" + encuesta.getPatConsTorFrec() + "' and " + MainDBConstants.catRoot + "='CAT_FRECCON'", null);  dato = new Bundle(); if(datoCatalogo!=null) dato.putString(SIMPLE_DATA_KEY, datoCatalogo.getSpanish()); modifPage.resetData(dato); modifPage.setmVisible(true);}
        	if(tieneValor(encuesta.getPatConsCar())){  modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getPatConsCar());datoCatalogo = sivinAdapter.getMessageResource(MainDBConstants.catKey + "='" + encuesta.getPatConsCar() + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null);  dato = new Bundle(); if(datoCatalogo!=null) dato.putString(SIMPLE_DATA_KEY, datoCatalogo.getSpanish()); modifPage.resetData(dato); modifPage.setmVisible(true);}
        	if(tieneValor(encuesta.getPatConsCarFrec())){  modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getPatConsCarFrec());datoCatalogo = sivinAdapter.getMessageResource(MainDBConstants.catKey + "='" + encuesta.getPatConsCarFrec() + "' and " + MainDBConstants.catRoot + "='CAT_FRECCON'", null);  dato = new Bundle(); if(datoCatalogo!=null) dato.putString(SIMPLE_DATA_KEY, datoCatalogo.getSpanish()); modifPage.resetData(dato); modifPage.setmVisible(true);}
        	if(tieneValor(encuesta.getPatConsHue())){  modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getPatConsHue());datoCatalogo = sivinAdapter.getMessageResource(MainDBConstants.catKey + "='" + encuesta.getPatConsHue() + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null);  dato = new Bundle(); if(datoCatalogo!=null) dato.putString(SIMPLE_DATA_KEY, datoCatalogo.getSpanish()); modifPage.resetData(dato); modifPage.setmVisible(true);}
        	if(tieneValor(encuesta.getPatConsHueFrec())){  modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getPatConsHueFrec());datoCatalogo = sivinAdapter.getMessageResource(MainDBConstants.catKey + "='" + encuesta.getPatConsHueFrec() + "' and " + MainDBConstants.catRoot + "='CAT_FRECCON'", null);  dato = new Bundle(); if(datoCatalogo!=null) dato.putString(SIMPLE_DATA_KEY, datoCatalogo.getSpanish()); modifPage.resetData(dato); modifPage.setmVisible(true);}
        	if(tieneValor(encuesta.getPatConsPan())){  modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getPatConsPan());datoCatalogo = sivinAdapter.getMessageResource(MainDBConstants.catKey + "='" + encuesta.getPatConsPan() + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null);  dato = new Bundle(); if(datoCatalogo!=null) dato.putString(SIMPLE_DATA_KEY, datoCatalogo.getSpanish()); modifPage.resetData(dato); modifPage.setmVisible(true);}
        	if(tieneValor(encuesta.getPatConsPanFrec())){  modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getPatConsPanFrec());datoCatalogo = sivinAdapter.getMessageResource(MainDBConstants.catKey + "='" + encuesta.getPatConsPanFrec() + "' and " + MainDBConstants.catRoot + "='CAT_FRECCON'", null);  dato = new Bundle(); if(datoCatalogo!=null) dato.putString(SIMPLE_DATA_KEY, datoCatalogo.getSpanish()); modifPage.resetData(dato); modifPage.setmVisible(true);}
        	if(tieneValor(encuesta.getPatConsBan())){  modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getPatConsBan());  datoCatalogo = sivinAdapter.getMessageResource(MainDBConstants.catKey + "='" + encuesta.getPatConsBan() + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null);  dato = new Bundle(); if(datoCatalogo!=null) dato.putString(SIMPLE_DATA_KEY, datoCatalogo.getSpanish()); modifPage.resetData(dato); modifPage.setmVisible(true);}
        	if(tieneValor(encuesta.getPatConsBanFrec())){  modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getPatConsBanFrec());  datoCatalogo = sivinAdapter.getMessageResource(MainDBConstants.catKey + "='" + encuesta.getPatConsBanFrec() + "' and " + MainDBConstants.catRoot + "='CAT_FRECCON'", null);  dato = new Bundle(); if(datoCatalogo!=null) dato.putString(SIMPLE_DATA_KEY, datoCatalogo.getSpanish()); modifPage.resetData(dato); modifPage.setmVisible(true);}
        	if(tieneValor(encuesta.getPatConsPanDul())){  modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getPatConsPanDul());  datoCatalogo = sivinAdapter.getMessageResource(MainDBConstants.catKey + "='" + encuesta.getPatConsPanDul() + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null);  dato = new Bundle(); if(datoCatalogo!=null) dato.putString(SIMPLE_DATA_KEY, datoCatalogo.getSpanish()); modifPage.resetData(dato); modifPage.setmVisible(true);}
        	if(tieneValor(encuesta.getPatConsPanDulFrec())){  modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getPatConsPanDulFrec());  datoCatalogo = sivinAdapter.getMessageResource(MainDBConstants.catKey + "='" + encuesta.getPatConsPanDulFrec() + "' and " + MainDBConstants.catRoot + "='CAT_FRECCON'", null);  dato = new Bundle(); if(datoCatalogo!=null) dato.putString(SIMPLE_DATA_KEY, datoCatalogo.getSpanish()); modifPage.resetData(dato); modifPage.setmVisible(true);}
        	if(tieneValor(encuesta.getPatConsPla())){  modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getPatConsPla());  datoCatalogo = sivinAdapter.getMessageResource(MainDBConstants.catKey + "='" + encuesta.getPatConsPla() + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null);  dato = new Bundle(); if(datoCatalogo!=null) dato.putString(SIMPLE_DATA_KEY, datoCatalogo.getSpanish()); modifPage.resetData(dato); modifPage.setmVisible(true);}
        	if(tieneValor(encuesta.getPatConsPlaFrec())){  modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getPatConsPlaFrec());  datoCatalogo = sivinAdapter.getMessageResource(MainDBConstants.catKey + "='" + encuesta.getPatConsPlaFrec() + "' and " + MainDBConstants.catRoot + "='CAT_FRECCON'", null);  dato = new Bundle(); if(datoCatalogo!=null) dato.putString(SIMPLE_DATA_KEY, datoCatalogo.getSpanish()); modifPage.resetData(dato); modifPage.setmVisible(true);}
        	if(tieneValor(encuesta.getPatConsPapa())){  modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getPatConsPapa());  datoCatalogo = sivinAdapter.getMessageResource(MainDBConstants.catKey + "='" + encuesta.getPatConsPapa() + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null);  dato = new Bundle(); if(datoCatalogo!=null) dato.putString(SIMPLE_DATA_KEY, datoCatalogo.getSpanish()); modifPage.resetData(dato); modifPage.setmVisible(true);}
        	if(tieneValor(encuesta.getPatConsPapaFrec())){  modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getPatConsPapaFrec());  datoCatalogo = sivinAdapter.getMessageResource(MainDBConstants.catKey + "='" + encuesta.getPatConsPapaFrec() + "' and " + MainDBConstants.catRoot + "='CAT_FRECCON'", null);  dato = new Bundle(); if(datoCatalogo!=null) dato.putString(SIMPLE_DATA_KEY, datoCatalogo.getSpanish()); modifPage.resetData(dato); modifPage.setmVisible(true);}
        	if(tieneValor(encuesta.getPatConsLec())){  modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getPatConsLec());  datoCatalogo = sivinAdapter.getMessageResource(MainDBConstants.catKey + "='" + encuesta.getPatConsLec() + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null);  dato = new Bundle(); if(datoCatalogo!=null) dato.putString(SIMPLE_DATA_KEY, datoCatalogo.getSpanish()); modifPage.resetData(dato); modifPage.setmVisible(true);}
        	if(tieneValor(encuesta.getPatConsLecFrec())){  modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getPatConsLecFrec());  datoCatalogo = sivinAdapter.getMessageResource(MainDBConstants.catKey + "='" + encuesta.getPatConsLecFrec() + "' and " + MainDBConstants.catRoot + "='CAT_FRECCON'", null);  dato = new Bundle(); if(datoCatalogo!=null) dato.putString(SIMPLE_DATA_KEY, datoCatalogo.getSpanish()); modifPage.resetData(dato); modifPage.setmVisible(true);}
        	if(tieneValor(encuesta.getPatConsSalTom())){  modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getPatConsSalTom());  datoCatalogo = sivinAdapter.getMessageResource(MainDBConstants.catKey + "='" + encuesta.getPatConsSalTom() + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null);  dato = new Bundle(); if(datoCatalogo!=null) dato.putString(SIMPLE_DATA_KEY, datoCatalogo.getSpanish()); modifPage.resetData(dato); modifPage.setmVisible(true);}
        	if(tieneValor(encuesta.getPatConsSalTomFrec())){  modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getPatConsSalTomFrec());  datoCatalogo = sivinAdapter.getMessageResource(MainDBConstants.catKey + "='" + encuesta.getPatConsSalTomFrec() + "' and " + MainDBConstants.catRoot + "='CAT_FRECCON'", null);  dato = new Bundle(); if(datoCatalogo!=null) dato.putString(SIMPLE_DATA_KEY, datoCatalogo.getSpanish()); modifPage.resetData(dato); modifPage.setmVisible(true);}
        	if(tieneValor(encuesta.getPatConsGas())){  modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getPatConsGas());  datoCatalogo = sivinAdapter.getMessageResource(MainDBConstants.catKey + "='" + encuesta.getPatConsGas() + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null);  dato = new Bundle(); if(datoCatalogo!=null) dato.putString(SIMPLE_DATA_KEY, datoCatalogo.getSpanish()); modifPage.resetData(dato); modifPage.setmVisible(true);}
        	if(tieneValor(encuesta.getPatConsGasFrec())){  modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getPatConsGasFrec());  datoCatalogo = sivinAdapter.getMessageResource(MainDBConstants.catKey + "='" + encuesta.getPatConsGasFrec() + "' and " + MainDBConstants.catRoot + "='CAT_FRECCON'", null);  dato = new Bundle(); if(datoCatalogo!=null) dato.putString(SIMPLE_DATA_KEY, datoCatalogo.getSpanish()); modifPage.resetData(dato); modifPage.setmVisible(true);}
        	if(tieneValor(encuesta.getPatConsCarRes())){  modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getPatConsCarRes());  datoCatalogo = sivinAdapter.getMessageResource(MainDBConstants.catKey + "='" + encuesta.getPatConsCarRes() + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null);  dato = new Bundle(); if(datoCatalogo!=null) dato.putString(SIMPLE_DATA_KEY, datoCatalogo.getSpanish()); modifPage.resetData(dato); modifPage.setmVisible(true);}
        	if(tieneValor(encuesta.getPatConsCarResFrec())){  modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getPatConsCarResFrec());  datoCatalogo = sivinAdapter.getMessageResource(MainDBConstants.catKey + "='" + encuesta.getPatConsCarResFrec() + "' and " + MainDBConstants.catRoot + "='CAT_FRECCON'", null);  dato = new Bundle(); if(datoCatalogo!=null) dato.putString(SIMPLE_DATA_KEY, datoCatalogo.getSpanish()); modifPage.resetData(dato); modifPage.setmVisible(true);}
        	if(tieneValor(encuesta.getPatConsAvena())){  modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getPatConsAvena());  datoCatalogo = sivinAdapter.getMessageResource(MainDBConstants.catKey + "='" + encuesta.getPatConsAvena() + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null);  dato = new Bundle(); if(datoCatalogo!=null) dato.putString(SIMPLE_DATA_KEY, datoCatalogo.getSpanish()); modifPage.resetData(dato); modifPage.setmVisible(true);}
        	if(tieneValor(encuesta.getPatConsAvenaFrec())){  modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getPatConsAvenaFrec());  datoCatalogo = sivinAdapter.getMessageResource(MainDBConstants.catKey + "='" + encuesta.getPatConsAvenaFrec() + "' and " + MainDBConstants.catRoot + "='CAT_FRECCON'", null);  dato = new Bundle(); if(datoCatalogo!=null) dato.putString(SIMPLE_DATA_KEY, datoCatalogo.getSpanish()); modifPage.resetData(dato); modifPage.setmVisible(true);}
        	if(tieneValor(encuesta.getPatConsNaran())){  modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getPatConsNaran());  datoCatalogo = sivinAdapter.getMessageResource(MainDBConstants.catKey + "='" + encuesta.getPatConsNaran() + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null);  dato = new Bundle(); if(datoCatalogo!=null) dato.putString(SIMPLE_DATA_KEY, datoCatalogo.getSpanish()); modifPage.resetData(dato); modifPage.setmVisible(true);}
        	if(tieneValor(encuesta.getPatConsNaranFrec())){  modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getPatConsNaranFrec());  datoCatalogo = sivinAdapter.getMessageResource(MainDBConstants.catKey + "='" + encuesta.getPatConsNaranFrec() + "' and " + MainDBConstants.catRoot + "='CAT_FRECCON'", null);  dato = new Bundle(); if(datoCatalogo!=null) dato.putString(SIMPLE_DATA_KEY, datoCatalogo.getSpanish()); modifPage.resetData(dato); modifPage.setmVisible(true);}
        	if(tieneValor(encuesta.getPatConsPasta())){  modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getPatConsPasta());  datoCatalogo = sivinAdapter.getMessageResource(MainDBConstants.catKey + "='" + encuesta.getPatConsPasta() + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null);  dato = new Bundle(); if(datoCatalogo!=null) dato.putString(SIMPLE_DATA_KEY, datoCatalogo.getSpanish()); modifPage.resetData(dato); modifPage.setmVisible(true);}
        	if(tieneValor(encuesta.getPatConsPastaFrec())){  modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getPatConsPastaFrec());  datoCatalogo = sivinAdapter.getMessageResource(MainDBConstants.catKey + "='" + encuesta.getPatConsPastaFrec() + "' and " + MainDBConstants.catRoot + "='CAT_FRECCON'", null);  dato = new Bundle(); if(datoCatalogo!=null) dato.putString(SIMPLE_DATA_KEY, datoCatalogo.getSpanish()); modifPage.resetData(dato); modifPage.setmVisible(true);}
        	if(tieneValor(encuesta.getPatConsAyote())){  modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getPatConsAyote());  datoCatalogo = sivinAdapter.getMessageResource(MainDBConstants.catKey + "='" + encuesta.getPatConsAyote() + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null);  dato = new Bundle(); if(datoCatalogo!=null) dato.putString(SIMPLE_DATA_KEY, datoCatalogo.getSpanish()); modifPage.resetData(dato); modifPage.setmVisible(true);}
        	if(tieneValor(encuesta.getPatConsAyoteFrec())){  modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getPatConsAyoteFrec());  datoCatalogo = sivinAdapter.getMessageResource(MainDBConstants.catKey + "='" + encuesta.getPatConsAyoteFrec() + "' and " + MainDBConstants.catRoot + "='CAT_FRECCON'", null);  dato = new Bundle(); if(datoCatalogo!=null) dato.putString(SIMPLE_DATA_KEY, datoCatalogo.getSpanish()); modifPage.resetData(dato); modifPage.setmVisible(true);}
        	if(tieneValor(encuesta.getPatConsMagg())){  modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getPatConsMagg());  datoCatalogo = sivinAdapter.getMessageResource(MainDBConstants.catKey + "='" + encuesta.getPatConsMagg() + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null);  dato = new Bundle(); if(datoCatalogo!=null) dato.putString(SIMPLE_DATA_KEY, datoCatalogo.getSpanish()); modifPage.resetData(dato); modifPage.setmVisible(true);}
        	if(tieneValor(encuesta.getPatConsMaggFrec())){  modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getPatConsMaggFrec());  datoCatalogo = sivinAdapter.getMessageResource(MainDBConstants.catKey + "='" + encuesta.getPatConsMaggFrec() + "' and " + MainDBConstants.catRoot + "='CAT_FRECCON'", null);  dato = new Bundle(); if(datoCatalogo!=null) dato.putString(SIMPLE_DATA_KEY, datoCatalogo.getSpanish()); modifPage.resetData(dato); modifPage.setmVisible(true);}
        	if(tieneValor(encuesta.getPatConsFrut())){  modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getPatConsFrut());  datoCatalogo = sivinAdapter.getMessageResource(MainDBConstants.catKey + "='" + encuesta.getPatConsFrut() + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null);  dato = new Bundle(); if(datoCatalogo!=null) dato.putString(SIMPLE_DATA_KEY, datoCatalogo.getSpanish()); modifPage.resetData(dato); modifPage.setmVisible(true);}
        	if(tieneValor(encuesta.getPatConsFrutFrec())){  modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getPatConsFrutFrec());  datoCatalogo = sivinAdapter.getMessageResource(MainDBConstants.catKey + "='" + encuesta.getPatConsFrutFrec() + "' and " + MainDBConstants.catRoot + "='CAT_FRECCON'", null);  dato = new Bundle(); if(datoCatalogo!=null) dato.putString(SIMPLE_DATA_KEY, datoCatalogo.getSpanish()); modifPage.resetData(dato); modifPage.setmVisible(true);}
        	if(tieneValor(encuesta.getPatConsRaic())){  modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getPatConsRaic());  datoCatalogo = sivinAdapter.getMessageResource(MainDBConstants.catKey + "='" + encuesta.getPatConsRaic() + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null);  dato = new Bundle(); if(datoCatalogo!=null) dato.putString(SIMPLE_DATA_KEY, datoCatalogo.getSpanish()); modifPage.resetData(dato); modifPage.setmVisible(true);}
        	if(tieneValor(encuesta.getPatConsRaicFrec())){  modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getPatConsRaicFrec());  datoCatalogo = sivinAdapter.getMessageResource(MainDBConstants.catKey + "='" + encuesta.getPatConsRaicFrec() + "' and " + MainDBConstants.catRoot + "='CAT_FRECCON'", null);  dato = new Bundle(); if(datoCatalogo!=null) dato.putString(SIMPLE_DATA_KEY, datoCatalogo.getSpanish()); modifPage.resetData(dato); modifPage.setmVisible(true);}
        	if(tieneValor(encuesta.getPatConsMenei())){  modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getPatConsMenei());  datoCatalogo = sivinAdapter.getMessageResource(MainDBConstants.catKey + "='" + encuesta.getPatConsMenei() + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null);  dato = new Bundle(); if(datoCatalogo!=null) dato.putString(SIMPLE_DATA_KEY, datoCatalogo.getSpanish()); modifPage.resetData(dato); modifPage.setmVisible(true);}
        	if(tieneValor(encuesta.getPatConsMeneiFrec())){  modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getPatConsMeneiFrec());  datoCatalogo = sivinAdapter.getMessageResource(MainDBConstants.catKey + "='" + encuesta.getPatConsMeneiFrec() + "' and " + MainDBConstants.catRoot + "='CAT_FRECCON'", null);  dato = new Bundle(); if(datoCatalogo!=null) dato.putString(SIMPLE_DATA_KEY, datoCatalogo.getSpanish()); modifPage.resetData(dato); modifPage.setmVisible(true);}
        	if(tieneValor(encuesta.getPatConsRepollo())){  modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getPatConsRepollo());  datoCatalogo = sivinAdapter.getMessageResource(MainDBConstants.catKey + "='" + encuesta.getPatConsRepollo() + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null);  dato = new Bundle(); if(datoCatalogo!=null) dato.putString(SIMPLE_DATA_KEY, datoCatalogo.getSpanish()); modifPage.resetData(dato); modifPage.setmVisible(true);}
        	if(tieneValor(encuesta.getPatConsRepolloFrec())){  modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getPatConsRepolloFrec());  datoCatalogo = sivinAdapter.getMessageResource(MainDBConstants.catKey + "='" + encuesta.getPatConsRepolloFrec() + "' and " + MainDBConstants.catRoot + "='CAT_FRECCON'", null);  dato = new Bundle(); if(datoCatalogo!=null) dato.putString(SIMPLE_DATA_KEY, datoCatalogo.getSpanish()); modifPage.resetData(dato); modifPage.setmVisible(true);}
        	if(tieneValor(encuesta.getPatConsZana())){  modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getPatConsZana());  datoCatalogo = sivinAdapter.getMessageResource(MainDBConstants.catKey + "='" + encuesta.getPatConsZana() + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null);  dato = new Bundle(); if(datoCatalogo!=null) dato.putString(SIMPLE_DATA_KEY, datoCatalogo.getSpanish()); modifPage.resetData(dato); modifPage.setmVisible(true);}
        	if(tieneValor(encuesta.getPatConsZanaFrec())){  modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getPatConsZanaFrec());  datoCatalogo = sivinAdapter.getMessageResource(MainDBConstants.catKey + "='" + encuesta.getPatConsZanaFrec() + "' and " + MainDBConstants.catRoot + "='CAT_FRECCON'", null);  dato = new Bundle(); if(datoCatalogo!=null) dato.putString(SIMPLE_DATA_KEY, datoCatalogo.getSpanish()); modifPage.resetData(dato); modifPage.setmVisible(true);}
        	if(tieneValor(encuesta.getPatConsPinolillo())){  modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getPatConsPinolillo());  datoCatalogo = sivinAdapter.getMessageResource(MainDBConstants.catKey + "='" + encuesta.getPatConsPinolillo() + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null);  dato = new Bundle(); if(datoCatalogo!=null) dato.putString(SIMPLE_DATA_KEY, datoCatalogo.getSpanish()); modifPage.resetData(dato); modifPage.setmVisible(true);}
        	if(tieneValor(encuesta.getPatConsPinolilloFrec())){  modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getPatConsPinolilloFrec());  datoCatalogo = sivinAdapter.getMessageResource(MainDBConstants.catKey + "='" + encuesta.getPatConsPinolilloFrec() + "' and " + MainDBConstants.catRoot + "='CAT_FRECCON'", null);  dato = new Bundle(); if(datoCatalogo!=null) dato.putString(SIMPLE_DATA_KEY, datoCatalogo.getSpanish()); modifPage.resetData(dato); modifPage.setmVisible(true);}
        	if(tieneValor(encuesta.getPatConsOVerd())){  modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getPatConsOVerd());  datoCatalogo = sivinAdapter.getMessageResource(MainDBConstants.catKey + "='" + encuesta.getPatConsOVerd() + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null);  dato = new Bundle(); if(datoCatalogo!=null) dato.putString(SIMPLE_DATA_KEY, datoCatalogo.getSpanish()); modifPage.resetData(dato); modifPage.setmVisible(true);}
        	if(tieneValor(encuesta.getPatConsOVerdFrec())){  modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getPatConsOVerdFrec());  datoCatalogo = sivinAdapter.getMessageResource(MainDBConstants.catKey + "='" + encuesta.getPatConsOVerdFrec() + "' and " + MainDBConstants.catRoot + "='CAT_FRECCON'", null);  dato = new Bundle(); if(datoCatalogo!=null) dato.putString(SIMPLE_DATA_KEY, datoCatalogo.getSpanish()); modifPage.resetData(dato); modifPage.setmVisible(true);}
        	if(tieneValor(encuesta.getPatConsPesc())){  modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getPatConsPesc());  datoCatalogo = sivinAdapter.getMessageResource(MainDBConstants.catKey + "='" + encuesta.getPatConsPesc() + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null);  dato = new Bundle(); if(datoCatalogo!=null) dato.putString(SIMPLE_DATA_KEY, datoCatalogo.getSpanish()); modifPage.resetData(dato); modifPage.setmVisible(true);}
        	if(tieneValor(encuesta.getPatConsPescFrec())){  modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getPatConsPescFrec());  datoCatalogo = sivinAdapter.getMessageResource(MainDBConstants.catKey + "='" + encuesta.getPatConsPescFrec() + "' and " + MainDBConstants.catRoot + "='CAT_FRECCON'", null);  dato = new Bundle(); if(datoCatalogo!=null) dato.putString(SIMPLE_DATA_KEY, datoCatalogo.getSpanish()); modifPage.resetData(dato); modifPage.setmVisible(true);}
        	if(tieneValor(encuesta.getPatConsAlimComp())){  modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getPatConsAlimComp());  datoCatalogo = sivinAdapter.getMessageResource(MainDBConstants.catKey + "='" + encuesta.getPatConsAlimComp() + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null);  dato = new Bundle(); if(datoCatalogo!=null) dato.putString(SIMPLE_DATA_KEY, datoCatalogo.getSpanish()); modifPage.resetData(dato); modifPage.setmVisible(true);}
        	if(tieneValor(encuesta.getPatConsAlimCompFrec())){  modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getPatConsAlimCompFrec());  datoCatalogo = sivinAdapter.getMessageResource(MainDBConstants.catKey + "='" + encuesta.getPatConsAlimCompFrec() + "' and " + MainDBConstants.catRoot + "='CAT_FRECCON'", null);  dato = new Bundle(); if(datoCatalogo!=null) dato.putString(SIMPLE_DATA_KEY, datoCatalogo.getSpanish()); modifPage.resetData(dato); modifPage.setmVisible(true);}
        	if(tieneValor(encuesta.getPatConsLecPol())){  modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getPatConsLecPol());  datoCatalogo = sivinAdapter.getMessageResource(MainDBConstants.catKey + "='" + encuesta.getPatConsLecPol() + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null);  dato = new Bundle(); if(datoCatalogo!=null) dato.putString(SIMPLE_DATA_KEY, datoCatalogo.getSpanish()); modifPage.resetData(dato); modifPage.setmVisible(true);}
        	if(tieneValor(encuesta.getPatConsLecPolFrec())){  modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getPatConsLecPolFrec());  datoCatalogo = sivinAdapter.getMessageResource(MainDBConstants.catKey + "='" + encuesta.getPatConsLecPolFrec() + "' and " + MainDBConstants.catRoot + "='CAT_FRECCON'", null);  dato = new Bundle(); if(datoCatalogo!=null) dato.putString(SIMPLE_DATA_KEY, datoCatalogo.getSpanish()); modifPage.resetData(dato); modifPage.setmVisible(true);}
        	if(tieneValor(encuesta.getPatConsCarCer())){  modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getPatConsCarCer());  datoCatalogo = sivinAdapter.getMessageResource(MainDBConstants.catKey + "='" + encuesta.getPatConsCarCer() + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null);  dato = new Bundle(); if(datoCatalogo!=null) dato.putString(SIMPLE_DATA_KEY, datoCatalogo.getSpanish()); modifPage.resetData(dato); modifPage.setmVisible(true);}
        	if(tieneValor(encuesta.getPatConsCarCerFrec())){  modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getPatConsCarCerFrec());  datoCatalogo = sivinAdapter.getMessageResource(MainDBConstants.catKey + "='" + encuesta.getPatConsCarCerFrec() + "' and " + MainDBConstants.catRoot + "='CAT_FRECCON'", null);  dato = new Bundle(); if(datoCatalogo!=null) dato.putString(SIMPLE_DATA_KEY, datoCatalogo.getSpanish()); modifPage.resetData(dato); modifPage.setmVisible(true);}
        	if(tieneValor(encuesta.getPatConsEmb())){  modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getPatConsEmb());  datoCatalogo = sivinAdapter.getMessageResource(MainDBConstants.catKey + "='" + encuesta.getPatConsEmb() + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null);  dato = new Bundle(); if(datoCatalogo!=null) dato.putString(SIMPLE_DATA_KEY, datoCatalogo.getSpanish()); modifPage.resetData(dato); modifPage.setmVisible(true);}
        	if(tieneValor(encuesta.getPatConsEmbFrec())){  modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getPatConsEmbFrec());  datoCatalogo = sivinAdapter.getMessageResource(MainDBConstants.catKey + "='" + encuesta.getPatConsEmbFrec() + "' and " + MainDBConstants.catRoot + "='CAT_FRECCON'", null);  dato = new Bundle(); if(datoCatalogo!=null) dato.putString(SIMPLE_DATA_KEY, datoCatalogo.getSpanish()); modifPage.resetData(dato); modifPage.setmVisible(true);}
        	if(tieneValor(encuesta.getPatConsMar())){  modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getPatConsMar());  datoCatalogo = sivinAdapter.getMessageResource(MainDBConstants.catKey + "='" + encuesta.getPatConsMar() + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null);  dato = new Bundle(); if(datoCatalogo!=null) dato.putString(SIMPLE_DATA_KEY, datoCatalogo.getSpanish()); modifPage.resetData(dato); modifPage.setmVisible(true);}
        	if(tieneValor(encuesta.getPatConsMarFrec())){  modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getPatConsMarFrec());  datoCatalogo = sivinAdapter.getMessageResource(MainDBConstants.catKey + "='" + encuesta.getPatConsMarFrec() + "' and " + MainDBConstants.catRoot + "='CAT_FRECCON'", null);  dato = new Bundle(); if(datoCatalogo!=null) dato.putString(SIMPLE_DATA_KEY, datoCatalogo.getSpanish()); modifPage.resetData(dato); modifPage.setmVisible(true);}
        	if(tieneValor(encuesta.getPatConsCarCaza())){  modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getPatConsCarCaza());  datoCatalogo = sivinAdapter.getMessageResource(MainDBConstants.catKey + "='" + encuesta.getPatConsCarCaza() + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null);  dato = new Bundle(); if(datoCatalogo!=null) dato.putString(SIMPLE_DATA_KEY, datoCatalogo.getSpanish()); modifPage.resetData(dato); modifPage.setmVisible(true);}
        	if(tieneValor(encuesta.getPatConsCarCazaFrec())){  modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getPatConsCarCazaFrec());  datoCatalogo = sivinAdapter.getMessageResource(MainDBConstants.catKey + "='" + encuesta.getPatConsCarCazaFrec() + "' and " + MainDBConstants.catRoot + "='CAT_FRECCON'", null);  dato = new Bundle(); if(datoCatalogo!=null) dato.putString(SIMPLE_DATA_KEY, datoCatalogo.getSpanish()); modifPage.resetData(dato); modifPage.setmVisible(true);}
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
        	if (page.getTitle().equals(labels.getPatConsAzuc())) { catalogo = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + page.getData().getString(TextPage.SIMPLE_DATA_KEY) + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null); visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && catalogo.getCatKey().matches(Constants.SI); changeStatus(mWizardModel.findByKey(labels.getPatConsAzucFrec()),visible);}
        	if (page.getTitle().equals(labels.getPatConsSal())) { catalogo = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + page.getData().getString(TextPage.SIMPLE_DATA_KEY) + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null); visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && catalogo.getCatKey().matches(Constants.SI); changeStatus(mWizardModel.findByKey(labels.getPatConsSalFrec()),visible);}
        	if (page.getTitle().equals(labels.getPatConsArroz())) { catalogo = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + page.getData().getString(TextPage.SIMPLE_DATA_KEY) + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null); visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && catalogo.getCatKey().matches(Constants.SI); changeStatus(mWizardModel.findByKey(labels.getPatConsArrozFrec()),visible);}
        	if (page.getTitle().equals(labels.getPatConsAcei())) { catalogo = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + page.getData().getString(TextPage.SIMPLE_DATA_KEY) + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null); visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && catalogo.getCatKey().matches(Constants.SI); changeStatus(mWizardModel.findByKey(labels.getPatConsAceiFrec()),visible);}
        	if (page.getTitle().equals(labels.getPatConsFri())) { catalogo = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + page.getData().getString(TextPage.SIMPLE_DATA_KEY) + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null); visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && catalogo.getCatKey().matches(Constants.SI); changeStatus(mWizardModel.findByKey(labels.getPatConsFriFrec()),visible);}
        	if (page.getTitle().equals(labels.getPatConsCebo())) { catalogo = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + page.getData().getString(TextPage.SIMPLE_DATA_KEY) + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null); visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && catalogo.getCatKey().matches(Constants.SI); changeStatus(mWizardModel.findByKey(labels.getPatConsCeboFrec()),visible);}
        	if (page.getTitle().equals(labels.getPatConsChi())) { catalogo = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + page.getData().getString(TextPage.SIMPLE_DATA_KEY) + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null); visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && catalogo.getCatKey().matches(Constants.SI); changeStatus(mWizardModel.findByKey(labels.getPatConsChiFrec()),visible);}
        	if (page.getTitle().equals(labels.getPatConsQue())) { catalogo = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + page.getData().getString(TextPage.SIMPLE_DATA_KEY) + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null); visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && catalogo.getCatKey().matches(Constants.SI); changeStatus(mWizardModel.findByKey(labels.getPatConsQueFrec()),visible);}
        	if (page.getTitle().equals(labels.getPatConsCafe())) { catalogo = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + page.getData().getString(TextPage.SIMPLE_DATA_KEY) + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null); visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && catalogo.getCatKey().matches(Constants.SI); changeStatus(mWizardModel.findByKey(labels.getPatConsCafeFrec()),visible);}
        	if (page.getTitle().equals(labels.getPatConsTor())) { catalogo = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + page.getData().getString(TextPage.SIMPLE_DATA_KEY) + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null); visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && catalogo.getCatKey().matches(Constants.SI); changeStatus(mWizardModel.findByKey(labels.getPatConsTorFrec()),visible);}
        	if (page.getTitle().equals(labels.getPatConsCar())) { catalogo = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + page.getData().getString(TextPage.SIMPLE_DATA_KEY) + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null); visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && catalogo.getCatKey().matches(Constants.SI); changeStatus(mWizardModel.findByKey(labels.getPatConsCarFrec()),visible);}
        	if (page.getTitle().equals(labels.getPatConsHue())) { catalogo = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + page.getData().getString(TextPage.SIMPLE_DATA_KEY) + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null); visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && catalogo.getCatKey().matches(Constants.SI); changeStatus(mWizardModel.findByKey(labels.getPatConsHueFrec()),visible);}
        	if (page.getTitle().equals(labels.getPatConsPan())) { catalogo = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + page.getData().getString(TextPage.SIMPLE_DATA_KEY) + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null); visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && catalogo.getCatKey().matches(Constants.SI); changeStatus(mWizardModel.findByKey(labels.getPatConsPanFrec()),visible);}
        	if (page.getTitle().equals(labels.getPatConsBan())) { catalogo = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + page.getData().getString(TextPage.SIMPLE_DATA_KEY) + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null); visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && catalogo.getCatKey().matches(Constants.SI); changeStatus(mWizardModel.findByKey(labels.getPatConsBanFrec()),visible);}
        	if (page.getTitle().equals(labels.getPatConsPanDul())) { catalogo = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + page.getData().getString(TextPage.SIMPLE_DATA_KEY) + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null); visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && catalogo.getCatKey().matches(Constants.SI); changeStatus(mWizardModel.findByKey(labels.getPatConsPanDulFrec()),visible);}
        	if (page.getTitle().equals(labels.getPatConsPla())) { catalogo = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + page.getData().getString(TextPage.SIMPLE_DATA_KEY) + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null); visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && catalogo.getCatKey().matches(Constants.SI); changeStatus(mWizardModel.findByKey(labels.getPatConsPlaFrec()),visible);}
        	if (page.getTitle().equals(labels.getPatConsPapa())) { catalogo = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + page.getData().getString(TextPage.SIMPLE_DATA_KEY) + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null); visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && catalogo.getCatKey().matches(Constants.SI); changeStatus(mWizardModel.findByKey(labels.getPatConsPapaFrec()),visible);}
        	if (page.getTitle().equals(labels.getPatConsLec())) { catalogo = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + page.getData().getString(TextPage.SIMPLE_DATA_KEY) + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null); visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && catalogo.getCatKey().matches(Constants.SI); changeStatus(mWizardModel.findByKey(labels.getPatConsLecFrec()),visible);}
        	if (page.getTitle().equals(labels.getPatConsSalTom())) { catalogo = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + page.getData().getString(TextPage.SIMPLE_DATA_KEY) + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null); visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && catalogo.getCatKey().matches(Constants.SI); changeStatus(mWizardModel.findByKey(labels.getPatConsSalTomFrec()),visible);}
        	if (page.getTitle().equals(labels.getPatConsGas())) { catalogo = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + page.getData().getString(TextPage.SIMPLE_DATA_KEY) + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null); visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && catalogo.getCatKey().matches(Constants.SI); changeStatus(mWizardModel.findByKey(labels.getPatConsGasFrec()),visible);}
        	if (page.getTitle().equals(labels.getPatConsCarRes())) { catalogo = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + page.getData().getString(TextPage.SIMPLE_DATA_KEY) + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null); visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && catalogo.getCatKey().matches(Constants.SI); changeStatus(mWizardModel.findByKey(labels.getPatConsCarResFrec()),visible);}
        	if (page.getTitle().equals(labels.getPatConsAvena())) { catalogo = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + page.getData().getString(TextPage.SIMPLE_DATA_KEY) + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null); visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && catalogo.getCatKey().matches(Constants.SI); changeStatus(mWizardModel.findByKey(labels.getPatConsAvenaFrec()),visible);}
        	if (page.getTitle().equals(labels.getPatConsNaran())) { catalogo = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + page.getData().getString(TextPage.SIMPLE_DATA_KEY) + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null); visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && catalogo.getCatKey().matches(Constants.SI); changeStatus(mWizardModel.findByKey(labels.getPatConsNaranFrec()),visible);}
        	if (page.getTitle().equals(labels.getPatConsPasta())) { catalogo = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + page.getData().getString(TextPage.SIMPLE_DATA_KEY) + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null); visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && catalogo.getCatKey().matches(Constants.SI); changeStatus(mWizardModel.findByKey(labels.getPatConsPastaFrec()),visible);}
        	if (page.getTitle().equals(labels.getPatConsAyote())) { catalogo = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + page.getData().getString(TextPage.SIMPLE_DATA_KEY) + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null); visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && catalogo.getCatKey().matches(Constants.SI); changeStatus(mWizardModel.findByKey(labels.getPatConsAyoteFrec()),visible);}
        	if (page.getTitle().equals(labels.getPatConsMagg())) { catalogo = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + page.getData().getString(TextPage.SIMPLE_DATA_KEY) + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null); visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && catalogo.getCatKey().matches(Constants.SI); changeStatus(mWizardModel.findByKey(labels.getPatConsMaggFrec()),visible);}
        	if (page.getTitle().equals(labels.getPatConsFrut())) { catalogo = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + page.getData().getString(TextPage.SIMPLE_DATA_KEY) + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null); visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && catalogo.getCatKey().matches(Constants.SI); changeStatus(mWizardModel.findByKey(labels.getPatConsFrutFrec()),visible);}
        	if (page.getTitle().equals(labels.getPatConsRaic())) { catalogo = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + page.getData().getString(TextPage.SIMPLE_DATA_KEY) + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null); visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && catalogo.getCatKey().matches(Constants.SI); changeStatus(mWizardModel.findByKey(labels.getPatConsRaicFrec()),visible);}
        	if (page.getTitle().equals(labels.getPatConsMenei())) { catalogo = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + page.getData().getString(TextPage.SIMPLE_DATA_KEY) + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null); visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && catalogo.getCatKey().matches(Constants.SI); changeStatus(mWizardModel.findByKey(labels.getPatConsMeneiFrec()),visible);}
        	if (page.getTitle().equals(labels.getPatConsRepollo())) { catalogo = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + page.getData().getString(TextPage.SIMPLE_DATA_KEY) + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null); visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && catalogo.getCatKey().matches(Constants.SI); changeStatus(mWizardModel.findByKey(labels.getPatConsRepolloFrec()),visible);}
        	if (page.getTitle().equals(labels.getPatConsZana())) { catalogo = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + page.getData().getString(TextPage.SIMPLE_DATA_KEY) + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null); visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && catalogo.getCatKey().matches(Constants.SI); changeStatus(mWizardModel.findByKey(labels.getPatConsZanaFrec()),visible);}
        	if (page.getTitle().equals(labels.getPatConsPinolillo())) { catalogo = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + page.getData().getString(TextPage.SIMPLE_DATA_KEY) + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null); visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && catalogo.getCatKey().matches(Constants.SI); changeStatus(mWizardModel.findByKey(labels.getPatConsPinolilloFrec()),visible);}
        	if (page.getTitle().equals(labels.getPatConsOVerd())) { catalogo = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + page.getData().getString(TextPage.SIMPLE_DATA_KEY) + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null); visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && catalogo.getCatKey().matches(Constants.SI); changeStatus(mWizardModel.findByKey(labels.getPatConsOVerdFrec()),visible);}
        	if (page.getTitle().equals(labels.getPatConsPesc())) { catalogo = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + page.getData().getString(TextPage.SIMPLE_DATA_KEY) + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null); visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && catalogo.getCatKey().matches(Constants.SI); changeStatus(mWizardModel.findByKey(labels.getPatConsPescFrec()),visible);}
        	if (page.getTitle().equals(labels.getPatConsAlimComp())) { catalogo = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + page.getData().getString(TextPage.SIMPLE_DATA_KEY) + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null); visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && catalogo.getCatKey().matches(Constants.SI); changeStatus(mWizardModel.findByKey(labels.getPatConsAlimCompFrec()),visible);}
        	if (page.getTitle().equals(labels.getPatConsLecPol())) { catalogo = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + page.getData().getString(TextPage.SIMPLE_DATA_KEY) + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null); visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && catalogo.getCatKey().matches(Constants.SI); changeStatus(mWizardModel.findByKey(labels.getPatConsLecPolFrec()),visible);}
        	if (page.getTitle().equals(labels.getPatConsCarCer())) { catalogo = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + page.getData().getString(TextPage.SIMPLE_DATA_KEY) + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null); visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && catalogo.getCatKey().matches(Constants.SI); changeStatus(mWizardModel.findByKey(labels.getPatConsCarCerFrec()),visible);}
        	if (page.getTitle().equals(labels.getPatConsEmb())) { catalogo = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + page.getData().getString(TextPage.SIMPLE_DATA_KEY) + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null); visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && catalogo.getCatKey().matches(Constants.SI); changeStatus(mWizardModel.findByKey(labels.getPatConsEmbFrec()),visible);}
        	if (page.getTitle().equals(labels.getPatConsMar())) { catalogo = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + page.getData().getString(TextPage.SIMPLE_DATA_KEY) + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null); visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && catalogo.getCatKey().matches(Constants.SI); changeStatus(mWizardModel.findByKey(labels.getPatConsMarFrec()),visible);}
        	if (page.getTitle().equals(labels.getPatConsCarCaza())) { catalogo = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + page.getData().getString(TextPage.SIMPLE_DATA_KEY) + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null); visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && catalogo.getCatKey().matches(Constants.SI); changeStatus(mWizardModel.findByKey(labels.getPatConsCarCazaFrec()),visible);}
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
            String patConsAzuc = datos.getString(this.getString(R.string.patConsAzuc));
            String patConsAzucFrec = datos.getString(this.getString(R.string.patConsAzucFrec));
            String patConsSal = datos.getString(this.getString(R.string.patConsSal));
            String patConsSalFrec = datos.getString(this.getString(R.string.patConsSalFrec));
            String patConsArroz = datos.getString(this.getString(R.string.patConsArroz));
            String patConsArrozFrec = datos.getString(this.getString(R.string.patConsArrozFrec));
            String patConsAcei = datos.getString(this.getString(R.string.patConsAcei));
            String patConsAceiFrec = datos.getString(this.getString(R.string.patConsAceiFrec));
            String patConsFri = datos.getString(this.getString(R.string.patConsFri));
            String patConsFriFrec = datos.getString(this.getString(R.string.patConsFriFrec));
            String patConsCebo = datos.getString(this.getString(R.string.patConsCebo));
            String patConsCeboFrec = datos.getString(this.getString(R.string.patConsCeboFrec));
            String patConsChi = datos.getString(this.getString(R.string.patConsChi));
            String patConsChiFrec = datos.getString(this.getString(R.string.patConsChiFrec));
            String patConsQue = datos.getString(this.getString(R.string.patConsQue));
            String patConsQueFrec = datos.getString(this.getString(R.string.patConsQueFrec));
            String patConsCafe = datos.getString(this.getString(R.string.patConsCafe));
            String patConsCafeFrec = datos.getString(this.getString(R.string.patConsCafeFrec));
            String patConsTor = datos.getString(this.getString(R.string.patConsTor));
            String patConsTorFrec = datos.getString(this.getString(R.string.patConsTorFrec));
            String patConsCar = datos.getString(this.getString(R.string.patConsCar));
            String patConsCarFrec = datos.getString(this.getString(R.string.patConsCarFrec));
            String patConsHue = datos.getString(this.getString(R.string.patConsHue));
            String patConsHueFrec = datos.getString(this.getString(R.string.patConsHueFrec));
            String patConsPan = datos.getString(this.getString(R.string.patConsPan));
            String patConsPanFrec = datos.getString(this.getString(R.string.patConsPanFrec));
            String patConsBan = datos.getString(this.getString(R.string.patConsBan));
            String patConsBanFrec = datos.getString(this.getString(R.string.patConsBanFrec));
            String patConsPanDul = datos.getString(this.getString(R.string.patConsPanDul));
            String patConsPanDulFrec = datos.getString(this.getString(R.string.patConsPanDulFrec));
            String patConsPla = datos.getString(this.getString(R.string.patConsPla));
            String patConsPlaFrec = datos.getString(this.getString(R.string.patConsPlaFrec));
            String patConsPapa = datos.getString(this.getString(R.string.patConsPapa));
            String patConsPapaFrec = datos.getString(this.getString(R.string.patConsPapaFrec));
            String patConsLec = datos.getString(this.getString(R.string.patConsLec));
            String patConsLecFrec = datos.getString(this.getString(R.string.patConsLecFrec));
            String patConsSalTom = datos.getString(this.getString(R.string.patConsSalTom));
            String patConsSalTomFrec = datos.getString(this.getString(R.string.patConsSalTomFrec));
            String patConsGas = datos.getString(this.getString(R.string.patConsGas));
            String patConsGasFrec = datos.getString(this.getString(R.string.patConsGasFrec));
            String patConsCarRes = datos.getString(this.getString(R.string.patConsCarRes));
            String patConsCarResFrec = datos.getString(this.getString(R.string.patConsCarResFrec));
            String patConsAvena = datos.getString(this.getString(R.string.patConsAvena));
            String patConsAvenaFrec = datos.getString(this.getString(R.string.patConsAvenaFrec));
            String patConsNaran = datos.getString(this.getString(R.string.patConsNaran));
            String patConsNaranFrec = datos.getString(this.getString(R.string.patConsNaranFrec));
            String patConsPasta = datos.getString(this.getString(R.string.patConsPasta));
            String patConsPastaFrec = datos.getString(this.getString(R.string.patConsPastaFrec));
            String patConsAyote = datos.getString(this.getString(R.string.patConsAyote));
            String patConsAyoteFrec = datos.getString(this.getString(R.string.patConsAyoteFrec));
            String patConsMagg = datos.getString(this.getString(R.string.patConsMagg));
            String patConsMaggFrec = datos.getString(this.getString(R.string.patConsMaggFrec));
            String patConsFrut = datos.getString(this.getString(R.string.patConsFrut));
            String patConsFrutFrec = datos.getString(this.getString(R.string.patConsFrutFrec));
            String patConsRaic = datos.getString(this.getString(R.string.patConsRaic));
            String patConsRaicFrec = datos.getString(this.getString(R.string.patConsRaicFrec));
            String patConsMenei = datos.getString(this.getString(R.string.patConsMenei));
            String patConsMeneiFrec = datos.getString(this.getString(R.string.patConsMeneiFrec));
            String patConsRepollo = datos.getString(this.getString(R.string.patConsRepollo));
            String patConsRepolloFrec = datos.getString(this.getString(R.string.patConsRepolloFrec));
            String patConsZana = datos.getString(this.getString(R.string.patConsZana));
            String patConsZanaFrec = datos.getString(this.getString(R.string.patConsZanaFrec));
            String patConsPinolillo = datos.getString(this.getString(R.string.patConsPinolillo));
            String patConsPinolilloFrec = datos.getString(this.getString(R.string.patConsPinolilloFrec));
            String patConsOVerd = datos.getString(this.getString(R.string.patConsOVerd));
            String patConsOVerdFrec = datos.getString(this.getString(R.string.patConsOVerdFrec));
            String patConsPesc = datos.getString(this.getString(R.string.patConsPesc));
            String patConsPescFrec = datos.getString(this.getString(R.string.patConsPescFrec));
            String patConsAlimComp = datos.getString(this.getString(R.string.patConsAlimComp));
            String patConsAlimCompFrec = datos.getString(this.getString(R.string.patConsAlimCompFrec));
            String patConsLecPol = datos.getString(this.getString(R.string.patConsLecPol));
            String patConsLecPolFrec = datos.getString(this.getString(R.string.patConsLecPolFrec));
            String patConsCarCer = datos.getString(this.getString(R.string.patConsCarCer));
            String patConsCarCerFrec = datos.getString(this.getString(R.string.patConsCarCerFrec));
            String patConsEmb = datos.getString(this.getString(R.string.patConsEmb));
            String patConsEmbFrec = datos.getString(this.getString(R.string.patConsEmbFrec));
            String patConsMar = datos.getString(this.getString(R.string.patConsMar));
            String patConsMarFrec = datos.getString(this.getString(R.string.patConsMarFrec));
            String patConsCarCaza = datos.getString(this.getString(R.string.patConsCarCaza));
            String patConsCarCazaFrec = datos.getString(this.getString(R.string.patConsCarCazaFrec));

    		
    		//Para recuperar catalogos
    		MessageResource catRespuesta;
            
    		if (tieneValor(patConsAzuc)) { catRespuesta = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + patConsAzuc + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null); if (catRespuesta!=null) encuesta.setPatConsAzuc(catRespuesta.getCatKey()); } else { encuesta.setPatConsAzuc(null);}
    		if (tieneValor(patConsAzucFrec)) { catRespuesta = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + patConsAzucFrec + "' and " + MainDBConstants.catRoot + "='CAT_FRECCON'", null); if (catRespuesta!=null) encuesta.setPatConsAzucFrec(catRespuesta.getCatKey()); } else { encuesta.setPatConsAzucFrec(null);}
    		if (tieneValor(patConsSal)) { catRespuesta = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + patConsSal + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null); if (catRespuesta!=null) encuesta.setPatConsSal(catRespuesta.getCatKey()); } else { encuesta.setPatConsSal(null);}
    		if (tieneValor(patConsSalFrec)) { catRespuesta = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + patConsSalFrec + "' and " + MainDBConstants.catRoot + "='CAT_FRECCON'", null); if (catRespuesta!=null) encuesta.setPatConsSalFrec(catRespuesta.getCatKey()); } else { encuesta.setPatConsSalFrec(null);}
    		if (tieneValor(patConsArroz)) { catRespuesta = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + patConsArroz + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null); if (catRespuesta!=null) encuesta.setPatConsArroz(catRespuesta.getCatKey()); } else { encuesta.setPatConsArroz(null);}
    		if (tieneValor(patConsArrozFrec)) { catRespuesta = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + patConsArrozFrec + "' and " + MainDBConstants.catRoot + "='CAT_FRECCON'", null); if (catRespuesta!=null) encuesta.setPatConsArrozFrec(catRespuesta.getCatKey()); } else { encuesta.setPatConsArrozFrec(null);}
    		if (tieneValor(patConsAcei)) { catRespuesta = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + patConsAcei + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null); if (catRespuesta!=null) encuesta.setPatConsAcei(catRespuesta.getCatKey()); } else { encuesta.setPatConsAcei(null);}
    		if (tieneValor(patConsAceiFrec)) { catRespuesta = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + patConsAceiFrec + "' and " + MainDBConstants.catRoot + "='CAT_FRECCON'", null); if (catRespuesta!=null) encuesta.setPatConsAceiFrec(catRespuesta.getCatKey()); } else { encuesta.setPatConsAceiFrec(null);}
    		if (tieneValor(patConsFri)) { catRespuesta = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + patConsFri + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null); if (catRespuesta!=null) encuesta.setPatConsFri(catRespuesta.getCatKey()); } else { encuesta.setPatConsFri(null);}
    		if (tieneValor(patConsFriFrec)) { catRespuesta = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + patConsFriFrec + "' and " + MainDBConstants.catRoot + "='CAT_FRECCON'", null); if (catRespuesta!=null) encuesta.setPatConsFriFrec(catRespuesta.getCatKey()); } else { encuesta.setPatConsFriFrec(null);}
    		if (tieneValor(patConsCebo)) { catRespuesta = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + patConsCebo + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null); if (catRespuesta!=null) encuesta.setPatConsCebo(catRespuesta.getCatKey()); } else { encuesta.setPatConsCebo(null);}
    		if (tieneValor(patConsCeboFrec)) { catRespuesta = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + patConsCeboFrec + "' and " + MainDBConstants.catRoot + "='CAT_FRECCON'", null); if (catRespuesta!=null) encuesta.setPatConsCeboFrec(catRespuesta.getCatKey()); } else { encuesta.setPatConsCeboFrec(null);}
    		if (tieneValor(patConsChi)) { catRespuesta = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + patConsChi + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null); if (catRespuesta!=null) encuesta.setPatConsChi(catRespuesta.getCatKey()); } else { encuesta.setPatConsChi(null);}
    		if (tieneValor(patConsChiFrec)) { catRespuesta = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + patConsChiFrec + "' and " + MainDBConstants.catRoot + "='CAT_FRECCON'", null); if (catRespuesta!=null) encuesta.setPatConsChiFrec(catRespuesta.getCatKey()); } else { encuesta.setPatConsChiFrec(null);}
    		if (tieneValor(patConsQue)) { catRespuesta = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + patConsQue + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null); if (catRespuesta!=null) encuesta.setPatConsQue(catRespuesta.getCatKey()); } else { encuesta.setPatConsQue(null);}
    		if (tieneValor(patConsQueFrec)) { catRespuesta = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + patConsQueFrec + "' and " + MainDBConstants.catRoot + "='CAT_FRECCON'", null); if (catRespuesta!=null) encuesta.setPatConsQueFrec(catRespuesta.getCatKey()); } else { encuesta.setPatConsQueFrec(null);}
    		if (tieneValor(patConsCafe)) { catRespuesta = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + patConsCafe + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null); if (catRespuesta!=null) encuesta.setPatConsCafe(catRespuesta.getCatKey()); } else { encuesta.setPatConsCafe(null);}
    		if (tieneValor(patConsCafeFrec)) { catRespuesta = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + patConsCafeFrec + "' and " + MainDBConstants.catRoot + "='CAT_FRECCON'", null); if (catRespuesta!=null) encuesta.setPatConsCafeFrec(catRespuesta.getCatKey()); } else { encuesta.setPatConsCafeFrec(null);}
    		if (tieneValor(patConsTor)) { catRespuesta = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + patConsTor + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null); if (catRespuesta!=null) encuesta.setPatConsTor(catRespuesta.getCatKey()); } else { encuesta.setPatConsTor(null);}
    		if (tieneValor(patConsTorFrec)) { catRespuesta = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + patConsTorFrec + "' and " + MainDBConstants.catRoot + "='CAT_FRECCON'", null); if (catRespuesta!=null) encuesta.setPatConsTorFrec(catRespuesta.getCatKey()); } else { encuesta.setPatConsTorFrec(null);}
    		if (tieneValor(patConsCar)) { catRespuesta = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + patConsCar + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null); if (catRespuesta!=null) encuesta.setPatConsCar(catRespuesta.getCatKey()); } else { encuesta.setPatConsCar(null);}
    		if (tieneValor(patConsCarFrec)) { catRespuesta = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + patConsCarFrec + "' and " + MainDBConstants.catRoot + "='CAT_FRECCON'", null); if (catRespuesta!=null) encuesta.setPatConsCarFrec(catRespuesta.getCatKey()); } else { encuesta.setPatConsCarFrec(null);}
    		if (tieneValor(patConsHue)) { catRespuesta = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + patConsHue + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null); if (catRespuesta!=null) encuesta.setPatConsHue(catRespuesta.getCatKey()); } else { encuesta.setPatConsHue(null);}
    		if (tieneValor(patConsHueFrec)) { catRespuesta = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + patConsHueFrec + "' and " + MainDBConstants.catRoot + "='CAT_FRECCON'", null); if (catRespuesta!=null) encuesta.setPatConsHueFrec(catRespuesta.getCatKey()); } else { encuesta.setPatConsHueFrec(null);}
    		if (tieneValor(patConsPan)) { catRespuesta = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + patConsPan + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null); if (catRespuesta!=null) encuesta.setPatConsPan(catRespuesta.getCatKey()); } else { encuesta.setPatConsPan(null);}
    		if (tieneValor(patConsPanFrec)) { catRespuesta = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + patConsPanFrec + "' and " + MainDBConstants.catRoot + "='CAT_FRECCON'", null); if (catRespuesta!=null) encuesta.setPatConsPanFrec(catRespuesta.getCatKey()); } else { encuesta.setPatConsPanFrec(null);}
    		if (tieneValor(patConsBan)) { catRespuesta = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + patConsBan + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null); if (catRespuesta!=null) encuesta.setPatConsBan(catRespuesta.getCatKey()); } else { encuesta.setPatConsBan(null);}
    		if (tieneValor(patConsBanFrec)) { catRespuesta = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + patConsBanFrec + "' and " + MainDBConstants.catRoot + "='CAT_FRECCON'", null); if (catRespuesta!=null) encuesta.setPatConsBanFrec(catRespuesta.getCatKey()); } else { encuesta.setPatConsBanFrec(null);}
    		if (tieneValor(patConsPanDul)) { catRespuesta = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + patConsPanDul + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null); if (catRespuesta!=null) encuesta.setPatConsPanDul(catRespuesta.getCatKey()); } else { encuesta.setPatConsPanDul(null);}
    		if (tieneValor(patConsPanDulFrec)) { catRespuesta = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + patConsPanDulFrec + "' and " + MainDBConstants.catRoot + "='CAT_FRECCON'", null); if (catRespuesta!=null) encuesta.setPatConsPanDulFrec(catRespuesta.getCatKey()); } else { encuesta.setPatConsPanDulFrec(null);}
    		if (tieneValor(patConsPla)) { catRespuesta = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + patConsPla + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null); if (catRespuesta!=null) encuesta.setPatConsPla(catRespuesta.getCatKey()); } else { encuesta.setPatConsPla(null);}
    		if (tieneValor(patConsPlaFrec)) { catRespuesta = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + patConsPlaFrec + "' and " + MainDBConstants.catRoot + "='CAT_FRECCON'", null); if (catRespuesta!=null) encuesta.setPatConsPlaFrec(catRespuesta.getCatKey()); } else { encuesta.setPatConsPlaFrec(null);}
    		if (tieneValor(patConsPapa)) { catRespuesta = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + patConsPapa + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null); if (catRespuesta!=null) encuesta.setPatConsPapa(catRespuesta.getCatKey()); } else { encuesta.setPatConsPapa(null);}
    		if (tieneValor(patConsPapaFrec)) { catRespuesta = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + patConsPapaFrec + "' and " + MainDBConstants.catRoot + "='CAT_FRECCON'", null); if (catRespuesta!=null) encuesta.setPatConsPapaFrec(catRespuesta.getCatKey()); } else { encuesta.setPatConsPapaFrec(null);}
    		if (tieneValor(patConsLec)) { catRespuesta = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + patConsLec + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null); if (catRespuesta!=null) encuesta.setPatConsLec(catRespuesta.getCatKey()); } else { encuesta.setPatConsLec(null);}
    		if (tieneValor(patConsLecFrec)) { catRespuesta = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + patConsLecFrec + "' and " + MainDBConstants.catRoot + "='CAT_FRECCON'", null); if (catRespuesta!=null) encuesta.setPatConsLecFrec(catRespuesta.getCatKey()); } else { encuesta.setPatConsLecFrec(null);}
    		if (tieneValor(patConsSalTom)) { catRespuesta = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + patConsSalTom + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null); if (catRespuesta!=null) encuesta.setPatConsSalTom(catRespuesta.getCatKey()); } else { encuesta.setPatConsSalTom(null);}
    		if (tieneValor(patConsSalTomFrec)) { catRespuesta = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + patConsSalTomFrec + "' and " + MainDBConstants.catRoot + "='CAT_FRECCON'", null); if (catRespuesta!=null) encuesta.setPatConsSalTomFrec(catRespuesta.getCatKey()); } else { encuesta.setPatConsSalTomFrec(null);}
    		if (tieneValor(patConsGas)) { catRespuesta = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + patConsGas + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null); if (catRespuesta!=null) encuesta.setPatConsGas(catRespuesta.getCatKey()); } else { encuesta.setPatConsGas(null);}
    		if (tieneValor(patConsGasFrec)) { catRespuesta = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + patConsGasFrec + "' and " + MainDBConstants.catRoot + "='CAT_FRECCON'", null); if (catRespuesta!=null) encuesta.setPatConsGasFrec(catRespuesta.getCatKey()); } else { encuesta.setPatConsGasFrec(null);}
    		if (tieneValor(patConsCarRes)) { catRespuesta = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + patConsCarRes + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null); if (catRespuesta!=null) encuesta.setPatConsCarRes(catRespuesta.getCatKey()); } else { encuesta.setPatConsCarRes(null);}
    		if (tieneValor(patConsCarResFrec)) { catRespuesta = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + patConsCarResFrec + "' and " + MainDBConstants.catRoot + "='CAT_FRECCON'", null); if (catRespuesta!=null) encuesta.setPatConsCarResFrec(catRespuesta.getCatKey()); } else { encuesta.setPatConsCarResFrec(null);}
    		if (tieneValor(patConsAvena)) { catRespuesta = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + patConsAvena + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null); if (catRespuesta!=null) encuesta.setPatConsAvena(catRespuesta.getCatKey()); } else { encuesta.setPatConsAvena(null);}
    		if (tieneValor(patConsAvenaFrec)) { catRespuesta = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + patConsAvenaFrec + "' and " + MainDBConstants.catRoot + "='CAT_FRECCON'", null); if (catRespuesta!=null) encuesta.setPatConsAvenaFrec(catRespuesta.getCatKey()); } else { encuesta.setPatConsAvenaFrec(null);}
    		if (tieneValor(patConsNaran)) { catRespuesta = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + patConsNaran + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null); if (catRespuesta!=null) encuesta.setPatConsNaran(catRespuesta.getCatKey()); } else { encuesta.setPatConsNaran(null);}
    		if (tieneValor(patConsNaranFrec)) { catRespuesta = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + patConsNaranFrec + "' and " + MainDBConstants.catRoot + "='CAT_FRECCON'", null); if (catRespuesta!=null) encuesta.setPatConsNaranFrec(catRespuesta.getCatKey()); } else { encuesta.setPatConsNaranFrec(null);}
    		if (tieneValor(patConsPasta)) { catRespuesta = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + patConsPasta + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null); if (catRespuesta!=null) encuesta.setPatConsPasta(catRespuesta.getCatKey()); } else { encuesta.setPatConsPasta(null);}
    		if (tieneValor(patConsPastaFrec)) { catRespuesta = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + patConsPastaFrec + "' and " + MainDBConstants.catRoot + "='CAT_FRECCON'", null); if (catRespuesta!=null) encuesta.setPatConsPastaFrec(catRespuesta.getCatKey()); } else { encuesta.setPatConsPastaFrec(null);}
    		if (tieneValor(patConsAyote)) { catRespuesta = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + patConsAyote + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null); if (catRespuesta!=null) encuesta.setPatConsAyote(catRespuesta.getCatKey()); } else { encuesta.setPatConsAyote(null);}
    		if (tieneValor(patConsAyoteFrec)) { catRespuesta = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + patConsAyoteFrec + "' and " + MainDBConstants.catRoot + "='CAT_FRECCON'", null); if (catRespuesta!=null) encuesta.setPatConsAyoteFrec(catRespuesta.getCatKey()); } else { encuesta.setPatConsAyoteFrec(null);}
    		if (tieneValor(patConsMagg)) { catRespuesta = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + patConsMagg + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null); if (catRespuesta!=null) encuesta.setPatConsMagg(catRespuesta.getCatKey()); } else { encuesta.setPatConsMagg(null);}
    		if (tieneValor(patConsMaggFrec)) { catRespuesta = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + patConsMaggFrec + "' and " + MainDBConstants.catRoot + "='CAT_FRECCON'", null); if (catRespuesta!=null) encuesta.setPatConsMaggFrec(catRespuesta.getCatKey()); } else { encuesta.setPatConsMaggFrec(null);}
    		if (tieneValor(patConsFrut)) { catRespuesta = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + patConsFrut + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null); if (catRespuesta!=null) encuesta.setPatConsFrut(catRespuesta.getCatKey()); } else { encuesta.setPatConsFrut(null);}
    		if (tieneValor(patConsFrutFrec)) { catRespuesta = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + patConsFrutFrec + "' and " + MainDBConstants.catRoot + "='CAT_FRECCON'", null); if (catRespuesta!=null) encuesta.setPatConsFrutFrec(catRespuesta.getCatKey()); } else { encuesta.setPatConsFrutFrec(null);}
    		if (tieneValor(patConsRaic)) { catRespuesta = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + patConsRaic + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null); if (catRespuesta!=null) encuesta.setPatConsRaic(catRespuesta.getCatKey()); } else { encuesta.setPatConsRaic(null);}
    		if (tieneValor(patConsRaicFrec)) { catRespuesta = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + patConsRaicFrec + "' and " + MainDBConstants.catRoot + "='CAT_FRECCON'", null); if (catRespuesta!=null) encuesta.setPatConsRaicFrec(catRespuesta.getCatKey()); } else { encuesta.setPatConsRaicFrec(null);}
    		if (tieneValor(patConsMenei)) { catRespuesta = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + patConsMenei + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null); if (catRespuesta!=null) encuesta.setPatConsMenei(catRespuesta.getCatKey()); } else { encuesta.setPatConsMenei(null);}
    		if (tieneValor(patConsMeneiFrec)) { catRespuesta = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + patConsMeneiFrec + "' and " + MainDBConstants.catRoot + "='CAT_FRECCON'", null); if (catRespuesta!=null) encuesta.setPatConsMeneiFrec(catRespuesta.getCatKey()); } else { encuesta.setPatConsMeneiFrec(null);}
    		if (tieneValor(patConsRepollo)) { catRespuesta = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + patConsRepollo + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null); if (catRespuesta!=null) encuesta.setPatConsRepollo(catRespuesta.getCatKey()); } else { encuesta.setPatConsRepollo(null);}
    		if (tieneValor(patConsRepolloFrec)) { catRespuesta = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + patConsRepolloFrec + "' and " + MainDBConstants.catRoot + "='CAT_FRECCON'", null); if (catRespuesta!=null) encuesta.setPatConsRepolloFrec(catRespuesta.getCatKey()); } else { encuesta.setPatConsRepolloFrec(null);}
    		if (tieneValor(patConsZana)) { catRespuesta = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + patConsZana + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null); if (catRespuesta!=null) encuesta.setPatConsZana(catRespuesta.getCatKey()); } else { encuesta.setPatConsZana(null);}
    		if (tieneValor(patConsZanaFrec)) { catRespuesta = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + patConsZanaFrec + "' and " + MainDBConstants.catRoot + "='CAT_FRECCON'", null); if (catRespuesta!=null) encuesta.setPatConsZanaFrec(catRespuesta.getCatKey()); } else { encuesta.setPatConsZanaFrec(null);}
    		if (tieneValor(patConsPinolillo)) { catRespuesta = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + patConsPinolillo + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null); if (catRespuesta!=null) encuesta.setPatConsPinolillo(catRespuesta.getCatKey()); } else { encuesta.setPatConsPinolillo(null);}
    		if (tieneValor(patConsPinolilloFrec)) { catRespuesta = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + patConsPinolilloFrec + "' and " + MainDBConstants.catRoot + "='CAT_FRECCON'", null); if (catRespuesta!=null) encuesta.setPatConsPinolilloFrec(catRespuesta.getCatKey()); } else { encuesta.setPatConsPinolilloFrec(null);}
    		if (tieneValor(patConsOVerd)) { catRespuesta = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + patConsOVerd + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null); if (catRespuesta!=null) encuesta.setPatConsOVerd(catRespuesta.getCatKey()); } else { encuesta.setPatConsOVerd(null);}
    		if (tieneValor(patConsOVerdFrec)) { catRespuesta = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + patConsOVerdFrec + "' and " + MainDBConstants.catRoot + "='CAT_FRECCON'", null); if (catRespuesta!=null) encuesta.setPatConsOVerdFrec(catRespuesta.getCatKey()); } else { encuesta.setPatConsOVerdFrec(null);}
    		if (tieneValor(patConsPesc)) { catRespuesta = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + patConsPesc + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null); if (catRespuesta!=null) encuesta.setPatConsPesc(catRespuesta.getCatKey()); } else { encuesta.setPatConsPesc(null);}
    		if (tieneValor(patConsPescFrec)) { catRespuesta = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + patConsPescFrec + "' and " + MainDBConstants.catRoot + "='CAT_FRECCON'", null); if (catRespuesta!=null) encuesta.setPatConsPescFrec(catRespuesta.getCatKey()); } else { encuesta.setPatConsPescFrec(null);}
    		if (tieneValor(patConsAlimComp)) { catRespuesta = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + patConsAlimComp + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null); if (catRespuesta!=null) encuesta.setPatConsAlimComp(catRespuesta.getCatKey()); } else { encuesta.setPatConsAlimComp(null);}
    		if (tieneValor(patConsAlimCompFrec)) { catRespuesta = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + patConsAlimCompFrec + "' and " + MainDBConstants.catRoot + "='CAT_FRECCON'", null); if (catRespuesta!=null) encuesta.setPatConsAlimCompFrec(catRespuesta.getCatKey()); } else { encuesta.setPatConsAlimCompFrec(null);}
    		if (tieneValor(patConsLecPol)) { catRespuesta = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + patConsLecPol + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null); if (catRespuesta!=null) encuesta.setPatConsLecPol(catRespuesta.getCatKey()); } else { encuesta.setPatConsLecPol(null);}
    		if (tieneValor(patConsLecPolFrec)) { catRespuesta = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + patConsLecPolFrec + "' and " + MainDBConstants.catRoot + "='CAT_FRECCON'", null); if (catRespuesta!=null) encuesta.setPatConsLecPolFrec(catRespuesta.getCatKey()); } else { encuesta.setPatConsLecPolFrec(null);}
    		if (tieneValor(patConsCarCer)) { catRespuesta = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + patConsCarCer + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null); if (catRespuesta!=null) encuesta.setPatConsCarCer(catRespuesta.getCatKey()); } else { encuesta.setPatConsCarCer(null);}
    		if (tieneValor(patConsCarCerFrec)) { catRespuesta = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + patConsCarCerFrec + "' and " + MainDBConstants.catRoot + "='CAT_FRECCON'", null); if (catRespuesta!=null) encuesta.setPatConsCarCerFrec(catRespuesta.getCatKey()); } else { encuesta.setPatConsCarCerFrec(null);}
    		if (tieneValor(patConsEmb)) { catRespuesta = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + patConsEmb + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null); if (catRespuesta!=null) encuesta.setPatConsEmb(catRespuesta.getCatKey()); } else { encuesta.setPatConsEmb(null);}
    		if (tieneValor(patConsEmbFrec)) { catRespuesta = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + patConsEmbFrec + "' and " + MainDBConstants.catRoot + "='CAT_FRECCON'", null); if (catRespuesta!=null) encuesta.setPatConsEmbFrec(catRespuesta.getCatKey()); } else { encuesta.setPatConsEmbFrec(null);}
    		if (tieneValor(patConsMar)) { catRespuesta = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + patConsMar + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null); if (catRespuesta!=null) encuesta.setPatConsMar(catRespuesta.getCatKey()); } else { encuesta.setPatConsMar(null);}
    		if (tieneValor(patConsMarFrec)) { catRespuesta = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + patConsMarFrec + "' and " + MainDBConstants.catRoot + "='CAT_FRECCON'", null); if (catRespuesta!=null) encuesta.setPatConsMarFrec(catRespuesta.getCatKey()); } else { encuesta.setPatConsMarFrec(null);}
    		if (tieneValor(patConsCarCaza)) { catRespuesta = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + patConsCarCaza + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null); if (catRespuesta!=null) encuesta.setPatConsCarCaza(catRespuesta.getCatKey()); } else { encuesta.setPatConsCarCaza(null);}
    		if (tieneValor(patConsCarCazaFrec)) { catRespuesta = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + patConsCarCazaFrec + "' and " + MainDBConstants.catRoot + "='CAT_FRECCON'", null); if (catRespuesta!=null) encuesta.setPatConsCarCazaFrec(catRespuesta.getCatKey()); } else { encuesta.setPatConsCarCazaFrec(null);}
            
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
