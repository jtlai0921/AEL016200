package tw.android;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MainActivity extends Activity {

	private Button mBtnOK;
	private TextView mTxtR;

	private RadioGroup mRadGrpSex, mRadGrpAge;
    private RadioButton mRadBtnAgeRange1, mRadBtnAgeRange2, mRadBtnAgeRange3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mBtnOK = (Button) findViewById(R.id.btnOK);
		mTxtR = (TextView) findViewById(R.id.txtR);
		
		mBtnOK.setOnClickListener(btnOKOnClick);

		mRadGrpSex = (RadioGroup)findViewById(R.id.radGrpSex);
        mRadGrpAge = (RadioGroup)findViewById(R.id.radGrpAge);
        mRadBtnAgeRange1 = (RadioButton)findViewById(R.id.radBtnAgeRange1);
        mRadBtnAgeRange2 = (RadioButton)findViewById(R.id.radBtnAgeRange2);
        mRadBtnAgeRange3 = (RadioButton)findViewById(R.id.radBtnAgeRange3);
		mRadGrpSex.setOnCheckedChangeListener(radGrpSexOnCheckedChange);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

    private View.OnClickListener btnOKOnClick = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			String strSug = getString(R.string.result);

			// 不需要判斷男女生，直接依照選擇的年齡區間顯示結果
			switch (mRadGrpAge.getCheckedRadioButtonId()) {
			case R.id.radBtnAgeRange1:
				strSug += getString(R.string.sug_not_hurry);
				break;
			case R.id.radBtnAgeRange2:
				strSug += getString(R.string.sug_find_couple);
				break;
			case R.id.radBtnAgeRange3:
				strSug += getString(R.string.sug_get_married);
				break;
			}

			mTxtR.setText(strSug);
		}
	};

	private RadioGroup.OnCheckedChangeListener radGrpSexOnCheckedChange = new RadioGroup.OnCheckedChangeListener() {
		
		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			// TODO Auto-generated method stub
			 if (checkedId == R.id.radBtnMale) {
    		    mRadBtnAgeRange1.setText(getString(R.string.male_age_range1));
    			mRadBtnAgeRange2.setText(getString(R.string.male_age_range2));
    			mRadBtnAgeRange3.setText(getString(R.string.male_age_range3));
	    	 } else {
	    		mRadBtnAgeRange1.setText(getString(R.string.female_age_range1));
    			mRadBtnAgeRange2.setText(getString(R.string.female_age_range2));
    			mRadBtnAgeRange3.setText(getString(R.string.female_age_range3));
	    	 }
		}
	};
}
