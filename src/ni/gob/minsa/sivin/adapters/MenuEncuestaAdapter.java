package ni.gob.minsa.sivin.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import ni.gob.minsa.sivin.R;
import ni.gob.minsa.sivin.domain.Encuesta;
import ni.gob.minsa.sivin.utils.Constants;

public class MenuEncuestaAdapter extends ArrayAdapter<String> {

	private final Context context;
	private final String[] values;
	private final Encuesta mEncuesta;
	
	public MenuEncuestaAdapter(Context context, int textViewResourceId,
			String[] values, Encuesta encuesta) {
		super(context, textViewResourceId, values);
		this.context = context;
		this.values = values;
		this.mEncuesta =encuesta;
	}
	
	@Override
    public boolean isEnabled(int position) {
        // Disable the first item of GridView
		boolean habilitado = true;
		switch (position){
		case 0:
			habilitado = true;
        	break;
		case 1:
			if(mEncuesta.getIdent()!=null){
				habilitado = true;
			}else {
				habilitado = false;
			}
        	break; 
		case 2:
			if(mEncuesta.getAgua()!=null){
				habilitado = true;
			}else {
				habilitado = false;
			}
        	break; 
		case 3:
			if(mEncuesta.getEntRealizada()!=null){
				habilitado = true;
			}else {
				habilitado = false;
			}
        	break;  
		case 4:
			if(mEncuesta.getNumNinos()!=null) {
				habilitado = true;
			}else {
				habilitado = false;
			}
        	break;        	
		case 5:
			if(mEncuesta.getHierron()!=null) {
				habilitado = true;
			}else {
				habilitado = false;
			}
        	break; 
		case 6:
			if(mEncuesta.getNlactmat()!=null) {
				habilitado = true;
			}else {
				habilitado = false;
			}
        	break;         	
		case 7:
			if(mEncuesta.getPesoTallaEnt()!=null) {
				habilitado = true;
			}else {
				habilitado = false;
			}
        	break;         
		case 8:
			if(mEncuesta.getMsEnt()!=null) {
				habilitado = true;
			}else {
				habilitado = false;
			}
        	break;  
		case 9:
			if(mEncuesta.getAgua()==null || mEncuesta.getEntRealizada()==null || mEncuesta.getNumNinos()==null || mEncuesta.getHierron()==null || mEncuesta.getNlactmat()==null
							|| mEncuesta.getPesoTallaEnt()==null || mEncuesta.getMsEnt()==null || mEncuesta.getPatConsAzuc()==null){
				habilitado = false;
			}else {
				habilitado = true;
			}
        	break;
		case 10:
			if(mEncuesta.getIdent()!=null){
				habilitado = true;
			}else {
				habilitado = false;
			}
        	break;          	
		default:
			if(mEncuesta.getIdent()==null) habilitado = false;
			break;
        }        	
		return habilitado;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		if (v == null) {
			LayoutInflater vi = (LayoutInflater) getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.menu_item_2, null);
		}
		TextView textView = (TextView) v.findViewById(R.id.label);
		textView.setTypeface(null, Typeface.BOLD);
		if(mEncuesta.getIdent()==null) textView.setTextColor(Color.GRAY);
		
		textView.setText(values[position]);
		
		// Change icon based on position
		Drawable img = null;
		switch (position){
		case 0: 
			if(mEncuesta.getIdent()!=null){
				textView.setTextColor(Color.BLACK);
				textView.setText(textView.getText()+"\n("+ context.getResources().getString(R.string.done)+")");
			}
			else{
				textView.setTextColor(Color.RED);
				textView.setText(textView.getText()+"\n("+ context.getResources().getString(R.string.pending)+")");
			}
			img=getContext().getResources().getDrawable( R.drawable.ic_identif);
			textView.setCompoundDrawablesWithIntrinsicBounds(null, img, null, null);
			break;
		case 1: 
			if(mEncuesta.getAgua()!=null){
				textView.setTextColor(Color.BLACK);
				textView.setText(textView.getText()+"\n("+ context.getResources().getString(R.string.done)+")");
			}
			else{
				if(mEncuesta.getIdent()==null) {
					textView.setTextColor(Color.GRAY);
				}
				else {
					textView.setTextColor(Color.RED);
				}
				textView.setText(textView.getText()+"\n("+ context.getResources().getString(R.string.pending)+")");
			}
			img=getContext().getResources().getDrawable( R.drawable.ic_secciona);
			textView.setCompoundDrawablesWithIntrinsicBounds(null, img, null, null);
			break;			
		case 2: 
			if(mEncuesta.getEntRealizada()!=null){
				textView.setTextColor(Color.BLACK);
				textView.setText(textView.getText()+"\n("+ context.getResources().getString(R.string.done)+")");
			}
			else{
				if(mEncuesta.getAgua()==null) {
					textView.setTextColor(Color.GRAY);
				}
				else {
					textView.setTextColor(Color.RED);
				}
				textView.setText(textView.getText()+"\n("+ context.getResources().getString(R.string.pending)+")");
			}
			img=getContext().getResources().getDrawable( R.drawable.ic_seccionb);
			textView.setCompoundDrawablesWithIntrinsicBounds(null, img, null, null);
			break;	
		case 3: 
			if(mEncuesta.getNumNinos()!=null) {
				textView.setTextColor(Color.BLACK);
				textView.setText(textView.getText()+"\n("+ context.getResources().getString(R.string.done)+")");
			}
			else{
				if(mEncuesta.getEntRealizada()==null) {
					textView.setTextColor(Color.GRAY);
				}
				else {
					textView.setTextColor(Color.RED);
				}
				textView.setText(textView.getText()+"\n("+ context.getResources().getString(R.string.pending)+")");
			}
			img=getContext().getResources().getDrawable( R.drawable.ic_seccionc);
			textView.setCompoundDrawablesWithIntrinsicBounds(null, img, null, null);
			break;
		case 4: 
			if(mEncuesta.getHierron()!=null){
				textView.setTextColor(Color.BLACK);
				textView.setText(textView.getText()+"\n("+ context.getResources().getString(R.string.done)+")");
			}
			else{
				if(mEncuesta.getNumNinos()==null) {
					textView.setTextColor(Color.GRAY);
				}
				else {
					textView.setTextColor(Color.RED);
				}
				textView.setText(textView.getText()+"\n("+ context.getResources().getString(R.string.pending)+")");
			}
			img=getContext().getResources().getDrawable( R.drawable.ic_secciond);
			textView.setCompoundDrawablesWithIntrinsicBounds(null, img, null, null);
			break;	
		case 5: 
			if(mEncuesta.getNlactmat()!=null){
				textView.setTextColor(Color.BLACK);
				textView.setText(textView.getText()+"\n("+ context.getResources().getString(R.string.done)+")");
			}
			else{
				if(mEncuesta.getHierron()==null) {
					textView.setTextColor(Color.GRAY);
				}
				else {
					textView.setTextColor(Color.RED);
				}
				textView.setText(textView.getText()+"\n("+ context.getResources().getString(R.string.pending)+")");
			}
			img=getContext().getResources().getDrawable( R.drawable.ic_seccione);
			textView.setCompoundDrawablesWithIntrinsicBounds(null, img, null, null);
			break;	
		case 6: 
			if(mEncuesta.getPesoTallaEnt()!=null){
				textView.setTextColor(Color.BLACK);
				textView.setText(textView.getText()+"\n("+ context.getResources().getString(R.string.done)+")");
			}
			else{
				if(mEncuesta.getNlactmat()==null) {
					textView.setTextColor(Color.GRAY);
				}
				else {
					textView.setTextColor(Color.RED);
				}
				textView.setText(textView.getText()+"\n("+ context.getResources().getString(R.string.pending)+")");
			}
			img=getContext().getResources().getDrawable( R.drawable.ic_seccionf);
			textView.setCompoundDrawablesWithIntrinsicBounds(null, img, null, null);
			break;	
		case 7: 
			if(mEncuesta.getMsEnt()!=null){
				textView.setTextColor(Color.BLACK);
				textView.setText(textView.getText()+"\n("+ context.getResources().getString(R.string.done)+")");
			}
			else{
				if(mEncuesta.getPesoTallaEnt()==null) {
					textView.setTextColor(Color.GRAY);
				}
				else {
					textView.setTextColor(Color.RED);
				}
				textView.setText(textView.getText()+"\n("+ context.getResources().getString(R.string.pending)+")");
			}
			img=getContext().getResources().getDrawable( R.drawable.ic_secciong);
			textView.setCompoundDrawablesWithIntrinsicBounds(null, img, null, null);
			break;	
		case 8: 
			if(mEncuesta.getPatConsAzuc()!=null){
				textView.setTextColor(Color.BLACK);
				textView.setText(textView.getText()+"\n("+ context.getResources().getString(R.string.done)+")");
			}
			else{
				if(mEncuesta.getMsEnt()==null) {
					textView.setTextColor(Color.GRAY);
				}
				else {
					textView.setTextColor(Color.RED);
				}
				textView.setText(textView.getText()+"\n("+ context.getResources().getString(R.string.pending)+")");
			}
			img=getContext().getResources().getDrawable( R.drawable.ic_seccionh);
			textView.setCompoundDrawablesWithIntrinsicBounds(null, img, null, null);
			break;	
		case 9:
			if(!(mEncuesta.getEstado()==Constants.STATUS_NOT_FINALIZED)){
				textView.setTextColor(Color.BLACK);
				textView.setText(textView.getText()+"\n("+ context.getResources().getString(R.string.done)+")");
			}else {
				if(mEncuesta.getAgua()==null || mEncuesta.getEntRealizada()==null || mEncuesta.getNumNinos()==null || mEncuesta.getHierron()==null || mEncuesta.getNlactmat()==null
						|| mEncuesta.getPesoTallaEnt()==null || mEncuesta.getMsEnt()==null|| mEncuesta.getPatConsAzuc()==null) {
					textView.setTextColor(Color.GRAY);
				}
				else {
					textView.setTextColor(Color.RED);
				}
				textView.setText(textView.getText()+"\n("+ context.getResources().getString(R.string.pending)+")");
			}		
			img=getContext().getResources().getDrawable( R.drawable.ic_terminar);
			textView.setCompoundDrawablesWithIntrinsicBounds(null, img, null, null);
			break;
		case 10:
			if(mEncuesta.getLatitud()!=null){
				textView.setTextColor(Color.BLACK);
				textView.setText(textView.getText()+"\n("+ context.getResources().getString(R.string.done)+")");
			}
			else{
				textView.setTextColor(Color.RED);
				textView.setText(textView.getText()+"\n("+ context.getResources().getString(R.string.pending)+")");
			}
			img=getContext().getResources().getDrawable( R.drawable.ic_menu_mylocation);
			textView.setCompoundDrawablesWithIntrinsicBounds(null, img, null, null);
			break;			
		default:
			img=getContext().getResources().getDrawable( R.drawable.ic_launcher);
			textView.setCompoundDrawablesWithIntrinsicBounds(null, img, null, null);
			break;
		}

		return v;
	}
}
