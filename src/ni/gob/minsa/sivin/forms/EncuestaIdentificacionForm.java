package ni.gob.minsa.sivin.forms;

import java.util.Date;
import java.util.List;

import org.joda.time.DateMidnight;
import android.content.Context;
import ni.gob.minsa.sivin.database.SivinAdapter;
import ni.gob.minsa.sivin.domain.Encuestador;
import ni.gob.minsa.sivin.domain.MessageResource;
import ni.gob.minsa.sivin.domain.Supervisor;
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

public class EncuestaIdentificacionForm extends AbstractWizardModel {
	
	int index = 0;
	private EncuestaIdentificacionFormLabels labels;
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
	
    public EncuestaIdentificacionForm(Context context, String pass) {    	
        super(context,pass);
    }

    @Override
    protected PageList onNewRootPageList() {
    	labels = new EncuestaIdentificacionFormLabels();
    	this.sivinAdapter = new SivinAdapter(mContext,mPass,false,false);
    	sivinAdapter.open();
    	String[] catSexo = fillCatalog("CAT_SEXO");
    	List<Encuestador> mEncuestador = sivinAdapter.getEncuestadores(null, MainDBConstants.nombre);
    	String[] catEncuestadores = new String[mEncuestador.size()];
    	index = 0;
        for (Encuestador encuestador: mEncuestador){
        	catEncuestadores[index] = encuestador.getNombre();
            index++;
        }
        List<Supervisor> mSupervisor = sivinAdapter.getSupervisores(null, MainDBConstants.nombre);
        String[] catSupervisores = new String[mSupervisor.size()];
        index = 0;
        for (Supervisor supervisor: mSupervisor){
        	catSupervisores[index] = supervisor.getNombre();
            index++;
        }
    	sivinAdapter.close();
    	Page introMessage = new LabelPage(this, labels.getIntroMessage() , labels.getIntroMessageHint(), Constants.WIZARD, true).setRequired(false);
		DateMidnight dmDesde = new DateMidnight(new Date().getTime());
		DateMidnight dmHasta = new DateMidnight(new Date().getTime());
    	Page fechaEntrevista = new NewDatePage(this,labels.getFechaEntrevista(), labels.getFechaEntrevistaHint(), Constants.WIZARD, true).setRangeValidation(true, dmDesde, dmHasta).setRequired(true);
    	Page encNum = new SingleFixedChoicePage(this,labels.getEncNum(), "", Constants.WIZARD, true).setRequired(true);
    	Page encuestador = new SingleFixedChoicePage(this,labels.getEncuestador(), labels.getEncuestadorHint(), Constants.WIZARD, true).setChoices(catEncuestadores).setRequired(true);
    	//Page supervisor = new SingleFixedChoicePage(this,labels.getSupervisor(), labels.getSupervisorHint(), Constants.WIZARD, true).setChoices(catSupervisores).setRequired(true);
    	Page jefeFamilia = new TextPage(this,labels.getJefeFamilia(),labels.getJefeFamiliaHint(),Constants.WIZARD,true).setPatternValidation(true, ".{6,250}").setRequired(true);
    	Page sexJefeFamilia = new SingleFixedChoicePage(this,labels.getSexJefeFamilia(), labels.getSexJefeFamiliaHint(), Constants.WIZARD, true).setChoices(catSexo).setRequired(true);
    	Page numPersonas = new NumberPage(this,labels.getNumPersonas(),labels.getNumPersonasHint(),Constants.WIZARD,true).setRangeValidation(true, 1, 30).setRequired(true);
        return new PageList(introMessage,fechaEntrevista,encNum,encuestador,jefeFamilia,sexJefeFamilia,numPersonas);
    }

	public EncuestaIdentificacionFormLabels getLabels() {
		return labels;
	}

	public void setLabels(EncuestaIdentificacionFormLabels labels) {
		this.labels = labels;
	}
    
}
