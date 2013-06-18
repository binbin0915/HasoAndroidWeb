package com.haso.hasoandroidweb;
import android.app.Activity;   
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;   
import android.view.KeyEvent;   
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;   
import android.webkit.WebViewClient;   
import android.widget.EditText;
   
public class MainActivity extends Activity {   
    private WebView webview;   
    private static final String URLSERVER="http://ip.haso-soft.com:13188/EMGRecommendation/phone!categorySearchByLat?Latitude=232144&Longitude=10722323719900";
    ProgressDialog progressDialog; // 进度条对话框
    @Override  
    public void onCreate(Bundle savedInstanceState) {   
        super.onCreate(savedInstanceState);   
        spfPreferences = getSharedPreferences(fileName, MODE_PRIVATE);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN ,
                              WindowManager.LayoutParams. FLAG_FULLSCREEN);
        setContentView(R.layout.main);   
        webview = (WebView) findViewById(R.id.webview);   
        //设置WebView属性，能够执行Javascript脚本   
        webview.getSettings().setJavaScriptEnabled(true);   
        //加载需要显示的网页  
        if("".equals(spfPreferences.getString("url", "")))
        {
        	 webview.loadUrl(URLSERVER);  
        }
        else
        {
        	webview.loadUrl(spfPreferences.getString("url", ""));  
        }
        
        //设置Web视图   
        webview.setWebViewClient(new HelloWebViewClient ());   
    }   
    
       
    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
    	 menu.add(0, 1, 0, "输入地址");
    	 menu.add(0, 2, 0, "刷新");
    	 menu.add(0, 3, 0, "退出");
    	 return true;
	}
    private SharedPreferences spfPreferences = null;
    private final String fileName = "spfDemofile.xml";
    /* Handles item selections */
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case 1:
        	final EditText input = new EditText(this);
        	input.setText(spfPreferences.getString("url", ""));
        	 new AlertDialog.Builder(this)
 			 
 			.setTitle("请输入地址：")
 			.setView(input)
 			.setPositiveButton("确定", 
 					new DialogInterface.OnClickListener(){

 				
 				public void onClick(DialogInterface dialog, int which) {
 					String value = input.getText().toString();
 	                
 					
 					
 					 SharedPreferences.Editor editor = spfPreferences.edit();
 		        	 editor.putString("url", value);
 		        	 editor.commit();
 		        	webview.loadUrl(value);   
 				}
 			
 			})
 			.setNegativeButton("取消", null).create().show();
        	
            return true;
        case 2:
        	webview.reload();
            return true;
        case 3:
        	 AlertDialog.Builder aa=new AlertDialog.Builder(this); 
             aa.setTitle("消息提示");
             aa.setMessage("确认退出？"); 
//             aa.setIcon(R.drawable.icon);
             aa.setPositiveButton("确定",new DialogInterface.OnClickListener() {
                 
                 @Override
                 public void onClick(DialogInterface dialog, int which) {
                     // TODO Auto-generated method stub
                     MainActivity.this.finish();
                 }
             });
             aa.setNegativeButton("取消", null);
             aa.create(); 
             aa.show(); 

            return true;
        }
        return false;
    }

	@Override  
    //设置回退   
    //覆盖Activity类的onKeyDown(int keyCoder,KeyEvent event)方法   
    public boolean onKeyDown(int keyCode, KeyEvent event) {   
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webview.canGoBack()) {   
            webview.goBack(); //goBack()表示返回WebView的上一页面   
            return true;   
        }   
        return false;   
    }   
       
    //Web视图   
    private class HelloWebViewClient extends WebViewClient {  
    	
    	
        @Override
		public void onPageFinished(WebView view, String url) {
        	progressDialog.dismiss();
			super.onPageFinished(view, url);
		}

		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			progressDialog = ProgressDialog.show(
					MainActivity.this, "请稍等...", "正在获取...", true);
			super.onPageStarted(view, url, favicon);
		}

		@Override  
        public boolean shouldOverrideUrlLoading(WebView view, String url) {   
            view.loadUrl(url);   
            return true;   
        }   
    }   
}  
