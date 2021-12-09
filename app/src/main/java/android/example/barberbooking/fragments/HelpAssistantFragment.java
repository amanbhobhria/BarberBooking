package android.example.barberbooking.fragments;

import android.content.Intent;

import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.example.barberbooking.R;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HelpAssistantFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HelpAssistantFragment extends Fragment implements View.OnClickListener {
    Button proceedBtn;
    EditText queryTxt;
    Button callBtn;


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HelpAssistantFragment() {
        // Required empty public constructor
    }


    public static HelpAssistantFragment newInstance(String param1, String param2) {
        HelpAssistantFragment fragment = new HelpAssistantFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_help_assistant, container, false);
        callBtn = (Button) view.findViewById(R.id.callHelpBtn);
        proceedBtn = (Button) view.findViewById(R.id.proceedBtn);
        queryTxt = (EditText) view.findViewById(R.id.queryTxt);


        proceedBtn.setOnClickListener(this);

        callBtn.setOnClickListener(this);

        return view;


    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.callHelpBtn) {
            try {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + "95181 56020"));//change the number
                startActivity(callIntent);
            } catch (Exception e) {
                Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
            }


        } else if (v.getId() == R.id.proceedBtn) {
            if (queryTxt.getText().toString().isEmpty()) {
                Toast.makeText(getActivity(), "Enter a query to proceed", Toast.LENGTH_SHORT).show();
            } else {
                Intent email = new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_EMAIL, new String[]{"bhobhriaaman333@gmail.com"});
                email.putExtra(Intent.EXTRA_SUBJECT, "Stylist Booking Query");
                email.putExtra(Intent.EXTRA_TEXT, queryTxt.getText().toString());

//need this to prompts email client only
                email.setType("message/rfc822");

                startActivity(Intent.createChooser(email, "Choose an Email client :"));
            }
        }


    }
}