package co.edu.udea.cmovil.gr7.yamba;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import com.thenewcircle.YambaClientLib.yamba-client;

public class StatusActivity extends AppCompatActivity implements View.OnClickListener{
    private Button mButtonTweet;
    private EditText mTextStatus;
    private TextView mTextCount;
    private int mDefaultColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);
        mButtonTweet = (Button) findViewById(R.id.status_button_tweet);
        mTextStatus = (EditText) findViewById(R.id.status_text);
        mTextCount =  (TextView) findViewById(R.id.status_text_count);

        mTextCount.setText("140");
        mDefaultColor = mTextCount.getTextColors().getDefaultColor();
        Log.d("Yamba","onCreate");
    }

    public void postear(View v){
        String status = mTextStatus.getText().toString();
        PostTask postTask = new PostTask();
        postTask.execute(status);
        Log.d("TAG", "onClicked");
    }


    class PostTask extends AsyncTask<String,Void,String>{
        private ProgressDialog progress;
        @Override
        protected void onPreExecute(){

        }

        protected String doInBackground(String...params){
            try{
                YambaClient cloud = new YambaClient("student","password");
                cloud.postStatus(params[0]);
                Log.d("Yamba","se posteo satisfactoriamente: "+params[0]);
                return "posteo satisfactorio";
            }catch (Exception e){
                Log.e("Yamba","fallo el posteo", e);
                e.printStackTrace();
                return "fallo el posteo";
            }
        }

        @Override
        protected void onPostExecute(String result){
            progress.dismiss();
            if (this != null && result!=null){
                Toast.makeText(StatusActivity.this,result,Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_status, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
