package kr.ac.jejunu.portal_front.Activity;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import kr.ac.jejunu.portal_front.CommonData;
import kr.ac.jejunu.portal_front.R;
import kr.ac.jejunu.portal_front.task.OnTaskResultListener;
import kr.ac.jejunu.portal_front.task.get.ReadMyDiaryTask;
import kr.ac.jejunu.portal_front.vo.DiaryVO;

public class DiaryListActivity extends AppCompatActivity implements OnTaskResultListener<List<DiaryVO>>{
    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_my);

        initRecyclerView();
        executeTask();
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recycler_my_diary);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new MyAdapter();
        recyclerView.setAdapter(adapter);

        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(getApplicationContext(),new LinearLayoutManager(this).getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
    }

    private void executeTask() {
        ReadMyDiaryTask task = new ReadMyDiaryTask(this);
        task.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, ((CommonData)getApplication()).getUserId(), "0");
    }

    @Override
    public void onTaskResult(List<DiaryVO> result) {
        Log.e(this.toString(), "onTaskResult: " +  result.size() );
        adapter.addVOs(result);
    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{
        private List<DiaryVO> vos;

        MyAdapter(){
            this.vos = new ArrayList<>();
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = View.inflate(parent.getContext(), R.layout.item_diary, null);

            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            holder.txtTitle.setText(vos.get(position).getTitle());
            holder.txtWriteDate.setText(vos.get(position).getWriteDate());
        }

        @Override
        public int getItemCount() {
            return vos.size();
        }

        void addVOs(List<DiaryVO> vos){
            this.vos.addAll(vos);
            notifyDataSetChanged();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            private TextView txtTitle;
            private TextView txtWriteDate;

            public MyViewHolder(View itemView) {
                super(itemView);

                txtTitle = itemView.findViewById(R.id.txt_title);
                txtWriteDate = itemView.findViewById(R.id.txt_write_date);
            }
        }
    }
}
