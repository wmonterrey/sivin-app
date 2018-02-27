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
import ni.gob.minsa.sivin.wizard.model.NewDatePage;
import ni.gob.minsa.sivin.wizard.model.NumberPage;
import ni.gob.minsa.sivin.wizard.model.Page;
import ni.gob.minsa.sivin.wizard.model.PageList;
import ni.gob.minsa.sivin.wizard.model.SingleFixedChoicePage;
import ni.gob.minsa.sivin.wizard.model.TextPage;

public class SeccionCForm extends AbstractWizardModel {
	
	int index = 0;
	private SeccionCFormLabels labels;
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
	
    public SeccionCForm(Context context, String pass) {    	
        super(context,pass);
    }

    @Override
    protected PageList onNewRootPageList() {
    	labels = new SeccionCFormLabels();
    	this.sivinAdapter = new SivinAdapter(mContext,mPass,false,false);
    	sivinAdapter.open();
    	String[] catSexo = fillCatalog("CAT_SEXO");
    	String[] catCalim = fillCatalog("CAT_CALIM");
    	String[] catConsalim = fillCatalog("CAT_CONSALIM");
    	String[] catCalimenf = fillCatalog("CAT_AENF");
    	String[] catClecheenf = fillCatalog("CAT_LENF");
    	sivinAdapter.close();
    	Page labelC = new LabelPage(this, labels.getLabelC() , labels.getLabelCHint(), Constants.WIZARD, true).setRequired(false);
    	//Page labelC2 = new LabelPage(this, labels.getLabelC2() , labels.getLabelC2Hint(), Constants.WIZARD, true).setRequired(false);
    	Page numNinos = new NumberPage(this,labels.getNumNinos(),"",Constants.WIZARD,true).setRangeValidation(true, 1, 6).setRequired(true);
    	Page n1 = new TextPage(this,labels.getN1(),"",Constants.WIZARD,true).setPatternValidation(true, ".{3,100}").setRequired(true);
    	Page e1 = new NumberPage(this,labels.getE1(),"",Constants.WIZARD,true).setRangeValidation(true, 0, 59).setRequired(true);
    	Page n2 = new TextPage(this,labels.getN2(),"",Constants.WIZARD,false).setPatternValidation(true, ".{3,100}").setRequired(true);
    	Page e2 = new NumberPage(this,labels.getE2(),"",Constants.WIZARD,false).setRangeValidation(true, 0, 59).setRequired(true);
    	Page n3 = new TextPage(this,labels.getN3(),"",Constants.WIZARD,false).setPatternValidation(true, ".{3,100}").setRequired(true);
    	Page e3 = new NumberPage(this,labels.getE3(),"",Constants.WIZARD,false).setRangeValidation(true, 0, 59).setRequired(true);
    	Page n4 = new TextPage(this,labels.getN4(),"",Constants.WIZARD,false).setPatternValidation(true, ".{3,100}").setRequired(true);
    	Page e4 = new NumberPage(this,labels.getE4(),"",Constants.WIZARD,false).setRangeValidation(true, 0, 59).setRequired(true);
    	Page n5 = new TextPage(this,labels.getN5(),"",Constants.WIZARD,false).setPatternValidation(true, ".{3,100}").setRequired(true);
    	Page e5 = new NumberPage(this,labels.getE5(),"",Constants.WIZARD,false).setRangeValidation(true, 0, 59).setRequired(true);
    	Page n6 = new TextPage(this,labels.getN6(),"",Constants.WIZARD,false).setPatternValidation(true, ".{3,100}").setRequired(true);
    	Page e6 = new NumberPage(this,labels.getE6(),"",Constants.WIZARD,false).setRangeValidation(true, 0, 59).setRequired(true);
    	Page nselec = new TextPage(this, labels.getNselec() , "", Constants.WIZARD, true).setPatternValidation(true, ".{1,1}").setRequired(true);
    	Page nomselec = new TextPage(this, labels.getNomselec() , "", Constants.WIZARD, true).setPatternValidation(true, ".{3,100}").setRequired(true);
    	DateMidnight dmDesde = DateMidnight.parse("01/01/2012", DateTimeFormat.forPattern("dd/MM/yyyy"));
		DateMidnight dmHasta = new DateMidnight(new Date().getTime());
    	Page fnacselec = new NewDatePage(this,labels.getFnacselec(), "", Constants.WIZARD, true).setRangeValidation(true, dmDesde, dmHasta).setRequired(true);
    	Page eselect = new NumberPage(this,labels.getEselect(),"",Constants.WIZARD,true).setRangeValidation(true, 0, 59).setRequired(true);
    	Page sexselec = new SingleFixedChoicePage(this,labels.getSexselec(), "", Constants.WIZARD, true).setChoices(catSexo).setRequired(true);
    	Page calim = new SingleFixedChoicePage(this,labels.getCalim(), labels.getCalimHint(), Constants.WIZARD, true).setChoices(catCalim).setRequired(true);
    	Page vcome = new NumberPage(this,labels.getVcome(),"",Constants.WIZARD,false).setRangeValidation(true, 1, 10).setRequired(true);
    	Page consalim = new SingleFixedChoicePage(this,labels.getConsalim(), labels.getConsalimHint(), Constants.WIZARD, false).setChoices(catConsalim).setRequired(true);
    	Page calimenf = new SingleFixedChoicePage(this,labels.getCalimenf(), labels.getCalimenfHint(), Constants.WIZARD, false).setChoices(catCalimenf).setRequired(true);
    	Page clecheenf = new SingleFixedChoicePage(this,labels.getClecheenf(), labels.getClecheenfHint(), Constants.WIZARD, true).setChoices(catClecheenf).setRequired(true);
    	
        return new PageList(labelC,numNinos,n1,e1,n2,e2,n3,e3,n4,e4,n5,e5,n6,e6,nselec,nomselec,fnacselec,eselect,sexselec,calim,vcome,consalim,calimenf,clecheenf);
    }

	public SeccionCFormLabels getLabels() {
		return labels;
	}

	public void setLabels(SeccionCFormLabels labels) {
		this.labels = labels;
	}
    
}
