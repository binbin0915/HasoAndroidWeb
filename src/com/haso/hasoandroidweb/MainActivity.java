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
    ProgressDialog progressDialog; // �������Ի���
    @Override  
    public void onCreate(Bundle savedInstanceState) {   
        super.onCreate(savedInstanceState);   
        spfPreferences = getSharedPreferences(fileName, MODE_PRIVATE);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN ,
                              WindowManager.LayoutParams. FLAG_FULLSCREEN);
        setContentView(R.layout.main);   
        webview = (WebView) findViewById(R.id.webview);   
        //����WebView���ԣ��ܹ�ִ��Javascript�ű�   
        webview.getSettings().setJavaScriptEnabled(true);   
        //������Ҫ��ʾ����ҳ  
        if("".equals(spfPreferences.getString("url", "")))
        {
        	 webview.loadUrl(URLSERVER);  
        }
        else
        {
        	webview.loadUrl(spfPreferences.getString("url", ""));  
        }
        
        //����Web��ͼ   
        webview.setWebViewClient(new HelloWebViewClient ());   
    }   
    
       
    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
    	 menu.add(0, 1, 0, "�����ַ");
    	 menu.add(0, 2, 0, "ˢ��");
    	 menu.add(0, 3, 0, "�˳�");
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
 			 
 			.setTitle("�������ַ��")
 			.setView(input)
 			.setPositiveButton("ȷ��", 
 					new DialogInterface.OnClickListener(){

 				
 				public void onClick(DialogInterface dialog, int which) {
 					String value = input.getText().toString();
 	                
 					
 					
 					 SharedPreferences.Editor editor = spfPreferences.edit();
 		        	 editor.putString("url", value);
 		        	 editor.commit();
 		        	webview.loadUrl(value);   
 				}
 			
 			})
 			.setNegativeButton("ȡ��", null).create().show();
        	
            return true;
        case 2:
        	webview.reload();
            return true;
        case 3:
        	 AlertDialog.Builder aa=new AlertDialog.Builder(this); 
             aa.setTitle("��Ϣ��ʾ");
             aa.setMessage("ȷ���˳���"); 
//             aa.setIcon(R.drawable.icon);
             aa.setPositiveButton("ȷ��",new DialogInterface.OnClickListener() {
                 
                 @Override
                 public void onClick(DialogInterface dialog, int which) {
                     // TODO Auto-generated method stub
                     MainActivity.this.finish();
                 }
             });
             aa.setNegativeButton("ȡ��", null);
             aa.create(); 
             aa.show(); 

            return true;
        }
        return false;
    }

	@Override  
    //���û���   
    //����Activity���onKeyDown(int keyCoder,KeyEvent event)����   
    public boolean onKeyDown(int keyCode, KeyEvent event) {   
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webview.canGoBack()) {   
            webview.goBack(); //goBack()��ʾ����WebView����һҳ��   
            return true;   
        }   
        return false;   
    }   
       
    //Web��ͼ   
    private class HelloWebViewClient extends WebViewClient {  
    	
    	
        @Override
		public void onPageFinished(WebView view, String url) {
        	progressDialog.dismiss();
			super.onPageFinished(view, url);
		}

		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			progressDialog = ProgressDialog.show(
					MainActivity.this, "���Ե�...", "���ڻ�ȡ...", true);
			super.onPageStarted(view, url, favicon);
		}

		@Override  
        public boolean shouldOverrideUrlLoading(WebView view, String url) {   
            view.loadUrl(url);   
            return true;   
        }   
    }   
}  
