package tw.android;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends Activity {

	private NumberPicker mNumPickerAge;
	private Button mBtnOK;
	private TextView mTxtR, mTxtAge;
	private Spinner mSpnSex;
	private String msSex;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mTxtAge = (TextView) findViewById(R.id.txtAge);
		mTxtAge.setText("25");

		mNumPickerAge = (NumberPicker) findViewById(R.id.numPickerAge);
		mNumPickerAge.setMinValue(0);
		mNumPickerAge.setMaxValue(200);
		mNumPickerAge.setValue(25);
		mNumPickerAge.setOnValueChangedListener(numPickerAgeOnValueChange);

		mBtnOK = (Button) findViewById(R.id.btnOK);
		mTxtR = (TextView) findViewById(R.id.txtR);
		
		mBtnOK.setOnClickListener(btnOKOnClick);

		mSpnSex = (Spinner) findViewById(R.id.spnSex);
		mSpnSex.setOnItemSelectedListener(spnSexOnItemSelected);
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
			int iAge = mNumPickerAge.getValue();
				
			String strSug = getString(R.string.result);
			if (msSex.equals(getString(R.string.sex_male)))
				if (iAge < 28)
					strSug += getString(R.string.sug_not_hurry);
				else if (iAge > 33)
					strSug += getString(R.string.sug_get_married);
				else
					strSug += getString(R.string.sug_find_couple);
			else
				if (iAge < 25)
					strSug += getString(R.string.sug_not_hurry);
				else if (iAge > 30)
					strSug += getString(R.string.sug_get_married);
				else
					strSug += getString(R.string.sug_find_couple);
				
			mTxtR.setText(strSug);
		}
	};

	private AdapterView.OnItemSelectedListener spnSexOnItemSelected =
	    	new AdapterView.OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> parent,
						View v,
						int position,
						long id) {
					// TODO Auto-generated method stub
					msSex = parent.getSelectedItem().toString();
				}

				@Override
				public void onNothingSelected(AdapterView<?> parent) {
					// TODO Auto-generated method stub
					
				}
			};

	private NumberPicker.OnValueChangeListener numPickerAgeOnValueChange =
			new NumberPicker.OnValueChangeListener() {
				
				@Override
				public void onValueChange(NumberPicker view, int oldValue, int newValue) {
					// TODO Auto-generated method stub
					mTxtAge.setText(String.valueOf(newValue));
				}
			};
}
