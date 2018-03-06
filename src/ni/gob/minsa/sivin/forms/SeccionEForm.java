package ni.gob.minsa.sivin.forms;

import java.util.Date;
import java.util.List;

import org.joda.time.DateMidnight;
import org.joda.time.format.DateTimeFormat;

import android.content.Context;
import ni.gob.minsa.sivin.database.SivinAdapter;
import ni.gob.minsa.sivin.domain.MessageResource;
import ni.gob.minsa.sivin.utils.Constants;
import ni.gob.minsa.sivin.utils.MainDBConstants;
import ni.gob.minsa.sivin.wizard.model.AbstractWizardModel;
import ni.gob.minsa.sivin.wizard.model.LabelPage;
import ni.gob.minsa.sivin.wizard.model.MultipleFixedChoicePage;
import ni.gob.minsa.sivin.wizard.model.NewDatePage;
import ni.gob.minsa.sivin.wizard.model.NumberPage;
import ni.gob.minsa.sivin.wizard.model.Page;
import ni.gob.minsa.sivin.wizard.model.PageList;
import ni.gob.minsa.sivin.wizard.model.SingleFixedChoicePage;
import ni.gob.minsa.sivin.wizard.model.TextPage;

public class SeccionEForm extends AbstractWizardModel {
	
	int index = 0;
	private SeccionEFormLabels labels;
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
	
    public SeccionEForm(Context context, String pass) {    	
        super(context,pass);
    }

    @Override
    protected PageList onNewRootPageList() {
    	labels = new SeccionEFormLabels();
    	this.sivinAdapter = new SivinAdapter(mContext,mPass,false,false);
    	sivinAdapter.open();
    	String[] catSexo = fillCatalog("CAT_SEXO");
    	String[] catSNNR = fillCatalog("CAT_SINONR");
    	String[] catSN = fillCatalog("CAT_SINO");
    	String[] catSNNA = fillCatalog("CAT_SINONA");
    	String[] catNoPecho = fillCatalog("CAT_NODIOPECHO");
    	String[] catTPechoNac = fillCatalog("CAT_TNACPECHO");
    	String[] catTPecho = fillCatalog("CAT_TTOMAPECHO");
    	String[] catSNAV = fillCatalog("CAT_SINOAV");
    	String[] catTLact = fillCatalog("CAT_LACTTIEMP");
    	String[] catTLact2 = fillCatalog("CAT_HIERROTIEMP");
    	String[] catBNin = fillCatalog("CAT_BEBENIN");
    	String[] catRPeso = fillCatalog("CAT_QREUPESO");
    	
    	
    	sivinAdapter.close();
    	Page labelE = new LabelPage(this, labels.getLabelE() , labels.getLabelEHint(), Constants.WIZARD, true).setRequired(false);
    	Page nlactmat = new SingleFixedChoicePage(this,labels.getNlactmat(), labels.getNlactmatHint(), Constants.WIZARD, true).setChoices(catSNNA).setRequired(true);
    	Page sexlmat = new SingleFixedChoicePage(this,labels.getSexlmat(), labels.getSexlmatHint(), Constants.WIZARD, false).setChoices(catSexo).setRequired(true);
    	DateMidnight dmDesde = DateMidnight.parse("01/01/2016", DateTimeFormat.forPattern("dd/MM/yyyy"));
		DateMidnight dmHasta = new DateMidnight(new Date().getTime());
    	Page fnaclmat = new NewDatePage(this,labels.getFnaclmat(), labels.getFnaclmatHint(), Constants.WIZARD, false).setRangeValidation(true, dmDesde, dmHasta).setRequired(true);
    	Page emeseslmat = new NumberPage(this,labels.getEmeseslmat(),labels.getEmeseslmatHint(),Constants.WIZARD, false).setRangeValidation(true, 0, 24).setRequired(true);
    	Page pecho = new SingleFixedChoicePage(this,labels.getPecho(), labels.getPechoHint(), Constants.WIZARD, false).setChoices(catSN).setRequired(true);
    	Page motNopecho = new SingleFixedChoicePage(this,labels.getMotNopecho(), labels.getMotNopechoHint(), Constants.WIZARD, false).setChoices(catNoPecho).setRequired(true);
    	Page motNopechoOtro = new TextPage(this,labels.getMotNopechoOtro(),labels.getMotNopechoOtroHint(),Constants.WIZARD, false).setPatternValidation(true, ".{1,255}").setRequired(true);
    	Page dapecho = new SingleFixedChoicePage(this,labels.getDapecho(), labels.getDapechoHint(), Constants.WIZARD, false).setChoices(catSN).setRequired(true);
    	Page tiempecho = new SingleFixedChoicePage(this,labels.getTiempecho(), labels.getTiempechoHint(), Constants.WIZARD, false).setChoices(catTPechoNac).setRequired(true);
    	Page tiemmama = new SingleFixedChoicePage(this,labels.getTiemmama(), labels.getTiemmamaHint(), Constants.WIZARD, false).setChoices(catTPecho).setRequired(true);
    	Page tiemmamaMins = new NumberPage(this,labels.getTiemmamaMins(),labels.getTiemmamaMinsHint(),Constants.WIZARD, false).setRangeValidation(true, 1, 60).setRequired(true);
    	Page dospechos = new SingleFixedChoicePage(this,labels.getDospechos(), labels.getDospechosHint(), Constants.WIZARD, false).setChoices(catSNAV).setRequired(true);    	    	
    	Page vecespechodia = new NumberPage(this,labels.getVecespechodia(),labels.getVecespechodiaHint(),Constants.WIZARD, false).setRangeValidation(true, 1, 10).setRequired(true);
    	Page vecespechonoche = new NumberPage(this,labels.getVecespechonoche(),labels.getVecespechonocheHint(),Constants.WIZARD, false).setRangeValidation(true, 1, 10).setRequired(true);
    	Page elmatexcund = new SingleFixedChoicePage(this,labels.getElmatexcund(), labels.getElmatexcundHint(), Constants.WIZARD, false).setChoices(catTLact).setRequired(true);
    	Page elmatexccant = new NumberPage(this,labels.getElmatexccant(),labels.getElmatexccantHint(),Constants.WIZARD, false).setRangeValidation(true, 1, 12).setRequired(true);
    	Page ediopechound = new SingleFixedChoicePage(this,labels.getEdiopechound(), labels.getEdiopechoundHint(), Constants.WIZARD, false).setChoices(catTLact2).setRequired(true);
    	Page ediopechocant = new NumberPage(this,labels.getEdiopechocant(),labels.getEdiopechocantHint(),Constants.WIZARD, false).setRangeValidation(true, 1, 12).setRequired(true);
    	Page combeb = new SingleFixedChoicePage(this,labels.getCombeb(), labels.getCombebHint(), Constants.WIZARD, false).setChoices(catSNNR).setRequired(true);
    	Page ealimund = new SingleFixedChoicePage(this,labels.getEalimund(), labels.getEalimundHint(), Constants.WIZARD, false).setChoices(catTLact).setRequired(true);
    	Page ealimcant = new NumberPage(this,labels.getEalimcant(),labels.getEalimcantHint(),Constants.WIZARD, false).setRangeValidation(true, 1, 12).setRequired(true);
    	Page bebeLiq = new MultipleFixedChoicePage(this,labels.getBebeLiq(), labels.getBebeLiqHint(), Constants.WIZARD, false).setChoices(catBNin).setRequired(true);
    	Page reunionPeso = new SingleFixedChoicePage(this,labels.getReunionPeso(), labels.getReunionPesoHint(), Constants.WIZARD, false).setChoices(catSNNR).setRequired(true);
    	Page quienReunionPeso = new SingleFixedChoicePage(this,labels.getQuienReunionPeso(), labels.getQuienReunionPesoHint(), Constants.WIZARD, false).setChoices(catRPeso).setRequired(true);
    	Page quienReunionPesoOtro = new TextPage(this,labels.getQuienReunionPesoOtro(),labels.getQuienReunionPesoOtroHint(),Constants.WIZARD, false).setPatternValidation(true, ".{1,255}").setRequired(true);
    	Page assitioReunionPeso = new SingleFixedChoicePage(this,labels.getAssitioReunionPeso(), labels.getAssitioReunionPesoHint(), Constants.WIZARD, false).setChoices(catSNNR).setRequired(true);
    	
    	return new PageList(labelE,nlactmat,sexlmat,fnaclmat,emeseslmat,pecho,motNopecho,motNopechoOtro,dapecho,tiempecho,
    			tiemmama,tiemmamaMins,dospechos,vecespechodia,vecespechonoche,elmatexcund,elmatexccant,ediopechound,ediopechocant,combeb,ealimund,ealimcant,bebeLiq,reunionPeso,quienReunionPeso,quienReunionPesoOtro,assitioReunionPeso);
    }

	public SeccionEFormLabels getLabels() {
		return labels;
	}

	public void setLabels(SeccionEFormLabels labels) {
		this.labels = labels;
	}
    
}
