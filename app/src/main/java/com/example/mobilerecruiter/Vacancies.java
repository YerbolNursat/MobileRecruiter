package com.example.mobilerecruiter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
 * {@link Vacancies.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Vacancies#newInstance} factory method to
 * create an instance of this fragment.
 */
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

    private Intent vacancy;


    private OnFragmentInteractionListener mListener;

    public Vacancies() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Vacancies.
     */
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
        NetworkService.getInstance()
                .getJSONApi()
                .getVacancies()
                .enqueue(new Callback<List<Vacancy>>() {
                    @Override
                    public void onResponse(Call<List<Vacancy>> call, Response<List<Vacancy>> response) {
                        assert response.body() != null;
                        events = new ArrayList<>(response.body());
                        Vacancy_adapter adapter=new Vacancy_adapter(events);
                        rv.setAdapter(adapter);
                    }
                    @Override
                    public void onFailure(Call<List<Vacancy>> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addVacancy();
            }
        });

        rv.addOnItemTouchListener(new RecyclerTouchListener(getContext(), rv, new ClickListener() {

            @Override
            public void onClick(View view, final int position) {

            }
            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        return view ;
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
