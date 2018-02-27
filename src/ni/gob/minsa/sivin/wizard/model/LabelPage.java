package ni.gob.minsa.sivin.wizard.model;

import java.util.ArrayList;

import android.support.v4.app.Fragment;
import ni.gob.minsa.sivin.wizard.ui.LabelFragment;

public class LabelPage extends Page {
	
	protected boolean mValPattern = false;
	protected String mPattern="";

	public LabelPage(ModelCallbacks callbacks, String title, String hintText, String textColor,boolean isVisible) {
		super(callbacks, title, hintText, textColor, isVisible, true);
	}

	@Override
	public Fragment createFragment() {
		return LabelFragment.create(getKey());
	}

	@Override
	public void getReviewItems(ArrayList<ReviewItem> dest) {

	}
}
