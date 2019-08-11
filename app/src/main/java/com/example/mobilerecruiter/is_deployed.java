package com.example.mobilerecruiter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link is_deployed.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link is_deployed#newInstance} factory method to
 * create an instance of this fragment.
 */
public class is_deployed extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ArrayList<Post> events ;
    RecyclerView rv;
    Toolbar toolbar;
    private Intent candidate;
    private OnFragmentInteractionListener mListener;
    private SharedPreferences preferences;

    public is_deployed() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment is_deployed.
     */
    // TODO: Rename and change types and number of parameters
    public static is_deployed newInstance(String param1, String param2) {
        is_deployed fragment = new is_deployed();
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
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_is_deployed, container, false);
        preferences = Objects.requireNonNull(getContext()).getSharedPreferences("myPrefs", MODE_PRIVATE);
        rv=view.findViewById(R.id.is_deployed_recycler_view);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setHasFixedSize(true);
        toolbar = view.findViewById(R.id.toolbar);
        toolbar.setSubtitle("Test Subtitle");
        toolbar.inflateMenu(R.menu.main_activity);
        return view ;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }
    public void onStart() {
        super.onStart();
        setData();
    }
    private void setData(){
        if(preferences.getBoolean("is_admin",false)) {

            NetworkService.getInstance()
                    .getJSONApi()
                    .getPosts()
                    .enqueue(new Callback<List<Post>>() {
                        @Override
                        public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                            assert response.body() != null;
                            events = new ArrayList<>(response.body());
                            for (int i=0;i<events.size();i++){
                                if(events.get(i).getPassed_interview()!=1||events.get(i).getIs_deployed()==1){
                                    events.remove(i);
                                    i--;
                                }
                            }
                            is_deployed_adapter adapter = new is_deployed_adapter(events);
                            rv.setAdapter(adapter);
                        }

                        @Override
                        public void onFailure(Call<List<Post>> call, Throwable t) {
                            t.printStackTrace();
                        }
                    });
        }
        else {
            NetworkService.getInstance()
                    .getJSONApi()
                    .getPostsById(preferences.getInt("id",1))
                    .enqueue(new Callback<List<Post>>() {
                        @Override
                        public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                            if(response.isSuccessful()) {
                                assert response.body() != null;
                                events = new ArrayList<>(response.body());
                                for (int i=0;i<events.size();i++){
                                    if(events.get(i).getPassed_interview()!=1||events.get(i).getIs_deployed()==1){
                                        events.remove(i);
                                        i--;
                                    }
                                }
                                is_deployed_adapter adapter = new is_deployed_adapter(events);
                                rv.setAdapter(adapter);
                            }
                        }

                        @Override
                        public void onFailure(Call<List<Post>> call, Throwable t) {
                            Log.d("Error", t.toString());
                        }
                    });
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
