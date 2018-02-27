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

public class SeccionDForm extends AbstractWizardModel {
	
	int index = 0;
	private SeccionDFormLabels labels;
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
	
    public SeccionDForm(Context context, String pass) {    	
        super(context,pass);
    }

    @Override
    protected PageList onNewRootPageList() {
    	labels = new SeccionDFormLabels();
    	this.sivinAdapter = new SivinAdapter(mContext,mPass,false,false);
    	sivinAdapter.open();
    	String[] catSNNS = fillCatalog("CAT_SINONR");
    	String[] catGotas = fillCatalog("CAT_GOTHIE");
    	String[] catDondeHierro = fillCatalog("CAT_HIERROOBTNIN");
    	String[] catSNNT = fillCatalog("CAT_SINONT");
    	String[] catTiemHierroUnd = fillCatalog("CAT_HIERROTIEMP");
    	String[] catTiemVitaUnd = fillCatalog("CAT_VITATIEMP");
    	String[] catDondeVita = fillCatalog("CAT_DONDEA");
    	String[] catDondeMic = fillCatalog("CAT_DONDEMN");
    	String[] catAp = fillCatalog("CAT_MEDPAR");
    	String[] catDAp = fillCatalog("CAT_DONMEDPAR");
    	String[] catEp = fillCatalog("CAT_EVITARPAR");
    	sivinAdapter.close();
    	Page labelD = new LabelPage(this, labels.getLabelD() , labels.getLabelDHint(), Constants.WIZARD, true).setRequired(false);
    	Page hierron = new SingleFixedChoicePage(this,labels.getHierron(), labels.getHierronHint(), Constants.WIZARD, true).setChoices(catSNNS).setRequired(true);
    	Page thierround = new SingleFixedChoicePage(this,labels.getThierround(), labels.getThierroundHint(), Constants.WIZARD, false).setChoices(catTiemHierroUnd).setRequired(true);
    	Page thierrocant = new NumberPage(this,labels.getThierrocant(),labels.getThierrocantHint(),Constants.WIZARD, false).setRangeValidation(true, 1, 12).setRequired(true);
    	Page fhierro = new NumberPage(this,labels.getFhierro(),labels.getFhierroHint(),Constants.WIZARD, false).setRangeValidation(true, 1, 88).setRequired(true);
    	Page ghierro = new SingleFixedChoicePage(this,labels.getGhierro(), labels.getGhierroHint(), Constants.WIZARD, false).setChoices(catGotas).setRequired(true);
    	Page donhierro = new MultipleFixedChoicePage(this,labels.getDonhierro(), labels.getDonhierroHint(), Constants.WIZARD, false).setChoices(catDondeHierro).setRequired(true);
    	Page donhierrobesp = new TextPage(this,labels.getDonhierrobesp(),labels.getDonhierrobespHint(),Constants.WIZARD, false).setPatternValidation(true, ".{1,255}").setRequired(true);
    	Page donhierrofesp = new TextPage(this,labels.getDonhierrofesp(),labels.getDonhierrofespHint(),Constants.WIZARD, false).setPatternValidation(true, ".{1,255}").setRequired(true);
    	Page fuhierro = new SingleFixedChoicePage(this,labels.getFuhierro(), labels.getFuhierroHint(), Constants.WIZARD, false).setChoices(catSNNT).setRequired(true);
    	DateMidnight dmDesde = DateMidnight.parse("01/01/2012", DateTimeFormat.forPattern("dd/MM/yyyy"));
		DateMidnight dmHasta = new DateMidnight(new Date().getTime());
    	Page fuhierror = new NewDatePage(this,labels.getFuhierror(), labels.getFuhierrorHint(), Constants.WIZARD, false).setRangeValidation(true, dmDesde, dmHasta).setRequired(true);
    	Page fauhierror = new NewDatePage(this,labels.getFauhierror(), labels.getFauhierrorHint(), Constants.WIZARD, false).setRangeValidation(true, dmDesde, dmHasta).setRequired(false);
    	Page nvita = new SingleFixedChoicePage(this,labels.getNvita(), labels.getNvitaHint(), Constants.WIZARD, true).setChoices(catSNNS).setRequired(true);
    	Page ncvita = new NumberPage(this,labels.getNcvita(),labels.getNcvitaHint(),Constants.WIZARD, false).setRangeValidation(true, 1, 5).setRequired(true);
    	Page tvitaund = new SingleFixedChoicePage(this,labels.getTvitaund(), labels.getTvitaundHint(), Constants.WIZARD, false).setChoices(catTiemVitaUnd).setRequired(true);
    	Page tvitacant = new NumberPage(this,labels.getTvitacant(),labels.getTvitacantHint(),Constants.WIZARD, false).setRangeValidation(true, 1, 12).setRequired(true);
    	Page ndvita = new SingleFixedChoicePage(this,labels.getNdvita(), labels.getNdvitaHint(), Constants.WIZARD, false).setChoices(catDondeVita).setRequired(true);
    	Page ndvitao = new TextPage(this,labels.getNdvitao(),labels.getNdvitaoHint(),Constants.WIZARD, false).setPatternValidation(true, ".{1,155}").setRequired(true);
    	Page tdvita = new SingleFixedChoicePage(this,labels.getTdvita(), labels.getTdvitaHint(), Constants.WIZARD, false).setChoices(catSNNT).setRequired(true);
    	Page fuvita = new NewDatePage(this,labels.getFuvita(), labels.getFuvitaHint(), Constants.WIZARD, false).setRangeValidation(true, dmDesde, dmHasta).setRequired(true);
    	Page fauvita = new NewDatePage(this,labels.getFauvita(), labels.getFauvitaHint(), Constants.WIZARD, false).setRangeValidation(true, dmDesde, dmHasta).setRequired(false);
    	Page ncnut = new SingleFixedChoicePage(this,labels.getNcnut(), labels.getNcnutHint(), Constants.WIZARD, true).setChoices(catSNNS).setRequired(true);
    	Page ncnutund = new SingleFixedChoicePage(this,labels.getNcnutund(), labels.getNcnutundHint(), Constants.WIZARD, false).setChoices(catTiemHierroUnd).setRequired(true);
    	Page ncnutcant = new NumberPage(this,labels.getNcnutcant(),labels.getNcnutcantHint(),Constants.WIZARD, false).setRangeValidation(true, 1, 12).setRequired(true);
    	Page doncnut = new MultipleFixedChoicePage(this,labels.getDoncnut(), labels.getDoncnutHint(), Constants.WIZARD, false).setChoices(catDondeMic).setRequired(true);
    	Page doncnutfotro = new TextPage(this,labels.getDoncnutfotro(),labels.getDoncnutfotroHint(),Constants.WIZARD, false).setPatternValidation(true, ".{1,155}").setRequired(true);
    	Page parasit = new SingleFixedChoicePage(this,labels.getParasit(), labels.getParasitHint(), Constants.WIZARD, true).setChoices(catSNNS).setRequired(true);
    	Page cvparas = new NumberPage(this,labels.getCvparas(),labels.getCvparasHint(),Constants.WIZARD, false).setRangeValidation(true, 1, 12).setRequired(true);
    	Page mParasitario = new MultipleFixedChoicePage(this,labels.getmParasitario(), labels.getmParasitarioHint(), Constants.WIZARD, false).setChoices(catAp).setRequired(true);
    	Page otroMpEsp = new TextPage(this,labels.getOtroMpEsp(),labels.getOtroMpEspHint(),Constants.WIZARD, false).setPatternValidation(true, ".{1,255}").setRequired(true);
    	Page donMp = new MultipleFixedChoicePage(this,labels.getDonMp(), labels.getDonMpHint(), Constants.WIZARD, false).setChoices(catDAp).setRequired(true);
    	Page otroDonMp = new TextPage(this,labels.getOtroDonMp(),labels.getOtroMpEspHint(),Constants.WIZARD, false).setPatternValidation(true, ".{1,255}").setRequired(true);
    	Page evitarParasito = new MultipleFixedChoicePage(this,labels.getEvitarParasito(), labels.getEvitarParasitoHint(), Constants.WIZARD, true).setChoices(catEp).setRequired(true);
    	Page oEvitarParasito = new TextPage(this,labels.getoEvitarParasito(),labels.getoEvitarParasitoHint(),Constants.WIZARD, false).setPatternValidation(true, ".{1,255}").setRequired(true);
    	
        return new PageList(labelD,hierron,thierround,thierrocant,fhierro,ghierro,donhierro,donhierrobesp,donhierrofesp,fuhierro,fuhierror,fauhierror
        						,nvita,ncvita,tvitaund,tvitacant,ndvita,ndvitao,tdvita,fuvita,fauvita,ncnut,ncnutund,ncnutcant,doncnut,doncnutfotro
        						,parasit,cvparas,mParasitario,otroMpEsp,donMp,otroDonMp,evitarParasito,oEvitarParasito);
    }

	public SeccionDFormLabels getLabels() {
		return labels;
	}

	public void setLabels(SeccionDFormLabels labels) {
		this.labels = labels;
	}
    
}
