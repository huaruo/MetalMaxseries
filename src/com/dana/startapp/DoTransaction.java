package com.dana.startapp;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.dana.constant.Constant;

public class DoTransaction extends Activity
{
	//Debug
	private static final String TAG = "DoTransaction";

	private String transName;
	private TextView dspTransType;
	private EditText inputAmt;
	private EditText inputCardNo;

	private EditText expiry;
	private Button swipeCard;
	private Button scan_btn;
	private Button nextStep;
	
	private LinearLayout llAmount, llCardNo, llInstallment, llCVD;
	private EditText itemNo;
	private TextView myTextView, manager_tv1, manager_tv2;
	private Spinner mySpinner;
	private ArrayAdapter<String> adapter;
	
	private ArrayList<String> scanList = new ArrayList<String>();
	private MySimpleAdapter mSimpleAdapter;
	
	private int m =0;
	private static final String[] item = {"һ	��		, 1, 0 ","��	��		, 2, 0 ", "һ	��		, 4, 0 "};

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dotransaction);

//		Intent intent = getIntent();
//		int transNo = intent.getExtras().getInt("TransNo");
		
		int transNo = 69;
	
		
		dspTransType = (TextView) findViewById(R.id.dsp_trans_type);
		inputAmt = (EditText) findViewById(R.id.input_amount);
		inputCardNo = (EditText) findViewById(R.id.input_card_no);
		expiry = (EditText) findViewById(R.id.expiry);
		scan_btn = (Button) findViewById(R.id.btn_scan);
		swipeCard = (Button) findViewById(R.id.swipe_card);
		nextStep = (Button) findViewById(R.id.next_step1);
		
		llAmount = (LinearLayout)findViewById(R.id.ll_amount);
		llCardNo = (LinearLayout)findViewById(R.id.ll_cardno);
		llInstallment = (LinearLayout) findViewById(R.id.ll_installment);
		llCVD = (LinearLayout) findViewById(R.id.ll_cvd);
		myTextView = (TextView)findViewById(R.id.text_spinner);
		mySpinner = (Spinner)findViewById(R.id.spinner_Item);
		itemNo = (EditText) findViewById(R.id.item_no);
		
		
		llAmount.setVisibility(View.VISIBLE);
		llCardNo.setVisibility(View.VISIBLE);
		llCVD.setVisibility(View.VISIBLE);
		
		switch(transNo)
		{
			case 1:
				Log.i(TAG,"TransType:");
				llInstallment.setVisibility(View.VISIBLE);
				itemNo.setHint("������");
				//����adapter
				adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, item);
				//��������Ϊ���������������б�����ʱ�Ĳ˵���ʽ�� 
		        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); 
		        //���Ĳ�������������ӵ������б��� 
		        mySpinner.setAdapter(adapter); 
		        //���岽��Ϊ�����б����ø����¼�����Ӧ���������Ӧ�˵���ѡ�� 
		        mySpinner.setOnItemSelectedListener(itemSelectedListener);
		        /*�����˵�����������ѡ����¼�����*/ 
		        mySpinner.setOnTouchListener(touchListener);
		        /*�����˵�����������ѡ���ı��¼�����*/ 
		        mySpinner.setOnFocusChangeListener(focusChangeListener); 
		        break;
		     default:
		    	 break;
		}

		dspTransType.setText(transName);
		
		
		inputAmt.addTextChangedListener(new TextWatcher(){
        	int l=0; //��¼�ַ�������ǰ�ĳ���
        	int location = 0;//��¼���λ��
        	String strBefore="";//����ǰ�ַ���
        	
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				l = s.length();
				strBefore = s.toString();
				location = inputAmt.getSelectionStart();
			}
        	
			
			
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				
			}
			
			
			public void afterTextChanged(Editable s) {
				String str = s.toString();
				amountRule(inputAmt,str,strBefore,location,l);
			}
        	
        });
		scan_btn.setOnClickListener(listener);
		swipeCard.setOnClickListener(listener);
		nextStep.setOnClickListener(listener);
		
	}

	private OnClickListener listener = new OnClickListener()
	{
		public void onClick(View v)
		{
			switch(v.getId())
			{
				case R.id.next_step1:
					break;
				case R.id.swipe_card:
					dialog();
					break;
				case R.id.btn_scan:
					/**��ɨ��ǹ
					if()
					{
					
					}
					**/
					scanList();
					break;
				
				default:
					break;
			}
		}
	};
	
	private void dialog()
    {
    	//��ʼ��Dialog
    	LayoutInflater inflater = getLayoutInflater();
    	final View layout = inflater.inflate(R.layout.dialog, (ViewGroup) findViewById(R.id.dialog_ll));
    	
    	AlertDialog.Builder builder = new Builder(DoTransaction.this);
    	builder.setTitle("  ");
    	builder.setView(layout);
    	
    	//��ʼ���ı���
    	manager_tv1 = (TextView) layout.findViewById(R.id.dialog_tv1);
    	manager_tv1.setVisibility(View.VISIBLE);
    	manager_tv1.setText("    ��ˢ��...");
    	manager_tv1.setTextSize(32);
    	manager_tv1.setTextColor(getResources().getColor(R.color.tan));
    	manager_tv2 = (TextView) layout.findViewById(R.id.dialog_tv2);
    	manager_tv2.setVisibility(View.VISIBLE);
    	manager_tv2.setText("  ");
    	manager_tv2.setTextSize(48);
    	final AlertDialog alert = builder.create();
    	alert.setCanceledOnTouchOutside(true);
    	alert.requestWindowFeature(Window.FEATURE_NO_TITLE);
    	alert.show();
    	//���ô��ڵĴ�С  
    	alert.getWindow().setLayout(500, 500);  
 /*   	WindowManager.LayoutParams params = alert.getWindow().getAttributes();
    	params.width = 300;
    	params.height = 400;
    	alert.getWindow().setAttributes(params);
*/
    	//����home��
 //   	alert.getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD);
    	final Timer t = new Timer(); 
    	t.schedule(new TimerTask() {
    		public void run() { 
    			alert.dismiss(); // when the task active then close the dialog 
    			t.cancel(); // also just top the timer thread, otherwise, you may receive a crash report 
    			}
    		}, 2000); // after 2 second (or 2000 miliseconds), the task will be active. 
    }
	
	private void scanList()
	{
		Log.i(TAG, "scanList");
		//����Dialog
		LayoutInflater inflater = getLayoutInflater();
		
		View view = inflater.inflate(R.layout.list_view, null);
		AlertDialog.Builder builder = new Builder(DoTransaction.this);
		builder.setView(view);
		
		ListView listView = (ListView) view.findViewById(R.id.list);
		listView.setBackgroundColor(Color.parseColor("#FFFFFF"));
		ArrayList<HashMap<String,Object>> listItem = new ArrayList<HashMap<String, Object>>();
		
		//if()//ɨ�赽����
//		scanValue[m] = String.valueOf(m); //ɨ�������
		scanList.add(m, String.valueOf(m));
		m++;
		
		if(m !=0)
		{
			for (int i=0; i<m; i++)
			{
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("ItemImage", R.drawable.image);
				map.put("ItemTitle", scanList.get(i));
				map.put("LastImage", R.drawable.image);
				
				listItem.add(map);
			}	
		}
		else
		{
			for (int i=0; i<7; i++)
			{
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("ItemImage", "");
				map.put("ItemTitle", "");
				map.put("LastImage", "");				
				listItem.add(map);
			}	
		}
		
				//������������Item�Ͷ�̬�����Ӧ��Ԫ��
		mSimpleAdapter = new MySimpleAdapter(DoTransaction.this, listItem, //����Դ
						R.layout.customlist_item, //ListItem��XMLʵ��
						//��̬������ImageItem��Ӧ������
						new String[] {"ItemImage", "ItemTitle", "LastImage"},
						//ImageItem��XML�ļ������һ��ImageView, ����TextView ID
						new int[] {R.id.cli_itemimg, R.id.cli_bigitemtitle, R.id.cli_lastimg}
						);
				//��Ӳ�����ʾ
				listView.setAdapter(mSimpleAdapter);
				
				Dialog scanListDialog = builder.create();
				scanListDialog.setTitle("ɨ���б�:");
				scanListDialog.show();
	}
	
	private class MySimpleAdapter extends BaseAdapter
	{
		private LayoutInflater mInflater;
		private ArrayList<HashMap<String,Object>> list;
		private int layoutID;
		private String[] flag;
		private int[] ItemIDs;
		
		public MySimpleAdapter(Context context, ArrayList<HashMap<String,Object>> list, int layoutID, String[] flag, int[] ItemIDs)
		{
			this.mInflater = LayoutInflater.from(context);
			this.list = list;
			this.layoutID = layoutID;
			this.flag = flag;
			this.ItemIDs = ItemIDs;
		}
		
		@Override
		public int getCount()
		{
			return list.size();
		}
		
		@Override
		public Object getItem(int arg0)
		{
			return arg0;
		}
		
		@Override
		public long getItemId(int arg0)
		{
			return arg0;
		}
		
		@Override
		public View getView(final int position, View convertView, ViewGroup parent)
		{
			if(convertView == null){
				convertView = mInflater.inflate(layoutID, null);
			}
			
			for (int i = 0; i < flag.length; i++) {//��ע1  
				if (convertView.findViewById(ItemIDs[i]) instanceof ImageView) {  
					ImageView iv = (ImageView) convertView.findViewById(ItemIDs[i]);  
					iv.setBackgroundResource((Integer) list.get(position).get(  
				         flag[i]));  
				 } 
				else if (convertView.findViewById(ItemIDs[i]) instanceof TextView) {  
					TextView tv = (TextView) convertView.findViewById(ItemIDs[i]);  
					tv.setText((String) list.get(position).get(flag[i]));  
				}
				 else{
				}
				}
			
			
			addListener(convertView, position);
			return convertView;
		}
		
		public void addListener(View convertView, final int position)
		{
			ImageView del = (ImageView) convertView.findViewById(R.id.cli_lastimg);
			del.setClickable(true);
			
			del.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					//�Ӽ�����ɾ����ɾ�����EditText������
					scanList.remove(position);
					m--;
					list.remove(position);
					mSimpleAdapter.notifyDataSetChanged();
				}
			});
		}
	}

	/**
	 * @param <T> �����е�Ԫ������
	 * @param arrs ��Ҫɾ��Ԫ�ص����顣
	 * @param index ��Ҫɾ����Ԫ�ص�����������ʱ���쳣����
	 * @return ָ�����͵������顣
	 */
	public static <T> T[] removeArrayItem(T[] arrs, int index) {
	    int len = arrs.length;
	    if(index < 0 || index >= len) {
	        throw new IllegalArgumentException("��������");
	    }
	    List<T> list = new LinkedList<T>();
	    for(int i = 0; i < len; i++) {
	        if(i != index) {
	            list.add(arrs[i]);
	        }
	    }
	    // ���ｫ�ı䴫���������� arrs ��ֵ
	    arrs = list.toArray(arrs);
	    return java.util.Arrays.copyOf(arrs, arrs.length - 1);
	}
	
	private OnItemSelectedListener itemSelectedListener = new OnItemSelectedListener(){ 
        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) { 
            // TODO Auto-generated method stub 
            /* ����ѡmySpinner ��ֵ����myTextView ��*/ 
            myTextView.setText("��ѡ����ǣ�"+ adapter.getItem(arg2)); 
            /* ��mySpinner ��ʾ*/ 
            arg0.setVisibility(View.VISIBLE); 
        } 
        public void onNothingSelected(AdapterView<?> arg0) { 
            // TODO Auto-generated method stub 
            myTextView.setText("NONE"); 
            arg0.setVisibility(View.VISIBLE); 
        } 
    }; 
    
    private OnTouchListener touchListener = new OnTouchListener(){ 
        public boolean onTouch(View v, MotionEvent event) { 
            // TODO Auto-generated method stub 
            /* ��mySpinner ���أ�������Ҳ���ԣ����Լ�����*/ 
//             v.setVisibility(View.INVISIBLE); 
            return false; 
        } 
    };
    
    private OnFocusChangeListener focusChangeListener = new OnFocusChangeListener(){ 
        public void onFocusChange(View v, boolean hasFocus) { 
        // TODO Auto-generated method stub 
            v.setVisibility(View.VISIBLE); 
        } 
        };

	private void amountRule(EditText amountEdt, String str, String pstr, int location, int len)
	{

		// add a character
		if ((len + 1) == str.length())
		{
			// the character which just entered
			String addValue = str.substring(location, location + 1);
			// delete and need add the character".'
			if (".".equals(addValue))
			{
				int position = str.indexOf(".");
				amountEdt.setText(str);
				Editable editable = amountEdt.getText();
				Selection.setSelection(editable, position + 1);
			} else
			{
				// pstr represent the string before TextChanged
				String[] strArr = pstr.split("\\.");
				int posQuote = pstr.indexOf(".");
				// the string before point is null
				if (pstr.startsWith("."))
				{
					if (pstr.length() > 2 && location != 0)
					{
						// С�����Ϊ�գ����ҹ����С�����
						amountCal(1, amountEdt, addValue, location, posQuote, pstr, 0);
					}
				} else
				{
					// ǰ��������������������������������������
					if (strArr[0].startsWith("0") && !"0".equals(strArr[0]) && location != 0)
					{
						int pLen = len;
						int strInt = Integer.parseInt(strArr[0]);
						strArr[0] = String.valueOf(strInt);

						if (pstr.endsWith("."))
							pstr = strArr[0] + ".";
						else
							pstr = strArr[0] + "." + strArr[1];
						posQuote = pstr.indexOf(".");
						len = pstr.length();

						if ((pLen - location) > len)
						{
							location = 1;
						} else
							location = len - (pLen - location);
					}
					// the string before point is zero
					if ("0".equals(strArr[0]))
					{
						if (location <= (posQuote + 1))
						{
							if (pstr.endsWith(".") && location <= posQuote)
							{
								amountCal(4, amountEdt, addValue, location, posQuote, pstr, 0);
							} else if (!pstr.endsWith("."))
							{
								if (strArr[1].length() == 1)
								{
									amountCal(4, amountEdt, addValue, location, posQuote, pstr, 1);
								} else if (strArr[1].length() == 2)
								{
									amountCal(4, amountEdt, addValue, location, posQuote, pstr, 2);
								}
							}
						} else
						{
							if (!pstr.endsWith("."))
							{
								if (strArr[1].length() == 2)
								{
									String lastValue = pstr.substring(posQuote + 1, pstr.length());
									int lastInt = Integer.parseInt(lastValue);
									if (lastInt == 0)
									{
										// С����ǰΪ�㣬���ҹ����С�������һλ֮��
										// ����С���������λ�����㣬
										amountCal(2, amountEdt, addValue, location, posQuote, pstr, 0);
									} else
									{
										// С����ǰΪ�㣬���ҹ����С�������һλ֮��
										// ����С���������λ�������㣬
										amountCal(3, amountEdt, addValue, location, posQuote, pstr, 0);
									}
								}
							}
						}
					} else if (!"0".equals(strArr[0]))
					{
						int addInt = Integer.parseInt(addValue);
						if (location == 0 && addInt == 0)
						{
							if (pstr.endsWith("."))
							{
								amountCal(6, amountEdt, addValue, location, posQuote, pstr, 0);
							} else
							{
								if (strArr[1].length() == 1)
								{
									amountCal(6, amountEdt, addValue, location, posQuote, pstr, 1);
								} else if (strArr[1].length() == 2)
								{
									amountCal(6, amountEdt, addValue, location, posQuote, pstr, 2);
								}
							}
						} else if (location > posQuote)
						{
							if (!pstr.endsWith("."))
							{
								if (strArr[1].length() == 2)
								{
									// С����ǰ��Ϊ�㣬���ҹ����С����󣬲���С���������λ
									amountCal(5, amountEdt, addValue, location, posQuote, pstr, 0);
								}
							}
						}

					}
				}
			}
		} else if ((len - 1) == str.length())
		{ // ��ֹɾ��С����
			if (!str.contains("."))
			{
				amountEdt.setText(pstr);
				amountEdt.setSelection(len - 1);
			}

		}
	}

	  //status: С����󳤶�
    private static void  amountCal(int type,EditText amountEdt,String addValue,int location,int posQuote,String pstr,int status){
    	int addInt = Integer.parseInt(addValue);
    	double result = 0;
    	DecimalFormat df = null;
    	String amount;
    	Editable editable;
    	double strDou;
    	boolean sign = false;
    	int flag = 0;
    	switch(type){
	    	case 1:
				if(location-posQuote == 1){
					String lastValue = pstr.substring(location,pstr.length());
					double lastDou = Double.parseDouble(lastValue);
					result = addInt+lastDou*0.01;
					flag = 1;
				}else if(location-posQuote == 2){
					String beginValue = pstr.substring(1, location);
					double beginDou = Double.parseDouble(beginValue);
					String lastValue = pstr.substring(location,pstr.length());
					double lastDou = Double.parseDouble(lastValue);
					result = beginDou+addInt*0.1+lastDou*0.01;
					flag = 2;
				}else if(location-posQuote == 3){
					String beginValue = pstr.substring(1, location);
					double beginDou = Double.parseDouble(beginValue);
					result = beginDou*0.1+addInt*0.01;
					flag = 3;
				}
				df = new DecimalFormat("0.00");
				amount = df.format(result);
				amountEdt.setText(amount);
				editable = amountEdt.getText();
				if(flag==1){
					Selection.setSelection(editable,posQuote+2);
				}else if(flag==2){
					Selection.setSelection(editable,posQuote+3);
				}else if(flag==3){
					Selection.setSelection(editable,posQuote+4);
				}
	    		break;
	    	case 2:
				strDou = Double.parseDouble(pstr);
				if(location-posQuote == 2){
					result = addInt*0.1+strDou;
					sign = true;
				}else if(location-posQuote == 3){
					result = addInt*0.01+strDou;
					sign = false;
				}
				df = new DecimalFormat("0.00");
				amount = df.format(result);
				amountEdt.setText(amount);
				editable = amountEdt.getText();
				if(sign)
					Selection.setSelection(editable,posQuote+2);
				else 
					Selection.setSelection(editable,posQuote+3);
				break;
	    	case 3:
	    		String preValue = pstr.substring(0,location);
				double preDou = Double.parseDouble(preValue);
				if(location-posQuote == 2){
					String endValue = pstr.substring(location,pstr.length());
					double lastDou = Double.parseDouble(endValue);
					result = preDou*10+addInt*0.1+lastDou*0.01;
					sign = true;
				}else if(location-posQuote == 3){
					result = preDou*10+addInt*0.01;
					sign = false;
				}
				df = new DecimalFormat("0.00");
				amount = df.format(result);
				amountEdt.setText(amount);
				editable = amountEdt.getText();
				if(sign)
					Selection.setSelection(editable,posQuote+2);
				else 
					Selection.setSelection(editable,posQuote+3);
				break;
	    	case 4:
				strDou = Double.parseDouble(pstr);
				if(location==0)
					result = addInt*10+strDou;
				else
					result = strDou+addInt;
				if(location==2)flag=2;
				if(status==0)
					df = new DecimalFormat("0.");
				else if(status==1)
					df = new DecimalFormat("0.0");
				else if(status==2)
					df = new DecimalFormat("0.00");
				amount = df.format(result);
				amountEdt.setText(amount);
				editable = amountEdt.getText();
				if(flag==2)
					Selection.setSelection(editable,posQuote+1);
				else
					Selection.setSelection(editable,posQuote);
	    		break;
	    	case 5:
				
				if(location-posQuote == 1){
					String preValue6 = pstr.substring(0,location-1);
					String lastValue = pstr.substring(location,pstr.length());
					double preDou6 = Double.parseDouble(preValue6);
					double lastDou = Double.parseDouble(lastValue);
					result = preDou6*10+addInt+lastDou*0.01;
					flag = 1;
				}else if(location-posQuote == 2){
					String preValue6 = pstr.substring(0,location);
					String lastValue = pstr.substring(location,pstr.length());
					double preDou6 = Double.parseDouble(preValue6);
					double lastDou = Double.parseDouble(lastValue);
					result = preDou6*10+addInt*0.1+lastDou*0.01;
					flag = 2;
				}else if(location-posQuote == 3){
					String preValue6 = pstr.substring(0,location);
					double preDou6 = Double.parseDouble(preValue6);
					result = preDou6*10+addInt*0.01;
					flag = 3;
				}
				df = new DecimalFormat("0.00");
				amount = df.format(result);
				amountEdt.setText(amount);
				editable = amountEdt.getText();
				if(flag==1)
					Selection.setSelection(editable,posQuote+2);
				else if(flag==2)
					Selection.setSelection(editable,posQuote+3);
				else if(flag==3)
					Selection.setSelection(editable,posQuote+4);
	    		break;
	    	case 6:
	    		strDou = Double.parseDouble(pstr);
				result = strDou;
				if(status==0)
					df = new DecimalFormat("0.");
				else if(status==1)
					df = new DecimalFormat("0.0");
				else if(status==2)
					df = new DecimalFormat("0.00");
				amount = df.format(result);
				amountEdt.setText(amount);
				editable = amountEdt.getText();
				Selection.setSelection(editable,0);
	    		break;
	    	default:
	    		break;
    	}
    }
}