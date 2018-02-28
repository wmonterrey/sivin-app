package ni.gob.minsa.sivin.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import ni.gob.minsa.sivin.R;
import ni.gob.minsa.sivin.domain.Segmento;
import java.util.List;

public class SegmentoAdapter extends ArrayAdapter<Segmento> {
	
	public SegmentoAdapter(Context context, int textViewResourceId,
                          List<Segmento> items) {
		super(context, textViewResourceId, items);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		if (v == null) {
			LayoutInflater vi = (LayoutInflater) getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.segmento_list_item, null);
		}
		Segmento p = getItem(position);
		if (p != null) {

			TextView textView = (TextView) v.findViewById(R.id.dep_text);
			if (textView != null) {
				textView.setText(p.getComunidad());
			}
			
			textView = (TextView) v.findViewById(R.id.mun_text);
			if (textView != null) {
				textView.setText(p.getMunicipio());
			}

			textView = (TextView) v.findViewById(R.id.name_text);
			textView.setText(p.getCodigo());
			
			textView = (TextView) v.findViewById(R.id.proc_text);
			textView.setText(p.getDepartamento());
		}
		return v;
	}
}
