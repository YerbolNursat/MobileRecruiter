package com.example.mobilerecruiter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.Toolbar;

import com.daimajia.swipe.util.Attributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;
public class Vacancies extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ArrayList<Vacancy> events ;
    RecyclerView rv;
    private FloatingActionButton fab;
    private SharedPreferences preferences;
    private Intent vacancy;
    Toolbar toolbar;
    private OnFragmentInteractionListener mListener;
    public Vacancies() {
        // Required empty public constructor
    }
 // TODO: Rename and change types and number of parameters
    public static Vacancies newInstance(String param1, String param2) {
        Vacancies fragment = new Vacancies();
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
        View view=inflater.inflate(R.layout.fragment_vacancies, container, false);
        fab = view.findViewById(R.id.vacancy_fab);
        rv=view.findViewById(R.id.vacancy_recycler_view);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setHasFixedSize(true);
        preferences = Objects.requireNonNull(getContext()).getSharedPreferences("myPrefs", MODE_PRIVATE);
        setData();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addVacancy();
            }
        });
        toolbar = view.findViewById(R.id.toolbar);
        toolbar.setSubtitle("Test Subtitle");
        toolbar.inflateMenu(R.menu.main_activity);
        return view ;
    }

    private void setData() {

        if(preferences.getBoolean("is_admin",false)) {
            NetworkService.getInstance()
                    .getJSONApi()
                    .getVacancies()
                    .enqueue(new Callback<List<Vacancy>>() {
                        @Override
                        public void onResponse(Call<List<Vacancy>> call, Response<List<Vacancy>> response) {
                            assert response.body() != null;
                            events = new ArrayList<>(response.body());
                            Vacancy_adapter adapter = new Vacancy_adapter(events);
                            adapter.setMode(Attributes.Mode.Single);
                            rv.setAdapter(adapter);
                        }

                        @Override
                        public void onFailure(Call<List<Vacancy>> call, Throwable t) {
                            t.printStackTrace();
                        }
                    });
        }else{
            NetworkService.getInstance()
                    .getJSONApi()
                    .getVacanciesById(preferences.getInt("id",1))
                    .enqueue(new Callback<List<Vacancy>>() {
                        @Override
                        public void onResponse(Call<List<Vacancy>> call, Response<List<Vacancy>> response) {
                            if(response.isSuccessful()){
                                assert response.body() != null;
                                events = new ArrayList<>(response.body());
                                Vacancy_adapter adapter = new Vacancy_adapter(events);
                                adapter.setMode(Attributes.Mode.Single);
                                rv.setAdapter(adapter);

                            }
                        }

                        @Override
                        public void onFailure(Call<List<Vacancy>> call, Throwable t) {
                            Log.d("Error", t.toString());
                        }
                    });
        }

    }


    private void addVacancy() {
        vacancy = new Intent(getActivity(), Vacancy_add.class);
        startActivity(vacancy);
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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setData();
    }
}
