package kr.ac.jejunu.portal_front.Activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import kr.ac.jejunu.portal_front.CommonData;
import kr.ac.jejunu.portal_front.R;
import kr.ac.jejunu.portal_front.task.OnTaskResultListener;
import kr.ac.jejunu.portal_front.task.get.GetDiaryByIdTask;
import kr.ac.jejunu.portal_front.task.get.GetRandomDiaryListTask;
import kr.ac.jejunu.portal_front.vo.DiaryVO;

public class OthersDiaryListActivity extends AppCompatActivity implements OnTaskResultListener<List<DiaryVO>> {
    private MyAdapter adapter;
    private boolean check = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_my);

        ((TextView) findViewById(R.id.txt_diary)).setText("익명 일기 리스트");

        initRecyclerView();

        check = ((CommonData) getApplication()).dateCheck(CommonData.getCurrentDay());

        if (!check) {
            executeTask();
        } else {
            List<String> ids = ((CommonData) getApplication()).getOtherDiaryIdList(CommonData.getCurrentDay());
            GetDiaryByIdTask task = new GetDiaryByIdTask(result -> {
                Log.e("fdf", "onCreate: " + ((List<DiaryVO>) result).size() );
                adapter.addVOs((List<DiaryVO>) result);
            });
            task.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, (String[])ids.toArray(new String[ids.size()]));
        }
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recycler_my_diary);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new MyAdapter();
        recyclerView.setAdapter(adapter);

        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(getApplicationContext(), new LinearLayoutManager(this).getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
    }

    private void executeTask() {
        GetRandomDiaryListTask task = new GetRandomDiaryListTask(this);
        task.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, ((CommonData) getApplication()).getUserId());
    }

    @Override
    public void onTaskResult(List<DiaryVO> result) {
        Log.e(this.toString(), "onTaskResult: " + result.size());

        ((CommonData) getApplication()).setOthersDiaryList(result, CommonData.getCurrentDay());

        adapter.addVOs(result);
    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
        private List<DiaryVO> vos;

        MyAdapter() {
            this.vos = new ArrayList<>();
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_diary, parent, false);

            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            holder.txtTitle.setText(vos.get(position).getTitle());
            holder.txtWriteDate.setText(vos.get(position).getWriteDate());
            holder.itemView.setOnClickListener(v -> {
                Intent intent = new Intent(OthersDiaryListActivity.this, ReadOthersDiaryActivity.class);
                intent.putExtra("title", vos.get(position).getTitle());
                intent.putExtra("story", vos.get(position).getStory());
                intent.putExtra("diaryId", vos.get(position).getId());
                startActivity(intent);
            });
        }

        @Override
        public int getItemCount() {
            return vos.size();
        }

        void addVOs(List<DiaryVO> vos) {
            this.vos.addAll(vos);
            notifyDataSetChanged();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            private TextView txtTitle;
            private TextView txtWriteDate;
            private View itemView;

            MyViewHolder(View itemView) {
                super(itemView);

                this.itemView = itemView;

                txtTitle = itemView.findViewById(R.id.txt_title);
                txtWriteDate = itemView.findViewById(R.id.txt_write_date);
            }
        }
    }
}