package com.example.aoibhn.currencyconverter;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private TextView source, end, aDate, amount, resultText, result;
    private Spinner sourceSpinner, endSpinner;
    private DatePicker datePicker;
    private EditText numbers;
    private Button convertButton;
    private int sourceIndex, endIndex;
    private String base, root;
    private Date date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        source = (TextView)findViewById(R.id.source);
        end = (TextView)findViewById(R.id.end);
        aDate = (TextView)findViewById(R.id.date);
        amount = (TextView)findViewById(R.id.amount);
        resultText = (TextView)findViewById(R.id.resultText);
        result = (TextView)findViewById(R.id.result);

        sourceSpinner = (Spinner)findViewById(R.id.sourceSpinner);
        endSpinner = (Spinner)findViewById(R.id.endSpinner);

        datePicker = (DatePicker)findViewById(R.id.datePicker);

        numbers = (EditText)findViewById(R.id.numbers);

        convertButton = (Button)findViewById(R.id.convertButton);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.currency, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        sourceSpinner.setAdapter(adapter);
        endSpinner.setAdapter(adapter);

        sourceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                sourceIndex = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });

        endSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                endIndex = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });

        convertButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                result.setText("...");
            }

            String text = numbers.getText().toString();
            double input = Double.parseDouble(text);

            new calc().execute();
        });
    }

    public static Date getDateFromDatePicker(DatePicker datePicker)
    {
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year =  datePicker.getYear();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        return calendar.getTime();
    }

    public class calc extends AsyncTask<String, String, String[]>
    {
        @Override
        protected String[] doInBackground(String... strings)
        {
            if(sourceIndex == 0)
            {
                base = "EUR";
            }
            if(sourceIndex == 1)
            {
                base = "USD";
            }
            if(sourceIndex == 2)
            {
                base = "GBP";
            }
            if(sourceIndex == 3)
            {
                base = "JPY";
            }
            if(sourceIndex == 4)
            {
                base = "CHF";
            }
            if(sourceIndex == 5)
            {
                base = "CAD";
            }
            if(sourceIndex == 6)
            {
                base = "AUD";
            }
            if(sourceIndex == 7)
            {
                base = "NZD";
            }
            if(sourceIndex == 8)
            {
                base = "ZAR";
            }
            if(endIndex == 0)
            {
                root = "EUR";
            }
            if(endIndex == 1)
            {
                root = "USD";
            }
            if(endIndex == 2)
            {
                root = "GBP";
            }
            if(endIndex == 3)
            {
                root = "JPY";
            }
            if(endIndex == 4)
            {
                root = "CHF";
            }
            if(endIndex == 5)
            {
                root = "CAD";
            }
            if(endIndex == 6)
            {
                root = "AUD";
            }
            if(endIndex == 7)
            {
                root = "NZD";
            }
            if(endIndex == 8)
            {
                root = "ZAR";
            }
            date = getDateFromDatePicker(datePicker);
            String url;
            try{
                url = getJson("https://api.fixer.io/" + date + "?base=" + base);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String[] strings)
        {
            super.onPostExecute(strings);
        }

        public String getJson(String url) throws ClientProtocolException, IOException
        {
            StringBuilder build = new StringBuilder();
            HttpClient client = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(url);
            HttpResponse resonse = client.execute(httpGet);
            HttpEntity entity = response.getEntity();
            InputStream content = entity.getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader((content)));
            String con;
            while((con = reader.readLine()) != null))
            {
                build.append(con);
            }
            return build.toString();
        }
    }
}
