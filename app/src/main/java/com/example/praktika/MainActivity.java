package com.example.praktika;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.praktika.R;
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {
    String data1 = "";
    double a = 0, b =0, c=0, b1 = 0, d = 0, m = 0, e = 0, f = 0,sr =0;
    String data2 = "";
    String url = "";
    LineGraphSeries<DataPoint> series;
    LineGraphSeries<DataPoint> series2;
    Date date = new Date();
    ArrayList<String>  data = new ArrayList<String>();
    ArrayList<String>  value = new ArrayList<String>();
    ArrayList<Double>  sma = new ArrayList<Double>();
    Document document;
    TextView textView;
    EditText textplace1;
    EditText textplace2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new
                    StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        textplace1 = (EditText) findViewById(R.id.editText2);
        textplace2 = (EditText) findViewById(R.id.editText3);
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new NewThread().execute();
            }
        });
    }
    @Override
    public void onResume()
    {
        super.onResume();
    }
    public class NewThread extends AsyncTask<Void,Void,Void> {
        @Override
        protected Void doInBackground(Void... uRls) {
            try {
                url = "https://weather.rambler.ru/v-sankt-peterburge/" ;
                document = Jsoup.connect(url).get();
                Elements table = document.select(".DhEW");
                Elements rows = table.select("tr");
                for (int i = 0; i < rows.size(); i++) {
                    Element row = rows.get(i);
                    Elements cols = row.select("td");
                        data.add(((cols.get(0)).text()));
                        value.add(cols.get(1).text());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            ArrayList<Integer> day = new ArrayList<Integer>();
            ArrayList<Integer> night = new ArrayList<>();
            for (int i = 0; i<value.size();i++)
            {
                String d = value.get(i).split("°")[0];
                String n = value.get(i).split("°")[1];
                day.add(Integer.parseInt(d));
                night.add(Integer.parseInt(n));
            }
            GraphView graph = (GraphView) findViewById(R.id.graph);
          //  SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());

                series = new LineGraphSeries<DataPoint>();
                series2 = new LineGraphSeries<DataPoint>();
                for (int i = 0; i < day.size(); i++) {

                    //  date = format.parse(data.get(i).trim());
                    series.appendData(new DataPoint(i, day.get(i)), true, 10);
                    series2.appendData(new DataPoint(i, night.get(i)), true, 10);
                    series2.setColor(Color.RED);
                }

            ///// Настройки /////////////
                graph.addSeries(series);
                graph.addSeries(series2);
                graph.getViewport().setScalable(true);
                graph.getViewport().setYAxisBoundsManual(true);
                graph.getViewport().setMaxY(27);
                graph.getViewport().setMinY(0);
            //final SimpleDateFormat formattter = new SimpleDateFormat("MM.yy", Locale.getDefault());
                graph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter()
                {
                    @Override
                    public String formatLabel(double value, boolean isValueX)
                    {
                        if(isValueX)
                        {
                            int val = (int) (value);
                            return data.get(val);
                        } else{
                        return super.formatLabel(value,isValueX);}
                    }
                });
        }
    }
}

