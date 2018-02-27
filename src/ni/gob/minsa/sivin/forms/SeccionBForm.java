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
import ni.gob.minsa.sivin.wizard.model.MultipleFixedChoicePage;
import ni.gob.minsa.sivin.wizard.model.NewDatePage;
import ni.gob.minsa.sivin.wizard.model.NumberPage;
import ni.gob.minsa.sivin.wizard.model.Page;
import ni.gob.minsa.sivin.wizard.model.PageList;
import ni.gob.minsa.sivin.wizard.model.SingleFixedChoicePage;
import ni.gob.minsa.sivin.wizard.model.TextPage;

public class SeccionBForm extends AbstractWizardModel {
	
	int index = 0;
	private SeccionBFormLabels labels;
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
	
    public SeccionBForm(Context context, String pass) {    	
        super(context,pass);
    }

    @Override
    protected PageList onNewRootPageList() {
    	labels = new SeccionBFormLabels();
    	this.sivinAdapter = new SivinAdapter(mContext,mPass,false,false);
    	sivinAdapter.open();
    	String[] catSN = fillCatalog("CAT_SINO");
    	String[] catNivel = fillCatalog("CAT_NIVEL");
    	String[] catGrado = fillCatalog("CAT_GRADO");
    	String[] catEntRealizada = fillCatalog("CAT_ENTREVISTADO");
    	String[] catSNNS = fillCatalog("CAT_SINONR");
    	String[] catEntRecHierro = fillCatalog("CAT_HIERROFREC");
    	String[] catTiemHierroUnd = fillCatalog("CAT_HIERROTIEMP");
    	String[] catDondeHierro = fillCatalog("CAT_HIERROOBT");
    	String[] catVita = fillCatalog("CAT_VITA");
    	String[] catEvitaEmb = fillCatalog("CAT_XEMB");
    	String[] catDEvitaEmb = fillCatalog("CAT_DXEMB");
    	sivinAdapter.close();
    	Page conoceFNac = new SingleFixedChoicePage(this,labels.getConoceFNac(), labels.getConoceFNacHint(), Constants.WIZARD, true).setChoices(catSN).setRequired(true);
    	DateMidnight dmDesde = DateMidnight.parse("01/01/1942", DateTimeFormat.forPattern("dd/MM/yyyy"));
		DateMidnight dmHasta = new DateMidnight(new Date().getTime());
    	Page fnacEnt = new NewDatePage(this,labels.getFnacEnt(), labels.getFnacEntHint(), Constants.WIZARD, false).setRangeValidation(true, dmDesde, dmHasta).setRequired(true);
    	Page edadEnt = new NumberPage(this,labels.getEdadEnt(),labels.getEdadEntHint(),Constants.WIZARD,true).setRangeValidation(true, 13, 88).setRequired(true);
    	Page leerEnt = new SingleFixedChoicePage(this,labels.getLeerEnt(), labels.getLeerEntHint(), Constants.WIZARD, true).setChoices(catSN).setRequired(true);
    	Page escribirEnt = new SingleFixedChoicePage(this,labels.getEscribirEnt(), labels.getEscribirEntHint(), Constants.WIZARD, true).setChoices(catSN).setRequired(true);
    	Page leeescEnt = new SingleFixedChoicePage(this,labels.getLeeescEnt(), labels.getLeeescEntHint(), Constants.WIZARD, true).setChoices(catSN).setRequired(true);
    	Page nivelEnt = new SingleFixedChoicePage(this,labels.getNivelEnt(), labels.getNivelEntHint(), Constants.WIZARD, false).setChoices(catNivel).setRequired(true);
    	Page gradoEnt = new SingleFixedChoicePage(this,labels.getGradoEnt(), labels.getGradoEntHint(), Constants.WIZARD, false).setChoices(catGrado).setRequired(true);
    	Page entRealizada = new SingleFixedChoicePage(this,labels.getEntRealizada(), labels.getEntRealizadaHint(), Constants.WIZARD, true).setChoices(catEntRealizada).setRequired(true);
    	Page entEmb = new SingleFixedChoicePage(this,labels.getEntEmb(), labels.getEntEmbHint(), Constants.WIZARD, false).setChoices(catSNNS).setRequired(true);
    	Page entEmbUnico = new SingleFixedChoicePage(this,labels.getEntEmbUnico(), labels.getEntEmbUnicoHint(), Constants.WIZARD, false).setChoices(catSN).setRequired(true);
    	Page entDioluz = new SingleFixedChoicePage(this,labels.getEntDioluz(), labels.getEntDioluzHint(), Constants.WIZARD, false).setChoices(catSNNS).setRequired(true);
    	Page entHieacfol = new SingleFixedChoicePage(this,labels.getEntHieacfol(), labels.getEntHieacfolHint(), Constants.WIZARD, false).setChoices(catSNNS).setRequired(true);
    	Page entMeseshierro = new NumberPage(this,labels.getEntMeseshierro(),labels.getEntMeseshierroHint(),Constants.WIZARD,false).setRangeValidation(true, 1, 9).setRequired(true);
    	Page entRecHierro = new SingleFixedChoicePage(this,labels.getEntRecHierro(), labels.getEntRecHierroHint(), Constants.WIZARD, false).setChoices(catEntRecHierro).setRequired(true);
    	Page entORecHierro = new TextPage(this,labels.getEntORecHierro(),labels.getEntORecHierroHint(),Constants.WIZARD,false).setPatternValidation(true, ".{1,155}").setRequired(true);
    	Page tiemHierroUnd = new SingleFixedChoicePage(this,labels.getTiemHierroUnd(), labels.getTiemHierroUndHint(), Constants.WIZARD, false).setChoices(catTiemHierroUnd).setRequired(true);
    	Page tiemHierro = new NumberPage(this,labels.getTiemHierro(),labels.getTiemHierroHint(),Constants.WIZARD,false).setRangeValidation(true, 1, 9).setRequired(true);
    	Page dondeHierro = new MultipleFixedChoicePage(this,labels.getDondeHierro(), labels.getDondeHierroHint(), Constants.WIZARD, false).setChoices(catDondeHierro).setRequired(true);
    	Page dondeHierroBesp = new TextPage(this,labels.getDondeHierroBesp(),labels.getDondeHierroBespHint(),Constants.WIZARD,false).setPatternValidation(true, ".{1,255}").setRequired(true);
    	Page dondeHierroFesp = new TextPage(this,labels.getDondeHierroFesp(),labels.getDondeHierroFespHint(),Constants.WIZARD,false).setPatternValidation(true, ".{1,255}").setRequired(true);
    	Page tomadoHierro = new SingleFixedChoicePage(this,labels.getTomadoHierro(), labels.getTomadoHierroHint(), Constants.WIZARD, false).setChoices(catSN).setRequired(true);
    	Page vita = new SingleFixedChoicePage(this,labels.getVita(), labels.getVitaHint(), Constants.WIZARD, false).setChoices(catSNNS).setRequired(true);
    	Page tiempVita = new SingleFixedChoicePage(this,labels.getTiempVita(), labels.getTiempVitaHint(), Constants.WIZARD, false).setChoices(catVita).setRequired(true);
    	Page evitaEmb = new SingleFixedChoicePage(this,labels.getEvitaEmb(), labels.getEvitaEmbHint(), Constants.WIZARD, false).setChoices(catEvitaEmb).setRequired(true);
    	Page dondeAntic = new SingleFixedChoicePage(this,labels.getDondeAntic(), labels.getDondeAnticHint(), Constants.WIZARD, false).setChoices(catDEvitaEmb).setRequired(true);
    	Page nuevoEmb = new SingleFixedChoicePage(this,labels.getNuevoEmb(), labels.getNuevoEmbHint(), Constants.WIZARD, false).setChoices(catSNNS).setRequired(true);
    	Page hierro = new SingleFixedChoicePage(this,labels.getHierro(), labels.getHierroHint(), Constants.WIZARD, false).setChoices(catSNNS).setRequired(true);
    	Page dHierro = new SingleFixedChoicePage(this,labels.getdHierro(), labels.getdHierroHint(), Constants.WIZARD,false).setChoices(catDEvitaEmb).setRequired(true);

    	return new PageList(conoceFNac,fnacEnt,edadEnt,leerEnt,escribirEnt,leeescEnt,nivelEnt,gradoEnt,entRealizada,entEmb,entEmbUnico,entDioluz,
    							entHieacfol,entMeseshierro,entRecHierro,entORecHierro,tiemHierroUnd,tiemHierro,dondeHierro,dondeHierroBesp,dondeHierroFesp,tomadoHierro,vita,tiempVita,evitaEmb,dondeAntic,nuevoEmb,hierro,dHierro);
    	
    }

	public SeccionBFormLabels getLabels() {
		return labels;
	}

	public void setLabels(SeccionBFormLabels labels) {
		this.labels = labels;
	}
    
}
