package com.example.codepath.tipcalculator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.method.KeyListener;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

/**
 * Activity for users to enter custom tips
 * 
 * @author gargka
 *
 */
public class CustomTipActivity extends Activity {
	private EditText etCustomTipPercent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_other_tip);
		
		etCustomTipPercent = (EditText) findViewById(R.id.etOtherTipPercent);
		
		init();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	/**
	 * Save onclick handler
	 * @param v
	 */
	public void done(View v) {		
		// Create an intent to pass the data back to the main activity 
		Intent data = new Intent();		
		data.putExtra("CustomTipPercent", Float.valueOf(etCustomTipPercent.getText().toString()));
		setResult(RESULT_OK, data); 
		finish(); 
	}
	
	/**
	 * Allow users to only enter numbers or one decimal for custom tip. 
	 */
	private void init() {
		etCustomTipPercent.setKeyListener( new KeyListener() {
			@Override
			public boolean onKeyUp(View view, Editable text, int keyCode, KeyEvent event) {
				char keyEntered = (char)event.getUnicodeChar();	
				String percent = etCustomTipPercent.getText().toString();
				if((keyEntered >= '0' && keyEntered <= '9')  //numbers 0-9
						|| (keyEntered == '.' && !percent.contains("."))) 					
					percent = percent + keyEntered;
				else if(keyCode == KeyEvent.KEYCODE_DEL)
					percent = percent.substring(0, percent.length()-1);
				
				etCustomTipPercent.setText(percent);
				return true;			
			}

			@Override
			public boolean onKeyOther(View view, Editable text, KeyEvent event) {				
				return true;
			}

			@Override
			public boolean onKeyDown(View view, Editable text, int keyCode, KeyEvent event) {				
				return true;
			}

			@Override
			public int getInputType() {				
				return 0;
			}

			@Override
			public void clearMetaKeyState(View view, Editable content, int states) { }
		});
	}
	
}
