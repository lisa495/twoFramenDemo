package com.base.framework.viewWiget.common;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextPaint;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.base.framework.R;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * 表单条目View
 */

public class TableTextView extends LinearLayout implements View.OnClickListener {

    private static List<TableTextView> tableTextViewList = new ArrayList<>();
    private static List<CharSequence> textList = new ArrayList<>();//存贮左侧textView的显示内容

    private final int INPUT_TYPE_TEXT = 0x0;//文本
    private final int INPUT_TYPE_NUMBER = 0x1;//数字
    private final int INPUT_TYPE_NUMBER_DECIMAL = 0x2;//小数
    private final int INPUT_TYPE_PHONE = 0x3;//手机号
    private final int INPUT_TYPE_PASSWORD = 0x4;//密码
    private final int INPUT_TYPE_TEXTEMAILADDRESS = 0x5;//邮箱

    private TextView leftTextView, middleTextView, rightTextView;//左中右两个textView

    private View dividerView;//分割线view
    private ImageView rightImageView;

    private EditText inputView;//输入框

    private OnClickListener listener;//点击事件

    public TableTextView(Context context) {
        super(context);
        init(context);
    }

    public TableTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
        setAttributes(context, attrs);
        setLeftViewWidth(leftTextView);
    }

    public TableTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
        setAttributes(context, attrs);
        setLeftViewWidth(leftTextView);
    }

    private void init(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_item_table_text, this);
        setOrientation(VERTICAL);
        leftTextView = view.findViewById(R.id.left_info_view);
        middleTextView = view.findViewById(R.id.middle_info_view);
        inputView = view.findViewById(R.id.inputView);
        rightTextView = view.findViewById(R.id.right_text_view);
        rightImageView = view.findViewById(R.id.right_image_view);
        dividerView = view.findViewById(R.id.divider_line);

        rightTextView.setOnClickListener(this);
        rightImageView.setOnClickListener(this);

        tableTextViewList.add(this);

    }

    private void setAttributes(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.table_text);
        for (int i = 0; i < array.getIndexCount(); i++) {
            int attr = array.getIndex(i);
//            switch (attr) {
            if (attr == R.styleable.table_text_table_leftText) {
                leftTextView.setText(array.getText(attr));
                textList.add(array.getText(attr));
            } else if (attr == R.styleable.table_text_table_middleText) {
                middleTextView.setText(array.getText(attr));
            } else if (attr == R.styleable.table_text_table_rightText) {
                rightTextView.setText(array.getText(attr));
            } else if (attr == R.styleable.table_text_table_leftText_color) {
                leftTextView.setTextColor(array.getColor(attr, Color.parseColor("#4f525a")));
            } else if (attr == R.styleable.table_text_table_middleText_color) {
                middleTextView.setTextColor(array.getColor(attr, Color.parseColor("#4f525a")));
            } else if (attr == R.styleable.table_text_table_rightText_color) {
                rightTextView.setTextColor(array.getColor(attr, Color.parseColor("#999999")));
            } else if (attr == R.styleable.table_text_table_leftText_size) {
                leftTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, array.getDimension(attr, 15));
            } else if (attr == R.styleable.table_text_table_middleText_size) {
                middleTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, array.getDimension(attr, 15));
            } else if (attr == R.styleable.table_text_table_rightText_size) {
                rightTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, array.getDimension(attr, 11));
                rightImageView.setVisibility(View.GONE);
            } else if (attr == R.styleable.table_text_table_hideBottomLime) {
                dividerView.setVisibility(array.getBoolean(attr, false) ? View.GONE : View.VISIBLE);
            } else if (attr == R.styleable.table_text_table_inputHint) {
                inputView.setHint(array.getText(attr));
            } else if (attr == R.styleable.table_text_table_showInputView) {
                boolean tempValue = array.getBoolean(attr, false);
                inputView.setVisibility(tempValue ? View.VISIBLE : View.GONE);
                middleTextView.setVisibility(tempValue ? View.GONE : View.VISIBLE);
            } else if (attr == R.styleable.table_text_table_input_color) {
                inputView.setTextColor(array.getColor(attr, Color.parseColor("#999999")));
            } else if (attr == R.styleable.table_text_table_input_size) {
                inputView.setTextSize(TypedValue.COMPLEX_UNIT_PX, array.getDimension(attr, 11));
            } else if (attr == R.styleable.table_text_table_inputHint_color) {
                inputView.setTextSize(TypedValue.COMPLEX_UNIT_PX, array.getDimension(attr, 11));
            } else if (attr == R.styleable.table_text_table_contentInputTypeValue) {
                int inputType = array.getInt(attr, INPUT_TYPE_TEXT);
                initContentInputType(inputType);
            } else if (attr == R.styleable.table_text_table_maxLength) {
                int value = array.getInt(attr, 16);
                inputView.setFilters(new InputFilter[]{new InputFilter.LengthFilter(value)});
            } else if (attr == R.styleable.table_text_table_rightImageRes) {
                rightImageView.setBackgroundResource(array.getResourceId(attr, 0));
                rightTextView.setVisibility(View.GONE);
            }
        }

    }

    private void initContentInputType(int inputType) {
        switch (inputType) {
            case INPUT_TYPE_TEXT:
                inputView.setInputType(InputType.TYPE_CLASS_TEXT);
                break;
            case INPUT_TYPE_NUMBER:
                inputView.setInputType(InputType.TYPE_CLASS_NUMBER);
                break;
            case INPUT_TYPE_NUMBER_DECIMAL:
                inputView.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                break;
            case INPUT_TYPE_PHONE:
                inputView.setInputType(InputType.TYPE_CLASS_PHONE);
                String digits = "0123456789";
                inputView.setKeyListener(DigitsKeyListener.getInstance(digits));
                break;
            case INPUT_TYPE_PASSWORD:
                inputView.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                break;
            case INPUT_TYPE_TEXTEMAILADDRESS:
                inputView.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                break;
        }
    }

    private void setLeftViewWidth(TextView leftView) {
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) leftView.getLayoutParams();
        if (null == params) {
            params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        }
        TextPaint textPaint = leftView.getPaint();
        params.width = getMax(textPaint, textList);
        for (TableTextView tableTextView : tableTextViewList) {
            tableTextView.leftTextView.setLayoutParams(params);
        }
    }

    private int getMax(TextPaint textPaint, List<CharSequence> list) {
        List<Integer> list1 = new ArrayList();
        for (CharSequence str : list) {
            float textLength = textPaint.measureText(str, 0, str.length());
            list1.add((int) textLength);
        }
        return Collections.max(list1);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        textList.clear();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.right_text_view) {
            //右侧文字点击
            onRightTextClick(inputView, rightTextView);
            if (null != listener) {
                listener.onClick(v);
            }
        } else if (id == R.id.right_image_view) {
            onRightImageClick(inputView, rightImageView);
            if (null != listener) {
                listener.onClick(v);
            }
        }
    }

    protected void onRightImageClick(EditText inputView, ImageView imageView) {
    }

    protected void onRightTextClick(EditText inputView, TextView rightTextView) {
    }

    public void setOnTextChangeListener(TextWatcher textChangeListener) {
        inputView.addTextChangedListener(textChangeListener);
    }

    public void setInputOnFocusChangeListener(OnFocusChangeListener onFocusChangeListener) {
        inputView.setOnFocusChangeListener(onFocusChangeListener);
    }

    public void setMiddleText2View(CharSequence charSequence) {
        middleTextView.setText(charSequence);
    }

    public void setOnRightViewClick(OnClickListener listener) {
        this.listener = listener;
    }

    public String getInputText() {
        return getInputView().getText().toString();
    }

    public EditText getInputView() {
        return inputView;
    }

    public static class TableTextWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }
}
