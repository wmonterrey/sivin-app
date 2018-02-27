package ni.gob.minsa.sivin.activities.enterdata;

import android.annotation.SuppressLint;
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
import ni.gob.minsa.sivin.forms.SeccionEForm;
import ni.gob.minsa.sivin.forms.SeccionEFormLabels;
import ni.gob.minsa.sivin.preferences.PreferencesActivity;
import ni.gob.minsa.sivin.utils.CalcularEdad;
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

import org.joda.time.Period;


@SuppressLint("SimpleDateFormat")
public class SeccionEActivity extends FragmentActivity implements
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
	private SeccionEFormLabels labels = new SeccionEFormLabels();
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
		infoMovil = new DeviceInfo(SeccionEActivity.this);
        segmento = (Segmento) getIntent().getExtras().getSerializable(Constants.SEGMENTO);
        encuesta = (Encuesta) getIntent().getExtras().getSerializable(Constants.ENCUESTA);
        nextViv = (Integer) getIntent().getExtras().getInt(Constants.VIVIENDA);
        
        String mPass = ((SivinApplication) this.getApplication()).getPassApp();
        mWizardModel = new SeccionEForm(this,mPass);
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
        	
        	modifPage = (LabelPage) mWizardModel.findByKey(labels.getLabelE());
        	modifPage.setHint(modifPage.getHint() + encuesta.getNselec() + "-" + encuesta.getNomselec() 
        										+"\n"+ this.getString(R.string.fnacselec).substring(4) + ": " + mDateFormat.format(encuesta.getFnacselec())
        										+"\n"+ this.getString(R.string.eselect).substring(4) + ": " + encuesta.getEselect()
        										+"\n"+ this.getString(R.string.sexselec).substring(4, 8) + ":" + datoCatalogo.getSpanish());
        	
        	if(tieneValor(encuesta.getNlactmat())){  modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getNlactmat());datoCatalogo = sivinAdapter.getMessageResource(MainDBConstants.catKey + "='" + encuesta.getNlactmat() + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null);  dato = new Bundle(); if(datoCatalogo!=null) dato.putString(SIMPLE_DATA_KEY, datoCatalogo.getSpanish()); modifPage.resetData(dato); modifPage.setmVisible(true);}
        	if(tieneValor(encuesta.getSexlmat())){  modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getSexlmat());datoCatalogo = sivinAdapter.getMessageResource(MainDBConstants.catKey + "='" + encuesta.getSexlmat() + "' and " + MainDBConstants.catRoot + "='CAT_SEXO'", null);  dato = new Bundle(); if(datoCatalogo!=null) dato.putString(SIMPLE_DATA_KEY, datoCatalogo.getSpanish()); modifPage.resetData(dato); modifPage.setmVisible(true);}
        	if(tieneValor(encuesta.getPecho())){  modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getPecho());datoCatalogo = sivinAdapter.getMessageResource(MainDBConstants.catKey + "='" + encuesta.getPecho() + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null);  dato = new Bundle(); if(datoCatalogo!=null) dato.putString(SIMPLE_DATA_KEY, datoCatalogo.getSpanish()); modifPage.resetData(dato); modifPage.setmVisible(true);}
        	if(tieneValor(encuesta.getMotNopecho())){  modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getMotNopecho());datoCatalogo = sivinAdapter.getMessageResource(MainDBConstants.catKey + "='" + encuesta.getMotNopecho() + "' and " + MainDBConstants.catRoot + "='CAT_NODIOPECHO'", null);  dato = new Bundle(); if(datoCatalogo!=null) dato.putString(SIMPLE_DATA_KEY, datoCatalogo.getSpanish()); modifPage.resetData(dato); modifPage.setmVisible(true);}
        	if(tieneValor(encuesta.getDapecho())){  modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getDapecho());datoCatalogo = sivinAdapter.getMessageResource(MainDBConstants.catKey + "='" + encuesta.getDapecho() + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null);  dato = new Bundle(); if(datoCatalogo!=null) dato.putString(SIMPLE_DATA_KEY, datoCatalogo.getSpanish()); modifPage.resetData(dato); modifPage.setmVisible(true);}
        	if(tieneValor(encuesta.getTiempecho())){  modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getTiempecho());datoCatalogo = sivinAdapter.getMessageResource(MainDBConstants.catKey + "='" + encuesta.getTiempecho() + "' and " + MainDBConstants.catRoot + "='CAT_TNACPECHO'", null);  dato = new Bundle(); if(datoCatalogo!=null) dato.putString(SIMPLE_DATA_KEY, datoCatalogo.getSpanish()); modifPage.resetData(dato); modifPage.setmVisible(true);}
        	if(tieneValor(encuesta.getTiemmama())){  modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getTiemmama());datoCatalogo = sivinAdapter.getMessageResource(MainDBConstants.catKey + "='" + encuesta.getTiemmama() + "' and " + MainDBConstants.catRoot + "='CAT_TTOMAPECHO'", null);  dato = new Bundle(); if(datoCatalogo!=null) dato.putString(SIMPLE_DATA_KEY, datoCatalogo.getSpanish()); modifPage.resetData(dato); modifPage.setmVisible(true);}
        	if(tieneValor(encuesta.getDospechos())){  modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getDospechos());datoCatalogo = sivinAdapter.getMessageResource(MainDBConstants.catKey + "='" + encuesta.getDospechos() + "' and " + MainDBConstants.catRoot + "='CAT_SINOAV'", null);  dato = new Bundle(); if(datoCatalogo!=null) dato.putString(SIMPLE_DATA_KEY, datoCatalogo.getSpanish()); modifPage.resetData(dato); modifPage.setmVisible(true);}
        	if(tieneValor(encuesta.getElmatexcund())){  modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getElmatexcund());datoCatalogo = sivinAdapter.getMessageResource(MainDBConstants.catKey + "='" + encuesta.getElmatexcund() + "' and " + MainDBConstants.catRoot + "='CAT_LACTTIEMP'", null);  dato = new Bundle(); if(datoCatalogo!=null) dato.putString(SIMPLE_DATA_KEY, datoCatalogo.getSpanish()); modifPage.resetData(dato); modifPage.setmVisible(true);}
        	if(tieneValor(encuesta.getEdiopechound())){  modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getEdiopechound());datoCatalogo = sivinAdapter.getMessageResource(MainDBConstants.catKey + "='" + encuesta.getEdiopechound() + "' and " + MainDBConstants.catRoot + "='CAT_HIERROTIEMP'", null);  dato = new Bundle(); if(datoCatalogo!=null) dato.putString(SIMPLE_DATA_KEY, datoCatalogo.getSpanish()); modifPage.resetData(dato); modifPage.setmVisible(true);}
        	if(tieneValor(encuesta.getCombeb())){  modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getCombeb());datoCatalogo = sivinAdapter.getMessageResource(MainDBConstants.catKey + "='" + encuesta.getCombeb() + "' and " + MainDBConstants.catRoot + "='CAT_SINONR'", null);  dato = new Bundle(); if(datoCatalogo!=null) dato.putString(SIMPLE_DATA_KEY, datoCatalogo.getSpanish()); modifPage.resetData(dato); modifPage.setmVisible(true);}
        	if(tieneValor(encuesta.getEalimund())){  modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getEalimund());datoCatalogo = sivinAdapter.getMessageResource(MainDBConstants.catKey + "='" + encuesta.getEalimund() + "' and " + MainDBConstants.catRoot + "='CAT_HIERROTIEMP'", null);  dato = new Bundle(); if(datoCatalogo!=null) dato.putString(SIMPLE_DATA_KEY, datoCatalogo.getSpanish()); modifPage.resetData(dato); modifPage.setmVisible(true);}
        	if(tieneValor(encuesta.getReunionPeso())){  modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getReunionPeso());datoCatalogo = sivinAdapter.getMessageResource(MainDBConstants.catKey + "='" + encuesta.getReunionPeso() + "' and " + MainDBConstants.catRoot + "='CAT_SINONR'", null);  dato = new Bundle(); if(datoCatalogo!=null) dato.putString(SIMPLE_DATA_KEY, datoCatalogo.getSpanish()); modifPage.resetData(dato); modifPage.setmVisible(true);}
        	if(tieneValor(encuesta.getQuienReunionPeso())){  modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getQuienReunionPeso());datoCatalogo = sivinAdapter.getMessageResource(MainDBConstants.catKey + "='" + encuesta.getQuienReunionPeso() + "' and " + MainDBConstants.catRoot + "='CAT_QREUPESO'", null);  dato = new Bundle(); if(datoCatalogo!=null) dato.putString(SIMPLE_DATA_KEY, datoCatalogo.getSpanish()); modifPage.resetData(dato); modifPage.setmVisible(true);}
        	if(tieneValor(encuesta.getAssitioReunionPeso())){  modifPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getAssitioReunionPeso());datoCatalogo = sivinAdapter.getMessageResource(MainDBConstants.catKey + "='" + encuesta.getAssitioReunionPeso() + "' and " + MainDBConstants.catRoot + "='CAT_SINONR'", null);  dato = new Bundle(); if(datoCatalogo!=null) dato.putString(SIMPLE_DATA_KEY, datoCatalogo.getSpanish()); modifPage.resetData(dato); modifPage.setmVisible(true);}


        	if(tieneValor(encuesta.getEmeseslmat())){  modifPage = (NumberPage) mWizardModel.findByKey(labels.getEmeseslmat());  dato = new Bundle(); dato.putString(SIMPLE_DATA_KEY,encuesta.getEmeseslmat()); modifPage.resetData(dato); modifPage.setmVisible(true); modifPage.setmEnabled(false);}
        	if(tieneValor(encuesta.getTiemmamaMins())){  modifPage = (NumberPage) mWizardModel.findByKey(labels.getTiemmamaMins());  dato = new Bundle(); dato.putString(SIMPLE_DATA_KEY,encuesta.getTiemmamaMins()); modifPage.resetData(dato); modifPage.setmVisible(true);}
        	if(tieneValor(encuesta.getVecespechodia())){  modifPage = (NumberPage) mWizardModel.findByKey(labels.getVecespechodia());  dato = new Bundle(); dato.putString(SIMPLE_DATA_KEY,encuesta.getVecespechodia()); modifPage.resetData(dato); modifPage.setmVisible(true);}
        	if(tieneValor(encuesta.getVecespechonoche())){  modifPage = (NumberPage) mWizardModel.findByKey(labels.getVecespechonoche());  dato = new Bundle(); dato.putString(SIMPLE_DATA_KEY,encuesta.getVecespechonoche()); modifPage.resetData(dato); modifPage.setmVisible(true);}
        	if(tieneValor(encuesta.getElmatexccant())){  modifPage = (NumberPage) mWizardModel.findByKey(labels.getElmatexccant());  dato = new Bundle(); dato.putString(SIMPLE_DATA_KEY,encuesta.getElmatexccant()); modifPage.resetData(dato); modifPage.setmVisible(true);}
        	if(tieneValor(encuesta.getEdiopechocant())){  modifPage = (NumberPage) mWizardModel.findByKey(labels.getEdiopechocant());  dato = new Bundle(); dato.putString(SIMPLE_DATA_KEY,encuesta.getEdiopechocant()); modifPage.resetData(dato); modifPage.setmVisible(true);}
        	if(tieneValor(encuesta.getEalimcant())){  modifPage = (NumberPage) mWizardModel.findByKey(labels.getEalimcant());  dato = new Bundle(); dato.putString(SIMPLE_DATA_KEY,encuesta.getEalimcant()); modifPage.resetData(dato); modifPage.setmVisible(true);}
  	
        	if(tieneValor(encuesta.getMotNopechoOtro())){  modifPage = (TextPage) mWizardModel.findByKey(labels.getMotNopechoOtro());  dato = new Bundle(); dato.putString(SIMPLE_DATA_KEY,encuesta.getMotNopechoOtro()); modifPage.resetData(dato); modifPage.setmVisible(true);}
        	if(tieneValor(encuesta.getQuienReunionPesoOtro())){  modifPage = (TextPage) mWizardModel.findByKey(labels.getQuienReunionPesoOtro());  dato = new Bundle(); dato.putString(SIMPLE_DATA_KEY,encuesta.getQuienReunionPesoOtro()); modifPage.resetData(dato); modifPage.setmVisible(true);}
        	
        	if(encuesta.getFnaclmat()!=null){
        		modifPage = (NewDatePage) mWizardModel.findByKey(labels.getFnaclmat());
	        	dato = new Bundle();dato.putString(SIMPLE_DATA_KEY, mDateFormat.format(encuesta.getFnaclmat()));modifPage.resetData(dato);modifPage.setmVisible(true);
            }
        	
        	if(tieneValor(encuesta.getBebeLiq())){
                modifPage = (MultipleFixedChoicePage) mWizardModel.findByKey(labels.getBebeLiq());
                String codBebLiq = encuesta.getBebeLiq().replaceAll("," , "','");
                List<String> descBebLiq = new ArrayList<String>();
                List<MessageResource> msBebeLiq = sivinAdapter.getMessageResources(MainDBConstants.catKey + " in ('" + codBebLiq + "') and " + MainDBConstants.catRoot + "='CAT_BEBENIN'", null);
                for(MessageResource ms : msBebeLiq){
                    descBebLiq.add(ms.getSpanish());
                }
                dato = new Bundle();
                dato.putStringArrayList(SIMPLE_DATA_KEY, (ArrayList<String>) descBebLiq);
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
        	Bundle dato;
        	if (page.getTitle().equals(labels.getNlactmat())) {
        		cambiarFormParaLactMat(false);
        		catalogo = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + page.getData().getString(TextPage.SIMPLE_DATA_KEY) + "' and " + MainDBConstants.catRoot + "='CAT_SINONA'", null);
        		visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && (catalogo.getCatKey().matches(Constants.SI)||catalogo.getCatKey().matches(Constants.NO));
        		if(catalogo.getCatKey().matches(Constants.SI)) {
        			if(!verificarSeleccionadoEsMenor24()) {
        				page.resetData(new Bundle());
        				Toast.makeText(getApplicationContext(), getString(R.string.nlactmatno),Toast.LENGTH_LONG).show();
        			}
        			else {
        				SingleFixedChoicePage sexoPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getSexlmat());
        				MessageResource datoCatalogo = sivinAdapter.getMessageResource(MainDBConstants.catKey + "='" + encuesta.getSexselec() + "' and " + MainDBConstants.catRoot + "='CAT_SEXO'", null);
        				dato = new Bundle(); if(catalogo!=null) dato.putString(SIMPLE_DATA_KEY, datoCatalogo.getSpanish()); sexoPage.resetData(dato); sexoPage.setmEnabled(false);sexoPage.setmVisible(true);
        				NewDatePage fecNacPage = (NewDatePage) mWizardModel.findByKey(labels.getFnaclmat());
        				dato = new Bundle();dato.putString(SIMPLE_DATA_KEY, mDateFormat.format(encuesta.getFnacselec()));fecNacPage.resetData(dato);fecNacPage.setmEnabled(false);fecNacPage.setmVisible(true);
        				NumberPage edadPage = (NumberPage) mWizardModel.findByKey(labels.getEmeseslmat());
        				dato = new Bundle();dato.putString(SIMPLE_DATA_KEY, encuesta.getEselect());edadPage.resetData(dato);edadPage.setmEnabled(false);edadPage.setmVisible(true);
        			}
        		}
        		if(catalogo.getCatKey().matches(Constants.NO)) {
        			if(!verificarHayMenor24()) {
        				page.resetData(new Bundle());
        				Toast.makeText(getApplicationContext(), getString(R.string.nlactmatna),Toast.LENGTH_LONG).show();
        			}
        			else {
        				SingleFixedChoicePage sexoPage = (SingleFixedChoicePage) mWizardModel.findByKey(labels.getSexlmat());
        				dato = new Bundle(); sexoPage.resetData(dato); sexoPage.setmEnabled(true);sexoPage.setmVisible(true);
        				NewDatePage fecNacPage = (NewDatePage) mWizardModel.findByKey(labels.getFnaclmat());
        				dato = new Bundle();fecNacPage.resetData(dato);fecNacPage.setmEnabled(true);fecNacPage.setmVisible(true);
        				NumberPage edadPage = (NumberPage) mWizardModel.findByKey(labels.getEmeseslmat());
        				dato = new Bundle();edadPage.resetData(dato);edadPage.setmEnabled(false);edadPage.setmVisible(true);
        			}
        		}
        		if(catalogo.getCatKey().matches("3")) {
        			if(verificarHayMenor24()) {
        				page.resetData(new Bundle());
        				Toast.makeText(getApplicationContext(), getString(R.string.nlactmathay),Toast.LENGTH_LONG).show();
        			}
        		}
        		changeStatus(mWizardModel.findByKey(labels.getPecho()),visible);
        		changeStatus(mWizardModel.findByKey(labels.getDapecho()),visible);
        		changeStatus(mWizardModel.findByKey(labels.getElmatexcund()),visible);
        		changeStatus(mWizardModel.findByKey(labels.getCombeb()),visible);
        		changeStatus(mWizardModel.findByKey(labels.getEalimund()),visible);
        		changeStatus(mWizardModel.findByKey(labels.getBebeLiq()),visible);
        		changeStatus(mWizardModel.findByKey(labels.getReunionPeso()),visible);
            }
        	if (page.getTitle().equals(labels.getFnaclmat())) {
        		Date fnacEntParsed = mDateFormat.parse(page.getData().getString(TextPage.SIMPLE_DATA_KEY));
               	Date fecEntrevista = encuesta.getFechaEntrevista();
               	CalcularEdad ce = new CalcularEdad();
               	Period fiff = ce.calcDiff(fnacEntParsed, fecEntrevista);
               	NumberPage edadValue = (NumberPage) mWizardModel.findByKey(labels.getEmeseslmat());
               	edadValue.setValue(String.valueOf(fiff.getYears()*12 + fiff.getMonths()));
               	edadValue.setmEnabled(false);
        	}
        	if (page.getTitle().equals(labels.getPecho())) {
        		cambiarFormParaLactMatNo(false);
        		catalogo = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + page.getData().getString(TextPage.SIMPLE_DATA_KEY) + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null);
        		visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && catalogo.getCatKey().matches(Constants.SI);
        		changeStatus(mWizardModel.findByKey(labels.getDapecho()),visible);
        		changeStatus(mWizardModel.findByKey(labels.getMotNopecho()),!visible);
        		changeStatus(mWizardModel.findByKey(labels.getMotNopechoOtro()),!visible);
            }
        	if (page.getTitle().equals(labels.getMotNopecho())) {
        		catalogo = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + page.getData().getString(TextPage.SIMPLE_DATA_KEY) + "' and " + MainDBConstants.catRoot + "='CAT_NODIOPECHO'", null);
        		visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && catalogo.getCatKey().matches("8");
        		changeStatus(mWizardModel.findByKey(labels.getMotNopechoOtro()),visible);
            }
        	if (page.getTitle().equals(labels.getDapecho())) {
        		catalogo = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + page.getData().getString(TextPage.SIMPLE_DATA_KEY) + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null);
        		visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && catalogo.getCatKey().matches(Constants.SI);
        		changeStatus(mWizardModel.findByKey(labels.getTiempecho()),visible);
        		changeStatus(mWizardModel.findByKey(labels.getTiemmama()),visible);
        		changeStatus(mWizardModel.findByKey(labels.getDospechos()),visible);
        		changeStatus(mWizardModel.findByKey(labels.getVecespechodia()),visible);
        		changeStatus(mWizardModel.findByKey(labels.getVecespechonoche()),visible);
        		changeStatus(mWizardModel.findByKey(labels.getElmatexcund()),visible);
            }
        	if (page.getTitle().equals(labels.getTiemmama())) {
        		catalogo = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + page.getData().getString(TextPage.SIMPLE_DATA_KEY) + "' and " + MainDBConstants.catRoot + "='CAT_TTOMAPECHO'", null);
        		visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && catalogo.getCatKey().matches("4");
        		changeStatus(mWizardModel.findByKey(labels.getTiemmamaMins()),visible);
            }
        	
        	if (page.getTitle().equals(labels.getElmatexcund())) {
        		changeStatus(mWizardModel.findByKey(labels.getElmatexccant()),false);
        		changeStatus(mWizardModel.findByKey(labels.getEdiopechound()),false);
        		changeStatus(mWizardModel.findByKey(labels.getEdiopechocant()),false);
        		catalogo = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + page.getData().getString(TextPage.SIMPLE_DATA_KEY) + "' and " + MainDBConstants.catRoot + "='CAT_LACTTIEMP'", null);
        		NumberPage tiempoLactMatPage = (NumberPage) mWizardModel.findByKey(labels.getElmatexccant());
        		tiempoLactMatPage.setValue("");
        		if(catalogo.getCatKey().matches("d")){
        			tiempoLactMatPage.setHint("En días desde 1 hasta 7");
        			tiempoLactMatPage.setmGreaterOrEqualsThan(1);
        			tiempoLactMatPage.setmLowerOrEqualsThan(7);
        			changeStatus(tiempoLactMatPage,true);
        			changeStatus(mWizardModel.findByKey(labels.getEdiopechound()),true);
        		}else if(catalogo.getCatKey().matches("s")){
        			tiempoLactMatPage.setHint("En semanas desde 1 hasta 8");
        			tiempoLactMatPage.setmGreaterOrEqualsThan(1);
        			tiempoLactMatPage.setmLowerOrEqualsThan(8);
        			changeStatus(tiempoLactMatPage,true);
        			changeStatus(mWizardModel.findByKey(labels.getEdiopechound()),true);
        		}
        		else if(catalogo.getCatKey().matches("m")){
        			tiempoLactMatPage.setHint("En meses desde 1 hasta 48");
        			tiempoLactMatPage.setmGreaterOrEqualsThan(1);
        			tiempoLactMatPage.setmLowerOrEqualsThan(48);
        			changeStatus(tiempoLactMatPage,true);
        			changeStatus(mWizardModel.findByKey(labels.getEdiopechound()),true);
        		}
        		else if(catalogo.getCatKey().matches("n")){
        			changeStatus(tiempoLactMatPage,false);
        			changeStatus(mWizardModel.findByKey(labels.getEdiopechound()),true);
        		}
        		else if(catalogo.getCatKey().matches("c")){
        			changeStatus(mWizardModel.findByKey(labels.getEdiopechound()),false);
        			changeStatus(mWizardModel.findByKey(labels.getEdiopechocant()),false);
        			changeStatus(tiempoLactMatPage,false);
        		}
            }
        	
        	if (page.getTitle().equals(labels.getEdiopechound())) {
        		catalogo = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + page.getData().getString(TextPage.SIMPLE_DATA_KEY) + "' and " + MainDBConstants.catRoot + "='CAT_HIERROTIEMP'", null);
        		visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && !catalogo.getCatKey().matches(Constants.NOSABE);
        		NumberPage tiempoPechoPage = (NumberPage) mWizardModel.findByKey(labels.getEdiopechocant());
        		tiempoPechoPage.setValue("");
        		if(catalogo.getCatKey().matches("d")){
        			tiempoPechoPage.setHint("En días desde 1 hasta 7");
        			tiempoPechoPage.setmGreaterOrEqualsThan(1);
        			tiempoPechoPage.setmLowerOrEqualsThan(7);
        		}else if(catalogo.getCatKey().matches("s")){
        			tiempoPechoPage.setHint("En semanas desde 1 hasta 8");
        			tiempoPechoPage.setmGreaterOrEqualsThan(1);
        			tiempoPechoPage.setmLowerOrEqualsThan(8);
        		}
        		else if(catalogo.getCatKey().matches("m")){
        			tiempoPechoPage.setHint("En meses desde 1 hasta 48");
        			tiempoPechoPage.setmGreaterOrEqualsThan(1);
        			tiempoPechoPage.setmLowerOrEqualsThan(48);
        		}
        		changeStatus(tiempoPechoPage,visible);
            }
        	if (page.getTitle().equals(labels.getEalimund())) {
        		catalogo = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + page.getData().getString(TextPage.SIMPLE_DATA_KEY) + "' and " + MainDBConstants.catRoot + "='CAT_HIERROTIEMP'", null);
        		visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && !catalogo.getCatKey().matches(Constants.NOSABE);
        		NumberPage tiempoPechoPage = (NumberPage) mWizardModel.findByKey(labels.getEalimcant());
        		tiempoPechoPage.setValue("");
        		if(catalogo.getCatKey().matches("d")){
        			tiempoPechoPage.setHint("En días desde 1 hasta 7");
        			tiempoPechoPage.setmGreaterOrEqualsThan(1);
        			tiempoPechoPage.setmLowerOrEqualsThan(7);
        		}else if(catalogo.getCatKey().matches("s")){
        			tiempoPechoPage.setHint("En semanas desde 1 hasta 8");
        			tiempoPechoPage.setmGreaterOrEqualsThan(1);
        			tiempoPechoPage.setmLowerOrEqualsThan(8);
        		}
        		else if(catalogo.getCatKey().matches("m")){
        			tiempoPechoPage.setHint("En meses desde 1 hasta 12");
        			tiempoPechoPage.setmGreaterOrEqualsThan(1);
        			tiempoPechoPage.setmLowerOrEqualsThan(12);
        		}
        		changeStatus(tiempoPechoPage,visible);
            }
        	
        	if (page.getTitle().equals(labels.getReunionPeso())) {
        		changeStatus(mWizardModel.findByKey(labels.getQuienReunionPesoOtro()),false);
        		catalogo = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + page.getData().getString(TextPage.SIMPLE_DATA_KEY) + "' and " + MainDBConstants.catRoot + "='CAT_SINONR'", null);
        		visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && catalogo.getCatKey().matches(Constants.SI);
        		changeStatus(mWizardModel.findByKey(labels.getQuienReunionPeso()),visible);
        		changeStatus(mWizardModel.findByKey(labels.getAssitioReunionPeso()),visible);
            }
        	
        	if (page.getTitle().equals(labels.getQuienReunionPeso())) {
        		catalogo = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + page.getData().getString(TextPage.SIMPLE_DATA_KEY) + "' and " + MainDBConstants.catRoot + "='CAT_QREUPESO'", null);
        		visible = page.getData().getString(TextPage.SIMPLE_DATA_KEY) != null && catalogo.getCatKey().matches("3");
        		changeStatus(mWizardModel.findByKey(labels.getQuienReunionPesoOtro()),visible);
            }
        	
        	onPageTreeChanged();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
    
    public boolean verificarHayMenor24() {
    	if(encuesta.getE1()!=null) {
    		if(Integer.parseInt(encuesta.getE1())<=24) return true;
		}
    	if(encuesta.getE2()!=null) {
    		if(Integer.parseInt(encuesta.getE2())<=24) return true;
		}
    	if(encuesta.getE3()!=null) {
    		if(Integer.parseInt(encuesta.getE3())<=24) return true;
		}
    	if(encuesta.getE4()!=null) {
    		if(Integer.parseInt(encuesta.getE4())<=24) return true;
		}
    	if(encuesta.getE5()!=null) {
    		if(Integer.parseInt(encuesta.getE5())<=24) return true;
		}
    	if(encuesta.getE6()!=null) {
    		if(Integer.parseInt(encuesta.getE6())<=24) return true;
		}
    	return false;
    }
    
    public boolean verificarSeleccionadoEsMenor24() {
    	if(Integer.parseInt(encuesta.getEselect())>24) {
			return false;
		}
    	return true;
    }
    
    public void cambiarFormParaLactMat(boolean valor) {
    	changeStatus(mWizardModel.findByKey(labels.getSexlmat()),valor);
		changeStatus(mWizardModel.findByKey(labels.getFnaclmat()),valor);
		changeStatus(mWizardModel.findByKey(labels.getEmeseslmat()),valor);
		changeStatus(mWizardModel.findByKey(labels.getPecho()),valor);
		changeStatus(mWizardModel.findByKey(labels.getMotNopecho()),valor);
		changeStatus(mWizardModel.findByKey(labels.getMotNopechoOtro()),valor);
		changeStatus(mWizardModel.findByKey(labels.getDapecho()),valor);
		changeStatus(mWizardModel.findByKey(labels.getTiempecho()),valor);
		changeStatus(mWizardModel.findByKey(labels.getTiemmama()),valor);
		changeStatus(mWizardModel.findByKey(labels.getTiemmamaMins()),valor);
		changeStatus(mWizardModel.findByKey(labels.getDospechos()),valor);
		changeStatus(mWizardModel.findByKey(labels.getVecespechodia()),valor);
		changeStatus(mWizardModel.findByKey(labels.getVecespechonoche()),valor);
		changeStatus(mWizardModel.findByKey(labels.getElmatexcund()),valor);
		changeStatus(mWizardModel.findByKey(labels.getElmatexccant()),valor);
		changeStatus(mWizardModel.findByKey(labels.getEdiopechound()),valor);
		changeStatus(mWizardModel.findByKey(labels.getEdiopechocant()),valor);
		changeStatus(mWizardModel.findByKey(labels.getCombeb()),valor);
		changeStatus(mWizardModel.findByKey(labels.getEalimund()),valor);
		changeStatus(mWizardModel.findByKey(labels.getEalimcant()),valor);
		changeStatus(mWizardModel.findByKey(labels.getBebeLiq()),valor);
		changeStatus(mWizardModel.findByKey(labels.getReunionPeso()),valor);
		changeStatus(mWizardModel.findByKey(labels.getQuienReunionPeso()),valor);
		changeStatus(mWizardModel.findByKey(labels.getQuienReunionPesoOtro()),valor);
		changeStatus(mWizardModel.findByKey(labels.getAssitioReunionPeso()),valor);
    }
    
    public void cambiarFormParaLactMatNo(boolean valor) {
    	changeStatus(mWizardModel.findByKey(labels.getDapecho()),valor);
		changeStatus(mWizardModel.findByKey(labels.getTiempecho()),valor);
		changeStatus(mWizardModel.findByKey(labels.getTiemmama()),valor);
		changeStatus(mWizardModel.findByKey(labels.getTiemmamaMins()),valor);
		changeStatus(mWizardModel.findByKey(labels.getDospechos()),valor);
		changeStatus(mWizardModel.findByKey(labels.getVecespechodia()),valor);
		changeStatus(mWizardModel.findByKey(labels.getVecespechonoche()),valor);
		changeStatus(mWizardModel.findByKey(labels.getElmatexcund()),valor);
		changeStatus(mWizardModel.findByKey(labels.getElmatexccant()),valor);
		changeStatus(mWizardModel.findByKey(labels.getEdiopechound()),valor);
		changeStatus(mWizardModel.findByKey(labels.getEdiopechocant()),valor);
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
            String nlactmat = datos.getString(this.getString(R.string.nlactmat));
            String sexlmat = datos.getString(this.getString(R.string.sexlmat));
            String emeseslmat = datos.getString(this.getString(R.string.emeseslmat));
            String fnaclmat = datos.getString(this.getString(R.string.fnaclmat));
            String pecho = datos.getString(this.getString(R.string.pecho));
            String motNopecho = datos.getString(this.getString(R.string.motNopecho));
            String motNopechoOtro = datos.getString(this.getString(R.string.motNopechoOtro));
            String dapecho = datos.getString(this.getString(R.string.dapecho));
            String tiempecho = datos.getString(this.getString(R.string.tiempecho));
            String tiemmama = datos.getString(this.getString(R.string.tiemmama));
            String tiemmamaMins = datos.getString(this.getString(R.string.tiemmamaMins));
            String dospechos = datos.getString(this.getString(R.string.dospechos));
            String vecespechodia = datos.getString(this.getString(R.string.vecespechodia));
            String vecespechonoche = datos.getString(this.getString(R.string.vecespechonoche));
            String elmatexcund = datos.getString(this.getString(R.string.elmatexcund));
            String elmatexccant = datos.getString(this.getString(R.string.elmatexccant));
            String ediopechound = datos.getString(this.getString(R.string.ediopechound));
            String ediopechocant = datos.getString(this.getString(R.string.ediopechocant));
            String combeb = datos.getString(this.getString(R.string.combeb));
            String ealimund = datos.getString(this.getString(R.string.ealimund));
            String ealimcant = datos.getString(this.getString(R.string.ealimcant));
            String bebeLiq = datos.getString(this.getString(R.string.bebeLiq));
            String reunionPeso = datos.getString(this.getString(R.string.reunionPeso));
            String quienReunionPeso = datos.getString(this.getString(R.string.quienReunionPeso));
            String quienReunionPesoOtro = datos.getString(this.getString(R.string.quienReunionPesoOtro));
            String assitioReunionPeso = datos.getString(this.getString(R.string.assitioReunionPeso));


    		
    		//Para recuperar catalogos
    		MessageResource catRespuesta;
            
            
    		if (tieneValor(nlactmat)) { 
    			catRespuesta = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + nlactmat + "' and " + MainDBConstants.catRoot + "='CAT_SINONA'", null); if (catRespuesta!=null) encuesta.setNlactmat(catRespuesta.getCatKey()); } else { encuesta.setNlactmat(null);}
    		if (tieneValor(sexlmat)) { 
    			catRespuesta = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + sexlmat + "' and " + MainDBConstants.catRoot + "='CAT_SEXO'", null); if (catRespuesta!=null) encuesta.setSexlmat(catRespuesta.getCatKey()); } else { encuesta.setSexlmat(null);}
    		if (tieneValor(pecho)) { 
    			catRespuesta = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + pecho + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null); if (catRespuesta!=null) encuesta.setPecho(catRespuesta.getCatKey()); } else { encuesta.setPecho(null);}
    		if (tieneValor(motNopecho)) { 
    			catRespuesta = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + motNopecho + "' and " + MainDBConstants.catRoot + "='CAT_NODIOPECHO'", null); if (catRespuesta!=null) encuesta.setMotNopecho(catRespuesta.getCatKey()); } else { encuesta.setMotNopecho(null);}
    		if (tieneValor(dapecho)) { 
    			catRespuesta = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + dapecho + "' and " + MainDBConstants.catRoot + "='CAT_SINO'", null); if (catRespuesta!=null) encuesta.setDapecho(catRespuesta.getCatKey()); } else { encuesta.setDapecho(null);}
    		if (tieneValor(tiempecho)) { 
    			catRespuesta = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + tiempecho + "' and " + MainDBConstants.catRoot + "='CAT_TNACPECHO'", null); if (catRespuesta!=null) encuesta.setTiempecho(catRespuesta.getCatKey()); } else { encuesta.setTiempecho(null);}
    		if (tieneValor(tiemmama)) { 
    			catRespuesta = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + tiemmama + "' and " + MainDBConstants.catRoot + "='CAT_TTOMAPECHO'", null); if (catRespuesta!=null) encuesta.setTiemmama(catRespuesta.getCatKey()); } else { encuesta.setTiemmama(null);}
    		if (tieneValor(dospechos)) { 
    			catRespuesta = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + dospechos + "' and " + MainDBConstants.catRoot + "='CAT_SINOAV'", null); if (catRespuesta!=null) encuesta.setDospechos(catRespuesta.getCatKey()); } else { encuesta.setDospechos(null);}
    		if (tieneValor(elmatexcund)) {
    			catRespuesta = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + elmatexcund + "' and " + MainDBConstants.catRoot + "='CAT_LACTTIEMP'", null); if (catRespuesta!=null) encuesta.setElmatexcund(catRespuesta.getCatKey()); } else { encuesta.setElmatexcund(null);}
    		if (tieneValor(ediopechound)) { 
    			catRespuesta = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + ediopechound + "' and " + MainDBConstants.catRoot + "='CAT_HIERROTIEMP'", null); if (catRespuesta!=null) encuesta.setEdiopechound(catRespuesta.getCatKey()); } else { encuesta.setEdiopechound(null);}
    		if (tieneValor(combeb)) { 
    			catRespuesta = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + combeb + "' and " + MainDBConstants.catRoot + "='CAT_SINONR'", null); if (catRespuesta!=null) encuesta.setCombeb(catRespuesta.getCatKey()); } else { encuesta.setCombeb(null);}
    		if (tieneValor(ealimund)) { 
    			catRespuesta = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + ealimund + "' and " + MainDBConstants.catRoot + "='CAT_HIERROTIEMP'", null); if (catRespuesta!=null) encuesta.setEalimund(catRespuesta.getCatKey()); } else { encuesta.setEalimund(null);}
    		if (tieneValor(reunionPeso)) { 
    			catRespuesta = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + reunionPeso + "' and " + MainDBConstants.catRoot + "='CAT_SINONR'", null); if (catRespuesta!=null) encuesta.setReunionPeso(catRespuesta.getCatKey()); } else { encuesta.setReunionPeso(null);}
    		if (tieneValor(quienReunionPeso)) { 
    			catRespuesta = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + quienReunionPeso + "' and " + MainDBConstants.catRoot + "='CAT_QREUPESO'", null); if (catRespuesta!=null) encuesta.setQuienReunionPeso(catRespuesta.getCatKey()); } else { encuesta.setQuienReunionPeso(null);}
    		if (tieneValor(assitioReunionPeso)) { 
    			catRespuesta = sivinAdapter.getMessageResource(MainDBConstants.spanish + "='" + assitioReunionPeso + "' and " + MainDBConstants.catRoot + "='CAT_SINONR'", null); if (catRespuesta!=null) encuesta.setAssitioReunionPeso(catRespuesta.getCatKey()); } else { encuesta.setAssitioReunionPeso(null);}

    		
    		Date fnacSelecParsed = null;
            if (tieneValor(fnaclmat)) {
	            try {
	            	fnacSelecParsed = mDateFormat.parse(fnaclmat);
	    		} catch (ParseException e) {
	    			// TODO Auto-generated catch block
	    			e.printStackTrace();
	    			Toast toast = Toast.makeText(getApplicationContext(),e.getLocalizedMessage(),Toast.LENGTH_LONG);
	    			toast.show();
	    			finish();
	    		}
            }
            encuesta.setFnaclmat(fnacSelecParsed);
            if (tieneValor(emeseslmat)) {encuesta.setEmeseslmat(emeseslmat);} else {encuesta.setEmeseslmat(null);}
            if (tieneValor(tiemmamaMins)) {encuesta.setTiemmamaMins(tiemmamaMins);} else {encuesta.setTiemmamaMins(null);}
            if (tieneValor(vecespechodia)) {encuesta.setVecespechodia(vecespechodia);} else {encuesta.setVecespechodia(null);}
            if (tieneValor(vecespechonoche)) {encuesta.setVecespechonoche(vecespechonoche);} else {encuesta.setVecespechonoche(null);}
            if (tieneValor(elmatexccant)) {encuesta.setElmatexccant(elmatexccant);} else {encuesta.setElmatexccant(null);}
            if (tieneValor(ediopechocant)) {encuesta.setEdiopechocant(ediopechocant);} else {encuesta.setEdiopechocant(null);}
            if (tieneValor(ealimcant)) {encuesta.setEalimcant(ealimcant);} else {encuesta.setEalimcant(null);}

            if (tieneValor(motNopechoOtro)) {encuesta.setMotNopechoOtro(motNopechoOtro);} else {encuesta.setMotNopechoOtro(null);}
            if (tieneValor(quienReunionPesoOtro)) {encuesta.setQuienReunionPesoOtro(quienReunionPesoOtro);} else {encuesta.setQuienReunionPesoOtro(null);}

            if (tieneValor(bebeLiq)) {
                String keysbebeLiq = "";
                bebeLiq = bebeLiq.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll(", " , "','");
                List<MessageResource> msbebeliq = sivinAdapter.getMessageResources(MainDBConstants.spanish + " in ('" + bebeLiq + "') and "
                        + MainDBConstants.catRoot + "='CAT_BEBENIN'", null);
                for(MessageResource ms : msbebeliq) {
                    keysbebeLiq += ms.getCatKey() + ",";
                }
                if (!keysbebeLiq.isEmpty())
                    keysbebeLiq = keysbebeLiq.substring(0, keysbebeLiq.length() - 1);
                encuesta.setBebeLiq(keysbebeLiq);
            } else {
            	encuesta.setBebeLiq(null);
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
