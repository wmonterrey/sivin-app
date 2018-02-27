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
import ni.gob.minsa.sivin.forms.SeccionDForm;
import ni.gob.minsa.sivin.forms.SeccionDFormLabels;
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


public class SeccionDActivity extends FragmentActivity implements
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
	private SeccionDFormLabels labels = new SeccionDFormLabels();
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
		infoMovil = new DeviceInfo(SeccionDActivity.this);
        segmento = (Segmento) getIntent().getExtras().getSerializable(Constants.SEGMENTO);
        encuesta = (Encuesta) getIntent().getExtras().getSerializable(Constants.ENCUESTA);
        nextViv = (Integer) getIntent().getExtras().getInt(Constants.VIVIENDA);
        
        String mPass = ((SivinApplication) this.getApplication()).getPassApp();
        mWizardModel = new SeccionDForm(this,mPass);
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
        	datoCatalogo = sivinAdapter.getMessageResource(MainDBConstants.catKey + "='" + encuesta.getSexselec() + "' and " + MainDBConstants.catRoot + "='CAT_SEXO'", null);
        	
        	modifPage = (LabelPage) mWizardModel.findByKey(labels.getLabelD());
        	modifPage.setHint(modifPage.getHint() + encuesta.getNselec() + "-" + encuesta.getNomselec() 
        										+"\n"+ this.getString(R.string.fnacselec).substring(4) + ": " + mDateFormat.format(encuesta.getFnacselec())
        										+"\n"+ this.getString(R.string.eselect).substring(4) + ": " + encuesta.getEselect()
        										+"\n"+ this.getString(R.string.sexselec).substring(4, 8) + ":" + datoCatalogo.getSpanish());
        	if(tieneValor(encuesta.getHierron())){
                modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getHierron());
                datoCatalogo = sivinAdapter.getMessageResource(MainDBConstants.catKey + "='" + encuesta.getHierron() + "' and " + MainDBConstants.catRoot + "='CAT_SINONR'", null);
                dato = new Bundle(); if(datoCatalogo!=null) dato.putString(SIMPLE_DATA_KEY, datoCatalogo.getSpanish()); modifPage.resetData(dato); modifPage.setmVisible(true);
            }
        	if(tieneValor(encuesta.getThierround())){
                modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getThierround());
                datoCatalogo = sivinAdapter.getMessageResource(MainDBConstants.catKey + "='" + encuesta.getThierround() + "' and " + MainDBConstants.catRoot + "='CAT_HIERROTIEMP'", null);
                dato = new Bundle(); if(datoCatalogo!=null) dato.putString(SIMPLE_DATA_KEY, datoCatalogo.getSpanish()); modifPage.resetData(dato); modifPage.setmVisible(true);
            }
        	if(tieneValor(encuesta.getThierrocant())){
        		modifPage = (NumberPage) mWizardModel.findByKey(labels.getThierrocant());
	        	dato = new Bundle();dato.putString(SIMPLE_DATA_KEY, encuesta.getThierrocant());modifPage.resetData(dato);modifPage.setmVisible(true);
	        	datoCatalogo = sivinAdapter.getMessageResource(MainDBConstants.catKey + "='" + encuesta.getThierround() + "' and " + MainDBConstants.catRoot + "='CAT_HIERROTIEMP'", null);
	        	NumberPage hierroCant = (NumberPage) mWizardModel.findByKey(labels.getThierrocant());
	        	if(datoCatalogo.getCatKey().matches("d")){
	        		hierroCant.setHint("En días desde 1 hasta 7");
	        		hierroCant.setmGreaterOrEqualsThan(1);
	        		hierroCant.setmLowerOrEqualsThan(7);
        		}else if(datoCatalogo.getCatKey().matches("s")){
        			hierroCant.setHint("En semanas desde 1 hasta 5");
        			hierroCant.setmGreaterOrEqualsThan(1);
        			hierroCant.setmLowerOrEqualsThan(5);
        		}
        		else if(datoCatalogo.getCatKey().matches("m")){
        			hierroCant.setHint("En meses desde 1 hasta 12");
        			hierroCant.setmGreaterOrEqualsThan(1);
        			hierroCant.setmLowerOrEqualsThan(12);
        		}
            }
        	if(tieneValor(encuesta.getFhierro())){
        		modifPage = (NumberPage) mWizardModel.findByKey(labels.getFhierro());
	        	dato = new Bundle();dato.putString(SIMPLE_DATA_KEY, encuesta.getFhierro());modifPage.resetData(dato);modifPage.setmVisible(true);
            }
        	if(tieneValor(encuesta.getGhierro())){
                modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getGhierro());
                datoCatalogo = sivinAdapter.getMessageResource(MainDBConstants.catKey + "='" + encuesta.getGhierro() + "' and " + MainDBConstants.catRoot + "='CAT_GOTHIE'", null);
                dato = new Bundle(); if(datoCatalogo!=null) dato.putString(SIMPLE_DATA_KEY, datoCatalogo.getSpanish()); modifPage.resetData(dato); modifPage.setmVisible(true);
            }
        	if(tieneValor(encuesta.getDonhierro())){
                modifPage = (MultipleFixedChoicePage) mWizardModel.findByKey(labels.getDonhierro());
                String codDondeHierro = encuesta.getDonhierro().replaceAll("," , "','");
                List<String> descDondeHierro = new ArrayList<String>();
                List<MessageResource> msDondeHierro = sivinAdapter.getMessageResources(MainDBConstants.catKey + " in ('" + codDondeHierro + "') and " + MainDBConstants.catRoot + "='CAT_HIERROOBTNIN'", null);
                for(MessageResource ms : msDondeHierro){
                    descDondeHierro.add(ms.getSpanish());
                }
                dato = new Bundle();
                dato.putStringArrayList(SIMPLE_DATA_KEY, (ArrayList<String>) descDondeHierro);
                modifPage.resetData(dato);
                modifPage.setmVisible(true);
            }
        	if(tieneValor(encuesta.getDonhierrobesp())){
	        	modifPage = (TextPage) mWizardModel.findByKey(labels.getDonhierrobesp());
	        	dato = new Bundle();dato.putString(SIMPLE_DATA_KEY, encuesta.getDonhierrobesp());modifPage.resetData(dato);modifPage.setmVisible(true);
	        }
        	if(tieneValor(encuesta.getDonhierrofesp())){
	        	modifPage = (TextPage) mWizardModel.findByKey(labels.getDonhierrofesp());
	        	dato = new Bundle();dato.putString(SIMPLE_DATA_KEY, encuesta.getDonhierrofesp());modifPage.resetData(dato);modifPage.setmVisible(true);
	        }
        	if(tieneValor(encuesta.getFuhierro())){
                modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getFuhierro());
                datoCatalogo = sivinAdapter.getMessageResource(MainDBConstants.catKey + "='" + encuesta.getFuhierro() + "' and " + MainDBConstants.catRoot + "='CAT_SINONR'", null);
                dato = new Bundle(); if(datoCatalogo!=null) dato.putString(SIMPLE_DATA_KEY, datoCatalogo.getSpanish()); modifPage.resetData(dato); modifPage.setmVisible(true);
            }
        	if(encuesta.getFuhierror()!=null){
        		modifPage = (NewDatePage) mWizardModel.findByKey(labels.getFuhierror());
	        	dato = new Bundle();dato.putString(SIMPLE_DATA_KEY, mDateFormat.format(encuesta.getFuhierror()));modifPage.resetData(dato);modifPage.setmVisible(true);
            }
        	if(encuesta.getFauhierror()!=null){
        		modifPage = (NewDatePage) mWizardModel.findByKey(labels.getFauhierror());
	        	dato = new Bundle();dato.putString(SIMPLE_DATA_KEY, mDateFormat.format(encuesta.getFauhierror()));modifPage.resetData(dato);modifPage.setmVisible(true);
            }
        	if(tieneValor(encuesta.getNvita())){
                modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getNvita());
                datoCatalogo = sivinAdapter.getMessageResource(MainDBConstants.catKey + "='" + encuesta.getNvita() + "' and " + MainDBConstants.catRoot + "='CAT_SINONR'", null);
                dato = new Bundle(); if(datoCatalogo!=null) dato.putString(SIMPLE_DATA_KEY, datoCatalogo.getSpanish()); modifPage.resetData(dato); modifPage.setmVisible(true);
            }
        	if(tieneValor(encuesta.getNcvita())){
	        	modifPage = (TextPage) mWizardModel.findByKey(labels.getNcvita());
	        	dato = new Bundle();dato.putString(SIMPLE_DATA_KEY, encuesta.getNcvita());modifPage.resetData(dato);modifPage.setmVisible(true);
	        }
        	if(tieneValor(encuesta.getTvitaund())){
                modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getTvitaund());
                datoCatalogo = sivinAdapter.getMessageResource(MainDBConstants.catKey + "='" + encuesta.getTvitaund() + "' and " + MainDBConstants.catRoot + "='CAT_VITATIEMP'", null);
                dato = new Bundle(); if(datoCatalogo!=null) dato.putString(SIMPLE_DATA_KEY, datoCatalogo.getSpanish()); modifPage.resetData(dato); modifPage.setmVisible(true);
            }
        	if(tieneValor(encuesta.getTvitacant())){
        		modifPage = (NumberPage) mWizardModel.findByKey(labels.getTvitacant());
	        	dato = new Bundle();dato.putString(SIMPLE_DATA_KEY, encuesta.getTvitacant());modifPage.resetData(dato);modifPage.setmVisible(true);
	        	datoCatalogo = sivinAdapter.getMessageResource(MainDBConstants.catKey + "='" + encuesta.getTvitaund() + "' and " + MainDBConstants.catRoot + "='CAT_VITATIEMP'", null);
	        	NumberPage vitaCant = (NumberPage) mWizardModel.findByKey(labels.getThierrocant());
	        	if(datoCatalogo.getCatKey().matches("a")){
	        		vitaCant.setHint("En años desde 1 hasta 5");
	        		vitaCant.setmGreaterOrEqualsThan(1);
	        		vitaCant.setmLowerOrEqualsThan(365);
        		}else if(datoCatalogo.getCatKey().matches("s")){
        			vitaCant.setHint("En semanas desde 1 hasta 5");
        			vitaCant.setmGreaterOrEqualsThan(1);
        			vitaCant.setmLowerOrEqualsThan(5);
        		}
        		else if(datoCatalogo.getCatKey().matches("m")){
        			vitaCant.setHint("En meses desde 1 hasta 12");
        			vitaCant.setmGreaterOrEqualsThan(1);
        			vitaCant.setmLowerOrEqualsThan(12);
        		}
            }
        	if(tieneValor(encuesta.getNdvita())){
                modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getNdvita());
                datoCatalogo = sivinAdapter.getMessageResource(MainDBConstants.catKey + "='" + encuesta.getNdvita() + "' and " + MainDBConstants.catRoot + "='CAT_DONDEA'", null);
                dato = new Bundle(); if(datoCatalogo!=null) dato.putString(SIMPLE_DATA_KEY, datoCatalogo.getSpanish()); modifPage.resetData(dato); modifPage.setmVisible(true);
            }
        	if(tieneValor(encuesta.getNdvitao())){
	        	modifPage = (TextPage) mWizardModel.findByKey(labels.getNdvitao());
	        	dato = new Bundle();dato.putString(SIMPLE_DATA_KEY, encuesta.getNdvitao());modifPage.resetData(dato);modifPage.setmVisible(true);
	        }
        	if(tieneValor(encuesta.getTdvita())){
                modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getTdvita());
                datoCatalogo = sivinAdapter.getMessageResource(MainDBConstants.catKey + "='" + encuesta.getTdvita() + "' and " + MainDBConstants.catRoot + "='CAT_SINONR'", null);
                dato = new Bundle(); if(datoCatalogo!=null) dato.putString(SIMPLE_DATA_KEY, datoCatalogo.getSpanish()); modifPage.resetData(dato); modifPage.setmVisible(true);
            }
        	if(encuesta.getFuvita()!=null){
        		modifPage = (NewDatePage) mWizardModel.findByKey(labels.getFuvita());
	        	dato = new Bundle();dato.putString(SIMPLE_DATA_KEY, mDateFormat.format(encuesta.getFuvita()));modifPage.resetData(dato);modifPage.setmVisible(true);
            }
        	if(encuesta.getFauvita()!=null){
        		modifPage = (NewDatePage) mWizardModel.findByKey(labels.getFauvita());
	        	dato = new Bundle();dato.putString(SIMPLE_DATA_KEY, mDateFormat.format(encuesta.getFauvita()));modifPage.resetData(dato);modifPage.setmVisible(true);
            }
        	if(tieneValor(encuesta.getNcnut())){
                modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getNcnut());
                datoCatalogo = sivinAdapter.getMessageResource(MainDBConstants.catKey + "='" + encuesta.getNcnut() + "' and " + MainDBConstants.catRoot + "='CAT_SINONR'", null);
                dato = new Bundle(); if(datoCatalogo!=null) dato.putString(SIMPLE_DATA_KEY, datoCatalogo.getSpanish()); modifPage.resetData(dato); modifPage.setmVisible(true);
            }
        	if(tieneValor(encuesta.getNcnutund())){
                modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getNcnutund());
                datoCatalogo = sivinAdapter.getMessageResource(MainDBConstants.catKey + "='" + encuesta.getNcnutund() + "' and " + MainDBConstants.catRoot + "='CAT_HIERROTIEMP'", null);
                dato = new Bundle(); if(datoCatalogo!=null) dato.putString(SIMPLE_DATA_KEY, datoCatalogo.getSpanish()); modifPage.resetData(dato); modifPage.setmVisible(true);
            }
        	if(tieneValor(encuesta.getNcnutcant())){
        		modifPage = (NumberPage) mWizardModel.findByKey(labels.getNcnutcant());
	        	dato = new Bundle();dato.putString(SIMPLE_DATA_KEY, encuesta.getNcnutcant());modifPage.resetData(dato);modifPage.setmVisible(true);
	        	datoCatalogo = sivinAdapter.getMessageResource(MainDBConstants.catKey + "='" + encuesta.getThierround() + "' and " + MainDBConstants.catRoot + "='CAT_HIERROTIEMP'", null);
	        	NumberPage mnCant = (NumberPage) mWizardModel.findByKey(labels.getNcnutcant());
	        	if(datoCatalogo.getCatKey().matches("d")){
	        		mnCant.setHint("En días desde 1 hasta 7");
	        		mnCant.setmGreaterOrEqualsThan(1);
	        		mnCant.setmLowerOrEqualsThan(7);
        		}else if(datoCatalogo.getCatKey().matches("s")){
        			mnCant.setHint("En semanas desde 1 hasta 5");
        			mnCant.setmGreaterOrEqualsThan(1);
        			mnCant.setmLowerOrEqualsThan(5);
        		}
        		else if(datoCatalogo.getCatKey().matches("m")){
        			mnCant.setHint("En meses desde 1 hasta 12");
        			mnCant.setmGreaterOrEqualsThan(1);
        			mnCant.setmLowerOrEqualsThan(12);
        		}
            }
        	if(tieneValor(encuesta.getDoncnut())){
                modifPage = (MultipleFixedChoicePage) mWizardModel.findByKey(labels.getDoncnut());
                String codCnut = encuesta.getDoncnut().replaceAll("," , "','");
                List<String> descDondeCnut = new ArrayList<String>();
                List<MessageResource> msDondeCnut = sivinAdapter.getMessageResources(MainDBConstants.catKey + " in ('" + codCnut + "') and " + MainDBConstants.catRoot + "='CAT_DONDEMN'", null);
                for(MessageResource ms : msDondeCnut){
                    descDondeCnut.add(ms.getSpanish());
                }
                dato = new Bundle();
                dato.putStringArrayList(SIMPLE_DATA_KEY, (ArrayList<String>) descDondeCnut);
                modifPage.resetData(dato);
                modifPage.setmVisible(true);
            }
        	if(tieneValor(encuesta.getDoncnutfotro())){
	        	modifPage = (TextPage) mWizardModel.findByKey(labels.getDoncnutfotro());
	        	dato = new Bundle();dato.putString(SIMPLE_DATA_KEY, encuesta.getDoncnutfotro());modifPage.resetData(dato);modifPage.setmVisible(true);
	        }
        	if(tieneValor(encuesta.getParasit())){
                modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getParasit());
                datoCatalogo = sivinAdapter.getMessageResource(MainDBConstants.catKey + "='" + encuesta.getParasit() + "' and " + MainDBConstants.catRoot + "='CAT_SINONR'", null);
                dato = new Bundle(); if(datoCatalogo!=null) dato.putString(SIMPLE_DATA_KEY, datoCatalogo.getSpanish()); modifPage.resetData(dato); modifPage.setmVisible(true);
            }
        	if(tieneValor(encuesta.getCvparas())){
	        	modifPage = (TextPage) mWizardModel.findByKey(labels.getCvparas());
	        	dato = new Bundle();dato.putString(SIMPLE_DATA_KEY, encuesta.getCvparas());modifPage.resetData(dato);modifPage.setmVisible(true);
	        }
        	if(tieneValor(encuesta.getmParasitario())){
                modifPage = (MultipleFixedChoicePage) mWizardModel.findByKey(labels.getmParasitario());
                String codAp = encuesta.getmParasitario().replaceAll("," , "','");
                List<String> descAp = new ArrayList<String>();
                List<MessageResource> msAp = sivinAdapter.getMessageResources(MainDBConstants.catKey + " in ('" + codAp + "') and " + MainDBConstants.catRoot + "='CAT_MEDPAR'", null);
                for(MessageResource ms : msAp){
                    descAp.add(ms.getSpanish());
                }
                dato = new Bundle();
                dato.putStringArrayList(SIMPLE_DATA_KEY, (ArrayList<String>) descAp);
                modifPage.resetData(dato);
                modifPage.setmVisible(true);
            }
        	if(tieneValor(encuesta.getOtroMpEsp())){
	        	modifPage = (TextPage) mWizardModel.findByKey(labels.getOtroMpEsp());
	        	dato = new Bundle();dato.putString(SIMPLE_DATA_KEY, encuesta.getOtroMpEsp());modifPage.resetData(dato);modifPage.setmVisible(true);
	        }
        	if(tieneValor(encuesta.getDonMp())){
                modifPage = (MultipleFixedChoicePage) mWizardModel.findByKey(labels.getDonMp());
                String codDonAp = encuesta.getDonMp().replaceAll("," , "','");
                List<String> descDonAp = new ArrayList<String>();
                List<MessageResource> msDonAp = sivinAdapter.getMessageResources(MainDBConstants.catKey + " in ('" + codDonAp + "') and " + MainDBConstants.catRoot + "='CAT_DONMEDPAR'", null);
                for(MessageResource ms : msDonAp){
                    descDonAp.add(ms.getSpanish());
                }
                dato = new Bundle();
                dato.putStringArrayList(SIMPLE_DATA_KEY, (ArrayList<String>) descDonAp);
                modifPage.resetData(dato);
                modifPage.setmVisible(true);
            }
        	if(tieneValor(encuesta.getOtroDonMp())){
	        	modifPage = (TextPage) mWizardModel.findByKey(labels.getOtroDonMp());
	        	dato = new Bundle();dato.putString(SIMPLE_DATA_KEY, encuesta.getOtroDonMp());modifPage.resetData(dato);modifPage.setmVisible(true);
	        }
        	if(tieneValor(encuesta.getEvitarParasito())){
                modifPage = (MultipleFixedChoicePage) mWizardModel.findByKey(labels.getEvitarParasito());
                String codEvP = encuesta.getEvitarParasito().replaceAll("," , "','");
                List<String> descEvP = new ArrayList<String>();
                List<MessageResource> msDonAp = sivinAdapter.getMessageResources(MainDBConstants.catKey + " in ('" + codEvP + "') and " + MainDBConstants.catRoot + "='CAT_EVITARPAR'", null);
                for(MessageResource ms : msDonAp){
                    descEvP.add(ms.getSpanish());
                }
                dato = new Bundle();
                dato.putStringArrayList(SIMPLE_DATA_KEY, (ArrayList<String>) descEvP);
                modifPage.resetData(dato);
                modifPage.setmVisible(true);
            }
        	if(tieneValor(encuesta.getoEvitarParasito())){
	        	modifPage = (TextPage) mWizardModel.findByKey(labels.getoEvitarParasito());
	        	dato = new Bundle();dato.putString(SIMPLE_DATA_KEY, encuesta.getoEvitarParasito());modifPage.resetData(dato);modifPage.setmVisible(true);
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
        	if (page.getTitle().equals(labels.getHierron())) {
        		cambiarFormParaHierro(false);
        		catalogo = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + page.getData().getString(TextPage.SIMPLE_DATA_KEY) + "' and " + MainDBConstants.catRoot + "='CAT_SINONR'", null);
        		visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && catalogo.getCatKey().matches(Constants.SI);
        		changeStatus(mWizardModel.findByKey(labels.getThierround()),visible);
        		changeStatus(mWizardModel.findByKey(labels.getFhierro()),visible);
        		changeStatus(mWizardModel.findByKey(labels.getGhierro()),visible);
        		changeStatus(mWizardModel.findByKey(labels.getDonhierro()),visible);
        		changeStatus(mWizardModel.findByKey(labels.getFuhierro()),visible);
            }
        	if (page.getTitle().equals(labels.getThierround())) {
        		catalogo = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + page.getData().getString(TextPage.SIMPLE_DATA_KEY) + "' and " + MainDBConstants.catRoot + "='CAT_HIERROTIEMP'", null);
        		visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && !catalogo.getCatKey().matches(Constants.NOSABE);
        		NumberPage tiempoHierroPage = (NumberPage) mWizardModel.findByKey(labels.getThierrocant());
        		tiempoHierroPage.setValue("");
        		if(catalogo.getCatKey().matches("d")){
        			tiempoHierroPage.setHint("En días desde 1 hasta 7");
        			tiempoHierroPage.setmGreaterOrEqualsThan(1);
        			tiempoHierroPage.setmLowerOrEqualsThan(7);
        		}else if(catalogo.getCatKey().matches("s")){
        			tiempoHierroPage.setHint("En semanas desde 1 hasta 5");
        			tiempoHierroPage.setmGreaterOrEqualsThan(1);
        			tiempoHierroPage.setmLowerOrEqualsThan(5);
        		}
        		else if(catalogo.getCatKey().matches("m")){
        			tiempoHierroPage.setHint("En meses desde 1 hasta 12");
        			tiempoHierroPage.setmGreaterOrEqualsThan(1);
        			tiempoHierroPage.setmLowerOrEqualsThan(12);
        		}
        		changeStatus(tiempoHierroPage,visible);
            }
        	if (page.getTitle().equals(labels.getFhierro())) {
        		Integer frascos = Integer.parseInt(page.getData().getString(TextPage.SIMPLE_DATA_KEY));
        		if(frascos > 8 && frascos != 88) {
        			page.resetData(new Bundle());
        			Toast.makeText(getApplicationContext(), getString(R.string.data_error) + "\n" + frascos,Toast.LENGTH_LONG).show();
        		}
            }
        	if (page.getTitle().equals(labels.getDonhierro())) {
            	ArrayList<String> respuestas = page.getData().getStringArrayList(TextPage.SIMPLE_DATA_KEY);
            	for(String respuesta:respuestas) {
            		catalogo = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + respuesta + "' and " + MainDBConstants.catRoot + "='CAT_HIERROOBTNIN'", null);
            		if(catalogo.getCatKey().matches("b")) {
            			visible=true;
            			break;
            		}
            	}
            	changeStatus(mWizardModel.findByKey(labels.getDonhierrobesp()),visible);
            	visible=false;
            	for(String respuesta:respuestas) {
            		catalogo = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + respuesta + "' and " + MainDBConstants.catRoot + "='CAT_HIERROOBTNIN'", null);
            		if(catalogo.getCatKey().matches("f")) {
            			visible=true;
            			break;
            		}
            	}
            	changeStatus(mWizardModel.findByKey(labels.getDonhierrofesp()),visible);
            }
        	if (page.getTitle().equals(labels.getFuhierro())) {
        		catalogo = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + page.getData().getString(TextPage.SIMPLE_DATA_KEY) + "' and " + MainDBConstants.catRoot + "='CAT_SINONR'", null);
        		visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && catalogo.getCatKey().matches(Constants.SI);
        		changeStatus(mWizardModel.findByKey(labels.getFuhierror()),visible);
        		changeStatus(mWizardModel.findByKey(labels.getFauhierror()),visible);
            }
        	if (page.getTitle().equals(labels.getNvita())) {
        		cambiarFormParaVita(false);
        		catalogo = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + page.getData().getString(TextPage.SIMPLE_DATA_KEY) + "' and " + MainDBConstants.catRoot + "='CAT_SINONR'", null);
        		visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && catalogo.getCatKey().matches(Constants.SI);
        		changeStatus(mWizardModel.findByKey(labels.getNcvita()),visible);
        		changeStatus(mWizardModel.findByKey(labels.getTvitaund()),visible);
        		changeStatus(mWizardModel.findByKey(labels.getNdvita()),visible);
        		changeStatus(mWizardModel.findByKey(labels.getTdvita()),visible);
            }
        	if (page.getTitle().equals(labels.getTvitaund())) {
        		catalogo = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + page.getData().getString(TextPage.SIMPLE_DATA_KEY) + "' and " + MainDBConstants.catRoot + "='CAT_VITATIEMP'", null);
        		visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && !catalogo.getCatKey().matches(Constants.NOSABE);
        		NumberPage tiempoVitaPage = (NumberPage) mWizardModel.findByKey(labels.getTvitacant());
        		tiempoVitaPage.setValue("");
        		if(catalogo.getCatKey().matches("a")){
        			tiempoVitaPage.setHint("En años desde 1 hasta 5");
        			tiempoVitaPage.setmGreaterOrEqualsThan(1);
        			tiempoVitaPage.setmLowerOrEqualsThan(365);
        		}else if(catalogo.getCatKey().matches("s")){
        			tiempoVitaPage.setHint("En semanas desde 1 hasta 5");
        			tiempoVitaPage.setmGreaterOrEqualsThan(1);
        			tiempoVitaPage.setmLowerOrEqualsThan(5);
        		}
        		else if(catalogo.getCatKey().matches("m")){
        			tiempoVitaPage.setHint("En meses desde 1 hasta 12");
        			tiempoVitaPage.setmGreaterOrEqualsThan(1);
        			tiempoVitaPage.setmLowerOrEqualsThan(12);
        		}
        		changeStatus(tiempoVitaPage,visible);
            }
        	if (page.getTitle().equals(labels.getNdvita())) {
        		catalogo = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + page.getData().getString(TextPage.SIMPLE_DATA_KEY) + "' and " + MainDBConstants.catRoot + "='CAT_DONDEA'", null);
        		visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && catalogo.getCatKey().matches("3");
        		changeStatus(mWizardModel.findByKey(labels.getNdvitao()),visible);
            }
        	if (page.getTitle().equals(labels.getTdvita())) {
        		catalogo = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + page.getData().getString(TextPage.SIMPLE_DATA_KEY) + "' and " + MainDBConstants.catRoot + "='CAT_SINONR'", null);
        		visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && catalogo.getCatKey().matches(Constants.SI);
        		changeStatus(mWizardModel.findByKey(labels.getFuvita()),visible);
        		changeStatus(mWizardModel.findByKey(labels.getFauvita()),visible);
            }
        	if (page.getTitle().equals(labels.getNcnut())) {
        		cambiarFormParaMN(false);
        		catalogo = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + page.getData().getString(TextPage.SIMPLE_DATA_KEY) + "' and " + MainDBConstants.catRoot + "='CAT_SINONR'", null);
        		visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && catalogo.getCatKey().matches(Constants.SI);
        		changeStatus(mWizardModel.findByKey(labels.getNcnutund()),visible);
        		changeStatus(mWizardModel.findByKey(labels.getDoncnut()),visible);
            }
        	if (page.getTitle().equals(labels.getNcnutund())) {
        		catalogo = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + page.getData().getString(TextPage.SIMPLE_DATA_KEY) + "' and " + MainDBConstants.catRoot + "='CAT_HIERROTIEMP'", null);
        		visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && !catalogo.getCatKey().matches(Constants.NOSABE);
        		NumberPage tiempoMnPage = (NumberPage) mWizardModel.findByKey(labels.getNcnutcant());
        		tiempoMnPage.setValue("");
        		if(catalogo.getCatKey().matches("d")){
        			tiempoMnPage.setHint("En días desde 1 hasta 7");
        			tiempoMnPage.setmGreaterOrEqualsThan(1);
        			tiempoMnPage.setmLowerOrEqualsThan(7);
        		}else if(catalogo.getCatKey().matches("s")){
        			tiempoMnPage.setHint("En semanas desde 1 hasta 5");
        			tiempoMnPage.setmGreaterOrEqualsThan(1);
        			tiempoMnPage.setmLowerOrEqualsThan(5);
        		}
        		else if(catalogo.getCatKey().matches("m")){
        			tiempoMnPage.setHint("En meses desde 1 hasta 12");
        			tiempoMnPage.setmGreaterOrEqualsThan(1);
        			tiempoMnPage.setmLowerOrEqualsThan(12);
        		}
        		changeStatus(tiempoMnPage,visible);
            }
        	if (page.getTitle().equals(labels.getDoncnut())) {
            	ArrayList<String> respuestas = page.getData().getStringArrayList(TextPage.SIMPLE_DATA_KEY);
            	for(String respuesta:respuestas) {
            		catalogo = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + respuesta + "' and " + MainDBConstants.catRoot + "='CAT_DONDEMN'", null);
            		if(catalogo.getCatKey().matches("f")) {
            			visible=true;
            			break;
            		}
            	}
            	changeStatus(mWizardModel.findByKey(labels.getDoncnutfotro()),visible);
            }
        	if (page.getTitle().equals(labels.getParasit())) {
        		cambiarFormParaMParasitos(false);
        		catalogo = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + page.getData().getString(TextPage.SIMPLE_DATA_KEY) + "' and " + MainDBConstants.catRoot + "='CAT_SINONR'", null);
        		visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && catalogo.getCatKey().matches(Constants.SI);
        		changeStatus(mWizardModel.findByKey(labels.getCvparas()),visible);
        		changeStatus(mWizardModel.findByKey(labels.getmParasitario()),visible);
        		changeStatus(mWizardModel.findByKey(labels.getDonMp()),visible);
            }
        	if (page.getTitle().equals(labels.getmParasitario())) {
            	ArrayList<String> respuestas = page.getData().getStringArrayList(TextPage.SIMPLE_DATA_KEY);
            	for(String respuesta:respuestas) {
            		catalogo = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + respuesta + "' and " + MainDBConstants.catRoot + "='CAT_MEDPAR'", null);
            		if(catalogo.getCatKey().matches("d")) {
            			visible=true;
            			break;
            		}
            	}
            	changeStatus(mWizardModel.findByKey(labels.getOtroMpEsp()),visible);
            }
        	if (page.getTitle().equals(labels.getDonMp())) {
            	ArrayList<String> respuestas = page.getData().getStringArrayList(TextPage.SIMPLE_DATA_KEY);
            	for(String respuesta:respuestas) {
            		catalogo = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + respuesta + "' and " + MainDBConstants.catRoot + "='CAT_DONMEDPAR'", null);
            		if(catalogo.getCatKey().matches("e")) {
            			visible=true;
            			break;
            		}
            	}
            	changeStatus(mWizardModel.findByKey(labels.getOtroDonMp()),visible);
            }
        	if (page.getTitle().equals(labels.getEvitarParasito())) {
            	ArrayList<String> respuestas = page.getData().getStringArrayList(TextPage.SIMPLE_DATA_KEY);
            	for(String respuesta:respuestas) {
            		catalogo = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + respuesta + "' and " + MainDBConstants.catRoot + "='CAT_EVITARPAR'", null);
            		if(catalogo.getCatKey().matches("g")) {
            			visible=true;
            			break;
            		}
            	}
            	changeStatus(mWizardModel.findByKey(labels.getoEvitarParasito()),visible);
            }
        	onPageTreeChanged();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
    
    public void cambiarFormParaHierro(boolean valor) {
    	changeStatus(mWizardModel.findByKey(labels.getThierround()),valor);
		changeStatus(mWizardModel.findByKey(labels.getThierrocant()),valor);
		changeStatus(mWizardModel.findByKey(labels.getFhierro()),valor);
		changeStatus(mWizardModel.findByKey(labels.getGhierro()),valor);
		changeStatus(mWizardModel.findByKey(labels.getDonhierro()),valor);
		changeStatus(mWizardModel.findByKey(labels.getDonhierrobesp()),valor);
		changeStatus(mWizardModel.findByKey(labels.getDonhierrofesp()),valor);
		changeStatus(mWizardModel.findByKey(labels.getFuhierro()),valor);
		changeStatus(mWizardModel.findByKey(labels.getFuhierror()),valor);
		changeStatus(mWizardModel.findByKey(labels.getFauhierror()),valor);    	
    }
    
    
    public void cambiarFormParaVita(boolean valor) {
    	changeStatus(mWizardModel.findByKey(labels.getNcvita()),valor);
		changeStatus(mWizardModel.findByKey(labels.getTvitaund()),valor);
		changeStatus(mWizardModel.findByKey(labels.getTvitacant()),valor);
		changeStatus(mWizardModel.findByKey(labels.getNdvita()),valor);
		changeStatus(mWizardModel.findByKey(labels.getNdvitao()),valor);
		changeStatus(mWizardModel.findByKey(labels.getTdvita()),valor);
		changeStatus(mWizardModel.findByKey(labels.getFuvita()),valor);
		changeStatus(mWizardModel.findByKey(labels.getFauvita()),valor);
    }
    
    public void cambiarFormParaMN(boolean valor) {
    	changeStatus(mWizardModel.findByKey(labels.getNcnutund()),valor);
		changeStatus(mWizardModel.findByKey(labels.getNcnutcant()),valor);
		changeStatus(mWizardModel.findByKey(labels.getDoncnut()),valor);
		changeStatus(mWizardModel.findByKey(labels.getDoncnutfotro()),valor);
    }
    
    public void cambiarFormParaMParasitos(boolean valor) {
    	changeStatus(mWizardModel.findByKey(labels.getCvparas()),valor);
		changeStatus(mWizardModel.findByKey(labels.getmParasitario()),valor);
		changeStatus(mWizardModel.findByKey(labels.getOtroMpEsp()),valor);
		changeStatus(mWizardModel.findByKey(labels.getDonMp()),valor);
		changeStatus(mWizardModel.findByKey(labels.getOtroDonMp()),valor);
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
            String hierron = datos.getString(this.getString(R.string.hierron));
            String thierround = datos.getString(this.getString(R.string.thierround));
            String thierrocant = datos.getString(this.getString(R.string.thierrocant));
            String fhierro = datos.getString(this.getString(R.string.fhierro));
            String ghierro = datos.getString(this.getString(R.string.ghierro));
            String donhierro = datos.getString(this.getString(R.string.donhierro));
            String donhierrobesp = datos.getString(this.getString(R.string.donhierrobesp));
            String donhierrofesp = datos.getString(this.getString(R.string.donhierrofesp));
            String fuhierro = datos.getString(this.getString(R.string.fuhierro));
            String fuhierror = datos.getString(this.getString(R.string.fuhierror));
            String fauhierror = datos.getString(this.getString(R.string.fauhierror));
            String nvita = datos.getString(this.getString(R.string.nvita));
            String ncvita = datos.getString(this.getString(R.string.ncvita));
            String tvitaund = datos.getString(this.getString(R.string.tvitaund));
            String tvitacant = datos.getString(this.getString(R.string.tvitacant));
            String ndvita = datos.getString(this.getString(R.string.ndvita));
            String ndvitao = datos.getString(this.getString(R.string.ndvitao));
            String tdvita = datos.getString(this.getString(R.string.tdvita));
            String fuvita = datos.getString(this.getString(R.string.fuvita));
            String fauvita = datos.getString(this.getString(R.string.fauvita));
            String ncnut = datos.getString(this.getString(R.string.ncnut));
            String ncnutund = datos.getString(this.getString(R.string.ncnutund));
            String ncnutcant = datos.getString(this.getString(R.string.ncnutcant));
            String doncnut = datos.getString(this.getString(R.string.doncnut));
            String doncnutfotro = datos.getString(this.getString(R.string.doncnutfotro));
            String parasit = datos.getString(this.getString(R.string.parasit));
            String cvparas = datos.getString(this.getString(R.string.cvparas));
            String mParasitario = datos.getString(this.getString(R.string.mParasitario));
            String otroMpEsp = datos.getString(this.getString(R.string.otroMpEsp));
            String donMp = datos.getString(this.getString(R.string.donMp));
            String otroDonMp = datos.getString(this.getString(R.string.otroDonMp));
            String evitarParasito = datos.getString(this.getString(R.string.evitarParasito));
            String oEvitarParasito = datos.getString(this.getString(R.string.oEvitarParasito));
    		
    		//Para recuperar catalogos
    		MessageResource catRespuesta;
            
            if (tieneValor(hierron)) {
    			catRespuesta = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + hierron + "' and " + MainDBConstants.catRoot + "='CAT_SINONR'", null);
    			if (catRespuesta!=null) encuesta.setHierron(catRespuesta.getCatKey());
    		}
            else {
            	encuesta.setHierron(null);
            }
            if (tieneValor(thierround)) {
            	catRespuesta = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + thierround + "' and " + MainDBConstants.catRoot + "='CAT_HIERROTIEMP'", null);
    			if (catRespuesta!=null) encuesta.setThierround(catRespuesta.getCatKey());
    		}
            else {
            	encuesta.setThierround(null);
            }
            if (tieneValor(thierrocant)) {encuesta.setThierrocant(thierrocant);} else {encuesta.setThierrocant(null);}
            if (tieneValor(fhierro)) {encuesta.setFhierro(fhierro);} else {encuesta.setFhierro(null);}
            if (tieneValor(ghierro)) {
    			catRespuesta = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + ghierro + "' and " + MainDBConstants.catRoot + "='CAT_GOTHIE'", null);
    			if (catRespuesta!=null) encuesta.setGhierro(catRespuesta.getCatKey());
    		}
            else {
            	encuesta.setGhierro(null);
            }
            if (tieneValor(donhierro)) {
                String keysDondeHierro = "";
                donhierro = donhierro.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll(", " , "','");
                List<MessageResource> msDondeHierro = sivinAdapter.getMessageResources(MainDBConstants.spanish + " in ('" + donhierro + "') and "
                        + MainDBConstants.catRoot + "='CAT_HIERROOBTNIN'", null);
                for(MessageResource ms : msDondeHierro) {
                    keysDondeHierro += ms.getCatKey() + ",";
                }
                if (!keysDondeHierro.isEmpty())
                    keysDondeHierro = keysDondeHierro.substring(0, keysDondeHierro.length() - 1);
                encuesta.setDonhierro(keysDondeHierro);
            } else {
            	encuesta.setDonhierro(null);
            }
            if (tieneValor(donhierrobesp)) {encuesta.setDonhierrobesp(donhierrobesp);} else {encuesta.setDonhierrobesp(null);}
            if (tieneValor(donhierrofesp)) {encuesta.setDonhierrofesp(donhierrofesp);} else {encuesta.setDonhierrofesp(null);}
            if (tieneValor(fuhierro)) {
    			catRespuesta = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + fuhierro + "' and " + MainDBConstants.catRoot + "='CAT_SINONT'", null);
    			if (catRespuesta!=null) encuesta.setFuhierro(catRespuesta.getCatKey());
    		}
            else {
            	encuesta.setFuhierro(null);
            }
            Date fuhierrorParsed = null;
            if (tieneValor(fuhierror)) {
	            try {
	            	fuhierrorParsed = mDateFormat.parse(fuhierror);
	    		} catch (ParseException e) {
	    			// TODO Auto-generated catch block
	    			e.printStackTrace();
	    			Toast toast = Toast.makeText(getApplicationContext(),e.getLocalizedMessage(),Toast.LENGTH_LONG);
	    			toast.show();
	    			finish();
	    		}
            }
            encuesta.setFuhierror(fuhierrorParsed);
            Date fauhierrorParsed = null;
            if (tieneValor(fauhierror)) {
	            try {
	            	fauhierrorParsed = mDateFormat.parse(fauhierror);
	    		} catch (ParseException e) {
	    			// TODO Auto-generated catch block
	    			e.printStackTrace();
	    			Toast toast = Toast.makeText(getApplicationContext(),e.getLocalizedMessage(),Toast.LENGTH_LONG);
	    			toast.show();
	    			finish();
	    		}
            }
            encuesta.setFauhierror(fauhierrorParsed);
            if (tieneValor(nvita)) {
    			catRespuesta = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + nvita + "' and " + MainDBConstants.catRoot + "='CAT_SINONR'", null);
    			if (catRespuesta!=null) encuesta.setNvita(catRespuesta.getCatKey());
    		}
            else {
            	encuesta.setNvita(null);
            }
            if (tieneValor(ncvita)) {encuesta.setNcvita(ncvita);} else {encuesta.setNcvita(null);}
            if (tieneValor(tvitaund)) {
            	catRespuesta = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + tvitaund + "' and " + MainDBConstants.catRoot + "='CAT_VITATIEMP'", null);
    			if (catRespuesta!=null) encuesta.setTvitaund(catRespuesta.getCatKey());
    		}
            else {
            	encuesta.setTvitaund(null);
            }
            if (tieneValor(tvitacant)) {encuesta.setTvitacant(tvitacant);} else {encuesta.setTvitacant(null);}
            if (tieneValor(ndvita)) {
            	catRespuesta = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + ndvita + "' and " + MainDBConstants.catRoot + "='CAT_DONDEA'", null);
    			if (catRespuesta!=null) encuesta.setNdvita(catRespuesta.getCatKey());
    		}
            else {
            	encuesta.setNdvita(null);
            }
            if (tieneValor(ndvitao)) {encuesta.setNdvitao(ndvitao);} else {encuesta.setNdvitao(null);}
        	
            if (tieneValor(tdvita)) {
    			catRespuesta = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + tdvita + "' and " + MainDBConstants.catRoot + "='CAT_SINONT'", null);
    			if (catRespuesta!=null) encuesta.setTdvita(catRespuesta.getCatKey());
    		}
            else {
            	encuesta.setTdvita(null);
            }
            Date fuvitaParsed = null;
            if (tieneValor(fuvita)) {
	            try {
	            	fuvitaParsed = mDateFormat.parse(fuvita);
	    		} catch (ParseException e) {
	    			// TODO Auto-generated catch block
	    			e.printStackTrace();
	    			Toast toast = Toast.makeText(getApplicationContext(),e.getLocalizedMessage(),Toast.LENGTH_LONG);
	    			toast.show();
	    			finish();
	    		}
            }
            encuesta.setFuvita(fuvitaParsed);
            Date fauvitaParsed = null;
            if (tieneValor(fauvita)) {
	            try {
	            	fauvitaParsed = mDateFormat.parse(fauvita);
	    		} catch (ParseException e) {
	    			// TODO Auto-generated catch block
	    			e.printStackTrace();
	    			Toast toast = Toast.makeText(getApplicationContext(),e.getLocalizedMessage(),Toast.LENGTH_LONG);
	    			toast.show();
	    			finish();
	    		}
            }
            encuesta.setFauvita(fauvitaParsed);
        	
            if (tieneValor(ncnut)) {
    			catRespuesta = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + ncnut + "' and " + MainDBConstants.catRoot + "='CAT_SINONR'", null);
    			if (catRespuesta!=null) encuesta.setNcnut(catRespuesta.getCatKey());
    		}
            else {
            	encuesta.setNcnut(null);
            }
            if (tieneValor(ncnutund)) {
            	catRespuesta = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + ncnutund + "' and " + MainDBConstants.catRoot + "='CAT_HIERROTIEMP'", null);
    			if (catRespuesta!=null) encuesta.setNcnutund(catRespuesta.getCatKey());
    		}
            else {
            	encuesta.setNcnutund(null);
            }
            if (tieneValor(ncnutcant)) {encuesta.setNcnutcant(ncnutcant);} else {encuesta.setNcnutcant(null);}
            
            if (tieneValor(doncnut)) {
                String keysDondeCnut = "";
                doncnut = doncnut.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll(", " , "','");
                List<MessageResource> msDondeCnut = sivinAdapter.getMessageResources(MainDBConstants.spanish + " in ('" + doncnut + "') and "
                        + MainDBConstants.catRoot + "='CAT_DONDEMN'", null);
                for(MessageResource ms : msDondeCnut) {
                    keysDondeCnut += ms.getCatKey() + ",";
                }
                if (!keysDondeCnut.isEmpty())
                    keysDondeCnut = keysDondeCnut.substring(0, keysDondeCnut.length() - 1);
                encuesta.setDoncnut(keysDondeCnut);
            } else {
            	encuesta.setDoncnut(null);
            }
            if (tieneValor(doncnutfotro)) {encuesta.setDoncnutfotro(doncnutfotro);} else {encuesta.setDoncnutfotro(null);}
        	
            if (tieneValor(parasit)) {
    			catRespuesta = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + parasit + "' and " + MainDBConstants.catRoot + "='CAT_SINONR'", null);
    			if (catRespuesta!=null) encuesta.setParasit(catRespuesta.getCatKey());
    		}
            else {
            	encuesta.setParasit(null);
            }
            if (tieneValor(cvparas)) {encuesta.setCvparas(cvparas);} else {encuesta.setCvparas(null);}
            
            if (tieneValor(mParasitario)) {
                String keysNombreMed = "";
                mParasitario = mParasitario.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll(", " , "','");
                List<MessageResource> msNombreMed = sivinAdapter.getMessageResources(MainDBConstants.spanish + " in ('" + mParasitario + "') and "
                        + MainDBConstants.catRoot + "='CAT_MEDPAR'", null);
                for(MessageResource ms : msNombreMed) {
                    keysNombreMed += ms.getCatKey() + ",";
                }
                if (!keysNombreMed.isEmpty())
                    keysNombreMed = keysNombreMed.substring(0, keysNombreMed.length() - 1);
                encuesta.setmParasitario(keysNombreMed);
            } else {
            	encuesta.setmParasitario(null);
            }
            if (tieneValor(otroMpEsp)) {encuesta.setOtroMpEsp(otroMpEsp);} else {encuesta.setOtroMpEsp(null);}
            
            if (tieneValor(donMp)) {
                String keysDonMp = "";
                donMp = donMp.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll(", " , "','");
                List<MessageResource> msDonMp = sivinAdapter.getMessageResources(MainDBConstants.spanish + " in ('" + donMp + "') and "
                        + MainDBConstants.catRoot + "='CAT_DONMEDPAR'", null);
                for(MessageResource ms : msDonMp) {
                    keysDonMp += ms.getCatKey() + ",";
                }
                if (!keysDonMp.isEmpty())
                    keysDonMp = keysDonMp.substring(0, keysDonMp.length() - 1);
                encuesta.setDonMp(keysDonMp);
            } else {
            	encuesta.setDonMp(null);
            }
            if (tieneValor(otroDonMp)) {encuesta.setOtroDonMp(otroDonMp);} else {encuesta.setOtroDonMp(null);}
            
            if (tieneValor(evitarParasito)) {
                String keysEvitarP = "";
                evitarParasito = evitarParasito.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll(", " , "','");
                List<MessageResource> msEvitarP = sivinAdapter.getMessageResources(MainDBConstants.spanish + " in ('" + evitarParasito + "') and "
                        + MainDBConstants.catRoot + "='CAT_EVITARPAR'", null);
                for(MessageResource ms : msEvitarP) {
                    keysEvitarP += ms.getCatKey() + ",";
                }
                if (!keysEvitarP.isEmpty())
                    keysEvitarP = keysEvitarP.substring(0, keysEvitarP.length() - 1);
                encuesta.setEvitarParasito(keysEvitarP);
            } else {
            	encuesta.setEvitarParasito(null);
            }
            if (tieneValor(oEvitarParasito)) {encuesta.setoEvitarParasito(oEvitarParasito);} else {encuesta.setoEvitarParasito(null);}

            
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
