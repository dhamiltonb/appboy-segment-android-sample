package com.appboy.segment.appboysample;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.segment.analytics.Analytics;
import com.segment.analytics.Properties;
import com.segment.analytics.Traits;

import java.util.Date;
import java.util.Random;

public class MainFragment extends Fragment {

  Random mRandom = new Random();

  public MainFragment() {}

  @Override
  public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstanceState) {
    final View view = layoutInflater.inflate(R.layout.content_main, container, false);

    Button identifyButton = (Button) view.findViewById(R.id.identifyButton);
    identifyButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View arg0) {
        String newUser = Integer.toString(Math.abs(mRandom.nextInt()));
        TextView textView = (TextView) view.findViewById(R.id.helloText);
        textView.setText("Hello Appboy and Segment! Current User: " + newUser);
        Toast.makeText(getContext(), "identify() called with user id: " + newUser + ".", Toast.LENGTH_LONG).show();
        Traits traits = new Traits();
        Date birthday = new Date(System.currentTimeMillis());
        traits.putBirthday(birthday);
        traits.putEmail("abc@123.com");
        traits.putFirstName("First");
        traits.putLastName("Last");
        traits.putGender("m");
        traits.putPhone("5555555555");
        Traits.Address address = new Traits.Address();
        address.putCity("City");
        address.putCountry("USA");
        traits.putAddress(address);
        traits.put("boolean", new Boolean(true));
        traits.put("integer", new Integer(50));
        traits.put("float", new Float(120.4));
        traits.put("long", new Long(1234L));
        traits.put("string", "hello");
        Analytics.with(getContext()).identify(newUser, traits, null);
      }
    });

    Button flushButton = (Button) view.findViewById(R.id.flushButton);
    flushButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View arg0) {
        Toast.makeText(getContext(), "flush() called.", Toast.LENGTH_LONG).show();
        Analytics.with(getContext()).flush();
      }
    });

    Button trackButton = (Button) view.findViewById(R.id.trackButton);
    trackButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View arg0) {
        Toast.makeText(getContext(), "track() called for custom event 'custom_event' and purchase 'custom_purchase'.", Toast.LENGTH_LONG).show();
        Analytics.with(getContext()).track("custom_event", new Properties().putValue("property_key", "property_value"));
        Properties purchaseProperties = new Properties();
        purchaseProperties.put("property_key", "property_value");
        purchaseProperties.putRevenue(10.0);
        purchaseProperties.putCurrency("JPY");
        Analytics.with(getContext()).track("custom_purchase", purchaseProperties);
      }
    });
    return view;
  }
}