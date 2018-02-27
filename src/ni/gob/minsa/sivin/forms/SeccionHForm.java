package ni.gob.minsa.sivin.forms;

import java.util.List;

import android.content.Context;
import ni.gob.minsa.sivin.database.SivinAdapter;
import ni.gob.minsa.sivin.domain.MessageResource;
import ni.gob.minsa.sivin.utils.Constants;
import ni.gob.minsa.sivin.utils.MainDBConstants;
import ni.gob.minsa.sivin.wizard.model.AbstractWizardModel;
import ni.gob.minsa.sivin.wizard.model.Page;
import ni.gob.minsa.sivin.wizard.model.LabelPage;
import ni.gob.minsa.sivin.wizard.model.PageList;
import ni.gob.minsa.sivin.wizard.model.SingleFixedChoicePage;

public class SeccionHForm extends AbstractWizardModel {
	
	int index = 0;
	private SeccionHFormLabels labels;
	private SivinAdapter sivinAdapter;
	
	private String[] fillCatalog(String codigoCatalogo){
        String[] catalogo;
        List<MessageResource> mCatalogo = sivinAdapter.getMessageResources(MainDBConstants.catRoot + "='"+codigoCatalogo+"'", MainDBConstants.order);
        catalogo = new String[mCatalogo.size()];
        index = 0;
        for (MessageResource message: mCatalogo){
            catalogo[index] = message.getSpanish();
            index++;
        }
        return catalogo;
    }

	public SeccionHForm(Context context, String pass) {    	
        super(context,pass);
    }

    @Override
    protected PageList onNewRootPageList() {
    	labels = new SeccionHFormLabels();
    	this.sivinAdapter = new SivinAdapter(mContext,mPass,false,false);
    	sivinAdapter.open();
    	String[] catSN = fillCatalog("CAT_SINO");
    	String[] catFrec = fillCatalog("CAT_FRECCON");
    	sivinAdapter.close();
    	Page labelH = new LabelPage(this, labels.getLabelH() , labels.getLabelHHint(), Constants.WIZARD, true).setRequired(false);
    	Page patConsAzuc = new SingleFixedChoicePage(this,labels.getPatConsAzuc(), "", Constants.WIZARD, true).setChoices(catSN).setRequired(true);
    	Page patConsAzucFrec = new SingleFixedChoicePage(this,labels.getPatConsAzucFrec(), "", Constants.WIZARD, false).setChoices(catFrec).setRequired(true);
    	Page patConsSal = new SingleFixedChoicePage(this,labels.getPatConsSal(), "", Constants.WIZARD, true).setChoices(catSN).setRequired(true);
    	Page patConsSalFrec = new SingleFixedChoicePage(this,labels.getPatConsSalFrec(), "", Constants.WIZARD, false).setChoices(catFrec).setRequired(true);
    	Page patConsArroz = new SingleFixedChoicePage(this,labels.getPatConsArroz(), "", Constants.WIZARD, true).setChoices(catSN).setRequired(true);
    	Page patConsArrozFrec = new SingleFixedChoicePage(this,labels.getPatConsArrozFrec(), "", Constants.WIZARD, false).setChoices(catFrec).setRequired(true);
    	Page patConsAcei = new SingleFixedChoicePage(this,labels.getPatConsAcei(), "", Constants.WIZARD, true).setChoices(catSN).setRequired(true);
    	Page patConsAceiFrec = new SingleFixedChoicePage(this,labels.getPatConsAceiFrec(), "", Constants.WIZARD, false).setChoices(catFrec).setRequired(true);
    	Page patConsFri = new SingleFixedChoicePage(this,labels.getPatConsFri(), "", Constants.WIZARD, true).setChoices(catSN).setRequired(true);
    	Page patConsFriFrec = new SingleFixedChoicePage(this,labels.getPatConsFriFrec(), "", Constants.WIZARD, false).setChoices(catFrec).setRequired(true);
    	Page patConsCebo = new SingleFixedChoicePage(this,labels.getPatConsCebo(), "", Constants.WIZARD, true).setChoices(catSN).setRequired(true);
    	Page patConsCeboFrec = new SingleFixedChoicePage(this,labels.getPatConsCeboFrec(), "", Constants.WIZARD, false).setChoices(catFrec).setRequired(true);
    	Page patConsChi = new SingleFixedChoicePage(this,labels.getPatConsChi(), "", Constants.WIZARD, true).setChoices(catSN).setRequired(true);
    	Page patConsChiFrec = new SingleFixedChoicePage(this,labels.getPatConsChiFrec(), "", Constants.WIZARD, false).setChoices(catFrec).setRequired(true);
    	Page patConsQue = new SingleFixedChoicePage(this,labels.getPatConsQue(), "", Constants.WIZARD, true).setChoices(catSN).setRequired(true);
    	Page patConsQueFrec = new SingleFixedChoicePage(this,labels.getPatConsQueFrec(), "", Constants.WIZARD, false).setChoices(catFrec).setRequired(true);
    	Page patConsCafe = new SingleFixedChoicePage(this,labels.getPatConsCafe(), "", Constants.WIZARD, true).setChoices(catSN).setRequired(true);
    	Page patConsCafeFrec = new SingleFixedChoicePage(this,labels.getPatConsCafeFrec(), "", Constants.WIZARD, false).setChoices(catFrec).setRequired(true);
    	Page patConsTor = new SingleFixedChoicePage(this,labels.getPatConsTor(), "", Constants.WIZARD, true).setChoices(catSN).setRequired(true);
    	Page patConsTorFrec = new SingleFixedChoicePage(this,labels.getPatConsTorFrec(), "", Constants.WIZARD, false).setChoices(catFrec).setRequired(true);
    	Page patConsCar = new SingleFixedChoicePage(this,labels.getPatConsCar(), "", Constants.WIZARD, true).setChoices(catSN).setRequired(true);
    	Page patConsCarFrec = new SingleFixedChoicePage(this,labels.getPatConsCarFrec(), "", Constants.WIZARD, false).setChoices(catFrec).setRequired(true);
    	Page patConsHue = new SingleFixedChoicePage(this,labels.getPatConsHue(), "", Constants.WIZARD, true).setChoices(catSN).setRequired(true);
    	Page patConsHueFrec = new SingleFixedChoicePage(this,labels.getPatConsHueFrec(), "", Constants.WIZARD, false).setChoices(catFrec).setRequired(true);
    	Page patConsPan = new SingleFixedChoicePage(this,labels.getPatConsPan(), "", Constants.WIZARD, true).setChoices(catSN).setRequired(true);
    	Page patConsPanFrec = new SingleFixedChoicePage(this,labels.getPatConsPanFrec(), "", Constants.WIZARD, false).setChoices(catFrec).setRequired(true);
    	Page patConsBan = new SingleFixedChoicePage(this,labels.getPatConsBan(), "", Constants.WIZARD, true).setChoices(catSN).setRequired(true);
    	Page patConsBanFrec = new SingleFixedChoicePage(this,labels.getPatConsBanFrec(), "", Constants.WIZARD, false).setChoices(catFrec).setRequired(true);
    	Page patConsPanDul = new SingleFixedChoicePage(this,labels.getPatConsPanDul(), "", Constants.WIZARD, true).setChoices(catSN).setRequired(true);
    	Page patConsPanDulFrec = new SingleFixedChoicePage(this,labels.getPatConsPanDulFrec(), "", Constants.WIZARD, false).setChoices(catFrec).setRequired(true);
    	Page patConsPla = new SingleFixedChoicePage(this,labels.getPatConsPla(), "", Constants.WIZARD, true).setChoices(catSN).setRequired(true);
    	Page patConsPlaFrec = new SingleFixedChoicePage(this,labels.getPatConsPlaFrec(), "", Constants.WIZARD, false).setChoices(catFrec).setRequired(true);
    	Page patConsPapa = new SingleFixedChoicePage(this,labels.getPatConsPapa(), "", Constants.WIZARD, true).setChoices(catSN).setRequired(true);
    	Page patConsPapaFrec = new SingleFixedChoicePage(this,labels.getPatConsPapaFrec(), "", Constants.WIZARD, false).setChoices(catFrec).setRequired(true);
    	Page patConsLec = new SingleFixedChoicePage(this,labels.getPatConsLec(), "", Constants.WIZARD, true).setChoices(catSN).setRequired(true);
    	Page patConsLecFrec = new SingleFixedChoicePage(this,labels.getPatConsLecFrec(), "", Constants.WIZARD, false).setChoices(catFrec).setRequired(true);
    	Page patConsSalTom = new SingleFixedChoicePage(this,labels.getPatConsSalTom(), "", Constants.WIZARD, true).setChoices(catSN).setRequired(true);
    	Page patConsSalTomFrec = new SingleFixedChoicePage(this,labels.getPatConsSalTomFrec(), "", Constants.WIZARD, false).setChoices(catFrec).setRequired(true);
    	Page patConsGas = new SingleFixedChoicePage(this,labels.getPatConsGas(), "", Constants.WIZARD, true).setChoices(catSN).setRequired(true);
    	Page patConsGasFrec = new SingleFixedChoicePage(this,labels.getPatConsGasFrec(), "", Constants.WIZARD, false).setChoices(catFrec).setRequired(true);
    	Page patConsCarRes = new SingleFixedChoicePage(this,labels.getPatConsCarRes(), "", Constants.WIZARD, true).setChoices(catSN).setRequired(true);
    	Page patConsCarResFrec = new SingleFixedChoicePage(this,labels.getPatConsCarResFrec(), "", Constants.WIZARD, false).setChoices(catFrec).setRequired(true);
    	Page patConsAvena = new SingleFixedChoicePage(this,labels.getPatConsAvena(), "", Constants.WIZARD, true).setChoices(catSN).setRequired(true);
    	Page patConsAvenaFrec = new SingleFixedChoicePage(this,labels.getPatConsAvenaFrec(), "", Constants.WIZARD, false).setChoices(catFrec).setRequired(true);
    	Page patConsNaran = new SingleFixedChoicePage(this,labels.getPatConsNaran(), "", Constants.WIZARD, true).setChoices(catSN).setRequired(true);
    	Page patConsNaranFrec = new SingleFixedChoicePage(this,labels.getPatConsNaranFrec(), "", Constants.WIZARD, false).setChoices(catFrec).setRequired(true);
    	Page patConsPasta = new SingleFixedChoicePage(this,labels.getPatConsPasta(), "", Constants.WIZARD, true).setChoices(catSN).setRequired(true);
    	Page patConsPastaFrec = new SingleFixedChoicePage(this,labels.getPatConsPastaFrec(), "", Constants.WIZARD, false).setChoices(catFrec).setRequired(true);
    	Page patConsAyote = new SingleFixedChoicePage(this,labels.getPatConsAyote(), "", Constants.WIZARD, true).setChoices(catSN).setRequired(true);
    	Page patConsAyoteFrec = new SingleFixedChoicePage(this,labels.getPatConsAyoteFrec(), "", Constants.WIZARD, false).setChoices(catFrec).setRequired(true);
    	Page patConsMagg = new SingleFixedChoicePage(this,labels.getPatConsMagg(), "", Constants.WIZARD, true).setChoices(catSN).setRequired(true);
    	Page patConsMaggFrec = new SingleFixedChoicePage(this,labels.getPatConsMaggFrec(), "", Constants.WIZARD, false).setChoices(catFrec).setRequired(true);
    	Page patConsFrut = new SingleFixedChoicePage(this,labels.getPatConsFrut(), "", Constants.WIZARD, true).setChoices(catSN).setRequired(true);
    	Page patConsFrutFrec = new SingleFixedChoicePage(this,labels.getPatConsFrutFrec(), "", Constants.WIZARD, false).setChoices(catFrec).setRequired(true);
    	Page patConsRaic = new SingleFixedChoicePage(this,labels.getPatConsRaic(), "", Constants.WIZARD, true).setChoices(catSN).setRequired(true);
    	Page patConsRaicFrec = new SingleFixedChoicePage(this,labels.getPatConsRaicFrec(), "", Constants.WIZARD, false).setChoices(catFrec).setRequired(true);
    	Page patConsMenei = new SingleFixedChoicePage(this,labels.getPatConsMenei(), "", Constants.WIZARD, true).setChoices(catSN).setRequired(true);
    	Page patConsMeneiFrec = new SingleFixedChoicePage(this,labels.getPatConsMeneiFrec(), "", Constants.WIZARD, false).setChoices(catFrec).setRequired(true);
    	Page patConsRepollo = new SingleFixedChoicePage(this,labels.getPatConsRepollo(), "", Constants.WIZARD, true).setChoices(catSN).setRequired(true);
    	Page patConsRepolloFrec = new SingleFixedChoicePage(this,labels.getPatConsRepolloFrec(), "", Constants.WIZARD, false).setChoices(catFrec).setRequired(true);
    	Page patConsZana = new SingleFixedChoicePage(this,labels.getPatConsZana(), "", Constants.WIZARD, true).setChoices(catSN).setRequired(true);
    	Page patConsZanaFrec = new SingleFixedChoicePage(this,labels.getPatConsZanaFrec(), "", Constants.WIZARD, false).setChoices(catFrec).setRequired(true);
    	Page patConsPinolillo = new SingleFixedChoicePage(this,labels.getPatConsPinolillo(), "", Constants.WIZARD, true).setChoices(catSN).setRequired(true);
    	Page patConsPinolilloFrec = new SingleFixedChoicePage(this,labels.getPatConsPinolilloFrec(), "", Constants.WIZARD, false).setChoices(catFrec).setRequired(true);
    	Page patConsOVerd = new SingleFixedChoicePage(this,labels.getPatConsOVerd(), "", Constants.WIZARD, true).setChoices(catSN).setRequired(true);
    	Page patConsOVerdFrec = new SingleFixedChoicePage(this,labels.getPatConsOVerdFrec(), "", Constants.WIZARD, false).setChoices(catFrec).setRequired(true);
    	Page patConsPesc = new SingleFixedChoicePage(this,labels.getPatConsPesc(), "", Constants.WIZARD, true).setChoices(catSN).setRequired(true);
    	Page patConsPescFrec = new SingleFixedChoicePage(this,labels.getPatConsPescFrec(), "", Constants.WIZARD, false).setChoices(catFrec).setRequired(true);
    	Page patConsAlimComp = new SingleFixedChoicePage(this,labels.getPatConsAlimComp(), "", Constants.WIZARD, true).setChoices(catSN).setRequired(true);
    	Page patConsAlimCompFrec = new SingleFixedChoicePage(this,labels.getPatConsAlimCompFrec(), "", Constants.WIZARD, false).setChoices(catFrec).setRequired(true);
    	Page patConsLecPol = new SingleFixedChoicePage(this,labels.getPatConsLecPol(), "", Constants.WIZARD, true).setChoices(catSN).setRequired(true);
    	Page patConsLecPolFrec = new SingleFixedChoicePage(this,labels.getPatConsLecPolFrec(), "", Constants.WIZARD, false).setChoices(catFrec).setRequired(true);
    	Page patConsCarCer = new SingleFixedChoicePage(this,labels.getPatConsCarCer(), "", Constants.WIZARD, true).setChoices(catSN).setRequired(true);
    	Page patConsCarCerFrec = new SingleFixedChoicePage(this,labels.getPatConsCarCerFrec(), "", Constants.WIZARD, false).setChoices(catFrec).setRequired(true);
    	Page patConsEmb = new SingleFixedChoicePage(this,labels.getPatConsEmb(), "", Constants.WIZARD, true).setChoices(catSN).setRequired(true);
    	Page patConsEmbFrec = new SingleFixedChoicePage(this,labels.getPatConsEmbFrec(), "", Constants.WIZARD, false).setChoices(catFrec).setRequired(true);
    	Page patConsMar = new SingleFixedChoicePage(this,labels.getPatConsMar(), "", Constants.WIZARD, true).setChoices(catSN).setRequired(true);
    	Page patConsMarFrec = new SingleFixedChoicePage(this,labels.getPatConsMarFrec(), "", Constants.WIZARD, false).setChoices(catFrec).setRequired(true);
    	Page patConsCarCaza = new SingleFixedChoicePage(this,labels.getPatConsCarCaza(), "", Constants.WIZARD, true).setChoices(catSN).setRequired(true);
    	Page patConsCarCazaFrec = new SingleFixedChoicePage(this,labels.getPatConsCarCazaFrec(), "", Constants.WIZARD, false).setChoices(catFrec).setRequired(true);
    	
    	
        return new PageList(labelH, patConsAzuc,patConsAzucFrec,patConsSal,patConsSalFrec,patConsArroz,patConsArrozFrec,
        		patConsAcei,patConsAceiFrec,patConsFri,patConsFriFrec,patConsCebo,patConsCeboFrec,patConsChi,patConsChiFrec,patConsQue,
        		patConsQueFrec,patConsCafe,patConsCafeFrec,patConsTor,patConsTorFrec,patConsCar,patConsCarFrec,patConsHue,patConsHueFrec,patConsPan,
        		patConsPanFrec,patConsBan,patConsBanFrec,patConsPanDul,patConsPanDulFrec,patConsPla,patConsPlaFrec,patConsPapa,patConsPapaFrec,patConsLec,
        		patConsLecFrec,patConsSalTom,patConsSalTomFrec,patConsGas,patConsGasFrec,patConsCarRes,patConsCarResFrec,patConsAvena,patConsAvenaFrec,patConsNaran,
        		patConsNaranFrec,patConsPasta,patConsPastaFrec,patConsAyote,patConsAyoteFrec,patConsMagg,patConsMaggFrec,patConsFrut,patConsFrutFrec,patConsRaic,patConsRaicFrec,
        		patConsMenei,patConsMeneiFrec,patConsRepollo,patConsRepolloFrec,patConsZana,patConsZanaFrec,patConsPinolillo,patConsPinolilloFrec,patConsOVerd,patConsOVerdFrec,
        		patConsPesc,patConsPescFrec,patConsAlimComp,patConsAlimCompFrec,patConsLecPol,patConsLecPolFrec,patConsCarCer,patConsCarCerFrec,patConsEmb,patConsEmbFrec,
        		patConsMar,patConsMarFrec,patConsCarCaza,patConsCarCazaFrec);
    }

	public SeccionHFormLabels getLabels() {
		return labels;
	}

	public void setLabels(SeccionHFormLabels labels) {
		this.labels = labels;
	}
    
}
