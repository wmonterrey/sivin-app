package ni.gob.minsa.sivin.forms;

import java.util.List;

import android.content.Context;
import ni.gob.minsa.sivin.database.SivinAdapter;
import ni.gob.minsa.sivin.domain.MessageResource;
import ni.gob.minsa.sivin.utils.Constants;
import ni.gob.minsa.sivin.utils.MainDBConstants;
import ni.gob.minsa.sivin.wizard.model.AbstractWizardModel;
import ni.gob.minsa.sivin.wizard.model.BarcodePage;
import ni.gob.minsa.sivin.wizard.model.Page;
import ni.gob.minsa.sivin.wizard.model.LabelPage;
import ni.gob.minsa.sivin.wizard.model.NumberPage;
import ni.gob.minsa.sivin.wizard.model.PageList;
import ni.gob.minsa.sivin.wizard.model.SingleFixedChoicePage;
import ni.gob.minsa.sivin.wizard.model.TextPage;

public class SeccionGForm extends AbstractWizardModel {
	
	int index = 0;
	private SeccionGFormLabels labels;
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

	public SeccionGForm(Context context, String pass) {    	
        super(context,pass);
    }

    @Override
    protected PageList onNewRootPageList() {
    	labels = new SeccionGFormLabels();
    	this.sivinAdapter = new SivinAdapter(mContext,mPass,false,false);
    	sivinAdapter.open();
    	String[] catSN = fillCatalog("CAT_SINO");
    	String[] catSNNA = fillCatalog("CAT_SINONA");
    	sivinAdapter.close();
    	Page labelG = new LabelPage(this, labels.getLabelG() , labels.getLabelGHint(), Constants.WIZARD, true).setRequired(false);
    	Page msEnt = new SingleFixedChoicePage(this,labels.getMsEnt(), "", Constants.WIZARD, true).setChoices(catSNNA).setRequired(true);
    	Page codMsEnt = new TextPage(this,labels.getCodMsEnt(),"",Constants.WIZARD,false).setPatternValidation(true, ".{11,50}").setRequired(true);
    	Page codMsEntBc = new BarcodePage(this,labels.getCodMsEntBc(),"",Constants.WIZARD,false).setPatternValidation(true, ".{11,50}").setRequired(true);
    	Page hbEnt = new NumberPage(this,labels.getHbEnt(),"",Constants.WIZARD,true).setRequired(true);
    	Page msNin = new SingleFixedChoicePage(this,labels.getMsNin(), "", Constants.WIZARD, true).setChoices(catSN).setRequired(true);
    	Page codMsNin = new TextPage(this,labels.getCodMsNin(),"",Constants.WIZARD,false).setPatternValidation(true, ".{11,50}").setRequired(true);
    	Page codMsNinBc = new BarcodePage(this,labels.getCodMsNinBc(),"",Constants.WIZARD,false).setPatternValidation(true, ".{11,50}").setRequired(true);
    	Page hbNin = new NumberPage(this,labels.getHbNin(),"",Constants.WIZARD,true).setRequired(true);
    	Page moEnt = new SingleFixedChoicePage(this,labels.getMoEnt(), "", Constants.WIZARD, true).setChoices(catSNNA).setRequired(true);
    	Page codMoEnt = new TextPage(this,labels.getCodMoEnt(),"",Constants.WIZARD,false).setPatternValidation(true, ".{11,50}").setRequired(true);
    	Page codMoEntBc = new BarcodePage(this,labels.getCodMoEntBc(),"",Constants.WIZARD,false).setPatternValidation(true, ".{11,50}").setRequired(true);
    	Page pan = new TextPage(this,labels.getPan(),"",Constants.WIZARD,true).setPatternValidation(true, ".{0,100}").setRequired(false);
    	Page sal = new SingleFixedChoicePage(this,labels.getSal(), "", Constants.WIZARD, true).setChoices(catSN).setRequired(true);
    	Page marcaSal = new TextPage(this,labels.getMarcaSal(),"",Constants.WIZARD,false).setPatternValidation(true, ".{1,100}").setRequired(true);
    	Page azucar = new SingleFixedChoicePage(this,labels.getAzucar(), "", Constants.WIZARD, true).setChoices(catSN).setRequired(true);
    	Page marcaAzucar = new TextPage(this,labels.getMarcaAzucar(),"",Constants.WIZARD,false).setPatternValidation(true, ".{1,100}").setRequired(true);
    	
        return new PageList(labelG,msEnt,codMsEnt,codMsEntBc,hbEnt,msNin,codMsNin,codMsNinBc,hbNin,
        		moEnt,codMoEnt,codMoEntBc,pan,sal,marcaSal,azucar,marcaAzucar);
    }

	public SeccionGFormLabels getLabels() {
		return labels;
	}

	public void setLabels(SeccionGFormLabels labels) {
		this.labels = labels;
	}
    
}
