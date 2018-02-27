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
import ni.gob.minsa.sivin.wizard.model.NumberPage;
import ni.gob.minsa.sivin.wizard.model.PageList;
import ni.gob.minsa.sivin.wizard.model.SingleFixedChoicePage;

public class SeccionFForm extends AbstractWizardModel {
	
	int index = 0;
	private SeccionFFormLabels labels;
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

	public SeccionFForm(Context context, String pass) {    	
        super(context,pass);
    }

    @Override
    protected PageList onNewRootPageList() {
    	labels = new SeccionFFormLabels();
    	this.sivinAdapter = new SivinAdapter(mContext,mPass,false,false);
    	sivinAdapter.open();
    	String[] catSN = fillCatalog("CAT_SINO");
    	String[] catSNNA = fillCatalog("CAT_SINONA");
    	sivinAdapter.close();
    	Page labelF = new LabelPage(this, labels.getLabelF() , labels.getLabelFHint(), Constants.WIZARD, true).setRequired(false);
    	Page pesoTallaEnt = new SingleFixedChoicePage(this,labels.getPesoTallaEnt(), "", Constants.WIZARD, true).setChoices(catSNNA).setRequired(true);
    	Page pesoEnt1 = new NumberPage(this,labels.getPesoEnt1(),"",Constants.WIZARD,false).setRangeValidation(true, 36, 150).setRequired(true);
    	Page pesoEnt2 = new NumberPage(this,labels.getPesoEnt2(),"",Constants.WIZARD,false).setRangeValidation(true, 36, 150).setRequired(true);
    	Page tallaEnt1 = new NumberPage(this,labels.getTallaEnt1(),"",Constants.WIZARD,false).setRangeValidation(true, 120, 180).setRequired(true);
    	Page tallaEnt2 = new NumberPage(this,labels.getTallaEnt2(),"",Constants.WIZARD,false).setRangeValidation(true, 120, 180).setRequired(true);
    	Page pesoTallaNin = new SingleFixedChoicePage(this,labels.getPesoTallaNin(), labels.getPesoTallaNinHint(), Constants.WIZARD, true).setChoices(catSN).setRequired(true);
    	Page pesoNin1 = new NumberPage(this,labels.getPesoNin1(),"",Constants.WIZARD,false).setRangeValidation(true, 2, 45).setRequired(true);
    	Page pesoNin2 = new NumberPage(this,labels.getPesoNin2(),"",Constants.WIZARD,false).setRangeValidation(true, 2, 45).setRequired(true);
    	Page longNin1 = new NumberPage(this,labels.getLongNin1(),"",Constants.WIZARD,false).setRangeValidation(true, 36, 90).setRequired(true);
    	Page longNin2 = new NumberPage(this,labels.getLongNin2(),"",Constants.WIZARD,false).setRangeValidation(true, 36, 90).setRequired(true);
    	Page tallaNin1 = new NumberPage(this,labels.getTallaNin1(),"",Constants.WIZARD,false).setRangeValidation(true, 70, 130).setRequired(true);
    	Page tallaNin2 = new NumberPage(this,labels.getTallaNin2(),"",Constants.WIZARD,false).setRangeValidation(true, 70, 130).setRequired(true);
        return new PageList(labelF,pesoTallaEnt,pesoEnt1,pesoEnt2,tallaEnt1,tallaEnt2,pesoTallaNin,pesoNin1,pesoNin2,longNin1,longNin2,tallaNin1,tallaNin2);
    }

	public SeccionFFormLabels getLabels() {
		return labels;
	}

	public void setLabels(SeccionFFormLabels labels) {
		this.labels = labels;
	}
    
}
