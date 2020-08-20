package com.example.p_recyclerview_detail;
/*
l   inearLayoutManager的三種建構式
    @param context       1.要呈現的主要頁面
    @param orientation   2.RecyclerView設定要左右滑還是上下滑的方向(HORIZONTAL/VERTICAL)
    @param reverseLayout 3.設定滑動開始的方向(falser是由左到右／true由右到左)
LinearLayoutManager(Context context,int orientation,boolean reverseLayout)：可以設定左右滑或上下滑,跟滑動起始位置的線性佈局

    @param orientation 設定要左右滑還是上下滑的方向(HORIZONTAL/VERTICAL)
linearLayoutManager.setOrientation(Orientation int orientation):RecyclerView設定要左右滑還是上下滑的方向(HORIZONTAL/VERTICAL);
  @param  true回收/fasle不回收
linearLayoutManager.setRecycleChildrenOnDetach(boolean recycleChildrenOnDetach):設定是否回收子類得LinerLauput,搭配RecycledViewPool使用在特定場景可提高效能
//LinearLayoutManager.findFirstCompletelyVisibleItemPosition(); //取得第一個完全顯示在螢幕內的view item位置
//LinearLayoutManager.findFirstVisibleItemPosition(); //取得第一個不完全顯示在螢幕內的view item位置
//LinearLayoutManager.findLastCompletelyVisibleItemPosition(); //取得最後一個完全顯示在螢幕內的view item位置
//LinearLayoutManager.findLastVisibleItemPosition(); //取得最後一個不完全顯示在螢幕內的view item位置

/*  ViewHolder
     getLayoutPosition(); //返回數據未刷新前view看到的Postion,當用戶做點即時考慮
     getAdapterPosition(); //使用場景為你更新數據,但在刷新前又要取得位置時使用
    //以下從未玩過
     getOldPosition()：這個看註釋說是用於處理動畫時用的，但還沒找到相關的場景，也沒理解具體有啥樣，後續再繼續研究。
     getItemId()：返回在“ adapter中通過getItemId（int position）為該項目生成的id，沒有在adapter轉換那個方法的話，就返回RecyclerView.NO_ID。
     getItemViewType()：返回在適配器中通過getItemViewType（）為該項目設置的類型，沒有在適配器上轉換那個方法的話，就是就是單一類型的項目類型。項目類型是用於實現不同的項目樣式。
     setIsRecyclable()：RecyclerView最大的特性就是它內部實現了一套高效的回收機制，而回收替代是ViewHolder為單位進行管理的，每個項目都會對應一個ViewHolder，都是都是會參與進回收 補充機制中。但可以通過該方法來標誌該ViewHolder不會被回收
 */

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.p_recyclerview_detail.linealayout.Movie;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView recyclerView;
    private List<Movie> moviess;
    private LinearLayoutManager linearLayoutManager;
    private Button btnRemove;
    private MovieRecyclerViewAdapter movieRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        setHorizontalRecyclerView();
    }

    private void init() {
        recyclerView = findViewById(R.id.recyclerView);
        btnRemove = findViewById(R.id.btnRemove);
        btnRemove.setOnClickListener(this);
        moviess = new ArrayList<>();
        moviess.add(new Movie("大話西游", "一個痛苦的愛情故事"));
        moviess.add(new Movie("喜劇之王", " 愛情喜劇故事"));
        moviess.add(new Movie("逃學威龍", "校園喜劇故事"));
        moviess.add(new Movie("逃學威龍", "校園喜劇故事"));
        moviess.add(new Movie("逃學威龍", "校園喜劇故事"));
        moviess.add(new Movie("逃學威龍", "校園喜劇故事"));
        moviess.add(new Movie("逃學威龍", "校園喜劇故事"));


    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void setHorizontalRecyclerView() {
        linearLayoutManager = new LinearLayoutManager(
                this,//1.要呈現的主要頁面
                LinearLayoutManager.VERTICAL, //2.RecyclerView設定要左右滑還是上下滑的方向(HORIZONTAL/VERTICAL)
                false  //3.設定滑動開始的方向(falser是由左到右／true由右到左)
        );


//      linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);//RecyclerView設定要左右滑還是上下滑的方向(HORIZONTAL/VERTICAL)
        linearLayoutManager.setRecycleChildrenOnDetach(true);//設定是否回收子類得LinerLauput,搭配RecycledViewPool使用在特定場景可提高效能

        movieRecyclerViewAdapter = new MovieRecyclerViewAdapter(moviess);
        recyclerView.setLayoutManager(linearLayoutManager);


        recyclerView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                int firstCompletelyVisibleItemPosition =
                        linearLayoutManager.findFirstCompletelyVisibleItemPosition(); //取得第一個完全顯示在螢幕內的view item位置
                int findFirstVisibleItemPosition =
                        linearLayoutManager.findFirstVisibleItemPosition(); //取得第一個不完全顯示在螢幕內的view item位置
                int findLastCompletelyVisibleItemPosition =
                        linearLayoutManager.findLastCompletelyVisibleItemPosition(); //取得最後一個完全顯示在螢幕內的view item位置
                int findLastVisibleItemPosition =
                        linearLayoutManager.findLastVisibleItemPosition(); //取得最後一個不完全顯示在螢幕內的view item位置
//                Log.v("hank",
//                        "firstCompletelyVisibleItemPosition:" + firstCompletelyVisibleItemPosition + "\n" +
//                                "findFirstVisibleItemPosition:" + findFirstVisibleItemPosition + "\n" +
//                                "findLastCompletelyVisibleItemPosition:" + findLastCompletelyVisibleItemPosition + "\n" +
//                                "findLastVisibleItemPosition:" + findLastVisibleItemPosition
//                ) ;

                //如果看到第五個item
                if (linearLayoutManager.findLastVisibleItemPosition() == 5) {

                    ViewGroup.LayoutParams layoutParams = recyclerView.getChildAt(0).getLayoutParams();
                    RecyclerView.ViewHolder viewHolder = recyclerView.getChildViewHolder(LayoutInflater.from(MainActivity.this).inflate(R.layout.linear_item,null));
                    layoutParams.width = (ScreenUtils.getScreenWidth(MainActivity.this) - DpPxUtils.dp2Px(MainActivity.this, 70)) / 3;//

                    dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_CANCEL, 0, 0, 0)); //強制停止滑動
                    Log.v("hank", "已出現超過5");
                    recyclerView.setNestedScrollingEnabled(false);
                    movieRecyclerViewAdapter.notifyDataSetChanged();
                } else {
                    Log.v("hank", "沒出現");

                }


            }

            ;
        });
        recyclerView.setAdapter(movieRecyclerViewAdapter);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnRemove:
                reMoveItemNotifyDataSetChanged();
                break;
        }
    }

    //1.刪除第一筆資料時,用notifyDataSetChanged更新資料,查看getLayoutPosition,getAdapterPosition的區別發現數據一樣,都可以取到值
    private void reMoveItemNotifyDataSetChanged() {
        moviess.remove(0);
        movieRecyclerViewAdapter.notifyDataSetChanged();
        logPosition();

        //已經刪除 getLayoutPosition馬上調用,還是出現0,1,2 =>代表他返回的是介面上刷新數據前的位置
        //已經刪除 getAdapterPosition馬上調用,還是出現-1,-1,-1 =>再重新製造ViewHolder之前馬上刷新去拿但沒有所以都-1
        //getLayoutPosition = 0  ,getAdapterPosition = -1
        //getLayoutPosition = 1  ,getAdapterPosition = -1
        //getLayoutPosition = 2  ,getAdapterPosition = -1

    }


    //2.刪除第一筆資料時,notifyItemRemoved(0)刪除並且更新,查看getLayoutPosition,getAdapterPosition的區別發現只有getLayoutPosition有值,getAdaterPostion並沒有值
    public void btnNotifyItemRemove(View view) {
//        movieRecyclerViewAdapter.notifyItemRemoved(0);
        movieRecyclerViewAdapter.notifyItemInserted(0);
        logPosition();

        //getLayoutPosition = 0  ,getAdapterPosition = -1
        //getLayoutPosition = 1  ,getAdapterPosition =  0
        //getLayoutPosition = 2  ,getAdapterPosition =  1
    }

    //3.
    public void btnNotifyItemRemovePost(View view) {
        movieRecyclerViewAdapter.notifyItemRemoved(0);
        recyclerView.post(new Runnable() {
            @Override
            public void run() {
                LogUtils.v("recyclerView.post");
                logPosition();
            }
        });

        //getLayoutPosition = 0  ,getAdapterPosition =  0
        //getLayoutPosition = 1  ,getAdapterPosition =  1
        //getLayoutPosition = 2  ,getAdapterPosition =  2
        //getLayoutPosition = -1  ,getAdapterPosition =  -1
    }

    //取得getLayoutPosition,getAdapterPosition的值
    private void logPosition() {
        for (int i = 0; i < recyclerView.getChildCount(); i++) {
            View view = recyclerView.getChildAt(i);
            int layPosi = recyclerView.findContainingViewHolder(view)
                    .getLayoutPosition(); //返回數據未刷新前view看到的Postion,當用戶做點即時考慮
            int adapterPosi = recyclerView.findContainingViewHolder(view)
                    .getAdapterPosition(); //使用場景為你更新數據,但在刷新前又要取得位置時使用
            int oldPosi = recyclerView.findContainingViewHolder(view).getOldPosition();
            LogUtils.v("logPosition => getLayoutPosition = " + layPosi);
            LogUtils.v("logPosition => getAdapterPosition = " + adapterPosi);
        }
    }

    public void btnGetAdapter(View view) {
        int getItemCount = movieRecyclerViewAdapter.getItemCount(); //取得adapter李資料的總比數
        long getItemId = movieRecyclerViewAdapter.getItemId(recyclerView.getChildAdapterPosition(recyclerView.getChildAt(0)));
        int getItemViewType = movieRecyclerViewAdapter.getItemViewType(recyclerView.getChildAdapterPosition(recyclerView.getChildAt(0)));

        LogUtils.v("getItemCount:" + getItemCount + "/getItemId:" + getItemId + "/getItemViewType:" + getItemViewType);
    }


    public class MovieRecyclerViewAdapter extends RecyclerView.Adapter<MovieViewHolder> {
        private List<Movie> movies;

        public MovieRecyclerViewAdapter(List<Movie> movies) {
            this.movies = movies;
        }

        @NonNull
        @Override
        public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.linear_item, parent, false);
            MovieViewHolder movieViewHolder = new MovieViewHolder(view);
            LogUtils.v("onCreateViewHolder");
            return movieViewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {


            holder.tvMovieName.setText(movies.get(position).getName());
            holder.tvMovieContent.setText(movies.get(position).getContent());
            LogUtils.v("onBindViewHolder" + "/position:" + position);
            int getItemCount = movieRecyclerViewAdapter.getItemCount(); //取得adapter李資料的總比數
            int getItemViewType = movieRecyclerViewAdapter.getItemViewType(position);

            LogUtils.v("onBindViewHolder => getItemCount:" + getItemCount + "/getItemId:" + "/getItemViewType:" + getItemViewType);

        }

        //取得adapter裡資料的總比數
        @Override
        public int getItemCount() {
            return movies.size();
        }



        @Override
        public long getItemId(int position) {
            LogUtils.v("getItemId:" + position);
            return super.getItemId(position);
        }

    }


    public class MovieViewHolder extends RecyclerView.ViewHolder {
        private TextView tvMovieName, tvMovieContent;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            LogUtils.v("MovieViewHolder");
            tvMovieName = itemView.findViewById(R.id.tvMovieName);
            tvMovieContent = itemView.findViewById(R.id.tvContent);
        }
    }


}
