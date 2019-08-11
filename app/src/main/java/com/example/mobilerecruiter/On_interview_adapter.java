package com.example.mobilerecruiter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;
public class On_interview_adapter extends RecyclerSwipeAdapter<On_interview_adapter.MyViewHolder> {
    private ArrayList<Post> post;
    View view;
    private SimpleDateFormat mSimpleDateFormat;
    private Calendar mCalendar;
    private Activity mActivity;
    private SharedPreferences preferences;
    private long diffDays;
    private int pos;
    private MyViewHolder Holder;
    public On_interview_adapter(ArrayList<Post> post){this.post=post;}
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.on_interview_info,viewGroup,false);
        preferences = Objects.requireNonNull(viewGroup.getContext()).getSharedPreferences("myPrefs", MODE_PRIVATE);
        mSimpleDateFormat = new SimpleDateFormat("YYYY-MM-dd hh:mm:ss", Locale.getDefault());
        mActivity= (Activity) view.getContext();
        return new MyViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int position) {
        myViewHolder.name_surname.setText(post.get(position).getF_name()+" "+post.get(position).getL_name());
        myViewHolder.post_skills.setText("");
        for (int i=0;i<post.get(position).getSkills().size();i++){
            myViewHolder.post_skills.setText(myViewHolder.post_skills.getText()+" "+
                    post.get(position).getSkills().get(i));
        }

        if(!preferences.getBoolean("is_admin",false)){
            myViewHolder.decline.setVisibility(View.GONE);
            myViewHolder.schedule.setVisibility(View.GONE);
            myViewHolder.accept.setVisibility(View.GONE);
        }

        if(post.get(position).getInterview_time()!=null) {
            myViewHolder.schedule.setText("Change Time");
            setStatus(myViewHolder,position);
        }else {
            myViewHolder.status.setText("Invite him");
        }
        myViewHolder.swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);
        myViewHolder.swipeLayout.addDrag(SwipeLayout.DragEdge.Right, myViewHolder.swipeLayout.findViewById(R.id.bottom_wraper));
        myViewHolder.swipeLayout.addDrag(SwipeLayout.DragEdge.Left, myViewHolder.swipeLayout.findViewById(R.id.bottom_wraper1));
        myViewHolder.swipeLayout.getSurfaceView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), Post_page.class);
                intent.putExtra("id",String.valueOf(post.get(position).getId()));
                view.getContext().startActivity(intent);
            }
        });
        myViewHolder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(preferences.getBoolean("is_admin",false)) {
                }else {
                    Toast.makeText(view.getContext(), "You don't have access", Toast.LENGTH_SHORT).show();
                }
            }
        });


        myViewHolder.dislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "Edit will be near future", Toast.LENGTH_SHORT).show();
            }
        });
        myViewHolder.schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCalendar = Calendar.getInstance();
                pos=position;
                Holder=myViewHolder;
                DatePickerDialog datePicker=new DatePickerDialog(mActivity, mDateDataSet, mCalendar.get(Calendar.YEAR),
                        mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH));
                datePicker.show();

            }
        });

        myViewHolder.accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NetworkService.getInstance()
                        .getJSONApi()
                        .putPost(post.get(position).getId(),post.get(position).getF_name(),post.get(position).getL_name(),post.get(position).getMail(),
                                post.get(position).getTelephon_number(),post.get(position).getCv_file_name(),post.get(position).getVacancy_id(),
                                1,1,1,0,null)
                        .enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                if (response.isSuccessful()){
                                    Toast.makeText(view.getContext(), "You accepted candidate", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                Log.d("Error", t.toString());
                                Toast.makeText(view.getContext(), "Something wrong...", Toast.LENGTH_SHORT).show();
                            }
                        });

            }
        });
        myViewHolder.decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                NetworkService.getInstance()
                        .getJSONApi()
                        .putPost(post.get(position).getId(),post.get(position).getF_name(),post.get(position).getL_name(),post.get(position).getMail(),
                                post.get(position).getTelephon_number(),post.get(position).getCv_file_name(),1,
                                0,0,0,0,null)
                        .enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                if (response.isSuccessful()){
                                    Toast.makeText(view.getContext(), "You decline candidate", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                Log.d("Error", t.toString());
                                Toast.makeText(view.getContext(), "Something wrong...", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
        mItemManger.bindView(myViewHolder.itemView, position);
    }
    @Override
    public int getItemCount() {
        return post.size();
    }
    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.on_interview_info_swipe;
    }
    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView name_surname,status,post_skills,schedule,like,dislike,decline,accept;
        SwipeLayout swipeLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            swipeLayout=itemView.findViewById(R.id.on_interview_info_swipe);
            name_surname=itemView.findViewById(R.id.on_interview_f_name_and_l_name);
            post_skills=itemView.findViewById(R.id.on_interview_skills);
            schedule=itemView.findViewById(R.id.on_interview_schedule);
            like=itemView.findViewById(R.id.on_interview_like);
            dislike=itemView.findViewById(R.id.on_interview_dislike);
            decline=itemView.findViewById(R.id.on_interview_decline);
            status=itemView.findViewById(R.id.on_interview_status);
            accept=itemView.findViewById(R.id.on_interview_accept);
        }
    }
    final DatePickerDialog.OnDateSetListener mDateDataSet = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            mCalendar.set(Calendar.YEAR, year);
            mCalendar.set(Calendar.MONTH, monthOfYear);
            mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            new TimePickerDialog(mActivity, mTimeDataSet, mCalendar.get(Calendar.HOUR_OF_DAY), mCalendar.get(Calendar.MINUTE), false).show();
        }
    };
     final TimePickerDialog.OnTimeSetListener mTimeDataSet = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            mCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
            mCalendar.set(Calendar.MINUTE, minute);
            String format=mSimpleDateFormat.format(mCalendar.getTime());
            setInterviewDay(format);
            System.out.println(format);
        }
    };
    private void setStatus(MyViewHolder myViewHolder, int position) {
        Date date = new Date();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String dateStart = post.get(position).getInterview_time().toString().substring(0, post.get(position).getInterview_time().toString().length() - 2).replaceAll("-", "/");
        String dateStop = format.format(date);
        Date d1 = null;
        Date d2 = null;
        try {
            d1 = format.parse(dateStart);
            d2 = format.parse(dateStop);
            long diff = d1.getTime() - d2.getTime();
            diffDays = diff / (24 * 60 * 60 * 1000);
            if (diffDays<0){
                myViewHolder.status.setText("Over");
            }else if (diffDays < 1) {
                myViewHolder.status.setText("Today");
            }else if (diffDays<2){
                myViewHolder.status.setText("Tomorrow");
            }else if (diffDays<7){
                myViewHolder.status.setText("At this week");
            }else if (diffDays<31){
                myViewHolder.status.setText("At this month");
            }else {
                myViewHolder.status.setText("In the Future");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void setInterviewDay(final String format) {
        NetworkService.getInstance()
                .getJSONApi()
                .putPost(post.get(pos).getId(),post.get(pos).getF_name(),post.get(pos).getL_name(),post.get(pos).getMail(),
                        post.get(pos).getTelephon_number(),post.get(pos).getCv_file_name(),post.get(pos).getVacancy_id(),
                        1,1,0,0,format)
                .enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()){
                            post.get(pos).setInterview_time(Timestamp.valueOf(format));
                            setStatus(Holder,pos);
                            sendMessage(format);
                        }
                    }
                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Log.d("Error", t.toString());
                        Toast.makeText(view.getContext(), "Something wrong...", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private void sendMessage(String format) {
        String text ="Hello" + Holder.name_surname.getText()+"\n"+
                "We are invite you for an interview at date "+format;
        String subject="Interview";
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        String[] recipients={post.get(pos).getMail()};
        emailIntent.putExtra(Intent.EXTRA_EMAIL, recipients);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, text);
        emailIntent.setType("message/rfc822");
        emailIntent.setPackage("com.google.android.gm");
        view.getContext().startActivity(Intent.createChooser(emailIntent, "Send mail"));
    }
}
