package com.example.mobilerecruiter;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link On_customers.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link On_customers#newInstance} factory method to
 * create an instance of this fragment.
 */
public class On_customers extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ArrayList<Post> events ;
    RecyclerView rv;

    private OnFragmentInteractionListener mListener;

    public On_customers() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static On_customers newInstance(String param1, String param2) {
        On_customers fragment = new On_customers();
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
        View view=inflater.inflate(R.layout.fragment_on_customer, container, false);
        rv=view.findViewById(R.id.on_customer_recycler_view);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setHasFixedSize(true);
        NetworkService.getInstance()
                .getJSONApi()
                .getPosts()
                .enqueue(new Callback<List<Post>>() {
                    @Override
                    public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                        assert response.body() != null;
                        events = new ArrayList<>(response.body());
                        On_customer_adapter adapter=new On_customer_adapter(events);
                        rv.setAdapter(adapter);
                    }
                    @Override
                    public void onFailure(Call<List<Post>> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
        return view ;
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
