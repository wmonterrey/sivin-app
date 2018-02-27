package ni.gob.minsa.sivin.wizard.model;




import android.support.v4.app.Fragment;
import ni.gob.minsa.sivin.wizard.ui.NumberFragment;


public class NumberPage extends TextPage {
	
	protected int mGreaterOrEqualsThan = 0;
	protected int mLowerOrEqualsThan = 0;
	protected boolean mValRange = false;

	public NumberPage(ModelCallbacks callbacks, String title, String hintText, String textColor, boolean isVisible) {
		super(callbacks, title, hintText, textColor, isVisible);
	}

	@Override
	public Fragment createFragment() {
		return NumberFragment.create(getKey());
	}
	
	public Page setRangeValidation(boolean valRange, int minimo, int maximo) {
		mGreaterOrEqualsThan = minimo;
		mLowerOrEqualsThan = maximo;
		mValRange = valRange;
        return this;
    }

	public int getmGreaterOrEqualsThan() {
		return mGreaterOrEqualsThan;
	}

	public void setmGreaterOrEqualsThan(int mGreaterOrEqualsThan) {
		this.mGreaterOrEqualsThan = mGreaterOrEqualsThan;
	}

	public int getmLowerOrEqualsThan() {
		return mLowerOrEqualsThan;
	}

	public void setmLowerOrEqualsThan(int mLowerOrEqualsThan) {
		this.mLowerOrEqualsThan = mLowerOrEqualsThan;
	}

	public boolean ismValRange() {
		return mValRange;
	}

	public void setmValRange(boolean mValRange) {
		this.mValRange = mValRange;
	}

	

}
