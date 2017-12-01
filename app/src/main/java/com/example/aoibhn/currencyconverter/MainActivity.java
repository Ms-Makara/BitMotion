package com.example.aoibhn.currencyconverter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    private TextView source, end, aDate, amount, resultText, result;
    private Spinner sourceSpinner, endSpinner;
    private DatePicker datePicker;
    private EditText numbers;
    private Button convertButton;
    private int sourceIndex, endIndex;
    private double base, root;
    private Date date;

    private String url = "http://openexchangerates.org/api/latest.json?app_id=fc8c18305121452584c0846a06777a28";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        source = (TextView) findViewById(R.id.source);
        end = (TextView) findViewById(R.id.end);
        aDate = (TextView) findViewById(R.id.date);
        amount = (TextView) findViewById(R.id.amount);
        resultText = (TextView) findViewById(R.id.resultText);
        result = (TextView) findViewById(R.id.result);

        sourceSpinner = (Spinner) findViewById(R.id.sourceSpinner);
        endSpinner = (Spinner) findViewById(R.id.endSpinner);

        datePicker = (DatePicker) findViewById(R.id.datePicker);

        numbers = (EditText) findViewById(R.id.numbers);

        convertButton = (Button) findViewById(R.id.convertButton);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.currency, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        sourceSpinner.setAdapter(adapter);
        endSpinner.setAdapter(adapter);

        sourceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sourceIndex = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                sourceIndex = 0;
            }
        });

        endSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                endIndex = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                endIndex = 0;
            }
        });

        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date = getDateFromDatePicker(datePicker);
                result.setText("...");

                if (!numbers.getText().toString().equals("")) {
                    AsyncHttpClient client = new AsyncHttpClient();
                    url = "https://openexchangerates.org/api/historical/" + date + "json?app_id=fc8c18305121452584c0846a06777a28";
                    client.get(url, new AsyncHttpResponseHandler() {

                        @Override
                        public void onStart() {
                            super.onStart();
                        }

                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                            Log.i("CurrencyConverter", "HTTP Success");

                            try {
                                JSONObject obj = new JSONObject(String.valueOf(response));
                                JSONObject rates = obj.getJSONObject("rates");

                                Double eurRate = rates.getDouble("EUR");
                                Double usdRate = rates.getDouble("USD");
                                Double gbpRate = rates.getDouble("GBP");
                                Double jpyRate = rates.getDouble("JPY");
                                Double chfRate = rates.getDouble("CHF");
                                Double cadRate = rates.getDouble("CAD");
                                Double audRate = rates.getDouble("AUD");
                                Double nzdRate = rates.getDouble("NZD");
                                Double zarRate = rates.getDouble("ZAR");

                                Log.i("CurrencyConverter", "EUR:" + eurRate);
                                Log.i("CurrencyConverter", "USD:" + usdRate);
                                Log.i("CurrencyConverter", "GBP:" + gbpRate);
                                Log.i("CurrencyConverter", "JPY:" + jpyRate);
                                Log.i("CurrencyConverter", "CHF:" + chfRate);
                                Log.i("CurrencyConverter", "CAD:" + cadRate);
                                Log.i("CurrencyConverter", "AUD:" + audRate);
                                Log.i("CurrencyConverter", "NZD:" + nzdRate);
                                Log.i("CurrencyConverter", "ZAR:" + zarRate);


                                if(sourceIndex == 0)
                                {
                                    base = eurRate;
                                }
                                if(sourceIndex == 1)
                                {
                                    base = usdRate;
                                }
                                if(sourceIndex == 2)
                                {
                                    base = gbpRate;
                                }
                                if(sourceIndex == 3)
                                {
                                    base = jpyRate;
                                }
                                if(sourceIndex == 4)
                                {
                                    base = chfRate;
                                }
                                if(sourceIndex == 5)
                                {
                                    base = cadRate;
                                }
                                if(sourceIndex == 6)
                                {
                                    base = audRate;
                                }
                                if(sourceIndex == 7)
                                {
                                    base = nzdRate;
                                }
                                if(sourceIndex == 8)
                                {
                                    base = zarRate;
                                }
                                if(endIndex == 0)
                                {
                                    root = eurRate;
                                }
                                if(endIndex == 1)
                                {
                                    root = usdRate;
                                }
                                if(endIndex == 2)
                                {
                                    root = gbpRate;
                                }
                                if(endIndex == 3)
                                {
                                    root = jpyRate;
                                }
                                if(endIndex == 4)
                                {
                                    root = chfRate;
                                }
                                if(endIndex == 5)
                                {
                                    root = cadRate;
                                }
                                if(endIndex == 6)
                                {
                                    root = audRate;
                                }
                                if(endIndex == 7)
                                {
                                    root = nzdRate;
                                }
                                if(endIndex == 8)
                                {
                                    root = zarRate;
                                }

                                Double amount = Double.valueOf(numbers.getText().toString());

                                Double answer = amount * (root / base);
                                result.setText(String.valueOf(answer));

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                            onFailure(statusCode, headers, errorResponse, e);
                        }

                        @Override
                        public void onFinish() {
                            super.onFinish();
                        }

                    });
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Please enter an amount!",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public static Date getDateFromDatePicker(DatePicker datePicker) {
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year = datePicker.getYear();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        return calendar.getTime();
    }
}
