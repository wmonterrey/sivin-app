package ni.gob.minsa.sivin.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import ni.gob.minsa.sivin.R;
import ni.gob.minsa.sivin.domain.Encuesta;
import java.text.SimpleDateFormat;
import java.util.List;

public class EncuestaAdapter extends ArrayAdapter<Encuesta> {
	
	private SimpleDateFormat mDateFormat = new SimpleDateFormat("MMM dd, yyyy");
	
	public EncuestaAdapter(Context context, int textViewResourceId,
                          List<Encuesta> items) {
		super(context, textViewResourceId, items);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		if (v == null) {
			LayoutInflater vi = (LayoutInflater) getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.encuesta_list_item, null);
		}
		Encuesta p = getItem(position);
		if (p != null) {

			TextView textView = (TextView) v.findViewById(R.id.code_text);
			if (textView != null) {
				textView.setText(this.getContext().getString(R.string.enc_code) + ": " + p.getCodigo());
			}
			
			textView = (TextView) v.findViewById(R.id.date_text);
			if (textView != null) {
				textView.setText(this.getContext().getString(R.string.enc_date) + ": " + mDateFormat.format(p.getFechaEntrevista()));
			}

			textView = (TextView) v.findViewById(R.id.name_text);
			textView.setText(this.getContext().getString(R.string.enc_num) + ": " +p.getNumEncuesta().toString() +" - "+p.getJefeFamilia());
			
			ImageView imageView = (ImageView) v.findViewById(R.id.image);
			if (imageView != null) {
				if (String.valueOf(p.getEstado()).equals("0")) {
					imageView.setImageResource(R.drawable.ic_pending);
				} else if (String.valueOf(p.getEstado()).equals("1")) {
					imageView.setImageResource(R.drawable.ic_notsent);
				} else if (String.valueOf(p.getEstado()).equals("2")) {
					imageView.setImageResource(R.drawable.ic_done);
				}
			}
		}
		return v;
	}
}
