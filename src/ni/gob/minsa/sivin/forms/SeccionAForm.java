package ni.gob.minsa.sivin.forms;

import java.util.List;

import android.content.Context;
import ni.gob.minsa.sivin.database.SivinAdapter;
import ni.gob.minsa.sivin.domain.MessageResource;
import ni.gob.minsa.sivin.utils.Constants;
import ni.gob.minsa.sivin.utils.MainDBConstants;
import ni.gob.minsa.sivin.wizard.model.AbstractWizardModel;
import ni.gob.minsa.sivin.wizard.model.MultipleFixedChoicePage;
import ni.gob.minsa.sivin.wizard.model.NumberPage;
import ni.gob.minsa.sivin.wizard.model.Page;
import ni.gob.minsa.sivin.wizard.model.PageList;
import ni.gob.minsa.sivin.wizard.model.SingleFixedChoicePage;
import ni.gob.minsa.sivin.wizard.model.TextPage;

public class SeccionAForm extends AbstractWizardModel {
	
	int index = 0;
	private SeccionAFormLabels labels;
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
	
    public SeccionAForm(Context context, String pass) {    	
        super(context,pass);
    }

    @Override
    protected PageList onNewRootPageList() {
    	labels = new SeccionAFormLabels();
    	this.sivinAdapter = new SivinAdapter(mContext,mPass,false,false);
    	sivinAdapter.open();
    	String[] catAgua = fillCatalog("CAT_AGUA");
    	String[] catNecesidades = fillCatalog("CAT_NECESIDADES");
    	String[] catCocina = fillCatalog("CAT_COCINA");
    	String[] catArticulos = fillCatalog("CAT_ARTICULOS");
    	sivinAdapter.close();
    	Page agua = new SingleFixedChoicePage(this,labels.getAgua(), labels.getAguaHint(), Constants.WIZARD, true).setChoices(catAgua).setRequired(true);
    	Page oagua = new TextPage(this,labels.getOagua(),labels.getOaguaHint(),Constants.WIZARD,false).setPatternValidation(true, ".{1,255}").setRequired(true);
    	Page cuartos = new NumberPage(this,labels.getCuartos(),labels.getCuartosHint(),Constants.WIZARD,true).setRangeValidation(true, 1, 10).setRequired(true);
    	Page lugNecesidades = new SingleFixedChoicePage(this,labels.getLugNecesidades(), labels.getLugNecesidadesHint(), Constants.WIZARD, true).setChoices(catNecesidades).setRequired(true);
    	Page olugNecesidades = new TextPage(this,labels.getOlugNecesidades(),labels.getOlugNecesidadesHint(),Constants.WIZARD,false).setPatternValidation(true, ".{1,255}").setRequired(true);
    	Page usaCocinar = new SingleFixedChoicePage(this,labels.getUsaCocinar(), labels.getUsaCocinarHint(), Constants.WIZARD, true).setChoices(catCocina).setRequired(true);
    	Page ousaCocinar = new TextPage(this,labels.getOusaCocinar(),labels.getOusaCocinarHint(),Constants.WIZARD,false).setPatternValidation(true, ".{1,255}").setRequired(true);
    	Page articulos = new MultipleFixedChoicePage(this,labels.getArticulos(), labels.getArticulosHint(), Constants.WIZARD, true).setChoices(catArticulos).setRequired(true);
    	Page oarticulos = new TextPage(this,labels.getOarticulos(),labels.getOarticulosHint(),Constants.WIZARD,false).setPatternValidation(true, ".{1,255}").setRequired(true);
        return new PageList(agua,oagua,cuartos,lugNecesidades,olugNecesidades,usaCocinar,ousaCocinar,articulos,oarticulos);
    }

	public SeccionAFormLabels getLabels() {
		return labels;
	}

	public void setLabels(SeccionAFormLabels labels) {
		this.labels = labels;
	}
    
}
