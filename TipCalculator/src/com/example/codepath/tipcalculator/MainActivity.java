package com.example.codepath.tipcalculator;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.KeyListener;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/***
 * 
 * @author gargka
 *
 * Main class to calculate the tip amount based on the tip percent selected. 
 */
public class MainActivity extends Activity {

	private EditText etAmount;
	private TextView tvTip;
	private float tipPercent;
	private char keyEntered;

	private final int REQUEST_CODE = 10;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		etAmount = (EditText) findViewById(R.id.etAmount);
		tvTip = (TextView) findViewById(R.id.ltTip);

		init();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/**
	 * Calculate 20% tip
	 * @param v
	 */
	public void calculate20PercentTip(View v) {
		tipPercent = 20;
		calculate();
	}

	/**
	 * Calculate 15% tip
	 * @param v
	 */
	public void calculate15PercentTip(View v) {
		tipPercent = 15;
		calculate();
	}

	/**
	 * Calculate 10% tip
	 * @param v
	 */
	public void calculate10PercentTip(View v) {
		tipPercent = 10;
		calculate();
	}

	/**
	 * Calculate custom tip
	 * @param v
	 */
	public void calculateCustomPercentTip(View v) {

		//create a new intent to launch the custom tip activity..
		Intent i = new Intent(MainActivity.this, CustomTipActivity.class);
		//pass in tip percent
		i.putExtra("OtherTipPercent", 0.0);		
		startActivityForResult(i, REQUEST_CODE);
	}

	/**
	 * Set the custom tip 
	 */	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		//extract the custom tip from the intent and set it tipPercent
		if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {			
			tipPercent = data.getExtras().getFloat("CustomTipPercent");
			calculate();			
		}
	} 

	/**
	 * Calculates the tip amount
	 */
	private void calculate() {
		float amount;
		String amt = etAmount.getText().toString();

		if(amt.equals(""))
			amount = (float) 0.0;
		else
			amount = Float.valueOf(etAmount.getText().toString());

		float tipAmount = (tipPercent * amount) / 100;	
		updateTipAmount(tipAmount);
	}

	/**
	 * set the tip amount
	 * @param tipAmount the tip amount calculated
	 */
	private void updateTipAmount(float tipAmount) {
		tvTip.setText((new StringBuilder("$").append(String.valueOf(tipAmount))).toString());
		tvTip.setTextColor(Color.parseColor("#FF0000"));
	}

	/**
	 * Set up key listener for the editable text. The user should only enter numbers for amount and cannot have more
	 * than one decimal in the amount. 
	 */
	private void init() {			
		etAmount.setCursorVisible(true);
		etAmount.requestFocus();
		etAmount.setFocusable(true);
		
		etAmount.setKeyListener( new KeyListener() {
			@Override
			public boolean onKeyUp(View view, Editable text, int keyCode, KeyEvent event) {
				keyEntered = (char)event.getUnicodeChar();	
				String amount = etAmount.getText().toString();
				if((keyEntered >= '0' && keyEntered <= '9')  //numbers 0-9
						|| (keyEntered == '.' && !amount.contains("."))) 					
					amount = amount + keyEntered;
				else if(keyCode == KeyEvent.KEYCODE_DEL)
					amount = amount.substring(0, amount.length()-1);
				
				etAmount.setText(amount);
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

		//once the amount has changed, call calculate() to calculate the tip automatically.
		etAmount.addTextChangedListener( new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				calculate();
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

			@Override
			public void afterTextChanged(Editable s) {	}
		});

	}
}
