package com.hfad.areastats;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.parsers.SAXParserFactory;

public class LocationDataActivity extends Activity {

    private String[] strings = new String[37];
    public static final String LOCATION_CODE = "lCode";
    public static final String STATE_CODE = "sCode";
    public static final String COUNTY_CODE = "cCode";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_data);
        Intent intent = getIntent();
        String locationCode = intent.getStringExtra(LOCATION_CODE);
        String stateCode = intent.getStringExtra(STATE_CODE);
        String countyCode = intent.getStringExtra(COUNTY_CODE);

        new DataRetriever(locationCode, stateCode, countyCode).execute();

    }



    class DataRetriever extends AsyncTask<String, Void, String> {

        private String[] strings = new String[37];
        String locationCode;
        String stateCode;
        String countyCode;

        DataRetriever(String locationCode, String stateCode, String countyCode) {
            this.locationCode = locationCode;
            this.stateCode = stateCode;
            this.countyCode = countyCode;
        }

        @Override
        protected void onPreExecute() {
            TextView textViewPopulation = (TextView) findViewById(R.id.population);
            String population = textViewPopulation.getText().toString() + " Working...";
            textViewPopulation.setText(population);
        }

        @Override
        protected String doInBackground(String... params) {

            try {

                URL url = new URL("https://api.census.gov/data/2017/acs/acs5/profile?get=DP05_0001E,DP03_0009PE,DP05_0004E,DP03_0119PE,DP02_0113PE,DP02_0069PE,DP02_0036E,DP03_0096PE,DP04_0089E,DP03_0063E,DP05_0086E,DP04_0003PE,DP02_0055PE,DP02_0055PE,DP02_0057PE,DP02_0060PE,DP02_0063PE,DP02_0064PE,DP05_0018E,DP05_0019PE,DP05_0005PE,DP05_0006PE,DP05_0007PE,DP05_0008PE,DP05_0009PE,DP05_0010PE,DP05_0011PE,DP05_0012PE,DP05_0013PE,DP05_0014PE,DP05_0015PE,DP05_0016PE,DP05_0017PE,NAME&for=county%20subdivision:" + locationCode + "&in=state:" + stateCode + "%20county:" + countyCode);

                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                con.setRequestProperty("Content-Type", "application/json");

                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                Log.v("4", "4");

                String inputLine;
                Log.v("5", "5");

                StringBuffer content = new StringBuffer();
                Log.v("6", "6");
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                in.close();

                con.disconnect();

                Log.v(content.toString(), content.toString());
                String response = content.toString();
                response = response.substring(response.indexOf("],[") + 3);

                System.out.println(response);
                int i = 0;
                while (response.charAt(0) != ']') {
                    Log.d("BOOP", response.substring(response.indexOf('"') + 1, response.indexOf('"', 1)));
                    strings[i] = response.substring(response.indexOf('"') + 1, response.indexOf('"', 1));
                    response = response.substring(response.indexOf('"', 1) + 2);
                    i++;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {


            TextView textViewPopulation = (TextView) findViewById(R.id.population);
            String population =  "Population: " + strings[0];
            textViewPopulation.setText(population);

            TextView textViewUnemploymentRate = (TextView) findViewById(R.id.generalPopulation_UnemploymentRate);
            String unemploymentRate = textViewUnemploymentRate.getText().toString() + "  " + strings[1] + "%";
            textViewUnemploymentRate.setText(unemploymentRate);

            TextView textViewMaleToFemaleRatio = (TextView) findViewById(R.id.generalPopulation_MaleToFemaleRatio);
            String maleToFemaleRatio = textViewMaleToFemaleRatio.getText().toString() + "  " + strings[2] + " M for every 100 F";
            textViewMaleToFemaleRatio.setText(maleToFemaleRatio);

            TextView textViewBelowPovertyLevel = (TextView) findViewById(R.id.generalPopulation_BelowPovertyLevel);
            String belowPovertyLevel = textViewBelowPovertyLevel.getText().toString() + "  " + strings[3] + "%";
            textViewBelowPovertyLevel.setText(belowPovertyLevel);

            TextView textViewNonEnglish = (TextView) findViewById(R.id.generalPopulation_NonEnglishSpeakers);
            String nonEnglish = textViewNonEnglish.getText().toString() + "  " + strings[4] + "%";
            textViewNonEnglish.setText(nonEnglish);

            TextView textViewVeterans = (TextView) findViewById(R.id.generalPopulation_Veterans);
            String veterans = textViewVeterans.getText().toString() + "  " + strings[5] + "%";
            textViewVeterans.setText(veterans);

            TextView textViewBabiesBorn = (TextView) findViewById(R.id.generalPopulation_BabiesBornPastYear);
            String babiesBorn = textViewBabiesBorn.getText().toString() + "  " + strings[6] + "%";
            textViewBabiesBorn.setText(babiesBorn);

            TextView textViewHealthInsurance = (TextView) findViewById(R.id.generalPopulation_WithHealthInsurance);
            String healthInsurance = textViewHealthInsurance.getText().toString() + "  " + strings[7] + "%";
            textViewHealthInsurance.setText(healthInsurance);

            TextView textViewMedianHomePrice = (TextView)findViewById(R.id.housing_MedianHomePrice);
            String homePrice = textViewMedianHomePrice.getText().toString() + "  " + "$" + strings[8];
            textViewMedianHomePrice.setText(homePrice);

            TextView textViewIncome = (TextView)findViewById(R.id.housing_AverageHouseholdIncome);
            String income = textViewIncome.getText().toString() + "  " + "$" + strings[9];
            textViewIncome.setText(income);

            TextView textViewHomes = (TextView)findViewById(R.id.housing_TotalHomes);
            String homes = textViewHomes.getText().toString() + "  " + strings[10];
            textViewHomes.setText(homes);

            TextView textViewVacantHomes = (TextView)findViewById(R.id.housing_VacantHomes);
            String vacantHomes = textViewVacantHomes.getText().toString() + "  " + strings[11] + "%";
            textViewVacantHomes.setText(vacantHomes);

            TextView textViewElemMiddle = (TextView)findViewById(R.id.education_ElementaryAndMiddleSchool);
            String elemMiddle = textViewElemMiddle.getText().toString() + "  " + strings[12] + "%";
            textViewElemMiddle.setText(elemMiddle);

            TextView textViewHighSchool = (TextView)findViewById(R.id.education_HighSchool);
            String highSchool = textViewHighSchool.getText().toString() + "  " + strings[13] + "%";
            textViewHighSchool.setText(highSchool);

            TextView textViewCollege = (TextView)findViewById(R.id.education_College);
            String college = textViewCollege.getText().toString() + "  " + strings[14] + "%";
            textViewCollege.setText(college);

            TextView textViewDropout = (TextView)findViewById(R.id.education_HighSchoolDropout);
            String dropout = textViewDropout.getText().toString() + "  " + strings[15] + "%";
            textViewDropout.setText(dropout);

            TextView textViewAssociate = (TextView)findViewById(R.id.education_WithAssociateDegree);
            String associate = textViewAssociate.getText().toString() + "  " + strings[16] + "%";
            textViewAssociate.setText(associate);

            TextView textViewBachelor = (TextView)findViewById(R.id.education_WithBachelorsDegree);
            String bachelor = textViewBachelor.getText().toString() + "  " + strings[17] + "%";
            textViewBachelor.setText(bachelor);

            TextView textViewMedianAge = (TextView)findViewById(R.id.age_MedianAge);
            String age = textViewMedianAge.getText().toString() + "  " + strings[18];
            textViewMedianAge.setText(age);

            TextView textViewUnder18 = (TextView)findViewById(R.id.age_Under18);
            String under18 = textViewUnder18.getText().toString() + "  " + strings[19] + "%";
            textViewUnder18.setText(under18);

            TextView textViewUnder5 = (TextView)findViewById(R.id.age_UnderAge5);
            String under5 = textViewUnder5.getText().toString() + "  " + strings[20] + "%";
            textViewUnder5.setText(under5);

            TextView textView59 = (TextView)findViewById(R.id.age_5to9);
            String a59 = textView59.getText().toString() + "  " + strings[21] + "%";
            textView59.setText(a59);

            TextView textView1014 = (TextView)findViewById(R.id.age_10to14);
            String a1014 = textView1014.getText().toString() + "  " + strings[22] + "%";
            textView1014.setText(a1014);

            TextView textView1519 = (TextView)findViewById(R.id.age_15to19);
            String a1519 = textView1519.getText().toString() + "  " + strings[23] + "%";
            textView1519.setText(a1519);

            TextView textView2024 = (TextView)findViewById(R.id.age_20to24);
            String a2024 = textView2024.getText().toString() + "  " + strings[24] + "%";
            textView2024.setText(a2024);

            TextView textView2534 = (TextView)findViewById(R.id.age_25to34);
            String a2534 = textView2534.getText().toString() + "  " + strings[25] + "%";
            textView2534.setText(a2534);

            TextView textView3544 = (TextView)findViewById(R.id.age_35to44);
            String a3544 = textView3544.getText().toString() + "  " + strings[26] + "%";
            textView3544.setText(a3544);

            TextView textView4554 = (TextView)findViewById(R.id.age_45to54);
            String a4554 = textView4554.getText().toString() + "  " + strings[27] + "%";
            textView4554.setText(a4554);

            TextView textView5559 = (TextView)findViewById(R.id.age_55to59);
            String a5559 = textView5559.getText().toString() + "  " + strings[28] + "%";
            textView5559.setText(a5559);

            TextView textView6064 = (TextView)findViewById(R.id.age_60to64);
            String a6064 = textView6064.getText().toString() + "  " + strings[29] + "%";
            textView6064.setText(a6064);

            TextView textView6574 = (TextView)findViewById(R.id.age_65to74);
            String a6574 = textView6574.getText().toString() + "  " + strings[30] + "%";
            textView6574.setText(a6574);

            TextView textView7584 = (TextView)findViewById(R.id.age_75to84);
            String a7584 = textView7584.getText().toString() + "  " + strings[31] + "%";
            textView7584.setText(a7584);

            TextView textViewOver85 = (TextView)findViewById(R.id.age_OverAge85);
            String over85 = textViewOver85.getText().toString() + "  " + strings[32] + "%";
            textViewOver85.setText(over85);

        }
    }

}

